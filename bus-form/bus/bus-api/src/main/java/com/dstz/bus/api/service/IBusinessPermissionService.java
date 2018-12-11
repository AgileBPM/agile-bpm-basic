package com.dstz.bus.api.service;

import com.dstz.bus.api.model.IBusinessPermission;

public interface IBusinessPermissionService {
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
	 *            以,分隔，eg:a,b,c,... 为空时则取不以boKey为模板
	 * @param calculate
	 *            是否计算当前用户的结果
	 * @return
	 */
	IBusinessPermission getByObjTypeAndObjVal(String objType, String objVal, String defalutBoKeys, boolean calculate);

}
