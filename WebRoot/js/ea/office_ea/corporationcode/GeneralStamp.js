$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	// 汇总
	var title = "";
	if (type == "company") {
		title = "普通印章汇总——公司";
	} else if (type == "group") {
		title = "普通印章汇总——集团";
	} else {
		title = "普通印章管理";
	}
	if (type == "company" || type == "group") {
		$('.address').flexigrid({
					allDouble : true,
					height : 145,
					width : 'auto',
					minwidth : 30,
					title : title,
					minheight : 80,
					buttons : [{
						name : '查看',
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
						}, {
						separator : true
					}, {
						name : '印章使用日志',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}]
				});
	} else {
		$('.address').flexigrid({
					allDouble : true,
					height : 145,
					width : 'auto',
					minwidth : 30,
					title : title,
					minheight : 80,
					buttons : [{
						name : '添加',
						bclass : 'add',
						hide : false,
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
						name : '用于学员证',
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
					}, {
						name : '设置每页显示条数',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '印章使用日志',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}]
				});
	}
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();

				break;

			case '修改' :
				if (enterpriseStampID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + enterpriseStampID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#scanningAccessories").text();
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (enterpriseStampID == "") {
					alert('请选择！');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#enterpriseStampID').val(enterpriseStampID);
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/enterprisestamp/ea_delEnterpriseStamp.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();

					$("tr#" + enterpriseStampID).remove();
					enterpriseStampID = "";

					token = 11;
				}
				break;

			case '导出' :
				var url = basePath
						+ "ea/enterprisestamp/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search + "&gore=g&type="
						+ type;
				open(url);
				break;
			case '查询' :
				if (type == "company") {
					$(".company").hide();
					$(".org").show();
					bmdepts("");
				} else if (type == "group") {
					getCurrentAndSubCompany();
					$(".company").show();
					$(".org").show();

				} else {
					$(".company").hide();
					$(".org").hide();
				}
				$("#jqModelSearch").jqmShow();
				break;
				
			case '用于学员证' :
				if (enterpriseStampID == "") {
					alert('请选择！');
					return
				}
				$p = $("tr#" + enterpriseStampID);
				var isStuStamp = $p.find("input#isStuStamp").val();
				if (isStuStamp == "1") {
					alert("对不起,请不要重复设置");
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#enterpriseStampID').val(enterpriseStampID);

				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/enterprisestamp/ea_setStudentStamp.jspa");
					document.cstaffForm.submit.click();

					token = 2;
				}
				break;
			case '查看' :
				if (enterpriseStampID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + enterpriseStampID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var photo = $p.find("span#scanningAccessories").text();
				$t.find("#pic").attr("src", "");
				if (photo.length != 0) {
					$t.find("#pic").attr("src", basePath + photo);
				}
				$("#tosave").hide();
				$("#jqModel").jqmShow();
				break;
			break;
		case '设置每页显示条数' :
			if (pNumber < 0 || pNumber != parseInt(pNumber) || pNumber > 50) {
				alert("请输入小于50的正整数");
				return;
			}
			document.location.href = basePath
					+ "ea/enterprisestamp/ea_getListGeneralStamp.jspa?search="
					+ search + "&gore=g&type=" + type + "&pageNumber="
					+ pNumber + "&date=" + new Date();
			break;
		case '印章使用日志' :
			if (enterpriseStampID == "") {
				alert("请选择具体印章！");
				return;
			}
			personurl = basePath
					+ "ea/stamplog/ea_getListStampLog.jspa?pageNumber="
					+ pNumber + "&gore=g&type=" + type
					+ "&stampLog.enterpriseStampID=";
			$("#mainframe").css({
						"height" : "auto"
					}).attr("src", personurl + enterpriseStampID);
			$(window).resize();
			break;
	}
}
	$("table tr[id]").click(function() {
				enterpriseStampID = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + enterpriseStampID);
				}
				stampName = $(this).find("span#stampName").text();
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
				basePath + "ea/enterprisestamp/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
	$(".address tr[id]").dblclick(function() {
				enterpriseStampID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				if (type != "company" && type != "group") {
					action("修改");
				} else {
					action("查看");
				}
			});

	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input#tosave").click(function() {
		$t = $("table#stafftable");
		if ($("select#userName", $t).attr("value") == "") {
			alert("请选择人员！");
			return;
		}

		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/enterprisestamp/ea_addEnterpriseStamp.jspa?pageNumber="
								+ pNumber + "&date=" + new Date());
		document.cstaffForm.submit.click();
		token = 2;
	});

});
function re_load() {
	if (token)
		document.location.reload();
	return;
}

// 获取当前公司及其子公司
function getCurrentAndSubCompany() {
	var url = basePath
			+ "ea/documentsummary/sajax_ea_getCurrentAndSubCompany.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var str = "<option value=''>请选择公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName
								+ "' value='" + obj.companyID + "'>"
								+ obj.companyName + "</option>";
					}
					$("#cataffSearchTable #companyID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

function bmdepts(val) {
	$("#cataffSearchTable #organizationID").html("");
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					companyID : val
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var orgaizationlist = member.orgaizationlist;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#cataffSearchTable #organizationID").html(str);

				},
				error : function cbf(data) {
					alert("获取公司失败！");
				}
			});
}

function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdepts($(obj).val());
	} else {
		$("#cataffSearchTable #organizationID")
				.html("<option value=''>请先选择公司</option>");
	}

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
	$("#cstaffForm #responsibleName").val(staffName);
	$("#cstaffForm #responsibleID").val(childopertionID);

	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();

}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}