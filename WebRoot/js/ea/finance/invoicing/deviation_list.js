$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "验货误差管理",
				minheight : 80,
				buttons : [{
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
			case '查询' :
				$("#jqModelSearch2").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "/ea/purchase/ea_showExcelDeviation.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/purchase/ea_getDeviationList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;

		}
	}

	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

	$("#tosearch").click(function() {// 查询
				$("#postSearchForm2")
						.attr(
								"action",
								basePath
										+ "ea/purchase/ea_toSearchDeviation.jspa?pageNumber="
										+ pNumber);
				document.postSearchForm2.submit.click();
			});
});

function re_load() {
	document.location.href = basePath
			+ "/ea/purchase/ea_getDeviationList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate;
}
