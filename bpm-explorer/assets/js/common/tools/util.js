var $ = jQuery;
jQuery.extend({
	/**
	 * 判断是否是IE浏览器
	 * 
	 * @returns {Boolean}
	 */
	isIE : function() {
		var appName = navigator.appName;
		var idx = appName.indexOf("Microsoft");
		return idx == 0;
	},
	/**
	 * 判断是否是IE6浏览器
	 * 
	 * @returns {Boolean}
	 */
	isIE6 : function() {
		if (($.browser.msie && $.browser.version == '6.0') && !$.support.style)
			return true;
		return false;
	},
	
	/**
	 * <img src="img/logo.png" onload="$.fixPNG(this);"/> 解决图片在ie中背景透明的问题。
	 * 
	 * @param imgObj
	 */
	fixPNG : function(imgObj) {
		var arVersion = navigator.appVersion.split("MSIE");
		var version = parseFloat(arVersion[1]);
		if ((version >= 5.5) && (version < 7) && (document.body.filters)) {
			var imgID = (imgObj.id) ? "id='" + imgObj.id + "' " : "";
			var imgClass = (imgObj.className) ? "class='" + imgObj.className
					+ "' " : "";
			var imgTitle = (imgObj.title)
					? "title='" + imgObj.title + "' "
					: "title='" + imgObj.alt + "' ";
			var imgStyle = "display:inline-block;" + imgObj.style.cssText;
			var strNewHTML = "<span "
					+ imgID
					+ imgClass
					+ imgTitle
					+ " style=\""
					+ "width:"
					+ imgObj.width
					+ "px; height:"
					+ imgObj.height
					+ "px;"
					+ imgStyle
					+ ";"
					+ "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
					+ "(src=\'" + imgObj.src
					+ "\', sizingMethod='scale');\"></span>";
			imgObj.outerHTML = strNewHTML;
		}
	},
	/**
	 * 获取当前路径中指定键的参数值。
	 * 
	 * @param key
	 * @returns
	 */
	getParameter : function(key) {
		var parameters = unescape(window.location.search.substr(1)).split("&");
		for (var i = 0; i < parameters.length; i++) {
			var paramCell = parameters[i].split("=");
			if (paramCell.length == 2
					&& paramCell[0].toUpperCase() == key.toUpperCase()) {
				return paramCell[1];
			}
		}
		return "";
	},
	/**
	 * 根据年份和月份获取某个月的天数。
	 * 
	 * @param year
	 * @param month
	 * @returns
	 */
	getMonthDays : function(year, month) {
		if (month < 0 || month > 11) {
			return 30;
		}
		var arrMon = new Array(12);
		arrMon[0] = 31;
		if (year % 4 == 0) {
			arrMon[1] = 29;
		} else {
			arrMon[1] = 28;
		}
		arrMon[2] = 31;
		arrMon[3] = 30;
		arrMon[4] = 31;
		arrMon[5] = 30;
		arrMon[6] = 31;
		arrMon[7] = 31;
		arrMon[8] = 30;
		arrMon[9] = 31;
		arrMon[10] = 30;
		arrMon[11] = 31;
		return arrMon[month];
	},
	/**
	 * 计算日期为当年的第几周
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @returns
	 */
	weekOfYear : function(year, month, day) {
		// year 年
		// month 月
		// day 日
		// 每周从周日开始
		var date1 = new Date(year, 0, 1);
		var date2 = new Date(year, month - 1, day, 1);
		var dayMS = 24 * 60 * 60 * 1000;
		var firstDay = (7 - date1.getDay()) * dayMS;
		var weekMS = 7 * dayMS;
		date1 = date1.getTime();
		date2 = date2.getTime();
		return Math.ceil((date2 - date1 - firstDay) / weekMS) + 1;
	},
	/**
	 * 时间差计算
	 */
	timeLag:function(difference){
		var  r ="",
		////计算出相差天数
		days=Math.floor(difference/(24*3600*1000)),
		//计算出小时数
		leave1=difference%(24*3600*1000),   //计算天数后剩余的毫秒数
		hours=Math.floor(leave1/(3600*1000)),
		//计算相差分钟数
		leave2=leave1%(3600*1000),      //计算小时数后剩余的毫秒数
		minutes=Math.floor(leave2/(60*1000)),
		//计算相差秒数
		leave3=leave2%(60*1000),    //计算分钟数后剩余的毫秒数
		seconds=Math.round(leave3/1000);
		if(days>0) r +=days+"天";
		if(hours>0) r +=hours+"小时";
		if(minutes>0) r +=minutes+"分钟";
		if(seconds>0) r +=seconds+"秒";
		
		if(r){
			return r;
		}
		
		if(leave3){
			return difference+"毫秒";
		}
		return ""
	},
	/**
	 * 时间差计算
	 * date1 开始时间 毫秒数
	 * date2 结束时间 毫秒数
	 */
	timeDifference:function(date1,date2){
		return $.timeLag(date2-date1);
	},
	/**
	 * 添加书签
	 * 
	 * @param title
	 * @param url
	 * @returns {Boolean}
	 */
	addBookmark : function(title, url) {
		if (window.sidebar) {
			window.sidebar.addPanel(title, url, "");
		} else if (document.all) {
			window.external.AddFavorite(url, title);
		} else if (window.opera && window.print) {
			return true;
		}
	},

	/**
	 * 设置cookie
	 * 
	 * @param name
	 * @param value
	 */
	setCookie : function(name, value) {
		var expdate = new Date();
		var argv = arguments;
		var argc = arguments.length;
		var expires = (argc > 2) ? argv[2] : null;
		var path = (argc > 3) ? argv[3] : null;
		var domain = (argc > 4) ? argv[4] : null;
		var secure = (argc > 5) ? argv[5] : false;
		if (expires != null)
			expdate.setTime(expdate.getTime() + (expires * 1000));

		document.cookie = name
				+ "="
				+ escape(value)
				+ ((expires == null) ? "" : (";  expires=" + expdate
						.toGMTString()))
				+ ((path == null) ? "" : (";  path=" + path))
				+ ((domain == null) ? "" : (";  domain=" + domain))
				+ ((secure == true) ? ";  secure" : "");

	},
	/**
	 * 删除cookie
	 * 
	 * @param name
	 */
	delCookie : function(name) {
		var exp = new Date();
		exp.setTime(exp.getTime() - 1);
		var cval = getCookie(name);
		document.cookie = name + "=" + cval + ";  expires=" + exp.toGMTString();

	},
	/**
	 * 读取cookie
	 * 
	 * @param name
	 * @returns
	 */
	getCookie : function(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return $.getCookieVal(j);
			i = document.cookie.indexOf("  ", i) + 1;
			if (i == 0)
				break;
		}
		return null;

	},
	getCookieVal : function(offset)

	{
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1)
			endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	},
	/**
	 * 打开全屏的窗口
	 * @param url
	 * @returns
	 */
	openFullWindow : function(url) {
		var h = screen.availHeight - 65;
		var w = screen.availWidth - 5;
		var vars = "top=0,left=0,height="
				+ h
				+ ",width="
				+ w
				+ ",status=no,toolbar=no,menubar=no,location=no,resizable=1,scrollbars=1";

		var win = window.open($.handProjectUrl(url), "", vars, true);
		return win;
	},
	handProjectUrl : function(url){
		if(url && url.indexOf("http://")==-1 && url.substring(0,1)==="/"){
			 var pathname =window.document.location.pathname;
			 var projectPath = pathname.substring(0,pathname.substr(1).indexOf('/')+1);
			 
			 if(projectPath === '/flow-editor' || projectPath === "/bpm" || projectPath === "/sys" || projectPath === "/org" || projectPath === "/form"){
				 return url;
			 }
			 
			 return projectPath+url;
		}
		return url;
	},
	/**
	 * 如果传入的值是null、undefined或空字符串，则返回true。（可选的）
	 * 
	 * @param {Mixed}
	 *            value 要验证的值。
	 * @param {Boolean}
	 *            allowBlank （可选的） 如果该值为true，则空字符串不会当作空而返回true。
	 * @return {Boolean}
	 */
	isEmpty : function(v, allowBlank) {
		return v === null || v === undefined
				|| (!allowBlank ? v === '' : false);
	},
	/**
	 * 将数字转换成人名币大写。
	 * 
	 * @param currencyDigits
	 * @returns
	 */
	convertCurrency : function(currencyDigits) {

		var MAXIMUM_NUMBER = 99999999999.99;
		var CN_ZERO = "零";
		var CN_ONE = "壹";
		var CN_TWO = "贰";
		var CN_THREE = "叁";
		var CN_FOUR = "肆";
		var CN_FIVE = "伍";
		var CN_SIX = "陆";
		var CN_SEVEN = "柒";
		var CN_EIGHT = "捌";
		var CN_NINE = "玖";
		var CN_TEN = "拾";
		var CN_HUNDRED = "佰";
		var CN_THOUSAND = "仟";
		var CN_TEN_THOUSAND = "万";
		var CN_HUNDRED_MILLION = "亿";
		var CN_SYMBOL = "";
		var CN_DOLLAR = "元";
		var CN_TEN_CENT = "角";
		var CN_CENT = "分";
		var CN_INTEGER = "整";
		var integral;
		var decimal;
		var outputCharacters;
		var parts;
		var digits, radices, bigRadices, decimals;
		var zeroCount;
		var i, p, d;
		var quotient, modulus;
		currencyDigits = currencyDigits.toString();
		if (currencyDigits == "") {
			return "";
		}
		if (currencyDigits.match(/[^,.\d]/) != null) {
			return "";
		}
		if ((currencyDigits)
				.match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
			return "";
		}
		currencyDigits = currencyDigits.replace(/,/g, "");
		currencyDigits = currencyDigits.replace(/^0+/, "");

		if (Number(currencyDigits) > MAXIMUM_NUMBER) {
			return "";
		}

		parts = currencyDigits.split(".");
		if (parts.length > 1) {
			integral = parts[0];
			decimal = parts[1];

			decimal = decimal.substr(0, 2);
		} else {
			integral = parts[0];
			decimal = "";
		}

		digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE,
				CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
		radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
		bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
		decimals = new Array(CN_TEN_CENT, CN_CENT);

		outputCharacters = "";

		if (Number(integral) > 0) {
			zeroCount = 0;
			for (i = 0; i < integral.length; i++) {
				p = integral.length - i - 1;
				d = integral.substr(i, 1);
				quotient = p / 4;
				modulus = p % 4;
				if (d == "0") {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						outputCharacters += digits[0];
					}
					zeroCount = 0;
					outputCharacters += digits[Number(d)] + radices[modulus];
				}
				if (modulus == 0 && zeroCount < 4) {
					outputCharacters += bigRadices[quotient];
				}
			}
			outputCharacters += CN_DOLLAR;
		}

		if (decimal != "") {
			for (i = 0; i < decimal.length; i++) {
				d = decimal.substr(i, 1);
				if (d != "0") {
					outputCharacters += digits[Number(d)] + decimals[i];
				}
			}
		}

		if (outputCharacters == "") {
			outputCharacters = CN_ZERO + CN_DOLLAR;
		}
		if (decimal == "") {
			outputCharacters += CN_INTEGER;
		}
		outputCharacters = CN_SYMBOL + outputCharacters;
		return outputCharacters;
	}
	,
	/**
	 * 克隆对象。
	 */
	cloneObject : function(obj) {
		var o = obj.constructor === Array ? [] : {};
		for (var i in obj) {
			if (obj.hasOwnProperty(i)) {
				o[i] = typeof obj[i] === "object"
						? cloneObject(obj[i])
						: obj[i];
			}
		}
		return o;
	},
	getFileExtName : function(fileName) {
		var pos = fileName.lastIndexOf(".");
		if (pos == -1)
			return "";
		return fileName.substring(pos + 1);
	},
	// 转成千分位。
	comdify : function(v) {
		if (v && v != '') {
			n = v + "";
			var re = /\d{1,3}(?=(\d{3})+$)/g;
			var n1 = n.trim().replace(/^(\d+)((\.\d+)?)$/, function(s, s1, s2) {
						return s1.replace(re, "$&,") + s2;
					});
			return n1;
		}
		return v;
	},
	toNumber : function(v) {
		if (v && v != '') {
			if (v.indexOf(',') == -1)
				return v;
			var ary = v.split(',');
			var val = ary.join("");
			return val;
		}
		return 0;
	},
	getParams : function(){
		var locUrl = window.location.search.substr(1);
		var aryParams=locUrl.split("&");
		var json={};
		for(var i=0;i<aryParams.length;i++){
			var pair=aryParams[i];
			var aryEnt=pair.split("=");
			var key=aryEnt[0];
			var val=aryEnt[1];
			if(json[key]){
				json[key]=json[key] +"," + val;
			}
			else{
				json[key]=val;
			}
		}
		return json;
	},
	getParam : function(name){
			var locUrl = window.location.search.substr(1);
			var aryParams=locUrl.split("&");
			var rtn="";
			for(var i=0;i<aryParams.length;i++){
				var pair=aryParams[i];
				var aryEnt=pair.split("=");
				var key=aryEnt[0];
				var val=aryEnt[1];
				if(key!=name) continue;
				if(rtn==""){
					rtn=val;
				}
				else{
					rtn+="," + val;
				}
			}
			return rtn;
	},
	/**
	 * @说明 获取请求响应的数据
	 * @失败 默认toast 提示错误信息
	 * @成功 不展示成功信息自己处理
	 * @isFastJson 是否触发FastJson格式化，默认false
	 */
	getResultData : function(defer,fn,msgType,isFastJson){
		defer.then(function(result){
			$.getResult(result,fn,msgType,null,null,isFastJson);
			},function(status){
				if(status ==!0){
					$.Dialog.alert("加载失败！"+status,2);
				}
				
			}
		);
	},
	/**
	 * @说明 获取请求响应的结果信息
	 * @失败 默认alert 提示错误信息
	 * @成功 默认alert 展示成功信息，并确定后回调调用者
	 * @isFastJson 是否触发FastJson格式化，默认false
	 */
	getResultMsg : function(defer,fn,errorFn,isFastJson){
		defer.then(function(result){
				$.getResult(result,fn,"alert","alert",errorFn,isFastJson);
			},function(status){
				if(errorFn){
					errorFn(status);
				}
				$.Dialog.alert("加载失败！"+status,2);
			}
		);
	},
	/**
	 * 获取请求响应的结果信息
	 * @result 服务器返回的常规数据
	 * @fn 回调函数
	 * @errMsgType 错误时的显示类型 alert，toast（默认）
	 * @sucMsgType 成功时的显示类型 alert（在确定后触发回调fn），toast（不触发回调fn），为空时直接触发回调fn
	 * @isFastJson 是否触发FastJson格式化，默认false
	 */
	getResult : function(result,fn,errMsgType,sucMsgType,errorFn,isFastJson){
		if(typeof(result) !== "object"){
			if(!result.startWith("{") && !result.startWith("[")){
				result = {"isOk":false,msg:"服务器反馈数据格式存在异常，无法解析反馈结果！","cause":result}
			}else{
				var result	= eval('(' + result + ')');
			}
		}
		if(isFastJson){
			FastJson.format(result);//处理json循环索引的问题
		}
		if(!result.isOk){
			if(!errMsgType || errMsgType ==='toast'){
				$.Toast.error(result.msg);
			}else if (errMsgType === 'alert'){
				$.Dialog.alert(result.msg,2);
			}
			console.error(result);
			if(errorFn){errorFn(result)};
			return;
		}else{
			if(!sucMsgType){//不需要任何显示
				if(fn){
					fn(result.data);
				}
			}else if(sucMsgType ==='toast'){
				$.Toast.success(result.msg);
				if(fn){
					fn(result.data);
				}
			}else if (errMsgType === 'alert'){
				$.Dialog.alert(result.msg,function(){
					if(fn){
						fn(result.data);
					}
				},1);
			}
		}
	},
	getPageSize:function() { 
		var winW, winH; 
		if(window.innerHeight) {// all except IE 
			winW = window.innerWidth; 
			winH = window.innerHeight; 
		} else if (document.documentElement && document.documentElement.clientHeight) {// IE 6 Strict Mode 
			winW = document.documentElement.clientWidth; 
			winH = document.documentElement.clientHeight; 
		} else if (document.body) { // other 
			winW = document.body.clientWidth; 
			winH = document.body.clientHeight; 
		}  // for small pages with total size less then the viewport  
		return {width:winW, height:winH}; 
	}
	
});


