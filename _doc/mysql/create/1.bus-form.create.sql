-- 业务对象、以及表单模块的表创建语句

-- ----------------------------
-- Table structure for bus_column
-- ----------------------------
DROP TABLE IF EXISTS `bus_column`;
CREATE TABLE `bus_column` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `table_id_` varchar(64) DEFAULT NULL COMMENT '表id',
  `key_` varchar(64) DEFAULT NULL COMMENT '别名',
  `name_` varchar(64) DEFAULT NULL COMMENT '名字',
  `type_` varchar(64) DEFAULT NULL COMMENT '类型',
  `length_` int(11) DEFAULT NULL,
  `decimal_` int(11) DEFAULT NULL,
  `required_` tinyint(4) DEFAULT NULL,
  `primary_` tinyint(4) DEFAULT NULL,
  `default_value_` varchar(128) DEFAULT NULL,
  `comment_` varchar(256) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务字段表';


-- ----------------------------
-- Table structure for bus_column_ctrl
-- ----------------------------
DROP TABLE IF EXISTS `bus_column_ctrl`;
CREATE TABLE `bus_column_ctrl` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `column_id_` varchar(64) DEFAULT NULL COMMENT '字段ID',
  `type_` varchar(64) DEFAULT NULL COMMENT '控件类型',
  `config_` varchar(256) DEFAULT NULL COMMENT '控件配置',
  `valid_rule_` varchar(256) DEFAULT NULL COMMENT '验证规则',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `column_id_unique` (`column_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字段控件表';


-- ----------------------------
-- Table structure for bus_object
-- ----------------------------
DROP TABLE IF EXISTS `bus_object`;
CREATE TABLE `bus_object` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `key_` varchar(64) DEFAULT NULL COMMENT 'key',
  `name_` varchar(128) DEFAULT NULL COMMENT '名字',
  `desc_` varchar(256) DEFAULT NULL COMMENT '描述',
  `relation_json_` text COMMENT 'relation字段用来持久化入库的字符串字段',
  `group_id_` varchar(64) DEFAULT NULL COMMENT '分组id',
  `group_name_` varchar(128) DEFAULT NULL COMMENT '分组名称',
  `persistence_type_` varchar(64) DEFAULT NULL COMMENT '持久化类型',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `key_unique_idx` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务对象';


-- ----------------------------
-- Table structure for bus_permission
-- ----------------------------
DROP TABLE IF EXISTS `bus_permission`;
CREATE TABLE `bus_permission` (
  `id_` varchar(64) NOT NULL,
  `bo_key_` varchar(128) DEFAULT NULL COMMENT 'boKey',
  `obj_type_` varchar(64) NOT NULL COMMENT '配置这个权限的对象，可以是表单，流程，或流程节点',
  `obj_val_` varchar(128) DEFAULT NULL COMMENT '能获取到配置权限的对象的唯一值\r\n 通常是key 或 id \r\n 可以是自定义的\r\n 例如 某个流程的某个节点，可以是 流程key.nodeKey\r\n 这样的格式\r\n',
  `bus_obj_map_json_` longtext COMMENT 'busObjMap的json数据',
  `rights_json_` longtext COMMENT 'rights的json数据',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `obj_type_obj_val_unique_idx_` (`obj_type_`,`obj_val_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='bo权限';


-- ----------------------------
-- Table structure for bus_table
-- ----------------------------
DROP TABLE IF EXISTS `bus_table`;
CREATE TABLE `bus_table` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `key_` varchar(64) DEFAULT NULL COMMENT '业务表key',
  `name_` varchar(64) DEFAULT NULL COMMENT '表名',
  `comment_` varchar(256) DEFAULT NULL COMMENT '描述',
  `ds_key_` varchar(64) DEFAULT NULL COMMENT '数据源的别名',
  `ds_name_` varchar(128) DEFAULT NULL COMMENT '数据源名称',
  `group_id_` varchar(64) DEFAULT NULL COMMENT '分组id',
  `group_name_` varchar(128) DEFAULT NULL COMMENT '分组名称',
  `external_` smallint(6) DEFAULT NULL COMMENT '是否外部表',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `key_unique_idx` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务表';

-- ----------------------------
-- Table structure for form_cust_dialog
-- ----------------------------
DROP TABLE IF EXISTS `form_cust_dialog`;
CREATE TABLE `form_cust_dialog` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `key_` varchar(64) DEFAULT NULL COMMENT '别名',
  `name_` varchar(128) NOT NULL COMMENT '名字',
  `desc_` varchar(256) DEFAULT NULL COMMENT '描述',
  `style_` varchar(32) DEFAULT NULL COMMENT '显示类型',
  `ds_key_` varchar(64) DEFAULT NULL COMMENT '数据源别名',
  `ds_name_` varchar(128) DEFAULT NULL COMMENT '数据源名字',
  `obj_type_` varchar(32) DEFAULT NULL COMMENT '对象类型',
  `obj_name_` varchar(64) NOT NULL COMMENT '对象名称',
  `page_` tinyint(4) DEFAULT NULL COMMENT '是否分页',
  `page_size_` int(11) DEFAULT NULL COMMENT '分页大小',
  `width_` int(11) DEFAULT NULL COMMENT '弹出框的宽度',
  `height_` int(11) DEFAULT NULL COMMENT '弹出框的高度',
  `system_` tinyint(4) DEFAULT NULL COMMENT '是否系统内置',
  `multiple_` tinyint(4) DEFAULT NULL COMMENT '是否多选',
  `tree_config_json_` varchar(512) DEFAULT NULL COMMENT '树形的配置信息，json字段',
  `display_fields_json_` text COMMENT '显示字段',
  `condition_fields_json_` text COMMENT '条件字段的json',
  `return_fields_json_` text COMMENT '返回字段json',
  `sort_fields_json_` text COMMENT '排序字段',
  `data_source_` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  UNIQUE KEY `idx_unqiue` (`key_`) USING BTREE
) DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='自定义对话框';
 
