-- 流程模块表创建语句   含 activiti SQL， AgileBPM包装的SQL



SET FOREIGN_KEY_CHECKS=0;

-- ---------------- Activiti table create --------------------

-- ----------------------------
-- Table structure for act_evt_log
-- ----------------------------
DROP TABLE IF EXISTS `act_evt_log`;
CREATE TABLE `act_evt_log` (
  `LOG_NR_` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE_` varchar(64)  DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64)  DEFAULT NULL,
  `PROC_INST_ID_` varchar(64)  DEFAULT NULL,
  `EXECUTION_ID_` varchar(64)  DEFAULT NULL,
  `TASK_ID_` varchar(64)  DEFAULT NULL,
  `TIME_STAMP_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `USER_ID_` varchar(255)  DEFAULT NULL,
  `DATA_` longblob,
  `LOCK_OWNER_` varchar(255)  DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  `IS_PROCESSED_` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`LOG_NR_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ge_bytearray
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_bytearray`;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `act_ge_bytearray_ibfk_1` (`DEPLOYMENT_ID_`) USING BTREE,
  CONSTRAINT `act_ge_bytearray_ibfk_1` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ge_property
-- ----------------------------
DROP TABLE IF EXISTS `act_ge_property`;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) NOT NULL,
  `VALUE_` varchar(300) DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


DROP TABLE IF EXISTS `act_procdef_info`;
CREATE TABLE `act_procdef_info` (
  `ID_` varchar(64)  NOT NULL,
  `PROC_DEF_ID_` varchar(64)  NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `INFO_JSON_ID_` varchar(64)  DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_INFO_PROCDEF` (`PROC_DEF_ID_`) USING BTREE,
  KEY `ACT_IDX_INFO_PROCDEF` (`PROC_DEF_ID_`) USING BTREE
) ENGINE=InnoDB ;

-- ----------------------------
-- Table structure for act_re_deployment
-- ----------------------------
DROP TABLE IF EXISTS `act_re_deployment`;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `DEPLOY_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_re_model
-- ----------------------------
DROP TABLE IF EXISTS `act_re_model`;
CREATE TABLE `act_re_model` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `META_INFO_` varchar(4000) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) DEFAULT NULL,
  `EDITOR_SOURCE_VALUE_ID_` varchar(64) DEFAULT NULL,
  `EDITOR_SOURCE_EXTRA_VALUE_ID_` varchar(64) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `CREATE_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATE_TIME_` datetime NOT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_re_procdef
-- ----------------------------
DROP TABLE IF EXISTS `act_re_procdef`;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) NOT NULL,
  `VERSION_` int(11) NOT NULL,
  `DEPLOYMENT_ID_` varchar(64) DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `HAS_GRAPHICAL_NOTATION_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`,`TENANT_ID_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- ----------------------------
-- Table structure for act_ru_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_identitylink`;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `USER_ID_` varchar(255) DEFAULT NULL,
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`) USING BTREE,
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`) USING BTREE,
  KEY `act_ru_identitylink_ibfk_1` (`PROC_DEF_ID_`) USING BTREE,
  KEY `act_ru_identitylink_ibfk_2` (`PROC_INST_ID_`) USING BTREE,
  KEY `act_ru_identitylink_ibfk_3` (`TASK_ID_`) USING BTREE,
  CONSTRAINT `act_ru_identitylink_ibfk_1` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`),
  CONSTRAINT `act_ru_identitylink_ibfk_2` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `act_ru_identitylink_ibfk_3` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


-- ----------------------------
-- Table structure for act_ru_job
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_job`;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) NOT NULL,
  `LOCK_OWNER_` varchar(255) DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) DEFAULT NULL,
  `REPEAT_` varchar(255) DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `LOCK_EXP_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DUEDATE_` datetime NOT NULL,
  PRIMARY KEY (`ID_`),
  KEY `act_ru_job_ibfk_1` (`EXCEPTION_STACK_ID_`) USING BTREE,
  CONSTRAINT `act_ru_job_ibfk_1` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ru_event_subscr
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_event_subscr`;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) NOT NULL,
  `EVENT_NAME_` varchar(255) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) DEFAULT NULL,
  `CONFIGURATION_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ru_execution
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_execution`;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) DEFAULT NULL,
  `PARENT_ID_` varchar(64) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) DEFAULT NULL,
  `ACT_ID_` varchar(255) DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `LOCK_TIME_` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`) USING BTREE,
  KEY `act_ru_execution_ibfk_1` (`PARENT_ID_`) USING BTREE,
  KEY `act_ru_execution_ibfk_2` (`PROC_DEF_ID_`) USING BTREE,
  KEY `act_ru_execution_ibfk_4` (`SUPER_EXEC_`) USING BTREE,
  CONSTRAINT `act_ru_execution_ibfk_1` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `act_ru_execution_ibfk_2` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
  CONSTRAINT `act_ru_execution_ibfk_3` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


