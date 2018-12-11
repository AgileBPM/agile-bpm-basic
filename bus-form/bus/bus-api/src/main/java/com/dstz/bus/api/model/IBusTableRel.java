package com.dstz.bus.api.model;

import java.util.List;

/**
 * <pre>
 * 描述：BusTableRel接口类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月26日 下午5:29:16
 * 版权:summer
 * </pre>
 */
public interface IBusTableRel {
	/**
	 * <pre>
	 * 子级
	 * </pre>
	 * 
	 * @return
	 */
	List<? extends IBusTableRel> getChildren();

	/**
	 * <pre>
	 * 业务表的key
	 * </pre>
	 * 
	 * @return
	 */
	String getTableKey();

	/**
	 * <pre>
	 * 业务表的描述
	 * </pre>
	 * 
	 * @return
	 */
	String getTableComment();

	/**
	 * <pre>
	 * 类型 枚举 BusTableRelType
	 * </pre>
	 * 
	 * @return
	 */
	String getType();

	/**
	 * <pre>
	 * 外键设置
	 * </pre>
	 * 
	 * @return
	 */
	List<? extends IBusTableRelFk> getFks();

	/**
	 * <pre>
	 * 获取busTableRel的list模式
	 * 包含根节点
	 * </pre>
	 * 
	 * @return
	 */
	List<? extends IBusTableRel> list();

	IBusinessTable getTable();
	
	/**
	 * <pre>
	 * 以当前为根节点，递归获取指定tableKey
	 * </pre>
	 * 
	 * @param tableKey
	 * @return
	 */
	IBusTableRel find(String tableKey);

	IBusTableRel getParent();

	IBusinessObject getBusObj();

	List<IBusTableRel> getChildren(String type);
}
