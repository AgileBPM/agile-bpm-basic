package com.dstz.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dstz.base.db.datasource.DbContextHolder;
import com.dstz.base.rest.util.RequestContext;
import com.dstz.sys.util.ContextUtil;

/**
 * 向 http请求中，设置当前request信息,以便在线程中使用request
 * 清除http中线程变量
 */
public class RequestThreadFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            cleanThreadLocal();
            RequestContext.setHttpServletRequest((HttpServletRequest) request);
            RequestContext.setHttpServletResponse((HttpServletResponse) response);
            chain.doFilter(request, response);
        } finally {
            cleanThreadLocal();
        }
    }

    private void cleanThreadLocal() {
        RequestContext.clearHttpReqResponse();
        ContextUtil.clearAll();
        DbContextHolder.setDefaultDataSource();
  //      ActivitiDefCache.clearLocal();
  //      BpmContext.cleanTread();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
