package com.dstz.bus.api.service;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.api.model.IBusinessData;
import com.dstz.bus.api.model.IBusinessObject;
import com.dstz.bus.api.model.IBusinessPermission;

/**
 * 业务数据服务接口
 */
public interface IBusinessDataService {
	/**
	 * <pre>
	 * 保存 FormDefData 中的data数据
	 * </pre>	
	 * @param data
	 * @param businessPermission
	 */
	void saveFormDefData(JSONObject data,IBusinessPermission businessPermission);

	/**
	 * <pre>
	 * 获取formdefData中的data相关信息
	 * </pre>
	 * 
	 * @param businessObject
	 * @param id
	 * @return
	 */
	JSONObject getFormDefData(IBusinessObject businessObject, Object id);
	
	/**
	 * <pre>
	 * 删除数据
	 * </pre>
	 * 
	 * @param businessObject
	 * @param id
	 */
	void removeData(IBusinessObject businessObject, Object id);
	
	void saveData(IBusinessData businessData);
	
	/**
	 * <pre>
	 * 加载数据
	 * </pre>	
	 * @param businessObject
	 * @param id
	 * @return
	 */
	IBusinessData loadData(IBusinessObject businessObject, Object id);
	
	/**
	 * 将前端的json 转成 IbusinessData结构
	 * @param jsonObject
	 * @param modelCode
	 * @return
	 */
	IBusinessData parseBusinessData(JSONObject jsonObject, String modelCode);
	
	/**
	 * 把businessData转成json格式<br>
	 * 用在BD执行过展示前置脚本后转成json提供给前端
	 * @param data
	 * @param businessData
	 */
	JSONObject assemblyFormDefData(IBusinessData businessData);
	
	/**
	 * <pre>
	 * 加载数据
	 * </pre>	
	 * @param businessObject
	 * @param id
	 * @param init	当id为空时是否需要初始化数据
	 * @return
	 */
	IBusinessData loadData(IBusinessObject businessObject, Object id, boolean init);
}
