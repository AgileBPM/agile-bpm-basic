	panelApp.controller("indexController", [ '$scope', 'baseService','panelService', function($scope, baseService,panelService) {
		//保存当前panel调整
		$scope.saveMyworkbench = function(){
			var dataArray = [];
			for(var i=0,id;id=$scope.idArray[i++];){
				var draggable =  $("[data-id='"+id+"']");
				var height = draggable.outerHeight();
				var width = Math.floor(draggable.outerWidth()/$(window).width()*100);
				dataArray.push({panelId:id,sn:i-1,custWidth:width,custHeight:height});
			}
			var defer = baseService.postForm( __ctx+"/sys/workbenchPanel/saveMyPanel",{layoutList:JSON.stringify(dataArray),layoutKey:layoutKey});
			
			$.getResultData(defer,function(data){
				$.Dialog.success(data);
				$scope.resized = false;
			})
		}
		
		// 使用默认的宽高
		$scope.restore = function(){
			for(var i=0,panel;panel=$scope.panelList[i++];){
				var item = $("[data-id='"+panel.id+"']");
				panel.custHeight = panel.height;
				panel.custWidth = panel.width;
				$(".draggable",item).css("width",panel.width+"%").height(panel.height);
				$("#sortable").append(item);
			}
		}
		
		//使用默认的布局
		$scope.useDefaultLayout = function(){
			$scope.getMyWorkbench(true);
		}
		
		// 使用默认
		$scope.restoreDefault = function(){
			for(var i=0,panel;panel=$scope.panelList[i++];){
				panel.custHeight = panel.height;
				panel.custWidth = panel.width;
			}
		}
		//添加面板
		$scope.addPanel = function(){
			CustUtil.openCustDialog("getMyUsablePanels",function(data,innerWindow){
				$scope.$apply(function(){
					var exsitStr = "";
					for(var i=0,p;p=data[i++];){
						var isExsit = false;
						for(var j=0,panel;panel=$scope.panelList[j++];){
							if(panel.alias===p.alias){
								isExsit = true;
								exsitStr +=  p.name+",";
							}
						}
						if(!isExsit){
							$scope.panelList.push(p);
						}
					}
					if(exsitStr){
						$.Dialog.error(exsitStr+"已经存在！");
					}
				})
				window.setTimeout(function(){
					$scope.init();
					$scope.idArray = $scope.sort.toArray();
					console.info($scope.idArray);
				},100)
			    $.Dialog.close();
			},{layoutKey:layoutKey})
				
		}
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
		
		$scope.getMyWorkbench = function(layoutKey){
			if(layoutKey){layoutKey="default_layout"};
			var defer = baseService.postForm( __ctx+"/sys/workbenchPanel/getMyWorkbench",{layoutKey:layoutKey});
			
			$.getResultData(defer,function(data){
				$scope.panelList = data;
				if(layoutKey){
					$scope.idArray = $scope.sort.toArray();
					console.info($scope.idArray);
				}
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
		
		$scope.getMyWorkbench(layoutKey);
		window.setTimeout(function(){
			$scope.init();
		},500)
		
		$scope.openFullWindow = function(url){
			$.openFullWindow(url);
		}
		$scope.openTab = function(url,name){
			 //TODO
		}
	}]);
	
