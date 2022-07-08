package com.dstz.bus.api.constant;

/**
 * 
 * <pre>
 * businessColumn业务字段的控件类型枚举
 * </pre>
 */
public enum BusColumnCtrlType {
	/**
	 * 隐藏字段
	 */
	HIDDEN("hidden", "隐藏", new String[] { "varchar", "number", "date", "clob" }),
	/**
	 * "onetext", "单行文本", new String[] { "varchar", "number" }
	 */
	ONETEXT("onetext", "单行文本", new String[] { "varchar", "number" }),
	/**
	 * "multitext", "多行文本", new String[] { "varchar", "clob" }
	 */
	MULTITEXT("multitext", "多行文本", new String[] { "varchar", "clob" }),
	/**
	 * "select", "下拉框", new String[] { "varchar", "number" }
	 */
	SELECT("select", "下拉框", new String[] { "varchar", "number" }),
	/**
	 * "multiselect", "下拉框多选", new String[] { "varchar"}
	 */
//	MULTISELECT("multiselect", "下拉框多选", new String[] { "varchar" }), 先屏蔽
	/**
	 * "checkbox", "复选框", new String[] { "varchar" }
	 */
	CHECKBOX("checkbox", "复选框", new String[] { "varchar" }),
	/**
	 * "radio", "单选按钮", new String[] { "varchar", "number" }
	 */
	RADIO("radio", "单选按钮", new String[] { "varchar", "number" }),
	/**
	 * "date", "日期控件", new String[] { "date" }
	 */
	DATE("date", "日期控件", new String[] { "date" }),
	/**
	 * "dic", "数据字典", new String[] { "varchar", "number" }
	 */
	DIC("dic", "数据字典", new String[] { "varchar", "number" }),
	/**
	 * "serialno", "流水号", new String[] { "varchar", "number" } 暂时不支持
	 */
	SERIALNO("serialno", "流水号", new String[] { "varchar", "number" }),
	/**
	 * "file", "附件上传", new String[] { "varchar", "clob" }
	 */
	FILE("file", "附件上传", new String[] { "varchar", "clob" });

	/**
	 * 值
	 */
	private String key;
	/**
	 * 描叙
	 */
	private String desc;
	/**
	 * 支持的数据库类型
	 */
	private String[] supports;

	BusColumnCtrlType(String key, String desc, String[] supports) {
		this.key = key;
		this.desc = desc;
		this.supports = supports;
	}

	@Override
	public String toString() {
		return this.key;
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
	 * 根据key获取泛型
	 * 
	 * @param key
	 * @return FieldControlType
	 * @exception @since
	 *                1.0.0
	 */
	public static BusColumnCtrlType getByKey(String key) {
		for (BusColumnCtrlType f : BusColumnCtrlType.values()) {
			if (f.key.equals(key)) {
				return f;
			}
		}
		return null;
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
