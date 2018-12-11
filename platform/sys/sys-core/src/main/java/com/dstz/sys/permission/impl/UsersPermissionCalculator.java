package com.dstz.sys.permission.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dstz.sys.api.permission.IPermissionCalculator;
import com.dstz.sys.util.ContextUtil;

/**
 * <pre>
 * 描述：用户
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月8日
 * 版权:summer
 * </pre>
 */
@Service
public class UsersPermissionCalculator implements IPermissionCalculator {

	@Override
	public String getTitle() {
		return "用户";
	}

	@Override
	public String getType() {
		return "user";
	}

	@Override
	public boolean haveRights(JSONObject json) {
		for(String id :json.getString("id").split(",")) {
			if(id.equals(ContextUtil.getCurrentUserId())) {
				return true;
			}
		}
		return false;
	}

}
