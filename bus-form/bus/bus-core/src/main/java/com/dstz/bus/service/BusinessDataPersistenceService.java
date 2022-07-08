package com.dstz.bus.service;

import com.dstz.bus.model.BusinessData;
import com.dstz.bus.model.BusinessObject;

/**
 * <pre>
 * 业务数据持久化的接口类
 * 负责业务系统的数据进行入库的中间层
 * </pre>
 *
 * @author aschs
 *
 */
public interface BusinessDataPersistenceService {
	/**
	 * <pre>
	 * 返回持久化方式的类型key
	 * </pre>
	 * 
	 * @return
	 */
	public String type();

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
