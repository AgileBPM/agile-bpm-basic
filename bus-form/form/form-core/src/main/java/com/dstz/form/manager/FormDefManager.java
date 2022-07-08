package com.dstz.form.manager;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.manager.Manager;
import com.dstz.form.model.FormDef;

/**
 * 表单 Manager处理接口
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-03-19 20:30:46
 */
public interface FormDefManager extends Manager<String, FormDef> {

    /**
     * <pre>
     * 根据别名获取表单
     * </pre>
     *
     * @param key
     * @return
     */
    FormDef getByKey(String key);

    /**
     * <pre>
     * 处理保存表单的html到文件的逻辑
     * 备份用
     * </pre>
     *
     * @param formDef
     */
    void saveBackupHtml(FormDef formDef);

    /**
     * <pre>
     * 读取备份文件中的表单html
     * </pre>
     *
     * @param formDef
     * @return
     */
    String getBackupHtml(FormDef formDef);

	String generateFormHtml(String boKey, JSONArray templateConfig, String formType);
	
	/**
	 * <pre>
	 * 获取表单列表包含bo的内容
	 * </pre>	
	 * @param queryFilter
	 * @return
	 */
	List<Map> queryWithBo(QueryFilter queryFilter);

}
