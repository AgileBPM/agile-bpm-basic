/**
 * 自定义按钮
 */
UE.registerUI('customButton',function(editor,uiName){
	
    //创建dialog
    var dialog = new UE.ui.Dialog({
        //指定弹出层中页面的路径，这里只能支持页面,因为跟addCustomizeDialog.js相同目录，所以无需加路径
        iframeUrl:__ctx+'/form/form/custBtn.ht?formId='+formId+'&formType='+formType,
        //需要指定当前的编辑器实例
        editor:editor,
        //指定dialog的名字
        name:uiName,
        //dialog的标题
        title:"自定义按钮",

        //指定dialog的外围样式
        cssRules:"width:750px;height:600px;",

        //如果给出了buttons就代表dialog有确定和取消
        buttons:[
            {
                className:'edui-okbutton',
                label:'确定',
                onclick:function () {
                    dialog.close(true);
                }
            },
            {
                className:'edui-cancelbutton',
                label:'取消',
                onclick:function () {
                    dialog.close(false);
                }
            }
        ]});

    //参考addCustomizeButton.js
    var btn = new UE.ui.Button({
        name:uiName+uiName,
        title:'自定义按钮',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon，每个图标偏移量为-20px
        cssRules :'background-position: -340px -40px;',
        onclick:function () {
            //渲染dialog
            dialog.render();
            dialog.open();
        }
    });
    
  //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
    	var selectedDom = editor.selection.getStart();
		 if(selectedDom.attributes['ht-custDialog']!=null){//编辑
			 btn.setChecked(true);
		 }else{
			 btn.setChecked(false);
		 }
    });
    
    return btn;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);