/**
 * ToolsController中的方法的快速调用工具类
 */
var ToolsController = window.ToolsController = {};
/**
 * <pre>
 * 根据一个枚举类的路径获取这个枚举的json形式，供前端使用
 * 使用例子:
 * ToolsController.getEnum('com.dstz.sys.persistence.enums.FieldControlType').then(function(data) {
 * 	$scope.FieldControlType = data; 
 * });
 * ToolsController.getEnum('com.dstz.base.db.model.Column$TYPE').then(function(data) {
 * 	$scope.ColumnTYPE = data; 
 * });
 * 注意！！如果枚举在类中间，那么路径如下：com.dstz.base.db.model.Column$TYPE
 * </pre>
 */
ToolsController.getEnum = function(path, listMode) {
	var deferred = $.Deferred();
	var data = {
		path : path,
		listMode : listMode
	};
	if (!listMode) {
		data.listMode = false;
	}

	$.ajax({
		url : __ctx + "/sys/tools/getEnum",
		type : 'POST',
		dataType : 'json',
		data : data,
		error : function(data, status) {
			deferred.reject(status);
		},
		success : function(data) {
			deferred.resolve(data);
		}
	});
	return deferred.promise();
};

ToolsController.getConstant = function(path, key) {
	var deferred = $.Deferred();
	$.ajax({
		url : __ctx + "/sys/tools/getConstant",
		type : 'POST',
		dataType : 'json',
		data : {
			path : path,
			key : key ? key : ""
		},
		error : function(data, status) {
			deferred.reject(status);
		},
		success : function(data) {
			deferred.resolve(data);
		}
	});
	return deferred.promise();
};

/**
 * 获取系统bean容器中的指定实现类map
 */
ToolsController.getInterFaceImpls = function(classPath) {
	var deferred = $.Deferred();
	$.ajax({
		url : __ctx + "/sys/tools/getInterFaceImpls",
		type : 'POST',
		dataType : 'json',
		data : {
			classPath : classPath
		},
		error : function(data, status) {
			deferred.reject(status);
		},
		success : function(data) {
			deferred.resolve(data);
		}
	});
	return deferred.promise();
};