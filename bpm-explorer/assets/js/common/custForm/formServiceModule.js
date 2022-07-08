/**
 * 表单服务 依赖
 * 
 * @表单权限模块
 * @表单高级控件模块
 * @表单计算模块
 * @表单基础指令模块
 * 
 */
var formServiceModule = angular.module("formServiceModule", [ "base", "baseDirective", "formPermissionModule", "formControlsModule", "formCalculateModule" ]);

/**
 * <pre>
 * 表单增加子表指令
 * abSubAdd:要增加的数据，从initData中取
 * ngModel:要操作的对象
 * </pre>
 */
formServiceModule.directive('abSubAdd', [ function($compile) {
	return {
		scope : {
			abSubAdd : "="
		},
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			$(element).on("click", function() {
				var list = ctrl.$viewValue;
				if (!list) {
					list = [];
					ctrl.$setViewValue(list);
				}
				scope.$apply(function() {
					list.push(CloneUtil.deep(scope.abSubAdd));
				});
			});
		}
	};
} ])
/**
 * <pre>
 * abSubDetail: 对话框的divId
 * ngModel: 要操作的对象
 * </pre>
 */
.directive('abSubDetail', [ '$compile', function($compile) {
	return {
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			$(element).on("click", function() {
				var listName = attrs.abSubDetail.split("-")[1]+"List";
				if(!scope.$parent.subTempData){
					scope.$parent.subTempData = {};
				}
				scope.$apply(function() {
					scope.$parent.subTempData[listName] = CloneUtil.list(ctrl.$viewValue[listName]||[]);
				});
				var conf = {
					height : 600,
					width : 800,
					title : $(element).text(),
					btn : true,
					type : "1",
					content : $('#' + attrs.abSubDetail)
				};
				conf.ok = function(index, innerWindow) {
					// 确定才数据才生效
					scope.$apply(function() {
						ctrl.$viewValue[listName] = scope.$parent.subTempData[listName];
					});
					layer.close(index);
				};
				$.Dialog.open(conf);
			});
		}
	};
} ]);