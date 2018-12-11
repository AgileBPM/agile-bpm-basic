package com.dstz.org.core.model;

import com.dstz.base.core.model.BaseModel;
import com.dstz.org.api.model.IUser;
import com.dstz.org.api.model.IdentityType;
import com.dstz.sys.util.SysPropertyUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;


/**
 * <pre>
 * 描述：用户表 实体对象
 * </pre>
 */
public class User extends BaseModel implements IUser{
    /**
     * 姓名
     */
    protected String fullname;

    /**
     * 账号
     */
    protected String account;

    /**
     * 密码
     */
    protected String password;

    /**
     * 邮箱
     */
    protected String email;

    /**
     * 手机号码
     */
    protected String mobile;

    /**
     * 微信号
     */
    protected String weixin;

    /**
     * 创建时间
     */
    protected java.util.Date createTime;

    /**
     * 地址
     */
    protected String address;

    /**
     * 头像
     */
    protected String photo;

    /**
     * 性别：男，女，未知
     */
    protected String sex;

    /**
     * 来源
     */
    protected String from = "system";

    /**
     * 0:禁用，1正常
     */
    protected Integer status;


    /**
     * 组织ID，用于在组织下添加用户。
     */
    protected String groupId = "";
    

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * 返回 姓名
     *
     * @return
     */
    public String getFullname() {
        return this.fullname;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 返回 账号
     *
     * @return
     */
    public String getAccount() {
        return this.account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 返回 密码
     *
     * @return
     */
    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 返回 邮箱
     *
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 返回 手机号码
     *
     * @return
     */
    public String getMobile() {
        return this.mobile;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    /**
     * 返回 微信号
     *
     * @return
     */
    public String getWeixin() {
        return this.weixin;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回 创建时间
     *
     * @return
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 返回 地址
     *
     * @return
     */
    public String getAddress() {
        return this.address;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 返回 头像
     *
     * @return
     */
    public String getPhoto() {
        return this.photo;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 返回 性别：男，女，未知
     *
     * @return
     */
    public String getSex() {
        return this.sex;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 返回 来源
     *
     * @return
     */
    public String getFrom() {
        return this.from;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 返回 0:禁用，1正常
     *
     * @return
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("fullname", this.fullname)
                .append("account", this.account)
                .append("password", this.password)
                .append("email", this.email)
                .append("mobile", this.mobile)
                .append("weixin", this.weixin)
                .append("createTime", this.createTime)
                .append("address", this.address)
                .append("photo", this.photo)
                .append("sex", this.sex)
                .append("from", this.from)
                .append("status", this.status)
                .toString();
    }

    public String getIdentityType() {
        return IdentityType.USER;
    }

    public String getUserId() {
        return this.id;
    }

    public void setUserId(String userId) {
        this.id = userId;

    }

    public void setAttributes(Map<String, String> map) {

    }

    public Map<String, String> getAttributes() {
        return null;
    }

    public String getAttrbuite(String key) {
        return null;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}