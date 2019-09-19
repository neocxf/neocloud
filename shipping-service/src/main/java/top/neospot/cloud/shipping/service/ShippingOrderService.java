package top.neospot.cloud.shipping.service;

import com.alibaba.fastjson.JSON;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import top.neospot.cloud.common.model.Message;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/19.
 */
@Service
public class ShippingOrderService {

    @JmsListener(destination = "reward_shipping")
    public void receiveMessage(Message message) {
        System.out.println("Received <" + message + ">");
        System.out.println(JSON.parse(message.getMessageBody()));
    }
}
