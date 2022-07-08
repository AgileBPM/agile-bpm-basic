(function($) {
	// 初始
	$.init = function() {
		$.initGrid();
		// 查询
		$.handlerSearch();
		// 重置查询
		$.handlerReSetSearch();
		// 删除选中行
		$.handlerRemoveSelect();

		$.handlerCollapseExpand();
	};
	// 查询
	$.search = function(obj) {
		if ($(obj).hasClass('disabled'))
			return;
		var param = {};
		$("input,select","#searchForm").each(function(item,i){
			if(!$(this).val())return ;
			param[$(this).attr("id")] = $(this).val();
		});
		
		$("[ab-grid]").bootstrapTable("refresh",{query:param});
	};

	// 清空查询参数 重置查询
	$.clearSearch = function() {
		var inputs = $("input,select","#searchForm");
		$(inputs).each(function(i, k) {
			k.value = "";
		});
		$.search();
	};

	/**
	 * obj：选择的范围，默认body
	 */
	$.getDatagridCheckedId = function() {
		var arrays = $("[ab-grid]").bootstrapTable('getSelections');
		var idName = $("[ab-grid]").bootstrapTable('getOptions').idField;
		var ids = [];
		arrays.forEach(function(item){
			ids.push(item[idName]);
		})
		
		return ids;
	}

	$.getUrl = function(url, param) {
		if (url.indexOf('http://') == 0 || url.indexOf(__ctx) == 0) {// 已是标准请求地址或已带项目前缀
			return url;
		}

		return __ctx + url;// 加上项目前缀
	}

	// 处理查询
	$.handlerSearch = function() {
		var $obj = $("#searchForm .btn.fa-search");
		$obj.unbind('click');
		$obj.click(function() {
			$.search(this);
		});
	};
	// 重置处理查询
	$.handlerReSetSearch = function() {
		var $obj = $("#searchForm .btn.fa-rotate-left");
		$obj.unbind('click');
		$obj.click(function() {
			$.clearSearch(this);
		});
	};


	// 删除选中行
	$.handlerRemoveSelect = function() {
		var $obj = $(".toolbar  a.btn.fa-remove");
		$obj.unbind('click');
		$obj.bind('click', function() {
			if ($(this).hasClass('disabled')) {
				return false;
			}

			var ids = $.getDatagridCheckedId();// 删除的ID数组
			var url = $(this).attr("url");// 删除请求的URL
			var tips = $(this).attr("tips") || "删除选中的数据";
			var idField = $("[ab-grid]").bootstrapTable('getOptions').idField;// 主键key
			if (ids == null || ids.length < 1) {
				$.Dialog.msg('请选择记录!');
				return false;
			}
			if (url == null || url == '') {
				$.Dialog.msg('请配置删除 URL!');
				return false;
			}
			url = $.getUrl(url);

			var param = {};
			param[idField] = ids.join(',');

			$.Dialog.confirm("温馨提示",'确认' + tips + '吗？', function() {
				$.post(url, param, function(data) {
					var result	= eval('(' + data + ')');
					if (result.isOk) {
						$.Toast.success('删除成功！', function() {
							reloadGrid();
						});
					} else {
						$.Toast.error(result.msg);
					}
				});
			});

		});
	};

	$.handlerCollapseExpand = function() {
		var $obj = $('[toggle-collapse]');
		$obj.unbind('click');
		// 收缩、展开
		$obj.bind("click", function() {
			var me = $(this);
			var el = $(me.attr("toggle-collapse"));
			if (!me.hasClass("expand")) {
				me.attr("title", "展开");
				me.removeClass("collapse").addClass("expand");
				me.removeClass("fa-angle-double-up").addClass("fa-angle-double-down");
				el.slideUp(200);
			} else {
				me.attr("title", "收缩");
				me.removeClass("expand").addClass("collapse");
				me.removeClass("fa-angle-double-down").addClass("fa-angle-double-up");
				el.slideDown(200);
			}
			// 重置
			window.setTimeout(function() {
				resizeGrid();
			}, 300);
		});
		if($obj.attr("closed")){
			$obj.click();
		}
		
	}
	$.initGrid = function(){
		
		var url = $("[ab-grid]").attr("data-url");
		url = getCtxUrl(url,true);

		$("[ab-grid]").bootstrapTable({
			url:url,
            method: 'post',                      //请求方式（*）
            contentType:'application/x-www-form-urlencoded',
            toolbar: '.toolbar',                //工具按钮用哪个容器
   //         striped: false,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",					//排序方式
            queryParams: $.getQueryParam,		//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                      //是否显示表格搜索
            strictSearch: false,				//全匹配搜索
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
        //  height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:false,                   //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            striped:true,						//隔行变色效果
            classes:'table table-hover',		//表格样式
            searchAlign:'right',
            onLoadSuccess: dataCheck
        });
		
	}
	//获取参数
	$.getQueryParam = function(params){
		$("input,select","#searchForm").each(function(item,i){
			if(!$(this).val() && params[$(this).attr("id")]){
				delete params[$(this).attr("id")];
				return;
			}
			params[$(this).attr("id")] = $(this).val();
		});
		
		return params;
	}

})(jQuery);

