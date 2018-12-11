var app = angular.module("app", [ 'base', 'formServiceModule' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.ArrayTool = ArrayToolService;
	$scope.init = function() {
		$scope.editable = !(!$.getParameter("id"));

		if (window.passData) {// 前端html传过来
			var html = window.passData.html;
			$scope.html = html;
		}
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		if (window.passData) {// 前端html传过来，则不需要data传来的
			delete data.html;
		}
		// 把字段都赋值到scope中
		jQuery.extend($scope, data);
		//发布权限更新事件
		$scope.$emit('permission:update',data);
	});
	
	//把scope.html 用ngjs编译后的处理
	$scope.$root.$on("afterBindHtmlEvent", function(event) {
		$(function() {
			// 隐藏
			$("[hide]").hide();
		});
	});
} ]);
