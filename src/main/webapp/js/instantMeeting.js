$(function() {
	initEvents();
	loadAgendasFromCache();//从redis缓存中获取所有议程
	menu();
	//makeSelectedList();
	uploadMeetingFile();//上传会议材料
	startMeeting();//发起会议
});

var addAgendaLayer,editLayer,participantLayer;

function initEvents(){
	$("#addAgenda").click(function(){
		var time = $(".addM input[name='time']").val();
		var staff = $(".addM input[name='staff']").val();
		var content = $(".addM textarea[name='content']").val();
		
		$.ajax({
			url:baseurl+'/agendaController/addAgendaForCache.action',
			type:'POST',
			data:{time:time,staff:staff,content:content},
			async:true,
			cache:false,
			success:function(result){
				result = JSON.parse(result);
				if(result){
					if(result.code==0){
						layer.msg('添加会议议程成功!', {
						    time: 2000,
						    skin:'succ',//20s后自动关闭
						   offset:'0'
						});
						loadAgendasFromCache();
						layer.close(addAgendaLayer);
					}else{
						layer.msg('添加会议议程失败!', {
						    time: 2000,
						    skin:'fail',//20s后自动关闭
						   offset:'0'
						});
					}
				}
			}
		});
	});
	$("#editAgenda").click(function(){
		var time = $(".eddM input[name='time']").val();
		var staff = $(".eddM input[name='staff']").val();
		var content = $(".eddM input[name='content']").html();
		$.ajax({
			url:baseurl+'/agendaController/modifyAgendaFromCache.action',
			type:'POST',
			data:{time:time,staff:staff,content:content},
			async:true,
			cache:false,
			success:function(result){
				result = JSON.parse(result);
				if(result){
					if(result.code==0){
						//layer.alert("编辑会议议程成功!");
						layer.msg('编辑会议议程成功!', {
						    time: 2000,
						    skin:'succ',//20s后自动关闭
						   offset:'0',
						   
						  });
					}else{
						layer.msg('编辑会议议程失败!', {
						    time: 2000,
						    skin:'fail',//20s后自动关闭
						   offset:'0',
						});
						//layer.alert("编辑会议议程失败!");
					}
				}
			}
		});
	});
	
	$(document).delegate('.icon-remove',"click",function(){
		var id = $(this).parent().attr("id");
		layer.confirm('确定删除该议程', 
			{
			  btn: ['删除','取消'] ,
			  title: false,
			  closeBtn: 0
			  //按钮
			}, function(){
				delAgendaFromCache(id);
			}, function(){
			  /*layer.msg('删除失败', {
			    time: 2000,
			    skin:'fail',//20s后自动关闭
			   offset:'0',
			});*/
			});	
	});

	$('.mettAdd').click(function(){
		$(".addM input[name='time']").val("");
		$(".addM input[name='staff']").val("");
		$(".addM textarea[name='content']").val("");
		addAgendaLayer = layer.open({
			type:1,
			//shade:false,
			title:'添加会议议程',
			content:$('.addM'),
			area:['570px','420px'],
			offset:'t'
			
		})
	})
	$(document).delegate(".mettedit","click",function(){
		getAgendaFromCache($(this));
		editLayer = layer.open({
			type:1,
			//shade:false,
			title:'编辑会议议程',
			content:$('.eddM'),
			area:['570px','420px'],
			offset:'t'
			
		})
	})
	$('.cancel').click(function(){
		
		layer.closeAll();
		
	})

	$('.addAttendee').click(function() {

		// 弹出面板
		participantLayer = layer.open({
			type: 1,
			title: '添加参会人员',
			content: $('.addMettMain'),
			area: ['780px', '600px'],
	
		});
	})
	
	$('#editAgenda').click(function() {
		editAgenda();
	})
	
	$('#treeUl li span').click(function() {
		_this = $(this).parent().parent()
		if(!$(_this).hasClass('choose')) {
			$(_this).addClass('choose').clone().prependTo("#selectedList");
		}

	});
	/*$('#selectedList').on('click', '.icon-remove', function() {
		var _id = $(this).parent().parent().attr('id');
		alert(_id);
		$(this).parent().parent().remove();

		$('#' + _id).removeClass('choose');
	})*/

	$('.treeUl ul').hide();
	$('.treeUl ul:first').show();
	$('.treeUl p i').click(function() {
		$(this).parent().next().toggle();
		$(this).toggleClass("icon-minus");
		event.stopPropagation();
	
	});
	
	//会议时长进度条选择效果 add by yubing
	$('.progress').mousedown(function(e){
		var leftw=36;
		Pageleft = $(".progress").offset().left;
		left = parseInt(e.pageX - Pageleft);
		$(".progress").children('.bar').css("width",Math.ceil(left/leftw)*leftw);	
		$('.tooltip-inner').text(Math.ceil(left/leftw)*0.5+"小时");
		$('.bar').css("width",Math.ceil(left/leftw)*leftw)
		if(Math.ceil(left/leftw)*leftw>0){
			$('.tooltip').css("left",(Math.ceil(left/leftw)*leftw)-leftw/2);
			}else{
			$('.tooltip').css("left",(Math.ceil(left/leftw)*leftw));
			}

		$("#meetingTimeLen").val(Math.ceil(left/leftw)*0.5); // 记录几小时
		left=Math.ceil(left/leftw)*leftw;
		if(left>=400){
		//$('.tooltip').css("margin-top",-72);
		//left=430;
		//$('.tooltip').animate({"left":left},430);
		}else{
			//$('.tooltip').css("margin-top",-42);
		//	$('.tooltip').animate({"left":left},430);
		}
	});
}

