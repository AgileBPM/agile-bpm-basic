/**
 * 选择表单模板
 */
UE.registerUI('selectTemplate', function(editor, uiName) {
	// 参考addCustomizeButton.js
	var btn = new UE.ui.Button({
		name : uiName + uiName,
		title : '选择模板',
		// 需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon，每个图标偏移量为-20px
		cssRules : 'background-position: -400px -40px;',
		onclick : function() {
			var conf = {
				height : 600,
				width : 800,
				url : "/form/formDef/selectTemplate.html",// url不为空则使用iframe类型对话框
				title : "选择模板",
				topOpen : true,
				btn : true,
				closeBtn : 1,
			};
			conf.passData = {
				parentScope : AngularUtil.getScope()
			};
			conf.ok = function(index, innerWindow) {
				innerWindow.createHtml();
			};
			jQuery.Dialog.open(conf);
		}
	});
	return btn;
}/*
	 * index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId
	 * 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮
	 */);