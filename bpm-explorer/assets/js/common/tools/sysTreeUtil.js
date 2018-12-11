/**
 * 调用系统树结构的工具 支持指令式： 1:<div sysTree treeKey="ywbfl" callBack="callback"></div>
 * function callback(event, treeId, treeNode){}
 * 
 * 2 <div sysTreeGroup treeKey="ywbfl" ></div> 常用列表分组指令
 * 可选属性
 * nodeKey：树节点
 * groupColumn：分组的字段，默认group_id_
 */
window.SysTree = {};
$(function() {
	// 处理树展示
	$("[sysTree]").each(function(index, item) {
		var id = $(item).attr("id");
		var treeKey = $(item).attr("treeKey");
		var nodeKey = $(item).attr("nodeKey");
		if (!id) {
			id = "tree_" + GetRandomStr(6);
			$(item).attr("id", id);
		}
		var callbackStr = $(item).attr("callBack");
		var callbackFun;
		if (callbackStr) {
			callbackFun = eval(callbackStr);
		}
		$(item).attr("class", "ztree");
		SysTree.loadTree(id, treeKey, nodeKey, callbackFun);
	});

	$("[sysTreeGroup]").each(function(index, item) {
		$(item).attr("class", "ztree");
		var id = $(item).attr("id");
		var treeKey = $(item).attr("treeKey");
		var nodeKey = $(item).attr("nodeKey");
		if (!id) {
			id = "tree_" + GetRandomStr(6);
			$(item).attr("id", id);
		}
		var groupColumn = $(item).attr("groupColumn");
		if (!groupColumn) {
			groupColumn = "group_id_";
		}
		
		var callbackFun = function(event, treeId, treeNode) {
			$("[ab-grid]").bootstrapTable("refreshOptions", {
				queryParams : function(params) {
					params[groupColumn + "^VEQ"] = treeNode.id;
					return params;
				}
			});
		}

		SysTree.loadTree(id, treeKey, nodeKey, callbackFun);
	});
});
/**
 * eg: html:<div class="ztree" id="sysTree"></div>
 * js:SysTree.loadTree("sysTree","treeKey",null,function(event,
 * treeId,treeNode)); id:div的id treeKey:树key nodeKey:节点id 可为null callBack:回调函数
 */
window.SysTree.loadTree = function(id, treeKey, nodeKey, callBack) {
	// 请求参数
	var params = {
		// 树id
		treeKey : treeKey
	};
	if (nodeKey) {
		params.nodeKey = nodeKey;
	}
	var url = __ctx + "/sys/sysTreeNode/getNodes";
	var ztreeCreator = new ZtreeCreator(id, url);
	if (callBack) {
		ztreeCreator.setCallback({
			onClick : callBack
		});
	}

	ztreeCreator.initZtree(params);
};
