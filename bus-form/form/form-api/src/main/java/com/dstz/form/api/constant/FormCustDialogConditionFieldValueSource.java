package com.dstz.form.api.constant;

/**
 * <pre>
 * 描述：FormCustDialogConditionField中的valueSource枚举
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月1日 下午4:29:12
 * 版权:summer
 * </pre>
 */
public enum FormCustDialogConditionFieldValueSource {
	/**
	 * 页面输入或者url传入
	 */
	PARAM("param", "参数传入", new String[] { FormCustDialogStyle.LIST.getKey(),FormCustDialogStyle.TREE.getKey() }),
	/**
	 * 固定值
	 */
	FIXED_VALUE("fixedValue", "固定值", new String[] { FormCustDialogStyle.LIST.getKey(), FormCustDialogStyle.TREE.getKey() }),
	/**
	 * 脚本
	 */
	SCRIPT("script", "脚本", new String[] { FormCustDialogStyle.LIST.getKey(), FormCustDialogStyle.TREE.getKey() });
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 支持的自定义对话框的显示类型
	 */
	private String[] supports;

	private FormCustDialogConditionFieldValueSource(String key, String desc, String[] supports) {
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
}
