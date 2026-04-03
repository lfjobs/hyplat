$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.staffappraisal').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '综合考评汇总',
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '查看' :

				if (appraisalID == '') {

					alert("请选择！");
					return;
				}
				$p = $("tr#" + appraisalID);
				$t = $("#appraisalForm");
				document.appraisalForm.reset();

				var url = basePath
						+ "ea/staffappraisal/sajax_n_ea_getCSPayScale.jspa?staffappraisal.staffID="
						+ pstaffappraisalstaffID
						+ "&staffappraisal.payScaleID=" + payScaleID + "&date="
						+ new Date();
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var payvo = member.payvo;
								var logLocklist = member.logLocklist;
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
									$("#appraisalForm #stPay")
											.text(payvo.stPay);
									$("#appraisalForm #secrecyPay")
											.text(payvo.secrecyPay);
									$("#appraisalForm #safetyAward")
											.text(payvo.safetyAward);
									$($p).find("span[id]").each(function() {
										$t.find(":input[name]#" + this.id)
												.val($(this).text());
									});
									sunstaff(logLocklist);
									$("#jqModelAppraisal").jqmShow();
								}
							},
							error : function cbf(data) {
								retoken = 0;
								alert("数据获取失败！");
							}
						});
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/staffappraisalsummary/ea_getStaffAppraisalList.jspa?startdate="
						+ pstartdate + "&enddate=" + penddate + "&search="
						+ search;
				numback(url);
				break;
			case '导出' :
				//var sdate = $("#sdate").attr("value");
				//var edate = $("#edate").attr("value");
				var url = basePath
						+ "ea/staffappraisalsummary/ea_showExcel.jspa?startdate="
						+ pstartdate + "&enddate=" + penddate + "&search="
						+ search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}
	// 点击选中

	$(".staffappraisal tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				appraisalID = this.id;
				payScaleID = $(this).find("#payScaleID").text();
			});
	$(".staffappraisal tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				appraisalID = this.id;
				payScaleID = $(this).find("#payScaleID").text();
				action('查看');

			});
	// 根据条件查询
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#searchForm")
				.attr(
						"action",
						basePath
								+ "ea/staffappraisalsummary/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.searchForm.submit.click();
	});
});

function sunstaff(logLocklist) {
	loglist = logLocklist;
	appDate = $("input#appraisalDate", $("#appraisalForm")).val();
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
	$("#stPayscore").html(stPay);
	$("#secrecyPayscore").html(secrecyPay);
	$("#safetyAwardscore").html(safetyAward);
}
