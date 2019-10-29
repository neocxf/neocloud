package top.neospot.cloud.messaging.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.neospot.cloud.common.model.Message;

public interface RpTransactionMessageService {
    String sayHello(String name);


    /**
     * 预存储消息.
     */
    boolean saveMessageWaitingConfirm(Message rpTransactionMessage);


    /**
     * 确认并发送消息.
     */
    void confirmAndSendMessage(String messageId);


    /**
     * 存储并发送消息.
     */
    boolean saveAndSendMessage(Message rpTransactionMessage);


    /**
     * 直接发送消息.
     */
    void directSendMessage(Message rpTransactionMessage);


    /**
     * 重发消息.
     */
    void reSendMessage(Message rpTransactionMessage);


    /**
     * 根据messageId重发某条消息.
     */
    void reSendMessageByMessageId(String messageId);


    /**
     * 将消息标记为死亡消息.
     */
    void setMessageToAlreadyDead(String messageId);


    /**
     * 根据消息ID获取消息
     */
    Message getMessageByMessageId(String messageId);

    /**
     * 根据消息ID删除消息
     */
    void deleteMessageByMessageId(String messageId);


    /**
     * 重发某个消息队列中的全部已死亡的消息.
     */
    void reSendAllDeadMessageByQueueName(String queueName, int batchSize);


}