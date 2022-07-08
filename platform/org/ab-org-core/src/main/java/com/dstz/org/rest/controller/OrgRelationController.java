package com.dstz.org.rest.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessError;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.org.core.constant.RelationTypeConstant;
import com.dstz.org.core.manager.OrgRelationManager;
import com.dstz.org.core.model.OrgRelation;
import com.github.pagehelper.Page;

import cn.hutool.core.collection.CollectionUtil;
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
    @RequestMapping("queryGroupUser")@CatchErr
    public PageResult queryGroupUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String groupId = RequestUtil.getRQString(request, "groupId", "查询 组用户 组ID 不能为空");
    	//查询 岗位 和 用户组的关系
    	QueryFilter filter = getQueryFilter(request);
    	filter.addFilter("tgroup.id_", groupId, QueryOP.EQUAL);
    	filter.addFilter("relation.type_", RelationTypeConstant.POST.getKey(), QueryOP.NOT_EQUAL);
    	
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
    
    @RequestMapping("saveGroupUserRel")
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
    
    @RequestMapping("saveRoleUsers")
    @CatchErr
    public ResultMsg<String> saveRoleUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String  roleId = RequestUtil.getString(request, "roleId");
        String [] userIds = RequestUtil.getStringAryByStr(request, "userIds");
        if(ArrayUtil.isEmpty(userIds)) {
        	throw new BusinessError("请选择用户！");
        }
        
       int i =  orgRelationManager.saveRoleUsers(roleId,userIds);

        return getSuccessResult(i +"条用户角色添加成功");
    }
    
    /**
     * 查询 组用户
     */
    @RequestMapping("roleJson")
    public PageResult roleJson(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String roleId = RequestUtil.getRQString(request, "roleId", "查询 角色ID不能为空");
    	//查询 岗位 和 用户组的关系
    	QueryFilter filter = getQueryFilter(request);
    	filter.addFilter("role.id_", roleId, QueryOP.EQUAL);
    	filter.addFilter("relation.type_", RelationTypeConstant.POST.getKey(), QueryOP.NOT_EQUAL);
    	
        Page<OrgRelation> pageList = (Page<OrgRelation>) orgRelationManager.query(filter);
        return new PageResult(pageList);
    }
    
    
    /**
     * 删除 角色、删除组织、删除岗位前进行校验
     * 删除角色 校验 岗位、岗位人员、角色人员是否存在
     * 删除组织、 校验岗位、组织人员
     * 删除岗位  校验岗位人员
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("removeCheck")
    @CatchErr
    public ResultMsg<String> removeCheck(HttpServletRequest request) throws Exception {
    	String roleId = RequestUtil.getString(request, "roleId");
    	String groupId = RequestUtil.getString(request, "groupId");
    	
        orgRelationManager.removeCheck(groupId,roleId);
        
        return getSuccessResult();
    }
}
