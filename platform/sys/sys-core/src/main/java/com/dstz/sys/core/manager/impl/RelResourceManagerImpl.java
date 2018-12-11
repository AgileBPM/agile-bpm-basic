package com.dstz.sys.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.RelResourceDao;
import com.dstz.sys.core.manager.RelResourceManager;
import com.dstz.sys.core.model.RelResource;

/**
 * <pre>
 * 描述：关联资源 处理实现类
 * </pre>
 */
@Service("relResourceManager")
public class RelResourceManagerImpl extends BaseManager<String, RelResource> implements RelResourceManager {
    @Resource
    RelResourceDao relResourceDao;

    @Override
    public List<RelResource> getByResourceId(String resId) {
        return relResourceDao.getByResourceId(resId);
    }

    @Override
    public void removeByResId(String resId) {
        relResourceDao.removeByResId(resId);
    }

}
