var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('userConditionCtrl', [ '$scope', 'baseService', 'ArrayToolService', '$rootScope',function($scope, baseService, ArrayToolService,$rootScope) {
	$scope.ArrayTool = ArrayToolService;
	$scope.nodeType = nodeType;
	$scope.userCondition = {calcs:[],rule:""};
	if(window.passData){
			$scope.userCondition=window.passData.userCondition;
			$scope.nodeList=window.passData.nodeList;
	}
	
	var post = ToolsController.getInterFaceImpls("com.dstz.bpm.api.engine.plugin.context.UserCalcPluginContext").then(function(data){
		$scope.$apply(function(){
			$scope.nodeUserPluginList = data;
		})
	})
	
	$scope.addCalc = function() {
		$scope.userCondition.calcs.push({
			pluginType : "user",
			extract : "no",
			logicCal : "or",
			source : "start",
			vars:"",
			description : "发起人"
		});
	};
	
	/**
	 * 点击选择按钮处理，这里使用动态调用对话框。
	 * 选择器命名修改成：
	 * pluginType +Selector,参数为：calc 对象。
	 */
	$scope.selector = function(calc){
		eval(calc.pluginType+"Selector(calc);");
	};
	
	$scope.preview = function(){
		var passConf = getuserConditionData();
		var callback = function(data, dialog) {
			dialog.dialog('close');
		};
		DialogUtil.openDialog(__ctx+"/flow/node/condition/preview", "预览", passConf,callback);
	};
	
	$scope.calcTypeChange = function(calc){
		calc.description="";
	};
	
	//修改人员条件前置条件
	$scope.editRule = function(){
		var url='/bpm/definition/definitionScriptDialog.html?defId='+defId+'&nodeId='+nodeId;
		
		var  passData = {script:$scope.userCondition.condition,notice:"脚本返回 boolean 值，若返回false则不会进行使用该人员条件配置"};
		$.Dialog.open({url:url,width:840,height:530,title:"人员配置条件"+nodeId,passData:passData,
			ok:function(index, innerWindow){
				var script =innerWindow.getData();
				$scope.$apply(function(){
					$scope.userCondition.condition = script;
				});
				innerWindow.parent.layer.close(index);
			},topOpen:true});
	}
	
	function openCalcDialog(calc,conf){
		conf.ok = function(index, innerWindow){
			var data =innerWindow.getData();
			if(!data)return;
			
			$scope.$apply(function(){
				jQuery.extend(calc, conf.passData);
				!$rootScope.$$phase && $rootScope.$digest();
			});
			innerWindow.parent.layer.close(index);
		};
		conf.topOpen = true;
		
		$.Dialog.open(conf);
	}
	
	
	/**
	 * 以下代码是可以扩展部分。
	 */
	function userSelector(calc){
		var conf = { height:600,width:800,
		    url:"/bpm/nodeDef/userSelector.html",
		    title:"选择用户",
		    topOpen:true,
		    passData:angular.copy(calc)
		};
		conf.ok = function(index,innerWindow){
		    var data = innerWindow.getResult(); 
		    if(!data)return;
			jQuery.extend(calc, data);
			!$rootScope.$$phase && $rootScope.$digest();
		    $.Dialog.close(innerWindow);
		}
		$.Dialog.open(conf);
	}
	
	function groupSelector(calc){
		var conf = { height:300,width:600,
		    url:"/bpm/nodeDef/groupSelector.html",
		    title:"选择组",
		    topOpen:true,
		    passData:angular.copy(calc)
		};
		conf.ok = function(index,innerWindow){
		    var data = innerWindow.getResult(); 
		    if(!data)return;
			jQuery.extend(calc, data);
			!$rootScope.$$phase && $rootScope.$digest();
		    $.Dialog.close(innerWindow);
		}
		$.Dialog.open(conf);
	}
	
	function scriptSelector(calc){
		var url='/bpm/definition/definitionScriptDialog.html?defId='+defId+'&nodeId='+nodeId;
		var  passData ={script:calc.script,desc:calc.description||"",notice:"此脚本返回人员集合 Set<SysIdentity>"};
		
		$.Dialog.open({url:url,width:840,height:530,title:"事件脚本设置　"+nodeId,passData:passData,topOpen:true,
			ok:function(index, innerWindow){
				var script =innerWindow.getData();
				var desc = innerWindow.getDesc();
				$scope.$apply(function(){
					calc.script = script;
					calc.description = desc;
				});
				innerWindow.parent.layer.close(index);
			}});
	}
	
	
} ]);

var logicCalc = {or:"或",and:"且",exclude:"排除掉"};
function getData(){
	 var userCondition = AngularUtil.getScope().userCondition;
	 var description = "";
	 if(userCondition.calcs.length==0){
		 jQuery.Dialog.warning("请完善人员配置！");
		 return false;
	 }
	 
	 for(var i=0,calc;calc =userCondition.calcs[i++];){
		 if(calc.nodeId && !calc.description){calc.description =calc.nodeId };
		 
		 if(!calc.description && calc.pluginType!='approver' ) {
			 jQuery.Dialog.warning("请完善人员配置！");
			 return false;
		 }
		 if(calc.logicCal && i !=1)description +=logicCalc[calc.logicCal];
		 else description+="  ";
		 description += "["+$("#select"+(i-1)+" option:selected").text()+"]"+calc.description+";\n" ;
	 }
	 userCondition.description = description;
	return userCondition;
}

