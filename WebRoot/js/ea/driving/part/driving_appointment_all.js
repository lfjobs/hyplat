$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.DtDrivingAppointmentRecordTable').flexigrid({
				height : 450,
				allDouble : true,
				width : 'auto',
				minwidth : 30,
				title: module_title,
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
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
			case '导出':
				var url = basePath
				+ "/ea/appointment/ea_showDtDrivingAppointmentRecordAll.jspa?module_title="+module_title+"&search="+search;
				open(url);
				break;
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/appointment/ea_getListDtDrivingAppointmentRecordAll.jspa?search="
						+ search+"&module_title="+module_title;
				numback(url);
				break;	
		}
	}
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",basePath+ "ea/appointment/ea_toSearch.jspa?pageNumber="+pNumber);
		document.getElementById("SearchForm").submit.click();
	})
	$(".DtDrivingAppointmentRecordTable").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
	});
	
});