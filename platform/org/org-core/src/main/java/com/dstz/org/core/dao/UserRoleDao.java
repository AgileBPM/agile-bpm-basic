package com.dstz.org.core.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.UserRole;

import java.util.List;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 * 描述：用户角色管理 DAO接口
 * </pre>
 */
@MapperScan
public interface UserRoleDao extends BaseDao<String, UserRole> {

    /**
     * 根据用户和角色id 查询 关联关系。
     *
     * @param roleId
     * @param userId
     * @return
     */
    UserRole getByRoleIdUserId(@Param("roleId")String roleId,@Param("userId") String userId);

	List<UserRole> queryByParams(@Param("roleId")String roleId, @Param("userId")String userId,@Param("alias") String alias);
}
