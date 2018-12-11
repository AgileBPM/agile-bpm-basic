package com.dstz.sys.api.service;

import java.util.Date;
import java.util.List;

import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.sys.api.model.calendar.Schedule;
import com.dstz.sys.api.model.calendar.dto.CompleteScheduleModel;
import com.dstz.sys.api.model.calendar.dto.CreateScheduleModel;

/**
 * 日程服务接口
 * @author miao
 *
 */
public interface ScheduleService {
	
	/**
	 * 获取某段时间内的所有日程<br>
	 * 非single类型存在参与者IScheduleParticipant，single类型不存在参与者
	 * @param start
	 * @param end
	 * @return
	 */
	ResultMsg<List<Schedule>> getSchedulesByTime(Date start,Date end,String source);
	/**
	 * 创建日程
	 * @param schdeleModel
	 * @return
	 */
	ResultMsg createSchedule(CreateScheduleModel scheduleModel);
	
	/**
	 * 完成日程
	 * @param param
	 * @return
	 */
	ResultMsg completeSchedule(CompleteScheduleModel param);

	/**
	 * 逻辑删除日程
	 * @param param
	 * @return
	 */
	ResultMsg deleteSchedule(String biz_id);
	
	/**
	 * 重新安排日程
	 * @param biz_id 业务id
	 * @param start 计划开始时间
	 * @param end 计划完成时间
	 * @param ownerAccount 所属人
	 * @return
	 */
	ResultMsg updateSchedule(String biz_id,Date start,Date end,String ownerAccount);
	/**
	 * 根据bizId判断有无创建或更新日程
	 * @param schedules
	 * @return
	 */
	ResultMsg createOrUpdateSchedule(List<Schedule> schedules);
	/**
	 * 删除日程
	 * @param schedules
	 * @return
	 */
	ResultMsg deleteSchedule(List<Schedule> schedules);

}
