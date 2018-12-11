package com.dstz.org.core.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.GroupRelDefDao;
import com.dstz.org.core.manager.GroupRelDefManager;
import com.dstz.org.core.model.GroupRelDef;

/**
 * <pre>
 * 描述：组织关系定义 处理实现类
 * </pre>
 */
@Service("groupReldefManager")
public class GroupRelDefManagerImpl extends BaseManager<String, GroupRelDef> implements GroupRelDefManager {
    @Resource
    GroupRelDefDao groupRelDefDao;


    public GroupRelDef getByCode(String code) {
        return groupRelDefDao.getByCode(code);
    }

}
