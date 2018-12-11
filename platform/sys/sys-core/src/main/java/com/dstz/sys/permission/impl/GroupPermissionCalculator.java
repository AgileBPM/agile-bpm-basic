package com.dstz.sys.permission.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.ThreadMapUtil;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.service.GroupService;
import com.dstz.sys.api.permission.IPermissionCalculator;
import com.dstz.sys.util.ContextUtil;
/**
 * <pre>
 * 描述：组 抽象类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年5月8日
 * 版权:summer
 * </pre>
 */
public abstract class GroupPermissionCalculator implements IPermissionCalculator {
	/**
	 * 线程变量ThreadMapUtil中关于当前权限解析器的线程变量
	 */
	private static String threadMapKey = "com.dstz.sys.permission.impl.GroupPermission";
	
	@Override
	public boolean haveRights(JSONObject json) {
		Map<String, List<IGroup>> allGroup = (Map<String, List<IGroup>>) ThreadMapUtil.get(threadMapKey);
		if(allGroup ==null) {
			GroupService groupService = AppUtil.getBean(GroupService.class);
			allGroup = groupService.getAllGroupByUserId(ContextUtil.getCurrentUserId());
			ThreadMapUtil.put(threadMapKey, allGroup);
		}
		
		List<IGroup> groups;
		if("post".equals(this.getType())) {//岗位的命名不一致
			groups = allGroup.get("position");
		}else {
			groups = allGroup.get(this.getType());
		}
		
		for(IGroup group:groups) {
			if(json.getString("id").contains(group.getGroupId())) {
				return true;
			}
		}
		
		return false;
	}
	
}
