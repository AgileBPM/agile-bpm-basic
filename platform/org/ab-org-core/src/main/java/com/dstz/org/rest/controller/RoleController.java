package com.dstz.org.rest.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.manager.RoleManager;
import com.dstz.org.core.model.Role;

/**
 * 角色管理 控制器类
 */
@RestController
@RequestMapping("/org/role")
public class RoleController extends BaseController<Role> {
    @Resource
    RoleManager roleManager;
    @Resource
    OrgRelationManager orgRelationMananger;

    @Override
    protected String getModelDesc() {
        return "角色";
    }

    @Override
    @CatchErr
    public ResultMsg<String> save( @RequestBody Role role) throws Exception {
        if (StringUtil.isEmpty(role.getId())) {
            boolean isExist = roleManager.isRoleExist(role);
            if (isExist) {
                throw new BusinessMessage("角色在系统中已存在!");
            }
        }
       return super.save(role);
    }
    
    /**
     * 移除 角色 权限
     */
    @Override @CatchErr
    public ResultMsg<String> remove(String id) throws Exception {
    	orgRelationMananger.removeCheck(null, id);
     	return super.remove(id);
    }

}
