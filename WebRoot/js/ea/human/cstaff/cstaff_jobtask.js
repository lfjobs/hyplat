$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.jobTask').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 20,
				title : '工作目标任务--当前人员：' + parent.staffName,
				minheight : 100,
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
					separator : true
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"jobTaskmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (jobTaskID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + jobTaskID + " #status").attr("value") == '01') {
					alert("不可修改");
					break;
				}
				$p = $("tr#" + jobTaskID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"jobTaskmap[" + select + "]." + this.name);
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
					alert("任务完成起时间和止时间都不能为空!");
					notoken = 0;
					return;
				}
				notoken = 1;

				$('#jobTaskForm').attr("target", "hidden").attr(
						"action",
						pbasePath + "ea/jobtask/t_ea_save.jspa?pageNumber="
								+ ppageNumber);
				document.jobTaskForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (jobTaskID == '') {
					alert("请选择！");
					return;
				}
				if (jobTaskID.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + jobTaskID).remove();
						jobTaskID = "";
					}
					return;
				}
				$f = $('#jobTaskForm');
				if (confirm("是否删除？")) {
					$f.attr("target", "hidden").attr(
							"action",
							pbasePath + "ea/jobtask/ea_del.jspa?pageNumber="
									+ ppageNumber + "&jobTask.staffID="
									+ pstaffID + "&jobTask.jobTaskID="
									+ jobTaskID);
					document.jobTaskForm.submit.click();
					$("tr#" + jobTaskID).remove();
					jobTaskID = "";
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/jobtask/ea_getJobTaskList.jspa?staffID="
						+ pstaffID;
				numback(url);
				break;

			case '导出' :
				open(pbasePath + "ea/jobtask/ea_showExcel.jspa?staffID="
						+ pstaffID + "&search=" + pserch);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}

	$(".jobTask tr[id]").click(function() {
				jobTaskID = $(this).attr('id');
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".jobTask tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				jobTaskID = this.id;

				action("修改");

			});
	$(".jobTask").find("select[id!=xxx]").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr("action",
				pbasePath + "ea/jobtask/ea_toSearch.jspa");
		document.postSearchForm.submit.click();
	});
});
function re_load() {
	document.location.href = pbasePath
			+ "ea/jobtask/ea_getJobTaskList.jspa?staffID=" + pstaffID
			+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value");
}