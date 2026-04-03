$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 445,
				width : 'auto',
				minwidth : 30,
				title : '订单显示',
				minheight : 80,
				buttons : [{
					
					
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
				clientPositioningID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (clientPositioningID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + clientPositioningID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#PhotoFile").text();
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (clientPositioningID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#clientPositioningID').val(clientPositioningID);
				if (confirm("确定删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/clientPositioning/ea_delClientPositioning.jspa?pageNumber="
											+ bpageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + clientPositioningID).remove();
					clientPositioningID = "";
					token = 11;
				}
				break;

			case '导出' :
				var url = basePath
						+ "ea/clientPositioning/ea_showClientPositioningExcel.jspa?pageNumber="
						+ bpageNumber + "&search=" + search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + clientPositioningID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/clientPositioning/ea_getClientPositioningList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
				clientPositioningID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				alert(clientPositioningID);
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				re_load();
				$("#jqModel").jqmHide();
			});
	/** ********************************************************** */
	$("input#tosave").click(function() {
		if (clientPositioningID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/clientPositioning/ea_saveClientPositioning.jspa?pageNumber="
									+ bpageNumber);
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
								+ "/ea/clientPositioning/ea_saveClientPositioning.jspa?pageNumber="
								+ bpageNumber);
		document.cstaffForm.submit.click();
		token = 2;

	});

	$(".address tr[id]").dblclick(function() {
				action("修改");
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/clientPositioning/ea_toSearch.jspa?pageNumber="
						+ bpageNumber);
		document.postSearchForm.submit.click();
	});

});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/clientPositioning/ea_getClientPositioningList.jspa?pageNumber="
				+ bpageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
