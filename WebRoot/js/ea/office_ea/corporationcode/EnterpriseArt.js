$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '企业文化艺术作品',
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
				enterpriseArtID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("table#stafftable").find('img#pic').attr("src", '');
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (enterpriseArtID == "") {
					alert('请选择文化艺术作品');
					return;
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#enterpriseArtID').val(enterpriseArtID);
					var url = basePath
							+ "ea/enterpriseart/ea_delEnterpriseArt.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + enterpriseArtID).remove();
					enterpriseArtID = "";
					token = 11;
				}
				break;
			case '查看' :
				if (enterpriseArtID == "") {
					alert('请选择文化艺术作品');
					return;
				}
				document.cstaffForm.reset();
				$("table#stafftable").find('img#pic').attr("src", '');
				$t = $("table#stafftable");
				$p = $("tr#" + enterpriseArtID);
				$t.find('img#pic').attr("src",
						basePath + $p.find("span#artFilePath").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/enterpriseart/ea_showEnterpriseArtExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/enterpriseart/ea_getEnterpriseArtList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				enterpriseArtID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {

		if (enterpriseArtID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/enterpriseart/ea_saveEnterpriseArt.jspa?pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			document.cstaffForm.reset();
			token = 1;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/enterpriseart/ea_saveEnterpriseArt.jspa?pageNumber="
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/enterpriseart/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/enterpriseart/ea_getEnterpriseArtList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}