$(function() {
	var gamJeomID = "";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
    $p = $("div#jqModelSearch");
	var title = "";
	if (type == "c") {
		title = "奖罚单公司汇总";
		
		flexh(title);
		$p.find("tr#cc").hide();
	} else if (type == "g") {
		title = "奖罚单集团汇总";
		flexh(title);
		$p.find("tr#cc").show();
	} else {
		flex();
		$p.find("tr#cc").hide();
	}
	function flex() {
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : '奖罚单',
					minheight : 80,
					buttons : [{
						name : '添加',
						bclass : 'add',
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
						name : '导出',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '打印预览',
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
	function flexh(title) {
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : title,
					minheight : 80,
					buttons : [{
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
						name : '导出',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '打印预览',
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
			case '添加' :
				gamJeomID = "";
				$(".xianshi").hide();
				$(".yincang").show();
				$("td.disName").find(".error").remove();
				$("td.disName").find(".corect").remove();
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				getID();
				$("#operator", $("#cstaffForm")).val(staff);
				$("input.JQuerySubmit").show();
				$("#jqModel").jqmShow();
				break;
			case '查看' :
				if (gamJeomID == "") {
					alert('请选择!');
					return;
				}

				$("td.disName").find(".error").remove();
				$("td.disName").find(".corect").remove();
				document.cstaffForm.reset();

				$t = $("div#jqModel");
				$p = $("tr#" + gamJeomID);
				$p.find("span[id]").each(function() {
							$t.find("#" + this.id).text($(this).text());
						});

				$(".yincang").hide();
				$(".xianshi").show();

				acceName = $p.find("#look1").text();
				if ($p.find("#look1").text() != '') {
					var onload = acceName.substring(acceName.lastIndexOf("."),
							acceName.length);
					if (onload.toLowerCase() != ".jpg"
							&& onload.toLowerCase() != ".gif"
							&& onload.toLowerCase() != ".png") {
						$("#isLoad")
								.find("a")
								.attr(
										"href",
										basePath
												+ "ea/publicreceipts/ea_downFile.jspa?downLoadPath="
												+ acceName);
						$("#isLoad").show(); // 附件下载显示
					} else {
						$("#isLook").show(); // 附件查看显示
					}
				} else {
					$("#isNull").show(); // 附件无显示
				}
				$("input.JQuerySubmit").hide();
				$("#jqModel").jqmShow();
				$("#rorpReason1", $("#cstaffForm")).blur();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/publicreceipts/ea_ShowExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + search + "&type=" + type;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/publicreceipts/ea_getListRewardPunishment.jspa?search="
						+ search + "&type=" + type;
				numback(url);
				break;
			case '打印预览' :
				if (gamJeomID == "") {
					alert('请选择!');
					return;
				}
				url = basePath + "ea/publicreceipts/ea_printReward.jspa?prID="
						+ gamJeomID;
				open(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				gamJeomID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					notoken = 0;
					return;
				}
				if ($("#rorpReason").val() == '') {
					alert('奖罚原因不能为空！');
					return;
				}
				if (notoken) {
					alert("正在提交数据");
					return;
				}
				notoken = 1;
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/publicreceipts/ea_saveRewardPunishment.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				document.cstaffForm.reset();
				token = 1;
				getID();
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("span.hideAll").hide();
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 关闭
				$("span.hideAll").hide();
				$("#jqModel").jqmHide();
				re_load();

			});
	$(".JQueryflexme tr[id]").each(function() { // 页面遍历判断附件格式
				var load = $("tr#" + this.id).find("#look1").text();
				if (load != '') {
					var onload = load.substring(load.lastIndexOf("."),
							load.length);
					if (onload.toLowerCase() != ".jpg"
							&& onload.toLowerCase() != ".gif"
							&& onload.toLowerCase() != ".png") {
						$("tr#" + this.id).find("#load").show();
					} else {
						$("tr#" + this.id).find("#look").show();
					}
				} else {
					$("tr#" + this.id).find("#wu").show();
				}
			});

	$("input.JQuerySubmitPrint").click(function() { // 打印预览
				url = basePath + "ea/publicreceipts/ea_printReward.jspa?prID="
						+ gamJeomID;
				open(url);
			});

	$("#tosearch").click(function() { // 查询
				$("#postSearchForm")
						.attr(
								"action",
								basePath
										+ "ea/publicreceipts/ea_toSearchReward.jspa?pageNumber="
										+ ppageNumber);
				document.postSearchForm.submit.click();
			});

	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

	$("#rorpDeductPoint", $("#cstaffForm")).blur(function() { // 奖罚分失去焦点事件,获取金额大写
				var rorpDeductPoint = $("#rorpDeductPoint", $("#cstaffForm"))
						.val();
				if (rorpDeductPoint == "") {
					return;
				} else {
					if (rorpDeductPoint <= 0 || isNaN(rorpDeductPoint)) {
						alert("奖罚分必须为正数!");
						$("#rorpDeductPoint").val("").focus();
						return;
					}
				}
				var points = parseFloat(rorpDeductPoint) * 20;
				$("#rorpMoney", $("#cstaffForm")).val(points);
				var url = basePath
						+ "ea/publicreceipts/sajax_ea_getMoneyToUp.jspa?publicreceiptsChild.rorpMoney="
						+ points;
				$.ajax({
							url : url,
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								$("#rorpMyriad", $("#cstaffForm"))
										.val(member.money);
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
			});

	$(function() {

		var url = "";

		if (type == "1") {
			url = basePath+"ea/responsibilities/sajax_n_ea_getoList.jspa?date="
					+ new Date().toLocaleString();

			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var oList = member.organizationlist;
							var data = new Array();
							data[0] = {
								id : treeID,
								pid : '-1',
								text : treeName
							};
							for (var i = 0; i < oList.length; i++) {
								data[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
							$t = $("div#jqModel");
							$p = $("div#jqModelSearch");
							var ts = new TreeSelector(
									$t.find("select#deptID")[0], data, -1);
							ts.createTree();
							var t1 = new TreeSelector(
									$p.find("select#deptID")[0], data, -1);
							t1.createTree();

						},
						error : function cbf(data) {
							alert("数据获取失败！")
						}
					});

		} 
		if(type=="c"){
			 bmdept(treePID);
		}


		if (type == "g") {
			var url = basePath
					+ "ea/publicreceipts/sajax_ea_getAllCompanyByCurrent.jspa?date="
					+ new Date().toLocaleString();
			$.ajax({
						url : encodeURI(url),
						type : "post",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var companylist = member.companylist;
							var str = "<option value=''>全部</option>";
							for (var i = 0; i < companylist.length; i++) {
								var obj = companylist[i];
								str += "<option title='" + obj.companyName
										+ "'value='" + obj.companyID + "'>"
										+ obj.companyName + "</option>";
							}
							$("select#companyID").html(str);

						},
						error : function cbf(data) {
							alert("数据获取失败！")
						}
					});
		}

	   $("div#jqModelSearch").find("select#companyID").change(function() {
					if ($(this).val() != '') {
						bmdept(this.value);
					} else {
						$("option", $("#orgID")).remove();
						$("#orgID").html("<option value=''>请选择公司</option>");
					}
				});


	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/publicreceipts/ea_getListRewardPunishment.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&type=" + type;
}



function bmdept(companyID){
	$p = $("div#jqModelSearch");
	$("option", $p.find("select#deptID")).remove();
			url = basePath+"ea/publicreceipts/sajax_ea_getoList.jspa?date="
					+ new Date().toLocaleString();

			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						data:{
							
							companyID:companyID
						},
						success : function cbf(data) {
							
							/** **添加部门列表** */
							var member = eval("(" + data + ")");
							var oList = member.organizationlist;
							var data2 = new Array();
							data2[0] = {
								id : companyID,
								pid : '-1',
								text : '全部'
							};
							for (var i = 0; i < oList.length; i++) {
								data2[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
							$p = $("div#jqModelSearch");
							ts = new TreeSelector($p.find("select#deptID")[0], data2, -1);
							ts.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！")
						}
					});
}