/**
 * 功能：移除首尾空格
 */
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
/**
 * 功能:移除左边空格
 */
String.prototype.lTrim = function() {
	return this.replace(/(^\s*)/g, "");
};
/**
 * 功能:移除右边空格
 */
String.prototype.rTrim = function() {
	return this.replace(/(\s*$)/g, "");
};
/**
 * 判断文件名是否是图片
 */
String.prototype.isPicture = function(){
    //判断是否是图片 - strFilter必须是小写列举
    var strFilter=".jpeg|.gif|.jpg|.png|.bmp|.pic|"
    if(this.indexOf(".")>-1)
    {
        var p = this.lastIndexOf(".");
        var strPostfix=this.substring(p,this.length) + '|';        
        strPostfix = strPostfix.toLowerCase();
        if(strFilter.indexOf(strPostfix)>-1){
            return true;
        }
    }        
    return false;            
}

/**
 * 字符串根据某个格式转化成日期
 * yyyy-MM-dd HH:mm:ss
 */
String.prototype.toDate = function(format) {
	if(!format){
		format = "yyyy-MM-dd";
	}
	var year = String_toDate(this,format,"yyyy")
	var month = String_toDate(this,format,"MM");
	var day = String_toDate(this,format,"dd");
	var hour = String_toDate(this,format,"HH");
	var minth = String_toDate(this,format,"mm");
	var second = String_toDate(this,format,"ss");
	var date = new Date(0);
	date.setFullYear(year||1);
	date.setMonth(parseInt(month||1)-1);
	date.setDate(day||1);
	date.setHours(hour||0);
	date.setMinutes(minth||0);
	date.setSeconds(second||0);
	return date;
};
function String_toDate(str,format,a){
	if(format.indexOf(a)==-1){
		return null;
	}
	return str.substr(format.indexOf(a),a.length);
}
/**
 * 去除数组中的重复项
 * @function [method] 判断对象是否相同的方法(可选参数，默认实现是深度匹配两个对象是否相同)，示例：function(x,y){if(x.id===y.id)return true;}
 */
