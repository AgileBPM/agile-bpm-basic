package com.dstz.org.core.manager.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.api.context.ICurrentContext;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.core.constant.RelationTypeConstant;
import com.dstz.org.core.dao.OrgRelationDao;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.model.OrgRelation;
import com.github.pagehelper.Page;

import cn.hutool.core.util.ArrayUtil;
/**
 * 用户组织关系 Manager处理实现类
 * @author Jeff
 * @email for_office@qq.com
 * @time 2018-12-16 01:07:59
 */
@Service("orgRelationManager")
public class OrgRelationManagerImpl extends BaseManager<String, OrgRelation> implements OrgRelationManager{
	protected  final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	OrgRelationDao orgRelationDao;

	@Override
	public List<OrgRelation> getPostByUserId(String userId) {
		return orgRelationDao.getUserRelation(userId, RelationTypeConstant.POST_USER.getKey());
	}

	@Override
	public List<OrgRelation> getUserRelation(String userId, String relationType) {
		return orgRelationDao.getUserRelation(userId, relationType);
	}

	@Override
	public void removeByUserId(String userId) {
		orgRelationDao.removeByUserId(userId);
	}

	@Override
	public List<OrgRelation> getGroupPost(String groupId) {
		return orgRelationDao.getGroupPost(groupId);
	}

	@Override
	public void removeGroupPostByGroupId(String id) {
		orgRelationDao.removeGroupPostByGroupId(id);
	}

	/**
	 * 呵呵
	 */
	@Override
	public void updateUserGroupRelationIsMaster(String id) {
		OrgRelation relation = orgRelationDao.get(id);
		if(relation == null || StringUtil.isEmpty(relation.getUserId())) return ;
		
		List<String> relationList = Arrays.asList(RelationTypeConstant.GROUP_USER.getKey(),RelationTypeConstant.POST_USER.getKey());
		//查询出用户 与 岗位，组织的所有关系，置为 非主版本
		List<OrgRelation>  userGroupRelations = orgRelationDao.getRelationsByParam(relationList, relation.getUserId(), null, null);
		userGroupRelations.forEach(rel ->{
			rel.setIsMaster(0);
			orgRelationDao.update(rel);
		});
		//切换是否主版本
		relation.setIsMaster(relation.getIsMaster() == 0 ? 1:0);
		orgRelationDao.update(relation);
	}

	@Override
	public void changeStatus(String id, int status) {
	   OrgRelation relation = orgRelationDao.get(id);
	   if(relation == null) return ;
	   
	   relation.setStatus(status);
	   orgRelationDao.update(relation);
	   
	   String userId = relation.getUserId();
	   if(StringUtil.isEmpty(userId)) return;
	   
	   //清楚缓存
	   ICache<IGroup> iCache = AppUtil.getBean(ICache.class);
       String userKey = ICurrentContext.CURRENT_ORG + relation.getUserId();
       iCache.delByKey(userKey);
	}

	@Override
	public void saveUserGroupRelation(String groupId, String[] roleIds, String[] userIds) {
		for(String userId : userIds) {
			if(StringUtil.isEmpty(userId))continue;
			OrgRelation orgRelation = new OrgRelation(groupId,userId,null,RelationTypeConstant.GROUP_USER.getKey());
			if(ArrayUtil.isNotEmpty(roleIds)) {
				for(String roleId : roleIds) {
					orgRelation.setRoleId(roleId);
					orgRelation.setId(null);
					orgRelation.setType(RelationTypeConstant.POST_USER.getKey());
					// 不存在则创建
					if(!checkRelationIsExist(orgRelation)) {
						orgRelationDao.create(orgRelation);
					}else {
						log.warn("关系重复添加，已跳过  {}",JSON.toJSONString(orgRelation));
					}
					continue;
				}
				continue;
			}
			if(!checkRelationIsExist(orgRelation)) {
				orgRelationDao.create(orgRelation);
			}else {
				log.warn("关系重复添加，已跳过  {}",JSON.toJSONString(orgRelation));
			}
		}
	}
	
	
	private boolean  checkRelationIsExist(OrgRelation orgRelation) {
		int  count = orgRelationDao.getCountByRelation(orgRelation);
		return count >0;
	}

	@Override
	public int saveRoleUsers(String roleId, String[] userIds) {
		int i = 0;
		for(String userId : userIds) {
			OrgRelation orgRelation = new OrgRelation(null,userId,roleId,RelationTypeConstant.USER_ROLE.getKey());
			if(checkRelationIsExist(orgRelation)) {
				log.warn("关系重复添加，已跳过  {}",JSON.toJSONString(orgRelation));
				continue;
			}
			i++;
			orgRelationDao.create(orgRelation);
		}
		
		return i;
	}

	@Override
	public List<OrgRelation> getUserRole(String userId) {
		return orgRelationDao.getUserRole(userId);
	}

	@Override
	public OrgRelation getPost(String id) {
		return orgRelationDao.getPost(id);
	}

	/**
	 * 删除 角色、删除组织、删除岗位前进行校验
     * 删除角色 校验 岗位、岗位人员、角色人员是否存在
     * 删除组织、 校验岗位、组织人员
     * 删除岗位  校验岗位人员
	 */
	@Override
	public void removeCheck(String groupId, String roleId) {
		// 通过  关系查询 用户
    	QueryFilter filter = new DefaultQueryFilter();
    	// 岗位检查 岗位下人员是否存在
    	if(StringUtil.isNotEmpty(groupId)) {
    		filter.addFilter("relation.group_id_", groupId, QueryOP.EQUAL);
    	}
    	if(StringUtil.isNotEmpty(roleId)) {
    		filter.addFilter("relation.role_id_", roleId, QueryOP.EQUAL);
    	}
    	if(StringUtil.isNotEmpty(roleId) && StringUtil.isNotEmpty(groupId)) {
    		filter.addFilter("relation.type_", RelationTypeConstant.POST.getKey(), QueryOP.NOT_EQUAL);
    	}
    	
    	Page<OrgRelation> relationList = (Page<OrgRelation>)this.query(filter);
		if(relationList.isEmpty())  return ;
		

		StringBuilder sb = new StringBuilder("请先移除以下关系：<br>");
		for(OrgRelation relation : relationList) {
			getRelationNotes(relation,sb);
		}
		sb.append(" 共[").append(relationList.getTotal()).append("]条，待移除关系");
		
		throw new BusinessMessage(sb.toString());
	}
	
	private void getRelationNotes(OrgRelation relation, StringBuilder sb) {
		// 岗位
		if(relation.getType().equals(RelationTypeConstant.POST.getKey())) {
			sb.append("岗位：").append(relation.getPostName());
		}else if(relation.getType().equals(RelationTypeConstant.POST_USER.getKey())) {
			sb.append("岗位 [").append(relation.getPostName()).append("] 下用户：").append(relation.getUserName());
		}else if(relation.getType().equals(RelationTypeConstant.GROUP_USER.getKey())) {
			sb.append("组织 [").append(relation.getGroupName()).append("] 下用户：").append(relation.getUserName());
		}else if(relation.getType().equals(RelationTypeConstant.USER_ROLE.getKey())) {
			sb.append("角色 [").append(relation.getRoleName()).append("] 下用户：").append(relation.getUserName());
		}
		sb.append("<br>");
	}
    

}
