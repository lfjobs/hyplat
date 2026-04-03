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
				title : '公共电话薄管理',
				minheight : 80,
				buttons : [/**{
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
				},**/ {
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
			/**case '添加' :
				telephoneID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (telephoneID == "") {
					alert('请选择!')
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + telephoneID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text())
						});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (telephoneID == "") {
					alert('请选择！')
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#telephoneID').val(telephoneID);
				if (confirm("确定删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/telephone/ea_delTelephone.jspa?pageNumber="
											+ bpageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + telephoneID).remove();
					telephoneID = "";
					token = 11;
				}
				break;**/

			case '导出' :
				var url = basePath
						+ "ea/telephone/ea_showExcel.jspa?pageNumber="
						+ bpageNumber + "&search=" + search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + telephoneID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/telephone/ea_getaTelephoneList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {

				telephoneID = this.id;

				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	/** ********************************************************** */
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input#tosave").click(function() {
		$t = $("table#stafftable");
		if ($t.find("option[value=请选择]").is("selected")) {
			alert("请选择人员");
		}
		if (telephoneID == "") {
			$("#cstaffForm").attr("target", "hidden").attr(
					"action",
					basePath
							+ "/ea/telephone/ea_saveTelephone.jspa?pageNumber="
							+ bpageNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 1;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/telephone/ea_saveTelephone.jspa?pageNumber="
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
				basePath + "ea/telephone/ea_toSearch.jspa?pageNumber="
						+ bpageNumber);
		document.postSearchForm.submit.click();
	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/telephone/ea_getaTelephoneList.jspa?pageNumber="
				+ bpageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
