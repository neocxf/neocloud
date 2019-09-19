package top.neospot.cloud.messaging;

import com.alibaba.fastjson.JSON;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.common.model.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@MapperScan("top.neospot.cloud.messaging.mapper")
@SpringCloudApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2
public class MessagingApp {

    public static void main(String[] args) {
        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(MessagingApp.class, args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending an email message.");
        Message message = new Message();
        message.setMessageId(1L);
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("type", "reward_shipping");
        stringMap.put("product_id", "1");
        stringMap.put("user_id", "1");
        stringMap.put("rewardExchangeId", "1");
        stringMap.put("num", "2");
        stringMap.put("addr", "pudong");
        message.setMessageBody(JSON.toJSONString(stringMap));
        message.setMessageDateType("json");
        message.setAlreadyDead(false);
        message.setConsumerQueue("reward_shipping");
        message.setMessageSendTimes(0);
        message.setCreator("neo");
        message.setEditor("neo");
        message.setStatus("PRE_SEND");
        jmsTemplate.convertAndSend("reward_shipping", message);
    }
}
