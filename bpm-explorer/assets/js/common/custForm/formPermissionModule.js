/***表单权限控制**/

/**
 * 表单定义的通用指令。
 */
var formPermissionModule = angular.module("formPermissionModule", [ "base" ])

/**
 * 基础的权限指令，获取ng-model 的 $viewValue
 * @无权限：则移除
 * @编辑权限：不处理
 * @只读权限：将ng-model中的值拿出。不做任何处理
 */
.directive('abBasicPermission', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			var hasLoadPermission = false;
			
			var handlePermission = function(value,isTimeOut){
				if(!hasLoadPermission) return value;
				
				// 特殊控件执行timeout处理。这里不再配置穷举写死
				if(attrs.abCombox && !isTimeOut){
					window.setTimeout(function(){
						handlePermission(ctrl.$viewValue,true);
					},300);
					return value;
				}
				
				
				var permission = getPermission(attrs.abBasicPermission,scope);
				if(permission=='r')	{	
					element.after(value);
	    			element.hide();
	    			return ;
	    		}
	    		if(permission=='n')	{
	    			$(element).remove(); 
	    			return ;
	    		}
	    		
	    		handleRequiredPermission(permission,ctrl);
	    		
	    		return value;
			}
			ctrl.$formatters.push(handlePermission);
			
			// 若权限标签在ng-model之前，则等待formatter触发计算
			// 若在之后，则直接使用viewValue去进行计算
			if(!scope.permission){
				scope.$root.$on("permission:update",function(event,data){
					hasLoadPermission = true;
					handlePermission(ctrl.$viewValue);
				})
			}else{
				hasLoadPermission = true;
			}
		}
	};
} ])

/**
 * 选择框、复选框、单选按钮、下拉框、radio 类型的权限指令。
 * @无权限：则移除
 * @编辑权限：不处理
 * @只读权限：取出 input中 value对应的 text
 */
.directive('abCheckedPermission', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			var hasLoadPermission = false;
			
			var handlePermission = function(value){
				if(!hasLoadPermission) return value;
				
				var permission = getPermission(attrs.abCheckedPermission,scope);
				
				if(permission === 'n') {
	    			element.remove();
	    			return;
	    		}	
				
				if(permission === 'r'){
					var modelValue = eval("scope."+attrs.ngModel);
					if(modelValue){
						var values = modelValue.split(",");
						var text=[];
	    				for(var i=0,val;val=values[i++];){
	    					if(!val) continue;
	    					var checked = $("[value='"+val+"']",element);
	    					if(checked.length>0){
	    						text.push(checked.text()||checked.parent().text());
	    					}
	    				}
	    				element.after(text.join("，"));
					}
	    			element.hide();
	    			return;
	    		}
				handleRequiredPermission(permission,ctrl);
				return value;
			}
			
			ctrl.$formatters.push(handlePermission);
			
			if(!scope.permission){
				scope.$root.$on("permission:update",function(event,data){
					hasLoadPermission = true;
					handlePermission(ctrl.$viewValue);
				})
			}else{
				hasLoadPermission = true;
			}
		}
	};
} ])

/**
 * 只对是否展示进行权限判断。或者【无权限】下不展示该标签
 * 比如某块使用权限路径判断后。发现无权限。则直接remove掉。
 * 不会处理任何数据。
 * 多用于子表，或者块、整个区域的权限断定。
 */
.directive('abShowPermission', [ function() {
	return {
		link : function(scope, element, attrs) {
			var handlePermission = function(){
				var permission = getPermission(attrs.abShowPermission,scope);
				
				if(permission === 'n'){
					element.hide();
				}
			}
			
			if(!scope.permission){
				scope.$root.$on("permission:update",function(event,data){
					handlePermission();
				})
			}else{
				handlePermission();
			}
		}
	};
} ])

.directive('abEditPermission', [ function() {
	return {
		link : function(scope, element, attrs) {
			
			var handlePermission = function(){
				var permission = getPermission(attrs.abEditPermission,scope);
				
				if(permission === 'n' || permission === 'r' ){
					element.remove();
				}
			}
			
			if(!scope.permission){
				scope.$root.$on("permission:update",function(event,data){
					handlePermission();
				})
			}else{
				handlePermission();
			}
		}
	};
} ])

function handleRequiredPermission(rights,ctrl){
	if(rights === 'b'){
		if(ctrl.validateJson){
			ctrl.validateJson.required = true;
		}else{
		   ctrl.validateJson = {"required":true};
		}
	}
	
}

function getPermission(permissionPath,scope){
	if(!permissionPath) return "w";
	var permission = scope.permission;
	var tablePermission = scope.tablePermission;
	if(!permission||!permissionPath) return "w";
	
	try {
		var p = eval(permissionPath); 
	} catch (e) {
		console.info("获取权限出现了异常 permissionPath:"+permissionPath)
		console.info(permission);
		console.info(e);
	}
	return p ||'w'; 
}