DROP TABLE IF EXISTS `act_ru_task`;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) DEFAULT NULL,
  `OWNER_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `DELEGATION_` varchar(64) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(255) DEFAULT NULL,
  `CREATE_TIME_` timestamp NULL DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  `FORM_KEY_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_RU_TASK_IBFK_1` (`EXECUTION_ID_`) USING BTREE,
  KEY `act_ru_task_ibfk_2` (`PROC_DEF_ID_`) USING BTREE,
  KEY `act_ru_task_ibfk_3` (`PROC_INST_ID_`) USING BTREE,
  CONSTRAINT `act_ru_task_ibfk_1` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `act_ru_task_ibfk_2` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE,
  CONSTRAINT `act_ru_task_ibfk_3` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

-- ----------------------------
-- Table structure for act_ru_variable
-- ----------------------------
DROP TABLE IF EXISTS `act_ru_variable`;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) NOT NULL,
  `NAME_` varchar(255) NOT NULL,
  `EXECUTION_ID_` varchar(64) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) DEFAULT NULL,
  `TEXT2_` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`) USING BTREE,
  KEY `act_ru_variable_ibfk_1` (`BYTEARRAY_ID_`) USING BTREE,
  KEY `act_ru_variable_ibfk_2` (`EXECUTION_ID_`) USING BTREE,
  KEY `act_ru_variable_ibfk_3` (`PROC_INST_ID_`) USING BTREE,
  CONSTRAINT `act_ru_variable_ibfk_1` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `act_ru_variable_ibfk_2` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `act_ru_variable_ibfk_3` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
 

-- ---------------- AgileBPM table create --------------------

-- ----------------------------
-- Table structure for bpm_bus_link
-- ----------------------------
DROP TABLE IF EXISTS `bpm_bus_link`;
CREATE TABLE `bpm_bus_link` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `def_id_` varchar(64) DEFAULT NULL COMMENT '流程定义ID',
  `inst_id_` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `biz_id_` varchar(64) DEFAULT NULL COMMENT '业务主键',
  `biz_code_` varchar(64) NOT NULL COMMENT 'bo_code',
  PRIMARY KEY (`id_`,`biz_code_`)
) ENGINE=InnoDB COMMENT='流程实例与业务数据关系表'
/*!50500 PARTITION BY LIST  COLUMNS(biz_code_)
(PARTITION p01 VALUES IN ('unknown') ENGINE = InnoDB) */;

