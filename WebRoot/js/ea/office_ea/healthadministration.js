$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '卫生管理',
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
				healthAdministrationID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (healthAdministrationID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + healthAdministrationID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (healthAdministrationID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#healthAdministrationID')
						.val(healthAdministrationID);
				if (confirm("确定继续？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/healthadministration/ea_del.jspa?pageNumber="
											+ ppageNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					$("tr#" + healthAdministrationID).remove();
					healthAdministrationID = "";
					token = 11;
				}
				break;
			case '导出' :
				var url = basePath
						+ "ea/healthadministration/ea_showExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + search;
				open(url);
				break;
			// case '查询':
			// $("#jqModelSearch").jqmShow();
			// break;
			// Autor:yjg
			case '查看' :
				if (healthAdministrationID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + healthAdministrationID);
				$p.find("span[id]").each(function() {
					$t.find(":input#" + this.id).val($(this).text()).attr(
							"readonly", "readonly");
				});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/healthadministration/ea_getListForPage.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("span.list_changes").click(function() {
		alert("13131");
		$p = $(this).parent().parent().parent();
		$p.find("select#principal").attr("name",
				"healthAdministration.principal");
		$p.find("select#examiner")
				.attr("name", "healthAdministration.examiner");
		$f = $('#addressForm');
		$p.find(':input').appendTo($f);
		$f.attr("action", basePath
						+ "ea/healthadministration/t_ea_save.jspa?pageNumber="
						+ ppageNumber);
		document.addressForm.submit.click();
	});
	$("span.list_remove").click(function() {// 删除点击事件
				$p = $(this).parent().parent().parent();
				$f = $('#addressForm');
				$p.find(':input').appendTo($f);
				$f
						.attr(
								"action",
								basePath
										+ "ea/healthadministration/t_ea_del.jspa?pageNumber="
										+ ppageNumber);
				document.addressForm.submit.click();
			});
	$("table tr[id]").click(function() {
				healthAdministrationID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
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
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/healthadministration/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	$(".address").find("select[id!=xxx]").each(function() {
				$(this).hide();
			});
	$(".address tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$("span.toedit").click(function() {
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $(this).parent().parent().parent();
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
			});
	$("input#tosave").click(function() {
		if (healthAdministrationID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/healthadministration/ea_save.jspa?pageNumber="
									+ ppageNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 1;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/healthadministration/ea_save.jspa?pageNumber="
						+ ppageNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/healthadministration/ea_getListForPage.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
