package com.dstz.org.rest.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessError;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.org.core.constant.RelationTypeConstant;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.model.OrgRelation;
import com.github.pagehelper.Page;

import cn.hutool.core.util.ArrayUtil;


/**
 * 用户组织关系 控制器类<br/>
 * @author  Jeff
 * </pre>
 */
@RestController
@RequestMapping("/org/orgRelation")
public class OrgRelationController extends BaseController<OrgRelation>{
	@Resource
	OrgRelationManager orgRelationManager;
	
	
	@Override
	protected String getModelDesc() {
		return "用户组织关系";
	}
	
	 /**
     * 查询 组用户
     */
    @RequestMapping("queryGroupUser")
    public PageResult queryGroupUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String groupId = RequestUtil.getRQString(request, "groupId", "查询 组用户 组ID 不能为空");
    	//查询 岗位 和 用户组的关系
    	QueryFilter filter = new DefaultQueryFilter();
    	filter.addFilter("tgroup.id_", groupId, QueryOP.EQUAL);
    	filter.addFilter("relation.type_", RelationTypeConstant.POST.getKey(), QueryOP.NOT_IN);
    	
        Page<OrgRelation> pageList = (Page<OrgRelation>) orgRelationManager.query(filter);
        return new PageResult(pageList);
    }
    
    @RequestMapping("setMaster")
    @CatchErr
    public ResultMsg<String> setMaster(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getString(request, "id");
        orgRelationManager.updateUserGroupRelationIsMaster(id);
        
        return getSuccessResult("设置用户主组织成功!");
    }
    
    
    @RequestMapping("disable")
    @CatchErr
    public ResultMsg<String> disable(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getRQString(request, "id");
        orgRelationManager.changeStatus(id,0);
        
        return getSuccessResult("禁用成功!");
    }
    
    @RequestMapping("enable")
    @CatchErr
    public ResultMsg<String> enable(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = RequestUtil.getRQString(request, "id");
        orgRelationManager.changeStatus(id,1);

        return getSuccessResult("启用成功!");
    }
    
    @RequestMapping("getGroupPost")
    @CatchErr
    public ResultMsg<List<OrgRelation>> getPostByGroupId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String groupId = RequestUtil.getRQString(request, "groupId");
        List<OrgRelation> orgRelations = orgRelationManager.getGroupPost(groupId);

        return getSuccessResult(orgRelations);
    }
    
    @RequestMapping("getGroupPost")
    @CatchErr
    public ResultMsg<String> saveGroupUserRel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String groupId = RequestUtil.getRQString(request, "groupId");
        String [] roleIds = RequestUtil.getStringAryByStr(request, "roleIds");
        String [] userIds = RequestUtil.getStringAryByStr(request, "userIds");
        if(ArrayUtil.isEmpty(userIds)) {
        	throw new BusinessError("请选择用户");
        }
        
        orgRelationManager.saveUserGroupRelation(groupId,roleIds,userIds);

        return getSuccessResult("添加成功");
    }
    
    
}
