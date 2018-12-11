package com.dstz.base.api.exception;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;

/**
 * @author jeff
 * 2017-11-19 20:38:08
 * @说明 业务逻辑异常，常常为可预料异常，此异常常常是开发时，非法操作信息提示。比如 流程表单丢失！
 */
public class BusinessException extends RuntimeException {
  
	private static final long serialVersionUID = 2450214686001409867L;
	public IStatusCode statusCode = BaseStatusCode.SYSTEM_ERROR;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public BusinessException(Throwable throwable) {
        super(throwable);
    }

    public BusinessException(String msg, IStatusCode errorCode) {
        super(msg);
        this.statusCode = errorCode;
    }

    public BusinessException(IStatusCode errorCode) {
        super(errorCode.getDesc());
        this.statusCode = errorCode;
    }

    public BusinessException(IStatusCode errorCode, Throwable throwable) {
        super(errorCode.getDesc(), throwable);
        this.statusCode = errorCode;
    }

    public BusinessException( String msg,IStatusCode errorCode, Throwable throwable) {
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
