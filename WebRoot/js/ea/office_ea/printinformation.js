$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 430,
				width : 'auto',
				minwidth : 30,
				title : '打印信息',
				minheight : 80,
				buttons : [{
					name : '查看',
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
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
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
			case '查看' :
				if (printInfoID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + printInfoID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#photo").text();

				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
					// alert( $t.find("#pic").attr("src"))
				}
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (printInfoID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#printInfoID').val(printInfoID);
				if (confirm("确定删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/car/ea_delCarInformation.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					$("tr#" + printInfoID).remove();
					printInfoID = "";
					token = 11;
				}
				break;
			// case '查询':
			// $("#jqModelSearch").jqmShow();
			// break;
			case '导出' :
				url = basePath + "ea/printInfo/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/printInfo/ea_getPrintInList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("#tosearch").click(function() { // 查询
				$("#postSearchForm").attr(
						"action",
						basePath + "ea/printInfo/ea_toSearch.jspa?pageNumber="
								+ pNumber);
				document.postSearchForm.submit.click();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				printInfoID = this.id;
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

	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/printInfo/ea_getPrintInList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
