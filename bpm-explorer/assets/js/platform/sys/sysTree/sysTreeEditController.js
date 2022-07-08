var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', '$filter', function($scope, baseService, ArrayToolService, $filter) {
	var filter = $filter('filter');
	$scope.ArrayTool = ArrayToolService;

	$scope.init = function() {
		// 初始化数据
		$scope.data = {};
		$scope.data.system = false;
	};

	$scope.$on("afterLoadEvent", function(event, data) {
		initActiveTreeNode();
		$scope.loadTree();
	});

	$scope.$on("afterSaveEvent", function(event, result) {
		$scope.$apply(function() {
			$scope.data = result.data;
			initActiveTreeNode();
		});
		$scope.loadTree();
	});

	/**
	 * 保存节点
	 */
	$scope.saveNode = function() {
		var url = __ctx + "/sys/sysTreeNode/save";
		if (!$scope.activeTreeNode.key || !$scope.activeTreeNode.name) {
			jQuery.Toast.error("请完善节点名字和别名信息");
			return;
		}

		var rtn = baseService.post(url, $scope.activeTreeNode);
		rtn.then(function(data) {
			if (data.isOk) {
				jQuery.Toast.success(data.msg);
				$scope.activeTreeNode.id = data.data.id;
				$scope.loadTree();
			} else {
				jQuery.Toast.error(data.msg, data.cause);
			}
		}, function(errorCode) {
			jQuery.Toast.error("请求失败!" + errorCode);
		});
	};

	/**
	 * 初始化当前编辑节点
	 */
	function initActiveTreeNode() {
		$scope.activeTreeNode = {};
		$scope.activeTreeNode.parentName = "无";
		$scope.activeTreeNode.treeId = $scope.data.id;
		$scope.activeTreeNode.parentId = "0";// 默认0
	}

	// 以下代码是跟ztree相关的--------------------------》》》
	/**
	 * 加载树
	 */
	$scope.loadTree = function() {
		// 请求参数
		var params = {
			// 树id
			treeId : $scope.data.id
		};
		var url = __ctx + "/sys/sysTreeNode/getNodes";
		new ZtreeCreator('sysTree', url).setCallback({
			onClick : zTreeOnLeftClick,
			onRightClick : zTreeOnRightClick,
			onMouseDown : zTreeOnMouseDown
		}).initZtree(params, function(treeObj) {
			$scope.sysTree = treeObj
		});
	};

	/**
	 * 树左击事件
	 */
	function zTreeOnLeftClick(event, treeId, treeNode) {
		var parentNode = treeNode.getParentNode();
		$scope.$apply(function() {
			$scope.activeTreeNode.parentName = parentNode ? parentNode.name : "无";
			$scope.activeTreeNode.id = treeNode.id;
			$scope.activeTreeNode.key = treeNode.key;
			$scope.activeTreeNode.name = treeNode.name;
			$scope.activeTreeNode.desc = treeNode.desc;
			$scope.activeTreeNode.treeId = treeNode.treeId;
			$scope.activeTreeNode.parentId = treeNode.parentId;
			$scope.activeTreeNode.path = treeNode.path;
			$scope.activeTreeNode.sn = treeNode.sn;
		});
	}

	/**
	 * 树右击事件
	 */
	function zTreeOnRightClick(event, treeId, treeNode) {
		if (!treeNode) {
			return;
		}
		$scope.sysTree.selectNode(treeNode);
		var h = $(window).height();
		var w = $(window).width();
		var menuWidth = 120;
		var menuHeight = 75;
		var menu = $('#treeMenu');
		var x = event.pageX, y = event.pageY;
		if (event.pageY + menuHeight > h) {
			y = event.pageY - menuHeight;
		}
		if (event.pageX + menuWidth > w) {
			x = event.pageX - menuWidth;
		}
		menu.menu('show', {
			left : x,
			top : y
		});
	}

	function zTreeOnMouseDown(event, treeId, treeNode) {
		if (treeNode) {
			return;
		}
		$scope.$apply(function() {
			initActiveTreeNode();
		});
	}

	/**
	 * 菜单增加节点
	 */
	$scope.addNode = function() {
		// 当前节点
		var node = $scope.sysTree.getSelectedNodes()[0];
		$scope.$apply(function() {
			$scope.activeTreeNode = {};
			$scope.activeTreeNode.treeId = $scope.data.id;
			$scope.activeTreeNode.parentId = node.id;
			$scope.activeTreeNode.parentName = node.name;
			$scope.activeTreeNode.path = node.path;
		});
	};

	/**
	 * 菜单删除节点
	 */
	$scope.removeNode = function() {
		// 当前节点
		var node = $scope.sysTree.getSelectedNodes()[0];
		var url = __ctx + "/sys/sysTreeNode/remove";
		var rtn = baseService.postForm(url, {
			id : node.id
		});
		rtn.then(function(data) {
			if (data.isOk) {
				jQuery.Toast.success(data.msg);
				$scope.loadTree();
			} else {
				jQuery.Toast.error(data.msg, data.cause);
			}
		}, function(errorCode) {
			jQuery.Toast.error("请求失败!" + errorCode);
		});
	};

} ]);
