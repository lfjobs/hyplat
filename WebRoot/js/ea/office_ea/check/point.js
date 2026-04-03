$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '检查点',
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'prev',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '发芯片',
					bclass : 'edit',
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
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '返回' :
				document.location.href = basePath
						+ "/page/ea/main/navigation/logistics_Car.jsp";
				break;

			case '添加' :
				document.postAddForm.reset();
				$("#postAddForm div#title").text("添加");
				$("#clicktaskitem").val(" 添加检查项 ");
				$("#postAddForm #pointitemtbl").html("");
				$("#postAddForm .pointitem").hide();
				$("#toSubmit").show();
				$("#jqModelAdd").jqmShow();
				$("#item").removeClass("item");
				$("#jqModelAdd").css("width", "400px");
				$("#postAddForm #loadcab")
						.attr(
								"src",
								basePath
										+ "/page/ea/main/office_ea/archives/loadActiveX.html?code="
										+ Math.random());
				break;
			case '修改' :
				if (checkpointid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + checkpointid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				getPointitem(checkpointid);
				if ($p.find("span[id=useflag]").text() != 0) {
					$t.find("#toSubmit").hide();
				}
				$("#postAddForm div#title").text("修改");
				$("#clicktaskitem").val(" 添加检查项 ");
				$t.find("input#oldchipids").val($t.find("input#chip").val());
				$("#jqModelAdd").jqmShow();
				$("#postAddForm #loadcab")
						.attr(
								"src",
								basePath
										+ "/page/ea/main/office_ea/archives/loadActiveX.html?code="
										+ Math.random());
				break;
			case '发芯片' :
				var devicetypeid = $("input:radio:checked[name=a]").val();
				if (devicetypeid == undefined) {
					alert("请选择");
					return;
				}
				$("#jqModelChip").jqmShow();

				getPointforChip(devicetypeid)
				break;

			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				window.location.href = basePath
						+ "/ea/safetycheck/ea_point.jspa?search=" + search
						+ "&pageSize=" + pageSize;
				break;
		}
	}

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				id = this.id;

				if (id.indexOf("car") > -1) {
					devicetypeid = id;
					if ($(".level2_" + devicetypeid).length > 0) {
						if ($(".level2_" + devicetypeid).is(":hidden")) {
							$(".level2_" + devicetypeid).show();
						} else {
							$(".level2_" + devicetypeid).hide();
							$(".level3_" + devicetypeid).hide();
						}
					} else {
						showPointList(devicetypeid);
					}
				} else if (id.indexOf("pointid") > -1) {
					checkpointid = id;
					if ($(".level3_" + checkpointid).length > 0) {
						if ($(".level3_" + checkpointid).is(":hidden")) {
							$(".level3_" + checkpointid).show();
						} else {
							$(".level3_" + checkpointid).hide();
						}
					} else {
						showPointItemList(checkpointid);
					}
				} else {
					return;
				}

			});

	// 添加提交
	$("#toSubmit").click(function() {
		$(".put3").trigger("blur");
		$parent = $("#caritemtbody").parent().parent().parent();
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($("#caritemtbody > tr").length == 0) {
			$parent
					.append("<span class=\"error\"><a class=\"tex\">请选择车辆</a></span>");
			return;
		} else {
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");

		}

		$("#postAddForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/safetycheck/ea_addPoint.jspa?pageNumber="
						+ pageNumber);
		document.postAddForm.submit.click();
		// document.postAddForm.reset();
		token = 2;
	});

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/safetycheck/ea_toSearchPoint.jspa?pageNumber="
						+ pageNumber);
		document.postSearchForm.submit.click();
	});

	$("#clicktaskitem").click(function() {
		$("#clicktaskitem").val(" 继续添加检查项 ");
		$("#postAddForm .pointitem").show();
		var str = "<tr><td align=\"right\" style=\"width: 30%;\">"
				+ "检查项名称：</td>"
				+ "<td align=\"left\">"
				+ "<input type=\"text\" name=\"pointitembean.checkpointitemname\" id=\"checkpointitemname\" />"
				+ "<input type=\"hidden\" name=\"pointitembean.checkpointitemid\" id=\"checkpointitemid\" value=\"add\" />"
				+ "&nbsp;&nbsp;<img src='"
				+ basePath
				+ "/images/gtk-del.png' class=\"delete\" onclick='deletepointitem(this,\"\")'/></td></tr>";

		$("#pointitemtbl").append($(str));
		$("#item").addClass("item");
		$("#jqModelAdd").css("width", "700px");

	});

	// 提交发芯片

	$("#submitchip").click(function() {
		$("#postChipForm .chip").trigger("blur");
		if ($("#postChipForm .error").length > 0) {
			return;
		}
		$("#postChipForm").attr("target", "hidden").attr("action",
				basePath + "/ea/safetycheck/ea_despatchChip.jspa");
		document.postChipForm.submit.click();
		token = 2;

	});

	// 检查点编辑提交
	$("#submitpoint").click(function() {
		$("#postPointEditForm").attr("target", "hidden").attr("action",
				basePath + "/ea/safetycheck/ea_submitEdit.jspa");
		document.postPointEditForm.submit.click();
		token = 2;

	});

	// 检查项编辑提交
	$("#submititem").click(function() {
		$("#postPointItemEditForm").attr("target", "hidden").attr("action",
				basePath + "/ea/safetycheck/ea_submitEdit.jspa");
		document.postPointItemEditForm.submit.click();
		token = 2;
	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_point.jspa?pageNumber=" + pageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

// 选择车辆
function importGY(url) { // 打开页面
	$("#daoRu").attr("src", basePath + url + "?date=" + new Date());

	$("#socialJqm").jqmShow();
}

function DaoruConfirm() {// 选择确定
	var FormObj = document.getElementById("daoRu").contentWindow;
	var checks = FormObj.document.getElementsByName("a");

	var str = "";
	var length = 0;
	for (var i = 0; i < checks.length; i++) {
		if (checks[i].checked == true) {
			$("#caritemid").val($("#caritemid").val() + checks[i].value + ",");
			var carNum = window.frames["daoRu"].$('tr#' + checks[i].value)
					.find("span#carNum").text();
			str += "<tr><td align='left'>"
					+ carNum
					+ "<input type='hidden' name='pointbean.carNum' value='"
					+ carNum
					+ "' /></td><td align='center'><input type='button' onclick='deletecaritem(this,\"\")' class='delete' /></td></tr>";

			length++;
		}
	}
	if (length == 0) {
		alert("请选择");
		return;
	}
	$("#caritemtbody").append(str);
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}// 添加时删除任务项

function deletecaritem(obj, devicetypeid) {
	$(obj).parent().parent().remove();
}

// 获取检查项
function getPointitem(checkpointid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_getPointItemList.jspa?data="
			+ Math.random();
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data : {
			checkpointid : checkpointid
		},
		success : function(data) {
			var mem = eval("(" + data + ")");
			var pointitemlist = mem.pointitemlist;
			var str = ""
			for (var i = 0; i < pointitemlist.length; i++) {
				var obj = pointitemlist[i];
				str += "<tr><td align='right' style='width:30%;'>检查项名称：</td><td align='left'><input type='text' name='pointitembean.checkpointitemname' value='"
						+ obj.checkpointitemname
						+ "'>"
						+ "<input type='hidden' name='pointitembean.checkpointitemid' value='"
						+ obj.checkpointitemid
						+ "' />&nbsp;&nbsp;<img src='"
						+ basePath
						+ "/images/gtk-del.png' class=\"delete\" onclick='deletepointitem(this,\""
						+ obj.checkpointitemid
						+ "\")' class='delete' /></td></tr>";

			}
			if (str != "") {
				$("#item").addClass("item");
				$("#jqModelAdd").css("width", "700px");
				$("#postAddForm .pointitem").show();
				$("#pointitemtbl").html(str);
			} else {
				$("#item").removeClass("item");
				$("#jqModelAdd").css("width", "400px");
				$("#postAddForm .pointitem").hide();
			}

		},
		error : function(data) {
			alert("获取检查项失败");
		}

	});
}

