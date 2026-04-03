$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '集团--奖罚单汇总',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/gamjeomscompany/ea_showExcel.jspa?pageNumber="
						+ ppageNumber + "&search=" + search;
				open(url);
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
				$("#rorpReason1",$("#cstaffForm")).blur();
				break;
		}
	}
	
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

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				gamJeomID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("span.hideAll").hide();
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() { // 查询
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/gamjeomscompany/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/gamjeomscompany/ea_getGamJeomsList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

$(function() {
var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?date1="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var companylist = member.companylist;
							var data1 = new Array();
							data1[0] = {
								id : comID,
								pid : '-1',
								text : comName
							};
							for (var i = 0; i < companylist.length; i++) {
								data1[i + 1] = {
									id : companylist[i].companyID,
									pid : companylist[i].companyPID,
									text : companylist[i].companyName
								};
							}
							var ts3 = new TreeSelector($("select#companyID")[0],
									data1, -1);
							ts3.createTree();

						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
	$("#companyID").change(function() {
		$("#tosearch").attr("disabled","disabled");
		$("option", $("#orgID")).remove();
		var url = basePath
				+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
				+ this.value + "&date2=" + new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var oList = member.organizationlist;
						var data2 = new Array();
						data2[0] = {
							id : $("#companyID").attr("value"),
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
						ts = new TreeSelector($("#orgID")[0], data2, -1);
						ts.createTree();
						$("option[value=" + this.value + "]", $("#orgID"))
								.val("");
						$("#tosearch").attr("disabled","");		
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});

	});
});