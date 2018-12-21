package com.dstz.org.core.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.OrgRelation;

/**
 * 用户组织关系 DAO接口
 * @author Jeff
 * @email for_office@qq.com
 * @time 2018-12-16 01:07:59
 */
public interface OrgRelationDao extends BaseDao<String, OrgRelation> {
	
	/**
	 * 获取用户的 关系
	 * @param userId 必须
	 * @param relationType 非必须
	 * @return
	 */
	List<OrgRelation> getUserRelation(@Param("userId") String userId,@Param("relationType")String relationType);
	
	/**
	 * 通过 userId 刪除所有關係
	 * @param userId
	 */
	void removeByUserId(String userId);
	
}
