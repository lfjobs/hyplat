$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 445,
				width : 'auto',
				minwidth : 30,
				title : '户内户外宣传管理',
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
				propagateID = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (propagateID == "") {
					alert('请选择户内户外宣传');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#propagateID').val(propagateID);
					var url = basePath
							+ "ea/inoutpropagate/ea_delInOutPropagate.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + propagateID).remove();
					propagateID = "";
					token = 11;
				}
				break;
			case '修改' :
				if (propagateID == "") {
					alert('请选择胡内户外宣传');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + propagateID);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
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
						+ "ea/inoutpropagate/ea_showInOutPropagateExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/inoutpropagate/ea_getInOutPropagateList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				propagateID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/inoutpropagate/ea_saveInOutPropagate.jspa?pageNumber="
								+ pNumber);
		if (propagateID == "") {
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}
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
				basePath + "ea/inoutpropagate/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/inoutpropagate/ea_getInOutPropagateList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}