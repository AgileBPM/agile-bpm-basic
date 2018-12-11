-- 流程模块表创建语句   含 activiti SQL， AgileBPM包装的SQL
 
-- ----------------------------
-- Table structure for bpm_bus_link
-- ----------------------------
CREATE TABLE bpm_bus_link (
  id_ varchar2(64) NOT NULL ,
  def_id_ varchar2(64) DEFAULT NULL ,
  inst_id_ varchar2(64) DEFAULT NULL ,
  biz_id_ varchar2(64) DEFAULT NULL ,
  biz_code_ varchar2(64) NOT NULL ,
  PRIMARY KEY (id_,biz_code_)
)  
partition by list(biz_code_)  
(  
  partition p01 values('unknown')
);

COMMENT ON TABLE bpm_bus_link IS '流程实例与业务数据关系表';

-- ----------------------------
-- Table structure for bpm_definition
-- ----------------------------
CREATE TABLE bpm_definition (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(64) NOT NULL ,
  key_ varchar2(64) NOT NULL ,
  desc_ varchar2(1024) DEFAULT NULL ,
  type_id_ varchar2(64) DEFAULT NULL ,
  status_ varchar2(40) DEFAULT NULL ,
  act_def_id_ varchar2(64) DEFAULT NULL ,
  act_model_id_ varchar2(64) DEFAULT NULL,
  act_deploy_id_ varchar2(64) DEFAULT NULL ,
  version_ number(10) DEFAULT NULL ,
  main_def_id_ varchar2(64) DEFAULT NULL ,
  is_main_ char(1) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_org_id_ varchar2(64) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  support_mobile_ number(10) DEFAULT '0',
  def_setting_ clob,
  rev_ number(10) DEFAULT NULL,
  PRIMARY KEY (id_)
)   ;

COMMENT ON TABLE bpm_definition IS '流程定义';

CREATE INDEX bpm_process_def_key ON bpm_definition (key_);

