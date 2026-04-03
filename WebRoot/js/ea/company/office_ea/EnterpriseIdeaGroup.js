$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '企业理念管理-集团汇总',
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
				$("#jqModelSearch").show();
				break;
			case '导出' :
				url = basePath + "ea/ideagroup/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
			
		}
	}
//模糊查询
	
	$("#tosearch").click(function() {
	$("#postSearchForm").attr(
			"action",
			basePath + "/ea/ideagroup/ea_toSearch.jspa?pageNumber="
					+ ppageNumber);
	document.postSearchForm.submit.click();
	
});
	
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/ideagroup/ea_getEnterpriseIdeaList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}