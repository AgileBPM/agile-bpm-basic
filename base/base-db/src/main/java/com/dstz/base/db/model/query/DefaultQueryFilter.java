package com.dstz.base.db.model.query;

import com.dstz.base.api.Page;
import com.dstz.base.api.query.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;


public class DefaultQueryFilter implements QueryFilter {
    /**
     * 分页组件
     */
    private Page page = new DefaultPage();
    /**
     * 排序字段
     */
    private List<FieldSort> fieldSortList = new ArrayList<FieldSort>();
    /**
     * 字段参数构建列表
     */
    private Map<String, Object> params = new LinkedHashMap<String, Object>();
    /**
     * 字段参数组合关系列表
     */
    private FieldLogic fieldLogic = new DefaultFieldLogic();


    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Map<String, Object> getParams() {
        initParams(this.fieldLogic);
        return params;
    }

    public DefaultQueryFilter() {
    }

    public DefaultQueryFilter(FieldLogic fieldLogic) {
        this.fieldLogic = fieldLogic;
    }

    public FieldLogic getFieldLogic() {
        return fieldLogic;
    }

    public void setFieldLogic(FieldLogic fieldLogic) {
        this.fieldLogic = fieldLogic;
    }

    //初始化参数
    private void initParams(FieldLogic fedLog) {
        List<WhereClause> list = fedLog.getWhereClauses();
        for (WhereClause clause : list) {
            if (clause instanceof QueryField) {
                QueryField queryField = (QueryField) clause;
                if (QueryOP.IS_NULL.equals(queryField.getCompare())
                        || QueryOP.NOTNULL.equals(queryField.getCompare())) {
                    continue;
                }
                //如果查询字段包含数据库别名，参数设置去掉别名
                String fileNameString = queryField.getField();
                if (fileNameString.indexOf(".") > -1) {
                    fileNameString = fileNameString.substring(fileNameString.indexOf(".") + 1);
                }
                this.params.put(fileNameString, queryField.getValue());
            } else if (clause instanceof FieldLogic) {
                FieldLogic fdTemp = (FieldLogic) clause;
                initParams(fdTemp);
            }
        }
    }

    public List<FieldSort> getFieldSortList() {
        return fieldSortList;
    }

    public void setFieldSortList(List<FieldSort> fieldSortList) {
        this.fieldSortList = fieldSortList;
    }


    /**
     * 添加排序配置。
     *
     * @param orderField 排序字段
     * @param orderSeq   排序
     */
    @Override
    public void addFieldSort(String orderField, String orderSeq) {
        fieldSortList.add(new DefaultFieldSort(orderField, Direction.fromString(orderSeq)));
    }

    public void addFilter(String name, Object obj, QueryOP queryType) {
        fieldLogic.getWhereClauses().add(new DefaultQueryField(name, queryType, obj));
    }

    public void addParamsFilter(String key, Object obj) {
        this.params.put(key, obj);
    }
	@Override
	public void addParams(Map<String, Object> params) {
		this.params.putAll(params);
	}

	@Override
	public String getWhereSql() {
		String dynamicWhereSql = this.getFieldLogic().getSql();
		
		String defaultWhere = params.containsKey("defaultWhere") ? params.get("defaultWhere").toString() : "";
        if (StringUtils.isNotEmpty(defaultWhere)) {
            dynamicWhereSql = StringUtils.isNotEmpty(dynamicWhereSql) ? dynamicWhereSql + " and " + defaultWhere : defaultWhere;
        }
		return dynamicWhereSql;
	}

	@Override
	public String getOrderBySql() {
		if (this.getFieldSortList().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (FieldSort fieldSort : this.getFieldSortList()) {
                sb.append(fieldSort.getField()).append(" ").append(fieldSort.getDirection()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
		return null;
	}
}
