package com.dstz.org.api.model.system;


public interface ISubsystem {

	/**
	 * 返回 主键
	 *
	 * @return
	 */
	String getId();

	/**
	 * 返回 系统名称
	 *
	 * @return
	 */
	String getName();

	/**
	 * 返回 系统别名
	 *
	 * @return
	 */
	String getAlias();
	/**
	 * JSON 更多配置
	 * @return
	 */
	String getConfig();

	/**
	 * 返回 是否可用 1 可用，0 ，不可用
	 *
	 * @return
	 */
	Integer getEnabled();

	int getIsDefault();

}