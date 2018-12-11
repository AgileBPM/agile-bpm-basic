package com.dstz.org.api.service.impl;

import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.GroupType;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.dto.GroupDto;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.core.manager.*;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.User;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户与组关系的实现类：通过用户找组，通过组找人等
 *
 * @author Administrator
 */
@Service("userGroupService")
public class DefaultGroupService implements GroupService {
    @Resource
    UserManager userManager;

    @Resource
    GroupManager groupManager;

    @Resource
    GroupRelationManager groupRelManager;

    @Resource
    UserRoleManager userRoleManager;

    @Resource
    RoleManager roleManager;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<IGroup> getGroupsByGroupTypeUserId(String groupType, String userId) {
    	List listGroup  = null;
    	
        if (groupType.equals(GroupTypeConstant.ORG.key())) {
        	listGroup = (List) groupManager.getByUserId(userId);
        }
        if (groupType.equals(GroupTypeConstant.ROLE.key())) {
        	listGroup = (List) roleManager.getListByUserId(userId);
        }
        if (groupType.equals(GroupTypeConstant.POSITION.key())) {
        	listGroup = (List) groupRelManager.getListByUserId(userId);
        }
        
        if(listGroup != null) {
        	return (List)BeanCopierUtils.transformList(listGroup, GroupDto.class);
        }
        
        return null;
    }

    @Override
    public Map<String, List<IGroup>> getAllGroupByAccount(String account) {
    	User user = userManager.getByAccount(account);
    	if(user == null) return Collections.EMPTY_MAP;
    	
    	return getAllGroupByUserId(user.getId());
    }

    
    @Override
    public Map<String, List<IGroup>> getAllGroupByUserId(String userId) {
        Map<String, List<IGroup>> listMap = new HashMap<String, List<IGroup>>();
        
        List<IGroup> listOrg = (List) groupManager.getByUserId(userId);
        if (BeanUtils.isNotEmpty(listOrg)) {
        	List<IGroup> groupList = (List)BeanCopierUtils.transformList(listOrg, GroupDto.class);
            listMap.put(GroupTypeConstant.ORG.key(), groupList);
        }
        List<IGroup> listRole = (List) roleManager.getListByUserId(userId);
        if (BeanUtils.isNotEmpty(listRole)) {
        	List<IGroup> groupList = (List)BeanCopierUtils.transformList(listRole, GroupDto.class);
            listMap.put(GroupTypeConstant.ROLE.key(), groupList);
        }
        List<IGroup> listOrgRel = (List) groupRelManager.getListByUserId(userId);
        if (BeanUtils.isNotEmpty(listOrgRel)) {
        	List<IGroup> groupList = (List)BeanCopierUtils.transformList(listOrgRel, GroupDto.class);
            listMap.put(GroupTypeConstant.POSITION.key(), groupList);
        }
        return listMap;
    }


    /**
     * 根据用户ID获取所有组
     */
    @Override
    public List<IGroup> getGroupsByUserId(String userId) {
        List<IGroup> listMap = new ArrayList<IGroup>();
        List<IGroup> listOrg = (List) groupManager.getByUserId(userId);
        if (BeanUtils.isNotEmpty(listOrg)) {
            listMap.addAll(listOrg);
        }
        List<IGroup> listRole = (List) roleManager.getListByUserId(userId);
        if (BeanUtils.isNotEmpty(listRole)) {
            listMap.addAll(listRole);
        }
        List<IGroup> listOrgRel = (List) groupRelManager.getListByUserId(userId);
        if (BeanUtils.isNotEmpty(listOrgRel)) {
            listMap.addAll(listOrgRel);
        }
        
        //转换成GROUP DTO
        List<IGroup> groupList = (List)BeanCopierUtils.transformList(listMap, GroupDto.class);
        
        return groupList;
    }

    /**
     * 根据组类别和组ID获取组定义
     */
    @Override
    public IGroup getById(String groupType, String groupId) {
    	IGroup group = null;
        if (groupType.equals(GroupTypeConstant.ORG.key())) {
        	group = groupManager.get(groupId);
        }
        if (groupType.equals(GroupTypeConstant.ROLE.key())) {
        	group = roleManager.get(groupId);
        }
        if (groupType.equals(GroupTypeConstant.POSITION.key())) {
        	group = groupRelManager.get(groupId);
        }
        
        if(group == null) return null;
        
        return new GroupDto(group);
    }

    /**
     * 根据组类别和组编码获取组定义
     */
    @Override
    public IGroup getByCode(String groupType, String code) {
    	IGroup group = null;
    	
        if (groupType.equals(GroupTypeConstant.ORG.key())) {
        	group = groupManager.getByCode(code);
        }
        if (groupType.equals(GroupTypeConstant.ROLE.key())) {
        	group = roleManager.getByAlias(code);
        }
        if (groupType.equals(GroupTypeConstant.POSITION.key())) {
        	group = groupRelManager.getByCode(code);
        }
        
        if(group == null) return null;
        
        return new GroupDto(group);
    }

    /**
     * 获取所有组类型
     */
    @Override
    public List<GroupType> getGroupTypes() {
        List<GroupType> list = new ArrayList<GroupType>();
        for (GroupTypeConstant e : GroupTypeConstant.values()) {
            GroupType type = new GroupType(e.key(), e.label());
            list.add(type);
        }
        return list;
    }

    @Override
    public IGroup getMainGroup(String userId) {
        return groupManager.getMainGroup(userId);
    }

}
