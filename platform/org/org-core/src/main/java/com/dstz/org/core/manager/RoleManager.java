package com.dstz.org.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.org.core.model.Role;

import java.util.List;

/**
 * <pre>
 * 描述：角色管理 处理接口
 * </pre>
 */
public interface RoleManager extends Manager<String, Role> {


    Role getByAlias(String alias);

    /**
     * 根据用户ID获取角色列表
     *
     * @param userId
     * @return
     */
    List<Role> getListByUserId(String userId);

    /**
     * 判断角色是否存在。
     *
     * @param role
     * @return
     */
    boolean isRoleExist(Role role);

}
