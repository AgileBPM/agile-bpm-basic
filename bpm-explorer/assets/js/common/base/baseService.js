var base = angular.module("base", [])

.config(function($httpProvider) {     
	$httpProvider.interceptors.push(function() {
	    return {
	      'request':function(config){
	    	  return config;
	      },
	      'response': function(response) {
	    	  if(response.data && !response.data.isOk && response.data.code==="401" && window.location.href.indexOf("index") == -1 && (!window.location.href.endWith || !window.location.href.endWith("bpm-explorer/"))){
	    		  jQuery.Toast.error("登录超时，请重新登录");
	    		  console.info(response.data);
	    		  console.info(window.location.href);
			  }
	    	  if(response.data && !response.data.isOk && response.data.code==="403" ){
	    		  jQuery.Toast.error("访问受限! "+response.data.msg);
			  }
	         return response;
	      }
	    };
	});
	$httpProvider.defaults.withCredentials = true;
});

// ifream 外链的时候 特殊处理
base.filter('trustedAsResourceUrl', ['$sce', function ($sce) {
    return function(url) {
        return $sce.trustAsResourceUrl(url);
    };
}]);
 

/**
 * 表单提交，将json转成 name=abc&age=19这种格式。
 */
base.factory("$jsonToFormData",function() {
	function transformRequest( data, getHeaders ){
		var headers = getHeaders();
		headers["Content-Type"] = "application/x-www-form-urlencoded; charset=utf-8";
		if(typeof( data )=="string"){
			return data;
		}
		return $.param(data);
	}
	return( transformRequest );
})
.service('baseService', ['$http','$q','$jsonToFormData', function($http,$q,$jsonToFormData) {
    var service = {
    		/**
    		 * get请求，输入url。
    		 * 调用方法：
    		 * var def=baseService.get(url);
    		 * def.then(
    		 * 	function(data){},
    		 * 	function(){});
    		 */
    		get:function(url){
    			var deferred = $q.defer();
    			$http.get(url).success(function(data,status){
    				deferred.resolve(data);
        		})
        		.error(function(data,status){
        			deferred.reject(status);
        		});
    			return deferred.promise;
    		},
    		/**
    		 * 数据使用表单键值对的方式提交。
    		 * 在post数据时使用www-form-urlencoded方式提交。
    		 * 发送数据方式：
    		 * 1.构建键值对
    		 * 	var str="name=tony&age=19";
    		 *  postForm(user,str);
    		 * 2.构建简单json对象
    		 *  var obj={name:"tony",age:19};
    		 *  postForm(user,obj);
    		 *  
    		 *  数据处理方式：
    		 *  var obj=baseService.postForm(url,params);
    		 *  obj.then(
    		 *  	//调用成功时处理，服务器返回状态为200时。
    		 *  	function(data){
    		 *  	},
    		 *  	//调用失败时处理，服务器返回状态 为400或500.
    		 * 		function(status){
    		 *  });
    		 *  
    		 */
    		postForm:function(url,param){
    			var deferred = $q.defer();
    			$http.post(url,param,{transformRequest:$jsonToFormData})
				 .success(function(data,status){
					 deferred.resolve(data);
				 })
				 .error(function(data,status){
					 deferred.reject(status);
				 });
    			 return deferred.promise;
    		},
    		/**
    		 * 将数据直接post到指定的URL。
    		 * 服务端接收方法：
    		 * 1.复杂对象。
    		 * var  obj={name:"zyg",age:"19",friends:[{name:"laoli",sex:"male"},{name:"tony",sex:"male"}]};
    		 * 使用：
    		 * 	public void demo1(HttpServletRequest request, HttpServletResponse response,@RequestBody User user) throws IOException 
    		 * 2.直接读取流的方式。
    		 * 	String str=FileUtil.inputStream2String(request.getInputStream());
			 *	User user= JSONObject.parseObject(str, User.class);
    		 */
    		post:function(url,param){
    			var deferred = $q.defer();
    			$http.post(url,param)
				 .success(function(data,status){
					 deferred.resolve(data);
				 })
				 .error(function(data,status){
					 deferred.reject(status);
				 });
    			 return deferred.promise;
    		}
        }
    return service;
}])
/**
 * 数组操作。
 */
