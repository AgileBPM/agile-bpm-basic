var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;
	$scope.FormRules = [];// 系统内置常用校验，只获取支持表单的
	$scope.temp = {};// 临时变量

	for ( var key in $.fn.rules) {
		var rule = angular.copy($.fn.rules[key]);
		if (!rule.formRule) {
			continue
		}
		// $.fn.rules 不含name 这里补上去
		rule.name = key;
		$scope.FormRules.push(rule);
	}

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
		$scope.data.dsKey = "dataSourceDefault";// 默认本地数据源
		$scope.data.external = true;
		$scope.data.createdTable = true;
		$scope.data.columns = [];// 字段

		// 拿出数据源
		baseService.postForm(__ctx + '/sys/sysDataSource/listJson', {
			page : 1,
			rows : 99
		}).then(function(result) {
			// 所有数据源
			$scope.AllSysDataSources = result.rows;
		});

		ToolsController.getEnum("com.dstz.bus.api.constant.BusColumnCtrlType").then(function(data) {
			$scope.FieldControlType = data;
		});
		ToolsController.getEnum("com.dstz.base.api.constant.ColumnType").then(function(data) {
			$scope.ColumnType = data;
		});
	};

	$scope.$on("beforeSaveEvent", function(event, data) {
		if ($scope.data.columns.length < 2) {
			jQuery.Toast.error("业务表需要至少一个非主键的字段");
			data.pass = false;
			return;
		}
		// 删除循环索引
		delete $scope.data.columnsWithoutPk;
		delete $scope.data.columnsWithOutHidden;
		delete $scope.data.pkColumn;
		angular.forEach($scope.data.columns, function(item) {
			delete item.table;
		});

		angular.forEach($scope.AllSysDataSources, function(item) {
			if (item.key === $scope.data.dsKey) {
				$scope.data.dsName = item.name;
			}
		});
	});

	$scope.$on("afterSaveEvent", function(event, data) {
		if (!data.r) {
			$.Dialog.close(window);
		} else {
			window.location.reload();
		}
	});

	$scope.$on("afterLoadEvent", function(event, data) {
		angular.forEach(data.columns, function(item) {
			if (!item.ctrl) {
				return;
			}
			item.ctrl.validRule = angular.fromJson(item.ctrl.validRule);
			item.ctrl.config = angular.fromJson(item.ctrl.config);
		});
	});

	/**
	 * 获取支持的控件
	 */
	$scope.getCtrlTypeList = function(column) {
		var list = [];
		angular.forEach($scope.FieldControlType, function(item) {
			var l = filter(item.supports, column.type, true);// 第三个参数：是否全文匹配校验，false（默认）:用like匹配
			if (l.length > 0) {// 该组件支持当前字段类型
				list.push(item);
			}
		});
		return list;
	};

	/**
	 * 字段增加一个校验
	 */
	$scope.addRule = function(ctrl) {
		var rule = {
			name : ctrl.newRule.name,
			title : ctrl.newRule.title
		};
		if (filter(ctrl.validRule, rule, true).length <= 0) {// 已存在
			ctrl.validRule.push(rule);
		}
		ctrl.newRule = null;
	};

	/**
	 * 根据控件类型初始化默认的控件配置
	 */
	$scope.initCtrlConfig = function(ctrl) {
		if (ctrl.type === "onetext" || ctrl.type === "customdialog") {// 单行文本框
			// 或
			// 自定义对话框
			ctrl.config = {};// 无特殊属性
		} else if (ctrl.type === "multitext") {// 多行文本
			ctrl.config = {
				isEditor : "0",// 是否富文本
				ewidth : "150",// 富文本的宽
				eheight : "500",// 富文本的高
			};
		} else if (ctrl.type === "select" || ctrl.type === "multiselect" || ctrl.type === "checkbox" || ctrl.type === "radio") {// 下拉框或多选下拉框或复选框或单选按钮
			ctrl.config = {
				options : [ {
					key : "",// 值
					txt : ""// 展示
				} ]
			};
		} else if (ctrl.type === "date") {// 日期
			ctrl.config = {
				greater : "",// 大于
				less : "",// 小于
				format : "yyyy-MM-dd HH:mm:ss"
			};
		} else if (ctrl.type === "dic" || ctrl.type === "identity") {// 数据字典
			// 或
			// 流水号
			ctrl.config = {
				key : ""// 字典别名 或 流水号别名
			};
		}
	};

	/**
	 * 打开字段控件配置对话框 ps:并不是所有控件类型都需要打开对话框的
	 */
	$scope.openColCtrlCfgDialog = function(column) {
		$.Dialog.open({
			url : "/bus/businessTable/columnCtrlConfDialog.html",
			width : 500,
			height : 300,
			title : "字段[" + column.comment + "]控件配置",
			passData : column,
			ok : function(index, innerWindow) {
				var c = innerWindow.getData();
				$scope.$apply(function() {
					angular.copy(c, column);
				});
				innerWindow.parent.layer.close(index);
			},
			topOpen : true
		});
	};

	/**
	 * 根据表名查询
	 */
	$scope.searchTableName = function() {
		var url = __ctx + "/form/formCustDialog/searchObjName";
		var defer = baseService.post(url, {
			dsKey : $scope.data.dsKey,
			objType : "table",
			objName : $scope.temp.tableName
		});
		$.getResultData(defer, function(data) {
			$scope.tableNames = data;
		});
	};

	/**
	 * 选择一个表
	 */
	$scope.selectTable = function(b) {
		// 获取objName的对象信息
		var url = __ctx + "/form/formCustDialog/getTable";
		var defer = baseService.post(url, {
			dsKey : $scope.data.dsKey,
			objType : "table",
			objName : $scope.data.name
		});
		$.getResultData(defer, function(data) {
			var pkNum = 0;
			angular.forEach(data.columns, function(c) {
				if (c.primary) {
					pkNum++;
				}
			});
			if (pkNum != 1) {
				jQuery.Toast.error("注意", "请保证外部表符合唯一主键的规则");
				return;
			}

			delete data.pkColumn;// 删除无用索引
			angular.forEach(data.columns, function(c) {
				var column = getColumn(c.name);
				if (!column) {
					column = c;
				} else {
					jQuery.extend(column, c);// 以库的字段为准
				}

				if (!column.comment) {
					column.comment = column.name;
				}
				if (!column.key) {
					column.key = column.name;
				}

				// 字段控件已存在
				if (column.ctrl || column.primary) {
					jQuery.extend(c, column);// 复用已配置好的字段属性
					return;
				}
				if (column.type == $scope.ColumnType.DATE.key) {
					column.ctrl = {
						type : $scope.FieldControlType.DATE.key,
						validRule : []
					};
				} else {
					column.ctrl = {
						type : $scope.FieldControlType.ONETEXT.key,
						validRule : []
					};
				}
				$scope.initCtrlConfig(column.ctrl);
				jQuery.extend(c, column);// 复用已配置好的字段属性
			});

			if (!$scope.data.key) {
				$scope.data.key = data.name;
			}

			jQuery.extend($scope.data, data);

			if (b) {
				$.Toast.success("同步成功，【保存】后生效");
			}
		});
	};

	function getColumn(name) {
		var c = null;
		angular.forEach($scope.data.columns, function(column) {
			if (column.name === name) {
				c = column;
			}
		});
		return c;
	}
} ]);
