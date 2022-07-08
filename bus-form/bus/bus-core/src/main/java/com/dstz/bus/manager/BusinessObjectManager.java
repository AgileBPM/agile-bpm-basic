package com.dstz.bus.manager;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.manager.Manager;
import com.dstz.bus.model.BusinessObject;

/**
 * <pre>
 * 描述：BusinessObject Manager层
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:下午8:11:10
 * 版权:summer
 * </pre>
 */
public interface BusinessObjectManager extends Manager<String, BusinessObject> {
	/**
	 * <pre>
	 * 根据key获取BusinessObject
	 * </pre>
	 * 
	 * @param key
	 * @return
	 */
	BusinessObject getByKey(String key);

	/**
	 * <pre>
	 * boTree的数据
	 * </pre>
	 * 
	 * @param key
	 * @return
	 */
	List<JSONObject> boTreeData(String key);
	
	/**
	 * <pre>
	 * 根据key获取填充好的BusinessObject
	 * rel包含table对象 table中的ctrl也被填充好
	 * </pre>
	 * @param key
	 * @return
	 */
	BusinessObject getFilledByKey(String key);

	void updateOverallArrangementByCode(String boCode, String overallArrangement);
	
	String getOverallArrangementByCode(String boCode);
}
