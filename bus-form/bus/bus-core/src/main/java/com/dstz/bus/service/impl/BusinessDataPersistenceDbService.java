package com.dstz.bus.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.tableoper.TableOperator;
import com.dstz.base.db.transaction.AbDataSourceTransactionManager;
import com.dstz.bus.api.constant.BusStatusCode;
import com.dstz.bus.api.constant.BusTableRelFkType;
import com.dstz.bus.api.constant.BusTableRelType;
import com.dstz.bus.api.constant.BusinessObjectPersistenceType;
import com.dstz.bus.manager.BusinessObjectManager;
import com.dstz.bus.manager.BusinessTableManager;
import com.dstz.bus.model.BusTableRel;
import com.dstz.bus.model.BusTableRelFk;
import com.dstz.bus.model.BusinessColumn;
import com.dstz.bus.model.BusinessData;
import com.dstz.bus.model.BusinessObject;
import com.dstz.bus.model.BusinessTable;
import com.dstz.bus.service.BusinessDataPersistenceService;
import com.dstz.sys.api.service.ISysDataSourceService;

/**
 * <pre>
 * 数据库方式的业务数据持久化实现类
 * </pre>
 *
 * @author aschs
 *
 */
@Service
public class BusinessDataPersistenceDbService implements BusinessDataPersistenceService {
	@Autowired
	BusinessTableManager businessTableManager;
	@Autowired
	BusinessObjectManager businessObjectManager;
	@Autowired
	ISysDataSourceService sysDataSourceService;

	@Override
	public String type() {
		return BusinessObjectPersistenceType.DB.getKey();
	}

	@Transactional
	@Override
	public void saveData(BusinessData businessData) {
		// 操作ab事务管理器，把bo用到的数据源进入到事务管理中
		for (String key : businessData.getBusTableRel().getBusObj().calDataSourceKeys()) {
			DataSource dataSource = sysDataSourceService.getDataSourceByKey(key);
			AbDataSourceTransactionManager.addDataSource(key, dataSource);
		}

		// 获取当前表的数据库操作者
		TableOperator tableOperator = businessTableManager.newTableOperatorCheckExist(businessData.getBusTableRel().getTable());

		String busTableRelType = businessData.getBusTableRel().getType();

		// 没有表数据库操作权限
		if (!businessData.getBusTableRel().getBusObj().haveTableDbEditRights(businessData.getBusTableRel().getTableKey())) {
			//没编辑，但是有读权限，则需要操作一下子表
			if (businessData.getBusTableRel().getBusObj().haveTableDbReadRights(businessData.getBusTableRel().getTableKey())) {
				saveChildren(businessData);
			}
			return;
		}

		// 主表
		if (BusTableRelType.MAIN.equalsWithKey(busTableRelType)) {
			// 数据中的ID
			Object id = businessData.getPk();
			if (id == null) {// 新增
				businessData.setPk(IdUtil.getSuid());
				tableOperator.insertData(businessData.getDbData());
			} else {// 更新
				tableOperator.updateData(businessData.getDbData());
			}
		}

		// 子表 一对一，一对多的处理
		if (BusTableRelType.ONE_TO_ONE.equalsWithKey(busTableRelType) || BusTableRelType.ONE_TO_MANY.equalsWithKey(busTableRelType)) {
			Object id = businessData.getPk();
			boolean newData = false;
			if (id == null) {// 新增
				businessData.setPk(IdUtil.getSuid());
				newData = true;
			}

			// 父数据
			BusinessData parBusinessData = businessData.getParent();
			// 设置一下外键的值，每次保存都赋值
			for (BusTableRelFk fk : businessData.getBusTableRel().getFks()) {
				// 固定值
				if (BusTableRelFkType.FIXED_VALUE.equalsWithKey(fk.getType())) {
					businessData.put(fk.getFrom(), fk.getValue());
				} else if (BusTableRelFkType.PARENT_FIELD.equalsWithKey(fk.getType())) {// 对应父字段
					Object fkValue = parBusinessData.get(fk.getValue());
					if (fkValue == null || StringUtil.isEmpty(fkValue.toString())) {
						BusinessTable mainTable = parBusinessData.getBusTableRel().getTable();
						BusinessTable subTable = businessData.getBusTableRel().getTable();
						throw new BusinessException(String.format("在子表外键对应父字段时，父表【%s（%s）】字段【%s】为空，导致子表【%s（%s）】外键字段【%s】无法关联", mainTable.getComment(), mainTable.getKey(), fk.getValue(), subTable.getComment(), subTable.getKey(), fk.getFrom()));
					}
					businessData.put(fk.getFrom(), fkValue);
				} else if (BusTableRelFkType.CHILD_FIELD.equalsWithKey(fk.getType())) {// 父外键对应当前字段，修改父数据
					Object fkValue = businessData.get(fk.getFrom());
					if (fkValue == null || StringUtil.isEmpty(fkValue.toString())) {
						BusinessTable mainTable = parBusinessData.getBusTableRel().getTable();
						BusinessTable subTable = businessData.getBusTableRel().getTable();
						throw new BusinessException(String.format("在主表外键对应子表字段时，子表【%s（%s）】字段【%s】为空，导致父表【%s（%s）】外键字段【%s】无法关联", subTable.getComment(), subTable.getKey(), fk.getFrom(), mainTable.getComment(), mainTable.getKey(), fk.getValue()));
					}
					parBusinessData.put(fk.getValue(), fkValue);
					// 更新父级数据
					businessTableManager.newTableOperator(parBusinessData.getBusTableRel().getTable()).updateData(parBusinessData.getDbData());
				}
			}

			if (newData) {// 新增
				tableOperator.insertData(businessData.getDbData());
			} else {// 更新
				tableOperator.updateData(businessData.getDbData());
			}
		}

		saveChildren(businessData);
	}

