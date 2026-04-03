$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});
	$(".JQueryClose").click(function() {
				notoken = 0;
				re_load();
			});
	if(types=='20'){
	$("#shuju").hide();
	$("#xiangmu").show();
	}
	$("#staffID").hide();
	$("#departmentID").hide();
	$("select#billsType").hide();
	if (cashierBillsID == "") {
		$("input.JQueryprint").hide();
		$("input.JQuerySubmit").show();
		$("input.JQuerySubmitgd").show();
		$("#cashierstatus").val("04");
		var staff = $("select#staffID").attr("name", "cashierBills.staffID")
				.show();
		var dept = $("select#departmentID").attr("name",
				"cashierBills.departmentID").show();
		$("td#dept").html(dept);
		$("td#staff").html(staff);
	} else {
		$("select#billsType").hide();
		$("input.JQueryunitret").hide();
		$("input.JQuerypersonret").hide();
		$("input.JQuerySubmitgd").show();
		$("input.JQueryprint").show();

		if ($("#cashierstatus").val() != ''
				&& $("#cashierstatus").val() == '04') { // 当单据状态不为空或是草稿状态
			$("input.JQuerySubmit").show(); // 添加页面草稿保存显示
		}
		if ($("#cashierstatus").val() == '04') { // 当单据状态为草稿或是待主管审核
			$("input.JQueryInvalid").show(); // 添加页面作废操作显示
		}
		/*---------------              显示审核公章               ---------------*/
		var st = $("#cashierstatus").val();
		if (st != "04") {
			if (st == "10" || st == "30") {
				var str = "<img src='" + basePath
						+ "images/ea/finance/zuofei.png'/>";
				$("#apDiv1").html(str);
			} else if (st == "07") {
				var str = "<img src='" + basePath
						+ "images/ea/finance/yishen.png'/>";
				$("#apDiv1").html(str);
			} else {
				var str = "<img src='" + basePath
						+ "images/ea/finance/daishen.png'/>";
				$("#apDiv1").html(str);
			}

		}

		// 查看时物品表里有ID的下拉框隐藏
		$("select[id]", $("tr.xggoods")).each(function() {
					$(this).hide();
				});
		$(".classhide").show();
	}
	$(".update").click(function() {
		var selectID = $(this).attr("title");
		var departmentIDselect = $(this).next("select").attr("name",
				"cashierBills." + selectID);
		$(this).parent().html(departmentIDselect);
		$("#" + selectID).show();
	});
	$("input.JQueryInvalid").click(function() {
		if (confirm("是否继续操作？")) {
			var url = basePath
					+ "/ea/cashierbillsclassify/sajax_ea_updateCashierBillsInvalid.jspa?date="
					+ new Date().toLocaleString()
					+ "&cashierBills.cashierBillsID=" + cashierBillsID
					+ "&BType=" + types + "&level=" + level;
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							alert("操作成功！");
							document.location.reload();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
		}
	});
	$("input.JQueryunitret").click(function() {// 重置往来单位
				$t = $("table#table4");
				$t.find(".qk").each(function() {
							$(this).text("");
						});
				$t.find("select").each(function() {
							$(this).empty();
							$(this).attr("style", "display:none");
						});
				$t.find("input").each(function() {
							$(this).attr("value", "");
						});
			});
	$("input.JQuerypersonret").click(function() {// 重置往来个人
				$t = $("table#table5");
				$t.find(".qk").each(function() {
							$(this).text("");
						});
				$t.find("select").each(function() {
							$(this).empty();
							$(this).attr("style", "display:none");
						});
				$t.find("input").each(function() {
							$(this).attr("value", "");
						});
			});

	// 双҉击҉事҉件҉修҉改
	$("tr.xggoods").dblclick(function() {
		var status = $("#cashierstatus").val();
		if (status != "04") {
			alert("已归档不能修改该条数据！");
			return;
		}
		goodsBillsID = this.id;
		$p = $("tr#" + goodsBillsID);
		if (!$p.hasClass("checkgoods")) {
			$p.addClass("checkgoods");
			$p.find(':input').each(function() {
						$(this).attr("name",
								"goodsmap[" + select + "]." + this.name);
					});
			select++;
			$p.find("td").children("span[class!=bhide]").addClass("model1");
			$p.find("td").children("input").removeClass("model1");
			$p.find("select").show();
			$p.find("a").show();
		}
	});
	$("input.JQueryprint").click(function() {// 打印预览
				var cashierBillsID = $("input#cashierID",
						$("#cashierTallyForm")).val();
				window
						.open(basePath
								+ "ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
			});
	// 更改部门事件 清空银行帐号
	$("select#departmentID", "table#table3").change(function() {
				$("input#bankNum", "table#table3").attr("value", "");
			});
	// 计算金额
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
							.find(":input#username5");
					var fangxiang = $(this).parent().parent()
							.find("span#direction");
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
							.find(":input#username5");
					var fangxiang = $(this).parent().parent()
							.find("span#direction");
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
		if (kufang == '出库') {
			$(jie).attr("value", jine);
			$(qita).attr("value", jine);
			$(fangxiang).attr("value", "贷");
			$(dai).val("");
			$(qitajine).val("");
		} else if (kufang == '入库') {
			$(dai).attr("value", jine);
			$(qita).attr("value", jine);
			$(fangxiang).attr("value", "借");
			$(jie).val("");
			$(qitajine).val("");
		} else if (kufang == '库存') {
			$(fangxiang).attr("value", "库存");
			$(qita).attr("value", jine);
			$(jie).val("");
			$(dai).val("");
			$(qitajine).val("");
		} else if (kufang == '其它') {
			$(qitajine).attr("value", jine);
			$(fangxiang).attr("value", "其它");
			$(qita).val("");
			$(jie).val("");
			$(dai).val("");
		}
	}
	$("select#depotType").change(function() {
				var kufang = $(this).val();
				var jie = $(this).parent().parent().find(":input#forLoan");
				var dai = $(this).parent().parent().find(":input#loan");
				var qita = $(this).parent().parent().find(":input#username5");
				var fangxiang = $(this).parent().parent()
						.find("input#direction");
				var qitajine = $(this).parent().parent()
						.find(":input#otherAmount");
				if ($(jie).val() != '') {
					jj(kufang, fangxiang, dai, jie, qita, $(jie).val(),
							qitajine);
				} else if ($(dai).val() != '') {
					jj(kufang, fangxiang, dai, jie, qita, $(dai).val(),
							qitajine);
				} else if ($(qita).val() != '') {
					jj(kufang, fangxiang, dai, jie, qita, $(qita).val(),
							qitajine);
				} else if ($(qitajine).val() != '') {
					jj(kufang, fangxiang, dai, jie, qita, $(qitajine).val(),
							qitajine);
				} else {
					jj(kufang, fangxiang, "", "", "", "", "");
				}
			});

	// 修҉改҉
	$(".ajaxxg").click(function() {
		var status = $("#cashierstatus").val();
		if (status != "00") {
			alert("已归档不能修改该条数据！");
			return;
		}
		goodsBillsID = $(this).parent().find("input#goodsBillsID").val();
		$p = $("tr#" + goodsBillsID);
		$p = $("tr#" + goodsBillsID);
		if (!$p.hasClass("checkgoods")) {
			$p.addClass("checkgoods");
			$p.find(':input').each(function() {
						$(this).attr("name",
								"goodsmap[" + select + "]." + this.name);
					});
			select++;

			$p.find("td").children("span[class!=bhide]").addClass("model1");
			$p.find("td").children("input").removeClass("model1");
			$p.find("select").show();
			$p.find("a").show();
		}
	});
	// 迭代的商品删除
	$(".ajaxsc").click(function() {
		var status = $("#cashierstatus").val();
		if (status != "04") {
			alert("已归档不能删除该条数据！");
			return;
		}
		if (confirm("是否删除？")) {
			var goodsBillsID = $(this).parent().find("input#goodsBillsID")
					.val();
			var delurl = basePath
					+ "/ea/cashierbillsclassify/sajax_ea_delGoodsBills.jspa?typeID="
					+ goodsBillsID + "&date=" + new Date().toLocaleString()
					+ "&BType=" + types;
			$.ajax({
						url : encodeURI(delurl),
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
							var typeID = member.typeID;
							alert("删除成功！");
							$("tr#" + typeID).remove();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
		}
	});
	// 克隆的商品删除
	$(".klsc").click(function() {
				$(this).parent().parent().remove();
			});
	// 提交保存
	$("input.JQuerySubmit").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		var num1=$("#departmentID").find("option:selected").text().indexOf("├");
		var leng1=$("#departmentID").find("option:selected").text().length;
		$("input#departmentName").attr("value",$("#departmentID").find("option:selected").text().substr(num1+1,leng1));
		var status = $("#cashierstatus").val();
		if (status != "04") {
			alert("已归档不能修改！");
			return;
		}
		$(".put3", $("tr.checkgoods")).trigger("blur");
		$(".notnull", $("tr.checkgoods")).trigger("blur");
		$(".isNaN").trigger("blur");
		$(".jisuan").trigger("blur");
		if ($("#cashierTallyForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		notoken = 1;
		$("#cashierstatus").attr("value", "04");
		if (cashierBillsID == "") {
			$("#cashierTallyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/cashierbillsclassify/ea_saveCashierBills.jspa?pageNumber="
									+ pNumber + "&BType=" + types + "&level="
									+ level+"&typeType="+jnumber);
			document.cashierTallyForm.submit.click();
			document.cashierTallyForm.reset();
			$("select#departmentID").get(0).selectedIndex=0;
			$(".qk").text("");
			$("select#contactConnections", $("table#table4")).hide();
			$("select#aNum", $("table#table4")).hide();
			$("select#phone", $("table#table5")).hide();
			$("select#userNum", $("table#table5")).hide();
			$("#cashierstatus").attr("value", "04");
			$("select#departmentID").get(0).selectedIndex=0;
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#cashierTallyForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/cashierbillsclassify/ea_saveCashierBills.jspa?pageNumber="
								+ pNumber + "&BType=" + types + "&level="
								+ level);
		document.cashierTallyForm.submit.click();
		token = 2;
	});

	// 归档
	$("input.JQuerySubmitgd").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		var num1=$("#departmentID").find("option:selected").text().indexOf("├");
		var leng1=$("#departmentID").find("option:selected").text().length;
		$("input#departmentName").attr("value",$("#departmentID").find("option:selected").text().substr(num1+1,leng1));
		var status = $("#cashierstatus").val();
		if (status != "04") {
			alert("已归档不能再次提交！");
			return;
		}
		
		$(".put3", $("tr.checkgoods")).trigger("blur");
		$(".notnull", $("tr.checkgoods")).trigger("blur");
		$(".isNaN").trigger("blur");
		$(".jisuan").trigger("blur");
		if ($("#cashierTallyForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		notoken = 1;
		$("#cashierstatus").attr("value", "04");
		if (cashierBillsID == "") {
			$("#cashierTallyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/cashierbillsclassify/ea_saveCashierBills.jspa?pageNumber="
									+ pNumber + "&BType=" + types + "&level="
									+ level+"&typeType="+jnumber);
			document.cashierTallyForm.submit.click();
			document.cashierTallyForm.reset();
			$("select#departmentID").get(0).selectedIndex=0;
			$(".qk").text("");
			$("select#contactConnections", $("table#table4")).hide();
			$("select#aNum", $("table#table4")).hide();
			$("select#phone", $("table#table5")).hide();
			$("select#userNum", $("table#table5")).hide();
			$("#cashierstatus").attr("value", "04");
			$("select#departmentID").get(0).selectedIndex=0;
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#cashierTallyForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/cashierbillsclassify/ea_saveCashierBills.jspa?pageNumber="
								+ pNumber + "&BType=" + types + "&level="
								+ level);
		document.cashierTallyForm.submit.click();
		token = 2;
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
	// var subRule = new Array();
	// var endnumber = 0;
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

	/** ************************************库房管理********************************** */
	// //////////////库房管理////////////////////
	$(".todepotID").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$td = $("td.depot");
		$td.children('select').empty();
		$td.children('select:gt(0)').hide();
		var depoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		depotID = "";
		depotName = "";
		$.ajax({
			url : encodeURI(depoturl + "001&date="
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
				var depotManagelist = member.depotManagelist;
				$td = $("td.depot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(0)').append($op);
				}
				notoken = 0;
				$add = "<option class='add'  value ='001' >--新增--</option>";
				$td.children('select:eq(0)').append($add);
				$td.children('select:eq(0)').show();
				$("#selectdepot").jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});

	});
	$("#savedepot").click(function() {
				if (depotName != "--请选择--" && depotName != "--新增--") {
					$("#discountMoney").attr("value", depotID);
					$("#afterDiscount").attr("value", depotName);
				} else {
					$("#discountMoney").attr("value", "");
					$("#afterDiscount").attr("value", "");
				}
				notoken = 0;
				$("#selectdepot").jqmHide();
			});

	// 库房管理
	$('td.depot select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.depot");
		$td.children('select:gt(' + num + ')').empty();
		depotPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		depotID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		depotName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			depotPID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectdepot").jqmHide();
			$("input#depotPID", $("#cstaffForm")).val(depotPID);
			$("input#treenum", $("#cstaffForm")).val(num);
			$("input#depotPID", $("#cstaffForm")).attr("title", "selectdepot");
			$("#jqModelkf").jqmShow();
			return;
		}
		if (depotID == "") {
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var depoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		$.ajax({
			url : encodeURI(depoturl + depotID + "&date="
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
				var depotManagelist = member.depotManagelist;
				$td = $("td.depot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + depotID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	/** ************************************************************** */
	$(".JQuerySubmitkf").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$(".put3", $("#cstaffForm")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		var formData = $("#cstaffForm").serialize();
		var depotsaveurl = basePath
				+ "ea/depotmanage/sajax_ea_saveDepotByAjax.jspa?" + formData
				+ "&date=" + new Date().toLocaleString();
		$.ajax({
			url : depotsaveurl,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var depotManage = member.depotManage;
				var divid = $("input#depotPID", $("#cstaffForm")).attr("title");
				$op1 = $("<option selected='selected'/>").attr("value",
						depotManage.depotID).attr("id", depotManage.depotID)
						.text(depotManage.depotName);
				var treenum = $("input#treenum", $("#cstaffForm")).val();
				var num = parseInt(treenum);
				$("select:eq(" + num + ")", $("#" + divid)).append($op1);
				$select = "<option selected='selected'>--请选择--</option>";
				var number = num + 1;
				$("select:eq(" + number + ")", $("#" + divid)).append($select);
				$add = "<option class='add'  value = '" + depotManage.depotID
						+ "' >--新增--</option>";
				$("select:eq(" + number + ")", $("#" + divid)).append($add);
				$("select:eq(" + number + ")", $("#" + divid)).show();
				depotID = depotManage.depotID;
				depotName = depotManage.depotName;
				notoken = 0;
				alert("添加成功！");
				document.cstaffForm.reset();
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});

	$(".JQueryreturnkf").click(function() {
				notoken = 0;
				document.cstaffForm.reset();
				var divid = $("input#depotPID", $("#cstaffForm")).attr("title");
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			});

	/** ***************************************************************** */
	/** ************************************资料库房管理********************************** */
	// //////////////库房管理////////////////////
	$(".todatadepotID").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$td = $("td.datadepot");
		$td.children('select').empty();
		$td.children('select:gt(0)').hide();
		var datadepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		depotID = "";
		depotName = "";
		$.ajax({
			url : encodeURI(datadepoturl + "002&date="
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
				var depotManagelist = member.depotManagelist;
				$td = $("td.datadepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(0)').append($op);
				}
				$add = "<option class='add'  value = '002' >--新增--</option>";
				$td.children('select:eq(0)').append($add);
				$td.children('select:eq(0)').show();
				notoken = 0;
				$("#selectdatadepot").jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});

	});
	$("#savedatadepot").click(function() {
				if (depotName != "--请选择--" && depotName != "--新增--") {
					$("#dataDepotID").attr("value", depotID);
					$("#dataDepotName").attr("value", depotName);
				} else {
					$("#dataDepotID").attr("value", "");
					$("#dataDepotName").attr("value", "");
				}
				notoken = 0;
				$("#selectdatadepot").jqmHide();
			});

	// 库房管理
	$('td.datadepot select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.datadepot");
		$td.children('select:gt(' + num + ')').empty();
		depotPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		depotID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		depotName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			depotPID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectdatadepot").jqmHide();
			$("input#depotPID", $("#cstaffForm")).val(depotPID);
			$("input#treenum", $("#cstaffForm")).val(num);
			$("input#depotPID", $("#cstaffForm")).attr("title",
					"selectdatadepot");
			$("#jqModelkf").jqmShow();
			return;
		}
		if (depotID == "") {
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var datadepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		$.ajax({
			url : encodeURI(datadepoturl + depotID + "&date="
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
				var depotManagelist = member.depotManagelist;
				$td = $("td.datadepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + depotID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	/** ************************************银行库房管理********************************** */
	// //////////////银行库房管理////////////////////
	$(".tobankdepotID").click(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$td = $("td.banksdepot");
		$td.children('select').empty();
		$td.children('select:gt(0)').hide();
		var bankdepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		depotID = "";
		depotName = "";
		$.ajax({
			url : encodeURI(bankdepoturl + "003&date="
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
				var depotManagelist = member.depotManagelist;
				$td = $("td.banksdepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(0)').append($op);
				}
				notoken = 0;
				$add = "<option class='add'  value ='003' >--新增--</option>";
				$td.children('select:eq(0)').append($add);
				$td.children('select:eq(0)').show();
				$("#selectbankdepot").jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});

	});
	$("#savebankdepot").click(function() {
				if (depotName != "--请选择--" && depotName != "--新增--") {
					$("#bankDepotID").attr("value", depotID);
					$("#bankDepotName").attr("value", depotName);
				} else {
					$("#bankDepotID").attr("value", "");
					$("#bankDepotName").attr("value", "");
				}
				notoken = 0;
				$("#selectbankdepot").jqmHide();
			});

	// 库房管理
	$('td.banksdepot select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.banksdepot");
		$td.children('select:gt(' + num + ')').empty();
		depotPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		depotID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		depotName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			depotPID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectbankdepot").jqmHide();
			$("input#depotPID", $("#cstaffForm")).val(depotPID);
			$("input#treenum", $("#cstaffForm")).val(num);
			$("input#depotPID", $("#cstaffForm")).attr("title",
					"selectbankdepot");
			$("#jqModelkf").jqmShow();
			return;
		}
		if (depotID == "") {
			var numbers = parseInt(num) - 1;
			depotID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			depotName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var bankdepoturl = basePath
				+ "ea/depotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=";
		$.ajax({
			url : encodeURI(bankdepoturl + depotID + "&date="
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
				var depotManagelist = member.depotManagelist;
				$td = $("td.banksdepot");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < depotManagelist.length; i++) {
					$op = $("<option />");
					$op.attr("value", depotManagelist[i].depotID).attr("id",
							depotManagelist[i].depotID)
							.text(depotManagelist[i].depotName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + depotID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	/** *******************************取得部门下拉************************************ */
		var treeName =treeNames;
			var treePID = treeID;
			var treePName = treeNames;
	$("span#companyNames").text(gongsiname);
	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
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
					/**
					 * function TreeSelector(item, data, rootId){ this._data =
					 * data; this._item = item; this._rootId = rootId; }
					 * 
					 * TreeSelector.prototype.createTree = function(){ var len =
					 * this._data.length; for (var i = 0; i < len; i++) { if
					 * (this._data[i].pid == this._rootId) {
					 * this._item.options.add(new Option("" +
					 * this._data[i].text, this._data[i].id)); for (var j = 0; j <
					 * len; j++) { this.createSubOption(len, this._data[i],
					 * this._data[j]); } } } }
					 * TreeSelector.prototype.createSubOption = function(len,
					 * current, next){ var blank = ' '; if (next.pid ==
					 * current.id) { intLevel = 0; var intlvl =
					 * this.getLevel(this._data, this._rootId, current); for(var
					 * a = 0; a < intlvl; a++ ) blank += " "; blank += "├";
					 * this._item.options.add(new Option(blank + next.text,
					 * next.id)); for (var j = 0; j < len; j++) {
					 * this.createSubOption(len, next, this._data[j]); } } }
					 * TreeSelector.prototype.getLevel = function(datasources,
					 * topId, currentitem){ var pid = currentitem.pid; if (pid !=
					 * topId) { for (var i = 0; i < datasources.length; i++) {
					 * if (datasources[i].id == pid) { intLevel++;
					 * this.getLevel(datasources, topId, datasources[i]); } } }
					 * return intLevel; }
					 */
					var ts = new TreeSelector($("select#departmentID")[0],
							data, -1);
					ts.createTree();
					if (deptID != "") {
						$("table#table3").find("select#departmentID").attr(
								"value", deptID);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		// //////////////////////////////////////////////////
});
function re_load() {
	var url = basePath
			+ "/ea/cashierbillsclassify/ea_getCashierBillsList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber=" + pageNumber + "&search="
			+ search + "&BType=" + types + "&level=" + level;
	document.location.href = encodeURI(url);
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

/************************************选择项目**************************************** */
$(document).ready(function() {
	$("table#gotable tr[id]").live("click", function(event) {
		var b = $("input.rauser", $(this)).attr("checked");
		$("input.rauser", $(this)).attr("checked", !b);
	});
	// 复选框选中物品
	$(".rauser").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	// 导入数据（选择物品）
	$("#xiangmu").click(function() {
		$(".jqmWindow", $("#billForm")).jqmShow();
	});
	//关闭
	$(".JQueryreturnbill").click(function() {
				notoken = 0;
				var numes="";
				$("#journalNum", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#billForm")).jqmHide();
			});
	// 根据编号查询
	$("#chaxun").click(function() {
				var typeType=$("#journalNum", $("table#searchgood")).val();
				jnumber=typeType;
				$(":input#parms").val("&typeType="+typeType);
				cx("&typeType="+typeType);
			});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx(typeCN);
				} else {
					alert("已是尾页！");
				}
			})	;
	//确定复制
	$("#qdbill").click(function() {
		$("input[name='check']").attr("checked",true);
			if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					select++;
					$("#kelong").before(
							$("#kelong").clone(true).attr("id","kelong" + select).addClass("checkgoods")
					);
					$("input#goodsNomber", $("#kelong" + select)).attr("value",select - 1);
					$("#kelong" + select).find(':input').each(function() {
						$(this).attr("name","goodsmap[" + select + "]." + this.name);
					});
					$op = $("<option value='' selected>请选择</option>");
					$("select#goodsVariableID", $("#kelong" + select)).append($op);
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "goodsID") {
							$("input#goodsID", $("#kelong" + select)).attr("value", $(this).text());
						} else if (this.id == "subjectsName") {
							$("input#subjectsName", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "subjectsID") {
							$("input#subjectsID", $("#kelong" + select)).attr("value", $(this).text());
						} else if (this.id == "batchNumber") {
							$("input#batchNumber", $("#kelong" + select)).attr("value", $(this).text());
						} else if (this.id == "reasonThing") {
							$("input#reasonThing", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "quantity") {
							$("input#quantity", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "weight") {
							$("input#weight", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "boxStandard") {
							$("input#boxStandard", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "unitPrice") {
							$("input#price", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "money") {
							$("input#money", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "loan") {
							$("input#loan", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "forLoan") {
							$("input#forLoan", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "direction") {
							$("input#direction", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "balance") {
							$("input#username5", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "costType") {
							$("select#costType", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "priceManage") {
							$("select#priceManage", $("#kelong" + select)).attr("value", $(this).text());
						}else if (this.id == "depotType") {
							$("select#depotType", $("#kelong" + select)).attr("value", $(this).text());
						}else {
							$("span#" + this.id, $("#kelong" + select)).text($(this).text());
						}
						if (this.title == "ava" && $(this).text().length != 0) {
							$op = $("<option />");
							$op.attr("value", $(this).text()).text($(this).text());
							$("select#goodsVariableID", $("#kelong" + select)).append($op);
						}
					});
					$("tr#kelong" + select).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#billForm")).jqmHide();
		} else {
			alert("请选择项目！");
		}

	});
	function cx(typeCN){
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
		var searchurl = basePath+ "ea/cashierbillsclassify/sajax_ea_getbill.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy").attr("title", dqy + 1);
				}
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择票据</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr ><th align='center' bgcolor='#E4F1FA'>批号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>品名编号</th><th align='center' bgcolor='#E4F1FA'>科目</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>摘要</th><th align='center' bgcolor='#E4F1FA'>品名名称</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>费用类别</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>型号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌规格</th><th align='center' bgcolor='#E4F1FA'>单位</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>数量</th><th align='center' bgcolor='#E4F1FA'>重量</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>箱规格</th><th align='center' bgcolor='#E4F1FA'>单价管理</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>单价</th><th align='center' bgcolor='#E4F1FA'>金额</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>库房管理</th><th align='center' bgcolor='#E4F1FA'>借方金额</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>贷方金额</th><th align='center' bgcolor='#E4F1FA'>方向</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>余额</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i][0] + ">";
					tabletr += "<td id='check' align='center' style='display:none'><input type ='checkbox' class='rauser' value="
							+ pageForm.list[i][0] + " name='check'/></td>";
					if(pageForm.list[i][24]==null){
						pageForm.list[i][24]="";
					}else{
						pageForm.list[i][24];
					}
					tabletr += "<td id='batchNumber' align='center'>"
							+ pageForm.list[i][24]+ "</td>";
					if(pageForm.list[i][1]==null){
						pageForm.list[i][1]="";
					}else{
						pageForm.list[i][1];
					}
					tabletr += "<td id='goodsCoding' align='center'>"
							+ pageForm.list[i][1]+ "</td>";
					tabletr += "<td id='subjectsID'  align='center'style='display:none'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='subjectsName'  align='center'>"
							+ pageForm.list[i][3] + "</td>";
					if(pageForm.list[i][4]==null){
						pageForm.list[i][4]="";
					}else{
						pageForm.list[i][4];
					}
					tabletr += "<td id='reasonThing' align='center'>"
							+ pageForm.list[i][4] + "</td>";	
					if(pageForm.list[i][5]==null){
						pageForm.list[i][5]="";
					}else{
						pageForm.list[i][5];
					}
					tabletr += "<td id='goodsName' align='center'>"
							+ pageForm.list[i][5]+ "</td>";
					if(pageForm.list[i][6]==null){
						pageForm.list[i][6]="";
					}else{
						pageForm.list[i][6];
					}
					tabletr += "<td id='costType'  align='center'>"
							+ pageForm.list[i][6] + "</td>";	
					if(pageForm.list[i][7]==null){
						pageForm.list[i][7]="";
					}else{
						pageForm.list[i][7];
					}
					tabletr += "<td id='typeID' align='center'>"
							+ pageForm.list[i][7] + "</td>";
					if(pageForm.list[i][8]==null){
						pageForm.list[i][8]="";
					}else{
						pageForm.list[i][8];
					}
					tabletr += "<td id='mnemonicCode' align='center'>"
							+ pageForm.list[i][8] + "</td>";	
					if(pageForm.list[i][9]==null){
						pageForm.list[i][9]="";
					}else{
						pageForm.list[i][9];
					}
					tabletr += "<td id='model' align='center'>"
							+ pageForm.list[i][9] + "</td>";
					if(pageForm.list[i][10]==null){
						pageForm.list[i][10]="";
					}else{
						pageForm.list[i][10];
					}
					tabletr += "<td id='standard' align='center'>"
							+ pageForm.list[i][10] + "</td>";	
					if(pageForm.list[i][11]==null){
						pageForm.list[i][11]="";
					}else{
						pageForm.list[i][11];
					}
					tabletr += "<td id='unit' align='center'>"
							+ pageForm.list[i][11] + "</td>";	
					if(pageForm.list[i][12]==null){
						pageForm.list[i][12]="";
					}else{
						pageForm.list[i][12];
					}
					tabletr += "<td id='quantity' align='center'>"
							+ pageForm.list[i][12] + "</td>";	
					if(pageForm.list[i][13]==null){
						pageForm.list[i][13]="";
					}else{
						pageForm.list[i][13];
					}
					tabletr += "<td id='weight' align='center'>"
							+ pageForm.list[i][13] + "</td>";
					if(pageForm.list[i][14]==null){
						pageForm.list[i][14]="";
					}else{
						pageForm.list[i][14];
					}
					tabletr += "<td id='boxStandard' align='center'>"
							+ pageForm.list[i][14] + "</td>";
					if(pageForm.list[i][15]==null){
						pageForm.list[i][15]="";
					}else{
						pageForm.list[i][15];
					}
					tabletr += "<td id='priceManage' align='center'>"
							+ pageForm.list[i][15] + "</td>";	
					if(pageForm.list[i][16]==null){
						pageForm.list[i][16]="";
					}else{
						pageForm.list[i][16];
					}
					tabletr += "<td id='unitPrice' align='center'>"
							+ pageForm.list[i][16] + "</td>";	
					if(pageForm.list[i][17]==null){
						pageForm.list[i][17]="";
					}else{
						pageForm.list[i][17];
					}
					tabletr += "<td id='amount' align='center'>"
							+ pageForm.list[i][17] + "</td>";
					if(pageForm.list[i][18]==null){
						pageForm.list[i][18]="";
					}else{
						pageForm.list[i][18];
					}
					tabletr += "<td id='depotType' align='center'>"
							+ pageForm.list[i][18] + "</td>";	
					if(pageForm.list[i][19]==null){
						pageForm.list[i][19]="";
					}else{
						pageForm.list[i][19];
					}
					tabletr += "<td id='loan' align='center'>"
							+ pageForm.list[i][19] + "</td>";
					if(pageForm.list[i][20]==null){
						pageForm.list[i][20]="";
					}else{
						pageForm.list[i][20];
					}
					tabletr += "<td id='forLoan' align='center'>"
							+ pageForm.list[i][20] + "</td>";	
					if(pageForm.list[i][21]==null){
						pageForm.list[i][21]="";
					}else{
						pageForm.list[i][21];
					}
					tabletr += "<td id='direction' align='center'>"
							+ pageForm.list[i][21] + "</td>";
					if(pageForm.list[i][22]==null){
						pageForm.list[i][22]="";
					}else{
						pageForm.list[i][22];
					}
					tabletr += "<td id='balance' align='center'>"
							+ pageForm.list[i][22] + "</td>";	
					if(pageForm.list[i][23]==null){
						pageForm.list[i][23]="";
					}else{
						pageForm.list[i][23];
					}
					tabletr += "<td id='csbid'  style='display:none' align='center'>"
							+ pageForm.list[i][23] + "</td>";
					tabletr += "<td id='goodsID'  style='display:none' align='center'>"
							+ pageForm.list[i][0] + "</td>";
					tabletr += " </tr>";
				}
				tabletr += "</table>";
				$("#body_03").html(tabletr);
				$("#body_03").show();
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}

});

/** **********************************选择物品**************************************** */
$(document).ready(function() {
	tree1 = new dhtmlXTreeObject("SubjectsAadTree", "100%", "100%", 0);
	tree1.enableDragAndDrop(false);
	tree1.enableHighlighting(1);
	tree1.enableCheckBoxes(0);
	tree1.enableThreeStateCheckboxes(false);
	tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
	var getcodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
			+ new Date().toLocaleString();
	tree1.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0,
			0);
	$.ajax({
				url : encodeURI(getcodeurl),
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
					var codeList = member.codeList;
					if (null == codeList) {
						return;
					}
					for (var i = 0; i < codeList.length; i++) {
						tree1.insertNewChild(
								"scode20101014v5zed7cukk0000000002",
								codeList[i].codeID, codeList[i].codeValue, 0,
								0, 0, 0);
						tree1.setUserData(codeList[i].codeID, "codeNumber",
								codeList[i].codeNumber);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
	});
	tree1.setOnClickHandler(function() {
		var oldtreeid = treeid;
		treeid = tree1.getSelectedItemId();
		treename = tree1.getItemText(treeid);
		if (oldtreeid != treeid) {
			if (treeid != "scode20101014v5zed7cukk0000000002") {
				$(":input#parms2").val("typeID=" + treename);
				cx("&typeID=" + treename);
				return;
			}
		}
			});

	// 双击选中物品
	$("table#gotable tr[id]").live("click", function(event) {
				var b = $("input.ragood", $(this)).attr("checked");
				$("input.ragood", $(this)).attr("checked", !b);
			});
	// 复选框选中物品
	$(".ragood").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});
	// 导入数据（选择物品）
	$("#shuju").click(function() {
				$(".jqmWindow", $("#SubjectsForm")).jqmShow();
			});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx(typeCN);
				} else {
					alert("已是尾页！");
				}
			});
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					// 选中一行克隆一行
					select++;
					// 克隆一行并修改文本框的name
					$("#kelong").before(

							$("#kelong").clone(true).attr("id",
									"kelong" + select).addClass("checkgoods")

					);
					// 修改input标签Id为goodsNomber的值
					$("input#goodsNomber", $("#kelong" + select)).attr("value",
							select - 1);
					// 修改当前行的所有input的name
					$("#kelong" + select).find(':input').each(function() {
						$(this).attr("name",
								"goodsmap[" + select + "]." + this.name);
					});
					$op = $("<option value='' selected>请选择</option>");
					// 当前行Id为goodsVariableID的select标签后追加$op变量
					$("select#goodsVariableID", $("#kelong" + select))
							.append($op);
					// 遍历Id为$(this).val()的tr里说以的td
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "goodsID") {
							$("input#goodsID", $("#kelong" + select)).attr(
									"value", $(this).text());
						} else if (this.id == "subjectsName") {
							$("input#subjectsName", $("#kelong" + select))
									.attr("value", $(this).text());
						} else if (this.id == "subjectsID") {
							$("input#subjectsID", $("#kelong" + select)).attr(
									"value", $(this).text());
						} else {
							$("span#" + this.id, $("#kelong" + select))
									.text($(this).text());
						}
						if (this.title == "ava" && $(this).text().length != 0) {
							$op = $("<option />");
							$op.attr("value", $(this).text()).text($(this)
									.text());
							$("select#goodsVariableID", $("#kelong" + select))
									.append($op);
						}
					});
					$("tr#kelong" + select).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#SubjectsForm")).jqmHide();
		} else {
			alert("请选择物品！");
		}
	});
	// 根据输入的物品编号或物品名称查询
	$("input#searchGood").click(function() {
				var typeName = $("#parameter", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName);
				cx("&parameter=" + typeName);
			});

});
// ajax查询物品列表
function cx(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#wpsy").attr("title", 0);
	$("#wpxy").attr("title", 0);
	$("#wpzy").attr("title", 0);
	var searchurl = basePath
			+ "/ea/cashierbillsclassify/sajax_ea_getGoodsManageByTypeID.jspa?BType="
			+ types;
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
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
			var pageForm = member.pageForm;
			if (pageForm == null) {
				// alert("没有数据")
				$("table").remove("#gotable");
				$("table").remove("#dixzwp");
				notoken = 0;
				return;
			}
			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#wpsy").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#wpxy").attr("title", dqy + 1);
			}
			$("span#wpzycount").text(zys);
			var tabletr = "<table width='98%' height='26' align='center' id='dixzwp' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>芯片号</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>换算单位</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].goodsID + ">";
				tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
						+ pageForm.list[i].goodsID + " name='check'/></td>";
				tabletr += "<td id='goodsCoding' align='center'>"
						+ pageForm.list[i].goodsCoding + "</td>";
				tabletr += "<td id='goodsName'  align='center'>"
						+ pageForm.list[i].goodsName + "</td>";
				tabletr += "<td id='defaultStorage'  align='center'>"
						+ pageForm.list[i].defaultStorage + "</td>";
				tabletr += "<td id='mnemonicCode' align='center'>"
						+ pageForm.list[i].mnemonicCode + "</td>";
				tabletr += "<td id='typeID' align='center'>"
						+ pageForm.list[i].typeID + "</td>";
				tabletr += "<td id='model' align='center'>"
						+ pageForm.list[i].model + "</td>";
				tabletr += "<td id='variableID'  align='center'>"
						+ pageForm.list[i].goodsvariable + "</td>";
				tabletr += "<td id='acquiesceStandard' align='center'>"
						+ pageForm.list[i].acquiesceStandard + "</td>";
				tabletr += "<td id='goodsID' style='display:none' align='center'>"
						+ pageForm.list[i].goodsID + "</td>";
				tabletr += "<td id='standard' align='center'>"
						+ pageForm.list[i].standard + "</td>";
				tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variableID + "</td>";
				tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable1ID + "</td>";
				tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable2ID + "</td>";
				tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable3ID + "</td>";
				tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable4ID + "</td>";
				tabletr += "<td id='subjectsName' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsName + "</td>";
				tabletr += "<td id='subjectsID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsID + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			$("#body_02").show();
			// alert("数据加载成功")
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}

/** **********************************选择设备**************************************** */
$(document).ready(function() {
	tree = new dhtmlXTreeObject("aadTree2", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	var getcodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
			+ new Date().toLocaleString();
	tree.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0,
			0);
	$.ajax({
				url : encodeURI(getcodeurl),
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
					var codeList = member.codeList;
					if (null == codeList) {
						return;
					}
					for (var i = 0; i < codeList.length; i++) {
						tree.insertNewChild(
								"scode20101014v5zed7cukk0000000002",
								codeList[i].codeID, codeList[i].codeValue, 0,
								0, 0, 0);
						tree.setUserData(codeList[i].codeID, "codeNumber",
								codeList[i].codeNumber);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	tree.setOnClickHandler(function() {
				var oldtreeid = treeid;
				treeid = tree.getSelectedItemId();
				treename = tree.getItemText(treeid);
				if (oldtreeid != treeid) {
					if (treeid != "scode20101014v5zed7cukk0000000002") {
						$(":input#parms2").val("typeID=" + treename);
						cx2("typeID=" + treename);
						return;
					}
				}
			});
	$("#newG").click(function() {
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 双击选中物品
	$("table#gotable2 tr[id]").live("click", function(event) {
				var b = $("input.ragood", $(this)).attr("checked");
				$("input.ragood", $(this)).attr("checked", !b);
			});

	// 上一页
	$("#wpsy2").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx2(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy2").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx2(typeCN);
				} else {
					alert("已是尾页！");
				}
			});
	// 新增物品
	$(".xzwp2").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	$("#selectGood2").click(function() {
		$("input[name='check']").each(function() {
			if ($(this).is(':checked')) {
				$("tr #" + $(this).val()).children("td").each(function() {
					$("input#" + this.id, $("table#goods")).attr("value",
							$(this).text());
				});
			}
		});
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	});

	// 根据输入的物品编号或物品名称查询
	$("input#searchGood2").click(function() {
				var typeName = $("#parameter2", $("table#searchgood2")).val();
				$(":input#parms2").val("parameter=" + typeName);
				cx2("parameter=" + typeName);
			});

	// ajax查询物品列表
	function cx2(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy2").attr("title", 0);
		$("#wpxy2").attr("title", 0);
		$("#wpzy2").attr("title", 0);
		var searchurl = basePath
				+ "/ea/cashierbillsclassify/sajax_ea_getGoodsManageByTypeID.jspa?BType="
				+ types;
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
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
				var pageForm = member.pageForm;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy2").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy2").attr("title", dqy + 1);
				}
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable2' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>统一分类条码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>换算单位</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].goodsID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
							+ pageForm.list[i].goodsID + " name='check'/></td>";
					tabletr += "<td id='goodsCoding' align='center'>"
							+ pageForm.list[i].goodsCoding + "</td>";
					tabletr += "<td id='goodsName'  align='center'>"
							+ pageForm.list[i].goodsName + "</td>";
					tabletr += "<td id='defaultStorage'  align='center'>"
							+ pageForm.list[i].defaultStorage + "</td>";
					tabletr += "<td id='mnemonicCode' align='center'>"
							+ pageForm.list[i].mnemonicCode + "</td>";
					tabletr += "<td id='typeID' align='center'>"
							+ pageForm.list[i].typeID + "</td>";
					tabletr += "<td id='model' align='center'>"
							+ pageForm.list[i].model + "</td>";
					tabletr += "<td id='variableID'  align='center'>"
							+ pageForm.list[i].goodsvariable + "</td>";
					tabletr += "<td id='acquiesceStandard' align='center'>"
							+ pageForm.list[i].acquiesceStandard + "</td>";
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='standard' align='center'>"
							+ pageForm.list[i].standard + "</td>";
					tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variableID + "</td>";
					tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable1ID + "</td>";
					tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable2ID + "</td>";
					tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable3ID + "</td>";
					tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable4ID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_022").html(tabletr);
				$("#body_022").show();
				// alert("数据加载成功")
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});
/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 选择往来单位
	$("#xzwlaw").click(function() {
				$("input#ccompanyID", $("table#searchcompany")).val("");
				$("select#contactConnections", $("table#searchcompany"))
						.val("全部");
				$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
				cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
			});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			var RegistrationURL = basePath
					+ "ea/contactcompany/sajax_ea_getListRegistration.jspa?contactCompany.ccompanyID="
					+ ccID + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(RegistrationURL),
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
					var bankList = member.bankList;
					$se = $("select#aNum", $("table#table4"));
					$se.empty();
					$se
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var i = 0; i < bankList.length; i++) {
						$op = $("<option />");
						$op.attr("value", bankList[i].bankAccount)
								.text(bankList[i].bankName + "---"
										+ bankList[i].bankAccount);
						$se.append($op);
					}
					$("span#accountNum", $("#table4")).remove();
					$("input#accountNum", $("#table4")).remove();
					$se.attr("name", "cashierBills.accountNum");
					$se.show();
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
			$("tr #" + contactcID).children("td").each(function() {
				if (this.id == "ccompanyID") {
					$("input#ccompanyID", $("table#table4"))
							.val($(this).text());

				}else if (this.id == "ccompanyname") {
					$("input#ccompanyname", $("table#table4")).val($(this).text());
					$("span#" + this.id, $("table#table4")).text($(this).text());
				} else if (this.id == "contactConnections") {
					$(
							"select#contactConnections option[value="
									+ $(this).text() + "]", $("table#table4"))
							.remove();
					$("select#contactConnections", $("table#table4"))
							.append("<option selected='selected' value = "
									+ $(this).text() + ">" + $(this).text()
									+ "</option>").show();
					$("span#contactConnections", $("table#table4")).hide();
				} else {
					$("span#" + this.id, $("table#table4"))
							.text($(this).text());
					$("input#" + this.id, $("table#table4"))
					.val($(this).text());
				}
			});
			$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
		} else {
			alert("请选择往来单位！");
		}
	});
	// 根据输入的往来单位名称查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		var contactConnections = $("select#contactConnections",
				$("table#searchcompany")).val();
		quzhi = contactConnections;
		cxwldw("contactCompany.companyName=" + typeName
				+ "&cconnection.contactConnections=" + contactConnections);
	});
	// 上一页
	$("#dwsy").click(function() {
		var sy = $("#dwsy").attr("title");
		if (sy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			if (quzhi != "") {
				var contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + sy;
			cxwldw(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#dwxy").click(function() {
		var xy = $("#dwxy").attr("title");
		if (xy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			if (quzhi != "") {
				var contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + xy;
			cxwldw(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#dwsy").attr("title", 0);
		$("#dwxy").attr("title", 0);
		$("#dwzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
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
				var pageForm = member.pageForm;
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#contactConnections",
						$("table#searchcompany"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < connectionlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", connectionlist[i].codeValue)
							.text(connectionlist[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#dwsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#dwxy").attr("title", dqy + 1);
				}
				$("span#zycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].ccompanyID + " id = "
							+ pageForm.list[i].contactConnectionID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].contactConnectionID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center'>"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i].contactConnections + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});

// 选择往来个人
$(document).ready(function() {
	var cuID = "";
	var userstaffID = "";
	$("table#gouserstable tr[id]").live("click", function(event) {
				cuID = this.id;
				userstaffID = this.title;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 选择往来个人
	$("#xzwlgr").click(function() {
				$("input#contactUserID", $("table#searchuser")).val("");
				$("select#relation", $("table#searchuser")).val("全部");
				$(".jqmWindow", $("#selectuserForm")).jqmShow();
				cxwlgr("contactUser.staffName=&contactUser.relation=");
			});
	// 新增往来个人
	$(".xzgr").click(function() {
				window.open(basePath
						+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
			});

	// 添加所选中的往来个人
	$("#qduser").click(function() {
		if (cuID != "") {
			var RegistrationuserURL = basePath
					+ "ea/contactuser/sajax_ea_getListRegistrationUser.jspa?contactUser.staffID="
					+ userstaffID + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(RegistrationuserURL),
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
					/**银行账号**/
					var bankList = member.bankList;
					$se = $("select#userNum");
					$se.empty();
					$se
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var i = 0; i < bankList.length; i++) {
						$op = $("<option />");
						$op.attr("value", bankList[i].bankAccount)
								.text(bankList[i].bankName + "---"
										+ bankList[i].bankAccount);
						$se.append($op);
					}
					$("span#userAccountNum").remove();
					$("input#userAccountNum").remove();
					$se.attr("name", "cashierBills.userAccountNum");
					$se.show();
					/**QQ**/
					var qqList = member.qqList;
					$se1 = $("select#referenceCode");
					$se1.empty();
					$se1
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var i = 0; i < qqList.length; i++) {
						$op1 = $("<option />");
						$op1.attr("value", qqList[i].contactWay)
								.text(qqList[i].contactWay);
						$se1.append($op1);
					}
					$("span#userQq").remove();
					$("input#userQq").remove();
					$se1.attr("name", "cashierBills.referenceCode");
					$se1.show();
					/**邮箱**/
					var emailList = member.emailList;
					$se2 = $("select#referenceOrganization");
					$se2.empty();
					$se2
							.append("<option selected='selected' value = ''>--请选择--</option>");
					for (var i = 0; i < emailList.length; i++) {
						$op2 = $("<option />");
						$op2.attr("value", emailList[i].contactWay)
								.text(emailList[i].contactWay);
						$se2.append($op2);
					}
					$("span#email").remove();
					$("input#email").remove();
					$se2.attr("name", "cashierBills.referenceOrganization");
					$se2.show();
					/**地址**/
					var Arraylist = member.Arraylist;
					$se3 = $("select#staffAddress");
					$se3.empty();
					$se3
							.append("<option selected='selected' value = ''>--请选择--</option>");
					if(Arraylist!= undefined){	
						for (var m = 0;m < Arraylist.length; m++) {
							$op3 = $("<option />");
							$op3.attr("value", Arraylist[m])
									.text(Arraylist[m]);
							$se3.append($op3);
						}
					}
					$("span#userAddr").remove();
					$("input#userAddr").remove();
					$se3.attr("name", "cashierBills.staffAddress");
					$se3.show();
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
			$("tr #" + cuID).children("td").each(function() {
				if (this.id == "contactUserID") {
					$("input#contactUserID", $("table#table5")).val($(this)
							.text());

				} else if (this.id == "contactUserName") {
					$("input#contactUserName", $("table#table5")).val($(this)
							.text()); 
					$("span#" + this.id, $("table#table5"))
					.text($(this).text());
				}else if (this.id == "phone") {

					$("select#phone option[value=" + $(this).text() + "]",
							$("table#table5")).remove();
					$("select#phone", $("table#table5"))
							.append("<option selected='selected' value = "
									+ $(this).text() + ">" + $(this).text()
									+ "</option>").show();
					$("span#phone", $("table#table5")).hide();
				} else {
					$("span#" + this.id, $("table#table5"))
							.text($(this).text());
					$("input#" + this.id, $("table#table5"))
					.val($(this).text());
				}
			});
			$(".jqmWindow", $("#selectuserForm")).jqmHide();
		} else {
			alert("请选择往来个人！");
		}
	});
	// 根据输入的往来个人名称查询
	$("input#searchuu").click(function() {
		cuID = "";
		userstaffID = "";
		var typeName = $("input#contactUserID", $("table#searchuser")).val();
		var relation = $("select#relation", $("table#searchuser")).val();
		quzhi = relation;
		cxwlgr("contactUser.staffName=" + typeName + "&contactUser.relation="
				+ relation);
	});
	// 上一页
	$("#grsy").click(function() {
		var sy = $("#grsy").attr("title");
		if (sy != 0) {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			if (quzhi != "") {
				var relation = quzhi;
			} else {
				relation = $("select#relation", $("table#searchuser")).val();
			}
			var typeCN = "contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation
					+ "&pageForm.pageNumber=" + sy;
			cxwlgr(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#grxy").click(function() {
		var xy = $("#grxy").attr("title");
		if (xy != 0) {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			if (quzhi != "") {
				var relation = quzhi;
			} else {
				relation = $("select#relation", $("table#searchuser")).val();
			}
			var typeCN = "contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation
					+ "&pageForm.pageNumber=" + xy;
			cxwlgr(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来个人列表
	function cxwlgr(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#grsy").attr("title", 0);
		$("#grxy").attr("title", 0);
		$("#grzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
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

				var pageForm = member.pageForm;
				var codeRelationList = member.codeRelationList;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#relation", $("table#searchuser"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < codeRelationList.length; i++) {
					$op = $("<option />");
					$op.attr("value", codeRelationList[i].codeValue)
							.text(codeRelationList[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy").attr("title", dqy + 1);
				}
				$("span#grzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
						+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].relationID + " title= "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i].relationID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='contactUserName' align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='phone' align='center'>"
							+ pageForm.list[i].relation + "</td>";
					tabletr += "<td id='tel' align='center'>"
							+ pageForm.list[i].reference + "</td>";
					tabletr += "<td id='staffIdentityCard' align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>";
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].staffID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
				$("#body_02cu").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});
