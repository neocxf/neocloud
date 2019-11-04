package top.neospot.cloud.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.neospot.cloud.common.constants.ENV;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.messaging.model.DeductReward;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderItem;
import top.neospot.cloud.order.mapper.OrderMapper;
import top.neospot.cloud.order.remote.RewardServiceClient;
import top.neospot.cloud.order.service.OrderItemService;
import top.neospot.cloud.order.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/30.
 */
@Component
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService, InitializingBean, DisposableBean {
    private Timer bgOrderPayTimer;

    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    RewardServiceClient rewardServiceClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.bgOrderPayTimer = new Timer(true);

        this.bgOrderPayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                resendYetNotShippedMessage();
            }
        }, 5000, 600000);
    }

    private void resendYetNotShippedMessage() {

        List<Order> orderEntities = list(Wrappers.<Order>lambdaQuery().in(Order::getStatus, "支付成功", "扣除积分成功").apply("timediff({0}, last_update_time) >= time('00:10:00')", LocalDateTime.now()));

        orderEntities.forEach(order -> {
            Message message = new Message();
            message.setMessageId(order.getLastMessageId());
            message.setMessageDataType("json");
            message.setConsumerQueue(ENV.REWARD_QUEUE);
            message.setMessageBody(order.getField1());

            if (Objects.equals(order.getStatus(), "未支付")) { // reward-service failure
                DeductReward deductReward = JSONObject.parseObject(order.getField1(), DeductReward.class);
                try {
                    rpTransactionMessageService.saveMessageWaitingConfirm(message);
                    rewardServiceClient.tryDeductReward(deductReward);
                } catch (Exception ex) {
                    rpTransactionMessageService.deleteMessageByMessageId(message.getMessageId());
                }
                order.setStatus("支付成功");
                updateById(order);

            } else {
                rpTransactionMessageService.directSendMessage(message);
            }

        });
    }

    @Override
    public void destroy() throws Exception {
        this.bgOrderPayTimer.cancel();
    }

    @Override
    public boolean removeOrders(Wrapper<Order> wrapper) {
        List<Order> orderList = list(wrapper);

        orderList.forEach(order -> {
            orderItemService.remove(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, order.getOrderId()));
        });

        remove(wrapper);

        return true;
    }

    @Override
    public boolean batchInsertOrderLines(List<OrderItem> orderItems) {
        return orderItemService.saveBatch(orderItems);
    }

    public Order showOrder(Long orderId) {
        return getBaseMapper().findOrderById(orderId);
    }
}
