angular.module('CustQueryService', ['base'])
.service('CustQuery', ['baseService','$q',function(baseService,$q){
	var _cachedcustQuery = {};
	
    var service = {
    		//通过别名获取自定义查询
    		get:function(alias){
    			var deferred = $q.defer();
    			if(alias){
    				if(_cachedcustQuery[alias]){
    					deferred.resolve(_cachedcustQuery[alias]);
    				}
    				else{
    					var url = __ctx +'/form/custQuery/getByAlias?alias='+alias;
        				baseService.get(url,function(data,status){
        					if(status=='200'){
        						deferred.resolve(data);
        						//缓存
        						_cachedcustQuery[data.alias] = data;
        					}
        					else{
        						deferred.reject();
        					}
        				});
    				}
    			}
    			else{
    				deferred.reject("必须传入自定义查询别名");
    			}
    			return deferred.promise;
    		},
    		detail : function(custQuery,callback){
    			if(!custQuery||!custQuery.id){
    				if(callback){
    					callback();
    				}
    				return;
    			}
    			baseService.postForm(__ctx +'/form/custQuery/getById',{id:custQuery.id}).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		},
    		//获取表或视图列表
    		getByDsObjectName:function(params,callback){
    			baseService.postForm(__ctx +'/form/custQuery/getByDsObjectName',params).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		},
    		//保存
    		save:function(custQuery,callback){
    			baseService.post(__ctx +'/form/custQuery/save',custQuery).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		},
    		//设置页面获取table字段
    		getTable:function(params,callback){
    			baseService.postForm(__ctx +'/form/custQuery/getTable',params).then(function(data){
    				if(callback){
    					callback(data);
    				}
    			});
    		},
    		search:function(params,callback){
    			baseService.postForm(__ctx +'/form/custQuery/doQuery',params).then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		},
    		getAll:function(callback){
    			baseService.get(__ctx +'/form/custQuery/getAll').then(function(data){
    				if(callback){
	    				callback(data);
	    			 }
    			});
    		}
        }
    return service;
}]);
