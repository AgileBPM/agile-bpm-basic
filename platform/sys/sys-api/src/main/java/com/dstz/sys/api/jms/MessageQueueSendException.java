package com.dstz.sys.api.jms;

/**
 * 消息队列发送异常
 *
 * @author wacxhs
 */
public class MessageQueueSendException extends RuntimeException {

    public MessageQueueSendException(String message) {
        super(message, null, false, false);
    }

}
