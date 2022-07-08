package com.dstz.base.api.response;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author Jeff
 */
public interface IResult extends Serializable {

    /**
     * 本地调用是否成功
     *
     * @return 是否成功
     */
    Boolean getIsOk();

    /**
     * 调用状态码
     *
     * @return 状态码
     */
    String getCode();

    /**
     * 调用信息
     *
     * @return 调用信息
     */
    String getMsg();

    /**
     * 调用出错堆栈信息
     *
     * @return 出错堆栈信息
     */
    String getCause();
}
