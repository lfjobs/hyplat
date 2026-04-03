$(function() {
	$("#jqModelDimission").jqm({
		modal : true,// 离职员工
		overlay : 20
			//  
		});
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#newdistrict").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModelAppraisal").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '集团--离职员工汇总管理',
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
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				staffsize = 1;
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$("table#stafftable").find('img#photo').show();
				$("table#stafftable").find('#singleShuter').hide();
				$p = $("tr#" + personvalue);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				personIdentityCard = $t.find('#staffIdentityCard')
						.attr("value");
				LiuZhongYaoDeShaGuaDiZhi($p.find("span#address").text());
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/staffdimissioncompany/ea_showExcel.jspa?search="
						+ psearch;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/staffdimissioncompany/ea_getStaffDimissionList.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	$("#singleShuterphoto").click(function() {
				$("table#stafftable").find('img#photo').hide();
				$("table#stafftable").find('#singleShuter').show();
			});
	$(".menu00").click(function() {
				$(this).hide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});

	$("input.JQueryreturn1").click(function() {// 城市添加取消
				retoken = 0;
				$("#newdistrict").jqmHide();
				$("#jqModel").jqmShow();
			});
	$(".JQueryflexme tr[id]").click(function() {
				personvalue = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + personvalue);
				}
				staffName = $(this).find("span#staffName").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("input.JQuerySubmit").click(function() {// 保存
				if (notoken)
					return;
				notoken = 1;
				$(".IdentityCard").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				$t = $("table#stafftable");
				var addr = "";
				$(".JQueryaddress").find("select")
						.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--'
									&& $(this).text() != '--请选择--')
								addr = addr + $(this).text();
						});
				$("#cstaffForm").find("input#staffAddress").val(addr);
				if ($("table#stafftable").find('#singleShuter').is(":visible")) {
					var f = null;
					if (document.singleShuter)
						f = document.singleShuter;
					else
						f = document.getElementById('singleShuter');
					f.SavePhoto(pbasePath + "js/photo/save2.jsp");
					token = 2;
				} else {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 2;
				}
			});
	$("input.JQueryreturn").click(function() {// 取消
				if (token)
					document.location.href = basePath
							+ "ea/soincumbent/ea_getStaffListForIncumbent.jspa?search="
							+ psearch + "&pageNumber=" + ppageNumber;
				$("#jqModel").jqmHide();
				$("#jqModelSearch").jqmHide();
				$("#jqMode2").jqmHide();
				$("#jqModelDimission").jqmHide();

			});

	// 查询相关操作
	$("#searchDim").click(function() {
		$("#cstaffDimForm").attr(
				"action",
				basePath
						+ "ea/staffdimissioncompany/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.cstaffDimForm.submit.click();
	});
	// 查询相关操作END

	// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	var PID='';// 当点新曾时,上一级被选中项的id
	var rovince='';// 被改变的那个的id
	var districtPID='';
	function LiuZhongYaoDeShaGuaDiZhi(address) {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");

		// 非空验证End
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date1=" + new Date();
			$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
							retoken = 0;
						},
						error : function cbf(data) {
							retoken = 0;
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date01=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				retoken = 0;
				for (var i = 0; i < distinctlistSaved.length; i++) {
					if (i == 9) {
						return;
					}
					$td.children('select:eq(' + i + ')').empty();
					$td.children('select:eq(' + i + ')').append($select);
					for (var j = 0; j < list[i].length; j++) {
						$op = $("<option />");
						$op.attr("value", list[i][j].districtID).attr("id",
								list[i][j].districtID)
								.text(list[i][j].districtName);
						$td.children('select:eq(' + i + ')').append($op);
					}
					$opp = $("<option  selected='selected'/>");
					$opp.attr("value", distinctlistSaved[i].districtID).attr(
							"id", distinctlistSaved[i].districtID)
							.text(distinctlistSaved[i].districtName);
					$td.children('select:eq(' + i + ')').append($opp);
					$add = "<option class='add'  value = '"
							+ distinctlistSaved[i].districtPID
							+ "' >--新增--</option>";
					$td.children('select:eq(' + i + ')').append($add);
				}
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($select);
				for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
					$op = $("<option />");
					$op
							.attr(
									"value",
									list[distinctlistSaved.length][z].districtID)
							.attr(
									"id",
									list[distinctlistSaved.length][z].districtID)
							.text(list[distinctlistSaved.length][z].districtName);
					$td.children('select:eq(' + distinctlistSaved.length + ')')
							.append($op);
				}
				$addd = "<option class='add'  value = '"
						+ distinctlistSaved[distinctlistSaved.length - 1].districtID
						+ "' >--新增--</option>";
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($addd);
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});
	}

	$('td.JQueryaddress select[number]').change(function() {
		if (retoken)
			return;
		retoken = 1;

		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress");
		rovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		var districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ districtPID + "&date2=" + new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var distinctlist = member.distinctlist;
						$select = "<option selected='selected'>--请选择--</option>";
						$(rovince, $td).next().append($select);
						if (distinctlist.length) {
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option/>");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$(rovince, $td).next().append($op);
							}
						}
						$add = "<option class='add'  value = '" + districtPID
								+ "' >--新增--</option>";
						$(rovince, $td).next().append($add);
						$td.find('input#address').val(districtPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});

	});

	$('input#savedistrict').click(function() {
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		number = $('input#districtNames').attr('title');
		districtName = $('input#districtNames').val();
		$td.children('select:gt(' + number + ')').empty();
		if ('' == districtName) {
			alert("请填写城市名称");
			retoken = 0;
			return;
		}
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?district.districtPID="
				+ PID + "&district.districtName=" + districtName + "&date02="
				+ new Date();
		 url = basePath + "ea/cstaff/sajax_n_ea_getCDistricts.jspa?date3="
				+ new Date();
		$.ajax({
					url : encodeURI(urldistrict),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var sdistrict = member.sdistrict;
						$op1 = $("<option selected='selected'/>").attr("value",
								sdistrict.districtID).attr("id",
								sdistrict.districtID)
								.text(sdistrict.districtName);
						$("#" + sdistrict.districtID, $td).remove();
						$(rovince, $td).append($op1);
						districtPID = sdistrict.districtID;
						//var params = {
						//	"districtPID" : districtPID
						//};

						$select = "<option selected='selected'>--请选择--</option>";
						$(rovince, $td).next().append($select);
						$add = "<option class='add'  value = '" + districtPID
								+ "' >--新增--</option>";
						$(rovince, $td).next().append($add);
						$td.find('input#address').val(districtPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});

	});
		// 保存新地址...............
		// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================END!
});

