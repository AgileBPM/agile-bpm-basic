package com.dstz.org.api.model.dto;

import com.dstz.org.api.model.IUserRole;

public class UserRoleDTO implements IUserRole{
	   /**
     * role_id_
     */
    protected String roleId;

    /**
     * user_id_
     */
    protected String userId;
    /**
     * 以下是扩展字段，用于关联显示。
     */

    //用户名
    protected String fullname;
    // 角色名称
    protected String roleName;
    //角色别名
    protected String alias;
    //账号
    protected String account = "";
    

    public UserRoleDTO() {
    	
	}

	public UserRoleDTO(String roleId, String userId, String fullname, String roleName) {
		super();
		this.roleId = roleId;
		this.userId = userId;
		this.fullname = fullname;
		this.roleName = roleName;
	}

	public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 返回 role_id_
     *
     * @return
     */
    public String getRoleId() {
        return this.roleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 返回 user_id_
     *
     * @return
     */
    public String getUserId() {
        return this.userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
