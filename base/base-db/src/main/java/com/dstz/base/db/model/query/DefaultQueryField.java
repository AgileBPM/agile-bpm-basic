package com.dstz.base.db.model.query;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import com.dstz.base.api.constant.StringConstants;
import com.dstz.base.api.query.QueryField;
import com.dstz.base.api.query.QueryOP;

import cn.hutool.core.date.DateUtil;

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

    // 字段前缀名，一般用于表的别名，如user.
    // private String preFieldName="";

    public DefaultQueryField() {
    }

    public DefaultQueryField(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public DefaultQueryField(String field, QueryOP compare, Object value) {
        this.field = field;
        this.compare = compare;
        this.value = value;

        if (QueryOP.IN.equals(compare) || QueryOP.NOT_IN.equals(compare)) {
            this.value = getInValueSql();
        } else {
            this.value = value;
        }

    }

    /**
     * 针对in查询方式，根据传入的value的类型做不同的处理。 value 是 string，则分隔字符串，然后合并为符合in规范的字符串。
     * value 是 list，则直接合并为符合in规范的字符串。
     *
     * @return
     */
    private String getInValueSql() {
        if (value instanceof String) { // 字符串形式，通过逗号分隔
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            StringTokenizer st = new StringTokenizer(value.toString(), ",");
            while (st.hasMoreTokens()) {
                sb.append("\'");//oracle参数只支持'
                sb.append(st.nextToken());
                sb.append("\'");
                sb.append(",");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb.append(")");
            return sb.toString();
        } else if (value instanceof List) { // 列表形式
            List<Object> objList = (List<Object>) value;
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Object obj : objList) {
                sb.append("\"");
                sb.append(obj.toString());
                sb.append("\"");
                sb.append(",");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));
            sb.append(")");
            return sb.toString();
        }
        return "";
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
			 sql = "upper(" + sql + ") = " + fieldParam;
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
			sql += " like " + fieldParam ;
			this.setValue("%" + String.valueOf(this.value));
			
			break;
		case RIGHT_LIKE:
			 sql += " like  " + fieldParam ;
			 this.setValue(String.valueOf(this.value)+ "%" );
			 
			 break;
		case LIKE:
			 sql += " like  " + fieldParam ;
			 this.setValue("%"+ String.valueOf(this.value)+ "%" );
			 
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
		default:
			 sql += " =  " + fieldParam;
			 break;
		}
			  
        return sql;
    }
    

    private String getBetweenSql() {
        StringBuilder sb = new StringBuilder();
        sb.append(" between ");
        if (this.value instanceof List) {
            List<Object> objList = (List<Object>) value;
            for (int i = 0; i < objList.size(); i++) {
                Object obj = objList.get(i);
                if (i == 1) {
                    sb.append(" and ");
                }
                if (obj instanceof Date) {
                    String dateString = DateUtil.format((Date) obj, StringConstants.DATE_FORMAT_DATETIME);
                    sb.append("\"" + dateString + "\"");
                } else {
                    sb.append("\"" + obj.toString() + "\"");
                }
            }
        }
        sb.append(" ");
        return sb.toString();
    }
}