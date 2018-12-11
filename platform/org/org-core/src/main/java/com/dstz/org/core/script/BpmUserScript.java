package com.dstz.org.core.script;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.org.core.manager.GroupManager;
import com.dstz.org.core.manager.GroupUserManager;
import com.dstz.org.core.manager.RoleManager;
import com.dstz.org.core.manager.UserManager;
import com.dstz.sys.api.groovy.IScript;

/**
 * 人员脚本
 * 作用：可用于节点处理人
 */
@Service
public class BpmUserScript implements IScript {
    @Resource
    RoleManager roleManager;
    @Resource
    UserManager userManager;
    @Resource
    GroupUserManager groupUserManager;
    @Resource
    GroupManager groupManager;


    /**
     * 根据角色编码获取人员列表
     * @param roleId
     * @return
     *//*
	public Set<BpmIdentity> getListUserByRoleId(String roleCode) {
		List<IUser> list=(List)userManager.getUserListByRoleCode(roleCode);
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity= bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new LinkedHashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	*//**
     * 根据岗位编码获取人员列表
     * @param roleId
     * @return
     *//*
	public Set<BpmIdentity> getListUserByRelCode(String relCode) {
		List<IUser> list=(List)userManager.getListByRelCode(relCode);
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<BpmIdentity> listIdentity= bpmIdentityConverter.convertUserList(list);
		Set<BpmIdentity> set = new HashSet<BpmIdentity>(listIdentity);
		return set;
	}
	
	*//**
     * 获取当前用户上级部门中指角色编码的人员
     * @param roleId
     * @return
     *//*
	public Set<BpmIdentity> getListUserByParentOrgRoleCode(String roleCode) {
		BpmIdentityConverter bpmIdentityConverter = AppUtil.getBean(BpmIdentityConverter.class);
		List<IUser> listResult=new ArrayList<IUser>();
		//角色中的人员
		List<IUser> list=(List)userManager.getUserListByRoleCode(roleCode);
		//求解出当前用户主组织的所有上级部门的人员
		IUser curser=ContextUtil.getCurrentUser();
		OrgUser orgUser= orgUserManager.getOrgUserMaster(curser.getUserId());
		String parentId= orgManager.get(orgUser.getOrgId()).getParentId();
		//上级部门下的人员
		List<IUser> listUser=(List)userManager.getUserListByOrgId(parentId);
		//取交集
		if(BeanUtils.isEmpty(listUser)){
			listResult=list;
		}
		else{
			for (IUser user : listUser) {
				for (IUser itemUser : list) {
					if(user.getUserId().equals(itemUser.getUserId())){
						listResult.add(itemUser);
					}
				}
			}
		}
		List<BpmIdentity> listIdentity=bpmIdentityConverter.convertUserList(listResult);
		Set<BpmIdentity> set = new HashSet<BpmIdentity>(listIdentity);
		return set;
	}*/

}
