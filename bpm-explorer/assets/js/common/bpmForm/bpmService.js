
var bpmModel = angular.module('bpm', ['base','formServiceModule'])
bpmModel.factory('bpmService', ['$rootScope','baseService','ArrayToolService', function($rootScope,baseService,ArrayToolService) { 
	//如果只有流程定义ID说明启动流程
	var defId = "";
	//如果有实例id说明草稿
	var instanceId ="";
	//存在任务则说明为处理流程任务
	var taskId ="";
	var bpmTask = null;
	var flowKey = null;
	var bpmService = {};
	/**
	 * 初始化流程数据
	 */
	bpmService.init = function(scope,initParam){
		var param = angular.fromJson(initParam);
		defId = param.defId;
		instanceId = param.instanceId;
		taskId = param.taskId;
		flowKey = param.defKey;
		
		var dataUrl =__ctx + "/bpm/task/getTaskData?taskId=" + taskId;
		if(!taskId){
			dataUrl = __ctx + "/bpm/instance/getInstanceData?defId="+defId+"&flowKey="+flowKey+"&instanceId="+instanceId+"&readonly="+param.readonly;
		}
		
		var defer=baseService.get(dataUrl);
		
		$.getResultData(defer,function(data){
			bpmTask = data.task;
			if(data.defId){defId = data.defId};
			scope.$emit('data:loaded',data)
			jQuery.extend(scope, data);
		},"alert");
		
		scope.ArrayTool = ArrayToolService;
		
	}
	
	//获取表单的数据
	bpmService.getFormData = function(scope,button,validateForm){
		var frmType = scope.form.type;
		if(frmType=='INNER'){
			if(!scope.custForm.$valid && validateForm){
				var str = "";
				for(var i=0,e;e=$("[error-msg]")[i++];){
					str = str + "<br>" + $(e).attr("error-msg");
				}
				
				$.Dialog.alert("表单校验不通过！"+str,7);
				return false; 
			}
			return scope.data;
		}
		else if(frmType=='FRAME'){
			var iframeObj=document.getElementById("frmFrame").contentWindow;
			try{
				if(validateForm){
					if(iframeObj.isValid() === false){
						return false;
					}
				}
			}
			catch(e){
			}
			
			try {
				return iframeObj.getData();
			} catch (e) {
				return {test:"jeff"};
			}
		}
	};
	
	bpmService.isInstance =  function(){
		return $.isEmpty(taskId);
	};
	
	bpmService.getDefId = function(){
		return defId;
	};
	bpmService.getTaskId = function(){
		return taskId;
	};
	bpmService.getNodeId = function(){
		if(bpmTask){
			return bpmTask.nodeId;
		}
		return "";
	}
	bpmService.getInstanceId = function(){
		if(!this.instanceId && bpmTask){
			return bpmTask.instId;
		}
		
		return instanceId;
	};
	
	return bpmService;
}])
/**
 * 流程初始化入口
 */
.directive('bpmInit', ['bpmService','$filter', function(bpmService,$filter){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			var paramKey = attrs.bpmInit || "bpmInitParam";
			bpmService.init(scope,scope[paramKey]);
			scope.$root.$on("afterBindHtmlEvent", function(event) {
				$(function() {
					// 隐藏
					$("[hide]").hide();
				});
			});
		},
		template: '<div>\
					<form ng-show="form.type==\'INNER\'" name="custForm" ab-bind-html="form.formHtml"></form>\
					<div ng-show="form.type==\'FRAME\'" bpm-frame-form ng-model="form"></div>\
				   </div>'
	};
}])
/**
 * 流程按钮解析。
 * 关于按钮样式，对话框宽高属性，不做可配置行，因为前段无法统一，
 * 但是、请求资源路径要求一致，比如/bpm/task/   /bpm/instance.
 * 前缀自己前段控制
 */
