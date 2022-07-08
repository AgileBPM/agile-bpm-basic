package com.dstz.base.db.model.query;

import java.util.Arrays;
import java.util.List;

import com.dstz.base.api.query.QueryField;
import com.dstz.base.api.query.QueryOP;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 默认条件接口实现类。
 */
public class DefaultQueryField implements QueryField {
	// 字段
	private String field;
	// 比较符
	private QueryOP compare;
	// 比较值
	private Object value;

	public DefaultQueryField() {
	}

	public DefaultQueryField(String field, Object value) {
		this.field = field;
		this.value = value;
	}

	public DefaultQueryField(String field, QueryOP compare, Object value) {
		this.value = value;
		this.field = field;
		this.compare = compare;

		if (QueryOP.IN == compare || QueryOP.NOT_IN == compare) {
			this.value = getInValueSql();
		} else {
			this.value = value;
		}

	}

	/**
	 * 针对in查询方式，根据传入的value的类型做不同的处理。 value 是 string，则分隔字符串，然后合并为符合in规范的字符串。
	 * value 是 list，则直接合并为符合in规范的字符串
	 * 
	 * @return
	 */
	private String getInValueSql() {
		List listValue = null;
		if (value instanceof String) {
			listValue = Arrays.asList(((String) value).split(","));
		}
		if (value instanceof List) {
			listValue = (List) value;
		}

		if (CollectionUtil.isEmpty(listValue))
			return "";

		StringBuilder inSqlStr = new StringBuilder("(");
		for (Object obj : listValue) {
			if (obj == null || obj.equals(""))
				continue;

			inSqlStr.append("\"");
			inSqlStr.append(obj.toString());
			inSqlStr.append("\"");
			inSqlStr.append(",");
		}
		inSqlStr = new StringBuilder(inSqlStr.substring(0, inSqlStr.length() - 1));
		inSqlStr.append(")");
		return inSqlStr.toString();
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public QueryOP getCompare() {
		return compare;
	}

	public void setCompare(QueryOP compare) {
		this.compare = compare;
	}

	public String getSql() {
		if (compare == null) {
			compare = QueryOP.EQUAL;
		}
		String fieldParam;
		if (field.indexOf(".") > -1) {
			fieldParam = "#{" + field.substring(field.indexOf(".") + 1) + "}";
		} else {
			fieldParam = "#{" + field + "}";
		}
		String sql = field;

		if (sql.lastIndexOf("^") != -1) {
			sql = sql.substring(0, sql.lastIndexOf("^"));
		}

		switch (compare) {
		case EQUAL:
			sql += " = " + fieldParam;
			break;
		case EQUAL_IGNORE_CASE:
			sql = "upper(" + sql + ") = " + fieldParam;
			break;
		case LESS:
			sql += " < " + fieldParam;
			break;
		case LESS_EQUAL:
			sql += " <= " + fieldParam;
			break;
		case GREAT:
			sql += " > " + fieldParam;
			break;
		case GREAT_EQUAL:
			sql += " >= " + fieldParam;
			break;
		case NOT_EQUAL:
			sql += " != " + fieldParam;
			break;
		case LEFT_LIKE:
			sql += " like " + fieldParam;
			this.setValue("%" + String.valueOf(this.value));
			break;
		case RIGHT_LIKE:
			sql += " like  " + fieldParam;
			this.setValue(String.valueOf(this.value) + "%");
			break;
		case LIKE:
			sql += " like  " + fieldParam;
			this.setValue("%" + String.valueOf(this.value) + "%");
			break;
		case IS_NULL:
			sql += " is null";
			break;
		case IN:
			sql += " in  " + this.value;
			break;
		case NOT_IN:
			sql += " not in  " + this.value;
			break;
		case BETWEEN:
			if (field.endsWith("-end")) {
				sql = field.substring(0, field.length() - 4) + " <= " + fieldParam;
			} else {
				sql += " >= " + fieldParam;
			}
			break;
		default:
			sql += " =  " + fieldParam;
			break;
		}

		return sql;
	}

}