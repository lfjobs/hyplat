$(function() {
	
	ajaxPreOrder();
	$('.ordertbl tr:even').css('background', '#F0FFF0');
	$('.ordertbl tr:odd').css('background', 'FFFFFF');

	$('.ordertbl tr:even').addClass("ou");
	$('.ordertbl tr:odd').addClass("ji");

	$('.ordertbl th').css('background', 'lightblue');

	$(".ordertbl").find("td").mouseover(function() {

				$(this).css("backgroundColor", "#FFBBFF");
				$(this).parent().find(".time").css("backgroundColor", "#FFBBFF");

			});

	$(".ordertbl").find("td").mouseout(function() {
				if ($(this).attr("class") != "time") {
                   
					
					if($(this).attr("id")==""){
					
					
					if ($(this).parent().attr("class") == "ou") {
						
						$(this).css("background", "#F0FFF0"); // 偶数行的颜色
					} else {
					
						$(this).css("background", "white"); // 奇数行的颜色
					}
					}else{
						$(this).css("background", "green"); //换为选中
						
					}
				} else {
						$(this).css('background', 'lightblue');
					

				}
				$(this).parent().find(".time").css("background", "lightblue");
			});

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
              //先判断是否预约冲突冲突的话显示冲突的会议不冲突提交预约
				var url = basePath + "ea/meetingroom/sajax_ea_isConflict.jspa";
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						startDates : $("#startDate").val(),
						endDates : $("#endDate").val(),
						"mroomOrder.startTime" : $("#starttime").val(),
						"mroomOrder.endTime" : $("#endtime").val(),
						"mroomOrder.mroomID" : $("#roomselect").val(),
						"mroomOrder.mroomoID" : $("#mroomoID").val()
					},
					success : function(data) {
						var me = eval("(" + data + ")");
						var orderlist = me.result;

						if (orderlist.length != 0) {
							var str = "";
							var obj;
							for (var i = 0; i < orderlist.length; i++) {
								obj = orderlist[i]
								str += "<tr><td>" + obj.meetingName
										+ "</td><td>" + obj.staffName
										+ "</td><td>" + obj.startDays
										+ "</td><td>" + obj.endDays
										+ "</td></tr>";
							}

							$("#confictorder").html(str);
							$("#jqModelConflict").jqmShow();
							$("#jqModel").jqmHide();

						} else {
							$("#cstaffForm")
									.attr("target", "hidden")
									.attr(
											"action",
											basePath
													+ "ea/meetingroom/ea_saveOrderRoom.jspa");
							document.cstaffForm.submit.click();
							document.cstaffForm.reset();
							token = 2;
						}
					},
					error : function(data) {
						alert("预约失败");
					}

				});
				return;

			});
	 //冲突页面返回操作
	$(".back").click(function() {
				$("#jqModelConflict").jqmHide();
				$("#jqModel").jqmShow();

			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/informbills/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

	$("td.preOrder").click(function() {
		$(".JQuerySubmit").show();
	   $("#cstaffForm #mroomoID").val("");
	  $("#cstaffForm #mroomoKey").val("");
		document.cstaffForm.reset();
		var endtime;
		var mroomoID = $(this).attr("id");
		if (mroomoID != "") {
			var url = basePath + "ea/meetingroom/sajax_ea_getOrderInfo.jspa";

			$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					"mroomOrder.mroomoID" : mroomoID
				},
				success : function(data) {
					var mem = eval("(" + data + ")");
					var mroomOrder = mem.mroomOrder;
					var update = mem.update;
					$("#cstaffForm #meetingName").val(mroomOrder.meetingName);
					$("#cstaffForm #roomselect").val(mroomOrder.mroomID);
					$("#cstaffForm #meetingTheme").val(mroomOrder.meetingTheme);
					$("#cstaffForm #mroomoID").val(mroomOrder.mroomoID);
					$("#cstaffForm #mroomoKey").val(mroomOrder.mroomoKey);
					$("#cstaffForm #datessss").val(mroomOrder.dates);
				    $("#cstaffForm #startDate").val(mroomOrder.startDates);
				    $("#cstaffForm #endDate").val(mroomOrder.endDates);
				    $("#cstaffForm #starttime").val(mroomOrder.startTime);
				     endtime = mroomOrder.endTime;
				    
					if (update == "view") {
						$(".JQuerySubmit").hide();
					} else {
						$(".JQuerySubmit").show();
					}

				},
				error : function(data) {
					alert("获取修改信息失败");
				}

			});

		}

		var time = $(this).parent().find("td.time").text();

		$("#starttime").val($.trim(time));

	    var tdSeq = $(this).parent().find("td").index($(this)[0]);
	    var roomID = $("tr:eq(0) th:eq(" + tdSeq + ")").attr("id");



		if($("#startDate").val()==""){
		$("#startDate").val($("#datess").val());// 开始日期
		$("#endDate").val($("#datess").val());// 结束日期
		
		}
		// 开始天
		var startday = $("#startDate").val();
		var endday = $("#endDate").val();
		
		
		getEndtime(startday, endday, time, "1.0");
		endtimelist(startday, endday, time);
        if(endtime!=""){
		$("#endtime").val($.trim(endtime));
        }
        
		$("#roomselect").val(roomID);
		$("#jqModel").jqmShow();

	});

	// 时分
	$("#starttime").change(function() {

				var starttime = $(this).val();
				// 开始天
				var startday = $("#startDate").val();
				var endday = $("#endDate").val();
				endtimelist(startday, endday, starttime);

			});

});

