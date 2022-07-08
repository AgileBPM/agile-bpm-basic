package com.dstz.form.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.form.model.FormCustDialog;
import org.mybatis.spring.annotation.MapperScan;

/**
 * form_cust_dialog DAO接口
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-01-18 19:30:51
 */
@MapperScan
public interface FormCustDialogDao extends BaseDao<String, FormCustDialog> {

    /**
     * 键是否存在
     * @param key 键
     * @return 是否存在
     */
    boolean existsByKey(String key);

}
