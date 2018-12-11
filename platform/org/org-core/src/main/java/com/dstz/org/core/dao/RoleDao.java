package com.dstz.org.core.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.Role;

import java.util.List;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 * 描述：角色管理 DAO接口
 * </pre>
 */
@MapperScan
public interface RoleDao extends BaseDao<String, Role> {
    Role getByAlias(String alias);

    List<Role> getList(@Param("userId") String userId,@Param("account") String account);

    /**
     * 判断角色系统中是否存在。
     *
     * @param role
     * @return
     */
    Integer isRoleExist(Role role);
}
