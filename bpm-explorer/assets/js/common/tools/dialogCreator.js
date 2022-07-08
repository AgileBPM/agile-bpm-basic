/**
 * 对话框适配器
 * toastr不在额外添加、插件已经很方便了
 * 更多配置参照官网配置
 * paream： content,title
 * toastr.warning()
 * toastr.success()
 * toastr.error()
 * toastr.clear()
 * layer.msg(content, options, end) 
  */
jQuery(function(){
	//初始化标签对话框打开 eg:<a openDialog="流程设计" top="true" url=""></a>
	dialogHelper__.init();
	dialogHelper__.initDialogs();
	dialogHelper__.initCustDialogs();
})
var dialogHelper__ = {};
dialogHelper__.initDialogs = function(){
	
	jQuery.extend({
		Dialog:{
			//icon:123456
		//1对号，2错，3问号，4锁，5难过脸，6笑脸，7，叹号
		alert:function(content,fn,icon){
			if (fn && !jQuery.isFunction(fn)) icon=fn;
			
			if(icon == "error"){ icon = 2; }
			if(icon == "ask"){ icon = 3; }
			if(icon == "ok"){ icon = 1; }
			if(icon == "smiley"){ icon = 1; }
			
			var conf = {skin:'layer-ext-moon',closeBtn: 0};
			if(icon)conf.icon = icon;
			
			conf.title = "提示信息"
			
			top.layer.alert(content,conf,function(index){
				if(fn && jQuery.isFunction(fn))fn();
				if(top && top.layer){
					top.layer.close(index);
				} 
				layer.close(index);
			});//http://layer.layui.com/skin.html#publish
		},
		error:function(content,fn){
			this.alert(content,fn,2);
		},
		success:function(content,fn){
			this.alert(content,fn,6);
		},
		warning:function(content,fn){
			this.alert(content,fn,7);
		},
		/**
		 * 对话框
		 * title:主题
		 * content:内容
		 * yesFn:确定回调函数
		 * cancelFn:取消回调函数
		 * conf:高级用法= =
		 */
		confirm:function(title,content,yesFn,cancelFn,conf){
			if(!conf){
				conf = {
					    btn: ['确定','取消'],//按钮
						icon:3 
					}
			}
			var tempfn = function(a){
				yesFn(a);
				layer.closeAll('dialog');
			}
			if(title){
				conf.title=title;
			}
			layer.confirm(content,conf,tempfn,cancelFn);
		},
		/**@
		 *  conf{title:头,url:访问url,height,width,data:data,topOpen:true}\
		 *  conf.btn==true/[btns123] 对应 fn conf.yes/conf.btn2,conf.btn3...
		 */
		open:function(conf){
			if(conf.btn===true){
				conf.btn = ["确定","取消"];
				if(!conf.ok)alert("确定btn 没有回调？");
			}
			var height=conf.height,
				width=conf.width,
				title=conf.title,
				url=conf.url;
			
			var openWindow = conf.topOpen ? top : window;
			
			var iframeId ; 
			if(url){
				url = dialogHelper__.getProjectUrl(url);
				 
				conf.type = 1;
				iframeId ="dialogId_"+Math.random(1000);
				var iframeHeight = conf.height > 360 ? "98%":"97%";
				var iframe ='<iframe  src="'+url+'" id="'+iframeId+'" name="'+iframeId+'" style="height:'+iframeHeight+';width:100%;border:none;"></iframe>';
				conf.content = iframe;
			}
			
			if (!conf.type) {
				jQuery.Dialog.msg( '请设置访问地址!或者指定对话框类型');
				return false;
			}
			
			//弹出一个当前窗口大小的宽高
			if(!height){
				height = jQuery(openWindow).height();
				width = jQuery(openWindow).width();
			}
			
			if(!height)height = 500;
			if(!width)width = 600;
			
			delete conf.url;
			delete conf.height;
			delete conf.width;
			 
			 // 支持 % 赋值 
			 height = height+'',width = width + '';
			 if(height.indexOf('%')== -1){
				 height = height+'px';
			 }
			 if(width.indexOf('%')== -1){
				 width = width+'px';
			 }
			
			var dialogConf = {
				   // type: 2,
				    title: title,
				    maxmin: false, 
				    closeBtn: 1,
				    shadeClose:false,
				    anim: -1 ,
				    area: [width, height],
				    content: url
				};
			if(conf.ok){
				conf.yes = function(index, layero){
					if(iframeId){
						conf.ok(index,openWindow.document.getElementById(iframeId).contentWindow);
					}else{
						conf.ok(index,layero);
					}
				}
				if(!conf.btn){
					conf.btn = ['确定', '取消'];
				}
			}
			
			jQuery.extend(dialogConf,conf);
			
			var index = openWindow.layer.open(dialogConf);
			
			// 记录index
			if(!openWindow.layerIndexRecord){openWindow.layerIndexRecord=[]}
			openWindow.layerIndexRecord.push(index);
			
			if(iframeId ){
				var contentWindow = openWindow.document.getElementById(iframeId).contentWindow;
				contentWindow.opener = window;
				if(conf.passData){
					contentWindow.passData=conf.passData;
				}
			}
			
		},
		//obj:window
		close:function(obj){
			var openner = parent;
			//若指定了当前window
			if(obj && typeof obj =="object"){
				openner =obj.parent;
			}
			//打开者
			var openner = openner.layerIndexRecord ? openner : top ;
			//若是parent打开则直接清除掉所有记录
			var index = openner.layerIndexRecord.pop();
			if(openner !==top ){
				openner.layerIndexRecord = null;
			}
			var aa = openner.layer.close(index); 
		},
		msg:function(content){
			top.layer.msg(content);
		}
		
	},
	Toast:{
		warning:function(content,title,openner){
			if(!openner){
				openner = top.toastr ? top : window;
			}
			
			openner.toastr.warning(content,title);
		},
		success:function(content,title,openner){
			if(!openner){
				openner = top.toastr ? top : window;
			}
			
			openner.toastr.success(content,title);
		},
		error:function(content,title,openner){
			if(!title)title = "错误提示！";
			
			if(!openner){
				openner = top.toastr ? top : window;
			}
			
			openner.toastr.error(content,title);
		}
		
	},
	//target eg:#id
	Tips:function(content,target){
		layer.tips(content, target);
	},
	//tab:{name:"tab名字",url:url,id:"标识"}
		Tab:function(tab,fullTab){
			top.addTab(tab,fullTab);
		}
	});

}