// 添加时删除检查项
function deletepointitem(obj, pointitemid) {
	if (pointitemid != "") {
		$("#deletepointitemid").val($("#deletepointitemid").val() + pointitemid
				+ ",");
	}
	$(obj).parent().parent().remove();
	if ($("#pointitemtbl:has(tr)").length == 0) {
		$("#postAddForm .pointitem").hide();
		$("#item").removeClass("item");
		$("#jqModelAdd").css("width", "400px");
	}
}

// 判断芯片号是否重复
function checkChip(chipid) {
	if ($("#postAddForm #oldchipids").val() == chipid) {
		return true;
	}
	var bool = null;
	var url = basePath + "/ea/safetycheck/sajax_ea_IsChipRepeat.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					"pointbean.chip" : chipid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {// 重复
						bool = false;
					} else {
						bool = true;// 不重复
					}

				},
				error : function(data) {
					alert("读取数据失败");
				}

			});

	return bool;
}

// 单击车辆信息展出检查点
function showPointList(devicetypeid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_pointListByCar.jspa?date="
			+ new Date();
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data : {
			"pointbean.devicetypeid" : devicetypeid
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pointlist = member.pointlist;
			var str = "";
			var obj;
			for (var i = 0; i < pointlist.length; i++) {
				obj = pointlist[i];
				// 克隆一份并且插入被克隆那行的前面
				$("tr#" + devicetypeid).after(

						$("#kelong").clone(true).attr("id", obj.checkpointid)
								.addClass("level2_" + devicetypeid));
				$tr = $("tr#" + obj.checkpointid);
				$tr.find("span#checkpointname").text(obj.checkpointname);
				$tr.find("span#chip").text(obj.chip);
				$tr
						.find("div.operate")
						.html("<a class='editdelete' onclick=\"edit('"
								+ obj.checkpointid
								+ "')\" href='#'><img width='16' height='16' border='0' title='编辑检查点' src =\""
								+ basePath
								+ "/images/ea/main/edit.gif\" /></a>"
								+ "&nbsp;&nbsp;<a class='editdelete' href='#'"
								+ "onclick=\"deleteLevel('"
								+ obj.checkpointid
								+ "')\"><img width='16' height='16' border='0' title='删除检查点'  src =\""
								+ basePath
								+ "/images/ea/main/gtk-del.png\" /></a>");
				if (obj.useflag == 0) {
					$tr.find("span#useflag").text("已启用");
				} else {
					$tr.find("span#useflag").text("已停用");
				}

			}

			$(".level2_" + devicetypeid).show();
		},
		error : function(data) {
			alert("根据车获取检查点失败");
		}

	});

}

