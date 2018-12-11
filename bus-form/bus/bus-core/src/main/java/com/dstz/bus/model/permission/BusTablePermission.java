package com.dstz.bus.model.permission;

import java.util.HashMap;
import java.util.Map;

import com.dstz.bus.api.model.permission.IBusTablePermission;

public class BusTablePermission extends AbstractPermission implements IBusTablePermission {
	/**
	 * 表key
	 */
	private String key;
	/**
	 * 表描述
	 */
	private String comment;
	/**
	 * 字段权限 Map<columnKey,BusColumnPermission>
	 */
	private Map<String, BusColumnPermission> columnMap = new HashMap<>();

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, BusColumnPermission> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, BusColumnPermission> columnMap) {
		this.columnMap = columnMap;
	}
	
	

}
