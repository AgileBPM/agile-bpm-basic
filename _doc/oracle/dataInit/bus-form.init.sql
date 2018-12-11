-- FORM 模块

-- 表单管理菜单启用
UPDATE sys_resource SET ENABLE_MENU_='1' WHERE (ID_='23');

-- 表单初始化模板

INSERT INTO FORM_TEMPLATE VALUES ('10000000000103', '单列模板', 'pc', 'main', '<table class="form-table">
	<#list relation.table.columnsWithOutHidden as column>
	<tr>								
		<th>${column.comment}</th>
		<td>${generator.getColumn(column,relation)}</td>								
	</tr>
	</#list>
</table>
${getOne2OneChild(relation)}




<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#list relation.table.columnsWithOutHidden as column>
						<tr>
							<th>${column.comment}</th>
							<td>${generator.getColumn(column,relation)} </td>
						</tr>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>', '单列模板', '0', 'mainOneColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000104', '两列模板', 'pc', 'main', '<table class="form-table">
	<#assign index=1>
	<#list relation.table.columnsWithOutHidden as column>
	<#if index==1>
	<tr>
	</#if>								
		<th>${column.comment}</th>
		<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>								
	<#if field.isSeparator==true || !column_has_next || index==2>
	</tr>
	<#assign index=0>
	</#if>
	<#assign index=index+1>
	</#list>
</table>
${getOne2OneChild(relation)}

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#assign index=1>
					<#list relation.table.columnsWithOutHidden as column>
						<#if index==1>
						<tr>
						</#if>
							<th>${column.comment}</th>
							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
						<#if !column_has_next || index==2>
						</tr>
						<#assign index=0>
						</#if> 
						<#assign index=index+1>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>


<#function getColspan index,hasNext>
	<#assign rtn="">
	
	 <#if !hasNext && index !=2>
		<#assign rtn="colspan=''"+((2-index)*2+1)+"''"> 
	</#if>
	
	<#return rtn>
</#function>', '两列模板', '0', 'mainTwoColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000105', '三列模板', 'pc', 'main', '<table class="form-table">
	<#assign index=1>
	<#list relation.table.columnsWithOutHidden as column>
		<#if index==1>
		<tr>
		</#if>
			<th>${column.comment}</th>
			<td ${getColspan(index,column_has_next)}> ${generator.getColumn(column,relation)} </td>
		<#if !column_has_next || index==3>
		</tr>
		<#assign index=0>
		</#if> 
		<#assign index=index+1>
	</#list>
</table>
 ${getOne2OneChild(relation)}
 
 
<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#assign index=1>
					<#list relation.table.columnsWithOutHidden as column>
						<#if index==1>
						<tr>
						</#if>
							<th>${column.comment}</th>
							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
						<#if !column_has_next || index==3>
						</tr>
						<#assign index=0>
						</#if> 
						<#assign index=index+1>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>

<#function getColspan index,hasNext>
	<#assign rtn="">
	
	 <#if !hasNext && index !=3>
		<#assign rtn="colspan=''"+((3-index)*2+1)+"''"> 
	</#if>
	
	<#return rtn>
</#function>', '三列模板', '0', 'mainThreeColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000106', '四列模板', 'pc', 'main', '<table class="form-table">
	<#assign index=1>
	<#list relation.table.columnsWithOutHidden as column>
		<#if index==1>
		<tr>
		</#if>
			<th>${column.comment}</th>
			<td ${getColspan(index,column_has_next)}> ${generator.getColumn(column,relation)} </td>
		<#if !column_has_next || index==4>
		</tr>
		<#assign index=0>
		</#if> 
		<#assign index=index+1>
	</#list>
</table>

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#assign index=1>
					<#list relation.table.columnsWithOutHidden as column>
						<#if index==1>
						<tr>
						</#if>
							<th>${column.comment}</th>
							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
						<#if !column_has_next || index==4>
						</tr>
						<#assign index=0>
						</#if> 
						<#assign index=index+1>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>

<#function getColspan index,hasNext>
	<#assign rtn="">
	
	 <#if (!hasNext || isSeparator==true) && index !=4>
		<#assign rtn="colspan=''"+((4-index)*2+1)+"''"> 
	</#if>
	
	<#return rtn>
</#function>', '四列模板', '0', 'mainFourColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000107', '单列模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >
	<div class="ibox-title"><span class="title">${relation.tableComment}</span>
		<a href="javascript:void(0)" class="btn btn-primary btn-sm fa fa-plus" ng-model="${generator.getScopePath(relation)}" ab-sub-add="initData.${relation.busObj.key}.${relation.tableKey}" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">添加</a>
	</div>
	<div class="ibox-content" ng-repeat="${relation.tableKey} in ${generator.getScopePath(relation)} track by $index"> ${getOne2ManyChild(relation)}<a class="btn btn-danger btn-xs fa fa-delete pull-right" ng-click="ArrayTool.del($index,${generator.getScopePath(relation)})" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}"> 移除</a>
		<table class="form-table">
		<#list relation.table.columnsWithOutHidden as column>
			<tr>
				<th>${column.comment}</th>
				<td>${generator.getColumn(column,relation)} </td>
			</tr>
		</#list>
		</table>
		 ${getOne2OneChild(relation)}
	</div>
