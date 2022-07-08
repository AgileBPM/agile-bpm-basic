package com.dstz.bus.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.dstz.bus.api.constant.BusTableRelType;
import com.dstz.bus.api.model.IBusTableRel;
import com.dstz.bus.api.model.IBusinessData;

/**
 * <pre>
 * 业务数据
 * 页面的数据会解析成这个对象，然后这个对象再进行持久化
 * </pre>
 *
 * @author aschs
 *
 */
public class BusinessData implements IBusinessData {
	/**
	 * 说明当前数据在bo的地位，所有层级相关信息都在里面取
	 */
	private BusTableRel busTableRel;
	/**
	 * <pre>
	 * 数据本身
	 * Map<columnKey,值>
	 * </pre>
	 */
	private Map<String, Object> data = new HashMap<>();
	/**
	 * 子数据Map<表名字(tableName),子表数据>
	 */
	private Map<String, List<BusinessData>> children = new HashMap<>();
	/**
	 * 父数据
	 */
	private BusinessData parent;

	@Override
	public BusTableRel getBusTableRel() {
		return busTableRel;
	}

	public void setBusTableRel(BusTableRel busTableRel) {
		this.busTableRel = busTableRel;
	}

	@Override
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, List<BusinessData>> getChildren() {
		return children;
	}

	public void setChildren(Map<String, List<BusinessData>> children) {
		this.children = children;
	}

	@Override
	public BusinessData getParent() {
		return parent;
	}

	public void setParent(BusinessData parent) {
		this.parent = parent;
	}

	public void setPk(Object id) {
		this.data.put(busTableRel.getTable().getPkKey(), id);
	}

	@Override
	public Object getPk() {
		return this.data.get(busTableRel.getTable().getPkKey());
	}

	/**
	 * <pre>
	 * 设置数据
	 * this.data.put(key, value);
	 * </pre>
	 * 
	 * @param key
	 * @param value
	 */
	@Override
	public void put(String key, Object value) {
		data.put(key, value);
	}

	/**
	 * <pre>
	 * 拿数据
	 * return this.data.get(key);
	 * </pre>
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Object get(String key) {
		return this.data.get(key);
	}

	@Override
	public String getString(String key) {
		Object obj = this.data.get(key);
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * <pre>
	 * 转化成数据库的data
	 * ps:
	 * data数据对应的Map<columnKey,值>入库是不行的
	 * 所以要映射成Map<columnName,值>
	 * 在过程中只会获取有编辑权限的字段
	 * </pre>
	 * 
	 * @return
	 */
	public Map<String, Object> getDbData() {
		Map<String, Object> dbData = new HashMap<>();
		for (BusinessColumn column : this.busTableRel.getTable().getColumns()) {
			if (column.isPrimary() || this.busTableRel.getBusObj().haveColumnDbEditRights(this.busTableRel.getTableKey(), column.getKey())) {
				Object val = this.data.get(column.getKey());
				dbData.put(column.getName(), val);
			}
		}
		return dbData;
	}

	/**
	 * <pre>
	 * 设置数据库的数据
	 * 会在里面映射出data
	 * 在过程中只会获取有只读权限的字段
	 * </pre>
	 * 
	 * @param dbData
	 */
	public void setDbData(Map<String, Object> dbData) {
		for (BusinessColumn column : busTableRel.getTable().getColumns()) {
			if (this.busTableRel.getBusObj().haveColumnDbReadRights(this.busTableRel.getTableKey(), column.getKey())) {
				data.put(column.getKey(), dbData.get(column.getName()));
			}
		}
	}

	/**
	 * <pre>
	 * 增加一个子数据
	 * </pre>
	 * 
	 * @param businessData
	 */
	public void addChildren(BusinessData businessData) {
		String tableKey = businessData.getBusTableRel().getTable().getKey();
		List<BusinessData> list = this.children.computeIfAbsent(tableKey, k -> new ArrayList<>());
		businessData.setParent(this);// 设置父节点
		list.add(businessData);
	}

	@Override
	public Map<String, List<IBusinessData>> getChilds() {
		Map<String, List<IBusinessData>> map = new HashMap<>();
		for (Entry<String, List<BusinessData>> entry : children.entrySet()) {
			List<IBusinessData> list = new ArrayList<>();
			list.addAll(entry.getValue());
			map.put(entry.getKey(), list);
		}
		return map;
	}

	@Override
	public List<IBusinessData> getChild(String subKey) {
		Map<String, List<IBusinessData>> subDatas = this.getChilds();
		if(subDatas.containsKey(subKey)){
			return subDatas.get(subKey);
		}
		return Collections.emptyList();
	}

	public List<IBusinessData> getChildren(String subKey){
		return this.getChild(subKey);
	}


	/**
	 * 将当前bd 的initdata 设置进去
	 */
	@Override
	public JSONObject fullBusDataInitData(JSONObject initData) {
		if (initData == null)
			initData = new JSONObject();

		JSONObject initTables = new JSONObject();
		for (IBusTableRel rel : this.getBusTableRel().list()) {
			initTables.put(rel.getTableKey(), getInitData(rel));
		}
		initData.put(this.getBusTableRel().getBusObj().getKey(), initTables);

		return initData;
	}

	/**
	 * <pre>
	 * 获取初始化数据
	 * </pre>
	 */
	private JSONObject getInitData(IBusTableRel busTableRel) {
		JSONObject table = new JSONObject();
		table.putAll(busTableRel.getTable().initData());
		for (IBusTableRel rel : busTableRel.getChildren()) {
			if (BusTableRelType.ONE_TO_ONE.equalsWithKey(rel.getType())) {
				// 递归处理一对一
				table.put(rel.getTableKey(), getInitData(rel));
			}
		}
		return table;
	}
}
