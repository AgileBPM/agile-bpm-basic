package com.dstz.org.core.manager.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.RoleDao;
import com.dstz.org.core.manager.RoleManager;
import com.dstz.org.core.model.Role;

/**
 * <pre>
 * 描述：角色管理 处理实现类
 * </pre>
 */
@Service("roleManager")
public class RoleManagerImpl extends BaseManager<String, Role> implements RoleManager {
    @Resource
    RoleDao roleDao;

    public Role getByAlias(String alias) {
        return roleDao.getByAlias(alias);
    }

    public List<Role> getListByUserId(String userId) {
    	if(StringUtil.isEmpty(userId))return Collections.emptyList();
        return roleDao.getList(userId, null);
    }

    public List<Role> getListByAccount(String account) {
    	if(StringUtil.isEmpty(account))return Collections.emptyList();
    	
        return roleDao.getList(null,account);
    }

    @Override
    public boolean isRoleExist(Role role) {
        return roleDao.isRoleExist(role) != 0;
    }

}
