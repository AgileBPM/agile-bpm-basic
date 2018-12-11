package com.dstz.sys.api.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dstz.sys.api.model.system.ISubsystem;
import com.dstz.sys.api.model.system.ISysResource;

public interface SysResourceService {

	List<ISubsystem> getCuurentUserSystem();

	ISubsystem getDefaultSystem(String currentUserId);

	List<ISysResource> getBySystemId(String systemId);

	List<ISysResource> getBySystemAndUser(String systemId, String userId);

	Map<String, Set<String>> getUrlRoleBySystem(String systemId);

}
