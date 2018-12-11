package com.dstz.form.manager;

import com.dstz.base.manager.Manager;
import com.dstz.form.model.FormCombinateDialog;

/**
 * <pre>
 * 描述：combinate_dialog 处理接口
 * </pre>
 */
public interface FormCombinateDialogManager extends Manager<String, FormCombinateDialog> {
    FormCombinateDialog getByAlias(String alias);
}
