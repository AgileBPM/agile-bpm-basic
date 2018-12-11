package com.dstz.sys.service.impl;

import com.dstz.sys.api.model.ISysTreeNode;
import com.dstz.sys.api.service.ISysTreeNodeService;
import com.dstz.sys.core.manager.SysTreeNodeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 描述：SysTreeNodeService接口
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年3月28日 下午3:31:25
 * 版权:summer
 * </pre>
 */
@Service
public class SysTreeNodeService implements ISysTreeNodeService {
    @Autowired
    SysTreeNodeManager sysTreeNodeManager;

    @Override
    public ISysTreeNode getById(String id) {
        return sysTreeNodeManager.get(id);
    }
}
