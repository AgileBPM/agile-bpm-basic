package com.dstz.security.rest.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.jwt.JWTService;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.ControllerTools;
import com.dstz.base.rest.util.RequestUtil;
import com.dstz.security.constant.PlatFormStatusCode;
import com.dstz.security.login.SecurityUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@Api(description="登陆服务接口")
@RestController
public class LoginController extends ControllerTools {
    SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();
    @Resource
    JWTService jWTService;

    @RequestMapping(value = "/org/login/valid",method= {RequestMethod.POST,RequestMethod.GET})
    @CatchErr
    @ApiOperation(value = "用户登录",notes="登录鉴权")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "form", dataType = "String", name = "account", value = "账号"),
		@ApiImplicitParam(paramType = "form", dataType = "String", name = "password", value = "密码"),
		@ApiImplicitParam(paramType = "form", dataType = "String", name = "audience", value = "接收方，pc/mobile/android")})
    public ResultMsg login(HttpServletRequest request, HttpServletResponse response) {
        String account = RequestUtil.getString(request, "account");
        String password = RequestUtil.getString(request, "password");
        String audience = RequestUtil.getString(request, "audience","pc");
        if (StringUtil.isEmpty(account)) {
            throw new BusinessMessage("账户不能为空", PlatFormStatusCode.LOGIN_ERROR);
        }
        if (StringUtil.isEmpty(password)) {
            throw new BusinessMessage("密码不能为空", PlatFormStatusCode.LOGIN_ERROR);
        }

        try {
        	// 用security 登录机制处理下
            Authentication auth = SecurityUtil.login(request, account, password, false);
            
            //jwt 模式 支持cookie模式和token调用形式
            if(jWTService.getJwtEnabled()) {
            	String token = jWTService.generateToken(account,audience);
            	//直接写入 cookie ,把cookie当做session来用
            	wiriteJwtToken2Cookie(request,response,token);
            	return getSuccessResult(token, "登录成功！");
            }else {
            	//写入session的
            	sessionStrategy.onAuthentication(auth, request, response);
            	//执行记住密码动作。
            	SecurityUtil.writeRememberMeCookie(request, response, account, password);
            	wiriteToken(request, response);
            	return getSuccessResult("登录成功！");
            	
            }
            
        } catch (BadCredentialsException e) {
            throw new BusinessMessage("账号或密码错误", PlatFormStatusCode.LOGIN_ERROR);
        } catch (DisabledException e) {
            throw new BusinessMessage("帐号已禁用", PlatFormStatusCode.LOGIN_ERROR);
        } catch (LockedException e) {
            throw new BusinessMessage("帐号已锁定", PlatFormStatusCode.LOGIN_ERROR);
        } catch (AccountExpiredException e) {
            throw new BusinessMessage("帐号已过期", PlatFormStatusCode.LOGIN_ERROR);
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new BusinessException(PlatFormStatusCode.LOGIN_ERROR, ex);
        }
    }


	protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";

    private void wiriteToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

        if (token != null) {
            Cookie cookie = new Cookie("XSRF-TOKEN", token.getToken());
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
    }
    
    /**
     * 类似session的形式将 token 写入 cookie 设值
     * @param request
     * @param response
     * @param token
     * @param audience 
     * @param jwtHeader
     */
    private void wiriteJwtToken2Cookie(HttpServletRequest request,HttpServletResponse response, String token){
    	Cookie cookie = new Cookie(jWTService.getJwtHeader(), jWTService.getJwtTokenHead()+ token);
    	String contextPath = request.getContextPath();
    	if(StringUtil.isEmpty(contextPath)) {
    		contextPath = "/";
    	}
    	cookie.setPath(contextPath);
    	response.addCookie(cookie);
	}
}