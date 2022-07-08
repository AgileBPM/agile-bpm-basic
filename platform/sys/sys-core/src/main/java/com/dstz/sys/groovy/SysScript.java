package com.dstz.sys.groovy;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dstz.base.core.util.BeanUtils;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.groovy.IScript;
import com.dstz.sys.api.service.SerialNoService;
import com.dstz.sys.util.ContextUtil;
import com.dstz.sys.util.SysPropertyUtil;

/**
 * 系统脚本
 * 常用的系统功能的脚本
 */
@Component
public class SysScript implements IScript {
	@Resource
	SerialNoService serialNoService;
	
	/**
	 * 获取系统流水号
	 * @param alias
	 * @return
	 */
	public String getNextSerialNo(String alias) {
		return serialNoService.genNextNo(alias);
	}
	
	/**
	 * 
	 * @param 获取系统属性
	 * @return
	 */
	public String getProperty(String key) {
		return SysPropertyUtil.getByAlias(key);
	}
	
	
	public IUser getCurrentUser() {
		IUser user = ContextUtil.getCurrentUser();
		return user;
	}
	
	public String getCurrentGroupName() {
		 IGroup iGroup =ContextUtil.getCurrentGroup();
        if (iGroup!= null) {
            return iGroup.getGroupName();
        } else {
            return "";
        }
	}
	
	public String getCurrentUserName() {
		return ContextUtil.getCurrentUser().getFullname();
	}
	
}
