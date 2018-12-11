<script>
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
	<#assign relationList = relation.getChildren('oneToOne')>
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
	<#assign relationList = relation.getChildren('oneToMany')>
	<#assign rtn>
		 <#if relationList?? && (relationList?size > 0) >
		<div class="pull-left"><#list relationList as relation><a href="#"  v-on:click="showSubTable(${relation.parent.tableKey},'${relation.tableKey}')" class="fa fa-list-alt weui-btn weui-btn_mini weui-btn_primary"  v-ab-permission:show="tablePermission.${relation.busObj.key}.${relation.tableKey}">${relation.tableComment}详情</a>
		</#list>
		</div>
		</#if>
	</#assign>
	<#return rtn>
</#function>