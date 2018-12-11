/**
 * edit页面和dialog页面公用的js，因为是scope的，有点
 */
var editAndDialogCommon = {};
/**
 * 初始化
 */
editAndDialogCommon.init = function(scope, baseService) {
	// 增加外键
	scope.addFk = function(fks) {
		fks.push({
			type : scope.BusTableRelFkType.PARENT_FIELD.key
		});
	};

	/**
	 * 打开子业务表配置对话框
	 */
	scope.openChildrenDialog = function(parent) {
		$.Dialog.open({
			url : "/bus/buinessObject/childrenDialog.html",
			width : 1500,
			height : 700,
			title : "[" + parent.tableComment + "(" + parent.tableKey + ")]子表配置",
			passData : parent,
			ok : function(index, innerWindow) {
				var data = innerWindow.getData();
				scope.$apply(function() {
					angular.copy(data, parent);
				});
				innerWindow.parent.layer.close(index);
			},
			topOpen : true
		});
	};

	scope.tableMap = {};
	/**
	 * 增加table详情
	 */
	scope.addTableDetail = function(key) {
		var table = scope.tableMap[key];
		if (!table) {
			var url = __ctx + "/bus/businessTable/getObject";
			var defer = baseService.postForm(url, {
				key : key,
				fill : true
			});
			$.getResultData(defer, function(data) {
				scope.tableMap[key] = data;
			});
		}
	};

	/**
	 * 增加子table
	 */
	scope.addSubTable = function(children) {
		CustUtil.openCustDialog("ywblb", null, function(data) {
			scope.$apply(function() {
				angular.forEach(data, function(item) {
					var isExist = false;
					angular.forEach(children, function(tem) {
						if (tem.tableKey === item.key) {
							isExist = true;
						}
					});
					if (!isExist) {
						scope.addTableDetail(item.key);
						children.push({
							tableKey : item.key,
							tableComment : item.comment,
							fks : [ {
								type : scope.BusTableRelFkType.PARENT_FIELD.key
							} ],
							type : scope.BusTableRelType.ONE_TO_ONE.key
						});
					}
				});
			});
		}, true, null, true);
	};
};
