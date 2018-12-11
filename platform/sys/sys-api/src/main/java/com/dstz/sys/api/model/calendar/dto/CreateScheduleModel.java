package com.dstz.sys.api.model.calendar.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.dstz.org.api.model.IUser;

public class CreateScheduleModel implements Serializable{
	/**业务ID*/
	@NotBlank(message = "业务ID")
	private String bizId;
	/**业务来源*/
	@NotBlank(message = "业务ID")
	private String source;
	/**标题*/
	@NotBlank(message = "标题不能为空")
	private String title;
	/**详细描述*/
	@NotBlank(message = "详细描述不能为空")
	private String remark;
	/**日程开始时间*/
	@NotNull(message = "日程开始时间不能为空")
	private Date startTime;
	/**日程结束时间*/
	@NotNull(message = "日程结束时间不能为空")
	private Date endTime;
	/**日程任务连接*/
	private String taskUrl;
	/**打开方式*/
	private String openType;
	/**所属人：当所属人为空是，使用所属人账户,二者不可皆空*/
	private IUser owner;
	/**所属人的账户，当IUser为空时此字段必填*/
	private String ownerAccount;
	/**日程类型：single 个人所属，share 共享的，所有参与者共享任务，一人完成则全部完成，ilka 每个参与者都需要完成任务*/
	@NotBlank(message = "日程类型不能为空")
	private String type = "single";
	
	/**userList 参与者列表 */
	private List<IUser> user;

	/**业务ID*/
	public String getBizId() {
		return bizId;
	}
	/**业务ID*/
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}

	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}
	/**所属人：当所属人为空是，使用所属人账户,二者不可皆空*/
	public IUser getOwner() {
		return owner;
	}
	/**所属人：当所属人为空是，使用所属人账户,二者不可皆空*/
	public void setOwner(IUser owner) {
		this.owner = owner;
	}
	/**所属人账户：当所属人为空是，使用所属人账户,二者不可皆空*/
	public String getOwnerAccount() {
		return ownerAccount;
	}
	/**所属人账户：当所属人为空是，使用所属人账户,二者不可皆空*/
	public void setOwnerAccount(String ownerAccount) {
		this.ownerAccount = ownerAccount;
	}
	/**日程类型：single 个人所属，share 共享的，所有参与者共享任务，一人完成则全部完成，ilka 每个参与者都需要完成任务*/
	public String getType() {
		return type;
	}
	/**日程类型：single 个人所属，share 共享的，所有参与者共享任务，一人完成则全部完成，ilka 每个参与者都需要完成任务*/
	public void setType(String type) {
		this.type = type;
	}

	public List<IUser> getUser() {
		return user;
	}

	public void setUser(List<IUser> user) {
		this.user = user;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
