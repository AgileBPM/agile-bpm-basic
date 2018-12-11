package com.dstz.bus.api.constant;

/**
 * <pre>
 *  
 * 描述：bus权限枚举
 * 注意枚举的顺序是从大到小的，例如拥有了REQUIRED权限，就表示包含下面的权限了
 * 通常页面配置不会展示最后那个权限，因为当用户配置了权限，前面的权限都没有了，最后的就是权限了
 * 当用户没配置权限，则应该选择默认权限
 * 默认权限跟最后一个权限的概念是不一样的哦
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月14日 下午6:07:46
 * 版权:summer
 * </pre>
 */
public enum RightsType {
	/**
	 * 必填权限
	 */
	REQUIRED("b", "必填", true, true),
	/**
	 * 编辑权限
	 */
	WRITE("w", "编辑", true, true),
	/**
	 * 只读权限
	 */
	READ("r", "只读", true, false),
	/**
	 * 无权限
	 */
	NONE("n", "无", false, false);
	/**
	 * key
	 */
	private String key;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 数据库读权限
	 */
	private boolean dbReadable;
	/**
	 * 数据库操作权限
	 */
	private boolean dbEditable;

	private RightsType(String key, String desc, boolean dbReadable, boolean dbEditable) {
		this.key = key;
		this.desc = desc;
		this.dbReadable = dbReadable;
		this.dbEditable = dbEditable;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isDbReadable() {
		return dbReadable;
	}

	public boolean isDbEditable() {
		return dbEditable;
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
	public static RightsType getByKey(String key) {
		for (RightsType value : RightsType.values()) {
			if (value.key.equals(key)) {
				return value;
			}
		}
		return null;
	}

	/**
	 * <pre>
	 * 返回默认权限
	 * </pre>
	 * 
	 * @param key
	 * @return PermissionType
	 * @exception @since
	 *                1.0.0
	 */
	public static RightsType getDefalut() {
		return RightsType.WRITE;
	}
}
