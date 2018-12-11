	
	$(function(){
		var datechoose=function(value){
			var ngModel =this.target.attr("ng-Model");
			if(!ngModel) console.info("error! 当前非 angular input 环境。无法为ngModel 赋值");
			var scope = angular.element(this.target[0]).scope();
			
			scope.$apply(function(){
				eval("scope."+ngModel+"='"+value+"'");
			});
			
		};
		
		
		/**
		 * 自定义的日期选择器 
		 *  <input type="text" 
		 * 	ng-model="data.person.born" 
		 * 	class="inputText dateformat" 
		 * 	ab-date="yyyy-MM-dd HH:mm:ss"
		 * 	">
		 */
		$(document).delegate("[ab-date]",'focus',function(){
			var format=$(this).attr('ab-date') ||'yyyy-MM-dd';
			laydate({istime: true, format:format,choose:datechoose,target:$(this)});
			$(this).blur();
		});
		
		$(document).delegate(".date",'focus',function(){
			laydate({istime: true, format:'yyyy-MM-dd'});
			$(this).blur();
		});
		
		$(document).delegate(".datetime",'focus',function(){
			laydate({istime: true, format:'yyyy-MM-dd HH:mm:ss'});
			$(this).blur();
		});
	})
	