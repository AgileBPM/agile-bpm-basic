package com.dstz.base.api.constant;

import java.util.Arrays;

import com.dstz.base.api.exception.BusinessException;

/**
 * <pre>
 * 描述：Column中的type枚举
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月13日 下午6:23:06
 * 版权:summer
 * </pre>
 */
public enum ColumnType {
	/**
	 * 字符串
	 */
	VARCHAR("varchar", "字符串", new String[] { "varchar", "varchar2", "char", "tinyblob", "tinytext" }),
	/**
	 * 大文本
	 */
	CLOB("clob", "大文本", new String[] { "text", "clob", "blob", "mediumblob", "mediumtext", "longblob", "longtext" }),
	/**
	 * 数字型
	 */
	NUMBER("number", "数字型", new String[] { "tinyint", "number", "smallint", "mediumint", "int", "integer", "bigint", "float", "double", "decimal", "numeric" }),
	/**
	 * 日期型
	 */
	DATE("date", "日期型", new String[] { "date", "time", "year", "datetime", "timestamp" });
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 支持的数据库类型
	 */
	private String[] supports;

	private ColumnType(String key, String desc, String[] supports) {
		this.key = key;
		this.desc = desc;
		this.supports = supports;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public String[] getSupports() {
		return supports;
	}

	/**
	 * <pre>
	 * 根据key来判断是否跟当前一致
	 * </pre>
	 *
	 * @param key
	 * @return
	 */
	public boolean equalsWithKey(String key) {
		return this.key.equals(key);
	}

	public static ColumnType getByKey(String key) {
		for (ColumnType type : ColumnType.values()) {
			if (type.getKey().equals(key)) {
				return type;
			}
		}
		throw new BusinessException(String.format("找不到key为[%s]的字段类型", key));
	}

	/**
	 * <pre>
	 * 根据数据库的字段类型获取type
	 * 无视大小写
	 * </pre>
	 *
	 * @param dbDataType
	 *            数据库的字段类型
	 * @return
	 */
	public static ColumnType getByDbDataType(String dbDataType, String errMsgApp) {
		for (ColumnType type : ColumnType.values()) {
			for (String support : Arrays.asList(type.supports)) {
				if (dbDataType.toLowerCase().contains(support.toLowerCase())) {
					return type;
				}
			}

		}
		throw new BusinessException(String.format("[%s]数据库类型[%s]转换不了系统支持的类型", errMsgApp, dbDataType));
	}

	public static ColumnType getByDbDataType(String dbDataType) {
		return getByDbDataType(dbDataType, "");
	}
}
