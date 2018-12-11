package com.dstz.sys.api.jms.producer;

import com.dstz.sys.api.jms.MessageQueueSendException;
import com.dstz.sys.api.jms.model.JmsDTO;

import java.util.List;

/**
 * 消息发送提供者
 *
 * @author jeff
 */
public interface JmsProducer {

    /**
     * 发送到队列中
     *
     * @param message 发送消息
     * @throws MessageQueueSendException 消息队列发送异常
     */
    void sendToQueue(JmsDTO message) throws MessageQueueSendException;

    /**
     * 发送列表到队列中
     *
     * @param messages 发送消息集
     * @throws MessageQueueSendException 消息队列发送异常
     */
    void sendToQueue(List<JmsDTO> messages) throws MessageQueueSendException;

}