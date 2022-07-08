package com.dstz.base.api.context;

/**
 * 当前线程 的用户ID，用户名等信息、不依赖ORG API 
 */
public interface ICurrentContext {
	/**
	 * 获取用户ID
	 * @return
	 */
	String getCurrentUserId();

	String getCurrentUserName();

	String getCurrentGroupId();

	String getCurrentGroupName();
	
}
