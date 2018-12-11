package com.dstz.sys.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.ResRole;

/**
 * <pre>
 * 描述：角色资源分配 DAO接口
 * </pre>
 */
@MapperScan
public interface ResRoleDao extends BaseDao<String, ResRole> {

    List<ResRole> getByRoleId(String roleId);

    void removeByRoleAndSystem(@Param("roleId")String roleId,@Param("systemId")String systemId);

    /**
     * 根据系统id获取关联的角色和URL资源。
     *
     * @param systemId
     * @return
     */
    List<ResRole> getUrlRoleBySystemId(String systemId);

    /**
     * 根据系统Id获取资源和角色的映射关系。
     *
     * @param systemId
     * @return
     */
    List<ResRole> getResRoleBySystemId(String systemId);
}
