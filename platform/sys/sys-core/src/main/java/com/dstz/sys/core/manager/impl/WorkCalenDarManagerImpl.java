package com.dstz.sys.core.manager.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.api.model.calendar.WorkCalenDar;
import com.dstz.sys.core.dao.WorkCalenDarDao;
import com.dstz.sys.core.manager.HolidayConfManager;
import com.dstz.sys.core.manager.WorkCalenDarManager;
import com.dstz.sys.core.model.HolidayConf;

import cn.hutool.core.date.DateUtil;

/**
 * 
 * <pre> 
 * 描述：c_work_calendar 处理实现类
 * 日期:2017-12-26 11:47:55
 * </pre>
 */
@Service("workCalenDarManager")
public class WorkCalenDarManagerImpl extends BaseManager<String, WorkCalenDar> implements WorkCalenDarManager{
	@Resource
	WorkCalenDarDao workCalenDarDao;
	@Resource
	HolidayConfManager holidayConfManager;
	
	//yyyy-MM-dd
	@Override
	public void initWorkCalenDarRecord(int year) {
		 Calendar calendarStart =Calendar.getInstance();
		 calendarStart.set(year, 0, 1);
		 
		 Calendar calendarEnd = Calendar.getInstance(); 
		 calendarEnd.set(year+1, 0, 1);
		 
		List<WorkCalenDar> workCalenDarList = getByTime(calendarStart.getTime());
		if(BeanUtils.isNotEmpty(workCalenDarList)){
			throw new BusinessMessage("当前年份已经初始化过");
		}
		 
		while(calendarStart.before(calendarEnd)){
			 int week=calendarStart.get(Calendar.DAY_OF_WEEK)-1;
			 WorkCalenDar calenDar = new WorkCalenDar();
			 calenDar.setId(IdUtil.getSuid());
			 calenDar.setDay(calendarStart.getTime());
			 
			//0代表周日，6代表周六  
			 if(week ==6 || week==0){
				 calenDar.setIsWorkDay(WorkCalenDar.HOLIDAY);
				 calenDar.setType(WorkCalenDar.DAY_TYPE_WEEKEND);
			 }else{
				 calenDar.setIsWorkDay(WorkCalenDar.WORKDAY);
				 calenDar.setType(WorkCalenDar.DAY_TYPE_WORKDAY);
			 }
			 workCalenDarDao.create(calenDar);
			 calendarStart.add(Calendar.DATE, 1);
		}
	}
    /**
     * 获取开始时间结束时间之内的日期列表
     */
	@Override
	public List<WorkCalenDar> getByTime(Date startDay, Date endDay) {
		return workCalenDarDao.getByPeriod(startDay, endDay);
	}
	/**
	 * 获取指定系统的开始时间结束时间之内的日期列表
	 */
	@Override
	public List<WorkCalenDar> getByTime(Date startDay, Date endDay, String system){
		return workCalenDarDao.getByPeriodAndSystem(startDay, endDay, system);
	}
	/**
	 * 获取指定系统的开始时间结束时间之内的日期列表,系统未设置的时间返回共有日历
	 */
	@Override
	public List<WorkCalenDar> getByTimeContainPublic(Date startDay, Date endDay, String system){
		return workCalenDarDao.getByTimeContainPublic(startDay, endDay, system);
	}
	/**
	 * 获取某日期的所有系统日期列表
	 */
	@Override
	public List<WorkCalenDar> getByTime(Date day) {
		return workCalenDarDao.getByDay(day);
	}

    /**
     * 获取n个工作日后的工作日期
     */
	@Override
	public Date getWorkDayByDays(Date startDay, int days) {
		return workCalenDarDao.getWorkDayByDays(startDay, days);
	}
	
	@Override
	public Date getWorkDayByDays(Date startDay, int days, String system) {
		return workCalenDarDao.getWorkDayByDays(startDay, days, system);
	}
	
