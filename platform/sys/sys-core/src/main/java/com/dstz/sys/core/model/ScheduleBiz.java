package com.dstz.sys.core.model;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;


 /**
  * 日程业务关联表 实体对象
 *  @author jeff
 */
public class ScheduleBiz implements IDModel{
	
	/**
	* id
	*/
	protected String id; 
	
	/**
	* 日程id
	*/
	protected String scheduleId; 
	
	/**
	* 业务id
	*/
	protected String bizId; 
	
	/**
	* 来源
	*/
	protected String source; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 id
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	/**
	 * 返回 日程id
	 * @return
	 */
	public String getScheduleId() {
		return this.scheduleId;
	}
	
	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
	/**
	 * 返回 业务id
	 * @return
	 */
	public String getBizId() {
		return this.bizId;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * 返回 来源
	 * @return
	 */
	public String getSource() {
		return this.source;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("scheduleId", this.scheduleId) 
		.append("bizId", this.bizId) 
		.append("source", this.source) 
		.toString();
	}
}