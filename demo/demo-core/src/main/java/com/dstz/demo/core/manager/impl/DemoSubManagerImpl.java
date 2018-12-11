package com.dstz.demo.core.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.bpm.api.engine.action.cmd.ActionCmd;
import com.dstz.demo.core.dao.DemoSubDao;
import com.dstz.demo.core.model.DemoSub;
import com.dstz.demo.core.manager.DemoSubManager;
/**
 * Demo子表 Manager处理实现类
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-08-24 18:26:33
 */
@Service("demoSubManager")
public class DemoSubManagerImpl extends BaseManager<String, DemoSub> implements DemoSubManager{
	@Resource
	DemoSubDao demoSubDao;

	public List<DemoSub> getByFk(String fk) {
		return demoSubDao.getByFk(fk);
	}

}
