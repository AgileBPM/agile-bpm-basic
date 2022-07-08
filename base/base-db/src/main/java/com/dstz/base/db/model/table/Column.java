package com.dstz.base.db.model.table;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.core.util.EnumUtil;

/**
 * <pre>
 * 对应数据库的列对象
 * </pre>
 *
 * @author aschs
 */
public class Column implements Serializable {
	/**
	 * 列名
	 */
	@NotEmpty
	protected String name;
	/**
	 * 类型 枚举 : ColumnType
	 */
	@NotEmpty
	protected String type;
	/**
	 * 长度
	 */
	protected int length;
	/**
	 * 小数点
	 */
	protected int decimal;
	/**
	 * 是否必填
	 */
	protected boolean required;
	/**
	 * 是否主键
	 */
	protected boolean primary;
	/**
	 * 默认值
	 */
	protected String defaultValue;
	/**
	 * 注释
	 */
	protected String comment;

	/**
	 * 设置一些默认值
	 */
	public Column() {
		super();
		this.type = ColumnType.VARCHAR.toString();
		this.length = 50;
		this.decimal = 0;
		this.primary = false;
		this.required = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDecimal() {
		return decimal;
	}

	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Column)) {
			return false;
		}
		Column c = (Column) obj;
		if (!c.getComment().equals(this.getComment())) {
			return false;
		}
		if (c.getDecimal() != this.getDecimal()) {
			return false;
		}
		if ((c.getDefaultValue() == null && this.getDefaultValue() != null) || (c.getDefaultValue() != null && !c.getDefaultValue().equals(this.getDefaultValue()))) {
			return false;
		}
		if (c.getLength() != this.getLength()) {
			return false;
		}
		if (!c.getName().equalsIgnoreCase(this.getName())) {
			return false;
		}
		if (!c.getType().equalsIgnoreCase(this.getType())) {
			return false;
		}
		if (!c.isPrimary() == this.isPrimary()) {
			return false;
		}
		if (!c.isRequired() == this.isRequired()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(EnumUtil.toJSON("com.dstz.base.db.model.Column$TYPE"));
	}
}
