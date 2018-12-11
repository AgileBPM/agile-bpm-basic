-- -----含常用脚本，流水号，子系统，菜单资源，通用授权，面板，系统属性，数据字典等功能表
 
/* SET FOREIGN_KEY_CHECKS=0; */

-- ------------------sys 模块功能 持久化表-----------------

-- ----------------------------
-- Table structure for sys_authorization
-- ----------------------------
CREATE TABLE sys_authorization (
  rights_id_ varchar2(64) NOT NULL ,
  rights_object_ varchar2(64) NOT NULL ,
  rights_target_ varchar2(64) NOT NULL ,
  rights_type_ varchar2(64) NOT NULL ,
  rights_identity_ varchar2(64) NOT NULL ,
  rights_identity_name_ varchar2(255) NOT NULL ,
  rights_permission_code_ varchar2(125) NOT NULL ,
  rights_create_time_ timestamp(0) NOT NULL ,
  rights_create_by_ varchar2(64) NOT NULL ,
  PRIMARY KEY (rights_id_)
)  ;

COMMENT ON TABLE sys_authorization IS '通用资源授权配置';

CREATE INDEX idx_permission_code_ ON sys_authorization (rights_permission_code_);

-- ----------------------------
-- Table structure for sys_data_dict
-- ----------------------------
CREATE TABLE sys_data_dict (
  id_ varchar2(64) NOT NULL ,
  parent_id_ varchar2(64) DEFAULT NULL ,
  key_ varchar2(255) NOT NULL ,
  name_ varchar2(255) NOT NULL ,
  dict_key_ varchar2(255) NOT NULL ,
  type_id_ varchar2(64) DEFAULT NULL ,
  sn_ number(10) DEFAULT NULL ,
  dict_type_ varchar2(10) NOT NULL ,
  delete_flag_ varchar2(1) DEFAULT NULL ,
  create_time_ timestamp(0) NOT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE sys_data_dict IS '数据字典';

-- ----------------------------
-- Table structure for sys_data_source
-- ----------------------------
CREATE TABLE sys_data_source (
  id_ varchar2(64) NOT NULL ,
  key_ varchar2(64) DEFAULT NULL ,
  name_ varchar2(64) DEFAULT NULL ,
  desc_ varchar2(256) DEFAULT NULL ,
  db_type_ varchar2(64) DEFAULT NULL ,
  class_path_ varchar2(100) DEFAULT NULL ,
  attributes_json_ clob ,
  PRIMARY KEY (id_),
  CONSTRAINT key_unique UNIQUE  (key_)
)  ;

COMMENT ON TABLE sys_data_source IS '数据源';

-- ----------------------------
-- Table structure for sys_data_source_def
-- ----------------------------
CREATE TABLE sys_data_source_def (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(64) DEFAULT NULL ,
  class_path_ varchar2(100) DEFAULT NULL ,
  attributes_json_ clob ,
  PRIMARY KEY (id_)
);

COMMENT ON TABLE sys_data_source_def IS '数据源模板';

CREATE INDEX class_path_unique ON sys_data_source_def (class_path_);
 
-- ----------------------------
-- Table structure for sys_log_err
-- ----------------------------
CREATE TABLE sys_log_err (
  id_ varchar2(50) NOT NULL ,
  ACCOUNT_ varchar2(20) DEFAULT NULL ,
  IP_ varchar2(20) DEFAULT NULL ,
  ip_address_ varchar2(255) DEFAULT NULL ,
  status_ varchar2(64) DEFAULT NULL ,
  URL_ varchar2(1500) DEFAULT NULL ,
  CONTENT_ clob ,
  request_param_ clob ,
  CREATE_TIME_ timestamp(0) DEFAULT NULL ,
  stack_trace_ clob ,
  PRIMARY KEY (id_)
) ;

-- ----------------------------
-- Table structure for sys_properties
-- ----------------------------
CREATE TABLE sys_properties (
  id_ varchar2(64) NOT NULL,
  name_ varchar2(64) DEFAULT NULL,
  alias_ varchar2(64) DEFAULT NULL,
  group_ varchar2(64) DEFAULT NULL,
  value_ varchar2(500) DEFAULT NULL,
  encrypt_ number(10) DEFAULT NULL,
  update_by_ varchar2(64) DEFAULT NULL,
  update_time_ timestamp(0) DEFAULT NULL,
  create_by_ varchar2(64) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL,
  description_ varchar2(500) DEFAULT NULL,
  environment_ varchar2(64) DEFAULT NULL,
  PRIMARY KEY (id_)
) ;

-- ----------------------------
-- Table structure for sys_rel_resources
-- ----------------------------
CREATE TABLE sys_rel_resources (
  ID_ varchar2(50) NOT NULL ,
  RES_ID_ varchar2(50) DEFAULT NULL ,
  NAME_ varchar2(50) DEFAULT NULL ,
  RES_URL_ varchar2(100) DEFAULT NULL ,
  PRIMARY KEY (ID_)
)   ;

COMMENT ON TABLE sys_rel_resources IS '关联资源';

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
CREATE TABLE sys_resource (
  ID_ varchar2(50) NOT NULL ,
  SYSTEM_ID_ varchar2(50) DEFAULT NULL ,
  ALIAS_ varchar2(50) DEFAULT NULL ,
  NAME_ varchar2(50) DEFAULT NULL ,
  default_url_ varchar2(50) DEFAULT NULL ,
  ENABLE_MENU_ number(10) DEFAULT NULL ,
  HAS_CHILDREN_ number(10) DEFAULT NULL ,
  OPENED_ number(10) DEFAULT NULL,
  ICON_ varchar2(50) DEFAULT NULL ,
  NEW_WINDOW_ number(10) DEFAULT NULL ,
  SN_ number(19) DEFAULT NULL ,
  PARENT_ID_ varchar2(50) DEFAULT NULL ,
  CREATE_TIME_ timestamp(0) DEFAULT NULL ,
  PRIMARY KEY (ID_)
)   ;

COMMENT ON TABLE sys_resource IS '子系统资源';

-- ----------------------------
-- Table structure for sys_res_role
-- ----------------------------
CREATE TABLE sys_res_role (
  ID_ varchar2(50) DEFAULT '' NOT NULL ,
  SYSTEM_ID_ varchar2(50) DEFAULT NULL ,
  RES_ID_ varchar2(50) DEFAULT NULL ,
  ROLE_ID_ varchar2(50) DEFAULT NULL ,
  PRIMARY KEY (ID_)
)   ;

COMMENT ON TABLE sys_res_role IS '角色资源分配';
 
 
-- ----------------------------
-- Table structure for sys_script
-- ----------------------------
CREATE TABLE sys_script (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(128) DEFAULT NULL ,
  script_ clob ,
  category_ varchar2(128) DEFAULT NULL ,
  memo_ varchar2(512) DEFAULT NULL ,
  PRIMARY KEY (id_)
)   ;

COMMENT ON TABLE sys_script IS '常用脚本';

-- ----------------------------
-- Table structure for sys_serialno
-- ----------------------------
CREATE TABLE sys_serialno (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(64) DEFAULT NULL ,
  alias_ varchar2(20) DEFAULT NULL ,
  regulation_ varchar2(128) DEFAULT NULL ,
  gen_type_ number(5) DEFAULT NULL ,
  no_length_ number(10) DEFAULT NULL ,
  cur_date_ varchar2(20) DEFAULT NULL ,
  init_value_ number(10) DEFAULT NULL ,
  cur_value_ number(10) DEFAULT NULL ,
  step_ number(5) DEFAULT NULL ,
  PRIMARY KEY (id_)
)   ;

COMMENT ON TABLE sys_serialno IS '流水号生成';

CREATE INDEX idx_uni_alias_val ON sys_serialno (alias_,cur_value_);

-- ----------------------------
-- Table structure for sys_subsystem
-- ----------------------------
CREATE TABLE sys_subsystem (
  ID_ varchar2(50) NOT NULL ,
  name_ varchar2(50)   DEFAULT NULL ,
  alias_ varchar2(50) DEFAULT NULL ,
  logo_ varchar2(50) DEFAULT NULL ,
  enabled_ number(10) DEFAULT NULL ,
  home_url_ varchar2(100) DEFAULT NULL ,
  base_url_ varchar2(50) DEFAULT NULL ,
  tenant_ varchar2(50) DEFAULT NULL ,
  MEMO_ varchar2(200) DEFAULT NULL ,
  creator_Id_ varchar2(50) DEFAULT NULL ,
  creator_ varchar2(50) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  is_default_ number(10) DEFAULT NULL ,
  PRIMARY KEY (ID_)
)   ;

COMMENT ON TABLE sys_subsystem IS '子系统定义';

-- ----------------------------
-- Table structure for sys_tree
-- ----------------------------
CREATE TABLE sys_tree (
  id_ varchar2(64)  NOT NULL ,
  key_ varchar2(64)  DEFAULT NULL ,
  name_ varchar2(256)  DEFAULT NULL ,
  desc_ varchar2(256)  DEFAULT NULL ,
  system_ number(3) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT key_unique_ UNIQUE  (key_)
)     ;

COMMENT ON TABLE sys_tree IS '系统树';

-- ----------------------------
-- Table structure for sys_tree_node
-- ----------------------------
CREATE TABLE sys_tree_node (
  id_ varchar2(64)  NOT NULL ,
  key_ varchar2(64)  DEFAULT NULL ,
  name_ varchar2(128)  DEFAULT NULL ,
  desc_ varchar2(256)  DEFAULT NULL ,
  tree_id_ varchar2(64)  DEFAULT NULL ,
  parent_id_ varchar2(64)  DEFAULT NULL ,
  path_ varchar2(512)  DEFAULT NULL ,
  sn_ number(3) DEFAULT NULL ,
  PRIMARY KEY (id_),
  CONSTRAINT tree_id_key_unique_ UNIQUE  (key_,tree_id_)
)     ;

COMMENT ON TABLE sys_tree_node IS '系统树节点';

-- ----------------------------
-- Table structure for sys_workbench_layout
-- ----------------------------
CREATE TABLE sys_workbench_layout (
  id_ varchar2(64) NOT NULL,
  panel_id_ varchar2(255) NOT NULL ,
  cust_width_ number(10) DEFAULT NULL ,
  cust_height_ number(10) DEFAULT NULL ,
  sn_ number(10) DEFAULT NULL ,
  user_id_ varchar2(64) NOT NULL ,
  create_time_ timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL ,
  PRIMARY KEY (id_)
)   ;

COMMENT ON TABLE sys_workbench_layout IS '工作台布局';

CREATE INDEX idx_panel_id_ ON sys_workbench_layout (panel_id_);

-- ----------------------------
-- Table structure for sys_workbench_panel
-- ----------------------------
CREATE TABLE sys_workbench_panel (
  id_ varchar2(64) NOT NULL,
  alias_ varchar2(255) NOT NULL ,
  name_ varchar2(255) DEFAULT '' NOT NULL ,
  type_ varchar2(64) DEFAULT NULL ,
  desc_ varchar2(500) DEFAULT NULL ,
  data_type_ varchar2(64) DEFAULT NULL ,
  data_source_ varchar2(255) DEFAULT NULL ,
  auto_refresh_ number(10) DEFAULT '0' ,
  width_ number(10) DEFAULT NULL ,
  height_ number(10) DEFAULT NULL ,
  display_content_ clob ,
  more_url_ varchar2(255) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT SYSTIMESTAMP NOT NULL,
  create_by_ varchar2(64) DEFAULT NULL,
  update_time_  timestamp(0)  DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL,
  delete_flag_ varchar2(10) DEFAULT NULL,
  PRIMARY KEY (id_)
)   ;

COMMENT ON TABLE sys_workbench_panel IS '工作台面板';

CREATE INDEX idx_alias_ ON sys_workbench_panel (alias_);

-- ----------------------------
-- Table structure for sys_workbench_panel_templ
-- ----------------------------
CREATE TABLE sys_workbench_panel_templ (
  id_ varchar2(64) NOT NULL,
  key_ varchar2(255) DEFAULT NULL ,
  name_ varchar2(255) DEFAULT NULL,
  desc_ varchar2(500) DEFAULT NULL ,
  html_ clob ,
  PRIMARY KEY (id_)
)   ;

COMMENT ON TABLE sys_workbench_panel_templ IS '工作台面板模板';

CREATE TABLE sys_file (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(64) DEFAULT NULL ,
  uploader_ varchar2(128) DEFAULT NULL ,
  path_ varchar2(256) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  version_ number(10) DEFAULT NULL ,
  delete_ number(3) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE sys_file IS '系统附件';


-- 附件存储 2018-6-10 00:29:06
-- IUploader 实现db策略的上传实现
CREATE TABLE db_uploader (
  id_ varchar2(64) NOT NULL,
  bytes_ blob,
  PRIMARY KEY (id_)
) ;



 
CREATE TABLE c_holiday_conf (
  id_ varchar2(64) NOT NULL,
  name_ varchar2(255) DEFAULT NULL,
  system_ varchar2(255) DEFAULT NULL,
  year_ number(10) DEFAULT NULL,
  startDay_ date DEFAULT NULL,
  endDay_ date DEFAULT NULL,
  type_ varchar2(255) DEFAULT NULL,
  remark_ varchar2(500) DEFAULT NULL,
  PRIMARY KEY (id_)
) ;


CREATE TABLE c_work_calendar (
  id_ varchar2(20) NOT NULL,
  day_ date DEFAULT NULL,
  isWorkDay_ varchar2(20) DEFAULT NULL,
  type_ varchar2(255) DEFAULT NULL,
  system_ varchar2(255) DEFAULT NULL,
  PRIMARY KEY (id_)
) ;



CREATE TABLE c_schedule (
  id_ varchar2(20) NOT NULL ,
  title_ varchar2(500) DEFAULT NULL ,
  desc_ varchar2(2000) DEFAULT NULL ,
  task_url_ varchar2(255) DEFAULT NULL ,
  type_ varchar2(64) DEFAULT NULL ,
  open_type_ varchar2(64) DEFAULT NULL ,
  owner_ varchar2(64) DEFAULT NULL ,
  owner_name_ varchar2(64) DEFAULT NULL ,
  participant_names_ varchar2(1000) DEFAULT NULL ,
  start_time_ timestamp(0) DEFAULT NULL ,
  end_time_ timestamp(0) DEFAULT NULL ,
  actual_start_time_ timestamp(0) DEFAULT NULL ,
  complete_time_ timestamp(0) DEFAULT NULL ,
  rate_progress_ number(10) DEFAULT NULL ,
  submitter_ varchar2(64) DEFAULT NULL ,
  submitNamer_ varchar2(64) DEFAULT NULL ,
  remark_ varchar2(500) DEFAULT NULL,
  isLock_ varchar2(10) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  delete_flag_ varchar2(10) DEFAULT NULL ,
  rev_ number(10) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE c_schedule IS '日程';



CREATE TABLE c_schedule_participant (
  id_ varchar2(20) NOT NULL ,
  schedule_id_ varchar2(20) DEFAULT NULL ,
  participantor_name_ varchar2(255) DEFAULT NULL ,
  participantor_ varchar2(64) DEFAULT NULL ,
  rate_progress_ number(10) DEFAULT NULL ,
  submit_comment_ varchar2(500) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  actual_start_time_ timestamp(0) DEFAULT NULL ,
  complete_time_ timestamp(0) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE c_schedule_participant IS '日程参与者';

CREATE INDEX idx_schedule_id ON c_schedule_participant (schedule_id_);
CREATE INDEX idx_participantor ON c_schedule_participant (participantor_);


CREATE TABLE c_schedule_biz (
  id_ varchar2(20) NOT NULL ,
  schedule_id_ varchar2(20) NOT NULL ,
  biz_id_ varchar2(20) NOT NULL ,
  from_ varchar2(64) NOT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE c_schedule_biz IS '日程业务关联表';

CREATE INDEX c_schedule_biz_idx_schedule_id ON c_schedule_biz (schedule_id_);
CREATE INDEX schedule_biz_id_idx ON c_schedule_biz (biz_id_);




 