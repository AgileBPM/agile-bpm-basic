package com.dstz.sys.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.aop.annotion.ParamValidate;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.model.calendar.Schedule;
import com.dstz.sys.api.model.calendar.ScheduleParticipant;
import com.dstz.sys.api.model.calendar.dto.CompleteScheduleModel;
import com.dstz.sys.api.model.calendar.dto.CreateScheduleModel;
import com.dstz.sys.api.service.ScheduleService;
import com.dstz.sys.core.dao.ScheduleBizDao;
import com.dstz.sys.core.dao.ScheduleParticipantDao;
import com.dstz.sys.core.manager.ScheduleManager;
import com.dstz.sys.core.model.ScheduleBiz;

@Transactional
@Service
public class DefaultScheduleService implements ScheduleService {
	@Resource
	ScheduleManager scheduleManager;
	@Resource
	ScheduleBizDao scheduleBizDao;
	@Resource
	ScheduleParticipantDao scheduleParticipantDao;
	/**
	 * 获取一段时间内特定来源的日程 
	 */
	@Override
	@CatchErr
	public ResultMsg<List<Schedule>> getSchedulesByTime(Date start, Date end, String source) {
		List<Schedule> schedules =  scheduleManager.getByPeriodAndSource(start, end, source);
		if(schedules != null) {
			for(Schedule schedule : schedules) {
				if(!Schedule.TYPE_SINGLE.equals(schedule.getType())) {
					List<ScheduleParticipant> scheduleParticipants = scheduleParticipantDao.getScheduleParticipantList(schedule.getId());
					schedule.setScheduleParticipantList(scheduleParticipants);
				}
			}
		}
		List list = schedules;
		return new ResultMsg<List<Schedule>>(list);
	}
	/**
	 * 创建日程
	 */
	@Override
	@CatchErr
	@ParamValidate
	public ResultMsg createSchedule(CreateScheduleModel scheduleModel) {
		ResultMsg resultMsg = new ResultMsg();
		Schedule schedule = new Schedule();
		ScheduleBiz scheduleBiz = new ScheduleBiz();
		ScheduleParticipant scheduleParticipant = new ScheduleParticipant();
		String scheduleId = IdUtil.getSuid();
		//设置schedule
		schedule.setId(scheduleId);
		schedule.setTitle(scheduleModel.getTitle());
		schedule.setRemark(scheduleModel.getRemark());
		schedule.setRateProgress(0);
		schedule.setStartTime(scheduleModel.getStartTime());
		schedule.setActualStartTime(scheduleModel.getStartTime());
		schedule.setEndTime(scheduleModel.getEndTime());
		if(scheduleModel.getTaskUrl() != null) {
			schedule.setTaskUrl(scheduleModel.getTaskUrl());
		}
		if(scheduleModel.getOpenType() != null) {
			schedule.setOpenType(scheduleModel.getOpenType());
		}
		if(scheduleModel.getOwner() != null) {
			schedule.setOwnerName(scheduleModel.getOwner().getFullname());
			schedule.setOwner(scheduleModel.getOwner().getAccount());
			schedule.setCreateBy(scheduleModel.getOwner().getFullname());
			schedule.setUpdateBy(scheduleModel.getOwner().getAccount());
		}
		if(scheduleModel.getOwnerAccount() != null) {
			schedule.setOwner(scheduleModel.getOwnerAccount());
			schedule.setCreateBy(scheduleModel.getOwnerAccount());
			schedule.setUpdateBy(scheduleModel.getOwnerAccount());
		}
		schedule.setType(scheduleModel.getType());
		schedule.setCreateTime(new Date());
		schedule.setUpdateTime(new Date());
		//--有参与者情况下设置参与者表--
		if(!Schedule.TYPE_SINGLE.equals(scheduleModel.getType()) && scheduleModel.getUser() != null) {
			List<ScheduleParticipant> list = new ArrayList<>();
			for(IUser iUser : scheduleModel.getUser()) {
				scheduleParticipant.setScheduleId(scheduleId);
				scheduleParticipant.setParticipantorName(iUser.getFullname());
				scheduleParticipant.setParticipantor(iUser.getAccount());
				scheduleParticipant.setCreateTime(new Date());
				scheduleParticipant.setActualStartTime(scheduleModel.getStartTime());
				scheduleParticipant.setRateProgress(0);
				//scheduleParticipantDao.create(scheduleParticipant);
				list.add(scheduleParticipant);
			}
			schedule.setScheduleParticipantList(list);
		}
		//--设置scheduleBiz--
		scheduleBiz.setBizId(scheduleModel.getBizId());
		scheduleBiz.setScheduleId(scheduleId);
		//？？？？？？？？？？？？？？？？？？？？？？？？是不是要加个source字段?
		scheduleBiz.setSource(scheduleModel.getSource());
		scheduleManager.create(schedule);
		scheduleBizDao.create(scheduleBiz);
		 
		resultMsg.setIsOk(true);
		resultMsg.setMsg("创建成功");
		return resultMsg;
	}
	/**
	 * 完成日程
	 */
	@Override
	@CatchErr
	@ParamValidate
	public ResultMsg completeSchedule(CompleteScheduleModel param) {
		ResultMsg resultMsg = new ResultMsg();
		Date date = new Date();
		int rate = param.getRate_progress();
		SimpleDateFormat dateFormat =new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date completeDate = null;
		if(param.getCompleteTime() != null) {
			try {
				completeDate = dateFormat.parse(param.getCompleteTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		try {
			List<Schedule> list = scheduleManager.getByBizId(param.getBizId());
			if(list == null) {
				throw new Exception("未找到相关日程");
			}
			//一个biz_id是对应1个日程
			Schedule schedule = list.get(0);
			String type = schedule.getType();
			//--share型更新参与者--
			if(Schedule.TYPE_SHARE.equals(type)) {
				List<ScheduleParticipant> scheduleParticipants = scheduleParticipantDao.getScheduleParticipantList(schedule.getId());
				if(scheduleParticipants != null) {
					for(ScheduleParticipant scheduleParticipant : scheduleParticipants) {
						scheduleParticipant.setRateProgress(rate);
						scheduleParticipant.setUpdateTime(date);
						if(rate >= 100) {
							if(completeDate != null) {
								scheduleParticipant.setCompleteTime(completeDate);
							} else {
								scheduleParticipant.setCompleteTime(date);
							}
						}
						if((scheduleParticipant.getParticipantor().equals(param.getOwnerAccount()) || scheduleParticipant.getParticipantor().equals(param.getOwner().getAccount())) && param.getComment() != null) {
							scheduleParticipant.setSubmitComment(param.getComment());
						}
						scheduleParticipantDao.update(scheduleParticipant);
					}
				}
			} 
			//--ilka型更新参与者和计算rate--
			else if(Schedule.TYPE_ILKA.equals(type)){
				List<ScheduleParticipant> scheduleParticipants = scheduleParticipantDao.getScheduleParticipantList(schedule.getId());
				if(scheduleParticipants != null) {
					int numberOfParticipants = scheduleParticipants.size();
					ScheduleParticipant sParticipant = new ScheduleParticipant();
					for(ScheduleParticipant scheduleParticipant : scheduleParticipants) {
						if(scheduleParticipant.getParticipantor().equals(param.getOwnerAccount()) || scheduleParticipant.getParticipantor().equals(param.getOwner().getAccount())) {
							scheduleParticipant.setRateProgress(param.getRate_progress());
							scheduleParticipant.setUpdateTime(date);
							if(rate >= 100) {
								if(completeDate != null) {
									scheduleParticipant.setCompleteTime(completeDate);
								} else {
									scheduleParticipant.setCompleteTime(date);
								}
							}
							if(param.getComment() != null) {
								scheduleParticipant.setSubmitComment(param.getComment());
							}
							scheduleParticipantDao.update(scheduleParticipant);
							continue;//ilka完成者进度已在一开始赋予，不用再加
						}
						rate += scheduleParticipant.getRateProgress();
					}
					rate = (int)Math.round(rate * (1.0 / numberOfParticipants));
				}
			}
			
			if(rate >= 100) {
				schedule.setRateProgress(100);
				schedule.setCompleteTime(completeDate == null ? date :completeDate);
			} else {
				schedule.setRateProgress(rate);
			}
			//--更新时间--
			schedule.setUpdateTime(date);
			if(param.getOwner() != null) {
				schedule.setSubmitNamer(param.getOwner().getFullname());
				schedule.setSubmitter(param.getOwner().getAccount());
				schedule.setUpdateBy(param.getOwner().getAccount());
			} else if(param.getOwnerAccount() != null) {
				schedule.setSubmitter(param.getOwnerAccount());
			}
			scheduleManager.updateOnlySchedule(schedule);
		} catch (Exception e) {
			resultMsg.setIsOk(false);
			resultMsg.setMsg("完成日程失败：" + e.getMessage());
		}
		resultMsg.setIsOk(true);
		resultMsg.setMsg("完成日程成功");
		return resultMsg;
	}
	/**
	 * 删除日程
	 */
	@Override
	@CatchErr("删除失败")
	public ResultMsg deleteSchedule(String biz_id) {
		scheduleManager.deleteByBizId(biz_id);
		return new ResultMsg(BaseStatusCode.SUCCESS,"删除成功");
	}
	/**
	 * 更新日程
	 */
	@Override
	@CatchErr
	public ResultMsg updateSchedule(String biz_id, Date start, Date end, String ownerAccount) {
		List<Schedule> list = scheduleManager.getByBizId(biz_id);
		if(list == null) {
			throw new BusinessException("未找到相关日程");
		}
		//一个biz_id是对应1个日程把
		Schedule schedule = list.get(0);
		schedule.setOwner(ownerAccount);
		schedule.setActualStartTime(start);
		schedule.setCompleteTime(end);
		schedule.setUpdateTime(new Date());
		scheduleManager.updateOnlySchedule(schedule);
		//scheduleManager.updateSchedule(biz_id, start, end, ownerAccount);
		 
		return new ResultMsg(BaseStatusCode.SUCCESS,"修改成功");
	}
	/**
	 * 根据bizId判断创建或更新日程,空值字段不变  瞎™写
	 * @param schedules
	 * @return
	 */
	@Override
	@CatchErr
	public ResultMsg createOrUpdateSchedule(List<Schedule> schedules) {
		ResultMsg resultMsg = new ResultMsg();
			for(Schedule schedule : schedules) {
				List<Schedule> list = scheduleManager.getByBizId(schedule.getBizId());
				//更新日程
				if(list != null && list.size() > 0) {
					String scheduleId = list.get(0).getId();
					schedule.setId(scheduleId);
					scheduleManager.updateOnlySchedule(schedule);
					//更新参与者
					if(schedule.getScheduleParticipantList() != null && schedule.getScheduleParticipantList().size() > 0) {
						scheduleParticipantDao.delByMainId(scheduleId);
						for(ScheduleParticipant scheduleParticipant:schedule.getScheduleParticipantList()){
		        			if(scheduleParticipant.getId() == null) {
		            			scheduleParticipant.setId(IdUtil.getSuid());
		            		}
		            		if(scheduleParticipant.getScheduleId() == null) {
		            			scheduleParticipant.setScheduleId(scheduleId);
		            		}
		            		if(scheduleParticipant.getRateProgress() == null) {
		            			scheduleParticipant.setRateProgress(0);
		            		}
		            		if(scheduleParticipant.getCreateTime() == null) {
		            			scheduleParticipant.setCreateTime(new Date());
		            		}
		        			if(scheduleParticipant.getActualStartTime() == null) {
		        				scheduleParticipant.setActualStartTime(schedule.getActualStartTime() == null ? list.get(0).getActualStartTime() : schedule.getActualStartTime());
		        			}
		            		scheduleParticipantDao.create(scheduleParticipant);
			        	}
					}
				//创建日程
				} else {
					String id = IdUtil.getSuid();
					schedule.setId(id);
					if(schedule.getStartTime() != null && schedule.getEndTime() != null &&
							schedule.getStartTime().getTime() >= schedule.getEndTime().getTime()) {
						throw new BusinessMessage("预计结束时间不能小于预计开始时间");
					}
					if(schedule.getActualStartTime() != null && schedule.getCompleteTime() != null && 
							schedule.getActualStartTime().getTime() >= schedule.getCompleteTime().getTime()) {
						throw new BusinessMessage("完成时间不能小于实际开始时间");
					}
					schedule.setId(IdUtil.getSuid());
					schedule.setRateProgress(0);//--创建时进度为0--
					schedule.setCreateTime(new Date());
					schedule.setUpdateTime(new Date());
					if(schedule.getActualStartTime() == null) {
						schedule.setActualStartTime(schedule.getStartTime());
					}
					if(schedule.getOwnerName() != null) {
						schedule.setCreateBy(schedule.getOwnerName());
					} else if (schedule.getOwner() != null) {
						schedule.setCreateBy(schedule.getOwner());
					}
					scheduleManager.create(schedule);//创建日程
					ScheduleBiz scheduleBiz = new ScheduleBiz();
					scheduleBiz.setBizId(schedule.getBizId());
					scheduleBiz.setScheduleId(id);
					scheduleBiz.setId(IdUtil.getSuid());
					scheduleBizDao.create(scheduleBiz);//创建任务关联表
				}
			}
		 
		return new ResultMsg(BaseStatusCode.SUCCESS,"更新创建成功");
	}
	
	/**
	 * 删除日程
	 */
	@Override
	@CatchErr
	public ResultMsg deleteSchedule(List<Schedule> schedules) {
		 
		for(Schedule schedule : schedules) {
			scheduleManager.deleteByBizId(schedule.getBizId());
		}
		
		return new ResultMsg(BaseStatusCode.SUCCESS,"删除成功");
	}

}
