package com.dstz.org.core.manager.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.UserDao;
import com.dstz.org.core.dao.UserRoleDao;
import com.dstz.org.core.manager.UserManager;
import com.dstz.org.core.model.User;

/**
 * <pre>
 * 描述：用户表 处理实现类
 * </pre>
 */
@Service("userManager")
public class UserManagerImpl extends BaseManager<String, User> implements UserManager {
    @Resource
    UserDao userDao;
    @Resource
    UserRoleDao userRoleDao;

    public User getByAccount(String account) {
        return this.userDao.getByAccount(account);
    }

    /**
     * 不含组织用户关系数据
     */
    public List<User> queryOrgUser(QueryFilter queryFilter) {
        return userDao.queryOrgUser(queryFilter);
    }

    /**
     * 不含组织用户关系数据
     */
    public List<User> getUserListByOrgId(String orgId) {
    	return userDao.getUserListByOrgId(orgId);
    }


    public List<User> getListByRelCode(String relCode) {
        return userDao.getListByRelCode(relCode);
    }

    public List<User> getListByRelId(String relId) {
        return userDao.getUserListByRelId(relId);
    }

    public List<User> getUserListByRoleId(String roleId) {
    	if(StringUtil.isEmpty(roleId))return Collections.emptyList();
    	
        return userDao.getUserListByRole(roleId, null);
    }

    @Override
    public boolean isUserExist(User user) {
        return userDao.isUserExist(user)>0;
    }

    @Override
    public List queryUserGroupRel(QueryFilter queryFilter) {
        return userDao.queryUserGroupRel(queryFilter);
    }

	@Override
	public List<User> getUserListByRoleCode(String roleCode) {
		if(StringUtil.isEmpty(roleCode))return Collections.emptyList();
		
		return userDao.getUserListByRole(null, roleCode);
	}
}
