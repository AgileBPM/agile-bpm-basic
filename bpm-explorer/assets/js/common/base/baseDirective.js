/**
 * 页面表单通用指令。
 */
var directive = angular.module("baseDirective", [ "base" ])
/**
 * 校验指令. 用法： <input type="text" ng-model="user.name"
 * ab-validate="{require:true}" />
 * 
 * 具体的规则： /js/common/CustomValid.js 的rules 内置规则。
 */
.directive('abValidate', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			var validate = attr.abValidate;

			var  validateJson = eval('(' + validate + ')');
			if(!ctrl.validateJson){
				ctrl.validateJson = validateJson;
			}else{
				$.extend(ctrl.validateJson,validateJson);
			}
			

			var customValidator = function(value) {
				if (!validate)
					return true;
				handlTargetValue(validateJson);
				var validity = jQuery.fn.validRules(value, ctrl.validateJson, element);
				ctrl.$setValidity("customValidate", validity);
				return validity ? value : undefined;
			};

			ctrl.$formatters.push(customValidator);
			ctrl.$parsers.push(customValidator);

			// 获取比较目标字段的值。 所有比较的都包含target对象eg:{eq:{target:data.mian.name}}
			var handlTargetValue = function(validateJson) {
				for (key in validateJson) {
					if (validateJson[key].target) {
						validateJson[key].targetVal = eval("scope." + dateRange.target);
					}
				}
			}
		}
	};
} ])
/**
 * 表单的常用保存指令，看例子说话： <input type="button" ng-model="data" ab-save="bOEnt/save"/>
 * 
 * ps:<form name ="form">元素必须是 name ="form"
 * 
 * 参数介绍： ng-model :代表保存对象 ab-save :是保存的url地址
 * 
 * 后台controller： 可以参照 BoEntController.save方法
 * 
 * 页面controller(ngjs的控制层): 我们可以捕获保存后抛出的事件进行个性化操作（也可以不捕获） eg:
 * $scope.$on("afterSaveEvent",function(event,data){
 * console.info("我捕获了afterSaveEvent事件"); console.info(data); });
 * data.r是选择的"是"true 和 "否"false beforeSaveEvent一样
 */
.directive('abSave', [ 'baseService', function(baseService) {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			var useDefaultAfterSaveEvent = attr.useDefaultAfterSaveEvent;
			element.on("click", function() {
				if (!scope.form.$valid)
					return;
				var method = attr.method || "post";

				var configObj = {};
				// 读取配置。
				var config = attr.config;
				if (config) {
					configObj = angular.fromJson(config);
				}

				var data = {};// 数据
				data.pass = true;// 前置事件用来控制能否提交的参数
				scope.$root.$broadcast('beforeSaveEvent', data);
				// 表单验证
				if (!data.pass)
					return;
				var url = getCtxUrl(attr.abSave,true);

				var postData = scope[attr.ngModel];

				var rtn = baseService[method](url, postData);
				rtn.then(function(data) {
					if (data.isOk) {
						data.postData = postData;
						// 如果设置了配置。
						if (configObj.afterSave) {
							eval("scope." + configObj.afterSave + "(data)");
							return;
						}

						// 尝试刷新父页面
						try {
							if(parent.reloadGrid){parent.reloadGrid()};
						} catch (e) {
						}

						jQuery.Dialog.confirm(data.title ? data.title : "操作成功", data.msg + ",是否继续操作", function() {
							// 发布保存事件用于给用户自定义操作
							if(useDefaultAfterSaveEvent){
								window.location.reload();
							}else{
								data.r = true;
								scope.$root.$broadcast('afterSaveEvent', data);
							}
						}, function() {
							if(useDefaultAfterSaveEvent){
								$.Dialog.close(window);
							}else{
								data.r = false;
								scope.$root.$broadcast('afterSaveEvent', data);
							}
						});
					} else {
						jQuery.Toast.error(data.msg, data.cause);
					}
				}, function(errorCode) {
					jQuery.Toast.error("请求失败!" + errorCode);
				});
			});
		}
	};
} ])
/**
 * 表单的常用初始化数据指令，看例子说话： <form name="form"
 * ab-load="bOEnt/getObject?id=${param.id}" ng-model="data"></form>
 * ps:当初始化对象为空时不作任何操作的 参数介绍： ab-load ：能返回一个对象的请求后台地址 ng-model :把获取的对象赋值到该对象
 * 
 * 后台controller： 可以参照 BoEntController.getObject方法
 * 
 * 页面controller(ngjs的控制层): 我们可以捕获初始化数据后抛出的事件进行个性化操作（也可以不捕获）eg:
 * $scope.$on("afterLoadEvent",function(event,data){
 * console.info("我捕获了afterLoadEvent事件"); console.info(data); });
 */
