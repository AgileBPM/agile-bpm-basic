
ALTER TABLE `bus_object`
ADD COLUMN `per_type_config_`  varchar(255) NULL COMMENT '持久化类型的配置内容' AFTER `persistence_type_`;

UPDATE sys_workbench_panel SET display_content_ = '<div class=\"row\">\n	 \n	<div class=\"col-sm-12\">\n		<div class=\"form-inline\">\n			<input type=\"text\" placeholder=\"搜索关键字\" class=\"input-sm form-control\" ng-model=\"param.subject$V\" > \n			<button type=\"button\" ng-click=\"loadPanelData()\" class=\"btn btn-sm btn-primary fa-search\"></button>\n		</div>\n	</div>\n</div>\n <div class=\"table-responsive\">\n	<table class=\"table table-striped\">\n		<thead>\n			<tr>\n				<th>任务名称</th>\n				<th>任务标题</th>\n				<th>创建时间</th>\n			</tr>\n		</thead>\n		<tbody>\n			<tr ng-repeat=\"task in list\">\n				<td>{{task.name}}</td>\n				<td><a ng-click=\"debugger;openFullWindow(\'/bpm/vueForm/taskComplete.html?taskId=\'+this.task.id)\" >{{task.subject}}</a> </td>\n				<td>{{task.createTime}}</td>\n			</tr>\n		</tbody>\n	</table>\n</div>' WHERE alias_= 'list';

UPDATE `form_cust_dialog` SET `id_`='405338661247254529', `key_`='postSelector', `name_`='岗位选择', `desc_`='岗位', `style_`='list', `ds_key_`='dataSourceDefault', `ds_name_`='本地数据源', `obj_type_`='view', `obj_name_`='org_relation_view', `page_`='1', `page_size_`='10', `width_`='800', `height_`='600', `system_`='1', `multiple_`='0', `tree_config_json_`='{\"pidInitValScript\":false}', `display_fields_json_`='[{\"columnName\":\"groupName\",\"showName\":\"组织名\"},{\"columnName\":\"roleName\",\"showName\":\"职务\"}]', `condition_fields_json_`='[{\"columnName\":\"status_\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"状态：1启用，2禁用\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"},{\"columnName\":\"type_\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"类型：groupUser,groupRole,userRole,groupUserRole\",\"value\":{\"text\":\"groupRole\"},\"valueSource\":\"fixedValue\"},{\"columnName\":\"groupName\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"组织\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"roleName\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"职务\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', `return_fields_json_`='[{\"columnName\":\"groupName\",\"returnName\":\"groupName\"},{\"columnName\":\"roleName\",\"returnName\":\"roleName\"},{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"group_id_\",\"returnName\":\"groupId\"},{\"columnName\":\"role_id_\",\"returnName\":\"roleId\"},{\"columnName\":\"status_\",\"returnName\":\"status_\"},{\"columnName\":\"type_\",\"returnName\":\"type_\"},{\"columnName\":\"id_\",\"returnName\":\"key\"},{\"columnName\":\"postName\",\"returnName\":\"name\"}]', `sort_fields_json_`='[]', `data_source_`=NULL WHERE (`id_`='405338661247254529');


-- 【重要】 缓存升级，如果默认使用Redis 缓存 升级版本需要清除缓存后升级 ，序列化修改成了 FST



-- 需要执行SQL
UPDATE  `form_cust_dialog` SET `id_`='20000002250001', `key_`='busObjectSelect', `name_`='业务对象选择', `desc_`=NULL, `style_`='list', `ds_key_`='dataSourceDefault', `ds_name_`='本地数据源', `obj_type_`='table', `obj_name_`='bus_object', `page_`='1', `page_size_`='10', `width_`='800', `height_`='600', `system_`='1', `multiple_`='1', `tree_config_json_`='{\"pidInitValScript\":false}', `display_fields_json_`='[{\"columnName\":\"key_\",\"showName\":\"别名\"},{\"columnName\":\"name_\",\"showName\":\"名字\"},{\"columnName\":\"desc_\",\"showName\":\"描述\"}]', `condition_fields_json_`='[{\"columnName\":\"key_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名字\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', `return_fields_json_`='[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"}]', `sort_fields_json_`='[]', `data_source_`=NULL WHERE (`id_`='20000002250001');

ALTER TABLE `bpm_plugin_reminder_trigger`
MODIFY COLUMN `msg_type_`  varchar(64)  NULL DEFAULT NULL COMMENT '催办消息类型' AFTER `before_script_`;

