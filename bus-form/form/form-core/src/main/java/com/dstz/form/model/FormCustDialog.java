package com.dstz.form.model;

import com.dstz.base.core.model.BaseModel;
import com.dstz.base.core.util.JsonUtil;
import com.dstz.base.core.util.ToStringUtil;
import com.dstz.form.model.custdialog.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.List;

/**
 * <pre>
 * 描述：自定义对话框
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年1月17日 下午5:05:55
 * 版权:summer
 * </pre>
 */
public class FormCustDialog extends BaseModel {
	public static final String DATA_SOURCE_INTERFACE = "interface";

    /**
     * 别名
     */
    @NotEmpty
    private String key;
    /**
     * 名字
     */
    @NotEmpty
    private String name;
    /**
     * 描述
     */
    private String desc;
    /**
     * 显示类型 枚举 FormCustDialogStyle
     */
    @NotEmpty
    private String style;
    /**
     * 数据源别名
     */
    @NotEmpty
    private String dsKey;
    /**
     * 数据源名字
     */
    @NotEmpty
    private String dsName;
    /**
     * 对象类型 枚举 FormCustDialogObjType
     */
    @NotEmpty
    private String objType;
    /**
     * 对象名称
     */
    @NotEmpty
    private String objName;
    /**
     * 是否分页
     */
    private boolean page;
    /**
     * 分页大小
     */
    private int pageSize;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 是否系统内置
     */
    private boolean system;
    /**
     * 是否多选
     */
    private boolean multiple;
    /**
     * 树形的配置信息，json字段
     */
    private String treeConfigJson;
    /**
     * 列表数据时的展示字段，json字段
     */
    private String displayFieldsJson;
    /**
     * 条件字段，json字段
     */
    private String conditionFieldsJson;
    /**
     * 返回字段，json字段
     */
    private String returnFieldsJson;
    /**
     * 排序字段，json字段
     */
    private String sortFieldsJson;

    private String dataSource;// 数据来源 interface、database 
    
    //以下字段不入库
    /**
     * 树形的配置信息
     */
    @Valid
    private FormCustDialogTreeConfig treeConfig;
    /**
     * 列表数据时的展示字段
     */
    @Valid
    private List<FormCustDialogDisplayField> displayFields;
    /**
     * 条件字段
     */
    @Valid
    private List<FormCustDialogConditionField> conditionFields;
    /**
     * 返回字段
     */
    @Valid
    private List<FormCustDialogReturnField> returnFields;
    /**
     * 排序字段
     */
    @Valid
    private List<FormCustDialogSortField> sortFields;
    

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDsKey() {
        return dsKey;
    }

    public void setDsKey(String dsKey) {
        this.dsKey = dsKey;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getWidth() {
        return width;
    }

    public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getTreeConfigJson() {
        return treeConfigJson;
    }

    public void setTreeConfigJson(String treeConfigJson) {
        this.treeConfig = JsonUtil.parseObject(treeConfigJson, FormCustDialogTreeConfig.class);
        this.treeConfigJson = treeConfigJson;
    }

    public String getDisplayFieldsJson() {
        return displayFieldsJson;
    }

    public void setDisplayFieldsJson(String displayFieldsJson) {
        this.displayFields = JsonUtil.parseArray(displayFieldsJson, FormCustDialogDisplayField.class);
        this.displayFieldsJson = displayFieldsJson;
    }

    public String getConditionFieldsJson() {
        return conditionFieldsJson;
    }

    public void setConditionFieldsJson(String conditionFieldsJson) {
        this.conditionFields = JsonUtil.parseArray(conditionFieldsJson, FormCustDialogConditionField.class);
        this.conditionFieldsJson = conditionFieldsJson;
    }

    public String getReturnFieldsJson() {
        return returnFieldsJson;
    }

    public void setReturnFieldsJson(String returnFieldsJson) {
        this.returnFields = JsonUtil.parseArray(returnFieldsJson, FormCustDialogReturnField.class);
        this.returnFieldsJson = returnFieldsJson;
    }

    public String getSortFieldsJson() {
        return sortFieldsJson;
    }

    public void setSortFieldsJson(String sortFieldsJson) {
        this.sortFields = JsonUtil.parseArray(sortFieldsJson, FormCustDialogSortField.class);
        this.sortFieldsJson = sortFieldsJson;
    }

    public FormCustDialogTreeConfig getTreeConfig() {
        return treeConfig;
    }

    public void setTreeConfig(FormCustDialogTreeConfig treeConfig) {
        this.treeConfigJson = JsonUtil.toJSONString(treeConfig);
        this.treeConfig = treeConfig;
    }

    public List<FormCustDialogDisplayField> getDisplayFields() {
        return displayFields;
    }

    public void setDisplayFields(List<FormCustDialogDisplayField> displayFields) {
        this.displayFieldsJson = JsonUtil.toJSONString(displayFields);
        this.displayFields = displayFields;
    }

    public List<FormCustDialogConditionField> getConditionFields() {
        return conditionFields;
    }

    public void setConditionFields(List<FormCustDialogConditionField> conditionFields) {
        this.conditionFieldsJson = JsonUtil.toJSONString(conditionFields);
        this.conditionFields = conditionFields;
    }

    public List<FormCustDialogReturnField> getReturnFields() {
        return returnFields;
    }

    public void setReturnFields(List<FormCustDialogReturnField> returnFields) {
        this.returnFieldsJson = JsonUtil.toJSONString(returnFields);
        this.returnFields = returnFields;
    }

    public List<FormCustDialogSortField> getSortFields() {
        return sortFields;
    }

    public void setSortFields(List<FormCustDialogSortField> sortFields) {
        this.sortFieldsJson = JsonUtil.toJSONString(sortFields);
        this.sortFields = sortFields;
    }

    public static void main(String[] args) {
        FormCustDialog dialog = new FormCustDialog();
        System.out.println(ToStringUtil.toString(dialog));
    }
}