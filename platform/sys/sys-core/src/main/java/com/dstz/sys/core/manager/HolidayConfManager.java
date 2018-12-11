package com.dstz.sys.core.manager;

import java.util.Date;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.HolidayConf;

/**
 * 
 * <pre> 
 * 描述：节假日配置 处理接口
 * @author jeff
 * </pre>
 */
public interface HolidayConfManager extends Manager<String, HolidayConf>{
	public HolidayConf queryOne(String name, Date startDay, Date endDay);
}
