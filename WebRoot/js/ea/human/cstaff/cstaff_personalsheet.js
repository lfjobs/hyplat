$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});
	$('.JQueryflexmepost').flexigrid({
				height : 'auto',
				width : 'auto',
				title : "人员资料出入库单据",
				minwidth : 20,
				minheight : 100,
				buttons : [{
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
					name : '打印预览',
					bclass : 'mysearch',
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
			case '查看' :
				if (pabillID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/personalarchive/ea_toViewSheet.jspa?pageNumber="
						+ pNumber + "&paBill.pabillID="+pabillID;
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '打印预览' :
				if (pabillID == "") {
					alert("请选择！");
					return;
				}
				window.open(basePath
						+ "ea/personalarchive/ea_toprintSheet.jspa?paBill.pabillID="
						+ pabillID);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/personalarchive/ea_getPArchiveSheetList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}

	$(".JQueryflexmepost tr[id]").click(function() {
				pabillID = $(this).attr('id');
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$(".JQueryflexmepost tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				pabillID = this.id;
				action("查看");
			});
			
	$("#tosearch").click(function() {

		$("#SearchForm").attr(
				"action",
				basePath + "ea/personalarchive/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});

});

function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "/ea/personalarchive/ea_getPArchiveSheetList.jspa?type=archive";
}
