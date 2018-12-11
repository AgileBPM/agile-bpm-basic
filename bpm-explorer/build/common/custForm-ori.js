var __path = "../.."

var aryCss__=[]; //先用打包的

var aryJs__=[ 	"/assets/js/common/custForm/formCalculateModule.js",
				"/assets/js/common/custForm/formPermissionModule.js",
				"/assets/js/common/custForm/formControlsModule.js",
				"/assets/js/common/custForm/formServiceModule.js"];
/**
 * js引入时导入必须的js文件。
 */
for(var i=0;i<aryJs__.length;i++){
	var str="<script src=\""+__path + aryJs__[i] +"\"></script>";
	document.write(str);
}