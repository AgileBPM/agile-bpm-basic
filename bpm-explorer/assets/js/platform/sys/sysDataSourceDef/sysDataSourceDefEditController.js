var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
	};

	/**
	 * 发请求去获取类路径的属性
	 */
	$scope.initAttributes = function() {
		baseService.postForm(__ctx + '/sys/sysDataSourceDef/initAttributes', {
			classPath : $scope.data.classPath,
		}).then(function(result) {
			$scope.data.attributes = result.data;
		});
	};
	
	$scope.$on("beforeSaveEvent", function(event, data) {
		delete $scope.data.attributesJson;
	});
} ]);
