var  app = angular.module("app", ['base','formDirective']);
app.controller('ctrl', ['$scope','baseService',function($scope,baseService){
	$scope.data={};
	$scope.data.isRequired="0";
	$scope.data.dataType="string";
}]);