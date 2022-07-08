package com.dstz.base.core.model;

import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.model.IBaseModel;
import com.dstz.base.core.util.ToStringUtil;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * <pre>
 * 描述：框架所有Model都需要继承的model
 * 里面有一些系统常用字段，当不需要持久化时map不要配置这些字段则可
 * version字段会在manager.update时自动+1 切记别重复加1了
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月2日 下午8:31:04
 * 版权:summer
 * </pre>
 */
public abstract class BaseModel extends ToStringUtil implements IBaseModel {
    // 主键
    protected String id;
    // 创建时间
    protected Date createTime;
    // 创建人ID
    protected String createBy;
    // 更新时间
    protected Date updateTime;
    // 更新人ID
    protected String updateBy;
    // 版本号
    protected int version = 0;
    // 逻辑删除标记
    protected boolean delete = false;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

  

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    
    @Override
    public String toString() {
    	return JSONObject.toJSONString(this);
    }

}
