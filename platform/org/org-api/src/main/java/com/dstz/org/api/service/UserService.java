package com.dstz.org.api.service;

import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.IUserRole;

import java.util.List;

/**
 * <pre>
 * 描述：用户服务接口类
 * </pre>
 */
public interface UserService {

    /**
     * 根据用户ID获取用户的对象。
     *
     * @param userId 用户ID
     * @return
     */
    IUser getUserById(String userId);


    /**
     * 根据用户帐号获取用户对象。
     *
     * @param account
     * @return
     */
    IUser getUserByAccount(String account);


    /**
     * 根据组织id和组织类型获取用户列表。
     *
     * @param groupId   组织列表
     * @param groupType 组织类型
     * @return
     */
    List<IUser> getUserListByGroup(String groupType, String groupId);
    
    /**
     * 获取用户的角色关系
     * @param userId
     * @return
     */
	List<IUserRole> getUserRole(String userId);


}
