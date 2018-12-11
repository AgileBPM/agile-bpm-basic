package com.dstz.bus.model.permission;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.dstz.bus.api.model.permission.IAbstractPermission;

/**
 * <pre>
 * 描述：BusColumnPermission 和 BusObjPermission 和BusTablePermission 共有的字段
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月13日
 * 版权:summer
 * </pre>
 */
public abstract class AbstractPermission implements IAbstractPermission{
	/**
	 * Map<RightsType ,类型的数据 >
	 */
	protected Map<String, JSONArray> rights = new HashMap<>();
	/**
	 * <pre>
	 * 计算结果 RightsType
	 * 不入库字段 
	 * </pre>
	 */
	protected String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, JSONArray> getRights() {
		return rights;
	}

	public void setRights(Map<String, JSONArray> rights) {
		this.rights = rights;
	}

}
