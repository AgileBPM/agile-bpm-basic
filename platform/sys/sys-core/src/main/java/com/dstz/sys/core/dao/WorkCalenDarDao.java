package com.dstz.sys.core.dao;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.api.model.calendar.WorkCalenDar;

/**
 *  jeff 
 */
public interface WorkCalenDarDao extends BaseDao<String, WorkCalenDar> {

	List<WorkCalenDar> getByDay(@Param("day")Date day);
	
	List<WorkCalenDar> getByPeriod(@Param("startDay")Date startDay, @Param("endDay")Date endDay);
	
	List<WorkCalenDar> getWorkDayByDays(@Param("startDay")Date startDay);
	
	List<WorkCalenDar> getWorkDayByDaysAndSystem(@Param("day")Date day, @Param("system")String system);
	
	public void updateWorkType(@Param("startDay")Date startDay, @Param("endDay")Date endDay, String isWorkDay, String type);

	List<WorkCalenDar> getByPeriodAndSystem(@Param("startDay")Date startDay, @Param("endDay")Date endDay, @Param("system")String system);

	List<WorkCalenDar> getWorkDayByDays(@Param("startDay")Date startDay,String system);

	List<WorkCalenDar> getByTimeContainPublic(@Param("startDay")Date startDay, @Param("endDay")Date endDay, @Param("system")String system);

	List<WorkCalenDar> getByDayAndSystem(Date day, String system);
}
