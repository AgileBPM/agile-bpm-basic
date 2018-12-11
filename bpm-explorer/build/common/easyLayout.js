
var __path = "../.."

var aryCss__=["/assets/js/plugins/easyui-layout/layout.css",
              "/assets/js/plugins/easyui-layout/panel.css",
              "/assets/js/plugins/easyui-layout/menu.css"
              ];

var aryJs__=[ "/assets/js/plugins/easyui-layout/jquery.parser.js",
              "/assets/js/plugins/easyui-layout/jquery.resizable.js",
              "/assets/js/plugins/easyui-layout/jquery.panel.js",
              "/assets/js/plugins/easyui-layout/jquery.layout.js",
              "/assets/js/plugins/easyui-layout/jquery.menu.js"];

/**
 * js引入时导入必须的css样式。
 */
for(var i=0;i<aryCss__.length;i++){
	var str="<link rel=\"stylesheet\" href=\""+__path + aryCss__[i] +"\">";
	document.write(str);
}

/**
 * js引入时导入必须的js文件。
 */
for(var i=0;i<aryJs__.length;i++){
	var str="<script src=\""+__path + aryJs__[i] +"\"></script>";
	document.write(str);
}



$(function(){
	$('.easy-layout').layout();
})
// eayui layout 引入改jsp
// 参考easyui-layout 的文档

