package com.dstz.org.rest.controller;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.github.pagehelper.Page;
import com.dstz.base.manager.Manager;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.UserManager;
import com.dstz.org.core.model.Group;
import com.dstz.org.core.model.OrgTree;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 组
 */
@RestController
@RequestMapping("/org/group")
public class GroupController extends BaseController<Group> {
    @Resource
    GroupManager groupManager;
    @Resource
    UserManager userManager;

    /**
     * 组织架构列表(分页条件查询)数据
     *
     * @param request
     * @param response
     * @return
     * @throws Exception PageJson
     * @throws
     */
    @RequestMapping("listJson")
    public PageResult listJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
        QueryFilter queryFilter = getQueryFilter(request);
        String parentId = request.getParameter("parentId");
        if (StringUtil.isNotEmpty(parentId)) {
            queryFilter.addFilter("parent_id_", parentId, QueryOP.EQUAL);
        }
        Page<Group> orgList = (Page<Group>) groupManager.query(queryFilter);
        return new PageResult(orgList);
    }


    @RequestMapping("isExist")
    public boolean isExist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String oldCode = RequestUtil.getString(request, "oldCode");
        String code = RequestUtil.getString(request, "key");
        if (oldCode.equals(code) && StringUtil.isNotEmpty(code)) {
            return false;
        }
        if (StringUtil.isNotEmpty(code)) {
            Group temp = groupManager.getByCode(code);
            return temp != null;
        }
        return false;
    }

    /**
     * 组织架构明细页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception ModelAndView
     */
    @RequestMapping("get")  @Override @CatchErr
    public ResultMsg<Group> get(@RequestParam String id) throws Exception {
        Group group = groupManager.get(id);
        if (group != null && !group.getParentId().equals("0")) {
            String parentOrgName = groupManager.get(group.getParentId()).getName();
            group.setParentOrgName(parentOrgName);
        }

       return getSuccessResult(group);
    }
    
    @RequestMapping("getTreeData")
    public List<OrgTree> getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<OrgTree> groupTreeList = getGroupTree();
        if (BeanUtils.isEmpty(groupTreeList)) {
            groupTreeList = new ArrayList<OrgTree>();
        }
        OrgTree rootGroup = new OrgTree();
        rootGroup.setName("行政组织");
        rootGroup.setId("0"); // 根节点
        groupTreeList.add(rootGroup);
        return groupTreeList;
    }

    private List<OrgTree> getGroupTree() {
        List<OrgTree> groupTreeList = new ArrayList<OrgTree>();
        List<Group> groupList = groupManager.getAll();
        for (Group group : groupList) {
            OrgTree groupTree = new OrgTree(group);
            groupTreeList.add(groupTree);
        }
        return groupTreeList;
    }

    @Override
    protected String getModelDesc() {
        return "组织";
    }
}
