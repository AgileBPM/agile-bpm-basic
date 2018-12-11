var app = angular.module("app", [ 'base', 'baseDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	$scope.ArrayTool = ArrayToolService;
	$scope.CommonRule = CommonRule;
	var filter = $filter('filter');

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
		$scope.data.packageId = packageId;
		$scope.data.status = "enabled";
		$scope.data.isExternal = "0";
		$scope.data.dsName = "LOCAL";
		$scope.data.isCreateTable = "0";
		$scope.data.pk = PK_NAME;
		$scope.data.pkType = "varchar";
		$scope.data.attributeList = [];
		$scope.selectedAttrList = [];// 被选中的属性

		$scope.FieldControlType = FieldControlType;

		// 判断当前BO是否能编辑
		baseService.post("isEditabled?id=" + id).then(function(data) {
			$scope.isEditabled = data === "true";
		});
	};

	$scope.$on("beforeSaveEvent", function(event, data) {
		if ($scope.data.attributeList.length < 1) {
			$.topCall.error("实例至少需要一个字段");
			data.pass = false;
			return;
		}

		if ($scope.isEditabled) {// 出于任意修改状态就不要增加字段提醒
			return;
		}
		for (var i = 0; i < $scope.data.attributeList.length; i++) {
			var attr = $scope.data.attributeList[i];
			if (!attr.id) {// 如果有ID为空，说明是在表生成的情况下新增了字段，那么需要提示一下
				var rtn = window.confirm('检查到新字段(' + attr.desc + "),将在表结构中插入该字段?");
				if (!rtn) {
					data.pass = false;
				}
			}
		}
	});

	$scope.$on("afterSaveEvent", function(event, data) {
		if (!data.r) {
			HT.window.closeEdit(true);
		}
	});

	$scope.$on("afterLoadEvent", function(event, data) {
		// 加载属性列表
		var rtn = baseService.post("getAttrList?entId=" + data.id);
		rtn.then(function(data) {
			if (!data)
				return;
			$scope.data.attributeList = data;
			angular.forEach(data,function(item){
				item.ctrl.calculation = angular.fromJson(item.ctrl.calculation);
				item.ctrl.ctrlTypeOpt = angular.fromJson(item.ctrl.ctrlTypeOpt);
				item.ctrl.validRule = angular.fromJson(item.ctrl.validRule);
			});
		}, function() {
			$.topCall.error("请求失败");
		});
	});

	/**
	 * 获取外部数据源的表
	 */
	$scope.getExternalTable = function() {
		var rtn = baseService.postForm(__ctx + '/form/customQuery/getByDsObjectName', {
			dsalias : $scope.data.dsName,
			isTable : "1",
			objName : $scope.data.tableName
		});
		rtn.then(function(data) {
			$scope.externalTable = data;
		});
	};

	/**
	 * 添加一条新属性
	 */
	$scope.addAttr = function() {
		var temp = {};
		temp.desc = "";
		temp.isRequired = "0";
		temp.dataType = "varchar";
		temp.attrLength = "50";
		temp.isNew = true;
		temp.ctrl = {
			ctrlType : FieldControlType.ONETEXT.key,
			ctrlTypeOpt : {},
			calculation : {},
			validRule : []
		};
		$scope.data.attributeList.push(temp);
	};

	/**
	 * 修改数据类型。
	 */
	$scope.changeDbType = function(row) {
		var type = row.dataType;
		switch (type) {
		case "number":
			row.attrLength = 10;
			row.decimalLen = 0;
			break;
		case "varchar":
			row.attrLength = 50;
			break;
		case "date":
			row.format = "yyyy-MM-dd HH:mm:ss";
			break;
		default:
			row.attrLength = 0;
			row.decimalLen = 0;
			break;
		}
	};

	/**
	 * 获取支持的控件
	 */
	$scope.getCtrlTypeList = function(attr) {
		var list = [];
		angular.forEach(FieldControlType, function(item) {
			var l = filter(item.supports, attr.dataType, true);// 第三个参数：是否全文匹配校验，false（默认）:用like匹配
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
		if (filter(ctrl.validRule, rule, true).length > 0) {// 已存在
			return;
		}
		ctrl.validRule.push(rule);
	};

	/**
	 * 根据控件类型初始化默认的控件配置
	 */
	$scope.initCtrlTypeOpt = function(ctrl) {
		if (ctrl.ctrlType === "onetext"||ctrl.ctrlType === "customdialog") {// 单行文本框 或 自定义对话框
			ctrl.ctrlTypeOpt = {};// 无特殊属性
		} else if (ctrl.ctrlType === "multitext") {// 多行文本
			ctrl.ctrlTypeOpt = {
				isEditor : "0",// 是否富文本
				ewidth : "150",// 富文本的宽
				eheight : "500",// 富文本的高
			};
		} else if (ctrl.ctrlType === "select" || ctrl.ctrlType === "multiselect") {// 下拉框或多选下拉框
			ctrl.ctrlTypeOpt = {
				fromType : "static",// static:固定；dynamic:动态的
				options : [ {
					key : "",// 值
					txt : ""// 展示
				} ]
			};
		} else if (ctrl.ctrlType === "checkbox" || ctrl.ctrlType === "radio") {// 复选框或单选按钮
			ctrl.ctrlTypeOpt = {
				options : [ {
					key : "",// 值
					txt : ""// 展示
				} ]
			};
		} else if (ctrl.ctrlType === "date") {// 日期
			ctrl.ctrlTypeOpt = {
				greater : "",// 大于
				less : "",// 小于
			};
		} else if (ctrl.ctrlType === "dic"||ctrl.ctrlType === "identity") {// 数据字典 或 流水号
			ctrl.ctrlTypeOpt = {
				key : ""// 字典别名 或 流水号别名
			};
		}
	};
	
	/**
	 * 下拉框增加选项
	 */
	$scope.addOption = function(options) {
		var option = {
			key : "",
			txt : ""
		};
		options.push(option);
	};
} ]);