package com.dstz.sys.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.sys.api.model.calendar.WorkCalenDar;
import com.dstz.sys.api.service.CalendarService;
import com.dstz.sys.core.manager.WorkCalenDarManager;

/**
 * 日历服务接口
 * @author Administrator
 *
 */
@Service
public class DefaultCalendarService implements CalendarService{ 
	@Resource
	private WorkCalenDarManager workCalenDarManager;

	/**
	 * 获取某一天日历信息
	 * 可以判断是否为工作日，日历详情
	 * @param day
	 * @return
	 */
	@CatchErr("获取日历信息失败")
	public ResultMsg<WorkCalenDar> getWorkCalenDarByDay(Date day){
		WorkCalenDar workCalenDar = workCalenDarManager.getByDayAndSystem(day, WorkCalenDar.SYSTEM_PUBLIC);
		return new ResultMsg<WorkCalenDar>(workCalenDar);
	}
	
	@CatchErr("获取日历信息失败")
	public ResultMsg<WorkCalenDar> getWorkCalenDarByDay(Date day, String system){
		WorkCalenDar workCalenDar = workCalenDarManager.getByDayAndSystem(day, system);
		return new ResultMsg<WorkCalenDar>(workCalenDar);
	}
	
	
	/**
	 * 通过时间区间返回
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	@CatchErr
	public ResultMsg<List<WorkCalenDar>> getWorkCalenDars(Date startDay,Date endDay){
		if(startDay.after(endDay)){
			throw new BusinessMessage("开始日期不应该晚于结束日期", BaseStatusCode.PARAM_ILLEGAL);
		}
		List workCalenDars = workCalenDarManager.getByTime(startDay, endDay);
		
		return new ResultMsg<List<WorkCalenDar>>(workCalenDars);
	}
	
	@CatchErr
	public ResultMsg<List<WorkCalenDar>> getWorkCalenDars(Date startDay,Date endDay, String system){
		if(startDay.after(endDay)){
			throw new BusinessMessage("开始日期不应该晚于结束日期", BaseStatusCode.PARAM_ILLEGAL);
		}
		List workCalenDars = workCalenDarManager.getByTimeContainPublic(startDay, endDay, system);
		return new ResultMsg<List<WorkCalenDar>>(workCalenDars);
	}
	
	/**
	 * 获取指定工作日，N天数后的工作日期
	 * @param startDay
	 * @param days
	 * @return
	 */
	@CatchErr
	public ResultMsg<Date> getEndWorkDay(Date startDay,int days){
		
		Date date = workCalenDarManager.getWorkDayByDays(startDay, days);
		return new ResultMsg<Date>(date);
	}
	
	@CatchErr
	public ResultMsg<Date> getEndWorkDay(Date startDay,int days, String system){
		
		Date date = workCalenDarManager.getWorkDayByDays(startDay, days, system);
		return new ResultMsg<Date>(date);
	}
		
}