package com.dstz.form.api.service;

import java.util.Set;

import com.dstz.form.api.model.IFormDef;

public interface FormService {

    /**
     * 根据formkey获取表单。
     *
     * @param formKey
     * @return IFormDef
     */
    IFormDef getByFormKey(String formKey);

    /**
     * 根据表单ID取得表单对象。
     *
     * @param formId
     * @return IFormDef
     */
    IFormDef getByFormId(String formId);

    /**
     * 根据formKey 导出表单
     *
     * @param formKeys
     * @return
     */
    String getFormExportXml(Set<String> formKeys);

    void importForm(String formXmlStr);
}
