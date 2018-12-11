var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;
	
	var type = formType ? formType : "pc";
	var ifremeCssUrl = type==='pc' ? '../../assets/js/plugins/ueditor/themes/pcframe.css' : '../../assets/js/plugins/ueditor/themes/mobileFormIframe.css';
	
	$scope.init = function() {
		// uedtor的配置
		$scope.editorConfig = {
			toolbars : [ [ 'source', 'undo', 'redo', 'bold', 'italic', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', '|', 'selectTemplate' ] ],
			initialFrameHeight : window.innerHeight - 260,
			enableAutoSave : false,
			autoHeightEnabled : false,
			allHtmlEnabled : true,
			focus : true,
			iframeCssUrl : ifremeCssUrl,// 加入css
		};
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		$scope.data.type = type;
	});
	
	$scope.$on("afterSaveEvent", function(event, data) {
		if(window.opener && window.opener.reloadGrid){
			window.opener.reloadGrid();
		}
		if (!data.r) {
			window.close();
		}else{
			window.location.reload();
		}
	});

	/**
	 * 预览
	 */
	$scope.preview = function() {
		var conf = {
			height : 0,
			title : "预览",
			url : "formDefPreview.html?key=" + $scope.data.key,
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
	$scope.selectTemplate = function(type) {
		if(!type)type="pc";
		var conf = {
			height : 600,
			width : 800,
			url : "/form/formDef/selectTemplate.html?type="+type,// url不为空则使用iframe类型对话框
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
