package com.dstz.sys.core.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.api.model.calendar.Schedule;
import com.dstz.sys.api.model.calendar.ScheduleParticipant;
import com.dstz.sys.core.dao.ScheduleDao;
import com.dstz.sys.core.dao.ScheduleParticipantDao;
import com.dstz.sys.core.manager.ScheduleManager;
import com.dstz.sys.core.model.ParticipantScheduleDO;

import cn.hutool.core.lang.Assert;

/**
 * 
 * <pre> 
 * 描述：日程 处理实现类
 * 作者:linkai
 * 邮箱:linkai@ddjf.com.cn
 * 日期:2018-02-01 17:45:09
 * </pre>
 */
@Service("scheduleManager")
public class ScheduleManagerImpl extends BaseManager<String, Schedule> implements ScheduleManager{
	@Resource
	ScheduleDao scheduleDao;
	@Resource
	ScheduleParticipantDao scheduleParticipantDao;
	 
	/**
	 * 创建实体包含子表实体
	 */
	public void create(Schedule schedule){
    	super.create(schedule);
    	String pk=schedule.getId();
    	
    	//--添加参与者--
//    	if((ISchedule.TYPE_SHARE.equals(schedule.getType()) || ISchedule.TYPE_ILKA.equals(schedule.getType())) && schedule.getParticipantNames() != null && !schedule.getParticipantNames().isEmpty()) {
//    		String[] participants = schedule.getParticipantNames().split(",");
//    		for(int i = 0; i < participants.length; i++) {
//    			ScheduleParticipant scheduleParticipant = new ScheduleParticipant();
//    			//??????????????id设置？？？？？？？？？？？？
//    			scheduleParticipant.setScheduleId(pk);
//    			scheduleParticipant.setParticipantorName(participants[i]);
//    			scheduleParticipant.setRateProgress(0);
//    			scheduleParticipant.setCreateTime(schedule.getCreateTime());
//    			scheduleParticipant.setActualStartTime(schedule.getCreateTime());
//    			scheduleParticipant.setUpdateTime(schedule.getCreateTime());
//    			scheduleParticipantDao.create(scheduleParticipant);
//    		}
//    	}
    	if((Schedule.TYPE_SHARE.equals(schedule.getType()) || Schedule.TYPE_ILKA.equals(schedule.getType()))){
    		List<ScheduleParticipant> scheduleParticipantList= schedule.getScheduleParticipantList();
        	for(ScheduleParticipant scheduleParticipant:scheduleParticipantList){
        		scheduleParticipant.setId(IdUtil.getSuid());
        		scheduleParticipant.setScheduleId(pk);
        		scheduleParticipant.setRateProgress(0);
    			scheduleParticipant.setCreateTime(schedule.getCreateTime());
    			scheduleParticipant.setActualStartTime(schedule.getActualStartTime());
    			scheduleParticipant.setUpdateTime(schedule.getUpdateTime());
        		scheduleParticipantDao.create(scheduleParticipant);
        	}
    	}
    	
    }
	
	/**
	 * 删除记录包含子表记录
	 */
	public void remove(String entityId){
		super.remove(entityId);
    	scheduleParticipantDao.delByMainId(entityId);
	}
	
	/**
	 * 批量删除包含子表记录
	 */
	@Override
	public void removeByIds(String[] entityIds){
		for(String id:entityIds){
			this.remove(id);
		}
	}
    
	/**
	 * 获取实体
	 */
    public Schedule get(String entityId){
    	Assert.notBlank("日程 ID 不能为空！");
    	Schedule schedule=super.get(entityId);
    	List<ScheduleParticipant> scheduleParticipantList=scheduleParticipantDao.getScheduleParticipantList(entityId);
    	schedule.setScheduleParticipantList(scheduleParticipantList);
    	return schedule;
    }
    