.factory("commonService",function() {
		return {
			/**
			 * 判断数组中是否包含指定的值。
			 * 判定aryChecked数组中是否val。
			 */
	 		isChecked:function(val,aryChecked){
	 			for(var i=0;i<aryChecked.length;i++){
	 				if(val==aryChecked[i])	return true;
	 			}
	    		return false;
	    	},
	    	/**
	    	 * 判断数组中是否包含数据。
	    	 * name : 属性名
	    	 * val : 值
	    	 * aryChecked : 列表数据。
	    	 */
	    	isExist:function(name,val,aryChecked){
	 			for(var i=0;i<aryChecked.length;i++){
	 				var obj=aryChecked[i];
	 				if(obj[name]==val) return true;
	 			}
	    		return false;
	    	},
	    	
	    	/**
	    	 * 数组操作。
	    	 * val:当前的值。
	    	 * checked:当前的值是否选中。
	    	 * aryChecked:选中的数据。
	    	 */
	    	operatorAry:function(val,checked,aryChecked){
	    		//判断指定的值在数组中存在。
	    		var isExist=this.isChecked(val,aryChecked);
	    		//如果当前数据选中，并且不存在，那么在数组中添加这个值。
	    		if(checked && !isExist){
	    			aryChecked.push(val);
	    		}
	    		//如果当前值没有选中，并且这个值在数组中存在，那么删除此值。
	    		else if(!checked && isExist){
					for(var i=0;i<aryChecked.length;i++){
						val==aryChecked[i] && aryChecked.splice(i,1);
		 			}
	    		}
	    	},
	    	/**
	    	 * 根据指定的值在json数组中查找对应的json对象。
	    	 * val ：指定的值。
	    	 * aryJson ：数据如下 
	    	 * [{val:1,name:""},{val:2,name:""}]
	    	 */
	    	getJson:function (val,aryJson){
	    		for(var i=0;i<aryJson.length;i++){
	    			var obj=aryJson[i];
	    			if(obj.val==val){
	    				return obj;
	    			}
	    		}
	    		return null;
	    	}
 		}
})
/**
 * 绑定angularjs模版html指令。
 * 这个指定用于将angularjs字符串模版，绑定到dom中，方便数据绑定。
 * 使用用法：
 * <div id="formHtml" ab-bind-html="formHtml" ></div>
 * 
 * 控制器调用方法如下：
 * var app=angular.module("app",["directive"]);
		app.controller('ctrl', ['$scope',function($scope){
			$scope.formHtml='<div>angular模版</div>';
		}])
 */
.directive('abBindHtml', function($compile) {
	return {
		restrict : 'A',
		link : function(scope, elm, attrs) {
			scope.unWatch = scope.$watch(attrs.abBindHtml, function(newVal, oldVal) {
                if (!elm.data('unbindWatchTag')) {
                    if(newVal){
                        elm.data('unbindWatchTag',true);
                        elm.html(newVal);
                        scope.htmlFn&&scope.htmlFn.call();
                        $compile(elm)(scope);
                        
                        //前端JS扩展使用。
        				if(window.ngFormReady){
        					window.setTimeout(window.ngFormReady,10,scope);
        				}
        				//html加载后发布事件
        				scope.$emit('afterBindHtmlEvent');
                    }
                    else{
                        //避免重复添加监视
                        elm.data('unbindWatchTag')&&scope.unWatch();
                    }
                }
            });
		}
	};
})
.filter('isEmpty', function () {
        var bar = "";
        return function (obj) {
            for (bar in obj) {
                if (obj.hasOwnProperty(bar)) {
                    return false;
                }
            }
            return true;
        };
})
.filter('htTime', function () {
	//毫秒转换成 **天**小时**分**秒的格式
	return function (input) {
        var day = (input / 1000 / 60 / 60 / 24) << 0
        	hour = (input / 1000 / 60 /60) % 24 << 0,
        	min = (input / 1000 / 60) % 60 << 0,
            sec = Math.round((input / 1000) % 60),
            result = [];
        
        if(day){
        	result.push(day + '天');
        }
        if(hour){
        	result.push(hour+'小时');
        }
        if(min){
        	result.push(min+'分');
        }
        if(!isNaN(sec)&&sec){
        	result.push(sec+'秒');
        }
        return result.join('');
    };
})

