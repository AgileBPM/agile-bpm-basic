package com.dstz.base.api.exception;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;

/**
 * @author jeff
 * 2017-11-19 20:38:08
 * @说明 业务消息，通常用于业务代码反馈，非系统异常！
 */
public class BusinessMessage extends RuntimeException {
    private static final long serialVersionUID = -7289238698048230824L;
    public IStatusCode statusCode = BaseStatusCode.SYSTEM_ERROR;

    public BusinessMessage(String msg) {
        super(msg);
    }

    public BusinessMessage(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BusinessMessage(Throwable throwable) {
        super(throwable);
    }

    public BusinessMessage(String msg, IStatusCode errorCode) {
        super(msg);
        this.statusCode = errorCode;
    }

    public BusinessMessage(IStatusCode errorCode) {
        super(errorCode.getDesc());
        this.statusCode = errorCode;
    }

    public BusinessMessage(IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getDesc(), throwable);
        this.statusCode = errorCode;
    }

    public BusinessMessage( String msg,IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getDesc() + ":" + msg, throwable);
        this.statusCode = errorCode;
    }

    public String getStatuscode() {
        if (statusCode == null) return "";
        return statusCode.getCode();
    }


    public IStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(IStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
