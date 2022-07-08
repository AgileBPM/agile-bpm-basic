package com.dstz.org.api.service;

import java.util.List;
import java.util.Set;

import com.dstz.org.api.model.system.ISubsystem;
import com.dstz.org.api.model.system.ISysResource;

public interface SysResourceService {

	List<ISubsystem> getCuurentUserSystem();

	ISubsystem getDefaultSystem(String currentUserId);

	List<ISysResource> getBySystemId(String systemId);

	List<ISysResource> getBySystemAndUser(String systemId, String userId);

	Set<String> getAccessRoleByUrl(String url);

}
