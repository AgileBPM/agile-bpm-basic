package com.dstz.security.constant;

import com.dstz.base.api.constant.IStatusCode;

public enum PlatFormStatusCode implements IStatusCode {

    SUCCESS("200", "成功"),
    SYSTEM_ERROR("500", "系统异常"),
    TIMEOUT("401", "访问超时"),
    NO_ACCESS("403", "访问受限"),
    LOGIN_ERROR("405", "登录失败"),
    PARAM_ILLEGAL("100", "参数校验不通过");

    private String code;
    private String desc;
    private String system;

    PlatFormStatusCode(String code, String description) {
        this.setCode(code);
        this.setDesc(description);
        this.setSystem("PLATFORM");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String msg) {
        this.desc = msg;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

}
