/**
 * bus权限工具类
 */
window.BusinessPermission = {};
/**
 * <pre>
 * 打开权限对话框 
 * objType:对象类型 枚举在 BusinessPermissionObjType 
 * objVal:对象的值
 * boKeys:boKey，逗号分隔 eg:a,b,c,...
 * </pre>
 */
window.BusinessPermission.openDialog = function(objType, objVal, boKeys) {
	var conf = {
		url : "/bus/businessPermission/businessPermissionEdit.html?objType=" + objType + "&objVal=" + objVal + "&boKeys=" + boKeys,// url不为空则使用iframe类型对话框
		title : "业务权限",
		topOpen : true
	};
	jQuery.Dialog.open(conf);
};
