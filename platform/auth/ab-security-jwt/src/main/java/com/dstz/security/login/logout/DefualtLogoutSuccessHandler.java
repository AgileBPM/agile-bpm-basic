package com.dstz.security.login.logout;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.rest.util.CookieUitl;
import com.dstz.security.jwt.service.JWTService;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefualtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JWTService jwtService = AppUtil.getBean(JWTService.class);
    	
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setCharacterEncoding("UTF-8");
        
        //设置过期
        if(jwtService.getJwtEnabled()) {
        	CookieUitl.delCookie(jwtService.getJwtHeader(), httpRequest, response);
        }
        
        ResultMsg resultMsg = new ResultMsg(BaseStatusCode.SUCCESS, "退出登录成功");
        response.getWriter().print(JSONObject.toJSONString(resultMsg));
        return;
    }

}
