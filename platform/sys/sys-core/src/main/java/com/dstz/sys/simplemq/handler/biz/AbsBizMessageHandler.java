package com.dstz.sys.simplemq.handler.biz;

import com.dstz.sys.api.jms.JmsHandler;

import java.io.Serializable;
/**
 * 做公共逻辑,如日志等
 * @author Jeff
 *
 */
public abstract class AbsBizMessageHandler<T extends Serializable> implements JmsHandler<T> {

}
