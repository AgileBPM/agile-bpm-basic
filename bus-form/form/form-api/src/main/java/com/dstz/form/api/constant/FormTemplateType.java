package com.dstz.form.api.constant;

/**
 * <pre>
 * 描述：FormTemplate的type枚举
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月8日 上午10:25:38
 * 版权:summer
 * </pre>
 */
public enum FormTemplateType {
	/**
	 * 主表模板
	 */
	MAIN("main", "主表模板"),
	/**
	 * 子表模板
	 */
	SUB_TABLE("subTable", "子表模板"),
	
	MAIN_OVERALLARRANGEMENT("mainFormOverallArrangement", "主表[布局版]"),
	
	SUB_OVERALLARRANGEMENT("subTableFormOverallArrangement", "子表[布局版]")
	;
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;

	private FormTemplateType(String key, String desc) {
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
