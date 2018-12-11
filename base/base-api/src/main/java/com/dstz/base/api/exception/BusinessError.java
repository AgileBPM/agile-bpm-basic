package com.dstz.base.api.exception;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;

/**
 *  系统异常,常常用于强制捕获的异常的包装
 * @author jeff
 * 2017-11-19 20:38:08
 *  
 */
public class BusinessError extends RuntimeException {
    private static final long serialVersionUID = -7289238698048230824L;
    public IStatusCode statusCode = BaseStatusCode.SYSTEM_ERROR;

    public BusinessError(String msg) {
        super(msg);
    }

    public BusinessError(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BusinessError(Throwable throwable) {
        super(throwable);
    }

    public BusinessError(String msg, IStatusCode errorCode) {
        super(msg);
        this.statusCode = errorCode;
    }

    public BusinessError(IStatusCode errorCode) {
        super(errorCode.getDesc());
        this.statusCode = errorCode;
    }

    public BusinessError(IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getDesc(), throwable);
        this.statusCode = errorCode;
    }

    public BusinessError( String msg,IStatusCode errorCode, Throwable throwable) {
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