function jump(dateType) {
	var dates = $("#dates").val();
	document.location.href = basePath
			+ "ea/meetingroom/ea_OrderMeetingRoom.jspa?dates=" + dates
			+ "&dateType=" + dateType;

}

// 获取结束时间 根据开始时间和间隔时间
function getEndtime(startday, endday, starttime, interval) {

	var hour = starttime.substr(0, starttime.indexOf(":"));
	var m = starttime.substr(starttime.indexOf(":") + 1);
	var hour1 = "";
	var m1 = "";
	var inter;
	var rval;
	if (interval.indexOf(".") == -1) {
		interval = interval + ".0";
	}

	inter = interval.substr(0, interval.indexOf("."));
	rval = interval.substr(interval.indexOf(".") + 1);

	var duo = 0;
	if (m == "00") {
		if (rval == "0") {

			m1 = "00";
		} else {
			m1 = "30"
		}
	} else {
		if (rval == "0") {
			m1 = "30";
		} else {
			duo = 1;
			m1 = "00"
		}

	}

	hour1 = Number(hour) + Number(inter) + duo;
	startday = startday + " " + starttime + ":00";
	endday = endday + " " + hour1 + ":" + m1 + ":00";

	var startdays = startday.replace(/-/g, "/");
	var enddays = endday.replace(/-/g, "/");
	var dt1 = new Date(startdays);
	var dt2 = new Date(enddays);
	var diff = parseInt(dt2.getTime() - dt1.getTime());
	var days = diff / 3600000 / 24; // 天的时间间隔
	var hourss = diff / 3600000 % 24;

	var dayss = Math.floor(days);
	var day = "";
	if (dayss != 0) {
		day = dayss + "天";
	}
	var defaultval = (hour1==9?"09":hour1) + ":" + m1 + "(" + day + hourss + "小时)";
	return defaultval;

}

// 获取结束时间 根据开始时间和间隔时间
function getEndtime2(startday, endday, starttime, end) {

	startday = startday + " " + starttime + ":00";
	endday = endday + " " + end;

	var startdays = startday.replace(/-/g, "/");
	var enddays = endday.replace(/-/g, "/");
	var dt1 = new Date(startdays);
	var dt2 = new Date(enddays);
	var diff = parseInt(dt2.getTime() - dt1.getTime());
	var days = diff / 3600000 / 24; // 天的时间间隔
	var hourss = diff / 3600000 % 24;

	var dayss = Math.floor(days);
	var day = "";
	if (dayss != 0) {
		day = dayss + "天";
	}

	var defaultval = end.substring(0, end.length - 3) + "(" + day + hourss
			+ "小时)";
	return defaultval;

}

function endtimelist(startday, endday, starttime) {
	var endtime = $("#endtime").val();
	var defaults = "";

	var str = "";
	// 拼了
	if (startday == endday) {
		for (var i = 0.5; i <= 14;) {
			var tim = getEndtime(startday, endday, starttime, i + "");
			var realtim = tim.substring(0, tim.indexOf("("));
			str += "<option value=" + realtim + ">" + tim + "</option>";
			if (realtim == endtime) {
				defaults = endtime;
			}
			i = i + 0.5;
			if (tim.indexOf("22:30") != -1) {
				break;
			}
		}
	} else {
		var h = 9;
		var ms = "00";
		for (var j = 1; j <= 28; j++) {

			if (j % 2 == 0) {
				ms = "30";

			} else {
				ms = "00";
				if (j != 1) {
					h++;
				}
			}
			var end = (h == 9 ? "09" : h) + ":" + ms + ":00";
			var tim = getEndtime2(startday, endday, starttime, end);
			var realend = end.substring(0, end.length - 3);
			str += "<option value=" + realend + ">" + tim + "</option>";
			if (realend == endtime) {
				defaults = endtime;
			}

		}

	}
	$("#endtime").html(str);
	$("#endtime").val(defaults);
}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/meetingroom/ea_OrderMeetingRoom.jspa?dates="+$("#dates").val()+"&dateType=cur";
	}
}

