var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};

		ToolsController.getEnum("com.dstz.base.db.api.table.DbType").then(function(data) {
			$scope.$apply(function() {
				$scope.DbType = data;
			});
		});

		// 拿出数据数据源模板
		baseService.postForm(__ctx + '/sys/sysDataSourceDef/listJson', {
			page : 1,
			rows : 99
		}).then(function(result) {
			// 所有数据源模板
			$scope.AllSysDataSourceDefs = result.rows;
		});
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		delete $scope.data.attributesJson;
	});

	/**
	 * 数据库类型改变事件
	 */
	$scope.dbTypeChange = function() {
		if (!$scope.data.dbType) {
			return;
		}
		var db;
		angular.forEach($scope.DbType, function(item) {
			if (item.key === $scope.data.dbType) {
				db = item;
			}
		});
		angular.forEach($scope.data.attributes, function(item) {
			if (item.name === "driverClassName") {
				item.value = db.driverClassName;
			} else if (item.name === "url") {
				item.value = db.url;
			}
		});
	};

	/**
	 * 数据源模板修改时间
	 */
	$scope.sysDataSourceDefChange = function() {
		var list = filter($scope.AllSysDataSourceDefs, $scope.data.classPath, true);
		if (list.length === 0) {
			return;
		}
		$scope.data.attributes = list[0].attributes;
		//设置默认值
		angular.forEach($scope.data.attributes, function(item) {
			if(item.defaultValue&&!item.value){
				item.value = item.defaultValue;
			}
		});
		$scope.dbTypeChange();// 触发更新数据库
	};

} ]);
