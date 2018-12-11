package com.dstz.bus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstz.bus.api.model.IBusinessTable;
import com.dstz.bus.api.service.IBusinessTableService;
import com.dstz.bus.manager.BusinessTableManager;

/**
 * <pre>
 * 描述：IBusinessTableService实现类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月26日 下午6:06:11
 * 版权:summer
 * </pre>
 */
@Service
public class BusinessTableService implements IBusinessTableService {
	@Autowired
	BusinessTableManager businessTableManager;

	@Override
	public IBusinessTable getByKey(String key) {
		return businessTableManager.getByKey(key);
	}

	@Override
	public IBusinessTable getFilledByKey(String key) {
		return businessTableManager.getFilledByKey(key);
	}

}
