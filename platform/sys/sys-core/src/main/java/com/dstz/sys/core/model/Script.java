package com.dstz.sys.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.core.model.BaseModel;

public class Script extends BaseModel implements Cloneable {

    private static final long serialVersionUID = 1L;
    protected String name;        /*名称*/
    protected String script;    /*脚本*/
    protected String category;    /*脚本分类*/
    protected String memo;        /*备注*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("script", this.script)
                .append("category", this.category)
                .append("memo", this.memo)
                .toString();
    }
}
