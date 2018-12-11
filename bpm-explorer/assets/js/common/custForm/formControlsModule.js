/**表单高级控件**/

var formControlsModule = angular.module("formControlsModule", [ "base" ])


/**
 *  选择器
 */
.directive('abSelector', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			
		}
	};
} ]);