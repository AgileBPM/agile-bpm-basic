package com.dstz.sys.rest.controller;


import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.github.pagehelper.Page;
import com.dstz.base.rest.GenericController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.core.manager.ResRoleManager;
import com.dstz.sys.core.manager.SubsystemManager;
import com.dstz.sys.core.manager.SysResourceManager;
import com.dstz.sys.core.model.ResRole;
import com.dstz.sys.core.model.SysResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 * 描述：角色资源分配 控制器类
 * </pre>
 */
@Controller
@RequestMapping("/sys/resRole")
public class SysResRoleController extends GenericController {
    @Resource
    ResRoleManager resRoleManager;

    @Resource
    SysResourceManager sysResourceManager;

    @Resource
    SubsystemManager subsystemManager;

    /**
     * 角色资源分配列表(分页条件查询)数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception PageJson
     * @throws
     */
    @RequestMapping("listJson")
    public @ResponseBody
    PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        Page<ResRole> resRoleList = (Page<ResRole>) resRoleManager.query(queryFilter);
        return new PageResult(resRoleList);
    }


    /**
     * 角色资源分配明细页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception ModelAndView
     */
    @RequestMapping("getJson")
    public @ResponseBody
    ResRole getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        if (StringUtil.isEmpty(id)) {
            return null;
        }
        ResRole resRole = resRoleManager.get(id);
        return resRole;
    }

    /**
     * 保存角色资源分配信息
     *
     * @param request
     * @param response
     * @param resRole
     * @throws Exception void
     * @throws
     */
    @RequestMapping("save")
    @CatchErr("对角色资源分配操作失败")
    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String roleId = RequestUtil.getString(request, "roleId");
        String systemId = RequestUtil.getString(request, "systemId");
        String resIds = RequestUtil.getString(request, "resIds");
        
        resRoleManager.assignResByRoleSys(resIds, systemId, roleId);
        writeSuccessResult(response, " 添加角色资源分配成功");
    }

    /**
     * 批量删除角色资源分配记录
     *
     * @param request
     * @param response
     * @throws Exception void
     * @throws
     */
    @RequestMapping("remove")
    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultMsg message = null;
        try {
            String[] aryIds = RequestUtil.getStringAryByStr(request, "id");
            resRoleManager.removeByIds(aryIds);
            message = new ResultMsg(ResultMsg.SUCCESS, "删除角色资源分配成功");
        } catch (Exception e) {
            message = new ResultMsg(ResultMsg.FAIL, "删除角色资源分配失败");
        }
        writeResultMessage(response.getWriter(), message);
    }


    @RequestMapping("getTreeData")
    @ResponseBody
    public List<SysResource> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String roleId = RequestUtil.getString(request, "roleId");
        String systemId = RequestUtil.getString(request, "systemId");
        List<SysResource> roleResourceList = sysResourceManager.getBySystemAndRole(systemId, roleId);
        List<SysResource> resourceList = sysResourceManager.getBySystemId(systemId);
        for (SysResource sysResource : resourceList) {
            if (roleResourceList.contains(sysResource)) {
                sysResource.setChecked(true);
            }
        }
        if (BeanUtils.isEmpty(resourceList))
            resourceList = new ArrayList<SysResource>();

        SysResource rootRes = new SysResource();
        String rootName = subsystemManager.get(systemId).getName();
        rootRes.setName(rootName);
        rootRes.setId("0");
        rootRes.setSystemId(systemId); // 根节点
        resourceList.add(rootRes);
        return resourceList;
    }
}
