$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 355,
				width : 'auto',
				minwidth : 30,
				title : '员工级别变更单',
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
					bclass : 'mysearch',
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
	function action(com, grid) {
		switch (com) {
			case '添加' :
				prID = '';
				$(".xianshi").hide();
				$(".yincang").show();
				$("td.disName").find(".error").remove();
				$("td.disName").find(".corect").remove();
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();

				$("#voucherNum1").val(vouch);
				var mon = "";
				var days = "";
				var myDate = new Date();
				if((myDate.getMonth()+ 1)<10){
					mon = "0" + (myDate.getMonth() + 1);
				}else{
					mon = myDate.getMonth() + 1;
				}
				if(myDate.getDate()<10){
					days = "0" + myDate.getDate();
				}else{
					days = myDate.getDate();
				}
				$("#applyDate1", $("#cstaffForm")).val(myDate.getFullYear()+"-"+mon+"-"+days);
				$("#jqModel").jqmShow();
				break;
			case '查看' :
				if (prID == "") {
					alert('请选择!');
					return;
				}
				$("td.disName").find(".error").remove();
				$("td.disName").find(".corect").remove();
				document.cstaffForm.reset();

				$t = $("div#jqModel");
				$p = $("tr#" + prID);
				$p.find("span[id]").each(function() {
							$t.find("#" + this.id).text($(this).text());
						});

				$(".xianshi").show();
				$(".yincang").hide();

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
				$("#jqModel").jqmShow();
				$("#rankContent1",$("#cstaffForm")).blur();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/publicreceipts/ea_showRankExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + psearch;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/publicreceipts/ea_toRankSearch.jspa?search="
						+ psearch;
				numback(url);
				break;
			case '打印预览' :
				if (prID == "") {
					alert("请选择！");
					return;
				}
				window.open(basePath
						+ "ea/publicreceipts/ea_toprintRank.jspa?prID=" + prID);
				break;
		}
	}
	// 新加内内容开始 target 指向页面隐藏Hidden
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if ($("#rankChangeReason").find("input:checked").length == 0) {
					alert("请选择变动理由！");
					return;
				}
				if ($("#rankContent").val() == '') {
					alert("工作内容不能为空！");
					return;
				}
				if ($("#rankExamine").val() == '') {
					alert("自我评定不能为空！");
					return;
				}
				if ($("#other").val() != '') { // 变动理由中其他赋值
					$("#rankChangeReason").find("input:checked").val("其他："
							+ $("#other").val());
				}
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/publicreceipts/ea_saveRankPublicreceipts.jspa?pageNumber="
										+ ppageNumber);
				document.cstaffForm.submit.click();
				document.cstaffForm.reset();
				token = 13;
			});

	// 新加内容结束
	$(".JQueryflexme tr[id]").dblclick(function() { // 双击查看
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});

	$("input.JQueryreturn").click(function() {// 取消
				$("span.hideAll").hide(); // 附件显示隐藏
				$(".others").hide(); // 变动理由中其他文本框隐藏
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 关闭
				$("span.hideAll").hide(); // 附件显示隐藏
				$(".others").hide(); // 变动理由中其他文本框隐藏
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
				window.open(basePath
						+ "ea/publicreceipts/ea_toprintRank.jspa?prID=" + prID);
			});

	$(".JQueryflexme tr[id]").click(function() {
				prID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/publicreceipts/ea_toRankSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

	$("#principalOrganizationID").change(function() { // 获取选中部门
				parm = $(":selected").val();
			});

	$("#childPartnerName").css("border", "none"); // 人员编号边框隐藏
	$("#childPartnerName").click(function() {
				if ($("#partnerName").val() == '') {
					$("#childPartnerName").val("请先选择责任人！").css("color", "red");
				}
			});

	$("#rankOldleve").css("border", "none"); // 原级别明细边框隐藏
	$("#rankOldleve").click(function() {
				if ($("#partnerName").val() == '') {
					$("#rankOldleve").val("请先选择责任人！").css("color", "red");
				}
			});

	$("#rankStartdate").css("border", "none"); // 原级别起日期边框隐藏
	$("#rankStartdate").click(function() {
				if ($("#partnerName").val() == '') {
					$("#rankStartdate").val("请先选择责任人！").css("color", "red");
				}
			});

	$("#rankEnddate").css("border", "none"); // 原级别明细边框隐藏
	$("#rankEnddate").focus(function() {
				$("#rankEnddate").val("自动添加！").css("color", "red");
			}).blur(function() {
				$("#rankEnddate", $("#cstaffForm")).val("");
			});

	$("#rankChangeReason").click(function() { // 变动理由点击事件
		if ($("#rankChangeReason").find("input:checked").attr("number") == '5') {
			$(".others").show();
		} else {
			$(".others").hide();
		}
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/publicreceipts/ea_getRankPublicreceipt.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

$(function() {
			var url = basePath
					+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(cc) {
							var member = eval("(" + cc + ")");
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
							var data1 = new Array();
							data1[0] = {
								id : treePID,
								pid : '-1',
								text : treePName
							};
							for (var i = 0; i < oList.length; i++) {
								data[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
								data1[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
							$t = $("div#jqModel");
							$p = $("div#jqModelSearch");
							var ts = new TreeSelector(
									$t.find("select#principalOrganizationID")[0],
									data, -1);
							ts.createTree();
							var t1 = new TreeSelector(
									$p.find("select#principalOrganizationID")[0],
									data, -1);
							t1.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
		});