.directive('abLoad', [ 'baseService', function(baseService) {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			if (!attr.abLoad || attr.abLoad == "") {
				return;
			}
			var rtn = baseService.post(getCtxUrl(attr.abLoad, true));
			// 简单数据、指不含result的数据
			if (attr.simpleData) {
				rtn.then(function(data, status) {
					if(""==attr.fastjson){//是否需要fastjson
						FastJson.format(data);
					}
					if (!data)
						return;
					scope[attr.ngModel] = data;
					scope.$root.$broadcast('afterLoadEvent', data);// 发布加载事件用于给用户自定义操作
				}, function(status) {
					$.Toast.error("请求失败");
				});
				return;
			}
			rtn.then(function(result, status) {
				if(""==attr.fastjson){//是否需要fastjson
					FastJson.format(result);
				}
				if (result.isOk) {
					if (!result.data) {
						return;
					}
					scope[attr.ngModel] = result.data;
					scope.$root.$broadcast('afterLoadEvent', result.data);// 发布加载事件用于给用户自定义操作
				} else {
					jQuery.Toast.error(result.msg, result.cause);
				}
			}, function(status) {
				$.Toast.error("请求失败");
			});
		}
	};
} ])
/**
 * 数据源下拉框选择， eg <div ab-ds-selector="data.dsName"></div> 会把选择的别名返回到指定对象中
 */
.directive('abDsSelector', [ 'baseService', function(baseService) {
	return {
		scope : {
			abDsSelector : "=",
			ngDisabled : "="
		},
		link : function(scope, element, attr, ctrl) {
			var rtn = baseService.post(__ctx + '/system/sysDataSource/getDataSourcesInBean');
			rtn.then(function(data) {
				scope.dataSourcesInBean = data;
			}, function(status) {
				$.topCall.error("请求失败");
			});
		},
		template : '<select class="inputText" ng-model="abDsSelector" ' + ' ng-options="m.alias as m.name for m in dataSourcesInBean"></select>',
		replace : true
	};
} ])
/**
 * ab-select-ajax 动态加载select的options数据 例如： <select ng-model="form.typeId"
 * ng-options="(m.id) as m.text for m in formTypeList"
 * ab-select-ajax="{url:'${ctx}/platform/system/sysType/getByGroupKey?groupKey=FORM_TYPE',field:'formTypeList'}">
 * <option value="">请选择</option> </select> 传入参数 url ： 请求地址 field ： formTypeList
 * 对应于 ng-options 中的 formTypeList （两者必须相同）
 */
.directive('abSelectAjax', function($injector) {
	return {
		restrict : 'A',
		require : '?ngModel',
		link : function(scope, element, attrs, ctrl) {
			var baseService = $injector.get("baseService");
			var option = attrs["abSelectAjax"];
			option = eval("(" + option + ")");
			if (scope.$root.$$childHead[option.field])
				return;
			var def = baseService.get(option.url);
			def.then(function(data) {
				if (option.dataRoot) {
					data = data[option.dataRoot];
				}
				scope[option.field] = data;
				scope.$root.$$childHead[option.field] = scope[option.field];
				// select option 生成后，让控件从新更新视图
				window.setTimeout(function() {
					ctrl.$render();
				}, 10)
			}, function() {
			});
		}
	};
})
/**
 * 汉字转拼音，例如 A 填写了 你好，当A失去焦点时，B自动填充为nh fullpinyin:1 全拼，不填0默认首字母 eg: <input
 * type="text" ng-model="chinese" value=汉字/> <input type="text"
 * ng-model="pingyin" ab-pinyin="chinese" type="0" fullpinyin="1"/>
 */
