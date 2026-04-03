$(function() {
	// 为弹出框准备下拉表内容

	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//

	$("#staffID").hide();
	$("#departmentID").hide();
	if (cashierID == "") {
		var staff = $("#staffID").attr("name", "cashierTally.staffID").show();
		var dept = $("#departmentID").attr("name", "cashierTally.departmentID")
				.show();
		$("td#dept").html(dept);
		$("td#staff").html(staff);
	} else {
		$(".classhide").show();
	}

	$(".update").click(function() {
		var selectID = $(this).attr("title");
		var departmentIDselect = $(this).next("select").attr("name",
				"cashierTally." + selectID);
		$(this).parent().html(departmentIDselect);
		$("#" + selectID).show();
	});
	// 科目管理
	$('td.subjects select[number]').change(function() {
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.subjects", $("#cashierTallyForm"));
		$td.children('select:gt(' + this.number + ')').empty();
		var subjectsPID = $td.children('select:eq(' + this.number + ')')
				.children("option:selected").attr("id");
		var subjectsnumber = $td.children('select:eq(' + this.number + ')')
				.children("option:selected").val();
		$td.find("#subjectsID").attr("value", subjectsnumber);
		if (subjectsnumber == "") {
			var numbers = parseInt(this.number) - 1;
			subjectsnumber = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			$td.find("#subjectsID").attr("value", subjectsnumber);
		}
		try {
			window.status = "正在加载数据";
			$.ajax({
				url : encodeURI(subjecturl + "?subjectsID=" + subjectsPID
						+ "&date=" + new Date().toLocaleString()),
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
					var subjectsList = member.subjectsList;
					$td = $("td.subjects", $("#cashierTallyForm"));
					$select = "<option selected='selected' value = ''>--请选择--</option>";
					$td.children('select:eq(' + number + ')').append($select);
					for (var i = 0; i < subjectsList.length; i++) {
						$op = $("<option />");
						$op.attr("value", subjectsList[i].subjectsNumbers)
								.attr("id", subjectsList[i].subjectsID)
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
						$td.children('select:eq(' + number + ')')
								.trigger("change");
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		} catch (e) {
			return;
		}

	});
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
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					subRule = (member.subRule.rules).split(",");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	var subjecturl = basePath
			+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=002&date="
			+ new Date().toLocaleString();
	var subjectsNumber = $("td.subjects", $("#cashierTallyForm"))
			.find("#subjectsID").attr("value");
	window.status = "正在加载数据";
	$.ajax({
		url : encodeURI(subjecturl),
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
			$td = $("td.subjects", $("#cashierTallyForm"));
			$select = "<option selected='selected' value = ''>--请选择--</option>";
			$td.children('select:eq(0)').append($select);
			for (var i = 0; i < subjectsList.length; i++) {
				$op = $("<option />");
				$op.attr("value", subjectsList[i].subjectsNumbers).attr("id",
						subjectsList[i].subjectsID)
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
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
	// **********************************物品开始*********************************
	$("#checksgoods").click(function() {
				document.goodsForm.reset();
				$("#goodsjqModel").jqmShow();
			});
	$("input.JQuerySubmitgoods").click(function() {
		if ($("goodsForm .error").length) {
			return;
		}
		if ($("input.goodsCoding", $("#goodsForm")).val() == '') {
			alert('编号不能为空');
			return;
		}
		if ($("input.goodsName", $("#goodsForm")).val() == '') {
			alert('名称不能为空');
			return;
		}
		$("#goodsForm").attr("target", "hidden").attr("action",
				basePath + "ea/goodsmanage/ea_saveGoodsManage.jspa?");
		document.goodsForm.submit.click();
		document.goodsForm.reset();
		$("#goodsjqModel").jqmHide();
		token = 0;
	});
	$("input.JQueryreturngoods").click(function() {// 取消
				$("#goodsjqModel").jqmHide();
				document.goodsForm.reset();
			});
	// **********************************物品结束*********************************
	// **********************************往来单位开始*********************************

	$("#checkscompany").click(function() {
				document.ccompanyForm.reset();
				LiuZhongYaoDeShaGuaDiZhi("", "ccompanyjqModel");
				myform = "ccompanyjqModel";
				$("#ccompanyjqModel").jqmShow();
			});
	$("input.JQuerySubmitccompany").click(function() {
		if ($("input#companyName", $("#ccompanyForm")).val() == "") {
			alert("单位不能为空");
			return;
		}
		var addr = "";
		$(".JQueryaddress", $("#ccompanyjqModel")).find("select")
				.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--')
								addr = addr + $(this).text();
						});
		$("#ccompanyForm").find("input#companyAddr").val(addr);
		$("#ccompanyForm").attr("target", "hidden").attr("action",
				basePath + "ea/contactcompany/ea_saveContactCompany.jspa?");
		document.ccompanyForm.submit.click();
		document.ccompanyForm.reset();
		$("#ccompanyjqModel").jqmHide();
		token = 0;
	});
	$("input.JQueryreturnccompany").click(function() {// 取消
				$("#ccompanyjqModel").jqmHide();
				document.ccompanyForm.reset();
			});
	// **********************************往来单位结束*********************************
	// **********************************往来个人开始*********************************
	$("#checksuser").click(function() {
				document.userForm.reset();
				LiuZhongYaoDeShaGuaDiZhi("", "userjqModel");
				myform = "userjqModel";
				$("#userjqModel").jqmShow();
			});
	$("input.JQuerySubmituser").click(function() {
		if ($("input.contactUserName", $("#userjqModel")).val() == "") {
			alert("用户名不能为空");
			return;
		}

		if ($("userForm .error").length) {
			return;
		}
		var addr = "";
		$(".JQueryaddress", $("#userjqModel")).find("select")
				.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--')
								addr = addr + $(this).text();
						});
		$("#userForm").find("input#userAddr").val(addr);
		$("#userForm").attr("target", "hidden").attr("action",
				basePath + "ea/contactuser/ea_saveContactUser.jspa?");
		document.userForm.submit.click();
		document.userForm.reset();
		$("#userjqModel").jqmHide();
	});
	$("input.JQueryreturnuser").click(function() {// 取消
				$("#userjqModel").jqmHide();
				document.userForm.reset();
				token = 0;
			});
	// **********************************往来个人结束*********************************
	$("input.JQueryreturndistrict").click(function() {// 取消
				$(".jqdistrice").jqmHide();

			});

	// 计算金额
	$(".jisuan").keyup(function() {
				if (this.value != "") {
					if (isNaN(this.value)) {
						return;
					} else {
						var dj = $("#quantity").val();
						var price = $("#price").val();
						if (!isNaN(dj) && !isNaN(price)) {
							var jine = dj * price;
							jine = Math.round(jine * 100) / 100;
							$("#money").attr("value", jine);
						}
					}
				}
			});
	$(".jisuan").keydown(function() {
				if (this.value != "") {
					if (isNaN(this.value)) {
						return;
					} else {
						var dj = $("#quantity").val();
						var price = $("#price").val();
						if (!isNaN(dj) && !isNaN(price)) {
							var jine = dj * price;
							jine = Math.round(jine * 100) / 100;

							$("#money").attr("value", jine);
						}
					}
				}
			});
	if (photoPath != null && photoPath != "") {
		$("img#photoPath").attr("src", basePath + photoPath);
	}
	if (accessory != null && accessory != "") {
		$("img#accessory").attr("src", basePath + accessory);
	}
	// 提交保存
	$("input.JQuerySubmit").click(function() {
		$(".put3").trigger("blur");
		$(".jisuan").trigger("blur");
		if ($("#cashierTallyForm .error").length) {
			alert("请填完所有必填项");
			return;
		}
		// var subjectsID=$("#subjectsID").val();
		var goodsID = $("#goodsID").val();
		var cashierID = $("#cashierID").val();
		if (goodsID == "") {
			alert("该物品不存在！");
			return;
		}
		if (cashierID == "") {
			$("#cashierTallyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/cashiertally/ea_saveCashierTally.jspa?pageNumber="
									+ pNumber);
			document.cashierTallyForm.submit.click();
			document.cashierTallyForm.reset();
			$("img").attr("src", "");
			$("#companyNames", $("#table")).attr("value", treePName);
			token = 1;
			return;
		}
		$("#cashierTallyForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/cashiertally/ea_saveCashierTally.jspa?pageNumber="
								+ pNumber);
		document.cashierTallyForm.submit.click();
		token = 2;
	});
	$("input.JQueryreturn").click(function() {// 取消
				$("#tdanwei").jqmHide;
				re_load();
			});
	// 取得部门下拉
	var treeID = parent.frames["leftFrame"].tree.getSelectedItemId();
	var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
	var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
	var data = new Array();
	$("#companyNames", $("#table")).attr("value", treePName);
	data[0] = {
		id : treeID,
		pid : '-1',
		text : treeName
	};
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
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	function TreeSelector(item, data, rootId) {
		this._data = data;
		this._item = item;
		this._rootId = rootId;
	}

	TreeSelector.prototype.createTree = function() {
		var len = this._data.length;
		for (var i = 0; i < len; i++) {
			if (this._data[i].pid == this._rootId) {
				this._item.options.add(new Option("" + this._data[i].text,
						this._data[i].id));
				for (var j = 0; j < len; j++) {
					this.createSubOption(len, this._data[i], this._data[j]);
				}
			}
		}
	};
	TreeSelector.prototype.createSubOption = function(len, current, next) {
		var blank = ' ';
		if (next.pid == current.id) {
			intLevel = 0;
			var intlvl = this.getLevel(this._data, this._rootId, current);
			for (var a = 0; a < intlvl; a++)
				blank += "　";
			blank += "├";
			this._item.options.add(new Option(blank + next.text, next.id));
			for (var j = 0; j < len; j++) {
				this.createSubOption(len, next, this._data[j]);
			}
		}
	};
	TreeSelector.prototype.getLevel = function(datasources, topId, currentitem) {
		var pid = currentitem.pid;
		if (pid != topId) {
			for (var i = 0; i < datasources.length; i++) {
				if (datasources[i].id == pid) {
					intLevel++;
					this.getLevel(datasources, topId, datasources[i]);
				}
			}
		}
		return intLevel;
	};
	$t = $("table#table");
	var ts = new TreeSelector($t.find("select#departmentID")[0], data, -1);
	ts.createTree();
	if (deptID != "") {
		$t.find("select#departmentID").attr("value", deptID);
	}

	// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	var PID = '';// 当点新曾时,上一级被选中项的id
	var rovince = '';// 被改变的那个的id
	function LiuZhongYaoDeShaGuaDiZhi(address, myform) {
		// 非空验证还原
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");
		// 非空验证End
		$td = $("td.JQueryaddress", $("#" + myform));
		$td.children('select:gt(0)').empty();
		$td.children('select').attr("disabled", false).empty().show();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$('#newdistrict', $td).jqmHide;
		$td = $("td.JQueryaddress", $("#" + myform));
		$('td.JQueryaddress input[name=changes]', $("#" + myform)).show();
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0&date="
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
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(urldistrict),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
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
				// num = distinctlistSaved.length -1 ;
				// $td.children('select:gt(' + num + ')').hide();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	}

	$('td.JQueryaddress select[number]').change(function() {

		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress", $("#" + myform));
		rovince = "#" + province;
		$('#newdistrict', $td).jqmHide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#" + userjqModel).jqmHide();
			$("#newdistrict").jqmShow();
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		var districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ districtPID + "&date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlist = member.distinctlist;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].districtID).attr(
								"id", distinctlist[i].districtID)
								.text(distinctlist[i].districtName);
						$(rovince, $td).next().append($op);
					}
				}
				$add = "<option class='add'  value = '" + districtPID
						+ "' >--新增--</option>";
				$(rovince, $td).next().append($add);
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		$td.find('input#address', $("#" + myform)).val(districtPID);
	});

	$('input#savedistrict').click(function() {
		$td = $("td.JQueryaddress", $("#" + myform));
		number = $('input#districtNames').attr('title');
		districtName = $('input#districtNames').val();
		$td.children('select:gt(' + number + ')').empty();
		if ('' == districtName) {
			alert("请填写城市名称");
			return;
		}
		$("#newdistrict").jqmHide();
		$("#" + myform).jqmShow();
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?district.districtPID="
				+ PID + "&district.districtName=" + districtName + "&date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(urldistrict),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var sdistrict = member.sdistrict;
				$op1 = $("<option selected='selected'/>").attr("value",
						sdistrict.districtID).attr("id", sdistrict.districtID)
						.text(sdistrict.districtName);
				$("#" + sdistrict.districtID, $td).remove();
				$(rovince, $td).append($op1);
				districtPID = sdistrict.districtID;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				$add = "<option class='add'  value = '" + districtPID
						+ "' >--新增--</option>";
				$(rovince, $td).next().append($add);
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		$td.find('input#address').val(districtPID);
	});
		// 保存新地址...............
		// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================END!

});

