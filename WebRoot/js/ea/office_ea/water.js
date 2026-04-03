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
				title : '用水管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
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
				addressID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (addressID == '') {
					alert("请选择！");
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly");
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (addressID == '') {
					alert("请选择！");
					return;
				}

				$f = $('#addressForm');
				$f.find(':input#addressID').val(addressID);
				if (confirm("确定继续？")) {
					$f.attr("target", "hidden").attr(
							"action",
							basePath + "ea/water/ea_del.jspa?pageNumber="
									+ ppageNumber + "&coWater.waterID="
									+ addressID);
					document.addressForm.submit.click();
					$("tr#" + addressID).remove();
					addressID == '';
					token = 11;
				}

				break;
			case '导出' :
				var url = basePath + "/ea/water/ea_showExcel.jspa?search="
						+ psearch;
				open(url);
				break;
			// case '查询':
			// $("#jqModelSearch").jqmShow();
			// break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly");
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/water/ea_getListForPage.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}

	// 新加内内容开始 target 指向页面隐藏Hidden
	$("input.JQuerySubmit").click(function() {// 保存
				if ($("form .error").length) {
					return;
				}
				if (addressID == "") {
					$("#cstaffForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/water/ea_save.jspa?pageNumber="
									+ ppageNumber);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 1;
					return;
				}
				$("#cstaffForm").attr("target", "hidden").attr(
						"action",
						basePath + "ea/water/ea_save.jspa?pageNumber="
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
	// $("span.list_changes").click(function(){
	// $p = $(this).parent().parent().parent();
	// $p.find("select#principal").attr("name",
	// "healthAdministration.principal");
	// $p.find("select#examiner").attr("name", "healthAdministration.examiner");
	// $f = $('#addressForm');
	// $p.find(':input').appendTo($f);
	// $f.attr("action",
	// basePath+"/ea/water/t_ea_save.jspa?pageNumber=${pageNumber}")
	// document.addressForm.submit.click();
	// });
	$("span.list_remove").click(function() {// 删除点击事件
				$p = $(this).parent().parent().parent();
				$f = $('#addressForm');
				$p.find(':input').appendTo($f);
				$f.attr("action", basePath
								+ "/ea/water/t_ea_del.jspa?pageNumber="
								+ ppageNumber);
				document.addressForm.submit.click();
			});
	$("table tr[id]").click(function() {
				addressID = this.id;
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
				basePath + "/ea/water/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	// 新加内内容开始
	$(".address").find("select[id!=xxx]").each(function() {
				$(this).hide();
			});
	$(".address tr[id]").dblclick(function() {
				action("查看");
			});
	$("span.toedit").click(function() {
				action("查看");
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/water/ea_getListForPage.jspa?pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
// 新加内容结束
