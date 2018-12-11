package com.dstz.bus.api.constant;

import com.dstz.base.api.constant.IStatusCode;

/**
 * <pre>
 * bus层的异常码
 * </pre>
 * 
 * @author aschs
 *
 */
public enum BusStatusCode implements IStatusCode {

	PARAM_ILLEGAL("100", "参数校验不通过"),
	
	
	BUS_DATA_LOSE("60001", "业务数据丢失");

	private String code;
	private String desc;
	private String system;

	private BusStatusCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
		this.system = "BUS";
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getSystem() {
		return system;
	}
}
