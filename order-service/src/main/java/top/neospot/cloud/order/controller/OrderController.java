package top.neospot.cloud.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.neospot.cloud.common.constants.ENV;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.common.util.UUIDUtil;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.messaging.model.DeductReward;
import top.neospot.cloud.order.dto.NewOrderDto;
import top.neospot.cloud.order.entity.OrderEntity;
import top.neospot.cloud.order.mapper.OrderEntityMapper;
import top.neospot.cloud.order.remote.RewardServiceClient;

import java.util.List;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderEntityMapper orderEntityMapper;

    @Autowired
    RewardServiceClient rewardServiceClient;

    @Autowired
    CuratorFramework curatorFramework;

    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @GetMapping("/orders")
    public List<OrderEntity> getAllOrders() {

        return orderEntityMapper.selectList(null);

    }

    @PutMapping("/orders/{orderId}")
    public void confirmDelivery(@PathVariable("orderId") Long orderId){
        OrderEntity orderEntity = orderEntityMapper.selectById(orderId);
        String messageId = orderEntity.getLastMessageId();

        rpTransactionMessageService.deleteMessageByMessageId(messageId);

        orderEntity.setStatus("已发货").setLastMessageId(null);
        orderEntityMapper.updateById(orderEntity);

        log.info("order[{}] has been shipped", orderId);
    }

    /**
     *   TODO: 重复支付的问题
     */
    @PostMapping("/orders/pay/{orderId}")
    public void payOrderByReward(@PathVariable(value = "orderId") Long orderId) throws Exception {
        OrderEntity orderEntity = orderEntityMapper.selectOne(Wrappers.<OrderEntity>lambdaQuery().eq(OrderEntity::getId, orderId).eq(OrderEntity::getStatus, "未支付"));

        if (orderEntity == null) return;

        InterProcessSemaphoreMutex interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorFramework, "/order-pay-" + orderId);

        interProcessSemaphoreMutex.acquire();

        DeductReward deductReward = new DeductReward();
        deductReward.setCredit((long) (orderEntity.getPrice() * orderEntity.getNumber()));
        deductReward.setProductId(orderEntity.getProductId());
        deductReward.setUserId(orderEntity.getUserId());
        deductReward.setOrderId(orderEntity.getId());
        deductReward.setNumber(orderEntity.getNumber());

        log.info("trying to pay the order[{}] with reward0", orderId);

        // 尝试扣除积分，将该逻辑发送到可靠消息部分
        Message message = new Message();
        message.setMessageId(UUIDUtil.messageId());
        message.setMessageDateType("json");
        message.setConsumerQueue(ENV.REWARD_QUEUE);
        message.setMessageBody(JSONObject.toJSONString(deductReward));

        orderEntity.setLastMessageId(message.getMessageId());
        orderEntityMapper.updateById(orderEntity);

        deductReward.setMessageId(message.getMessageId());

        rpTransactionMessageService.saveMessageWaitingConfirm(message);

        try {
            rewardServiceClient.tryDeductReward(deductReward);
        } catch (Exception e) {
            e.printStackTrace();

            log.error("deduct reward error order[{}], rollback the logic, delete the message: {}", orderId, message);
            rpTransactionMessageService.deleteMessageByMessageId(message.getMessageId());
            return;
        }

        // 检查扣分逻辑是否成功，若成功，则真实发送消息给shipping 端发货

        orderEntity.setStatus("支付成功");

        log.info("the order[{}] pay success, waiting for shipping", orderId);
        orderEntityMapper.update(orderEntity, Wrappers.<OrderEntity>lambdaUpdate().eq(OrderEntity::getLastMessageId, orderEntity.getLastMessageId()));

        interProcessSemaphoreMutex.release();
    }

    @PostMapping("/orders")
    public void newOrder(@RequestBody NewOrderDto newOrderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setNumber(newOrderDto.getNumber());
        orderEntity.setPrice(newOrderDto.getPrice());
        orderEntity.setProductId(newOrderDto.getProductId());
        orderEntity.setUserId(newOrderDto.getUserId());
        orderEntity.setStatus("未支付");
        orderEntityMapper.insert(orderEntity);

    }
}

