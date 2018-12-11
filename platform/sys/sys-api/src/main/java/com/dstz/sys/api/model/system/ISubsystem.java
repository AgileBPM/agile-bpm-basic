package com.dstz.sys.api.model.system;


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
	 * 返回 logo地址
	 *
	 * @return
	 */
	String getLogo();

	/**
	 * 返回 是否可用 1 可用，0 ，不可用
	 *
	 * @return
	 */
	Integer getEnabled();

	/**
	 * 返回 主页地址
	 *
	 * @return
	 */
	String getHomeUrl();

	/**
	 * 返回 基础地址
	 *
	 * @return
	 */
	String getBaseUrl();

	/**
	 * 返回 租户名称
	 *
	 * @return
	 */
	String getTenant();

	/**
	 * 返回 备注
	 *
	 * @return
	 */
	String getMemo();

	/**
	 * 返回 创建人ID
	 *
	 * @return
	 */
	String getCreatorId();

	/**
	 * 返回 创建人
	 *
	 * @return
	 */
	String getCreator();

	/**
	 * 返回 创建时间
	 *
	 * @return
	 */
	java.util.Date getCreateTime();

	int getIsDefault();

}