package com.dstz.org.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.User;

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
     * 判断用户是否存在。
     */
    Integer isUserExist(User user);


	List<User> getUserListByRelation(@Param("relationId")String relId, @Param("relationType")String type);
	
	List<User> getUserListByPost(@Param("roleId")String roleId, @Param("groupId")String groupId);
	
}
