
-- 催办相关表
CREATE TABLE `bpm_plugin_reminder_trigger` (
  `id_` varchar(20) NOT NULL COMMENT 'ID',
  `task_id_` varchar(20) NOT NULL COMMENT '任务ID',
  `reminder_desc_` varchar(255) DEFAULT NULL COMMENT '催办的描述',
  `before_script_` varchar(500) DEFAULT NULL COMMENT '催办前置脚本',
  `msg_type_` varchar(10) DEFAULT NULL COMMENT '催办消息类型',
  `html_msg_` varchar(1000) DEFAULT NULL COMMENT 'html消息',
  `text_msg_` varchar(500) DEFAULT NULL COMMENT '普通消息',
  `is_calc_workday_` int(1) DEFAULT NULL COMMENT '是否工作日计算',
  `is_urgent_` int(1) DEFAULT NULL COMMENT '是否加急任务',
  `max_reminder_times` int(10) DEFAULT NULL COMMENT '最多催办次数',
  `reminder_times_` int(10) DEFAULT NULL COMMENT '催办次数',
  `reminder_cycle_` int(12) DEFAULT NULL COMMENT '催办周期',
  `duedate_` datetime NOT NULL COMMENT '到期时间',
  PRIMARY KEY (`id_`),
  KEY `task_id_` (`task_id_`) USING BTREE,
  KEY `duedate_` (`duedate_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程催办触发';



CREATE TABLE `bpm_plugin_reminder_log` (
  `id_` varchar(20) NOT NULL COMMENT 'ID',
  `instance_id_` varchar(20) DEFAULT NULL COMMENT '实例ID',
  `reminder_title_` varchar(255) DEFAULT NULL COMMENT '催办标题',
  `subject_` varchar(500) DEFAULT NULL COMMENT '流程标题',
  `node_id_` varchar(64) DEFAULT NULL COMMENT '节点ID',
  `msg_type_` varchar(64) DEFAULT NULL COMMENT '催办消息类型',
  `reminder_users_` varchar(500) DEFAULT NULL COMMENT '催办人',
  `reminder_userids_` varchar(500) DEFAULT NULL COMMENT '催办人ID',
  `reminder_date_` datetime DEFAULT NULL COMMENT '催办时间',
  `extend_` varchar(500) DEFAULT NULL COMMENT '其他说明',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程催办日志';


CREATE TABLE `bpm_user_agency_config` (
  `id_` varchar(64) NOT NULL COMMENT '配置ID',
  `start_datetime_` datetime NOT NULL COMMENT '起始时间',
  `end_datetime_` datetime NOT NULL COMMENT '结束时间',
  `agency_flow_key_` varchar(1000) NOT NULL COMMENT '代理流程编码，多个中间逗号分隔(,)',
  `agency_flow_name_` varchar(5000) NOT NULL COMMENT '代理流程名称，多个中间逗号分隔(,)',
  `config_user_id_` varchar(64) NOT NULL COMMENT '配置用户编码',
  `target_user_id_` varchar(1000) NOT NULL COMMENT '目标用户编码，多个中间逗号分隔(,)',
  `target_user_name_` varchar(1000) NOT NULL COMMENT '目标用户姓名，多个中间逗哥分隔(,)',
  `enable_` tinyint(1) NOT NULL DEFAULT '1' COMMENT '启用/未启用(1/0)',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_org_id_` varchar(64) DEFAULT NULL COMMENT '创建者所属组织ID',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人ID',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `rev_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  KEY `config_user_id_` (`config_user_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程用户代理配置';

CREATE TABLE `bpm_user_agency_log` (
  `id_` varchar(64) NOT NULL COMMENT '日志ID',
  `config_id_` varchar(64) NOT NULL COMMENT '配置ID',
  `flow_instance_id_` varchar(64) NOT NULL COMMENT '流程实例编号',
  `task_id_` varchar(64) NOT NULL COMMENT '代理任务编号',
  `task_node_id_` varchar(64) NOT NULL COMMENT '代理任务节点',
  `task_node_name_` varchar(64) NOT NULL COMMENT '代理任务节点名称',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_org_id_` varchar(64) DEFAULT NULL COMMENT '创建者所属组织ID',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人ID',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `rev_` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_`),
  KEY `config_id_` (`config_id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程用户代理日志';

-- 新增三个菜单资源

INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('406714104337661953', '1', 'userAgencyLogList', '代理记录', 'bpmplugin/userAgencyConfig/userAgencyLogList.html', '1', '1', '', 'menu', '6', '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('406714125032357889', '1', 'userAgencyConfig', '流程代理', 'bpmplugin/userAgencyConfig/tabs.html', '1', '1', '', 'menu', '5', '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('406719341437911041', '1', 'instanceList', '流程实例-部门', 'bpm/instance/instanceList_org.html', '1', '1', '', 'menu', '6', '10000000710005', NULL);

-- 流程乐观锁字段修改
update bpm_definition set rev_ = 0;
ALTER TABLE `bpm_definition`
MODIFY COLUMN `rev_` int(11) NOT NULL DEFAULT 0 AFTER `def_setting_`;

