package top.neospot.cloud.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.neospot.cloud.common.constants.ENV;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.common.util.UUIDUtil;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.messaging.model.DeductReward;
import top.neospot.cloud.order.dto.NewOrderDto;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderItem;
import top.neospot.cloud.order.mapper.OrderMapper;
import top.neospot.cloud.order.remote.RewardServiceClient;
import top.neospot.cloud.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RewardServiceClient rewardServiceClient;

    @Autowired
    RedissonClient redissonClient;

    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @GetMapping("/orders/{id}")
    public Order fetchOneOrder(@PathVariable("id") Long orderId) {
        return orderService.showOrder(orderId);
    }

    @PutMapping("/orders/{orderId}")
    public boolean confirmDelivery(@PathVariable("orderId") Long orderId){
        log.info("confirm the delivery of order: {}", orderId);

        Order orderEntity = orderService.getById(orderId);
        String messageId = orderEntity.getLastMessageId();

        rpTransactionMessageService.deleteMessageByMessageId(messageId);

        orderEntity.setStatus("已发货").setLastMessageId(null);
        orderService.updateById(orderEntity);

        log.info("order[{}] has been shipped", orderId);

        return true;
    }

    /**
     *  when this action was triggered, it means that pre-check has already done, we should minus the reward point and do the delivery
     *   TODO: 重复支付的问题
     */
    @PostMapping("/orders/pay/{orderId}")
    public void payOrderByReward(@PathVariable(value = "orderId") Long orderId) throws Exception {
        Order persistOrder = orderService.showOrder(orderId);

        if (persistOrder == null || !Objects.equals(persistOrder.getStatus(), "支付成功")) {
            return;
        }


        RLock lock = redissonClient.getLock("/order-pay-" + orderId);

        boolean successGainedLock = lock.tryLock(1, 5, TimeUnit.SECONDS);

        if (! successGainedLock) return;

        DeductReward deductReward = new DeductReward();
        deductReward.setCredit(persistOrder.totalCost().longValue());
        deductReward.setUserId(persistOrder.getUserId());
        deductReward.setOrderId(persistOrder.getOrderId());

        String deductRewardJson = JSONObject.toJSONString(deductReward);

        log.info("trying to pay the order[{}] with reward0", orderId);

        // 尝试扣除积分，将该逻辑发送到可靠消息部分
        Message message = new Message();
        message.setMessageId(UUIDUtil.messageId());
        message.setMessageDateType("json");
        message.setConsumerQueue(ENV.REWARD_QUEUE);
        message.setMessageBody(deductRewardJson);

        persistOrder.setLastMessageId(message.getMessageId());
        persistOrder.setField1(deductRewardJson);
        persistOrder.setStatus("支付成功");

        orderService.updateById(persistOrder);

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

        persistOrder.setStatus("扣除积分成功");

        log.info("the order[{}] pay success, waiting for shipping", orderId);
        orderService.update(persistOrder, Wrappers.<Order>lambdaUpdate().eq(Order::getLastMessageId, persistOrder.getLastMessageId()));

        lock.unlock();
    }

    @Transactional
    @PostMapping("/orders")
    public void newOrder(@RequestBody NewOrderDto newOrderDto) {
        Order order = new Order();
        order.setUserId(newOrderDto.getUserId());
        order.setAddressId(newOrderDto.getAddressId());
        order.setStatus("未支付");

        orderService.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (NewOrderDto.OrderLine line : newOrderDto.getLines()) {
            OrderItem item = new OrderItem();
            item.setUserId(newOrderDto.getUserId());
            item.setOrderId(order.getOrderId());
            item.setProductName(line.getProductName());
            item.setProductId(line.getProductId());
            item.setCostPerUnit(Double.valueOf(line.getCostPerUnit()));
            item.setNum(line.getNum());
            orderItems.add(item);
        }

        orderService.batchInsertOrderLines(orderItems);



    }

}

