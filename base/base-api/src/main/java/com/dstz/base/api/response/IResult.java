package com.dstz.base.api.response;

import com.dstz.base.api.constant.IStatusCode;

import java.io.Serializable;

public interface IResult extends Serializable {
    public abstract Boolean getIsOk();

    public abstract IStatusCode getStatusCode();

    public abstract String getCode();

    public abstract String getMsg();

    public abstract String getCause();
}
