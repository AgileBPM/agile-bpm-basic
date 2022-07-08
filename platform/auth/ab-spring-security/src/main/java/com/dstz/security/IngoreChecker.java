package com.dstz.security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.collection.CollectionUtil;

public class IngoreChecker {
    private List<Pattern> ingores = new ArrayList<Pattern>();

    public void setIngores(List<String> urls) {
        if (CollectionUtil.isEmpty(urls)) return;
        for (String url : urls) {
            Pattern regex = Pattern.compile(url, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE |
                    Pattern.DOTALL | Pattern.MULTILINE);
            ingores.add(regex);
        }
    }


    /**
     * 判断当前URL是否在忽略的地址中。
     *
     * @param requestUrl
     * @return
     */
    public boolean isIngores(String requestUrl) {
    	// 会再跳转 index.html 所以直接忽略
    	if("/".equals(requestUrl))return true;
    	
        for (Pattern pattern : ingores) {
            Matcher regexMatcher = pattern.matcher(requestUrl);
            if (regexMatcher.find()) {
                return true;
            }
        }
        return false;
    }

}