/** ******************物品模糊查询****************** */
function getgoodslist(keyID, goodsID) {
	if (tokens) {
		return;
	}
	if ($("#" + keyID).val() == "") {
		return;
	}
	if ($("#" + keyID).val() == undefined) {
		return;
	}
	if (keyvalue == $("#" + keyID).val()) {
		return;
	}
	keyvalue = $("#" + keyID).val();
	tokens = 1;
	var searchurl = basePath
			+ "ea/goodsmanage/sajax_ea_getListGoodsManageBygoodsCodingOrgoodsName.jspa?parameter="
			+ $("#" + keyID).val() + "&date=" + new Date().toLocaleString();
	window.status = "正在加载数据";
	$.ajax({
		url : encodeURI(searchurl),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var goodsManageList = member.goodsManageList;
			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
			tabletr += "<table width='98%' height='26' align='center' cellpadding='1' cellspacing='0' class='table' id='stafftable' style='margin-bottom:5px; margin-top:5px;'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>品名编号</th><th align='center' bgcolor='#E4F1FA'>品名名称</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>类型</th><th align='center' bgcolor='#E4F1FA'>单位</th><th align='center' bgcolor='#E4F1FA'>默认规格</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>品牌规格</th><th align='center' bgcolor='#E4F1FA'>品牌</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>缺省仓库</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>厂家</th></tr>";

			for (var i = 0; i < goodsManageList.length; i++) {
				if (i == 10)
					break;
				tabletr += "<tr style='cursor: hand;' id = "
						+ goodsManageList[i].goodsID
						+ " onclick='checksgoodsManage(\""
						+ goodsManageList[i].goodsID + "\")'>";
				tabletr += "<td id='goodsCoding' align='center'>"
						+ goodsManageList[i].goodsCoding + "</td>";
				tabletr += "<td id='goodsID' style='display: none;'  align='center'>"
						+ goodsManageList[i].goodsID + "</td>";
				tabletr += "<td id='photoPath' style='display: none;'  align='center'>"
						+ goodsManageList[i].photoPath + "</td>";
				tabletr += "<td id='goodsName' align='center'>"
						+ goodsManageList[i].goodsName + "</td>";
				tabletr += "<td id='typeID' align='center'>"
						+ goodsManageList[i].typeID + "</td>";
				tabletr += "<td id='variableID' align='center'>"
						+ goodsManageList[i].variableID + "</td>";
				tabletr += "<td id='acquiesceStandard' align='center'>"
						+ goodsManageList[i].acquiesceStandard + "</td>";
				tabletr += "<td id='standard' align='center'>"
						+ goodsManageList[i].standard + "</td>";
				tabletr += "<td id='mnemonicCode' align='center'>"
						+ goodsManageList[i].mnemonicCode + "</td>";
				tabletr += "<td id='model' align='center'>"
						+ goodsManageList[i].model + "</td>";
				tabletr += "<td id='defaultStorage' align='center'>"
						+ goodsManageList[i].defaultStorage + "</td>";
				tabletr += "<td id='manufacturers' align='center'>"
						+ goodsManageList[i].manufacturers + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			window.status = "数据加载成功";
			$("#body_02").show();
			if (goodsID != "") {
				checksgoodsManage(goodsID);
				goodsID = "";
			}
			tokens = 0;
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}
function checksgoodsManage(goodsID) {
	var $f = $("#goodstable");
	$("#" + goodsID).children("td").each(function() {
				if (this.id == "photoPath") {
					$("#" + this.id, $f).attr("src", basePath + $(this).text());
				}
				$("#" + this.id, $f).attr("value", $(this).text());
			});
	$("#body_02").html("");
}
/** ******************往来单位模糊查询****************** */
function getcontactcompanylist(keyID, ccompanyID) {
	if (tokens) {
		return;
	}
	if ($("#" + keyID).val() == "") {
		return;
	}
	if ($("#" + keyID).val() == undefined) {
		return;
	}
	if (keyvalue == $("#" + keyID).val()) {
		return;
	}
	keyvalue = $("#" + keyID).val();
	tokens = 1;
	var searchurl = basePath
			+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?contactCompany.companyName="
			+ $("#" + keyID).val() + "&contactCompany.status=00&date="
			+ new Date().toLocaleString();
	window.status = "正在加载数据";
	$.ajax({
		url : encodeURI(searchurl),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var contactCompanyList = member.contactCompanyList;
			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
			tabletr += "<table width='98%' height='26' align='center' cellpadding='1' cellspacing='0' class='table' id='stafftable' style='margin-bottom:5px; margin-top:5px;'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>往来单位</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>单位负责人电话</th><th align='center' bgcolor='#E4F1FA'>公司地址</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>单位代码</th></tr>";
			for (var i = 0; i < contactCompanyList.length; i++) {
				if (i == 10)
					break;
				tabletr += "<tr style='cursor: hand;' id = "
						+ contactCompanyList[i].ccompanyID
						+ " onclick='checkscontactcompany(\""
						+ contactCompanyList[i].ccompanyID + "\")'>";
				tabletr += "<td id='companyName' align='center'>"
						+ contactCompanyList[i].companyName + "</td>";
				tabletr += "<td id='ccompanyID' style='display: none;'  align='center'>"
						+ contactCompanyList[i].ccompanyID + "</td>";
				tabletr += "<td id='companyTel' align='center'>"
						+ contactCompanyList[i].companyTel + "</td>";
				tabletr += "<td id='cresponsible' align='center'>"
						+ contactCompanyList[i].cresponsible + "</td>";
				tabletr += "<td id='responsibleTel' align='center'>"
						+ contactCompanyList[i].responsibleTel + "</td>";
				tabletr += "<td id='companyAddr' align='center'>"
						+ contactCompanyList[i].companyAddr + "</td>";
				tabletr += "<td id='remark' align='center'>"
						+ contactCompanyList[i].remark + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			window.status = "数据加载成功";
			$("#body_02").show();
			if (ccompanyID != "") {
				checkscontactcompany(ccompanyID);
				ccompanyID = "";
			}
			tokens = 0;
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}
function checkscontactcompany(ccompanyID) {
	var $f = $("#contactcompany");
	$("#" + ccompanyID).children("td").each(function() {
				$("#" + this.id, $f).attr("value", $(this).text());
			});
	$("#body_02").html("");
}
/** *****************************往来个人********************************* */
function getcontactUserlist(keyID, contactUserID) {
	if (tokens) {
		return;
	}
	if ($("#" + keyID).val() == "") {
		return;
	}
	if ($("#" + keyID).val() == undefined) {
		return;
	}
	if (keyvalue == $("#" + keyID).val()) {
		return;
	}
	keyvalue = $("#" + keyID).val();
	tokens = 1;
	var searchurl = basePath
			+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?contactUser.contactUserName="
			+ $("#" + keyID).val() + "&contactUser.status=00&date="
			+ new Date().toLocaleString();;
	window.status = "正在加载数据";
	$.ajax({
		url : encodeURI(searchurl),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var contactUserList = member.contactUserList;

			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
			tabletr += "<table width='98%' height='26' align='center' cellpadding='1' cellspacing='0' class='table' id='stafftable' style='margin-bottom:5px; margin-top:5px;'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>往来个人</th><th align='center' bgcolor='#E4F1FA'>个人身份证号</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>固定电话</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>qq</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>邮箱</th><th align='center' bgcolor='#E4F1FA'>地址</th></tr>";

			for (var i = 0; i < contactUserList.length; i++) {
				if (i == 10)
					break;
				tabletr += "<tr style='cursor: hand;' id = "
						+ contactUserList[i].contactUserID
						+ " onclick='checkscontactUser(\""
						+ contactUserList[i].contactUserID + "\")'>";
				tabletr += "<td id='contactUserName' align='center'>"
						+ contactUserList[i].contactUserName + "</td>";
				tabletr += "<td id='contactUserID' style='display: none;'  align='center'>"
						+ contactUserList[i].contactUserID + "</td>";
				tabletr += "<td id='staffIdentityCard' align='center'>"
						+ contactUserList[i].staffIdentityCard + "</td>";
				tabletr += "<td id='phone' align='center'>"
						+ contactUserList[i].phone + "</td>";
				tabletr += "<td id='tel' align='center'>"
						+ contactUserList[i].tel + "</td>";
				tabletr += "<td id='userQq' align='center'>"
						+ contactUserList[i].userQq + "</td>";
				tabletr += "<td id='email' align='center'>"
						+ contactUserList[i].email + "</td>";
				tabletr += "<td id='userAddr' align='center'>"
						+ contactUserList[i].userAddr + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			window.status = "数据加载成功";
			$("#body_02").show();
			if (contactUserID != "") {
				checkscontactUser(contactUserID);
				contactUserID = "";
			}
			tokens = 0;
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}
function checkscontactUser(contactUserID) {
	var $f = $("#contactcompany");
	$("#" + contactUserID).children("td").each(function() {
				$("#" + this.id, $f).attr("value", $(this).text());
			});
	$("#body_02").html("");
}

function re_load() {
	document.location.href = basePath
			+ "ea/cashiertally/ea_getCashierTallyList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value");
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
