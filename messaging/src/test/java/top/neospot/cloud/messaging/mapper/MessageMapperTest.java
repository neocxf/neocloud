package top.neospot.cloud.messaging.mapper;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.messaging.MessagingAppTest;

import java.util.HashMap;
import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/19.
 */
public class MessageMapperTest extends MessagingAppTest {
    @Autowired
    private MessageMapper messageMapper;

    @Before
    public void init() throws Exception {
        Message message = new Message();
        message.setMessageId("1");
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

        if (messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, 1)) == null) {
            messageMapper.insert(message);
        }
    }

    @Test
    public void selectTest() {
        Message message = messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, "ee61e7b099534f71ab29846ccd8304f9"));
        System.out.println(message);
    }

    @Test
    public void sendingTest() {
        Message message = messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, 1));
        System.out.println(JSON.parse(message.getMessageBody()));

        Message m = new Message();
        m.setStatus("SENDING");

        messageMapper.update(m, Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, 1));
    }

    @Test
    public void sendOKTest() {
        Message message = messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, 1));
        System.out.println(JSON.parse(message.getMessageBody()));

        Message m = new Message();
        m.setStatus("SENT");

        messageMapper.update(m, Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, 1));
    }
}
