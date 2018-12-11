package com.dstz.form.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "bpmFormImport")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormImportXml {

    @XmlElement(name = "formXml", type = FormXml.class)
    List<FormXml> formXmlList = new ArrayList<FormXml>();

    public List<FormXml> getFormXmlList() {
        return formXmlList;
    }


    public void setFormXmlList(List<FormXml> formXmlList) {
        this.formXmlList = formXmlList;
    }


    public void addFormXml(FormXml formXml) {
        formXmlList.add(formXml);
    }


}
