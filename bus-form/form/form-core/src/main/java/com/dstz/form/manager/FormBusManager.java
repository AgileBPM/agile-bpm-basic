package com.dstz.form.manager;

import com.alibaba.fastjson.JSONArray;
import com.dstz.bus.api.model.IBusinessData;

import java.util.Map;


/**
 *  
 */
public interface FormBusManager {
    /**
     * 通过 boKey，id获取bo数据
     *
     * @param boKey
     * @param id
     * @return
     */
    IBusinessData getBoData(String boKey, Map param);

    /**
     * 通过formKey json 保存bo数据
     *
     * @param formKey
     * @param json
     */
    void saveData(String formKey, String json);

    /**
     * 通过formKey 删除业务数据
     *
     * @param aryIds
     * @param formKey
     */
    void removeByIds(String[] aryIds, String formKey);

    /**
     * 通过 formKey 获取业务数据
     *
     * @param formKey
     * @param param
     * @return
     */
    JSONArray getList(String formKey, Map<String, Object> param);
}
