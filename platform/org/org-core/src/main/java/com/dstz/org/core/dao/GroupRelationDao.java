package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.GroupRelation;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 * 描述：组织关联关系 DAO接口
 * </pre>
 */
@MapperScan
public interface GroupRelationDao extends BaseDao<String, GroupRelation> {
    GroupRelation getByCode(String code);

    List<GroupRelation> getListByGroupId(String groupId);

    List<GroupRelation> queryInfoList(QueryFilter queryFilter);

    GroupRelation getByGroupIdRelDefId(String orgId, String relDefId);

    List<GroupRelation> getRelListByParam(@Param("account")String account,@Param("userId")String userId);
}
