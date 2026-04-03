$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 420,
				width : 'auto',
				minwidth : 30,
				title : '安全检查类别',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// �
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
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				id = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			break;
		case '修改' :
			if (id == "") {
				alert('请选择!');
				return;
			}
			document.cstaffForm.reset();
			$t = $("table#stafftable");
			$p = $("tr#" + id);
			$p.find("span[id]").each(function() {
						$t.find(":input[name]#" + this.id).val($(this).text());
					});
			var photo = $p.find("span#attachment").text();
			$t = $("table#stafftable");
			$t.find("#pic").attr("src", "");
			if (photo.length != 0) {
				$t.find("#pic").attr("src", bPath + photo);
			}
			$("#jqModel").jqmShow();
			break;
		case '删除' :
			if (id == "") {
				alert('请选择');
				return
			}
			$f = $('#cstaffForm');
			$f.find(':input#id').val(id);
			if (confirm("确定删除？")) {
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								bPath
										+ "ea/oasafeKind/ea_delOASafeKind.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				$("tr#" + id).remove();
				id = "";
				token = 11;
			}
			break;
		case '导出' :
			var url = bPath + "ea/oasafeKind/ea_showExcel.jspa?search="
					+ search;
			open(url);
			break;
		case '查询' :
			$("#jqModelSearch2").jqmShow();
			break;
		case '设置每页显示条数' :
			var url = bPath + "ea/oasafeKind/ea_getListOASafeKind.jspa?search="
					+ search;
			numback(url);
			break;
	}
}
	$("input.JQuerySubmit").click(function() {
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				bPath + "ea/oasafeKind/ea_saveOASafeKind.jspa?pageNumber="
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
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		id = this.id;
	});
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('修改');
	});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				bPath + "ea/oasafeKind/ea_toSearch.jspa?pageNumber="
						+ ppageNumber + "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = bPath
				+ "ea/oasafeKind/ea_getListOASafeKind.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
$(document).ready(function() {
	// 图片预览
	$('#filePhoto').change(function() {
				$t = $("table#stafftable");
				$t.find('img#showphoto').attr("src", this.value).show();
			});
		// 图片预览END
	});