</div>

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#list relation.table.columnsWithOutHidden as column>
						<tr>
							<th>${column.comment}</th>
							<td>${generator.getColumn(column,relation)} </td>
						</tr>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="javascript:void(0)" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>', '单列模板', '0', 'subOneColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000108', '两列模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >
	<div class="ibox-title"><span class="title">${relation.tableComment}</span>
		<a href="###" class="btn btn-primary btn-sm fa fa-plus" ng-model="${generator.getScopePath(relation)}" ab-sub-add="initData.${relation.busObj.key}.${relation.tableKey}" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >添加</a>
	</div>
	<div class="ibox-content" ng-repeat="${relation.tableKey} in ${generator.getScopePath(relation)} track by $index"> ${getOne2ManyChild(relation)}<a class="btn btn-danger btn-xs fa fa-delete pull-right" ng-click="ArrayTool.del($index,${generator.getScopePath(relation)})" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}"> 移除</a>
		<table class="form-table">
		<#assign index=1>
		<#list relation.table.columnsWithOutHidden as column>
			<#if index==1>
			<tr>
			</#if>
				<th>${column.comment}</th>
				<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
			<#if !column_has_next || index==2>
			</tr>
			<#assign index=0>
			</#if> 
			<#assign index=index+1>
		</#list>
		</table>
		 ${getOne2OneChild(relation)}
	</div>
</div>

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#assign index=1>
					<#list relation.table.columnsWithOutHidden as column>
						<#if index==1>
						<tr>
						</#if>
							<th>${column.comment}</th>
							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
						<#if !column_has_next || index==2>
						</tr>
						<#assign index=0>
						</#if> 
						<#assign index=index+1>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>

<#function getColspan index,hasNext>
	<#assign rtn="">
		 <#if !hasNext && index !=2>
			<#assign rtn="colspan=''"+((2-index)*2+1)+"''"> 
		</#if>
<#return rtn>
</#function> ', '两列模板', '0', 'subTwoColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000109', '三列模板', 'pc', 'subTable', '<div ${generator.getSubAttrs(relation)} ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >
	<div class="ibox-title"><span class="title">${relation.tableComment}</span>
		<a href="###" class="btn btn-primary btn-sm fa fa-plus" ng-model="${generator.getScopePath(relation)}" ab-sub-add="initData.${relation.busObj.key}.${relation.tableKey}" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">添加</a>
	</div>
	<div class="ibox-content" ng-repeat="${relation.tableKey} in ${generator.getScopePath(relation)} track by $index"> ${getOne2ManyChild(relation)}<a class="btn btn-danger btn-xs fa fa-delete pull-right" ng-click="ArrayTool.del($index,${generator.getScopePath(relation)})" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}"> 移除</a>
		<table class="form-table">
		<#assign index=1>
		<#list relation.table.columnsWithOutHidden as column>
			<#if index==1>
			<tr>
			</#if>
				<th>${column.comment}</th>
				<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
			<#if !column_has_next || index==3>
			</tr>
			<#assign index=0>
			</#if> 
			<#assign index=index+1>
		</#list>
		</table>
		 ${getOne2OneChild(relation)}
	</div>
