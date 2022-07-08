 
-- 支持整体布局
ALTER TABLE `bus_object` ADD COLUMN `overall_arrangement_` longtext NULL COMMENT '整体布局' AFTER `persistence_type_`;
ALTER TABLE `bus_object` ADD COLUMN `per_type_config_`  varchar(255) NULL COMMENT '持久化类型的配置内容' AFTER `persistence_type_`;

-- 支持 门户平台子系统切换
ALTER TABLE `sys_subsystem` 
ADD COLUMN `url_`  varchar(500) NULL COMMENT '子系统地址，空则为当前系统' AFTER `alias_`,
ADD COLUMN `open_type_`  varchar(64) NULL COMMENT '打开方式' AFTER `url_`;

 
-- 需要执行SQL
UPDATE  `form_cust_dialog` SET `id_`='20000002250001', `key_`='busObjectSelect', `name_`='业务对象选择', `desc_`=NULL, `style_`='list', `ds_key_`='dataSourceDefault', `ds_name_`='本地数据源', `obj_type_`='table', `obj_name_`='bus_object', `page_`='1', `page_size_`='10', `width_`='800', `height_`='600', `system_`='1', `multiple_`='1', `tree_config_json_`='{\"pidInitValScript\":false}', `display_fields_json_`='[{\"columnName\":\"key_\",\"showName\":\"别名\"},{\"columnName\":\"name_\",\"showName\":\"名字\"},{\"columnName\":\"desc_\",\"showName\":\"描述\"}]', `condition_fields_json_`='[{\"columnName\":\"key_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名字\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', `return_fields_json_`='[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"}]', `sort_fields_json_`='[]', `data_source_`=NULL WHERE (`id_`='20000002250001');

ALTER TABLE `bpm_plugin_reminder_trigger`
MODIFY COLUMN `msg_type_`  varchar(64)  NULL DEFAULT NULL COMMENT '催办消息类型' AFTER `before_script_`;

ALTER TABLE `bpm_bus_link`
ADD INDEX `link_inst_id_idx` (`inst_id_`) ;

-- 【重要】 缓存升级，如果默认使用Redis 缓存 升级版本需要清除缓存后升级 ，序列化修改成了 FST
