<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/get.jsp" %>
		<script type="text/javascript" src="${ctx}/js/platform/system/customQuery/customQueryService.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/plugins/gangedSetting/page/js/Controller.js"></script>
		<script type="text/javascript" src="${ctx}/js/lib/angular/form/service/BpmFormService.js"></script>
		<script type="text/javascript">
			var formId="${param.formId}";
		</script>
	</head>
	
	<body ng-app="app" ng-controller="Controller">
		<fieldset>
    		<legend>查询设置</legend>
    		<table class="table-form">
	    		<tr>
	    			<th>选择查询：</th>
	    			<td>
	    				<select class="inputText"  validate="{required:true}" ng-model="prop.cid" ng-change="changeCid(prop.cid);" ng-options="m.id as m.name for m in customQuerys"></select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th>条件字段：</th>
	    			<td>
	    				<select class="inputText" ng-model="prop.condition" ng-options="m.field as m.comment for m in selectedCustomQuery.conditionfield"></select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<th>触发事件：</th>
	    			<td>
	    				<select class="inputText"  validate="{required:true}" ng-model="prop.triggerEvent" ng-options="m.value as m.key for m in triggerEventList"></select>
	    			</td>
	    		</tr>
    		</table>
    	</fieldset>
    	
    	<fieldset ng-if="selectedCustomQuery!=null">
    		<legend>返回结果设置</legend>
    		<table class="table-form">
    			<th>查询返回字段</th><th style="text-align: left;">表单字段</th>
    			<tr ng-repeat="field in selectedCustomQuery.resultfield">
		   			<th>{{field.comment}}</th>
		   			<td>
			   			<select class="inputText" ng-model="field.target" ng-options="(m.path+'.'+m.name) as (m.desc!=null?m.desc:m.name) for m in formFieldList |filter:filterFormFieldList">
							<option value="">(空)请选择</option>
						</select>
					</td>
		   		</tr>
    		</table>
    	</fieldset>
	</body>
</html>