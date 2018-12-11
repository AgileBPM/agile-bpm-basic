# Demo 模块

这是一个案例模块，您可以复制该项目，在此基础上修改

1. 执行demo案例 相关表单的 SQL
2. 访问前端地址  http://localhost:8080/bpm-explorer/demo/demo/demoList.html  

> demo java 对象 和 业务对象demo json 可以无缝转换。
 demoList 页面可以挂demo的自定义表单，无需开发编辑页面。支持字段级权限控制。
表单可以直接用作流程审批。

*您也可以选择使用复制public库，有完整demo流程案例，如果public库被人破坏请联系我重置*
 
 ### 若修改company 包名，您需要以下操作
 - 需要添加bean扫描 在 applicationContext.xml中配置
 - 添加mapper.xml的 扫描 在base-db.xml中配置 sqlSessionFactory 的 mapperLocations
 - 添加 dao层扫描 在base-db.xml中配置  MapperScannerConfigurer
 - 修改 app-tx.xml，添加声明式事物配置
 - app-mvc.xml扫描controller层