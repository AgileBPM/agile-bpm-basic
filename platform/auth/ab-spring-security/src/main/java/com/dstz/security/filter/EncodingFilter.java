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

import com.dstz.base.core.util.StringUtil;

/**
 * 编码过滤器， 设置当前系统的编码,并支持所有跨域请求。跨域的控制通过过滤器配置的形式来控制。
 * @author dstz
 */
public class EncodingFilter implements Filter {

    private String defaultEncoding = "UTF-8";
    private String defaultContentType = "text/html;charset=UTF-8";

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse httpresponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) httpresponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        
        request.setCharacterEncoding(defaultEncoding);
        httpServletResponse.setCharacterEncoding(defaultEncoding);
        httpServletResponse.setContentType(defaultContentType);
        
        //此处我们忽略跨域问题由 @RefererCsrfFilter.java 来处理
        //默认全部支持跨域
        String origin = httpServletRequest.getHeader("Origin");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "accept, origin, content-type");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        
        //其他设置
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", -1);
        
        chain.doFilter(request, httpServletResponse);
        
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String encoding = config.getInitParameter("encoding");
        String contentType = config.getInitParameter("contentType");
        // String ext=config.getInitParameter("ext");
        if (StringUtil.isEmpty(encoding)) {
        	defaultEncoding = encoding;
        }
        if (contentType != null) {
        	defaultContentType = contentType;
        }
    }

}