$(function() {
			// 为弹出框准备下拉表内容
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getSelectLists.jspa?date4="
					+ new Date();
			var parames = null;
			$.post(url, parames, function(data) {
						var member = eval("(" + data + ")");
						var codeSexList = member.codeSexList;
						var codeNationalityList = member.codeNationalityList;
						var codeNationList = member.codeNationList;
						var codeNativePlaceList = member.codeNativePlaceList;

						$t = $("table#stafftable");

						$sex = $t.find("select#sex");// 性别拉框
						for (var i = 0; i < codeSexList.length; i++) {
							$op = $("<option/>");
							$op.val(codeSexList[i].codeValue)
									.text(codeSexList[i].codeValue);
							$sex.append($op);
						}

						$nationality = $t.find("select#nationality");// 国籍拉框
						for (var i = 0; i < codeNationalityList.length; i++) {
							$op = $("<option/>");
							$op.val(codeNationalityList[i].codeValue)
									.text(codeNationalityList[i].codeValue);
							$nationality.append($op);
						}

						$nation = $t.find("select#nation");// 民族拉框
						for (var i = 0; i < codeNationList.length; i++) {
							$op = $("<option/>");
							$op.val(codeNationList[i].codeValue)
									.text(codeNationList[i].codeValue);
							$nation.append($op);
						}

						$nativePlace = $t.find("select#nativePlace");// 籍贯拉框
						for (var i = 0; i < codeNativePlaceList.length; i++) {
							$op = $("<option/>");
							$op.val(codeNativePlaceList[i].codeValue)
									.text(codeNativePlaceList[i].codeValue);
							$nativePlace.append($op);
						}
					}, 'json');

		});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/soincumbent/ea_getStaffListForIncumbent.jspa?search="
				+ psearch + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
