package com.dstz.org.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="用户角色新 ")
public interface IUserRole {
	
	@ApiModelProperty("角色标识")
	String getAlias();

	@ApiModelProperty("用户名")
	String getFullname();

	@ApiModelProperty("角色名")
	String getRoleName();


	/**
	 * 返回 role_id_
	 *
	 * @return
	 */
	@ApiModelProperty("角色ID")
	String getRoleId();


	/**
	 * 返回 user_id_
	 *
	 * @return
	 */
	@ApiModelProperty("用户ID")
	String getUserId();
	@ApiModelProperty("用户账户")
	String getAccount();
}