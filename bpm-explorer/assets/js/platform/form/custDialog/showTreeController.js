var app = angular.module('app', [ 'formDirective', 'arrayToolService' ]);
app.controller("ctrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;

	/**
	 * 加载zTree
	 */
	$scope.$on("afterLoadEvent", function(event, data) {
		data.conditionfield = JSON.parse(data.conditionfield);
		data.displayfield = JSON.parse(data.displayfield);
		data.resultfield = JSON.parse(data.resultfield);
		data.sortfield = JSON.parse(data.sortfield);
		
		var param=__param;
		// 处理url上的参数和对话框传来的参数
		if (window.passConf && window.passConf.param) {
			param = jQuery.extend(__param, window.passConf.param);
		}
		
		var treeUrl = __ctx + "/form/customDialog/getTreeData?dialog_alias_=" + data.alias;
		
		var idKey =data.displayfield.id;
		var pIdKey =data.displayfield.pid;
		var name = data.displayfield.displayName;
		ztreeCreator = new ZtreeCreator("ztree", treeUrl).setDataKey({
			idKey :idKey,
			pIdKey : pIdKey,
			name : name,
		});
		ztreeCreator.setAsync({
			enable : true,
			url : treeUrl,
			autoParam : [ idKey, pIdKey ],
			otherParam:param
		});
		if (data.selectNum != 1) {// 多选
			ztreeCreator.setCheckboxType({
				"Y" : "",
				"N" : ""
			});
		}
		
		//这里触发组合对话框的事件CombinateDialog/show.jsp
		ztreeCreator.setCallback({onClick:function(){
			if(window.parent.treeClick){
				window.parent.treeClick(getResult());
			}
		}});
		
		ztreeCreator.initZtree();
	});

} ]);