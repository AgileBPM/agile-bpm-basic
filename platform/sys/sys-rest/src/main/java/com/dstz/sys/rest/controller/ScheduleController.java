package com.dstz.sys.rest.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.sys.api.model.calendar.Schedule;
import com.dstz.sys.api.model.calendar.ScheduleParticipant;
import com.dstz.sys.core.dao.ScheduleDao;
import com.dstz.sys.core.dao.ScheduleParticipantDao;
import com.dstz.sys.core.manager.ScheduleManager;
import com.dstz.sys.core.model.ParticipantScheduleDO;
import com.dstz.sys.util.ContextUtil;



/**
 * 描述：日程 控制器类
 */
@RestController
@RequestMapping("/calendar/schedule")
public class ScheduleController extends BaseController<Schedule>{
	@Resource
	ScheduleManager scheduleManager;
	@Resource
	ScheduleParticipantDao scheduleParticipantDao;
	@Resource
	ScheduleDao scheduleDao;
	
	
	/**
	 * @return 
	 * 保存日程信息
	 * @param request
	 * @param response
	 * @param schedule
	 * @throws Exception 
	 * void
	 * @exception 
	 */
	@CatchErr("对日程操作失败")
	@RequestMapping("save")
	@Override
	public ResultMsg<String> save(@RequestBody Schedule schedule) throws Exception{
		String resultMsg=null;
		String id=schedule.getId();
			if(schedule.getEndTime() != null && schedule.getStartTime() != null && schedule.getEndTime().getTime() <= schedule.getStartTime().getTime()) {
				throw new BusinessMessage("结束日期不能小于开始日期");
			}
			if(schedule.getActualStartTime() != null && schedule.getCompleteTime() != null && schedule.getCompleteTime().getTime() <= schedule.getActualStartTime().getTime()) {
				throw new BusinessMessage("完成时间不能小于实际开始日期");
			}
			if(StringUtil.isEmpty(id)){
				String ownerName = ContextUtil.getCurrentUser().getFullname();
				String owner = ContextUtil.getCurrentUserId();
				schedule.setOwner(owner);
				schedule.setOwnerName(ownerName);
				schedule.setId(IdUtil.getSuid());
				schedule.setRateProgress(0);//--创建时进度为0--
				schedule.setCreateTime(new Date());
				schedule.setUpdateTime(new Date());
				schedule.setUpdateBy(owner);
				schedule.setActualStartTime(schedule.getStartTime());
				schedule.setCreateBy(ownerName);
				scheduleManager.saveSchedule(schedule);
				resultMsg="添加日程成功";
			}else{
				
				scheduleManager.update(schedule);
				resultMsg="更新日程成功";
			}
			
			return  getSuccessResult(resultMsg);
		 
	}
	 
	
	
