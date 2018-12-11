package com.dstz.org.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.GroupDao;
import com.dstz.org.core.dao.UserDao;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.User;

/**
 * <pre>
 * 描述：组织架构 处理实现类
 * </pre>
 */
@Service("groupManager")
public class GroupManagerImpl extends BaseManager<String, Group> implements GroupManager {
    @Resource
    GroupDao groupDao;
    @Resource
    UserDao userDao;


    public Group getByCode(String code) {
        return groupDao.getByCode(code);
    }

    public List<Group> getByUserId(String userId) {
        return groupDao.getByUserId(userId);
    }

    public List<Group> getByUserAccount(String account) {
        User user = userDao.getByAccount(account);
        return groupDao.getByUserId(user.getId());
    }

    @Override
    public Group getMainGroup(String userId) {
        List<Group> list = groupDao.getByUserId(userId);
        if (BeanUtils.isEmpty(list)) return null;
        if (list.size() == 1) return list.get(0);
        for (Group org : list) {
            if (org.getIsMaster() == 1) return org;
        }
        return list.get(0);
    }
}
