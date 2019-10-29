package top.neospot.cloud.messaging.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RpTransactionMessageServiceImpl  extends ServiceImpl<MessageMapper, Message> implements RpTransactionMessageService {

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
    public boolean saveMessageWaitingConfirm(Message message) {
        log.debug("saving WAITING_CONFIRM message: {}", message);

        message.setStatus("WAITING_CONFIRM");

        Message persistedMessage = getOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, message.getMessageId()));

        if (persistedMessage != null) {
            message.setId(persistedMessage.getId());
            message.setDeleted(0);
            return updateById(message);
        }

        return save(message);
    }

    @Override
    @Transactional
    public void confirmAndSendMessage(String messageId) {

        Message message = getOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));

        if (message == null) return;

        message.setStatus("SENDING");

        updateById(message);

        log.debug("confirm, and sending message: {}", message);

        jmsTemplate.convertAndSend(message.getConsumerQueue(), message);

    }

    @Override
    @Transactional
    public boolean saveAndSendMessage(Message message) {
        message.setStatus("SENDING");
        message.setMessageSendTimes(0);
        boolean result = save(message);

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
        updateById(message);
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
        updateById(message);
    }

    @Override
    @Transactional
    public void setMessageToAlreadyDead(String messageId) {
        Message message = getMessageByMessageId(messageId);
        setMessageToAlreadyDead(message);
    }

    @Override
    public Message getMessageByMessageId(String messageId) {
        return getOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));
    }

    @Override
    @Transactional
    public void deleteMessageByMessageId(String messageId) {
        remove(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));
    }

    @Override
    @Transactional
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) {
        Page<Message> page = new Page<>(1, batchSize);

        IPage<Message> messageIPage = page(page, Wrappers.<Message>lambdaQuery().eq(Message::getAlreadyDead, true).eq(Message::getConsumerQueue, queueName).orderByAsc(Message::getId));

        messageIPage.getRecords().forEach(this::directSendMessage);
    }


}
