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
import com.dstz.base.db.tableoper.TableOperator;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.bus.manager.BusinessTableManager;
import com.dstz.bus.model.BusinessTable;
import com.dstz.bus.util.BusinessTableCacheUtil;

/**
 * businessTable层的controller
 *
 * @author aschs
 */
@RestController
@RequestMapping("/bus/businessTable/")
public class BusinessTableController extends BaseController<BusinessTable> {
	@Resource
	BusinessTableManager businessTableManager;

	/**
	 * <pre>
	 * businessTableEdit.html的save后端
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param businessTable
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Override
	@CatchErr(value = "保存业务表失败")
	public ResultMsg<String> save(@RequestBody BusinessTable businessTable) throws Exception {
		businessTableManager.save(businessTable);
		return getSuccessResult("保存业务表成功");
	}

	/**
	 * <pre>
	 * 获取BusinessTable的后端
	 * 目前支持根据id,key 获取BusinessTable
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getObject")
	@CatchErr(write2response = true, value = "获取BusinessTable异常")
	public void getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String key = RequestUtil.getString(request, "key");
		boolean fill = RequestUtil.getBoolean(request, "fill");// 是否要填充table
		BusinessTable table = null;
		if (StringUtil.isNotEmpty(id)) {
			table = businessTableManager.get(id);
		} else if (StringUtil.isNotEmpty(key)) {
			table = businessTableManager.getByKey(key);
		}
		if (fill && table != null) {
			table = businessTableManager.getFilledByKey(table.getKey());
		}
		
		writeSuccessData(response, table);
	}


	/**
	 * <pre>
	 * 新建表
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("createTable")
	@CatchErr(write2response = true, value = "建表失败")
	public void createTable(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		BusinessTable businessTable = businessTableManager.get(id);
		businessTable = businessTableManager.getFilledByKey(businessTable.getKey());
		TableOperator tableOperator = businessTableManager.newTableOperator(businessTable);
		tableOperator.createTable();
		businessTable.setCreatedTable(true);
		BusinessTableCacheUtil.put(businessTable);//入缓存
		writeSuccessResult(response, "建表成功");
	}

	@Override
	protected String getModelDesc() {
		return "业务表";
	}
}
