package com.dstz.org.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.org.core.model.UserRole;

import java.util.List;

/**
 * <pre>
 * 描述：用户角色管理 处理接口
 * </pre>
 */
public interface UserRoleManager extends Manager<String, UserRole> {

    /**
     * 根据用户和角色id 查询 关联关系。
     *
     * @param roleId
     * @param userId
     * @return
     */
    UserRole getByRoleIdUserId(String roleId, String userId);

    /**
     * 获取用户的角色。
     *
     * @param userId
     * @return
     */
    List<UserRole> getListByUserId(String userId);

    /**
     * 根据角色ID查询关联的用户。
     *
     * @param roleId
     * @return
     */
    List<UserRole> getListByRoleId(String roleId);

    /**
     * 根据角色别名查询关联的用户。
     *
     * @param roleId
     * @return
     */
    List<UserRole> getListByAlias(String alias);
}
