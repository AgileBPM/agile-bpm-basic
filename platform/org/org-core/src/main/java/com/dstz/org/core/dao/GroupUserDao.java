package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.GroupUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 描述：用户组织关系 DAO接口
 */
@MapperScan
public interface GroupUserDao extends BaseDao<String, GroupUser> {

    int updateUserPost(@Param("id")String id, @Param("relId")String relId);

    GroupUser getGroupUser(@Param("groupId")String groupId, @Param("userId")String userId,@Param("relId") String relId);

    int removeByGroupIdUserId(@Param("groupId")String groupId, @Param("userId")String userId);

    List<GroupUser> getListByGroupIdUserId(@Param("groupId")String groupId,@Param("userId") String userId);

    /**
     * 获取用户的主岗位组织关系
     * @return
     */
    GroupUser getGroupUserMaster(String userId);

    /**
     * 设置主部门
     *
     * @param id
     * @return
     */
    Integer setMaster(String id);

    /**
     * 取消主部门。
     *
     * @param userId
     * @return
     */
    Integer cancelUserMasterGroup(String userId);

    /**
     * 根据组织和关系id获取用户列表。
     *
     * @param queryFilter
     * @return
     */
    List getUserByGroup(QueryFilter queryFilter);
}
