$(document).ready(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '员工级别：',
				minheight : 80,
				buttons : [{
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/payscale/ea_getStaffPayScaleList.jspa?search="
						+ search;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/payscale/ea_showStaffExcel.jspa?search=" + search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				if (staffID == "") {
					alert("请选择！");
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + staffID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				staffID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
			});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm")
				.attr(
						"action",
						basePath + "ea/payscale/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.cstaffSearchForm.submit.click();
	});
});