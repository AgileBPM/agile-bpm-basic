package com.dstz.form.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.form.model.FormTemplate;
@MapperScan
public interface FormTemplateDao extends BaseDao<String, FormTemplate> {
}
