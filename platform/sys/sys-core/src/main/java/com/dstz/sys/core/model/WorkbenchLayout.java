package com.dstz.sys.core.model;

import com.dstz.base.core.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author jeff
 */
public class WorkbenchLayout extends BaseModel {
    public static final String DEFAULT_LAYOUT = "default_layout";

    /**
     * id_
     */
    protected String id;

    /**
     * 面板id
     */
    protected String panelId;

    /**
     * 自定义宽
     */
    protected Integer custWidth;

    /**
     * 自定义高
     */
    protected Integer custHeight;

    /**
     * 排序
     */
    protected Integer sn;

    /**
     * 用户id
     */
    protected String userId;

    /**
     * 创建时间
     */
    protected java.util.Date createTime;


    public void setId(String id) {
        this.id = id;
    }

    /**
     * 返回 id_
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    /**
     * 返回 面板id
     *
     * @return
     */
    public String getPanelId() {
        return this.panelId;
    }

    public void setCustWidth(Integer custWidth) {
        this.custWidth = custWidth;
    }

    /**
     * 返回 自定义宽
     *
     * @return
     */
    public Integer getCustWidth() {
        return this.custWidth;
    }

    public void setCustHeight(Integer custHeight) {
        this.custHeight = custHeight;
    }

    /**
     * 返回 自定义高
     *
     * @return
     */
    public Integer getCustHeight() {
        return this.custHeight;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    /**
     * 返回 排序
     *
     * @return
     */
    public Integer getSn() {
        return this.sn;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 返回 用户id
     *
     * @return
     */
    public String getUserId() {
        return this.userId;
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

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("panelId", this.panelId)
                .append("custWidth", this.custWidth)
                .append("custHeight", this.custHeight)
                .append("sn", this.sn)
                .append("userId", this.userId)
                .append("createTime", this.createTime)
                .toString();
    }
}