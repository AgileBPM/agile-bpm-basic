package com.dstz.security.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.GenericController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.service.GroupService;
import com.dstz.security.util.SubSystemUtil;
import com.dstz.sys.api.model.system.ISubsystem;
import com.dstz.sys.api.model.system.ISysResource;
import com.dstz.sys.api.service.SysResourceService;
import com.dstz.sys.util.ContextUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户资源
 *
 * @author Jeff
 */
@RestController
@Api(description="用户资源信息")
public class UserResourceController extends GenericController {
    @Resource
    GroupService orgService;
    @Resource
    SysResourceService sysResourceService;


    @RequestMapping(value="userResource/userMsg",method={RequestMethod.POST,RequestMethod.GET})
    @CatchErr
    @ApiOperation(value = "用户信息",notes="获取用户信息，当前组织，可切换的组织岗位，当前系统，拥有的系统列表等信息")
    public ResultMsg<JSONObject> userMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ISubsystem> subsystemList = sysResourceService.getCuurentUserSystem();
        JSONObject mv = new JSONObject();
        if (BeanUtils.isEmpty(subsystemList)) {
            throw new BusinessException("当前用户尚未分配任何资源权限！");
        }

        String systemId = SubSystemUtil.getSystemId(request);
        ISubsystem currentSystem = null;
        //获取当前系统
        if (StringUtil.isNotEmpty(systemId)) {
            for (ISubsystem system : subsystemList) {
                if (system.getId().equals(systemId)) {
                    currentSystem = system;
                    break;
                }
            }
        } else {
            //获取默认系统
            currentSystem = sysResourceService.getDefaultSystem(ContextUtil.getCurrentUserId());
        }

        //没有之前使用的系统
        if (currentSystem == null) {
            currentSystem = subsystemList.get(0);
        }
        SubSystemUtil.setSystemId(request, response, currentSystem.getId());

        IGroup group = ContextUtil.getCurrentGroup();
        List<IGroup> orgList = orgService.getGroupsByGroupTypeUserId(GroupTypeConstant.ORG.key(), ContextUtil.getCurrentUserId());

        mv.put("currentEnviroment",AppUtil.getCtxEnvironment());
        mv.put("subsystemList", subsystemList);
        mv.put("currentSystem", currentSystem);
        mv.put("currentOrg", group);
        mv.put("orgList", orgList);
        mv.put("user", ContextUtil.getCurrentUser());
        mv.put("resourceList", getSysResource(request, response));
        return getSuccessResult(mv);
    }

    
    // 重新获取 userMsg
    @RequestMapping("userResource/changeSystem")
    public ResultMsg changeSystem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = RequestUtil.getString(request, "id");
        SubSystemUtil.setSystemId(request, response, id);

        return getSuccessResult("切换成功");
    }
    // 重新获取 userMsg
    @RequestMapping("userResource/changeOrg")
    public ResultMsg changeOrg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = RequestUtil.getString(request, "id");
        IGroup org = orgService.getById(GroupTypeConstant.ORG.key(), id);
        ContextUtil.setCurrentOrg(org);

        return getSuccessResult("切换成功");
    }

    @RequestMapping(value="userResource/getResTree",method={RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "用户菜单资源",notes="获取用户可访问的菜单资源")
    public List<ISysResource> getSysResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IUser user = ContextUtil.getCurrentUser();
        String systemId = SubSystemUtil.getSystemId(request);
        boolean isAdmin = ContextUtil.isAdmin(user);
        List<ISysResource> list = null;
        if (isAdmin) {
            list = sysResourceService.getBySystemId(systemId);
        } else {
            list = sysResourceService.getBySystemAndUser(systemId, user.getUserId());
        }

        return BeanUtils.listToTree(list);
    }
}
