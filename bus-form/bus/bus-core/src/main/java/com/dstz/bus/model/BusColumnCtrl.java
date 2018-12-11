package com.dstz.bus.model;

import org.hibernate.validator.constraints.NotEmpty;

import com.dstz.base.core.model.BaseModel;
import com.dstz.bus.api.model.IBusColumnCtrl;

/**
 * 字段对应的控件配置
 * 
 * @author 李易峻
 *
 * @email:632266504@qq.com
 */
public class BusColumnCtrl extends BaseModel implements IBusColumnCtrl{
	@NotEmpty
	private String columnId; /* 对应字段的ID */
	@NotEmpty
	private String type; /* 控件类型  BusColumnCtrlType */
	private String config; /* 控件对应的配置内容，不同控件会有不同属性 */
	private String validRule; /* 验证规则 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
	
	public String getValidRule() {
		return validRule;
	}

	public void setValidRule(String validRule) {
		this.validRule = validRule;
	}
}