.service('ArrayToolService', [function() {
    var service = {
    		//上移按钮
	    	up:function(idx,list){
	    		if(idx<1){
	    			return;
	    		}
	    		var t=list[idx-1];
	    		list[idx-1]=list[idx];
	    		list[idx]=t;
	    	},
	    	//下移按钮
	    	down:function(idx,list){
	    		if(idx>=list.length-1){
	    			return;
	    		}
	    		var t=list[idx+1];
	    		list[idx+1]=list[idx];
	    		list[idx]=t;
	    	},
	    	resumeSn:function(list){
	    		for(var k = 0 ; k < list.length ; k++){
	    			list[k].sn = k;
				}
	    		return list;
	    	},
	    	/**
	    	 * idx 原位置
	    	 * num 目标位置
	    	 * list 数组
	    	 */
	    	moveToNum:function(idx,target,list){
	    		if(target==-1){
	    			target = 0;
	    		}else if(idx >= target){
	    			target = target+1;
	    		}
	    		var t= list.splice(idx,1);
	    		list.insert(target,t[0]);
	    		this.resumeSn(list);
	    	},
	    	//默认ngModel turnToIndex
	    	turnTo:function(rowScope,list){
	    		var toIndex =rowScope.turnToIndex - 1;
	    		if(!rowScope.turnToIndex || toIndex<0 || toIndex>=list.length) return; 
	    		
	    		var index = rowScope.$index;
	    		if(toIndex == index) return;
	    		
	    		var row= list.splice(index,1);
	    		list.insert(toIndex,row[0]);
	    		
	    		rowScope.turnToIndex= "";
	    	},
	    	//删除按钮
	    	del:function(idx,list){
	    		list.splice(idx,1);
	    	},
	    	//找到指定元素的未知
	    	idxOf:function(val,list){
	    		for (var i = 0; i < list.length; i++) {  
	    	        if (list[i] == val) return i;  
	    	    }  
	    	    return -1; 
	    	},
	    	//删除指定元素
	    	remove:function(val,list){
	    		var idx = this.idxOf(val,list);  
	    	    if (idx > -1) {  
	    	    	list.splice(idx, 1);  
	    	    }  
	    	},
	    	//置顶
	    	top:function(idx,list){
	    		if(idx>=list.length || idx<1){
	    	           return;
	    		}
	    		//逐个交换
	            for(var i=0;i<idx;i++){
		            var temp=list[i];
		            list[i]=list[idx];
		            list[idx]=temp;
	            }
	    	},
	    	//置底
	    	bottom:function(idx,list){
	    		if(idx>=list.length-1 || idx<0){
	                return;
	            }
	            //逐个交换
                for(var i=list.length-1;i>idx;i--){
	                var temp=list[i];
	                list[i]=list[idx];
	                list[idx]=temp;
                }
	    	}
    };
    return service;
}]);


/**
 * 外部JS和angular 交互类。
 * 主要包括两个方法：
 * 1.获取当前上下文的scope。
 * 2.设置修改后的scope。
 */
window.AngularUtil={};
window.ngUtil = AngularUtil;

/**
 * 获取当前Angularjs scope 。
 */
AngularUtil.getScope=function(){
	return angular.element(jQuery("[ng-controller]")[0]).scope();
}

/**
 * 保存外部js对scope的修改。
 */
AngularUtil.setData=function(scope){
	!scope.$$phase && scope.$digest();
};


/**
 * 获取当前环境中的 service
 * serviceName：指定的服务名称。
 * 这里需要注意的是，只能获取当前ng-controller注入模块中的service。
 */
AngularUtil.getService = function(serviceName){
	if(!this.$injector){
		this.$injector =angular.element($("[ng-controller]")).injector();
	}
	if(this.$injector.has(serviceName)) {
		return this.$injector.get(serviceName);
	}
	else {
		alert(serviceName+"angular环境中没有找到该service！");
	}
};
/**
 * 根据jquery表达式获取指定元素上的控件值。
 * 比如:获取id为 userId的控件的值。
 * var userId=CustForm.getModelVal("#userId");
 */
AngularUtil.getModelVal = function(element){
	var inputCtrl = $(element).data("$ngModelController");
	return inputCtrl.$modelValue;
};