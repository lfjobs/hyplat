$(function() {
	var prID = "";
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
				title : '员工请假单',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印预览',
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
			case '添加' :
				prID = "";
				$(".yincang").show();
				$(".xianshi").hide();
				$("#leaveForm").find(".error").remove();
				$("#leaveForm").find(".corect").remove();
				$("input.JQuerypersonvalue").attr("checked", false);
				document.leaveForm.reset();

				getID();
				$("#jqModel").jqmShow();
				break;
			case '查看' :
				if (prID == "") {
					alert("请选择!");
					return;
				}
				$("td.disName").find(".error").remove();
				$("td.disName").find(".corect").remove();
				document.leaveForm.reset();

				$("#leaveDays", $("#leaveForm")).attr("disabled", false).attr(
						"readonly", "readonly");
				$("#leaveHour", $("#leaveForm")).attr("disabled", false).attr(
						"readonly", "readonly");

				$t = $("div#jqModel");
				$p = $("tr#" + prID);
				$p.find("span[id]").each(function() {
							$t.find("#" + this.id).text($(this).text());
						});

				$(".yincang").hide();
				$(".xianshi").show();

				$("#accessoryName").hide(); // 附件浏览隐藏
				acceName = $p.find("#look1").text();
				if ($p.find("#look1").text() != '') {
					var onload = acceName.substring(acceName.lastIndexOf("."),
							acceName.length);
					if (onload.toLowerCase() != ".jpg"
							&& onload.toLowerCase() != ".gif"
							&& onload.toLowerCase() != ".png") {
						$("#isLoad")
								.find("a")
								.attr(
										"href",
										basePath
												+ "ea/publicreceipts/ea_downFile.jspa?downLoadPath="
												+ acceName);
						$("#isLoad").show(); // 附件下载显示
					} else {
						$("#isLook").show(); // 附件查看显示
					}
				} else {
					$("#isNull").show(); // 附件无显示
				}

				$("input.JQuerySubmit").hide(); // 提交按钮隐藏
				$("#jqModel").jqmShow();
				$("#leaveReason1",$("#leaveForm")).blur();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/publicreceipts/ea_showLeaveExcel.jspa?search="
						+ psearch;
				open(url);
				break;
			case '打印预览' :
				if (prID == "") {
					alert("请选择！");
					return;
				}
				window.open(basePath
						+ "ea/publicreceipts/ea_printLeaveBill.jspa?prID="
						+ prID);
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/publicreceipts/ea_getLeaveBillsList.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}

	// 新加内容开始 target 指向页面隐藏Hidden
	$("input.JQuerySubmit").click(function() { // 提交审核
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if ($("#leaveStartDate").val() == ''
						|| $("#leaveEndDate").val() == '') {
					alert("请假日期不能为空！");
					return;
				}
				if ($("td#leaveTypes").find("input:checked").length == 0) {
					alert("请选择请假类别！");
					return;
				}
				if ($("#leaveReason").val() == '') {
					alert("请假原因不能为空！");
					return;
				}
				$("#leaveForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/publicreceipts/ea_saveLeaveBills.jspa?pageNumber="
										+ ppageNumber);
				document.leaveForm.submit.click();
				document.leaveForm.reset();
				token = 1;
				getID();
			});

	// 双击查看开始
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				$("#leaveDays", $("#leaveForm")).attr("disabled", "disabled");
				$("#leaveHour", $("#leaveForm")).attr("disabled", "disabled");

				$("span.hideAll").hide(); // 附件显示隐藏
				$("input.JQuerySubmit").show(); // 提交按钮显示
				re_load();
			});

	$(".close").click(function() {// 关闭
				$("#jqModel").jqmHide();
				$("#leaveDays", $("#leaveForm")).attr("disabled", "disabled");
				$("#leaveHour", $("#leaveForm")).attr("disabled", "disabled");

				$("span.hideAll").hide(); // 附件显示隐藏
				$("input.JQuerySubmit").show(); // 提交按钮显示
				re_load();
			});
	// 双击查看结束

	// 双击查看弹出框中打印预览按钮事件
	$("input.JQueryprint").click(function() {
		window.open(basePath + "ea/publicreceipts/ea_printLeaveBill.jspa?prID="
				+ prID);
	});

	$(".JQueryflexme tr[id]").each(function() { // 页面遍历判断附件格式
				var load = $("tr#" + this.id).find("#look1").text();// 取出附件在服务器上存放地址
				if (load != '') {
					var onload = load.substring(load.lastIndexOf("."),
							load.length);
					if (onload.toLowerCase() != ".jpg"
							&& onload.toLowerCase() != ".gif"
							&& onload.toLowerCase() != ".png") {
						$("tr#" + this.id).find("#load").show();
					} else {
						$("tr#" + this.id).find("#look").show();
					}
				} else {
					$("tr#" + this.id).find("#wu").show();
				}
			});
	$(".JQueryflexme tr[id]").click(function() {// 选择按钮事件
				prID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {// 查询请求事件
				$("#postSearchForm")
						.attr(
								"action",
								basePath
										+ "ea/publicreceipts/ea_toLeaveSearch.jspa?pageNumber="
										+ ppageNumber);
				document.postSearchForm.submit.click();
			});

	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

	$("#principalOrganizationID", $("#leaveForm")).change(function() {
				parm = $(this).val();
			});

	$("#leaveStartDate", $("#leaveForm")).blur(function() { // 起时间失去焦点事件
				$("td.errortime").find(".corect").remove();

				var start = $("#leaveStartDate", $("#leaveForm")).val()
						.replace(/-/g, '');
				var end = $("#leaveEndDate", $("#leaveForm")).val().replace(
						/-/g, '');
				if (end != '' && start > end) {
					alert('起时间必须小于止时间！');
					$(".errortime").find(".input").val("");
					$("#leaveStartDate", $("#leaveForm")).focus();
				}
			});

	$("#leaveEndDate", $("#leaveForm")).focus(function() { // 止时间获取焦点事件
		if ($("#leaveStartDate", $("#leaveForm")).val() == '') {
			alert("请先填写起时间！");
			$("#leaveStartDate", $("#leaveForm")).focus();
		}
	}).blur(function() { // 止时间失去焦点事件
		$("td.errortime").find(".corect").remove();

		var start = $("#leaveStartDate", $("#leaveForm")).val();
		var end = $("#leaveEndDate", $("#leaveForm")).val();
		if (end != '') {
			if (start.replace(/-/g, '') > end.replace(/-/g, '')) {
				if (times == '0') {
					alert('起时间必须小于止时间！');
					$(".errortime").find(".input").val("");
					$("#leaveStartDate", $("#leaveForm")).focus();
					times++;
				}
				if ($("#leaveStartDate", $("#leaveForm")).focus()) {
					times = '0';
				}
			} else {
				$(".errortime")
						.append("<span class=\"corect\"><a class=\"tex\">&nbsp;&nbsp;&nbsp;&nbsp;</a></span>");
				$("#leaveDays", $("#leaveForm")).val("");
				$("#leaveHour", $("#leaveForm")).val("");
				var date1 = new Date(start.replace(/-/g, "/"));
				var date2 = new Date(end.replace(/-/g, "/"));
				var date3 = parseInt((date2.getTime() - date1.getTime()));
				var daysNum = Math.floor(date3 / (1000 * 3600 * 24)); // 相差天数
				if (daysNum != '0') {
					$("#leaveDays", $("#leaveForm")).val(daysNum).attr(
							"disabled", false).attr("readonly",
							"readonly");
				}

				var hours = date3 % (24 * 3600 * 1000); // 计算天数后剩余的毫秒数
				var timeNum = (hours / (3600 * 1000)).toFixed(2); // 相差小时数
				if (timeNum != '0.00') {
					$("#leaveHour", $("#leaveForm")).val(timeNum).attr(
							"disabled", false).attr("readonly",
							"readonly");
				}
			}
		}
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/publicreceipts/ea_getLeaveBillsList.jspa?search="
				+ psearch + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

$(function() {
			var url = basePath
					+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(cc) {
							var member = eval("(" + cc + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var oList = member.organizationlist;
							var data = new Array();
							data[0] = {
								id : treeID,
								pid : '-1',
								text : treeName
							};
							var data1 = new Array();
							data1[0] = {
								id : treePID,
								pid : '-1',
								text : treePName
							};
							for (var i = 0; i < oList.length; i++) {
								data[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
								data1[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
							$t = $("div#jqModel");
							$p = $("div#jqModelSearch");
							var ts = new TreeSelector(
									$t.find("select#principalOrganizationID")[0],
									data, -1);
							ts.createTree();
							var t1 = new TreeSelector(
									$p.find("select#principalOrganizationID")[0],
									data, -1);
							t1.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
		});