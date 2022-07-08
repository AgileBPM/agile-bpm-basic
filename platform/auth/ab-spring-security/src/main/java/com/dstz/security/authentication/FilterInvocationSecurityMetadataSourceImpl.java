package com.dstz.security.authentication;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.service.SysResourceService;
import com.dstz.security.IngoreChecker;
import com.dstz.security.constans.PlatformConsts;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 根据当前的URL获取他上面分配的角色列表
 */
public class FilterInvocationSecurityMetadataSourceImpl extends IngoreChecker implements FilterInvocationSecurityMetadataSource {

    @Resource
    SysResourceService sysResourceService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Collection<ConfigAttribute> configAttribute = new HashSet<ConfigAttribute>();

        FilterInvocation filterInvocation = ((FilterInvocation) object);
        HttpServletRequest request = filterInvocation.getRequest();

        String url = request.getRequestURI();
        url = removeCtx(url, request.getContextPath());

        if (isIngores(url)) {
            configAttribute.add(PlatformConsts.ROLE_CONFIG_ANONYMOUS);
            return configAttribute;
        }

        //根据当前的URL获取所需要的角色
        Set<String> roles = sysResourceService.getAccessRoleByUrl(url);
        if (CollectionUtil.isNotEmpty(roles)) {
        	roles.forEach(role-> configAttribute.add(new SecurityConfig(role)));
        } else {
            configAttribute.add(PlatformConsts.ROLE_CONFIG_PUBLIC);
        }
        return configAttribute;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }


    /**
     * 获取当前URL
     *
     * @param url
     * @param ctxPath
     * @return
     */
    private static String removeCtx(String url, String ctxPath) {
        url = url.trim();
        if (StringUtil.isEmpty(ctxPath)) return url;
        if (StringUtil.isEmpty(url)) return "";
        if (url.startsWith(ctxPath)) {
            url = url.replaceFirst(ctxPath, "");
        }
        return url;
    }

}
