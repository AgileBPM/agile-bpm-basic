package com.dstz.bus.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.manager.BusinessPermissionManager;
import com.dstz.bus.model.BusinessObject;
import com.dstz.bus.model.BusinessPermission;

/**
 * <pre>
 * 描述：businessPermission层的controller
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:下午5:11:06
 * 版权:summer
 * </pre>
 */
@RestController
@RequestMapping("/bus/businessPermission/")
public class BusinessPermissionController extends BaseController<BusinessPermission> {
	@Resource
	BusinessObjectManager businessObjectManager;
	@Autowired
	BusinessPermissionManager businessPermissionManager;

	/**
	 * <pre>
	 * 获取businessPermission的后端
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getObject")
	@CatchErr(write2response = true, value = "获取businessPermission异常")
	public void getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String objType = RequestUtil.getString(request, "objType");
		String objVal = RequestUtil.getString(request, "objVal");
		BusinessPermission businessPermission = businessPermissionManager.getByObjTypeAndObjVal(objType, objVal);
		writeSuccessData(response, businessPermission);
	}

	/**
	 * <pre>
	 * 获取bo数据的后端
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getBo")
	@CatchErr(write2response = true, value = "获取boo异常")
	public void getBo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] boKeys = RequestUtil.getStringAryByStr(request, "boKeys");
		
		Map<String, BusinessObject> boMap = new HashMap<>();
		for (String boKey : boKeys) {
			BusinessObject bo = businessObjectManager.getFilledByKey(boKey);
			boMap.put(boKey, bo);
		}
		writeSuccessData(response, boMap);
	}

	@Override
	protected String getModelDesc() {
		return "业务对象权限";
	}

}
