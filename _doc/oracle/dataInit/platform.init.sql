
-- 系统分类
INSERT INTO sys_tree (id_, key_, name_, desc_, system_) VALUES ('20000002810002', 'ywbfl', '业务表分类', '业务表分类的树', '1');
INSERT INTO sys_tree (id_, key_, name_, desc_, system_) VALUES ('20000002900001', 'ywdxfl', '业务对象分类', '业务对象分类树', '1');
INSERT INTO sys_tree (id_, key_, name_, desc_, system_) VALUES ('20000002960002', 'bdfl', '表单分类', '表单分类', '1');
INSERT INTO sys_tree (id_, key_, name_, desc_, system_) VALUES ('20000007060001', 'dict', '数据字典分类', '数据字典', '1');
INSERT INTO sys_tree (id_, key_, name_, desc_, system_) VALUES ('20000008980001', 'flow', '流程分类', NULL, '1');

-- 分类节点
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000002810003', 'mrfl', '默认分类', NULL, '20000002810002', '0', '20000002810003.', '1');
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000002900002', 'ywdxmrfl', '业务对象默认分类', NULL, '20000002900001', '0', '20000002900002.', '3');
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000002960003', 'mrfl', '默认分类', NULL, '20000002960002', '0', '20000002960003.', '4');
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000007060004', 'xtpz', '系统配置', NULL, '20000007060001', '0', '20000007060004.', '6');
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000007060005', 'khxg', '客户相关', NULL, '20000007060001', '0', '20000007060005.', '7');
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000008980003', 'bg', '办公', NULL, '20000008980001', '0', '20000008980003.', '7');
INSERT INTO sys_tree_node (id_, key_, name_, desc_, tree_id_, parent_id_, path_, sn_) VALUES ('20000008980004', 'ywlc', '业务流程', NULL, '20000008980001', '0', '20000008980004.', '8');


-- 组织
INSERT INTO org_group (id_, name_, parent_id_, sn_, code_, grade_, desc_) VALUES ('1', '敏捷工作流程', '0', '1', 'htrj', '1', NULL);
INSERT INTO org_group (id_, name_, parent_id_, sn_, code_, grade_, desc_) VALUES ('20000003450003', '深圳分公司', '1', NULL, '711', NULL, NULL);
-- 用户
INSERT INTO org_user (id_, fullname_, account_, password_, email_, mobile_, weixin_, create_time_, address_, photo_, sex_, from_, status_) VALUES ('1', '系统管理员', 'admin', 'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=', 'for_office@qq.com', '11111111', 'test', NULL, 'test', NULL, '未知', 'system', '1');

