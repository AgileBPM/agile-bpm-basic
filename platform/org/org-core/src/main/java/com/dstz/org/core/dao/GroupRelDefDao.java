package com.dstz.org.core.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.org.core.model.GroupRelDef;

/**
 * <pre>
 * 描述：组织关系定义 DAO接口
 * </pre>
 */
@MapperScan
public interface GroupRelDefDao extends BaseDao<String, GroupRelDef> {
    public GroupRelDef getByCode(String code);
}