-- ----------------------------
-- Table structure for bpm_instance
-- ----------------------------
CREATE TABLE bpm_instance (
  id_ varchar2(64) NOT NULL ,
  subject_ varchar2(128) NOT NULL ,
  def_id_ varchar2(64) NOT NULL ,
  act_def_id_ varchar2(64) DEFAULT NULL ,
  def_key_ varchar2(128) DEFAULT NULL ,
  def_name_ varchar2(128) NOT NULL ,
  biz_key_ varchar2(64) DEFAULT NULL ,
  status_ varchar2(40) DEFAULT NULL ,
  end_time_ timestamp(0) DEFAULT NULL ,
  duration_ number(19) DEFAULT NULL ,
  type_id_ varchar2(64) DEFAULT NULL ,
  act_inst_id_ varchar2(64) DEFAULT NULL ,
  create_by_ varchar2(64) DEFAULT NULL ,
  creator_ varchar2(64) DEFAULT NULL ,
  create_time_ timestamp(0) DEFAULT NULL ,
  create_org_id_ varchar2(64) DEFAULT NULL ,
  update_by_ varchar2(64) DEFAULT NULL ,
  update_time_ timestamp(0) DEFAULT NULL ,
  is_formmal_ char(1) NOT NULL ,
  parent_inst_id_ varchar2(64) DEFAULT NULL ,
  is_forbidden_ number(5) DEFAULT NULL ,
  data_mode_ varchar2(20) DEFAULT NULL,
  support_mobile_ number(10) DEFAULT '0',
  super_node_id_ varchar2(50) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE bpm_instance IS '流程实例';

CREATE INDEX idx_proinst_bpminstid ON bpm_instance (act_inst_id_);
CREATE INDEX idx_proinst_parentId ON bpm_instance (parent_inst_id_);
CREATE INDEX idx_proinst_bizkey ON bpm_instance (biz_key_);

-- ----------------------------
-- Table structure for bpm_task
-- ----------------------------
CREATE TABLE bpm_task (
  id_ varchar2(64) NOT NULL ,
  name_ varchar2(64) NOT NULL ,
  subject_ varchar2(128) NOT NULL ,
  inst_id_ varchar2(64) NOT NULL ,
  task_id_ varchar2(64) DEFAULT NULL ,
  act_inst_id_ varchar2(64) DEFAULT NULL ,
  act_execution_id_ varchar2(64) DEFAULT NULL ,
  node_id_ varchar2(64) DEFAULT NULL ,
  def_id_ varchar2(64) NOT NULL ,
  assignee_id_ varchar2(64) DEFAULT NULL ,
  assignee_names_ varchar2(500) DEFAULT NULL,
  status_ varchar2(64) NOT NULL ,
  priority_ number(10) DEFAULT NULL ,
  due_time_ timestamp(0) DEFAULT NULL ,
  task_type_ varchar2(64) DEFAULT NULL ,
  parent_id_ varchar2(64) DEFAULT NULL ,
  type_id_ varchar2(64) DEFAULT NULL ,
  create_time_ timestamp(0) NOT NULL ,
  create_by_ varchar2(64) DEFAULT NULL,
  support_mobile_ number(10) DEFAULT '0',
  back_node_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE bpm_task IS '流程任务';

CREATE INDEX idx_bpmtask_instid ON bpm_task (inst_id_);
CREATE INDEX idx_bpmtask_taskid ON bpm_task (task_id_);
CREATE INDEX idx_bpmtask_parentid ON bpm_task (parent_id_);
CREATE INDEX idx_bpmtask_userid ON bpm_task (assignee_id_);

-- ----------------------------
-- Table structure for bpm_task_identitylink
-- ----------------------------
CREATE TABLE bpm_task_identitylink (
  id_ varchar2(64) NOT NULL ,
  task_id_ varchar2(64) DEFAULT NULL ,
  inst_id_ varchar2(64) DEFAULT NULL,
  type_ varchar2(20) DEFAULT NULL ,
  identity_name_ varchar2(64) DEFAULT NULL ,
  identity_ varchar2(20) DEFAULT NULL ,
  permission_code_ varchar2(64) DEFAULT NULL,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE bpm_task_identitylink IS '任务候选人';

CREATE INDEX idx_taskcandidate_taskid ON bpm_task_identitylink (task_id_);
CREATE INDEX idx_candidate_instid ON bpm_task_identitylink (inst_id_);
CREATE INDEX idx_candidate_permission_code_ ON bpm_task_identitylink (permission_code_);

-- ----------------------------
-- Table structure for bpm_task_opinion
-- ----------------------------
CREATE TABLE bpm_task_opinion (
  id_ varchar2(64) NOT NULL ,
  inst_id_ varchar2(64) NOT NULL ,
  sup_inst_id_ varchar2(64) DEFAULT NULL ,
  task_id_ varchar2(64) DEFAULT NULL ,
  task_key_ varchar2(64) DEFAULT NULL ,
  task_name_ varchar2(255) DEFAULT NULL ,
  token_ varchar2(64) DEFAULT NULL ,
  assign_info_ varchar2(2000) DEFAULT NULL ,
  approver_ varchar2(64) DEFAULT NULL ,
  approver_name_ varchar2(64) DEFAULT NULL ,
  approve_time_ timestamp(0) DEFAULT NULL ,
  opinion_ varchar2(2000) DEFAULT NULL ,
  status_ varchar2(64) NOT NULL ,
  form_id_ varchar2(64) DEFAULT NULL ,
  create_by_ varchar2(255) DEFAULT NULL,
  create_time_ timestamp(0) DEFAULT NULL ,
  dur_ms_  number(19) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE bpm_task_opinion IS '流程任务审批记录';

CREATE INDEX idx_opinion_supinstid ON bpm_task_opinion (sup_inst_id_);
CREATE INDEX idx_opinion_task ON bpm_task_opinion (task_id_);
CREATE INDEX idx_opinion_instId ON bpm_task_opinion (inst_id_);

-- ----------------------------
-- Table structure for bpm_task_stack
-- ----------------------------
CREATE TABLE bpm_task_stack (
  id_ varchar2(64) NOT NULL ,
  task_id_ varchar2(64) DEFAULT NULL NOT NULL ,
  inst_id_ varchar2(64) DEFAULT NULL ,
  parent_id_ varchar2(64) DEFAULT NULL ,
  node_id_ varchar2(64) NOT NULL ,
  node_name_ varchar2(125) DEFAULT NULL,
  start_time_ timestamp(0) DEFAULT NULL ,
  end_time timestamp(0) DEFAULT NULL ,
  is_muliti_task_ number(5) DEFAULT NULL ,
  node_type_ varchar2(64) DEFAULT NULL ,
  action_name_ varchar2(64) DEFAULT NULL ,
  PRIMARY KEY (id_)
)  ;

COMMENT ON TABLE bpm_task_stack IS '流程执行堆栈树';

CREATE INDEX idx_exestack_instid ON bpm_task_stack (inst_id_);
CREATE INDEX idx_exestack_taskid ON bpm_task_stack (task_id_);

-- 流程日志插件
CREATE TABLE bpm_submit_data_log (
  id varchar2(64) NOT NULL ,
  task_id_ varchar2(64) DEFAULT NULL ,
  inst_id_ varchar2(64) DEFAULT NULL ,
  data clob ,
  destination varchar2(255) DEFAULT NULL ,
  extendConf varchar2(500) DEFAULT NULL ,
  PRIMARY KEY (id)
)  ;

COMMENT ON TABLE bpm_submit_data_log IS '业务对象数据提交日志'
