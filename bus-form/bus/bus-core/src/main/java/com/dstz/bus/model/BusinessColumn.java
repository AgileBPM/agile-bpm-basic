package com.dstz.bus.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.model.IBaseModel;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.table.Column;
import com.dstz.bus.api.model.IBusinessColumn;
import com.dstz.sys.api.groovy.IGroovyScriptEngine;

import cn.hutool.core.date.DateUtil;

/**
 * 业务字段
 *
 * @author aschs
 *
 */
public class BusinessColumn extends Column implements IBaseModel, IBusinessColumn {
	@NotEmpty
	private String id;
	/**
	 * 列key
	 */
	@NotEmpty
	private String key;
	/**
	 * 表Id
	 */
	@NotEmpty
	private String tableId;

	// 以下字段不进行持久化
	/**
	 * 字段控件
	 */
	@Valid
	private BusColumnCtrl ctrl;
	private BusinessTable table;

	// 创建时间
	protected Date createTime;
	// 创建人ID
	protected String createBy;
	// 更新时间
	protected Date updateTime;
	// 更新人ID
	protected String updateBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public BusColumnCtrl getCtrl() {
		return ctrl;
	}

	public void setCtrl(BusColumnCtrl ctrl) {
		this.ctrl = ctrl;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	@Override
	public BusinessTable getTable() {
		return table;
	}

	public void setTable(BusinessTable table) {
		this.table = table;
	}

	@Override
	public Object initValue() {
		return value(defaultValue);
	}

	@Override
	public Object value(String str) {
		if (StringUtil.isEmpty(str)) {
			return null;
		}
		if (str.startsWith("{") && str.endsWith("}")) {// 以{}包围的是脚本
			IGroovyScriptEngine engine = AppUtil.getBean(IGroovyScriptEngine.class);
			String script = str.substring(1, str.length() - 1);
			return engine.executeObject(script, new HashMap<>());
		}

		Object value = null;
		try {
			if (ColumnType.VARCHAR.equalsWithKey(type) || ColumnType.CLOB.equalsWithKey(type)) {
				value = str;
			} else if (ColumnType.NUMBER.equalsWithKey(type)) {
				BigDecimal bigDecimal = new BigDecimal(str);
				// 保留精度小数，且四舍五入
				value = bigDecimal.setScale(this.getDecimal(), RoundingMode.HALF_UP);
			} else if (ColumnType.DATE.equalsWithKey(type)) {
				JSONObject config = JSON.parseObject(this.getCtrl().getConfig());
				try {
					value = DateUtil.parse(str, config.getString("format"));
				} catch (Exception e) {// 解析失败，默认超匹配模式
					value = DateUtil.parse(str);
				}
			}
		} catch (Exception e) {
			ColumnType columnType = ColumnType.getByKey(this.type);
			throw new BusinessException("字段值解析失败，无法把字符串[" + str + "]转化成" + columnType.getDesc() + "[" + columnType.getKey() + "]");
		}
		return value;
	}

	@Override
	public Date getCreateTime() {
		return createTime;
	}

	@Override
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String getCreateBy() {
		return createBy;
	}

	@Override
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	public Date getUpdateTime() {
		return updateTime;
	}

	@Override
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String getUpdateBy() {
		return updateBy;
	}

	@Override
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

}
