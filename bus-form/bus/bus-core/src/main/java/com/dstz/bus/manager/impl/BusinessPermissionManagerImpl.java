package com.dstz.bus.manager.impl;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.bus.api.constant.RightsType;
import com.dstz.bus.dao.BusinessPermissionDao;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.manager.BusinessPermissionManager;
import com.dstz.bus.model.BusTableRel;
import com.dstz.bus.model.BusinessColumn;
import com.dstz.bus.model.BusinessObject;
import com.dstz.bus.model.BusinessPermission;
import com.dstz.bus.model.permission.AbstractPermission;
import com.dstz.bus.model.permission.BusColumnPermission;
import com.dstz.bus.model.permission.BusObjPermission;
import com.dstz.bus.model.permission.BusTablePermission;

/**
 * bo权限 Manager处理实现类
 * 
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-04-25 19:00:07
 */
@Service("busObjPermissionManager")
public class BusinessPermissionManagerImpl extends BaseManager<String, BusinessPermission> implements BusinessPermissionManager {
	@Resource
	BusinessPermissionDao busObjPermissionDao;
	@Autowired
	BusinessObjectManager businessObjectManager;

	@Override
	public BusinessPermission getByObjTypeAndObjVal(String objType, String objVal) {
		return busObjPermissionDao.getByObjTypeAndObjVal(objType, objVal);
	}

	@Override
	public BusinessPermission getByObjTypeAndObjVal(String objType, String objVal, String defaultBoKeys) {
		BusinessPermission oldPermission = getByObjTypeAndObjVal(objType, objVal);// 数据库的权限
		if (oldPermission == null) {// 数据库找不到
			oldPermission = new BusinessPermission();
		}

		BusinessPermission businessPermission = new BusinessPermission();
		businessPermission.setObjType(objType);
		businessPermission.setObjVal(objVal);
		for (String boKey : defaultBoKeys.split(",")) {
			// 1 bo层数据
			BusinessObject bo = businessObjectManager.getFilledByKey(boKey);
			if(bo == null) {
				throw new BusinessException(boKey + " 业务对象丢失！");
			}
			
			BusObjPermission busObjPermission = oldPermission.getBusObj(boKey);
			if (busObjPermission == null) {
				busObjPermission = new BusObjPermission();
				busObjPermission.setKey(boKey);
				busObjPermission.setName(bo.getName());
				hanldeDefaultRightsField(busObjPermission);// bo才赋予默认值，因为下层会在计算逻辑中继承它的
			}
			businessPermission.getBusObjMap().put(boKey, busObjPermission);

			// 处理表层数据
			for (BusTableRel rel : bo.getRelation().list()) {
				BusTablePermission busTablePermission = busObjPermission.getTableMap().get(rel.getTableKey());
				if (busTablePermission == null) {
					busTablePermission = new BusTablePermission();
					busTablePermission.setKey(rel.getTableKey());
					busTablePermission.setComment(rel.getTableComment());
				}
				busObjPermission.getTableMap().put(rel.getTableKey(), busTablePermission);

				// 处理字段层数据
				for (BusinessColumn column : rel.getTable().getColumnsWithoutPk()) {
					BusColumnPermission busColumnPermission = busTablePermission.getColumnMap().get(column.getKey());
					if (busColumnPermission == null) {
						busColumnPermission = new BusColumnPermission();
						busColumnPermission.setKey(column.getKey());
						busColumnPermission.setComment(column.getComment());
					}
					busTablePermission.getColumnMap().put(column.getKey(), busColumnPermission);
				}
				
				// 删除表中不存在却在权限里有的字段
				Iterator<Entry<String, BusColumnPermission>> it = busTablePermission.getColumnMap().entrySet().iterator();  
				while(it.hasNext()){  
		            Entry<String, BusColumnPermission> entry = it.next();  
		            if (rel.getTable().getColumnByKey(entry.getKey()) == null) {
		                it.remove();
		            }
		        }  
			}
			
			// 删除bo中不存在却在权限里有的表
			Iterator<Entry<String, BusTablePermission>> it = busObjPermission.getTableMap().entrySet().iterator();  
			while(it.hasNext()){  
	            Entry<String, BusTablePermission> entry = it.next();  
				if (bo.getRelation().find(entry.getKey())==null) {
	            	it.remove();
	            }
	        }  
		}

		return businessPermission;
	}

	/**
	 * <pre>
	 * 处理默认权限字段
	 * 默认是 所有人 有编辑权限
	 * </pre>
	 * 
	 * @param permission
	 */
	private void hanldeDefaultRightsField(AbstractPermission permission) {
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		json.put("type", "everyone");
		json.put("desc", "所有人");
		jsonArray.add(json);
		permission.getRights().put(RightsType.getDefalut().getKey(), jsonArray);
	}
}
