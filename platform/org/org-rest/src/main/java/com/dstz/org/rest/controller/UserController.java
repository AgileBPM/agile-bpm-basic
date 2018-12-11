package com.dstz.org.rest.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.encrypt.EncryptUtil;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.manager.GroupUserManager;
import com.dstz.org.core.manager.UserManager;
import com.dstz.org.core.model.GroupUser;
import com.dstz.org.core.model.User;
import com.dstz.sys.util.ContextUtil;
import com.github.pagehelper.Page;

/**
 * <pre>
 * 描述：用户表 控制器类
 * </pre>
 */
@RestController
@RequestMapping("/org/user")
public class UserController extends BaseController<User> {
    @Resource
    UserManager userManager;
    @Resource
    GroupUserManager orgUserManager;

    /**
     * 获取用户下的组织列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listUserOrgJson")
    public PageResult listUserOrgJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        String userId = RequestUtil.getString(request, "userId");
        queryFilter.addFilter("u.id_", userId, QueryOP.EQUAL);
        Page<User> userList = (Page<User>) userManager.queryOrgUser(queryFilter);
        return new PageResult(userList);
    }

    /**
     * 获取用户下的岗位列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("listUserPostJson")
    public PageResult listUserPostJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        String userId = RequestUtil.getString(request, "userId");
        queryFilter.addFilter("orguser.user_id_", userId, QueryOP.EQUAL);
        Page orgUserList = (Page) userManager.queryUserGroupRel(queryFilter);
        return new PageResult(orgUserList);
    }

    /**
     * 保存用户表信息
     *
     * @param request
     * @param response
     * @param user
     * @throws Exception void
     * @throws
     */
    @RequestMapping("save")
    @Override
    @CatchErr(write2response = true, value = "操作用户失败！")
    public ResultMsg<String> save( @RequestBody User user) throws Exception {
        String resultMsg = null;
        boolean isExist = userManager.isUserExist(user);
        if (isExist) {
            throw new BusinessMessage("用户在系统中已存在!");
        }

        String id = user.getId();
        if (StringUtil.isEmpty(id)) {
            user.setId(IdUtil.getSuid());
            String password = EncryptUtil.encryptSha256(user.getPassword());
            user.setPassword(password);
            //添加用户和组织的关系，默认为主关系。
            if (StringUtil.isNotEmpty(user.getGroupId())) {
                GroupUser orgUser = new GroupUser();
                orgUser.setId(IdUtil.getSuid());
                orgUser.setIsMaster(GroupUser.MASTER_YES);
                orgUser.setGroupId(user.getGroupId());
                orgUser.setUserId(user.getUserId());
                orgUserManager.create(orgUser);
            }
            userManager.create(user);
            resultMsg = "添加用户成功!";
        } else {
            userManager.update(user);
            resultMsg = "更新用户成功";
        }
        
        return getSuccessResult(user.getId(), resultMsg);
    }


    @RequestMapping("saveUserInfo")
    @CatchErr("更新失败")
    public void saveUserInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) throws Exception {
        userManager.update(user);
        writeSuccessData(response, "更新用户成功");
    }

    @RequestMapping("updateUserPsw")
    @CatchErr("更新密码失败")
    public void updateUserPsw(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oldPassWorld = RequestUtil.getString(request, "oldPassWorld");
        String newPassword = RequestUtil.getString(request, "newPassword");

        User user = userManager.get(ContextUtil.getCurrentUserId());
        if (!user.getPassword().equals(EncryptUtil.encryptSha256(oldPassWorld))) {
            throw new BusinessMessage("旧密码输入错误");
        }

        user.setPassword(EncryptUtil.encryptSha256(newPassword));
        userManager.update(user);
        writeSuccessResult(response, "更新密码成功");

    }


    @RequestMapping("getUserByGroupJson")
    public PageResult getUserByGroupJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        String orgId = RequestUtil.getString(request, "orgId");
        String relId = RequestUtil.getString(request, "relId");
        queryFilter.addFilter("orguser.org_id_", orgId, QueryOP.EQUAL);
        if (StringUtil.isNotEmpty(relId)) {
            queryFilter.addFilter("orguser.rel_id_", relId, QueryOP.EQUAL);
        }
        Page orgUserList = (Page) orgUserManager.getUserByGroup(queryFilter);
        return new PageResult(orgUserList);
    }

    @Override
    protected String getModelDesc() {
        return "用户";
    }
}
