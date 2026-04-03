$(function() {
	var capitalID = "";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '基建管理',
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
				capitalID == "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (capitalID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + capitalID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (capitalID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#capitalID').val(capitalID);
				if (confirm('是否确定删除？')) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/capital/ea_delCapitalConstruction.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					$('tr#' + capitalID).remove();
					capitalID = "";
					token = 11;
				}

				break;
			// case '查询':
			// $("#jqModelSearch").jqmShow();
			// break;
			case '导出' :
				url = basePath
						+ "ea/capital/ea_showExcel.jspa?search=${search}";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/capital/ea_getCapitalConstructionList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				capitalID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				if ($("form .error").length) {
					return;
				}
				if (capitalID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/capital/ea_saveCapitalConstruction.jspa?pageNumber="
											+ pNumber + "&search=" + search);
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
										+ "ea/capital/ea_saveCapitalConstruction.jspa?pageNumber="
										+ pNumber + "&search=" + search);
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
	$("#tosearch").click(function() {
		$("#postSearchForm").attr("action",
				basePath + "ea/capital/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/capital/ea_getCapitalConstructionList.jspa?search="
				+ search + "&pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