	/**
	 * <pre>
	 * 处理子表
	 * 主要是删除子表的旧数据
	 * </pre>
	 * 
	 * @param businessData
	 *            ：数据
	 */
	private void saveChildren(BusinessData businessData) {
		for (BusTableRel rel : businessData.getBusTableRel().getChildren()) {
			if (!rel.getBusObj().haveTableDbEditRights(rel.getTableKey())) {// 该表没数据库操作权限
				continue;
			}
			TableOperator tableOperator = businessTableManager.newTableOperatorCheckExist(rel.getTable());// 子表的表操作者
			// 拥有数据库操作权限
			if (BusTableRelType.ONE_TO_MANY.equalsWithKey(rel.getType()) || BusTableRelType.ONE_TO_ONE.equalsWithKey(rel.getType())) {
				Map<String, Object> param = new HashMap<>();
				for (BusTableRelFk fk : rel.getFks()) {
					// 固定值
					if (BusTableRelFkType.FIXED_VALUE.equalsWithKey(fk.getType())) {
						param.put(fk.getFrom(), fk.getValue());
					} else if (BusTableRelFkType.PARENT_FIELD.equalsWithKey(fk.getType())) {// 对应父字段
						param.put(fk.getFrom(), businessData.get(fk.getValue()));
					} else if (BusTableRelFkType.CHILD_FIELD.equalsWithKey(fk.getType())) {// 父外键对应当前字段
						// 不需要删除旧数据
					}
				}
				// 在子表中的原有数据
				List<Map<String, Object>> oldDatas = new ArrayList<>();
				if (!param.isEmpty()) {// 不为空才去查旧数据
					oldDatas = tableOperator.selectData(toDbMap(rel.getTable(), param));
				}

				// 这是数据带过来的新数据
				List<BusinessData> children = businessData.getChildren().computeIfAbsent(rel.getTableKey(), k -> new ArrayList<>());

				outer: // 外层循环标记
				for (Map<String, Object> oldData : oldDatas) {
					Object id = oldData.get(rel.getTable().getPkName());
					for (BusinessData data : children) {
						if (id.equals(data.getPk())) {// 旧数据id在新数据中找到对应ID，则不需要删除，继续外层循环
							continue outer;
						}
					}
					removeChildren(oldData, rel);
					// 最终留下的就是过期ID，要被删除
					tableOperator.deleteData(id);
				}

				for (BusinessData data : children) {
					saveData(data);
				}
			}
		}

	}

	@Override
	public BusinessData loadData(BusinessObject businessObject, Object id) {
		BusinessData businessData = new BusinessData();

		// 处理主表
		BusTableRel busTableRel = businessObject.getRelation();
		businessData.setBusTableRel(busTableRel);
		BusinessTable businessTable = busTableRel.getTable();
		if (id == null) {// 主键为空
			return businessData;
		}
		if (!businessObject.haveTableDbReadRights(busTableRel.getTableKey())) {// 没表的读取权限就返回
			return businessData;
		}

		Map<String, Object> data = businessTableManager.newTableOperatorCheckExist(businessTable).selectData(getReadableColumnName(busTableRel), id);
		if (data == null) {
			throw new BusinessException(String.valueOf(id), BusStatusCode.BUS_DATA_LOSE);
		}
		businessData.setDbData(data);

		// 处理子表
		loadChildren(businessData, busTableRel);

		return businessData;
	}

