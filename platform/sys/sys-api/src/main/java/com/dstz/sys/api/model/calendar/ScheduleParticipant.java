package com.dstz.sys.api.model.calendar;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ScheduleParticipant implements Serializable{
	/**
	* id
	*/
	protected String id; 
	
	/**
	* 日程ID
	*/
	protected String scheduleId; 
	
	/**
	* 参与者名字
	*/
	protected String participantorName; 
	
	/**
	* 参与者
	*/
	protected String participantor; 
	
	/**
	* ilka完成比例
	*/
	protected Integer rateProgress; 
	
	/**
	* ilka提交注释
	*/
	protected String submitComment; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date createTime; 
	
	/**
	* 更新时间
	*/
	protected java.util.Date updateTime; 
	
	/**
	* ilka实际开始时间
	*/
	protected java.util.Date actualStartTime; 
	
	/**
	* ilka完成时间
	*/
	protected java.util.Date completeTime; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getId()
	 */
	
	public String getId() {
		return this.id;
	}
	
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getScheduleId()
	 */
	
	public String getScheduleId() {
		return this.scheduleId;
	}
	
	public void setParticipantorName(String participantorName) {
		this.participantorName = participantorName;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getParticipantorName()
	 */
	
	public String getParticipantorName() {
		return this.participantorName;
	}
	
	public void setParticipantor(String participantor) {
		this.participantor = participantor;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getParticipantor()
	 */
	
	public String getParticipantor() {
		return this.participantor;
	}
	
	public void setRateProgress(Integer rateProgress) {
		this.rateProgress = rateProgress;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getRateProgress()
	 */
	
	public Integer getRateProgress() {
		return this.rateProgress;
	}
	
	public void setSubmitComment(String submitComment) {
		this.submitComment = submitComment;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getSubmitComment()
	 */
	
	public String getSubmitComment() {
		return this.submitComment;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getCreateTime()
	 */
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getUpdateTime()
	 */
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setActualStartTime(java.util.Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getActualStartTime()
	 */
	
	public java.util.Date getActualStartTime() {
		return this.actualStartTime;
	}
	
	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleParticipant#getCompleteTime()
	 */
	
	public java.util.Date getCompleteTime() {
		return this.completeTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("scheduleId", this.scheduleId) 
		.append("participantorName", this.participantorName) 
		.append("participantor", this.participantor) 
		.append("rateProgress", this.rateProgress) 
		.append("submitComment", this.submitComment) 
		.append("createTime", this.createTime) 
		.append("updateTime", this.updateTime) 
		.append("actualStartTime", this.actualStartTime) 
		.append("completeTime", this.completeTime) 
		.toString();
	}

}