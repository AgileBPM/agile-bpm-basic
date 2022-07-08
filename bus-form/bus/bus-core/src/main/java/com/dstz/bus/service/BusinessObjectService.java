package com.dstz.bus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.api.model.IBusinessObject;
import com.dstz.bus.api.service.IBusinessObjectService;
import com.dstz.bus.manager.BusinessObjectManager;

/**
 * <pre>
 *  
 * 描述：IBusinessObjectService实现类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月26日 下午5:53:29
 * 版权:summer
 * </pre>
 */
@Service
public class BusinessObjectService implements IBusinessObjectService {
	@Autowired
	BusinessObjectManager businessObjectManager;

	@Override
	public IBusinessObject getByKey(String key) {
		return businessObjectManager.getByKey(key);
	}

	@Override
	public IBusinessObject getFilledByKey(String key) {
		return businessObjectManager.getFilledByKey(key);
	}

	@Override
	public List<JSONObject> boTreeData(String key) {
		return businessObjectManager.boTreeData(key);
	}

	@Override
	public String getBoOverallArrangement(String key) {
		return businessObjectManager.getOverallArrangementByCode(key);
	}

}