$(function() {
	$.init();
	rowsManager.sendAction();
	resizeGrid();
	initQtip();
	initButtonPermission();
});

function dataCheck(data){
	if(data.isOk === false && data.code === "401"){
		 jQuery.Toast.error("登录超时，请重新登录");
	}
}

function initQtip(){
	$("body").delegate("[qtip]", 'mouseover', function() {
		var qtipApi = $(this).qtip("api"); 
		var msg = $(this).attr("qtip");
		if(!qtipApi){
			var defaultSetting = {
					show: { solo: true },
					style: {classes: 'qtip-default  qtip qtip-bootstrap qtip-shadow' },//position: { my: 'center left', at: 'center right'},
					position : { my: 'bottom center', at: 'top center'},
					content : msg
			};
		
			 $(this).qtip(defaultSetting);	
			 qtipApi = $(this).qtip("api"); 
		}else{
			if(qtipApi.disabled)qtipApi.enable();
		}
		qtipApi.show();
	});
}

function initButtonPermission(){
	$("[ab-btn-rights]").each(function(){
		handelPermission(this)
	})
}
function handelPermission(target){
	var btnRightsKey = $(target).attr("ab-btn-rights");
	if(window.localStorage){
		var btnPermission = window.localStorage.getItem( 'buttonPermision' );
		btnPermission = btnPermission ? JSON.parse( btnPermission ) : {};
		 
		if( btnRightsKey && btnPermission[btnRightsKey]){
			return;
		}
		console.info(btnRightsKey +" no rights");
		$(target).hide();
	}else{
		console.info("浏览器版本太低不支持按钮权限！");
	}
}


window.reloadGrid = function() {
	if ($('[ab-grid]').length == 1) $('[ab-grid]').bootstrapTable('refresh');
}
function resizeGrid(){
	var pageSize = $.getPageSize();
	if(!pageSize.height){ return;  }
	
	var searchHeith = $(".search-panel").height()+10;
	$('[ab-grid]').bootstrapTable('resetView',{height:pageSize.height-searchHeith})
}



window.labelFmt = function(arr, value) {
	for(var i=0,item;item=arr[i++];){
		if(item.key==value){
			return '<span class="label label-'+item.css+'">'+item.val+'</span>'
		}
	}
  return '';
}


// 打开一个连接
var rowsManager = {};

rowsManager.getUrl = function(obj) {
	var url = obj.attr("url");
	if (!url)
		return "";

	if (url.substr(0, 1) == "/" && url.substr(0, 7) != "http://") {
		url = __ctx + url;
	}
	return url;
}

// 执行一个动作
rowsManager.sendAction = function() {
	$("body").delegate("[sendAction]", 'click', function() {
		var me = $(this);
		var url = rowsManager.getUrl(me);
		if(!url)alert("url不能为空！");
		
		var text = me.attr("sendAction") || me.text();
		$.Dialog.confirm("对话框", '确认' + text + '吗？', function(r) {
			if (!r)
				return;
			$.post(url, function(responseText) {
				var result	= eval('(' + responseText + ')');
				if (result.isOk) {
					$.Toast.success(text + '成功！', function() {
						reloadGrid();
					});
				} else {
					$.Toast.error(result.msg);
				}
			});

		});

	});
};


