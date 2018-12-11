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

	private static final Logger LOGGER = LoggerFactory.getLogger(AbsNotifyMessageHandler.class);


	public abstract String getTitle();

	public boolean getIsDefault() {
		return false;
	}

	public boolean getSupportHtml() {
		return true;
	}
	
	
	 @Override
	 public boolean handlerMessage(JmsDTO<T> message) {
		 // 日志记录一下？？？
		 return  sendMessage(message.getData());
	 }

	 public abstract boolean sendMessage(T data);

}