-- ----------------------------
-- Table structure for bpm_definition
-- ----------------------------
DROP TABLE IF EXISTS `bpm_definition`;
CREATE TABLE `bpm_definition` (
  `id_` varchar(64) NOT NULL COMMENT '流程定义ID',
  `name_` varchar(64) NOT NULL COMMENT '流程名称',
  `key_` varchar(64) NOT NULL COMMENT '流程业务主键',
  `desc_` varchar(1024) DEFAULT NULL COMMENT '流程描述',
  `type_id_` varchar(64) DEFAULT NULL COMMENT '所属分类ID',
  `status_` varchar(40) DEFAULT NULL COMMENT '流程状态。草稿、发布、禁用',
  `act_def_id_` varchar(64) DEFAULT NULL COMMENT 'BPMN - 流程定义ID',
  `act_model_id_` varchar(64) DEFAULT NULL,
  `act_deploy_id_` varchar(64) DEFAULT NULL COMMENT 'BPMN - 流程发布ID',
  `version_` int(11) DEFAULT NULL COMMENT '版本 - 当前版本号',
  `main_def_id_` varchar(64) DEFAULT NULL COMMENT '版本 - 主版本流程ID',
  `is_main_` char(1) DEFAULT NULL COMMENT '版本 - 是否主版本',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_org_id_` varchar(64) DEFAULT NULL COMMENT '创建者所属组织ID',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人ID',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `support_mobile_` int(11) DEFAULT '0',
  `def_setting_` text,
  `rev_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  KEY `bpm_process_def_key` (`key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin  COMMENT='流程定义';

-- ----------------------------
-- Table structure for bpm_instance
-- ----------------------------
DROP TABLE IF EXISTS `bpm_instance`;
CREATE TABLE `bpm_instance` (
  `id_` varchar(64) NOT NULL COMMENT '流程实例ID',
  `subject_` varchar(128) NOT NULL COMMENT '流程实例标题',
  `def_id_` varchar(64) NOT NULL COMMENT '流程定义ID',
  `act_def_id_` varchar(64) DEFAULT NULL COMMENT 'BPMN流程定义ID',
  `def_key_` varchar(128) DEFAULT NULL COMMENT '流程定义Key',
  `def_name_` varchar(128) NOT NULL COMMENT '流程名称',
  `biz_key_` varchar(64) DEFAULT NULL COMMENT '关联数据业务主键',
  `status_` varchar(40) DEFAULT NULL COMMENT '实例状态',
  `end_time_` datetime DEFAULT NULL COMMENT '实例结束时间',
  `duration_` bigint(20) DEFAULT NULL COMMENT '持续时间(ms)',
  `type_id_` varchar(64) DEFAULT NULL COMMENT '所属分类ID',
  `act_inst_id_` varchar(64) DEFAULT NULL COMMENT 'BPMN流程实例ID',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `creator_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_org_id_` varchar(64) DEFAULT NULL COMMENT '创建者所属组织ID',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人ID',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `is_formmal_` char(1) NOT NULL COMMENT '是否正式数据',
  `parent_inst_id_` varchar(64) DEFAULT NULL COMMENT '父实例Id',
  `is_forbidden_` smallint(6) DEFAULT NULL COMMENT '禁止',
  `data_mode_` varchar(20) DEFAULT NULL,
  `support_mobile_` int(11) DEFAULT '0',
  `super_node_id_` varchar(50) DEFAULT NULL COMMENT '父流程定义节点ID',
  PRIMARY KEY (`id_`),
  KEY `idx_proinst_bpminstid` (`act_inst_id_`) USING BTREE,
  KEY `idx_proinst_parentId` (`parent_inst_id_`) USING BTREE,
  KEY `idx_proinst_bizkey` (`biz_key_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程实例';

-- ----------------------------
-- Table structure for bpm_task
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task`;
CREATE TABLE `bpm_task` (
  `id_` varchar(64) NOT NULL COMMENT '任务ID',
  `name_` varchar(64) NOT NULL COMMENT '任务名称',
  `subject_` varchar(128) NOT NULL COMMENT '待办事项标题',
  `inst_id_` varchar(64) NOT NULL COMMENT '关联 - 流程实例ID',
  `task_id_` varchar(64) DEFAULT NULL COMMENT '关联的任务ID',
  `act_inst_id_` varchar(64) DEFAULT NULL COMMENT 'activiti 实例id',
  `act_execution_id_` varchar(64) DEFAULT NULL COMMENT 'activiti 执行id',
  `node_id_` varchar(64) DEFAULT NULL COMMENT '关联 - 任务节点ID',
  `def_id_` varchar(64) NOT NULL COMMENT '关联 - 流程定义ID',
  `assignee_id_` varchar(64) DEFAULT NULL COMMENT '任务执行人ID',
  `assignee_names_` varchar(500) DEFAULT NULL,
  `status_` varchar(64) NOT NULL COMMENT '任务状态',
  `priority_` int(11) DEFAULT NULL COMMENT '任务优先级',
  `due_time_` datetime DEFAULT NULL COMMENT '任务到期时间',
  `task_type_` varchar(64) DEFAULT NULL COMMENT '任务类型',
  `parent_id_` varchar(64) DEFAULT NULL COMMENT '父任务ID',
  `type_id_` varchar(64) DEFAULT NULL COMMENT '分类ID',
  `create_time_` datetime NOT NULL COMMENT '任务创建时间',
  `create_by_` varchar(64) DEFAULT NULL,
  `support_mobile_` int(11) DEFAULT '0',
  `back_node_` varchar(64) DEFAULT NULL COMMENT '返回节点',
  PRIMARY KEY (`id_`),
  KEY `idx_bpmtask_instid` (`inst_id_`) USING BTREE,
  KEY `idx_bpmtask_taskid` (`task_id_`) USING BTREE,
  KEY `idx_bpmtask_parentid` (`parent_id_`) USING BTREE,
  KEY `idx_bpmtask_userid` (`assignee_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程任务';

-- ----------------------------
-- Table structure for bpm_task_identitylink
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_identitylink`;
CREATE TABLE `bpm_task_identitylink` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `task_id_` varchar(64) DEFAULT NULL COMMENT '任务ID',
  `inst_id_` varchar(64) DEFAULT NULL,
  `type_` varchar(20) DEFAULT NULL COMMENT '候选人类型',
  `identity_name_` varchar(64) DEFAULT NULL COMMENT '名字',
  `identity_` varchar(20) DEFAULT NULL COMMENT 'ID',
  `permission_code_` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  KEY `idx_taskcandidate_taskid` (`task_id_`) USING BTREE,
  KEY `idx_candidate_instid` (`inst_id_`) USING BTREE,
  KEY `idx_permission_code_` (`permission_code_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='任务候选人';

-- ----------------------------
-- Table structure for bpm_task_opinion
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_opinion`;
CREATE TABLE `bpm_task_opinion` (
  `id_` varchar(64) NOT NULL COMMENT '意见ID',
  `inst_id_` varchar(64) NOT NULL COMMENT '流程实例ID',
  `sup_inst_id_` varchar(64) DEFAULT NULL COMMENT '父流程实例ID',
  `task_id_` varchar(64) DEFAULT NULL COMMENT '任务ID',
  `task_key_` varchar(64) DEFAULT NULL COMMENT '任务定义Key',
  `task_name_` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `token_` varchar(64) DEFAULT NULL COMMENT '任务令牌',
  `assign_info_` varchar(2000) DEFAULT NULL COMMENT '任务分配情况',
  `approver_` varchar(64) DEFAULT NULL COMMENT '审批人',
  `approver_name_` varchar(64) DEFAULT NULL COMMENT '审批人名字',
  `approve_time_` datetime DEFAULT NULL COMMENT '审批时间',
  `opinion_` varchar(2000) DEFAULT NULL COMMENT '审批意见',
  `status_` varchar(64) NOT NULL COMMENT '审批状态。start=发起流程；awaiting_check=待审批；agree=同意；against=反对；return=驳回；abandon=弃权；retrieve=追回',
  `form_id_` varchar(64) DEFAULT NULL COMMENT '表单定义ID',
  `create_by_` varchar(255) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '执行开始时间',
  `dur_ms_`  bigint(20) DEFAULT NULL COMMENT '持续时间(ms)',
  PRIMARY KEY (`id_`),
  KEY `idx_opinion_supinstid` (`sup_inst_id_`) USING BTREE,
  KEY `idx_opinion_task` (`task_id_`) USING BTREE,
  KEY `idx_opinion_instId` (`inst_id_`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程任务审批记录';

-- ----------------------------
-- Table structure for bpm_task_stack
-- ----------------------------
DROP TABLE IF EXISTS `bpm_task_stack`;
CREATE TABLE `bpm_task_stack` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `task_id_` varchar(64) NOT NULL COMMENT '任务ID',
  `inst_id_` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `parent_id_` varchar(64) DEFAULT NULL COMMENT '父ID',
  `node_id_` varchar(64) NOT NULL COMMENT '节点ID',
  `node_name_` varchar(125) DEFAULT NULL,
  `start_time_` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `is_muliti_task_` smallint(6) DEFAULT NULL COMMENT '1=是\r\n                        0=否',
  `node_type_` varchar(64) DEFAULT NULL COMMENT '节点类型',
  `action_name_` varchar(64) DEFAULT NULL COMMENT '响应动作',
  PRIMARY KEY (`id_`),
  KEY `idx_exestack_instid` (`inst_id_`) USING BTREE,
  KEY `idx_exestack_taskid` (`task_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin COMMENT='流程执行堆栈树';


-- 流程提交日志插件
CREATE TABLE `bpm_submit_data_log` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `task_id_` varchar(64) DEFAULT NULL COMMENT '任务ID',
  `inst_id_` varchar(64) DEFAULT NULL COMMENT '实例ID',
  `data` longtext COMMENT '业务数据',
  `destination` varchar(255) DEFAULT NULL COMMENT '目标节点',
  `extendConf` varchar(500) DEFAULT NULL COMMENT '特殊配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务对象数据提交日志';

