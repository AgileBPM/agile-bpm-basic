package com.dstz.sys.core.model;

import com.dstz.base.core.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author jeff
 */
public class WorkbenchPanel extends BaseModel {
    public static final String DATA_TYPE_INTERFACE = "interface";
    public static final String DATA_TYPE_CUST_QUERY = "custQuery";


    /**
     * 标识
     */
    protected String alias;

    /**
     * 名字
     */
    protected String name;

    /**
     * 类型，不同类型的panel有不同的指令来实现
     */
    protected String type;

    /**
     * 描述
     */
    protected String desc;

    /**
     * 数据类型
     */
    protected String dataType;

    /**
     * 数据来源
     */
    protected String dataSource;

    /**
     * 自动刷新
     */
    protected Integer autoRefresh;

    /**
     * 宽
     */
    protected Integer width;

    /**
     * 高
     */
    protected Integer height;


    /**
     * 展示内容
     */
    protected String displayContent;

    /**
     * 更多链接
     */
    protected String moreUrl;

    /**
     * 所属系统
     */
    protected String system;

    /**
     * create_time_
     */
    protected java.util.Date createTime;

    /**
     * create_by_
     */
    protected String createBy;

    /**
     * update_time_
     */
    protected java.util.Date updateTime;

    /**
     * update_by_
     */
    protected String updateBy;

    /**
     * delete_flag_
     */
    protected String deleteFlag;
    /**
     * 自定义宽
     */
    protected Integer custWidth;
    /**
     * 自定义高
     */
    protected Integer custHeight;

    /**
     * 自定义布局ID
     */
    protected String custLayOutId;


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

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 返回 标识
     *
     * @return
     */
    public String getAlias() {
        return this.alias;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回 名字
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 返回 类型
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 返回 描述
     *
     * @return
     */
    public String getDesc() {
        return this.desc;
    }

    public Integer getCustWidth() {
        return custWidth;
    }

    public void setCustWidth(Integer custWidth) {
        this.custWidth = custWidth;
    }

    public Integer getCustHeight() {
        return custHeight;
    }

    public void setCustHeight(Integer custHeight) {
        this.custHeight = custHeight;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * 返回 数据类型
     *
     * @return
     */
    public String getDataType() {
        return this.dataType;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 返回 数据来源
     *
     * @return
     */
    public String getDataSource() {
        return this.dataSource;
    }

    public void setAutoRefresh(Integer autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    /**
     * 返回 自动刷新
     *
     * @return
     */
    public Integer getAutoRefresh() {
        return this.autoRefresh;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 返回 宽
     *
     * @return
     */
    public Integer getWidth() {
        return this.width;
    }

    public String getCustLayOutId() {
        return custLayOutId;
    }

    public void setCustLayOutId(String custLayOutId) {
        this.custLayOutId = custLayOutId;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 返回 高
     *
     * @return
     */
    public Integer getHeight() {
        return this.height;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

    /**
     * 返回 展示内容
     *
     * @return
     */
    public String getDisplayContent() {
        return this.displayContent;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }

    /**
     * 返回 更多链接
     *
     * @return
     */
    public String getMoreUrl() {
        return this.moreUrl;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回 create_time_
     *
     * @return
     */
    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 返回 create_by_
     *
     * @return
     */
    public String getCreateBy() {
        return this.createBy;
    }

    public void setUpdateTime(java.util.Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 返回 update_time_
     *
     * @return
     */
    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 返回 update_by_
     *
     * @return
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 返回 delete_flag_
     *
     * @return
     */
    public String getDeleteFlag() {
        return this.deleteFlag;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("alias", this.alias)
                .append("name", this.name)
                .append("type", this.type)
                .append("desc", this.desc)
                .append("dataType", this.dataType)
                .append("dataSource", this.dataSource)
                .append("autoRefresh", this.autoRefresh)
                .append("width", this.width)
                .append("height", this.height)
                .append("displayContent", this.displayContent)
                .append("moreUrl", this.moreUrl)
                .append("createTime", this.createTime)
                .append("createBy", this.createBy)
                .append("updateTime", this.updateTime)
                .append("updateBy", this.updateBy)
                .append("deleteFlag", this.deleteFlag)
                .toString();
    }
}