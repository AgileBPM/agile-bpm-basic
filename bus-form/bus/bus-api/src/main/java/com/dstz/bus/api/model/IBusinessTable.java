package com.dstz.bus.api.model;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 描述：BusinessTable对其他模块提供的接口类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年2月9日 下午4:19:02
 * 版权:summer
 * </pre>
 */
public interface IBusinessTable {
	/**
	 * 默认的主键id名称
	 */
	public static final String ID_COLUMN_NAME = "id";
	/**
	 * <pre>
	 * 业务表的别名
	 * </pre>
	 * 
	 * @return
	 */
	String getKey();

	/**
	 * <pre>
	 * 表名
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
	String getComment();

	/**
	 * <pre>
	 * 数据源别名
	 * </pre>
	 * 
	 * @return
	 */
	String getDsKey();

	/**
	 * <pre>
	 * 字段
	 * </pre>
	 * 
	 * @return
	 */
	List<? extends IBusinessColumn> getColumns();
	/**
	 * <pre>
	 * 数据源名称
	 * </pre>
	 * @return
	 */
	String getDsName();
	
	/**
	 * <pre>
	 * 获取不包含主键的字段
	 * </pre>
	 * @return
	 */
	List<? extends IBusinessColumn> getColumnsWithoutPk();
	
	/**
	 * <pre>
	 * 获取表的初始化数据库的数据
	 * 不包含主键
	 * 字段取的是name
	 * 其实就是获取字段的默认值
	 * </pre>	
	 * @return
	 */
	Map<String, Object> initDbData();
	
	/**
	 * <pre>
	 * 获取表的初始化数据
	 * 不包含主键
	 * 字段取的是key
	 * 其实就是获取字段的默认值
	 * </pre>	
	 * @return
	 */
	Map<String, Object> initData();
	
	/**
	 * <pre>
	 * 根据字段key获取字段
	 * </pre>	
	 * @param key
	 * @return
	 */
	IBusinessColumn getColumnByKey(String key);
	
	/**
	 * <pre>
	 * 根据字段Name获取字段
	 * </pre>	
	 * @param name
	 * @return
	 */
	IBusinessColumn getColumn(String name);
}
