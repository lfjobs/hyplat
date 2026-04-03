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
		title = "电子印章汇总——公司";
	} else if (type == "group") {
		title = "电子印章汇总——集团";
	} else {
		title = "电子印章管理";
	}
	if (type == "company" || type == "group") {
		$('.address').flexigrid({
					allDouble : true,
					height : 170,
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
					}, {
						name : '印章密码修改日志',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}]

				});
	} else {
		$('.address').flexigrid({
					allDouble : true,
					height : 170,
					width : 'auto',
					minwidth : 30,
					title : title,
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
						name : '停用',
						bclass : 'delete',
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
					}, {
						name : '印章密码修改日志',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '下载印章制作工具',
						bclass : 'load',
						onpress : action
							// 当点击调用方法
						}]

				});
	}
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#jqModel").jqmShow();
				$("#butdiv").show();
				$("#psw").hide();
				$("#psw2").hide();
				$("input#tosavesign").hide();
				$("input#tosavestamp").hide();
				document.cstaffForm.reset();
				document.cstaffFormsign.reset();
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
				var type3 = $("span#type3", "tr#" + enterpriseStampID).text();
				$("#jqModel").jqmShow();
				var useStamp = $p.find("input#useStatus").val();
				if (type3 == "stamp") {
					$("#cstaffForm").show();
					$("#cstaffFormsign").hide();
					$("#jqModel #butdiv").hide();
					$("input#tosavestamp").show();
					if (useStamp == "use") {
						$("input#tosavestamp").val("停用");
					} else {
						$("input#tosavestamp").val("启用");
					}

				}
				if (type3 == "sign") {
					$("#cstaffForm").hide();
					$("#cstaffFormsign").show();
					$("#jqModel #butdiv").hide();
					$("input#tosavesign").show();
					if (useStamp == "use") {
						$("input#tosavesign").val("停用");
					} else {
						$("input#tosavesign").val("启用");
					}
				}
				$("#psw").show();
				$("#psw2").show();
				break;
			case '删除' :
				if (enterpriseStampID == "") {
					alert('请选择！');
					return
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
			case '停用' :
				if (enterpriseStampID == "") {
					alert('请选择！');
					return
				}
				$p = $("tr#" + enterpriseStampID);
				var useStamp = $p.find("input#useStatus").val();
				if (useStamp == "unuse" || useStamp == "") {
					alert("已停用");
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
											+ "ea/enterprisestamp/ea_stopUseStamp.jspa");
					document.cstaffForm.submit.click();

					token = 2;
				}
				break;

			case '导出' :
				var url = basePath
						+ "ea/enterprisestamp/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search + "&gore=e&type="
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
				var type3 = $("span#type3", "tr#" + enterpriseStampID).text();
				$("#jqModel").jqmShow();
				var useStamp = $p.find("input#useStatus").val();

				if (type3 == "stamp") {
					$("#cstaffForm").show();
					$("#cstaffFormsign").hide();
					$("#jqModel #butdiv").hide();
					$("input#tosavestamp").hide();
					$("#psw").hide();
					$("#cstaffForm #tosave").hide();
				}
				if (type3 == "sign") {
					$("#cstaffForm").hide();
					$("#cstaffFormsign").show();
					$("#jqModel #butdiv").hide();
					$("input#tosavesign").hide();
					$("#psw2").hide();
					$("#cstaffFormsign #tosave3").hide();
				}
				break;
			break;
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?search="
					+ search + "&gore=e&type=" + type;
			numback(url);
			break;
		case '印章使用日志' :
			if (enterpriseStampID == "") {
				alert("请选择具体印章！");
				return;
			}
			personurl = basePath
					+ "ea/stamplog/ea_getListStampLog.jspa?pageNumber="
					+ pNumber + "&gore=e&type=" + type
					+ "&stampLog.enterpriseStampID=";
			$("#mainframe").css({
						"height" : "auto",
						"width" : "100%"
					}).attr("src", personurl + enterpriseStampID);
			$(window).resize();
			break;

		case '印章密码修改日志' :
			if (enterpriseStampID == "") {
				alert("请选择具体印章！");
				return;
			}
			$("#mainframe")
					.css({
								"height" : "auto",
								"width" : "100%"
							})
					.attr(
							"src",
							basePath
									+ "ea/enterprisestamp/ea_getStampPswLog.jspa?enterpriseStampID="
									+ enterpriseStampID + "&pageNumber="
									+ pNumber);

			$(window).resize();
			break;
			
	   case  '下载印章制作工具':
	       window.open(basePath+"js/cabs/sealTool.zip");
	       break;
	   
	   
	}
}

	$("table tr[id]").click(function() {
				enterpriseStampID = this.id;
				// if (personurl) {
				// $("#mainframe").attr("src", personurl + enterpriseStampID);
				// }
				stampName = $(this).find("span#stampName").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("table tr[id]").dblclick(function() {
				enterpriseStampID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				if (type != "company" && type != "group") {
					action("修改");
				} else {
					action("查看");
				}
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
	if (type != "company" && type != "group") {
		$(".address tr[id]").dblclick(function() {
					action("修改");

				});
	}
	$("#close").click(function() {// 取消

				$("#jqModel").jqmHide();
				re_load();
			});
	$("#closepsw").click(function() {// 取消
				$("#jqModelpsw").jqmHide();
				re_load();
			});
	$("#closeS").click(function() {// 取消
				$("#jqModelSearch").jqmHide();
			});
	$(".JQueryreturn").click(function() {
				$("input[type=radio].JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmHide();
				re_load();
			});
	// 印章保存
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
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});

	// 签名保存
	$("input#tosave3").click(function() {
		$t = $("table#stafftable");
		if ($("select#userName", $t).attr("value") == "") {
			alert("请选择人员！");
			return;
		}
		$("#cstaffFormsign")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/enterprisestamp/ea_addEnterpriseStamp.jspa?pageNumber="
								+ pNumber);
		document.cstaffFormsign.submit.click();
		token = 2;
	});
	// 修改密码保存
	$("input#tosave2").click(function() {
		var enterpriseStampID = $("input[name=a]:radio:checked").val();

		if (enterpriseStampID == undefined) {
			alert('请选择!');
			return;
		}
		var oldpsw = $("input#oldpsw").val();
		var newpsw = $("input#newpsw").val();
		var confirmnewpsw = $("input#confirmnewpsw").val();
		if (newpsw != confirmnewpsw) {
			alert("新密码和确认新密码不一致！");
			return;
		}

		var url = basePath
				+ "/ea/enterprisestamp/sajax_n_ea_changeStampPsw.jspa?oldpsw="
				+ oldpsw + "&newpsw=" + newpsw + "&enterpriseStampID="
				+ enterpriseStampID;
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var jsonresult = eval("(" + data + ")");
						var result = jsonresult.result;
						if (result == "suc") {
							alert("修改密码成功！");
							$("#jqModelpsw").jqmHide();
						} else {
							alert("旧密码输入错误！");
						}
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});

	});

});
function re_load() {
	if (token)
		document.location.reload();// .href=basePath+"ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
	return;
}

