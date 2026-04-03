$(function() {
	var appraisalID = "";

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({ 
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '集团--考评汇总',
				minheight : 80,
				buttons : [{
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
				if (appraisalID == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + appraisalID);
				document.appraisalForm.reset();
				var url = basePath
						+ "ea/staffappraisal/sajax_n_ea_getCSPayScale.jspa?staffappraisal.staffID="
						+ staffID + "&staffappraisal.payScaleID=" + payScaleID
						+ "&date1=" + new Date();
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var payvo = member.payvo;
								if (payvo == null) {
									alert("还未分配职务级别");
								} else {
									$("#appraisalForm #scale")
											.text(payvo.scale);
									$("#appraisalForm #payScaleID").attr(
											"value", payvo.payScaleID);
									$("#appraisalForm #positionPay")
											.text(payvo.positionPay);
									$("#appraisalForm #pushMoney")
											.text(payvo.pushMoney);
									$("#appraisalForm #timingMoney")
											.text(payvo.timingMoney);
									$("#appraisalForm #awardPay")
											.text(payvo.awardPay);
									$("#appraisalForm #stPay")
											.text(payvo.stPay);
									$("#appraisalForm #secrecyPay")
											.text(payvo.secrecyPay);
									$("#appraisalForm #safetyAward")
											.text(payvo.safetyAward);
									$($p).find("span[id]").each(function() {
										$("#appraisalForm")
												.find(":input[name]#" + this.id)
												.val($(this).text());
									});
									sunstaff();
									$("#jqModelAppraisal").jqmShow();

								}
							},
							error : function cbf(data) {
								retoken = 0;
								alert("数据获取失败！");
							}
						});
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/appraisalcompany/ea_showExcel.jspa?search="
						+ search + "&startdate=" + startdate + "&enddate="
						+ enddate;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/appraisalcompany/ea_getAppraisalList.jspa?search="
						+ search + "&startdate=" + startdate + "&enddate="
						+ enddate;
				numback(url);
				break;
		}
	}
	$(".menu00").click(function() {
				$(this).hide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				appraisalID = this.id;
				payScaleID = $(this).find("#payScaleID").text();
				staffID = $(this).find("#staffID").text();
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				appraisalID = this.id;
				payScaleID = $(this).find("#payScaleID").text();
				staffID = $(this).find("#staffID").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModelAppraisal").jqmHide();

			});
	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/appraisalcompany/ea_toSearch.jspa?pageNumber="
								+ pNumber );
		document.postSearchForm.submit.click();
	});
});

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
});

function sunstaff() {
	workday = $("input#workDateSaturation", $("#appraisalForm")).attr("value");
	Allsum = 0;
	positionPaysum = 0;
	achievementsum = 0;
	$(".positionPay").each(function() {
				positionPaysum = positionPaysum + $(this).attr("value") * 1;
				addpositionPay(positionPaysum);
			});
	$(".achievement").each(function() {
				achievementsum = achievementsum + $(this).attr("value") * 1;
				addachievementsum(achievementsum);
			});

	$(":input[class != 'nocheck']", "#staffappr").each(function() {
				Allsum = Allsum + $(this).attr("value") * 1;
				addAllsum(Allsum);
			});
	addworkday(workday);
}

function addpositionPay(positionPaysum) {
	var pc1 = Math.round(positionPaysum * 10000 / 15) / 100;
	var positionPayMoney = Math.round(positionPaysum
			* $("#appraisalForm #positionPay").text() * 100 / 15)
			/ 100;
	var positionPayScore = Math.round(positionPayMoney * 100 * workday / 20)
			/ 100;
	$("#sumScole1").html(positionPaysum);
	$("#pc1").html(pc1 + "%");
	$("#positionPayMoney").html(positionPayMoney);
	$("#positionPayScore").html(positionPayScore);
}
function addachievementsum(achievementsum) {
	var pc1 = Math.round(achievementsum * 10000 / 15) / 100;
	var positionPayMoney = Math.round(achievementsum
			* $("#appraisalForm #timingMoney").text() * 100 / 15)
			/ 100;
	var positionPayScore = Math.round(positionPayMoney * 100 * workday / 20)
			/ 100;
	$("#sumScole3").html(achievementsum);
	$("#pc3").html(pc1 + "%");
	$("#timingMoneyMoney").html(positionPayMoney);
	$("#timingMoneyScore").html(positionPayScore);
}
function addAllsum(Allsum) {
	var pc1 = Math.round(Allsum * 10000 / 75) / 100;
	var positionPayMoney = Math.round(Allsum
			* $("#appraisalForm #pushMoney").text() * 100 / 75)
			/ 100;
	var positionPayScore = Math.round(positionPayMoney * 100 * workday / 20)
			/ 100;
	$("#allMoney").html(Allsum);
	$("#allpc").html(pc1 + "%");
	$("#pushMoneyMoney").html(positionPayMoney);
	$("#pushScore").html(positionPayScore);
}
function addworkday(workday) {
	var stPay = Math.round($("#appraisalForm #stPay").text() * 100 * workday
			/ 20)
			/ 100;
	var secrecyPay = Math.round($("#appraisalForm #secrecyPay").text() * 100
			* workday / 20)
			/ 100;
	var safetyAward = Math.round($("#appraisalForm #safetyAward").text() * 100
			* workday / 20)
			/ 100;
	var awardPayScore = Math.round($("#appraisalForm #awardPay").text() * 100
			* workday / 20)
			/ 100;
	$("#stPayscore").html(stPay);
	$("#awardPayScore").html(awardPayScore);
	$("#secrecyPayscore").html(secrecyPay);
	$("#safetyAwardscore").html(safetyAward);
}
