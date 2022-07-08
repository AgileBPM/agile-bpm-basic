package com.dstz.sys.api.model;

import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.model.IUser;

/**
 * <pre>
 * 描述：默认的SysIdentity
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2019年5月26日
 * 版权:summer
 * </pre>
 */
public class DefaultIdentity implements SysIdentity {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String type;

	public DefaultIdentity() {

	}

	/**
	 * 
	 * 创建一个新的实例 DefaultDefaultIdentity.
	 * 
	 * @param id
	 * @param name
	 * @param type
	 */
	public DefaultIdentity(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public DefaultIdentity(IUser user) {
		this.id = user.getUserId();
		this.name = user.getFullname();
		this.type = TYPE_USER;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		return this.id.hashCode() + this.type.hashCode();
	}

	@Override
	public boolean equals(Object obj){
		if(!( obj instanceof SysIdentity)){
			return false;
		}
		
		if(StringUtil.isEmpty(id) || StringUtil.isEmpty(name)) {
			return false;
		}
		
		SysIdentity identity=(SysIdentity)obj;
		
		if(this.type.equals(identity.getType()) && this.id.equals(identity.getId())){
			return true;
		}
	 
		return false;
	}
}
