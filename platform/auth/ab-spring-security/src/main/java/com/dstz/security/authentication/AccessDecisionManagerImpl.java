package com.dstz.security.authentication;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dstz.security.constans.PlatformConsts;
import com.dstz.security.login.model.LoginUser;

public class AccessDecisionManagerImpl implements AccessDecisionManager {
	
	/**
	 * 判断 当前用户所拥有的角色 是否满足 当前资源所需的角色
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes.contains(PlatformConsts.ROLE_CONFIG_ANONYMOUS)) {
			return;
		}

		Object principal = authentication.getPrincipal();

		if (principal == null || "anonymousUser".equals(principal)) {
			throw new AccessDeniedException("请登录");
		}

		if (!(principal instanceof LoginUser)) {
			throw new AccessDeniedException("登录对象必须为LoginUser实现类");
		}

		// 获取当前用户的角色。
		UserDetails user = (UserDetails) principal;
		Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) user.getAuthorities();

		// 超级访问
		if (roles.contains(PlatformConsts.ROLE_GRANT_SUPER)) {
			return;
		}
		// 公开访问
		if (configAttributes.contains(PlatformConsts.ROLE_CONFIG_PUBLIC)) {
			return;
		}
		// 授权访问
		// 遍历当前用户的角色，如果当前页面对应的角色包含当前用户的某个角色则有权限访问。
		for (GrantedAuthority hadRole : roles) {
			if (configAttributes.contains(new SecurityConfig(hadRole.getAuthority()))) {
				return;
			}
		}
		throw new AccessDeniedException("对不起,您没有该操作的权限!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