//常用的格式化
window.dateTimeFormatter=function(value, row, index) {
    if (value == null || value == ""){return "";}
    //IE 不支持  new date(“2018-11-9”);
    if(value.indexOf("-")!=-1){
    	value = value.replaceAll("-",'/');
    }
    
    return new Date(value).format("yyyy-MM-dd HH:mm:ss");
};

window.dateFormatter=function(value, row, index) {
	if (value == null || value == ""){return "";}
    if(value.indexOf("-")!=-1){
    	value = value.replaceAll("-",'/');
    }
    return new Date(value).format("yyyy-MM-dd");
}
/**
 * 在ab-grid 的colom配置中
 * @eg: data-formatter="labelFormatter" data-val-text="{'0':'禁用','1':'启用'}" data-val-style="{'0':'primary','1':'info'}"
 * @valStyle : default primary success info warning danger
 * @valText key对应的text值的配置
 * @eg: 其他形式 data-value-style="key_val_style,key2_val2_style2,"
 */
window.labelFormatter = function(value, row, index){
	if (value === null || value === "")  return "";
	var lableStyle = 'primary';
	
	/**如果是data-val-text第一种形式*/
	if(this.valText){
		try{
			var valText = eval("("+this.valText+")");
			var labelText = valText[value];
			if(labelText == undefined) labelText = '';
			
			if(this.valStyle){
				var valStyle = eval("("+this.valStyle+")");
				if(valStyle[value]){
					lableStyle = valStyle[value];
				}
			}
			return '<span class="label label-'+lableStyle+'">'+labelText+'</span>';
		}catch(e){
			console.error(e+"格式化异常！"+row);
			if(labelText){
				return labelText;
			}
		}
	}
	/** 第二种实现 data-value-style="key-val-style,key2-val2-style2,"*/
	if(this.valueStyle){
		var patt = new RegExp("(?="+value+"-).*?(?=,)","g");
		try{
		var result = patt.exec(this.valueStyle);
		if(result && result[0]){
			var vs = result[0].split("-");
			if(vs.length==3){
				lableStyle =vs[2];
			}
			return '<span class="label label-'+lableStyle+'">'+vs[1]+'</span>';
		}
		}catch(e){
			console.error(row);
			console.error("labelFormatter格式化异常:"+valueStyle,e);
		}
	}
	
	return value;
	
}

/**
 * 可以对bootstrapTable 的内容进行HTML形式的格式化，此时使用data-title作为title，innerHtml则进行参数格式化<br>
 * @提示 使用innerHtmlFormatter 必须添加 data-title属性
 * @param row
 * eg:<th data-field="value" data-formatter="innerHtmlFormatter" data-title="管理">
 *   	<a class='btn btn-outline btn-primary fa fa-edit' openDialog='编辑'qtip='编辑' url='./sysPropertiesEdit.html?id={id}'></a>
 *		<a class='btn btn-outline btn-primary fa fa-detail' openDialog='明细' qtip='明细' url='./sysPropertiesGet.html?id={id}'></a>
 *		<a class=' btn btn-outline btn-primary fa  fa-trash' sendAction='删除' qtip='删除' url='/sys/sysProperties/remove?id={id}'></a>
 *    </th>
 * 案例中会对innerHTML的{id}进行取值，数据scope为row，eg:row.id 可以用{id}取值
 */
window.innerHtmlFormatter = function(value, row, index){
	row.__ctx=__ctx;
	if(row && this.html){
		var htmlStr = this.html.format(row);
		
		var jqHtml = $("<div>"+htmlStr+"</div>");
		var ifEl = $("[if]",jqHtml);
		var btnRightEl = $("[ab-btn-rights]",jqHtml);
		
		// 权限标签
		for(var j_=0,btn_;btn_=btnRightEl[j_++];){
			handelPermission(btn_);
		}
		// if 标签
		try{
			for(var i_=0,item_;item_=ifEl[i_++];){
				var fnStr = $(item_).attr("if");
				if(!eval(fnStr)){
					$(item_).remove();
				}
			}
		}catch (e) {
			console.error(row);
			console.error("innerHtmlFormatter if 标签解析异常:"+fnStr,e);
		}
		
		return jqHtml[0].innerHTML;
	}
	return "";
}
