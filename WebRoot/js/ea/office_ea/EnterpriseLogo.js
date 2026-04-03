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
				title : '企业形象管理',
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
				logoID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (logoID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + logoID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#logoPhoto").text();
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (logoID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#logoID').val(logoID);
				if (confirm("确定继续？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/logo/ea_delEnterpriseLogo.jspa?pageNumber="
											+ ppageNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					$("tr#" + logoID).remove();
					logoID == '';
					token = 11;
				}
				break;
			case '导出' :
				url = basePath + "ea/logo/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/logo/ea_getEnterpriseLogoList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}

	// 新加内内容开始 target 指向页面隐藏Hidden
	$("input.JQuerySubmit").click(function() {// 保存
				if ($("form .error").length) {
					return;
				}
				if (logoID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/logo/ea_saveEnterpriseLogo.jspa?pageNumber="
											+ ppageNumber);
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
										+ "ea/logo/ea_saveEnterpriseLogo.jspa?pageNumber="
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

	// 新加内容结束

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				logoID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath + "ea/logo/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/logo/ea_getEnterpriseLogoList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}