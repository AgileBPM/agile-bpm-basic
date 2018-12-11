package com.dstz.sys.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.dstz.base.api.model.IDModel;

public class SerialNo implements IDModel{
    protected String id; /*主键*/
    protected String name; /*名称*/
    protected String alias; /*别名*/
    protected String regulation; /*规则*/
    protected Short genType; /*生成类型*/
    protected Integer noLength; /*流水号长度*/
    protected String curDate; /*当前日期*/
    protected Integer initValue; /*初始值*/
    protected Integer curValue = 0; /*当前值*/
    protected Short step; /*步长*/

    /**
     * 新的流水号。
     */
    protected Integer newCurValue = 0;

    /**
     * 预览流水号。
     */
    protected String curIdenValue = "";


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

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 返回 name_
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 返回 alias_
     *
     * @return
     */
    public String getAlias() {
        return this.alias;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }

    /**
     * 返回 regulation_
     *
     * @return
     */
    public String getRegulation() {
        return this.regulation;
    }

    public void setGenType(Short genType) {
        this.genType = genType;
    }

    /**
     * 返回 gen_type_
     *
     * @return
     */
    public Short getGenType() {
        return this.genType;
    }

    public void setNoLength(Integer noLength) {
        this.noLength = noLength;
    }

    /**
     * 返回 no_length_
     *
     * @return
     */
    public Integer getNoLength() {
        return this.noLength;
    }

    public void setCurDate(String curDate) {
        this.curDate = curDate;
    }

    /**
     * 返回 cur_date_
     *
     * @return
     */
    public String getCurDate() {
        return this.curDate;
    }

    public void setInitValue(Integer initValue) {
        this.initValue = initValue;
    }

    /**
     * 返回 init_value_
     *
     * @return
     */
    public Integer getInitValue() {
        return this.initValue;
    }

    public void setCurValue(Integer curValue) {
        this.curValue = curValue;
    }

    /**
     * 返回 cur_value_
     *
     * @return
     */
    public Integer getCurValue() {
        if (curValue == null) return 0;
        return this.curValue;
    }

    public void setStep(Short step) {
        this.step = step;
    }

    /**
     * 返回 step_
     *
     * @return
     */
    public Short getStep() {
        return this.step;
    }


    public Integer getNewCurValue() {
        return newCurValue;
    }

    public void setNewCurValue(Integer newCurValue) {
        this.newCurValue = newCurValue;
    }

    public String getCurIdenValue() {
        return curIdenValue;
    }

    public void setCurIdenValue(String curIdenValue) {
        this.curIdenValue = curIdenValue;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.id)
                .append("name", this.name)
                .append("alias", this.alias)
                .append("regulation", this.regulation)
                .append("genType", this.genType)
                .append("noLength", this.noLength)
                .append("curDate", this.curDate)
                .append("initValue", this.initValue)
                .append("curValue", this.curValue)
                .append("step", this.step)
                .toString();
    }
}