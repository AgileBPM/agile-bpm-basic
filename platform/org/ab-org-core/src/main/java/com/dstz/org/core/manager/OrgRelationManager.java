package com.dstz.org.core.manager;

import java.util.List;

import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.manager.Manager;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.core.model.OrgRelation;

/**
 * 用户组织关系 Manager处理接口
 * @author Jeff
 * @email for_office@qq.com
 * @time 2018-12-16 01:07:59
 */
public interface OrgRelationManager extends Manager<String, OrgRelation>{
	
	/**
	 * 获取用户的岗位
	 * @param userId
	 * @return
	 */
	List<OrgRelation> getPostByUserId(String userId);
	
	/**
	 * 获取所有用户的关系
	 * @param userId
	 * @param relationType
	 * @return
	 */
	List<OrgRelation>  getUserRelation(String userId,String relationType);

	/**
	 * 通过用户删除所有关联的关系
	 * @param id
	 */
	void removeByUserId(String id);

	/**
	 * 获取组织上的岗位
	 * @param groupId
	 * @return
	 */
	List<OrgRelation> getGroupPost(String groupId);
	
	/**
	 * 删除 组织上的岗位
	 * @param id
	 */
	void removeGroupPostByGroupId(String groupId);

	/**
	 * 更新组织关系是否为主岗位
	 * @param id
	 */
	void updateUserGroupRelationIsMaster(String id);

	/**
	 * 修改状态
	 * @param id
	 * @param status
	 */
	void changeStatus(String id, int status);
	
	/**
	 * 保存 用户与组织的关系
	 * @param groupId
	 * @param roleIds
	 * @param userIds
	 */
	void saveUserGroupRelation(String groupId, String[] roleIds, String[] userIds);
	
	/**
	 * 保存 用户与角色的关系
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	int saveRoleUsers(String roleId, String[] userIds);
	
	/**
	 * 获取用户角色 含岗位角色
	 * @param userId
	 * @return
	 */
	List<OrgRelation> getUserRole(String userId);
	
	/**
	 * 获取岗位，岗位没有code，只有ID
	 * @param groupId
	 * @return
	 */
	OrgRelation getPost(String groupId);

	void removeCheck(String groupId, String roleId);
	
}
