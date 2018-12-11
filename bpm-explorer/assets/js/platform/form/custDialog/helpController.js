var app = angular.module('app',['CustomDialogService']);
app.controller("HelpController",['$scope','CustomDialog',function($scope,CustomDialog){
	if(id!=""){
		CustomDialog.detail({id:id},function(data){
			$scope.prop=data;
			$scope.prop.displayfield=JSON.parse(data.displayfield);
	    	$scope.prop.resultfield=JSON.parse(data.resultfield);
	    	$scope.prop.sortfield=JSON.parse(data.sortfield);
	    	$scope.prop.conditionfield=JSON.parse(data.conditionfield);
    		
	    	//拼装paramValueString
	    	$scope.paramValueString="";
	    	for(var i=0;i<$scope.prop.conditionfield.length;i++){
	    		var c=$scope.prop.conditionfield[i];
	    		if(c.defaultType=="4"){
					if($scope.paramValueString!=""){
						$scope.paramValueString+="|";
	    			}
	    			$scope.paramValueString+=c.field+":"+'参数'+i;
	    		}
	    	}
	    	
	    	//拼装resultJson
	    	$scope.resultJson="{";
	    	for(var i=0;i<$scope.prop.resultfield.length;i++){
	    		var c=$scope.prop.resultfield[i];
				if($scope.resultJson!="{"){
					$scope.resultJson+=",";
    			}
    			$scope.resultJson+='"'+c.field+'"'+":"+'"'+c.field+'0"';
	    	}
	    	$scope.resultJson+="}";	
		});
	}
}]);