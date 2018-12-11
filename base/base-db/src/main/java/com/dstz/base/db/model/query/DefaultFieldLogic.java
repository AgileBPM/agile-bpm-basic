package com.dstz.base.db.model.query;

import com.dstz.base.api.query.FieldLogic;
import com.dstz.base.api.query.FieldRelation;
import com.dstz.base.api.query.WhereClause;

import java.util.ArrayList;
import java.util.List;

/**
 * @author csx
 */
public class DefaultFieldLogic implements FieldLogic {

    /**
     * 查询字段组合列表
     */
    private List<WhereClause> whereClauses = new ArrayList<WhereClause>();
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
        if (whereClauses.size() == 0) return "";
        if (whereClauses.size() == 1 && !FieldRelation.NOT.equals(fieldRelation)) return whereClauses.get(0).getSql();

        StringBuilder sqlBuf = new StringBuilder("(");
        int i = 0;
        if (whereClauses.size() > 0 && FieldRelation.NOT.equals(fieldRelation)) {
            sqlBuf.append(" NOT (");
            for (WhereClause clause : whereClauses) {
                if (i++ > 0) {
                    sqlBuf.append(" ").append(FieldRelation.AND).append(" ");
                }
                sqlBuf.append(clause.getSql());
            }
            sqlBuf.append(")");

            return sqlBuf.toString();
        }

        for (WhereClause clause : whereClauses) {
            if (i++ > 0) {
                sqlBuf.append(" ").append(fieldRelation).append(" ");
            }
            sqlBuf.append(clause.getSql());
        }
        sqlBuf.append(")");

        return sqlBuf.toString();
    }

}
