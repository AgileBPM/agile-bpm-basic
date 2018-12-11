package com.dstz.bus.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.bus.dao.BusinessColumnDao;
import com.dstz.bus.manager.BusinessColumnManager;
import com.dstz.bus.model.BusinessColumn;

/**
 * businessColumn 的manager层实现类
 * @author aschs
 *
 */
@Service
public class BusinessColumnManagerImpl extends BaseManager<String, BusinessColumn> implements BusinessColumnManager {
	@Resource
	BusinessColumnDao businessColumnDao;

	@Override
	public void removeByTableId(String tableId){
		businessColumnDao.removeByTableId(tableId);
	}

	@Override
	public List<BusinessColumn> getByTableId(String tableId) {
		return businessColumnDao.getByTableId(tableId);
	}
}
