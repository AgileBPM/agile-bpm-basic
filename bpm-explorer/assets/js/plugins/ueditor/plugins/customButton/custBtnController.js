var app = angular.module('app',['base','dragDivTreeModel']);

app.controller("CustBtnController",['$scope','baseService','dragService','$http',function($scope,baseService,dragService,$http){
	$scope.custBtn={name:"选择",custQueryList:[],custDialog:{}};  //页面数据格式
	$scope.custQueryList = [];
	$scope.allFields=[];
	dragService.setTreeParamDef({idKey:"field",nameKey:"comment",pIdKey:"parentId"});
	$scope.currentSubTable = currentSubTable;
	$scope.custBtn.isInSub =isInSub;//位于子表中，添加子表一行的情况
	$scope.loadStep=0;
	
	$scope.getHtml = function(){
		//获取自定义对话框的bind
		if($scope.custBtn.custDialog.alias) $scope.custBtn.custDialog.mappingConf =$scope.getTreeTarget("custDialogTree");
		for(var i=0,query;query=$scope.custBtn.custQueryList[i++];){
			query.mappingConf =$scope.getTreeTarget("querytree"+(i-1));
		}
		var custDialogConf = " ht-custDialog='"+JSON.stringify($scope.custBtn)+"' "
		if(formType=='mobile'){
			return "<span "+custDialogConf+" class='btn-sm button "+$scope.custBtn.icon+"'>"+$scope.custBtn.name+"</span>"
		}
		
		return "<span "+custDialogConf+" class='btn  btn-sm btn-primary "+$scope.custBtn.icon+"'>"+$scope.custBtn.name+"</span>";
	}
	//数据回显
	$scope.innit = function(json){
		if(json.custQueryList.length>0){
			//需要多加载一步
			$scope.getQueryList();
			$scope.loadStep--;
		}
		$scope.custBtn = json;
 
		$scope.$watch("loadStep",function(newValue, oldValue){
			//等到所有字段和所有对话框加载完毕
			if(newValue == oldValue || newValue !=2){
				return;
			}
			window.setTimeout(function(){
				$scope.changeCustDialog('custDialogTree',true);
				$scope.setTreeTarget("custDialogTree",json.custDialog);
				
				for(var i=0,query;query=json.custQueryList[i++];){
					$scope.changeCustQuery(i-1,true);
					$scope.setTreeTarget("querytree"+(i-1),query);
				}
			}, 10)
				
		});
	}
	
	$scope.setTreeTarget = function(treeId,json){
		if(!json.mappingConf && json.mappingConf.length==0)return;
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var allNode=zTree.getNodes(),mappingConf=json.mappingConf;
		for(var i=0,node;node=allNode[i++];){
			for(var j=0,m;m=mappingConf[j++];){
				if(node.field != m.from) continue;

				for(var k=0,t;t=m.target[k++];){
					var dom =$("[domid='"+t+"']",$("#"+treeId+"_dom"));
					dom.addClass("domBtn_Disabled");
					dom.removeClass("domBtn");
					
					var nodes = zTree.addNodes(node,{"field":t,"comment":dom.text(),domNode:true});
					zTree.selectNode(nodes[0]);
				}
			}
		}
	}
	
	$scope.getTreeTarget = function(treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var nodes=zTree.getNodes(),mappingConf=[];
		for(var i=0,node;node=nodes[i++];){
			if(!node.children)continue;
			var target=[], mapping = {};
			for(var j=0,c;c=node.children[j++];){
				target.push(c.field);
			}
			
			if(target.length>0){
				mapping.from=node.field;
				mapping.target=target;
				mappingConf.push(mapping);
			}
		}
		return mappingConf;
	}
	
	// 自定义对话和change事件
	$scope.changeCustDialog = function(ztreeId,isInnit){
		if($scope.customDialogs.length==0)return;
		for(var i=0,d;d=$scope.customDialogs[i++];){
			if(d.alias ==$scope.custBtn.custDialog.alias){
				var treeData =eval("(" + d.resultfield + ")");
				for(var q=0,f;f=treeData[q++];){
					f.field =f.comment;
				}
				
				dragService.innit(ztreeId,ztreeId+"_dom",treeData);
 
				var conditionList = eval("(" + d.conditionfield + ")");
				if(!$scope.custBtn.custDialog.conditions)$scope.custBtn.custDialog.conditions = [];
				//只处理类型等于4的对话框参数
				if(conditionList && conditionList.length>0){
					for(var j=0,c;c=conditionList[j++];){
						if(c.defaultType=="4"){
							var has = false;
							if(isInnit){//初始化的时候、将新增参数配置进行添加
								for(var k=0,old;old=$scope.custBtn.custDialog.conditions[k++];){
									if(old.field ==c.field) {
										has = true;
										break;
									}
								}
							}
						if(!has) $scope.custBtn.custDialog.conditions.push(c);
					}}
				}
			}
		}
	}
	
	$scope.changeCustQuery = function(index,isInnit){
		var query = $scope.custBtn.custQueryList[index];
		if($scope.custQuerys.length==0)return;
		var ztreeId ="querytree"+index;
		for(var i=0,d;d=$scope.custQuerys[i++];){
			if(d.alias ==query.alias){
				dragService.innit(ztreeId,ztreeId+"_dom",eval("(" + d.resultfield + ")"));
				
				var conditionList = eval("(" + d.conditionfield + ")");
				
				
				if(!query.conditions)query.conditions = [];
				//只处理类型等于4的对话框参数
				if(conditionList && conditionList.length>0){
					for(var j=0,c;c=conditionList[j++];){
						if(c.defaultType=="1"){
							var has = false;
							if(isInnit){//初始化的时候、将新增参数配置进行添加
								for(var k=0,old;old=query.conditions[k++];){
									if(old.field ==c.field) {
										has = true;
										break;
									}
								}
							}
							if(!has)query.conditions.push(c);
						}
					}
				}
			}
		}
	}
	
	$scope.isSelect = function(model,value){
		if(model == value){
			return true;
		}
		return false;
	}
	
	/*$scope.changeQueryType =function(index){
		var queryType = custBtn.custQueryList[index].type;
		if(!scope[queryType+"List"])getQueryList();
		
		
	}*/
	
	//获取别名脚本和自定查询
	$scope.getQueryList = function(){
		if($scope.custQuerys) return;
		var custQuery = baseService.get(__ctx+"/form/customQuery/getAll");
		custQuery.then(function(data){
			$scope.custQuerys = data;
			$scope.loadStep++;
	    }, function(status){alert("error! code:"+status)});
		//别名脚本
		/*var aliasScript =  baseService.postForm(__ctx+"/form/form/getFormBoLists",{formId:formId});
		aliasScript.then(function(data){
			$scope.aliasScripts = data;
	    }, function(status){alert("error!  code:"+status)});*/
		
	}
	$scope.getAllFields = function(){
		var post =  baseService.postForm(__ctx+"/form/form/getFormBoLists",{formId:formId});
		post.then(function(data){
			$scope.allFields = data;
			$scope.loadStep++;
	    }, function(status){alert("error!  code:"+status)});
	}
	
	$scope.getAllDialogs = function(){
		$http.post(__ctx +'/form/customDialog/getAll').success(function(data, status, headers, config){
			$scope.customDialogs = data;
			$scope.loadStep++;
		});
	}
	$scope.selectIcon = function(){
		new IconDialog({callback:function(alias,win){
			$scope.$apply(function(){
		        $scope.custBtn.icon="fa "+alias;    
		    });
			win.dialog('close');
		}}).show();
	}
	//添加一个查询
	$scope.addCustQuery = function(){
		$scope.custBtn.custQueryList.push({name:"未命名查询"});
		$scope.getQueryList();
	}
	//删除某一个查询
	$scope.delCustQuery = function(index){
		$scope.custBtn.custQueryList.splice(index,1);
		$("#custTab").click();
	}
	//获取所有字段
	$scope.getAllFields();
	//获取所有对话框
	$scope.getAllDialogs();
	
	custombuttonjson&&$scope.innit(custombuttonjson);
}]);

