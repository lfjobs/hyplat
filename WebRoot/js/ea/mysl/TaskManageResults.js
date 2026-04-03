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
				title : '提交成果阶段',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '文件归档',
					bclass : 'transfer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '提交客户',
					bclass : 'examine',
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
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				document.location.href = basePath
							+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid;
				break;
			case '文件归档' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				if (confirm("确认要转入归档阶段？")) {
					
					$("[name='chbox']").each(function(index) {
						if ($(this).is(':checked')) {
							var value=$(this).val();
							$("<input>",{type:"hidden",value:value,name:"dtMytaskMap[" + index + "].taskid"}).appendTo($("form#searchForm"));
						}
					});
					$("#phasestatus",$("#searchForm")).val("04");
					$("#searchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/taskmanage/ea_toChangePhaseByTask.jspa");
					document.searchForm.submit.click();
					token = 2;
				}	
				break;
			case '提交客户' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				if (confirm("确认要提交客户？")) {
					$("[name='chbox']").each(function(index) {
						if ($(this).is(':checked')) {
							var value=$(this).val();
							$("<input>",{type:"hidden",value:value,name:"dtMytaskMap[" + index + "].taskid"}).appendTo($("form#searchForm"));
						}
					});
					$("#phasestatus",$("#searchForm")).val("05");
					$("#searchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/taskmanage/ea_toChangePhaseByTask.jspa");
					document.searchForm.submit.click();
					token = 2;
				}	
				break;	
			case '查询' :
				$("#jqModel").jqmShow();
				break;
			case '导出' :
				var url = basePath + "ea/taskmanage/ea_toExportExcelByTask.jspa?search=" + search+"&myproject.proid="+proid+"&mytask.phasestatus=03";
				open(url);
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/taskmanage/ea_getListByTaskManageResults.jspa?search=" + search+"&myproject.proid="+proid;
				numback(url);
				break;
		}
	}
		$("input.JQueryreturn").click(function() {// 取消
			$("#jqModel").jqmHide();
			location.reload(); 
		});
		$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				location.reload(); 
		
		});
		
		$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
		});
		
		$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
		});
		
		// 查询
		$("#search").click(function() {
		$("#searchForm").attr("action",
				basePath + "ea/taskmanage/ea_toSearchByTaskManageResults.jspa");
		document.searchForm.submit.click();
		
		});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/taskmanage/ea_getListByTaskManageResults.jspa?pageNumber="
				+ pageNumber +"&myproject.proid="+proid;
}