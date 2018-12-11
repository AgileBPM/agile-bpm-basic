package com.dstz.form.manager;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.dstz.base.manager.Manager;
import com.dstz.form.model.FormTemplate;

/**
 * <pre>
 * 描述：FormTemplate的Manager
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月8日 下午2:32:26
 * 版权:summer
 * </pre>
 */
public interface FormTemplateManager extends Manager<String, FormTemplate> {

    /**
     * 根据模版别名取得模版。
     *
     * @param key
     * @return
     */
    public FormTemplate getByKey(String key);

    /**
     * 获取所有的系统原始模板
     *
     * @return
     * @throws Exception
     */
    public void initAllTemplate();

    /**
     * 当模版数据为空时，将form目录下的模版添加到数据库中。
     */
    public void init();

    /**
     * 检查模板别名是否唯一
     *
     * @param key
     * @return
     */
    public boolean isExist(String key);

    /**
     * 将用户自定义模板备份
     *
     * @param id
     */
    public void backUpTemplate(String id);

    
    /**
     * <pre>
     * 根据boKey获取模板数据
     * </pre>
     * @param boKey
     * @param type 
     * @return
     */
	JSONArray templateData(String boKey, String type);


}
