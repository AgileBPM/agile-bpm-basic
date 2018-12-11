package com.dstz.bus.service;

import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.bus.api.constant.RightsType;
import com.dstz.bus.api.service.IBusinessPermissionService;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.manager.BusinessPermissionManager;
import com.dstz.bus.model.BusinessPermission;
import com.dstz.bus.model.permission.AbstractPermission;
import com.dstz.bus.model.permission.BusColumnPermission;
import com.dstz.bus.model.permission.BusObjPermission;
import com.dstz.bus.model.permission.BusTablePermission;
import com.dstz.sys.api.permission.PermissionCalculatorFactory;

@Service
public class BusinessPermissionService implements IBusinessPermissionService {
	@Autowired
	BusinessPermissionManager businessPermissionManager;
	@Autowired
	BusinessObjectManager businessObjectManager;

	@Override
	public BusinessPermission getByObjTypeAndObjVal(String objType, String objVal, String defalutBoKeys, boolean calculate) {
		BusinessPermission businessPermission = null;
		if (StringUtil.isNotEmpty(defalutBoKeys)) {
			businessPermission = businessPermissionManager.getByObjTypeAndObjVal(objType, objVal, defalutBoKeys);
		} else {
			businessPermission = businessPermissionManager.getByObjTypeAndObjVal(objType, objVal);
		}
		if(businessPermission == null) {
			return new BusinessPermission();
		}
		
		if (calculate) {
			calculateResult(businessPermission);
		}
		return businessPermission;
	}

	private void calculateResult(BusinessPermission businessPermission) {

		for (Entry<String, BusObjPermission> entry : businessPermission.getBusObjMap().entrySet()) {
			BusObjPermission busObjPermission = entry.getValue();
			calculateResult(busObjPermission);
			for (Entry<String, BusTablePermission> etry : busObjPermission.getTableMap().entrySet()) {
				BusTablePermission busTablePermission = etry.getValue();
				// 自身为空则设置为上级bo的权限
				if (BeanUtils.isEmpty(busTablePermission.getRights())) {
					busTablePermission.setResult(busObjPermission.getResult());
				} else {
					calculateResult(busTablePermission);
				}

				for (Entry<String, BusColumnPermission> ery : busTablePermission.getColumnMap().entrySet()) {
					BusColumnPermission busColumnPermission = ery.getValue();
					// 自身为空则设置为上级table的权限
					if (BeanUtils.isEmpty(busColumnPermission.getRights())) {
						busColumnPermission.setResult(busTablePermission.getResult());
					} else {
						calculateResult(busColumnPermission);
					}

				}
			}
		}
	}

	/**
	 * <pre>
	 * 计算AbstractPermission对于当前系统用户的结果
	 * </pre>
	 * 
	 * @param permission
	 */
	private void calculateResult(AbstractPermission permission) {

		for (RightsType rightsType : RightsType.values()) {
			JSONArray jsonArray = permission.getRights().get(rightsType.getKey());
			boolean b = PermissionCalculatorFactory.haveRights(jsonArray);
			if (b) {
				permission.setResult(rightsType.getKey());
				break;
			}
		}
		// 没计算出结果，则默认最后一个
		if (StringUtil.isEmpty(permission.getResult())) {
			permission.setResult(RightsType.values()[RightsType.values().length - 1].getKey());
		}
	}
}
