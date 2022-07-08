/**
 * 自定义对话框
 */
UE.registerUI('custDialog', function(editor, uiName) {
	// 参考addCustomizeButton.js
	var btn = new UE.ui.Button({
		name : uiName + uiName,
		title : '自定义对话框',
		// 需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon，每个图标偏移量为-20px
		cssRules :'background: url("./alert.png") !important',
		onclick : function() {
			var conf = {
				height : 600,
				width : 900,
				url : "/form/formDef/custDialog.html",// url不为空则使用iframe类型对话框
				title : "自定义对话框",
				topOpen : true,
				btn : true,
				closeBtn : 1,
			};
			conf.passData = {
				parentScope : AngularUtil.getScope()
			};

			var selectedDom = editor.selection.getStart();
			var subTableDiv = $(selectedDom).closest("[table-key]")[0];
			conf.passData.tableKey = subTableDiv? $(subTableDiv).attr("table-key"):"";
			
			if ($(selectedDom).attr("dialog-key")) {
				var data = {};
				data.dialogKey = $(selectedDom).attr("dialog-key");
				data.dialogName = $(selectedDom).text();
				data.targetNgModelPath = $(selectedDom).attr("v-model");
				
				data.mapList = [];
				$.each(selectedDom.attributes, function(i, v) {
					if (v.name.indexOf("value-") !== 0) {// 处理value-开头的属性
						return;
					}
					data.mapList.push({
						key : v.value,
						returnField : v.name.replace("value-", "")
					});
				});
				
				if($(selectedDom).attr(":param")){
					data.dialogParam = parseToJson($(selectedDom).attr(":param"));
				}
				if($(selectedDom).attr(":dialog-setting")){
					data.dialogSetting = parseToJson($(selectedDom).attr(":dialog-setting"));
				}
				
				
				conf.passData.data = data;
			}

			conf.ok = function(index, innerWindow) {
				var data = innerWindow.getData();
				var elm = "";
				if (data.mapList.length > 0) {
					elm = $('<ab-cust-dialog class="ivu-btn ivu-btn-primary">' + data.dialogName + '</ab-cust-dialog>');
					elm.attr("dialog-key", data.dialogKey);
					elm.attr("v-model",data.targetNgModelPath);
					elm.attr(":param",JSON.stringify(data.dialogParam));
					elm.attr(":dialog-setting",JSON.stringify(data.dialogSetting));
					
					angular.forEach(data.mapList, function(map){
						elm.attr("value-" + map.returnField, map.key);
					});
				}
				
				$(selectedDom).after(elm);//则追加到最后
				
				if (conf.passData.data) {
					$(selectedDom).remove();// 删除自身
				}
				editor.execCommand('inserthtml',"");//触发ueditor内容变化
				$.Dialog.close(innerWindow);
			};

			jQuery.Dialog.open(conf);
		}
	});

	// 当点到编辑内容上时，按钮要做的状态反射
	editor.addListener('selectionchange', function() {
		var selectedDom = editor.selection.getStart();
		if (!selectedDom) {
			btn.setDisabled(true);
			btn.setChecked(false);
			return;
		} else {
			btn.setDisabled(false);
		}
		if ($(selectedDom).attr("dialog-key")) {
			btn.setChecked(true);
		}else{
			btn.setChecked(false);
		}
	});

	return btn;
}/*
	 * index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId
	 * 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮
	 */);