.directive('abPinyin', [ 'baseService', function(baseService) {
	return {
		restrict : 'A',
		require : "ngModel",
		link : function(scope, elm, attrs,ctrl) {
			var type = attrs.fullpinyin || 0;

			// 利用jq方法绑定失去焦点事件
			jQuery("[ng-model='" + attrs.abPinyin + "']", elm.parent().closest(".ng-scope")).blur(function() {
				if (elm.val())
					return;

				var obj = jQuery(this);

				var value = obj.val();
				if (!value)
					return;

				var param = {
					chinese : value,
					type : type
				};
				var rtn = baseService.postForm(__ctx + "/sys/tools/pinyin", param);
				rtn.then(function(data) {
					scope.ngModel = data;
					elm.val(data);
					// 延迟触发blur,ngModel 还未将值设置进input
					window.setTimeout(function() {
						elm.trigger("change");
					}, 500);
				}, function(errorCode) {
				});
			});
		}
	};
} ])
/**
 * 富文本框指令： <ab-editor config="editorConfig" ng-model="content" height="100"></ab-editor>
 * ng-model：scope 数据表达式 config:编辑器配置 height:文本框高度
 * 
 * 使用方法：
 * 
 * <body ng-controller="ctrl"> <div config="editorConfig" ab-editor
 * ng-model="content1" style="width: 80%">测试</div> <script>
 * angular.module('example',['formDirective']).controller('ctrl', function
 * ($scope,$sce) { $scope.content1="hello world"; }); </script> </body>
 */
