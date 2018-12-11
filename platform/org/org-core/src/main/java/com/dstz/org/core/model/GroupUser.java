package com.dstz.org.core.model;

import com.dstz.base.core.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * <pre>
 * 描述：用户组织关系 实体对象
 * </pre>
 */
public class GroupUser extends BaseModel {

    /**
     * 主关系
     */
    public static final Integer MASTER_YES = 1;

    /**
     * 非主关系
     */
    public static final Integer MASTER_NO = 0;

    /**
     * groupId
     */
    protected String groupId;

    /**
     * userId
     */
    protected String userId;

    /**
     * 0:非主部门，1：主部门
     */
    protected Integer isMaster;

    /**
     * 关系id
     */
    protected String relId;


    public GroupUser(String groupId, String userId, String relId) {
        this.groupId = groupId;
        this.userId = userId;
        this.relId = relId;
        this.isMaster = 0;
    }

    public GroupUser() {
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 返回 user_id_
     *
     * @return
     */
    public String getUserId() {
        return this.userId;
    }

    public void setIsMaster(Integer isMaster) {
        this.isMaster = isMaster;
    }

    /**
     * 返回 0:非主部门，1：主部门
     *
     * @return
     */
    public Integer getIsMaster() {
        return this.isMaster;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 返回 rel_id_
     *
     * @return
     */
    public String getRelId() {
        return this.relId;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("groupId", this.groupId)
                .append("userId", this.userId)
                .append("isMaster", this.isMaster)
                .append("relId", this.relId)
                .toString();
    }


}