<table class="form-table">
	<#list relation.table.columnsWithOutHidden as column>
	<tr>								
		<th>${column.comment}</th>
		<td>${generator.getColumn(column,relation)}</td>								
	</tr>
	</#list>
</table>
${getOne2OneChild(relation)}




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
		<div class="pull-left"><#list relationList as relation><a href="#" class="btn btn-link btn-sm fa fa-detail" ng-model="${relation.parent.tableKey}" ab-sub-detail="${relation.getBusObj().getKey()}-${relation.tableKey}" ab-show-permission="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}详情</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>