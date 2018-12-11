package com.dstz.demo.core.model;
import java.util.Date;
import java.math.BigDecimal;

import com.dstz.base.core.model.BaseModel;


/**
 * Demo子表 实体对象
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:26:33
 */
public class DemoSub extends BaseModel{
	/**
	* 名字
	*/
	protected  String mz; 
	/**
	* 描述
	*/
	protected  String ms; 
	/**
	* 字段1
	*/
	protected  String zd1; 
	/**
	* 字段2
	*/
	protected  String zd2; 
	/**
	* 外键
	*/
	protected  String fk; 
	
	
	
	
	
	public void setMz( String mz) {
		this.mz = mz;
	}
	
	/**
	 * 返回 名字
	 * @return
	 */
	public  String getMz() {
		return this.mz;
	}
	
	
	
	
	public void setMs( String ms) {
		this.ms = ms;
	}
	
	/**
	 * 返回 描述
	 * @return
	 */
	public  String getMs() {
		return this.ms;
	}
	
	
	
	
	public void setZd1( String zd1) {
		this.zd1 = zd1;
	}
	
	/**
	 * 返回 字段1
	 * @return
	 */
	public  String getZd1() {
		return this.zd1;
	}
	
	
	
	
	public void setZd2( String zd2) {
		this.zd2 = zd2;
	}
	
	/**
	 * 返回 字段2
	 * @return
	 */
	public  String getZd2() {
		return this.zd2;
	}
	
	
	
	
	public void setFk( String fk) {
		this.fk = fk;
	}
	
	/**
	 * 返回 外键
	 * @return
	 */
	public  String getFk() {
		return this.fk;
	}
	
 
}