/**
 * 意见
 */
UE.registerUI('opinion',function(editor,uiName){
    //参考addCustomizeButton.js
    var btn = new UE.ui.Button({
        name:uiName+uiName,
        title:'意见',
        //需要添加的额外样式，指定icon图标，这里默认使用一个重复的icon，每个图标偏移量为-20px
        cssRules :'background-position: -700px -0px;',
        onclick:function () {
    		var url= __ctx+'/js/lib/ueditor/plugins/opinion/page/opinionPage.jsp';
    		var dialog = {};
    		var def = {
    				passConf :{editor:editor,name:uiName},
    				title : '意见',
    				width : 700,
    				height : 400,
    				modal : true,
    				resizable : false,
    				iconCls : 'icon-collapse',
    				buttonsAlign:'center',
    				buttons : [{
    					text:'确定',
    					iconCls:'fa fa-check-circle',
    					handler:function(){
    						var valid = dialog.innerWin.triggerScopeFun("done");
                        	if(!valid) return;
    						dialog.dialog('close');
    					}
    				},{
    					text:'取消',
    					iconCls:'fa fa-times-circle',
    					handler:function(){
    						dialog.dialog('close');
    					}
    				}]
    		};
    		var show = function() {
    			dialog = $.topCall.dialog({
    				src : url,
    				base : def
    			});
    			return dialog;
    		};
    		show();
    	
        }
    });
    
    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function () {
    	var selectedDom = editor.selection.getStart();
    	//不能在自身上再添加意见
    	if(selectedDom.attributes['opinion']==null){
    		 btn.setDisabled(false);
    	}else{
    		btn.setDisabled(true);
    	}
    });
    
    return btn;
}/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);