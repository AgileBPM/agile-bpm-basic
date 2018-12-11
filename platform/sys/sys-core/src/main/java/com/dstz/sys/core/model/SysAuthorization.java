package com.dstz.sys.core.model;

import com.dstz.base.core.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author jeff
 */
public class SysAuthorization extends BaseModel {

    public static final String RIGHT_TYPE_USER = "user";
    public static final String RIGHT_TYPE_ALL = "all";

    /**
     * id
     */
    protected String rightsId;

    /**
     * 授权对象
     **/
    protected String rightsObject;

    /**
     * 授权目标
     */
    protected String rightsTarget;

    /**
     * 权限类型
     */
    protected String rightsType;

    /**
     * 授权标识
     */
    protected String rightsIdentity;

    /**
     * 标识名字
     */
    protected String rightsIdentityName;

    /**
     * 授权code=identity+type
     */
    protected String rightsPermissionCode;

    /**
     * 创建时间
     */
    protected java.util.Date rightsCreateTime;

    /**
     * 创建人
     */
    protected String rightsCreateBy;

    @Override
    public void setId(String rightsId) {
        this.rightsId = rightsId;
    }

    @Override
    public String getId() {
        return rightsId;
    }

    public void setRightsId(String rightsId) {
        this.rightsId = rightsId;
    }

    /**
     * 返回 id
     *
     * @return
     */
    public String getRightsId() {
        return this.rightsId;
    }

    public void setRightsTarget(String rightsTarget) {
        this.rightsTarget = rightsTarget;
    }

    /**
     * 返回 授权目标
     *
     * @return
     */
    public String getRightsTarget() {
        return this.rightsTarget;
    }

    public void setRightsType(String rightsType) {
        this.rightsType = rightsType;
    }

    /**
     * 返回 权限类型
     *
     * @return
     */
    public String getRightsType() {
        return this.rightsType;
    }

    public void setRightsIdentity(String rightsIdentity) {
        this.rightsIdentity = rightsIdentity;
    }

    /**
     * 返回 授权标识
     *
     * @return
     */
    public String getRightsIdentity() {
        return this.rightsIdentity;
    }

    public String getRightsObject() {
        return rightsObject;
    }

    public void setRightsObject(String rightsObject) {
        this.rightsObject = rightsObject;
    }

    public void setRightsIdentityName(String rightsIdentityName) {
        this.rightsIdentityName = rightsIdentityName;
    }

    /**
     * 返回 标识名字
     *
     * @return
     */
    public String getRightsIdentityName() {
        return this.rightsIdentityName;
    }

    public void setRightsPermissionCode(String rightsPermissionCode) {
        this.rightsPermissionCode = rightsPermissionCode;
    }

    /**
     * 返回 授权code=identity+type
     *
     * @return
     */
    public String getRightsPermissionCode() {
        return this.rightsPermissionCode;
    }

    public void setRightsCreateTime(java.util.Date rightsCreateTime) {
        this.rightsCreateTime = rightsCreateTime;
    }

    /**
     * 返回 创建时间
     *
     * @return
     */
    public java.util.Date getRightsCreateTime() {
        return this.rightsCreateTime;
    }

    public void setRightsCreateBy(String rightsCreateBy) {
        this.rightsCreateBy = rightsCreateBy;
    }

    /**
     * 返回 创建人
     *
     * @return
     */
    public String getRightsCreateBy() {
        return this.rightsCreateBy;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("rightsId", this.rightsId)
                .append("rightsTarget", this.rightsTarget)
                .append("rightsType", this.rightsType)
                .append("rightsIdentity", this.rightsIdentity)
                .append("rightsIdentityName", this.rightsIdentityName)
                .append("rightsPermissionCode", this.rightsPermissionCode)
                .append("rightsCreateTime", this.rightsCreateTime)
                .append("rightsCreateBy", this.rightsCreateBy)
                .toString();
    }
}