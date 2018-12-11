package com.dstz.demo.core.manager;

import com.dstz.base.manager.Manager;
import com.dstz.bpm.api.engine.action.cmd.ActionCmd;
import com.dstz.demo.core.model.Demo;

/**
 * 案例 Manager处理接口
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:06:04
 */
public interface DemoManager extends Manager<String, Demo>{
	
	public void saveDemoJson(ActionCmd  actionCmd);
	
}
