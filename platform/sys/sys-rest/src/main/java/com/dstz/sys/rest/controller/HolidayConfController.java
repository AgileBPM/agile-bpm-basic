package com.dstz.sys.rest.controller;



import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.sys.core.manager.HolidayConfManager;
import com.dstz.sys.core.manager.WorkCalenDarManager;
import com.dstz.sys.core.model.HolidayConf;

@RestController
@RequestMapping("/calendar/holidayConf")
public class HolidayConfController extends BaseController<HolidayConf>{
	@Resource
	HolidayConfManager holidayConfManager;
	@Resource
	WorkCalenDarManager workCalenDarManager;

	
	/**
	 * 保存c_holiday_conf信息
	 * @param request
	 * @param response
	 * @param holidayConf
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@RequestMapping("save")
	@CatchErr("对节假日操作失败")
	@Override
	public ResultMsg<String> save(@RequestBody HolidayConf holidayConf) throws Exception{
		
		String id=holidayConf.getId();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(holidayConf.getStartDay());
		
		int year = calendar.get(Calendar.YEAR);
		holidayConf.setYear(year);
		
		
		if(StringUtil.isEmpty(id)){
			HolidayConf hc = holidayConfManager.queryOne(holidayConf.getName(),holidayConf.getStartDay(),holidayConf.getEndDay());
			if(hc != null)  throw new BusinessMessage("重复添加,相同日期内只能有一个同名节假日");
			
			holidayConf.setId(IdUtil.getSuid());
			workCalenDarManager.updateWhenHolidayConfCreate(holidayConf);
			holidayConfManager.create(holidayConf);
			return  getSuccessResult("添加节假日成功");
		}else{
			HolidayConf oldConf = holidayConfManager.get(id);
			workCalenDarManager.updateWhenHolidayConfUpd(oldConf, holidayConf);
			holidayConfManager.update(holidayConf);
			return  getSuccessResult("更新节假日成功"); 
		}
	}
	
	
	@RequestMapping("initWorkCalenDar")
	@CatchErr
	public ResultMsg<String> initWorkCalenDar(HttpServletRequest request,HttpServletResponse response){
		int year = RequestUtil.getInt(request, "year");
		if(year < 2000){
			throw new BusinessMessage("初始化日历信息，年份必须大于2000");
		}
	
		workCalenDarManager.initWorkCalenDarRecord(year);
		return getSuccessResult( "初始化"+ year + "年日历成功");
	}

	@Override
	protected String getModelDesc() {
		return "节假日";
	}
	



}
