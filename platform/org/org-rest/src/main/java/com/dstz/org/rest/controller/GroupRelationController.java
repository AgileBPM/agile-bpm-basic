package com.dstz.org.rest.controller;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.github.pagehelper.Page;
import com.dstz.base.manager.Manager;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.manager.GroupRelationManager;
import com.dstz.org.core.model.GroupRelation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 组织关联关系 控制器类
 */
@RestController
@RequestMapping("/org/groupRelation")
public class GroupRelationController extends BaseController<GroupRelation> {
    @Resource
    GroupRelationManager groupRelManager;

    /**
     * 组织关联关系列表(分页条件查询)数据
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
        String groupId = RequestUtil.getString(request, "groupId");
        if (StringUtil.isNotEmpty(groupId)) {
            queryFilter.addParamsFilter("orgId", groupId);
        }
        Page<GroupRelation> orgRelList = (Page<GroupRelation>) groupRelManager.queryInfoList(queryFilter);
        return new PageResult(orgRelList);
    }


    /**
     * 保存组织关联关系信息
     */
    @RequestMapping("save")
    @CatchErr
    @Override
    public ResultMsg<String> save(@RequestBody GroupRelation orgRel) throws Exception {

        if (StringUtil.isEmpty(orgRel.getId())) {
            GroupRelation relation = groupRelManager.getByCode(orgRel.getGroupCode());
            if (relation != null) {
                throw new BusinessMessage("岗位编码已经存在！");
            }
        }
       return super.save(orgRel);
    }


    @RequestMapping("isExist")
    public boolean isExist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        String code = RequestUtil.getString(request, "key");
        if (StringUtil.isNotEmpty(id))
            return false;
        if (StringUtil.isNotEmpty(code)) {
            GroupRelation temp = groupRelManager.getByCode(code);
            return temp != null;
        }
        return false;
    }


    @RequestMapping("getByGroupId")
    @CatchErr
    public void getByGroupId(HttpServletRequest request, HttpServletResponse response, String groupId) throws Exception {
        List<GroupRelation> list = groupRelManager.getListByGroupId(groupId);
        writeSuccessData(response, list);
    }

    @Override
    protected String getModelDesc() {
        return "组关系";
    }
}
