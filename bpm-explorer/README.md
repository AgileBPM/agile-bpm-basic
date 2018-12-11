# bpm-explorer 
## 前端项目 
前端项目非单页面应用，而且系统为了入手更容易，仅仅对公共js 模块化打包，并暴露关键框架的全局引用。
当在开发的时候，不需要关注公共模块的js，你可以直接引入自己的js。

允许前端项目有两种形式
- 前后端一起开发的推荐 通过Tomcat 运行前端资源
- 前端独立开发的，推荐 	`npm run dev ` 运行前端项目

## rest服务端 ctx
assets/app-conf.js 中配置后端服务 前缀、默认为  /agile-bpm-platform  需要修改为具体后端请求地址：http://localhost:8080/agile-bpm-platform
flow-editor/editor-app/app-cfg.js  中配置流程设计器的后端服务前缀、默认为 /agile-bpm-platform 
修改公共 js 后需要手动打包   `npm run build`

## 页面
列表页面可参考 sysPropertiesList.html
编辑页可参考 sysPropertiesEdit.html


#### 安装 nodejs 安装 webpack 等相关依赖
` npm install`
