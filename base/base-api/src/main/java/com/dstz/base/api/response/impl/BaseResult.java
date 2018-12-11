package com.dstz.base.api.response.impl;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;
import com.dstz.base.api.constant.StatusCode;
import com.dstz.base.api.response.IResult;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
public class BaseResult implements IResult {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("本次调用是否成功")
    private Boolean isOk;// 本次调用是否成功
    @ApiModelProperty("操作提示信息")
    private String msg = "";//
    @ApiModelProperty("异常堆栈信息")
    private String cause = ""; //StackTrace
    @ApiModelProperty("状态码")
    private StatusCode statusCode;

    public BaseResult() {

    }

    public BaseResult(String errorMsg) {
        this.msg = errorMsg;
        this.isOk = false;
    }

    public void IsOk(Boolean isSuccess) {
        if (isSuccess && statusCode == null) {
            this.setStatusCode(BaseStatusCode.SUCCESS);
        }

        this.isOk = isSuccess;
    }

    public Boolean IsOk() {
        return isOk;
    }

    public Boolean getIsOk() {
        return isOk;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }


    public void setStackTrace(String stackTrace) {
        setCause(stackTrace);
    }

    public String getCause() {
        return cause;
    }

    public void setIsOk(Boolean isOK) {
        this.isOk = isOK;
    }


    public void setStatusCode(IStatusCode statusCode) {
        this.statusCode = new StatusCode(statusCode);
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public IStatusCode getStatusCode() {
        return this.statusCode;
    }


    @Override
    public String getCode() {
        if (statusCode != null) return statusCode.getCode();
        return "";
    }
}
