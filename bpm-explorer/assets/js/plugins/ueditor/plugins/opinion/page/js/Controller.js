var app = angular.module('app',[]);
app.controller("Controller",['$scope',function($scope){
	var editor = window.passConf.editor;
	var form = $(".table-form").form();
	$scope.prop={width:400,widthunit:"px",heightunit:"px",height:100};
	$scope.done = function(){
    	if(form.valid()){
    		var html = "<div class='form-control' ht-bpm-opinion='data.__form_opinion."+$scope.prop.name+"' opinion-history='opinionList."+$scope.prop.name+"' permission='permission.opinion."+$scope.prop.name+"' style='width:"+$scope.prop.width+$scope.prop.widthunit+"!important;height:"+$scope.prop.height+$scope.prop.heightunit+"'></div>";
    		editor.execCommand('inserthtml',html);
            return true;
    	}
    	return false;
	};
	$scope.unitList=[
		{
			value:'px'
		},
		{
			value:'%'
		}
	];
	var formDefId = $("[name='defId']",editor.form).val();
	$scope.getOpinion = function(){
		$.post(__ctx+"/form/formDef/getOpinionConf",{id:formDefId},function(data){
			$scope.opinionConf = JSON.parse(data);
			$scope.$apply();
		});
	}
	
	$scope.getOpinion();
	$scope.prop.widthunit=$scope.unitList[0].value;//默认px
	$scope.prop.heightunit=$scope.unitList[0].value;
	
	$scope.selectOpinion = function(index){
		var opinion = $scope.opinionConf[index];
		$scope.prop.name =opinion.name;
		$scope.prop.desc =opinion.desc;
	}
	
	$scope.setOpinion = function(){
			var title="表单意见配置";
			var url=__ctx+"/form/formDef/opinionConf?id="+formDefId;
			HT.window.openEdit(url,title, 'view', 'grid', 630, 450, null, null, formDefId, true);
		};
}]);