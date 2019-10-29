package top.neospot.cloud.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.neospot.cloud.common.constants.ENV;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.common.util.UUIDUtil;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.order.entity.OrderEntity;
import top.neospot.cloud.order.mapper.OrderEntityMapper;
import top.neospot.cloud.order.service.OrderEntityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/30.
 */
@Component
public class OrderEntityServiceImpl extends ServiceImpl<OrderEntityMapper, OrderEntity> implements OrderEntityService, InitializingBean, DisposableBean {
    private Timer bgOrderPayTimer;

    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.bgOrderPayTimer = new Timer(true);

        this.bgOrderPayTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                resendPaidYetNotShippedMessage();
            }
        }, 5000, 600000);
    }

    private void resendPaidYetNotShippedMessage() {
        List<OrderEntity> orderEntities = list(Wrappers.<OrderEntity>lambdaQuery().eq(OrderEntity::getStatus, "支付成功").apply("timediff({0}, last_update_time) >= time('00:10:00')", LocalDateTime.now()));

        orderEntities.forEach(order -> {
            Message message = new Message();
            message.setMessageId(order.getLastMessageId());
            message.setMessageDateType("json");
            message.setConsumerQueue(ENV.REWARD_QUEUE);
            message.setMessageBody(order.getField1());
            rpTransactionMessageService.directSendMessage(message);
        });
    }

    @Override
    public void destroy() throws Exception {
        this.bgOrderPayTimer.cancel();
    }
}
