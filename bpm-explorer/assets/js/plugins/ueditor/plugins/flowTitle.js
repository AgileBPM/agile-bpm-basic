/**
 * 审批历史
 */
UE.registerUI('flowTitle',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(){
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName+uiName,
        //提示
        title:"流程标题",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -140px -20px;',
        //点击时执行的命令
        onclick:function () {
        	//这里可以不用执行命令,做你自己的操作也可
        	var str='{流程标题:title}';
        	editor.execCommand('inserthtml',str);
        }
    });

    //因为你是添加button,所以需要返回这个button
    return btn;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);