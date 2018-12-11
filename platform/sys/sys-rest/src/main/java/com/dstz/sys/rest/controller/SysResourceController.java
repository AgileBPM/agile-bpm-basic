package com.dstz.sys.rest.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.GenericController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.core.manager.RelResourceManager;
import com.dstz.sys.core.manager.SubsystemManager;
import com.dstz.sys.core.manager.SysResourceManager;
import com.dstz.sys.core.model.Subsystem;
import com.dstz.sys.core.model.SysResource;
import com.github.pagehelper.Page;


/**
 * 子系统资源 控制器类
 */
@RestController
@RequestMapping("/sys/sysResource")
public class SysResourceController extends GenericController {
    @Resource
    SysResourceManager sysResourceManager;

    @Resource
    RelResourceManager relResourceManager;

    @Resource
    SubsystemManager subsystemManager;

    /**
     * 子系统资源列表(分页条件查询)数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception PageJson
     * @throws
     */
    @RequestMapping("listJson")
    public  PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        Page<SysResource> sysResourceList = (Page<SysResource>) sysResourceManager.query(queryFilter);
        return new PageResult(sysResourceList);
    }


    /**
     * 子系统资源明细页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception ModelAndView
     */
    @RequestMapping("getJson")
    public  void getJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        if (StringUtil.isEmpty(id)) {
            String parentId = RequestUtil.getString(request, "parentId");
            String sysytemId = RequestUtil.getString(request, "systemId");
            SysResource sysResource = new SysResource();
            sysResource.setSystemId(sysytemId);
            sysResource.setParentId(parentId);
            sysResource.setNewWindow(0);
            sysResource.setHasChildren(1);
            sysResource.setOpened(1);
            sysResource.setEnableMenu(1);
            writeSuccessData(response, sysResource);
        } else {
            SysResource sysResource = sysResourceManager.getByResId(id);
            writeSuccessData(response, sysResource);
        }
    }

    /**
     * 保存子系统资源信息
     *
     * @param request
     * @param response
     * @param sysResource
     * @throws Exception void
     * @throws
     */
    @RequestMapping("save")
    @CatchErr
    public ResultMsg<String> save(@RequestBody SysResource sysResource) throws Exception {
        String resultMsg = null;
        String id = sysResource.getId();
        boolean isExist = sysResourceManager.isExist(sysResource);
        if (isExist) {
           throw new BusinessMessage("资源已经存在,请修改重新添加!");
        }
        
        if (StringUtil.isEmpty(id)) {
            sysResource.setSn(System.currentTimeMillis());
            sysResourceManager.create(sysResource);
            sysResource.setCreateTime(new Date());
            resultMsg = "添加子系统资源成功";
        } else {
            sysResourceManager.update(sysResource);
            resultMsg = "更新子系统资源成功";
        }
        
        return getSuccessResult(sysResource.getId(), resultMsg);
        
    }

    /**
     * 批量删除子系统资源记录
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
            String id = RequestUtil.getString(request, "id");
            sysResourceManager.removeByResId(id);
            message = new ResultMsg(ResultMsg.SUCCESS, "删除子系统资源成功");
        } catch (Exception e) {
            message = new ResultMsg(ResultMsg.FAIL, "删除子系统资源失败");
        }
        writeResultMessage(response.getWriter(), message);
    }
	
	/*@RequestMapping("sysResourceEdit")
	public ModelAndView sysResourceEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		String id = request.getParameter("id");
		String parentsysResourceName = "子系统资源管理";
		if (id == null && parentId != null && !parentId.equals("0")) {
			// 新增时
			parentsysResourceName = sysResourceManager.get(parentId).getName();
		}
		return getAutoView().addObject("id", id)
				.addObject("parentId", parentId).addObject("parentsysResourceName", parentsysResourceName);
	}
	*/


    @RequestMapping("sysResourceGet")
    @CatchErr(value = "获取资源失败", write2response = true)
    public void sysResourceGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        SysResource sysResource = sysResourceManager.get(id);
        writeSuccessData(response, sysResource);
    }
	
/*	
	@RequestMapping("sysResourceList")
	public ModelAndView sysResourceList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Subsystem> subsystemList=subsystemManager.getAll();
		return getAutoView().addObject("subsystemList",subsystemList);
	}*/


    @RequestMapping("getTreeData")
    @CatchErr
    public List<SysResource> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String systemId = RequestUtil.getString(request, "systemId");
        Subsystem subsystem = subsystemManager.get(systemId);
        List<SysResource> groupList = getGroupTree(systemId);
        if (BeanUtils.isEmpty(groupList))
            groupList = new ArrayList<SysResource>();
        SysResource rootResource = new SysResource();
        rootResource.setName(subsystem.getName());
        rootResource.setId("0");
        rootResource.setSystemId(systemId); // 根节点
        rootResource.setHasChildren(1);
        groupList.add(rootResource);
        return groupList;
    }

    private List<SysResource> getGroupTree(String systemId) {
        List<SysResource> groupList = sysResourceManager.getBySystemId(systemId);
        return groupList;
    }

    @RequestMapping("moveResource")
    public void moveResource(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResultMsg message = null;
        try {
            String id = RequestUtil.getString(request, "id");
            SysResource sysResource = sysResourceManager.get(id);
            String parentId = RequestUtil.getString(request, "parentId");

            SysResource parentResource = sysResourceManager.get(parentId);
            if (parentResource != null) {
                parentResource.setHasChildren(1);
                sysResourceManager.update(parentResource);
            }
            sysResource.setParentId(parentId);
            sysResourceManager.update(sysResource);
            message = new ResultMsg(ResultMsg.SUCCESS, "移动资源成功");
        } catch (Exception e) {
            message = new ResultMsg(ResultMsg.FAIL, "移动资源失败");
        }
        writeResultMessage(response.getWriter(), message);
    }

}
