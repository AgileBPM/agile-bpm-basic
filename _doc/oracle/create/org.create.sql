-- ---------------ORG 模块 SQL语句 ---------------
-- ----------------------------
-- Table structure for org_user
-- ----------------------------
CREATE TABLE org_user (
  id_ varchar2(64) NOT NULL,
  fullname_ varchar2(255) NOT NULL ,
  account_ varchar2(255) NOT NULL ,
  password_ varchar2(64) NOT NULL ,
  email_ varchar2(64) DEFAULT NULL ,
  mobile_ varchar2(32) DEFAULT NULL ,
  weixin_ varchar2(64) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  address_ varchar2(512) DEFAULT NULL ,
  photo_ varchar2(255) DEFAULT NULL ,
  sex_ varchar2(10) DEFAULT NULL ,
  from_ varchar2(64) DEFAULT NULL ,
  status_ number(10) DEFAULT '1' NOT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE org_user IS '用户表';

-- ----------------------------
-- Table structure for org_role
-- ----------------------------
CREATE TABLE org_role (
  id_ varchar2(64) NOT NULL,
  name_ varchar2(64) NOT NULL ,
  alias_ varchar2(64) NOT NULL ,
  enabled_ number(10) DEFAULT '1' NOT NULL ,
  description varchar2(200) NOT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE org_role IS '角色管理';
-- ----------------------------
-- Table structure for org_group
-- ----------------------------
CREATE TABLE org_group (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(64) NOT NULL,
  parent_id_ varchar2(64) DEFAULT NULL,
  sn_ number(10) DEFAULT '100',
  code_ varchar2(64) NOT NULL,
  grade_ varchar2(64) DEFAULT NULL ,
  desc_ varchar2(500) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE org_group IS '组织架构';


-- ----------------------------
-- Table structure for org_group_reldef
-- ----------------------------
CREATE TABLE org_group_reldef (
  id_ varchar2(64) NOT NULL,
  name_ varchar2(64) NOT NULL ,
  code_ varchar2(64) NOT NULL ,
  post_level_ varchar2(64) DEFAULT NULL ,
  description_ varchar2(500) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE org_group_reldef IS '组织关系定义';


-- ----------------------------
-- Table structure for org_group_rel
-- ----------------------------
CREATE TABLE org_group_rel (
  id_ varchar2(64) NOT NULL,
  group_id_ varchar2(64) DEFAULT NULL,
  rel_def_id_ varchar2(64) DEFAULT NULL,
  rel_name_ varchar2(64) DEFAULT NULL ,
  rel_code_ varchar2(64) DEFAULT NULL ,
  rel_def_name_ varchar2(64) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
 ,
  CONSTRAINT org_group_rel_ibfk_1 FOREIGN KEY (group_id_) REFERENCES org_group (id_) ON DELETE CASCADE,
  CONSTRAINT org_group_rel_ibfk_2 FOREIGN KEY (rel_def_id_) REFERENCES org_group_reldef (id_) ON DELETE CASCADE
)  ;

COMMENT ON TABLE org_group_rel IS '组织关联关系';

CREATE INDEX FK_reference_19 ON org_group_rel (rel_def_id_);
CREATE INDEX FK_reference_20 ON org_group_rel (group_id_);


-- ----------------------------
-- Table structure for org_group_user
-- ----------------------------
CREATE TABLE org_group_user (
  id_ varchar2(64) NOT NULL,
  group_id_ varchar2(64) NOT NULL,
  user_id_ varchar2(64) NOT NULL,
  is_master_ number(10) DEFAULT '0' NOT NULL ,
  rel_id_ varchar2(64) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
 ,
  CONSTRAINT org_group_user_ibfk_1 FOREIGN KEY (rel_id_) REFERENCES org_group_rel (id_) ON DELETE CASCADE,
  CONSTRAINT org_group_user_ibfk_2 FOREIGN KEY (user_id_) REFERENCES org_user (id_) ON DELETE CASCADE,
  CONSTRAINT org_group_user_ibfk_3 FOREIGN KEY (group_id_) REFERENCES org_group (id_) ON DELETE CASCADE
)  ;

COMMENT ON TABLE org_group_user IS '用户组织关系';

CREATE INDEX FK_reference_21 ON org_group_user (user_id_);
CREATE INDEX FK_reference_22 ON org_group_user (group_id_);
CREATE INDEX FK_reference_23 ON org_group_user (rel_id_);


-- ----------------------------
-- Table structure for org_user_role
-- ----------------------------
CREATE TABLE org_user_role (
  id_ varchar2(64) NOT NULL,
  role_id_ varchar2(64) NOT NULL,
  user_id_ varchar2(64) NOT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
 ,
  CONSTRAINT org_user_role_ibfk_1 FOREIGN KEY (user_id_) REFERENCES org_user (id_) ON DELETE CASCADE,
  CONSTRAINT org_user_role_ibfk_2 FOREIGN KEY (role_id_) REFERENCES org_role (id_) ON DELETE CASCADE
)  ;

COMMENT ON TABLE org_user_role IS '用户角色管理';

CREATE INDEX FK_reference_user_role ON org_user_role (role_id_);
CREATE INDEX FK_reference_userrole_user ON org_user_role (user_id_);
