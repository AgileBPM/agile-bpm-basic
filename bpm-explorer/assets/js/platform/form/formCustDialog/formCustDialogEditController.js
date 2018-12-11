var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
		$scope.data.width = 800;
		$scope.data.height = 600;
		$scope.data.page = true;
		$scope.data.pageSize = 10;
		$scope.data.multiple = false;
		$scope.data.system = false;
		$scope.data.dsKey = "dataSourceDefault";
		$scope.data.treeConfig = {};
		$scope.data.displayFields = [];
		$scope.data.conditionFields = [];
		$scope.data.returnFields = [];
		$scope.data.sortFields = [];

		ToolsController.getEnum("com.dstz.form.api.constant.FormCustDialogStyle").then(function(data) {
			$scope.$apply(function() {
				$scope.FormCustDialogSTYLE = data;
				if (!$scope.data.id) {
					$scope.data.style = data.LIST.key;
				}
			});
		});

		ToolsController.getEnum("com.dstz.form.api.constant.FormCustDialogObjType").then(function(data) {
			$scope.$apply(function() {
				$scope.FormCustDialogOBJTYPE = data;
				if (!$scope.data.id) {
					$scope.data.objType = data.TABLE.key;
				}
			});
		});

		// 拿出数据数据源模板
		baseService.postForm(__ctx + '/sys/sysDataSource/listJson', {
			page : 1,
			rows : 99
		}).then(function(result) {
			// 所有数据源模板
			$scope.AllSysDataSources = result.rows;
		});
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		delete $scope.data.treeConfigJson;
		delete $scope.data.displayFieldsJson;
		delete $scope.data.conditionFieldsJson;
		delete $scope.data.returnFieldsJson;
		delete $scope.data.sortFieldsJson;
	});
	
	$scope.$on("beforeSaveEvent", function(event, data) {
		angular.forEach($scope.AllSysDataSources, function(item) {
			if (item.key === $scope.data.dsKey) {
				$scope.data.dsName = item.name;
			}
		});
	});
	
	/**
	 * 查询objName
	 */
	$scope.searchObjName = function() {
		var url = __ctx + "/form/formCustDialog/searchObjName";
		var defer = baseService.post(url, $scope.data);
		$.getResultData(defer, function(data) {
			$scope.AllObjNames = data;
			angular.forEach(data, function(value, key) {
				$scope.data.objName = key;
			});
		});
	};

	/**
	 * 打开配置窗口
	 */
	$scope.openSettingDialog = function() {
		var title = $scope.data.name ? $scope.data.name : "未命名";
		var url = '/form/formCustDialog/formCustDialogSettingDialog.html';
		var conf = {
			height : 650,
			width : 1065,
			url : url,
			title : title,
			topOpen : true,
			passData : $scope.data,
			ok : function(index, innerWindow) {
				var data = innerWindow.getData();
				$scope.$apply(function() {
					angular.copy(data, $scope.data);
				});
				innerWindow.parent.layer.close(index);
			}
		};
		$.Dialog.open(conf);
	}
} ]);
