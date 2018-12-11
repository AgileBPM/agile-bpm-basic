-- activiti 初始化数据
INSERT INTO `act_ge_property` (`NAME_`, `VALUE_`, `REV_`) VALUES ('next.dbid', '0', '0');
INSERT INTO `act_ge_property` (`NAME_`, `VALUE_`, `REV_`) VALUES ('schema.history', 'create(5.22.0.0) upgrade(5.21.0.0->5.22.0.0)', '2');
INSERT INTO `act_ge_property` (`NAME_`, `VALUE_`, `REV_`) VALUES ('schema.version', '5.22.0.0', '2');


-- 流程管理
UPDATE `sys_resource` SET `ENABLE_MENU_`='1' WHERE (`ID_`='10000000710005');
-- 个人办公
UPDATE `sys_resource` SET `ENABLE_MENU_`='1' WHERE (`ID_`='1');




