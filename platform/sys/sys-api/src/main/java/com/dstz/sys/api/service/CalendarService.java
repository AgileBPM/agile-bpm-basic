package com.dstz.sys.api.service;

import java.util.Date;
import java.util.List;

import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.sys.api.model.calendar.WorkCalenDar;


public interface CalendarService{ 

	/**
	 * 获取某一天日历信息
	 * 可以判断是否为工作日，日历详情
	 * @param day
	 * @return
	 */
	public ResultMsg<WorkCalenDar> getWorkCalenDarByDay(Date day);
	
	public ResultMsg<WorkCalenDar> getWorkCalenDarByDay(Date day, String system);
	
	
	/**
	 * 通过时间区间返回
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public ResultMsg<List<WorkCalenDar>> getWorkCalenDars(Date startDay,Date endDay);
	
	public ResultMsg<List<WorkCalenDar>> getWorkCalenDars(Date startDay,Date endDay, String system);

	/**
	 * 获取指定工作日，N天数后的工作日期
	 * @param startDay
	 * @param days
	 * @return
	 */
	public ResultMsg<Date> getEndWorkDay(Date startDay,int days);
	
	public ResultMsg<Date> getEndWorkDay(Date startDay,int days, String system);
	
	/**
	 * 获取多少分钟后的天数
	 * @param startDay
	 * @param minute
	 * @return
	 */
	public ResultMsg<Date> getEndWorkDayByMinute(Date startDay,int minute);
	
	
}