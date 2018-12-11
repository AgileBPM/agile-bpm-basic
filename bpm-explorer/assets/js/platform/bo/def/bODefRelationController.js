var app = angular.module("app", [ 'base', 'formDirective', 'arrayToolService' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
	$scope.init = function() {
		baseService.post("bODef/initRelation?id=" + __param.id).then(function(data) {
			$scope.formList = data.formList;
			$scope.defList = data.defList;
		});
	};
} ]);