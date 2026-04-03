$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '检查结果',
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'prev',
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
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '返回' :
				document.location.href = basePath
						+ "/page/ea/main/navigation/logistics_Car.jsp";
				break;

			case '查询' :
				getTaskByCompany();
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				if (defectflowid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + defectflowid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
							if (this.id == "finishflag") {
								if ($(this).text() == 0) {
									$t.find("input#finishflag").val("未成功");
								} else {
									$t.find("input#finishflag").val("成功");
								}
							}
						});

				$("#jqModelAdd").jqmShow();
				break;

			case '设置每页显示条数' :
				pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				window.location.href = basePath
						+ "/ea/safetycheck/ea_results.jspa?search=" + search
						+ "&pageSize=" + pageSize;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				defectflowid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				defectflowid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath
						+ "/ea/safetycheck/ea_toSearchResults.jspa?pageNumber="
						+ pageNumber);
		document.postSearchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_results.jspa?pageNumber=" + pageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

// 选择人员
function importGY(url) { // 打开页面
	$("#daoRu")
			.attr("src", basePath + url + "?date=" + new Date() + "&hid=hid");

	$("#socialJqm").jqmShow();
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择");
		return;
	}

	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	$("#postAddForm #principalname").val(staffName);
	$("#postAddForm #principal").val(childopertionID);

	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

// 获取当前公司的所有巡检任务
function getTaskByCompany() {
	var url = basePath + "/ea/safetycheck/sajax_ea_getTaskByCompany.jspa?data="
			+ Math.random();
	$.ajax({

				url : url,
				type : "get",
				dateType : "json",
				async : false,
				success : function(data) {
					var member = eval("(" + data + ")");
					var tasklist = member.tasklist;
					var str = "<option value=''>选择巡检任务</option>";
					for (var i = 0; i < tasklist.length; i++) {
						var obj = tasklist[i];
						str += "<option value='" + obj.checktaskid + "'>"
								+ obj.checktaskname + "</option>"
					}

					$("#postSearchForm #checktaskid").html(str);
				},
				error : function(data) {
					alert("获取计划失败");
				}

			});
}