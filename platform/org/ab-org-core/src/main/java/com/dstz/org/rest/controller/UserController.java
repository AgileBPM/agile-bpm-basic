package com.dstz.org.rest.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.aop.annotion.ParamValidate;
import com.dstz.base.api.exception.BusinessError;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.encrypt.EncryptUtil;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.manager.UserManager;
import com.dstz.org.core.model.User;
import com.dstz.sys.api.constant.EnvironmentConstant;
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
    
    /**
     * 保存用户表信息，后端添加实体校验
     */
    @RequestMapping("save")
    @Override
    @CatchErr("操作用户失败！")@ParamValidate
    public ResultMsg<String> save(@RequestBody User user) throws Exception {
        if (userManager.isUserExist(user)) throw new BusinessMessage("用户在系统中已存在!");
        
        userManager.saveUserInfo(user);
        return getSuccessResult(user.getId(), "保存成功");
    }

    @RequestMapping("updateUserPassWorld")
    @CatchErr("更新密码失败")
    public ResultMsg<String> updateUserPsw(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oldPassWorld = RequestUtil.getRQString(request, "oldPassword","旧密码必填");
        String newPassword = RequestUtil.getRQString(request, "newPassword","新密码必填");
        String userId = RequestUtil.getRQString(request, "id","当前用户ID");
        
        if(!userId.equals(ContextUtil.getCurrentUserId())) {
        	throw new BusinessException("禁止修改他人密码！");
        }
        
        if(AppUtil.getCtxEnvironment().contains(EnvironmentConstant.SIT.key())) {
       	 throw new BusinessError("测试环境为了防止不法之徒恶意破坏演示数据，禁止修改密码！<br/>您的访问信息已经被我们统计！");
        }
        
        User user = userManager.get(ContextUtil.getCurrentUserId());
        if (!user.getPassword().equals(EncryptUtil.encryptSha256(oldPassWorld))) {
            throw new BusinessMessage("旧密码输入错误");
        }

        user.setPassword(EncryptUtil.encryptSha256(newPassword));
        userManager.update(user);
        return getSuccessResult("更新密码成功");

    }
    
    /**
     * 批量删除
     */
    @Override
    @RequestMapping("remove")
    @CatchErr
    public ResultMsg<String> remove(@RequestParam String id) throws Exception {
    	if(AppUtil.getCtxEnvironment().contains(EnvironmentConstant.SIT.key())) {
          	 throw new BusinessError("测试环境为了防止不法之徒恶意破坏演示数据，禁止删除用户！<br/>您的访问信息已经被我们统计！");
        }
    	
    	return super.remove(id);
    }
    
    

    @Override
    protected String getModelDesc() {
        return "用户";
    }
}
