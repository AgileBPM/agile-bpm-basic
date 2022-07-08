-- ----- 常用脚本，流水号，子系统，菜单资源，通用授权，面板，系统属性，数据字典等功能表

 
SET FOREIGN_KEY_CHECKS=0;
-- ------------------sys 模块功能 持久化表-----------------

-- ----------------------------
-- Table structure for sys_authorization
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorization`;
CREATE TABLE `sys_authorization` (
  `rights_id_` varchar(64) NOT NULL COMMENT 'id',
  `rights_object_` varchar(64) NOT NULL COMMENT '授权对象表分区用',
  `rights_target_` varchar(64) NOT NULL COMMENT '授权目标ID',
  `rights_type_` varchar(64) NOT NULL COMMENT '权限类型',
  `rights_identity_` varchar(64) NOT NULL COMMENT '授权标识',
  `rights_identity_name_` varchar(255) NOT NULL COMMENT '标识名字',
  `rights_permission_code_` varchar(125) NOT NULL COMMENT '授权code=identity+type',
  `rights_create_time_` timestamp NOT NULL   COMMENT '创建时间',
  `rights_create_by_` varchar(64) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`rights_id_`),
  KEY `idx_permission_code_` (`rights_permission_code_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='通用资源授权配置';

-- ----------------------------
-- Table structure for sys_data_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_dict`;
CREATE TABLE `sys_data_dict` (
  `id_` varchar(64) NOT NULL COMMENT 'id',
  `parent_id_` varchar(64) DEFAULT NULL COMMENT '上级id',
  `key_` varchar(255) NOT NULL COMMENT 'key',
  `name_` varchar(255) NOT NULL COMMENT 'name',
  `dict_key_` varchar(255) NOT NULL COMMENT '字典key',
  `type_id_` varchar(64) DEFAULT NULL COMMENT '分组id',
  `sn_` int(10) DEFAULT NULL COMMENT '排序',
  `dict_type_` varchar(10) NOT NULL COMMENT 'dict/node字典项',
  `delete_flag_` varchar(1) DEFAULT NULL COMMENT '是否删除',
  `create_time_` timestamp NOT NULL   COMMENT '创建时间',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='数据字典';

-- ----------------------------
-- Table structure for sys_data_source
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source`;
CREATE TABLE `sys_data_source` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `key_` varchar(64) DEFAULT NULL COMMENT '别名',
  `name_` varchar(64) DEFAULT NULL COMMENT '数据源名称',
  `desc_` varchar(256) DEFAULT NULL COMMENT '数据源的描述',
  `db_type_` varchar(64) DEFAULT NULL COMMENT '数据库类型',
  `class_path_` varchar(100) DEFAULT NULL COMMENT '数据源全路径',
  `attributes_json_` text COMMENT '属性配置',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `key_unique` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='数据源';

