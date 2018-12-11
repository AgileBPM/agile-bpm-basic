///import core
///commands 全屏
///commandsName FullScreen
///commandsTitle  全屏
(function () {
    var utils = baidu.editor.utils,
        uiUtils = baidu.editor.ui.uiUtils,
        UIBase = baidu.editor.ui.UIBase,
        domUtils = baidu.editor.dom.domUtils;
    var nodeStack = [];
    function EditorUI(options) {
        this.initOptions(options);
        this.initEditorUI();
    }

    EditorUI.prototype = {
        uiName:'editor',
        initEditorUI:function () {
        	this.editor = baidu.editor;
            this.editor.ui = this;
            this._dialogs = {};
            var editor = this.editor,
                me = this;
            editor.addListener('ready', function () {
                //提供getDialog方法
                editor.getDialog = function (name) {
                    return editor.ui._dialogs[name + "Dialog"];
                };
                domUtils.on(editor.window, 'scroll', function (evt) {
                    baidu.editor.ui.Popup.postHide(evt);
                });
                //提供编辑器实时宽高(全屏时宽高不变化)
                editor.ui._actualFrameWidth = editor.options.initialFrameWidth;

                //display bottom-bar label based on config
                if (editor.options.elementPathEnabled) {
                    editor.ui.getDom('elementpath').innerHTML = '<div class="edui-editor-breadcrumb">' + editor.getLang("elementPathTip") + ':</div>';
                }
                if (editor.options.wordCount) {
                    function countFn() {
                        setCount(editor,me);
                        domUtils.un(editor.document, "click", arguments.callee);
                    }
                    domUtils.on(editor.document, "click", countFn);
                    editor.ui.getDom('wordcount').innerHTML = editor.getLang("wordCountTip");
                }
                editor.ui._scale();
                if (editor.options.scaleEnabled) {
                    if (editor.autoHeightEnabled) {
                        editor.disableAutoHeight();
                    }
                    me.enableScale();
                } else {
                    me.disableScale();
                }
                if (!editor.options.elementPathEnabled && !editor.options.wordCount && !editor.options.scaleEnabled) {
                    editor.ui.getDom('elementpath').style.display = "none";
                    editor.ui.getDom('wordcount').style.display = "none";
                    editor.ui.getDom('scale').style.display = "none";
                }

                if (!editor.selection.isFocus())return;
                editor.fireEvent('selectionchange', false, true);


            });

            editor.addListener('mousedown', function (t, evt) {
                var el = evt.target || evt.srcElement;
                baidu.editor.ui.Popup.postHide(evt, el);
            });

            var pastePop, isPaste = false, timer;
            editor.addListener("afterpaste", function () {
                if(editor.queryCommandState('pasteplain'))
                    return;
                pastePop = new baidu.editor.ui.Popup({
                    content:new baidu.editor.ui.PastePicker({editor:editor}),
                    editor:editor,
                    className:'edui-wordpastepop'
                });
                pastePop.render();
                isPaste = true;
            });

            editor.addListener("afterinserthtml", function () {
                clearTimeout(timer);
                timer = setTimeout(function () {
                    if (pastePop && (isPaste || editor.ui._isTransfer)) {
                        var span = domUtils.createElement(editor.document, 'span', {
                                'style':"line-height:0px;",
                                'innerHTML':'\ufeff'
                            }),
                            range = editor.selection.getRange();
                        range.insertNode(span);
                        pastePop.showAnchor(span);
                        domUtils.remove(span);
                        delete editor.ui._isTransfer;
                        isPaste = false;
                    }
                }, 200)
            });
            function setCount(editor,ui) {
                editor.setOpt({
                    wordCount:true,
                    maximumWords:10000,
                    wordCountMsg:editor.options.wordCountMsg || editor.getLang("wordCountMsg"),
                    wordOverFlowMsg:editor.options.wordOverFlowMsg || editor.getLang("wordOverFlowMsg")
                });
                var opt = editor.options,
                    max = opt.maximumWords,
                    msg = opt.wordCountMsg ,
                    errMsg = opt.wordOverFlowMsg,
                    countDom = ui.getDom('wordcount');
                if (!opt.wordCount) {
                    return;
                }
                var count = editor.getContentLength(true);
                if (count > max) {
                    countDom.innerHTML = errMsg;
                    editor.fireEvent("wordcountoverflow");
                } else {
                    countDom.innerHTML = msg.replace("{#leave}", max - count).replace("{#count}", count);
                }
            }
            var popup = new baidu.editor.ui.Popup({
                editor:editor,
                content:'',
                className:'edui-bubble',
                _onEditButtonClick:function () {
                    this.hide();
                    editor.ui._dialogs.linkDialog.open();
                },
                _onImgEditButtonClick:function (name) {
                    this.hide();
                    editor.ui._dialogs[name] && editor.ui._dialogs[name].open();

                },
                _onImgSetFloat:function (value) {
                    this.hide();
                    editor.execCommand("imagefloat", value);

                },
                _setIframeAlign:function (value) {
                    var frame = popup.anchorEl;
                    var newFrame = frame.cloneNode(true);
                    switch (value) {
                        case -2:
                            newFrame.setAttribute("align", "");
                            break;
                        case -1:
                            newFrame.setAttribute("align", "left");
                            break;
                        case 1:
                            newFrame.setAttribute("align", "right");
                            break;
                    }
                    frame.parentNode.insertBefore(newFrame, frame);
                    domUtils.remove(frame);
                    popup.anchorEl = newFrame;
                    popup.showAnchor(popup.anchorEl);
                },
                _updateIframe:function () {
                    editor._iframe = popup.anchorEl;
                    editor.ui._dialogs.insertframeDialog.open();
                    popup.hide();
                },
                _onRemoveButtonClick:function (cmdName) {
                    editor.execCommand(cmdName);
                    this.hide();
                },
                queryAutoHide:function (el) {
                    if (el && el.ownerDocument == editor.document) {
                        if (el.tagName.toLowerCase() == 'img' || domUtils.findParentByTagName(el, 'a', true)) {
                            return el !== popup.anchorEl;
                        }
                    }
                    return baidu.editor.ui.Popup.prototype.queryAutoHide.call(this, el);
                },
                //⑤扩展
                //处理输入框的修改
                _onInputEditButtonClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	if(child){
                		var external = popup.anchorEl.getAttribute("external");
                		if(external){
                			editor.curInputElement = popup.anchorEl;
                			editor.execCommand(popup.anchorEl.className,false);     //xcx 第二个参数为false是用于区分是用_onInputEditButtonClick进入修改的而不是点击新增的
                			return;
                		}
                		switch(child.tagName.toLowerCase())
                		{
	                		case "a":
	                			editor.curInput=popup.anchorEl;
	            	    		editor.ui._dialogs['inputDialog'].open();
	                			break;
	                		case "textarea":
	                			editor.curOpinion=popup.anchorEl;
	            	    		editor.ui._dialogs['opinionDialog'].open();
	                			break;
	                		}
                	}
                	
                },
                //⑥扩展
                //处理输入框的自定义对话框
                _onInputDialogButtonClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	if(child){
            			editor.curInput=child;
        	    		editor.execCommand("customdialog");
                	}
                },
                //⑦扩展
                _onInputDelButtonClick:function(){
                	this.hide();
                	if(popup.anchorEl){
                		popup.anchorEl.parentNode.removeChild(popup.anchorEl);
                	}
                },
                //⑩扩展
                //级联设置
                _onCascadeQueryClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	if(child){
            			editor.curInput=child;
        	    		editor.execCommand("cascadequery");
                	}
                },
                
                 //删除级联
                _onUnCascadeQueryClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	if(child){
            			editor.curInput=child;
        	    		editor.execCommand("uncascadequery");
                	}
                },
                
                //设为只读
                _onReadOnlyClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	if(child){
            			editor.curInput=child;
        	    		editor.execCommand("setreadonly");
                	}
                },
                //删除只读
                _unReadOnlyClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	if(child){
            			editor.curInput=child;
        	    		editor.execCommand("delreadonly");
                	}
                },
                //⑨扩展
                //处理字段的剪切和粘贴
                _onInputCutButtonClick:function(){
                	this.hide();
                	var child=popup.anchorEl.childNodes[0];
                	child = domUtils.findParentByTagName(child,'span',false);
                	if(child.getAttribute("name")=="editable-input"){
                		editor.curCutInput = child;
                		domUtils.remove(child);
                	}
                }
            });
            popup.render();
            if (editor.options.imagePopup) {
                editor.addListener('mouseover', function (t, evt) {
                	//⑧扩展
                    var el = evt.target || evt.srcElement;
    	    		if(!(el.tagName&&el.parentElement))return;
    	    		var ifEl=el.getAttribute("name")=="editable-input";
    	    		//是否为按钮
    	    		var ifInput = el.className.indexOf("extend")>-1||el.parentElement.className.indexOf("extend")>-1;
    	    		var ifElParent = el.parentElement.getAttribute("name") =="editable-input";
    	    		var onlydelete = el.className.indexOf("onlydelete")>-1||el.parentElement.className.indexOf("onlydelete")>-1;
    	    		var isinput=el.tagName=="INPUT"||el.tagName=="TEXTAREA";
    	    		var readOnly=false;
    	    		var isflag=el.getAttribute("isflag")=="tableflag"?true:false;
    	    		if(isinput){
    	    			readOnly=el.getAttribute("readonly")?true:false;
    	    		}
    	    		else if(el.childNodes[0]){
    	    			readOnly=(el.childNodes[0].tagName=="INPUT"||el.childNodes[0].tagName=="TEXTAREA")&&el.childNodes[0].getAttribute("readonly")?true:false;
    	    		}
    	    		var isSelect=false;
    	    		var isCascadequery=false;
    	    		var isNowSelect= el.tagName=="SELECT";
    	    		if(isNowSelect){
    	    			isSelect= el.tagName=="SELECT";
	    	    		isCascadequery=el.getAttribute("selectquery")?true:false;
    	    		}else{
    	    			var hascompiler=el.className.indexOf("selectinput")>-1
    	    			if(hascompiler ){
	    	    			isSelect= el.childNodes[0].tagName=="SELECT";
	    	    			isCascadequery=el.childNodes[0].getAttribute("selectquery")?true:false;
    	    			}
    	    		}
    	    		
    	    		if(ifEl||ifElParent){
    	    			if(readOnly){
    	    				if(isflag){
    	    					var html = popup.formatHtml(
		                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
		                        '<span onclick="$$._unReadOnlyClick(event, this);" class="edui-clickable">删除只读</span>&nbsp;&nbsp;'+
		                       '</nobr>' );
    	    				}else{
    	    				 var html = popup.formatHtml(
		                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputDelButtonClick()" class="edui-clickable">'+editor.getLang("delete")+'</span>&nbsp;&nbsp;' +	                       
		                       '<span onclick="$$._onInputEditButtonClick(event, this);" class="edui-clickable">' + editor.getLang("modify") + '</span>&nbsp;&nbsp;'+
		                       '<span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
		                        '<span onclick="$$._unReadOnlyClick(event, this);" class="edui-clickable">删除只读</span>&nbsp;&nbsp;'+
		                       '</nobr>' );
    	    				}
    	    			}else{
    	    				if(isflag){
    	    					var html = popup.formatHtml(
		                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
		                        '<span onclick="$$._onReadOnlyClick(event, this);" class="edui-clickable">设为只读</span>&nbsp;&nbsp;'+
		                       '</nobr>' );
    	    				}else{
    	    				 var html = popup.formatHtml(
		                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputDelButtonClick()" class="edui-clickable">'+editor.getLang("delete")+'</span>&nbsp;&nbsp;' +	                       
		                       '<span onclick="$$._onInputEditButtonClick(event, this);" class="edui-clickable">' + editor.getLang("modify") + '</span>&nbsp;&nbsp;'+
		                       '<span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
		                      '<span onclick="$$._onReadOnlyClick(event, this);" class="edui-clickable">设为只读</span>&nbsp;&nbsp;'+
		                       '</nobr>' );
    	    				}
    	    			}
					 
    				   if(ifInput){   				   	 
		    				  html = popup.formatHtml(
		    	                    '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputDelButtonClick()" class="edui-clickable">'+editor.getLang("delete")+'</span>&nbsp;&nbsp;' +	                       
		    	                    '<span onclick="$$._onInputEditButtonClick(event, this);" class="edui-clickable">' + editor.getLang("modify") + '</span>&nbsp;&nbsp;'+
		    	                    '<span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
		    	                    '<span onclick="$$._onInputDialogButtonClick(event, this);" class="edui-clickable">'+editor.getLang("customdialog.self")+'</span>' +
		    	                       '</nobr>' );
    				   }
    				   if(onlydelete){
    					   html = popup.formatHtml(
    		                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputDelButtonClick()" class="edui-clickable">'+editor.getLang("delete")+'</span>&nbsp;&nbsp;' +
    		                       '</nobr>' );
    				   }
    				   if(isSelect){
    				   		
	    				   	if(isCascadequery){
	    				   		html = popup.formatHtml(
	    	                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputDelButtonClick()" class="edui-clickable">'+editor.getLang("delete")+'</span>&nbsp;&nbsp;' +	                       
	    	                       '<span onclick="$$._onInputEditButtonClick(event, this);" class="edui-clickable">' + editor.getLang("modify") + '</span>&nbsp;&nbsp;'+
	    	                       '<span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
	    	                       '<span onclick="$$._onCascadeQueryClick(event, this);" class="edui-clickable">级联设置</span>&nbsp;&nbsp;' +
	    	                       '<span onclick="$$._onUnCascadeQueryClick(event, this);" class="edui-clickable">删除级联</span>&nbsp;&nbsp;'+
	    	                       '</nobr>' );
	    				   	}else{
	    				   		html = popup.formatHtml(
    	                       '<nobr>'+editor.getLang("property")+': <span onclick="$$._onInputDelButtonClick()" class="edui-clickable">'+editor.getLang("delete")+'</span>&nbsp;&nbsp;' +	                       
    	                       '<span onclick="$$._onInputEditButtonClick(event, this);" class="edui-clickable">' + editor.getLang("modify") + '</span>&nbsp;&nbsp;'+
    	                       '<span onclick="$$._onInputCutButtonClick(event, this);" class="edui-clickable">剪切</span>&nbsp;&nbsp;'+
    	                       '<span onclick="$$._onCascadeQueryClick(event, this);" class="edui-clickable">级联设置</span>&nbsp;&nbsp;' +
    	                       '</nobr>' );
	    				   	}
    				   }
    				 
    				   
    				   popup.getDom( 'content' ).innerHTML = html;
    				   popup.anchorEl = ifEl?el:el.parentElement;
    				   popup.showAnchor( popup.anchorEl );
    	    		}
                });
                editor.addListener("mouseup",function(t,evt){
                	if(editor.tableFormatState){
                		editor.execCommand( "applytableformat");
                    }
                });
                editor.addListener('selectionchange', function (t, causeByUi) {
                    if (!causeByUi) return;
                    var html = '', str = "",
                        img = editor.selection.getRange().getClosedNode(),
                        dialogs = editor.ui._dialogs;
                    if (img && img.tagName == 'IMG') {
                        var dialogName = 'insertimageDialog';
                        if (img.className.indexOf("edui-faked-video") != -1) {
                            dialogName = "insertvideoDialog"
                        }
                        if (img.className.indexOf("edui-faked-webapp") != -1) {
                            dialogName = "webappDialog"
                        }
                        if (img.src.indexOf("http://api.map.baidu.com") != -1) {
                            dialogName = "mapDialog"
                        }
                        if (img.className.indexOf("edui-faked-music") != -1) {
                            dialogName = "musicDialog"
                        }
                        if (img.src.indexOf("http://maps.google.com/maps/api/staticmap") != -1) {
                            dialogName = "gmapDialog"
                        }
                        if (img.getAttribute("anchorname")) {
                            dialogName = "anchorDialog";
                            html = popup.formatHtml(
                                '<nobr>' + editor.getLang("property") + ': <span onclick=$$._onImgEditButtonClick("anchorDialog") class="edui-clickable">' + editor.getLang("modify") + '</span>&nbsp;&nbsp;' +
                                    '<span onclick=$$._onRemoveButtonClick(\'anchor\') class="edui-clickable">' + editor.getLang("delete") + '</span></nobr>');
                        }
                        if (img.getAttribute("word_img")) {
                            //todo 放到dialog去做查询
                            editor.word_img = [img.getAttribute("word_img")];
                            dialogName = "wordimageDialog";
                        }
                        if (!dialogs[dialogName]) {
                            return;
                        }
                        str = '<nobr>' + editor.getLang("property") + ': '+
                            '<span onclick=$$._onImgSetFloat("none") class="edui-clickable">' + editor.getLang("default") + '</span>&nbsp;&nbsp;' +
                            '<span onclick=$$._onImgSetFloat("left") class="edui-clickable">' + editor.getLang("justifyleft") + '</span>&nbsp;&nbsp;' +
                            '<span onclick=$$._onImgSetFloat("right") class="edui-clickable">' + editor.getLang("justifyright") + '</span>&nbsp;&nbsp;' +
                            '<span onclick=$$._onImgSetFloat("center") class="edui-clickable">' + editor.getLang("justifycenter") + '</span>&nbsp;&nbsp;'+
                            '<span onclick="$$._onImgEditButtonClick(\'' + dialogName + '\');" class="edui-clickable">' + editor.getLang("modify") + '</span></nobr>';

                        !html && (html = popup.formatHtml(str))

                    }
                    if (editor.ui._dialogs.linkDialog) {
                        var link = editor.queryCommandValue('link');
                        var url;
                        if (link && (url = (link.getAttribute('data_ue_src') || link.getAttribute('href', 2)))) {
                            var txt = url;
                            if (url.length > 30) {
                                txt = url.substring(0, 20) + "...";
                            }
                            if (html) {
                                html += '<div style="height:5px;"></div>'
                            }
                            html += popup.formatHtml(
                                '<nobr>' + editor.getLang("anthorMsg") + ' <span class="edui-clickable" onclick="$$._onEditButtonClick();">' + editor.getLang("modify") + '</span>' +
                                    ' <span class="edui-clickable" onclick="$$._onRemoveButtonClick(\'unlink\');"> ' + editor.getLang("clear") + '</span></nobr>');
                            popup.showAnchor(link);
                        }
                    }

                    if (html) {
                        popup.getDom('content').innerHTML = html;
                        popup.anchorEl = img || link;
                        popup.showAnchor(popup.anchorEl);
                    } else {
                        popup.hide();
                    }
                });
            }

        },
        getHtmlTpl:function () {
            return '<div id="##" class="%%">' +
                '<div id="##_toolbarbox" class="%%-toolbarbox">' +
                (this.toolbars.length ?
                    '<div id="##_toolbarboxouter" class="%%-toolbarboxouter"><div class="%%-toolbarboxinner">' +
                        this.renderToolbarBoxHtml() +
                        '</div></div>' : '') +
                '<div id="##_toolbarmsg" class="%%-toolbarmsg" style="display:none;">' +
                '<div id = "##_upload_dialog" class="%%-toolbarmsg-upload" onclick="$$.showWordImageDialog();">' + this.editor.getLang("clickToUpload") + '</div>' +
                '<div class="%%-toolbarmsg-close" onclick="$$.hideToolbarMsg();">x</div>' +
                '<div id="##_toolbarmsg_label" class="%%-toolbarmsg-label"></div>' +
                '<div style="height:0;overflow:hidden;clear:both;"></div>' +
                '</div>' +
                '</div>' +
                '<div id="##_iframeholder" class="%%-iframeholder"></div>' +
                //modify wdcount by matao
                '<div id="##_bottombar" class="%%-bottomContainer"><table><tr>' +
                '<td id="##_elementpath" class="%%-bottombar"></td>' +
                '<td id="##_wordcount" class="%%-wordcount"></td>' +
                '<td id="##_scale" class="%%-scale"><div class="%%-icon"></div></td>' +
                '</tr></table></div>' +
                '<div id="##_scalelayer"></div>' +
                '</div>';
        },
        showWordImageDialog:function () {
            this.editor.execCommand("wordimage", "word_img");
            this._dialogs['wordimageDialog'].open();
        },
        setFullScreen:function (fullscreen) {

            var editor = this.editor,
                container = editor.container.parentNode.parentNode;
            if (this._fullscreen != fullscreen) {
                this._fullscreen = fullscreen;
                this.editor.fireEvent('beforefullscreenchange', fullscreen);
                if (baidu.editor.browser.gecko) {
                    var bk = editor.selection.getRange().createBookmark();
                }
                if (fullscreen) {
                    while (container.tagName != "BODY") {
                        var position = baidu.editor.dom.domUtils.getComputedStyle(container, "position");
                        nodeStack.push(position);
                        container.style.position = "static";
                        container = container.parentNode;
                    }
                    this._bakHtmlOverflow = document.documentElement.style.overflow;
                    this._bakBodyOverflow = document.body.style.overflow;
                    this._bakAutoHeight = this.editor.autoHeightEnabled;
                    this._bakScrollTop = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
                    if (this._bakAutoHeight) {
                        //当全屏时不能执行自动长高
                        editor.autoHeightEnabled = false;
                        this.editor.disableAutoHeight();
                    }
                    document.documentElement.style.overflow = 'hidden';
                    document.body.style.overflow = 'hidden';
                    this._bakCssText = this.getDom().style.cssText;
                    this._bakCssText1 = this.getDom('iframeholder').style.cssText;
                    this._updateFullScreen();
                } else {
                    while (container.tagName != "BODY") {
                        container.style.position = nodeStack.shift();
                        container = container.parentNode;
                    }
                    this.getDom().style.cssText = this._bakCssText;
                    this.getDom('iframeholder').style.cssText = this._bakCssText1;
                    if (this._bakAutoHeight) {
                        editor.autoHeightEnabled = true;
                        this.editor.enableAutoHeight();
                    }

                    document.documentElement.style.overflow = this._bakHtmlOverflow;
                    document.body.style.overflow = this._bakBodyOverflow;
                    window.scrollTo(0, this._bakScrollTop);
                }
                if (baidu.editor.browser.gecko) {
                    var input = document.createElement('input');
                    document.body.appendChild(input);
                    editor.body.contentEditable = false;
                    setTimeout(function () {
                        input.focus();
                        setTimeout(function () {
                            editor.body.contentEditable = true;
                            editor.selection.getRange().moveToBookmark(bk).select(true);
                            baidu.editor.dom.domUtils.remove(input);
                            fullscreen && window.scroll(0, 0);
                        }, 0)
                    }, 0)
                }
                this.editor.fireEvent('fullscreenchanged', fullscreen);
                this.triggerLayout();
            }
        },
        _updateFullScreen:function () {
            if (this._fullscreen) {
                var vpRect = uiUtils.getViewportRect();
                this.getDom().style.cssText = 'border:0;position:absolute;left:0;top:' + (this.editor.options.topOffset || 0) + 'px;width:' + vpRect.width + 'px;height:' + vpRect.height + 'px;z-index:' + (this.getDom().style.zIndex * 1 + 100);
                uiUtils.setViewportOffset(this.getDom(), { left:0, top:this.editor.options.topOffset || 0 });
                this.editor.setHeight(vpRect.height - this.getDom('toolbarbox').offsetHeight - this.getDom('bottombar').offsetHeight - (this.editor.options.topOffset || 0));

            }
        },
        _updateElementPath:function () {
            var bottom = this.getDom('elementpath'), list;
            if (this.elementPathEnabled && (list = this.editor.queryCommandValue('elementpath'))) {

                var buff = [];
                for (var i = 0, ci; ci = list[i]; i++) {
                    buff[i] = this.formatHtml('<span unselectable="on" onclick="$$.editor.execCommand(&quot;elementpath&quot;, &quot;' + i + '&quot;);">' + ci + '</span>');
                }
                bottom.innerHTML = '<div class="edui-editor-breadcrumb" onmousedown="return false;">' + this.editor.getLang("elementPathTip") + ': ' + buff.join(' &gt; ') + '</div>';

            } else {
                bottom.style.display = 'none'
            }
        },
        disableElementPath:function () {
            var bottom = this.getDom('elementpath');
            bottom.innerHTML = '';
            bottom.style.display = 'none';
            this.elementPathEnabled = false;

        },
        enableElementPath:function () {
            var bottom = this.getDom('elementpath');
            bottom.style.display = '';
            this.elementPathEnabled = true;
            this._updateElementPath();
        },
        _scale:function () {
            var doc = document,
                editor = this.editor,
                editorHolder = editor.container,
                editorDocument = editor.document,
                toolbarBox = this.getDom("toolbarbox"),
                bottombar = this.getDom("bottombar"),
                scale = this.getDom("scale"),
                scalelayer = this.getDom("scalelayer");

            var isMouseMove = false,
                position = null,
                minEditorHeight = 0,
                minEditorWidth = editor.options.minFrameWidth,
                pageX = 0,
                pageY = 0,
                scaleWidth = 0,
                scaleHeight = 0;

            function down() {
                position = domUtils.getXY(editorHolder);

                if (!minEditorHeight) {
                    minEditorHeight = editor.options.minFrameHeight + toolbarBox.offsetHeight + bottombar.offsetHeight;
                }

                scalelayer.style.cssText = "position:absolute;left:0;display:;top:0;background-color:#41ABFF;opacity:0.4;filter: Alpha(opacity=40);width:" + editorHolder.offsetWidth + "px;height:"
                    + editorHolder.offsetHeight + "px;z-index:" + (editor.options.zIndex + 1);

                domUtils.on(doc, "mousemove", move);
                domUtils.on(editorDocument, "mouseup", up);
                domUtils.on(doc, "mouseup", up);
            }

            var me = this;
            //by xuheng 全屏时关掉缩放
            this.editor.addListener('fullscreenchanged', function (e, fullScreen) {
                if (fullScreen) {
                    me.disableScale();

                } else {
                    if (me.editor.options.scaleEnabled) {
                        me.enableScale();
                        var tmpNode = me.editor.document.createElement('span');
                        me.editor.body.appendChild(tmpNode);
                        me.editor.body.style.height = Math.max(domUtils.getXY(tmpNode).y, me.editor.iframe.offsetHeight - 20) + 'px';
                        domUtils.remove(tmpNode)
                    }
                }
            });
            function move(event) {
                clearSelection();
                var e = event || window.event;
                pageX = e.pageX || (doc.documentElement.scrollLeft + e.clientX);
                pageY = e.pageY || (doc.documentElement.scrollTop + e.clientY);
                scaleWidth = pageX - position.x;
                scaleHeight = pageY - position.y;

                if (scaleWidth >= minEditorWidth) {
                    isMouseMove = true;
                    scalelayer.style.width = scaleWidth + 'px';
                }
                if (scaleHeight >= minEditorHeight) {
                    isMouseMove = true;
                    scalelayer.style.height = scaleHeight + "px";
                }
            }

            function up() {
                if (isMouseMove) {
                    isMouseMove = false;
                    editor.ui._actualFrameWidth = scalelayer.offsetWidth - 2;
                    editorHolder.style.width = editor.ui._actualFrameWidth + 'px';

                    editor.setHeight(scalelayer.offsetHeight - bottombar.offsetHeight - toolbarBox.offsetHeight - 2);
                }
                if (scalelayer) {
                    scalelayer.style.display = "none";
                }
                clearSelection();
                domUtils.un(doc, "mousemove", move);
                domUtils.un(editorDocument, "mouseup", up);
                domUtils.un(doc, "mouseup", up);
            }

            function clearSelection() {
                if (browser.ie)
                    doc.selection.clear();
                else
                    window.getSelection().removeAllRanges();
            }

            this.enableScale = function () {
                //trace:2868
                if (editor.queryCommandState("source") == 1)    return;
                scale.style.display = "";
                this.scaleEnabled = true;
                domUtils.on(scale, "mousedown", down);
            };
            this.disableScale = function () {
                scale.style.display = "none";
                this.scaleEnabled = false;
                domUtils.un(scale, "mousedown", down);
            };
        },
        isFullScreen:function () {
            return this._fullscreen;
        },
        postRender:function () {
            UIBase.prototype.postRender.call(this);
            for (var i = 0; i < this.toolbars.length; i++) {
                this.toolbars[i].postRender();
            }
            var me = this;
            var timerId,
                domUtils = baidu.editor.dom.domUtils,
                updateFullScreenTime = function () {
                    clearTimeout(timerId);
                    timerId = setTimeout(function () {
                        me._updateFullScreen();
                    });
                };
            domUtils.on(window, 'resize', updateFullScreenTime);

            me.addListener('destroy', function () {
                domUtils.un(window, 'resize', updateFullScreenTime);
                clearTimeout(timerId);
            })
        },
        showToolbarMsg:function (msg, flag) {
            this.getDom('toolbarmsg_label').innerHTML = msg;
            this.getDom('toolbarmsg').style.display = '';
            //
            if (!flag) {
                var w = this.getDom('upload_dialog');
                w.style.display = 'none';
            }
        },
        hideToolbarMsg:function () {
            this.getDom('toolbarmsg').style.display = 'none';
        },
        mapUrl:function (url) {
            return url ? url.replace('~/', this.editor.options.UEDITOR_HOME_URL || '') : ''
        },
        triggerLayout:function () {
            var dom = this.getDom();
            if (dom.style.zoom == '1') {
                dom.style.zoom = '100%';
            } else {
                dom.style.zoom = '1';
            }
        }
    };
    utils.inherits(EditorUI, baidu.editor.ui.UIBase);
//    new EditorUI();
})();