package com.dstz.org.core.model;
import java.util.Date;
import java.math.BigDecimal;

import com.dstz.base.core.model.BaseModel;


/**
 * 用户组织关系 实体对象
 * @author Jeff
 * @email for_office@qq.com
 * @time 2018-12-16 01:07:59
 */
public class OrgRelation extends BaseModel{
	/**
	* 组ID
	*/
	protected  String groupId; 
	/**
	* 用户ID
	*/
	protected  String userId; 
	/**
	* 0:默认组织，1：从组织
	*/
	protected  Integer isMaster = 0; 
	
	/**
	* 0:默认组织，1：从组织
	*/
	protected  Integer status = 1; 
	
	/**
	* 角色ID
	*/
	protected  String roleId; 
	/**
	* 类型：groupUser,groupRole,userRole,groupUserRole
	*/
	protected  String type; 
	
	
	/**
	 * 前端字段
	 */
	protected String groupName;
	protected String userName;
	protected String userAccount;
	protected String roleName;
	protected String roleAlias;
	
	/**
	 * 岗位使用的时候调用
	 * @return
	 */
	public String getPostName() {
		return String.format("%s-%s", this.getGroupName(),this.getRoleName());
	}
	/**
	 * post ID
	 * @return
	 */
	public String getPostId() {
		return String.format("%s-%s", this.getGroupId(),this.getRoleId());
	}


	public void setGroupId( String groupId) {
		this.groupId = groupId;
	}
	

	public OrgRelation() {
		 
	}
	
	public OrgRelation(String groupId, String userId, String roleId, String type) {
		super();
		this.groupId = groupId;
		this.userId = userId;
		this.roleId = roleId;
		this.type = type;
	}

	/**
	 * 返回 组ID
	 * @return
	 */
	public  String getGroupId() {
		return this.groupId;
	}
	
	
	
	
	public void setUserId( String userId) {
		this.userId = userId;
	}
	
	/**
	 * 返回 用户ID
	 * @return
	 */
	public  String getUserId() {
		return this.userId;
	}
	
	
	
	
	public void setIsMaster( Integer isMaster) {
		this.isMaster = isMaster;
	}
	
	/**
	 * 返回 0:默认组织，1：从组织
	 * @return
	 */
	public  Integer getIsMaster() {
		return this.isMaster;
	}
	
	
	
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getStatus() {
		return status;
	}

	public String getRoleAlias() {
		return roleAlias;
	}


	public void setRoleAlias(String roleAlias) {
		this.roleAlias = roleAlias;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleId( String roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * 返回 角色ID
	 * @return
	 */
	public  String getRoleId() {
		return this.roleId;
	}
	
	
	
	
	public void setType( String type) {
		this.type = type;
	}
	
	/**
	 * 返回 类型：groupUser,groupRole,userRole,groupUserRole
	 * @return
	 */
	public  String getType() {
		return this.type;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
	
	
	
	
 
}