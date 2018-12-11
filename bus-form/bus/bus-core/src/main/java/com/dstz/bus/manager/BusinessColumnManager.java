package com.dstz.bus.manager;

import java.util.List;

import com.dstz.base.manager.Manager;
import com.dstz.bus.model.BusinessColumn;

/**
 * 
 * @author 李易峻
 *
 * @email:632266504@qq.com
 */
public interface BusinessColumnManager extends Manager<String, BusinessColumn> {
	/**
	 * <pre>
	 * 根据tableId删除字段
	 * </pre>
	 * 
	 * @param tableId
	 */
	void removeByTableId(String tableId);

	/**
	 * <pre>
	 * 根据tableId获取字段
	 * </pre>
	 * 
	 * @param tableId
	 * @return
	 */
	List<BusinessColumn> getByTableId(String tableId);
}
