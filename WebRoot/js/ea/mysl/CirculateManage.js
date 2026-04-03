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
				title : '传阅管理',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查询',
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
				},{
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
				if (auditid == "") {
					alert("请选择！");
					return;
				}
				var taskid=$("#"+auditid).find("#taskid").val();
				document.location.href = basePath
				+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid;
				break;
				break;
			case '查询' :
				$("#jqModelsubmit").jqmShow();
				break;
			case '导出' :
				var url = basePath + "/ea/checkmanage/ea_showExcelMypassrecord.jspa?search=" + search
					+ "&sdate=" + sdate + "&edate=" + edate+"&auditorstatus="+auditorstatus;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
				+ "/ea/checkmanage/ea_getMypassrecordList.jspa?search=" + search
				+ "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	
	$("#JQuerySubmit").click(function() {
		$("#cstaffForm").attr(
				"action",
				basePath+"/ea/checkmanage/ea_toSearchByMypassrecord.jspa?pageNumber="
						+ ppageNumber);
		document.cstaffForm.submit.click();
	});

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});

	$(".JQueryflexme tr[id]").click(function() {
		auditid = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
		+ "/ea/checkmanage/ea_getMypassrecordList.jspa?pageNumber="
		+ ppageNumber + "&pageForm.pageNumber="
		+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate;
}