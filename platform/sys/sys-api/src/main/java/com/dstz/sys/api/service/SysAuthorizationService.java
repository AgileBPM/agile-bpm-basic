package com.dstz.sys.api.service;

import java.util.Map;
import java.util.Set;

import com.dstz.sys.api.constant.RightsObjectConstants;

public interface SysAuthorizationService {
	Set<String> getUserRights(String userId);

	/**
	 * 获取授权sql
	 * @param userId 用户id
	 * @param targetKey 默认为Id_ 关联authorization的 targetId在数据库中的字段名字，
	 * @return
	 */
	Map<String, Object> getUserRightsSql(RightsObjectConstants rightsObject,String userId, String targetKey);

}
