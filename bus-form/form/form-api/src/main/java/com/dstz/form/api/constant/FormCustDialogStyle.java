package com.dstz.form.api.constant;
/**
 * <pre> 
 * 描述：FormCustDialog中的style枚举
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月1日 下午4:24:48
 * 版权:summer
 * </pre>
 */
public enum FormCustDialogStyle {
	LIST("list", "列表"), TREE("tree", "树形");
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;

	private FormCustDialogStyle(String key, String desc) {
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
