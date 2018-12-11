var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		editAndDialogCommon.init($scope, baseService);// 初始化公用js

		ToolsController.getEnum("com.dstz.bus.api.constant.BusTableRelType").then(function(data) {
			$scope.$apply(function() {
				$scope.BusTableRelType = data;
			});
		});

		ToolsController.getEnum("com.dstz.bus.api.constant.BusTableRelFkType").then(function(data) {
			$scope.$apply(function() {
				$scope.BusTableRelFkType = data;
			});
		});

		ToolsController.getEnum("com.dstz.bus.api.constant.BusinessObjectPersistenceType").then(function(data) {
			$scope.$apply(function() {
				$scope.BusinessObjectPersistenceType = data;
				if (!$scope.data.id) {
					$scope.data.persistenceType = data.DB.key;
				}
			});
		});

		$scope.data = {};
		$scope.data.relation = {};
	};
	
	$scope.$on("afterSaveEvent", function(event, data) {
		if (!data.r) {
			$.Dialog.close(window);
		}else{
			window.location.reload();
		}
	});

	$scope.$on("afterLoadEvent", function(event, data) {
		delete $scope.data.relationJson;
		$scope.addTableDetail($scope.data.relation.tableKey);
		angular.forEach($scope.data.relation.children, function(item) {
			$scope.addTableDetail(item.tableKey);
		});
	});

	/**
	 * 选中主table
	 */
	$scope.selectMainTable = function() {
		var initData = [ {
			key_ : $scope.data.relation.tableKey,
			comment_ : $scope.data.relation.tableComment
		} ];
		CustUtil.openCustDialog("ywblb", null, function(data) {
			if (data.length < 1) {
				return;
			}
			$scope.$apply(function() {
				$scope.addTableDetail(data[0].key);
				$scope.data.relation.tableKey = data[0].key;
				$scope.data.relation.type = $scope.BusTableRelType.MAIN.key;
				$scope.data.relation.tableComment = data[0].comment;
				// 选择主表后、将子表全部置为空数组
				$scope.data.relation.children = [];
			});
		}, initData, {
			multiple : false
		}, true);
	};
} ]);
