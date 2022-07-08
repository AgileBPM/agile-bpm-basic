-- 1 表单定义，表单模板，【初始化表单模板】 否则生成表单会发生异常

-- 2 执行更新sql
-- 对话框修改过滤类型
UPDATE `form_cust_dialog` SET `key_` = 'formSelector', `name_` = '表单选择框', `desc_` = NULL, `style_` = 'list', `ds_key_` = 'dataSourceDefault', `ds_name_` = '本地数据源', `obj_type_` = 'table', `obj_name_` = 'form_def', `page_` = 1, `page_size_` = 10, `width_` = 800, `height_` = 600, `system_` = 1, `multiple_` = 0, `tree_config_json_` = '{\"pidInitValScript\":false}', `display_fields_json_` = '[{\"columnName\":\"name_\",\"showName\":\"名字\"},{\"columnName\":\"key_\",\"showName\":\"key\"},{\"columnName\":\"desc_\",\"showName\":\"描述\"},{\"columnName\":\"bo_name_\",\"showName\":\"业务对象\"},{\"columnName\":\"bo_key_\",\"showName\":\"业务对象key\"}]', `condition_fields_json_` = '[{\"columnName\":\"bo_key_\",\"condition\":\"IN\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"\"},\"valueSource\":\"param\"},{\"columnName\":\"type_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"分类\",\"value\":{},\"valueSource\":\"param\"}]', `return_fields_json_` = '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"key_\",\"returnName\":\"key\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"desc_\",\"returnName\":\"desc\"},{\"columnName\":\"group_id_\",\"returnName\":\"groupId\"},{\"columnName\":\"bo_key_\",\"returnName\":\"boKey\"},{\"columnName\":\"bo_name_\",\"returnName\":\"boName\"},{\"columnName\":\"type_\",\"returnName\":\"type_\"}]', `sort_fields_json_` = '[]', `data_source_` = NULL WHERE `id_` = '20000003460003';
-- 修改 vue表单的类型
UPDATE  `sys_resource` SET `url_` = 'form/formDef/formDefList.html?formType=pc_vue' WHERE `ID_` = '33';
UPDATE  `form_def` SET `type_` = 'pc_vue' WHERE `type_` = 'vue';

-- 支持整体布局
ALTER TABLE `bus_object` 
ADD COLUMN `overall_arrangement_` longtext NULL COMMENT '整体布局' AFTER `persistence_type_`;

-- 支持 门户平台子系统切换
ALTER TABLE `sys_subsystem` 
ADD COLUMN `url_`  varchar(500) NULL COMMENT '子系统地址，空则为当前系统' AFTER `alias_`,
ADD COLUMN `open_type_`  varchar(64) NULL COMMENT '打开方式' AFTER `url_`;
