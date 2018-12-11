var tempInput = null,input = null;
UE.registerUI('cutinput',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
	var me = this;
    editor.registerCommand(uiName,{
        execCommand:function(cmdName){
			if(!tempInput)return;
			input = $(tempInput).clone();
			$(tempInput).remove();
		},
        queryCommandState : function() {
			if(this.highlight ){return -1;}
			var start = me.selection.getStart(),
				nodeName = start.nodeName;
			if(nodeName!="INPUT") return -1;
			tempInput = start;
			return 0;
		}
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"剪切字段",
//        icon : "glyphicon glyphicon-th-list",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -580px -40px;',
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

    //因为你是添加button,所以需要返回这个button
    return btn;
},104/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);


/**
 *  粘贴字段
 */
UE.registerUI('pasteinput',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(cmdName){
    		var start = this.selection.getStart();
    		if(!start||!input)return;
    		editor.execCommand("insertHtml",input[0].outerHTML);
    		//下面的写法。编辑器不会立即保存HTML，如果此时点击了保存。就会出现丢失掉刚刚粘贴的 字段。
    		/*if(start.tagName=='TD'){
				start.appendChild(input[0]);
			}
			else{
				$(start).after(input);
			}*/
    		input = null;
    	},
        queryCommandState : function() {
    		if(this.highlight||!input){return -1;}
    		return 0;
    	}
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"粘贴字段",
//        icon : "glyphicon glyphicon-th-list",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -560px -0px;',
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

    //因为你是添加button,所以需要返回这个button
    return btn;
},105/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);

