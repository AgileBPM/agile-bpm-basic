var app = angular.module("app", [ 'base', 'baseDirective' ]);
app.controller('ctrl', [ '$scope', 'baseService', 'ArrayToolService', function($scope, baseService, ArrayToolService) {
    $scope.ArrayTool = ArrayToolService;
    $scope.config={row:2,containHideColumn:false,containOne2oneChild:true};
    
    $scope.loadColumnTree = function(){
        var callBack = function(event, treeId, treeNode) {
            console.info(treeNode);
            if(treeNode.nodeType==="table"){
                $scope.clickTable(treeNode);
            }else{
                $scope.clickColumn(treeNode);
            }
        };

        var url = __ctx + "/form/formDef/boTreeData";
        var ztreeCreator = new ZtreeCreator("boTree", url);
        ztreeCreator.setCallback({
            onClick : callBack
        });

        ztreeCreator.initZtree({boKey: $.getParam("boCode")});
    }
    
    $scope.addTabLayout = function(){
    	if(!$scope.overallArrangement.tabList){
    		$scope.overallArrangement.tabList = [];
    	}
    	$scope.overallArrangement.tabList.push({});
    }
    
    $scope.batchResetRow = function(group){
    	if(!group || !group.columnList || !this.oneColumnRowSet)return;
    	
    	for(var i=0,column;column=group.columnList[i++];){
    		column.row = this.oneColumnRowSet;
    	}
    	this.oneColumnRowSet = "";
    }
    
    $scope.add2Tab = function(tab){
    	if(!tab.groupList){tab.groupList = []}
    	tab.groupList.push(this.selectedGroup);
    	this.selectedGroup = "";
    }

    // 添加一个column，如果是一对多的子表不支持
    $scope.clickTable= function(table){
    	if(!$scope.overallArrangement.groupList){
    		$scope.overallArrangement.groupList=[];
    	}
    	
    	var group = {comment:table.comment,key:table.key};
    	if(table.relationType==='oneToOne'){
    		group.key = $scope.getOneToOneParentTableKey(table);
    	}
    	
        group.columnList = [];
        for(var i=0,item;item=table.children[i++];){
        	// 一对一子表 出现在主表中
        	if(item.nodeType ==='table' && item.relationType==='oneToOne'
        		&& $scope.config.containOne2oneChild){
        		$scope.getOne2OneChildColumns(item,group.columnList);
        	}
        	
        	if(item.nodeType!=='column') continue;
        	// 隐藏字段
        	if(item.isHidden && !$scope.config.containHideColumn){
        		continue;
        	}
        	
        	group.columnList.push({
        		comment: item.name,
        		key:item.key,
        		id:item.id,
        		tableKey:table.key,
        		row:$scope.config.row
        	})
        }

       $scope.$apply(function(){
    	   $scope.overallArrangement.groupList.push(group);
       })
       window.setTimeout(function(){
    	   var length = $scope.overallArrangement.groupList.length-1;
    	   $scope.sortableColumn($scope.overallArrangement.groupList[length],length);
       },100)
    }
    
    // 递归获取子表的字段
    $scope.getOne2OneChildColumns = function(table,columnList){
    	for(var i=0,item;item=table.children[i++];){
    		if(item.nodeType ==='table' && item.relationType==='oneToOne'){
        		$scope.getOne2OneChildColumns(item,columnList);
        	}
    		
    		if(item.isHidden && !$scope.config.containHideColumn){
        		continue;
        	}
        	
        	if(item.nodeType!=='column') continue;
        	columnList.push({
        		comment: item.name,
        		key:item.key,
        		id:item.id,
        		tableKey:table.key,
        		row:$scope.config.row
        	})
    	}
    }
    
    $scope.getOneToOneParentTableKey = function(table){
    	var parent = table.getParentNode();
    	if(parent.relationType ==='oneToOne'){
    		return  $scope.getOneToOneParentTableKey(parent);
    	}
    	return parent.key;
    }

    $scope.clickColumn = function(node){
    	if(!$scope.config.currentGroup){
    		$.Toast.error("请选择一个需要添加进入的组！<br> 点击需要加入的组");
    		return;
    	}
    	if(node.tableKey !== $scope.config.currentGroup.key){
    		$.Toast.warning("请选择与该字段相同的子表组");
    		return;
    	}
    	 for(var i=0,item;item=$scope.config.currentGroup.columnList[i++];){
    		 if(item.key === node.key){
    			 $.Toast.warning("当前组已经存在 该字段【"+node.name+"】请勿重复添加");
    			 return ;
    		 }
    	 }
    	
    	$scope.$apply(function(){
    	$scope.config.currentGroup.columnList.push({
    		comment: node.name,
    		key:node.key,
    		id:node.id,
    		row:$scope.config.row
    	});
    	})
    }

    $scope.loadColumnTree();
    
    $scope.save = function(){
    	var overallArrangement = {groupList:[],tabList:[]};
    	overallArrangement.tabList = $scope.overallArrangement.tabList;
    	
    	for(var j=0,index;index=$scope.groupSortable.toArray()[j++];){
    		var columnSort = $scope.columnSorts[index];
    		var group = $scope.overallArrangement.groupList[index];
    		var newColumnList = [];
    		for(var i=0,columnIndex;columnIndex=columnSort.toArray()[i++];){
    			newColumnList.push(group.columnList[columnIndex]);
    		}
    		group.columnList = newColumnList;
    		overallArrangement.groupList.push(group);
    	}
    	var json = {
    				"boCode":$.getParam("boCode"),
    				"overallArrangement":JSON.stringify(overallArrangement),
    			}
    	
    	var post = baseService.postForm(__ctx+"/bus/businessObject/saveOverallArrangement",json);
    	$.getResultMsg(post,function(){
    		window.location.reload();
    	})
    }
    
    
    $scope.sortableGroups = function(){
    	var el = document.getElementById('groupSortable');
		$scope.groupSortable =  Sortable.create(el, {
			handle: '.sort-handle',
			draggable: '.sortablebody',
			onUpdate:function(){
				$scope.$apply(function(){
					console.info("group sort update")
				});
			}
		});
    }
    $scope.sortableGroups();
    
    $scope.sortableColumn = function(group,index){
    	if(!$scope.columnSorts){
    		$scope.columnSorts = new Array(100);
    	}
    	
    	var el = document.getElementById('columnSortable'+index);
    	$scope.columnSorts[index] =  Sortable.create(el, {
			handle: '.sort-handle',
			draggable: '.sortablebody',
			onUpdate:function(){
				 console.info(index+"column sort update")
			}
		});
    }

    $scope.$on("afterLoadEvent", function(event, data) {
        window.setTimeout(function(){
        	 for(var i=0,g;g=$scope.overallArrangement.groupList[i++];){
        		 $scope.sortableColumn(g,i-1);
               }
        	 
        	 if($scope.overallArrangement.tabList)
        	 for(var i=0,tab;tab=$scope.overallArrangement.tabList[i++];){
        		 var groupList = [];
        		 if(tab.groupList)
        		 for(var i=0,tabGroup;tabGroup=tab.groupList[i++];){
        			 for(var i=0,g;g=$scope.overallArrangement.groupList[i++];){
                		 if(tabGroup.key === g.key){
                			 groupList.push(g);
                		 }
                       }
                   }
        		 tab.groupList = groupList;
               }
        },100)
    	
    	
    });

} ]);
