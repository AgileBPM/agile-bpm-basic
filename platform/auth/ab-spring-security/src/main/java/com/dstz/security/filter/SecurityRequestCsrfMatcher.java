package com.dstz.security.filter;

import com.dstz.security.IngoreChecker;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class SecurityRequestCsrfMatcher extends IngoreChecker implements RequestMatcher {

    @Override
    public boolean matches(HttpServletRequest request) {

        boolean isIngoreUrl = isIngores(request.getRequestURI());

        if (isIngoreUrl) return true;

        return false;
    }

}
