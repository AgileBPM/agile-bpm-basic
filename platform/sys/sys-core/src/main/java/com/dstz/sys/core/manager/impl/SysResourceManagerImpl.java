package com.dstz.sys.core.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.RelResourceDao;
import com.dstz.sys.core.dao.SysResourceDao;
import com.dstz.sys.core.manager.SysResourceManager;
import com.dstz.sys.core.model.RelResource;
import com.dstz.sys.core.model.SysResource;

/**
 * <pre>
 * 描述：子系统资源 处理实现类
 * </pre>
 */
@Service("sysResourceManager")
public class SysResourceManagerImpl extends BaseManager<String, SysResource> implements SysResourceManager {
    @Resource
    SysResourceDao sysResourceDao;

    @Resource
    RelResourceDao relResourceDao;


    @Override
    public List<SysResource> getBySystemId(String id) {
        List<SysResource> list = sysResourceDao.getBySystemId(id);

        return list;
    }

    @Override
    public SysResource getByResId(String id) {
        SysResource sysResource = this.get(id);
        sysResource.setRelResources(relResourceDao.getByResourceId(id));
        return sysResource;
    }

    @Override
    public void create(SysResource sysResource) {
        String resId = IdUtil.getSuid();
        sysResource.setId(resId);
        //先删除
        relResourceDao.removeByResId(resId);
        //在添加
        List<RelResource> relResources = sysResource.getRelResources();
        for (RelResource relResource : relResources) {
            relResource.setId(IdUtil.getSuid());
            relResource.setResId(resId);
            relResourceDao.create(relResource);
        }
        super.create(sysResource);
    }

    @Override
    public void update(SysResource sysResource) {
        String resId = sysResource.getId();
        //先删除
        relResourceDao.removeByResId(resId);
        //在添加
        List<RelResource> relResources = sysResource.getRelResources();
        for (RelResource relResource : relResources) {
            relResource.setId(IdUtil.getSuid());
            relResource.setResId(resId);
            relResourceDao.create(relResource);
        }
        super.update(sysResource);
    }

    @Override
    public List<SysResource> getBySystemAndRole(String systemId, String roleId) {
        return sysResourceDao.getBySystemAndRole(systemId, roleId);
    }

    @Override
    public boolean isExist(SysResource resource) {
        boolean rtn = sysResourceDao.isExist(resource)>0;
        return rtn;
    }

    @Override
    public void removeByResId(String resId) {
        List<SysResource> list = getRecursionById(resId);
        for (SysResource resource : list) {
            this.remove(resource.getId());
        }
    }


    @Override
    public void remove(String entityId) {
        relResourceDao.removeByResId(entityId);
        super.remove(entityId);
    }

    @Override
    public List<SysResource> getRecursionById(String resId) {
        List<SysResource> list = new ArrayList<SysResource>();

        SysResource resource = this.get(resId);
        list.add(resource);

        List<SysResource> tmpList = sysResourceDao.getByParentId(resId);
        if (BeanUtils.isEmpty(tmpList)) return list;

        for (SysResource sysResource : tmpList) {
            recursion(sysResource, list);
        }
        return list;
    }

    private void recursion(SysResource sysResource, List<SysResource> list) {
        list.add(sysResource);
        List<SysResource> tmpList = sysResourceDao.getByParentId(sysResource.getId());
        if (BeanUtils.isEmpty(tmpList)) return;

        for (SysResource resource : tmpList) {
            recursion(resource, list);
        }
    }

    @Override
    public List<SysResource> getBySystemAndUser(String systemId, String userId) {
        List<SysResource> list = sysResourceDao.getBySystemAndUser(systemId, userId);
        return list;
    }


}
