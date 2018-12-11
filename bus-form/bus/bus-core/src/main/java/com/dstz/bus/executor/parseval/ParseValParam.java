package com.dstz.bus.executor.parseval;

import java.util.Map;

import com.dstz.bus.model.BusTableRel;

/**
 * <pre>
 * 描述：formDefData的值转businessData中的data的执行链参数
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月20日
 * 版权:summer
 * </pre>
 */
public class ParseValParam {
	private String key;
	private Object value;
	private Map<String, Object> data;
	private BusTableRel busTableRel;

	public ParseValParam(String key, Object value, Map<String, Object> data, BusTableRel busTableRel) {
		super();
		this.key = key;
		this.value = value;
		this.data = data;
		this.busTableRel = busTableRel;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public BusTableRel getBusTableRel() {
		return busTableRel;
	}

}
