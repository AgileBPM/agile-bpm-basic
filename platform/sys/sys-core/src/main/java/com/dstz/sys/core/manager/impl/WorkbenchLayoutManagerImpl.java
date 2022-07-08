package com.dstz.sys.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.manager.impl.BaseManager;
import com.dstz.sys.core.dao.WorkbenchLayoutDao;
import com.dstz.sys.core.manager.WorkbenchLayoutManager;
import com.dstz.sys.core.model.WorkbenchLayout;

import cn.hutool.core.collection.CollectionUtil;


@Service("workbenchLayoutManager")
public class WorkbenchLayoutManagerImpl extends BaseManager<String, WorkbenchLayout> implements WorkbenchLayoutManager {
    @Resource
    WorkbenchLayoutDao workbenchLayoutDao;

    @Override
    public void savePanelLayout(List<WorkbenchLayout> layOutList, String userId) {
        workbenchLayoutDao.removeByUserId(userId);

        for (WorkbenchLayout layOut : layOutList) {
            layOut.setUserId(userId);
            workbenchLayoutDao.create(layOut);
        }
    }


    @Override
    public List<WorkbenchLayout> getByUserId(String userId) {
        List<WorkbenchLayout> list = workbenchLayoutDao.getByUserId(userId);

        if (CollectionUtil.isEmpty(list)) {
            list = workbenchLayoutDao.getByUserId(WorkbenchLayout.DEFAULT_LAYOUT);
        }
        return list;
    }


}
