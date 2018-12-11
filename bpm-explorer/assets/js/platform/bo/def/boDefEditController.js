var app = angular.module("app", [ 'base', 'baseDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	$scope.ArrayTool = ArrayToolService;
	var filter = $filter('filter');

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
		$scope.data.categoryId = categoryId;
		$scope.data.status = "normal";
		$scope.subEnts = [];
		
		
	};

} ]);