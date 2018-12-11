package com.dstz.base.dao.baseinterceptor;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.dstz.base.api.Page;
import com.dstz.base.api.query.FieldSort;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.core.util.BeanUtils;
import com.github.pagehelper.PageHelper;
/**
 * 查询的切面逻辑
 * @author Jeff
 *
 */
@Intercepts(
	    {
	        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
	        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
	    }
	)
public class QueryInterceptor  implements Interceptor{

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		if(BeanUtils.isEmpty(args) || args.length < 2) {
			 return invocation.proceed();
		}
		
		Object param = args[1];
		
		// 分页 ，参数转换
		if(param instanceof QueryFilter) {
			QueryFilter filter = (QueryFilter) param;
			
			//将queryFilter转为Map Param 
			Map<String, Object> params = getQueryParamsByFilter(filter);
			args[1] = params;
			
			Page page = filter.getPage();
			if(page != null) {
				PageHelper.startPage(page.getPageNo(), page.getPageSize(),true);
			}
		}
		
		// 其他事情
		
		
		 return invocation.proceed();
	}
	
	

	private Map<String, Object> getQueryParamsByFilter(QueryFilter filter) {
        //构建动态条件SQL
        String dynamicWhereSql = filter.getFieldLogic().getSql();
        Map<String, Object> params = filter.getParams();

        //默认条件过虑
        String defaultWhere = params.containsKey("defaultWhere") ? params.get("defaultWhere").toString() : "";
        if (StringUtils.isNotEmpty(defaultWhere)) {
            dynamicWhereSql = StringUtils.isNotEmpty(dynamicWhereSql) ? dynamicWhereSql + " and " + defaultWhere : defaultWhere;
        }

        if (StringUtils.isNotEmpty(dynamicWhereSql)) {
            params.put("whereSql", dynamicWhereSql);
        }
        //构建排序SQL
        if (filter.getFieldSortList().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (FieldSort fieldSort : filter.getFieldSortList()) {
                sb.append(fieldSort.getField()).append(" ").append(fieldSort.getDirection()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            params.put("orderBySql", sb.toString());
        }
        return  params;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}

}
