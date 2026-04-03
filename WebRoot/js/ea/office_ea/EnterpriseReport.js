$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '企业纪实管理',
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
				reportID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (reportID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + reportID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (reportID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#reportID').val(reportID);
				if (confirm("确定删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/report/ea_delEnterpriseReport.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + reportID).remove();
					reportID = "";
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/report/ea_getEnterpriseReportList.jspa?1=1";
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				reportID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				if ($("form .error").length) {
					return;
				}
				if (reportID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/report/ea_saveEnterpriseReport.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					$("#cstaffForm").find("#staffID").trigger("change");
					token = 1;
					return;
				}
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/report/ea_saveEnterpriseReport.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				token = 2;
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/report/ea_getEnterpriseReportList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
