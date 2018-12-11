package com.dstz.base.api.model;

import java.io.Serializable;

/**
 * ID 接口 <br>
 * 若实现改接口、创建时、会自动赋值ID字段
 */
public interface IDModel extends Serializable{
	  /**
     * <pre>
     * 主键
     * </pre>
     *
     * @return
     */
    public String getId();

    /**
     * <pre>
     * 设置主键
     * </pre>
     *
     * @param id
     */
    public void setId(String id);
}
