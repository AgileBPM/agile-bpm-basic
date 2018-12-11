ALTER TABLE "BPM_TASK_STACK" 
MODIFY ("TASK_ID_" NOT NULL );

ALTER TABLE "BPM_TASK_STACK" 
ADD ("NODE_TYPE_" VARCHAR2(64) )
ADD ("ACTION_NAME_" VARCHAR2(64) );

ALTER TABLE "BPM_TASK_STACK" DROP ("PATH_");

CREATE INDEX "idx_exestack_taskid"  ON "BPM_TASK_STACK" ("TASK_ID_"); 
	
update bpm_task_stack set  node_type_ = 'userNode';

-- 流程提交日志
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