	/**
	 * <pre>
	 * 加载子数据
	 * </pre>
	 * 
	 * @param businessData
	 *            当前数据
	 * @param busTableRel
	 *            当前数据的表关系
	 */
	private void loadChildren(BusinessData businessData, BusTableRel busTableRel) {
		for (BusTableRel rel : busTableRel.getChildren()) {
			BusinessTable table = rel.getTable();// 子表

			if (!rel.getBusObj().haveTableDbReadRights(rel.getTableKey())) {// 没表的读取权限就返回
				return;
			}

			Map<String, Object> param = new HashMap<>();
			for (BusTableRelFk fk : rel.getFks()) {
				// 固定值
				if (BusTableRelFkType.FIXED_VALUE.equalsWithKey(fk.getType())) {
					param.put(fk.getFrom(), fk.getValue());
				} else if (BusTableRelFkType.PARENT_FIELD.equalsWithKey(fk.getType())) {// 对应父字段
					param.put(fk.getFrom(), businessData.get(fk.getValue()));
				} else if (BusTableRelFkType.CHILD_FIELD.equalsWithKey(fk.getType())) {// 父外键对应当前字段
					param.put(fk.getFrom(), businessData.get(fk.getValue()));// 跟上一个一样，但是还是在if中好操作点
				}
			}
			List<Map<String, Object>> dataMapList = businessTableManager.newTableOperatorCheckExist(table).selectData(getReadableColumnName(rel), toDbMap(table, param));
			for (Map<String, Object> dataMap : dataMapList) {
				BusinessData data = new BusinessData();
				data.setBusTableRel(rel);
				data.setParent(businessData);
				data.setDbData(dataMap);
				businessData.addChildren(data);

				// 递归处理子表数据
				loadChildren(data, rel);
			}

		}
	}

	@Override
	public void removeData(BusinessObject businessObject, Object id) {
		BusTableRel busTableRel = businessObject.getRelation();
		// 没有表数据库操作权限
		if (!busTableRel.getBusObj().haveTableDbEditRights(busTableRel.getTableKey())) {
			return;
		}

		Map<String, Object> data = businessTableManager.newTableOperatorCheckExist(busTableRel.getTable()).selectData(id);
		businessTableManager.newTableOperator(busTableRel.getTable()).deleteData(data.get(busTableRel.getTable().getPkName()));
		removeChildren(data, busTableRel);
	}

	private void removeChildren(Map<String, Object> dbData, BusTableRel busTableRel) {
		// 处理子表
		for (BusTableRel rel : busTableRel.getChildren()) {
			// 没有表数据库操作权限
			if (!rel.getBusObj().haveTableDbEditRights(rel.getTableKey())) {
				continue;
			}

			Map<String, Object> param = new HashMap<>();
			Map<String, Object> data = fromDbMap(busTableRel.getTable(), dbData);
			for (BusTableRelFk fk : rel.getFks()) {
				// 固定值
				if (BusTableRelFkType.FIXED_VALUE.equalsWithKey(fk.getType())) {
					param.put(fk.getFrom(), fk.getValue());
				} else if (BusTableRelFkType.PARENT_FIELD.equalsWithKey(fk.getType())) {// 对应父字段
					param.put(fk.getFrom(), data.get(fk.getValue()));
				} else if (BusTableRelFkType.CHILD_FIELD.equalsWithKey(fk.getType())) {// 子表字段
																						// 对应
																						// 父实例外键
					// 逻辑跟PARENT_FIELD一样，在这里故意写两个冗余代码，方便理解
					param.put(fk.getFrom(), data.get(fk.getValue()));
				}
			}
			if (rel.getChildren().isEmpty()) {// 子表没子表了，那就按照主表外键输出子表数据
				businessTableManager.newTableOperatorCheckExist(rel.getTable()).deleteData(toDbMap(rel.getTable(), param));
			} else {// 子表还有子表，那就要遍历递归了
				List<Map<String, Object>> dataMapList = businessTableManager.newTableOperator(rel.getTable()).selectData(toDbMap(rel.getTable(), param));
				for (Map<String, Object> dataMap : dataMapList) {
					removeChildren(dataMap, rel);
				}
			}
		}
	}

	/**
	 * <pre>
	 * 获取有读取权限的字段名
	 * </pre>
	 * 
	 * @param busTableRel
	 * @return
	 */
	private List<String> getReadableColumnName(BusTableRel busTableRel) {
		List<String> columnName = new ArrayList<>();
		for (BusinessColumn column : busTableRel.getTable().getColumns()) {
			if (busTableRel.getBusObj().haveColumnDbReadRights(busTableRel.getTableKey(), column.getKey())) {
				columnName.add(column.getName());
			}
		}
		return columnName;
	}

	/**
	 * <pre>
	 * 属性是字段key的map转成属性是name的map
	 * </pre>
	 * 
	 * @param data
	 * @return
	 */
	private Map<String, Object> toDbMap(BusinessTable table, Map<String, Object> map) {
		Map<String, Object> dbData = new HashMap<>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String columnName = table.getColumnByKey(entry.getKey()).getName();
			dbData.put(columnName, entry.getValue());
		}
		return dbData;
	}

	/**
	 * <pre>
	 * 属性是字段name的map转成属性是key的map
	 * </pre>
	 * 
	 * @param table
	 * @param map
	 * @return
	 */
	private Map<String, Object> fromDbMap(BusinessTable table, Map<String, Object> map) {
		Map<String, Object> data = new HashMap<>();
		for (Entry<String, Object> entry : map.entrySet()) {
			String columnKey = table.getColumn(entry.getKey()).getKey();
			data.put(columnKey, entry.getValue());
		}
		return data;
	}
}
