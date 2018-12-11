package com.dstz.base.api.constant;

/**
 * @说明 基础系统状态码定义
 */
public enum BaseStatusCode implements IStatusCode {

    /**
     * 成功
     */
    SUCCESS("200", "成功"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("500", "系统异常"),

    /**
     * 访问超时
     */
    TIMEOUT("401", "访问超时"),

    /**
     * 访问受限
     */
    NO_ACCESS("403", "访问受限"),

    /**
     * 参数校验不通过
     */
    PARAM_ILLEGAL("100", "参数校验不通过"),

    /**
     * 数据已存在
     */
    DATA_EXISTS("101", "数据已存在");

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 系统
     */
    private String system;

    BaseStatusCode(String code, String description) {
        this.code = code;
        this.desc = description;
        this.system = "BASE";
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getSystem() {
        return system;
    }
}
	 