Array.prototype.unique=function(method){
	if(!angular.isArray(this))return this;
	var sameObj = method || function(a, b) {
        var tag = true;
        for (var x in a) {
            if (!b[x])
                return false;
            if (typeof(a[x]) === 'object') {
                tag = sameObj(a[x], b[x]);
            } else {
                if (a[x] !== b[x])
                    return false;
            }
        }
        return tag;
    }
	
	var flag, that = this.slice(0);
	this.length = 0;
	for (var i = 0; i < that.length; i++) {
	    var x = that[i];
	    flag = true;
	    for (var j = 0; j < this.length; j++) {
	        y = this[j];
	        if (sameObj(x, y)) {
	            flag = false;
	            break;
	        }
	    }
	    if (flag) this[this.length] = x;
	}
	return this;
}


/**
 * 判断结束是否相等
 * 
 * @param str
 * @param isCasesensitive
 * @returns {Boolean}
 */
String.prototype.endWith = function(str, isCasesensitive) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	var tmp = this.substring(this.length - str.length);
	if (isCasesensitive == undefined || isCasesensitive) {
		return tmp == str;
	} else {
		return tmp.toLowerCase() == str.toLowerCase();
	}

};

/**
 * 判断开始是否相等
 * 
 * @param str
 * @param isCasesensitive
 * @returns {Boolean}
 */
