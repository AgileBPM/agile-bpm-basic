<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/dialog/iconDialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/plugins/customButton/page/js/Controller.js"></script>
		<script type="text/javascript" src="${ctx}/js/platform/system/customDialog/customDialogService.js"></script>
		<script type="text/javascript" src="${ctx}/js/angular/form/service/BpmFormService.js"></script>
		<script type="text/javascript">
			var formId="${param.formId}";
		</script>
	</head>
	
	<body ng-app="app" ng-controller="Controller">
		<fieldset> 
		   	<legend>按钮设置</legend>
		   	<table class="table-form">
		   		<tr><th>按钮标题：</th><td><input ng-model="prop.title" class="inputText" type="text"/></td></tr>
		   		<tr>
		   			<th>按钮图标：</th>
		   			<td>
		   				<i class="{{prop.img}}"></i>
		   				<!--input ng-model="prop.img" class="inputText" type="hidden"/-->
		   				<a class="btn btn-info btn-sm fa-search-plus" href="#" ng-click="showIcon();">选择</a>
		   			</td>
		   		</tr>
		   	</table>
		</fieldset>
		
		<fieldset>
		   	<legend>对话框设置</legend>
		   	<table class="table-form">
		   		<tr>
		   			<th>自定义对话框：</th>
		   			<td>
		   				<select class="inputText" ng-model="prop.cid" ng-change="changeCid(prop.cid)" ng-options="m.id as m.name for m in customDialogs">
						</select>
					</td>
				</tr>
			</table>
			<table class="table-form" ng-if="selectedCustomDialog!=null">
				<tr>
		   			<th style="width: 50%;">对话框参数</th>
		   			<th style="text-align: left;width: 50%;">表单字段</th>
		   		</tr>
				<tr ng-repeat="field in selectedCustomDialog.conditionfield|filter:filterConditionfield">
		   			<th>{{field.comment}}</th>
		   			<td>
		   				<!-- 条件字段不能用子表数据，因为子表数据可能是多个 -->
		   				<select class="inputText" ng-model="field.target" ng-options="(m.path+'.'+m.name) as (m.desc!=null?m.desc:m.name) for m in form.bpmFormFieldList |filter:filterFormFieldList">
							<option value="">(空)请选择</option>
						</select>
		   			</td>
		   		</tr>
			</table>
			
			<table class="table-form" ng-if="selectedCustomDialog!=null">
		   		<tr>
		   			<th style="width: 50%;">对话框返回字段</th>
		   			<th style="text-align: left;width: 50%;">表单字段</th>
		   		</tr>
		   		<tr ng-repeat="field in selectedCustomDialog.resultfield">
		   			<th>{{field.comment}}</th>
		   			<td>
		   				<select class="inputText" ng-model="field.target" ng-options="(m.path+'.'+m.name) as (m.desc!=null?m.desc:m.name) for m in formFieldList |filter:filterFormFieldList">
							<option value="">(空)请选择</option>
						</select>
		   			</td>
		   		</tr>
	   		</table>
		</fieldset>
		
		<!--页面中一定要引入internal.js为了能直接使用当前打开dialog的实例变量-->
		<!--internal.js默认是放到dialogs目录下的-->
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/dialogs/internal.js"></script>
		<script type="text/javascript">
		    
		    //获取选择焦点，判断是编辑还是新增
		    var selectedDom = editor.selection.getStart();
		    var custombuttonjson=null;//编辑的Json
		    if(selectedDom.attributes['custom-button']!=null){
		    	custombuttonjson=JSON.parse(selectedDom.attributes['custom-button'].value);
		    	//console.info(custombuttonjson);
		    }
		    
		    var content =editor.getContent();
		    function isEditorContentContain(str){
		    	return content.indexOf(str)>=0;
		    }
		    
		    dialog.onok = function(){
		    	var scope =$("body").scope();
		    	if(custombuttonjson!=null){//删除旧的，添加新的
		    		$(selectedDom).remove();
		    	}
		    	editor.execCommand('inserthtml',scope.getHtml());
			}
		    
		</script>
	</body>
</html>