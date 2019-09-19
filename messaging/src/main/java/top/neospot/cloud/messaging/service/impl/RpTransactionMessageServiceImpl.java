package top.neospot.cloud.messaging.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.messaging.mapper.MessageMapper;
import top.neospot.cloud.messaging.service.RpTransactionMessageService;

import javax.jms.Queue;

/**
 *
 * SENDING --> WAITING_CONFIRM
 *
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@Service
public class RpTransactionMessageServiceImpl implements RpTransactionMessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Override
    @Transactional
    public int saveMessageWaitingConfirm(Message rpTransactionMessage) {
        int result = messageMapper.insert(rpTransactionMessage);

        return result;
    }

    @Override
    @Transactional
    public void confirmAndSendMessage(String messageId) {
        Message message = messageMapper.selectOne(Wrappers.<Message>lambdaQuery().eq(Message::getMessageId, messageId));

        if (message == null) return;

        message.setStatus("WAITING_CONFIRM");
        messageMapper.updateById(message);

        jmsTemplate.convertAndSend(queue, message.getMessageBody());

    }

    @Override
    @Transactional
    public int saveAndSendMessage(Message message) {
        message.setStatus("SENDING");
        message.setAlreadyDead(false);
        message.setMessageSendTimes(0);
        int result = messageMapper.insert(message);

        jmsTemplate.convertAndSend(queue, message.getMessageBody());

        return result;
    }

    @Override
    @Transactional
    public void directSendMessage(Message message) {
        jmsTemplate.convertAndSend(queue, message.getMessageBody());
    }

    @Override
    @Transactional
    public void reSendMessage(Message rpTransactionMessage) {

    }

    @Override
    @Transactional
    public void reSendMessageByMessageId(String messageId) {

    }

    @Override
    @Transactional
    public void setMessageToAlreadyDead(String messageId) {

    }

    @Override
    public Message getMessageByMessageId(String messageId) {
        return null;
    }

    @Override
    @Transactional
    public void deleteMessageByMessageId(String messageId) {

    }

    @Override
    @Transactional
    public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) {

    }

    @Override
    @Transactional
    public Page<Message> listPage(QueryWrapper<Message> queryWrapper) {
        return null;
    }
}
