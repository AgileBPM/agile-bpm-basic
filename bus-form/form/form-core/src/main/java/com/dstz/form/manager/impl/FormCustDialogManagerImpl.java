package com.dstz.form.manager.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.core.util.ThreadMapUtil;
import com.dstz.base.dao.CommonDao;
import com.dstz.base.db.dboper.DbOperator;
import com.dstz.base.db.dboper.DbOperatorFactory;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.db.model.table.Column;
import com.dstz.base.db.model.table.Table;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.form.api.constant.FormCustDialogConditionFieldValueSource;
import com.dstz.form.api.constant.FormCustDialogObjType;
import com.dstz.form.api.constant.FormCustDialogStyle;
import com.dstz.form.dao.FormCustDialogDao;
import com.dstz.form.manager.FormCustDialogManager;
import com.dstz.form.model.FormCustDialog;
import com.dstz.form.model.custdialog.FormCustDialogConditionField;
import com.dstz.form.model.custdialog.FormCustDialogDisplayField;
import com.dstz.form.model.custdialog.FormCustDialogReturnField;
import com.dstz.form.model.custdialog.FormCustDialogSortField;
import com.dstz.sys.api.groovy.IGroovyScriptEngine;
import com.dstz.sys.api.model.ISysDataSource;
import com.dstz.sys.api.service.ISysDataSourceService;

/**
 * form_cust_dialog Manager处理实现类
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-01-18 19:30:51
 */
@Service("formCustDialogManager")
public class FormCustDialogManagerImpl extends BaseManager<String, FormCustDialog> implements FormCustDialogManager {
    @Resource
    FormCustDialogDao formCustDialogDao;
    @Autowired
    ISysDataSourceService sysDataSourceService;
    @Autowired
    CommonDao<?> commonDao;
    @Autowired
    IGroovyScriptEngine groovyScriptEngine;

    @Override
    public FormCustDialog getByKey(String key) {
        QueryFilter filter = new DefaultQueryFilter();
        filter.addFilter("key_", key, QueryOP.EQUAL);
        return this.queryOne(filter);
    }

    @Override
    public Map<String, String> searchObjName(FormCustDialog formCustDialog) {
        ISysDataSource sysDataSource = sysDataSourceService.getByKey(formCustDialog.getDsKey());
        JdbcTemplate jdbcTemplate = sysDataSourceService.getJdbcTemplateByKey(formCustDialog.getDsKey());
        Map<String, String> map = new HashMap<>();// Map<表/视图名,表/视图描述>
        DbOperator dbOperator = DbOperatorFactory.newOperator(sysDataSource.getDbType(), jdbcTemplate);
        // 表
        if (FormCustDialogObjType.TABLE.equalsWithKey(formCustDialog.getObjType())) {
            map = dbOperator.getTableNames(formCustDialog.getObjName());
        } else if (FormCustDialogObjType.VIEW.equalsWithKey(formCustDialog.getObjType())) {// 视图
            List<String> viewNames = dbOperator.getViewNames(formCustDialog.getObjName());
            for (String viewName : viewNames) {
                map.put(viewName, viewName);
            }
        }
        return map;
    }