dialogHelper__ .init = function(){
		jQuery("body").delegate("[openDialog]",'click', function() {
			var me = jQuery(this);
			var url= dialogHelper__.getUrl(me);
			var conf = {};
			
			var dialogConf = me.attr("dialogConf");
			if(dialogConf){
				if(dialogConf.indexOf("{")==-1){
					dialogConf = "{"+dialogConf+"}";
				}
				dialogConf = eval("(" + dialogConf + ")"); 
				conf.height = dialogConf.height;
				conf.width = dialogConf.width;
			}else{
				conf.height = 0;
			}
			var text = me.attr("openDialog")|| me.text();
			conf.url = url;
			conf.title = text;
			conf.topOpen = me.attr("top");
			jQuery.Dialog.open(conf);
		});
	}
dialogHelper__ .getUrl = function(obj){
		var url = obj.attr("url");
		if(!url)return "";
		
		if(url.substr(0,7)!="http://" && url.indexOf("html")==-1){
			url = url + ".html";
		}
		
		if(url.indexOf("?")!=-1){
			url = url.format(jQuery.getParams());
		}
		
		return url;
	}
dialogHelper__.initCustDialogs = function(){
	window.CustUtil = {
		/**
		 * @key 自定义对话框的别名
		 * @callback 回调函数
		 * @param 调用动态参数
		 * @dialogSetting 强行修改key对话框的参数，用这个json数据中有的字段覆盖原有对话框的配置
		 * @initData 初始化回显的数据
		 * @closeDialog 确认后是否关闭对话框
		 */
		openCustDialog:function(key,param,callBack,initData,dialogSetting,closeDialog){
			if (jQuery.isFunction(param)) {
				dialogSetting = initData;
				initData = callBack;
				callBack = param;
				param = {}; 
			}
			
			if(!callBack){
				callBack = function(data,innerwin) {
					jQuery.Dialog.alert(JSON.stringify(data),function(){
						jQuery.Dialog.close(innerwin);
					},6);
				};
			}
			
			var conf = {
					height:600,
					width:800,  
					url: "/form/formCustDialog/formCustDialogShowList.html?key="+key,// url不为空则使用iframe类型对话框
					title:"",
					topOpen:true,
					btn:true, 
					closeBtn:1,
			};
			jQuery.post(__ctx+"/form/formCustDialog/getObject?key="+key,{},function(result){
				var dialogConf = result.data;
				if(!dialogConf){
					jQuery.Dialog.error("对话框查找不到"+key);
					return;
				}
				
				conf.height = dialogConf.height;
				conf.width = dialogConf.width;
				conf.title = dialogConf.name;
				window.CustUtil.handleParam(dialogConf,param);
				
				var passData = {
					params : param,
					initData : initData,
					dialogSetting : dialogSetting
				};
				
				conf.passData = passData;
				if(dialogConf.style==="tree"){//树形url修改
					conf.url="/form/formCustDialog/formCustDialogShowTree.html?key="+key;
				}
				
				jQuery.Dialog.open(conf); 
			},"json");
			
			conf.ok = function(index,innerWindow){
				callBack(innerWindow.getData(),innerWindow);
				if(closeDialog){
					jQuery.Dialog.close(innerWindow);
				}
			}
		},
		/**
		 * 自定义查询其实是自定义对话框的一种简略调用
		 * @key 自定义对话框的别名
		 * @callback 回调函数
		 * @param 调用动态参数
		 * @dialogSetting 强行修改key对话框的参数，用这个json数据中有的字段覆盖原有对话框的配置
		 */
		doCustQuery:function(key, param, callBack, dialogSetting){
			if (jQuery.isFunction(param)) {
				dialogSetting = callBack;
				callBack = param;
				param = {};
			}
			
			if(!param){
				param = {};
			}
			
			if (!callBack) {
				callBack = function(data) {
					jQuery.Dialog.alert(JSON.stringify(data));
				};
			}
			
			jQuery.post(__ctx + "/form/formCustDialog/getObject?key=" + key, {}, function(result) {
				var dialogConf = result.data;
				dialogConf = jQuery.extend(dialogConf, dialogSetting);
				
				//2 根据条件配置修改入参
				window.CustUtil.handleParam(dialogConf,param);
				
				//3 发请求
				jQuery.post(__ctx + "/form/formCustDialog/listData_" + key, param, function(rlt) {
					//转一下返回字段
					var datas = [];
					jQuery.each(rlt.rows, function(index,row) {
						var data = {};
						jQuery.each(dialogConf.returnFields, function(i,field) {
							data[field.returnName] = row[field.columnName];
						});
						datas.push(data);
					});
					callBack(datas);
				}, "json");
				
			}, "json");
		},
		/**
		 * 处理js传来参数结合对话框的条件处理一下
		 */
		handleParam:function(dialog,param){
			if(!param){
				return;
			}
			jQuery.each(dialog.conditionFields, function(index,field) {
				jQuery.each(param, function(key, val) {
					if (key !== field.columnName) {//找到key等于条件参数的
						return;
					}
					//帮忙key拼装配置中的查询条件，当然如果param中的key有写好的^这种写法这里是不干预的
					var id = field.columnName + "^";
					if (field.dbType === "varchar") {
						id += "V";
					}
					if (field.dbType === "number") {
						id += "N";
					}
					if (field.dbType === "date") {
						id += "D";
					}
					id += field.condition;
					param[id] = val;
				});
			});
		}
	};
}
dialogHelper__.getProjectUrl = function(url){
	//由于对话框只为前端页面打开，故此目前以/开头则自动加上前端的 projectpath，不以开头则说明相对路径。自动装载地址
	if(url && url.indexOf("http://")==-1 && url.substring(0,1)==="/"){
		 var pathname =window.document.location.pathname;
		 var projectPath = pathname.substring(0,pathname.substr(1).indexOf('/')+1);
		 // 特殊处理下流程设计器
		 if(projectPath === '' || "/bus,/bpm,/sys,/org,/form,/flow-editor,/bpmplugin".indexOf(projectPath)!= -1){
			 return url;
		 }
		if(url.startWith(projectPath)){
			return url;
		}
		 return projectPath+url;
	}
	return url;
}