/**
 * 从redis获取会议议程详情
 * 
 * @author yubing
 */
function getAgendaFromCache(obj){
	$("#editDl").data("id",obj.parent().attr("id"));
	$.ajax({
		url:baseurl+'/agendaController/getAgendaFromCache.action',
		type:'POST',
		data:{agendaId:obj.parent().attr("id")},
		async:true,
		cache:false,
		success:function(result){
			result = JSON.parse(result);
			if(result){
				if(result.code==200){
					var receipt = result.receipt;
					if(receipt){
						var agenda = receipt.agenda;
						$(".eddM input[name='time']").val(agenda.formatTime);
						$(".eddM input[name='staff']").val(agenda.staff);
						$(".eddM textarea[name='content']").val(agenda.content);
					}
				}else{
					//layer.alert("获取会议议程失败!");
					layer.msg('获取会议议程失败!', {
					    time: 2000,
					    skin:'fail',//20s后自动关闭
					   offset:'0',
					});
				}
			}
		}
	});
}

/**
 * 根据sessionId和议程ID从radis缓存中删除议程。
 * 
 * @author yubing
 * @param obj
 */
function delAgendaFromCache(id){
	$.ajax({
		url:baseurl+'/agendaController/delAgendaFromCache.action',
		type:'POST',
		data:{agendaId:id},
		async:true,
		cache:false,
		success:function(result){
			result = JSON.parse(result);
			if(result){
				if(result.code==200){
					layer.msg('删除成功', {
						  time: 2000,
						  skin:'succ',//20s后自动关闭
						  offset:'0',
					});
					loadAgendasFromCache();
				}else{
					if(result.message){
						layer.msg(result.message, {
						    time: 2000,
						    skin:'fail',//20s后自动关闭
						   offset:'0',
						});
					}else{
						layer.msg('删除失败!', {
						    time: 2000,
						    skin:'fail',//20s后自动关闭
						   offset:'0',
						});
					}
				}
			}
		}
	});
}

/**
 * 从redis缓存中获取所有议程
 * 
 * @author yubing
 */
function loadAgendasFromCache(){
	$.ajax({
		url:baseurl+'/agendaController/getAgendasFromCache.action',
		type:'POST',
		async:true,
		cache:false,
		success:function(result){
			result = JSON.parse(result);
			if(result){
				if(result.code==200){
					var receipt = result.receipt;
					if(receipt){
						var agendas = receipt.agendas;
						buildAgendas(agendas);
					}
				}else{
					layer.msg('加载会议议程失败!', {
					    time: 2000,
					    skin:'fail',//20s后自动关闭
					   offset:'0',
					});
				}
			}
		}
	});
}

/**
 * 构建会议议程列表
 * 
 * @author yubing
 * @param agendas
 */
