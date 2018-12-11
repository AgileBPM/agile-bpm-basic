package com.dstz.bus.model.permission;

import java.util.HashMap;
import java.util.Map;

import com.dstz.bus.api.model.permission.IBusObjPermission;

/**
 * <pre>
 * 描述：bo权限
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月22日 下午4:20:27
 * 版权:summer
 * </pre>
 */
public class BusObjPermission extends AbstractPermission implements IBusObjPermission{
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

}
