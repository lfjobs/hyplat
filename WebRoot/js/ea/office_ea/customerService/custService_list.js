$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "客户服务列表",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
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
				}, {
					name : '打印预览',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印',
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
				document.location.href = basePath
						+ "ea/custService/ea_toCashierPage.jspa?pageNumber="
						+ pNumber;
				break;
			case '删除' :
				$form = $("#CashierBillsform");
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				var status = $("tr#" + cashierBillsID)
						.find("span#consultStatus").text();
				if (status != '' && status != '草稿') {
					alert("已归档不能删除该条数据！");
					return;
				}
				if (confirm("确定删除？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/custService/ea_delCashierBills.jspa?search="
											+ search + "&pageNumber=" + pNumber);
					$form.find("input#cashierBillsID").val(cashierBillsID);
					document.CashierBillsform.submit.click();
					$("tr#" + cashierBillsID).remove();
					cashierBillsID = "";
					token = 11;
				}
				break;
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "ea/custService/ea_toCashierPage.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value");
				break;
			case '打印预览' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				var status = $("tr#" + cashierBillsID)
						.find("span#consultStatus").text();
				if (status == '草稿') {
					alert("草稿不能打印预览!");
					return;
				}
				window
						.open(basePath
								+ "ea/custService/ea_toprintCashierService.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
				break;
			case '打印' :
				$("#DaYin").jqmShow();
				break;

			/*
			 * case '导出': url =
			 * basePath+"ea/custService/ea_showExcel.jspa?search="+search+"&sdate="+sdate+"&edate="+edate;
			 * open(url); break;
			 */

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/custService/ea_getCashierToPage.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				status = $("tr#" + cashierBillsID).find("span#consultStatus")
						.text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "ea/custService/ea_toSearchByCondition.jspa?pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});

	$("#toDaYin").click(function() { // 查询打印确定
				$td = $("td.serviceDayin");
				var staffNameID = $td.children('select').val();
				var sdate = $(".sdate").val();
				var edate = $(".edate").val();
				$("td.sd").find(".error").remove();
				$("td.ed").find(".error").remove();
				if (sdate == '' && edate != '') {
					$("td.sd")
							.append("<span class=\"error\"><a class=\"tex\">起始时间不能为空!</a></span>");
					return;
				}
				if (sdate != '' && edate == '') {
					$("td.ed")
							.append("<span class=\"error\"><a class=\"tex\">结束时间不能为空!</a></span>");
					return;
				}
				window
						.open(basePath
								+ "ea/custService/ea_toprintCashierServiceAll.jspa?staffNameID="
								+ staffNameID + "&pageNumber=" + pNumber
								+ "&pageForm.pageNumber="
								+ $("#pageNumber").attr("value") + "&sdate="
								+ sdate + "&edate=" + edate);
				$("#DaYin").jqmHide();
			});

	var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
	//var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	//var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);

	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
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
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					$t = $("table#SearchTable");
					var ts = new TreeSelector(
							$t.find("select#departmentID")[0], data, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
});

function re_load() {
	document.location.href = basePath
			+ "ea/custService/ea_getCashierToPage.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate=" + edate;
}
function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#consultStatus").text();
	if (statusfj != '' && statusfj != '草稿') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}