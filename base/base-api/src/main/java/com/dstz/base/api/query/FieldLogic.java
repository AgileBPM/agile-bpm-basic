package com.dstz.base.api.query;

import java.util.List;

/**
 * 字段条件组合查询
 *
 * @author csx
 */
public interface FieldLogic extends WhereClause {
    public List<WhereClause> getWhereClauses();
}
