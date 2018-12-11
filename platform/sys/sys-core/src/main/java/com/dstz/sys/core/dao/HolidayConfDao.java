package com.dstz.sys.core.dao;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.HolidayConf;

public interface HolidayConfDao extends BaseDao<String, HolidayConf> {
	public HolidayConf queryOne(@Param("name")String name,@Param("startDay") Date startDay, @Param("endDay")Date endDay);
}
