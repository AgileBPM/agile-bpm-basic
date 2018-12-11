package com.dstz.sys.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.RelResource;

import java.util.List;

/**
 * <pre>
 * 描述：关联资源 处理接口
 * </pre>
 */
public interface RelResourceManager extends Manager<String, RelResource> {
    /**
     * 根据资源ID获得其拥有的URL
     *
     * @return
     */
    List<RelResource> getByResourceId(String resId);

    /**
     * 根据资源ID删除关联资源。
     *
     * @param resId
     */
    void removeByResId(String resId);
}
