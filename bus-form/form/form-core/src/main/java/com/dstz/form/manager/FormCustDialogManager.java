package com.dstz.form.manager;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;
import com.dstz.base.manager.Manager;
import com.dstz.form.model.FormCustDialog;

import java.util.List;
import java.util.Map;

/**
 * form_cust_dialog Manager处理接口
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-01-18 19:30:51
 */
public interface FormCustDialogManager extends Manager<String, FormCustDialog> {

    /**
     * <pre>
     * 根据key获取FormCustDialog
     * </pre>
     *
     * @param key
     * @return
     */
    FormCustDialog getByKey(String key);

    /**
     * <pre>
     * 查询ObjName
     * </pre>
     *
     * @param formCustDialog
     * @return
     */
    Map<String, String> searchObjName(FormCustDialog formCustDialog);

    /**
     * <pre>
     * 获取objName对象的表 / 视图信息
     * </pre>
     *
     * @param formCustDialog
     * @return
     */
    Table<Column> getTable(FormCustDialog formCustDialog);

    /**
     * <pre>
     * 根据formCustDialog获取数据
     * 包含树形和列表
     * </pre>
     *
     * @param formCustDialog
     * @param queryFilter    页面传参
     * @return
     */
    List<?> data(FormCustDialog formCustDialog, QueryFilter queryFilter);


    /**
     * 键是否存在
     * @param key 键
     * @return 是否存在
     */
    boolean existsByKey(String key);
}
