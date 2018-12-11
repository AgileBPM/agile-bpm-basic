package com.dstz.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dstz.base.core.util.BeanUtils;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.api.service.UserService;
import com.dstz.sys.api.model.SysIdentity;
import com.dstz.sys.api.service.SysIdentityConvert;
@Component
public class SysIdentityConvertServiceImpl implements SysIdentityConvert {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysIdentityConvert.class);

	@Resource
	GroupService groupService;
	
	@Resource
	UserService userService;
	
	@Override
	public IUser convert2User(SysIdentity identity) {
		List<IUser> users = convert2Users(identity);
		
		if(BeanUtils.isNotEmpty(users)) {
			return users.get(0);
		}
		
		return null;
		
	}

	@Override
	public List<IUser> convert2Users(SysIdentity identity) {
		//如果为用户
		if(SysIdentity.TYPE_USER.equals(identity.getType())) {
			List<IUser> users = new ArrayList<>();
			
			IUser user = userService.getUserById(identity.getId());
			if(user == null) {
				LOGGER.error("identity convert 2 users error id[{}],name[{}] not found! ",identity.getId(),identity.getName());
			}else {
				users.add(user);
			}
			return users;
		}
		//目前其他均为组类型
		List<IUser> userList = userService.getUserListByGroup(identity.getType(), identity.getId());
		
		return userList;
	}

	@Override
	public List<IUser> convert2Users(List<SysIdentity> identitys) {
		List<IUser> users = new ArrayList<>();
		
		for(SysIdentity identity : identitys) {
			users.addAll(convert2Users(identity));
		}
		
		return users;
	}

}
