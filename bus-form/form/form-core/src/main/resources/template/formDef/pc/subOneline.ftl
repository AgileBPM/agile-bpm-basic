<div ${generator.getSubAttrs(relation)} ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >
	<div class="ibox-title"><span class="title">${relation.tableComment}</span>
		<a href="javascript:void(0)" class="btn btn-primary btn-sm fa fa-plus" ng-model="${generator.getScopePath(relation)}" ab-sub-add="initData.${relation.busObj.key}.${relation.tableKey}" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">添加</a>
	</div>
	<div class="ibox-content">
		<table class="form-table">
			<thead>
				<tr>
					<#list relation.table.columnsWithOutHidden as column>
					<th>${column.comment}</th>
					</#list>	
					<th>操作</th>
				</tr>
			</thead>
			<tr ng-repeat="${relation.tableKey} in ${generator.getScopePath(relation)} track by $index">
			<#list relation.table.columnsWithOutHidden as column>
				<td>${generator.getColumn(column,relation)} </td>
			</#list>
			<td>${getOne2ManyChild(relation)}
			<a class="btn btn-danger btn-sm fa fa-delete" ng-click="ArrayTool.del($index,${generator.getScopePath(relation)})" ab-edit-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}"> </a></td>
			</tr>
		</table>
	</div>
</div>
<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren('oneToOne')>
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
	<#assign relationList = relation.getChildren('oneToMany')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="javascript:void(0)" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}" >${relation.tableComment}详情</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>