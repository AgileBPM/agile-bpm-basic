package com.dstz.bus.executor.assemblyval;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.model.BusinessData;

/**
 * <pre>
 * 描述：
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月21日
 * 版权:summer
 * </pre>
 */
public class AssemblyValParam {
	private JSONObject data;
	private BusinessData businessData;

	public AssemblyValParam(JSONObject data, BusinessData businessData) {
		super();
		this.data = data;
		this.businessData = businessData;
	}

	public JSONObject getData() {
		return data;
	}

	public BusinessData getBusinessData() {
		return businessData;
	}

}
