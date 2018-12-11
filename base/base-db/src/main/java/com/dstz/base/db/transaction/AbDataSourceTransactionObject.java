package com.dstz.base.db.transaction;
/**
 * <pre>
 * 描述：ab事务管理器的事务对象
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年10月17日
 * 版权:summer
 * </pre>
 */

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.transaction.TransactionDefinition;

import com.dstz.base.db.datasource.DataSourceUtil;

public class AbDataSourceTransactionObject {
	/**
	 * 事务流水号，用于跟踪问题
	 */
	private String serialNumber;
	/**
	 * 线程中的事务配置，就是txAdvice相关信息，如，read-only="true" isolation="READ_COMMITTED"等
	 */
	private TransactionDefinition definition;
	/**
	 * Map<数据源别名,DataSourceTransactionObject>
	 */
	private Map<String, DataSourceTransactionObject> dsTxObjMap = new LinkedHashMap<>();

	public AbDataSourceTransactionObject() {
		this.serialNumber = Integer.toHexString(this.hashCode());
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public TransactionDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(TransactionDefinition definition) {
		this.definition = definition;
	}

	public Map<String, DataSourceTransactionObject> getDsTxObjMap() {
		return dsTxObjMap;
	}

	/**
	 * <pre>
	 * 需求的put
	 * 需要保证本地数据源是最后一个链接
	 * 因为commit时（或者说其他操作），需要最复杂，操作最多的本地数据源保持强一致性
	 * </pre>
	 * 
	 * @param dsKey
	 * @param dsTxObj
	 */
	public void putDsTxObj(String dsKey, DataSourceTransactionObject dsTxObj) {
		dsTxObjMap.put(dsKey, dsTxObj);
		DataSourceTransactionObject txObj = dsTxObjMap.remove(DataSourceUtil.GLOBAL_DATASOURCE);
		dsTxObjMap.put(DataSourceUtil.GLOBAL_DATASOURCE, txObj);
	}

	public DataSourceTransactionObject getDsTxObj(String dsKey) {
		return dsTxObjMap.get(dsKey);
	}

}