-- ----------------------------
-- Table structure for form_def
-- ----------------------------
DROP TABLE IF EXISTS `form_def`;
CREATE TABLE `form_def` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `type_` varchar(64) NOT NULL COMMENT '分类（pc/mobile）',
  `key_` varchar(64) DEFAULT NULL COMMENT 'key',
  `name_` varchar(128) DEFAULT NULL COMMENT '名字',
  `desc_` varchar(256) DEFAULT NULL COMMENT '描述',
  `group_id_` varchar(64) DEFAULT NULL COMMENT '分组id',
  `group_name_` varchar(128) DEFAULT NULL COMMENT '分组名称',
  `bo_key_` varchar(64) DEFAULT NULL COMMENT '业务对象key',
  `bo_name_` varchar(128) DEFAULT NULL COMMENT '业务对象名称',
  `html_` longtext COMMENT 'html',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `creator_` varchar(128) DEFAULT NULL COMMENT '创建人名字',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人ID',
  `updator_` varchar(128) DEFAULT NULL COMMENT '更新人名字',
  `version_` int(11) DEFAULT NULL COMMENT '版本号',
  `delete_` tinyint(4) DEFAULT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `key_unique_idx` (`key_`) USING BTREE
) DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='表单';
 
-- ----------------------------
-- Table structure for form_template
-- ----------------------------
DROP TABLE IF EXISTS `form_template`;
CREATE TABLE `form_template` (
  `id_` varchar(64) NOT NULL COMMENT '模板id',
  `name_` varchar(128) DEFAULT NULL COMMENT '模板名称',
  `form_type_` varchar(64) DEFAULT NULL COMMENT '表单类型（pc/mobile/vuepc）',
  `type_` varchar(32) DEFAULT NULL COMMENT '模板类型',
  `html_` text COMMENT '模板内容',
  `desc_` varchar(400) DEFAULT NULL COMMENT '模板描述',
  `editable_` tinyint(4) DEFAULT NULL COMMENT '是否可以编辑',
  `key_` varchar(64) DEFAULT NULL COMMENT '别名',
  PRIMARY KEY (`id_`)
) DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='表单模版';
