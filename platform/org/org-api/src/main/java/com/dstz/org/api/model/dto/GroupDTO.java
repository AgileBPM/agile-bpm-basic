package com.dstz.org.api.model.dto;

import com.dstz.org.api.model.IGroup;
/**
 * <pre>
 *  岗位对外post code postID 均为 【组织ID-组织ID】
 *  岗位选择框 postCODE 为 关系的ID 
 *  对外提供岗位查询 CODE查询的时候为 关系ID 返回POST 对象ID已经被转换成 【组织ID-组织ID】
 *  岗位不支持ID 的查询
 *  当前用户的岗位ID 也为【组织ID-组织ID】
 * </pre>
 */
public class GroupDTO implements IGroup {
	String identityType;
	String groupId;
	String groupName;
	String groupCode;
	Long Sn;
	String groupType;
	String parentId;
	String path;
	
	public GroupDTO(IGroup group) {
		this.groupCode = group.getGroupCode();
		this.groupId = group.getGroupId();
		this.groupType = group.getGroupType();
		this.parentId = group.getParentId();
		this.groupName = group.getGroupName();
	}
	
	public GroupDTO() {

	}
	
	public GroupDTO(String groupId, String groupName, String groupType) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.groupType = groupType;
	}

	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
 
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
 
	public Long getSn() {
		return Sn;
	}
	public void setSn(Long sn) {
		Sn = sn;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
 
	

}
