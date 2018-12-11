package com.dstz.sys.api.model.calendar.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import com.dstz.org.api.model.IUser;


public class CompleteScheduleModel implements Serializable{
	@NotBlank(message = "bizId不能为空")
	private String bizId;
	
	private String completeTime;
	// 完成比例与完成时间不可都为空
	private Integer rate_progress;
	
	private String comment;
	
	private IUser owner;
	private String ownerAccount;
	
	

	/**业务ID*/
	public String getBizId() {
		return bizId;
	}
	/**业务ID*/
	public void setBizId(String bizId) {
		this.bizId = bizId;
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
	public String getCompleteTime() {
		return completeTime;
	}
	/**
	 * 完成时间，完成比例与完成时间不可皆空
	 * @return
	 */
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
	
	public Integer getRate_progress() {
		return rate_progress;
	}
	
	/**
	 * 完成比例，完成比例与完成时间不可皆空
	 * @return
	 */
	public void setRate_progress(Integer rate_progress) {
		this.rate_progress = rate_progress;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
