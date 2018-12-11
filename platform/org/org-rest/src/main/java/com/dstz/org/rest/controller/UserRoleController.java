package com.dstz.org.rest.controller;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.github.pagehelper.Page;
import com.dstz.base.manager.Manager;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.manager.UserRoleManager;
import com.dstz.org.core.model.UserRole;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/org/userRole")
public class UserRoleController extends BaseController<UserRole> {
    @Resource
    UserRoleManager userRoleManager;

    /**
     * 用户角色管理列表(分页条件查询)数据
     */
    @RequestMapping("listJson")
    public PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String roleId = RequestUtil.getString(request, "roleId");
        String userId = RequestUtil.getString(request, "userId");
        QueryFilter queryFilter = getQueryFilter(request);
        if (StringUtil.isNotEmpty(roleId)) {
            queryFilter.addParamsFilter("roleId", roleId);
        }
        if (StringUtil.isNotEmpty(userId)) {
            queryFilter.addParamsFilter("userId", userId);
        }
        Page<UserRole> userRoleList = (Page<UserRole>) userRoleManager.query(queryFilter);
        return new PageResult(userRoleList);
    }

    /**
     * 保存角色下的用户
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("saveRoleUsers")
    public void saveRoleUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String roleId = RequestUtil.getString(request, "roleId");
        String[] aryIds = RequestUtil.getStringAryByStr(request, "userId");

        if (StringUtil.isNotEmpty(roleId)) {
            for (String userId : aryIds) {
                addUserRole(userId, roleId);
            }
        }
        writeSuccessResult(response, "添加成功");
    }

    private void addUserRole(String userId, String roleId) {
        if (userRoleManager.getByRoleIdUserId(roleId, userId) != null) return;

        UserRole userRole = new UserRole();
        userRole.setId(IdUtil.getSuid());
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleManager.create(userRole);
    }

    /**
     * 保存用户角色
     */
    @RequestMapping("saveUserRole")
    @CatchErr
    public void saveUserRole(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = RequestUtil.getString(request, "userId");
        String[] aryIds = RequestUtil.getStringAryByStr(request, "groupIds");

        if (StringUtil.isNotEmpty(userId)) {
            for (String roleId : aryIds) {
                addUserRole(userId, roleId);
            }
        }

        writeSuccessResult(response, "添加成功");
    }

    @Override
    protected String getModelDesc() {
        return "用户角色";
    }
}
