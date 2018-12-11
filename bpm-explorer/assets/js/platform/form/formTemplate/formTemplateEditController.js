var app = angular.module("app", [ 'base', 'baseDirective','ui.codemirror' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
		$scope.data.editable = true;
		
		ToolsController.getEnum("com.dstz.form.api.constant.FormTemplateType").then(function(data) {
			$scope.$apply(function() {
				$scope.FormTemplateType = data;
			});
		});
	};
} ]);
