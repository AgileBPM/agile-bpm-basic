package com.dstz.sys.core.manager;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.dstz.base.manager.Manager;
import com.dstz.sys.core.model.DataDict;

/**
 * 数据字典 Manager处理接口
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-05-16 14:39:58
 */
public interface DataDictManager extends Manager<String, DataDict>{

	List<DataDict> getDictNodeList(String dictKey, Boolean hasRoot);

	JSONArray getDictTree();
	
}
