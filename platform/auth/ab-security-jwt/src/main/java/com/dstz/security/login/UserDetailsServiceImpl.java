package com.dstz.security.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.IUserRole;
import com.dstz.org.api.service.UserService;
import com.dstz.security.constans.PlatformConsts;
import com.dstz.security.login.model.LoginUser;
import com.dstz.sys.util.ContextUtil;

/**
 * 实现UserDetailsService 接口获取UserDetails 接口实例对象。
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final String loginUserCacheKey = "agilebpm:loginUser:";
    @Resource
    UserService userService;
    @Resource
    ICache<LoginUser> loginUserCache;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	LoginUser loginUser = loginUserCache.getByKey(loginUserCacheKey.concat(username));
    	
    	if(loginUser != null) {
    		return loginUser;
    	}
    	
    	IUser defaultUser = userService.getUserByAccount(username);
    	
        if (defaultUser == null) {
        	throw new UsernameNotFoundException("用户：" + username + "不存在");
        }
        
        loginUser = new LoginUser(defaultUser);

        //构建用户角色。
        List<IUserRole> userRoleList = userService.getUserRole(loginUser.getUserId());
        Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
        for (IUserRole userRole : userRoleList) {
            GrantedAuthority role = new SimpleGrantedAuthority(userRole.getAlias());
            collection.add(role);
        }
        if (ContextUtil.isAdmin(loginUser)) {
            collection.add(PlatformConsts.ROLE_GRANT_SUPER);
        }
        loginUser.setAuthorities(collection);
        
        loginUserCache.add(loginUserCacheKey, loginUser);
        return loginUser;
    }
}