	/**
	 * 通过假日配置更新工作日历
	 */
	@Override
	public void updateWhenHolidayConfCreate(HolidayConf conf){
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(conf.getStartDay());
		end.setTime(conf.getEndDay());
		//判断开始日期是否大于结束日期
		if(start.compareTo(end) > 0) {
			throw new BusinessMessage("开始日期大于结束日期");
		}
		
		List<WorkCalenDar> workCalenDars = getByTime(conf.getStartDay(), conf.getEndDay(), conf.getSystem());
		
		//法定节假日情况
		if(WorkCalenDar.SYSTEM_PUBLIC.equals(conf.getSystem())) {
			//检查
			if(workCalenDars.isEmpty() || workCalenDars.get(0).getDay().compareTo(conf.getStartDay()) != 0 || workCalenDars.get(workCalenDars.size() - 1).getDay().compareTo(conf.getEndDay()) != 0) {
				this.initWorkCalenDarRecord(conf.getYear());
				//throw new BusinessException("含有未初始化年份，请先初始化");
			}
			for(WorkCalenDar workCalenDar : workCalenDars) {
				String type = workCalenDar.getType();
				//更新前检查：判断是否已设置过法定日期,已设置过的法定日期与要添加的法定日期类型不同，则不允许添加
				if(type.length() > 2 && !(type.substring(type.length() - 2, type.length()).equals(conf.getType()))) {
					throw new BusinessException("该时间段有日期已设定过不同法定节假日类型，请删除后添加或直接更新");
				}
			}
			//更新
			for(WorkCalenDar workCalenDar : workCalenDars) {
				//节假日
				if(WorkCalenDar.DAY_TYPE_LEGAL_HOLIDAY.equals(conf.getType())) {
					workCalenDar.setIsWorkDay(WorkCalenDar.HOLIDAY);
				} else if(WorkCalenDar.DAY_TYPE_LEGAL_WORKDAY.equals(conf.getType())) {
					workCalenDar.setIsWorkDay(WorkCalenDar.WORKDAY);
				} else {
					throw new BusinessException("系统与类型不一致");
				}
				workCalenDar.setType(workCalenDar.getType() + conf.getType());
				workCalenDarDao.update(workCalenDar);
			}
		//非法定（公司）
		} else {
			//检查
			if(!workCalenDars.isEmpty()) {
				throw new BusinessException("该时间段有日期已设定过公司节假日，请删除后添加或直接更新");
			}
			//新增记录
			while(start.compareTo(end) <= 0) {
				WorkCalenDar workCalenDar = new WorkCalenDar();
				//---------------需要设置id吗？
				workCalenDar.setDay(start.getTime());
				if(WorkCalenDar.DAY_TYPE_COMPAY_HOLIDAY.equals(conf.getType())) {
					workCalenDar.setIsWorkDay(WorkCalenDar.HOLIDAY);
				}
				else if(WorkCalenDar.DAY_TYPE_COMPAY_WORKDAY.equals(conf.getType())){
					workCalenDar.setIsWorkDay(WorkCalenDar.WORKDAY);
				} else {
					throw new BusinessException("系统与类型不一致");
				}
				workCalenDar.setType(conf.getType());
				workCalenDar.setSystem(conf.getSystem());
				workCalenDarDao.create(workCalenDar);
				start.add(Calendar.DAY_OF_MONTH, 1);
			}
		}

	}
	
	/**
	 * 通过假日配置更新工作日历
	 * @param oldConf 更新工作日历前先将旧的工作日历还原
	 * @param newConf 通过更新节假对日历进行更新
	 */
	@Override
	public void updateWhenHolidayConfUpd(HolidayConf oldConf, HolidayConf newConf) {
		// TODO Auto-generated method stub
		clearWorkCalenDarByHoliday(oldConf);
		updateWhenHolidayConfCreate(newConf);
	}
	
