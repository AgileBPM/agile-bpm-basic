package com.dstz.bus.api.remote;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * 描述：远程业务数据对象
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2019年7月16日
 * 版权:summer
 * </pre>
 */
@ApiModel(description="远程业务对象DTO")
public class RemoteBusinessData<T> implements Serializable {
	private static final long serialVersionUID = -6612311363502701439L;
	/**
	 * 业务对象KEY
	 */
	@ApiModelProperty("业务对象KEY")
	protected String boKey;
	/**
	 * <pre>
	 * 业务对象 JSON， 
	 * 在  agilebpm T 为业务对象的 JSON，
	 * 在 业务系统         T 可以直接利用模型驱动转换成 实体对象 eg:User
	 * </pre>
	 */
	@ApiModelProperty("业务对象JSON")
	protected T data;
	
	/**
	 * <pre>
	 * 业务对象的id
	 * </pre>
	 */
	@ApiModelProperty("业务对象id")
	protected Object id;
	
	public String getBoKey() {
		return boKey;
	}

	public void setBoKey(String boKey) {
		this.boKey = boKey;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

}
