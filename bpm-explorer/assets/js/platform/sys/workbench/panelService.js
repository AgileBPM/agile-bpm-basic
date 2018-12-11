 
var panelApp = angular.module('workbenchApp', ['baseDirective']);
	
	panelApp.service('panelService',['baseService',function(baseService){
		return {
			loadPanelData:function(scope,refresh,fn){
				var panel = scope.panel;
				var queryParam = scope.param ||{};
				queryParam.panelId_ = panel.id;
				queryParam.dataType_ = panel.dataType;
				queryParam.dataSource_ = panel.dataSource;
				var defer = baseService.postForm( __ctx+"/sys/workbenchPanel/getPanelData",queryParam);
				
				$.getResultData(defer,function(data){
					scope[panel.alias] = data;
					if(fn)fn(data);
					
					if(refresh){ $.Toast.success("刷新成功")}
					
				})
			}
		}
	}]);

	// ==========================指令=========================
	//基础模版指令
 	panelApp.directive('workbenchBasic', [ 'panelService',function(panelService) {
		return {
			templateUrl:"../../assets/js/platform/sys/workbench/basic-templ.html",
			replace:true,
			link : function(scope,element, attr){
				scope.param = {};
				var panel = scope.panel;
				panelService.loadPanelData(scope);
				
				// name-key
				scope.loadPanelData = function(filter){
					if(filter){
						var arr = filter.split("-");
						scope.param[arr[0]] = arr[1];
					}
					panelService.loadPanelData(scope,true);
				}
			}
		};
	} ])
	//iframe 指令
	.directive('workbenchIframe', [ function() {
		return {
			replace:true,
			link : function(scope,element, attr){
				var panel = scope.panel;
				
				scope.url = getProjectUrl(panel.dataSource);
			},
			templateUrl:"../../assets/js/platform/sys/workbench/iframe-templ.html",
		};
	} ])
	 
	/**
	 * 柱状图与折线图 基于dataSet 统一数据格式的百度图表
	 * http://echarts.baidu.com/tutorial.html#使用%20dataset%20管理数据
	 */
	.directive('workbenchEcharts', ['panelService', function(panelService) {
		return {
			replace:true,
			link : function(scope,element, attr){
				var panel = scope.panel;
				var option = panel.displayContent;
				if(option){
					option = JSON.parse(option);
				}else{
					option =  {
							title: {
						        text: panel.name
						    },
						    legend: {},
						    tooltip: {},
						    toolbox: {
						        show : true,
						        feature : {
						            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    dataset: {
						        source: []
						    },
						    xAxis: {type: 'category',},
						    yAxis: {},
						    // Declare several bar series, each will be mapped
						    // to a column of dataset.source by default.
					       /* {type: 'bar',areaStyle: {},smooth:true},
					        {type: 'bar',areaStyle: {},smooth:true},
					        {type: 'bar',areaStyle: {},smooth:true},*/
						    series: []
						};
				}
				
				
				/**
				 * [
					 ['product', '产品1', '产品2', '产品3'], //产品列
					 ['2015', -12.3, 30.8, 40.7], //某区域的多个产品
					 ['2016', 22.1, 73.4, 55.1],
					 ['2017', 33.4, 65.2, 82.5],
					 ['2018', 1.4, 53.9, 39.1]
				   ]
				 */
				var myChart = null; 
				scope.relaod = function(data){
					if(option.series && option.series.length== 0){
						option.series=[];
						for(var i=1;i < data[0].length; i++){
							option.series.push({type: 'bar',areaStyle: {},smooth:true});
						}
					}
					option.dataset = {source:data}
					if(!myChart){
						myChart = echarts.init($("#echartsBar",element)[0]);
						myChart.setOption(option);
					}else{
						myChart.setOption(option,true,true);
					}
				}
				
				scope.loadPanelData = function(){
					panelService.loadPanelData(scope,true,scope.relaod);
				}
				
				panelService.loadPanelData(scope,false,scope.relaod);
			},
			templateUrl:"../../assets/js/platform/sys/workbench/echarts-templ.html",
		};
	} ])
	.directive('dynamicDirective', function($compile) {
		return {
			restrict : 'A',
			link : function(scope, element, attrs) {
				if(!attrs.dynamicDirective)return;
				
				var str = "<div "+attrs.dynamicDirective+"></div>";
				var directive = $compile(str)(scope);
				element.after(directive);
				element.remove();
			},
		};
	});