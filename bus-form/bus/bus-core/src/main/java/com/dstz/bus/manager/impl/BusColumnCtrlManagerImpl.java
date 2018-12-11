package com.dstz.bus.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.bus.dao.BusColumnCtrlDao;
import com.dstz.bus.manager.BusColumnCtrlManager;
import com.dstz.bus.model.BusColumnCtrl;

/**
 * busColCtrl 的manager层实现类
 * @author aschs
 *
 */
@Service
public class BusColumnCtrlManagerImpl extends BaseManager<String, BusColumnCtrl> implements BusColumnCtrlManager {
	@Resource
	BusColumnCtrlDao busColumnCtrlDao;

	@Override
	public void removeByTableId(String tableId) {
		busColumnCtrlDao.removeByTableId(tableId);
	}

	@Override
	public BusColumnCtrl getByColumnId(String columnId) {
		return busColumnCtrlDao.getByColumnId(columnId);
	}

}