function buildAgendas(agendas){
	if(agendas){
		$("#agendaList").empty();
		var agendaStr = "";
		for(var i in agendas){
			agendaStr = '<li id="'+agendas[i].agendaId+'"><span class="s1">'+agendas[i].formatTime
				+'</span><span class="s2">'+agendas[i].staff+'</span><span class="s3">'+agendas[i].content
				+'</span><span class="s4 icon-edit mettedit"></span><span class="s5 icon-remove"></span></li>';
			$("#agendaList").append(agendaStr);
		}
	}
}
function showNoMetting(){
	var value =  $("#selectedList li").length;
	if(value!=null&&typeof(value)!=undefined&&value>0){
		$('#noMetting').hide();
	}else{
		$('#noMetting').show();
	}
}
var node_list_map = new Map();
function checkDepartIdExist(departId,mobile){
	var show = node_list_map.get(departId);
	var check_result = true;
	if(show==null||typeof(show)==undefined){
		if(mobile==undefined){
			mobile = null;
		}
		$.ajax({
			url:'departmentController/checkDepartId.action',
			type:'POST',
			data:{departId:departId,mobile:mobile},
			async:false,
			cache:false,
			success:function(result){
				result = JSON.parse(result);
				if(result){
					var receipt = result.receipt;
					if(receipt){
						var obj = receipt.parentDepartIds;
						alert(obj);
						if(obj!=null&&obj!=""&&obj.length>1){
								for(var i=0;i<obj.length;i++){
									var value = node_list_map.get(obj[i]);
									if(value!=null&&typeof(value)!=undefined){
										check_result =  false;
										break;
									}
								}
							}
							/*if(parentId==null||typeof(parentId)==undefined){
								check_result =  true;
							}*/
						}
					}
			},
			error:function(){
				check_result = false;
			}
		});
		
	}else if(show=="-1"){
		check_result = true;
	}else{
		check_result = false;
	}
	return check_result;
}
function deleteChild(childId){
	
}
function addToParant(departId){
	
}
function add(departId){
	
}
function childmenu(departId){
	var show = node_list_map.get("ul"+departId);
	if(show==null||typeof(show)==undefined){
		$.ajax({
			url:'departmentController/tree.action',
			type:'POST',
			data:{departId:departId},
			async:true,
			cache:false,
			success:function(result){
				result = JSON.parse(result);
				if(result){
					var receipt = result.receipt;
					if(receipt){
						var obj = receipt.tree;
						var data = eval('(' + obj + ')');
						var ul = "<ul>";
						if(data!=null){
							for(var i = 0; i < data.length; i++) {
								/*ul += "<li id='li" + data[i].Id + "'><p>";
								if(data[i].mobile==''){
									ul+="<i class='icon-plus' id='i"+data[i].Id+"'></i>"
								}else{
									ul+="<img src='plugins/assets/avatars/avatar.png' class='fl'>";
								}
								ul+="<span  id='" + data[i].Id + "'>" + data[i].title;
								if(data[i].mobile!=''&&data[i].mobile.length>1){
									ul+=  "&nbsp;:"+data[i].mobile;
								}
								ul += "</span>";
								ul += "<i class='icon-remove fr'></i></p>";
								ul += "</li>";*/
								ul += "<li id='li" + data[i].Id + "'><p>";
								if(data[i].mobile==''){
									ul+="<i class='icon-plus' id='i"+data[i].Id+"'></i>"
								}else{
									ul+="<img src='plugins/assets/avatars/avatar.png' class='fl'>";
								}
								ul+="<span  id='" + data[i].Id + "' class='search1'>" + data[i].title;
								if(data[i].mobile!=''&&data[i].mobile.length>1){
									ul+=  "&nbsp;:"+data[i].mobile;
								}
								ul +="</span>";
								ul +="<i class='icon-remove fr'></i></p>";
								ul += "</li>";
							};
						}
						ul += "</ul>";
						$("#li"+departId).append(ul);
						$("i.icon-plus").unbind("click").click(
			    			    function(){
			    			       var departId = $(this).attr("id");
			    			       childmenu(departId.substring(1,departId.length));
			    			   		$(this).parent().next().toggle();
			    			   		$(this).toggleClass("icon-minus");
			    			   		event.stopPropagation();
			    			    }
			    		);
						$('#treeUl li span').unbind("click").click(function() {
							_this = $(this).parent().parent();
							var _id = _this.attr('id');
							var this_id = $(this).attr('id');
							var text = $(this).text();
							var text_array = text.split(":");
							var mobile = '';
							if(text_array!=null){
								mobile = text_array[1];
							}
							if(checkDepartIdExist(this_id,mobile)){
								if(!$(_this).hasClass('choose')) {
									$(_this).addClass('choose').clone().prependTo("#selectedList");
									$("#"+_id+" li").each(function(){
										$(this).addClass('choose');
									});
								}else{
									alert("该对象已添加，请勿重复添加");
								}
								node_list_map.put(this_id,'2');
								showNoMetting();
							}else{
								alert("该对象已添加，请勿重复添加");
							}
						})
						$('#selectedList').off('click'); 
						$('#selectedList').on('click', '.icon-remove', function() {
						var _id = $(this).parent().parent().attr('id');
						var this_id = _id.substring(2);
						var node_value = node_list_map.get(this_id);
						if(node_value!=null&&typeof(node_value)!=undefined){
							node_list_map.remove(this_id);
						}else{
							node_list_map.put(this_id,'-1');
						}
						$(this).parent().parent().remove();
						$("#"+_id+" li").each(function(){
						    $(this).removeClass('choose');
						  });
						$('#' + _id).removeClass('choose');
						showNoMetting();
						});
						node_list_map.put("ul"+departId,'1');
					}
				}
			},
			error:function(){
			}
		});
	}
}

