package com.dstz.sys.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.ScriptDao;
import com.dstz.sys.core.manager.ScriptManager;
import com.dstz.sys.core.model.Script;


@Service("scriptManager")
public class ScriptManagerImpl extends BaseManager<String, Script> implements ScriptManager {
    @Resource
    private ScriptDao scriptDao;

    @Override
    public List<String> getDistinctCategory() {
        return scriptDao.getDistinctCategory();
    }

    @Override
    public Integer isNameExists(String name) {
        return scriptDao.isNameExists(name);
    }

}
