<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@include file="/commons/include/list.jsp" %>
		<script type="text/javascript" src="${ctx}/js/lib/ueditor/plugins/opinion/page/js/Controller.js"></script>
		<style>
			.table-form tr {
				height: 45px;
				}
			fieldset{
				padding: 6px !important;
			}
			
			textarea{
				height: 114px!important;
			}
		</style>
	</head>
	
	<body ng-app="app" ng-controller="Controller">
		<fieldset >
		   	<table class="table-form">
		   		<tr>
			 		<th>意见选择：
			 		</th>
			 		<td>
			 		<div class="btn-group" ng-if="opinionConf.length>0" >
					  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
					    	意见选择
					  </button>
					  <ul class="dropdown-menu">
					    <li ng-repeat="opinion in opinionConf"><a href="#" ng-click="selectOpinion($index)">{{opinion.desc}}</a></li>
					  </ul>
					</div>
					 <button ng-if="opinionConf.length==0" type="button" class="btn btn-default" ng-click="setOpinion()">配置意见</button>
					 <button ng-if="opinionConf.length==0" type="button" class="btn btn-default" ng-click="getOpinion()">刷新</button>
			 	</tr>
			 	<tr>
					<th>意见描述：</th>
					<td>
					<input ng-model="prop.desc" type="text" class="inputText" validate="{required:true}"  style="width: 300px"/></td>
				</tr>
				<tr>
					<th>意见标识：</th>
					<td>
					<input ng-model="prop.name" type="text" readonly="readonly" class="inputText" validate="{required:true,varirule:true}" /></td>
				</tr>
			 	<tr>
			 		<th>控件宽度：</th>
			 		<td>
			 			<input ng-model="prop.width" type="text" class="inputText"  validate="{required:true}"/>
			 			<select class="inputText" style="width: 60px;" ng-model="prop.widthunit" ng-options="m.value as m.value for m in unitList"></select>
			 		</td>
			 	</tr>
			 	<tr>
			 	<th>控件高度：</th>
			 	<td>
			 		<input ng-model="prop.height" type="text" class="inputText" validate="{required:true}"/>
			 		<select class="inputText" style="width: 60px;" ng-model="prop.heightunit" ng-options="m.value as m.value for m in unitList"></select>
				</td>
				</tr>
			</table>
		</fieldset>	
	</body>
</html>