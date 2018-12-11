package com.dstz.sys.core.manager.impl;

import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.ResRoleDao;
import com.dstz.sys.core.manager.ResRoleManager;
import com.dstz.sys.core.model.ResRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <pre>
 * 描述：角色资源分配 处理实现类
 * </pre>
 */
@Service("resRoleManager")
public class ResRoleManagerImpl extends BaseManager<String, ResRole> implements ResRoleManager {
    @Resource
    ResRoleDao resRoleDao;
    @Resource
    ICache iCache;

    public final String RESOURCE_URL = "RES_URL_";

    public final String RESOURCE_RES = "SYS_RES_";

    @Override
    public List<ResRole> getAllByRoleId(String roleId) {

        return resRoleDao.getByRoleId(roleId);
    }


    @Override
    public void assignResByRoleSys(String resIds, String systemId, String roleId) {
        resRoleDao.removeByRoleAndSystem(roleId, systemId);

        String[] aryRes = resIds.split(",");
        for (String resId : aryRes) {
            if ("0".equals(resId)) continue;
            ResRole resRole = new ResRole();
            resRole.setId(IdUtil.getSuid());
            resRole.setRoleId(roleId);
            resRole.setSystemId(systemId);
            resRole.setResId(resId);
            resRoleDao.create(resRole);
        }

    }

    @Override
    public Map<String, Set<String>> getResRoleBySystem(String systemId) {
        String resStr = RESOURCE_RES + systemId;
        if (iCache.containKey(resStr)) {
            return (Map<String, Set<String>>) iCache.getByKey(resStr);
        }

        List<ResRole> list = resRoleDao.getResRoleBySystemId(systemId);
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();

        for (ResRole res : list) {
            String resAlias = res.getResAlias();
            if (map.containsKey(resAlias)) {
                Set<String> set = map.get(resAlias);
                set.add(res.getRoleAlias());
            } else {
                Set<String> set = new HashSet<String>();
                set.add(res.getRoleAlias());
                map.put(resAlias, set);
            }
        }
        iCache.add(resStr, map);
        return map;
    }

    @Override
    public Map<String, Set<String>> getUrlRoleBySystem(String systemId) {
        String urlStr = RESOURCE_URL + systemId;
        if (iCache.containKey(urlStr)) {
            return (Map<String, Set<String>>) iCache.getByKey(urlStr);
        }

        List<ResRole> list = resRoleDao.getResRoleBySystemId(systemId);
        List<ResRole> urlList = resRoleDao.getUrlRoleBySystemId(systemId);

        urlList.addAll(list);

        Map<String, Set<String>> map = new HashMap<String, Set<String>>();

        for (ResRole res : list) {
            String url = res.getUrl();
            if (map.containsKey(url)) {
                Set<String> set = map.get(url);
                set.add(res.getRoleAlias());
            } else {
                Set<String> set = new HashSet<String>();
                set.add(res.getRoleAlias());
                map.put(url, set);
            }
        }
        //添加到缓存
        iCache.add(urlStr, map);
        return map;
    }

    @Override
    public void cleanResCache(String systemId) {
        String urlStr = RESOURCE_URL + systemId;
        String resStr = RESOURCE_RES + systemId;
        iCache.delByKey(urlStr);
        iCache.delByKey(resStr);
    }


}
