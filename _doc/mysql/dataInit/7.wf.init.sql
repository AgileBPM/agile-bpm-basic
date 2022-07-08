-- activiti 初始化数据
INSERT INTO `ACT_GE_PROPERTY` (`NAME_`, `VALUE_`, `REV_`) VALUES ('next.dbid', '0', '0');
INSERT INTO `ACT_GE_PROPERTY` (`NAME_`, `VALUE_`, `REV_`) VALUES ('schema.history', 'create(5.22.0.0) upgrade(5.21.0.0->5.22.0.0)', '2');
INSERT INTO `ACT_GE_PROPERTY` (`NAME_`, `VALUE_`, `REV_`) VALUES ('schema.version', '5.22.0.0', '2');


-- 流程管理
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='10000000710005');
-- 个人办公
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='1');


-- 使用 angular 表单
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='30'); 
UPDATE `sys_resource` SET `ENABLE_`='0' WHERE (`ID_`='33');