String.prototype.startWith = function(str, isCasesensitive) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	var tmp = this.substr(0, str.length);
	if (isCasesensitive == undefined || isCasesensitive) {
		return tmp == str;
	} else {
		return tmp.toLowerCase() == str.toLowerCase();
	}
};

/**
 * 对html字符进行编码 用法： str=str.htmlEncode();
 * 
 * @returns
 */
String.prototype.htmlEncode = function() {
	return this.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g,
			"&gt;").replace(/\"/g, "&#34;").replace(/\'/g, "&#39;");
};

/**
 * 对html字符串解码 用法： str=str.htmlDecode();
 * 
 * @returns
 */
String.prototype.htmlDecode = function() {
	return this.replace(/\&amp\;/g, '\&').replace(/\&gt\;/g, '\>').replace(
			/\&lt\;/g, '\<').replace(/\&quot\;/g, '\'').replace(/\&\#39\;/g,
			'\'');
};

/**
 * 对json中的特殊字符进行转义
 */
String.prototype.jsonEscape = function() {
	return this.replace(/\"/g, "&quot;").replace(/\n/g, "&nuot;");
};

/**
 * 对json中的特殊字符进行转义
 */
String.prototype.jsonUnescape = function() {
	return this.replace(/&quot;/g, "\"").replace(/&nuot;/g, "\n");
};

/**
 * 字符串替换
 * 
 * @param s1
 *            需要替换的字符
 * @param s2
 *            替换的字符。
 * @returns
 */
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};

