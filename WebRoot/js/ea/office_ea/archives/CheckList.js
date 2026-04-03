$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	if (type == "company" || type == "group") {
		var title = "";
		if (type == "company") {
			title = "盘点公司汇总"
		} else {
			title = "盘点集团汇总";
		}
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : title,
					minheight : 80,
					buttons : [{
						name : '返回',
						bclass : 'prev',
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
	} else {
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : '盘点',
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
						name : '删除',
						bclass : 'delete',
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
	}
	function action(com, grid) {
		switch (com) {
			case '返回' :
				document.location.href = basePath
						+ "ea/archive/ea_getArchiveList.jspa?type=" + type
						+ "&date=" + new Date();
				break;

			case '添加' :
				document.postAddForm.reset();
				$(".JQuerypersonvalue").attr("checked", false);
				$("form span").remove();
				$("#postAddForm div#title").text("添加");
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :

				if (checkid == "") {
					alert("请选择");
					return;
				}
				document.postAddForm.reset();
				$t = $("table#addTable");
				$p = $("tr#" + checkid);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});

				var url = basePath
						+ "/ea/check/sajax_ea_getCheckView.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
							url : encodeURI(url),
							type : "post",
							async : true,
							dataType : "json",
							data : {
								checkid : checkid
							},
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var check = member.result;
								$("table#addTable select#departmentid option[value="
										+ check.departmentid + "]").attr(
										"selected", "selected");
								$("table#addTable #categoryid")
										.val(check.categoryid);
								$("table#addTable #checkuser")
										.val(check.checkuser);

							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});

				$t.find("input#checkid").val(checkid);
				$("#postAddForm div#title").text("修改");
				$("#jqModelAdd").jqmShow();
				break;
			case '删除' :
				if (checkid == "") {
					alert("请选择");
					return;
				}

				if (confirm("确定删除")) {
					document.location.href = basePath
							+ "ea/check/ea_deleteCheck.jspa?checkid=" + checkid
							+ "&dates=" + new Date();
					token = 2;
					re_load();
				}
				break;
			case '查询' :
				document.postSearchForm.reset();
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				pNumber = prompt("输入显示条数", "请输入小于50正整数");
				if (pNumber < 0 || pNumber != parseInt(pNumber) || pNumber > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				document.location.href = basePath
						+ "/ea/check/ea_getCheckList.jspa?search=" + search
						+ "&pageNumber=" + pNumber+"&type="+type;
				break;
		}
	}

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				checkid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#postAddForm #tosubmit").click(function() {//
				$(".put3").trigger("blur");
				if ($("#jqModelAdd form .error").length) {
					return;
				}
				$("#postAddForm").attr("target", "hidden").attr(
						"action",
						basePath + "ea/check/ea_addCheck.jspa?pageNumber="
								+ pNumber);
				document.postAddForm.submit.click();
				document.postAddForm.reset();
				if (checkid == "") {
					token = 1;
				} else {
					token = 2;
				}
				return;
			});
	$("#toCancel").click(function() {// 取消
				$("#jqModelAdd").jqmHide();

			});

	$("#tosearch").click(function() {
		$("#postSearchForm").attr("action",
				basePath + "ea/check/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.postSearchForm.submit.click();
	});

	bmdept();
	getCatalogue(type);
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/check/ea_getCheckList.jspa?pageNumber=" + pNumber
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
	$("#postAddForm #checkusername").val(staffName);
	$("#postAddForm #checkuser").val(childopertionID);

	$("#postSearchForm #checkusername").val(staffName);
	$("#postSearchForm #checkuser").val(childopertionID);

	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

function bmdept() {
	var url = basePath
			+ "ea/docunfinish/sajax_ea_getOrganizationLists.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var organizationList = member.organizationList;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < organizationList.length; i++) {
						var obj = organizationList[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}

					$("#postAddForm #departmentid").html(str);
					$("#postSearchForm #departmentid").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

function getCatalogue(type) {
	var url = basePath
			+ "ea/archive/sajax_ea_getCatalogueAndLocation.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				data:{
					type:type
				},
				success : function cbf(data) {
					/** **添加类别列表** */
					var member = eval("(" + data + ")");
					var cataList = member.cataloguelist;
					$("#postSearchForm #categoryid").empty();

					// 处理档案类别
					var data = new Array();
					data[0] = {
						id : "",
						pid : '-1',
						text : '请选择档案类别'
					};
					for (var i = 0; i < cataList.length; i++) {
						data[i + 1] = {
							id : cataList[i].archiveid,
							pid : cataList[i].parent,
							text : cataList[i].name
						};
					}

					ts2 = new TreeSelector($("#postSearchForm #categoryid")[0],
							data, -1);
					ts2.createTree();

					ts1 = new TreeSelector($("#postAddForm #categoryid")[0],
							data, -1);
					ts1.createTree();

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}
