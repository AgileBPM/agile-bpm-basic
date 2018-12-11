var app = angular.module('app', [ 'baseDirective' ]);
app.controller("ListDialogCtrl", [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayTool) {
	$scope.ArrayTool = ArrayTool;
	$scope.selectedList = [];

	$scope.init = function() {
	};
	
	$scope.clear = function() {
		$scope.selectedList=[];
	};
	 $scope.getQueryParam = function(params){
		 //url param
		 var requestParam = $.getParam();
		 if(requestParam){
			 params = jQuery.extend(params, requestParam);
		 }
		// window passData param
		if (window.passData && window.passData.params) {
			params = jQuery.extend(params, window.passData.params);
		} 
		return params;
	 }
	
	$scope.gridConf = {
			url : __ctx+"/form/custDialog/getListData?dialog_alias_="+$.getParam("dialog_alias_"),
            method: 'post',                      //请求方式（*）
            contentType:'application/x-www-form-urlencoded',
            cache: false,                       
            pagination: true,                   
            sortable: true,                     
            sortOrder: "asc",                   
            queryParams: $scope.getQueryParam,
            sidePagination: "server",           
            pageNumber:1,                       
            pageSize: 10,                       
            pageList: [10, 25, 50, 100],        
            search: false,                      
            strictSearch: false,				
            minimumCountColumns: 2,             
            clickToSelect: true,                
            uniqueId: "id",                     
            showToggle:false,                   
            cardView: false,                    
            detailView: false,                  
            striped:true,						
            classes:'table table-hover',		
		};

	$scope.initGridParam = function(data) {
		$scope.customDialog = data;
		data.conditionfield = JSON.parse(data.conditionfield);
		data.displayfield = JSON.parse(data.displayfield);
		data.resultfield = JSON.parse(data.resultfield);
		data.sortfield = JSON.parse(data.sortfield);
		
		if (window.passData && window.passData.selectNum) {//处理修改多选单选参数
			data.selectNum = window.passData.selectNum;
		}
		
		if (window.passData && window.passData.initData) {//处理回显数据
			$(window.passData.initData).each(function() {
				var that = this;
				$(data.resultfield).each(function(){
					that[this["field"]]=that[this["comment"]];
				});
				pushSelectedList(that);
			});
		}
		
		if (data.needPage) {// 分页的情况
			$scope.gridConf.pagination = false;
		}
		// 列数据
		$scope.gridConf.columns = [{field:"test",checkbox:true}];
		$(data.displayfield).each(function() {// 显示字段
			$scope.gridConf.columns.push({
				title : this.comment,
				field : this.field
			});
		});

		$scope.gridConf.onCheck = function(row) {
			$scope.$apply(function() {
				$scope.activeRow = row;// 标记当前活跃row，供单选时使用
				pushSelectedList(row);
			});
		};

		 $("[ab-grid]").bootstrapTable($scope.gridConf);
		 $scope.resizeGrid();
		 $scope.handlerCollapseExpand();
	};
	
	$scope.search = function(){
		var postData = $scope.getQueryParam({});
		$($scope.customDialog.conditionfield).each(function() {
			if (this.defaultType != "1") return;
			
			var val = this.value ? this.value : "";
			postData["Q^" + this.field] = val;
		});
		$("[ab-grid]").bootstrapTable("refresh",{query:postData});
	}
	
	$scope.reset = function(){
		for(var i=0,item;item=$scope.customDialog.conditionfield[i++];){
			if (item.defaultType == "1") {
				item.value = "";
			}
		}
	}
	
	$scope.hasCondition = function(arr){
		if(!arr)return false;
		for(var i=0,item;item=arr[i++];){
			if(item.defaultType==1)return true;
		}
		return false;
	}

	$scope.resizeGrid = function(){
		var pageSize = $.getPageSize();
		var searchHeith = $(".search-panel").height()+39;
		$('[ab-grid]').bootstrapTable('resetView',{height:pageSize.height-searchHeith})
	}
	
	$scope.handlerCollapseExpand = function() {
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
				$scope.resizeGrid();
			}, 300);
		});
	}
	function pushSelectedList(row) {
		var isExist = false;
		for (var i = 0; i < $scope.selectedList.length; i++) {
			var temp = $scope.selectedList[i];
			var allEq = true;
			for ( var key in temp) {
				if (row[key]&&temp[key]&&temp[key]!=row[key]) {
					allEq = false;
					break;
				}
			}
			if (allEq) {
				isExist = true;
				break;
			}
		}

		if (isExist) return;
		$scope.selectedList.push(row);
	}
	$scope.initGridParam(window.passData.dialogConf);
} ]);