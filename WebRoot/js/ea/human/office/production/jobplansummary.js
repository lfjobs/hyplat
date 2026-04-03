$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '工作计划汇总',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
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
					name : '打印',
					bclass : 'excel',
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
			case '导出' :
				var url = pbasePath
						+ "ea/jobplan/ea_showExcelSummary.jspa?search="
						+ psearch;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				if (addressID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + addressID);
				$p.find("span[id]").each(function() {
					$t.find("input#" + this.id).val($(this).text()).attr(
							"readonly", "readonly");
				});
				
				$("#jqModel").jqmShow();
				break;
			case '打印' :
				$("#jqModelSearch1").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/jobplan/ea_getJobPlanListSummary.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
				addressID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 条件查询
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				pbasePath + "ea/jobplan/ea_toSearchSummary.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	// 条件打印
	$("#toprint").click(function() {
		//var staffName = $("#staffName1", $("#cataffPrintTable")).val();
		var startDate = $("#startdate", $("#cataffPrintTable")).val();
		var endDate = $("#enddate", $("#cataffPrintTable")).val();

		if (startDate == "") {
			alert("日期不能为空");
			$(".startdate").css("background-color", "red");
			return;
		}
		if (endDate == "") {
			alert("日期不能为空");
			$(".enddate").css("background-color", "red");
			return;
		}
		$("#postSearchForm1").attr("action",
				pbasePath + "ea/jobplan/ea_toPrint.jspa?");
		document.postSearchForm1.submit.click();
	});
	$(".address tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
});