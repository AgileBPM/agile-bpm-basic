package com.dstz.sys.simplemq.consumer;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.dstz.sys.api.jms.constants.JmsDestinationConstant;
import com.dstz.sys.api.jms.model.JmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Redis消息队列消费
 *
 * @author wacxhs
 */
public class RedisMessageQueueConsumer extends AbstractMessageQueue implements DisposableBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisMessageQueueConsumer.class);

    /**
     * 处理消息核心线程数
     */
    private int handleMessageCoreThreadSize = 1;

    /**
     * 处理消息最大线程数
     */
    private int handleMessageMaxThreadSize = 2;

    /**
     * 处理消息活跃时间,单位秒
     */
    private long handleMessageKeepAliveTime = 30;

    /**
     * 监听队列间隔(ms)
     */
    private long listenInterval = 1000;

    /**
     * 消息队列
     */
    private BoundListOperations<String, Object> messageQueue;

    /**
     * 处理消息线程池
     */
    private ThreadPoolExecutor handleMessageThreadPool;

    /**
     * Redis队列监听器
     */
    private RedisQueueListener redisQueueListener;

    /**
     * 设置处理消息核心线程数
     *
     * @param handleMessageCoreThreadSize 处理消息核心线程数
     */
    public void setHandleMessageCoreThreadSize(int handleMessageCoreThreadSize) {
        this.handleMessageCoreThreadSize = handleMessageCoreThreadSize;
    }

    /**
     * 设置处理消息最大线程数
     *
     * @param handleMessageMaxThreadSize 处理消息最大线程数
     */
    public void setHandleMessageMaxThreadSize(int handleMessageMaxThreadSize) {
        this.handleMessageMaxThreadSize = handleMessageMaxThreadSize;
    }

    /**
     * 设置处理消息线程活跃时长
     *
     * @param handleMessageKeepAliveTime 处理消息线程活跃时长
     */
    public void setHandleMessageKeepAliveTime(long handleMessageKeepAliveTime) {
        this.handleMessageKeepAliveTime = handleMessageKeepAliveTime;
    }

    /**
     * 设置监听队列间隔
     *
     * @param listenInterval 监听间隔(ms)
     */
    public void setListenInterval(long listenInterval) {
        this.listenInterval = listenInterval;
    }

    @Override
    public void destroy() throws Exception {
        LOGGER.info("准备关闭Redis消息队列消息消费端");
        this.redisQueueListener.interrupt();
        this.handleMessageThreadPool.shutdown();
        while (!this.handleMessageThreadPool.isTerminated()) {
            LOGGER.info("等待redis消息队列处理完毕");
            TimeUnit.SECONDS.sleep(1L);
        }
        LOGGER.info("关闭Redis消息队列消息消费完毕");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void containerInitialCompleteAfter() {
        LOGGER.debug("初始化Redis消息队列处理");
        this.messageQueue = getApplicationContext().getBean(RedisTemplate.class).boundListOps(JmsDestinationConstant.DEFAULT_NAME);
        this.handleMessageThreadPool = new ThreadPoolExecutor(
                handleMessageCoreThreadSize,
                handleMessageMaxThreadSize,
                handleMessageKeepAliveTime,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(handleMessageMaxThreadSize << 2),
                ThreadFactoryBuilder.create().setNamePrefix(String.format("redis-%s-queue-consumer-pool-", JmsDestinationConstant.DEFAULT_NAME)).build()
        );
        RedisQueueListener redisQueueListener = new RedisQueueListener();
        redisQueueListener.setName(String.format("redis-queue-%s-listen", JmsDestinationConstant.DEFAULT_NAME));
        redisQueueListener.setDaemon(true);
        redisQueueListener.start();
        this.redisQueueListener = redisQueueListener;
    }


    /**
     * Redis队列监听器
     */
    private class RedisQueueListener extends Thread {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                LOGGER.trace("监听Redis消息队列({})返回结果......", JmsDestinationConstant.DEFAULT_NAME);
                Object jmsDTO;
                try {
                    jmsDTO = messageQueue.leftPop(listenInterval, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    LOGGER.warn("监听Redis消息队列({})返回结果出错", e);
                    continue;
                }
                if (jmsDTO == null) {
                    continue;
                } else if (!(jmsDTO instanceof JmsDTO)) {
                    LOGGER.warn("Redis消息队列({})返回数据类型({})非[com.dstz.sys.api.jms.model.JmsDTO]", JmsDestinationConstant.DEFAULT_NAME, jmsDTO.getClass());
                    continue;
                }
                handleMessageThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleMessage((JmsDTO<?>) jmsDTO);
                    }
                });
            }
            LOGGER.info("退出Redis消息队列({})监听", JmsDestinationConstant.DEFAULT_NAME);
        }
    }

}
