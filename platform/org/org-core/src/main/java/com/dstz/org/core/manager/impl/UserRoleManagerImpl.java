package com.dstz.org.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.UserRoleDao;
import com.dstz.org.core.manager.UserRoleManager;
import com.dstz.org.core.model.UserRole;

/**
 * <pre>
 * 描述：用户角色管理 处理实现类
 * </pre>
 */
@Service("userRoleManager")
public class UserRoleManagerImpl extends BaseManager<String, UserRole> implements UserRoleManager {
    @Resource
    UserRoleDao userRoleDao;

    public UserRole getByRoleIdUserId(String roleId, String userId) {
        return userRoleDao.getByRoleIdUserId(roleId, userId);
    }

    // 这是什么鬼写法
    public List<UserRole> getListByUserId(String userId) {
    	return userRoleDao.queryByParams(null, userId, null);
    }

    public List<UserRole> getListByRoleId(String roleId) {
    	return userRoleDao.queryByParams(roleId, null, null);
    }

    public List<UserRole> getListByAlias(String alias) {
    	
    	return userRoleDao.queryByParams(null,null,alias);
    }
}
