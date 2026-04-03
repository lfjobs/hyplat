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
				title : '投诉处理箱',
				minheight : 350,
				buttons : [{
					name : '查看',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '领导审批',
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
					name : '回复',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '标为已处理',
					bclass : 'excel',
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
							if (this.id == "suggestion" || this.id == "reply") {
								$t.find("#" + this.id).text($(this).text());
							}
						});
				$t.find("#hidcontent").val($p.find("input[id=docPath]").val());
				$t.find("img#dealstatus").attr("src",
						$p.find("img#statusPic").attr("src"));

				$("#jqModelView").jqmShow();
				$("#help").hide();
				break;
			case '领导审批' :
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
				$("#SendForm2 #hidids").val(Ids);
				$("#jqModelSend2").jqmShow();

				break;
			case '删除' :
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
				if (confirm("确认继续？")) {
					var url = basePath
							+ "ea/extralflow/sajax_ea_deleteComplaint.jspa?dat="
							+ new Date();
					$.ajax({
								url : encodeURI(url),
								type : "get",
								async : false,
								dataType : "json",
								data : {
									Ids : Ids
								},
								success : function(data) {
									var member = eval("(" + data + ")");
									var result = member.result;
									if (result == "") {
										alert("删除成功");
									} else {
										alert("编号:" + result + "审批中不能删除");
									}
									token = 2;
									re_load();

								},
								error : function(data) {
									alert("数据读取失败");
								}
							});
				}
				break;
			case '回复' :
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

				var userName = $(".draft0 tr[id=" + Id + "] td:eq(4)")
						.find("span").text();
				if (userName == "匿名") {
					alert("对方匿名不可回复");
					return;
				}
				$("#replyForm #hidreply").val(Id);
				$("#jqModelReply").jqmShow();
				break;
			case '标为已处理' :
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
				var url = basePath + "ea/extralflow/sajax_ea_setDealed.jspa?dat="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						data : {
							Ids : Ids

						},
						success : function(data) {
							var member = eval("(" + data + ")");
							var result = member.result;
							if (result == "") {
								alert("操作成功");
							} else {
								alert("编号:" + result + "无法进行此操作");
							}
							token = 2;
							re_load();

						},
						error : function(data) {
							alert("数据读取失败");
						}
					});

				/*$("#replyForm #hidreply").val(Ids);
				$("#replyForm").attr("target", "hidden").attr(
						"action",
						basePath
								+ "ea/extralflow/ea_setDealed.jspa?pageNumber="
								+ pNumber + "&date=" + new Date());
				document.replyForm.submit.click();
				token = 2;
				re_load();*/

				break;
			case '查询' :
				document.searchForm.reset();
				$("#jqModelSearch").jqmShow();
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/extralflow/ea_getComplaintList.jspa?type=draft&date="
						+ new Date();
				numback(url);
				break;
		}
	}

	$("#tosearch").click(function() {
		$("#searchForm").attr(
				"action",
				basePath + "ea/extralflow/ea_toSearchPrepare.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
	});
	// 回复
	$("#toReply").click(function() {
		$("#replyForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/extralflow/ea_toReply.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.replyForm.submit.click();
		token = 2;
		re_load();
	});

	// 查看点击word
	$("#viewtable img#content").click(function() {
				var docPath = $("#viewtable #hidcontent").val();
				OpenWord(docPath);

			});

	// 发送审批
	$("#SendForm2 #send").click(function() {
		var comSubscriber = $("#SendForm2 #companyIDofSubscriber2").val();
		var deptSubscriber = $("#SendForm2 #deptIDofSubscriber2").val();
		var subscriberID = $("#SendForm2 #subscriberID2").val();
		var Ids = $("#SendForm2 #hidids").val();
		if (confirm("确认继续？")) {
			var url = basePath + "ea/extralflow/sajax_ea_createDoc.jspa?dat="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						data : {
							Ids : Ids,
							comSubscriber : comSubscriber,
							deptSubscriber : deptSubscriber,
							subscriberID : subscriberID

						},
						success : function(data) {
							var member = eval("(" + data + ")");
							var result = member.result;
							if (result == "") {
								alert("发送成功");
							} else {
								alert("编号:" + result + "无法再发送");
							}
							token = 2;
							re_load();

						},
						error : function(data) {
							alert("数据读取失败");
						}
					});
		}

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
						$("select#companyIDofSubscriber2").html(str);

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});

		// 收件公司
		$("#SendForm2 #companyIDofSubscriber2").change(function() {

			if ($(this).val() != '') {
				bmdept(this.value);
			} else {
				$("option", $("#deptIDofSubscriber2")).remove();
				$("#deptIDofSubscriber2")
						.html("<option value=''>请选择公司</option>");
			}
		});

		// 收件部门
		$("#SendForm2 #deptIDofSubscriber2").change(function() {

					var temp = $("#deptIDofSubscriber2").val();
					if (temp.substring(0, 7) != 'company') {
						getPerson($("#companyIDofSubscriber2").val(),
								this.value);
					} else {
						$("option", $("select#subscriberID2")).remove();
						$("#subscriberID2")
								.html("<option value=''>请选择部门</option>");
					}
				});

		$(".draft0 tr[id]").dblclick(function() {
					Id = this.id;
					$("input.JQuerypersonvalue", $(this)).attr("checked",
							"checked");
					action("查看");
				});

		$(".close").click(function() {
					$("#help").show();
				});

	});
});

// 根据
function getPerson(company, org) {

	$("option", $("select#subscriberID2")).remove();

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

					$("#subscriberID2").html(str);
				}
			});
}

function bmdept(val) {

	$("option", $("#deptIDofSubscriber2")).remove();

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
					$("#deptIDofSubscriber2").html(str);

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
				+ "ea/extralflow/ea_getComplaintList.jspa?type=draft&pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}