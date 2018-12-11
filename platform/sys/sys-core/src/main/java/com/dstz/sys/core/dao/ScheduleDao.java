package com.dstz.sys.core.dao;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.api.model.calendar.Schedule;
import com.dstz.sys.core.model.ParticipantScheduleDO;


public interface ScheduleDao extends BaseDao<String, Schedule> {
	/**
	 * 获取一段时间内的日程
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Schedule> getByPeriodAndOwner(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("ownerName")String ownerName,@Param("owner") String owner);
	/**
	 * 获取一段时间内特定source的日程
	 * @param start
	 * @param end
	 * @param source
	 * @return
	 */
	public List<Schedule> getByPeriodAndSource(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("source")String source);
	/**
	 * 根据bizId删除日程
	 * @param bizId
	 */
	public void deleteByBizId(String bizId);
	/**
	 * 日程表的拖拽更新
	 * @param schedule
	 */
	public void dragUpdate(Schedule schedule);
	/**
	 * 根据biz_id更新schedule表的start_time,end_time,owner
	 * @param biz_id
	 * @param start
	 * @param end
	 * @param ownerAccount
	 */
	public void updateSchedule(@Param("bizId")String bizId, @Param("startTime")Date startTime, @Param("endTime")Date endTime,@Param("owner") String ownerAccount);
	/**
	 * 根据biz_id获取日程
	 * @param biz_id
	 * @return
	 */
	public List<Schedule> getByBizId(String bizId);
	/**
	 * 只更新schedule表
	 * @param schedule
	 */
	public void updateOnlySchedule(Schedule schedule);
	
	public List<ParticipantScheduleDO> getParticipantEvents(Map<String, Object> params);
}

