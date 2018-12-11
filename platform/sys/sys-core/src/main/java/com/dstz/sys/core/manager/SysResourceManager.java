package com.dstz.sys.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.SysResource;

import java.util.List;

/**
 * <pre>
 * 描述：子系统资源 处理接口
 * </pre>
 */
public interface SysResourceManager extends Manager<String, SysResource> {

    /**
     * 根据子系统ID获取实体列表。
     */
    List<SysResource> getBySystemId(String id);

    /**
     * 根据资源ID获取资源对象，包括关联资源数据。
     *
     * @param id
     * @return
     */
    SysResource getByResId(String id);

    /**
     * 根据资源id获取包括自身的下级数据。
     *
     * @param resId
     * @return
     */
    List<SysResource> getRecursionById(String resId);

    /**
     * 根据系统和角色ID获取资源。
     *
     * @param systemId
     * @param roleId
     * @return
     */
    List<SysResource> getBySystemAndRole(String systemId, String roleId);

    /**
     * 判断资源是否存在。
     *
     * @param resource
     * @return
     */
    boolean isExist(SysResource resource);

    /**
     * 根据资源id递归删除资源数据。
     *
     * @param resId
     */
    void removeByResId(String resId);

    /**
     * 根据系统id和用户id获取资源。
     *
     * @param systemId
     * @param userId
     * @return
     */
    List<SysResource> getBySystemAndUser(String systemId, String userId);
}
