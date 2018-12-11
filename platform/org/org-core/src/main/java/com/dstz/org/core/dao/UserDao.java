package com.dstz.org.core.dao;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.User;

import java.util.List;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 * 描述：用户表 DAO接口
 * </pre>
 */
@MapperScan
public interface UserDao extends BaseDao<String, User> {
    /**
     * 根据Account取定义对象。
     *
     * @param code
     * @return
     */
    User getByAccount(String account);

    /**
     * 不含用户组织关系
     *
     * @param queryFilter
     * @return
     */
    List<User> queryOrgUser(QueryFilter queryFilter);

    /**
     * 根据岗位编码获取用户列表
     *
     * @param relCode
     * @return
     */
    List<User> getListByRelCode(String relCode);

    /**
     * 根据角色获取用户列表
     *
     * @param roleId
     * @return
     */
    List<User> getUserListByRole(@Param("roleId")String roleId,@Param("roleCode")String roleCode);

    /**
     * 判断用户是否存在。
     *
     * @param user
     * @return
     */
    Integer isUserExist(User user);

    List getUserListByRelId(String relId);

	List queryUserGroupRel(QueryFilter queryFilter);

	List<User> getUserListByOrgId(String orgId);
}
