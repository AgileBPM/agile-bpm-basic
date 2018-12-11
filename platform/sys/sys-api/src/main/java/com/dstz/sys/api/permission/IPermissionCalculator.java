package com.dstz.sys.api.permission;

import com.alibaba.fastjson.JSONObject;

/**
 * <pre>
 * 描述：系统内置权限
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月8日
 * 版权:summer
 * </pre>
 */
public interface IPermissionCalculator {
	/**
	 * <pre>
	 * 类型
	 * </pre>	
	 * @return
	 */
	String getType();
	/**
	 * <pre>
	 * 名字
	 * </pre>	
	 * @return
	 */
	String getTitle();
	/**
	 * <pre>
	 * 判断当前用户是否有这个权限
	 * json格式:{type:"everyone",...(一些不同类型约定的参数)}
	 * </pre>	
	 * @param jsonObject
	 * @return
	 */
	boolean haveRights(JSONObject json);
	
}
