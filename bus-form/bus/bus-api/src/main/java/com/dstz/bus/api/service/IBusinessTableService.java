package com.dstz.bus.api.service;

import com.dstz.bus.api.model.IBusinessTable;

/**
 * <pre>
 * 描述：业务表对其他模块的service
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月26日 下午6:03:05
 * 版权:summer
 * </pre>
 */
public interface IBusinessTableService {
	/**
	 * <pre>
	 * 根据key获取businessTable
	 * </pre>
	 * 
	 * @param key
	 * @return
	 */
	IBusinessTable getByKey(String key);
	
	/**
	 * <pre>
	 * 获取填充好所有数据的表
	 * </pre>
	 * @param key
	 * @return
	 */
	IBusinessTable getFilledByKey(String key);
	
}
