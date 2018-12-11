package com.dstz.bus.api.constant;
/**
 * <pre> 
 * 描述：BusTableRelFkType中type属性的枚举
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年2月26日 下午3:22:03
 * 版权:summer
 * </pre>
 */
public enum BusTableRelFkType {
	/**
	 * 对应父实例的字段
	 */
	PARENT_FIELD("parentField", "子表外键 对应 父实例字段"),
	/**
	 * 固定值
	 */
	FIXED_VALUE("fixedValue", "固定值"),
	/**
	 * 父实例对应字段
	 */
	CHILD_FIELD("childField", "子表字段 对应 父实例外键");
	private String key;
	private String desc;

	private BusTableRelFkType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
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
}
