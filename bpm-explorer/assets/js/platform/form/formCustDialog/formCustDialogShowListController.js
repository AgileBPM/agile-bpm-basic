var app = angular.module('app', [ 'baseDirective' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayTool, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {
		// 选择中的数据
		$scope.selectedList = [];
		$scope.p = [];
		$scope.rows = [];
	};

	/**
	 * 根据字段生成其ID
	 */
	$scope.getId = function(field, end) {
		var id = field.columnName + "^";
		if (end) {
			id = field.columnName + "-end^";
		}
		if (field.dbType === "varchar") {
			id += "V";
		}
		if (field.dbType === "number") {
			id += "N";
		}
		if (field.dbType === "date") {
			id += "D";
		}
		id += field.condition;
		return id;
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		$("[ab-grid]").bootstrapTable("refreshOptions", $scope.getGridConf());
	});

	/**
	 * 获取根据对话框影响的列表配置(girdConf)内容
	 */
	$scope.getGridConf = function() {
		// 打开对话框的传参
		var windowPassData = window.passData;
		// 修改被修改的对话框配置
		if (windowPassData && windowPassData.dialogSetting) {
			$scope.data = angular.extend($scope.data, windowPassData.dialogSetting);
		}

		var girdConf = {};
		girdConf.url = __ctx + "/form/formCustDialog/listData_" + $scope.data.key;
		girdConf.pagination = $scope.data.page;
		girdConf.pageSize = $scope.data.pageSize;
		girdConf.singleSelect = !$scope.data.multiple;

		// 处理页面来的动态参数
		girdConf.queryParams = function(params) {
			if (windowPassData && windowPassData.params) {
				params = angular.extend(params, windowPassData.params);
			}

			return $.getQueryParam(params);
		};

		// 列 默认一个选择列
		if($scope.data.multiple){
			girdConf.columns = [ {
				checkbox : true
			} ];
		}else{
			girdConf.columns = [ {
				radio : true
			} ];
		}
		
		// 设置显示列
		angular.forEach($scope.data.displayFields, function(item) {
			var column = {
				field : item.columnName,
				title : item.showName,
				sortable : true,
			};
			if (item.formatter) {
				eval("column.formatter = function(value, row, index){" + item.formatter + "}");
			}
			girdConf.columns.push(column);
		});

		// 选上事件
		girdConf.onCheck = function(row) {
			pushSelectedList(row);
		};
		
		// 取消选上事件
		girdConf.onUncheck = function(row) {
			var item = getItemInSelectedList(row);
			if (!item) {
				return;
			}
			if (noScopeApply) {
				noScopeApply = false;
				ArrayTool.remove(item, $scope.selectedList);
			} else {
				$scope.$apply(function() {
					ArrayTool.remove(item, $scope.selectedList);
				});
			}
		};
		
		//全选
		girdConf.onCheckAll = function(rows){
			angular.forEach(rows, function(row, index) {
				girdConf.onCheck(row);
			});
		};
		
		//反选
		girdConf.onUncheckAll = function(rows){
			angular.forEach(rows, function(row, index) {
				girdConf.onUncheck(row);
			});
		};
		
		girdConf.onLoadSuccess = function(data) {
			$scope.rows = data.rows;// 列表数据
			// 有初始化值
			if (windowPassData && windowPassData.initData) {
				angular.forEach(data.rows, function(row, index) {
					angular.forEach(windowPassData.initData, function(item) {
						// 处理返回字段的返回名映射为字段名
						var itemTemp = {};
						angular.forEach(item, function(val, key) {
							var isMatch = false;
							angular.forEach($scope.data.returnFields, function(field) {
								if (field.returnName === key) {
									itemTemp[field.columnName] = val;
									isMatch = true;
								}
							});
							if (!isMatch) {
								itemTemp[key] = val;
							}
						});

						if (jsonEqual(row, itemTemp)) {
							$("[ab-grid]").bootstrapTable("check", index);// 选中
						}
					});
				});
			}
		};

		girdConf.height = $scope.data.height - 210;

		return girdConf;
	};

	/**
	 * 返回结果数据
	 */
	$scope.getData = function() {
		var data = [];
		angular.forEach($scope.selectedList, function(item) {
			data.push(transformRow2Data(item));
		});
		return data;
	};

	/**
	 * 清空选择
	 */
	$scope.clear = function() {
		$scope.selectedList.splice(0, $scope.selectedList.length);// 清空数组
	};

	/**
	 * 是否显示搜索栏
	 */
	$scope.isShowSearch = function() {
		var show = false;
		if (!$scope.data) {
			return show;
		}
		angular.forEach($scope.data.conditionFields, function(field) {
			// 有参数，且有控件类型
			if (field.valueSource == "param" && field.value.ctrlType) {
				show = true;
			}
		});
		return show;
	};

	var noScopeApply;
	$scope.del = function(index) {
		var idx = null;
		angular.forEach($scope.rows, function(row, i) {
			if (jsonEqual(row, $scope.selectedList[index])) {
				idx = i;
			}
		});
		if (idx != null) {
			noScopeApply = true;
			$("[ab-grid]").bootstrapTable("uncheck", idx);// 反选
		} else {
			ArrayTool.del(index, $scope.selectedList);
		}
	};

	/**
	 * 把row转化为返回数据
	 */
	function transformRow2Data(row) {
		var data = {};
		angular.forEach($scope.data.returnFields, function(field) {
			data[field.returnName] = row[field.columnName];
		});
		return data;
	}

	/**
	 * 插入选中列表
	 */
	function pushSelectedList(row) {
		if (getItemInSelectedList(row))// 已存在
			return;
		$scope.$apply(function() {
			if (!$scope.data.multiple) {// 单选
				$scope.selectedList = [];
			}
			$scope.selectedList.push(row);
		});
	}

	/**
	 * 返回在已选列表中的对应项
	 */
	function getItemInSelectedList(row) {
		for (var i = 0; i < $scope.selectedList.length; i++) {
			var temp = $scope.selectedList[i];
			if (jsonEqual(row, temp)) {
				return temp;
			}
		}
		return null;
	}

	/**
	 * 判断两个json是否相等 ps：只比较只b中存在的字段
	 */
	function jsonEqual(a, b) {
		if (a === b) {
			return true;
		}
		var allEq = true;// 全相等
		var hasOneEq = false;// 有一个字段相等
		for ( var key in b) {
			if (a[key] && b[key] && b[key] !== a[key]) {
				allEq = false;
				break;
			}
			if (a[key] && b[key] && b[key] === a[key]) {
				hasOneEq = true;
			}
		}
		return allEq && hasOneEq;
	}
} ]);