
CREATE TABLE `biz_demosub` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `mz` varchar(50) DEFAULT NULL COMMENT '名字',
  `ms` varchar(50) DEFAULT NULL COMMENT '描述',
  `zd1` varchar(50) DEFAULT NULL COMMENT '字段1',
  `zd2` varchar(50) DEFAULT NULL COMMENT '字段2',
  `fk` varchar(50) DEFAULT NULL COMMENT '外键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Demo子表';

CREATE TABLE `biz_demo` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `mz` varchar(50) DEFAULT NULL COMMENT '名字',
  `ah` varchar(50) DEFAULT NULL COMMENT '爱好',
  `xb` varchar(50) DEFAULT NULL COMMENT '性别',
  `bm` varchar(50) DEFAULT NULL COMMENT '部门',
  `bm_id` varchar(50) DEFAULT NULL COMMENT '部门ID',
  `zjlx` varchar(50) DEFAULT NULL COMMENT '证件类型',
  `nl` decimal(10,0) DEFAULT NULL COMMENT '年龄',
  `zd1` varchar(50) DEFAULT NULL COMMENT '字段1',
  `zd2` varchar(50) DEFAULT NULL COMMENT '字段2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='案例';

-- --------------------------------------------------
-- 导入表单需要导入 （TODO 业务对象导入导出功能，流程可以使用预览导入）
-- 1.业务对象 ：select * from bus_object where key_ = 'SchoolStudent';
-- 2.表定义	  ：select * from bus_table where name_ in ('biz_school','biz_student','biz_class');
-- 3.字段定义 ：select * from bus_column where table_id_ in (select id_ from bus_table where name_ in ('biz_school','biz_student','biz_class'));
-- 4.字段控件：select * from bus_column_ctrl where column_id_ in (select id_ from bus_column where table_id_ in (select id_ from bus_table where name_ in ('biz_school','biz_student','biz_class')));


-- 5.表单	  ：select * from form_def where key_ in('SchoolStudent_m','SchoolStudent');
-- 6.表单权限 ：select * from bus_permission where bo_key_ in ('SchoolStudent') and type ='flow/form'
-- -----------------------------------------------------



