package com.dstz.sys.permission.impl;


import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dstz.sys.api.permission.IPermissionCalculator;

/**
 * <pre>
 * 描述：所有人
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月8日
 * 版权:summer
 * </pre>
 */
@Service
public class EveryonePermissionCalculator implements IPermissionCalculator {

	@Override
	public String getType() {
		return "everyone";
	}

	@Override
	public String getTitle() {
		return "所有人";
	}

	@Override
	public boolean haveRights(JSONObject json) {
		return true;
	}

}
