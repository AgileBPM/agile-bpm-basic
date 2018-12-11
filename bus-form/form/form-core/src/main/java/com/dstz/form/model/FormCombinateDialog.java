package com.dstz.form.model;

import com.dstz.base.api.model.IDModel;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.form.manager.FormCustDialogManager;

/**
 * <pre>
 * 描述：combinate_dialog 实体对象
 * </pre>
 */
public class FormCombinateDialog implements IDModel {
    protected String id; /* id_ */
    protected String name; /* name_ */
    protected String alias; /* alias_ */
    protected Integer width; /* width_ */
    protected Integer height; /* height_ */
    protected String treeDialogId; /* tree_dialog_id_ */
    protected String treeDialogName; /* tree_dialog_name_ */
    protected String listDialogId; /* list_dialog_id_ */
    protected String listDialogName; /* list_dialog_name_ */
    protected String field; /* 树数据返回数据对应列表数据的查询条件 */
    // 以下字段跟数据库无关
    private FormCustDialog treeDialog;
    private FormCustDialog listDialog;

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

    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * 返回 width_
     *
     * @return
     */
    public Integer getWidth() {
        return this.width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 返回 height_
     *
     * @return
     */
    public Integer getHeight() {
        return this.height;
    }

    public void setTreeDialogId(String treeDialogId) {
        this.treeDialogId = treeDialogId;
    }

    /**
     * 返回 tree_dialog_id_
     *
     * @return
     */
    public String getTreeDialogId() {
        return this.treeDialogId;
    }

    public void setTreeDialogName(String treeDialogName) {
        this.treeDialogName = treeDialogName;
    }

    /**
     * 返回 tree_dialog_name_
     *
     * @return
     */
    public String getTreeDialogName() {
        return this.treeDialogName;
    }

    public void setListDialogId(String listDialogId) {
        this.listDialogId = listDialogId;
    }

    /**
     * 返回 list_dialog_id_
     *
     * @return
     */
    public String getListDialogId() {
        return this.listDialogId;
    }

    public void setListDialogName(String listDialogName) {
        this.listDialogName = listDialogName;
    }

    /**
     * 返回 list_dialog_name_
     *
     * @return
     */
    public String getListDialogName() {
        return this.listDialogName;
    }

    public void setField(String field) {
        this.field = field;
    }

    /**
     * 返回 树数据返回数据对应列表数据的查询条件
     *
     * @return
     */
    public String getField() {
        return this.field;
    }

    public FormCustDialog getTreeDialog() {
        if (StringUtil.isEmpty(treeDialogId)) {
            return null;
        }
        treeDialog = AppUtil.getBean(FormCustDialogManager.class).get(treeDialogId);
        return treeDialog;
    }

    public FormCustDialog getListDialog() {
        if (StringUtil.isEmpty(listDialogId)) {
            return null;
        }
        listDialog = AppUtil.getBean(FormCustDialogManager.class).get(listDialogId);
        return listDialog;
    }

}