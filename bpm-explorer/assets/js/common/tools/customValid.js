/**
 * anjular js 校验
 * $.fn.validRules
 * $.fn.addRule
 * 
 */
(function($) {
	$.extend($.fn, {
		// 手动扩展验证规则
		addRule : function(name,rule) {
			this.rules[name.toLowerCase()]=rule;
		},
		// ng-js 验证单个控件。
		validRules : function(value,validRule,element){
			var name = element.attr("name");
			value = value == null ? "" : value+"";
			if(typeof value == 'string')value.trim();
			// 获取json。
			var json = validRule;
			if(typeof json == 'string') {
				json = eval('(' + validRule + ')');
			}
			var isRequired = json.required;
			
			// 非必填的字段且值为空 那么直接返回成功。
			if ((isRequired == false || isRequired == undefined) && value == "")
				return true;
			
			//number：true将number取消格式化
			if(json.number){
				value =$.fn.toNumber(value)+"";
			}
			
			// 遍历json规则。
			var validates=[] ;
			for (var name in json) {
				var validValue = json[name];
				validates.push({
						ruleName:name,// 规则名称
						validValue:validValue,//验证的值
						value:value,//实际的值
						errormsgtips:element.errormsgtips,
						element:element
					}
				);
			}
			//验证规则
			for (var i=0,v;v=validates[i++];) {
				if(i==validates.length)v.isLast=true;
				var _valid = this._validRules(v);
				//如果当前规则验证不通过则直接返回false
				if(!_valid) return false;
			}
			
			//全部验证通过则返回true
			return true;
		},
		/***
		 * 将格式化数字转换成number
		 */
		toNumber: function(x){
			if(x === null || x === undefined ||  x === '') return '';
			if(typeof x == "string"){
				x = x.replace(/,/g, "");
			}
			var match = x.toString().match(/([$|￥])\d+\.?\d*/);
			if(match){
				x = x.replace(match[1],'');
			}
			var val =Number(x);
			if(Number.isNaN(val))return x;
			return val;
		},
		/**
		 * 验证规则
		 **/
		_validRules :function(conf){
			var  _valid = true,
				ruleName = conf.ruleName.toLowerCase(),// 规则名称
				validValue = conf.validValue,//验证的值
				value =conf.value,//实际的值
				element = conf.element;//当前对象
			
			
			var rule = this.rules[ruleName];
			
			if(!rule) return true;
			// 验证规则如下：
			// email:true,url:true.
			//验证规则是boolean类型
			if ($.type(validValue)  === "boolean") 
				_valid = (!rule.rule(value,validValue,element) && validValue == true) ? false:true; 	
			else 
				_valid = rule.rule(value, validValue,element);
			
			
			//错误 提示， qtip 会有
			var qtipApi = $(element).qtip("api"); 
			if (!_valid){ //验证不通过返回消息
				var errorMsg=rule.msg;
				if(conf.errormsgtips){
					var errormsgtips=eval("("+conf.errormsgtips.replaceAll("'","\"")+")")
					for(var i in errormsgtips){
						if(i==ruleName){
							errorMsg=errormsgtips[i];
							break;
						}
					}
				}
				
				var errMsg =this.format(errorMsg, validValue);
				// 在element上加上标记
				if(element.attr("desc") && errMsg){
					element.attr("error-msg",element.attr("desc") +"["+ errMsg + "]");
				}
				
				if(!qtipApi){
					var defaultSetting = {
							hide: { when: { event: 'inactive' }, delay: 2000 },
							style: {classes: 'qtip-default qtip-shadow qtip-red' },
							position: { my: 'center left', at: 'center right'},
							//position = { my: 'left top', at: 'bottom left',swap:true};左下
							content : errMsg
					};
					//如果是div,为radio，checkbox, 提示不好处理。还是鼠标跟随吧。 
					if(element.is("div")){
						defaultSetting.position = {target: 'mouse', adjust:{mouse: true},my: 'bottom left', at: 'top right'}; 
					}
				
					$(element).qtip(defaultSetting);	
				}else{
					if(qtipApi.disabled)qtipApi.enable();
					qtipApi._updateContent(errMsg);
					qtipApi.show()
				}
				return _valid;
			}
			if(conf.isLast && element.attr("desc")){
				element.removeAttr("error-msg");
			}
			
			//置为不可用，隐藏
			if(qtipApi &&!qtipApi.disabled && conf.isLast){
				qtipApi.hide();
				qtipApi.destroy();
				//qtipApi.disable();
			}
			
			return _valid;
		},
		/**
		 * 消息格式化
		 **/
		format:function(msg,args){
			//boolean类型的直接返回
			if ($.type(args)  === "boolean") 
				return  msg;
			if(typeof args =='object' && args.target){
				args = args.targetVal;
			}
			if (!$.isArray(args)) //不是数组类型的
				args = [args];
			//数组类型的
			$.each(args,function(d, e) {
				msg = msg.replace(RegExp("\\{" + d + "\\}", "g"), e)
			});
			return msg;		
		},

		// 内置的规则。
		rules : {
			"required":{
						rule : function(v) {
							if (v == "" || v.length == 0)
								return false;
							return true;
						},
						title : "必填",
						formRule : true,
						msg : "必填"
					  },
			"number":{
					rule : function(v) {
						return /^-?((\d{1,3}(,\d{3})+?|\d+)(\.\d{1,5})?)$/
								.test(v.trim());
					},
					title : "数字",
					formRule : true,
					msg : "只能输入数字"
				},
			"variable":{
					rule : function(v) {
						return /^[A-Za-z_]*$/gi.test(v.trim());
					},
					title : "字母或下划线",
					formRule : true,
					msg : "只能是字母和下划线"
				},
			"fields":{
					rule : function(v){
						return /^[A-Za-z]{1}([a-zA-Z0-9_]{1,17})?$/gi.test(v.trim());
					},
					msg : "首字符为字母,最大长度18"
				},
			"minlength":{
					rule : function(v, b) {
						return (v.length >= b);
					},
					msg : "长度不少于{0}"
				}, 
			"maxlength":{
					rule : function(v, b) {
						return (v.trim().length <= b);
					},
					msg : "长度不超过{0}"
				},
			"rangelength":{
					rule : function(v, args) {
						return (v.trim().length >= args[0] && v.trim().length <= args[1]);
					},
					msg : "长度必须在{0}之{1}间"
				},
			"email":{
					rule : function(v) {
						return /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i
								.test(v.trim());
					},
					title : "email",
					formRule : true,
					msg : "请输入一合法的邮箱地址"
				},
			"url":{
					rule : function(v) {
						return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
								.test(v.trim());
					},
					title : "url",
					formRule : true,
					msg : "请输入一合法的网址"
				},
			 "date":{
					rule : function(v) {
						var re = /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))/g
								.test(v.trim());
						return re;
					},
					title : "日期",
					formRule : true,
					msg : "请输入日期格式"
				},
			"time":{
					rule : function(v) {
						var re = /^(?:[01]?\d|2[0-3])(?::[0-5]?\d){2}$/g
								.test(v.trim());
						return re;
					},
					title : "时间",
					formRule : true,
					msg : "请输入一合法的时间"
				},
			 "chinese":{
					rule : function(v) {
						var re = /[\u4E00-\u9FA5\uF900-\uFA2D]/
								.test(v.trim());
						return re;
					},
					title : "汉字",
					formRule : true,
					msg : "请输中文字符"
				},
			"qq":{
				rule : function(v) {
					return /^[1-9]*[1-9][0-9]*$/i.test(v.trim());
				},
				title : "QQ号",
				formRule : true,
				msg : "请输入正确的QQ号码"
				},
			"phonenumber":{
				rule : function(v) {
					if(v.length>11) return false;
					return /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/i.test(v.trim());
				},
				title : "手机号码",
				formRule : true,
				msg : "请输入正确的手机号码"
				},
			"digits":{
					rule : function(v) {
						return /^\d+$/.test(v.trim());
					},
					title : "整数",
					formRule : true,
					msg : "请输入整数"
				},
			"equalto":{
					rule : function(v, b) {
						var a = $("#" + b).val();
						return (v.trim() == a.trim());
					},
					msg : "两次输入不等"
				}, 
			"range":{
					rule : function(v, args) {
						return v <= args[1] && v >= args[0];
					},
					msg : "请输入在{0}到{1}范围之内的数字"
				},
			"maxvalue":{
					rule : function(v, max) {
						return $.fn.toNumber(v) <= $.fn.toNumber(max); 
					},
					msg : "输入的值不能大于{0}"
				},
			"minvalue":{
					rule : function(v, min) {
						return $.fn.toNumber(v) >= $.fn.toNumber(min); 
					},
					msg :"输入的值不能小于{0}"
				},
			"maxintlen":{
					// 判断数字整数位
					rule : function(v, b) {
						return (v + '').split(".")[0].replace("/,/g","").length <= b;
					},
					msg : "整数位最大长度为{0}"
				}, 
			"maxdecimallen":{
					// 判断数字小数位
					rule : function(v, b) {
						return (v + '').replace(/^[^.]*[.]*/, '').length <= b;
					},
					msg : "小数位最大长度为{0}"
				},
			"daterangestart":{
					rule : function(v, b,e) {
						return daysBetween(b.targetVal, v); 
					},
					msg : "日期必须大于或等于{0}"
				}, 
			"daterangeend":{
					rule : function(v,b,e) {
						return daysBetween(v, b.targetVal);
					},
					msg : "日期必须小于或等于{0}"
				},
			"empty":{
					// 空的字段（永远通过验证,返回true）  防止在验证JSON中出现有多余的逗号
					rule : function(v, b) {
					//	var start = $("#" + b).val();
						return true;
					},
					msg : ""
				},
			"nodigitsstart":{
					// 不能以数字开头
					rule : function(v) {
						return !/^(\d+)(.*)$/.test(v.trim());
					},
					title : "不以数字开头",
					formRule : true,
					msg : "不能以数字开头"
				},
			"varirule":{
					name : "varirule",
					rule : function(v) {
						return /^[a-zA-Z]\w*$/.test(v.trim());
					},
					title : "以字母开头",
					formRule : true,
					msg : "只能为字母开头,允许字母、数字和下划线"
				},
			/**
			 * eg:<input type="text" ng-model="data.name" ht-validate="{isexist:'bOEnt/isExist'}"/>
			 * isexist:是请求地址的参数，需要自己实现，要验证的数据参数是‘key’；
			 * 返回true:代表已存在，不可用；返回false:不存在，可用
			 */
			"isexist":{
					name : "isexist",
					rule : function(v,url) {
						if(!v) return true;
						var b;
						if(url.indexOf("?")<1){
							url+="?";
						}
						$.ajax({
							url : url+"&key="+v,
							type : 'POST',
							dataType : 'json',
							async : false,
							success : function(result) {
								b=!result;
							}
						});
						return b;
					},
					msg : "数据已被使用,请重新填写"
				}	
		} 
	});

})(jQuery);
						
function daysBetween(DateOne,DateTwo)
{
	 var date1 =  new Date(DateOne).getTime();
	 var date2 = new Date(DateTwo).getTime();;
       if(date1>date2){
           return false;
       }else{
           return true;
       }
};
