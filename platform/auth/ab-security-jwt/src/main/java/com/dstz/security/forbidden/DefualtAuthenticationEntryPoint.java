package com.dstz.security.forbidden;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.response.impl.ResultMsg;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 超时访问
 *
 * @author jeff
 */
public class DefualtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setCharacterEncoding("UTF-8");

        ResultMsg resultMsg = new ResultMsg(BaseStatusCode.TIMEOUT, authException.getMessage());
        response.getWriter().print(JSONObject.toJSONString(resultMsg));
        return;
    }

}