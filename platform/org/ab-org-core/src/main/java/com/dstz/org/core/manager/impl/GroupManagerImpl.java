package com.dstz.org.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.core.dao.GroupDao;
import com.dstz.org.core.dao.UserDao;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.OrgRelation;
import com.dstz.org.core.model.User;

import cn.hutool.core.collection.CollectionUtil;

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
    @Autowired
    OrgRelationManager orgRelationMananger;


    public Group getByCode(String code) {
        return groupDao.getByCode(code);
    }
    
    @Override
    public void remove(String id) {
    	orgRelationMananger.removeCheck(id, null);
    	Group group =	groupDao.get(id);
    	List<Group> childList = groupDao.getChildByPath(group.getPath()+"%");
    	
    	// 级联删除子组织
    	childList.forEach(g ->{
    		orgRelationMananger.removeCheck(g.getId(), null);
    		super.remove(g.getId());
    	});
    	
    	super.remove(id);
    }
    
    @Override
    public Group get(String entityId) {
    	Group group =  super.get(entityId);
    	if(group != null) {
    		List<OrgRelation> orgRelationList = orgRelationMananger.getGroupPost(group.getId());
    		group.setOrgRelationList(orgRelationList);
    	}
    	return group;
    }
    @Override
    public void create(Group entity) {
    	entity.setId(IdUtil.getSuid());
    	entity.setPath(entity.getId());
    	if(StringUtil.isNotZeroEmpty(entity.getParentId())) {
    		Group parent = groupDao.get(entity.getParentId());
    		if(parent != null && parent.getPath() != null) {
    			entity.setPath(parent.getPath().concat(".").concat(entity.getId()));
    		}
    	}
    	
    	// 创建组织岗位
    	List<OrgRelation> list = entity.getOrgRelationList();
    	if(CollectionUtil.isNotEmpty(list)) {
    		list.forEach( orgRelation ->{
    			orgRelation.setGroupId(entity.getId());
    			orgRelationMananger.create(orgRelation);
    		});
    	}
    	
    	super.create(entity);
    }
    
    @Override
    public void update(Group entity) {
    	orgRelationMananger.removeGroupPostByGroupId(entity.getId());
    	
    	// 创建组织岗位
    	List<OrgRelation> list = entity.getOrgRelationList();
    	if(CollectionUtil.isNotEmpty(list)) {
    		list.forEach( orgRelation ->{
    			orgRelation.setGroupId(entity.getId());
    			orgRelationMananger.create(orgRelation);
    		});
    	}
    	super.update(entity);
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
       List<Group> groups = groupDao.getByUserId(userId);
       if(CollectionUtil.isEmpty(groups)) return null;
       
        return groups.get(0);
    }
}
