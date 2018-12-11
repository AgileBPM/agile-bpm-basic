	panelApp.controller("indexController", [ '$scope', 'baseService', function($scope, baseService) {
		
		//删除面板
		$scope.removePanel = function(layoutId,panelId){
			var defer = baseService.postForm( __ctx+"/sys/workbenchPanel/removeMyPanel",{layoutId:layoutId});
			$.getResultMsg(defer,function(data){
				for(var j=0,panel;panel=$scope.panelList[j++];){
					if(panel.id===panelId){
						$scope.panelList.splice(j-1, 1); 
					}
				}
				
				$("[data-id='"+panelId+"']").remove();
			})
		}
		
		
		$scope.openTab = function(panel){
			top.addTab({
				name:panel.name,
				defaultUrl: panel.moreUrl,
				icon: '',
				closable:true
			});
		}
		
		$scope.init = function(){
			var el = document.getElementById('sortable');
			$scope.sort =  Sortable.create(el, {
				handle: '.sort-handle',
				draggable: '.sortablebody',
				onUpdate:function(){
					$scope.$apply(function(){
						$scope.resized = true;
						$scope.idArray = $scope.sort.toArray();
					});
					console.info($scope.sort.toArray() );
				}
			});
			
			var gridWidht = $(window).width()/24;
			$( ".draggable" ).resizable({
				grid : [gridWidht,1],
				resize : function( event, ui ) {
					$scope.$apply(function(){
						$scope.resized = true;
						$scope.idArray = $scope.sort.toArray();
					});
				}
			});
		}
		
		window.setTimeout(function(){
			$scope.init();
		},500)
		$scope.panelList = [];
		var panel = window.passData;
		panel.custHeight = panel.height;
		panel.custWidth = panel.width;
		$scope.panelList.push(panel);
		
		$scope.loadPanelData = function(scope,panel){
			var queryParam = panel.param || {};
			queryParam.panelId_ = panel.id;
			if(!panel.id){
				alert("保存后才可以查看服务器数据情况");
			}
			
			queryParam.dataType_ = panel.dataType;
			queryParam.dataSource_ = panel.dataSource;
			var defer = baseService.postForm( __ctx+"/sys/workbenchPanel/getPanelData",queryParam);
			
			$.getResultData(defer,function(data){
				scope[panel.alias] = data;
			})
		}
		
		$scope.openFullWindow = function(url){
			$.openFullWindow(url);
		}
		$scope.openTab = function(url,name){
			 //TODO
		}
		
	}]);
	