// ajax加载预约信息
function ajaxPreOrder() {
	var url = basePath + "ea/meetingroom/sajax_ea_ajaxPreOrder.jspa";
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					dates : $("#datess").val()

				},
				success : function(data) {
					
					
					
					var today = $("#dates").val();
					var mem = eval("(" + data + ")");
					var orderlist = mem.orderlist;
					var obj;
						for (var i = 0; i < orderlist.length; i++) {
							obj = orderlist[i];
					     var startTime = obj.startTime;
					     var endTime = obj.endTime;
					 
                         var startDate = obj.startDate;
                         var endDate = obj.endDate;
                         var startDates = obj.startDates;
                         var endDates = obj.endDates;
                         var mroomID = obj.mroomID
                         var trSeqS = $("#"+startTime.replace(":","")).parent().parent().find("tr").index($("#"+startTime.replace(":","")).parent()[0]);//获取开始时间所在行
          
                         var trSeqE = $("#"+endTime.replace(":","")).parent().parent().find("tr").index($("#"+endTime.replace(":","")).parent()[0]);//获取开始时间所在行
                         if(trSeqE=="-1"){
                        	trSeqE="28";
                         }
                         var tdSeqM = $("#"+mroomID).parent().find("th").index($("#"+mroomID)[0]);//当前对象处于第几列1开始
                         var startDatess = new Date(startDates.replace("-","/").replace("-", "/"));
                         var endDatess = new Date(endDates.replace("-","/").replace("-", "/"));
                         var todayss = new Date(today.replace("-","/").replace("-", "/"));
                         if(startDates==today&&startDatess<endDatess){
                        	trSeqE = Number($("#2200").parent().parent().find("tr").index($("#2200").parent()[0]))+1;//获取开始时间所在行
                           
                        }
                        if(startDatess<todayss&&todayss<endDatess){
                        	trSeqS = $("#0900").parent().parent().find("tr").index($("#0900").parent()[0]);//获取开始时间所在行
                        	trSeqE = Number($("#2200").parent().parent().find("tr").index($("#2200").parent()[0]))+1;//获取开始时间所在行
                        }
                        
                         if(today==endDates&&startDates!=endDates){
                         	trSeqS = $("#0900").parent().parent().find("tr").index($("#0900").parent()[0]);//获取开始时间所在行
                        	trSeqE = $("#"+endTime.replace(":","")).parent().parent().find("tr").index($("#"+endTime.replace(":","")).parent()[0]);//获取开始时间所在行
                        }
                         
                         
                         var diff = trSeqE-trSeqS;
                     
                    
                         //获得当前单元格（开始时间）
                      
                        var tre = Number(trSeqS);
                        var tre2 = Number(trSeqE);
                        var tde = Number(tdSeqM);
          
                         for (var z = tre+1; z < tre2; z++) {
                        	  $("tr:eq("+z+") td:eq("+tde+")").hide();
                              
                             
                         }
                      	          
                      
                         $("tr:eq("+tre+") td:eq("+tde+")").text($.trim(obj.meetingName)+"(预约人:"+obj.staffName+")")
							.attr("id", obj.mroomoID)
							.css("background-color", "green")
							.css("border-bottom", "0")
							.attr("rowspan", diff);
                        
							
						}
						
						
                      

                       
                       
                      


				},
				error : function(data) {
					alert("获取预约信息失败");
				}

			});

}

function getstat() {
	var startday = $("#startDate").val();
	var endday = $("#endDate").val();
	var dt1 = new Date(startday.replace("-", "/").replace("-", "/"));
	var dt2 = new Date(endday.replace("-", "/").replace("-", "/"));
	if (dt1 > dt2) {
		$("#endDate").val(startday)
		endday = startday;
	}

	var starttime = $("#starttime").val();
	endtimelist(startday, endday, starttime);

}


function back(){
    document.location.href=basePath+"ea/meetingroom/ea_getMyRoomOrder.jspa";
}

