package com.dstz.form.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.core.util.ThreadMapUtil;
import com.dstz.base.dao.CommonDao;
import com.dstz.base.db.datasource.DbContextHolder;
import com.dstz.base.db.model.page.PageResult;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.form.manager.FormCustDialogManager;
import com.dstz.form.model.FormCustDialog;
import com.dstz.sys.api.model.ISysDataSource;
import com.dstz.sys.api.service.ISysDataSourceService;

/**
 * <pre>
 * 描述：自定义对话框管理
 * </pre>
 */
@RestController
@RequestMapping("/form/formCustDialog/")
public class FormCustDialogController extends BaseController<FormCustDialog> {
	@Autowired
	FormCustDialogManager formCustDialogManager;
	@Autowired
	CommonDao<?> commonDao;

	@Autowired
	ISysDataSourceService sysDataSourceService;

	/**
	 * <pre>
	 * 获取formCustDialog的后端
	 * 目前支持根据id 获取formCustDialog
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getObject")
	@CatchErr(write2response = true, value = "获取formCustDialog异常")
	public ResultMsg<FormCustDialog> getObject(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = RequestUtil.getString(request, "id");
		String key = RequestUtil.getString(request, "key");
		FormCustDialog formCustDialog = null;
		if (StringUtil.isNotEmpty(id)) {
			formCustDialog = formCustDialogManager.get(id);
		} else if (StringUtil.isNotEmpty(key)) {
			formCustDialog = formCustDialogManager.getByKey(key);
		}

		return getSuccessResult(formCustDialog);
	}

	/**
	 * <pre>
	 * edit页面的searchObjName
	 * 其他页面使用可以参数传一个json:
	 * {dsKey:"数据源key",objType:"枚举在 com.dstz.form.api.model.FormCustDialogOBJTYPE ",objName:"要查询的名字"}
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param formCustDialog
	 * @throws Exception
	 */
	@RequestMapping("searchObjName")
	@CatchErr(write2response = true, value = "根据数据源获取objName信息失败")
	public ResultMsg<Map<String, String>> searchObjName(HttpServletRequest request, HttpServletResponse response, @RequestBody FormCustDialog formCustDialog) throws Exception {
		return getSuccessResult(formCustDialogManager.searchObjName(formCustDialog), "根据数据源获取objName信息成功");
	}

	/**
	 * <pre>
	 * 获取objName对象的信息
	 * 其他页面使用可以参数传一个json:
	 * {dsKey:"数据源key",objType:"枚举在com.dstz.form.model.FormCustDialog$OBJTYPE",objName:"要查询的名字"}
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param formCustDialog
	 * @throws Exception
	 */
	@RequestMapping("getTable")
	@CatchErr(write2response = true, value = "根据数据源获取objName的字段信息失败")
	public ResultMsg<Table<Column>> getTable(HttpServletRequest request, HttpServletResponse response, @RequestBody FormCustDialog formCustDialog) throws Exception {
		return getSuccessResult(formCustDialogManager.getTable(formCustDialog), "根据数据源获取objName的字段信息成功");
	}

	/**
	 * <pre>
	 * 获取对话框的列表数据后端
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listData_{key}")
	@CatchErr(write2response = true, value = "获取对话框的列表数据失败")
	public PageResult listData(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "key") String key) throws Exception {
		// 传页面是否分页到线程变量中
		boolean isPage = request.getParameter("limit") != null;
		ThreadMapUtil.put("isPage", isPage);

		FormCustDialog formCustDialog = formCustDialogManager.getByKey(key);
		QueryFilter queryFilter = getQueryFilter(request);
		ISysDataSource sysDataSource = sysDataSourceService.getByKey(formCustDialog.getDsKey());
		// 切换数据源
		if (sysDataSource != null) {
			DbContextHolder.setDataSource(sysDataSource.getKey(), sysDataSource.getDbType());
		}
		List<?> list = formCustDialogManager.data(formCustDialog, queryFilter);

		ThreadMapUtil.remove();
		return new PageResult(list);
	}

	/**
	 * <pre>
	 * 自定义查询后端
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("queryListData_{key}")
	@CatchErr(write2response = true, value = "获取对话框的列表数据失败")
	public PageResult qlistData(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "key") String key) throws Exception {
		FormCustDialog formCustDialog = formCustDialogManager.getByKey(key);
		// 拼装请求
		formCustDialog.getConditionFields().forEach(field -> {
			Object val = request.getParameter(field.getColumnName());
			if (val == null) {
				return;
			}
			String str = field.getColumnName() + "$" + field.getDbType().substring(0, 1).toUpperCase() + field.getCondition();
			request.setAttribute(str, val);
		});

		PageResult pageResult = listData(request, response, key);
		List<Map<String, Object>> rows = new ArrayList<>();
		pageResult.getRows().forEach(row -> {
			Map<String, Object> map = (Map<String, Object>) row;
			Map<String, Object> m = new HashMap<>();
			formCustDialog.getReturnFields().forEach(field -> {
				m.put(field.getReturnName(), map.get(field.getColumnName()));
			});
			rows.add(m);
		});
		pageResult.setRows(rows);
		return pageResult;
	}

	/**
	 * <pre>
	 * 获取对话框的树数据后端
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("treeData_{key}")
	public List<?> treeData(HttpServletRequest request, HttpServletResponse response, @PathVariable(value = "key") String key) throws Exception {
		QueryFilter queryFilter = getQueryFilter(request);
		queryFilter.setPage(null);
		// 页面来的参数
		FormCustDialog formCustDialog = formCustDialogManager.getByKey(key);
		ISysDataSource sysDataSource = sysDataSourceService.getByKey(formCustDialog.getDsKey());
		// 切换数据源
		DbContextHolder.setDataSource(sysDataSource.getKey(), sysDataSource.getDbType());
		return formCustDialogManager.data(formCustDialog, queryFilter);
	}

	/**
	 * 键是否存在
	 *
	 * @param key 键
	 * @return 结果信息-是否存在
	 */
	@CatchErr
	@RequestMapping("/{key}/exists")
	public ResultMsg<Boolean> exists(@PathVariable("key") String key) {
		boolean exists = formCustDialogManager.existsByKey(key);
		return new ResultMsg<>(exists);
	}

	@Override
	protected String getModelDesc() {
		return "自定义对话框";
	}

}
