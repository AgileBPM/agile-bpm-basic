package com.dstz.form.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.form.dao.FormCombinateDialogDao;
import com.dstz.form.manager.FormCombinateDialogManager;
import com.dstz.form.model.FormCombinateDialog;

/**
 * <pre>
 * 描述：combinate_dialog 处理实现类
 * </pre>
 */
@Service("combinateDialogManager")
public class FormCombinateDialogManagerImpl extends BaseManager<String, FormCombinateDialog> implements FormCombinateDialogManager {
    @Resource
    FormCombinateDialogDao combinateDialogDao;

    @Override
    public FormCombinateDialog getByAlias(String alias) {
        QueryFilter queryFilter = new DefaultQueryFilter();
        queryFilter.addFilter("alias_", alias, QueryOP.EQUAL);
        List<FormCombinateDialog> combinateDialogs = query(queryFilter);
        if (combinateDialogs == null || combinateDialogs.isEmpty())
            return null;
        return combinateDialogs.get(0);
    }
}
