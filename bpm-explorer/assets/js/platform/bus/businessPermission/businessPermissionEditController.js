var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	$scope.ArrayTool = ArrayToolService;
	var filter = $filter('filter');

	$scope.init = function() {
		ToolsController.getEnum("com.dstz.bus.api.constant.BusinessPermissionObjType").then(function(data) {
			$scope.$apply(function() {
				$scope.BusinessPermissionObjType = data;
				angular.forEach(data, function(val, key) {
					if (val.key === $.getParameter("objType")) {
						$scope.objName = val.desc;// 不入库的
					}
				});
			});
		});

		ToolsController.getEnum("com.dstz.bus.api.constant.BusTableRelType").then(function(data) {
			$scope.$apply(function() {
				$scope.BusTableRelType = data;
			});
			$scope.BusTableRelTypeKeyMap = {};
			angular.forEach(data, function(val) {
				$scope.BusTableRelTypeKeyMap[val.key] = val;
			});
		});

		ToolsController.getEnum("com.dstz.bus.api.constant.RightsType", true).then(function(data) {
			$scope.$apply(function() {
				$scope.RightsTypeList = data;
				ArrayToolService.del(data.length - 1, data);
			});
		});

		$scope.data = {};
		$scope.data.objType = $.getParameter("objType");
		$scope.data.objVal = $.getParameter("objVal");
		$scope.initData($.getParameter("boKeys"));
	};

	var isLoadedData = false;// 是否已加载数据
	$scope.$on("afterLoadEvent", function(event, data) {
		delete $scope.data.busObjMapJson;
		isLoadedData = true;
		mergeData();
	});

	/**
	 * 获取权限的描述
	 */
	$scope.getRightsDesc = function(rights) {
		if (!rights) {
			return "未配置";
		}

		var desc = "";
		angular.forEach(rights, function(item) {
			if (desc) {
				desc = desc + " 和 ";
			}
			desc = desc + item.desc;
		});
		return desc;
	};

	/**
	 * 设置权限 rights:权限 key:对应权限类型
	 */
	$scope.setRights = function(rights, key, type) {
		rights[key] = [ {
			type : type
		} ];
		if (type === "everyone") {
			rights[key][0].desc = "所有人";
		} else {
			rights[key][0].desc = "无";
		}
	};

	/**
	 * 打开权限对话框
	 */
	$scope.openRightsDialog = function(rights, key) {
		$.Dialog.open({
			url : "/bus/businessPermission/rightsDialog.html",
			width : 800,
			height : 600,
			title : "权限配置",
			passData : angular.copy(rights[key]),
			ok : function(index, innerWindow) {
				var data = innerWindow.getData();
				$scope.$apply(function() {
					rights[key] = data;
				});
				$.Dialog.close(innerWindow);
			},
			topOpen : true
		});
	};

	/**
	 * 初始化数据
	 */
	var isLoadedBo = false;// 是已加载完bo
	$scope.initData = function(boKeys) {
		// 加载bo
		var url = __ctx + "/bus/businessPermission/getBo";
		var defer = baseService.postForm(url, {
			boKeys : boKeys
		});
		$.getResultData(defer, function(data) {
			$scope.boMap = data;

			angular.forEach(data, function(val, key) {
				var allRelations = [];
				TraverseTreeUtil.traverse(val.relation, "children", function(node) {
					allRelations.push(node);
				});
				val.allRelations = allRelations;
			});
			isLoadedBo = true;

			if (!$scope.data.id) {// id为空才重置数据
				$scope.resetDataWithBo();
			} else {// 不为空触发一次合并数据
				mergeData();
			}
		}, null,true);
	};

	/**
	 * 根据bo重置数据
	 */
	$scope.resetDataWithBo = function() {

		$scope.data.busObjMap = {};
		angular.forEach($scope.boMap, function(bo, key) {
			var busObj = {};
			busObj.key = bo.key;
			busObj.name = bo.name;
			// 只有bo设置默认数据
			busObj.rights = {
				w : [ {
					type : "everyone",
					desc : "所有人"
				} ]
			};
			busObj.tableMap = {};

			// 根据bo生成初始化tableMap
			angular.forEach(bo.allRelations, function(relation) {
				var table = {};
				table.key = relation.tableKey;
				table.comment = relation.tableComment;
				table.rights = {};

				// 处理字段
				table.columnMap = {};
				angular.forEach(relation.table.columnsWithoutPk, function(item) {
					var column = {};
					column.key = item.key;
					column.comment = item.comment;
					column.rights = {};
					table.columnMap[column.key] = column;
				});

				busObj.tableMap[table.key] = table;
			});
			$scope.data.busObjMap[key] = busObj;
		});
	};

	function mergeData() {
		if (!isLoadedBo || !isLoadedData) {// 数据都准备好了才合并
			return;
		}

		var dataTemp = CloneUtil.deep($scope.data);// 备份加载出来的数据
		$scope.resetDataWithBo();// 根据Bo刷新出新的数据 $scope.data
		// 合并bo
		angular.forEach($scope.data.busObjMap, function(bo, boKey) {
			var boTemp = dataTemp.busObjMap[boKey];
			if (!boTemp) {
				return;
			}
			bo.rights = boTemp.rights;

			// 合并bo中的表
			angular.forEach(bo.tableMap, function(table, tableKey) {
				var tableTemp = dataTemp.busObjMap[boKey].tableMap[tableKey];
				if (!tableTemp) {
					return;
				}
				table.rights = tableTemp.rights;

				// 合并bo中的表的字段
				angular.forEach(table.columnMap, function(column, columnName) {
					var columnTemp = dataTemp.busObjMap[boKey].tableMap[tableKey].columnMap[columnName];
					if (!columnTemp) {
						return;
					}
					column.rights = columnTemp.rights;
				});
			});
		});
	}
} ]);