.directive('bpmButtons', ['bpmService','baseService', function(bpmService,baseService){
	return {
		restrict:"EA",
		link:function(scope, element, attrs){
			var ii;
			var button;
			scope.$root.$on("data:loaded",function(event,data){
				scope.buttonList = data.buttonList; 
			})
			
			/**
			 *  是否校验表单，对话框高，宽
			 */
			scope.buttonClick = function(validateForm,dialogHeigth,dialogWidth){
				button = this.button;
				//执行前置js
				if(!scope.execuFn(button.beforeScript)){
					 return;
				 }
				ii = layer.load();
				//获取流程数据
				var busData = bpmService.getFormData(scope,button,validateForm);
				if(busData === false){
					layer.close(ii);
					return;
				}
				var flowData = {
						defId: bpmService.getDefId(),
						taskId: bpmService.getTaskId(),
						instanceId: bpmService.getInstanceId(),
						formType: scope.form.type,
						data: busData,
						action: button.alias,
						nodeId:bpmService.getNodeId()
					};
				//获取更多完成动作配置
				if( button.configPage){
					layer.close(ii);
					var height = dialogHeigth ||300;
					var width = dialogWidth || 600;
					
					var conf = {title:button.name,url:button.configPage,height:height,width:width,passData:flowData,topOpen:true};
					conf.ok = function(index,innerWindow){
						if(!innerWindow.isValidate())return ;
						scope.postAction(flowData,innerWindow);
					}
					$.Dialog.open(conf);
				}else{
					scope.postAction(flowData);
				}
			}
			
			scope.postAction = function(flowData,innerWindow){
				ii = layer.load();
				// 执行动作
				var url =  __ctx + (flowData.taskId? "/bpm/task/doAction":"/bpm/instance/doAction");
				var defer = baseService.post(url,flowData);
				$.getResultMsg(defer,function(){
					layer.close(ii); //关闭等待框
					scope.execuFn(button.afterScript);
					
					if(innerWindow){
						$.Dialog.close(innerWindow);
					}
					//关不掉 已经绝望、打一波组合拳吧
					top.layer.closeAll('dialog');
					layer.closeAll();
					parent.layer.closeAll(); // 父窗口把当前窗口关掉
					if(window.opener && window.opener.reloadGrid){
						window.opener.reloadGrid();
					}
				},function(){
					layer.close(ii);
				});
			}
			
			scope.execuFn = function(fnStr){
				if(!fnStr)return true;
				
				var script = "var tempFunction = function(scope){ "+fnStr+"};"
				var result =  eval(script+"tempFunction(scope);");
				if(result===false) return false;
				return true;
			}
		}, /* button 是否需要对表单校验 后面修改为 后台配置，按钮可配置形*/
		template:'<span  ng-repeat="button in buttonList">\
					<div ng-if="button.alias==\'start\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(true)" 			class="btn btn-success fa fa-send">{{button.name}}</div>\
					<div ng-if="button.alias==\'draft\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false)" 			class="btn btn-primary fa fa-clipboard">保存草稿</div>\
					<div ng-if="button.alias==\'save\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false)" 			class="btn btn-primary fa fa-clipboard">保存</div>\
					<div ng-if="button.alias==\'agree\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(true,422,690)" 	class="btn btn-success fa fa-check-square-o">{{button.name}}</div>\
					<div ng-if="button.alias==\'oppose\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(true,422,690)" 	class="btn btn-primary fa fa-close">{{button.name}}</div>\
					<div ng-if="button.alias==\'reject\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false,422,690)" 	class="btn btn-danger fa fa-lastfm">{{button.name}}</div>\
					<div ng-if="button.alias==\'reject2Start\'" buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false,300,500)" 	class="btn btn-danger fa fa-lastfm">{{button.name}}</div>\
					<div ng-if="button.alias==\'lock\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false)" 			class="btn btn-primary fa fa-lock">锁定</div>\
					<div ng-if="button.alias==\'unlock\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false)" 			class="btn btn-primary fa fa-unlock">解锁</div>\
					<div ng-if="button.alias==\'taskOpinion\'" 	buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false,500,900)" 	class="btn btn-primary fa fa-navicon">审批历史</div>\
					<div ng-if="button.alias==\'flowImage\'" 	buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false,600,800)" 	class="btn btn-primary fa fa-image">流程图</div>\
			        <div ng-if="button.alias==\'manualEnd\'" 	buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false,300,500)" 	class="btn btn-danger fa fa-ioxhost">人工终止</div>\
					<div ng-if="button.alias==\'print\'" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false)" 			class="btn btn-primary fa fa-print">打印</div>\
					<div ng-if="\'start,draft,save,agree,oppose,reject,reject2Start,lock,unlock,taskOpinion,flowImage,manualEnd,print,\'.indexOf(button.alias)==-1" 		buttonAlias="{{button.alias}}"   	ng-click="buttonClick(false)" 			class="btn btn-primary">{{button.name}}</div>\
				 </span>',
		replace:true
	};
}])//[[name=同意, alias=agree], [name=保存草稿, alias=draft], [name=驳回, alias=back], [name=审批历史, alias=taskOpinion], [name=流程图, alias=image], [name=打印, alias=print]]

/**
 * IFRAME方式加载表单。
 * ng-model：需要绑定表单的地址。
 * <div ng-show="form.type==\'FRAME\'" bpm-frame-form ng-model="form"></div>
 */
.directive('bpmFrameForm', ['bpmService','baseService', function(bpmService,baseService){
	return {
		restrict:"A",
		require : '?ngModel', 
		link:function(scope, element, attrs,ngModel){
			if(!ngModel) return;
			ngModel.$render = function () {
				if(!ngModel.$modelValue) return ;
				if(ngModel.$modelValue.type!="FRAME") return;
				
				var url=ngModel.$modelValue.formValue;
				url = url.startWith("http")?url : getProjectUrl(url);
				var frameObj=$("<iframe id='frmFrame' src="+url +" onload='iframeHeight(this)' style='width:100%;border:none;'></iframe>")
				//将表单加载到系统中
				element.append(frameObj);
			}
		}
	};
}]);

/**
 * iframe高度沾满浏览器高度。
 * @param obj
 */
window.iframeHeight = function(obj){
	var height=document.documentElement.clientHeight  || document.body.clientHeight ;
	$(obj).height(height-40);
}