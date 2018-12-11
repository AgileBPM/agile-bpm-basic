package com.dstz.bus.manager;

import com.dstz.base.manager.Manager;
import com.dstz.bus.model.BusinessPermission;

/**
 * bo权限 Manager处理接口
 * 
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-04-25 19:00:07
 */
public interface BusinessPermissionManager extends Manager<String, BusinessPermission> {

	/**
	 * <pre>
	 * 根据objType和objVal获取唯一对象
	 * </pre>
	 * 
	 * @param objType
	 * @param objVal
	 * @return
	 */
	BusinessPermission getByObjTypeAndObjVal(String objType, String objVal);

	/**
	 * <pre>
	 * 以特定的bo为模板，根据objType和objVal返回权限
	 * ps:
	 * 因为有时候我们需要的是某个bo的权限，而不是数据库的权限
	 * 数据库的权限可能是旧的，所以要返回特定bo的权限，以数据库的存在权限装载进去
	 * </pre>
	 * 
	 * @param objType
	 * @param objVal
	 * @param defaultBoKeys
	 *            以,分隔，eg:a,b,c,...
	 * @return
	 */
	BusinessPermission getByObjTypeAndObjVal(String objType, String objVal, String defaultBoKeys);
}
