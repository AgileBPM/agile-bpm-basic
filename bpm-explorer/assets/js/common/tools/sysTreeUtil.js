/**
 * 调用系统树结构的工具 支持指令式： 1:<div sysTree treeKey="ywbfl" callBack="callback"></div>
 * function callback(event, treeId, treeNode){}
 * 
 * 2 <div sysTreeGroup treeKey="ywbfl" ></div> 常用列表分组指令 可选属性 nodeKey：树节点
 * groupColumn：分组的字段，默认group_id_；rootName：前端增加定义的根节点名字
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
		var rootName = $(item).attr("rootName");
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
					if (treeNode.id == "-1") {
						params[groupColumn + "$VEQ"] = "";
					} else {
						params[groupColumn + "$VEQ"] = treeNode.id;
					}
					return params;
				}
			});

			if (document.getElementById(groupColumn + "$VEQ")) {
				document.getElementById(groupColumn + "$VEQ").value = treeNode.id;
			}
		}

		SysTree.loadTree(id, treeKey, nodeKey, callbackFun, rootName || "所有数据");
	});
});
/**
 * eg: html:<div class="ztree" id="sysTree"></div>
 * js:SysTree.loadTree("sysTree","treeKey",null,function(event,
 * treeId,treeNode)); id:div的id treeKey:树key nodeKey:节点id 可为null
 * callBack:回调函数,rootName:前端增加的根节点
 */
window.SysTree.loadTree = function(id, treeKey, nodeKey, callBack, rootName) {
	// 请求参数
	var params = {
		// 树id
		treeKey : treeKey
	};
	if (nodeKey) {
		params.nodeKey = nodeKey;
	}
	if (rootName) {
		params.rootName = rootName;
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
