package com.dstz.org.core.manager.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.GroupRelationDao;
import com.dstz.org.core.manager.GroupRelationManager;
import com.dstz.org.core.model.GroupRelation;

/**
 * <pre>
 * 描述：组织关联关系 处理实现类
 * </pre>
 */
@Service("groupRelationManager")
public class GroupRelationManagerImpl extends BaseManager<String, GroupRelation> implements GroupRelationManager {
    @Resource
    GroupRelationDao orgRelDao;

    public GroupRelation getByCode(String code) {
        return this.orgRelDao.getByCode(code);
    }

    public List<GroupRelation> getListByGroupId(String groupId) {
        return this.orgRelDao.getListByGroupId(groupId);
    }

    public List<GroupRelation> queryInfoList(QueryFilter queryFilter) {
        return this.orgRelDao.queryInfoList(queryFilter);
    }

    public List<GroupRelation> getListByUserId(String userId) {
    	if(StringUtil.isEmpty(userId))return Collections.emptyList();
        return this.orgRelDao.getRelListByParam(null, userId);
    }

    public List<GroupRelation> getListByAccount(String account) {
    	if(StringUtil.isEmpty(account))return Collections.emptyList();
        return this.orgRelDao.getRelListByParam(account,null);
    }
}
