-- v1.2.0 升级至 1.2.2 changesql

-- 2018-8-30 21:13:15 所有更新SQL 需要在此记录


ALTER TABLE `org_group` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;
	
ALTER TABLE `org_group_user` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `org_role` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `org_group_rel` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `org_group_reldef` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;



ALTER TABLE `org_user` 
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `org_user_role` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

	

ALTER TABLE `bus_table` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `bus_permission` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `bus_object` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `bus_column` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;

ALTER TABLE `bus_column_ctrl` 
ADD COLUMN `create_time_` datetime NULL COMMENT '创建时间',
ADD COLUMN `create_by_` varchar(64) NULL COMMENT '创建人' AFTER `create_time_`,
ADD COLUMN `update_time_` datetime NULL COMMENT '更新时间' AFTER `create_by_`,
ADD COLUMN `update_by_` varchar(64) NULL  COMMENT '更新人' AFTER `update_time_`;




/*
Navicat MySQL Data Transfer

Source Server         : 10.1.108.111
Source Server Version : 50630
Source Host           : 10.1.108.111:3306
Source Database       : bpms

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2018-03-13 10:30:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for c_holiday_conf
-- ----------------------------
DROP TABLE IF EXISTS `c_holiday_conf`;
CREATE TABLE `c_holiday_conf` (
  `id` varchar(64) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  `year` int(255) DEFAULT NULL,
  `startDay` date DEFAULT NULL,
  `endDay` date DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_holiday_conf
-- ----------------------------
INSERT INTO `c_holiday_conf` VALUES ('10000042150106', '春节', 'public', '2018', '2018-02-15', '2018-02-21', 'LR', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150107', '清明节', 'public', '2018', '2018-04-05', '2018-04-07', 'LR', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150108', '劳动节', 'public', '2018', '2018-04-29', '2018-05-01', 'LR', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150109', '端午节', 'public', '2018', '2018-06-16', '2018-06-18', 'LR', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150110', '中秋节	', 'public', '2018', '2018-09-22', '2018-09-24', 'LR', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150111', '国庆节', 'public', '2018', '2018-10-01', '2018-10-07', 'LR', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150112', '国庆节', 'public', '2018', '2018-09-29', '2018-09-30', 'LW', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150113', '劳动节', 'public', '2018', '2018-04-28', '2018-04-28', 'LW', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150114', '清明节', 'public', '2018', '2018-04-08', '2018-04-08', 'LW', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150115', '春节', 'public', '2018', '2018-02-11', '2018-02-11', 'LW', null);
INSERT INTO `c_holiday_conf` VALUES ('10000042150116', '春节', 'public', '2018', '2018-02-24', '2018-02-24', 'LW', null);

CREATE TABLE `c_work_calendar` (
  `id` varchar(20) NOT NULL,
  `day` date DEFAULT NULL,
  `isWorkDay` varchar(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `system` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `c_schedule` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `title` varchar(500) DEFAULT NULL COMMENT '标题',
  `desc` varchar(2000) DEFAULT NULL COMMENT '描述',
  `task_url` varchar(255) DEFAULT NULL COMMENT '任务连接',
  `type` varchar(64) DEFAULT NULL COMMENT '类型',
  `open_type` varchar(64) DEFAULT NULL COMMENT '任务打开方式',
  `owner` varchar(64) DEFAULT NULL COMMENT '所属人',
  `owner_name` varchar(64) DEFAULT NULL COMMENT '所属人',
  `participant_names` varchar(1000) DEFAULT NULL COMMENT '参与者',
  `start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '开始日期',
  `end_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '结束日期',
  `actual_start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '实际开始日期',
  `complete_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '完成时间',
  `rate_progress` int(10) DEFAULT NULL COMMENT '进度',
  `submitter` varchar(64) DEFAULT NULL COMMENT '提交人',
  `submitNamer` varchar(64) DEFAULT NULL COMMENT '提交人',
  `remark` varchar(500) DEFAULT NULL,
  `isLock` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  `delete_flag` varchar(10) DEFAULT NULL COMMENT '删除标记',
  `rev` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程';



CREATE TABLE `c_schedule_participant` (
  `id_` varchar(20) NOT NULL COMMENT 'id',
  `schedule_id` varchar(20) DEFAULT NULL COMMENT '日程ID',
  `participantor_name` varchar(255) DEFAULT NULL COMMENT '参与者名字',
  `participantor` varchar(64) DEFAULT NULL COMMENT '参与者',
  `rate_progress` int(10) DEFAULT NULL COMMENT 'ilka完成比例',
  `submit_comment` varchar(500) DEFAULT NULL COMMENT 'ilka提交注释',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `actual_start_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'ilka实际开始时间',
  `complete_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'ilka完成时间',
  PRIMARY KEY (`id_`),
  KEY `idx_schedule_id` (`schedule_id`),
  KEY `idx_participantor` (`participantor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程参与者';


CREATE TABLE `c_schedule_biz` (
  `id` varchar(20) NOT NULL COMMENT 'id',
  `schedule_id` varchar(20) NOT NULL COMMENT '日程id',
  `biz_id` varchar(20) NOT NULL COMMENT '业务id',
  `from` varchar(64) NOT NULL COMMENT '来源',
  PRIMARY KEY (`id`),
  KEY `idx_schedule_id` (`schedule_id`),
  KEY `idx_biz_id` (`biz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程业务关联表';

-- sys2整合 2018/9/22
UPDATE `sys_resource` SET `SYSTEM_ID_` = '1', `ALIAS_` = 'sysDataSourceDefList', `NAME_` = '系统数据源模板', `default_url_` = 'sys/sysDataSourceDef/sysDataSourceDefList.html', `ENABLE_MENU_` = 1, `HAS_CHILDREN_` = 1, `OPENED_` = 1, `ICON_` = '', `NEW_WINDOW_` = 0, `SN_` = 10, `PARENT_ID_` = '44', `CREATE_TIME_` = '2018-02-27 15:50:44' WHERE `ID_` = '20000001570004';
UPDATE `sys_resource` SET `SYSTEM_ID_` = '1', `ALIAS_` = 'sysTreeList', `NAME_` = '系统树', `default_url_` = 'sys/sysTree/sysTreeList.html', `ENABLE_MENU_` = 1, `HAS_CHILDREN_` = 1, `OPENED_` = 1, `ICON_` = '', `NEW_WINDOW_` = 0, `SN_` = 1521442292317, `PARENT_ID_` = '56', `CREATE_TIME_` = '2018-03-19 14:51:32' WHERE `ID_` = '20000002880001';
UPDATE `sys_resource` SET `SYSTEM_ID_` = '1', `ALIAS_` = 'sysDataSourceList', `NAME_` = '系统数据源', `default_url_` = 'sys/sysDataSource/sysDataSourceList.html', `ENABLE_MENU_` = 1, `HAS_CHILDREN_` = 1, `OPENED_` = 1, `ICON_` = '', `NEW_WINDOW_` = 0, `SN_` = 6, `PARENT_ID_` = '44', `CREATE_TIME_` = NULL WHERE `ID_` = '50';

-- 添加 IP地址 2018-9-28 10:15:04
ALTER TABLE `sys_log_err` 
ADD COLUMN `ip_address_` varchar(255) NULL COMMENT 'IP地址' AFTER `IP_`,
ADD COLUMN `status_` varchar(64) NULL COMMENT '状态：unchecked，checked，fixed' AFTER `ip_address_`,
ADD COLUMN `request_param_` text NULL COMMENT '请求参数' AFTER `CONTENT_`;

-- 添加 异常日志菜单
INSERT INTO `sys_resource`(`ID_`, `SYSTEM_ID_`, `ALIAS_`, `NAME_`, `default_url_`, `ENABLE_MENU_`, `HAS_CHILDREN_`, `OPENED_`, `ICON_`, `NEW_WINDOW_`, `SN_`, `PARENT_ID_`, `CREATE_TIME_`) VALUES ('403205519290925057', '1', 'errLog', '异常日志', 'sys/sysLogErr/sysLogErrList.html', 1, 1, 1, '', 0, 1538106991915, '56', NULL);


-- 添加 日程功能菜单 2018-10-4 14:59:57
UPDATE  `sys_resource` SET `ID_`='10000034500201', `SYSTEM_ID_`='1', `ALIAS_`='gjjjr', `NAME_`='国家节假日', `default_url_`='sys/holidayConf/holidayConfList.html', `ENABLE_MENU_`='1', `HAS_CHILDREN_`='1', `OPENED_`='1', `ICON_`='', `NEW_WINDOW_`='0', `SN_`='1516274778478', `PARENT_ID_`='44', `CREATE_TIME_`='2018-01-18 19:26:18' WHERE (`ID_`='10000034500201');
UPDATE  `sys_resource` SET `ID_`='10000045331201', `SYSTEM_ID_`='1', `ALIAS_`='rcb', `NAME_`='我的日程', `default_url_`='sys/schedule/scheduleDisplay.html', `ENABLE_MENU_`='1', `HAS_CHILDREN_`='1', `OPENED_`='1', `ICON_`='', `NEW_WINDOW_`='0', `SN_`='1517382648293', `PARENT_ID_`='1', `CREATE_TIME_`='2018-01-31 15:10:48' WHERE (`ID_`='10000045331201');
UPDATE  `sys_resource` SET `ID_`='10000047101201', `SYSTEM_ID_`='1', `ALIAS_`='rcgl', `NAME_`='日程管理', `default_url_`='sys/schedule/scheduleList.html', `ENABLE_MENU_`='1', `HAS_CHILDREN_`='1', `OPENED_`='1', `ICON_`='', `NEW_WINDOW_`='0', `SN_`='1517540203764', `PARENT_ID_`='1', `CREATE_TIME_`='2018-02-02 10:56:44' WHERE (`ID_`='10000047101201');
