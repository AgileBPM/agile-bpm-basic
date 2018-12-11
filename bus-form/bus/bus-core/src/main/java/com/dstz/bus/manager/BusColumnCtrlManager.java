package com.dstz.bus.manager;

import com.dstz.base.manager.Manager;
import com.dstz.bus.model.BusColumnCtrl;

/**
 * 
 * @author 李易峻
 *
 * @email:632266504@qq.com
 */
public interface BusColumnCtrlManager extends Manager<String, BusColumnCtrl> {
	/**
	 * <pre>
	 * 根据tableId删除BusColCtrl
	 * </pre>
	 * @param tableId
	 */
	void removeByTableId(String tableId);
	
	/**
	 * <pre>
	 * 根据columnId获取BusColCtrl
	 * </pre>
	 * @param columnId
	 * @return
	 */
	BusColumnCtrl getByColumnId(String columnId);
}
