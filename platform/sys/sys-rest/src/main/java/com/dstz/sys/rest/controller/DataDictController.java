package com.dstz.sys.rest.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.sys.core.manager.DataDictManager;
import com.dstz.sys.core.model.DataDict;


/**
 * 数据字典 控制器类<br/>
 * @author  aschs
 */
@RestController
@RequestMapping("/sys/dataDict")
public class DataDictController extends BaseController<DataDict>{
	@Resource
	DataDictManager dataDictManager;
	
	
	@Override
	protected String getModelDesc(){
		return "数据字典";
	}
	
	@RequestMapping("getDictData")
	public ResultMsg<List<DataDict>> getByDictKey(@RequestParam String dictKey,@RequestParam(defaultValue="false") Boolean hasRoot) throws Exception{
		if(StringUtil.isEmpty(dictKey)) return null;
		
		List<DataDict> dict = dataDictManager.getDictNodeList(dictKey,hasRoot);
		return getSuccessResult(dict);
	}
	
	@RequestMapping("getDictList")
	public ResultMsg<List<DataDict>> getDictList(HttpServletRequest request) throws Exception{
		QueryFilter filter = getQueryFilter(request);
		filter.addFilter("dict_type_", DataDict.TYPE_DICT, QueryOP.EQUAL);
		filter.setPage(null);
		
		List<DataDict> dict = dataDictManager.query(filter);
		return getSuccessResult(dict);
	}
	
	/**
	 * 获取所有数据字典，以tree的形式
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getDictTree")
	@CatchErr("获取数据字典失败")
	public ResultMsg<JSONArray> getDictTree() throws Exception{
		JSONArray dict = dataDictManager.getDictTree();
		return getSuccessResult(dict);
	}
	
}
