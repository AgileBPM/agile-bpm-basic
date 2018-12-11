package com.dstz.bus.api.constant;

/**
 * <pre>
 *  
 * 描述：BusinessPermission的objType枚举
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月22日 下午5:54:01
 * 版权:summer
 * </pre>
 */
public enum BusinessPermissionObjType {

	/**
	 * 表单
	 */
	FORM("form", "表单"),
	/**
	 * 流程
	 */
	FLOW("flow", "流程"),
	/**
	 * 流程节点
	 */
	FLOW_NODE("flowNode", "流程节点");
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;

	private BusinessPermissionObjType(String key, String desc) {
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

	/**
	 * 根据key获取泛型
	 * 
	 * @param key
	 * @return PermissionType
	 * @exception @since
	 *                1.0.0
	 */
	public static BusinessPermissionObjType getByKey(String key) {
		for (BusinessPermissionObjType value : BusinessPermissionObjType.values()) {
			if (value.key.equals(key)) {
				return value;
			}
		}
		return null;
	}
}