	// 清除工作日历的节假日处理
	public void clearWorkCalenDarByHoliday(HolidayConf conf){
		List<WorkCalenDar> workCalenDars = getByTime(conf.getStartDay(), conf.getEndDay(), conf.getSystem());
		if(!workCalenDars.isEmpty()) {
			//判断是否public 
			if(WorkCalenDar.SYSTEM_PUBLIC.equals(conf.getSystem())) {
				for(WorkCalenDar workCalenDar : workCalenDars) {
					String type = workCalenDar.getType();
					//判断是否设置过节假日
					if(type.length() > 2) {
						type = type.substring(0, type.length()-2);
						String keyType = type.substring(type.length()-2, type.length());
						if(keyType.equals(WorkCalenDar.DAY_TYPE_LEGAL_HOLIDAY) || keyType.equals(WorkCalenDar.DAY_TYPE_WEEKEND)) {
							workCalenDar.setIsWorkDay(WorkCalenDar.HOLIDAY);
						} else {
							workCalenDar.setIsWorkDay(WorkCalenDar.WORKDAY);
						}
						workCalenDar.setType(type);
						workCalenDarDao.update(workCalenDar);
					}
				}
			} else {
				for(WorkCalenDar workCalenDar : workCalenDars) {
					workCalenDarDao.remove(workCalenDar.getId());
				}
			}
		}
	}
	
	
	@Override
	public void create(WorkCalenDar entity){
		throw new BusinessException("不支持创建", BaseStatusCode.NO_ACCESS);
	}
	@Override
	public void update(WorkCalenDar entity){
		throw new BusinessException("不支持更新", BaseStatusCode.NO_ACCESS);
	}
	
	@Override
	public WorkCalenDar getByDayAndSystem(Date day, String system){
		List<WorkCalenDar> workCalenDars = workCalenDarDao.getByDayAndSystem(day, system);
		if(null != workCalenDars && !workCalenDars.isEmpty()) {
			return workCalenDars.get(0);
		}
		List<WorkCalenDar> workCalenDars1 = workCalenDarDao.getByDayAndSystem(day, WorkCalenDar.SYSTEM_PUBLIC);
		if(null != workCalenDars1 && !workCalenDars1.isEmpty()) {
			return workCalenDars1.get(0);
		} else {
			throw new BusinessException("年份未初始化", BaseStatusCode.NO_ACCESS);
		}
	}
	
	@Override
	public void updateWorkType(Date startDay, Date endDay, String isWorkDay, String type) {
		workCalenDarDao.updateWorkType(startDay, endDay, isWorkDay, type);
	}
	
	private boolean initialized(Date day) {
		List<WorkCalenDar> workCalenDars = workCalenDarDao.getByDayAndSystem(day, WorkCalenDar.SYSTEM_PUBLIC);
		if(null != workCalenDars && !workCalenDars.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	

public static void main(String[] args) {
	int year = 2017;
	 Calendar calendarStart =Calendar.getInstance();
	 calendarStart.set(year, 0, 1);
	 
	 Calendar calendarEnd = Calendar.getInstance(); 
	 calendarEnd.set(year+1, 0, 1);
	 int a = 0;
	while(calendarStart.before(calendarEnd)){
		a++;
		 int week=calendarStart.get(Calendar.DAY_OF_WEEK)-1;
		 WorkCalenDar calenDar = new WorkCalenDar();
		 calenDar.setDay(calendarStart.getTime());
		 
		//0代表周日，6代表周六  
		 if(week ==6 || week==0){
			 calenDar.setIsWorkDay(WorkCalenDar.HOLIDAY);
			 calenDar.setType("周末");
		 }else{
			 calenDar.setIsWorkDay(WorkCalenDar.WORKDAY);
			 calenDar.setType("工作日");
		 }
		 
		 
		 System.out.println(DateUtil.formatDate(calenDar.getDay()));
		 System.out.println(calenDar);
		 
		 calendarStart.add(Calendar.DATE, 1);
	}
	
	System.out.println(a);
}



}
