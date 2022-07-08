package com.dstz.base.db.model.query;

import java.util.ArrayList;
import java.util.List;

import com.dstz.base.api.query.FieldLogic;
import com.dstz.base.api.query.FieldRelation;
import com.dstz.base.api.query.WhereClause;

/**
 * @author csx
 */
public class DefaultFieldLogic implements FieldLogic {

	/**
	 * 查询字段组合列表
	 */
	private List<WhereClause> whereClauses = new ArrayList<>();
	/**
	 * 字段关系
	 */
	private FieldRelation fieldRelation = FieldRelation.AND;

	public DefaultFieldLogic() {
	}

	public DefaultFieldLogic(FieldRelation fieldRelation) {
		this.fieldRelation = fieldRelation;
	}

	public List<WhereClause> getWhereClauses() {
		return whereClauses;
	}

	public void setWhereClauses(List<WhereClause> whereClauses) {
		this.whereClauses = whereClauses;
	}

	public FieldRelation getFieldRelation() {
		return fieldRelation;
	}

	public void setFieldRelation(FieldRelation fieldRelation) {
		this.fieldRelation = fieldRelation;
	}

	public String getSql() {
		if (whereClauses.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder("(");
		if (!whereClauses.isEmpty() && FieldRelation.NOT == fieldRelation) {
			sb.append(" NOT (");
			for (WhereClause wc : whereClauses) {
				if (!sb.toString().endsWith("NOT (")) {
					sb.append(" " + FieldRelation.AND.value() + " ");
				}
				sb.append(wc.getSql());
			}
			sb.append(")");
			return sb.toString();
		}

		for (WhereClause wc : whereClauses) {
			if (!sb.toString().endsWith("(")) {
				sb.append(" " + fieldRelation.value() + " ");
			}
			sb.append(wc.getSql());
		}
		sb.append(")");

		return sb.toString();
	}

}
