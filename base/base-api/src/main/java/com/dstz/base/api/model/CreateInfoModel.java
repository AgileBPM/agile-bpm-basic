package com.dstz.base.api.model;

import java.util.Date;

/**
 * 创建更新信息接口<br>
 * 若实现了该接口、则保存更新时会自动赋值
 *
 */
public interface CreateInfoModel {

    /**
     * <pre>
     * 创建时间
     * </pre>
     *
     * @return
     */
    public Date getCreateTime();

    /**
     * <pre>
     * 设置创建时间
     * </pre>
     *
     * @param createTime
     */
    public void setCreateTime(Date createTime);

    /**
     * <pre>
     * 创建人ID
     * </pre>
     *
     * @return
     */
    public String getCreateBy();

    /**
     * <pre>
     * 设置创建人ID
     * </pre>
     *
     * @param createBy
     */
    public void setCreateBy(String createBy);
    /**
     * <pre>
     * 更新时间
     * </pre>
     *
     * @return
     */
    public Date getUpdateTime();

    /**
     * <pre>
     * 设置 更新时间
     * </pre>
     *
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime);

    /**
     * <pre>
     * 更新人ID
     * </pre>
     *
     * @return
     */
    public String getUpdateBy();

    /**
     * <pre>
     * 设置更新人ID
     * </pre>
     *
     * @param updateBy
     */
    public void setUpdateBy(String updateBy);
}
