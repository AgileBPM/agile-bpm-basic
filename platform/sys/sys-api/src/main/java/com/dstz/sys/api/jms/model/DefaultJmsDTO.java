package com.dstz.sys.api.jms.model;

import java.io.Serializable;

/**
 * 默认的消息
 *
 * @param <T>
 * @author jeff
 */
public class DefaultJmsDTO<T extends Serializable> implements JmsDTO{

	private String type;
	
	private T data;
	
	
	public DefaultJmsDTO(String type, T data) {
		this.type = type;
		this.data = data;
	}

	@Override
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
