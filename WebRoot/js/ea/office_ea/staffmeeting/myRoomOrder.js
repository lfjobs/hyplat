$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '会议室预约管理——我的预约',
				minheight : 80,
				buttons : type != "inner" ? ([{
					name : '预约',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改预约',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '取消预约',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '会议室管理',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]) : ([{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}])
			});
	function action(com, grid) {
		switch (com) {
			case '预约' :
				document.location.href = basePath
						+ "ea/meetingroom/ea_OrderMeetingRoom.jspa";
				break;
			case '修改预约' :
				if (mroomoID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + mroomoID);
				var time = "";
				var endtime = "";
				// 开始天
				var startday = "";
				var endday = "";
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
							if (this.id == "startTime") {
								time = $(this).text();
								$("#starttime").val(time);
							}

							if (this.id == "endTime") {
								endtime = $(this).text();
							}

							if (this.id == "startDate") {
								startday = $(this).text();
							}

							if (this.id == "endDate") {
								endday = $(this).text();
							}
							if (this.id == "mroomID") {
								$("#mroomIDs").val($(this).text());
							}

						});

				getEndtime(startday, endday, time, "1.0");
				endtimelist(startday, endday, time);
				if (endtime != "") {
					$("#endtime").val(endtime);
				}
				$("#jqModel").jqmShow();
				break;
			case '取消预约' :
				if (mroomoID == "") {
					alert('请选择！');
					return
				}

				var url = basePath
						+ "ea/meetingroom/sajax_ea_checkCancelOrder.jspa";
				$.ajax({
					url : url,
					type : "get",
					aysnc : false,
					dataType : "json",
					data : {
						"mroomOrder.mroomoID" : mroomoID
					},
					success : function(data) {
						var mem = eval("(" + data + ")");
						var result = mem.result;
						if (result == "suc") {
							$("#cstaffForm #mroomoID").val(mroomoID);
							if (confirm("是否取消？")) {
								$("#cstaffForm")
										.attr("target", "hidden")
										.attr(
												"action",
												basePath
														+ "ea/meetingroom/ea_cancelMyOrder.jspa");
								document.cstaffForm.submit.click();
								token = 2;
							}
						} else {

							alert("该会议室已被使用");
							return;
						}
					},
					error : function(data) {
						alert("验证是否可以取消失败");
					}

				});

				break;
			case '会议室管理' :
				document.location.href = basePath
						+ "ea/meetingroom/ea_getMeetingRoomList.jspa";
				break;

			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/meetingroom/ea_getMyRoomOrder.jspa?search="
						+ psearch + "&type=" + type;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				mroomoID = this.id;
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				mroomoID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			
			
		// 时分
	$("#starttime").change(function() {

				var starttime = $(this).val();
				// 开始天
				var startday = $(".startdate").val();
				var endday = $(".enddate").val();
				endtimelist(startday, endday, starttime);

			});

	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				// 先判断是否预约冲突冲突的话显示冲突的会议不冲突提交预约
				var url = basePath + "ea/meetingroom/sajax_ea_isConflict.jspa";
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						startDates : $(".startdate").val(),
						endDates : $(".enddate").val(),
						"mroomOrder.startTime" : $("#cstaffForm #starttime").val(),
						"mroomOrder.endTime" : $("#cstaffForm #endtime").val(),
						"mroomOrder.mroomID" : $("#cstaffForm #mroomIDs").val(),
						"mroomOrder.mroomoID" : $("#cstaffForm .mroomoID").val()
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
	// 冲突页面返回操作
	$(".back").click(function() {
				$("#jqModelConflict").jqmHide();
				$("#jqModel").jqmShow();

			});

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/meetingroom/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

	/**
	 * 
	 * 提交取消会议
	 */
	$("#toCancel").click(function() {
		$("#postCancelForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/smeeting/ea_toSaveTiao.jspa?pageNumber="
						+ ppageNumber);
		document.postCancelForm.submit.click();
		token = 2;

	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/meetingroom/ea_getMyRoomOrder.jspa?search=" + psearch
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&type=" + type;
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
	var defaultval = (hour1 == 9 ? "09" : hour1) + ":" + m1 + "(" + day
			+ hourss + "小时)";
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


function getstat() {
	var startday = $("#cstaffForm .startdate").val();
	var endday = $("#cstaffForm .enddate").val();
	var dt1 = new Date(startday.replace("-", "/").replace("-", "/"));
	var dt2 = new Date(endday.replace("-", "/").replace("-", "/"));
	if (dt1 > dt2) {
		$("#cstaffForm .enddate").val(startday)
		endday = startday;
	}

	var starttime = $("#cstaffForm #starttime").val();
	endtimelist(startday, endday, starttime);

}