</div>

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${generator.getSubAttrs(relation)} >
				<div class="block-title"> <span class="title">${relation.tableComment} </span>
					${getOne2ManyChild(relation)}
				</div>
				<table class="form-table">
					<#assign index=1>
					<#list relation.table.columnsWithOutHidden as column>
						<#if index==1>
						<tr>
						</#if>
							<th>${column.comment}</th>
							<td ${getColspan(index,column_has_next)}>${generator.getColumn(column,relation)}</td>
						<#if !column_has_next || index==3>
						</tr>
						<#assign index=0>
						</#if> 
						<#assign index=index+1>
					</#list>
				</table>
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >${relation.tableComment}</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>

<#function getColspan index,hasNext>
	<#assign rtn="">
		 <#if (!hasNext || isSeparator==true) && index !=3>
			<#assign rtn="colspan=''"+((3-index)*2+1)+"''"> 
		</#if>
<#return rtn>
</#function>', '三列模板', '0', 'subThreeColumn');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000110', 'vux主表模板', 'mobile', 'main', '<script>
	<!--脚本将会混入表单自定义表单控件-->
	window.custFormComponentMixin ={
			data: function () {
		    	return {"test":"helloWorld"};
		  	},
			created:function(){
				console.log("混入对象的钩子被调用");
			},methods:{
				testaaa:function(){alert(1)}
			}
	}
</script>
<div class="weui-cells weui-cells_form">
<#list relation.table.columnsWithOutHidden as column>
	<div class="weui-cell" v-ab-permission:show="${vuxGenerator.getPermissionPath(column,relation)}">
        <div class="weui-cell__hd"><label class="weui-label">${column.comment}</label></div>
        <div class="weui-cell__bd">${vuxGenerator.getColumn(column,relation)}</div>
	</div>
