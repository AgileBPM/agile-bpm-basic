package com.dstz.org.core.manager;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.manager.Manager;
import com.dstz.org.core.model.GroupUser;

import java.util.List;

/**
 * <pre>
 * 描述：用户组织关系 处理接口
 * </pre>
 */
public interface GroupUserManager extends Manager<String, GroupUser> {

    int updateUserPost(String id, String relId);

    /**
     * 根据组织用户和关系id查找关联关系。
     *
     * @param orgId
     * @param userId
     * @param relId
     * @return
     */
    GroupUser getGroupUser(String orgId, String userId, String relId);

    /**
     * 根据用户和组织ID获取关联关系。
     *
     * @param orgId
     * @param userId
     * @return
     */
    List<GroupUser> getListByGroupIdUserId(String orgId, String userId);

    /**
     * 获取用户的主岗位组织关系
     *
     * @return
     */
    GroupUser getGroupUserMaster(String userId);

    int removeByOrgIdUserId(String orgId, String userId);

    /**
     * 设置主组织关系。
     *
     * @param id 主键
     * @return
     */
    void setMaster(String id);

    /**
     * 根据queryfilter查询部门或岗位下的人员。
     *
     * @param queryFilter
     * @return
     */
    public List getUserByGroup(QueryFilter queryFilter);

    /**
     * 为某个组添加用户
     *
     * @param grouId
     * @param userId
     * @param relIds 组关系可选
     */
    void saveGroupUserRel(String grouId, String[] userId, String[] relIds);

}