    @Override
    public Table<Column> getTable(FormCustDialog formCustDialog) {
        try {
            ISysDataSource sysDataSource = sysDataSourceService.getByKey(formCustDialog.getDsKey());
            JdbcTemplate jdbcTemplate = sysDataSourceService.getJdbcTemplateByKey(formCustDialog.getDsKey());
            DbOperator dbOperator = DbOperatorFactory.newOperator(sysDataSource.getDbType(), jdbcTemplate);
            Table<Column> table = null;
            // 表
            if (FormCustDialogObjType.TABLE.equalsWithKey(formCustDialog.getObjType())) {
                table = dbOperator.getTable(formCustDialog.getObjName());
            } else if (FormCustDialogObjType.VIEW.equalsWithKey(formCustDialog.getObjType())) {// 视图
                table = dbOperator.getView(formCustDialog.getObjName());
            }
            return table;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<?> data(FormCustDialog formCustDialog, QueryFilter queryFilter) {
        String sql = analyseSql(formCustDialog);
        handleQueryFilter(formCustDialog, queryFilter);

        if(FormCustDialog.DATA_SOURCE_INTERFACE.equals(formCustDialog.getDataSource())) {
        	return getDataByInterface(formCustDialog,queryFilter);
        }
        
        List<?> list;
        boolean isPage = (boolean) ThreadMapUtil.getOrDefault("isPage", true);
        if (isPage) {
            list = commonDao.queryForListPage(sql, queryFilter);
        } else {
        	queryFilter.setPage(null);
        	list = commonDao.queryForListPage(sql, queryFilter);
        }
        return list;
    }
    
	private List getDataByInterface(FormCustDialog customDialog, QueryFilter queryFilter) {
		String beanMethod = customDialog.getObjName();
		if(StringUtil.isEmpty(beanMethod)) throw new RuntimeException("自定义对话框数据服务接口不能为空！"); 
		
		String[] aryHandler = beanMethod.split("[.]");
		if(aryHandler==null || aryHandler.length!=2) throw new RuntimeException("自定义对话框数据服务接口格式不正确！"+beanMethod); ;
		
		String beanId = aryHandler[0];
		String method = aryHandler[1];
		// 触发该Bean下的业务方法
		Object serviceBean = AppUtil.getBean(beanId);
		if(serviceBean==null) return null;
		try {
			Method invokeMethod = serviceBean.getClass().getDeclaredMethod(method, new Class[] {QueryFilter.class});
			return (List) invokeMethod.invoke(serviceBean,queryFilter);
		} catch (Exception e) {
			throw new RuntimeException("查询异常！"+e.getMessage(),e);
		}
	}

    /**
     * <pre>
     * 获取sql
     * 只获取到select XXXX from xxxx 这一段
     * where 和 order 由后面queryFilter来获取
     * </pre>
     *
     * @param formCustDialog
     * @return
     */
    private String analyseSql(FormCustDialog formCustDialog) {
        // 返回sql
        Set<String> columnNameSet = new HashSet<>();//select 的展示字段
        if (FormCustDialogStyle.LIST.equalsWithKey(formCustDialog.getStyle())) {
            for (FormCustDialogDisplayField field : formCustDialog.getDisplayFields()) {//显示字段要查出来
                columnNameSet.add(field.getColumnName());
            }
        }
        if (FormCustDialogStyle.TREE.equalsWithKey(formCustDialog.getStyle())) {
            columnNameSet.add(formCustDialog.getTreeConfig().getPid());
            columnNameSet.add(formCustDialog.getTreeConfig().getId());
            columnNameSet.add(formCustDialog.getTreeConfig().getShowColumn());
        }

        for (FormCustDialogReturnField field : formCustDialog.getReturnFields()) {//返回字段要查出来
            columnNameSet.add(field.getColumnName());
        }
        for (FormCustDialogSortField field : formCustDialog.getSortFields()) {//排序字段要查出来
            columnNameSet.add(field.getColumnName());
        }

        //拼装displaySql
        StringBuilder displaySql = new StringBuilder();
        for (String columnName : columnNameSet) {
            if (displaySql.length() > 0) {
                displaySql.append(",");
            }
            displaySql.append(columnName);
        }

        return "select " + displaySql.toString() + " from " + formCustDialog.getObjName();
    }

    /**
     * <pre>
     * 处理QueryFilter
     * </pre>
     *
     * @param formCustDialog
     * @param queryFilter
     * @return
     */
    private QueryFilter handleQueryFilter(FormCustDialog formCustDialog, QueryFilter queryFilter) {
        //处理条件 固定值，和脚本参数
        for (FormCustDialogConditionField field : formCustDialog.getConditionFields()) {
            if (FormCustDialogConditionFieldValueSource.FIXED_VALUE.equalsWithKey(field.getValueSource())) {
                Object value = BeanUtils.getValue(field.getDbType(), QueryOP.getByVal(field.getCondition()), field.getValue().getText());
                queryFilter.addFilter(field.getColumnName(), value, QueryOP.getByVal(field.getCondition()));
            }
            if (FormCustDialogConditionFieldValueSource.SCRIPT.equalsWithKey(field.getValueSource())) {
                Object value = groovyScriptEngine.executeObject(field.getValue().getText(), queryFilter.getParams());
                queryFilter.addFilter(field.getColumnName(), value, QueryOP.getByVal(field.getCondition()));
            }
        }

        //处理默认排序
        for (FormCustDialogSortField field : formCustDialog.getSortFields()) {
            queryFilter.addFieldSort(field.getColumnName(), field.getSortType());
        }

        return queryFilter;
    }

    @Override
    public boolean existsByKey(String key) {
        return formCustDialogDao.existsByKey(key);
    }
}
