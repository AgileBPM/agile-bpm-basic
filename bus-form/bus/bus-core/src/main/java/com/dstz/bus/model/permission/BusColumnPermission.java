package com.dstz.bus.model.permission;

import com.dstz.bus.api.model.permission.IBusColumnPermission;

/**
 * <pre>
 *  
 * 描述：字段权限
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月22日 下午5:21:14
 * 版权:summer
 * </pre>
 */
public class BusColumnPermission extends AbstractPermission implements IBusColumnPermission {
	/**
	 * 字段名
	 */
	private String key;
	/**
	 * 字段描述
	 */
	private String comment;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
