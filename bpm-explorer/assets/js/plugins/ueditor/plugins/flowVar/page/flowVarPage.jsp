<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html ng-app="baseServices">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/dialogs/internal.js"></script>
		<script type="text/javascript">
		var defId="${param.defId}";
		 
		var varTree;
 
		var nodeId ; 
		$(function() {
			var conf ={};
			defId =defId;
			nodeId = "BB";
			loadGroupsTree();
			
			changeType();
		});
		function loadGroupsTree(){
			var setting = {
				data: {
					key : {
						name: "name",
					},
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId"
					}
				},
				async: {enable: false},
				view: {
					selectedMulti: true,
					showIconFont:true,
				},
				onRightClick: false,
				onClick:false,
				callback:{
					beforeClick: beforeClick,
					onClick: onClickTree
				}
			};	
			var params={'defId':defId,'nodeId':nodeId};
			$.post(__ctx+"/flow/node/flowVarJson",params,function(result){
				varTree=$.fn.zTree.init($("#varTree"), setting,result); 
				varTree.expandAll(true);
			});
		}
		function beforeClick(treeId,node){
			if(node.type =='bo' || node.type =='root') return false;
		}
		
		function onClickTree(e,treeId,node){
			if(node.fromType == 'boAttr'){
				$("#description").text(node.description);
				$("#name").val(node.code+"."+node.name);
				$("#source").val('BO');
			}else if (node.fromType == 'var'){
				$("#description").text(node.description);
				$("#name").val(node.varKey);
				$("#source").val('flowVar');
			}
		}
		function changeType(){
			var selectType = $("#executorType").val();
			var toHide = selectType == 'user';
			$("#userValTr").attr('hidden',!toHide);
			$("#groupValTr").attr('hidden',toHide);  
		}
		function getVarJson(){
			var frm = $("#formaaa").form();
			if(!frm.valid()) return false;
			var varJson = { source:$("#source").val(),
							name:$("#name").val(),
							executorType:$("#executorType").val(),
							};
			if(varJson.executorType == 'group'){
				varJson.groupValType=$("#groupValType").val(); 
				varJson.dimension=$("#dimension").val();
			}else{
				varJson.userValType=$("#userValType").val();
			}
			return varJson;
			
		}
		</script>
		
	</head>
	<body class="easyui-layout">
		<div  data-options="region:'west',border:true,headerCls:'background-color:gray'"  style=" text-align:center;width:200px;" >
			<div id="varTree" class="ztree" ></div>
		</div>
    	
		<div data-options="region:'center',border:true"  style="text-align:center;width:400px"  >
		<form id="formaaa"> 
			<table class="table" style="width:99.8% !important;">	

				<tr>
					 <th> 标识：  </th>
			         <td style="text-align:left;">
			       		<input name="name" id="name" readonly="readonly" class="inputText" style="width: 90%" validate="{required:true}">
			        	<input type="hidden" id="source" name="source">
			         </td>
				</tr>
				
				 <tr>
			         <th> 描述：  </th>
			         <td style="text-align:left;">
			            	<span id="description"></span>
			         </td>
				</tr>	 
			</table>
			</form>
		</div>
	</body>
</html>
<script>
    //可以直接使用以下全局变量
    //当前打开dialog的实例变量
    //console.info('editor: '+editor);
    //一些常用工具
    //console.info('domUtils: '+domUtils);
    //console.info('utils: '+utils);
    //console.info('browser: '+browser);
    //console.info('dialog: '+dialog);
    dialog.onok = function(){
    	var name=document.getElementById("name").value;
    	if(name=="")
    	{
    	    alert("请选择流程变量");
    	    return false;
    	}
    	editor.execCommand('inserthtml',"{"+name+"}");
	}
</script>
