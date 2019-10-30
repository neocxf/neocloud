package top.neospot.cloud.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
import top.neospot.cloud.order.service.OrderEntityService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderEntityService orderEntityService;

    @Autowired
    RewardServiceClient rewardServiceClient;

    @Autowired
    RedissonClient redissonClient;

    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @GetMapping("/orders")
    public List<OrderEntity> getAllOrders() {
        System.out.println("hello orders get method");
        return orderEntityService.list();
    }

    @PutMapping("/orders/{orderId}")
    public boolean confirmDelivery(@PathVariable("orderId") Long orderId){
        log.info("confirm the delivery of order: {}", orderId);

        OrderEntity orderEntity = orderEntityService.getById(orderId);
        String messageId = orderEntity.getLastMessageId();

        rpTransactionMessageService.deleteMessageByMessageId(messageId);

        orderEntity.setStatus("已发货").setLastMessageId(null);
        orderEntityService.updateById(orderEntity);

        log.info("order[{}] has been shipped", orderId);

        return true;
    }

    /**
     *   TODO: 重复支付的问题
     */
    @PostMapping("/orders/pay/{orderId}")
    public void payOrderByReward(@PathVariable(value = "orderId") Long orderId) throws Exception {

        OrderEntity orderEntity = orderEntityService.getOne(Wrappers.<OrderEntity>lambdaQuery().eq(OrderEntity::getId, orderId).eq(OrderEntity::getStatus, "未支付"));

        if (orderEntity == null) return;

        RLock lock = redissonClient.getLock("/order-pay-" + orderId);

        boolean successGainedLock = lock.tryLock(1, 5, TimeUnit.SECONDS);

        if (! successGainedLock) return;

        DeductReward deductReward = new DeductReward();
        deductReward.setCredit((long) (orderEntity.getPrice() * orderEntity.getNumber()));
        deductReward.setProductId(orderEntity.getProductId());
        deductReward.setUserId(orderEntity.getUserId());
        deductReward.setOrderId(orderEntity.getId());
        deductReward.setNumber(orderEntity.getNumber());

        String deductRewardJson = JSONObject.toJSONString(deductReward);

        log.info("trying to pay the order[{}] with reward0", orderId);

        // 尝试扣除积分，将该逻辑发送到可靠消息部分
        Message message = new Message();
        message.setMessageId(UUIDUtil.messageId());
        message.setMessageDateType("json");
        message.setConsumerQueue(ENV.REWARD_QUEUE);
        message.setMessageBody(deductRewardJson);

        orderEntity.setLastMessageId(message.getMessageId());
        orderEntityService.updateById(orderEntity);

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
        orderEntity.setField1(deductRewardJson);

        log.info("the order[{}] pay success, waiting for shipping", orderId);
        orderEntityService.update(orderEntity, Wrappers.<OrderEntity>lambdaUpdate().eq(OrderEntity::getLastMessageId, orderEntity.getLastMessageId()));

        lock.unlock();
    }

    @PostMapping("/orders")
    public void newOrder(@RequestBody NewOrderDto newOrderDto) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setNumber(newOrderDto.getNumber());
        orderEntity.setPrice(newOrderDto.getPrice());
        orderEntity.setProductId(newOrderDto.getProductId());
        orderEntity.setUserId(newOrderDto.getUserId());
        orderEntity.setStatus("未支付");
        orderEntityService.save(orderEntity);

    }
}

