package com.dstz.sys.simplemq.producer;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.dstz.sys.api.jms.MessageQueueSendException;
import com.dstz.sys.api.jms.constants.JmsDestinationConstant;
import com.dstz.sys.api.jms.model.JmsDTO;
import com.dstz.sys.api.jms.producer.JmsProducer;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import java.util.List;

/**
 * 通用消息队列消息发送具体实现
 *
 * @author wacxhs
 */
public class CommonMessageQueueProducer implements JmsProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonMessageQueueProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendToQueue(JmsDTO message) {
        if (message == null) {
            LOGGER.info("传入参数为空, 跳过执行");
            return;
        }
        try {
            MethodUtils.invokeMethod(jmsTemplate, "convertAndSend", JmsDestinationConstant.DEFAULT_NAME, message);
        } catch (Exception e) {
            LOGGER.warn("JMS发送失败, 发送参数：{}", JSON.toJSONString(message));
            throw new MessageQueueSendException("发送队列消息失败, " + e.getMessage());
        }
    }

    @Override
    public void sendToQueue(List<JmsDTO> messages) {
        if (CollectionUtil.isEmpty(messages)) {
            LOGGER.info("传入参数为空, 跳过执行");
            return;
        }
        for (JmsDTO jmsDTO : messages) {
            sendToQueue(jmsDTO);
        }
    }
}
