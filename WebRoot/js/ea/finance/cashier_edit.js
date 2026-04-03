$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$(".JQueryClose").click(function() {
				re_load();
			});
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});

	$("select[id]", $("tr.xggoods")).each(function() {
				$(this).hide();
			});

	// if(history == 'history'){ //查看历史数据不可修改数据、审核、驳回
	// $(".JQuerySubmitbh").hide();
	// $(".JQuerySubmit").hide();
	// } else{
	// 双҉击҉事҉件҉修҉改
	$("tr.xggoods").dblclick(function() {
		goodsBillsID = this.id;
		$p = $("tr#" + goodsBillsID);
		if (!$p.hasClass("checkgoods")) {
			$p.addClass("checkgoods");
			$p.find(':input').each(function() {
						$(this).attr("name",
								"goodsmap[" + select + "]." + this.name);
					});
			select++;
			$p.find("td").children("span[class!=bhide]")
					.addClass("model1");
			$p.find("td").children("input").removeClass("model1");
			$p.find("select").show();
			$p.find("a").show();
			$p.find("input.endDate").val($p.find("span#endDate").text());
		}
	});
	// }
	
	
	$(".bankNumk").click(function(){
		$(this).hide();
		$("span#bankNumk").hide();
		$("input#bankNum").show();
		$(".bankNum").show();
	});

	/** ************************自动计算*********************** */
	$(".jisuan").live("keyup", function(event) {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#quantity")
						.val();
				var price = $(this).parent().parent().find(":input#price")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					var kufang = $(this).parent().parent()
							.find(":input#depotType").attr("value");
					var jie = $(this).parent().parent().find(":input#forLoan");
					var dai = $(this).parent().parent().find(":input#loan");
					var qita = $(this).parent().parent()
							.find(":input#balance");
					var fangxiang = $(this).parent().parent()
							.find(":input#direction");
					var qitajine = $(this).parent().parent()
							.find(":input#otherAmount");
					jj(kufang, fangxiang, dai, jie, qita, jine, qitajine);
					$(this).parent().parent().find(":input#money").attr(
							"value", jine);
				}
			}
		}
	});
	$(".jisuan").live("keydown", function(event) {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#quantity")
						.val();
				var price = $(this).parent().parent().find(":input#price")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					var kufang = $(this).parent().parent()
							.find(":input#depotType").attr("value");
					var jie = $(this).parent().parent().find(":input#forLoan");
					var dai = $(this).parent().parent().find(":input#loan");
					var qita = $(this).parent().parent()
							.find(":input#balance");
					var fangxiang = $(this).parent().parent()
							.find(":input#direction");	
					var qitajine = $(this).parent().parent()
							.find(":input#otherAmount");
					jj(kufang, fangxiang, dai, jie, qita, jine, qitajine);
					$(this).parent().parent().find(":input#money").attr(
							"value", jine);

				}
			}
		}
	});

	function jj(kufang, fangxiang, dai, jie, qita, jine, qitajine) {
		if(kufang=='出库'){
			$(jie).attr("value",jine);
			$(qita).attr("value",jine);
			$(fangxiang).attr("value","贷");
			$(dai).val("");
			$(qitajine).val("");
		}else if(kufang=='入库'){
			$(dai).attr("value",jine);
			$(qita).attr("value",jine);
			$(fangxiang).attr("value","借");
			$(jie).val("");
			$(qitajine).val("");
		}else if(kufang=='库存'){
			$(fangxiang).attr("value","库存");
			$(qita).attr("value",jine);
			$(jie).val("");
			$(dai).val("");
			$(qitajine).val("");
		}else if(kufang=='其它'){
			$(qitajine).attr("value",jine);
			$(fangxiang).attr("value","其它");
			$(qita).val("");
			$(jie).val("");
			$(dai).val("");
		}
	}
	$("select#depotType").change(function () {
			var kufang=$(this).val();
			var jie=$(this).parent().parent().find(":input#forLoan");
			var dai=$(this).parent().parent().find(":input#loan");
			var qita=$(this).parent().parent().find(":input#balance");
			var fangxiang=$(this).parent().parent().find(":input#direction");
			var qitajine=$(this).parent().parent().find(":input#otherAmount");
			if($(jie).val()!=''){
				jj(kufang,fangxiang,dai,jie,qita,$(jie).val(),qitajine);
			}else if($(dai).val()!=''){
				jj(kufang,fangxiang,dai,jie,qita,$(dai).val(),qitajine);
			}else if($(qita).val()!=''){
				jj(kufang,fangxiang,dai,jie,qita,$(qita).val(),qitajine);
			}else if($(qitajine).val()!=''){
				jj(kufang,fangxiang,dai,jie,qita,$(qitajine).val(),qitajine);
			}else{
				jj(kufang,fangxiang,"","","","","");
			}
	});
	/** **************************************科目管理******************************************* */
	var subjectsNumber = "";
	var subjectsName = "";
	$(".tosubjects").click(function() {
		$(this).parent().parent().find("td").addClass("receivesubjects");
		$td = $("td.subjects");
		$td.children('select').empty();
		var subRuleurl = basePath
				+ "/ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="
				+ new Date().toLocaleString();
		var subRule = new Array();
		var endnumber = 0;
		$.ajax({
			url : encodeURI(subRuleurl),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				subRule = (member.subRule.rules).split(",");
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		var subjecturl = basePath
				+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		subjectsNumber = "";
		subjectsName = "";
		$.ajax({
			url : encodeURI(subjecturl + "002&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjectsList = member.subjectsList;
				$td = $("td.subjects");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < subjectsList.length; i++) {
					$op = $("<option />");
					$op.attr("value", subjectsList[i].subjectsNumbers).attr(
							"id", subjectsList[i].subjectsID)
							.text(subjectsList[i].subjectsName);
					$td.children('select:eq(0)').append($op);
				}
				$td.children('select:eq(0)').show();
				endnumber += parseInt(subRule[0]);
				if (subjectsNumber.substring(0, subRule[0]) != "") {
					$td.children('select:eq(0)').attr("value",
							subjectsNumber.substring(0, subRule[0]));
					$td.children('select:eq(0)').trigger("change");
				}
				$("#selectsubjects").jqmShow();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

	});
	$("#savesubjects").click(function() {
		if (subjectsName != "--请选择--") {
			$("#subjectsID", $(".receivesubjects")).attr("value",
					subjectsnumber);
			$("#subjectsName", $(".receivesubjects")).attr("value",
					subjectsName);
		} else {
			$("#subjectsID", $(".receivesubjects")).attr("value", "");
			$("#subjectsName", $(".receivesubjects")).attr("value", "");
		}
		$("#cashierTallyForm").find("td.receivesubjects")
				.removeClass("receivesubjects");
		notoken = 0;
		$("#selectsubjects").jqmHide();
	});

	// 科目管理
	$('td.subjects select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.subjects");
		$td.children('select:gt(' + num + ')').empty();
		var subjectsPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		subjectsnumber = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		subjectsName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		if (subjectsnumber == "") {
			var numbers = parseInt(num) - 1;
			subjectsnumber = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			subjectsName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var subjecturl = basePath
				+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		var subjectsNumber = "";
		$.ajax({
			url : encodeURI(subjecturl + subjectsPID + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjectsList = member.subjectsList;
				notoken = 0;
				$td = $("td.subjects");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < subjectsList.length; i++) {
					$op = $("<option />");
					$op.attr("value", subjectsList[i].subjectsNumbers).attr(
							"id", subjectsList[i].subjectsID)
							.text(subjectsList[i].subjectsName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$td.children('select:eq(' + number + ')').show();
				if (subjectsNumber.length == endnumber
						|| subjectsNumber.length == 0) {
					return;
				}
				endnumber += parseInt(subRule[number]);
				var startnumber = subRule[number - 1];
				if (subjectsNumber.substring(startnumber, endnumber) != "") {
					$td.children('select:eq(' + number + ')').attr("value",
							subjectsNumber.substring(0, endnumber));
					$td.children('select:eq(' + number + ')').trigger("change");
				}

			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	var subRuleurl = basePath
			+ "ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="
			+ new Date().toLocaleString();
	//var subRule = new Array();
	//var endnumber = 0;
	$.ajax({
				url : encodeURI(subRuleurl),
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
					subRule = (member.subRule.rules).split(",");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("#submitResult2").click(function(){
		if ($("#SendForm2 #comments").val() == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		 }
		$form = $("#cashierTallyForm");
		if (confirm("确定执行该操作？")) {
			$form
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/responsible/ea_updateCashier.jspa?search="
									+ search + "&pageNumber=" + pNumber);
			$form.find("input#comments").val($("#SendForm2 #comments").val());
			document.cashierTallyForm.submit.click();
			token = 2;
		}
	});

	// 审核通过
	$("input.JQuerySubmit").click(function() {
		$("#cashierTallyForm").find("input#cashierstatus").val("07");
		$("#jqModelSend2").jqmShow();
		document.SendForm2.reset();
	});
	
	// 驳回
	$("input.JQuerySubmitbh").click(function() {
		$("#cashierTallyForm").find("input#cashierstatus").val("10");
		$("#jqModelSend2").jqmShow();
		document.SendForm2.reset();
	});

});
function re_load() {
	document.location.href = basePath
			+ "ea/cashier/ea_getCashierList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&history=" + history + "&sdate=" + sdate + "&edate=" + edate;
}

function toCCode(codePID, selectID, formID) {
	$(".jqmWindow").jqmHide();
	$("#codePID").attr("value", codePID);
	$("#selectID").attr("value", selectID);
	$("#formID").attr("value", formID);
	$("#ccodevalue").attr("value", "");
	$("#newccode").jqmShow();
}
function saveCCode() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
			+ codePID + "&code.codeValue=" + codeValue + "&date="
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
					var code = member.code;
					$("#newccode").jqmHide();
					$op = $("<option/>");
					$op.attr("value", code.codeValue).text(code.codeValue);
					$(selectID, formID).append($op);
					alert("操作成功！");
					$(".jqmWindow", formID).jqmShow();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}
$(document).ready(function() {
			$("input.JQueryreturn1").click(function() {// 取消
						var formID = $($("#formID").attr("value"));
						$("#newccode").jqmHide();
						$(".jqmWindow", formID).jqmShow();
					});
		});