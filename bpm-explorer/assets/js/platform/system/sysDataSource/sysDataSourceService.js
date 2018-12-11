angular.module('SysDataSourceService', ['base'])
.service('SysDataSource', ['$rootScope','baseService', function($rootScope,BaseService) {
    var service = {    		
    		detail : function(SysDataSource,callback){
    			if(!SysDataSource||!SysDataSource.id){
    				if(callback){
    					callback();
    				}
    				return;
    			}
    			BaseService.post(__ctx +'/system/sysDataSource/getById',{id:SysDataSource.id}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		},
    		//获取在容器的数据源
    		getDataSourcesInBean:function(callback){
    			BaseService.post(__ctx +'/system/sysDataSource/getDataSourcesInBean',{}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		}
        }
    return service;
}]);
