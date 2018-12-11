package com.dstz.bus.model;

import java.io.Serializable;

import com.dstz.bus.api.model.IBusTableRelFk;

/**
 * <pre>
 * BusTabRelation中的外键实体类
 * </pre>
 *
 * @author aschs
 *
 */
public class BusTableRelFk implements Serializable, IBusTableRelFk {
	/**
	 * 业务表对应的映射字段
	 */
	private String from;
	/**
	 * 映射的方式 枚举 BusTableRelFkType
	 */
	private String type;
	/**
	 * 值
	 */
	private String value;

	@Override
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