/**
 * 移除数组中指定对象
 */
Array.prototype.remove = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] === val) {
            this.splice(i, 1);
            i--;
        }
    }
    return this;
};
Array.prototype.insert = function (index, item) {
	this.splice(index, 0, item);
	return this;
};

/**
 * 格式化字符串
 * eg: var aaa = "myName is {name} my wife name is {wife.name}".format({"name":"苗继方",wife:{name:"柠檬"})
 *    alert (aaa);
 */
String.prototype.format = function (context) {
	  return _stringRender(this, context);
};

window._stringRender = function(template, scopeData) {
	 
	  var tokenReg = /(\\)?\{([^\{\}\\]+)(\\)?\}/g;
	 
	  return template.replace(tokenReg, function (word, slash1, token, slash2) {
	    if (slash1 || slash2) { 
	      return word.replace('\\', '');
	    }
	    
	    var variables = token.replace(/\s/g, '').split('.');
	    
	    if(word.startWith("{url.")){
	    	return $.getParam(variables[1]);
	    }
	    var currentObject = scopeData;
	    var i, length, variable;
	    
	    for(var i=0,key;key=variables[i++];){
	    	currentObject = currentObject[key];
	    	if (currentObject === undefined || currentObject === null) return '';
	    }
	    
	    return currentObject;
	  })
}




