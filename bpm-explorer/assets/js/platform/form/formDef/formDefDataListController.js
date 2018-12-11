var app = angular.module('app', [ 'baseDirective' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayTool, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayTool;

	$scope.init = function() {

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
		girdConf.url = __ctx + "/form/formDefData/getList_" + $scope.data.key;
		girdConf.pagination = true;
		girdConf.pageSize = 10;
		girdConf.singleSelect = true;

		// 列 默认一个选择列
		girdConf.columns = [ {
			checkbox : true
		} ];
		var table = $scope.data.relation.table;
		// 设置显示列
		angular.forEach(table.columnsWithOutHidden, function(item) {
			var column = {
				field : item.name,
				title : item.comment,
				sortable : true,
			};
			if (girdConf.columns.length < 7) {// 不展示太多字段
				girdConf.columns.push(column);
			}
		});

		var formatter = function(value, row, index) {
			var html = "<span class='btn btn-outline btn-primary fa-edit' qtip='编辑' onclick='edit(\"" + row[table.pkName] + "\")'></span>";
			html += "<span class='btn btn-outline btn-primary fa-trash' qtip='删除' onclick='remove(\"" + row[table.pkName] + "\")'></span>";
			return html;
		}
		// 操作列

		girdConf.columns.push({
			title : "操作",
			formatter : formatter
		});

		girdConf.onLoadSuccess = function(data) {
			if (!data.isOk) {
				jQuery.Toast.error(data.msg, data.cause);
			}
		};

		girdConf.onLoadError = function(status) {
			jQuery.Toast.error("加载失败，代码:" + status);
		};

		return girdConf;
	};

} ]);