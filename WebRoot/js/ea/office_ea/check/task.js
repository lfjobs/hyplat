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
				title : '巡检任务',
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
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '确认任务',
					bclass : 'edit',
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
				getPlanByCompany("");
				$("#postAddForm div#title").text("添加");
				$("#postAddForm #taskitemtbody").html("");
				$("#postAddForm .img").show();
				$("#toSubmit").show();
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :
				if (checktaskid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + checktaskid);
				$p.find("span[id]").each(function() {
							if (this.id == "checkplanid") {
								getPlanByCompany($(this).text());
							}

							$t.find(":input#" + this.id).val($(this).text());
						});

				$("#postAddForm div#title").text("修改");
				if ($p.find("span[id=taskstatus]").text() != 0) {
					$("#postAddForm .img").hide();
					$("#toSubmit").hide();
				} else {
					$("#postAddForm .img").show();
				}
				// 获取任务项;
				taskitem(checktaskid);

				$("#jqModelAdd").jqmShow();
				break;
			case '确认任务' :
				if (checktaskid == "") {
					alert("请选择");
					return;
				}
				$p = $("tr#" + checktaskid);
				var confimerid = $p.find("span[id=confimerid]").text();
				if (confimerid != "") {
					alert("已确认任务");
					return;
				}

				if (confirm("确认任务？")) {
					$t = $("table#addTable");
					$t.find("input#checktaskid").val(checktaskid);
					$("#postAddForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/safetycheck/ea_confirmTask.jspa?pageNumber="
											+ pageNumber);
					document.postAddForm.submit.click();
					document.postAddForm.reset();
					token = 2;

				}
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
						+ "/ea/safetycheck/ea_task.jspa?search=" + search
						+ "&pageSize=" + pageSize;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				checktaskid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				checktaskid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 添加提交
	$("#toSubmit").click(function() {
		$(".put3").trigger("blur");
		if ($("#postAddForm .error").length > 0) {
			return;
		}
		if ($("#postAddForm #taskitemtbl").html() == "") {
			alert("请添加任务项");
			return;
		}

		$("#postAddForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/safetycheck/ea_addTask.jspa?pageNumber="
						+ pageNumber);
		document.postAddForm.submit.click();
		document.postAddForm.reset();
		token = 2;
	});

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/safetycheck/ea_toSearchTask.jspa?pageNumber="
						+ pageNumber);
		document.postSearchForm.submit.click();
	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_task.jspa?pageNumber=" + pageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

// 选择人员
function importGY(url) { // 打开页面
	$("#daoRu").attr("src", basePath + url);

	$("#socialJqm").jqmShow();

}

function DaoruConfirm() {// 选择确定
	var type = window.frames["daoRu"].type;
	if (type == "task") {
		var FormObj = document.getElementById("daoRu").contentWindow;
		var checks = FormObj.document.getElementsByName("a");
		var str = "";
		for (var i = 0; i < checks.length; i++) {
			if (checks[i].checked == true) {
//				if ($("#socialJqm #addtype").val() == "edit") {
//					$("#taskitemid").val($("#taskitemid").val()
//							+ checks[i].value + ",");
//				}
				var carNum = window.frames["daoRu"].$('tr#'
						+ checks[i].value).find("span#carNum").text();
				str += "<tr><td align='left'>"
						+ carNum
						+ "<input type='hidden' name='pointbean.carNum' value='"
						+ carNum
						+ "' /><input type='hidden' name='pointbean.devicetypeid' value='"
						+ checks[i].value
						+ "' /></td><td align='center'><input type='button' onclick='deletetaskitem(this,\"\",\"\")' class='delete' /></td></tr>";

			}
		}
		$("#taskitemtbody").append(str);
	} else {
		var childopertionID = window.frames["daoRu"].opertionID;
		if (childopertionID == "") {
			alert("请选择");
			return;
		}

		var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
				.find("span#staffName").text();
		$("#postAddForm #principalname").val(staffName);
		$("#postAddForm #principal").val(childopertionID);
	}
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

// 获取当前公司的所有巡检计划
function getPlanByCompany(checkplanid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_getPlanByCompany.jspa?data="
			+ Math.random();
	$.ajax({

				url : url,
				type : "get",
				dateType : "json",
				async : false,
				success : function(data) {
					var member = eval("(" + data + ")");
					var planlist = member.planlist;
					var str = "<option value=''>选择所属巡检计划</option>";
					for (var i = 0; i < planlist.length; i++) {
						var obj = planlist[i];
						str += "<option value='" + obj.checkplanid + "'>"
								+ obj.checkplanname + "</option>"
					}

					$("#postAddForm #planid").html(str);
				},
				error : function(data) {
					alert("获取计划失败");
				}

			});

	if (checkplanid != "") {
		$("#postAddForm select#planid option[value=" + checkplanid + "]").attr(
				"selected", "selected");
	}
}

// 添加修改时删除任务项
function deletetaskitem(obj, devicetypeid,checktaskid) {
	if (devicetypeid != "") {
		var url = basePath
				+ "/ea/safetycheck/sajax_ea_deleteTaskitemByCarID.jspa";
		$.ajax({
					url : url,
					type : "get",
					async : true,
					dataType : "json",
					data : {
						"pointbean.devicetypeid" : devicetypeid,
						"taskbean.checktaskid":checktaskid
					},
					success : function(data) {
						$(obj).parent().parent().remove();
					},
					error : function(data) {
						alert("删除任务项失败");
					}
				});
	} else {

		$(obj).parent().parent().remove();
	}
}

// 获取任务项
function taskitem(checktaskid) {
	var url = basePath + "/ea/safetycheck/sajax_ea_taskitem.jspa?data="
			+ Math.random();
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data : {
			"taskbean.checktaskid" : checktaskid
		},
		success : function(data) {
			var member = eval("(" + data + ")");
			var pointlist = member.pointlist;
			var str = "";
			for (var i = 0; i < pointlist.length; i++) {
				var obj = pointlist[i];
				str += "<tr><td align='left'>"
						+ obj.carNum
						+ "<input type='hidden' value='"
						+ obj.devicetypeid
						+ "' /></td><td align='center'><input type='button' onclick='deletetaskitem(this,\""
						+ obj.devicetypeid + "\",\""+checktaskid+"\")' class='delete' /></td></tr>";
			}
			if (pointlist == 0) {
				str = "";
			}
			$("#postAddForm #taskitemtbody").html(str);
			$("#socialJqm #addtype").val("edit");

		},
		error : function(data) {
			alet("获取任务项失败");
		}

	});
}
