-- 业务对象、以及表单模块的表创建语句

-- ----------------------------
-- Table structure for bus_column
-- ----------------------------
CREATE TABLE bus_column (
  id_ varchar2(64) NOT NULL ,
  table_id_ varchar2(64) DEFAULT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  name_ varchar2(64) DEFAULT NULL ,
  type_ varchar2(64) DEFAULT NULL ,
  length_ number(10) DEFAULT NULL,
  decimal_ number(10) DEFAULT NULL,
  required_ number(3) DEFAULT NULL,
  primary_ number(3) DEFAULT NULL,
  default_value_ varchar2(128) DEFAULT NULL,
  comment_ varchar2(256) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE bus_column IS '业务字段表';


-- ----------------------------
-- Table structure for bus_column_ctrl
-- ----------------------------
CREATE TABLE bus_column_ctrl (
  id_ varchar2(64) NOT NULL ,
  column_id_ varchar2(64) DEFAULT NULL ,
  type_ varchar2(64) DEFAULT NULL ,
  config_ varchar2(256) DEFAULT NULL ,
  valid_rule_ varchar2(256) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT column_id_unique UNIQUE  (column_id_)
)  ;

COMMENT ON TABLE bus_column_ctrl IS '字段控件表';


-- ----------------------------
-- Table structure for bus_object
-- ----------------------------
CREATE TABLE bus_object (
  id_ varchar2(64) NOT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  name_ varchar2(128) DEFAULT NULL ,
  desc_ varchar2(256) DEFAULT NULL ,
  relation_json_ clob ,
  group_id_ varchar2(64) DEFAULT NULL ,
  group_name_ varchar2(128) DEFAULT NULL ,
  persistence_type_ varchar2(64) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT key_unique_idx UNIQUE  (key_)
)  ;

COMMENT ON TABLE bus_object IS '业务对象';


-- ----------------------------
-- Table structure for bus_permission
-- ----------------------------
CREATE TABLE bus_permission (
  id_ varchar2(64) NOT NULL,
  bo_key_ varchar2(128) DEFAULT NULL ,
  obj_type_ varchar2(64) NOT NULL ,
  obj_val_ varchar2(128) DEFAULT NULL ,
  bus_obj_map_json_ clob ,
  rights_json_ clob ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT obj_type_obj_val_unique_idx_ UNIQUE  (obj_type_,obj_val_)
)  ;

COMMENT ON TABLE bus_permission IS 'bo权限';


-- ----------------------------
-- Table structure for bus_table
-- ----------------------------
CREATE TABLE bus_table (
  id_ varchar2(64) NOT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  name_ varchar2(64) DEFAULT NULL ,
  comment_ varchar2(256) DEFAULT NULL ,
  ds_key_ varchar2(64) DEFAULT NULL ,
  ds_name_ varchar2(128) DEFAULT NULL ,
  group_id_ varchar2(64) DEFAULT NULL ,
  group_name_ varchar2(128) DEFAULT NULL ,
  external_ number(5) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT bus_table_key_unique_idx UNIQUE  (key_)
)  ;

COMMENT ON TABLE bus_table IS '业务表';

-- ----------------------------
-- Table structure for form_cust_dialog
-- ----------------------------
CREATE TABLE form_cust_dialog (
  id_ varchar2(64) NOT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  name_ varchar2(128) NOT NULL ,
  desc_ varchar2(256) DEFAULT NULL ,
  style_ varchar2(32) DEFAULT NULL ,
  ds_key_ varchar2(64) DEFAULT NULL ,
  ds_name_ varchar2(128) DEFAULT NULL ,
  obj_type_ varchar2(32) DEFAULT NULL ,
  obj_name_ varchar2(64) NOT NULL ,
  page_ number(3) DEFAULT NULL ,
  page_size_ number(10) DEFAULT NULL ,
  width_ number(10) DEFAULT NULL ,
  height_ number(10) DEFAULT NULL ,
  system_ number(3) DEFAULT NULL ,
  multiple_ number(3) DEFAULT NULL ,
  tree_config_json_ varchar2(512) DEFAULT NULL ,
  display_fields_json_ clob ,
  condition_fields_json_ clob ,
  return_fields_json_ clob ,
  sort_fields_json_ clob ,
  data_source_ varchar2(64) DEFAULT NULL,
  PRIMARY KEY (id_),
  CONSTRAINT idx_unqiue UNIQUE  (key_)
) ;

COMMENT ON TABLE form_cust_dialog IS '自定义对话框';
 
-- ----------------------------
-- Table structure for form_def
-- ----------------------------
CREATE TABLE form_def (
  id_ varchar2(64) NOT NULL ,
  type_ varchar2(64) NOT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  name_ varchar2(128) DEFAULT NULL ,
  desc_ varchar2(256) DEFAULT NULL ,
  group_id_ varchar2(64) DEFAULT NULL ,
  group_name_ varchar2(128) DEFAULT NULL ,
  bo_key_ varchar2(64) DEFAULT NULL ,
  bo_name_ varchar2(128) DEFAULT NULL ,
  html_ clob ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  creator_ varchar2(128) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  updator_ varchar2(128) DEFAULT NULL ,
  version_ number(10) DEFAULT NULL ,
  delete_ number(3) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT form_def_key_unique_idx UNIQUE  (key_)
) ;

COMMENT ON TABLE form_def IS '表单';
 
-- ----------------------------
-- Table structure for form_template
-- ----------------------------
CREATE TABLE form_template (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(128) DEFAULT NULL ,
  form_type_ varchar2(64) DEFAULT NULL ,
  type_ varchar2(32) DEFAULT NULL ,
  html_ clob ,
  desc_ varchar2(400) DEFAULT NULL ,
  editable_ number(3) DEFAULT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
) ;

COMMENT ON TABLE form_template IS '表单模版';
