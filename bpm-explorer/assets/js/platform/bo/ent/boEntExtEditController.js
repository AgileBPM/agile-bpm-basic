var  app = angular.module("app", ['base','formDirective','arrayToolService']);
app.controller('ctrl', ['$scope','baseService','ArrayToolService',function($scope,baseService,ArrayToolService){
	$scope.data={};
	$scope.data.status = "enabled";
	$scope.data.isExternal = "1";
	$scope.data.dsName="dataSource_Default";
	$scope.data.packageId = packageId;
	$scope.data.isCreateTable="1";
	$scope.data.attributeList=[];
	
	$scope.$on("afterSaveEvent",function(event,data){
		if(!data.r){
			HT.window.closeEdit(true);
		}else{
			window.location.reload();
		}
	});
	
	$scope.$on("afterLoadEvent",function(event,data){
		//加载属性列表
		var rtn=baseService.post("bOEnt/getAttrList?entId="+data.id);
  	  	rtn.then(function(data){
  			if(!data) return;
  			$scope.data.attributeList=data;
  	  		},function(status){
  	  			$.topCall.error("请求失败");
  	  		}
  	  	);
	});
	
	$scope.$on("beforeSaveEvent",function(event,data){
		if($scope.data.id) return;
		//提交前删除外键字段
		for(var i=0;i<$scope.data.attributeList.length;i++){
			if($scope.data.attributeList[i].name==$scope.data.fk){
				ArrayToolService.del(i,$scope.data.attributeList);
				break;
			}
		}
	});
	
	/**
	 * 获取外部数据源的表
	 */
	$scope.getExternalTable = function(){
		var rtn=baseService.postForm(__ctx +'/form/customQuery/getByDsObjectName',{dsalias:$scope.data.dsName,isTable:"1",objName:$scope.data.tableName});
		rtn.then(function(data){
  		  	$scope.externalTable= data;
		});
	};
	
	/**
	 * 获取外部数据源的表
	 */
	$scope.tableChange = function(){
		if(!$scope.data.tableName){
			return;
		}
		
		var rtn=baseService.postForm(__ctx +'/form/customQuery/getTable',{dsalias:$scope.data.dsName,isTable:"1",objName:$scope.data.tableName});
		rtn.then(function(data){
			if(!data){
				return;
			}
			$scope.data.pk = data.table.primayKey[0]["fieldName"];
			var pkType = data.table.primayKey[0]["columnType"];
			//主键类型不是字符串就是数字
			if(pkType.indexOf("varchar")>=0){
				$scope.data.pkType="varchar";
			}else{
				$scope.data.pkType="number";
			}
			$scope.data.attributeList=[];//重置字段
			//拼装成字段
			$(data.table.columnList).each(function(){
				if(this.isPk) return;//主键不展示
				
				var attr = {};
				attr.desc = this.comment;
				attr.name = this.fieldName;
				attr.fieldName = this.fieldName;
				attr.isRequired = this.isNull?"0":"1";
				attr.dataType = this.columnType;
				if(this.columnType=="number"){
					attr.attrLength = this.intLen;
				}else{
					attr.attrLength = this.charLen;
				}
				attr.defaultValue = this.defaultValue;
				$scope.data.attributeList.push(attr);
			});
			
		});
	};
}]);