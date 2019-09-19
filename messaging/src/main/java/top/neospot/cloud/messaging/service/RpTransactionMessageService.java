package top.neospot.cloud.messaging.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.neospot.cloud.common.model.Message;

public interface RpTransactionMessageService {
 
	/**
	 * 预存储消息. 
	 */
	public int saveMessageWaitingConfirm(Message rpTransactionMessage) ;
	
	
	/**
	 * 确认并发送消息.
	 */
	public void confirmAndSendMessage(String messageId) ;
 
	
	/**
	 * 存储并发送消息.
	 */
	public int saveAndSendMessage(Message rpTransactionMessage) ;
 
	
	/**
	 * 直接发送消息.
	 */
	public void directSendMessage(Message rpTransactionMessage) ;
	
	
	/**
	 * 重发消息.
	 */
	public void reSendMessage(Message rpTransactionMessage) ;
	
	
	/**
	 * 根据messageId重发某条消息.
	 */
	public void reSendMessageByMessageId(String messageId) ;
	
	
	/**
	 * 将消息标记为死亡消息.
	 */
	public void setMessageToAlreadyDead(String messageId) ;
 
 
	/**
	 * 根据消息ID获取消息
	 */
	public Message getMessageByMessageId(String messageId) ;
 
	/**
	 * 根据消息ID删除消息
	 */
	public void deleteMessageByMessageId(String messageId) ;
	
	
	/**
	 * 重发某个消息队列中的全部已死亡的消息.
	 */
	public void reSendAllDeadMessageByQueueName(String queueName, int batchSize) ;
	
	/**
	 * 获取分页数据
	 */
	Page<Message> listPage(QueryWrapper<Message> queryWrapper) ;
 
 
}