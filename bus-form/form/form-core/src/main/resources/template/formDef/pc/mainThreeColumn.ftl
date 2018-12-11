<table class="form-table">
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
	<#assign relationList = relation.getChildren('oneToOne')>
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

<#function getColspan index,hasNext>
	<#assign rtn="">
	
	 <#if !hasNext && index !=3>
		<#assign rtn="colspan='"+((3-index)*2+1)+"'"> 
	</#if>
	
	<#return rtn>
</#function>