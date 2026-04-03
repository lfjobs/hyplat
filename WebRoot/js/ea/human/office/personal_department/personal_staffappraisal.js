$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.staffappraisal').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '综合考评----当前人员：' + parent.staffName,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
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
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$t = parent.$("#appraisalForm");
				parent.document.appraisalForm.reset();
				var url = basePath
						+ "ea/staffappraisal/sajax_n_ea_getCSPayScale.jspa?staffappraisal.staffID="
						+ pstaffappraisalstaffID + "&date1=" + new Date();
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
							parent.$("#appraisalForm #scale").text(payvo.scale);
							parent.$("#appraisalForm #payScaleID").attr(
									"value", payvo.payScaleID);
							parent.$("#appraisalForm #positionPay")
									.text(payvo.positionPay);
							parent.$("#appraisalForm #pushMoney")
									.text(payvo.pushMoney);
							parent.$("#appraisalForm #timingMoney")
									.text(payvo.timingMoney);
							parent.$("#appraisalForm #stPay").text(payvo.stPay);
							parent.$("#appraisalForm #awardPay")
									.text(payvo.awardPay);
							parent.$("#appraisalForm #secrecyPay")
									.text(payvo.secrecyPay);
							parent.$("#appraisalForm #safetyAward")
									.text(payvo.safetyAward);
							
							parent.$("#appraisalForm #pietypay")
							.text(payvo.pietypay);
							parent.$("#appraisalForm #campaignpay")
							.text(payvo.campaignpay);
							parent.$("#appraisalForm #telecompay")
							.text(payvo.telecompay);
							parent.$("#appraisalForm #pkpay")
							.text(payvo.pkpay);
							parent.$("#appraisalForm #living")
							.text(payvo.living);
							
							parent.sunstaff(logLocklist);
							$t.find(":input").each(function() {
										$(this).removeAttr("readonly");
									});
							parent.$("#jqModelAppraisal").jqmShow();
						}
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});
				break;
			case '修改' :
				if (appraisalID == '') {
					alert("请选择！");
					return;
				}
				var todate = $("span#appraisalDate", $("tr#" + appraisalID)).text();
				var tomanths = todate.substring(0,todate.lastIndexOf("-"));
				var url = basePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+pstaffappraisalstaffID+"&tomanths="+tomanths;
				$.ajax({ //判断月考评是否被加锁
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							var member = eval("(" + data + ")");
							var islock = member.islock;
							if(islock != ''){
							 	alert("此人员"+islock+"的考评已被加锁,不可修改！");
							 	return;
							}
							$p = $("tr#" + appraisalID);
							$t = parent.$("#appraisalForm");
							parent.document.appraisalForm.reset();
							var url = basePath
									+ "ea/staffappraisal/sajax_n_ea_getCSPayScale.jspa?staffappraisal.staffID="
									+ pstaffappraisalstaffID
									+ "&staffappraisal.payScaleID=" + payScaleID
									+ "&date2=" + new Date();
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
										parent.$("#appraisalForm #scale").text(payvo.scale);
										parent.$("#appraisalForm #payScaleID").attr(
												"value", payvo.payScaleID);
										parent.$("#appraisalForm #positionPay")
												.text(payvo.positionPay);
										parent.$("#appraisalForm #pushMoney")
												.text(payvo.pushMoney);
										parent.$("#appraisalForm #timingMoney")
												.text(payvo.timingMoney);
										parent.$("#appraisalForm #stPay").text(payvo.stPay);
										parent.$("#appraisalForm #awardPay")
												.text(payvo.awardPay);
										parent.$("#appraisalForm #secrecyPay")
												.text(payvo.secrecyPay);
										parent.$("#appraisalForm #safetyAward")
												.text(payvo.safetyAward);
										parent.$($p).find("span[id]").each(function() {
											$t.find(":input[name]#" + this.id).val($(this)
													.text()).attr("readonly", "true");
										});
										parent.sunstaff(logLocklist);
										parent.$("#jqModelAppraisal").jqmShow();
			
									}
								},
								error : function cbf(data) {
									retoken = 0;
									alert("数据获取失败！");
								}
							});
						}
				});
				break;
			case '删除' :
				if (appraisalID == '') {
					alert("请选择！");
					return;
				}
				var todate = $("span#appraisalDate", $("tr#" + appraisalID)).text();
				var tomanths = todate.substring(0,todate.lastIndexOf("-"));
				var url = basePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+pstaffappraisalstaffID+"&tomanths="+tomanths;
				$.ajax({ //判断月考评是否被加锁
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							var member = eval("(" + data + ")");
							var islock = member.islock;
							if(islock != ''){
							 	alert("此人员"+islock+"的考评已被加锁,不可删除！");
							 	return;
							}
							$f = $('#staffappraisalForm');
							if (confirm("是否删除？")) {
								$f
										.attr("target", "hidden")
										.attr(
												"action",
												basePath
														+ "ea/staffappraisal/ea_delStaffAppraisal.jspa?pageNumber="
														+ ppageNumber
														+ "&staffappraisal.staffID="
														+ pstaffappraisalstaffID
														+ "&staffappraisal.appraisalID="
														+ appraisalID);
								document.staffappraisalForm.submit.click();
								$("tr#" + appraisalID).remove();
								appraisalID = "";
								payScaleID = "";
								token = 11;
							}
						}
				});
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/staffappraisal/ea_getListStaffAppraisal.jspa?staffappraisal.staffID="
						+ pstaffappraisalstaffID;
				numback(url);
				break;
			case '导出' :
				//var sdate = $("#sdate").attr("value");
				//var edate = $("#edate").attr("value");
				var url = basePath
						+ "ea/staffappraisal/ea_showExcel.jspa?staffappraisal.staffID="
						+ pstaffappraisalstaffID + "&startdate=" + pstartdate
						+ "&enddate=" + penddate;
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
		action("修改");
	});
	
	// 根据条件查询
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#appraisalForm")
				.attr(
						"action",
						basePath
								+ "ea/staffappraisal/ea_getListStaffAppraisal.jspa?pageNumber="
								+ ppageNumber);
		document.appraisalForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/staffappraisal/ea_getListStaffAppraisal.jspa?staffappraisal.staffID="
				+ pstaffappraisalstaffID + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");;
}