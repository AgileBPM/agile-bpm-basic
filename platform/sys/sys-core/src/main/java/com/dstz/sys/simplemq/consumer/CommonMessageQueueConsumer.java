package com.dstz.sys.simplemq.consumer;

import com.dstz.sys.api.jms.model.JmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用消息队列消费
 *
 * @author wacxhs
 */
public class CommonMessageQueueConsumer extends AbstractMessageQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonMessageQueueConsumer.class);

    /**
     * 加载完成
     */
    private volatile boolean loadComplete;

    @Override
    protected void containerInitialCompleteAfter() {
        this.loadComplete = true;
    }

    @Override
    public void handleMessage(JmsDTO<?> jmsDTO) {
        //防止还未加载成功，消息已经调用
        while (!this.loadComplete) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                LOGGER.error("线程中断", e.getMessage());
            }
        }
        super.handleMessage(jmsDTO);
    }
}