$(function() {
	$(".positionPay").blur(function() {
		if (isNaN($(this).attr("value"))) {
			alert("必须为数字！");
			$(this).attr("value", 0);
			return;
		}
		positionPaysum = 0;
		$(".positionPay").each(function() {
					positionPaysum = positionPaysum + $(this).attr("value") * 1;
				});
		addpositionPay(positionPaysum);
	});
	$(".achievement").blur(function() {
		if (isNaN($(this).attr("value"))) {
			alert("必须为数字！");
			$(this).attr("value", 0);
			return;
		}
		achievementsum = 0;
		$(".achievement").each(function() {
					achievementsum = achievementsum + $(this).attr("value") * 1;
				});
		addachievementsum(achievementsum);
	});
	$(":input[class != 'nocheck']", "#staffappr").blur(function() {
				if (isNaN($(this).attr("value"))) {
					alert("必须为数字！");
					$(this).attr("value", 0);
					return;
				}
				Allsum = 0;
				$(":input[class != 'nocheck']", "#staffappr").each(function() {
							Allsum = Allsum + $(this).attr("value") * 1;
						});
				addAllsum(Allsum);
			});
	$("#workDateSaturation").blur(function() {
				if (isNaN($(this).attr("value"))) {
					alert("必须为数字！");
					$(this).attr("value", 0);
					return;
				}
				workday = $("#workDateSaturation").attr("value");
				addpositionPay(positionPaysum);
				addAllsum(Allsum);
				addachievementsum(achievementsum);
				addworkday(workday);
			});

	$(".JQuerySubmitAppraisal").click(function() {
		$(":input[class != 'nocheck']", "#staffappr").trigger("blur");
		$("#workDateSaturation").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		var stPay = Math.round($("#appraisalForm #stPay").text() * 100
				* workday / 20)
				/ 100;
		var secrecyPay = Math.round($("#appraisalForm #secrecyPay").text()
				* 100 * workday / 20)
				/ 100;
		var safetyAward = Math.round($("#appraisalForm #safetyAward").text()
				* 100 * workday / 20)
				/ 100;
		var pushScore = $("#pushScore").text();
		var timingMoneyScore = $("#timingMoneyScore").text();
		var positionPayScore = $("#positionPayScore").text();
		var parmeter = stPay + secrecyPay + "-" + safetyAward + "-" + pushScore
				+ "-" + timingMoneyScore + "-" + positionPayScore;
		$("#result").attr("value", parmeter);
		$("#appraisalForm").attr("target", "main");
		$("#appraisalForm")
				.attr(
						"action",
						basePath
								+ "ea/staffappraisal/ea_saveStaffAppraisal.jspa?pageNumber="
								+ ppageNumber + "&staffappraisal.staffID="
								+ personvalue);
		document.appraisalForm.submit.click();
		alert("成功！");
		$("#jqModelAppraisal").jqmHide();
	});
		// 职务职责

});
function onUploadSuccess(data) {
	if (data == "error") {
		alert("上传失败！");
		return;
	}
	if (data != "nophoto") {
		$t = $("table#stafftable");
		$("table#stafftable").find('input#photo').attr("value", data);
	}
	$("#cstaffForm").attr("target", "hidden").attr(
			"action",
			basePath + "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
					+ ppageNumber);
	document.cstaffForm.submit.click();
	document.cstaffForm.reset();
}
function thisMovie(movieName) {
	var app = navigator.appName;
	//var verStr = navigator.appVersion;
	if (app.indexOf('Netscape') != -1) {
		return window[movieName];
	} else if (app.indexOf('Microsoft') != -1) {
		return document[movieName];
	}
}
function sunstaff() {
	workday = $("#workDateSaturation").attr("value");
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