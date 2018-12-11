package com.dstz.sys.core.dao;
import java.util.List;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.api.model.calendar.ScheduleParticipant;


public interface ScheduleParticipantDao extends BaseDao<String, ScheduleParticipant> {
	/**
	 * 根据外键获取子表明细列表
	 * @param scheduleId
	 * @return
	 */
	public List<ScheduleParticipant> getScheduleParticipantList(String scheduleId);
	
	/**
	 * 根据外键删除子表记录
	 * @param scheduleId
	 * @return
	 */
	public void delByMainId(String scheduleId);
	/**
	 * 根据参与者姓名获取记录
	 * @param name
	 * @return
	 */
	public ScheduleParticipant getByName(String name);
	
}
