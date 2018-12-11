/**表单函数计算相关js**/


var formControlsModule = angular.module("formCalculateModule", [ "base" ])

.directive('abFormMath', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			
		}
	};
} ]);