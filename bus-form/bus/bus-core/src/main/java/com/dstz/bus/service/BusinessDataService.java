package com.dstz.bus.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.core.executor.ExecutorFactory;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.bus.api.constant.BusTableRelType;
import com.dstz.bus.api.model.IBusinessData;
import com.dstz.bus.api.model.IBusinessObject;
import com.dstz.bus.api.model.IBusinessPermission;
import com.dstz.bus.api.service.IBusinessDataService;
import com.dstz.bus.executor.assemblyval.AssemblyValExecuteChain;
import com.dstz.bus.executor.assemblyval.AssemblyValParam;
import com.dstz.bus.executor.parseval.ParseValExecuteChain;
import com.dstz.bus.executor.parseval.ParseValParam;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.model.BusTableRel;
import com.dstz.bus.model.BusinessData;
import com.dstz.bus.model.BusinessObject;
import com.dstz.bus.model.permission.BusObjPermission;

@Service
public class BusinessDataService implements IBusinessDataService {
	@Autowired
	BusinessObjectManager businessObjectManager;

	@Override
	public void saveFormDefData(JSONObject data, IBusinessPermission businessPermission) {
		for (Entry<String, Object> entry : data.entrySet()) {
			String boKey = entry.getKey();
			JSONObject boData = (JSONObject) entry.getValue();
			BusinessData businessData = (BusinessData) parseBusinessData(boData, boKey);
			businessData.getBusTableRel().getBusObj().setPermission((BusObjPermission) businessPermission.getBusObj(boKey));
			BusinessDataPersistenceServiceFactory.saveData(businessData);
		}
	}

	@Override
	public JSONObject getFormDefData(IBusinessObject businessObject, Object id) {
		BusinessData businessData = BusinessDataPersistenceServiceFactory.loadData((BusinessObject) businessObject, id);
		if (BeanUtils.isEmpty(id)) {// id为空时需要初始化数据
			initFormDefData(businessData);
		}

		JSONObject data = new JSONObject();

		assemblyFormDefData(data, businessData);
		return data;
	}

	/**
	 * <pre>
	 * 根据formDefData的需求
	 * 获取businessData的初始化数据
	 * </pre>
	 * 
	 * @param businessData
	 */
	private void initFormDefData(BusinessData businessData) {
		BusTableRel busTableRel = businessData.getBusTableRel();
		businessData.setDbData(busTableRel.getTable().initDbData());

		// 处理其子表
		for (BusTableRel rel : busTableRel.getChildren()) {
			if (BusTableRelType.ONE_TO_ONE.equalsWithKey(rel.getType())) {// 一对一才需要初始化
				BusinessData childData = new BusinessData();
				childData.setBusTableRel(rel);
				initFormDefData(childData);
				businessData.addChildren(childData);
			}
		}

	}

	@Override
	public JSONObject assemblyFormDefData(IBusinessData businessData) {
		JSONObject data = new JSONObject();
		assemblyFormDefData(data, businessData);
		return data;
	}

	/**
	 * <pre>
	 * 拼装formdefdata中的data,查看父类接口描述
	 * </pre>
	 * 
	 * @param data
	 * @param businessData
	 */
	private void assemblyFormDefData(JSONObject data, IBusinessData ibusinessData) {
		BusinessData businessData = (BusinessData) ibusinessData;
		
		AssemblyValParam param = new AssemblyValParam(data, businessData);
		ExecutorFactory.execute(AssemblyValExecuteChain.class, param);
		
		// 处理子表
		for (Entry<String, List<BusinessData>> entry : businessData.getChildren().entrySet()) {
			String tableKey = entry.getKey();
			List<BusinessData> children = entry.getValue();
			if (BusTableRelType.ONE_TO_ONE.equalsWithKey(children.get(0).getBusTableRel().getType())) {
				JSONObject cData = new JSONObject();
				assemblyFormDefData(cData, children.get(0));
				data.put(tableKey, cData);
			} else {// 下面要是数组类型
				JSONArray dataList = new JSONArray();
				for (BusinessData bd : children) {
					JSONObject cData = new JSONObject();
					assemblyFormDefData(cData, bd);
					dataList.add(cData);
				}
				data.put(tableKey + "List", dataList);
			}
		}
	}

	/**
	 * <pre>
	 * 解析FormDefData 中的data 转成businessData
	 * </pre>
	 * 
	 * @param object
	 * @param relation
	 * @param parent
	 * @return
	 */
	private BusinessData analysisFormDefData(Object object, BusTableRel relation) {
		BusinessData businessData = new BusinessData();
		businessData.setBusTableRel(relation);
		// 主表或者一对一子表
		if (object instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) object;
			Map<String, Object> bdData = new HashMap<>();// businessData中的数据
			for (Entry<String, Object> enty : jsonObject.entrySet()) {
				// 不是数组 和 对象 则才是字段
				if (!(enty.getValue() instanceof JSONArray) && !(enty.getValue() instanceof JSONObject)) {
					ParseValParam param = new ParseValParam(enty.getKey(), enty.getValue(), bdData, relation);
					ExecutorFactory.execute(ParseValExecuteChain.class, param);
				}

				// 处理数组 （相当于处理子表）
				if (enty.getValue() instanceof JSONArray) {
					String tableKey = enty.getKey().substring(0, enty.getKey().length() - 4);// 切割尾部的List
					BusTableRel rel = relation.find(tableKey);
					for (Object obj : (JSONArray) enty.getValue()) {// 遍历
						businessData.addChildren(analysisFormDefData(obj, rel));
					}
				}
				// 处理对象 （相当于处理子表）
				if (enty.getValue() instanceof JSONObject) {
					BusTableRel rel = relation.find(enty.getKey());
					businessData.addChildren(analysisFormDefData(enty.getValue(), rel));
				}
			}
			businessData.setData(bdData);
		}
		return businessData;
	}

	@Override
	public void removeData(IBusinessObject businessObject, Object id) {
		BusinessDataPersistenceServiceFactory.removeData((BusinessObject) businessObject, id);
	}

	@Override
	public void saveData(IBusinessData businessData) {
		BusinessDataPersistenceServiceFactory.saveData((BusinessData) businessData);
	}

	@Override
	public IBusinessData loadData(IBusinessObject businessObject, Object id) {
		return BusinessDataPersistenceServiceFactory.loadData((BusinessObject) businessObject, id);
	}

	@Override
	public IBusinessData parseBusinessData(JSONObject jsonObject, String boKey) {
		BusinessObject businessObject = businessObjectManager.getFilledByKey(boKey);
		return analysisFormDefData(jsonObject, businessObject.getRelation());
	}
}
