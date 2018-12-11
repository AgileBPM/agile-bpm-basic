package com.dstz.base.api.constant;

/**
 * @author jeef
 * @说明 系统状态码定义抽象接口<br>
 * 子模块或者系统需要定义自己的系统状态码<br>
 * @StatusCode 为基础模块定义的基本码值，1~500 目前已经被占用
 */
public interface IStatusCode {
    /**
     * 状态码
     *
     * @return
     */
    public String getCode();

    /**
     * 异常信息
     *
     * @return
     */
    public String getDesc();

    /**
     * 系统编码
     *
     * @return
     */
    public String getSystem();

}