// 单击检查点信息展出检查点项
function showPointItemList(checkpointid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_getPointItemList.jspa?date="
			+ new Date();
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data : {
			"checkpointid" : checkpointid
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pointitemlist = member.pointitemlist;
			var carID = member.carID;
			var str = "";
			var obj;
			for (var i = 0; i < pointitemlist.length; i++) {
				obj = pointitemlist[i];
				// 克隆一份并且插入被克隆那行的前面
				$("tr#" + checkpointid).after(

						$("#kelong2").clone(true).attr("id",
								obj.checkpointitemid).addClass("level3_"
								+ checkpointid).addClass("level3_" + carID));
				$tr = $("tr#" + obj.checkpointitemid);
				$tr.find("span#checkpointitemname")
						.text(obj.checkpointitemname);
				$tr
						.find("div.operate")
						.html("<a onclick=\"edit('"
								+ obj.checkpointitemid
								+ "')\" href='#'><img width='16' height='16' border='0' title='编辑检查项' src =\""
								+ basePath
								+ "/images/ea/main/edit.gif\" /></a>"
								+ "&nbsp;&nbsp;<a class='editdelete' href='#'"
								+ "onclick=\"deleteLevel('"
								+ obj.checkpointitemid
								+ "')\"><img width='16' height='16' border='0' title='删除检查项'  src =\""
								+ basePath
								+ "/images/ea/main/gtk-del.png\" /></a>");

			}

			$(".level3_" + checkpointid).show();
		},
		error : function(data) {
			alert("根据检查点获取检查项失败");
		}

	});

}

