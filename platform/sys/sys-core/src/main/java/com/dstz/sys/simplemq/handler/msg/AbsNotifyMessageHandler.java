package com.dstz.sys.simplemq.handler.msg;

import com.dstz.sys.api.jms.JmsHandler;
import com.dstz.sys.api.jms.model.JmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 做消息类型的公共逻辑 ：如日志等
 * @author Jeff
 * @param <T>
 */
public abstract class AbsNotifyMessageHandler<T extends Serializable> implements JmsHandler<T> {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbsNotifyMessageHandler.class);

	/**
	 * 消息类型名
	 * @return
	 */
	public abstract String getTitle();

	/**
	 * 是否默认选中
	 * @return
	 */
	public boolean getIsDefault() {
		return false;
	}
	
	/**
	 * 是否支持 HTML 内容
	 * @return
	 */
	public boolean getSupportHtml() {
		return true;
	}
	
	
	 @Override
	 public boolean handlerMessage(JmsDTO<T> message) {
		 // 日志记录一下？？？
		 return  sendMessage(message.getData());
	 }
	 
	 /**
	  * 发送消息处理器具体实现 不同消息的发送
	  * @param data
	  * @return
	  */
	 public abstract boolean sendMessage(T data);

}
