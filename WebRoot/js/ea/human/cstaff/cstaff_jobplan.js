$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '项目工作计划----当前人员：' + parent.staffName,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
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
					name : '全部保存',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
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
				}, {
					name : '打印',
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
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"jobPlanmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (jobPlanID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + jobPlanID + " #status").attr("value") == '01') {
					alert("不可修改");
					break;
				}
				$p = $("tr#" + jobPlanID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"jobPlanmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("s:select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if ($("form .error").length) {
					return;
				}
				if (notoken)
					return;
				if (select == 1) {
					return;
				}
				var re = 0;
				$("input.aaaa", $(".check")).each(function() {
							var date = this.value;
							var $s = $(this);
							if (date == "") {
								$s.css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					alert("起始日期和结束日期都不能为空!");
					notoken = 0;
					return;
				}
				notoken = 1;
				$f = $('#jobPlanForm');

				$('#jobPlanForm').attr("target", "hidden").attr(
						"action",
						pbasePath + "ea/jobplan/t_ea_save.jspa?pageNumber="
								+ ppageNumber);

				document.jobPlanForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (jobPlanID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + jobPlanID + " #status").attr("value") == '01') {
					alert("不可删除");
					break;
				}
				if (jobPlanID.substring(0, 2) == 'sa') {
					if (confirm("是否删除?")) {
						$("#" + jobPlanID).remove();
						jobPlanID = "";
					}
					return;
				}
				$f = $('#jobPlanForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f.attr("target", "hidden").attr(
							"action",
							pbasePath + "ea/jobplan/ea_del.jspa?pageNumber="
									+ ppageNumber + "&jobPlan.staffID="
									+ pstaffID + "&jobPlan.jobPlanID="
									+ jobPlanID);
					document.jobPlanForm.submit.click();
					$("tr#" + jobPlanID).remove();
					jobPlanID = '';
					token = 11;
				}
				break;
			case '导出' :
				var url = pbasePath + "ea/jobplan/ea_showExcel.jspa?staffID="
						+ pstaffID + "&search=" + pserch;
				open(url);
				break;
			case '打印' :
				var url = pbasePath
						+ "ea/jobplan/ea_toDaYin.jspa?jobPlan.staffID="
						+ pstaffID + "&search=" + pserch;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/jobplan/ea_getJobPlanList.jspa?staffID="
						+ pstaffID + "&search=" + pserch;
				numback(url);
				break;
		}
	}
	$(".address tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				jobPlanID = this.id;
			});
	$(".address tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				jobPlanID = this.id;
				action("修改");
			});
	$(".address").find("select[id!=xxx]").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
	$("table tr[id]").click(function() {
				jobPlanID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				pbasePath + "ea/jobplan/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

});
function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "/ea/jobplan/ea_getJobPlanList.jspa?staffID=" + pstaffID
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
