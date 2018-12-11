package com.dstz.bus.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.api.model.permission.IBusObjPermission;

public interface IBusinessData extends Serializable{

	Object getPk();

	Map<String, Object> getData();

	void put(String key, Object value);

	Object get(String key);

	IBusinessData getParent();

	IBusTableRel getBusTableRel();

	String getString(String key);
	
	/**
	 * <pre>
	 * 返回子列表
	 * ps：我故意拼错的childs的，因为里面有一个getChildren方法了。
	 * 因为转类型问题而无法提供接口出去，神烦！！！
	 * </pre>	
	 * @return
	 */
	Map<String, List<IBusinessData>> getChilds();
	
	JSONObject fullBusDataInitData(JSONObject initData);
}