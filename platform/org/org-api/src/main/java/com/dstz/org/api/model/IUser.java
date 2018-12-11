package com.dstz.org.api.model;

import java.io.Serializable;
import java.util.Map;

import com.dstz.base.api.model.IBaseModel;

/**
 * 描述：用户实体接口
 */
public interface IUser extends IdentityType ,Serializable{
    /**
     * 男性=Male
     */
    public static final String SEX_MALE = "Male";
    /**
     * 女性=Female
     */
    public static final String SEX_FAMALE = "Female";

    /**
     * 用户标识Id
     *
     * @return String
     */
    String getUserId();

    void setUserId(String userId);

    /**
     * 用户姓名
     *
     * @return String
     */
    String getFullname();

    void setFullname(String fullName);

    /**
     * 用户账号
     *
     * @return String
     */
    String getAccount();

    void setAccount(String account);

    /**
     * 获取密码
     *
     * @return String
     */
    String getPassword();

    /**
     * 邮件。
     *
     * @return String
     */
    String getEmail();

    /**
     * 手机。
     *
     * @return String
     */
    String getMobile();

    /**
     * 设置用户其它属性
     *
     * @param map
     */
    void setAttributes(Map<String, String> map);

    /**
     * 获取用户其它属性
     *
     * @param map
     */
    Map<String, String> getAttributes();

    /**
     * 根据属性获取属性值。
     *
     * @param key
     * @return
     */
    String getAttrbuite(String key);

    Integer getStatus();

    String getWeixin();
}
