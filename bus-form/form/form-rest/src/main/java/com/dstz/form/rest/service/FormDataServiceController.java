package com.dstz.form.rest.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.dao.CommonDao;
import com.dstz.base.db.datasource.DbContextHolder;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.rest.ControllerTools;
import com.dstz.bus.api.constant.BusinessPermissionObjType;
import com.dstz.bus.api.model.IBusinessObject;
import com.dstz.bus.api.model.IBusinessPermission;
import com.dstz.bus.api.model.IBusinessTable;
import com.dstz.bus.api.service.IBusinessDataService;
import com.dstz.bus.api.service.IBusinessObjectService;
import com.dstz.bus.api.service.IBusinessPermissionService;
import com.dstz.form.api.constant.FormStatusCode;
import com.dstz.form.manager.FormDefManager;
import com.dstz.form.model.FormDef;
import com.dstz.form.model.FormDefData;
import com.dstz.form.service.FormDefDataService;
import com.dstz.sys.api.model.ISysDataSource;
import com.dstz.sys.api.service.ISysDataSourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 业务表单服务接口
 * @author jeff
 * @Time 2019-6-5 20:55:02
 */
@RestController
@RequestMapping("/form/formDataService/")
@Api(description="在线业务表单服务接口")
public class FormDataServiceController extends ControllerTools{
	@Autowired
	FormDefDataService formDefDataService;
	@Autowired
	IBusinessDataService businessDataService;
	@Autowired
	IBusinessObjectService businessObjectService;
	@Autowired
	ISysDataSourceService sysDataSourceService;
	@Autowired
	CommonDao<?> commonDao;
	@Autowired
	IBusinessPermissionService businessPermissionService;
	@Autowired
	FormDefManager formDefManager;

	/**
	 * <pre>
	 * 获取FormDefData的后端
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@GetMapping("getData/{formKey}/{id}")
	@ApiOperation(value = "获取业务表单的数据", notes = "获取业务表单的相关数据：表单业务数据、表单字段权限、表单初始化数据、表单HTML源码")
	@CatchErr(value = "获取FormDefData异常")
	public ResultMsg<FormDefData> getFormDefData(
			@PathVariable("formKey") @ApiParam(value = "表单key", required = true) String formKey,
			@PathVariable("id") @ApiParam(value = "业务数据主键", required = false) String id){
		
		FormDefData formDefData = formDefDataService.getByFormDefKey(formKey, id);
		
		return new ResultMsg<> (formDefData);
	}

	/**
	 * <pre>
	 * 保存formDef中的data数据
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param data
	 * @throws Exception
	 */
	@PostMapping("saveData/{formKey}")
	@ApiOperation(value = "保存业务表单的数据", notes = "把业务表单的业务数据持久化")
	@CatchErr(write2response = true, value = "保存formDef中的data数据异常")
	public ResultMsg<String> saveData( 
			@PathVariable("formKey") @ApiParam(value = "表单key", required = true) String formKey,
			@RequestBody @ApiParam(value = "业务数据JSON", required = true) JSONObject data) throws Exception {
		FormDef formDef = formDefManager.getByKey(formKey);
		IBusinessPermission permission = businessPermissionService.getByObjTypeAndObjVal(BusinessPermissionObjType.FORM.getKey(), formKey, formDef.getBoKey(), true);
		businessDataService.saveFormDefData(data, permission);
		
		return new ResultMsg<>(BaseStatusCode.SUCCESS,"保存数据成功");
	}

	/**
	 * <pre>
	 * 获取bo的数据列表
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@PostMapping("getList/{boKey}")
	@CatchErr(write2response = true, value = "获取对话框的列表数据失败")
	@ApiOperation(value = "获取bo的数据分页列表", notes = "通过boKey 获取改业务对象下所有的业务数据 -分页，返回 JSON列表")
	@ApiImplicitParams({ @ApiImplicitParam(paramType = "form", dataType = "String", name = "offset", value = "offset"),
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "limit", value = "分页大小"),
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "sort", value = "排序字段"),
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "order", value = "order", defaultValue = "ASC"),
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "filter$VEQ", value = "其他字段过滤参数") })
	public PageResult getList(HttpServletRequest request, 
			@ApiParam(value = "BoKey", required = true) @PathVariable(value = "boKey") String boKey) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		// 页面来的参数
		IBusinessObject businessObject = businessObjectService.getFilledByKey(boKey);
		IBusinessTable businessTable = businessObject.getRelation().getTable();
		ISysDataSource sysDataSource = sysDataSourceService.getByKey(businessTable.getDsKey());
		// 切换数据源
		DbContextHolder.setDataSource(sysDataSource.getKey(), sysDataSource.getDbType());
		String sql = "select * from " + businessTable.getName();
		List<?> list = commonDao.queryForListPage(sql, queryFilter);
		return new PageResult(list);
	}

	@GetMapping("removeData/{formKey}/{id}")
	@CatchErr(write2response = true, value = "删除formDef中的data数据异常")
	@ApiOperation(value = "通过业务主键删除业务数据", notes = "删除某个业务对象下某一条业务数据，通过表单key,主键ID")
	public ResultMsg<String> removeData(@PathVariable(value = "formKey", required = false) @ApiParam(value = "表单KEY", required = true) String formKey,
					@PathVariable(value = "id", required = false) @ApiParam(value = "主键ID", required = true)  String id) throws Exception {
		if (StringUtil.isEmpty(formKey)) {
			throw new BusinessException("formKey 不能为空", FormStatusCode.PARAM_ILLEGAL);
		}
		if (StringUtil.isEmpty(id)) {
			throw new BusinessException("ID 不能为空", FormStatusCode.PARAM_ILLEGAL);
		}

		FormDef formDef = formDefManager.getByKey(formKey);
		String boKey = formDef.getBoKey();

		IBusinessPermission permission = businessPermissionService.getByObjTypeAndObjVal(BusinessPermissionObjType.FORM.getKey(), formKey, formDef.getBoKey(), true);
		IBusinessObject businessObject = businessObjectService.getFilledByKey(boKey);
		businessObject.setPermission(permission.getBusObj(boKey));

		businessDataService.removeData(businessObject, id);

		return new ResultMsg<>(BaseStatusCode.SUCCESS,"删除成功！");
	}

}