-- ----------------------------
-- Table structure for sys_data_source_def
-- ----------------------------
DROP TABLE IF EXISTS `sys_data_source_def`;
CREATE TABLE `sys_data_source_def` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `name_` varchar(64) DEFAULT NULL COMMENT '数据源名称',
  `class_path_` varchar(100) DEFAULT NULL COMMENT '数据源全路径',
  `attributes_json_` text COMMENT '属性配置',
  PRIMARY KEY (`id_`),
  KEY `class_path_unique` (`class_path_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='数据源模板';
 
-- ----------------------------
-- Table structure for sys_log_err
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_err`;
CREATE TABLE `sys_log_err` (
  `id_` varchar(50) NOT NULL COMMENT '主键',
  `account_` varchar(20) DEFAULT NULL COMMENT '帐号',
  `ip_` varchar(20) DEFAULT NULL COMMENT 'IP来源',
  `ip_address_` varchar(255) DEFAULT NULL COMMENT 'IP地址',
  `status_` varchar(64) DEFAULT NULL COMMENT '状态：unchecked，checked，fixed',
  `url_` varchar(1500) DEFAULT NULL COMMENT '错误URL',
  `content_` text COMMENT '出错信息',
  `request_param_` text COMMENT '请求参数',
  `create_time_` datetime DEFAULT NULL COMMENT '出错时间',
  `stack_trace_` longtext COMMENT '出错异常堆栈',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统异常日志';

-- ----------------------------
-- Table structure for sys_properties
-- ----------------------------
CREATE TABLE `sys_properties` (
  `id_` varchar(64) NOT NULL COMMENT 'ID',
  `name_` varchar(64) DEFAULT NULL COMMENT '属性名',
  `alias_` varchar(64) DEFAULT NULL COMMENT '别名',
  `group_` varchar(64) DEFAULT NULL COMMENT '分组',
  `value_` varchar(500) DEFAULT NULL COMMENT '值',
  `encrypt_` int(11) DEFAULT NULL COMMENT '是否加密',
  `update_by_` varchar(64) DEFAULT NULL,
  `update_time_` datetime DEFAULT NULL,
  `create_by_` varchar(64) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL,
  `description_` varchar(500) DEFAULT NULL,
  `environment_` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统属性';
 

CREATE TABLE `sys_resource` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `system_id_` varchar(64) DEFAULT NULL COMMENT '子系统ID',
  `alias_` varchar(64) DEFAULT NULL COMMENT '别名',
  `name_` varchar(64) DEFAULT NULL COMMENT '名字',
  `url_` varchar(120) DEFAULT NULL COMMENT '请求地址',
  `enable_` int(11) DEFAULT NULL COMMENT '显示到菜单(1,显示,0 ,不显示)',
  `opened_` int(11) DEFAULT NULL COMMENT '是否默认打开',
  `icon_` varchar(50) DEFAULT NULL COMMENT '图标',
  `type_` varchar(50) DEFAULT NULL COMMENT 'menu，button，link',
  `sn_` int(10) DEFAULT NULL COMMENT '排序',
  `parent_id_` varchar(50) DEFAULT NULL COMMENT '父节点ID',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源';

-- ----------------------------
-- Table structure for sys_res_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_res_role`;
CREATE TABLE `sys_res_role` (
  `ID_` varchar(50) NOT NULL DEFAULT '' COMMENT '主键',
  `SYSTEM_ID_` varchar(50) DEFAULT NULL COMMENT '系统ID',
  `res_id_` varchar(50) DEFAULT NULL COMMENT '资源ID',
  `role_id_` varchar(50) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='角色资源分配';
 
-- ----------------------------
-- Table structure for sys_script
-- ----------------------------
DROP TABLE IF EXISTS `sys_script`;
CREATE TABLE `sys_script` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `name_` varchar(128) DEFAULT NULL COMMENT '脚本名称',
  `script_` text COMMENT '脚本',
  `category_` varchar(128) DEFAULT NULL COMMENT '脚本分类',
  `memo_` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='常用脚本';

-- ----------------------------
-- Table structure for sys_serialno
-- ----------------------------
DROP TABLE IF EXISTS `sys_serialno`;
CREATE TABLE `sys_serialno` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `name_` varchar(64) DEFAULT NULL COMMENT '名称',
  `alias_` varchar(20) DEFAULT NULL COMMENT '别名',
  `regulation_` varchar(128) DEFAULT NULL COMMENT '规则',
  `gen_type_` smallint(6) DEFAULT NULL COMMENT '生成类型',
  `no_length_` int(11) DEFAULT NULL COMMENT '流水号长度',
  `cur_date_` varchar(20) DEFAULT NULL COMMENT '当前日期',
  `init_value_` int(11) DEFAULT NULL COMMENT '初始值',
  `cur_value_` int(11) DEFAULT NULL COMMENT '当前值',
  `step_` smallint(6) DEFAULT NULL COMMENT '步长',
  PRIMARY KEY (`id_`),
  KEY `idx_uni_alias_val` (`alias_`,`cur_value_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='流水号生成';

 
CREATE TABLE `sys_subsystem` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `name_` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '系统名称',
  `alias_` varchar(64) DEFAULT NULL COMMENT '系统别名',
  `url_` varchar(500) DEFAULT NULL COMMENT '子系统地址，空则为当前系统',
  `open_type_` varchar(64) DEFAULT NULL COMMENT '打开方式',
  `enabled_` int(11) DEFAULT NULL COMMENT '是否可用 1 可用，0 ，不可用',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `is_default_` int(11) DEFAULT NULL COMMENT '是否默认 1 可用，0 ，不可用',
  `desc_` varchar(500) DEFAULT NULL,
  `config_` varchar(2000) DEFAULT NULL,
  `create_by_` varchar(64) DEFAULT NULL,
  `update_time_` datetime DEFAULT NULL,
  `update_by_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子系统定义';


-- ----------------------------
-- Table structure for sys_tree
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree`;
CREATE TABLE `sys_tree` (
  `id_` varchar(64)  NOT NULL COMMENT '主键',
  `key_` varchar(64)  DEFAULT NULL COMMENT '别名',
  `name_` varchar(256)  DEFAULT NULL COMMENT '名字',
  `desc_` varchar(256)  DEFAULT NULL COMMENT '描述',
  `system_` tinyint(4) DEFAULT NULL COMMENT '是否系统内置树',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `key_unique_` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin    COMMENT='系统树';

-- ----------------------------
-- Table structure for sys_tree_node
-- ----------------------------
DROP TABLE IF EXISTS `sys_tree_node`;
CREATE TABLE `sys_tree_node` (
  `id_` varchar(64)  NOT NULL COMMENT '主键',
  `key_` varchar(64)  DEFAULT NULL COMMENT '别名',
  `name_` varchar(128)  DEFAULT NULL COMMENT '名字',
  `desc_` varchar(256)  DEFAULT NULL COMMENT '描述',
  `tree_id_` varchar(64)  DEFAULT NULL COMMENT '所属树id',
  `parent_id_` varchar(64)  DEFAULT NULL COMMENT '父ID',
  `path_` varchar(512)  DEFAULT NULL COMMENT '路径 eg:pppid.ppid.pid',
  `sn_` tinyint(4) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id_`),
  UNIQUE KEY `tree_id_key_unique_` (`key_`,`tree_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin    COMMENT='系统树节点';

-- ----------------------------
-- Table structure for sys_workbench_layout
-- ----------------------------
DROP TABLE IF EXISTS `sys_workbench_layout`;
CREATE TABLE `sys_workbench_layout` (
  `id_` varchar(64) NOT NULL,
  `panel_id_` varchar(255) NOT NULL COMMENT '面板id',
  `cust_width_` int(10) DEFAULT NULL COMMENT '自定义宽',
  `cust_height_` int(10) DEFAULT NULL COMMENT '自定义高',
  `sn_` int(10) DEFAULT NULL COMMENT '排序',
  `user_id_` varchar(64) NOT NULL COMMENT '用户id',
  `create_time_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id_`),
  KEY `idx_panel_id_` (`panel_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='工作台布局';

-- ----------------------------
-- Table structure for sys_workbench_panel
-- ----------------------------
DROP TABLE IF EXISTS `sys_workbench_panel`;
CREATE TABLE `sys_workbench_panel` (
  `id_` varchar(64) NOT NULL,
  `alias_` varchar(255) NOT NULL COMMENT '标识',
  `name_` varchar(255) NOT NULL DEFAULT '' COMMENT '名字',
  `type_` varchar(64) DEFAULT NULL COMMENT '类型',
  `desc_` varchar(500) DEFAULT NULL COMMENT '描述',
  `data_type_` varchar(64) DEFAULT NULL COMMENT '数据类型',
  `data_source_` varchar(255) DEFAULT NULL COMMENT '数据来源',
  `auto_refresh_` int(10) DEFAULT '0' COMMENT '自动刷新',
  `width_` int(10) DEFAULT NULL COMMENT '宽',
  `height_` int(10) DEFAULT NULL COMMENT '高',
  `display_content_` text COMMENT '展示内容',
  `more_url_` varchar(255) DEFAULT NULL COMMENT '更多链接',
  `create_time_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_by_` varchar(64) DEFAULT NULL,
  `update_time_`  datetime  DEFAULT NULL ,
  `update_by_` varchar(64) DEFAULT NULL,
  `delete_flag_` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_alias_` (`alias_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='工作台面板';

-- ----------------------------
-- Table structure for sys_workbench_panel_templ
-- ----------------------------
DROP TABLE IF EXISTS `sys_workbench_panel_templ`;
CREATE TABLE `sys_workbench_panel_templ` (
  `id_` varchar(64) NOT NULL,
  `key_` varchar(255) DEFAULT NULL COMMENT '模板key',
  `name_` varchar(255) DEFAULT NULL,
  `desc_` varchar(500) DEFAULT NULL COMMENT '模板描述',
  `html_` text COMMENT '模板内容',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='工作台面板模板';

CREATE TABLE `sys_file` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `name_` varchar(64) DEFAULT NULL COMMENT '附件名',
  `uploader_` varchar(128) DEFAULT NULL COMMENT '上传器',
  `path_` varchar(256) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人ID',
  `version_` int(11) DEFAULT NULL COMMENT '版本号',
  `delete_` tinyint(4) DEFAULT NULL COMMENT '逻辑删除标记',
  PRIMARY KEY (`id_`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='系统附件';


-- 附件存储 2018-6-10 00:29:06
-- IUploader 实现db策略的上传实现
CREATE TABLE `db_uploader` (
  `id_` varchar(64) NOT NULL,
  `bytes_` longblob,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `c_holiday_conf` (
  `id_` varchar(64) NOT NULL,
  `name_` varchar(255) DEFAULT NULL,
  `system_` varchar(255) DEFAULT NULL,
  `year_` int(255) DEFAULT NULL,
  `startDay_` date DEFAULT NULL,
  `endDay_` date DEFAULT NULL,
  `type_` varchar(255) DEFAULT NULL,
  `remark_` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `c_work_calendar` (
  `id_` varchar(20) NOT NULL,
  `day_` date DEFAULT NULL,
  `isWorkDay_` varchar(20) DEFAULT NULL,
  `type_` varchar(255) DEFAULT NULL,
  `system_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `c_schedule` (
  `id_` varchar(20) NOT NULL COMMENT 'ID',
  `title_` varchar(500) DEFAULT NULL COMMENT '标题',
  `desc_` varchar(2000) DEFAULT NULL COMMENT '描述',
  `task_url_` varchar(255) DEFAULT NULL COMMENT '任务连接',
  `type_` varchar(64) DEFAULT NULL COMMENT '类型',
  `open_type_` varchar(64) DEFAULT NULL COMMENT '任务打开方式',
  `owner_` varchar(64) DEFAULT NULL COMMENT '所属人',
  `owner_name_` varchar(64) DEFAULT NULL COMMENT '所属人',
  `participant_names_` varchar(1000) DEFAULT NULL COMMENT '参与者',
  `start_time_` datetime DEFAULT NULL COMMENT '开始日期',
  `end_time_` datetime DEFAULT NULL  COMMENT '结束日期',
  `actual_start_time_` datetime DEFAULT NULL COMMENT '实际开始日期',
  `complete_time_` datetime DEFAULT NULL COMMENT '完成时间',
  `rate_progress_` int(10) DEFAULT NULL COMMENT '进度',
  `submitter_` varchar(64) DEFAULT NULL COMMENT '提交人',
  `submitNamer_` varchar(64) DEFAULT NULL COMMENT '提交人',
  `remark_` varchar(500) DEFAULT NULL,
  `isLock_` varchar(10) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL  COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  `delete_flag_` varchar(10) DEFAULT NULL COMMENT '删除标记',
  `rev_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程';



CREATE TABLE `c_schedule_participant` (
  `id_` varchar(20) NOT NULL COMMENT 'id',
  `schedule_id_` varchar(20) DEFAULT NULL COMMENT '日程ID',
  `participantor_name_` varchar(255) DEFAULT NULL COMMENT '参与者名字',
  `participantor_` varchar(64) DEFAULT NULL COMMENT '参与者',
  `rate_progress_` int(10) DEFAULT NULL COMMENT 'ilka完成比例',
  `submit_comment_` varchar(500) DEFAULT NULL COMMENT 'ilka提交注释',
  `create_time_` datetime DEFAULT NULL   COMMENT '创建时间',
  `update_time_` datetime DEFAULT NULL   COMMENT '更新时间',
  `actual_start_time_` datetime DEFAULT NULL   COMMENT 'ilka实际开始时间',
  `complete_time_` datetime DEFAULT NULL   COMMENT 'ilka完成时间',
  PRIMARY KEY (`id_`),
  KEY `idx_schedule_id` (`schedule_id_`),
  KEY `idx_participantor` (`participantor_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程参与者';


CREATE TABLE `c_schedule_biz` (
  `id_` varchar(20) NOT NULL COMMENT 'id',
  `schedule_id_` varchar(20) NOT NULL COMMENT '日程id',
  `biz_id_` varchar(20) NOT NULL COMMENT '业务id',
  `from_` varchar(64) NOT NULL COMMENT '来源',
  PRIMARY KEY (`id_`),
  KEY `idx_schedule_id` (`schedule_id_`),
  KEY `idx_biz_id` (`biz_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程业务关联表';