.directive('abEditor', function() {
	return {
		restrict : 'AE',
		transclude : true,
		template : '',
		require : '?ngModel',
		scope : {
			config : '=',
			ngModel : "="
		},
		link : function(scope, element, attrs, ngModel) {
			var editor = new UE.ui.Editor(scope.config || {
				focus : true,
				toolbars : [ [ 'source', 'undo', 'redo', 'bold', 'italic', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist' ] ],
				initialFrameHeight : attrs.height || 150,
				autoClearEmptyNode : true,
				enableAutoSave: false
			});
			editor.render(element[0]);
			// 百度UEditor数据更新时，更新Model
			editor.addListener('contentChange', function() {
				setTimeout(function() {
					scope.$apply(function() {
						ngModel.$setViewValue(editor.getContent());
					});
				}, 0);
			});
			
			/**
			 * 加载指令时数据已经有了，那就要触发editor的加载后方法
			 * 这里有一个很坑的时机点的：1指令加载 2 ngmodel数据加载 3 ueditor加载
			 */
			var isEditorReady = false;
			editor.ready(function() {
				isEditorReady = true;
				if(ngModel.$viewValue){
					editor.setContent(ngModel.$viewValue);
				}
			});
			
			// Model数据更新时，更新百度UEditor
			ngModel.$render = function() {
				if(ngModel.$viewValue&&isEditorReady){
					editor.setContent(ngModel.$viewValue);
				}
			};
		}
	}
})
/**
 * 功能说明: abCheckbox 指令用于收集checkbox数据。 在页面中使用 属性指令：ab-checkbox
 * 对应的值为scope对应的数据data.users。 示例: <div > <input type="checkbox" ab-checkbox
 * ng-model="data.users" value="1" />红 <input type="checkbox" ab-checkbox
 * ng-model="data.users" value="2" />绿 <input type="checkbox" ab-checkbox
 * ng-model="data.users" value="3" />蓝 <span>{{data.users}}</span> </div>
 * <script> var app=angular.module("app",["directive"]); app.controller('ctrl',
 * ['$scope',function($scope){ $scope.data={users:"1,2"};
 * $scope.getData=function(){ console.info($scope.data.users) } }]) </script>
 */
.directive('abCheckbox', [ function() {
	return {
		restrict : 'A',
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			var checkValue = attrs.value;

			// modelValue转viewValue的过程
			ctrl.$formatters.push(function(value) {
				if (!value)
					return false;

				var valueArr = value.split(",");
				if (valueArr.indexOf(checkValue) == -1)
					return false;

				return true;
			});

			// viewValue转modelValue的过程
			ctrl.$parsers.push(function(value) {
				var valueArr = [];
				if (ctrl.$modelValue) {
					valueArr = ctrl.$modelValue.split(",");
				}
				var index = valueArr.indexOf(checkValue);
				if (value) {
					// 如果checked modelValue 不含当前value
					if (index == -1)
						valueArr.push(checkValue);
				} else {
					if (index != -1)
						valueArr.splice(index, 1);
				}
				
				return valueArr.join(",");
			});
		}
	}
} ])
.directive('abArrayStr', [ function() {
	return {
		restrict : 'A',
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {

			// modelValue转viewValue的过程
			ctrl.$formatters.push(function(value) {
				if (!value) return [];
				return value.split(",");
			});

			// viewValue转modelValue的过程
			ctrl.$parsers.push(function(value) {
				if(value && value.length>0){
					return value.join(","); 
				}
				return "";
			});
		}
	}
} ])
/**
 * 在对象上显示提示框。 使用方法: <div class="flowNode"
 * ab-tip="{content:getMenuContent,alignX:'center',alignY:'bottom',offsetY:10}" ></div>
 */
.directive('abTip', function($injector) {
	return {
		restrict : 'A',
		scope : {
			abTip : "="
		},
		link : function(scope, element, attrs) {
			var defaultSetting = {
				hide : {
					event : 'mouseleave',
					leave : false,
					fixed : true,
					delay : 100
				},
				style : {
					classes : 'qtip-default  qtip qtip-bootstrap qtip-shadow'
				}
			};
			var setting = angular.extend(scope.abTip || {}, defaultSetting);
			element.qtip(setting);
		}
	};
})

/**
 * 列表全选指令，这个指令用于 ng-repeat 列表全选，反选。 指令的用法： 在列表项checkbox控件上增加指令
 * ab-checked="selectAll",属性值为全选checkbox的 ng-model属性。 eg: <input type="checkbox"
 * ng-model="selectAll"/>
 * 
 * <tr  ng-repeat="item in data.defNameJson  track by $index">
 * <td> <input ng-model="item.selected" type="checkbox" ab-checked="selectAll">
 * </td>
 * </tr>
 * 
 */
.directive('abChecked', function() {
	return {
		restrict : 'A',
		require : "ngModel",
		scope : {
			ngModel : "="
		},
		link : function(scope, elm, attrs, ctrl) {
			scope.$parent.$watch(attrs.abChecked, function(newValue, oldValue) {
				if (newValue == undefined)
					return;
				ctrl.$setViewValue(newValue);
				ctrl.$render();
			});
		}
	};
})
/**
 * eg: <span return-data-key="" showLevel=2 url="请求树结构数据的地址"
 * ztreeDataKey="{idKey:"id",pIdKey:"parentId",name:"name",title:"name" >
 * ztreeDataKey : 若数据格式与默认值一致可以不用设置改参数 chkboxType : true 或者 { "Y": "p", "N": "s" }
 * callback
 * :{beforeClick：beforeClick,onClick:onClick,beforeCheck:beforeCheck,loadSuccess}
 * 若设置callback则忽略ng-model赋值以及name-value赋值
 */
.directive('abZTree', [ 'baseService', function(baseService) {
	return {
		restrict : 'A',
		scope : {
			abTree : "=",
			callback : "="
		},
		link : function(scope, element, attrs) {

			var ztreeDataKey = attrs.ztreeDataKey || {
				idKey : "id",
				pIdKey : "parentId",
				name : "name"
			};
			var url = __ctx + attrs.url;

			scope.treeId = parseInt(Math.random() * 1000);

			scope.loadOrgTree = function() {
				new ZtreeCreator(scope.treeId, url).setDataKey(ztreeDataKey).setCallback(scope.callback).setChildKey().initZtree({}, attrs.showLevel || 1, function(treeObj, treeId) {
					if (scope.callback.loadSuccess) {
						scope.callback.loadSuccess(treeObj, treeId);
					}
				});
			}
			scope.loadOrgTree();
		}
	};
} ])
/**
 * 日期控件。 控件用法： <input type="text" ab-date="yyyy-MM-dd HH:mm:00" ng-model="date1" />
 * <input type="text" ab-date ng-model="date1" /> 需要增加：ab-date 指令。
 * ab-date="yyyy-MM-dd HH:mm:ss" 属性为日期格式。
 */
.directive('abDate', function() {
	return {
		restrict : 'A',
		require : "ngModel",
		link : function(scope, element, attrs, $ctrl) {
			element.addClass("dateformat");
			var format = attrs.abDate || "yyyy-MM-dd";
			var type = "date"
			if(format.indexOf("HH:mm:ss") != -1){
				type = "datetime";
			}
		 
			laydate.render({ 
				  elem: element[0]
				  ,calendar: true
				  ,format: format
				  ,type:type
				  ,done: function(value, date, endDate){
					  $ctrl.$setViewValue(value)
					  // console.log(value);得到日期生成的值，如：2017-08-18
					  //console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
					  //console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
				  }
			});
			
			$ctrl.$formatters.push(function(value) {
				if(value&&typeof value =="number"){//数字就格式化
					return new Date(value).format(format);
				}
				return value;
			});
			
			return;
		}
	};
})
/**
 * @ab-boolean 可为空， 格式以“/”分割truevalue 和falsevalue
 * @text 按钮名字 （可以通过/分割则为trueName/falseName） <span ab-boolean="Y/N"
 *       class="checkbox disabled" text="测试按钮" ng-model="prop.skipFirstNode"
 *       ab-tip title="我是按钮的提示"></span>
 */
.directive('abBoolean', function() {
	return {
		restrict : 'A',
		require : 'ngModel',
		scope : true,
		link : function(scope, element, attrs, ctrl) {
			var booleanConf = attrs.abBoolean;
			var textArr = attrs.text.split("/");
			scope.trueText = textArr[0];
			scope.falseText = textArr.length == 2 ? textArr[1] : textArr[0];
			if (booleanConf) {
				booleanConf = booleanConf.split("/");
			} else {
				booleanConf = [ true, false ];
			}

			var trueValue = booleanConf[0], falseValue = booleanConf[1];

			ctrl.$formatters.push(function(value) {
				if (typeof (value) == 'number')
					value = "" + value;
				return value === trueValue;
			});

			ctrl.$parsers.push(function(value) {
				return value ? trueValue : falseValue;
			});

			ctrl.$render = function() {
				scope.checked = ctrl.$viewValue;
			};

			element.on('click', function() {
				if (jQuery(this).attr("disabled"))
					return;
				scope.$apply(function() {
					scope.checked = !scope.checked;
					ctrl.$setViewValue(scope.checked);
				});
			});
		},
		template : '<lable class="btn btn-sm {{checked ? \'btn-success\':\'disabled\'}}" >{{checked ?trueText:falseText}}</lable>'
	};
})
/**
 * 数据字典指令。 dictype：数据字典别名。 数据字典用法：<span ab-combox="节点id映射的值" tree-key="树key"
 * node-key="节点key"
 * value-name="对name进行赋值",value-key="对key进行赋值"...value-形式的都是对某某赋值> 特殊属性combox用法：<span
 * ab-combox="data.sex" name-value="" url="请求数据格式" ztreeDataKey="{字段设置}"  dictKey="">
 */
.directive('abCombox', function($injector) {
	return {
		restrict : 'A',
		require : "?ngModel",
		scope : {
			abCombox : '='
		},
		link : function(scope, element, attrs,ctrl) {
			//指令中的映射关系 {对话框返回字段:赋值的对象}
			var valueMap = {};
			var watchAttr;//key value模式中的监听对象，这里随便找一个监听就行
			angular.forEach(attrs, function(val, key) {
				// 找到value开头的赋值配置
				if (key.indexOf("value") !== 0) {
					return;
				}
				var str = key.replace("value", "");
				var name = str.substring(0, 1).toLowerCase();
				name =name + str.substring(1, key.length);
				valueMap[name] = val;
				watchAttr = val;
			});
			
			var ztreeDataKey = attrs.ztreeDataKey || {
				idKey : "id",
				pIdKey : "parentId",
				name : "name"
			};
			
			//对于他们只不过是url的不同而已
			
			//=========自定义url支持============
			var url = __ctx + attrs.url;

			//==========SYS TREE 的支持=============
			var treeKey = attrs.treeKey; // 树key
			var nodeKey = attrs.nodeKey; // 节点key
			if (treeKey) {
				// 若配置是系统树
				url = __ctx + "/sys/sysTreeNode/getNodes?treeKey=" + treeKey + "&nodeKey=" + (nodeKey ? nodeKey : "");
			}
			//============数据字典支持============
			if(attrs.dictKey){
				url = __ctx + "/sys/dataDict/getDictData?dictKey="+attrs.dictKey;
			}

			scope.treeId = "tree_" + GetRandomStr(6);
			scope.dicData = {};// 模板html中展示的对象
			var ztreeCreator = new ZtreeCreator(scope.treeId, url);
			scope.treeClick = function(event, treeId, treeNode) {
				if(treeNode.noclick)return;
				angular.forEach(valueMap, function(val, key) {
					eval("scope.$parent." + val + " = treeNode[key]");
				});
				scope.dicData.value = treeNode[ztreeDataKey.name];// name肯定用来展示
				if(attrs.abCombox){
					//其他场景请不要按照此方法添加更多逻辑。请将abCombox = "",然后用 value-keyName的形式赋值！
					if(attrs.dictKey){
						scope.abCombox = treeNode.key;
					}else{
						scope.abCombox = treeNode[ztreeDataKey.idKey];// idKey默认是赋值对象
					}
					
				}
				ztreeCreator.hideMenu();// 隐藏菜单

				!scope.$parent.$$phase && scope.$parent.$digest();
			}

			scope.loadTree = function() {
				ztreeCreator.setDataKey(ztreeDataKey).setCallback({
					onClick : scope.treeClick
				}).makeCombTree(scope.treeId + "input").initZtree(function(_treeObj) {
					scope.treeObj = _treeObj;
					scope.initData();
				});
			};

			// 初始化数据
			var hasInited = false;
			scope.initData = function(id) {
				if (!scope.treeObj) {
					return;
				}
				hasInited = true;
				
				var nodes = scope.treeObj.getNodesByFilter(function(node) {
					if(attrs.abCombox){//如果配置了Id的初始化逻辑
						if(attrs.dictKey && node.key === scope.abCombox){
							return true;
						}
						
						if (node[ztreeDataKey.idKey] === scope.abCombox) {
							return true;
						}
						return false;
					}else{//value key 模式下的获取初始化node逻辑
						var eql = true;
						angular.forEach(valueMap, function(val, key) {
							eval("var data = scope.$parent." + val );
							if(data!=node[key]){
								eql = false
							}
						});
						return eql;
					}
				});
				if (nodes.length > 0) {
					scope.dicData.value = nodes[0][ztreeDataKey.name];
					if(ctrl){ctrl.$viewValue = nodes[0][ztreeDataKey.name] }
					!scope.$parent.$$phase && scope.$parent.$digest();
				}
			}

			window.setTimeout(function() {
				scope.loadTree();
				
				//开始监听初始化事件
				if(attrs.abCombox){//如果配置了Id则监听这个
					scope.$watch("abCombox", function(newValue, oldValue) {
						if (newValue !== oldValue || !hasInited) {
							scope.initData();
						}
					});
				}else{//这是要监听value key这种赋值模式
					scope.$watch("$parent."+watchAttr, function(newValue, oldValue) {
						if (newValue !== oldValue || !hasInited) {
							scope.initData();
						}
					});
				}
				
			}, 10)
		},
		template : '<div style="width: 100%">\
					<input ng-model="dicData.value" id="{{treeId}}input" class="form-control" readonly="readonly" type="text"  placeholder="点击选择" >\
				</div>'
	};
})
/**
 * <pre>
 * 自定义对话框
 * ab-cust-dialog=对话框key
 * value-对话框的返回数据中的某个字段key:绑定到某个scope值中
 * ng-model:如果有这个值（必须是数组），则会返回值拼装成json然后push到ng-model中，对于ngModel只增不减
 * TODO value-这种风格有个致命缺陷，就是标签属性中不能存在大小写 例如value-aBc 所以现在返回值都是忽略大小写映射的。以后出问题再整体改一下
 * eg:
 * <a class="btn btn-sm btn-primary fa-search" href="javascript:void(0)" ab-cust-dialog="busObjectSelect" value-key="data.boKey" value-name="data.boName">选择</a>
 * </pre>
 */
.directive('abCustDialog', function($injector) {
	return {
		restrict : 'A',
		scope : {
			ngModel : "="
		},
		link : function(scope, element, attrs ) {
			//指令中的映射关系 {对话框返回字段:赋值的对象}
			var valueMap = {};
			angular.forEach(attrs, function(val, key) {
				// 找到value开头的赋值配置
				if (key.indexOf("value") !== 0) {
					return;
				}
				var str = key.replace("value", "");
				var name = str.substring(0, 1).toLowerCase();
				name =name + str.substring(1, key.length);
				valueMap[name] = val;
			});
			
			
			$(element).on("click",function(){
				if(attrs.ngModel&&!scope.ngModel){//帮其初始化
					scope.ngModel = [];
				}
				
				var initData = [];
				if(!attrs.ngModel){
					initData = handleInitData();
				}else{
					initData = handleNgModelInitData();
				}
				
				var param = attrs.param?parseToJson(attrs.param):null;
				var dialogSetting = attrs.dialogSetting?parseToJson(attrs.dialogSetting):null;
				CustUtil.openCustDialog(attrs.abCustDialog, param, function(data) {
					if (data.length < 1) {
						return;
					}
					//忽略大小写的数据 value-这种属性的赋值形式不能存在大小写的，真郁闷，以后要改掉
					var dataIgnoreCase = [];
					angular.forEach(data, function(item) {
						var json = {};
						angular.forEach(item, function(val,key) {
							json[key.toLowerCase()] = val;
						});
						dataIgnoreCase.push(json);
					});
					
					scope.$apply(function() {
						if(!attrs.ngModel){
							handleData(dataIgnoreCase);
						}else{
							handleNgModelData(dataIgnoreCase);
						}
					});
				}, initData, dialogSetting, true);
			});
			
			//无ngModel下的初始化数据处理
			var handleInitData = function(){
				var initData = [];
				var json = null;
				angular.forEach(valueMap, function(val, key) {
					eval("var data = scope.$parent." + val );
					if(!data){
						return;
					}
					if(!json){
						json = {};
					}
					eval("json[key]=data");
				});
				if(!json){
					return initData;
				}
				//切割json中的,当作多选
				angular.forEach(json, function(val, key) {
					var list = val.split(",");
					var index = 0;
					angular.forEach(list, function(item) {
						if(!initData[index]){
							initData[index] = {};
						}
						initData[index][key] = item;
						index++;
					});
				});
				return initData;
			};
			
			//有ngModel下的初始化数据处理
			var handleNgModelInitData = function(){
				//因为对ngmodel只增不减，所以不需要初始化数据
			};
			
			//无ngModel下的数据处理
			var handleData = function(data){
				angular.forEach(valueMap, function(val, key) {
					var list = [];
					angular.forEach(data, function(item) {
						list.push(item[key]);
					});
					eval("scope.$parent." + val + " = list.join(',');");
				});
			};
			
			//有ngmodel下的数据处理
			var handleNgModelData = function(data){
				if(!scope.ngModel){
					scope.ngModel = [];
				}
				//ng-model 为对象则直接赋值即可
				if(!Array.isArray(scope.ngModel)){
					angular.forEach(valueMap, function(val, key) {
						var list = [];
						angular.forEach(data, function(item) {
							list.push(item[key]);
						});
						scope.ngModel[val] = list.join(',');
					}); 
					return;
				}
				
				angular.forEach(data, function(item) {
					if($.isEmptyObject(valueMap)){
						scope.ngModel.push(data);
						return;
					}
					
					var json = {};
					angular.forEach(valueMap, function(val, key) {
						//如果val是a.b这样的，则需要对json.a初始化，不然直接操作json.a.b会报错
						var strs = val.split(".");
						var exp = "json";
						for (var i=0;i<strs.length-1;i++){
							exp = exp + "."+strs[i];
							if(eval("!"+exp)){//为空则初始化
								eval(exp+" = {};");
							}
						}
						console.info(item[key]);
						eval("json."+val+" = item[key];");
					});
					scope.ngModel.push(json);
				});
			};
		}
	}
})
//dynamicDirective="{name:'attr',directive1:'attr'}"
.directive('dynamicDirective', function($compile) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var directives = attrs.dynamicDirective;
			if(!directives)return;
			var directiveArr = directives.split(",");
			
			var str = "<div ";
			for(var i=0,d;d=directiveArr[i++];){
				str=str+d.split(":")[0]+"="+d.split(":")[1]+" ";
			}
			str +="></div>";
			element.removeAttr("dynamic-directive")
			element.removeClass("ng-binding");
			element.html(str);
            $compile(element)(scope);
		}
	};
})
/**
 * <pre>
 * 上传指令
 * 实例：<a href="javascript:void(0)" class="btn btn-primary fa-reply" ab-upload ng-model="test">指令测试</a>
 * test的字符串内容是：
 * $scope.test = '[ {
 * 		id : "20000011550001",//sysFile的id
 * 		name : "Penguins.jpg"//文件名
 * 		}, {
 * 		id : "20000011550006",
 * 		name : "Desert.jpg"
 * } ]';
 * </pre>
 * @returns
 */
