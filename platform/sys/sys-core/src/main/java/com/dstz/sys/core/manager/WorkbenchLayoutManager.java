package com.dstz.sys.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.WorkbenchLayout;

import java.util.List;

public interface WorkbenchLayoutManager extends Manager<String, WorkbenchLayout> {

    List<WorkbenchLayout> getByUserId(String currentUserId);

    void savePanelLayout(List<WorkbenchLayout> layOutList, String layoutKey);

}
