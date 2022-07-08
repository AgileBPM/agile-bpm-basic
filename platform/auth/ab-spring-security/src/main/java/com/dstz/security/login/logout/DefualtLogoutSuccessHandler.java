package com.dstz.security.login.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.jwt.JWTService;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.CookieUitl;
import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.context.ICurrentContext;
import com.dstz.security.login.UserDetailsServiceImpl;
import com.dstz.security.login.model.LoginUser;

public class DefualtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JWTService jwtService = AppUtil.getBean(JWTService.class);
        ICache icache =  AppUtil.getBean(ICache.class);
    	
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setCharacterEncoding("UTF-8");
        
       LoginUser user = (LoginUser) authentication.getPrincipal();
       //删除组织缓存
       icache.delByKey(ICurrentContext.CURRENT_ORG .concat(user.getId()));
       
        //设置过期
        if(jwtService.getJwtEnabled()) {
    	  String authHeader = request.getHeader(jwtService.getJwtHeader());
    	  if(StringUtil.isEmpty(authHeader)) {
    		  authHeader = CookieUitl.getValueByName(jwtService.getJwtHeader(), request);
    	  }
          if (authHeader != null && authHeader.startsWith(jwtService.getJwtTokenHead())) {
              String authToken = authHeader.substring(jwtService.getJwtTokenHead().length());
              jwtService.logoutRedisToken(authToken);
          }
          
          //删除 UserDetial 
          icache.delByKey(UserDetailsServiceImpl.loginUserCacheKey.concat(user.getAccount()));
          CookieUitl.delCookie(jwtService.getJwtHeader(), httpRequest, response);
        }
        
        
        
        ResultMsg resultMsg = new ResultMsg(BaseStatusCode.SUCCESS, "退出登录成功");
        response.getWriter().print(JSONObject.toJSONString(resultMsg));
        return;
    }

}
