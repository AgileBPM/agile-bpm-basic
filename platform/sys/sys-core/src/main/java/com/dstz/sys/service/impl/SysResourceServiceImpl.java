package com.dstz.sys.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.sys.api.model.system.ISubsystem;
import com.dstz.sys.api.model.system.ISysResource;
import com.dstz.sys.api.service.SysResourceService;
import com.dstz.sys.core.manager.ResRoleManager;
import com.dstz.sys.core.manager.SubsystemManager;
import com.dstz.sys.core.manager.SysResourceManager;

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
	public Map<String, Set<String>> getUrlRoleBySystem(String systemId) {
		return resRoleManager.getUrlRoleBySystem(systemId);
	}

}
