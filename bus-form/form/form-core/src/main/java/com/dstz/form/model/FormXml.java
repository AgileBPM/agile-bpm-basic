package com.dstz.form.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.dstz.form.api.model.IFormDef;

import java.util.ArrayList;
import java.util.List;

/**
 * 此类用于表单的导入导出。
 */
@XmlRootElement(name = "bpmForms")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormXml {


    @XmlElement(name = "bpmForm", type = FormDef.class)
    private IFormDef bpmForm;

    @XmlElement(name = "formBusSet", type = FormBusSet.class)
    private FormBusSet formBusSet;

    @XmlElement(name = "boCodes")
    private List<String> boCodes;


    public List<String> getBoCodes() {
        return boCodes;
    }


    public void setBoCodes(List<String> boCodes) {
        this.boCodes = boCodes;
    }


    public IFormDef getBpmForm() {
        return bpmForm;
    }
 
    public FormBusSet getFormBusSet() {
        return formBusSet;
    }


    public void setFormBusSet(FormBusSet formBusSet) {
        this.formBusSet = formBusSet;
    }


    public void setBpmForm(IFormDef bpmForm) {
        this.bpmForm = bpmForm;
    }


}
