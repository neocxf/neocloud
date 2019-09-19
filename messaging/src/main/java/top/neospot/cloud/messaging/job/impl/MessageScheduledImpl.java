package top.neospot.cloud.messaging.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.neospot.cloud.common.model.Message;
import top.neospot.cloud.messaging.job.MessageScheduled;
import top.neospot.cloud.messaging.mapper.MessageMapper;
import top.neospot.cloud.messaging.service.RpTransactionMessageService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@Service
@Slf4j
public class MessageScheduledImpl implements MessageScheduled, InitializingBean, DisposableBean {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RpTransactionMessageService rpTransactionMessageService;

    private Timer waitingConfirmTimer;
    private Timer sendingTimeoutTimer;

    @Override
    public void handleWaitingConfirmTimeOutMessages() {
        waitingConfirmTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("executing handleWaitingConfirmTimeOutMessages task ...");

                LambdaQueryWrapper<Message> sending = Wrappers.<Message>lambdaQuery().eq(Message::getStatus, "WAIT_CONFIRM").le(Message::getMessageSendTimes, 5).apply("timediff({0}, last_update_time) >= time('00:05:00')", LocalDateTime.now());
                System.out.println(sending.getSqlSelect());
                System.out.println(sending.getCustomSqlSegment());
                List<Message> messages = messageMapper.selectList(sending);

                System.out.println(messages);


                //TODO: send to mq
            }
        }, 1000, 300000);
    }

    @Override
    public void handleSendingTimeOutMessage() {
        sendingTimeoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("executing handleSendingTimeOutMessage task ...");

                LambdaQueryWrapper<Message> sending = Wrappers.<Message>lambdaQuery().eq(Message::getStatus, "SENDING").le(Message::getMessageSendTimes, 5).apply("timediff({0}, last_update_time) >= time('00:05:00')", LocalDateTime.now());
                List<Message> messages = messageMapper.selectList(sending);

                System.out.println(messages);


                //TODO: send to mq

                //TODO: message-send-times + 1
            }
        }, 5000, 300000);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        waitingConfirmTimer = new Timer(true);
        sendingTimeoutTimer = new Timer(true);

        handleSendingTimeOutMessage();
        handleWaitingConfirmTimeOutMessages();
    }


    @Override
    public void destroy() throws Exception {
        waitingConfirmTimer.cancel();
        sendingTimeoutTimer.cancel();
    }
}