-- 表单
INSERT INTO `form_def`(`id_`, `type_`, `key_`, `name_`, `desc_`, `group_id_`, `group_name_`, `bo_key_`, `bo_name_`, `html_`, `create_time_`, `create_by_`, `creator_`, `update_time_`, `update_by_`, `updator_`, `version_`, `delete_`) VALUES ('20000000530044', 'pc', 'demo', 'Demo', NULL, '20000002960003', '默认分类', 'Demo', 'Demo', '<script type=\"text/javascript\">// 建议您将HTML代码 copy到 eclipse中进行编辑。\n  // 更建议您设置 app-dev.properties 的 formDefBackupPath 属性，这样系统会把代码自动备份至您本地磁盘。最好是git的资源库。这样代码可以拥有版本历史记录\n	var testCtrl = [ \'$scope\',\'baseService\',function(scope, baseService) {\n      // 初始化一个字段 \n      if(!scope.data.Demo.zd1){\n        scope.data.Demo.zd1=\"JS初始化\";\n      }\n      \n      //初始化一条子表数据（这不是唯一的方式）\n      debugger;\n	  if(!scope.data.Demo.DemoSubList ||scope.data.Demo.DemoSubList.length==0){\n          scope.data.Demo.DemoSubList = [];\n        // scope.initData.Demo.DemoSub 子表数据的副本。当然直接push一个{} 也可以。\n        var subTempData = CloneUtil.deep(scope.initData.Demo.DemoSub);\n        subTempData.ms = \"请开启控制台，或者查看表单源码，来查看我是如何初始化的\";\n        scope.data.Demo.DemoSubList.push(subTempData);\n      }\n      //植入一个点击事件\n      scope.testfn = function(){\n          $.Dialog.alert(scope.data.Demo.bmId);\n      }      \n      \n				}\n			]</script><div ng-controller=\"testCtrl\"><table class=\"form-table\"><thead><tr class=\"firstRow\"><th colspan=\"2\" style=\"word-break: break-all;\"><h1>任务节点【{{task.name}}】该表单为开发案例表单,不同节点配置了不同的权限</h1>表单为原生HTML表单，随意扩展JS逻辑，当然也可以自定义特殊指令，注入到表单服务中(formServiceModule)。实现特殊的功能。移动端则是VUE，我们通过动态组件来实现了表单js逻辑的扩展,请参考文档表单开发指导</th></tr></thead><tbody><tr><th><span style=\"font-size: 12px;\">名字</span></th><td style=\"word-break: break-all;\"><p><input type=\"text\" class=\" form-control\" ng-model=\"data.Demo.mz\" ab-basic-permission=\"permission.Demo.Demo.mz\" desc=\"名字\" ab-validate=\"{}\"/><a class=\"btn btn-primary fa-search\" href=\"javascript:void(0)\" ab-cust-dialog=\"userSelector\" ng-model=\"data.Demo\" param=\"{}\" dialog-setting=\"{&quot;multiple&quot;:true}\" value-name=\"mz\" ab-edit-permission=\"permission.Demo.Demo.mz\" style=\"font-size: 12px; text-decoration: underline;\"><span style=\"font-size: 12px;\">用户查询</span></a></p><p class=\"text-danger\"><span style=\"font-size: 12px;\">您可以尝试下自定义对话框，这是一个很便利的数据选择功能，支持主表字段赋值，子表多行整体赋值，子表表内字段赋值（</span>并且支持多数据源，可以从任意一个数据源获取一些数据）</p></td></tr><tr><th>爱好</th><td><div ng-model=\"data.Demo.ah\" ab-checked-permission=\"permission.Demo.Demo.ah\" ab-validate=\"{}\" desc=\"爱好\"><label class=\" checkbox-inline\"><input type=\"checkbox\" ab-checkbox=\"\" ng-model=\"data.Demo.ah\" value=\"1\"/>游泳</label><label class=\" checkbox-inline\"><input type=\"checkbox\" ab-checkbox=\"\" ng-model=\"data.Demo.ah\" value=\"2\"/>羽毛器</label><label class=\" checkbox-inline\"><input type=\"checkbox\" ab-checkbox=\"\" ng-model=\"data.Demo.ah\" value=\"3\"/>篮球</label></div></td></tr><tr><th>性别</th><td><div ng-model=\"data.Demo.xb\" ab-checked-permission=\"permission.Demo.Demo.xb\" ab-validate=\"{}\" desc=\"性别\"><label class=\" radio-inline\"><input type=\"radio\" ng-model=\"data.Demo.xb\" value=\"1\"/>男</label><label class=\" radio-inline\"><input type=\"radio\" ng-model=\"data.Demo.xb\" value=\"2\"/>女</label></div></td></tr><tr><th>部门</th><td style=\"word-break: break-all;\"><p><input type=\"text\" class=\" form-control\" ng-model=\"data.Demo.bm\" ab-basic-permission=\"permission.Demo.Demo.bm\" desc=\"部门\" ab-validate=\"{}\"/><a class=\"btn btn-primary fa-search\" href=\"javascript:void(0)\" ng-click=\"testfn()\">点击我查看部门ID</a></p><p class=\"text-danger\">在启动流程时，表单展示前，后台通过Groovy脚本赋值。部门id为隐藏字段 {{data.Demo.bmId}}</p></td></tr><tr><th>证件类型</th><td><span type=\"text\" class=\" input-div\" ng-model=\"data.Demo.zjlx\" ab-validate=\"{}\" desc=\"证件类型\" ab-basic-permission=\"permission.Demo.Demo.zjlx\" ab-combox=\"data.Demo.zjlx\" dict-key=\"idtype\"></span><p class=\"text-danger\">我是一个数据字典，您可以仿照标签指令来写，也可以在生成表单前，配置表字段控件为数据字典，并选择一个字典分类</p></td></tr><tr><th>年龄</th><td style=\"word-break: break-all;\"><input type=\"text\" class=\" form-control\" ng-model=\"data.Demo.nl\" ab-basic-permission=\"permission.Demo.Demo.nl\" desc=\"年龄\" ab-validate=\"{&quot;digits&quot;:true}\"/>&nbsp;<p class=\"text-danger\">我是一个数字，表单校验可以直接修改 ab-validate 指令配置，或者生成表单前，对实体的字段进行校验配置。会通过配置的校验类型来生成带有相应校验的字段控件</p></td></tr><tr><th>字段1</th><td style=\"word-break: break-all;\"><input type=\"text\" class=\" form-control\" disabled=\"\" ng-model=\"data.Demo.zd1\" ab-basic-permission=\"permission.Demo.Demo.zd1\" desc=\"字段1\" ab-validate=\"{}\"/><span class=\"text-danger\"> 我是通过js 初始化出来的，请查看表单源码 &lt;script&gt;中的内容 </span></td></tr><tr><th>字段2</th><td style=\"word-break: break-all;\"><input type=\"text\" class=\" form-control\" disabled=\"\" ng-model=\"data.Demo.zd2\" ab-basic-permission=\"permission.Demo.Demo.zd2\" desc=\"字段2\" ab-validate=\"{}\"/><span class=\"text-danger\">我是一个流程保存后置脚本 初始化的流水号。&nbsp;</span></td></tr></tbody></table><p><br/></p><div id=\"Demo-DemoSub\" table-key=\"DemoSub\" ab-show-permission=\"tablePermission.Demo.DemoSub\"><div class=\"ibox-title\"><span class=\"title\">Demo子表</span><a href=\"javascript:void(0)\" class=\"btn btn-primary btn-sm fa fa-plus\" ng-model=\"data.Demo.DemoSubList\" ab-sub-add=\"initData.Demo.DemoSub\" ab-edit-permission=\"tablePermission.Demo.DemoSub\">添加</a>&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<a class=\"btn btn-primary fa-search\" href=\"javascript:void(0)\" ab-cust-dialog=\"roleSelector\" ng-model=\"data.Demo.DemoSubList\" param=\"{}\" dialog-setting=\"{&quot;multiple&quot;:true}\" value-name=\"mz\">从数据库选择一个子表对象的数据填充进来</a></div><div class=\"ibox-content\" ng-repeat=\"DemoSub in data.Demo.DemoSubList track by $index\"><a class=\"btn btn-danger btn-xs fa fa-delete pull-right\" ng-click=\"ArrayTool.del($index,data.Demo.DemoSubList)\" ab-edit-permission=\"tablePermission.Demo.DemoSub\"> 移除</a><table class=\"form-table\"><tbody><tr class=\"firstRow\"><th>名字</th><td><input type=\"text\" class=\" form-control\" ng-model=\"DemoSub.mz\" ab-basic-permission=\"permission.Demo.DemoSub.mz\" desc=\"名字\" ab-validate=\"{}\"/></td></tr><tr><th>描述</th><td><textarea type=\"text\" class=\" form-control\" ng-model=\"DemoSub.ms\" ab-basic-permission=\"permission.Demo.DemoSub.ms\" desc=\"描述\" ab-validate=\"{}\"></textarea></td></tr><tr><th>字段1</th><td style=\"word-break: break-all;\"><input type=\"text\" class=\" form-control\" ng-model=\"DemoSub.zd1\" ab-basic-permission=\"permission.Demo.DemoSub.zd1\" desc=\"字段1\" ab-validate=\"{}\"/><a class=\"btn btn-primary fa-search\" href=\"javascript:void(0)\" ab-cust-dialog=\"orgSelector\" ng-model=\"DemoSub\" param=\"{}\" dialog-setting=\"{&quot;multiple&quot;:true}\" value-name=\"zd1\" ab-edit-permission=\"permission.Demo.Demo.zd1\">组织选择框</a>多选</td></tr><tr><th>字段2</th><td style=\"word-break: break-all;\"><input type=\"text\" class=\" form-control\" ng-model=\"DemoSub.zd2\" ab-basic-permission=\"permission.Demo.DemoSub.zd2\" desc=\"字段2\" ab-validate=\"{}\"/><a class=\"btn btn-primary fa-search\" href=\"javascript:void(0)\" ab-cust-dialog=\"orgSelector\" ng-model=\"DemoSub\" param=\"{}\" dialog-setting=\"{&quot;multiple&quot;:false}\" value-name=\"zd2\" ab-edit-permission=\"permission.Demo.Demo.zd2\">组织选择框</a>单选</td></tr></tbody></table></div></div></div>', '2018-08-16 15:09:47', NULL, NULL, '2018-08-16 15:09:47', NULL, NULL, 12, 0);
-- bo_
INSERT INTO `bus_object`(`id_`, `key_`, `name_`, `desc_`, `relation_json_`, `group_id_`, `group_name_`, `persistence_type_`) VALUES ('20000000530043', 'Demo', 'Demo', '普通主子表案例', '{\"children\":[{\"children\":[],\"fks\":[{\"from\":\"fk\",\"type\":\"parentField\",\"value\":\"id\"}],\"tableComment\":\"Demo子表\",\"tableKey\":\"DemoSub\",\"type\":\"oneToMany\"}],\"tableComment\":\"案例\",\"tableKey\":\"Demo\",\"type\":\"main\"}', '20000002900002', '业务对象默认分类', 'db');
-- table
INSERT INTO `bus_table`(`id_`, `key_`, `name_`, `comment_`, `ds_key_`, `ds_name_`, `group_id_`, `group_name_`, `external_`) VALUES ('20000000530015', 'Demo', 'biz_demo', '案例', 'dataSourceDefault', '本地数据源', '20000002810003', '默认分类', 0);
INSERT INTO `bus_table`(`id_`, `key_`, `name_`, `comment_`, `ds_key_`, `ds_name_`, `group_id_`, `group_name_`, `external_`) VALUES ('20000000530031', 'DemoSub', 'biz_demosub', 'Demo子表', 'dataSourceDefault', '本地数据源', '20000002810003', '默认分类', 0);
-- column
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530016', '20000000530015', 'id', 'id', 'varchar', 50, 0, 1, 1, NULL, '主键');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530017', '20000000530015', 'mz', 'mz', 'varchar', 50, 0, 0, 0, NULL, '名字');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530019', '20000000530015', 'ah', 'ah', 'varchar', 50, 0, 0, 0, NULL, '爱好');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530021', '20000000530015', 'xb', 'xb', 'varchar', 50, 0, 0, 0, NULL, '性别');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530023', '20000000530015', 'bm', 'bm', 'varchar', 50, 0, 0, 0, NULL, '部门');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530025', '20000000530015', 'bmId', 'bm_id', 'varchar', 50, 0, 0, 0, NULL, '部门ID');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530027', '20000000530015', 'zjlx', 'zjlx', 'varchar', 50, 0, 0, 0, NULL, '证件类型');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530029', '20000000530015', 'nl', 'nl', 'number', 10, 0, 0, 0, NULL, '年龄');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530032', '20000000530031', 'id', 'id', 'varchar', 50, 0, 1, 1, NULL, '主键');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530033', '20000000530031', 'mz', 'mz', 'varchar', 50, 0, 0, 0, NULL, '名字');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530035', '20000000530031', 'ms', 'ms', 'varchar', 50, 0, 0, 0, NULL, '描述');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530037', '20000000530031', 'zd1', 'zd1', 'varchar', 50, 0, 0, 0, NULL, '字段1');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530039', '20000000530031', 'zd2', 'zd2', 'varchar', 50, 0, 0, 0, NULL, '字段2');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530041', '20000000530031', 'fk', 'fk', 'varchar', 50, 0, 0, 0, NULL, '外键');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530046', '20000000530015', 'zd1', 'zd1', 'varchar', 50, 0, 0, 0, NULL, '字段1');
INSERT INTO `bus_column`(`id_`, `table_id_`, `key_`, `name_`, `type_`, `length_`, `decimal_`, `required_`, `primary_`, `default_value_`, `comment_`) VALUES ('20000000530048', '20000000530015', 'zd2', 'zd2', 'varchar', 50, 0, 0, 0, NULL, '字段2');
-- column_ctrl
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530018', '20000000530017', 'onetext', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530020', '20000000530019', 'checkbox', '{\"options\":[{\"txt\":\"游泳\",\"key\":\"1\"},{\"txt\":\"羽毛器\",\"key\":\"2\"},{\"txt\":\"篮球\",\"key\":\"3\"}]}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530022', '20000000530021', 'radio', '{\"options\":[{\"txt\":\"男\",\"key\":\"1\"},{\"txt\":\"女\",\"key\":\"2\"}]}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530024', '20000000530023', 'onetext', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530026', '20000000530025', 'hidden', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530028', '20000000530027', 'dic', '{\"keyName\":\"证件类型\",\"key\":\"idtype\"}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530030', '20000000530029', 'onetext', '{}', '[{\"name\":\"digits\",\"title\":\"整数\"}]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530034', '20000000530033', 'onetext', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530036', '20000000530035', 'multitext', '{\"eheight\":\"500\",\"ewidth\":\"150\",\"isEditor\":\"0\"}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530038', '20000000530037', 'onetext', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530040', '20000000530039', 'onetext', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530042', '20000000530041', 'hidden', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530047', '20000000530046', 'onetext', '{}', '[]');
INSERT INTO `bus_column_ctrl`(`id_`, `column_id_`, `type_`, `config_`, `valid_rule_`) VALUES ('20000000530049', '20000000530048', 'onetext', '{}', '[]');

-- DEMO 菜单启用
<<<<<<< HEAD
UPDATE `sys_resource` SET `ENABLE_MENU_`='1' WHERE (`ID_`='402552195187998721');
=======
UPDATE `sys_resource` SET `enable_`='1' WHERE (`ID_`='402552195187998721');
>>>>>>> branch 'master' of https://gitee.com/agile-bpm/agile-bpm-basic.git
