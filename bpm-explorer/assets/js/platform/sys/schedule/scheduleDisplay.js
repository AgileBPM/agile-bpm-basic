var clicks = 0;//判断双击
	var preStart = 0;//判断双击
	var tooltip = $('<div/>').qtip({
		content:{
			text:'',
		},	
		}).qtip('api');
    //-----------------创建、添加日程--------------//
	function openDetail(id, action, startTime, endTime) {
    	//
		var title = action == "edit" ? "编辑日程" : action == "add" ? "添加日程" : "查看日程";
		action = action == "edit" ? "Edit.html" : action == "add" ? "Create.html" : "Get.html";
		var url="schedule" + action;
		if(!$.isEmpty(id)){
			url+='?id=' + id;
		}
		if((action=="Create.html") && !$.isEmpty(startTime) && !$.isEmpty(endTime)){
			url+='?startTime=' + startTime + "&endTime=" + endTime;
		}
		var conf = { height:680,width:800,  url:'sys/schedule/'+url,  title:title,topOpen:true};
		$.Dialog.open(conf); 
	}
    function complete(id,type,mainId) {
    	var title = "完成日程";
		
		var url="sys/schedule/scheduleComplete.html";
		if(mainId) {
			url+='?mainId=' + mainId;
			url+='&id=' + id;
		} else {
			url+='?mainId=' + id;
		}
		var conf = { height:520,width:600,  url:url,  title:title,topOpen:true };
		$.Dialog.open(conf); 

    }
    //打开任务链接
    function openTask(url) {
    	//
    	$.openFullWindow(url);

    }
    //中英文定长子串截取
    function sublen(str, length) {
        var len = 0;
        for (var i = 0; i < str.length; i++) {
            var c = str.charCodeAt(i);
            //单字节加1 
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                len++;
            }
            else {
                len += 2;
            }
            if(len > length) {
            	return str.substring(0,i-1);
            }
        }
        
    }
    //计算中英文字符串长度
    function strlen(str) {
        var len = 0;
        for (var i = 0; i < str.length; i++) {
            var c = str.charCodeAt(i);
            //单字节加1 
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                len++;
            }
            else {
                len += 2;
            }
        }
        return len;
    }
	//--------------提示框信息-------------------//
	function getEventMouseoverHtml(event){
		
		var html = '';
		var taskType = "";
		var remark = '';
	   	var len = 46;
		if(event==null){
			return html;
		}
		if(event.type == "single") {
			taskType = "个人";
		} else if (event.type == "share") {
			taskType = "共享";
		} else if (event.type == "ilka") {
			taskType = "共同任务";
		}
		if(event.remark != null && event.remark.length > 0) {
   		   if(strlen(event.remark) >len) {
   			   remark = sublen(event.remark,len) + "...";
   		   } else {
   			   remark = event.remark;
   		   }
   	   } else {
   		   remark = '';
   	   }
		//event.title.substring(0, event.title.indexOf('('))
		html = '<table  class="form-table">	';
		html +=	'<tr><th>备注:</th><td colspan="3">'+remark+'</td></tr>';
		html +=	'<tr><th>任务类型:</th><td>'+taskType+'</td><th>日程进度:</th><td>'+(event.rateProgress == null ? 0 : event.rateProgress)+'%</td></tr>';		 
		html +=	'<tr><th>提交人:</th><td>'+(event.committer == null ? " " : event.committer)+'</td><th>所属人:</th><td>'+(event.owner == null ? " " : event.owner)+'</td></tr>';		 
		
		var mainId = event.mainId || '';
		var id = event.id || '';
		if(event.rateProgress <=0) {
			html +=	'<tr><th>预计开始时间:</th><td>'+event.start._d.toLocaleString()+'</td><th>预计完成时间:</th><td>'+event.end._d.toLocaleString()+'</td></tr>';
		} else {
			html +=	'<tr><th>实际开始时间:</th><td>'+event.actualStart.toLocaleString()+'</td><th>实际完成时间:</th><td>'+event.actualEnd.toLocaleString()+'</td></tr>';
		}
		html += '<tr>'
		//没有taskUrl,并且是个人任务或参与者，可以完成任务
		if(event.taskUrl == null && (event.type == "single" || event.isMine == false)) {
			html +=	'<td align="center" colspan="2"><span class="btn  btn-sm btn-primary fa  ng-scope" onclick="complete(\''+id+'\','+'\''+event.type+'\''+',\''+mainId+''+'\')">完成</span></td>';
		//有taskUrl,并且是个人任务或参与者，可以跳转到任务链接
		} else if(event.taskUrl != null && (event.type == "single" || event.isMine == false)) {
			//
			html +=	'<td align="center" colspan="2"><span class="btn  btn-sm btn-primary fa  ng-scope" onclick="openTask(\''+event.taskUrl+'\')">完成</span></td>';
		//其他情况（非个人的所属者）不能完成
		} else {
			html +=	'<td align="center" colspan="2"><span style="background-color:red" class="btn  btn-sm btn-primary fa  ng-scope">完成</span></td>';
		}
		//所属者可以编辑
		if (event.isMine == true) {
			html +=	'<td align="center" colspan="2"><span class="btn  btn-sm btn-primary fa  ng-scope" onclick="openDetail(\''+ id+'\','+'\'edit\''+')">编辑</span></td>';
		} else {
			html +=	'<td align="center" colspan="2"><span class="btn  btn-sm btn-primary fa  ng-scope" onclick="openDetail(\''+mainId+'\','+'\'get\''+')">详情</span></td>';
		}
		//html +=	'<tr><td align="center" colspan="4"><span class="btn  btn-sm btn-primary fa  ng-scope" onclick="complete(event)">完成</span></td></tr>';
		html += '</tr>'
		html += '</table>';
        return html;
	}
  $(document).ready(function() {
	  
	  //--------------------日程表--------------------------//
     $('#calendar').fullCalendar({
         header: {
             left: 'prev,next today',
             center: 'title',
             right: 'month,agendaWeek,agendaDay'
       },
       //----------------------------汉化/设置------------------------------
       isRTL: false,
       //height: 650,
       //handleWindowResize : true,
       timeFormat: "HH:mm",
       weekends: true,
       allDaySlot: false,
       firstDay: 0,
       monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
       monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
       dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
       dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
       eventLimitText  : "更多", 
       dayPopoverFormat : "YYYY年M月d日", 
       buttonText: {
           prev: "<",
           next: ">",
           prevYear: "上一年",
           nextYear: "下一年",
           today: '返回今天',
           month: '月',
           week: '周',
           day: '日',
           listWeek:'列表'
       },
       navLinks: true, 
       selectable: true,
       selectHelper: true,
       editable: true,
       eventLimit: true,
       height: window.innerHeight,
      //-----------------------选中事件（一段时间)------------------------------
       select: function(start, end,allday) {
    	   ;
    	   //
    	   var time = end._i - start._i;
    	   var startTime = start._d.getTime()-8*60*60*1000;
				if(startTime%86400000 == 57600000) {
					startTime += 9*60*60*1000;
				}
		   var endTime = end._d.getTime()-8*60*60*1000;
		   if(endTime%86400000 == 57600000) {
			   endTime -= 6*60*60*1000;
		   }
    	   if(time == 86400000) {
    		   clicks += 1;
    		   if(clicks == 1) {
    			   preStart = start._i;
    		   }
    		   if(clicks == 2) {
        		   clicks = 0;
        		   if(start._i == preStart) {
        			   $.Dialog.confirm("创建日程","是否创建一个新日程？",function(){
               			   openDetail(undefined,'add',startTime,endTime);
                	   });
        		   }
        	   }
    		   
    	   } else {
    		   $.Dialog.confirm("创建日程","是否创建一个新日程？",function(){
       			   openDetail(undefined,'add',startTime,endTime);
        	   });
    	   }
       },
      //-----------------------------数据源--------------------------------------
       events: function(start, end, timezone, callback) {
    	   
           $.ajax({
               url: __ctx+'/calendar/schedule/getEvents',//--获取所属者日程--
               dataType: 'json',
               data: {
                   start: Date.parse(start._d),
                   end: Date.parse(end._d),
               },
               success: function(data) {
            	   //
                   var events = [];
                   i = 0;
                   while(data[i] != null) {
                	   events.push({
                           isMine:true,
                		   title: data[i].title + "(" +(data[i].rateProgress==null ? 0 : data[i].rateProgress) + "%)",
                           start: new Date(data[i].startTime), // will be parsed
                           end: new Date(data[i].endTime),
                           actualStart: data[i].actualStartTime ? new Date(data[i].actualStartTime) : "",
                           actualEnd: data[i].completeTime ? new Date(data[i].completeTime) : "",
                	       id: data[i].id,
                	       className: "height:200",
                	       rateProgress: data[i].rateProgress,
                	       owner: ((data[i].ownerName == null || data[i].ownerName == "") ? data[i].owner : data[i].ownerName),
                	       color: data[i].rateProgress >= 100 ?"#9AFF9A" : ((new Date()).getTime() > data[i].endTime ? '#FF6A6A' : '#63B8FF'),
                	       committer: (data[i].submitNamer != null ? data[i].submitNamer : data[i].submitter),
                	       taskUrl: data[i].taskUrl,
                	       openType: data[i].openType,
                	       type: data[i].type,
                	       remark: data[i].remark
                       });
                	   i++;
                   }
                   $.ajax({
                       url: __ctx+'/calendar/schedule/getParticipantEvents',//--获取参与者日程--
                       dataType: 'json',
                       data: {
                           start: Date.parse(start._d),
                           end: Date.parse(end._d),
                       },
                       success: function(data) {
                           i = 0;
                           while(data[i] != null) {
                        	   var participantSchedule = data[i];
                        	   events.push({
                                   isMine: false,
                        		   title: participantSchedule.title + "(" +(participantSchedule.rateProgress==null ? 0 : participantSchedule.rateProgress) + "%)",
                                   start: new Date(participantSchedule.startTime), // will be parsed
                                   end: new Date(participantSchedule.endTime),
                                   actualStart: participantSchedule.actualStartTime,
                                   actualEnd: participantSchedule.completeTime,
                        	       id: participantSchedule.id,
                        	       mainId: participantSchedule.scheduleId,
                        	       className: "height:200",
                        	       rateProgress:participantSchedule.rateProgress,
                        	       owner: (participantSchedule.ownerName != null ? participantSchedule.ownerName : participantSchedule.owner),
                        	       color: participantSchedule.rateProgress >= 100 ?"#9AFF9A" : ((new Date()).getTime() > participantSchedule.endTime ? '#FF6A6A' : '#FFEC8B'),
                        	       committer: (participantSchedule.submitNamer != null ? participantSchedule.submitNamer : participantSchedule.submitter),
                        	       taskUrl: participantSchedule.taskUrl,
                        	       openType: participantSchedule.openType,
                        	       type: participantSchedule.type,
                        	       remark: participantSchedule.remark
                               });
                        	   i++;
                           }
                           callback(events);
                       }
                   });
               }
           });
           
       },
       //--------------------------日程点击事件---------------------------------------
       eventClick: function( event, jsEvent, view ) {
    	   //
	   	   var topPositon = '';
	   	   var bottomPosition = '';
	   	   var title = '';
	   	   var len = 33;
	   	   if(event.title != null && event.title.length > 0) {
	   		   title = event.title.substring(0, event.title.indexOf('('));
	   		   if(title.length > len) {
	   			   title = title.substring(0,len) + "...";
	   		   } 
	   	   }
    	   if(event.start._d.getDay() > 4) {
    		   topPositon = 'top right';
    		   bottomPosition = 'top right';
    	   } else {
    		   topPositon = 'top left';
    		   bottomPosition = 'bottom left';
    	   }
    	   tooltip =  $(this).qtip({  
				content:{
					text:'',
					title:{
						text:title
					}
				},
		        position: { my: topPositon, at: bottomPosition,adjust:{method:'flipinvert'}},
		        show: {
		            effect: function() {
		                $(this).slideDown();
		            }
		        },
		        hide: {
		            effect: function() {
		                $(this).slideUp();
		            },
		            fixed: true,
	                delay: 500
		        },
		        style: {  classes : 'qtip-default  qtip qtip-bootstrap qtip-shadow',width:500,height: 280 },	
	 		}).qtip('api');
    	  var html = getEventMouseoverHtml(event);
    	  tooltip.set({
				'content.text': html
			})
			.reposition(event).show(event);
     	  /* if(event.start._d.getDay() > 4) {
     		
     	  } else {
     		
     	  } */
       },
       dayClick: function( date, allDay ) { 
    	   tooltip.hide();
    	   tooltip.disable();
       },
/*     	   
    	   var html = getEventMouseoverHtml(event);
     	  $(this).qtip({  
 				content:{
 					text:html,
 					title:{
 						text:"日程信息"			
 					}
 				},
 		        position: { my: 'top center', at: 'bottom center'},
 		        show: {
 		            effect: function() {
 		                $(this).slideDown();
 		            }
 		        },
 		        hide: {
 		            effect: function() {
 		                $(this).slideUp();
 		            },
 		            fixed: true,
 	                delay: 300
 		        },
 		        style: {  classes : 'qtip-default  qtip qtip-bootstrap qtip-shadow'  },			    
 	 	});
    	   
       }, */
       //------------------------日程拖拽事件-------------------------------------
       eventDrop: function( event, delta, revertFunc, jsEvent, ui, view ) { 
   		   $.post(
	           __ctx+'/calendar/schedule/dragUpdate',
               {
                   id: event.id,
                   start: Date.parse(event.start._d),
                   end: Date.parse(event.end._d),
               }
       	   );
       },
       //-----------------------日程调整事件-----------------------
       eventResize: function( event, delta, revertFunc, jsEvent, ui, view) {
   		   $.post(
	           __ctx+'/calendar/schedule/dragUpdate',
               {
                   id: event.id,
                   start: Date.parse(event.start._d),
                   end: Date.parse(event.end._d),
               }
       	   );
       },
     });
  });     