    /**
     * 更新实体同时更新子表记录
     */
    public void update(Schedule schedule){
    	super.update(schedule);
    	String id=schedule.getId();
    	scheduleParticipantDao.delByMainId(id);

    	
    	//--添加参与者--
    	if((Schedule.TYPE_SHARE.equals(schedule.getType()) || Schedule.TYPE_ILKA.equals(schedule.getType()))){
    		List<ScheduleParticipant> scheduleParticipantList= schedule.getScheduleParticipantList();
        	for(ScheduleParticipant scheduleParticipant:scheduleParticipantList){
        		if(!("delete".equals(scheduleParticipant.getId()))) {
        			if(scheduleParticipant.getId() == null) {
            			scheduleParticipant.setId(IdUtil.getSuid());
            		}
            		if(scheduleParticipant.getScheduleId() == null) {
            			scheduleParticipant.setScheduleId(id);
            		}
            		if(scheduleParticipant.getRateProgress() == null) {
            			scheduleParticipant.setRateProgress(0);
            		}
            		if(scheduleParticipant.getCreateTime() == null) {
            			scheduleParticipant.setCreateTime(new Date());
            		}
        			if(scheduleParticipant.getActualStartTime() == null) {
        				scheduleParticipant.setActualStartTime(schedule.getActualStartTime());
        			}
            		scheduleParticipantDao.create(scheduleParticipant);
        		}
        	}
    	}
//    	if((ISchedule.TYPE_SHARE.equals(schedule.getType()) || ISchedule.TYPE_ILKA.equals(schedule.getType())) && schedule.getParticipantNames() != null && !schedule.getParticipantNames().isEmpty()) {
//    		String[] participants = schedule.getParticipantNames().split(",");
//    		for(int i = 0; i < participants.length; i++) {
//    			ScheduleParticipant scheduleParticipant = new ScheduleParticipant();
//    			//??????????????id设置？？？？？？？？？？？
//    			scheduleParticipant.setScheduleId(id);
//    			scheduleParticipant.setParticipantorName(participants[i]);
//    			scheduleParticipant.setRateProgress(0);
//    			scheduleParticipant.setCreateTime(schedule.getCreateTime());
//    			scheduleParticipant.setActualStartTime(schedule.getCreateTime());
//    			scheduleParticipant.setUpdateTime(schedule.getCreateTime());
//    			scheduleParticipantDao.create(scheduleParticipant);
//    		}
//    	}
//    	
//    	List<ScheduleParticipant> scheduleParticipantList=schedule.getScheduleParticipantList();
//    	if(scheduleParticipantList != null) {
//    		List<ScheduleParticipant> list = scheduleParticipantDao.getByMainId(id);
//    		for(ScheduleParticipant participant:list){
//    			for(ScheduleParticipant s : scheduleParticipantList) {
//    				if(s.getParticipantorName().equals(participant.getParticipantorName())) {
//    					participant.setRateProgress(s.getRateProgress());
//        				participant.setSubmitComment(s.getSubmitComment());
//        				participant.setCreateTime(s.getCreateTime());
//        				participant.setActualStartTime(s.getActualStartTime());
//        				participant.setCompleteTime(s.getCompleteTime());
//        				scheduleParticipantDao.update(participant);
//    				}
//    			}
//    	    } 
//    	}
    }
    
    /**
     * 获取一段时间内的日程
     */
	@Override
	public List<Schedule> getByPeriodAndOwner(Date start, Date end, String ownerName, String owner) {
		return scheduleDao.getByPeriodAndOwner(start, end, ownerName, owner);
	}
	
	@Override
	public void saveSchedule(Schedule schedule) {
		if(schedule.getStartTime().compareTo(schedule.getEndTime()) > 0) {
			throw new BusinessMessage("日程开始时间不能大于结束时间");
		}
		create(schedule);
	}
	@Override
	public List<Schedule> getByPeriodAndSource(Date start, Date end, String source) {
		return scheduleDao.getByPeriodAndSource(start, end, source);
	}
	@Override
	public void deleteByBizId(String bizId) {
		scheduleDao.deleteByBizId(bizId);
	}
	@Override
	public void dragUpdate(Schedule schedule) {
		scheduleDao.dragUpdate(schedule);
	}
	@Override
	public void updateSchedule(String biz_id, Date start, Date end, String ownerAccount) {
		scheduleDao.updateSchedule(biz_id, start, end, ownerAccount);
	}
	@Override
	public List<Schedule> getByBizId(String biz_id) {
		return scheduleDao.getByBizId(biz_id);
	}
	@Override
	public void updateOnlySchedule(Schedule schedule) {
		scheduleDao.updateOnlySchedule(schedule);
	}

	@Override
	public List<ParticipantScheduleDO> getParticipantEvents(Date startDate, Date endDate, String name, String id) {
		Map<String,Object> map = new HashMap<>();
		map.put("startTime", startDate);
		map.put("endTime", endDate);
		map.put("participantName", name);
		map.put("participant", id);
		
		return scheduleDao.getParticipantEvents(map);
	}
    
    
	
}
