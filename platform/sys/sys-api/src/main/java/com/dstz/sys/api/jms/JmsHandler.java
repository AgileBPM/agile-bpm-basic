package com.dstz.sys.api.jms;

import com.dstz.sys.api.jms.model.JmsDTO;

import java.io.Serializable;

/**
 * <pre>
 * 所有消费者 均需要实现该接口<br />
 * 通过type 获取具体的处理者
 * </pre>
 *
 * @author jeff
 */
public interface JmsHandler<T extends Serializable> {

    /**
     * 得到消息类型
     *
     * @return 消息类型
     */
    String getType();

    /**
     * 处理消息
     *
     * @param message 消息传输类型
     * @return 是否成功处理
     */
    boolean handlerMessage(JmsDTO<T> message);
}
