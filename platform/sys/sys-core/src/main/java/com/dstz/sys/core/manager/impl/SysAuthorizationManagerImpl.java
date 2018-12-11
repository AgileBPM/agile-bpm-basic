package com.dstz.sys.core.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.service.GroupService;
import com.dstz.sys.api.constant.RightsObjectConstants;
import com.dstz.sys.core.dao.SysAuthorizationDao;
import com.dstz.sys.core.manager.SysAuthorizationManager;
import com.dstz.sys.core.model.SysAuthorization;
import com.dstz.sys.util.ContextUtil;


@Service("sysAuthorizationManager")
public class SysAuthorizationManagerImpl extends BaseManager<String, SysAuthorization> implements SysAuthorizationManager {

    @Resource
    private SysAuthorizationDao sysAuthorizationDao;

    @Autowired(required = false)
    private GroupService userGroupService;

    /**
     * 获取用户的权限
     * type-vale
     */
    @Override
    public Set<String> getUserRights(String userId) {
        List<IGroup> list = userGroupService.getGroupsByUserId(userId);

        Set<String> rights = new HashSet<String>();
        rights.add(String.format("%s-%s", userId, SysAuthorization.RIGHT_TYPE_USER));
        rights.add(String.format("%s-%s", SysAuthorization.RIGHT_TYPE_USER, SysAuthorization.RIGHT_TYPE_ALL));


        if (BeanUtils.isEmpty(list)) return rights;

        for (IGroup group : list) {
            rights.add(String.format("%s-%s", group.getGroupId(), group.getGroupType()));
        }

        return rights;
    }

    /**
     * 获取用户的权限sql, sql中需要使用 ${rightsSql},若不使用可以使用 Set rights, 自行拼装
     *
     * @param targetKey    为permissionTarget的 字段ID
     * @param targetObject 为授权对象名字
     * @rightsSql = inner join sys_authorization rights on id_ = rights.rights_id_ and rights_permission_code_ in ( id-type,groupid-type )
     */
    @Override
    public Map<String, Object> getUserRightsSql(RightsObjectConstants rightsObject, String userId, String targetKey) {
        if (StringUtil.isEmpty(targetKey)) {
            targetKey = "id_";
        }

        StringBuilder sb = new StringBuilder();

        Set<String> rights = getUserRights(userId);

        for (String r : rights) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append("'").append(r).append("'");
        }
        sb.insert(0, "inner join sys_authorization rights on " + targetKey + " = rights.rights_target_  and  rights.rights_object_ ='" + rightsObject.key() + "' and rights_permission_code_ in ( ");
        sb.append(")");

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("rightsSql", sb.toString());

        param.put("rights", rights);

        return param;
    }


    @Override
    public List<SysAuthorization> getByTarget(RightsObjectConstants rightsObject, String rightsTarget) {
        return sysAuthorizationDao.getByTarget(rightsObject.key(), rightsTarget);
    }


    @Override
    public void createAll(List<SysAuthorization> sysAuthorizationList, String targetId, String targetObject) {
        sysAuthorizationDao.deleteByTarget(targetObject, targetId);

        for (SysAuthorization authorization : sysAuthorizationList) {
            authorization.setRightsPermissionCode(String.format("%s-%s", authorization.getRightsIdentity(), authorization.getRightsType()));
            if (StringUtil.isEmpty(authorization.getRightsObject())) {
                authorization.setRightsObject(targetObject);
            }
            authorization.setRightsCreateBy(ContextUtil.getCurrentUserId());
            authorization.setRightsCreateTime(new Date());

            sysAuthorizationDao.create(authorization);
        }

    }


}
