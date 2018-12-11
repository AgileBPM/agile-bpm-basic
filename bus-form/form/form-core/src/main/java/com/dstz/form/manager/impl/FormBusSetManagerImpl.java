package com.dstz.form.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.form.dao.FormBusSetDao;
import com.dstz.form.manager.FormBusSetManager;
import com.dstz.form.model.FormBusSet;

/**
 * <pre>
 * 描述：form_bus_set 处理实现类
 * </pre>
 */
@Service("formBusSetManager")
public class FormBusSetManagerImpl extends BaseManager<String, FormBusSet> implements FormBusSetManager {
    @Resource
    FormBusSetDao formBusSetDao;

    @Override
    public FormBusSet getByFormKey(String formKey) {
        return formBusSetDao.getByFormKey(formKey);
    }
}
