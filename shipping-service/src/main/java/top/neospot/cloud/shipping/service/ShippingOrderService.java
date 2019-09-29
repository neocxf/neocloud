package top.neospot.cloud.shipping.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.messaging.model.DeductReward;
import top.neospot.cloud.shipping.entity.ShippingOrder;
import top.neospot.cloud.shipping.mapper.ShippingOrderMapper;
import top.neospot.cloud.shipping.remote.OrderServiceClient;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/19.
 */
@Service
@Slf4j
public class ShippingOrderService {
    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @Autowired
    private ShippingOrderMapper shippingOrderMapper;

    @Autowired
    private OrderServiceClient orderServiceClient;

    // 重复消费问题
    @JmsListener(destination = "reward_shipping")
    public void receiveMessage(Message message) {
        System.out.println("Received <" + message + ">");
        System.out.println(JSON.parse(message.getMessageBody()));

        DeductReward deductReward = JSONObject.parseObject(message.getMessageBody(), DeductReward.class);

        log.info("shipping the order[{}] that buy through reward point", deductReward.getOrderId());

        ShippingOrder persistShippingOrder = shippingOrderMapper.selectOne(Wrappers.<ShippingOrder>lambdaQuery().eq(ShippingOrder::getOrderId, deductReward.getOrderId()));

        if (persistShippingOrder != null) {
            log.error("order[{}]has already been shipped, do nothing", deductReward.getOrderId() );
            return;
        }

        persistShippingOrder = new ShippingOrder().setProductId(deductReward.getProductId()).setType("reward").setOrderId(deductReward.getOrderId()).setAddr("Shanghai Pudong").setNumber(deductReward.getNumber());

        shippingOrderMapper.insert(persistShippingOrder);

        // callback to confirm the shipping
        orderServiceClient.confirmShipping(deductReward.getOrderId());
    }
}
