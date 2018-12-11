package com.dstz.sys.core.manager;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.dstz.base.manager.Manager;
import com.dstz.sys.api.model.calendar.WorkCalenDar;
import com.dstz.sys.core.model.HolidayConf;

/**
 * 
 * <pre> 
 * 描述：工作日历记录表 处理接口
 * 作者:miao
 * 邮箱:miaojifang@ddjf.com.cn
 * </pre>
 */
public interface WorkCalenDarManager extends Manager<String, WorkCalenDar>{
	/**
	 * 初始化某年的工作日历
	 * @param year
	 */
	public void initWorkCalenDarRecord(int year) ;
	
	/**
	 * 通过开始时间，结束时间获取工作日历
	 * @param starDay大于  小于endDay
	 */
	public List<WorkCalenDar> getByTime(Date startDay,Date endDay);
	
	/**
	 * 获取开始日期N工作日后的日期
	 * @param startDay
	 * @param days
	 * @return
	 * @throws ParseException 
	 */
	public Date getWorkDayByDays(Date startDay, int days);
	
	
	public List<WorkCalenDar> getByTime(Date day);
	
	/**
	 * 创建假日设置的时候更新
	 * @param conf
	 * @throws ParseException 
	 */
	public void updateWhenHolidayConfCreate(HolidayConf conf);
	
	public void updateWhenHolidayConfUpd(HolidayConf oldConf,HolidayConf newConf);
	
	public WorkCalenDar getByDayAndSystem(Date day, String system);

	void updateWorkType(Date startDay, Date endDay, String isWorkDay, String type);
	
	public void clearWorkCalenDarByHoliday(HolidayConf conf);

	List<WorkCalenDar> getByTime(Date startDay, Date endDay, String system);

	Date getWorkDayByDays(Date startDay, int days, String system);

	List<WorkCalenDar> getByTimeContainPublic(Date startDay, Date endDay, String system);

	
}
