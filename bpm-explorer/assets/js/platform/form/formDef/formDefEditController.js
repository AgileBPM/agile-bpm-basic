var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	var ifremeCssUrlMap = {
		"pc" : '../../assets/js/plugins/ueditor/themes/pcframe.css',
		"pc_vue" : '../../assets/js/plugins/ueditor/themes/pcframe.css',
		"mobile" : '../../assets/js/plugins/ueditor/themes/mobileFormIframe.css',
		"pc_iview" : '../../assets/iview/css/iview.css',
	};

	$scope.init = function() {
		// uedtor的配置
		$scope.editorConfig = {
		//	toolbars : [ [ 'source', 'undo', 'redo', 'bold', 'italic', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|', 'selectTemplate' ] ],
			initialFrameHeight : window.innerHeight - 260,
			enableAutoSave : false,
			autoHeightEnabled : false,
			allHtmlEnabled : true,
			focus : true,
			iframeCssUrl : ifremeCssUrlMap[formType],// 加入css
		};
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		$scope.data.type = formType;
	});

	$scope.$on("afterSaveEvent", function(event, data) {
		if (window.opener && window.opener.reloadGrid) {
			window.opener.reloadGrid();
		}
		if (!data.r) {
			window.close();
		} else {
			window.location.reload();
		}
	});

	/**
	 * 预览
	 */
	$scope.preview = function(isBlank) {
		var previewUrlMap = {
			"pc" : 'formDefPreview.html',
			"pc_vue" : 'vueFormDefPreview.html',
			"pc_iview" : window.__ctx_pc_iview + '/#/bpm/form/preview',
			"mobile" : window.__ctx_mb + '/#/bpm/preview',
		};

		if (!previewUrlMap[formType]) {
			$.Dialog.alert(formType + "类型表单暂不支持预览");
			return;
		}
		if(isBlank){
			window.open(previewUrlMap[formType] + "?key=" + $scope.data.key,"_blank");
			return ;
		}

		var conf = {
			height : 0,
			title : "预览",
			url : previewUrlMap[formType] + "?key=" + $scope.data.key,
			passData : {
				html : $scope.data.html
			}
		};
		$.Dialog.open(conf);
	};

	$scope.$watch("data.boKey", function(newValue, oldValue) {
		if (!newValue || newValue === oldValue) {
			return;
		}
		// 加载boTree
		// 请求参数
		var params = {
			boKey : newValue
		};
		var callBack = function(event, treeId, treeNode) {

		};

		var url = __ctx + "/form/formDef/boTreeData";
		var ztreeCreator = new ZtreeCreator("boTree", url);
		ztreeCreator.setCallback({
			onClick : callBack
		});

		ztreeCreator.initZtree(params);
	});

	/**
	 * 获取获取备份表单信息
	 */
	$scope.getBackupHtml = function() {
		var url = __ctx + "/form/formDef/getBackupHtml";
		var defer = baseService.postForm(url, {
			id : $scope.data.id
		});
		$.getResultData(defer, function(data) {
			$.Toast.success("同步成功");
			$scope.data.html = data;
		});
	};

	/**
	 * 选择模板
	 */
	$scope.selectTemplate = function() {
		var conf = {
			height : 600,
			width : 800,
			url : "/form/formDef/selectTemplate.html?type=" + formType,
			title : "选择模板",
			topOpen : true,
			btn : true,
			closeBtn : 1,
		};
		conf.passData = {
			parentScope : $scope
		};
		conf.ok = function(index, innerWindow) {
			innerWindow.createHtml();
		};
		jQuery.Dialog.open(conf);
	};
} ]);