	/**
	 * 从数据库中获取日程数据(所属者)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getEvents")
	@CatchErr("日程获取失败")
	public List<Schedule> getEvents(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Schedule> list = null;
		String name = ContextUtil.getCurrentUser().getFullname();
		String id = ContextUtil.getCurrentUserId();
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTimeInMillis(Long.valueOf(start));
		endDate.setTimeInMillis(Long.valueOf(end));
		
		try {
			list = scheduleManager.getByPeriodAndOwner(startDate.getTime(), endDate.getTime(), name, id);
		} catch (Exception e) {
			throw new BusinessException("呵呵",BaseStatusCode.NO_ACCESS,e);
		}
		 
		return list;
	}
	/**
	 * 从数据库中获取日程数据(参与者)
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getParticipantEvents")
	@CatchErr("日程获取失败")
	public List<ParticipantScheduleDO> getParticipantEvents(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String name = ContextUtil.getCurrentUser().getFullname();
		String id = ContextUtil.getCurrentUserId();
		
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		startDate.setTimeInMillis(Long.valueOf(start));
		endDate.setTimeInMillis(Long.valueOf(end));
		
		List<ParticipantScheduleDO> list = scheduleManager.getParticipantEvents(startDate.getTime(),endDate.getTime(), name, id);
		return list;
	}
	
	
	/**
	 * 日程表上选中保存
	 * @return 
	 */
	@RequestMapping("saveOwn")
	@CatchErr("日程保存失败")
	public ResultMsg<String> saveOwn(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		
		String end = request.getParameter("end");
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		
		startDate.setTimeInMillis(Long.valueOf(start)-8*60*60*1000);//格林乔治时间减8小时调整为中国时间
		endDate.setTimeInMillis(Long.valueOf(end)-8*60*60*1000);
		
		Schedule schedule = new Schedule();
		schedule.setTitle(title);
		schedule.setStartTime(startDate.getTime());
		schedule.setEndTime(endDate.getTime());
		schedule.setRateProgress(0);
		schedule.setCreateTime(new Date());
		scheduleManager.create(schedule);
		
		return getSuccessResult("日程保存成功");
	}
	/**
	 * 日程表拖拽更新
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("dragUpdate")
	@CatchErr("日程拖拽失败")
	public ResultMsg<String> dargUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.setTimeInMillis(Long.valueOf(start)-8*60*60*1000);//格林乔治时间减8小时调整为中国时间
			endDate.setTimeInMillis(Long.valueOf(end)-8*60*60*1000);//格林乔治时间减8小时调整为中国时间
			
			Schedule schedule = new Schedule();
			schedule.setId(id);
			schedule.setStartTime(startDate.getTime());
			schedule.setEndTime(endDate.getTime());
			scheduleManager.dragUpdate(schedule);
			
			return getSuccessResult("日程拖拽成功");
	}
	
	/**
	 * 完成日程任务  ??? wtf 
	 * @param request
	 * @param response
	 * @param schedule
	 * @return 
	 * @throws Exception
	 */
	@CatchErr("完成日程失败")
	@RequestMapping("completeTask")
	public ResultMsg<String> completeTask(HttpServletRequest request,HttpServletResponse response,@RequestBody Schedule schedule2) throws Exception{
		String participantId = request.getParameter("id");
			//String id = request.getParameter("id");
			String type = schedule2.getType();
			String comment = schedule2.getRemark();
			Date actualStart = schedule2.getActualStartTime();
			String subName = ContextUtil.getCurrentUser().getFullname();
			String subId = ContextUtil.getCurrentUserId();
			Date date = new Date();
			//int rate = schedule2.getRateProgress();
			//--single情况下是所属
			if(Schedule.TYPE_SINGLE.equals(type)) {
				if(schedule2.getCompleteTime() != null && schedule2.getActualStartTime() != null && schedule2.getCompleteTime().getTime() <= schedule2.getActualStartTime().getTime()) {
					throw new BusinessMessage("完成时间不能小于实际开始日期");
				}
				if(schedule2.getRateProgress() >= 100 ) {
					schedule2.setRateProgress(100);
					if(schedule2.getCompleteTime() == null) {
						schedule2.setCompleteTime(new Date());
					}
				}
			}
			//--share情况下只有参与者才能完成，故id为参与者
			if(Schedule.TYPE_SHARE.equals(type)) {
				List<ScheduleParticipant> list = schedule2.getScheduleParticipantList();
				int rate = 0;
				Date completeTime = null;
				for(ScheduleParticipant participant : list) {
					if(participant.getId().equals(participantId)) {
						if(participant.getCompleteTime() != null && participant.getActualStartTime() != null && participant.getCompleteTime().getTime() <= participant.getActualStartTime().getTime()) {
							throw new BusinessMessage("完成时间不能小于实际开始日期");
						}
						rate = participant.getRateProgress();
						participant.setUpdateTime(new Date());
						completeTime = participant.getCompleteTime();
						break;
					}
				}
				// 共同任务。一人修改、所有人同步？？？
				for(ScheduleParticipant participant : list) {
					if(rate >= 100) {
						participant.setRateProgress(100);
						participant.setCompleteTime(completeTime == null ? date : completeTime);
					} else {
						participant.setRateProgress(rate);
					}
				}
				if(rate >= 100) {
					schedule2.setRateProgress(100);
					schedule2.setCompleteTime(completeTime == null ? date : completeTime);
				} else {
					schedule2.setRateProgress(rate);
				}
				
			}
			if(Schedule.TYPE_ILKA.equals(type)) {
				List<ScheduleParticipant> list = schedule2.getScheduleParticipantList();
				int mainRate = 0;
				Date completeDate = null;
				for(ScheduleParticipant participant : list) {
					mainRate += participant.getRateProgress();
					if(participant.getId().equals(participantId)) {
						if(participant.getCompleteTime() != null && participant.getActualStartTime() != null && participant.getCompleteTime().getTime() <= participant.getActualStartTime().getTime()) {
							throw new BusinessMessage("完成时间不能小于实际开始日期");
						}
						completeDate = participant.getCompleteTime();
						if(participant.getRateProgress() >= 100 && completeDate == null) {
							participant.setCompleteTime(new Date());
						}
					}
				}
				int rate = (int)Math.round(mainRate * (1.0 / list.size()));
				if(rate >= 100) {
					schedule2.setRateProgress(100);
					if(completeDate != null) {
						schedule2.setCompleteTime(completeDate);
					} else {
						schedule2.setCompleteTime(new Date());
					}
				} else {
					schedule2.setRateProgress(rate);
				}
			}
			schedule2.setSubmitNamer(subName);
			schedule2.setSubmitter(subId);
			schedule2.setUpdateTime(new Date());
			scheduleManager.update(schedule2);
			
			return getSuccessResult("操作成功");
	}
	
//	/**
//	 * 完成日程
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
////	@RequestMapping(value="completee",produces = "application/json;utf-8")
//	@RequestMapping("complete")
//	public void complete(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String resultMsg=null;
//		try {
//			String id = request.getParameter("id");
//			String type = request.getParameter("type");
////			String comment = request.getParameter("comment");
//			String subName = ContextUtil.getCurrentUser().getFullname();
//			String subId = ContextUtil.getCurrentUserId();
//			Date date = new Date();
//			//--single情况下是所属者
//			if(ISchedule.TYPE_SINGLE.equals(type)) {
//				Schedule schedule = new Schedule();
//				schedule.setId(id);
//				schedule.setRateProgress(100);
//				schedule.setCompleteTime(date);
//				schedule.setUpdateTime(date);
//				schedule.setSubmitNamer(subName);
//				schedule.setSubmitter(subId);
//				scheduleManager.updateOnlySchedule(schedule);
//			}
//			//--share情况下只有参与者才能完成，故id为参与者
//			if(ISchedule.TYPE_SHARE.equals(type) || ISchedule.TYPE_ILKA.equals(type)) {
//				ScheduleParticipant scheduleParticipant = scheduleParticipantDao.get(id);
//				String mainId = scheduleParticipant.getScheduleId();
//				Schedule schedule = scheduleManager.get(mainId);
//				schedule.setRateProgress(100);
//				schedule.setCompleteTime(date);
//				schedule.setUpdateTime(date);
//				schedule.setSubmitNamer(subName);
//				schedule.setSubmitter(subId);
//				scheduleManager.updateOnlySchedule(schedule);
//				List<ScheduleParticipant> list = scheduleParticipantDao.getByMainId(mainId);
//				for(ScheduleParticipant participant : list) {
//					participant.setRateProgress(100);
//					participant.setCompleteTime(date);
//					participant.setUpdateBy(subId);
//					participant.setUpdateTime(date);
////					if(id.equals(participant.getId())) {
////						participant.setSubmitComment(comment);
////					}
//					scheduleParticipantDao.update(participant);
//				}
//				
//			}
////			if(ISchedule.TYPE_ILKA.equals(type)) {
////				ScheduleParticipant scheduleParticipant = scheduleParticipantDao.get(id);
////				scheduleParticipant.setRateProgress(100);
////				scheduleParticipant.setCompleteTime(date);
////				scheduleParticipant.setUpdateBy(subId);
////				scheduleParticipant.setUpdateTime(date);
////				scheduleParticipant.setSubmitComment(comment);
////				scheduleParticipantDao.update(scheduleParticipant);
////				String mainId = scheduleParticipant.getScheduleId();
////				Schedule schedule = scheduleManager.get(mainId);
////				String participantNames = schedule.getParticipantNames();
////				String[] names = participantNames.split(",");
////				int numberOfParticipants = names.length;
////				int mainProgress = (int) Math.round(100.0 * (1.0 / numberOfParticipants));
////				if(mainProgress + schedule.getRateProgress() >= 100) {
////					schedule.setRateProgress(100);
////					schedule.setCompleteTime(date);
////				} else {
////					schedule.setRateProgress(mainProgress + schedule.getRateProgress());
////				}
////				
////				schedule.setUpdateTime(date);
////				schedule.setSubmitNamer(subName);
////				schedule.setSubmitter(subId);
////				scheduleManager.updateOnlySchedule(schedule);
////			}
//			
//		} catch (Exception e) {
//			resultMsg="完成日程失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
//		}
//	}
//	/**
//	 * 完成参与者日程进度
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping("completePart")
//	public void completePart(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		String resultMsg=null;
//		try {
//			String id = request.getParameter("id");
//			Schedule schedule = new Schedule();
//			Date date = new Date();
//			schedule.setId(id);
//			schedule.setRateProgress(100);
//			schedule.setCompleteTime(date);
//			schedule.setUpdateTime(date);
//			scheduleManager.updateOnlySchedule(schedule);
//		} catch (Exception e) {
//			resultMsg="完成日程失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
//		}
//	}
	/**
	 * 删除参与者
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@CatchErr("删除参与者失败")
	@RequestMapping("itemDelete")
	public ResultMsg<String> itemDelete(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String id = request.getParameter("id");
			ScheduleParticipant scheduleParticipant = scheduleParticipantDao.get(id);
			String mainId = scheduleParticipant.getScheduleId();
			String participantName = scheduleParticipant.getParticipantorName();
			Schedule schedule = scheduleManager.get(mainId);
			String[] participantNames = schedule.getParticipantNames().split(",");
			//List<String> list = Arrays.asList(participantNames);
			//list.remove(paticipantName);
			List<String> newNames = new ArrayList<>();
			for(int i = 0; i < participantNames.length; i++) {
				if(!participantName.equals(participantNames[i])) {
					newNames.add(participantNames[i]);
				}
			}
			String newNamesString = "";
			for(int i = 0; i < newNames.size(); i++) {
				newNamesString += newNames.get(i);
				if(i < newNames.size() -1) {
					newNamesString += ",";
				}
			}
			schedule.setParticipantNames(newNamesString);
			schedule.setUpdateTime(new Date());
			schedule.setUpdateBy(ContextUtil.getCurrentUserId());
			scheduleParticipantDao.remove(id);
			scheduleManager.updateOnlySchedule(schedule);
			//scheduleParticipantDao.remove(id);
		 
			return getSuccessResult("删除参与者成功");
	}
//	/**
//	 * 添加参与者
//	 * @param request
//	 * @param response
//	 * @param scheduleParticipant
//	 * @throws Exception
//	 */
//	@RequestMapping("saveParticipant")
//	public void saveParticipant(HttpServletRequest request,HttpServletResponse response,@RequestBody ScheduleParticipant scheduleParticipant) throws Exception{
//		String resultMsg=null;
//		String scheduleId = request.getParameter("scheduleId");
//		String id=scheduleParticipant.getId();
//		try {
//			if(StringUtil.isEmpty(id)){
//				scheduleParticipant.setId(UniqueIdUtil.getSuid());
//				if(scheduleId != null) {
//					scheduleParticipant.setScheduleId(scheduleId);
//				}
//				scheduleParticipant.setRateProgress(0);//--创建时进度为0--
//				scheduleParticipantDao.create(scheduleParticipant);
//				resultMsg="添加参与者成功";
//			}
//			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.SUCCESS);
//		} catch (Exception e) {
//			resultMsg="添加参与者失败";
//			writeResultMessage(response.getWriter(),resultMsg,e.getMessage(),ResultMessage.FAIL);
//		}
//	}



@Override
protected String getModelDesc() {
	return "日程";
}
}
