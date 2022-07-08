<div ${mobileGenerator.getSubAttrs(relation)} v-ab-permission:show="tablePermission.${relation.busObj.key}.${relation.tableKey}" >
	<#if mobileGenerator.isThreeChildren(relation)><popup v-model="subTableDialog.${relation.tableKey}" position="bottom" height="80%"> </#if>
	<div class="weui-cells__title" ><span class="title">${relation.tableComment}</span>
		<ab-sub-add href="javascript:;" v-model="${mobileGenerator.getScopePath(relation)}" v-bind:init-data="initData.${relation.busObj.key}.${relation.tableKey}" class="fa fa-plus weui-btn weui-btn_mini weui-btn_primary" v-ab-permission:edit="tablePermission.${relation.busObj.key}.${relation.tableKey}"></ab-sub-add> 
	</div>
	<div class="weui-cells weui-cells_form" v-for="(${relation.tableKey},index) in ${mobileGenerator.getScopePath(relation)}">
		<div class="weui-cells__title">
		 	${getOne2ManyChild(relation)}
		 	<a href="javascript:;" v-sub-del="[${mobileGenerator.getScopePath(relation)},index]" class="fa fa-trash weui-btn weui-btn_mini weui-btn_warn pull-right" v-ab-permission:edit="tablePermission.${relation.busObj.key}.${relation.tableKey}"></a> 
		 </div>
		<#list relation.table.columnsWithOutHidden as column>
			<div class="weui-cell" v-ab-permission:show="${mobileGenerator.getPermissionPath(column,relation)}">
		        <div class="weui-cell__hd"><label class="weui-label">${column.comment}</label></div>
				<div class="weui-cell__bd">${mobileGenerator.getColumn(column,relation)}</div>
	    	</div>
		</#list>
		 ${getOne2OneChild(relation)}
	</div>
	<#if mobileGenerator.isThreeChildren(relation) ></popup></#if>
</div>

<#function getOne2OneChild relation> 
	<#assign relationList = relation.getChildren('oneToOne')>
	<#assign rtn>
		<#list relationList as relation>
			<div ${mobileGenerator.getSubAttrs(relation)} >
				<div class="weui-cells__title"> ${relation.tableComment}
					${getOne2ManyChild(relation)}
				</div>
				
				<div class="weui-cells weui-cells_form">
					<#list relation.table.columnsWithOutHidden as column>
						<div class="weui-cell" v-ab-permission:show="${mobileGenerator.getPermissionPath(column,relation)}">
					        <div class="weui-cell__hd"><label class="weui-label">${column.comment}</label></div>
					        <div class="weui-cell__bd">${mobileGenerator.getColumn(column,relation)}</div>
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
	<#assign relationList = relation.getChildren('oneToMany')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
			<div class="pull-left"><#list relationList as relation><a href="#"  v-on:click="showSubTable(${relation.parent.tableKey},'${relation.tableKey}')" class="fa fa-list-alt weui-btn weui-btn_mini weui-btn_primary"  v-ab-permission:show="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}详情</a>		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>