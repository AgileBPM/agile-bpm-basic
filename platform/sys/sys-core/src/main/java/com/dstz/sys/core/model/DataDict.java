package com.dstz.sys.core.model;
import java.util.Date;
import java.math.BigDecimal;

import com.dstz.base.core.model.BaseModel;


/**
 * 数据字典 实体对象
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-05-16 14:39:58
 */
public class DataDict extends BaseModel{
	public static final String TYPE_DICT = "dict";
	public static final String TYPE_NODE = "node";
	/**
	* 上级id
	*/
	protected  String parentId ; 
	/**
	* key
	*/
	protected  String key; 
	/**
	* name
	*/
	protected  String name; 
	/**
	* 字典key
	*/
	protected  String dictKey; 
	/**
	* 分组id
	*/
	protected  String typeId; 
	/**
	* 排序
	*/
	protected  Integer sn = 0; 
	/**
	* dict/node字典项
	*/
	protected  String dictType; 
	/**
	* 是否删除
	*/
	protected  String deleteFlag = "0"; 
	
	
	
	
	
	public void setParentId( String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 返回 上级id
	 * @return
	 */
	public  String getParentId() {
		return this.parentId;
	}
	
	
	
	
	public void setKey( String key) {
		this.key = key;
	}
	
	/**
	 * 返回 key
	 * @return
	 */
	public  String getKey() {
		return this.key;
	}
	
	
	
	
	public void setName( String name) {
		this.name = name;
	}
	
	/**
	 * 返回 name
	 * @return
	 */
	public  String getName() {
		return this.name;
	}
	
	
	
	
	public void setDictKey( String dictKey) {
		this.dictKey = dictKey;
	}
	
	/**
	 * 返回 字典key
	 * @return
	 */
	public  String getDictKey() {
		return this.dictKey;
	}
	
	
	
	
	public void setTypeId( String typeId) {
		this.typeId = typeId;
	}
	
	/**
	 * 返回 分组id
	 * @return
	 */
	public  String getTypeId() {
		return this.typeId;
	}
	
	
	
	
	public void setSn( Integer sn) {
		this.sn = sn;
	}
	
	/**
	 * 返回 排序
	 * @return
	 */
	public  Integer getSn() {
		return this.sn;
	}
	
	
	
	
	public void setDictType( String dictType) {
		this.dictType = dictType;
	}
	
	/**
	 * 返回 dict/node字典项
	 * @return
	 */
	public  String getDictType() {
		return this.dictType;
	}
	
	
	
	
	public void setDeleteFlag( String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 * 返回 是否删除
	 * @return
	 */
	public  String getDeleteFlag() {
		return this.deleteFlag;
	}
	
	
 
}