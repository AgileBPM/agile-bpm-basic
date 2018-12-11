package com.dstz.bus.rest.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.model.BusinessObject;
import com.dstz.bus.util.BusinessObjectCacheUtil;

/**
 * <pre>
 * 描述：businessObject层的controller
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:下午5:11:06
 * 版权:summer
 * </pre>
 */
@RestController
@RequestMapping("/bus/businessObject/")
public class BusinessObjectController extends BaseController<BusinessObject> {
	@Resource
	BusinessObjectManager businessObjectManager;

	/**
	 * <pre>
	 * 获取businessObject的后端
	 * 目前支持根据id,key 获取businessObject
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getObject")
	@CatchErr(write2response = true, value = "获取businessObject异常")
	public void getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String key = RequestUtil.getString(request, "key");
		boolean fill = RequestUtil.getBoolean(request, "fill");// 是否要填充table
		BusinessObject object = null;
		if (StringUtil.isNotEmpty(id)) {
			object = businessObjectManager.get(id);
		} else if (StringUtil.isNotEmpty(key)) {
			object = businessObjectManager.getByKey(key);
		}
		if (fill && object != null) {
			object = businessObjectManager.getFilledByKey(object.getKey());
		}

		writeSuccessData(response, object);
	}
	
	@Override
	 public ResultMsg<String> save(@RequestBody BusinessObject businessObject) throws Exception {
		 ResultMsg<String> resultMsg= super.save(businessObject);
		 BusinessObjectCacheUtil.putDataSourcesKeys(businessObject.getKey(), businessObjectManager.getFilledByKey(businessObject.getKey()).calDataSourceKeys());
		 return resultMsg;
	 }
	
	@Override
	protected String getModelDesc() {
		return "业务对象";
	}

}
