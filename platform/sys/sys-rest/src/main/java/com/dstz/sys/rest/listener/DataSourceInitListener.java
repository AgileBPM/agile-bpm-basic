package com.dstz.sys.rest.listener;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.PropertyUtil;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.datasource.DataSourceUtil;
import com.dstz.sys.core.manager.SysDataSourceManager;
import com.dstz.sys.core.model.SysDataSource;

/**
 * <pre>
 * 在spring容器启动时加载数据源：
 * 从spring文件中做加载。 扫描系统spring 配置中数据源动态加入到系统的dataSourceMap数据源中，
 * </pre>
 *
 * <pre>
 * </pre>
 */
@Component
public class DataSourceInitListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private SysDataSourceManager sysDataSourceManager;

	protected static final Logger LOGGER = LoggerFactory.getLogger(DataSourceInitListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent ev) {
		// 防止重复执行。
		if (ev.getApplicationContext().getParent() != null)
			return;

		ApplicationContext context = ev.getApplicationContext();
		// 加载配置文件中的数据源然后放到dynamicDataSource中，然后增加到系统数据中
		loadDataSourceFromFile(context);
		// 加载系统数据源到dynamicDataSource中
		loadDataSourceFromSysDataSource();
	}

	/**
	 * <pre>
	 * 加载系统数据源到dynamicDataSource中
	 * </pre>
	 */
	private void loadDataSourceFromSysDataSource() {
		for (SysDataSource sysDataSource : sysDataSourceManager.getAll()) {
			// 系统数据源，同时sysDataSource表中的数据库类型跟配置文件的不一致
			if (sysDataSource.getKey().equals(DataSourceUtil.DEFAULT_DATASOURCE) && !PropertyUtil.getJdbcType().equals(sysDataSource.getDbType())) {
				sysDataSource.setDbType(PropertyUtil.getJdbcType());
				sysDataSourceManager.update(sysDataSource);

			}

			// 本地数据源不需要再次增加进去
			if (DataSourceUtil.isDataSourceExist(sysDataSource.getKey()) || sysDataSource.getKey().equals(DataSourceUtil.GLOBAL_DATASOURCE) || sysDataSource.getKey().equals(DataSourceUtil.DEFAULT_DATASOURCE)) {
				continue;
			}
			DataSourceUtil.addDataSource(sysDataSource.getKey(), sysDataSourceManager.tranform2DataSource(sysDataSource), sysDataSource.getDbType(), false);
			LOGGER.debug("add datasource " + sysDataSource.getKey());
		}
	}

	/**
	 * 加载配置文件中的数据源然后放到dynamicDataSource中，然后增加到系统数据中
	 *
	 * @param context
	 *            void
	 */
	void loadDataSourceFromFile(ApplicationContext context) {
		Map<String, DataSource> mapDataSource = context.getBeansOfType(DataSource.class);
		for (Entry<String, DataSource> entry : mapDataSource.entrySet()) {
			// 本地数据源不需要再次增加进去
			if (entry.getKey().equals(DataSourceUtil.GLOBAL_DATASOURCE) || entry.getKey().equals(DataSourceUtil.DEFAULT_DATASOURCE)) {
				continue;
			}
			String dbType = getDbType(entry.getValue());
			DataSourceUtil.addDataSource(entry.getKey(), entry.getValue(), dbType, false);
			LOGGER.debug("add datasource " + entry.getKey());
			// 将其新增到系统配置的数据源中，供客户使用
			if (sysDataSourceManager.getByKey(entry.getKey()) == null) {
				SysDataSource sysDataSource = new SysDataSource();
				sysDataSource.setKey(entry.getKey());
				sysDataSource.setName(entry.getKey() + "数据源");
				sysDataSource.setId(IdUtil.getSuid());
				sysDataSource.setDbType(dbType);
				sysDataSourceManager.create(sysDataSource);
			}
		}
	}

	/**
	 * <pre>
	 * 根据数据源获取数据库类型
	 * </pre>
	 *
	 * @param dataSource
	 * @return
	 */
	private String getDbType(DataSource dataSource) {
		try {
			Class<?> cls = dataSource.getClass();
			Method getDriverClassNameMethod = cls.getDeclaredMethod("getDriverClassName");
			String driverClassName = (String) getDriverClassNameMethod.invoke(dataSource);
			for (DbType dbType : DbType.values()) {
				if (driverClassName.contains(dbType.getKey())) {
					return dbType.getKey();
				}
			}
			return "";
		} catch (Exception e) {
			return "";
		}
	}

}
