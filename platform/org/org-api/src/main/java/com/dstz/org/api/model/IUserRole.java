package com.dstz.org.api.model;

public interface IUserRole {

	String getAlias();


	String getFullname();


	String getRoleName();


	/**
	 * 返回 role_id_
	 *
	 * @return
	 */
	String getRoleId();


	/**
	 * 返回 user_id_
	 *
	 * @return
	 */
	String getUserId();

	String getAccount();
}