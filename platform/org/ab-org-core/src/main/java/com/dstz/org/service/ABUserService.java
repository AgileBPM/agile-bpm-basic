package com.dstz.org.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.IUserRole;
import com.dstz.org.api.model.dto.UserDTO;
import com.dstz.org.api.model.dto.UserRoleDTO;
import com.dstz.org.api.service.UserService;
import com.dstz.org.core.constant.RelationTypeConstant;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.manager.UserManager;
import com.dstz.org.core.model.OrgRelation;
import com.dstz.org.core.model.User;

import cn.hutool.core.collection.CollectionUtil;


@SuppressWarnings("unchecked")
@Service(value = "userService")
public class ABUserService implements UserService {
    @Resource
    UserManager userManager;
    @Resource
    GroupManager groupManager;
    @Resource
    OrgRelationManager orgRelationMananger;
    

    @Override
    public IUser getUserById(String userId) {
    	IUser user = userManager.get(userId);
        return BeanCopierUtils.transformBean(user, UserDTO.class);
    }

    @Override
    public IUser getUserByAccount(String account) {
    	IUser user =  userManager.getByAccount(account);
    	return BeanCopierUtils.transformBean(user, UserDTO.class);
    }

    @Override
    public List<IUser> getUserListByGroup(String groupType, String groupId) {
        //此处可以根据不同的groupType去调用真实的实现：如角色下的人，组织下的人
    	RelationTypeConstant relationType = RelationTypeConstant.getUserRelationTypeByGroupType(groupType);
    	if(relationType == null) {
    		throw new BusinessException(groupType + "查找不到对应用户的类型！");
    	}
    	
    	List<User> userList  = userManager.getUserListByRelation(groupId,relationType.getKey());

        if(CollectionUtil.isNotEmpty(userList)) {
        	return (List)BeanCopierUtils.transformList(userList, UserDTO.class);
        }
        
        return Collections.emptyList();
    }

	@Override
	public List<IUserRole> getUserRole(String userId) {
		List<OrgRelation> orgRelationList = orgRelationMananger.getUserRole(userId);
		List<UserRoleDTO> userRoleList = new ArrayList<>();
		
		for(OrgRelation orgRelation : orgRelationList) {
			UserRoleDTO userRole = new UserRoleDTO(orgRelation.getRoleId(),orgRelation.getUserId(),orgRelation.getUserName(),orgRelation.getRoleName());
			userRole.setAlias(orgRelation.getRoleAlias());
			userRoleList.add(userRole); 
		}
		
		return  (List)userRoleList;
	}


}
