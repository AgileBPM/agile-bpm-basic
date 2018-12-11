var app = angular.module('app',['base']);
app.controller("indexCtrl",['$scope','baseService',function(scope,baseService){
	scope.getUserMsg = function(){
		var denfer = baseService.post(__ctx+"/userResource/userMsg",{});
		denfer.then(
			function(result){
				if(!result.isOk && result.code==="401"){
					window.location = "login.html";
					return ;
				}else if(!result.isOk){
					$.Toast.error(result.msg);
				}
				
				scope.userMsg = result.data;
				scope.userRes = scope.userMsg.resourceList;
				var menuId = $.getCookie("default_menu");
				var currentMenu=null ;
				//获取cookie中的当前菜单
				if(menuId&&scope.userRes){
					for(var i=0,m;m=scope.userRes[i++];){
						if(m.id==menuId){
							currentMenu = m;
							break;
						}
					}
				}
				
				if(!currentMenu&&scope.userRes){
					currentMenu =scope.userRes[0];
				}
				scope.topClick(currentMenu);
				
			},function(aa){
				
			}
		)
	}
	scope.getUserMsg();
	
	scope.topClick = function(topMenu){
		if(!topMenu)return;
		if(topMenu.children && topMenu.children.length>0){
			//记录打开的主菜单
			$.setCookie("default_menu",topMenu.id);
			scope.memus = topMenu.children;
		}
		for(var i=0;i<scope.userRes.length;i++){
			var obj=scope.userRes[i];
			obj.active=(topMenu==obj)?"menuActive":"";
		}
		window.setTimeout(function(){
			 $('#side-menu').metisMenu();
		},10)
	}
	scope.menuClick = function(menu,noReload){
		if(!menu.defaultUrl){
			menu.opened = !menu.opened;
		}
		if(!menu.url){
			var url = menu.defaultUrl;
			if(!url) return;
			//if(url.indexOf("http")==-1)url = __ctx+url;
			menu.url = url;
		}

		var hasOpened = false;
		for(var i=0,m;m=scope.openedMenu[i++];){
			m.active = "";
			if(m==menu){
				if(!noReload)scope.relaodIfream(menu)
				hasOpened = true;
			}
		}
		menu.active = "active";
		if(!hasOpened){
			scope.openedMenu.push(menu);
		}
		if(noReload)return;
		window.setTimeout(function(){
			var $frame = $("#"+menu.id+"iframe");
			if(!$frame[0])return;
			/*var index = layer.load();
			console.info(" layer load()" + index);
			$frame.load(function (){
				console.info(" layer close()"+ index);
				layer.close(index); 
	        });*/
		},2)
	}
	
	scope.relaodIfream = function(menu){
		var $frame = $("#"+menu.id+"iframe");
		$frame[0].contentWindow.location.reload();
	}
	
	scope.closeTab = function(menu){
		var idx = 0;
		for(var i=0,m;m=scope.openedMenu[i++];){
			if(m==menu){
				idx = i;
				break;
			}
		}
		if(menu.active=="active") scope.openedMenu[idx-2].active ="active";
		
		var $frame = $("#"+menu.id+"iframe");
		try {
			$frame[0].contentWindow.document.write('');
			$frame[0].contentWindow.close();
		} catch (e) {
		}
		scope.openedMenu.splice(idx-1,1);
	} 
	
	scope.getEnviroment = function(){
		if(!scope.userMsg)return "";
		
		if(scope.userMsg.currentEnviroment==="DEV") return " | 开发";
		if(scope.userMsg.currentEnviroment==="SIT") return " | 测试";
		if(scope.userMsg.currentEnviroment==="UAT") return " | 用户测试";
		if(scope.userMsg.currentEnviroment==="GRAY") return " | 灰度";
		return "";
	}
	
	scope.logout = function(){
		var get = baseService.get(__ctx+"/logout");
		$.getResultData(get,function(){
			window.location = "login.html";
		})
	}
	scope.closeAll = function(){
		scope.openedMenu = [{id:"indexpage",active:"active",name:"首页",noclose:true,url:"sys/workbenchPanel/myWorkbench.html"}]; 
	}
	scope.cloaseOther = function(){
		var array = [{id:"indexpage",active:"active",name:"首页",noclose:true,url:"sys/workbenchPanel/myWorkbench.html"}];
		for(var i=0,item;item = scope.openedMenu[i++];){
			if(item.active === "active" && item.name !=="首页"){
				array.push(item);
			}
		}
		 scope.openedMenu = array;
	}
	
	scope.closeOthers = function(){
		var array = [{id:"indexpage",active:"active",name:"首页",noclose:true,url:"sys/workbenchPanel/myWorkbench.html"}];
		for(var i=0,item;item = scope.openedMenu[i++];){
			if(item.active === "active" && item.name !=="首页"){
				array.push(item);
			}
		}
		 scope.openedMenu = array;
	}
	
	scope.scrollCurrent = function(){
		for(var i=0,item;item = scope.openedMenu[i++];){
			if(item.active === "active"){
				scrollToTab($("#"+item.id))
				return;
			}
		}
	}
	 
	scope.openedMenu = [{id:"indexpage",active:"active",name:"首页",noclose:true,url:"sys/workbenchPanel/myWorkbench.html"}]; 
	// 左移按扭
	$('.J_tabLeft').on('click', scrollTabLeft);
	// 右移按扭
	$('.J_tabRight').on('click', scrollTabRight);
	
	
	// url特殊处理
	window.setTimeout(function(){
		$("#indexpageiframe").attr("src", "sys/workbenchPanel/myWorkbench.html");
	},10)
}])
var onlyOpenTitle = "首页";
//当这个窗口出现在iframe里，表示其目前已经timeout，需要把外面的框架窗口也重定向登录页面
if(top != window){
	  top.location= '/index.html';
}

//tab:{name:"tab名字",url:url,id:"标识"}

window.addTab = function(tab,fullTab){
	var scope =  angular.element($("[ng-controller='indexCtrl']")).scope(); 
	scope.$apply(function(){
		scope.menuClick(tab);
	});
	
	if(fullTab){
		window.setTimeout(function(){
			$(".navbar-minimalize")[0].click();
		},10)
	}
}



window.userInfo = function (){
	addTab({
		id:"userInfo",
		name:"个人信息",
		defaultUrl: '/org/user/userGet?id='+currentUserId,
		icon: 'fa-user',
		closable:true
	});
}
function editPassworld(){
	addTab({
		id:"editPassworld",
		name:"修改个人密码",
		defaultUrl: '/org/user/userPswEdit',
		icon: 'fa-key',
		closable:true
	});
}
window.editUserInfo = function(){
	addTab({
		id:"editUserInfo",
		name:"个人信息编辑",
		defaultUrl: '/org/user/userInfoEdit',
		icon: 'fa-info',
		closable:true
	});
}

function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function () {
        width += $(this).outerWidth(true);
    });
    return width;
}

//滚动到指定选项卡
function scrollToTab(element) {
    var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}

//查看左侧隐藏的选项卡
function scrollTabLeft() {
    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").width() < visibleWidth) {
        return false;
    } else {
        var tabElement = $(".J_menuTab:first");
        var offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        offsetVal = 0;
        if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).prev();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
        }
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}
//查看右侧隐藏的选项卡
function scrollTabRight() {
    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").width() < visibleWidth) {
        return false;
    } else {
        var tabElement = $(".J_menuTab:first");
        var offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        scrollVal = calSumWidth($(tabElement).prevAll());
        if (scrollVal > 0) {
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        }
    }
}