angular.module('CustDialogService', [ 'base' ])
.service('CustDialog', [ 'baseService', function(baseService) {
	var service = {
		detail : function(CustDialog, callback) {
			if (!CustDialog || !CustDialog.id) {
				if (callback) {
					callback();
				}
				return;
			}
			// 获取CustDialog的详情
			var defer = baseService.postForm(__ctx + '/form/custDialog/getById', {
				id : CustDialog.id
			});
			
			if (!callback)	return ;
			
			$.getResultData(defer,function(data){
				callback(data);
			});
		},
		getByAlias : function(alias, callback) {
			// 获取CustDialog的详情
			var defer = baseService.postForm(__ctx + '/form/custDialog/getByAlias', {
				alias : alias
			});
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		// 保存
		save : function(CustDialog, callback) {

			var defer = baseService.post(__ctx + '/form/custDialog/save', CustDialog);
			$.getResultData(defer,callback);
		},
		// 查询所有
		getAll : function(callback) {
			var defer = baseService.get(__ctx + '/form/custDialog/getAll');
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		// 查询所有选择器
		getSelectorAll:function(callback) {
			var defer = baseService.get(__ctx + '/form/selectorDef/getAll');
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		},
		
		// 根据custDialog对象查询数据结果
		search : function(params, callback) {
			var defer = baseService.postForm(__ctx + '/form/custDialog/doQuery', params);
			defer.then(function(data) {
				if (callback) {
					callback(data);
				}
			}, function(status) {
			});
		}
		,custDialog:function(parm){
			CustDialog.openCustDialog(parm);
		}
	}
	return service;
} ]);
