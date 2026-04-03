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
				title : '检查项',
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

			case '添加' :
				document.postAddForm.reset();
				$("#postAddForm div#title").text("添加");
				getAllPointlist("");
				$("#jqModelAdd").jqmShow();
				$("#toSubmit").show();
				break;
			case '修改' :
				if (checkpointitemid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + checkpointitemid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
							if (this.id == "checkpointid") {
								getAllPointlist($(this).text());
							}
						});
				if ($p.find("span[id=useflag]").text() != 0) {
					$t.find("#toSubmit").hide();
				}
				$("#postAddForm div#title").text("修改");
				$("#jqModelAdd").jqmShow();
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
						+ "/ea/safetycheck/ea_pointitem.jspa?search=" + search
						+ "&pageSize=" + pageSize;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				checkpointitemid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				checkpointitemid = this.id;
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
				basePath + "/ea/safetycheck/ea_addPointItem.jspa?pageNumber="
						+ pageNumber);
		document.postAddForm.submit.click();
		document.postAddForm.reset();
		token = 2;
	});

	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/safetycheck/ea_toSearchPointItem.jspa?pageNumber="
								+ pageNumber);
		document.postSearchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/safetycheck/ea_pointitem.jspa?pageNumber=" + pageNumber
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

function getAllPointlist(checkpointid) {
	var url = basePath
			+ "/ea/safetycheck/sajax_ea_getPointByCompany.jspa?data="
			+ Math.random();
	$.ajax({
				url : url,
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					var mem = eval("(" + data + ")");
					var pointlist = mem.pointlist;
					var str = "<option selected='selected'>请选择所属检查点</option>"
					for (var i = 0; i < pointlist.length; i++) {
						var obj = pointlist[i];
						str += "<option value='" + obj.checkpointid + "'>"
								+ obj.checkpointname + "</option>";

					}
					$("#postAddForm #point").html(str);
				},
				error : function(data) {
					alert("获取检查点失败");
				}

			});

	if (checkpointid != "") {
		$("#postAddForm select#point option[value=" + checkpointid + "]").attr(
				"selected", "selected");
	}
}
