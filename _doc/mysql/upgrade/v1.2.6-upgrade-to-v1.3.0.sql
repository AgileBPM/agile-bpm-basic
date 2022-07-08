SET FOREIGN_KEY_CHECKS=0;

ALTER TABLE `org_group` ADD COLUMN `type_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型：0集团，1公司，3部门' AFTER `code_`;
ALTER TABLE `org_group` ADD COLUMN `path_`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `update_by_`;
ALTER TABLE `org_group` DROP COLUMN `grade_`;
CREATE INDEX `parent_id_` ON `org_group`(`parent_id_`) USING BTREE ;

CREATE TABLE `org_relation` (
`id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID' ,
`group_id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组ID' ,
`user_id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID' ,
`is_master_`  int(11) NULL DEFAULT 0 COMMENT '0:默认组织，1：从组织' ,
`role_id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色ID' ,
`status_`  int(11) NULL DEFAULT 1 COMMENT '状态：1启用，2禁用' ,
`type_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型：groupUser,groupRole,userRole,groupUserRole' ,
`create_time_`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`create_by_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人' ,
`update_time_`  datetime NULL DEFAULT NULL COMMENT '更新时间' ,
`update_by_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人' ,
PRIMARY KEY (`id_`),
INDEX `FK_reference_21` (`user_id_`) USING BTREE ,
INDEX `FK_reference_22` (`group_id_`) USING BTREE ,
INDEX `FK_reference_23` (`role_id_`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;

CREATE VIEW org_relation_view AS
SELECT tgroup.name_ groupName,tuser.fullname_ userName,role.name_ roleName ,
	CONCAT(tgroup.name_,'-',role.name_) postName,concat(`tgroup`.`id_`,'-',`role`.`id_`) AS `postId`, relation.*
	FROM org_relation relation 
	left join org_user tuser on relation.user_id_ = tuser.id_
	left join org_group tgroup on relation.group_id_ = tgroup.id_
	left join org_role role on relation.role_id_ = role.id_;

ALTER TABLE `org_role` ADD COLUMN `type_id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类ID' AFTER `update_by_`;
ALTER TABLE `org_role` ADD COLUMN `type_name_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名字' AFTER `type_id_`;
ALTER TABLE `org_user` MODIFY COLUMN `address_`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址' AFTER `weixin_`;
ALTER TABLE `org_user` MODIFY COLUMN `photo_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像' AFTER `address_`;
ALTER TABLE `org_user` MODIFY COLUMN `sex_`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别：男，女，未知' AFTER `photo_`;
ALTER TABLE `org_user` MODIFY COLUMN `from_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '来源' AFTER `sex_`;
ALTER TABLE `org_user` MODIFY COLUMN `status_`  int(11) NOT NULL DEFAULT 1 COMMENT '0:禁用，1正常' AFTER `from_`;
ALTER TABLE `org_user` MODIFY COLUMN `update_by_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新人' AFTER `create_by_`;
CREATE INDEX `account` ON `org_user`(`account_`) USING BTREE ;

ALTER TABLE `sys_properties` MODIFY COLUMN `id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ID' FIRST ;
ALTER TABLE `sys_properties` MODIFY COLUMN `name_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '属性名' AFTER `id_`;
ALTER TABLE `sys_properties` MODIFY COLUMN `alias_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名' AFTER `name_`;
ALTER TABLE `sys_properties` MODIFY COLUMN `group_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分组' AFTER `alias_`;
ALTER TABLE `sys_properties` MODIFY COLUMN `value_`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值' AFTER `group_`;
ALTER TABLE `sys_properties` MODIFY COLUMN `encrypt_`  int(11) NULL DEFAULT NULL COMMENT '是否加密' AFTER `value_`;

DROP TABLE `sys_resource`;

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

INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('1', '1', 'personOffice', '个人办公', '', '1', '1', 'slideshare', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('1000000071000', '1', '1taskList', '任务列表', 'bpm/task/taskList.html', '1', '1', '', 'menu', '1', '10000000710005', '2016-11-16 20:04:26');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000000710005', '1', 'newFlow', '流程管理', NULL, '1', '1', '', 'menu', '1', '0', '2016-11-16 19:57:50');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000000710006', '1', 'list', '流程列表', 'bpm/definition/definitionList.html', '1', '1', '', 'menu', '1', '10000000710005', '2016-11-16 20:04:26');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000001480006', '1', 'sysres', '资源管理', 'sys/sysResource/sysResourceList.html', '1', '1', '', 'menu', '1', '44', '2017-03-16 23:52:15');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000001640004', '1', 'formCustDialogList', '自定义对话框', 'form/formCustDialog/formCustDialogList.html', '1', '1', '', 'menu', '2', '56', '2017-04-05 23:38:15');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000001640007', '1', 'combinDialog', '组合对话框', '/form/combinateDialog/combinateDialogList', '0', '1', '', 'menu', '2', '56', '2017-04-05 23:39:58');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000034500201', '1', 'gjjjr', '国家节假日', 'sys/holidayConf/holidayConfList.html', '1', '1', '', 'menu', '1', '44', '2018-01-18 19:26:18');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000045331201', '1', 'rcb', '我的日程', 'sys/schedule/scheduleDisplay.html', '1', '1', '', 'menu', '6', '1', '2018-01-31 15:10:48');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000047101201', '1', 'rcgl', '日程管理', 'sys/schedule/scheduleList.html', '1', '1', '', 'menu', '6', '1', '2018-02-02 10:56:44');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('10000052971201', '1', 'gztgl', '工作台管理', 'sys/workbenchPanel/workbenchPanelList.html', '1', '1', '', 'menu', '2', '44', '2018-02-28 14:52:46');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('11', '1', 'myDraft', '我的草稿', 'bpm/my/draftList.html', '1', '1', '', 'menu', '1', '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('18', '1', 'orgManager', '用户组织', '', '1', '1', 'users', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('19', '1', 'userManager', '用户管理', 'org/user/userList.html', '1', '1', '', 'menu', '1', '18', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('2', '1', 'flowEvent', '事项办理', '', '1', '1', '', 'menu', '1', '1', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('20', '1', 'orgMgr', '组织管理', 'org/group/groupList.html', '1', '1', '', 'menu', '1', '18', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('20000001570004', '1', 'sysDataSourceDefList', '系统数据源模板', 'sys/sysDataSourceDef/sysDataSourceDefList.html', '1', '1', '', 'menu', '1', '44', '2018-02-27 15:50:44');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('20000002880001', '1', 'sysTreeList', '系统树', 'sys/sysTree/sysTreeList.html', '1', '1', '', 'menu', '3', '56', '2018-03-19 14:51:32');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('20000002980001', '1', 'copeTask', '我的抄送', NULL, '0', '1', '', 'menu', '1', '2', '2018-03-25 19:07:18');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('20000003070153', '1', 'processInstanceList', '流程实例', 'bpm/instance/instanceList.html', '1', '1', '', 'menu', '1', '10000000710005', '2018-03-18 17:01:06');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('20000010520001', '1', 'myTodo', '待办事项', 'bpm/my/todoTaskList.html', '1', '1', '', 'menu', '1', '2', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('21', '1', 'roleMgr', '角色管理', 'org/role/roleList.html', '1', '1', '', 'menu', '1', '18', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('23', '1', 'flowManager', '表单管理', '', '1', '1', '', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('25', '1', 'boManager', '业务对象', '', '1', '1', 'fa-database', 'menu', '1', '23', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('26', '1', 'businessTableList', '业务实体', 'bus/businessTable/businessTableList.html', '1', '1', '', 'menu', '1', '25', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('27', '1', 'businessObjectList', '业务对象', 'bus/buinessObject/businessObjectList.html', '1', '1', '', 'menu', '1', '25', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('28', '1', 'formDefManager', '表单定义', '', '1', '1', 'fa-th-list', 'menu', '1', '23', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('30', '1', 'boForm', '业务表单', 'form/formDef/formDefList.html', '1', '1', '', 'menu', '1', '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('31', '1', 'mobileForm', '手机表单', 'form/formDef/mobileFormDefList.html', '1', '1', '', 'menu', '1', '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('32', '1', 'formTemplate', '表单模版', 'form/formTemplate/formTemplateList.html', '1', '1', '', 'menu', '1', '28', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('4', '1', 'myHandledEvent', '办理历史', 'bpm/my/approveList.html', '1', '1', '', 'menu', '1', '2', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('403205519290925057', '1', 'errLog', '异常日志', 'sys/sysLogErr/sysLogErrList.html', '1', '1', '', 'menu', '5', '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('404802462664884225', '2', 'testSubSystem', '测试子系统', 'https://www.hao123.com/', '1', '1', '', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('404802467431710721', '2', 'test', '子系统', 'test', '1', '1', '', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('404802513291968513', '2', 'aaasdfasdf', 'aaaaa', 'asdf', '1', '1', '', 'menu', '1', '404802462664884225', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('404802617380700161', '3', '11111', '111', NULL, '1', '1', '', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('404818977800323073', '1', 'icon', '字体图标', 'sys/icon/iconSelector.html', '1', '1', '', 'menu', '9', '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('405244952846008321', '1', 'userManager:add', '添加', '/org/user/save', '1', '1', '', 'button', '1', '19', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('405244965276614657', '1', 'userManager:edit', '编辑', '/org/user/save', '1', '1', '', 'button', '1', '19', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('405245052357181441', '1', 'userManager:del', '删除', '/org/user/remove', '1', '1', '', 'button', '1', '19', '2018-12-27 13:06:33');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('405313955925590017', '1', 'userManager:search', '搜索', '/org/user/listJson', '1', '1', '', 'button', '1', '19', '2018-12-30 14:07:19');
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('43', '1', 'sysSetting', '系统配置', '', '1', '1', 'cogs', 'menu', '1', '0', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('44', '1', 'systemMgr', '系统设置', '', '1', '1', '', 'menu', '1', '43', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('46', '1', 'dicManager', '数据字典', 'sys/dataDict/dataDictList.html', '1', '1', '', 'menu', '1', '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('47', '1', 'schedulerMgr', '定时计划', 'sys/scheduleJob/sysjobList.html', '1', '1', '', 'menu', '1', '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('49', '1', 'syspropertyMgr', '系统属性管理', 'sys/sysProperties/sysPropertiesList.html', '1', '1', '', 'menu', '1', '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('50', '1', 'sysDataSourceList', '系统数据源', 'sys/sysDataSource/sysDataSourceList.html', '1', '1', '', 'menu', '1', '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('54', '1', 'subSystemMgr', '子系统管理', '/base/base/subsystem/subsystemList', '0', '1', '', 'menu', '1', '44', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('56', '1', 'flowAssist', '开发辅助', '', '1', '1', '', 'menu', '1', '43', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('60', '1', 'serialNoMgr', '流水号', 'sys/serialNo/serialNoList.html', '1', '1', '', 'menu', '1', '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('61', '1', 'scriptMgr', '常用脚本', 'sys/script/scriptList.html', '1', '1', '', 'menu', '1', '56', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('7', '1', 'myStartEvent', '事项申请', '', '1', '1', '', 'menu', '1', '1', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('8', '1', 'myStartFlow', '发起申请', 'bpm/my/definitionList.html', '1', '1', '', 'menu', '1', '7', NULL);
INSERT INTO `sys_resource` (`ID_`, `system_id_`, `alias_`, `name_`, `url_`, `enable_`, `opened_`, `icon_`, `type_`, `sn_`, `parent_id_`, `create_time_`) VALUES ('9', '1', 'myRequest', '申请历史', 'bpm/my/applyTaskList.html', '1', '1', '', 'menu', '1', '7', NULL);
-- 清除老的对话框、重新初始化
delete from form_cust_dialog where  key_ in ('orgSelector','roleSelector','userSelector','postSelector');
INSERT INTO `form_cust_dialog`(`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003130001', 'userSelector', '用户查询', NULL, 'list', 'dataSourceDefault', '本地数据源', 'table', 'org_user', 1, 10, 930, 660, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"fullname_\",\"showName\":\"姓名\"},{\"columnName\":\"account_\",\"showName\":\"账号\"},{\"columnName\":\"mobile_\",\"showName\":\"手机号码\"}]', '[{\"columnName\":\"fullname_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"姓名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"account_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"账号\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"status_\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"0:禁用，1正常\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"fullname_\",\"returnName\":\"name\"},{\"columnName\":\"account_\",\"returnName\":\"account\"},{\"columnName\":\"email_\",\"returnName\":\"email\"},{\"columnName\":\"mobile_\",\"returnName\":\"mobile\"},{\"columnName\":\"weixin_\",\"returnName\":\"weixin\"},{\"columnName\":\"address_\",\"returnName\":\"address\"},{\"columnName\":\"sex_\",\"returnName\":\"sex\"},{\"columnName\":\"status_\",\"returnName\":\"status\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog`(`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003160001', 'orgSelector', '组织选择框', NULL, 'tree', 'dataSourceDefault', '本地数据源', 'table', 'org_group', 1, 10, 800, 600, 1, 1, '{\"id\":\"id_\",\"pid\":\"parent_id_\",\"pidInitVal\":\"0\",\"pidInitValScript\":false,\"showColumn\":\"name_\"}', '[]', '[]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"parent_id_\",\"returnName\":\"parentId\"},{\"columnName\":\"code_\",\"returnName\":\"key\"},{\"columnName\":\"type_\",\"returnName\":\"type\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog`(`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('20000003160002', 'roleSelector', '角色对话框', '', 'list', 'dataSourceDefault', '本地数据源', 'table', 'org_role', 1, 10, 800, 600, 1, 1, '{\"pidInitValScript\":false}', '[{\"columnName\":\"name_\",\"showName\":\"角色名称\"},{\"columnName\":\"alias_\",\"showName\":\"别名\"}]', '[{\"columnName\":\"name_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"名称\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"alias_\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"别名\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"name_\",\"returnName\":\"name\"},{\"columnName\":\"alias_\",\"returnName\":\"key\"},{\"columnName\":\"enabled_\",\"returnName\":\"enabled\"}]', '[]', NULL);
INSERT INTO `form_cust_dialog`(`id_`, `key_`, `name_`, `desc_`, `style_`, `ds_key_`, `ds_name_`, `obj_type_`, `obj_name_`, `page_`, `page_size_`, `width_`, `height_`, `system_`, `multiple_`, `tree_config_json_`, `display_fields_json_`, `condition_fields_json_`, `return_fields_json_`, `sort_fields_json_`, `data_source_`) VALUES ('405338661247254529', 'postSelector', '岗位选择', '岗位', 'list', 'dataSourceDefault', '本地数据源', 'view', 'org_relation_view', 1, 10, 800, 600, 1, 0, '{\"pidInitValScript\":false}', '[{\"columnName\":\"groupName\",\"showName\":\"组织名\"},{\"columnName\":\"roleName\",\"showName\":\"职务\"}]', '[{\"columnName\":\"status_\",\"condition\":\"EQ\",\"dbType\":\"number\",\"showName\":\"状态：1启用，2禁用\",\"value\":{\"text\":\"1\"},\"valueSource\":\"fixedValue\"},{\"columnName\":\"type_\",\"condition\":\"EQ\",\"dbType\":\"varchar\",\"showName\":\"类型：groupUser,groupRole,userRole,groupUserRole\",\"value\":{\"text\":\"groupRole\"},\"valueSource\":\"fixedValue\"},{\"columnName\":\"groupName\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"组织\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"},{\"columnName\":\"roleName\",\"condition\":\"LK\",\"dbType\":\"varchar\",\"showName\":\"职务\",\"value\":{\"ctrlType\":\"inputText\"},\"valueSource\":\"param\"}]', '[{\"columnName\":\"groupName\",\"returnName\":\"groupName\"},{\"columnName\":\"roleName\",\"returnName\":\"roleName\"},{\"columnName\":\"id_\",\"returnName\":\"id\"},{\"columnName\":\"group_id_\",\"returnName\":\"groupId\"},{\"columnName\":\"role_id_\",\"returnName\":\"roleId\"},{\"columnName\":\"status_\",\"returnName\":\"status_\"},{\"columnName\":\"type_\",\"returnName\":\"type_\"},{\"columnName\":\"id_\",\"returnName\":\"key\"},{\"columnName\":\"postName\",\"returnName\":\"name\"}]', '[]', NULL);

ALTER TABLE `sys_subsystem`
DROP COLUMN `logo_`,
DROP COLUMN `home_url_`,
DROP COLUMN `base_url_`,
DROP COLUMN `tenant_`,
DROP COLUMN `MEMO_`,
DROP COLUMN `creator_Id_`,
DROP COLUMN `creator_`,
CHANGE COLUMN `ID_` `id_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键' FIRST ,
MODIFY COLUMN `name_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '系统名称' AFTER `id_`,
MODIFY COLUMN `alias_`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '系统别名' AFTER `name_`,
ADD COLUMN `desc_`  varchar(500) NULL AFTER `is_default_`,
ADD COLUMN `config_`  varchar(2000) NULL AFTER `desc_`,
ADD COLUMN `create_by_`  varchar(64) NULL,
ADD COLUMN `update_time_`  datetime NULL,
ADD COLUMN `update_by_`  varchar(255) NULL;

CREATE INDEX `resouceId` ON `sys_res_role`(`RES_ID_`) USING BTREE ;
CREATE INDEX `roleId` ON `sys_res_role`(`ROLE_ID_`) USING BTREE ;
ALTER TABLE `bpm_task_identitylink` MODIFY COLUMN `identity_` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ID' AFTER `identity_name_`;

DROP TABLE `bpm_reminder_history`;
DROP TABLE `bpm_task_reminder`;
DROP TABLE `org_group_rel`;
DROP TABLE `org_group_reldef`;
DROP TABLE `org_group_user`;
DROP TABLE `org_user_role`;
DROP TABLE `sys_rel_resources`;
DROP TABLE `sys_schedule_job`;
DROP TABLE `sys_schedule_job_log`;



SET FOREIGN_KEY_CHECKS=1;