function menu() {
	var departId = 0;
	$.ajax({
		url:'departmentController/tree.action',
		type:'POST',
		data:{departId:departId},
		async:true,
		cache:false,
		success:function(result){
			result = JSON.parse(result);
			if(result){
				var receipt = result.receipt;
				if(receipt){
					var obj = receipt.tree;
					var data = eval('(' + obj + ')');
					var ul = "<ul>";
					if(data!=null){
						for(var i = 0; i < data.length; i++) {
							ul += "<li id='li" + data[i].Id + "'><p>";
							if(data[i].mobile==''){
								ul+="<i class='icon-plus' id='i"+data[i].Id+"'></i>"
							}else{
								ul+="<img src='plugins/assets/avatars/avatar.png' class='fl'>";
							}
							ul+="<span  id='" + data[i].Id + "' class='search1'>" + data[i].title;
							if(data[i].mobile!=''&&data[i].mobile.length>1){
								ul+=  "&nbsp;:"+data[i].mobile;
							}
							ul +="</span>";
							ul +="<i class='icon-remove fr'></i></p>";
							ul += "</li>";
						};
					}
					ul += "</ul>";
					$("#treeUl").html(ul);
					$("i.icon-plus").click(
		    			    function(){
		    			       var departId = $(this).attr("id");
		    			       childmenu(departId.substring(1,departId.length));
		    			   		$(this).parent().next().toggle();
		    			   		$(this).toggleClass("icon-minus");
		    			   		event.stopPropagation();
		    			   		
		    			    }
		    		);
					$('#treeUl li span').unbind("click").click(function() {
						_this = $(this).parent().parent()
						var _id = _this.attr('id');
						var this_id = $(this).attr('id');
						var text = $(this).text();
						var text_array = text.split(":");
						var mobile = '';
						if(text_array!=null){
							mobile = text_array[1];
						}
						if(checkDepartIdExist(this_id,mobile)){
							if(!$(_this).hasClass('choose')) {
								$(_this).addClass('choose').clone().prependTo("#selectedList");
								$("#"+_id+" li").each(function(){
									$(this).addClass('choose');
								});
							}else{
								alert("该对象已添加，请勿重复添加");
							}
							node_list_map.put(this_id,'2');
							showNoMetting();
						}else{
							alert("该对象已添加，请勿重复添加");
						}
					});
					$('#selectedList').off('click'); 
					$('#selectedList').on('click', '.icon-remove', function() {
						var _id = $(this).parent().parent().attr('id');
						var this_id = _id.substring(2);
						var node_value = node_list_map.get(this_id);
						if(node_value!=null&&typeof(node_value)!=undefined){
							node_list_map.remove(this_id);
						}else{
							node_list_map.put(this_id,'-1');
						}
						$("#"+_id+" li").each(function(){
						    $(this).removeClass('choose');
						  });
						$(this).parent().parent().remove();
						$('#' + _id).removeClass('choose');
						showNoMetting();
					});
				}
			}
		},
		error:function(){
		}
	});
	
}

