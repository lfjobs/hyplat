$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.draft0').flexigrid({
				height : 340,
				width : 'auto',
				minwidth : 30,
				title : '审批投诉',
				minheight : 350,
				buttons : [{
					name : '查看',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '驳回',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '通过',
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
			case '查看' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var Id = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						Id = checkinput[i].value;
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				} else if (length > 1) {
					alert("最多选一个");
					return;
				}

				document.viewForm.reset();
				$t = $("table#viewtable");
				$p = $("tr#" + Id);
				$p.find("span[id]").each(function() {
							$t.find("span#" + this.id).text($(this).text());
							if (this.id == "suggestion") {
								$t.find("#" + this.id).text($(this).text());
							}
						});
				$t.find("#hidcontent").val($p.find("input[id=docPath]").val());
				$t.find("img#dealstatus").attr("src",
						$p.find("img#statusPic").attr("src"));

				$("#jqModelView").jqmShow();
				$("#help").hide();
				break;
			case '驳回' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var Ids = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						Ids += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#rejectForm #hidreg").val(Ids);
				$("#rejectForm #jqModelReject").jqmShow();

				break;
			case '通过' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var Ids = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						Ids += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#passForm #hidpass").val(Ids);
				$("#passForm #jqModelPass").jqmShow();
				break;
			case '查询' :
				document.searchForm.reset();
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/extralflow/ea_getUnfinishedList.jspa?type=examine&date="
						+ new Date();
				numback(url);
				break;
		}
	}


	$("#tosearch").click(function() {
		$("#searchForm").attr(
				"action",
				basePath + "ea/extralflow/ea_unfinishSearchPrepare.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
	});

	// 查看点击word
	$("#viewtable img#content").click(function() {
				var docPath = $("#viewtable #hidcontent").val();
				OpenWord(docPath);

			});
	$(".close").click(function() {
				$("#help").show();
			});

	$(function() {
		var url = basePath
				+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
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
									+ "'value='" + obj.companyID + "'>"
									+ obj.companyName + "</option>";
						}
						$("select#companyIDofSealer").html(str);

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});

		// 收件公司
		$("#SendForm2 #companyIDofSealer").change(function() {

					if ($(this).val() != '') {
						bmdept(this.value);
					} else {
						$("option", $("#deptIDofSealer")).remove();
						$("#deptIDofSealer")
								.html("<option value=''>请选择公司</option>");
					}
				});

		// 收件部门
		$("#SendForm2 #deptIDofSealer").change(function() {

					var temp = $("#deptIDofSealer").val();
					if (temp.substring(0, 7) != 'company') {
						getPerson($("#companyIDofSealer").val(), this.value);
					} else {
						$("option", $("select#sealerID")).remove();
						$("#sealerID").html("<option value=''>请选择部门</option>");
					}
				});
		$(".draft0 tr[id]").dblclick(function() {
					Id = this.id;
					$("input.JQuerypersonvalue", $(this)).attr("checked",
							"checked");
					action("查看");
				});

	});
});

// 根据
function getPerson(company, org) {

	$("option", $("select#sealerID")).remove();

	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffList.jspa?date111="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"currentCompanyID" : company,
					"currentOrgnizationID" : org
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "<option value=''>请选择收件人</option>";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj[0] + "'>" + obj[1] + "("
								+ obj[2] + ")</option>";
					}

					$("#sealerID").html(str);
				}
			});
}

function bmdept(val) {

	$("option", $("#deptIDofSealer")).remove();

	var url = basePath
			+ "ea/corganization/sajax_ea_getOrganizationLists.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"organizationID" : val
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var organizationList = member.organizationList;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < organizationList.length; i++) {
						var obj = organizationList[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#deptIDofSealer").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

function OpenWord(docPath) {
	open(basePath + "page/ea/common/weonlyreadprint.jsp?docPath=" + docPath
			+ "&fileType=W&WorkMode=2");
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/extralflow/ea_getUnfinishedList.jspa?type=examine&pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

// 驳回
function examine(jump) {

	if (jump == "reject") {
		$("#rejectForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/extralflow/ea_examineDocument.jspa?date="
						+ new Date());
		document.rejectForm.submit.click();
	} else if (jump == "sealortrans") {
		$("#hidcompany").val($("#companyIDofSealer").val());
		$("#hiddept").val($("#deptIDofSealer").val());
		$("#hidsealer").val($("#sealerID").val());
		$("#hidjump").val($("#sealortrans").val());
		$("#passForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/extralflow/ea_examineDocument.jspa?date="
						+ new Date());
		document.passForm.submit.click();

	} else {
		$("#hidjump").val(jump);
		$("#passForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/extralflow/ea_examineDocument.jspa?date="
						+ new Date());
		document.passForm.submit.click();

	}
	token = 2;

}

function selectPeople(sealortrans) {
	$("#passForm #jqModelPass").jqmHide();
	$("#jqModelSend2").jqmShow();
	$("#sealortrans").val(sealortrans);

}