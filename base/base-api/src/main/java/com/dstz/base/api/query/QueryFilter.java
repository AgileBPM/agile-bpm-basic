package com.dstz.base.api.query;

import java.util.List;
import java.util.Map;

import com.dstz.base.api.Page;

/**
 * 
 * <pre> 
 * 描述：组合条件查询过滤
 * </pre>
 */
public interface QueryFilter {
	/**
	 * 返回分页信息
	 * @return
	 */
	public Page getPage();
	
	public void setPage(Page page);
	/**
	 * 返回字段组合查询逻辑
	 * @return
	 */
	public FieldLogic getFieldLogic();

	/**
	 * 返回组合的参数映射
	 * @return
	 */
	public Map<String,Object> getParams();
	
	public void addParams(Map<String,Object> params);
	
	/**
	 * 返回字段排序列表
	 * @return
	 */
	public List<FieldSort> getFieldSortList();
	/**
	 * 添加自定义过滤条件（用于自动组装条件：whereSql）
	 * @param name
	 * @param obj
	 * @param queryType
	 */
	public void addFilter(String name,Object obj,QueryOP queryType);
	
	/**
	 * 添加自定义过滤条件（用于手动组装条件，在MAPPING文件判断用的参数）
	 * @param name
	 * @param obj
	 */
	public void addParamsFilter(String key,Object obj);
	/**
	 * <pre>
	 * 增加排序
	 * </pre>
	 * @param orderField
	 * @param orderSeq
	 */
	void addFieldSort(String orderField, String orderSeq);
	
	
	String getWhereSql();
	
	String getOrderBySql();

}
