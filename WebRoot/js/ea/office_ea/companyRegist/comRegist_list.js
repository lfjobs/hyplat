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
				title : "企业资料列表",
				minheight : 80,
				buttons : [{
					name : '企业注册',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询企业',
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
			case '企业注册' :
				document.location.href = basePath + "register/ea_isCompanyRegister.jspa";
				break;
			case '查询企业' :
				$("#jqModelSearch1").jqmShow();
				break;
			case '导出' :
				var url = basePath
						+ "ea/comregist/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/comregist/ea_getCompanyRegistList.jspa?1=1";
				numback(url);
				break;
		}
	}

	$("#tosearch").click(function() { // 查询
				$("#SearchForm").attr(
						"action",
						basePath + "ea/comregist/ea_toSearch.jspa?pageNumber="
								+ pNumber);
				document.SearchForm.submit.click();
				$("#jqModelSearch1").jqmHide();
			});

	$("#closes").click(function() {
				window.location.reload();
				$("#jqModelSearch").jqmHide();
			});
});
function re_load() {
	window.location.reload();
}
