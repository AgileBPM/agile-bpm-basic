ALTER TABLE bpm_task_stack DROP COLUMN path_;

ALTER TABLE `bpm_task_stack` MODIFY COLUMN `task_id_`  varchar(64) NOT NULL COMMENT '任务ID' AFTER `id_`,
ADD COLUMN node_type_  varchar(64) NULL AFTER is_muliti_task_;

update bpm_task_stack set  node_type_ = 'userNode';

ALTER TABLE `bpm_task_stack` ADD INDEX `idx_exestack_taskid` (`task_id_`) ;

ALTER TABLE `bpm_task_stack` 
MODIFY COLUMN `node_type_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点类型' AFTER `is_muliti_task_`,
ADD COLUMN `action_name_` varchar(64) NULL COMMENT '响应动作' AFTER `node_type_`;

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