/**
 * 会议议程编辑
 * 
 * @author yubing
 */
function editAgenda(){
	var agendaId = $("#editDl").data("id")
	var time = $(".eddM input[name='time']").val();
	var staff = $(".eddM input[name='staff']").val();
	var content = $(".eddM textarea[name='content']").val();
	$.ajax({
		url:baseurl+'/agendaController/modifyAgendaFromCache.action',
		type:'POST',
		data:{agendaId:agendaId,time:time,staff:staff,content:content},
		async:true,
		cache:false,
		success:function(result){
			result = JSON.parse(result);
			if(result){
				if(result.code==200){
					layer.msg('编辑会议议程成功!', {
						  time: 2000,
						  skin:'succ',//20s后自动关闭
						  offset:'0',
					});
					loadAgendasFromCache();
					layer.close(editLayer);
				}else{
					layer.msg('编辑会议议程失败!', {
					    time: 2000,
					    skin:'fail',//20s后自动关闭
					   offset:'0',
					});
				}
			}
		},
		error:function(){
			layer.msg('编辑会议议程失败!', {
			    time: 2000,
			    skin:'fail',//20s后自动关闭
			   offset:'0',
			});
		}
	});
}

/**
 * 上传会议材料
 * 
 * @author yubing
 */
function uploadMeetingFile(){
	$("#uploadMeetingFile").bind('change',  function(event) {
		var $content=$("#uploadMeetingFile").val();
		var $contentFile=$content.split('\\');//注split可以用字符或字符串分割 
		var $Filecontent=$contentFile[$contentFile.length-1];//这就是要取得的图片名称 
		$("#fileName").html($Filecontent);
	});
}


var canImport = true;
function doImport(){
	if(!canImport){
		return;
	}
	if($.trim($("#importFile").val()) == ""){
		layer.msg('请选择需要导入的文件!', {
		    time: 2000,
		    skin:'fail',//20s后自动关闭
		   offset:'0',
		});
		return;
	}
	//设置导入按钮不可用
	canImport = false;
	// $("#importBtn").attr({
	// 	background: '#BFBFBF',
	// 	value:'正在导入'
	// });
	
	//$("#importBtn").css('background', '#BFBFBF');
	
	$("#uploadBtn").text("正在导入");
	//$("#importResult").css('color', '#666');
	//$("#importForm").submit();
	importForm.submit();
}

function finishImport(){
	canImport = true;
	//$("#importBtn").css('background', '#4DCD70');		
	$("#uploadBtn").text("上传材料");
}

/**
 * 发起会议
 */
function startMeeting(){
	$(".mettingBtn").click(function(){
		var subject = $("input[name='subject']").val();
		var relaLong = $("input[name='meetingTimeLen']").val();
		var sponsorType = 1;
		var mediaType = 0;
		
		$.ajax({
			url:baseurl+'/meetingController/startMeeting.action',
			type:'POST',
			data:{subject:subject,relaLong:relaLong,sponsorType:sponsorType,mediaType:mediaType},
			async:true,
			cache:false,
			success:function(result){
				result = JSON.parse(result);
				if(result){
					if(result.code==200){
						layer.msg('发起会议议程成功!', {
							  time: 2000,
							  skin:'succ',//20s后自动关闭
							  offset:'0',
						});
					}else{
						if(result.code==999){//资源不足
							
						}else{
							layer.msg('发起会议失败!', {
							    time: 2000,
							    skin:'fail',//20s后自动关闭
							   offset:'0',
							});
						}
					}
				}
			},
			error:function(){
				layer.msg('发起会议失败!', {
				    time: 2000,
				    skin:'fail',//20s后自动关闭
				   offset:'0',
				});
			}
		});
	});
}