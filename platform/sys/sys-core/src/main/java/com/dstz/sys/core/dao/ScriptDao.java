package com.dstz.sys.core.dao;

import com.dstz.base.dao.BaseDao;
import com.dstz.sys.core.model.Script;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface ScriptDao extends BaseDao<String, Script> {

    /**
     * 返回所有脚本的分类
     *
     * @return
     */
    public List<String> getDistinctCategory();

    /**
     * 判断脚本名称是否存在
     *
     * @param name
     * @return
     */
    public Integer isNameExists(String name);
}
