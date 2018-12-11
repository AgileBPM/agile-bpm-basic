package com.dstz.sys.permission.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dstz.sys.api.permission.IPermissionCalculator;
/**
 * <pre>
 * 描述：无人
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月8日
 * 版权:summer
 * </pre>
 */
@Service
public class NonePermissionCalculator implements IPermissionCalculator {

	@Override
	public String getTitle() {
		return "无";
	}

	@Override
	public String getType() {
		return "none";
	}

	@Override
	public boolean haveRights(JSONObject json) {
		return false;
	}

}
