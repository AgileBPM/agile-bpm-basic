package com.dstz.sys.api.jms.model;

import java.io.Serializable;

/**
 * 消息传输对象
 *
 * @param <T>
 * @author Jeff
 */
public interface JmsDTO<T extends Serializable> extends Serializable {

	/**
	 * 具体消费者的标识
	 *
	 * @return 消费者标识
	 */
	String getType();

	/**
	 * 消费者的数据对象
	 *
	 * @return 消费数据
	 */
	T getData();
}