-- 系统参数
INSERT INTO sys_properties (id_, name_, alias_, group_, value_, encrypt_, update_by_, update_time_, create_by_, create_time_, description_, environment_) VALUES ('6', '系统管理员', 'admin.account', '系统参数', '3e0225725ff214e4', '1', NULL, NULL, NULL, NULL, '系统管理员', 'DEV');
-- 系统脚本
INSERT INTO "SYS_SCRIPT" VALUES ('10000000000001', '获取流水号', 'sysScript.getNextSerialNo("流水号Alias");', '系统内置', '获取流水号');
INSERT INTO "SYS_SCRIPT" VALUES ('10000000000002', '获取当前用户信息', 'sysScript.getCurrentUser().getFullname()', '系统内置', '获取用户的某一个属性');
INSERT INTO "SYS_SCRIPT" VALUES ('20000000570001', '获取当前组织ID', 'import com.dstz.sys.util.ContextUtil;
return ContextUtil.getCurrentGroupId();', '系统内置', '通过引入静态方法来调用系统脚本，这样不需要实现Iscript脚本 。');
-- 数据源
INSERT INTO "sys_data_source_def" VALUES ('1', 'DruidDataSource数据源', 'com.alibaba.druid.pool.DruidDataSource', '[{"comment":"url","name":"url","required":true,"type":"java.lang.String"},{"comment":"username","name":"username","required":true,"type":"java.lang.String"},{"comment":"password","name":"password","required":true,"type":"java.lang.String"},{"comment":"initialSize","defaultValue":"1","name":"initialSize","required":true,"type":"int"},{"comment":"minIdle","defaultValue":"10","name":"minIdle","required":true,"type":"int"},{"comment":"maxActive","defaultValue":"10","name":"maxActive","required":true,"type":"int"},{"comment":"maxWait","defaultValue":"6000","name":"maxWait","required":true,"type":"long"},{"comment":"timeBetweenEvictionRunsMillis","defaultValue":"6000","name":"timeBetweenEvictionRunsMillis","required":true,"type":"long"},{"comment":"minEvictableIdleTimeMillis","defaultValue":"30000","name":"minEvictableIdleTimeMillis","required":true,"type":"long"},{"comment":"连接失败后是否不再尝试","defaultValue":"true","name":"breakAfterAcquireFailure","required":true,"type":"boolean"},{"comment":"校验sql","defaultValue":"","name":"validationQuery","required":false,"type":"java.lang.String"},{"comment":"testWhileIdle","defaultValue":"true","name":"testWhileIdle","required":true,"type":"boolean"},{"comment":"testOnBorrow","defaultValue":"false","name":"testOnBorrow","required":true,"type":"boolean"},{"comment":"testOnReturn","defaultValue":"false","name":"testOnReturn","required":true,"type":"boolean"},{"comment":"poolPreparedStatements","defaultValue":"true","name":"poolPreparedStatements","required":true,"type":"boolean"},{"comment":"maxPoolPreparedStatementPerConnectionSize","defaultValue":"20","name":"maxPoolPreparedStatementPerConnectionSize","required":true,"type":"int"},{"comment":"filters","defaultValue":"stat","name":"filters","required":true,"type":"java.lang.String"},{"comment":"removeAbandoned","defaultValue":"true","name":"removeAbandoned","required":true,"type":"boolean"},{"comment":"removeAbandonedTimeout","defaultValue":"2880","name":"removeAbandonedTimeout","required":true,"type":"int"},{"comment":"logAbandoned","defaultValue":"true","name":"logAbandoned","required":true,"type":"boolean"},{"comment":"enable","name":"enable","required":false,"type":"boolean"},{"comment":"logDifferentThread","name":"logDifferentThread","required":false,"type":"boolean"},{"comment":"useGlobalDataSourceStat","name":"useGlobalDataSourceStat","required":false,"type":"boolean"},{"comment":"reStatEnable","name":"reStatEnable","required":false,"type":"boolean"},{"comment":"name","name":"name","required":false,"type":"java.lang.String"},{"comment":"logWriter","name":"logWriter","required":false,"type":"java.io.PrintWriter"},{"comment":"loginTimeout","name":"loginTimeout","required":false,"type":"int"},{"comment":"maxIdle","name":"maxIdle","required":false,"type":"int"},{"comment":"failFast","name":"failFast","required":false,"type":"boolean"},{"comment":"dbType","name":"dbType","required":false,"type":"java.lang.String"},{"comment":"queryTimeout","name":"queryTimeout","required":false,"type":"int"},{"comment":"proxyFilters","name":"proxyFilters","required":false,"type":"java.util.List"},{"comment":"oracle","name":"oracle","required":false,"type":"boolean"},{"comment":"useUnfairLock","name":"useUnfairLock","required":false,"type":"boolean"},{"comment":"timeBetweenLogStatsMillis","name":"timeBetweenLogStatsMillis","required":false,"type":"long"},{"comment":"clearFiltersEnable","name":"clearFiltersEnable","required":false,"type":"boolean"},{"comment":"notFullTimeoutRetryCount","name":"notFullTimeoutRetryCount","required":false,"type":"int"},{"comment":"maxWaitThreadCount","name":"maxWaitThreadCount","required":false,"type":"int"},{"comment":"phyTimeoutMillis","name":"phyTimeoutMillis","required":false,"type":"long"},{"comment":"maxEvictableIdleTimeMillis","name":"maxEvictableIdleTimeMillis","required":false,"type":"long"},{"comment":"driverClassName","name":"driverClassName","required":false,"type":"java.lang.String"},{"comment":"transactionQueryTimeout","name":"transactionQueryTimeout","required":false,"type":"int"},{"comment":"exceptionSorterClassName","name":"exceptionSorterClassName","required":false,"type":"java.lang.String"},{"comment":"defaultAutoCommit","name":"defaultAutoCommit","required":false,"type":"boolean"},{"comment":"defaultReadOnly","name":"defaultReadOnly","required":false,"type":"java.lang.Boolean"},{"comment":"defaultTransactionIsolation","name":"defaultTransactionIsolation","required":false,"type":"java.lang.Integer"},{"comment":"statLoggerClassName","name":"statLoggerClassName","required":false,"type":"java.lang.String"},{"comment":"connectionProperties","name":"connectionProperties","required":false,"type":"java.lang.String"},{"comment":"transactionThresholdMillis","name":"transactionThresholdMillis","required":false,"type":"long"},{"comment":"useOracleImplicitCache","name":"useOracleImplicitCache","required":false,"type":"boolean"},{"comment":"useLocalSessionState","name":"useLocalSessionState","required":false,"type":"boolean"},{"comment":"dupCloseLogEnable","name":"dupCloseLogEnable","required":false,"type":"boolean"},{"comment":"connectionErrorRetryAttempts","name":"connectionErrorRetryAttempts","required":false,"type":"int"},{"comment":"sharePreparedStatements","name":"sharePreparedStatements","required":false,"type":"boolean"},{"comment":"timeBetweenConnectErrorMillis","name":"timeBetweenConnectErrorMillis","required":false,"type":"long"},{"comment":"maxOpenPreparedStatements","name":"maxOpenPreparedStatements","required":false,"type":"int"},{"comment":"removeAbandonedTimeoutMillis","name":"removeAbandonedTimeoutMillis","required":false,"type":"long"},{"comment":"validationQueryTimeout","name":"validationQueryTimeout","required":false,"type":"int"},{"comment":"defaultCatalog","name":"defaultCatalog","required":false,"type":"java.lang.String"},{"comment":"passwordCallbackClassName","name":"passwordCallbackClassName","required":false,"type":"java.lang.String"},{"comment":"exceptionSorter","name":"exceptionSorter","required":false,"type":"java.lang.String"},{"comment":"asyncCloseConnectionEnable","name":"asyncCloseConnectionEnable","required":false,"type":"boolean"},{"comment":"maxCreateTaskCount","name":"maxCreateTaskCount","required":false,"type":"int"},{"comment":"validConnectionCheckerClassName","name":"validConnectionCheckerClassName","required":false,"type":"java.lang.String"},{"comment":"accessToUnderlyingConnectionAllowed","name":"accessToUnderlyingConnectionAllowed","required":false,"type":"boolean"},{"comment":"numTestsPerEvictionRun","name":"numTestsPerEvictionRun","required":false,"type":"int"}]');
INSERT INTO sys_data_source (id_, key_, name_, desc_, db_type_, class_path_, attributes_json_) VALUES ('1', 'dataSourceDefault', '本地数据源', '本地数据源', 'mysql', NULL, NULL);

-- 流水号
INSERT INTO sys_serialno (id_, name_, alias_, regulation_, gen_type_, no_length_, cur_date_, init_value_, cur_value_, step_) VALUES ('10000001620002', '每天使用一组流水号', 'dayNo', '{yyyy}{MM}{DD}{NO}', '1', '5', '20180710', '1', '1', '1');

-- 子系统
INSERT INTO sys_subsystem (ID_, name_, alias_, logo_, enabled_, home_url_, base_url_, tenant_, MEMO_, creator_Id_, creator_, create_time_, is_default_) VALUES ('1', '业务流程平台', 'agilebpm', '', '1', '', 'index', NULL, NULL, '1', '系统管理员', NULL, '1');


-- 面板授权
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000054820143', 'WORKBENCH', '10000053631203', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-09 16:10:47', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000054820144', 'WORKBENCH', '10000053631202', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-09 16:10:58', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000054820146', 'WORKBENCH', '10000051360122', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-09 16:11:35', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000054820147', 'WORKBENCH', '10000049030124', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-09 16:11:41', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000054820148', 'WORKBENCH', '10000047970167', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-09 16:11:47', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000055210122', 'WORKBENCH', '10000053631205', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-09 17:30:50', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('10000055980295', 'WORKBENCH', '10000053631201', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-04-10 14:40:54', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('20000000120012', 'FLOW', '10000000000141', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-07-18 16:26:00', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('20000000120013', 'FLOW', '10000000000125', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-07-18 16:26:07', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('20000000540057', 'FLOW', 'demo', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-08-16 20:28:45', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('20000000540058', 'FLOW', 'schoolStudent', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-08-16 20:28:51', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('20000000570008', 'WORKBENCH', '20000000570002', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-08-17 15:14:15', 'YYYY-MM-DD HH24:MI:SS:'), '1');
INSERT INTO "SYS_AUTHORIZATION" VALUES ('403960673230389249', 'FLOW', 'orderCustomer', 'all', 'user', '所有人', 'user-all', TO_TIMESTAMP(' 2018-10-31 20:07:56', 'YYYY-MM-DD HH24:MI:SS:'), '1');

-- 默认布局
INSERT INTO "SYS_WORKBENCH_LAYOUT" VALUES ('403344394360455169', '10000049030124', '43', '450', '0', 'default_layout', TO_TIMESTAMP(' 2018-10-04 15:05:58', 'YYYY-MM-DD HH24:MI:SS:'));
INSERT INTO "SYS_WORKBENCH_LAYOUT" VALUES ('403344394366222337', '10000051360122', '50', '450', '1', 'default_layout', TO_TIMESTAMP(' 2018-10-04 15:05:58', 'YYYY-MM-DD HH24:MI:SS:'));
INSERT INTO "SYS_WORKBENCH_LAYOUT" VALUES ('403344394371465217', '10000047970167', '94', '504', '2', 'default_layout', TO_TIMESTAMP(' 2018-10-04 15:05:58', 'YYYY-MM-DD HH24:MI:SS:'));

-- 工作台案例
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000047970167', 'myCalendar', '我的日程', 'iframe', '我的日程', 'iframe', '/sys/schedule/scheduleDisplay.html', null, '75', '504', empty_clob(), '/sys/schedule/scheduleDisplay.html', TO_TIMESTAMP(' 2018-03-29 16:37:03', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-03-29 17:33:10', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000049030124', 'list', '我的待办', 'basic', '测试', 'interface', 'bpmTaskManager.getTodoList', null, '75', '300', '<div class="row">
	<div class="col-sm-7 m-b-xs">
		<div data-toggle="buttons" class="btn-group">
			<label class="btn btn-sm btn-default active" ng-click="loadPanelData()"> <input type="radio" ng-model="param.type$V" name="asdf" value="1">待办</label>
			<label class="btn btn-sm btn-default" ng-click="loadPanelData()"> <input type="radio" ng-model="param.type$V" name="asdf"  value="done">已办</label>
		</div>
	</div>
	<div class="col-sm-5">
		<div class="form-inline">
			<input type="text" placeholder="搜索关键字" class="input-sm form-control" ng-model="param.subject$V" > 
			<button type="button" ng-click="loadPanelData()" class="btn btn-sm btn-primary fa-search"></button>
		</div>
	</div>
</div>
 <div class="table-responsive">
	<table class="table table-striped">
		<thead>
			<tr>
				<th>任务名称</th>
				<th>任务标题</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="task in list">
				<td>{{task.name}}</td>
				<td><a ng-click="debugger;openFullWindow(''/bpm/task/taskComplete.html?taskId=''+this.task.id)" >{{task.subject}}</a> </td>
				<td>{{task.createTime}}</td>
			</tr>
		</tbody>
	</table>
</div>', '/bpm/my/todoTaskList.html', TO_TIMESTAMP(' 2018-03-30 18:05:43', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-04-02 17:18:17', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000051360122', 'productSales', '产品销售情况', 'echarts', '产品年度销售情况', 'interface', 'workbenchPanelManager.getTestData', null, '50', '300', '{
	"title": {
		"text": "产品销售情况"
	},
	"legend": {},
	"tooltip": {},
	"toolbox": {
		"show": true,
		"feature": {
			"magicType": {
				"show": true,
				"type": ["line", "bar", "stack", "tiled"]
			},
			"restore": {
				"show": true
			},
			"saveAsImage": {
				"show": true
			}
		}
	},
	"dataset": {
		"source": []
	},
	"xAxis": {
		"type": "category"
	},
	"yAxis": {},
	"series": []
}', null, TO_TIMESTAMP(' 2018-04-04 10:38:05', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-04-04 10:38:05', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000052180122', 'getPieData', '销售份额', 'echarts', null, 'interface', 'workbenchPanelManager.getPieData', null, '75', '500', '{
	"title": {
		"text": "产品销售情况"
	},
	"legend": {},
	"tooltip": {},
	"dataset": {
		"source": []
	},
	"series": [{
		"type": "pie"
	}]
}', null, TO_TIMESTAMP(' 2018-04-04 11:45:02', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-04-04 14:09:13', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000053631201', 'userInfo', '用户信息', 'basic', '用户信息展示', 'interface', 'loginContext.getCurrentUser', '60', '25', '150', '<h4 class="no-margins">{{userInfo.fullname}}</h4>
<div class=" pull-right  text-info">{{userInfo.account}} <i class="fa-user-circle-o"></i>
</div>
 <small>{{userInfo.email}}</small>', '/org/user/userInfoEdit', TO_TIMESTAMP(' 2018-03-08 09:50:19', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-03-29 16:08:54', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000053631202', 'roleInfo', '角色信息', 'basic', '用户信息展示', 'interface', 'loginContext.getCurrentUser', '60', '25', '150', '<p class="text-info">{{roleInfo.email}}</p>
<p class="text-info">{{roleInfo.weixin}}</p>
<p class="text-info">{{roleInfo.mobile}}</p>', '1', TO_TIMESTAMP(' 2018-03-12 11:26:16', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-03-29 16:20:53', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('10000053631203', 'org', '当前组织', 'basic', '用户信息展示', 'interface', 'loginContext.getCurrentGroup', null, '25', '150', '<h4 class="no-margins">{{org.name}}</h4>
<h2 style="color:red">系统每天凌晨重置数据库</h2>

<div class=" pull-right  text-info">{{org.code}} <i class="fa fa-address-card"></i>
</div>', null, TO_TIMESTAMP(' 2018-03-12 11:26:21', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-03-29 16:13:54', 'YYYY-MM-DD HH24:MI:SS:'), null, null);
INSERT INTO "SYS_WORKBENCH_PANEL" VALUES ('20000000570002', 'cacheDesign', '提前缓存设计器', 'iframe', null, 'iframe', '/flow-editor/modeler.html?modelId=20000000530051', null, '25', '20', empty_clob(), null, TO_TIMESTAMP(' 2018-08-17 15:06:33', 'YYYY-MM-DD HH24:MI:SS:'), null, TO_TIMESTAMP(' 2018-08-17 15:06:33', 'YYYY-MM-DD HH24:MI:SS:'), null, null);



-- 面板模板
INSERT INTO "SYS_WORKBENCH_PANEL_TEMPL" VALUES ('4', 'basic', '列表模板', '列表数据的模板案例，支持检索', '<div class="row">
	<div class="col-sm-7 m-b-xs">
		<div data-toggle="buttons" class="btn-group">
			<label class="btn btn-sm btn-default active"> <input type="radio" name="param.Q_user_SL" value="1">待办</label>
			<label class="btn btn-sm btn-default"> <input type="radio" name="param.Q_user_SL" value="2">已办</label>
		</div>
	</div>
	<div class="col-sm-5">
		<div class="input-group">
			<input type="text" placeholder="搜索订单关键字" class="input-sm form-control" ng-model="param.subject_" > 
			<span class="input-group-btn"> <button type="button" ng-click="loadPanelData()" class="btn btn-sm btn-primary">搜索</button> </span>
		</div>
	</div>
</div>
 <div class="table-responsive">
	<table class="table table-striped">
		<thead>
			<tr>
				<th>订单编号</th>
				<th>待办事项</th>
				<th>业务品种</th>
				<th>客户姓名</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="task in list">
				<td>{{task.apply_no}}</td>
				<td><a ng-click="openFullWindow(''/flow/task/taskApprove?id=''+this.task.ID_)" >{{task.NAME_}}</a> </td>
				<td>{{task.PROC_DEF_NAME_}}</td>
				<td>{{task.seller_name}}</td>
				<td>{{task.create_time}} </td>
			</tr>
		</tbody>
	</table>
</div>');
INSERT INTO "SYS_WORKBENCH_PANEL_TEMPL" VALUES ('5', 'echarts', '饼图模板', '饼状图案例模板', '{
	"title": {
		"text": "产品销售情况"
	},
	"legend": {},
	"tooltip": {},
	"dataset": {
		"source": []
	},
	"series": [{
		"type": "pie"
	}]
}');
INSERT INTO "SYS_WORKBENCH_PANEL_TEMPL" VALUES ('1', 'basic', '基础模板', '适用基本信息展示，或者列表数据', '<ul class="list-group">
  <li class="list-group-item">{{data-alias.account}}</li>
  <li class="list-group-item">{{data-alias.fullname}}</li>
  <li class="list-group-item">{{data-alias.mobile}}</li>
  <li class="list-group-item">{{data-alias.orgName}}</li>
  <li class="list-group-item">{{data-alias.roleNames}}</li>
</ul>');
INSERT INTO "SYS_WORKBENCH_PANEL_TEMPL" VALUES ('3', 'echarts', '柱状图折线图模板', null, '{
	"title": {
		"text": "产品销售情况"
	},
	"legend": {},
	"tooltip": {},
	"toolbox": {
		"show": true,
		"feature": {
			"magicType": {
				"show": true,
				"type": ["line", "bar", "stack", "tiled"]
			},
			"restore": {
				"show": true
			},
			"saveAsImage": {
				"show": true
			}
		}
	},
	"dataset": {
		"source": []
	},
	"xAxis": {
		"type": "category"
	},
	"yAxis": {},
	"series": []
}');

-- 系统菜单
INSERT INTO "SYS_RESOURCE" VALUES ('1', '1', 'personOffice', '个人办公', null, '1', '1', '1', 'slideshare', '0', '10', '0', null);
INSERT INTO "SYS_RESOURCE" VALUES ('1000000071000', '1', '1taskList', '任务列表', 'bpm/task/taskList.html', '1', '1', '1', null, '0', '1479297866587', '10000000710005', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000000710005', '1', 'newFlow', '流程管理', null, '1', '1', '1', null, '0', '33', '0', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000000710006', '1', 'list', '流程列表', 'bpm/definition/definitionList.html', '1', '1', '1', null, '0', '1479297866587', '10000000710005', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000001480006', '1', 'sysres', '资源管理', 'sys/sysResource/sysResourceList.html', '1', '1', '1', null, '0', '1', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000001640004', '1', 'formCustDialogList', '自定义对话框', 'form/formCustDialog/formCustDialogList.html', '1', '1', '1', null, '0', '1491406695020', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000001640007', '1', 'combinDialog', '组合对话框', '/form/combinateDialog/combinateDialogList', '0', '1', '1', null, '0', '1491406798665', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000034500201', '1', 'gjjjr', '国家节假日', 'sys/holidayConf/holidayConfList.html', '1', '1', '1', null, '0', '1516274778478', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000045331201', '1', 'rcb', '我的日程', 'sys/schedule/scheduleDisplay.html', '1', '1', '1', null, '0', '1517382648293', '1', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000047101201', '1', 'rcgl', '日程管理', 'sys/schedule/scheduleList.html', '1', '1', '1', null, '0', '1517540203764', '1', null);
INSERT INTO "SYS_RESOURCE" VALUES ('10000052971201', '1', 'gztgl', '工作台管理', 'sys/workbenchPanel/workbenchPanelList.html', '1', '0', '1', null, '0', '1519800765966', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('11', '1', 'myDraft', '我的草稿', 'bpm/my/draftList.html', '1', '1', '1', null, '0', '2', '7', null);
INSERT INTO "SYS_RESOURCE" VALUES ('18', '1', 'orgManager', '用户组织', null, '1', '1', '1', 'users', '0', '20', '0', null);
INSERT INTO "SYS_RESOURCE" VALUES ('19', '1', 'userManager', '用户管理', 'org/user/userList.html', '1', '1', '1', null, '0', '1', '18', null);
INSERT INTO "SYS_RESOURCE" VALUES ('2', '1', 'flowEvent', '事项办理', null, '1', '1', '1', null, '0', '0', '1', null);
INSERT INTO "SYS_RESOURCE" VALUES ('20', '1', 'orgMgr', '组织管理', 'org/group/groupList.html', '1', '1', '1', null, '0', '3', '18', null);
INSERT INTO "SYS_RESOURCE" VALUES ('20000001570004', '1', 'sysDataSourceDefList', '系统数据源模板', 'sys/sysDataSourceDef/sysDataSourceDefList.html', '1', '1', '1', null, '0', '10', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('20000002880001', '1', 'sysTreeList', '系统树', 'sys/sysTree/sysTreeList.html', '1', '1', '1', null, '0', '1521442292317', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('20000002980001', '1', 'copeTask', '我的抄送', null, '0', '1', '1', null, '0', '3', '2', null);
INSERT INTO "SYS_RESOURCE" VALUES ('20000003070153', '1', 'processInstanceList', '流程实例', 'bpm/instance/instanceList.html', '1', '1', '1', null, '0', '1521363666263', '10000000710005', null);
INSERT INTO "SYS_RESOURCE" VALUES ('20000010520001', '1', 'myTodo', '待办事项', 'bpm/my/todoTaskList.html', '1', '1', '1', null, '0', '1', '2', null);
INSERT INTO "SYS_RESOURCE" VALUES ('21', '1', 'roleMgr', '角色管理', 'org/role/roleList.html', '1', '1', '1', null, '0', '5', '18', null);
INSERT INTO "SYS_RESOURCE" VALUES ('22', '1', 'jobManager', '职务管理', 'org/groupRelDef/groupRelDefList.html', '1', '1', '1', null, '0', '7', '18', null);
INSERT INTO "SYS_RESOURCE" VALUES ('23', '1', 'flowManager', '表单管理', null, '1', '1', '1', null, '0', '31', '0', null);
INSERT INTO "SYS_RESOURCE" VALUES ('25', '1', 'boManager', '业务对象', null, '1', '1', '1', 'fa-database', '0', '0', '23', null);
INSERT INTO "SYS_RESOURCE" VALUES ('26', '1', 'businessTableList', '业务实体', 'bus/businessTable/businessTableList.html', '1', '1', '1', null, '0', '0', '25', null);
INSERT INTO "SYS_RESOURCE" VALUES ('27', '1', 'businessObjectList', '业务对象', 'bus/buinessObject/businessObjectList.html', '1', '1', '1', null, '0', '0', '25', null);
INSERT INTO "SYS_RESOURCE" VALUES ('28', '1', 'formDefManager', '表单定义', null, '1', '1', '1', 'fa-th-list', '0', '0', '23', null);
INSERT INTO "SYS_RESOURCE" VALUES ('30', '1', 'boForm', '业务表单', 'form/formDef/formDefList.html', '1', '1', '1', null, '0', '0', '28', null);
INSERT INTO "SYS_RESOURCE" VALUES ('31', '1', 'mobileForm', '手机表单', 'form/formDef/mobileFormDefList.html', '1', '1', '1', null, '0', '0', '28', null);
INSERT INTO "SYS_RESOURCE" VALUES ('32', '1', 'formTemplate', '表单模版', 'form/formTemplate/formTemplateList.html', '1', '1', '1', null, '0', '0', '28', null);
INSERT INTO "SYS_RESOURCE" VALUES ('33', '1', 'flowDefManager', '流程管理', null, '0', '1', '1', 'fa-slideshare', '0', '0', '23', null);
INSERT INTO "SYS_RESOURCE" VALUES ('4', '1', 'myHandledEvent', '办理历史', 'bpm/my/approveList.html', '1', '1', '1', null, '0', '2', '2', null);
INSERT INTO "SYS_RESOURCE" VALUES ('403205519290925057', '1', 'errLog', '异常日志', 'sys/sysLogErr/sysLogErrList.html', '1', '1', '1', null, '0', '1538106991915', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('43', '1', 'sysSetting', '系统配置', null, '1', '1', '1', 'cogs', '0', '40', '0', null);
INSERT INTO "SYS_RESOURCE" VALUES ('44', '1', 'systemMgr', '系统设置', null, '1', '1', '1', null, '0', '0', '43', null);
INSERT INTO "SYS_RESOURCE" VALUES ('46', '1', 'dicManager', '数据字典', 'sys/dataDict/dataDictList.html', '1', '1', '1', null, '0', '2', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('49', '1', 'syspropertyMgr', '系统属性管理', 'sys/sysProperties/sysPropertiesList.html', '1', '1', '1', null, '0', '5', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('50', '1', 'sysDataSourceList', '系统数据源', 'sys/sysDataSource/sysDataSourceList.html', '1', '1', '1', null, '0', '6', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('54', '1', 'subSystemMgr', '子系统管理', '/base/base/subsystem/subsystemList', '0', '1', '1', null, '0', '1473151574699', '44', null);
INSERT INTO "SYS_RESOURCE" VALUES ('56', '1', 'flowAssist', '开发辅助', null, '1', '1', '1', null, '0', '1472910579075', '43', null);
INSERT INTO "SYS_RESOURCE" VALUES ('59', '1', 'personScriptMgr', '人员脚本', '/system/conditionScript/userScriptList', '0', '0', '1', null, '0', '8', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('60', '1', 'serialNoMgr', '流水号', 'sys/serialNo/serialNoList.html', '1', '1', '1', null, '0', '9', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('61', '1', 'scriptMgr', '常用脚本', 'sys/script/scriptList.html', '1', '1', '1', null, '0', '16', '56', null);
INSERT INTO "SYS_RESOURCE" VALUES ('7', '1', 'myStartEvent', '事项申请', null, '1', '1', '1', null, '0', '0', '1', null);
INSERT INTO "SYS_RESOURCE" VALUES ('8', '1', 'myStartFlow', '发起申请', 'bpm/my/definitionList.html', '1', '1', '1', null, '0', '1', '7', null);
INSERT INTO "SYS_RESOURCE" VALUES ('9', '1', 'myRequest', '申请历史', 'bpm/my/applyTaskList.html', '1', '1', '1', null, '0', '2', '7', null);

