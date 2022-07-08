package com.dstz.sys.simplemq.producer;

import com.dstz.sys.api.jms.MessageQueueSendException;
import com.dstz.sys.api.jms.model.JmsDTO;
import com.dstz.sys.api.jms.producer.JmsProducer;
import com.dstz.sys.simplemq.consumer.AbstractMessageQueue;

import java.util.List;

/**
 * 同步队列发送，内部应用
 *
 * @author wacxhs
 */
public class SynchronousQueueProducer extends AbstractMessageQueue implements JmsProducer {

    @Override
    public void sendToQueue(JmsDTO message) throws MessageQueueSendException {
        handleMessage(message);
    }

    @Override
    public void sendToQueue(List<JmsDTO> messages) throws MessageQueueSendException {
        if (messages != null && !messages.isEmpty()) {
            for (JmsDTO jmsDTO : messages) {
                sendToQueue(jmsDTO);
            }
        }
    }
}
