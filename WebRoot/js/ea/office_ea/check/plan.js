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
				title : '巡检计划',
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'prev',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
				},
//				{
//					name : '删除',
//					bclass : 'delete',
//					onpress : action
//						// 当点击调用方法
//					}, {
//					separator : true
//				}, 
					{
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

			case '添加' :
				document.postAddForm.reset();
				$("#postAddForm div#title").text("添加");
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :
				if (checkplanid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + checkplanid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});

				$t.find("input#checkplanid").val(checkplanid);
				$("#postAddForm div#title").text("修改");
				$("#jqModelAdd").jqmShow();
				break;
			case '删除' :
				if (checkplanid == "") {
					alert("请选择");
					return;
				}

				if (confirm("确定删除")) {
				$t = $("table#addTable");
				$t.find("input#checkplanid").val(checkplanid);
					$("#postAddForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/safetycheck/ea_deletePlan.jspa?pageNumber="
											+ pageNumber);
					document.postAddForm.submit.click();
					document.postAddForm.reset();
					token = 2;

				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				window.location.href = basePath
						+ "/ea/safetycheck/ea_plan.jspa?search="
						+ search+"&pageSize="
						+ pageSize;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				checkplanid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				checkplanid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 添加提交
	$("#toSubmit").click(function() {
		$(".put3").trigger("blur");
		if ($("#postAddForm .error").length > 0) {
			return;
		}
		$("#postAddForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/safetycheck/ea_addPlan.jspa?pageNumber="
						+ pageNumber);
		document.postAddForm.submit.click();
		document.postAddForm.reset();
		token = 2;
	});

	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/safetycheck/ea_toSearchPlan.jspa?pageNumber="
						+ pageNumber);
		document.postSearchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_plan.jspa?pageNumber=" + pageNumber
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

