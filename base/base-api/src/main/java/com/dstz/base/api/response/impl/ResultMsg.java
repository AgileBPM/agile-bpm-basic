package com.dstz.base.api.response.impl;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.IStatusCode;
import com.dstz.base.api.constant.StatusCode;
import com.dstz.base.api.exception.BusinessException;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果
 * @param <E>
 * @author Administrator
 */
@ApiModel(description="标准的返回结果包装类")
public class ResultMsg<E> extends BaseResult {

    private static final long serialVersionUID = 7420095794453471L;

    @Deprecated
    public static final int SUCCESS = 1;
    @Deprecated
    public static final int FAIL = 0;
    @Deprecated
    public static final int ERROR = -1;
    @Deprecated
    public static final int TIMEOUT = 2;


    /**
     * 结果数据
     */
    @ApiModelProperty("结果数据")
    private E data;

    public ResultMsg() {
        super();
    }

    /**
     * 成功，有结果数据
     *
     * @param result 返回结果
     */
    public ResultMsg(E result) {
        this.setOk(Boolean.TRUE);
        this.setCode(BaseStatusCode.SUCCESS.getCode());
        this.setData(result);
    }

    public ResultMsg(IStatusCode code, String msg) {
        this.setOk(BaseStatusCode.SUCCESS.getCode().equals(code.getCode()));
        this.setCode(code.getCode());
        this.setMsg(msg);
    }

    @Deprecated
    public ResultMsg(int code, String msg) {
        this.setOk(code == SUCCESS);
        this.setMsg(msg);
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
    /**
     * 获取成功的结果、若失败则向上抛出异常，让全局捕获，继续抛出
     * @param result
     * @return
     */
    public static  <T> T getSuccessResult(ResultMsg<T> result) {
    	if(result!= null && result.getIsOk()) {
    		return result.getData();
    	}
    	throw new BusinessException(new StatusCode(result.getCode(), result.getMsg()) );
    }

    public ResultMsg<E> addMapParam(String key, Object val) {
        if (data == null) {
            Map map = new HashMap();
            this.data = (E) map;
        }
        if (!(this.data instanceof Map)) {
            throw new RuntimeException("设置参数异常！当前返回结果非map对象，无法使用 addMapParam方法获取数据");
        }

        Map map = (Map) data;
        map.put(key, val);

        return this;
    }

    public Object getMapParam(String key) {
        if (!(this.data instanceof Map)) {
            throw new RuntimeException("获取参数异常！当前返回结果非map对象，无法使用 addMapParam方法获取数据");
        }

        Map map = (Map) data;
        return map.get(key);
    }
}

