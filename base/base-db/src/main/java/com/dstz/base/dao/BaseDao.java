package com.dstz.base.dao;

import java.io.Serializable;
import java.util.List;

import com.dstz.base.api.query.QueryFilter;

/**
 * 数据库访问基础类
 */
public interface BaseDao<PK extends Serializable, T> {
    /**
     * 创建实体对象
     *
     * @param entity
     * @return
     */
    public void create(T entity);

    /**
     * 更新实体对象
     *
     * @param entity
     * @return
     */
    public Integer update(T entity);

    /**
     * 按实体ID删除对象
     *
     * @param entityId
     */
    public void remove(PK entityId);

    /**
     * 按实体ID获取实体
     *
     * @param entityId
     */
    public T get(PK entityId);

    /**
     * 查询实体对象
     *
     * @param queryFilter
     * @return
     */
    public List<T> query(QueryFilter queryFilter);

    /**
     * 取得所有查询对象
     *
     * @return
     */
    public List<T> query();

}
