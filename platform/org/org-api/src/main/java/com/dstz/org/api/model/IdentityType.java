package com.dstz.org.api.model;

import java.io.Serializable;

/**
 * <pre>
 * 描述：用户标识类型
 * </pre>
 */
public interface IdentityType extends Serializable {

    /**
     * 用户
     */
    public static final String USER = "user";

    /**
     * 用户组
     */
    public static final String GROUP = "group";

    /**
     * 返回用户标识类型
     *
     * @return String
     */
    String getIdentityType();


}
