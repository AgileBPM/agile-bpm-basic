package com.dstz.sys.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.sys.api.model.calendar.WorkCalenDar;
import com.dstz.sys.api.service.CalendarService;
import com.dstz.sys.core.manager.WorkCalenDarManager;

import cn.hutool.core.math.MathUtil;

/**
 * 日历服务接口
 * @author Administrator
 *
 */
@Service
public class DefaultCalendarService implements CalendarService{ 
	protected Logger LOG = LoggerFactory.getLogger(this.getClass());
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

	@Override
	public ResultMsg<Date> getEndWorkDayByMinute(Date startDay, int minute) {
		if(minute <1) throw new BusinessMessage("minute  canot be null ");
		
		Calendar startDayHours =Calendar.getInstance(); 
		startDayHours.setTime(startDay);
		// 把 startDay 的时分 也计算进去
		minute = minute +   startDayHours.get(Calendar.HOUR_OF_DAY) * 60;
		minute = minute + startDayHours.get(Calendar.MINUTE) ;
		
		// 计算出 天数 
		int days =new Double(Math.floor(minute/(60*24))).intValue() ;
		int hours  =new Double(Math.floor(  (minute - days * (60 * 24)) / 60)).intValue();
		int minutes  =	minute - days * (60 * 24) - hours * 60;

		Date calcDate = workCalenDarManager.getWorkDayByDays(startDay, days);
		if(calcDate == null) {
			LOG.warn("日期计算异常！ getEndWorkDayByMinute  {} {} ",startDay,minute);
			return new ResultMsg<Date>(null);
		}
		
		Calendar calcCalendar =Calendar.getInstance(); 
		calcCalendar.setTime(calcDate);
		calcCalendar.set(Calendar.HOUR_OF_DAY, hours);
		calcCalendar.set(Calendar.MINUTE, minutes);
		
		return new ResultMsg<Date>(calcCalendar.getTime());
	}
		
	public static void main(String[] args) {
		Calendar startDayHours =Calendar.getInstance();//  startDay.getHours();
		startDayHours.setTime(new Date());
		int a = startDayHours.get(Calendar.MINUTE);
		
		System.out.println(a);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}