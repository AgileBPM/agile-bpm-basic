package com.dstz.sys.core.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.RelResource;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

/**
 * <pre>
 * 描述：关联资源 DAO接口
 * </pre>
 */
@MapperScan
public interface RelResourceDao extends BaseDao<String, RelResource> {

    List<RelResource> getByResourceId(String resId);

    /**
     * 根据资源id删除关联资源。
     *
     * @param resId
     */
    void removeByResId(String resId);
}
