$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '域名管理集团汇总',
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				var url = basePath
						+ "ea/domainnamegroup/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search;
				open(url);
				break;
		}
	}

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/domainnamegroup/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
});

function re_load() {
	document.location.href = basePath
			+ "ea/domainnamegroup/ea_getListDomainnameGroup.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value");
}