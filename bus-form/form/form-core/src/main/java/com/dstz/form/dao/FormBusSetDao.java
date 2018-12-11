package com.dstz.form.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.form.model.FormBusSet;

/**
 * <pre>
 * 描述：表单业务数据保存设置 DAO
 * 作者:jeff
 * 邮箱:jeff@jee-soft.cn
 * </pre>
 */
@MapperScan
public interface FormBusSetDao extends BaseDao<String, FormBusSet> {

    FormBusSet getByFormKey(String formKey);

    /**
     * 判断业务数据保存设置是否存在。
     *
     * @param formSet
     * @return
     */
    Integer isExist(FormBusSet formSet);

    /**
     * 根据表单键删除业务数据设置。
     *
     * @param formKey
     */
    void removeByFormKey(String formKey);
}