function edit(id) {
	// 用于阻止编辑的冒泡行为；
	$("a.editdelete").click(function(event) {
				event.stopPropagation();
			});
	if (id.indexOf("pointid") > -1) {
		$("#jqModelEditPoint").jqmShow();
		$("#pointname").val($("tr#" + id).find("span#checkpointname").text());
		$("#pointid").val(id);

		var useflag = $("tr#" + id).find("span#useflag").text();
		if (useflag == "已启用") {
			$("#useflags").val(0);
		} else {
			$("#useflags").val(1);
		}

	} else {
		$("#jqModelEditItem").jqmShow();
		$("#pointitemname").val($("tr#" + id).find("span#checkpointitemname")
				.text());
		$("#pointitemid").val(id);
	}
}

function deleteLevel(id) {
	// 用于阻止删除单击的冒泡行为；
	$("a.editdelete").click(function(event) {
				event.stopPropagation();
			});
	if (confirm("确定删除？")) {
		var url = basePath + "/ea/safetycheck/sajax_ea_deletelevel.jspa?date="
				+ new Date();
		$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					data : {
						"id" : id
					},
					success : function(data) {
						token = 2;
						re_load();

					},
					error : function(data) {
						alert("删除失败");
					}

				});
	}
}

// 发芯片获取该车的所有检查点
function getPointforChip(devicetypeid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_pointListByCar.jspa?date="
			+ new Date();
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data : {
			"pointbean.devicetypeid" : devicetypeid
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pointlist = member.pointlist;
			var str = "";
			var obj;
			for (var i = 0; i < pointlist.length; i++) {
				obj = pointlist[i];
				str += "<tr><td align='right'>"
						+ obj.checkpointname
						+ "：</td><td><input type='hidden' name='pointbean.checkkey' value='"
						+ obj.checkkey
						+ "'><input type='text' size='25'  id = "
						+ i
						+ " name='pointbean.chip' value='"
						+ obj.chip
						+ "'/><input type='hidden' id='old"
						+ i
						+ "' value='"
						+ obj.chip
						+ "'/></td><td align='left'><input type='button' onclick ='readChip("
						+ i
						+ ")' class='input-button chip' value='  读取  ' onblur = 'chipblur("
						+ i + "," + pointlist.length + ")' /></td></tr>";
			}
			$("#fchip").html(str);

		},
		error : function(data) {
			alert("根据车获取检查点失败");
		}

	});

}
// 读取芯片
function readChip(i) {
	$("#chippos").val(i);
	$("#jqModelChip #loadcab")
			.attr(
					"src",
					basePath + "/page/ea/common/loadActiveX.html?code="
							+ Math.random());

}

function readChiping(values) {
	var i = $("#chippos").val();
	$("input#" + i).val(values);

}

// 判断芯片重复用的事件
function chipblur(i, length) {
	$input = $("input#" + i);
	$parent = $input.parent().next("td");
	var inputValue = $input.attr("value");
	$parent.find(".error").remove();
	$parent.find(".corect").remove();
	$parent.find(".tooltip").remove();
	if (inputValue != "") {
		var oldchip = $("input#old" + i).val();
		if (inputValue != oldchip) {
			for (var j = 0; j < length; j++) {
				if (j != i) {
					var value = $("input#" + j).val();
					if (value != "") {
						if (inputValue != value) {
							if (!checkChip(inputValue)) {
								$parent
										.append("<span class=\"error\"><a class=\"tex\">已使用</a></span>");
								break;
							}
						} else {
							$parent
									.append("<span class=\"error\"><a class=\"tex\">已使用</a></span>");
							break;
						}
					}
				}
			}
		} else {
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
			return;
		}

	} else {
		$parent
				.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
	}

}
