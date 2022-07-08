package com.dstz.org.api.model;

import java.io.Serializable;
import java.util.Map;

import com.dstz.base.api.model.IBaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户实体接口
 */
@ApiModel(description="用户信息")
public interface IUser extends Serializable{
    /**
     * 用户标识Id
     *
     * @return String
     */
    @ApiModelProperty("用户ID")
    String getUserId();

    void setUserId(String userId);

    /**
     * 用户姓名
     *
     * @return String
     */
    @ApiModelProperty("用户名")
    String getFullname();

    void setFullname(String fullName);

    /**
     * 用户账号
     *
     * @return String
     */
    @ApiModelProperty("账户")
    String getAccount();

    void setAccount(String account);

    /**
     * 获取密码
     *
     * @return String
     */
    @ApiModelProperty("密码")
    String getPassword();

    /**
     * 邮件。
     *
     * @return String
     */
    @ApiModelProperty("Email")
    String getEmail();

    /**
     * 手机。
     *
     * @return String
     */
    @ApiModelProperty("手机号")
    String getMobile();

    @ApiModelProperty("微信号")
    String getWeixin();
    
    @ApiModelProperty("是否启用 0/1")
    Integer getStatus();

}
