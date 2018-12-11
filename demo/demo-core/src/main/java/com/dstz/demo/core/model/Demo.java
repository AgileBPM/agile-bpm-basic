package com.dstz.demo.core.model;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import com.dstz.base.core.model.BaseModel;


/**
 * 案例 实体对象 (该对象与实体 Demo业务对象的json 直接转化)
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:06:04
 */
public class Demo extends BaseModel{
	/**
	* 名字
	*/
	protected  String mz; 
	/**
	* 爱好
	*/
	protected  String ah; 
	/**
	* 性别
	*/
	protected  String xb; 
	/**
	* 部门
	*/
	protected  String bm; 
	/**
	* 部门ID
	*/
	protected  String bmId; 
	/**
	* 证件类型
	*/
	protected  String zjlx; 
	/**
	* 年龄
	*/
	protected  Integer nl; 
	/**
	* 字段1
	*/
	protected  String zd1; 
	/**
	* 字段2
	*/
	protected  String zd2; 
	
	private List<DemoSub> demoSubList;
	
	
	
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
	
	
	
	
	public void setAh( String ah) {
		this.ah = ah;
	}
	
	/**
	 * 返回 爱好
	 * @return
	 */
	public  String getAh() {
		return this.ah;
	}
	
	
	
	
	public void setXb( String xb) {
		this.xb = xb;
	}
	
	/**
	 * 返回 性别
	 * @return
	 */
	public  String getXb() {
		return this.xb;
	}
	
	
	
	
	public void setBm( String bm) {
		this.bm = bm;
	}
	
	/**
	 * 返回 部门
	 * @return
	 */
	public  String getBm() {
		return this.bm;
	}
	
	
	
	
	public void setBmId( String bmId) {
		this.bmId = bmId;
	}
	
	/**
	 * 返回 部门ID
	 * @return
	 */
	public  String getBmId() {
		return this.bmId;
	}
	
	
	
	
	public void setZjlx( String zjlx) {
		this.zjlx = zjlx;
	}
	
	/**
	 * 返回 证件类型
	 * @return
	 */
	public  String getZjlx() {
		return this.zjlx;
	}
	
	
	
	
	public void setNl( Integer nl) {
		this.nl = nl;
	}
	
	/**
	 * 返回 年龄
	 * @return
	 */
	public  Integer getNl() {
		return this.nl;
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

	public List<DemoSub> getDemoSubList() {
		return demoSubList;
	}

	public void setDemoSubList(List<DemoSub> demoSubList) {
		this.demoSubList = demoSubList;
	}
	
 
}