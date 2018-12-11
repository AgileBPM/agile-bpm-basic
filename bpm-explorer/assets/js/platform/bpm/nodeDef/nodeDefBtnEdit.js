var app = angular.module("btnApp", [ 'base', 'baseDirective' ]);
		app.controller('btnController', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
			$scope.buttonList = window.passData;
			$scope.defaultNodeBtns = [];
			$scope.ArrayTool = ArrayTool;
			
			//按钮状态 1.存在的，2，直接新增的，3，添加预定义的按钮。
			$scope.setStatus=function(data,status){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					obj.status=status;
				}
			}
			
			//0:获取默认初始化的按钮,1:获取配置的按钮,2:获取默认不初始化的按钮
			$scope.getInitButtons = function(){
				var get = baseService.get(__ctx + "/bpm/processDef/getDefaultNodeBtns?isDefault=false&defId="+$.getParam("defId")+"&nodeId="+$.getParam("nodeId"))
				get.then(function(data){
					$scope.defaultNodeBtns = data;
				},function(code){
					$.Toast.error("error!"+code);
				})
			}
			
			$scope.initBtns = function(){
				var get = baseService.get(__ctx + "/bpm/processDef/getDefaultNodeBtns?isDefault=true&defId="+$.getParam("defId")+"&nodeId="+$.getParam("nodeId"))
				get.then(function(data){
					$scope.buttonList =data;
					$.Toast.success("已经初始化为默认按钮！");
				},function(code){
					$.Toast.error("error!"+code);
				})
			}
			
			//判断默认的是否需要展示在可选列表中
			$scope.isInclude = function(thisBtn){
				for(var i=0,btn;btn=$scope.buttonList[i++];){
					if(btn.alias==thisBtn.alias) return true;
				}
				return false;
			}
			
			$scope.getData = function(){
				if(!$scope.myForm.$valid){
					$.Toast.error("当前编辑按钮表单不合法！");
					return false;
				}
				return $scope.buttonList;
			}
			
			$scope.addButton = function(){
				if(!$scope.myForm.$valid){
					$.Toast.error("请确认当前编辑按钮是否正确！");
				}
				
				var btn = {name:"",alias:"",status:2,supportScript:true};
				$scope.buttonList.push(btn);
				$scope.btn =btn;
			}
			
			$scope.changeEditing = function(index){
				if(!$scope.myForm.$valid){
					$.Toast.error("当前编辑按钮表单不合法！");
					return;
				}
				$scope.btn = $scope.buttonList[index];
			}
			$scope.del = function(index){
				if($scope.buttonList[index]==$scope.btn)$scope.btn =false;
				$scope.buttonList.splice(index,1);
			}
			$scope.changeAlias = function(){
				for(var i=0,btn;btn=$scope.buttonList[i++];){
					if(btn.alias==$scope.btn.alias && $scope.btn !== btn){
						$.Toast.error("["+btn.alias+"]按钮alias 不可重复！");
						$scope.btn.alias = "";
						return;
					}					
				}
			}
			
			$scope.changeButtonType=function(obj){
				if(obj.alias){
					obj.status=3;
					var btn = $scope.getButton(obj.alias);
					$.extend(obj,btn);
				}
				else{
					obj.status=2;
				}
			}
			
			$scope.getButton=function(alias){
				for(var i=0;i<$scope.defaultNodeBtns.length;i++){
					var obj=$scope.defaultNodeBtns[i];
					if(alias==obj.alias){
						return obj;
					}
				}
				return "";
			}
			
			
			$scope.getInitButtons();
			
		} ]);
		
		
		function getData(){
			return AngularUtil.getScope().getData();
		}