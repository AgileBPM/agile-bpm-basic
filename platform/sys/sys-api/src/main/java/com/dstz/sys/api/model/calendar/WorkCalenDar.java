package com.dstz.sys.api.model.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;


public class WorkCalenDar implements IDModel{

	//工作日
	public static final String DAY_TYPE_WORKDAY = "DW";
	//周末
	public static final String DAY_TYPE_WEEKEND = "DR";
	//法定节假日
	public static final String DAY_TYPE_LEGAL_HOLIDAY = "LR";
	//法定工作日
	public static final String DAY_TYPE_LEGAL_WORKDAY = "LW";
	//公司节假日
	public static final String DAY_TYPE_COMPAY_HOLIDAY = "CR";
	//公司工作日
	public static final String DAY_TYPE_COMPAY_WORKDAY = "CW";
	// isWorkDay 
	public static final String WORKDAY = "1";
	public static final String HOLIDAY = "0";
	public static final String SYSTEM_PUBLIC = "public";
	public static final String SYSTEM_DDJF = "ddjf";

	/**
	* id
	*/
	protected String id; 
	
	/**
	* 当前时间
	*/
	protected java.util.Date day; 
	
	/**
	* 是否工作日
	*/
	protected String isWorkDay; 
	
	/**
	* 时间类型
	*/
	protected String type; 
	
	/**
	* 所属系统
	*/
	protected String system  = WorkCalenDar.SYSTEM_PUBLIC; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IWorkCalenDar#getId()
	 */
	
	public String getId() {
		return this.id;
	}
	
	public void setDay(java.util.Date day) {
		this.day = day;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IWorkCalenDar#getDay()
	 */
	
	public java.util.Date getDay() {
		return this.day;
	}
	
	public void setIsWorkDay(String isWorkDay) {
		this.isWorkDay = isWorkDay;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IWorkCalenDar#getIsWorkDay()
	 */
	
	public String getIsWorkDay() {
		return this.isWorkDay;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IWorkCalenDar#getType()
	 */
	
	public String getType() {
		return this.type;
	}
	
	public void setSystem(String system) {
		this.system = system;
	}
	
	/* (non-Javadoc)
	 * @see com.ddjf.calendar.model.IWorkCalenDar#getSystem()
	 */
	
	public String getSystem() {
		return this.system;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("day", this.day) 
		.append("isWorkDay", this.isWorkDay) 
		.append("type", this.type) 
		.append("system", this.system) 
		.toString();
	}

	
	public boolean isWorkDay() {
		return (WorkCalenDar.WORKDAY.equals( this.isWorkDay));
	}

}