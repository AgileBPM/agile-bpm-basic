package com.dstz.security.login.context;


import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.context.ICurrentContext;
import com.dstz.org.api.model.IGroup;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.service.GroupService;
import com.dstz.org.api.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.Locale;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;


public class LoginContext implements ICurrentContext {

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
        if (currentUser.get() != null) {
            IUser user = currentUser.get();
            return user;
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();

        if (securityContext == null) return null;

        Authentication auth = securityContext.getAuthentication();

        if (auth == null) return null;

        Object principal = auth.getPrincipal();
        if (principal != null && principal instanceof IUser) {
            return (IUser) principal;
        }

        return null;
    }


    public IGroup getCurrentGroup() {
        if (currentOrg.get() != null) {
            return currentOrg.get();
        }
        String userId = getCurrentUserId();
        //从缓存中取
        ICache iCache = (ICache) AppUtil.getBean(ICache.class);
        String userKey = ICurrentContext.CURRENT_ORG + userId;
        if (iCache.containKey(userKey)) {
            return (IGroup) iCache.getByKey(userKey);
        }
        //获取当前人的主部门
        IGroup group = groupService.getMainGroup(userId);
        if (group != null) setCurrentGroup(group);
        return group;
    }

    /**
     * 将当前组织放到线程变量和缓存中
     */
    public void setCurrentGroup(IGroup group) {
        currentOrg.set(group);
        //将当前人和组织放到缓存中。
        String userId = getCurrentUserId();
        ICache iCache = (ICache) AppUtil.getBean(ICache.class);
        String userKey = ICurrentContext.CURRENT_ORG + userId;
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
