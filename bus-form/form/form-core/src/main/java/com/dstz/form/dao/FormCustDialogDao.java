package com.dstz.form.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.form.model.FormCustDialog;

/**
 * form_cust_dialog DAO接口
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-01-18 19:30:51
 */
@MapperScan
public interface FormCustDialogDao extends BaseDao<String, FormCustDialog> {

}
