package com.dstz.sys.core.manager.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.SysTreeDao;
import com.dstz.sys.core.manager.SysTreeManager;
import com.dstz.sys.core.manager.SysTreeNodeManager;
import com.dstz.sys.core.model.SysTree;

/**
 * 系统树 Manager处理实现类
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-03-13 19:58:28
 */
@Service("sysTreeManager")
public class SysTreeManagerImpl extends BaseManager<String, SysTree> implements SysTreeManager {
    @Resource
    SysTreeDao sysTreeDao;
    @Autowired
    SysTreeNodeManager sysTreeNodeManager;

    @Override
    public SysTree getByKey(String key) {
        QueryFilter filter = new DefaultQueryFilter();
        filter.addFilter("key_", key, QueryOP.EQUAL);
        return this.queryOne(filter);
    }

    @Override
    public void removeContainNode(String id) {
        this.remove(id);
        sysTreeNodeManager.removeByTreeId(id);
    }
}
