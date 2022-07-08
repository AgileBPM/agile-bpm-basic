package com.dstz.org.core.script;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.api.service.UserService;
import com.dstz.sys.api.groovy.IScript;
import com.dstz.sys.api.model.DefaultIdentity;
import com.dstz.sys.api.model.SysIdentity;
import com.dstz.sys.util.ContextUtil;

/**
 * <pre>
 * 描述：常用org的脚本
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2019年5月26日
 * 版权:summer
 * </pre>
 */
@Component
public class OrgScript implements IScript {
	@Resource
	GroupService groupService;
	@Resource
	UserService userService;

	/**
	 * <pre>
	 * 获取某个组织下的某些角色的人员列表
	 * </pre>
	 * 
	 * @param groupIds
	 *            组织id，多个以“,”分隔；为空，则取当前用户所在主组织
	 * @param roleCodes
	 *            角色编码，多个以“,”分隔
	 * @return
	 */
	public Set<SysIdentity> getSisByGroupAndRole(String groupIds, String roleCodes) {
		Set<SysIdentity> identities = new HashSet<>();
		if (StringUtil.isEmpty(groupIds)) {
			if (ContextUtil.getCurrentGroup() == null) {
				throw new BusinessMessage("请为当前人员分配组织");
			}
			groupIds = ContextUtil.getCurrentGroup().getGroupId();
		}
		for (String gi : groupIds.split(",")) {
			List<? extends IUser> users = userService.getUserListByGroup(GroupTypeConstant.ORG.key(), gi);
			users.forEach(user -> {
				List<? extends IGroup> roles = groupService.getGroupsByGroupTypeUserId(GroupTypeConstant.ROLE.key(), user.getUserId());
				roles.forEach(role -> {
					if (roleCodes.contains(role.getGroupCode())) {
						SysIdentity identity = new DefaultIdentity(user.getUserId(), user.getFullname(), SysIdentity.TYPE_USER);
						identities.add(identity);
					}
				});
			});
		}

		return identities;
	}
}
