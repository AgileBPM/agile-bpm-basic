package com.dstz.bus.api.model;

import java.util.Set;

import com.dstz.bus.api.model.permission.IBusObjPermission;

/**
 * <pre> 
 * 描述：BusinessObject接口类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月26日 下午5:40:45
 * 版权:summer
 * </pre>
 */
public interface IBusinessObject {
	/**
	 * <pre>
	 * key
	 * </pre>
	 * 
	 * @return
	 */
	String getKey();

	/**
	 * <pre>
	 * 名字
	 * </pre>
	 * 
	 * @return
	 */
	String getName();

	/**
	 * <pre>
	 * 描述
	 * </pre>
	 * 
	 * @return
	 */
	String getDesc();

	/**
	 * <pre>
	 * 持久化类型 枚举： BusinessObjectPersistenceType
	 * </pre>
	 * 
	 * @return
	 */
	String getPersistenceType();

	/**
	 * <pre>
	 * 业务表对象的关联对象
	 * </pre>
	 * 
	 * @return
	 */
	IBusTableRel getRelation();

	IBusObjPermission getPermission();

	void setPermission(IBusObjPermission permission);
	
	/**
	 * <pre>
	 * 判断某个表有没有数据库操作权限
	 * 逻辑如下：
	 * 如果字段中有一个字段包含操作权限，则表就有数据库操作权限
	 * 如果一个字段都没，则表没有操作权限
	 * </pre>
	 * 
	 * @param columnKey
	 * @return
	 */
	boolean haveTableDbEditRights(String tableKey);
	
	/**
	 * <pre>
	 * 判断某个表有没有数据库读取权限
	 * 逻辑如下：
	 * 如果字段中有一个字段包含读取权限，则表就有数据库读取权限
	 * 如果一个字段都没，则表没有读取权限
	 * </pre>
	 * 
	 * @param columnKey
	 * @return
	 */
	boolean haveTableDbReadRights(String tableKey);
	
	/**
	 * <pre>
	 * 判断某个字段有没有数据库操作权限
	 * </pre>
	 * 
	 * @param tableKey
	 * @param columnKey
	 * @return
	 */
	boolean haveColumnDbEditRights(String tableKey, String columnKey);
	
	/**
	 * <pre>
	 * 判断某个字段有没有数据库读取权限
	 * </pre>
	 * 
	 * @param tableKey
	 * @param columnKey
	 * @return
	 */
	boolean haveColumnDbReadRights(String tableKey, String columnKey);
	
	/**
	 * <pre>
	 * 计算出这个Bo用到的所有数据源别名
	 * </pre>	
	 * @return
	 */
	Set<String> calDataSourceKeys();
}