/**
 * 日期格式化。
 * 日期格式：
 * yyyy，yy 年份
 * MM 大写表示月份
 * dd 表示日期
 * HH 表示小时
 * mm 表示分钟
 * ss 表示秒
 * q  表示季度
 * 实例如下：
 * var now = new Date(); 
 * var nowStr = now.format("yyyy-MM-dd HH:mm:ss"); 
 */
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"H+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	} 
	
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 

	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
} 


/**
 * 求当前时间到指定时间的差（当前时间是起点）
 * type:date 天数;month:月数;year:年数;hour：小时
 */
Date.prototype.between = function(date,type) {
	if(!type){
		type = "date";
	}
	if(type=="hour"){
		var dateSpan = date.getTime() - this.getTime();
		return Math.floor(dateSpan / (3600 * 1000));
	}
	if(type=="date"){
		var dateSpan = date.getTime() - this.getTime();
		return Math.floor(dateSpan / (24 * 3600 * 1000));
	}
	if(type=="month"){
		return (date.getFullYear()-this.getFullYear()) * 12 + (date.getMonth()-this.getMonth());
	}
	if(type=="year"){
		return date.getFullYear()-this.getFullYear();
	}
	
};

// 禁用刷新。通过传入浏览器类型 来指定禁用某个浏览器的刷新
function forbidF5(exp) {
	var currentExplorer = window.navigator.userAgent;
	// ie "MSIE" ,, firefox "Firefox" ,,Chrome "Chrome",,Opera "Opera",,Safari
	// "Safari"
	if (currentExplorer.indexOf(exp) >= 0) {
		document.onkeydown = function(e) {
			var ev = window.event || e;
			var code = ev.keyCode || ev.which;
			if (code == 116) {
				ev.keyCode ? ev.keyCode = 0 : ev.which = 0;
				cancelBubble = true;
				return false;
			}
		};
	}
}

//产生随机数
window.GetRandomNum = function(Min, Max) {
	var Range = Max - Min;
	var Rand = Math.random();
	return (Min + Math.round(Rand * Range));
}
//产生随机字符串
window.GetRandomStr = function(len) {
　　len = len || 32;
　　var $chars = 'ABCDEFGHJKLMNOPQRSTUVWXYZabcdefhijklmnoprstuvwxyz';
　　var maxPos = $chars.length;
　　var pwd = '';
　　for (var i = 0; i < len; i++) {
　　　　pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
　　}
　　return pwd;
}

