package com.dstz.bus.api.model.permission;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface IBusObjPermission extends IAbstractPermission {

	String getKey();

	String getName();

	Map<String, ? extends IBusTablePermission> getTableMap();
	
	/**
	 * <pre>
	 * 处理权限
	 * </pre>	
	 * @param tablePermission
	 * @param permission
	 * @param isReadonly
	 */
	void handlePermission(JSONObject tablePermission, JSONObject permission, Boolean isReadonly);

}
