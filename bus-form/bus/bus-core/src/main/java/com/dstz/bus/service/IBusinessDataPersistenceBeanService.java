package com.dstz.bus.service;

import com.dstz.bus.model.BusinessData;
import com.dstz.bus.model.BusinessObject;

/**
 * <pre>
 * 描述：servicBean方式持久化的接口
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2019年7月22日
 * 版权:summer
 * </pre>
 */
public interface IBusinessDataPersistenceBeanService {
	/**
	 * <pre>
	 * servicBean的名字
	 * </pre>
	 * 
	 * @return
	 */
	String getName();

	/**
	 * <pre>
	 * 保存businessData
	 * 持久化操作，过程中会赋值id
	 * </pre>
	 * 
	 * @param businessData
	 */
	public void saveData(BusinessData businessData);

	/**
	 * <pre>
	 * 根据主表id加载某个businessObject的数据
	 * </pre>
	 * 
	 * @param businessObject
	 * @param id
	 * @param tableKey
	 * @return
	 */
	public BusinessData loadData(BusinessObject businessObject, Object id);

	/**
	 * <pre>
	 * 根据主表id加载删除数据
	 * </pre>
	 * 
	 * @param businessObject
	 * @param id
	 * @param tableKey
	 * @return
	 */
	void removeData(BusinessObject businessObject, Object id);
}
