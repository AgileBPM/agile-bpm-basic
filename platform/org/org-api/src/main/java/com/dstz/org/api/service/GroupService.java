package com.dstz.org.api.service;

import java.util.List;
import java.util.Map;

import com.dstz.org.api.model.IGroup;

/**
 * 描述：用户与组服务接口
 * <pre>
 * </pre>
 */
public interface GroupService {


    /**
     * 根据用户ID和组类别获取相关的组。
     *
     * @param groupType 用户组类型
     * @param userId    用户ID
     * @return
     */
    List<? extends IGroup> getGroupsByGroupTypeUserId(String groupType, String userId);

    /**
     * 根据用户账号获取用户当前所在的组。
     *
     * @param account 用户帐号
     * @return 返回一个Map，键为维度类型，值为组列表。
     */
    Map<String, List<? extends IGroup>> getAllGroupByAccount(String account);


    /**
     * 获取用户所在的所有组织。
     *
     * @param userId 用户ID
     * @return 返回一个Map，键为维度类型，值为组列表。
     */
    Map<String, List<? extends IGroup>> getAllGroupByUserId(String userId);


    /**
     * 根据用户获取用户所属的组。
     *
     * @param userId
     * @return
     */
    List<? extends IGroup> getGroupsByUserId(String userId);


    /**
     * 根据组织ID和类型获取组织对象。
     *
     * @param groupType
     * @param groupId
     * @return
     */
    IGroup getById(String groupType, String groupId);


    /**
     * 根据组织ID和类型获取组织对象。
     *
     * @param groupType
     * @param code
     * @return
     */
    IGroup getByCode(String groupType, String code);


    IGroup getMainGroup(String userId);


}
