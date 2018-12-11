
var dragModle = angular.module('dragDivTreeModel', ['base']);


dragModle.service('dragService', ['$rootScope','baseService', function($rootScope,baseService) {
	var treeParamDef  = {idKey:"id",nameKey:"name",pIdKey:"parentId"};
	var service ={
			errorMsg: "请选择正确目标！",
			curTarget: null,
			curTmpTarget: null,
			noSel: function() {
				try {
					window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
				} catch(e){}
			},
			dragTree2Dom: function(treeId, treeNodes) {
				return !treeNodes[0].isParent;
			},
			prevTree: function(treeId, treeNodes, targetNode) {
				return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
			},
			nextTree: function(treeId, treeNodes, targetNode) {
				return !targetNode.isParent && targetNode.parentTId == treeNodes[0].parentTId;
			},
			innerTree: function(treeId, treeNodes, targetNode) {
				return targetNode!=null && targetNode.isParent && targetNode.tId == treeNodes[0].parentTId;
			},
			dragMove: function(e, treeId, treeNodes) {
				var p = null, pId = 'dom_' + treeNodes[0][treeParamDef.pIdKey]; //pId
				if (e.target[treeParamDef.pIdKey] == pId) {
					p = $(e.target);
				} else {
					p = $(e.target).parent('#' + pId);
					if (!p.get(0)) {
						p = null;
					}
				}

				$('#'+service[treeId]+'.active',$("#"+service[treeId])).removeClass('active');
				if (p) {
					p.addClass('active');
				}
			},
			dropTree2Dom: function(e, treeId, treeNodes, targetNode, moveType) {
				var domId = treeNodes[0][treeParamDef.idKey];
				var target =$("[domId='"+domId+"']",$("#"+service[treeId]));
				if(target.length>0 && treeNodes[0].domNode){  
					target.removeClass("domBtn_Disabled");
					target.addClass("domBtn");
					var zTree = $.fn.zTree.getZTreeObj(treeId)
					zTree.removeNode(treeNodes[0]);
					service.updateType(treeId);
				}
				
			},
			dom2Tree: function(e, treeId, treeNode) {
				var target = service.curTarget, tmpTarget = service.curTmpTarget;
				if (!target) return;
				var zTree = $.fn.zTree.getZTreeObj(treeId), parentNode;
				//可以添加的父节点
				if (treeNode != null &&!treeNode.domNode) { 
					parentNode =treeNode;
				} 

				if (tmpTarget) tmpTarget.remove();
				if (!!parentNode) {
					var newNode = {domNode:true};  newNode[treeParamDef.idKey]=target.attr("domId"); newNode[treeParamDef.nameKey] = target.text();
					var nodes = zTree.addNodes(parentNode, newNode);
					zTree.selectNode(nodes[0]);
				} else {
					target.removeClass("domBtn_Disabled");
					target.addClass("domBtn");
					alert(service.errorMsg);
				}
				service.updateType(treeId);
				service.curTarget = null;
				service.curTmpTarget = null;
			},
			updateType: function(ztreeId) {
				var zTree = $.fn.zTree.getZTreeObj(ztreeId),
				nodes = zTree.getNodes();
				for (var i=0, l=nodes.length; i<l; i++) {
					var num = nodes[i].children ? nodes[i].children.length : 0;
					nodes[i][treeParamDef.nameKey] = nodes[i][treeParamDef.nameKey].replace(/ \(.*\)/gi, "") + " (" + num + ")";
					zTree.updateNode(nodes[i]);
				}
			},
			bindDom: function(treeId) {
				$("#"+service[treeId]).bind("mousedown", service.bindMouseDown);
			},
			bindMouseDown: function(e) {
				var target = e.target;
				if (target!=null && $(target).hasClass("domBtn")) {
					var doc = $(document), target = $(target),
					docScrollTop = doc.scrollTop(),
					docScrollLeft = doc.scrollLeft();
					target.addClass("domBtn_Disabled");
					target.removeClass("domBtn");
					curDom = $("<span class='dom_tmp domBtn'>" + target.text() + "</span>");
					curDom.appendTo("body");

					curDom.css({
						"top": (e.clientY + docScrollTop + 3) + "px",
						"left": (e.clientX + docScrollLeft + 3) + "px"
					});
					service.curTarget = target;
					service.curTmpTarget = curDom;

					doc.bind("mousemove", service.bindMouseMove);
					doc.bind("mouseup", service.bindMouseUp);
					doc.bind("selectstart", service.docSelect);
				}
				if(e.preventDefault) {
					e.preventDefault();
				}
			},
			bindMouseMove: function(e) {
				service.noSel();
				var doc = $(document), 
				docScrollTop = doc.scrollTop(),
				docScrollLeft = doc.scrollLeft(),
				tmpTarget = service.curTmpTarget;
				if (tmpTarget) {
					tmpTarget.css({
						"top": (e.clientY + docScrollTop + 3) + "px",
						"left": (e.clientX + docScrollLeft + 3) + "px"
					});
				}
				return false;
			},
			bindMouseUp: function(e) {
				var doc = $(document);
				doc.unbind("mousemove", service.bindMouseMove);
				doc.unbind("mouseup", service.bindMouseUp);
				doc.unbind("selectstart", service.docSelect);

				var target = service.curTarget, tmpTarget = service.curTmpTarget;
				if (tmpTarget) tmpTarget.remove();
				if ($(e.target).parents(".ztree").length == 0) {
					if (target) {
						target.removeClass("domBtn_Disabled");
						target.addClass("domBtn");
					}
					service.curTarget = null;
					service.curTmpTarget = null;
				}
			},
			bindSelect: function() {
				return false;
			},
			setTreeParamDef:function(param){
				treeParamDef = param;
				setting.data.simpleData.idKey=treeParamDef.idKey;
				setting.data.simpleData.pIdKey=treeParamDef.pIdKey;
				setting.data.key.name=treeParamDef.nameKey;
			},
			innit: function(ztreeId,domId,zNodes){
				$("span.domBtn_Disabled",$("#"+domId)).removeClass("domBtn_Disabled").addClass("domBtn");
				service[ztreeId] = domId;
				$.fn.zTree.init($("#"+ztreeId), setting, zNodes);
				service.updateType(ztreeId);
				service.bindDom(ztreeId);
			}
		};
	
	var setting = {
	    edit: {
	        enable: true,
	        showRemoveBtn: false,
	        showRenameBtn: false,
	        drag: {
	            
	        }
	    },
	    data: {
	    	simpleData:{
	    		idKey:treeParamDef.idKey,
	    		pIdKey:treeParamDef.pIdKey
	    	},
	    	key:{name:treeParamDef.nameKey},
	        keep: {
	            parent: true,
	            leaf: false
	        },
	        simpleData: {
	            enable: true
	        }
	    },
	    callback:{
			beforeDrag: service.dragTree2Dom,
			onDrop: service.dropTree2Dom,
			onDragMove: service.dragMove,
			onMouseUp: service.dom2Tree
		},
	    view: {
	        selectedMulti: false
	    }
	};
	
	return service;
}])

