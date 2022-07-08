package com.dstz.security.login.context;


import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.CookieUitl;
import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.constant.GroupTypeConstant;
import com.dstz.org.api.context.ICurrentContext;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.api.service.UserService;

public class LoginContext implements ICurrentContext {
private static final Logger log = LoggerFactory.getLogger(LoginContext.class); 
    /**
     * 存放当前用户登录的语言环境
     */
    private static ThreadLocal<Locale> currentLocale = new ThreadLocal<Locale>();
    /**
     * 当前上下文用户。
     */
    private static ThreadLocal<IUser> currentUser = new ThreadLocal<IUser>();
    /**
     * 当前上下文用户。
     */
    private static ThreadLocal<IGroup> currentOrg = new ThreadLocal<IGroup>();

    @Resource
    GroupService groupService;
    @Resource
    UserService userService;

    /**
     * 获取当前语言环境
     *
     * @return
     */
    public Locale getLocale() {
        if (currentLocale.get() != null) {
            return currentLocale.get();
        }
        setLocale(new Locale("zh", "CN"));
        return currentLocale.get();
    }

    /**
     * 返回当前用户ID
     *
     * @return String
     */
    public String getCurrentUserId() {
        IUser user = getCurrentUser();
        if (user == null) return null;
        return user.getUserId();
    }

    public IUser getCurrentUser() {
        if (currentUser.get() != null)  return currentUser.get();
        
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth != null && !"anonymousUser".equals(auth.getPrincipal())) {
    		return (IUser)auth.getPrincipal();
    	}
        return null;
    }


    public IGroup getCurrentGroup() {
        if (currentOrg.get() != null)  return currentOrg.get();
        
        //从缓存中取
        ICache<IGroup> iCache = AppUtil.getBean(ICache.class);
        String userKey = ICurrentContext.CURRENT_ORG .concat(getCurrentUserId());
        
        IGroup currentGroup = iCache.getByKey(userKey);
        if (currentGroup != null)  return currentGroup;
        // cookie 中存一份标识
        String cookieCurrrentOrgId = CookieUitl.getValueByName("currentOrg");
        if(StringUtil.isNotEmpty(cookieCurrrentOrgId)) {
        	IGroup group = groupService.getById(GroupTypeConstant.ORG.key(), cookieCurrrentOrgId);
        	if(group != null) {
        		cacheCurrentGroup(group);
        		return group;
        	}
        }
        
        
        //获取当前人的主部门
        IGroup group = groupService.getMainGroup(getCurrentUserId());
        if (group != null) {
        	cacheCurrentGroup(group);
        }
        return group;
    }

    /**
     * 将当前组织放到线程变量和缓存中
     */
    public void cacheCurrentGroup(IGroup group) {
        currentOrg.set(group);
        //将当前人和组织放到缓存中。
        String userId = getCurrentUserId();
		ICache<IGroup> iCache = AppUtil.getBean(ICache.class);
        String userKey = ICurrentContext.CURRENT_ORG .concat( userId);
        iCache.add(userKey, group);
    }

    /**
     * 清理线程中的用户变量，以及他的岗位信息
     */
    public void clearCurrentUser() {
        currentUser.remove();
        currentOrg.remove();
    }

    public void setCurrentUser(IUser user) {
        currentUser.set(user);
    }

    public void clearLocale() {
        currentLocale.remove();
    }

    public void setLocale(Locale local) {
        currentLocale.set(local);
    }

    @Override
    public void setCurrentUserByAccount(String account) {
        if (StringUtil.isEmpty(account)) {
            throw new RuntimeException("输入帐号为空!");
        }
        IUser user = userService.getUserByAccount(account);
        if (user == null) {
            throw new RuntimeException("系统中没有帐号[" + account + "]对应的用户");
        }
        currentUser.set(user);
    }

}
