package com.dstz.sys.api.constant;

import com.dstz.base.api.constant.IStatusCode;

public enum SysStatusCode implements IStatusCode {

    SUCCESS("200", "成功"),
    SYSTEM_ERROR("500", "系统异常"),
    TIMEOUT("401", "访问超时"),
    NO_ACCESS("403", "访问受限"),
    PARAM_ILLEGAL("100", "参数校验不通过"),


    SERIALNO_EXSIT("50001", "流水号已存在"),
    SERIALNO_NO_EXSIT("50002", "流水号不存在"),;

    private String code;
    private String desc;
    private String system;

    SysStatusCode(String code, String description) {
        this.setCode(code);
        this.setDesc(description);
        this.setSystem("SYS");
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
