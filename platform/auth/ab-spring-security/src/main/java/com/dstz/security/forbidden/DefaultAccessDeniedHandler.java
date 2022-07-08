package com.dstz.security.forbidden;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.response.impl.ResultMsg;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 返回无权限
 */
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        response.setCharacterEncoding("UTF-8");

        ResultMsg resultMsg = new ResultMsg(BaseStatusCode.NO_ACCESS, ex.getMessage());
        response.getWriter().print(JSONObject.toJSONString(resultMsg));
        return;
    }

}