var JsonUtil = {
	//定义换行符
	n : "\n",
	//定义制表符
	t : "\t",
	//转换String
	convertToString : function(obj) {
		return JsonUtil.__writeObj(obj, 1);
	},
	//写对象
	__writeObj : function(obj //对象
			, level //层次（基数为1）
			, isInArray) { //此对象是否在一个集合内
		//如果为空，直接输出null
		if (obj == null) {
			return "null";
		}
		//为普通类型，直接输出值
		if (obj.constructor == Number || obj.constructor == Date
				|| obj.constructor == String || obj.constructor == Boolean) {
			var v = obj.toString();
			var tab = isInArray
					? JsonUtil.__repeatStr(JsonUtil.t, level - 1)
					: "";
			if (obj.constructor == String || obj.constructor == Date) {
				//时间格式化只是单纯输出字符串，而不是Date对象
				return tab + ("\"" + v + "\"");
			} else if (obj.constructor == Boolean) {
				return tab + v.toLowerCase();
			} else {
				return tab + (v);
			}
		}

		//写Json对象，缓存字符串
		var currentObjStrings = [];
		//遍历属性
		for (var name in obj) {
			var temp = [];
			//格式化Tab
			var paddingTab = JsonUtil.__repeatStr(JsonUtil.t, level);
			temp.push(paddingTab);
			//写出属性名
			temp.push(name + " : ");

			var val = obj[name];
			if (val == null) {
				temp.push("null");
			} else {
				var c = val.constructor;

				if (c == Array) { //如果为集合，循环内部对象
					temp.push(JsonUtil.n + paddingTab + "[" + JsonUtil.n);
					var levelUp = level + 2; //层级+2

					var tempArrValue = []; //集合元素相关字符串缓存片段
					for (var i = 0; i < val.length; i++) {
						//递归写对象                         
						tempArrValue.push(JsonUtil.__writeObj(val[i], levelUp,
								true));
					}

					temp.push(tempArrValue.join("," + JsonUtil.n));
					temp.push(JsonUtil.n + paddingTab + "]");
				} else if (c == Function) {
					temp.push(val);
				} else {
					//递归写对象
					temp.push(JsonUtil.__writeObj(val, level + 1));
				}
			}
			//加入当前对象“属性”字符串
			currentObjStrings.push(temp.join(""));
		}
		return (level > 1 && !isInArray ? JsonUtil.n : "") //如果Json对象是内部，就要换行格式化
				+ JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "{" + JsonUtil.n //加层次Tab格式化
				+ currentObjStrings.join("," + JsonUtil.n) //串联所有属性值
				+ JsonUtil.n + JsonUtil.__repeatStr(JsonUtil.t, level - 1) + "}"; //封闭对象
	},
	__isArray : function(obj) {
		if (obj) {
			return obj.constructor == Array;
		}
		return false;
	},
	__repeatStr : function(str, times) {
		var newStr = [];
		if (times > 0) {
			for (var i = 0; i < times; i++) {
				newStr.push(str);
			}
		}
		return newStr.join("");
	}
};

/**
 * 将字符串转为json对象。
 * @param jsonStr
 * @param type  可不填写
 * @returns
 */
window.parseToJson = function(jsonStr){
	if(jsonStr === "") return ;
	return eval("("+jsonStr+")");
}

window.CacheUtil={};
/**
 * 设置缓存。
 */
CacheUtil.set=function(key,value){
	localStorage[key]=value;
}

/**
 * 获取缓存
 */
CacheUtil.get=function(key){
	return localStorage[key];
}

/**
 * 删除缓存
 */
CacheUtil.clean=function(key){
	localStorage.rmStorage(key);
}

/**
 * 设置缓存，value 为JSON对象。
 */
CacheUtil.setJSON=function(key,value){
	var json=JSON.stringify(value)
	localStorage[key]=json;
}

/**
 * 根据键获取json对象。
 */
CacheUtil.getJSON=function(key){
	var json=localStorage[key];
	if(json==undefined) return null;
	return JSON.parse(json);
}


window.CloneUtil = {
		/**
		 * 深复制【可以迭代】
		 */
		deep:function(obj){
			return jQuery.extend(true,{}, obj);
		},
		/**
		 * 浅复制【不能迭代】
		 */
		shallow:function(obj){
			return jQuery.extend({}, obj);
		},
		/**
		 * 数组复制
		 */
		list:function(obj){
			return $.map(obj, function (n) { return n; });
		}
}

/**
 * 遍历树工具
 */
window.TraverseTreeUtil = {
	/**
	 * 遍历树 node：跟节点 nodeAttrName:子节点的属性名字
	 * eg:TraverseTreeUtil.traverse(relation,"children",function(node){
	 * 		$scope.allRelations.push(node); 
	 * });
	 * 
	 */
	traverse:function(node,nodeAttrName,callback){
	    if (!node) {
	        return;
	    }
	    //处理回调
	    callback(node);
	    
	    //递归
	    if (node[nodeAttrName] && node[nodeAttrName].length > 0) {
	        for (var i = 0; i < node[nodeAttrName].length; i++) {
	        	TraverseTreeUtil.traverse(node[nodeAttrName][i],nodeAttrName,callback);
	        }
	    }
	}
}