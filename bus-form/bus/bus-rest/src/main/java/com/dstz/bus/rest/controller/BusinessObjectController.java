package com.dstz.bus.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.bus.api.constant.BusTableRelType;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.model.BusTableRel;
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
	@CatchErr(value = "获取businessObject异常")
	public ResultMsg<BusinessObject> getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

		return getSuccessResult(object);
	}

	@Override
	public ResultMsg<String> save(@RequestBody BusinessObject businessObject) throws Exception {
		ResultMsg<String> resultMsg = super.save(businessObject);
		BusinessObjectCacheUtil.putDataSourcesKeys(businessObject.getKey(), businessObjectManager.getFilledByKey(businessObject.getKey()).calDataSourceKeys());
		return resultMsg;
	}

	@RequestMapping("saveOverallArrangement")
	@CatchErr
	public ResultMsg<String> saveOverallArrangement(HttpServletRequest request) throws Exception {
		String overallArrangement = RequestUtil.getString(request, "overallArrangement");
		String boCode = RequestUtil.getString(request, "boCode");
		businessObjectManager.updateOverallArrangementByCode(boCode, overallArrangement);
		return getSuccessResult();
	}

	@RequestMapping("getOverallArrangement")
	@CatchErr
	public ResultMsg<JSONObject> getOverallArrangement(@RequestParam String boCode) throws Exception {
		String param = businessObjectManager.getOverallArrangementByCode(boCode);
		JSONObject object = new JSONObject();
		if (StringUtil.isNotEmpty(param)) {
			object = JSON.parseObject(param);
		}
		System.out.println(object);
		return getSuccessResult(object);
	}

	@RequestMapping("getBoStruct")
	@CatchErr(value = "获取businessObject结构异常")
	public ResultMsg<JSONObject> getBoStruct(@RequestParam String key) throws Exception {
		BusinessObject bo = businessObjectManager.getFilledByKey(key);
		return getSuccessResult(getBoStruct(bo.getRelation()));
	}

	/**
	 * <pre>
	 * 获取bo结构
	 * </pre>
	 * 
	 * @param rel
	 * @return
	 */
	private JSONObject getBoStruct(BusTableRel rel) {
		JSONObject json = new JSONObject();
		rel.getTable().getColumns().forEach(column -> {
			json.put(column.getKey(), column.initValue() == null ? "" : column.initValue());
		});
		rel.getChildren().forEach(r -> {
			if (BusTableRelType.ONE_TO_ONE.equalsWithKey(r.getType())) {
				json.put(r.getTableKey(), getBoStruct(r));
			} else {
				JSONObject jo = getBoStruct(r);
				JSONArray ja = new JSONArray();
				ja.add(jo);
				json.put(r.getTableKey() + "List", ja);
			}
		});

		return json;
	}

	@Override
	protected String getModelDesc() {
		return "业务对象";
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("a", null);
		System.out.println(map);
	}
}
