package com.dstz.sys.permission.impl.group;

import org.springframework.stereotype.Service;

import com.dstz.sys.permission.impl.GroupPermissionCalculator;
/**
 * <pre>
 * 描述：组织
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年7月12日
 * 版权:summer
 * </pre>
 */
@Service
public class OrgPermissionCalculator extends GroupPermissionCalculator {
	
	@Override
	public String getType() {
		return "org";
	}

	@Override
	public String getTitle() {
		return "组织";
	}
	
}
