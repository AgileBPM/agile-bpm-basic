package com.dstz.form.manager;

import com.dstz.base.manager.Manager;
import com.dstz.form.model.FormBusSet;

/**
 * <pre>
 * 描述：form_bus_set 处理接口
 * </pre>
 */
public interface FormBusSetManager extends Manager<String, FormBusSet> {

    FormBusSet getByFormKey(String formKey);

}
