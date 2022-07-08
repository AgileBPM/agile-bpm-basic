package com.dstz.bus.model.permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.api.constant.RightsType;
import com.dstz.bus.api.model.permission.IBusColumnPermission;
import com.dstz.bus.api.model.permission.IBusObjPermission;
import com.dstz.bus.api.model.permission.IBusTablePermission;

/**
 * <pre>
 * 描述：bo权限
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月22日 下午4:20:27
 * 版权:summer
 * </pre>
 */
public class BusObjPermission extends AbstractPermission implements IBusObjPermission {
	/**
	 * boKey
	 */
	private String key;
	/**
	 * <pre>
	 * boName
	 * </pre>
	 */
	private String name;
	/**
	 * <pre>
	 * 权限map
	 * Map<tableKey,BusTablePermission>
	 * </pre>
	 */
	private Map<String, BusTablePermission> tableMap = new HashMap<>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, BusTablePermission> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, BusTablePermission> tableMap) {
		this.tableMap = tableMap;
	}
	
	@Override
	public void handlePermission(JSONObject tablePermission, JSONObject permission, Boolean isReadonly) {
		permission.put(this.getKey(), new JSONObject());
		tablePermission.put(this.getKey(), new JSONObject());
		for (Entry<String, ? extends IBusTablePermission> etry : this.getTableMap().entrySet()) {
			IBusTablePermission busTablePermission = etry.getValue();
			permission.getJSONObject(this.getKey()).put(busTablePermission.getKey(), new JSONObject());
			tablePermission.getJSONObject(this.getKey()).put(busTablePermission.getKey(), handleReadonlyResult(busTablePermission.getResult(), isReadonly));

			for (Entry<String, ? extends IBusColumnPermission> ery : busTablePermission.getColumnMap().entrySet()) {
				IBusColumnPermission busColumnPermission = ery.getValue();
				permission.getJSONObject(this.getKey()).getJSONObject(busTablePermission.getKey()).put(busColumnPermission.getKey(), handleReadonlyResult(busColumnPermission.getResult(), isReadonly));
			}
		}
	}

	private String handleReadonlyResult(String result, Boolean isReadonly) {
		if (!isReadonly)
			return result;

		if (RightsType.REQUIRED.getKey().equals(result) || RightsType.WRITE.getKey().equals(result)) {
			return RightsType.READ.getKey();
		}

		return result;
	}
}
