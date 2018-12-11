package com.dstz.sys.core.model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.dstz.base.api.model.IDModel;


 /**
 * 
 * 节假日配置
 * @author jeff
 */
public class HolidayConf implements IDModel{
	
	/**
	* id
	*/
	protected String id; 
	
	/**
	* 名字
	*/
	@NotBlank
	protected String name; 
	
	/**
	* 系统
	*/
	protected String system; 
	
	/**
	* 年份
	*/
	@NotEmpty
	protected Integer year; 
	
	/**
	* 开始日期
	*/
	@NotEmpty
	protected java.util.Date startDay; 
	
	/**
	* 结束日期
	*/
	@NotEmpty
	protected java.util.Date endDay; 
	
	/**
	* 分类
	*/
	@NotBlank
	protected String type; 
	
	/**
	* 描述
	*/
	protected String remark; 
	
	
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 名字
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	/**
	 * 返回 系统
	 * @return
	 */
	public String getSystem() {
		return this.system;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	/**
	 * 返回 年份
	 * @return
	 */
	public Integer getYear() {
		return this.year;
	}
	
	public void setStartDay(java.util.Date startDay) {
		this.startDay = startDay;
	}
	
	/**
	 * 返回 开始日期
	 * @return
	 */
	public java.util.Date getStartDay() {
		return this.startDay;
	}
	
	public void setEndDay(java.util.Date endDay) {
		this.endDay = endDay;
	}
	
	/**
	 * 返回 结束日期
	 * @return
	 */
	public java.util.Date getEndDay() {
		return this.endDay;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 返回 分类
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("system", this.system) 
		.append("year", this.year) 
		.append("startDay", this.startDay) 
		.append("endDay", this.endDay) 
		.append("type", this.type) 
		.append("remark", this.remark) 
		.toString();
	}
}