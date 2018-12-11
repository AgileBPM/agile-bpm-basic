var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		$scope.data = angular.copy(window.passData);
		if($scope.data.dataSource != 'interface'){
			// 获取objName的对象信息
			var url = __ctx + "/form/formCustDialog/getTable";
			var defer = baseService.post(url, $scope.data);
			$.getResultData(defer, function(data) {
				$scope.table = data;
			});
		}else{
			$scope.addTableColom();
		}
		

		ToolsController.getEnum("com.dstz.base.api.query.QueryOP").then(function(data) {
			$scope.$apply(function() {
				$scope.QueryOP = data;
			});
		});

		ToolsController.getEnum("com.dstz.form.api.constant.FormCustDialogConditionFieldValueSource").then(function(data) {
			$scope.$apply(function() {
				$scope.FormCustDialogConditionFieldVALUESOURCE = data;
			});
		});
		
		ToolsController.getEnum("com.dstz.base.api.constant.Direction").then(function(data) {
			$scope.$apply(function() {
				$scope.Direction = data;
			});
		});
	};

	$scope.addColumns = function() {
		// 显示列
		if ($('.tab-pane.active').attr('id') === 'showCol') {
			angular.forEach($scope.table.columns, function(item) {
				if (!item.selected || !item.name ) {
					return;
				}
				if ($scope.data.style === "list") {
					$scope.data.displayFields.push({
						columnName : item.name,
						showName : item.comment
					});
				}

			});
		}

		// 条件列
		if ($('.tab-pane.active').attr('id') === 'condition') {
			angular.forEach($scope.table.columns, function(item) {
				if (!item.selected || !item.name) {
					return;
				}
				$scope.data.conditionFields.push({
					columnName : item.name,
					showName : item.comment,
					dbType : item.type,
					condition : $scope.QueryOP.EQUAL.val,
					valueSource : $scope.FormCustDialogConditionFieldVALUESOURCE.FIXED_VALUE.key,
					value : {}
				});
			});
		}
		
		// 返回列
		if ($('.tab-pane.active').attr('id') === 'result') {
			angular.forEach($scope.table.columns, function(item) {
				if (!item.selected || !item.name) {
					return;
				}
				$scope.data.returnFields.push({
					columnName : item.name,
					returnName : item.name
				});
			});
		}
		
		// 排序列
		if ($('.tab-pane.active').attr('id') === 'sort') {
			angular.forEach($scope.table.columns, function(item) {
				if (!item.selected || !item.name) {
					return;
				}
				$scope.data.sortFields.push({
					columnName : item.name,
					sortType : $scope.Direction.ASC.key
				});
			});
		}
	};

	/**
	 * 获取这个字段支持的条件类型
	 */
	$scope.getSupportCondition = function(field) {
		var list = [];
		angular.forEach($scope.QueryOP, function(val, key) {
			if ($.inArray(field.dbType, val.supports) >= 0) {
				list.push(val);
			}
		});
		return list;
	};

	/**
	 * 获取这个字段支持的来源类型
	 */
	$scope.getSupportValueSource = function() {
		var list = [];
		angular.forEach($scope.FormCustDialogConditionFieldVALUESOURCE, function(val, key) {
			if ($.inArray($scope.data.style, val.supports) >= 0) {
				list.push(val);
			}
		});
		return list;
	};
	
	//打开常用脚本
    $scope.openCommonScript = function(field){
    	alert("暂时不支持-。-");
    };
    
    $scope.addTableColom = function(){
    	if(!$scope.table) {$scope.table = {columns:[]}};
    	$scope.table.columns.push({type:"varchar"});
    	$scope.table.columns.push({type:"varchar"});
    	$scope.table.columns.push({type:"varchar"});
    }
    
    
} ]);
