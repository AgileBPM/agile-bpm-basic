-- ---------------ORG 模块 SQL语句 ---------------
-- ----------------------------
-- Table structure for org_user
-- ----------------------------
DROP TABLE IF EXISTS `org_user`;
CREATE TABLE `org_user` (
  `id_` varchar(64) NOT NULL,
  `fullname_` varchar(255) NOT NULL COMMENT '姓名',
  `account_` varchar(255) NOT NULL COMMENT '账号',
  `password_` varchar(64) NOT NULL COMMENT '密码',
  `email_` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `mobile_` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `weixin_` varchar(64) DEFAULT NULL COMMENT '微信号',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  `address_` varchar(512) DEFAULT NULL COMMENT '地址',
  `photo_` varchar(255) DEFAULT NULL COMMENT '头像',
  `sex_` varchar(10) DEFAULT NULL COMMENT '性别：男，女，未知',
  `from_` varchar(64) DEFAULT NULL COMMENT '来源',
  `status_` int(11) NOT NULL DEFAULT '1' COMMENT '0:禁用，1正常',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for org_role
-- ----------------------------
DROP TABLE IF EXISTS `org_role`;
CREATE TABLE `org_role` (
  `id_` varchar(64) NOT NULL,
  `name_` varchar(64) NOT NULL COMMENT '角色名称',
  `alias_` varchar(64) NOT NULL COMMENT '英文别名',
  `enabled_` int(11) NOT NULL DEFAULT '1' COMMENT '0：禁用，1：启用',
  `description` varchar(200) NOT NULL COMMENT '描述',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色管理';
-- ----------------------------
-- Table structure for org_group
-- ----------------------------
DROP TABLE IF EXISTS `org_group`;
CREATE TABLE `org_group` (
  `id_` varchar(64) NOT NULL COMMENT '主键',
  `name_` varchar(64) NOT NULL,
  `parent_id_` varchar(64) DEFAULT NULL,
  `sn_` int(11) DEFAULT '100',
  `code_` varchar(64) NOT NULL,
  `grade_` varchar(64) DEFAULT NULL COMMENT '级别',
  `desc_` varchar(500) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构';


-- ----------------------------
-- Table structure for org_group_reldef
-- ----------------------------
DROP TABLE IF EXISTS `org_group_reldef`;
CREATE TABLE `org_group_reldef` (
  `id_` varchar(64) NOT NULL,
  `name_` varchar(64) NOT NULL COMMENT '名称',
  `code_` varchar(64) NOT NULL COMMENT '编码',
  `post_level_` varchar(64) DEFAULT NULL COMMENT '职务级别',
  `description_` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织关系定义';


-- ----------------------------
-- Table structure for org_group_rel
-- ----------------------------
DROP TABLE IF EXISTS `org_group_rel`;
CREATE TABLE `org_group_rel` (
  `id_` varchar(64) NOT NULL,
  `group_id_` varchar(64) DEFAULT NULL,
  `rel_def_id_` varchar(64) DEFAULT NULL,
  `rel_name_` varchar(64) DEFAULT NULL COMMENT '岗位名称',
  `rel_code_` varchar(64) DEFAULT NULL COMMENT '岗位编码',
  `rel_def_name_` varchar(64) DEFAULT NULL COMMENT '职务名称',
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  KEY `FK_reference_19` (`rel_def_id_`) USING BTREE,
  KEY `FK_reference_20` (`group_id_`) USING BTREE,
  CONSTRAINT `org_group_rel_ibfk_1` FOREIGN KEY (`group_id_`) REFERENCES `org_group` (`id_`) ON DELETE CASCADE,
  CONSTRAINT `org_group_rel_ibfk_2` FOREIGN KEY (`rel_def_id_`) REFERENCES `org_group_reldef` (`id_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织关联关系';


-- ----------------------------
-- Table structure for org_group_user
-- ----------------------------
DROP TABLE IF EXISTS `org_group_user`;
CREATE TABLE `org_group_user` (
  `id_` varchar(64) NOT NULL,
  `group_id_` varchar(64) NOT NULL,
  `user_id_` varchar(64) NOT NULL,
  `is_master_` int(11) NOT NULL DEFAULT '0' COMMENT '0:非主部门，1：主部门',
  `rel_id_` varchar(64) DEFAULT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  KEY `FK_reference_21` (`user_id_`) USING BTREE,
  KEY `FK_reference_22` (`group_id_`) USING BTREE,
  KEY `FK_reference_23` (`rel_id_`) USING BTREE,
  CONSTRAINT `org_group_user_ibfk_1` FOREIGN KEY (`rel_id_`) REFERENCES `org_group_rel` (`id_`) ON DELETE CASCADE,
  CONSTRAINT `org_group_user_ibfk_2` FOREIGN KEY (`user_id_`) REFERENCES `org_user` (`id_`) ON DELETE CASCADE,
  CONSTRAINT `org_group_user_ibfk_3` FOREIGN KEY (`group_id_`) REFERENCES `org_group` (`id_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组织关系';


-- ----------------------------
-- Table structure for org_user_role
-- ----------------------------
DROP TABLE IF EXISTS `org_user_role`;
CREATE TABLE `org_user_role` (
  `id_` varchar(64) NOT NULL,
  `role_id_` varchar(64) NOT NULL,
  `user_id_` varchar(64) NOT NULL,
  `create_time_` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by_` varchar(64) DEFAULT NULL COMMENT '创建人',
  `update_time_` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by_` varchar(64) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id_`),
  KEY `FK_reference_user_role` (`role_id_`) USING BTREE,
  KEY `FK_reference_userrole_user` (`user_id_`) USING BTREE,
  CONSTRAINT `org_user_role_ibfk_1` FOREIGN KEY (`user_id_`) REFERENCES `org_user` (`id_`) ON DELETE CASCADE,
  CONSTRAINT `org_user_role_ibfk_2` FOREIGN KEY (`role_id_`) REFERENCES `org_role` (`id_`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色管理';