</#list>
</div>
${getOne2OneChild(relation)}

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${vuxGenerator.getSubAttrs(relation)} >
				<div class="weui-cells__title"> ${relation.tableComment}
					${getOne2ManyChild(relation)}
				</div>
				
				<div class="weui-cells weui-cells_form">
					<#list relation.table.columnsWithOutHidden as column>
						<div class="weui-cell" v-ab-permission:show="${vuxGenerator.getPermissionPath(column,relation)}">
					        <div class="weui-cell__hd"><label class="weui-label">${column.comment}</label></div>
					        <div class="weui-cell__bd">${vuxGenerator.getColumn(column,relation)}</div>
					    </div>
					</#list>
				</div>
				
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#"  v-on:click="showSubTable(${relation.parent.tableKey},''${relation.tableKey}'')" class="fa fa-list-alt weui-btn weui-btn_mini weui-btn_primary"  v-ab-permission:show="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}详情</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>', 'vux主表模板', '0', 'vuxMainTemplate');
INSERT INTO FORM_TEMPLATE VALUES ('10000000000111', 'vux子表模板', 'mobile', 'subTable', '<div ${vuxGenerator.getSubAttrs(relation)} v-ab-permission:show="tablePermission.${relation.busObj.key}.${relation.tableKey}" >
	<#if vuxGenerator.isThreeChildren(relation)><popup v-model="subTableDialog.${relation.tableKey}" position="bottom" height="80%"> </#if>
	<div class="weui-cells__title" ><span class="title">${relation.tableComment}</span>
		<a href="javascript:;" v-sub-add="[${vuxGenerator.getScopePath(relation)},initData.${relation.busObj.key}.${relation.tableKey}]" class="fa fa-plus weui-btn weui-btn_mini weui-btn_primary" v-ab-permission:edit="tablePermission.${relation.busObj.key}.${relation.tableKey}"></a> 
	</div>
	<div class="weui-cells weui-cells_form" v-for="(${relation.tableKey},index) in ${vuxGenerator.getScopePath(relation)}">
		<div class="weui-cells__title">
		 	${getOne2ManyChild(relation)}
		 	<a href="javascript:;" v-sub-del="[${vuxGenerator.getScopePath(relation)},index]" class="fa fa-trash weui-btn weui-btn_mini weui-btn_warn pull-right" v-ab-permission:edit="tablePermission.${relation.busObj.key}.${relation.tableKey}"></a> 
		 </div>
		<#list relation.table.columnsWithOutHidden as column>
			<div class="weui-cell" v-ab-permission:show="${vuxGenerator.getPermissionPath(column,relation)}">
		        <div class="weui-cell__hd"><label class="weui-label">${column.comment}</label></div>
				<div class="weui-cell__bd">${vuxGenerator.getColumn(column,relation)}</div>
	    	</div>
		</#list>
		 ${getOne2OneChild(relation)}
	</div>
	<#if vuxGenerator.isThreeChildren(relation) ></popup></#if>
</div>

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren(''oneToOne'')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${vuxGenerator.getSubAttrs(relation)} >
				<div class="weui-cells__title"> ${relation.tableComment}
					${getOne2ManyChild(relation)}
				</div>
				
				<div class="weui-cells weui-cells_form">
					<#list relation.table.columnsWithOutHidden as column>
						<div class="weui-cell" v-ab-permission:show="${vuxGenerator.getPermissionPath(column,relation)}">
					        <div class="weui-cell__hd"><label class="weui-label">${column.comment}</label></div>
					        <div class="weui-cell__bd">${vuxGenerator.getColumn(column,relation)}</div>
					    </div>
					</#list>
				</div>
				
				${getOne2OneChild(relation)}
			</div>
		</#list>
	</#assign>
	<#return rtn>
</#function>

<#function getOne2ManyChild relation> 
	<#assign relationList = relation.getChildren(''oneToMany'')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
			<div class="pull-left"><#list relationList as relation><a href="#"  v-on:click="showSubTable(${relation.parent.tableKey},''${relation.tableKey}'')" class="fa fa-list-alt weui-btn weui-btn_mini weui-btn_primary"  v-ab-permission:show="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}详情</a>		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>', 'vux子表模板', '0', 'vuxSubTemplate');


-- 系统对话框
INSERT INTO FORM_CUST_DIALOG VALUES ('20000004660004', 'getMyUsablePanels', '获取可用面板', '面板布局获取可用的面板', 'list', 'dataSourceDefault', '本地数据源', 'table', 'workbenchPanelManager.getMyUsablePanels', '1', '10', '800', '600', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"name","showName":"名字"},{"columnName":"desc","showName":"描述"}]', '[{"columnName":"layoutKey","condition":"EQ","dbType":"varchar","showName":"layoutKey","value":{},"valueSource":"param"}]', '[{"columnName":"type","returnName":"type"},{"columnName":"alias","returnName":"alias"},{"columnName":"name","returnName":"name"},{"columnName":"type","returnName":"type"},{"columnName":"desc","returnName":"desc"},{"columnName":"dataType","returnName":"dataType"},{"columnName":"dataSource","returnName":"dataSource"},{"columnName":"autoRefresh","returnName":"autoRefresh"},{"columnName":"width","returnName":"width"},{"columnName":"height","returnName":"height"},{"columnName":"displayContent","returnName":"displayContent"},{"columnName":"moreUrl","returnName":"moreUrl"},{"columnName":"id","returnName":"id"},{"columnName":"height","returnName":"custHeight"},{"columnName":"width","returnName":"custWidth"}]', '[]', 'interface');
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000003160002', 'roleSelector', '角色对话框', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'ORG_ROLE', '1', '10', '800', '600', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"NAME_","showName":"角色名称"},{"columnName":"ALIAS_","showName":"别名"}]', '[{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"名称","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"ALIAS_","condition":"LK","dbType":"varchar","showName":"别名","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"NAME_","returnName":"name"},{"columnName":"ALIAS_","returnName":"key"},{"columnName":"ENABLED_","returnName":"enabled"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000003130001', 'userSelector', '用户查询', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'ORG_USER', '1', '10', '930', '660', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"FULLNAME_","showName":"姓名"},{"columnName":"ACCOUNT_","showName":"账号"},{"columnName":"MOBILE_","showName":"手机号码"}]', '[{"columnName":"FULLNAME_","condition":"LK","dbType":"varchar","showName":"姓名","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"ACCOUNT_","condition":"LK","dbType":"varchar","showName":"账号","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"STATUS_","condition":"EQ","dbType":"number","showName":"0:禁用，1正常","value":{"text":"1"},"valueSource":"fixedValue"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"FULLNAME_","returnName":"name"},{"columnName":"ACCOUNT_","returnName":"account"},{"columnName":"EMAIL_","returnName":"email"},{"columnName":"MOBILE_","returnName":"mobile"},{"columnName":"WEIXIN_","returnName":"weixin"},{"columnName":"ADDRESS_","returnName":"address"},{"columnName":"SEX_","returnName":"sex"},{"columnName":"STATUS_","returnName":"status"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000003410001', 'jobSelector', '职务选择', '职务选择框', 'list', 'dataSourceDefault', '本地数据源', 'table', 'ORG_GROUP_RELDEF', '1', '10', '800', '600', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"NAME_","showName":"名称"},{"columnName":"CODE_","showName":"编码"},{"columnName":"DESCRIPTION_","showName":"描述"}]', '[{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"名称","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"NAME_","returnName":"name"},{"columnName":"CODE_","returnName":"key"},{"columnName":"POST_LEVEL_","returnName":"postLevel"},{"columnName":"DESCRIPTION_","returnName":"description"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000005180002', 'scriptSelector', '常用脚本选择框', '选择常用脚本', 'list', 'dataSourceDefault', '本地数据源', 'table', 'SYS_SCRIPT', '1', '10', '800', '600', '1', '0', '{"pidInitValScript":false}', '[{"columnName":"NAME_","showName":"名称"},{"columnName":"CATEGORY_","showName":"分类"},{"columnName":"MEMO_","showName":"备注"}]', '[{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"脚本名称","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"SCRIPT_","returnName":"script"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000003460003', 'formSelector', '表单选择框', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'FORM_DEF', '1', '10', '800', '600', '1', '0', '{"pidInitValScript":false}', '[{"columnName":"NAME_","showName":"名字"},{"columnName":"KEY_","showName":"key"},{"columnName":"DESC_","showName":"描述"},{"columnName":"BO_NAME_","showName":"业务对象"},{"columnName":"BO_KEY_","showName":"业务对象key"}]', '[{"columnName":"BO_KEY_","condition":"IN","dbType":"varchar","showName":"别名","value":{"ctrlType":""},"valueSource":"param"},{"columnName":"TYPE_","condition":"EQ","dbType":"varchar","showName":"分类","value":{},"valueSource":"param"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"KEY_","returnName":"key"},{"columnName":"NAME_","returnName":"name"},{"columnName":"DESC_","returnName":"desc"},{"columnName":"GROUP_ID_","returnName":"groupId"},{"columnName":"BO_KEY_","returnName":"boKey"},{"columnName":"BO_NAME_","returnName":"boName"},{"columnName":"TYPE_","returnName":"TYPE_"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000008820001', 'IdentitySeletor', '流水号选择', '流水号选择', 'list', 'dataSourceDefault', '本地数据源', 'table', 'SYS_SERIALNO', '1', '10', '800', '600', '1', '0', '{"pidInitValScript":false}', '[{"columnName":"NAME_","showName":"名称"},{"columnName":"ALIAS_","showName":"别名"},{"columnName":"REGULATION_","showName":"规则"},{"columnName":"STEP_","showName":"步长"}]', '[{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"名称","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"ALIAS_","condition":"LK","dbType":"varchar","showName":"别名","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"NAME_","returnName":"name"},{"columnName":"ALIAS_","returnName":"alias"},{"columnName":"REGULATION_","returnName":"regulation"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000013260001', 'bpmDefSelector', '流程定义选择', '流程定义主版本', 'list', 'dataSourceDefault', '本地数据源', 'table', 'BPM_DEFINITION', '1', '10', '800', '600', '1', '0', '{"pidInitValScript":false}', '[{"columnName":"NAME_","showName":"流程名称"},{"columnName":"KEY_","showName":"流程业务主键"},{"columnName":"DESC_","showName":"流程描述"}]', '[{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"流程名称","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"IS_MAIN_","condition":"EQ","dbType":"varchar","showName":"版本 - 是否主版本","value":{"text":"Y"},"valueSource":"fixedValue"}]', '[{"columnName":"NAME_","returnName":"name"},{"columnName":"KEY_","returnName":"key"},{"columnName":"DESC_","returnName":"desc"},{"columnName":"TYPE_ID_","returnName":"typeId"},{"columnName":"IS_MAIN_","returnName":"isMain"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('10000005470001', 'ywblb', '业务表列表', '业务表列表', 'list', 'dataSourceDefault', '本地数据源', 'table', 'BUS_TABLE', '1', '10', '1300', '600', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"COMMENT_","showName":"描述"},{"columnName":"KEY_","showName":"业务表key"},{"columnName":"NAME_","showName":"表名"}]', '[{"columnName":"KEY_","condition":"LK","dbType":"varchar","showName":"业务表key","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"表名","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"KEY_","returnName":"key"},{"columnName":"NAME_","returnName":"name"},{"columnName":"DS_KEY_","returnName":"dsKey"},{"columnName":"COMMENT_","returnName":"comment"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000002250001', 'busObjectSelect', '业务对象选择', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'BUS_OBJECT', '1', '10', '800', '600', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"KEY_","showName":"别名"},{"columnName":"NAME_","showName":"名字"},{"columnName":"DESC_","showName":"描述"}]', '[{"columnName":"KEY_","condition":"LK","dbType":"varchar","showName":"key","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"NAME_","condition":"LK","dbType":"varchar","showName":"名字","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"KEY_","returnName":"key"},{"columnName":"NAME_","returnName":"name"},{"columnName":"DESC_","returnName":"desc"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('10000005290006', 'zdydhklb', '自定义对话框列表', '自定义对话框列表', 'list', 'dataSourceDefault', '本地数据源', 'table', 'FORM_CUST_DIALOG', '1', '10', '1300', '800', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"NAME_","formatter":"","showName":"名字"},{"columnName":"KEY_","showName":"别名"},{"columnName":"STYLE_","formatter":"var span = ''<span class=\"label label-primary\">表</span>'';\n\t\tif (value == ''view'')\n\t\t\tspan = ''<span class=\"label label-warning\">视图</span>'';\n\t\treturn span;","showName":"显示类型"}]', '[{"columnName":"KEY_","condition":"EQ","dbType":"varchar","showName":"别名","value":{"ctrlType":"inputText"},"valueSource":"param"},{"columnName":"NAME_","condition":"EQ","dbType":"varchar","showName":"名字","value":{"ctrlType":"inputText"},"valueSource":"param"}]', '[{"columnName":"KEY_","returnName":"key"},{"columnName":"NAME_","returnName":"name"}]', '[{"columnName":"ID_","sortType":"desc"}]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000003050001', 'postSelector', '岗位选择框', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'ORG_GROUP_REL', '1', '10', '800', '600', '1', '1', '{"pidInitValScript":false}', '[{"columnName":"REL_NAME_","showName":"岗位名称"},{"columnName":"REL_CODE_","showName":"岗位编码"},{"columnName":"REL_DEF_NAME_","showName":"职务名称"}]', '[]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"GROUP_ID_","returnName":"orgId"},{"columnName":"REL_DEF_ID_","returnName":"jobId"},{"columnName":"REL_NAME_","returnName":"name"},{"columnName":"REL_CODE_","returnName":"key"},{"columnName":"REL_DEF_NAME_","returnName":"jobName"}]', '[]', NULL);
INSERT INTO FORM_CUST_DIALOG("ID_", "KEY_", "NAME_", "DESC_", "STYLE_", "DS_KEY_", "DS_NAME_", "OBJ_TYPE_", "OBJ_NAME_", "PAGE_", "PAGE_SIZE_", "WIDTH_", "HEIGHT_", "SYSTEM_", "MULTIPLE_", "TREE_CONFIG_JSON_", "DISPLAY_FIELDS_JSON_", "CONDITION_FIELDS_JSON_", "RETURN_FIELDS_JSON_", "SORT_FIELDS_JSON_", "DATA_SOURCE_") VALUES ('20000003160001', 'orgSelector', '组织选择框', NULL, 'tree', 'dataSourceDefault', '本地数据源', 'table', 'ORG_GROUP', '1', '10', '800', '600', '1', '1', '{"id":"ID_","pid":"PARENT_ID_","pidInitVal":"0","pidInitValScript":false,"showColumn":"NAME_"}', '[]', '[]', '[{"columnName":"ID_","returnName":"id"},{"columnName":"NAME_","returnName":"name"},{"columnName":"PARENT_ID_","returnName":"parentId"},{"columnName":"CODE_","returnName":"key"},{"columnName":"GRADE_","returnName":"grade"}]', '[]', NULL);
