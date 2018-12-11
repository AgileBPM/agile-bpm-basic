package com.dstz.org.core.manager;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.manager.Manager;
import com.dstz.org.core.model.User;

import java.util.List;

/**
 * <pre>
 * 描述：用户表 处理接口
 * </pre>
 */
public interface UserManager extends Manager<String, User> {
    /**
     * 根据Account取定义对象。
     *
     * @param code
     * @return
     */
    User getByAccount(String account);

    /**
     * 不含用户组织关系
     */
    List<User> getUserListByOrgId(String orgId);

    /**
     * 不含用户组织关系
     *
     * @param queryFilter
     * @return
     */
    List<User> queryOrgUser(QueryFilter queryFilter);

    /**
     * 含组织用户关系表数据
     *
     * @param queryFilter
     * @return
     */
    List queryUserGroupRel(QueryFilter queryFilter);

    /**
     * 根据岗位编码获取用户列表
     *
     * @param relCode
     * @return
     */
    List<User> getListByRelCode(String relCode);

    /**
     * 根据岗位ID获取用户列表
     *
     * @param relCode
     * @return
     */
    List<User> getListByRelId(String relId);

    /**
     * 根据角色ID获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> getUserListByRoleId(String roleId);

    /**
     * 根据角色Code获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> getUserListByRoleCode(String roleCode);

    /**
     * 判断系统中用户是否存在。
     *
     * @param user
     * @return
     */
    boolean isUserExist(User user);
}
