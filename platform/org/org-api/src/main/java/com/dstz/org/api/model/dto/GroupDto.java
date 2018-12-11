package com.dstz.org.api.model.dto;

import com.dstz.org.api.model.GroupStructEnum;
import com.dstz.org.api.model.IGroup;

public class GroupDto implements IGroup {
	String identityType;
	String groupId;
	String name;
	String groupCode;
	GroupStructEnum  groupStructEnum;
	Long Sn;
	String groupType;
	String parentId;
	String path;
	
	public GroupDto(IGroup group) {
		this.groupCode = group.getGroupCode();
		this.groupId = group.getGroupId();
		this.groupType = group.getGroupType();
		this.parentId = group.getParentId();
		this.name = group.getName();
	}
	
	public GroupDto() {

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public GroupStructEnum getGroupStructEnum() {
		return groupStructEnum;
	}
	public void setGroupStructEnum(GroupStructEnum groupStructEnum) {
		this.groupStructEnum = groupStructEnum;
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
	@Override
	public GroupStructEnum getStruct() {
		return groupStructEnum;
	}
	

}
