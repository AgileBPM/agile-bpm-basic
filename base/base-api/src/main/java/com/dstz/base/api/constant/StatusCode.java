package com.dstz.base.api.constant;

public class StatusCode implements IStatusCode {
    private String code;
    private String system;
    private String desc;


    public StatusCode(IStatusCode statusCode) {
        this.code = statusCode.getCode();
        this.system = statusCode.getSystem();
        this.desc = statusCode.getDesc();
    }

    public StatusCode() {
    }
    
    public StatusCode(String code,String desc) {
    	this.code = code;
    	this.desc = desc;
    }
    
    public StatusCode(String code,String desc,String system) {
    	this.code = code;
    	this.desc = desc;
    	this.system = system;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
