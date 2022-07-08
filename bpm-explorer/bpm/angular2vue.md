
1.搜索html 文件全文替换 
bpm/definition/start.html      全文替换成        bpm/vueForm/start.html
bpm/task/taskComplete.html      全文替换成        bpm/vueForm/complete.html
bpm/instance/instanceDetail.html  全文替换成        bpm/vueForm/instanceDetail.html

2.
屏蔽 业务表单菜单  改成   vue表单

3.禁用angular表单入口，启用vue表单入口
UPDATE `sys_resource` SET `ENABLE_`='0' WHERE (`ID_`='30'); 
UPDATE `sys_resource` SET `ENABLE_`='1' WHERE (`ID_`='33');




开源版本 内容删除
流程预览导入
业务对象布局，多种持久化功能
表单无用的模板
移除 wf-plugin-biz

前端、替换楼上的入口
删除表单设计器vue，iview相关的配置内容

