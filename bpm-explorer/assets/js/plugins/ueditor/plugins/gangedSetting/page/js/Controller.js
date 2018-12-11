var app = angular.module('app',['baseServices','arrayToolService','commonListService','CustomQueryService','BpmFormService','FieldService']);
app.controller("Controller",['$scope','BaseService','ArrayToolService','CommonListService','CustomQuery','BpmForm',function($scope,BaseService,ArrayToolService,CommonListService,CustomQuery,BpmForm){
	$scope.ArrayTool=ArrayToolService;
	$scope.CommonList=CommonListService;
	$scope.BaseService=BaseService;
	var editor = window.passConf.editor;
	var selectedDom = window.passConf.selectedDom;
	var form = $(".table-form").form();
	var gangedsettingjson=null;//编辑的Json
    if(selectedDom.attributes['ganged-setting']!=null){
    	gangedsettingjson=JSON.parse(selectedDom.attributes['ganged-setting'].value);
    }
	
    var content =editor.getContent();
    function isEditorContentContain(str){
    	return content.indexOf(str)>=0;
    }
	$scope.prop={
			bind:{},
			query : []
		
	};
	$scope.done = function(){
		if(form.valid()){
			selectedDom.setAttribute('ganged-setting',$scope.getHtml());
			return true;
		}
		$.topCall.error("表单验证失败");
		return false;
    	
	};
	//初始化
	if(gangedsettingjson!=null){
		$scope.prop=gangedsettingjson;
	}
	
	try{
		if(formFieldList){
			setFormFieldList(formFieldList);
		}else{
			BpmForm.detail({id:formId},function(data){
				setFormFieldList(data.bpmForm.bpmFormFieldList);
			});
		}
	}catch(e){
		BpmForm.detail({id:formId},function(data){
			setFormFieldList(data.bpmForm.bpmFormFieldList);
		});
	}
	function setFormFieldList(bpmFormFieldList){
		$scope.formFieldList = [];
		for(var j=0,bff;bff=bpmFormFieldList[j++];){
			$scope.formFieldList.push(bff);
			
			if(bff.children==null) continue;
			for(var k =0;child=bff.children[k];k++){
				$scope.formFieldList.push(child);
			}
		}
	}
	
	CustomQuery.getAll(function(data){
		$scope.customQuerys=data;
		for(var i=0;i<data.length;i++){
			var customQuery = data[i];
			customQuery.conditionfield=JSON.parse(customQuery.conditionfield);
			customQuery.resultfield=JSON.parse(customQuery.resultfield);
			customQuery.sortfield=JSON.parse(customQuery.sortfield);
			
			if(gangedsettingjson==null) continue;
			var prop=$scope.prop;
			if(prop.cid!=customQuery.id) continue;
			
			//初始化编辑的自定义查询
			$scope.selectedCustomQuery=customQuery;
			for(var j=0;rf=prop.resultfield[j];j++){
				for(var k=0;r=customQuery.resultfield[k];k++){
					if(r.comment!=rf.field) continue;
					r.target=rf.target;
				}
			}
		}
		if($scope.prop.id){
			$scope.changeCid($scope.prop.id);
		}
	});
	
	$scope.changeCid=function (id){
		var data=$scope.customQuerys;
		for(var i=0;i<data.length;i++){
			var customQuery = data[i];
			if(customQuery.id==id){
				$scope.selectedCustomQuery=customQuery;//当前选择的查询
			}
		}
	};
	//FormFieldList拦截器，判断editor是否存在这个字段Input
	$scope.filterFormFieldList = function(val){
		var str1 = 'ng-model="data.'+val.path+'.'+val.name+'"';
		var str2 = 'ng-model="item.'+val.name+'"';
		if(isEditorContentContain(str1)||isEditorContentContain(str2)){
			//console.info(str1+"---"+str2);
			return true;
		}
		return false;
	};
	
	//最终结果
	$scope.getHtml=function(){
		var prop=$scope.prop;
		
		prop.resultfield=[];
		for(var i=0;r=$scope.selectedCustomQuery.resultfield[i];i++){
			var p={};
			if(r.target==null) continue;
			p.field=r.comment;
			p.target=r.target;
			prop.resultfield.push(p);
		}
		
		var str=JSON.stringify(prop);
		//console.info(str);
		return str;
	};
	$scope.getEditingJlsz = function(){
		var form = $("body").form();
		if(form.valid()){
			return $scope.prop;
		}else{
			$.topCall.error("表单验证失败");
		}
		
	};
	
	$scope.triggerEventList=[
  		{
  			key:'回车',
  			value:0
  		},
  		{
  			key:"值改变",
  			value:1
  		}
  	];
}]);