$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '票务报表管理',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '打印预览',
					bclass : 'printer',
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
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/piaowuManager/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '打印预览' :
				var url = basePath+"ea/piaowuManager/ea_printaccess.jspa?search="+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/piaowuManager/ea_getListpiaowu.jspa?aa=bb&search="
						+ search;
				numback(url);
				break;
		}
	}
	//模糊查询
			$("#tosearch").click(function(){
				$("#postSearchForm").attr(
				"action",
				basePath + "ea/piaowuManager/ea_toSearch.jspa?aa=bb&pageNumber="
			+ pNumber);
		document.postSearchForm.submit.click();
			});
	$(".JQueryflexme tr[id]").click(function() {
				ticketbusinessid = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + ticketbusinessid);
				}
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/piaowuManager/ea_getListpiaowu.jspa?aa=zz&pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}