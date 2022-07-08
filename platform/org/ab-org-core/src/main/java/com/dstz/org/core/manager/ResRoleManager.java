package com.dstz.org.core.manager;

import java.util.List;
import java.util.Set;

import com.dstz.base.manager.Manager;
import com.dstz.org.core.model.ResRole;

/**
 * <pre>
 * 描述：角色资源分配 处理接口
 * </pre>
 */
public interface ResRoleManager extends Manager<String, ResRole> {

    List<ResRole> getAllByRoleId(String roleId);

    /**
     * 分配角色资源。
     *
     * @param resIds
     * @param systemId
     * @param roleId
     */
    void assignResByRoleSys(String resIds, String systemId, String roleId);
    
    /**
     * 通过url 获取可访问的角色
     * @param url
     * @return
     */
	Set<String> getAccessRoleByUrl(String url);

}
