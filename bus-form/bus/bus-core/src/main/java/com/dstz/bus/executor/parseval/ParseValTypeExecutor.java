package com.dstz.bus.executor.parseval;

import org.springframework.stereotype.Service;

import com.dstz.base.core.util.StringUtil;
import com.dstz.bus.model.BusinessColumn;

/**
 * <pre>
 * 描述：formDefData.data的值按照字段类型转成businessData.data所需的
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月20日
 * 版权:summer
 * </pre>
 */
@Service
public class ParseValTypeExecutor extends ParseValExecuteChain {

	@Override
	public int getSn() {
		return 0;
	}

	@Override
	protected void run(ParseValParam param) {
		String key = param.getKey();
		Object value = param.getValue();

		if (value == null||StringUtil.isEmpty(value.toString())) {
			return;
		}

		BusinessColumn column = param.getBusTableRel().getTable().getColumnByKey(key);
		if(column == null) {
			param.getData().put(key,value);
			return;
		}
		
		param.getData().put(column.getKey(), column.value(value.toString()));

	}

}
