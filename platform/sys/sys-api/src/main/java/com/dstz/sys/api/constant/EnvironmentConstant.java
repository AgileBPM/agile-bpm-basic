package com.dstz.sys.api.constant;

import com.dstz.base.core.util.StringUtil;


public enum EnvironmentConstant {
    DEV("DEV", ",开发-默认"),
    SIT("SIT", "测试"),
    UAT("UAT", "用户测试"),
    GRAY("GRAY", "灰度"),
    PROD("PROD", "生产");


    private String key;
    private String value;

    EnvironmentConstant(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

    public static String getKes() {
        StringBuilder sb = new StringBuilder();
        for (EnvironmentConstant e : EnvironmentConstant.values()) {
            sb.append("[").append(e.key).append("]");
        }
        return sb.toString();
    }

    public static boolean contain(String key) {
        if (StringUtil.isEmpty(key)) return false;

        for (EnvironmentConstant e : EnvironmentConstant.values()) {
            if (key.equals(e.key)) return true;
        }
        return false;
    }

}
