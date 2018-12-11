UE.registerUI('picture',function(editor,uiName){
    //注册按钮执行时的command命令，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(cmdName){
        	var html = "";
        	UploadDialog({type:"jpg",title:"上传图片",callback:function(images){
        		for(var i=0,img;img=images[i++];){
        			html+="<img src='"+__ctx+"/system/file/download?id="+img.id+"' />"; 
        		}
        		editor.execCommand('inserthtml',html);
        	}})
        	
        },
        queryCommandState : function() {
            return this.highlight ? -1 : 0;
        }
    });

    //创建一个button
    var btn = new UE.ui.Button({
        //按钮的名字
        name:uiName,
        //提示
        title:"添加图片",
//        icon : "glyphicon glyphicon-th-list",
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon
        cssRules :'background-position: -380px -0px',
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
},150);



