$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.address').flexigrid({
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '客户成交服务',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				transactionServiceID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (transactionServiceID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + transactionServiceID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (transactionServiceID == "") {
					alert('请选择！');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#transactionServiceID')
						.val(transactionServiceID);
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/transactionservice/ea_delTransactionService.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();

					$("tr#" + transactionServiceID).remove();
					transactionServiceID = "";

					token = 11;
				}
				break;

			case '导出' :
				var url = basePath
						+ "ea/transactionservice/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + transactionServiceID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/transactionservice/ea_getListTransactionService.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
				transactionServiceID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/transactionservice/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
	$(".address tr[id]").dblclick(function() {
				action("修改");
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input#tosave").click(function() {
		$t = $("table#stafftable");
		if ($("select#userName", $t).attr("value") == "") {
			alert("请选择人员！");
			return;
		}
		if (transactionServiceID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/transactionservice/ea_addTransactionService.jspa?pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 1;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/transactionservice/ea_addTransactionService.jspa?pageNumber="
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/transactionservice/ea_getListTransactionService.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
