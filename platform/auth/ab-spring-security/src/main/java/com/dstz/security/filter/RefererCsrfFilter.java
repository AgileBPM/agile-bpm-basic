package com.dstz.security.filter;

import com.alibaba.fastjson.JSON;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.security.IngoreChecker;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防止CSRF跨站请求攻击。<br>
 * 这个主要是防止外链连入到本系统。
 */
public class RefererCsrfFilter extends IngoreChecker implements Filter {


    @Override
    public void destroy() {
    	
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
       
        //判断是否外链。
        String referer = req.getHeader("Referer");
        String serverName = request.getServerName();
        //请求不是来自本网站。
        if (null != referer && referer.indexOf(serverName) < 0) {
            //是否包含当前URL
            boolean isIngoreUrl = this.isIngores(referer);
            if (isIngoreUrl) {
                chain.doFilter(request, response);
            } else {
            	String msg = String.format("系统不支持当前域名的访问，请联系管理员！<br> 服务器：%s,当前域名:%s",serverName ,referer);
            	 ResultMsg resultMsg = new ResultMsg<>(BaseStatusCode.PARAM_ILLEGAL,msg);
                 response.getWriter().print(JSON.toJSONString(resultMsg));
            }
        } else {
            chain.doFilter(request, response);
        }
    }
    
    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
