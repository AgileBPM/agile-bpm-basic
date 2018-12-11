package com.dstz.base.api.response.impl;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @param <E>
 * @author Administrator
 * @描述 返回结果
 */
@ApiModel
public class ResultMsg<E> extends BaseResult {

    @Deprecated
    public static final int SUCCESS = 1;
    @Deprecated
    public static final int FAIL = 0;
    @Deprecated
    public static final int ERROR = -1;
    @Deprecated
    public static final int TIMEOUT = 2;
    @ApiModelProperty("结果数据")
    private E data = null; // 返回数据

    public ResultMsg() {

    }

    /**
     * 成功，有结果数据
     *
     * @param data
     */
    public ResultMsg(E result) {
        this.IsOk(true);
        this.setData(result);
    }

    public ResultMsg(IStatusCode code, String msg) {
        if (BaseStatusCode.SUCCESS.getCode().equals(code.getCode())) {
            this.setIsOk(true);
        } else {
            this.IsOk(false);
        }
        this.setStatusCode(code);
        this.setMsg(msg);
    }

    @Deprecated
    public ResultMsg(int code, String msg) {
        this.IsOk(code == SUCCESS);
        this.setMsg(msg);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void addMapParam(String key, Object val) {
        if (data == null) {
            Map map = new HashMap();
            this.data = (E) map;
        }
        if (!(this.data instanceof Map)) {
            throw new RuntimeException("设置参数异常！当前返回结果非map对象，无法使用 addMapParam方法获取数据");
        }

        Map map = (Map) data;
        map.put(key, val);
    }

    public Object getMapParam(String key) {
        if (!(this.data instanceof Map)) {
            throw new RuntimeException("获取参数异常！当前返回结果非map对象，无法使用 addMapParam方法获取数据");
        }

        Map map = (Map) data;
        return map.get(key);
    }
}

