package com.dstz.sys.api.model.calendar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;


 /**
 * 
 * <pre> 
 * 描述：日程 实体对象
 * 作者:linkai
 * 邮箱:linkai@ddjf.com.cn
 * 日期:2018-02-01 17:45:09
 * </pre>
 */
public class Schedule implements IDModel ,Serializable{
	public static final String TYPE_SINGLE = "single";
	public static final String TYPE_SHARE = "share";
	public static final String TYPE_ILKA = "ilka";
	/**
	* ID
	*/
	protected String id; 
	/**
	 * 业务关联id。此id用在回调时
	 */
	protected  String bizId;
	
	/**
	* 标题
	*/
	protected String title; 
	
	/**
	* 描述
	*/
	protected String remark; 
	
	/**
	* 任务连接
	*/
	protected String taskUrl; 
	
	/**
	* 类型
	*/
	protected String type; 
	
	/**
	* 任务打开方式
	*/
	protected String openType; 
	
	/**
	* 所属人
	*/
	protected String owner; 
	
	/**
	* 所属人
	*/
	protected String ownerName; 
	
	/**
	* 参与者
	*/
	protected String participantNames; 
	
	/**
	* 开始日期
	*/
	protected java.util.Date startTime; 
	
	/**
	* 结束日期
	*/
	protected java.util.Date endTime; 
	
	/**
	* 实际开始日期
	*/
	protected java.util.Date actualStartTime; 
	
	/**
	* 完成时间
	*/
	protected java.util.Date completeTime; 
	
	/**
	* 进度
	*/
	protected Integer rateProgress; 
	
	/**
	* 提交人
	*/
	protected String submitter; 
	
	/**
	* 提交人
	*/
	protected String submitNamer; 
	
	/**
	* isLock
	*/
	protected String isLock; 
	
	/**
	* 创建时间
	*/
	protected java.util.Date createTime; 
	
	/**
	* 创建人
	*/
	protected String createBy; 
	
	/**
	* 更新时间
	*/
	protected java.util.Date updateTime; 
	
	/**
	* 更新人
	*/
	protected String updateBy; 
	
	/**
	* 删除标记
	*/
	protected String deleteFlag; 
	
	/**
	* 版本
	*/
	protected Integer rev; 
	
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	/**
	*日程参与者列表
	*/
	protected List<ScheduleParticipant> scheduleParticipantList = new ArrayList<ScheduleParticipant>(); 
	
	public void setId(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getId()
	 */
	
	public String getId() {
		return this.id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getTitle()
	 */
	
	public String getTitle() {
		return this.title;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getRemark()
	 */
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getTaskUrl()
	 */
	
	public String getTaskUrl() {
		return this.taskUrl;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getType()
	 */
	
	public String getType() {
		return this.type;
	}
	
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getOpenType()
	 */
	
	public String getOpenType() {
		return this.openType;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getOwner()
	 */
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getOwnerName()
	 */
	
	public String getOwnerName() {
		return this.ownerName;
	}
	
	public void setParticipantNames(String participantNames) {
		this.participantNames = participantNames;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getParticipantNames()
	 */
	
	public String getParticipantNames() {
		return this.participantNames;
	}
	
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getStartTime()
	 */
	
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getEndTime()
	 */
	
	public java.util.Date getEndTime() {
		return this.endTime;
	}
	
	public void setActualStartTime(java.util.Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getActualStartTime()
	 */
	
	public java.util.Date getActualStartTime() {
		return this.actualStartTime;
	}
	
	public void setCompleteTime(java.util.Date completeTime) {
		this.completeTime = completeTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getCompleteTime()
	 */
	
	public java.util.Date getCompleteTime() {
		return this.completeTime;
	}
	
	public void setRateProgress(Integer rateProgress) {
		this.rateProgress = rateProgress;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getRateProgress()
	 */
	
	public Integer getRateProgress() {
		return this.rateProgress;
	}
	
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getSubmitter()
	 */
	
	public String getSubmitter() {
		return this.submitter;
	}
	
	public void setSubmitNamer(String submitNamer) {
		this.submitNamer = submitNamer;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getSubmitNamer()
	 */
	
	public String getSubmitNamer() {
		return this.submitNamer;
	}
	
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getIsLock()
	 */
	
	public String getIsLock() {
		return this.isLock;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getCreateTime()
	 */
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getCreateBy()
	 */
	
	public String getCreateBy() {
		return this.createBy;
	}
	
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getUpdateTime()
	 */
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getUpdateBy()
	 */
	
	public String getUpdateBy() {
		return this.updateBy;
	}
	
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getDeleteFlag()
	 */
	public String getDeleteFlag() {
		return this.deleteFlag;
	}
	
	public void setRev(Integer rev) {
		this.rev = rev;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getRev()
	 */
	public Integer getRev() {
		return this.rev;
	}

	public void setScheduleParticipantList(List<ScheduleParticipant> scheduleParticipantList) {
		this.scheduleParticipantList = scheduleParticipantList;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IScheduleService#getScheduleParticipantList()
	 */
	public List<ScheduleParticipant> getScheduleParticipantList() {
		return this.scheduleParticipantList;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("title", this.title) 
		.append("remark", this.remark) 
		.append("taskUrl", this.taskUrl) 
		.append("type", this.type) 
		.append("openType", this.openType) 
		.append("owner", this.owner) 
		.append("ownerName", this.ownerName) 
		.append("participantNames", this.participantNames) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("actualStartTime", this.actualStartTime) 
		.append("completeTime", this.completeTime) 
		.append("rateProgress", this.rateProgress) 
		.append("submitter", this.submitter) 
		.append("submitNamer", this.submitNamer) 
		.append("isLock", this.isLock) 
		.append("createTime", this.createTime) 
		.append("createBy", this.createBy) 
		.append("updateTime", this.updateTime) 
		.append("updateBy", this.updateBy) 
		.append("deleteFlag", this.deleteFlag) 
		.append("rev", this.rev) 
		.toString();
	}

	
	public String getBizId() {
		return bizId;
	}
}