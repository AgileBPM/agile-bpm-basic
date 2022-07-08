window.__ctx = "/agile-bpm-platform";

////跨域时修改 此 ctx 
//window.__ctx = "http://localhost:8080/agile-bpm-platform";

//jQuery 跨域处理
jQuery(function () {  //, headers: { 'x-requested-with': 'XMLHttpRequest' }
	jQuery.ajaxSetup({crossDomain: true, xhrFields: {withCredentials: true}});
	jQuery.support.cors = true;
}); 

/**
 * @url 如果不含ctx，添加ctx
 * @replacePageParam true /false
 * eg:user/getData?userId={userId}, 当前页面：userEdit.html?userId=1
 * 这时候会将1 赋值给userId
 * 
 */

window.getCtxUrl = function(url,replaceRequestParam){
	if(url && !url.startWith(__ctx) && !url.startWith("http")){
		url.startWith("/")?"":url = "/"+url;
		url = __ctx + url;
		
		//如果URL含?且需要替换页面请求参数，则进行格式化
		if(replaceRequestParam && url.indexOf("?")!=-1 && $.getParams){
			url = url.format($.getParams());
		}
	}
	return url;
}

window.getProjectUrl = function(url){
	if(url && url.indexOf("http://")==-1 && url.substring(0,1)==="/"){
		 var pathname =window.document.location.pathname;
		 var projectPath = pathname.substring(0,pathname.substr(1).indexOf('/')+1);
		 // 特殊处理下流程设计器
		 if("/bus,/bpm,/sys,/org,/form,/flow-editor,/bpmplugin".indexOf(projectPath)!= -1){
			 return url;
		 }
		if(url.startWith(projectPath)){
			return url;
		}
		 return projectPath+url;
	}
	return url;
}