.directive('abUpload', function() {
	return {
		restrict : 'AE',
		require : '?ngModel',
		scope : {
			ngModel : "="
		},
		link : function(scope, element, attrs, ngModel) {
			$(element).on("click", function() {
				var list = ngModel.$viewValue;
				if (!list) {
					list = "[]";
					ngModel.$setViewValue(list);
				}
				var conf = {
					height : 600,
					width : 800,
					url : "/sys/sysFile/uploadDialog.html",
					title : "附件上传",
					topOpen : true,
					btn : true,
					closeBtn : 1,
					passData : {
						fileList : JSON.parse(list),
					}
				};
				conf.ok = function(index, innerWindow) {
					scope.$apply(function() {
						ngModel.$setViewValue(JSON.stringify(innerWindow.getData()));
						ngModel.$render();// 调用数据修改函数
					});
					$.Dialog.close(innerWindow);
				}
				jQuery.Dialog.open(conf);
			});

			// Model数据更新时 在td 下增加按钮
			ngModel.$render = function() {
				if (!ngModel.$viewValue) {
					return;
				}
				var list = JSON.parse(ngModel.$viewValue);
				var parent = $(element).parent();
				// 增加没有的下载链接
				$.each(list, function(index, file) {
					// 已存在，不存在则增加
					if (parent.find("[fileId='"+file.id+"']").length > 0) {
						return;
					}
					var a = $("<a>" + file.name + "</a>");
					var href = __ctx + "/sys/sysFile/download?fileId=" + file.id;
					a.attr("href", href);
					a.attr("fileId", file.id);
					a.attr("style", "margin-right:10px");
					$(element).before(a);
				});
				// 删除多余的没有的下载链接
				$.each(parent.find("a[fileId]"), function(index, a) {
					var exist = false;
					var fileId = $(a).attr("fileId");
					$.each(list, function(i, file) {
						if (file.id == fileId) {
							exist = true;
						}
					});
					if (!exist) {
						$(a).remove();
					}
				});

			};
		}
	}
})
/**
 * <pre>
 * 流水号
 * 每次加载会消耗一次流水号
 * <input type="text" class="form-control" ng-model="data.kjcs.zd" ab-basic-permission="permission.kjcs.cskj.zd" ab-serial-no="dayNo">
 * </pre>
 */
.directive('abSerialNo', [ 'baseService', function(baseService) {
	return {
		restrict : 'AE',
		require : '?ngModel',
		scope : {
			ngModel : "=",
			abBasicPermission :"="
		},
		link : function(scope, element, attrs,ctrl) {
			var fn = function(){
				//无权限或者已经有值了，则不需要处理
				if(scope.abBasicPermission ==="n"||scope.abBasicPermission ==="r"||scope.ngModel){
					return;
				}
				
				var defer = baseService.postForm(__ctx + "/sys/serialNo/getNextIdByAlias",{
					alias:attrs.abSerialNo
				});
				$.getResultData(defer,function(data){
					scope.ngModel = data;
				});
			};
			
			if(scope.abBasicPermission){
				fn();
			}else{
				scope.$parent.$on("permission:update",function(event,data){
					fn();
				});
			}
		}
	}
}])
/**
 * 数字转成中文大写。 用法： <input class="inputText" type="text" ng-model="jinge" />
 * 
 * {{jinge | cnCapital}}
 */
.filter('cnCapital', function() {
	return function(input) {
		if (!input)
			return "";
		return jQuery.convertCurrency(input);
	};
});
