package com.dstz.sys.permission.impl.group;

import org.springframework.stereotype.Service;

import com.dstz.sys.permission.impl.GroupPermissionCalculator;
/**
 * <pre>
 * 描述：岗位
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年7月12日
 * 版权:summer
 * </pre>
 */
@Service
public class PostPermissionCalculator extends GroupPermissionCalculator {
	
	@Override
	public String getType() {
		return "post";
	}

	@Override
	public String getTitle() {
		return "岗位";
	}
}
