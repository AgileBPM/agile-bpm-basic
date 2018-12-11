package com.dstz.demo.core.manager;

import java.util.List;

import com.dstz.base.manager.Manager;
import com.dstz.bpm.api.engine.action.cmd.ActionCmd;
import com.dstz.demo.core.model.DemoSub;

/**
 * Demo子表 Manager处理接口
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:26:33
 */
public interface DemoSubManager extends Manager<String, DemoSub>{

	List<DemoSub> getByFk(String entityId);
}
