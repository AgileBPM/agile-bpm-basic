package com.dstz.sys.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.api.constant.Direction;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.SysTreeNodeDao;
import com.dstz.sys.core.manager.SysTreeNodeManager;
import com.dstz.sys.core.model.SysTreeNode;

/**
 * 系统树节点 Manager处理实现类
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-03-13 20:02:33
 */
@Service("sysTreeNodeManager")
public class SysTreeNodeManagerImpl extends BaseManager<String, SysTreeNode> implements SysTreeNodeManager {
    @Resource
    SysTreeNodeDao sysTreeNodeDao;

    @Override
    public List<SysTreeNode> getByTreeId(String treeId) {
        QueryFilter filter = new DefaultQueryFilter();
        filter.addFilter("tree_id_", treeId, QueryOP.EQUAL);
        filter.addFieldSort("sn_", Direction.ASC.getKey());
        return this.query(filter);
    }

    @Override
    public SysTreeNode getByTreeIdAndKey(String treeId, String key) {
        QueryFilter filter = new DefaultQueryFilter();
        filter.addFilter("tree_id_", treeId, QueryOP.EQUAL);
        filter.addFilter("key_", key, QueryOP.EQUAL);
        return this.queryOne(filter);
    }

    @Override
    public List<SysTreeNode> getByParentId(String parentId) {
        QueryFilter filter = new DefaultQueryFilter();
        filter.addFilter("parent_id_", parentId, QueryOP.EQUAL);
        return this.query(filter);
    }

    @Override
    public List<SysTreeNode> getStartWithPath(String path) {
        QueryFilter filter = new DefaultQueryFilter();
        filter.addFilter("path_", path , QueryOP.RIGHT_LIKE);
        return this.query(filter);
    }

    @Override
    public void removeByTreeId(String treeId) {
        sysTreeNodeDao.removeByTreeId(treeId);
    }
    
    @Override
    public void removeByPath(String path) {
    	sysTreeNodeDao.removeByPath(path);
    }
}
