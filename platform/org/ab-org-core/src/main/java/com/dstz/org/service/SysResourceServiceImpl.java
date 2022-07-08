package com.dstz.org.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.org.api.model.system.ISubsystem;
import com.dstz.org.api.model.system.ISysResource;
import com.dstz.org.api.service.SysResourceService;
import com.dstz.org.core.manager.ResRoleManager;
import com.dstz.org.core.manager.SubsystemManager;
import com.dstz.org.core.manager.SysResourceManager;

/**
 * 用户系统资源服务接口
 * @author jeff
 */
@Service
public class SysResourceServiceImpl implements SysResourceService{
	@Resource
	SysResourceManager sysResourceManager;
	@Resource
	SubsystemManager sybSystemManager;
	@Resource
	ResRoleManager resRoleManager;
	
	
	@Override
	public List<ISubsystem> getCuurentUserSystem() {
		return (List)sybSystemManager.getCuurentUserSystem();
	}

	@Override
	public ISubsystem getDefaultSystem(String currentUserId) {
		return sybSystemManager.getDefaultSystem(currentUserId);
	}

	@Override
	public List<ISysResource> getBySystemId(String systemId) {
		return (List)sysResourceManager.getBySystemId(systemId);
	}

	@Override
	public List<ISysResource> getBySystemAndUser(String systemId, String userId) {
		return (List)sysResourceManager.getBySystemAndUser(systemId, userId);
	}


	@Override
	public Set<String> getAccessRoleByUrl(String url) {
		return resRoleManager.getAccessRoleByUrl(url);
	}

}
