var tempDiv = null,subTableDiv = null;
UE.registerUI('cutsubtable',function(editor,uiName){
	var me = this;
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(cmdName){
			if(!tempDiv)return;
			subTableDiv = tempDiv.clone();
			$(tempDiv).remove();
		},
        queryCommandState : function() {
			if(this.highlight ){return -1;}
			var start = me.selection.getStart(),
				div = $(start).closest("[type='subGroup']");
			if(!div[0])return -1;
			tempDiv = div;
			return 0;
		}
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"剪切子表",
//        icon : "glyphicon glyphicon-th-list",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -680px -0px;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });
    return btn;
},101/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);

UE.registerUI('pastesubtable',function(editor,uiName){
	var me = this;
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(cmdName){
			var start = me.selection.getStart();
			if(!start||!subTableDiv)return;
			editor.execCommand("insertHtml",subTableDiv[0].outerHTML);
			subTableDiv = null;
		},
        queryCommandState : function() {
			if(this.highlight||!subTableDiv){return -1;}
			return 0;
		}
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"粘贴子表",
//        icon : "glyphicon glyphicon-th-list",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -280px -20px;',
        //点击时执行的命令
        onclick:function () {
            //这里可以不用执行命令,做你自己的操作也可
           editor.execCommand(uiName);
        }
    });

    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
        var state = editor.queryCommandState(uiName);
        if (state == -1) {
            btn.setDisabled(true);
            btn.setChecked(false);
        } else {
            btn.setDisabled(false);
            btn.setChecked(state);
        }
    });
    return btn;
},102/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);





