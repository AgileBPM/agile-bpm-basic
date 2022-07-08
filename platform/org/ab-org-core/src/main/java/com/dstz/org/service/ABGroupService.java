package com.dstz.org.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.dto.GroupDTO;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.manager.RoleManager;
import com.dstz.org.core.manager.UserManager;
import com.dstz.org.core.model.OrgRelation;
import com.dstz.org.core.model.User;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 用户与组关系的实现类：通过用户找组，通过组找人等
 */
@Service("defaultGroupService")
public class ABGroupService implements GroupService {
	private Logger log  = LoggerFactory.getLogger(getClass());
    @Resource
    UserManager userManager;

    @Resource
    GroupManager groupManager;
    
    @Autowired
    RoleManager roleManager;

    @Resource
    OrgRelationManager orgRelationManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<? extends IGroup> getGroupsByGroupTypeUserId(String groupType, String userId) {
    	List listGroup  = null;
    	
        if (groupType.equals(GroupTypeConstant.ORG.key())) {
        	listGroup =  groupManager.getByUserId(userId);
        }
        if (groupType.equals(GroupTypeConstant.ROLE.key())) {
        	listGroup =  roleManager.getByUserId(userId);
        }
        
        if (groupType.equals(GroupTypeConstant.POST.key()) && false) {
        	listGroup = orgRelationManager.getPostByUserId(userId);
        }
        
        if(listGroup != null) {
        	return (List)BeanCopierUtils.transformList(listGroup, GroupDTO.class);
        }
        
        return null;
    }

    @Override
    public Map<String, List<? extends IGroup>> getAllGroupByAccount(String account) {
    	User user = userManager.getByAccount(account);
    	if(user == null) return Collections.emptyMap();
    	
    	return getAllGroupByUserId(user.getId());
    }

    
    @Override
    public Map<String, List<? extends IGroup>> getAllGroupByUserId(String userId) {
        Map<String, List<? extends IGroup>> listMap = new HashMap<String, List<? extends IGroup>>();
        
        List<? extends IGroup> listOrg = groupManager.getByUserId(userId);
        if (CollectionUtil.isNotEmpty(listOrg)) {
        	List<? extends IGroup> groupList = BeanCopierUtils.transformList(listOrg, GroupDTO.class);
            listMap.put(GroupTypeConstant.ORG.key(), groupList);
        }
        List<? extends IGroup> listRole = roleManager.getByUserId(userId);
        if (CollectionUtil.isNotEmpty(listRole)) {
        	List<? extends IGroup> groupList = BeanCopierUtils.transformList(listRole, GroupDTO.class);
            listMap.put(GroupTypeConstant.ROLE.key(), groupList);
        }
        
        /**
         *  岗位对外post code postID 均为 【组织ID-组织ID】
         *  岗位选择框 postCODE 为 关系的ID 
         *  对外提供岗位查询 CODE查询的时候为 关系ID 返回POST 对象ID已经被转换成 【组织ID-组织ID】
         *  岗位不支持ID 的查询
         *  当前用户的岗位ID 也为【组织ID-组织ID】
         */
        List<OrgRelation> listOrgRel =  orgRelationManager.getPostByUserId(userId);
        if (CollectionUtil.isNotEmpty(listOrgRel)) {
        	List<IGroup> userGroups = new ArrayList<>();
        	listOrgRel.forEach(post ->{
            	userGroups.add( new GroupDTO(post.getPostId(),post.getPostName(),GroupTypeConstant.POST.key()) );
            });
            listMap.put(GroupTypeConstant.POST.key(), userGroups);
        }
        return listMap;
    }


    /**
     * 根据用户ID获取所有组
     */
    @Override
    public List<? extends IGroup> getGroupsByUserId(String userId) {
        List<IGroup> userGroups = new ArrayList<IGroup>();
        List<? extends IGroup> listOrg = groupManager.getByUserId(userId);
        if (CollectionUtil.isNotEmpty(listOrg)) {
            userGroups.addAll(listOrg);
        }
        List<? extends IGroup> listRole = roleManager.getByUserId(userId);
        if (CollectionUtil.isNotEmpty(listRole)) {
            userGroups.addAll(listRole);
        }
        List<OrgRelation> listOrgRel = orgRelationManager.getPostByUserId(userId);
        listOrgRel.forEach(post ->{
        	userGroups.add( new GroupDTO(post.getPostId(),post.getPostName(),GroupTypeConstant.POST.key()) );
        });
        
        //转换成GROUP DTO 13556806587
        List<? extends IGroup> groupList = BeanCopierUtils.transformList(userGroups, GroupDTO.class);
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
        if (groupType.equals(GroupTypeConstant.POST.key())) {
        	// 详情请查看95行代码
        	log.warn("岗位不支持ID的查询！！！->{}",groupId);
        	OrgRelation relation  = orgRelationManager.getPost(groupId);
        	if(relation != null) {
        		return new GroupDTO(relation.getPostId(), relation.getPostName(),groupType);
        	}
        }
        
        if(group == null) return null;
        
        return new GroupDTO(group);
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
        OrgRelation relation  = orgRelationManager.getPost(code);
    	if(relation != null) {
    		// 详情请查看95行代码
    		return new GroupDTO(relation.getPostId(), relation.getPostName(),groupType);
    	}
        
        if(group == null) return null;
        return new GroupDTO(group);
    }


    @Override
    public IGroup getMainGroup(String userId) {
        return groupManager.getMainGroup(userId);
    }

}
