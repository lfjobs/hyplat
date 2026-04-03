$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
				modal : true,// 
				overlay : 20,// 
				revert : true
			}).jqmAddClose('.close');//

	$("#shuju").click(function() {
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});
	$(".JQueryClose").click(function() {
				notoken = 0;
				re_load();
			});
	// 修改部门事件
	$("#xgbm").click(function() {
				$("span#xgxsbm").show();
				$("span#spanTsiDept").hide();
				$("a#xgbm").hide();
			});

	// 安全检查的上传操作
	$("#cxscfj").click(function() {
				$("#cxscfjyck").show();
				$("#qxscfj").show();
				$("#cxscfj").hide();
				$("#filephoto").hide();
				return;
			});
	$("#qxscfj").click(function() {
				$("#cxscfjyck").hide();
				$("#qxscfj").hide();
				$("#cxscfj").show();
				$("#filephoto").show();
				return;
			});
	// 保存安全检查
	$("input.JQuerySubmitgd").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		notoken = 1;
		$(".put3", $("tr.checkgoods")).trigger("blur");
		var re = 0;
		$("input[name$='operationDate']", $(".checkgoods")).each(
				function(i, tmp) {
					if (this.value == "") {
						$(this).css("background-color", "red");
						re = 1;
					}
				});
		$("input[name$='startDate']", $(".checkgoods")).each(function(i, tmp) {
					if (this.value == "") {
						$(this).css("background-color", "red");
						re = 1;
					}
				});
		$("input[name$='endStart']", $(".checkgoods")).each(function(i, tmp) {
					if (this.value == "") {
						$(this).css("background-color", "red");
						re = 1;
					}
				});
		if (re) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		if (id == "") {
			$("#cashierTallyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/safeinspect/ea_saveOASafeInspectItem.jspa?pageNumber="
									+ pNumber);
			document.cashierTallyForm.submit.click();
			document.cashierTallyForm.reset();
			$(".qk").text("");
			$("select#contactConnections", $("table#table4")).hide();
			$("select#aNum", $("table#table4")).hide();
			$("select#phone", $("table#table5")).hide();
			$("select#userNum", $("table#table5")).hide();
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#cashierTallyForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/safeinspect/ea_saveOASafeInspectItem.jspa?pageNumber="
								+ pNumber);
		document.cashierTallyForm.submit.click();
		token = 2;
	});

	/** **********************************选择物品**************************************** */
	// 迭代的安全检查项双击事件修҉改҉
	$("tr.xggoods").dblclick(function() {
		id = this.id;
		$p = $("tr#" + id);
		if (!$p.hasClass("checkgoods")) {
			$p.addClass("checkgoods");
			$p.find(':input').each(function() {
				$(this).attr("name",
						"oasafeInspectItemmap[" + select + "]." + this.name);
			});
			select++;
			$p.find("td").children("span[class!=bhide]").addClass("model1");
			$p.find("td").children("input").removeClass("model1");
			$p.find("select").show();
			$p.find("a").show();
		}
	});
	// 迭代的安全检查项修҉改҉
	$(".ajaxxg").click(function() {
		id = $(this).parent().find("input#id").val();
		$p = $("tr#" + id);
		if (!$p.hasClass("checkgoods")) {
			$p.addClass("checkgoods");
			$p.find(':input').each(function() {
				$(this).attr("name",
						"oasafeInspectItemmap[" + select + "]." + this.name);
			});
			select++;
			$p.find("td").children("span[class!=bhide]").addClass("model1");
			$p.find("td").children("input").removeClass("model1");
			$p.find("select").show();
			$p.find("a").show();
		}
	});
	// 迭代的安全检查项删除
	$(".ajaxsc").click(function() {

		if (confirm("是否删除？")) {
			var id = $(this).parent().find("input#id").val();
			var delurl = basePath
					+ "ea/safeinspect/sajax_ea_delOASafeInspectItem.jspa?typeID="
					+ id + "&date=" + new Date().toLocaleString();
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

	/** *******************************取得部门下拉************************************ */
	var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
	var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
	$("span#companyNames").text(treePName);
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
					var ts = new TreeSelector($("select#tsiDept")[0], data, -1);
					ts.createTree();
					if (tsiDeptID != "") {
						$("table#tabletop").find("select#tsiDept").attr(
								"value", tsiDeptID);
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	// //////////////////////////////////////////////////
	// 克隆的安全任务删除
	$(".klsc").click(function() {
				$(this).parent().parent().remove();
			});
	$(document).ready(function() {
		// 双击选中物品
		var ajaxTypeName;
		$("table#gotable tr[id]").live("click", function(event) {
					var b = $("input.ragood", $(this)).attr("checked");
					$("input.ragood", $(this)).attr("checked", !b);
				});
		// 复选框选中物品
		$(".ragood").live("click", function(event) {
					var b = $(this).attr("checked");
					$(this).attr("checked", !b);
				});
		// 选择安全类别
		$("#shuju").click(function() {
			$(".jqmWindow", $("#goodsForm")).jqmShow();
			if (id == "") {
				ajaxTypeName = $("select#inspectTypeID")
						.find("option:selected").val();
			} else {
				ajaxTypeName = $("input#inspectTypeID").val();
			}
			cx("typeName=" + ajaxTypeName);
		});
		var ajaxTypeName ='';
		// 上一页
		$("#wpsy").click(function() {
			var sy = $("#wpsy").attr("title");
			if (sy != 0) {
				var typeName = $(":input#parms").val();
				var typeCN = typeName + "&pageForm.pageNumber=" + sy
						+ "&typeName=" + ajaxTypeName;
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
				var typeCN = typeName + "&pageForm.pageNumber=" + xy
						+ "&typeName=" + ajaxTypeName;
				cx(typeCN);
			} else {
				alert("已是尾页！");
			}
		});
		// 新增安全类别
		$(".xzwp").click(function() {
					$(".jqmWindow", $("#goodsForm")).jqmHide();
					$(".jqmWindow", $("#TypeForm")).jqmShow();
				});
		// 保存新增类别
		$("input.saveType").click(function() {
			if ($("form .error").length) {
				return;
			}
			if ($("#body_02").html() == "") {
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' width='40' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' width='140' bgcolor='#E4F1FA' >安全检查类别名称</th><th align='center' bgcolor='#E4F1FA'>描述</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>检查指标</th></tr></table>";
				$("#body_02").html(tabletr);
			}
			var url = basePath
					+ "ea/oasafeKind/sajax_ea_saveOASafeKind.jspa?parentID="
					+ ajaxTypeName;
			$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : $("#TypeForm").serialize(),
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var entity = member.entity;
					if (null == entity) {
						return;
					}
					var str = "";
					str += "<tr style='cursor: hand;' id = " + entity.id + ">";
					str += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
							+ entity.id + " name='check'/></td>";
					str += "<td id='name' align='center' >" + entity.name
							+ "</td>";
					str += "<td id='descRiption'  align='center'>"
							+ entity.descRiption + "</td>";
					str += "<td id='guideline'  align='center'>"
							+ entity.guideline + "</td>";
					str += "<td id='companyID' style='display:none' align='center'>"
							+ entity.companyID + "</td>";
					str += "<td id='id' style='display:none' align='center'>"
							+ entity.id + "</td>";
					str += " </tr>";
					$("table#gotable", $("#goodsForm")).append(str);
					$(".jqmWindow", $("#TypeForm")).jqmHide();
					$(".jqmWindow", $("#goodsForm")).jqmShow();
					document.TypeForm.reset();

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		});
		// 取消
		$("input.unSave").click(function() {
					document.TypeForm.reset();
					$(".jqmWindow", $("#TypeForm")).jqmHide();
					$(".jqmWindow", $("#goodsForm")).jqmShow();
				});
		// 新增一行
		$("#selectLeibie").click(function() {
			select++;
			// 克隆一行并修改文本框的name
			$("#kelong").after($("#kelong").clone(true).attr("id",
					"kelong" + select).addClass("checkgoods"));
			$("#kelong" + select).find(':input').each(function() {
				$(this).attr("name",
						"oasafeInspectItemmap[" + select + "]." + this.name);
			});
			$("tr#kelong" + select).show();

		});
		// 添加所选中的物品到物品单
		$("#selectGood").click(function() {
			if ($("[name='check']").is(':checked')) {
				$("input[name='check']").each(function() {
					if ($(this).is(':checked')) {
						// 选中一行克隆一行
						select++;
						// 克隆一行并修改文本框的name
						$("#kelong").after($("#kelong").clone(true).attr("id",
								"kelong" + select).addClass("checkgoods"));
						$("#kelong" + select).find(':input').each(function() {
							$(this).attr(
									"name",
									"oasafeInspectItemmap[" + select + "]."
											+ this.name);
						});

						$("tr #" + $(this).val()).children("td").each(
								function() {
									if (this.id == "name") {
										$("input#inspectType",
												$("#kelong" + select)).attr(
												"value", $(this).text());
									} else {
										$("span#" + this.id,
												$("#kelong" + select))
												.text($(this).text());
									}
								});
						$("tr#kelong" + select).show();
						$(this).attr("checked", false);
					}
				});
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			} else {
				alert("请选择物品！");
			}
		});
		// 根据输入
		$("input#searchGood").click(function() {
					var typeName = $("#typeID", $("table#searchgood")).val();
					$(":input#parms").val("parameter=" + typeName);
					cx("parameter=" + typeName + "&typeName=" + ajaxTypeName);
				});
		// ajax查询安全类别列表
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
					+ "/ea/safeinspect/sajax_ea_getOASafeKindManager.jspa?";
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
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var pageForm = member.pageForm;
					if (pageForm == null) {
						$("#body_02").html("");
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
					var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
					tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
					tabletr += " <tr><th height='21' align='center' width='40' bgcolor='#E4F1FA'>选择</th>";
					tabletr += "<th align='center' width='140' bgcolor='#E4F1FA' >安全检查类别名称</th><th align='center' bgcolor='#E4F1FA'>描述</th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'>检查指标</th>";
					for (var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' id = "
								+ pageForm.list[i].id + ">";
						tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
								+ pageForm.list[i].id + " name='check'/></td>";
						tabletr += "<td id='name' align='center' >"
								+ pageForm.list[i].name + "</td>";
						tabletr += "<td id='descRiption'  align='center'>"
								+ pageForm.list[i].descRiption + "</td>";
						tabletr += "<td id='guideline'  align='center'>"
								+ pageForm.list[i].guideline + "</td>";
						tabletr += "<td id='companyID' style='display:none' align='center'>"
								+ pageForm.list[i].companyID + "</td>";
						tabletr += "<td id='id' style='display:none' align='center'>"
								+ pageForm.list[i].id + "</td>";
						tabletr += " </tr>";
					}
					tabletr += " </table>";
					$("#body_02").html(tabletr);
					$("#body_02").show();
					alert("数据加载成功");
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
			$("select#contactConnections", $("table#searchcompany")).val("全部");
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
						$se.attr("name", "entity.contactcompanyBankNum");
						$se.show();
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
				$("tr #" + contactcID).children("td").each(function() {
					if (this.id == "ccompanyID") {
						$("input#ccompanyID", $("table#table4")).val($(this)
								.text());

					} else if (this.id == "contactConnections") {
						$(
								"select#contactConnections option[value="
										+ $(this).text() + "]",
								$("table#table4")).remove();
						$("select#contactConnections", $("table#table4"))
								.append("<option selected='selected' value = "
										+ $(this).text() + ">" + $(this).text()
										+ "</option>").show();
						$("span#contactConnections", $("table#table4")).hide();
					} else {
						$("span#" + this.id, $("table#table4")).text($(this)
								.text());
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
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
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
				var contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
				var typeCN = "contactCompany.companyName=" + typeName
						+ "&cconnection.contactConnections="
						+ contactConnections + "&pageForm.pageNumber=" + sy;
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
				var contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
				var typeCN = "contactCompany.companyName=" + typeName
						+ "&cconnection.contactConnections="
						+ contactConnections + "&pageForm.pageNumber=" + xy;
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
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
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
					alert("数据加载成功");
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
						$se.attr("name", "entity.contactUserBankNum");
						$se.show();
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

					} else if (this.id == "phone") {

						$("select#phone option[value=" + $(this).text() + "]",
								$("table#table5")).remove();
						$("select#phone", $("table#table5"))
								.append("<option selected='selected' value = "
										+ $(this).text() + ">" + $(this).text()
										+ "</option>").show();
						$("span#phone", $("table#table5")).hide();
					} else {
						$("span#" + this.id, $("table#table5")).text($(this)
								.text());
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
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			var relation = $("select#relation", $("table#searchuser")).val();
			cxwlgr("contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation);
		});
		// 上一页
		$("#grsy").click(function() {
			var sy = $("#grsy").attr("title");
			if (sy != 0) {
				cuID = "";
				userstaffID = "";
				var typeName = $("input#contactUserID", $("table#searchuser"))
						.val();
				var relation = $("select#relation", $("table#searchuser"))
						.val();
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
				var relation = $("select#relation", $("table#searchuser"))
						.val();
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
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
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
							+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th><th align='center' bgcolor='#E4F1FA'>家庭地址</th></tr>";
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
						tabletr += "<td id='userQq'  align='center'>"
								+ pageForm.list[i].referenceCode + "</td>";
						tabletr += "<td id='email'  align='center'>"
								+ pageForm.list[i].referenceOrganization
								+ "</td>";
						tabletr += "<td id='userAddr'  align='center'>"
								+ pageForm.list[i].staffAddress + "</td>";
						tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
								+ pageForm.list[i].staffID + "</td>";
						tabletr += " </tr>";
					}
					tabletr += " </table>";
					$("#body_02cu").html(tabletr);
					$("#body_02cu").show();
					alert("数据加载成功");
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
});
function re_load() {
	document.location.href = basePath
			+ "ea/safeinspect/ea_getSafeInspectList.jspa?pageNumber=" + pNumber
			+ "&search=" + search;
}