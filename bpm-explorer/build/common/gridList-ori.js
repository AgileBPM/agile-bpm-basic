var __path = "../.."

var aryCss__=[]; //先用打包的

var aryJs__=[
            "/assets/js/jquery.min.js",
            "/assets/app-conf.js",
            "/assets/js/bootstrap.min.js",
            "/assets/js/plugins/bootstrap-table/bootstrap-table.js",
            "/assets/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js",
            "/assets/js/plugins/peity/jquery.peity.min.js",
            
            "/assets/js/common/tools/util.js",
            "/assets/js/common/grid.js",
            "/assets/js/common/tools/sysTreeUtil.js",
            
            "/assets/js/plugins/jquery-qtip/jquery.qtip.js",
            "/assets/js/plugins/toastr/toastr.min.js",
            "/assets/js/plugins/layer/layer.min.js",
            
            "/assets/js/plugins/ztree/jquery.ztree.all.min.js",
            "/assets/js/plugins/ztree/ztreeCreator.js",
            "/assets/js/common/tools/customValid.js",
            "/assets/js/common/tools/dialogCreator.js"];

/**
 * js引入时导入必须的css样式。
 */
/*for(var i=0;i<aryCss__.length;i++){
	var str="<link rel=\"stylesheet\" href=\""+__ctx + aryCss__[i] +"\">";
	document.write(str);
}*/

/**
 * js引入时导入必须的js文件。
 */
for(var i=0;i<aryJs__.length;i++){
	var str="<script src=\""+__path + aryJs__[i] +"\"></script>";
	document.write(str);
}