function updatePsw() {
	$("#jqModelpsw").jqmShow();
	document.cstaffFormpsw.reset();
}

function cancelpsw() {
	$("#jqModelpsw").jqmHide();
}

function updateUseStatus() {
	$f = $('#cstaffForm');
	$f.find(':input#enterpriseStampID').val(enterpriseStampID);
	$p = $("tr#" + enterpriseStampID);
	var useStamp = $p.find("input#useStatus").val();
	var useOperation;
	if (useStamp == "use") {
		useOperation = "unuse";
	} else {
		useOperation = "use";
	}
	$f.find(':input#useStatus2').val(useOperation);
	if (confirm("确定继续？")) {
		$f.attr("target", "hidden").attr("action",
				basePath + "ea/enterprisestamp/ea_operationUseStatus.jspa");
		document.cstaffForm.submit.click();
		token = 2;
	}
}

function stamp() {
	$("#cstaffForm").show();
	$("#cstaffFormsign").hide();
	$("#stamptype").attr("class", "type");
	$("#signtype").removeAttr("class");
}
function sign() {
	$("#cstaffForm").hide();
	$("#cstaffFormsign").show();
	$("#signtype").attr("class", "type");
	$("#stamptype").removeAttr("class");
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
				data:{
					companyID:val
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
		$("#cataffSearchTable #organizationID").html("<option value=''>请先选择公司</option>");
	}

}

// 选择人员
function importGY(url,type) { // 打开页面
	$("#daoRu")
			.attr("src", basePath + url + "?date=" + new Date() + "&hid=hid");

	$("#socialJqm").jqmShow();
	$("#socialJqm #type").val(type);
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择");
		return;
	}

	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	var type = $("#socialJqm #type").val();
	if (type == "seal") {
		$("#cstaffForm #responsibleName").val(staffName);
		$("#cstaffForm #responsibleID").val(childopertionID);
	} else {
		$("#cstaffFormsign #responsibleName").val(staffName);
		$("#cstaffFormsign #responsibleID").val(childopertionID);
	}
	
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();

}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}
