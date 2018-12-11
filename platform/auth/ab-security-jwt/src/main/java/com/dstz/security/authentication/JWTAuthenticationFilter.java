package com.dstz.security.authentication;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.util.CookieUitl;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.security.jwt.service.JWTService;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	
	@Resource
    private JWTService jwtService;
	@Resource 
	private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    	if(!jwtService.getJwtEnabled())  {
    		chain.doFilter(request, response);
    		return ;
    	}
    	
    	  String authHeader = request.getHeader(jwtService.getJwtHeader());
    	  //支持 session的形式，从cookie中尝试获取
    	  if(StringUtil.isEmpty(authHeader)) {
    		  authHeader = CookieUitl.getValueByName(jwtService.getJwtHeader(), request);
    	  }
    	  
          String tokenHead = this.jwtService.getJwtTokenHead();
          
          if (authHeader != null && authHeader.startsWith(tokenHead)) {
              String authToken = authHeader.substring(tokenHead.length());
              if (jwtService.validateToken(authToken)) {
                  String username = jwtService.getSubjectFromToken(authToken);
                  
                  //从缓存中获取，获取失败则通过实现接口的方法获取用户
                  UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                  
                  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  logger.debug("authenticated user " + username + ", setting security context");
                  SecurityContextHolder.getContext().setAuthentication(authentication);
              }
          }else {
        	  logger.info("无权限访问"+request.getRequestURI());
          }
          chain.doFilter(request, response); ;
      }
}
