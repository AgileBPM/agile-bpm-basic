package com.dstz.form.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.dstz.base.dao.BaseDao;
import com.dstz.form.model.FormCombinateDialog;

/**
 * <pre>
 * 描述：combinate_dialog DAO接口
 * </pre>
 */
@MapperScan
public interface FormCombinateDialogDao extends BaseDao<String, FormCombinateDialog> {
}
