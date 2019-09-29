package top.neospot.cloud.messaging.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.messaging.mapper.MessageMapper;

import javax.jms.Queue;
import java.util.Date;

/**
 *
 * SENDING --> WAITING_CONFIRM
 *
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@Service
@Slf4j
public class RpTransactionMessageServiceImpl implements RpTransactionMessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

    @Override
    @Transactional
    public int saveMessageWaitingConfirm(Message message) {
        log.debug("saving WAITING_CONFIRM message: {}", message);
        message.setStatus("WAITING_CONFIRM");
        return messageMapper.insert(message);
    }

    @Override
    @Transactional
    public void confirmAndSendMessage(String messageId) {
        Message message = messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));

        if (message == null) return;

        message.setStatus("SENDING");
        messageMapper.updateById(message);

        log.debug("confirm, and sending message: {}", message);

        jmsTemplate.convertAndSend(message.getConsumerQueue(), message);

    }

    @Override
    @Transactional
    public int saveAndSendMessage(Message message) {
        message.setStatus("SENDING");
        message.setMessageSendTimes(0);
        int result = messageMapper.insert(message);

        jmsTemplate.convertAndSend(message.getConsumerQueue(), message);

        return result;
    }

    @Override
    @Transactional
    public void directSendMessage(Message message) {
        jmsTemplate.convertAndSend(message.getConsumerQueue(), message);
    }

    @Override
    @Transactional
    public void reSendMessage(Message message) {
        message.incrementTimes();
        message.setEditTime(new Date());
        messageMapper.updateById(message);
        jmsTemplate.convertAndSend(message.getConsumerQueue(), message);
    }

    @Override
    @Transactional
    public void reSendMessageByMessageId(String messageId) {
        Message message = getMessageByMessageId(messageId);

        if (message.getMessageSendTimes() >= 5) {
            setMessageToAlreadyDead(message);
        } else {
            reSendMessage(message);
        }

    }

    private void setMessageToAlreadyDead(Message message) {
        message.setAlreadyDead(true);
        messageMapper.updateById(message);
    }

    @Override
    @Transactional
    public void setMessageToAlreadyDead(String messageId) {
        Message message = getMessageByMessageId(messageId);
        setMessageToAlreadyDead(message);
    }

    @Override
    public Message getMessageByMessageId(String messageId) {
        return messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));
    }

    @Override
    @Transactional
    public void deleteMessageByMessageId(String messageId) {
        messageMapper.delete(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));
    }

    @Override
    @Transactional
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) {
        Page<Message> page = new Page<>(1, batchSize);

        IPage<Message> messageIPage = messageMapper.selectPage(page, Wrappers.<Message>lambdaQuery().eq(Message::getAlreadyDead, true).eq(Message::getConsumerQueue, queueName).orderByAsc(Message::getId));

        messageIPage.getRecords().forEach(this::directSendMessage);
    }

    @Override
    public IPage<Message> listPage(Page<Message> page, QueryWrapper<Message> queryWrapper) {
        return messageMapper.selectPage(page, queryWrapper);
    }

}
