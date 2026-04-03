$(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		//	.jqDrag('.drag');// 添加拖拽的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#jqMode2").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
		$("#jqModelSend").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '公司招聘登记管理',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
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
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印登记表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印登记数据',
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
			case '查看' :

				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + auditionID);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find("input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			 case '打印登记表':
				var url = basePath
					+ "/page/ea/main/human/office/production/printBasicInformation.jsp?star=招聘登记表";
				window.open(encodeURI(url));
				break;
			 case '打印登记数据' :
					if (auditionID == "") {
						alert("请选择人员！");
						return;
					}
					var staffid = $("tr#"+auditionID).find("span#staffID").text();
					var url = basePath
							+ "ea/saudition/ea_printBIAud.jspa?star=招聘登记表&staffID="
							+ staffid;
					//cstaff20101020s4nxggkgkb0000000483
					window.open(encodeURI(url));
					break;
			case '删除' :
				if (auditionID == "") {
					alert('请选择人员');
					return;
				}
				$("#cstaffForm")
						.attr(
								"action",
								basePath
										+ "ea/saudition/t_ea_delAudition.jspa?pageNumber="
										+ pNumber
										+ "&status=3&audition.auditionID="
										+ auditionID);
				document.cstaffForm.submit();
				break;
			case '导出' :
				url = basePath + "ea/saudition/ea_showExcel.jspa?status=3";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/saudition/ea_getauditionList.jspa?status=3";
				numback(url);
				break;
			
		}
	}

	$("input.JQueryreturn").click(function() {// 取消
				$("#jqMode2").jqmHide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				auditionID = this.id;
				$("input.JQueryauditionID", $(this)).attr("checked", "checked");
			});
	$("#auditionBC").click(function() {
		$("#employmentForm").attr(
				"action",
				basePath + "ea/saudition/ea_saveAllAudition.jspa?pageNumber="
						+ pNumber + "&status=3");
		document.getElementById("employmentForm").submit();
	});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/saudition/ea_toSearchCStaff.jspa?pageNumber="
						+ pNumber + "&status=3");
		document.getElementById("cstaffSearchForm").submit();
		$("#cataffSearchTable").find(":input[name]").val("");
	});

});

//子窗口调用
function refresh(){
    window.location.reload();
} 
