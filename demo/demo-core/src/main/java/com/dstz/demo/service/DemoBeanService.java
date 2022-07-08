package com.dstz.demo.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.model.BusTableRel;
import com.dstz.bus.model.BusinessData;
import com.dstz.bus.model.BusinessObject;
import com.dstz.bus.service.IBusinessDataPersistenceBeanService;

@Service
public class DemoBeanService implements IBusinessDataPersistenceBeanService {

	@Override
	public String getName() {
		return "demoBeanService";
	}

	@Override
	public void saveData(BusinessData businessData) {
		BusinessObject businessObject = businessData.getBusTableRel().getBusObj();
		// permission
		JSONObject permission = new JSONObject();
		JSONObject tablePermission = new JSONObject();
		businessObject.getPermission().handlePermission(tablePermission, permission, false);
		
		businessData.setPk("123");
	}

	@Override
	public BusinessData loadData(BusinessObject businessObject, Object id) {
		BusinessData businessData = new BusinessData();
		// 处理主表
		BusTableRel busTableRel = businessObject.getRelation();
		businessData.setBusTableRel(busTableRel);
		if (id == null) {
			return businessData;
		}
		businessData.setPk(id);
		businessData.put("name", "尤雅");
		return businessData;
	}

	@Override
	public void removeData(BusinessObject businessObject, Object id) {

	}

}
