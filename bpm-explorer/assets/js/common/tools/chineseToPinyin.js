/**
 * type :1 全拼  不填为首字母
 * @param param{type:1 / 0,Chinese,'我是中国人'}  return 'woshizhongguoren'
 */
function ChineseToPinyin(param){
	if(param.Chinese == undefined ){ 
		$.topCall.error("使用 汉字转拼音功能出错！<br>  参数：param.Chinese <br>eg:ChineseToPinyin({Chinese:'国语'})");
		return;
	}
	// 为空的时候直接返回
	if(!param.Chinese){
		return;
	}
	if(!param.type){
		param.type =0;
	}
	 
	var py = "";
	var url= __ctx+ "/pinyinServlet";
	url=url.getNewUrl();
	
	$.ajax({
		   async : false,
		   type: "POST",
		   url: url,
		   data: param,
		   success: function(msg){
		    py = msg;
		   }
		 });
	return py;
}