UE.registerUI('button', function(editor, uiName) {
	var popup = initPopup(editor);
    //当点到编辑内容上时，按钮要做的状态反射
    editor.addListener('selectionchange', function() {
    	var element = editor.selection.getStart();
    	
    	var isNgInput = element.hasAttribute('ng-model'); //所有控件    咯咯...
    	if(element.hasAttribute('ht-bpm-opinion')||element.hasAttribute('ht-bpm-approval-history')
    			||element.hasAttribute('ht-bpm-flow-image'))
    	{
    		isNgInput=true;
    	}
    	if(!isNgInput) return ;
    	
    	
		var menuHtml =  '<span onclick="$$._onInputDelButtonClick()" class="edui-clickable">删除</span>&nbsp;&nbsp;';  //删除按钮
		
    	//菜单
    	html = popup.formatHtml('<nobr>' + menuHtml + '</nobr>');
		if(popup.getDom("content")){//已生成过菜单，要改变其dom元素
			popup.getDom("content").innerHTML = html;
		}else{//第一次dom元素还不存在的，直接设置content则可
			popup.content = html;
		}
		popup.editElement = element;
		popup.showAnchor(popup.editElement);
    });
  
   
});


/**
 * 在此处添加控件事件
 * 删除控件
 * @param id
 */
function delElement_(id){
	var element = edittingElement_[id];
	
	element.qtip('destroy', true); 
	element.remove();
}

function initPopup(editor) {
	var popup = new baidu.editor.ui.Popup({
		editor : editor,
		content : '',
		className : 'edui-bubble',
		// ⑦扩展
		_onInputDelButtonClick : function() {
			this.hide();
			if (popup.editElement) {
				popup.editElement.parentNode.removeChild(popup.editElement); 
			}
		}
	});
	return popup;
}