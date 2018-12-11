package com.dstz.sys.simplemq.consumer;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.dstz.sys.api.jms.JmsHandler;
import com.dstz.sys.api.jms.model.JmsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象公共消息队列
 *
 * @author wacxhs
 */
public abstract class AbstractMessageQueue implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessageQueue.class);

    private ApplicationContext applicationContext;

    /**
     * 注册消息处理器
     */
    private Map<String, JmsHandler> registerJmsHandler = Collections.emptyMap();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            LOGGER.debug("准备加载JmsHandler实现集");
            this.applicationContext = event.getApplicationContext();
            Map<String, JmsHandler> jmsHandlerMap = applicationContext.getBeansOfType(JmsHandler.class);
            LOGGER.debug("加载JmsHandler实现共{}条", jmsHandlerMap.size());
            if (MapUtil.isNotEmpty(jmsHandlerMap)) {
                Collection<JmsHandler> jmsHandlers = jmsHandlerMap.values();
                this.registerJmsHandler = new HashMap<>(jmsHandlers.size(), 1);
                for (JmsHandler jmsHandler : jmsHandlers) {
                    this.registerJmsHandler.put(jmsHandler.getType(), jmsHandler);
                }
            }
            containerInitialCompleteAfter();
        }
    }

    /**
     * 容器初始化完成后
     */
    protected void containerInitialCompleteAfter() {
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 发送消息
     *
     * @param jmsDTO 消息传输对象
     */
    @SuppressWarnings("unchecked")
    public void handleMessage(JmsDTO<?> jmsDTO) {
        JmsHandler jmsHandler = getJmsHandler(jmsDTO.getType());
        if (jmsHandler == null) {
            LOGGER.warn("未找到({})消息处理器", jmsDTO.getType());
            return;
        }
        try {
            jmsHandler.handlerMessage(jmsDTO);
        } catch (Exception e) {
            LOGGER.error("处理消息({})出错, 参数入参：{}", jmsDTO.getType(), JSON.toJSONString(jmsDTO), e);
        }
    }

    /**
     * 获取消息处理实现
     *
     * @return 消息处理实现
     */
    protected JmsHandler getJmsHandler(String type) {

        return this.registerJmsHandler.get(type);
    }
}
