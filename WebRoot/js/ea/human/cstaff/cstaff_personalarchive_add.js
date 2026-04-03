var chipids = new Array();
var i=0;
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
				loadcab.window.closePort();//关闭读数据端口
				chipids = new Array();
                i = 0;
                $("#tbody").html("");
			});

	$(".JQueryClose").click(function() {
				notoken = 0;
				re_load();
			});
			
			
	//手动输入点击
	$(".manual").click(function(){
	 $(this).hide();
	 $(".scan").show();
	 $("#searchGood").show();
//	 $("#recordCode", $("table#searchgood")).removeAttr("readonly");
		
		
	});
	
	// 扫描输入点击
	$(".scan").click(function() {
				$(this).hide();
				$(".manual").show();
				$("#searchGood").hide();
//				$("#recordCode", $("table#searchgood")).attr({
//							readonly : 'true'
//						});
			});

	 setInterval(function() {
	 	
	 	var recordCode = $("#recordCode", $("table#searchgood")).val();
	 	var type = $("#goodsForm #type").val();
	    if ($("#goodsForm .scan").is(":hidden")) {
	           if($("#tbody").html().indexOf($.trim(recordCode))>0){
	 	       $("#recordCode", $("table#searchgood")).val("");
	 	        return;
	 	       }
		if (recordCode != "") {
			$("input#parms").val("parameter=" + recordCode);
			cx("parameter=" + recordCode, type);
			$("#recordCode", $("table#searchgood")).val("");
//			$("#recordCode", $("table#searchgood"))[0].focus(); 
			
		}

	}
	 },1000);
	 

	


	if (type == 'in') {
		$("input.JQuerySubmitgd").val("入库");
		$("tr.out").hide();
	} else {
		$("input.JQuerySubmitgd").val("出库");
		$("tr.out").show();
	}

	$("input.JQueryunitret").click(function() {// 重置往来单位
				$t = $("table#table4");
				$t.find("span.qk").each(function() {
							$(this).text("");
						});
				$t.find("select").each(function() {
							$(this).empty();
							$(this).attr("style", "display:none");
						});
				$t.find(":input").each(function() {
							$(this).attr("value", "");
						});
			});
	$("input.JQuerypersonret").click(function() {// 重置往来个人
				$t = $("table#table5");
				$t.find("span.qk").each(function() {
							$(this).text("");
						});
				$t.find("select").each(function() {
							$(this).empty();
							$(this).attr("style", "display:none");
						});
				$t.find(":input").each(function() {
							$(this).attr("value", "");
						});
			});

	// 双҉击҉事҉件҉修҉改
	$("tr.xggoods").dblclick(function() {
		var taxstatus = $("#cashiertaxstatus").val();
		if (taxstatus != "00") {
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
					$(this).parent().parent().find(":input#money").attr(
							"value", jine);
				}
			}
		}
	});
	// 修҉改҉
	$(".ajaxxg").click(function() {
		var taxstatus = $("#cashiertaxstatus").val();
		if (taxstatus != "00") {
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
		var taxstatus = $("#cashiertaxstatus").val();
		if (taxstatus != "00") {
			alert("已归档不能删除该条数据！");
			return;
		}
		if (confirm("是否删除？")) {
			var goodsBillsID = $(this).parent().find("input#goodsBillsID")
					.val();
			var delurl = basePath
					+ "ea/cashierbills/sajax_ea_delGoodsBills.jspa?typeID="
					+ goodsBillsID + "&date=" + new Date().toLocaleString();
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
				docNull -= 1;
			});
			
		// 克隆的商品查看
	$(".view").click(function() {
		var $tr = $(this).parent().parent();
		var staffID = $tr.find("input#staffID").val();
		var url = basePath
			+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
			+ staffID;
	    window.open(url, '', 'scrollbars=yes,resizable=yes,channelmode');
		

		});

	// 入库
	$("input.JQuerySubmitgd").click(function() {
		if (type == "out") {
			if ($("#managerName").val() == "") {
				alert("请选择出库接收人");
				return;
			}
		}
		if($("#goodtable tbody > tr ").length<=1){
			alert("请选择物品");
			return;
		}
		$(".notnull").trigger("blur");
		if ($("form .error").length) {
			return;
		}
//		if (notoken) {
//			alert("正在提交数据！请稍等");
//			return;
//		}
		notoken = 1;

		$(".put3", $("tr.checkgoods")).trigger("blur");
		if ($("#PArchiveForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		if (pabillID == "") {
			$("#PArchiveForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/personalarchive/ea_savePArchive.jspa?pageNumber="
									+ pNumber);
			document.PArchiveForm.submit.click();
			document.PArchiveForm.reset();
			$(".qk").text("");
			$("select#contactConnections", $("table#table4")).hide();
			$("select#aNum", $("table#table4")).hide();
			$("select#phone", $("table#table5")).hide();
			$("select#userNum", $("table#table5")).hide();
			$("#cashierstatus").attr("value", "00");
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#PArchiveForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/personalarchive/ea_savePArchive.jspa?pageNumber="
								+ pNumber);
		document.PArchiveForm.submit.click();
		token = 2;
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
			
			
	// 根据输入的条码查询
	$("input#searchGood").click(function() {
				var recordCode = $("#recordCode", $("table#searchgood")).val();
				if($("#tbody").html().indexOf($.trim(recordCode))>0){
				     return;
	 	         }
				$(":input#parms").val("parameter=" + recordCode);

				cx("parameter=" + recordCode, type);
			});
		  // ajax查询物品列表
	function cx(typeCN, type) {
//		if (notoken) {
//			alert("正在获取数据！请稍等");
//			return;
//		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/personalarchive/sajax_ea_getGoodsManage.jspa?type="
				+ type + "&";
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
					window.status = "数据加载成功";
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
				var tabletr = ""
				for (var i = 0; i < pageForm.list.length; i++) {
					var staffIDs = '"' + pageForm.list[i].staffID + '"';
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
							+ pageForm.list[i].staffID + " name='check'/></td>";
					tabletr += "<td id='staffCode' align='center'>"
							+ pageForm.list[i].staffCode + "</td>";
					tabletr += "<td id='recordCode'  align='center'>"
							+ pageForm.list[i].recordCode + "</td>";
					tabletr += "<td id='staffName'  align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='usedNmae' align='center'>"
							+ pageForm.list[i].usedNmae + "</td>";
					tabletr += "<td id='sex' align='center'>"
							+ pageForm.list[i].sex + "</td>";
					tabletr += "<td id='staffID' style='display:none' align='center'>"
							+ pageForm.list[i].staffID + "</td>";
					tabletr += "<td id='birthday'  align='center'>"
							+ pageForm.list[i].birthday + "</td>";
					tabletr += "<td id='nationality'  align='center'>"
							+ pageForm.list[i].nationality + "</td>";
					tabletr += "<td id='nativePlace'  align='center'>"
							+ pageForm.list[i].nativePlace + "</td>";
					tabletr += "<td id='nation'  align='center'>"
							+ pageForm.list[i].nation + "</td>";
					tabletr += "<td id='staffIdentityCard'  align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>";
					tabletr += "<td id='price'  align='center'><a  onclick='viewDetail("
							+ staffIDs + ")'>查看</a></td>";
					tabletr += " </tr>";
				}
				$("#tbody").append(tabletr);
				$("#body_02").show();
//				alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("获取物品出错！");
			}
		});
	}


	/** *******************************取得部门下拉************************************ */
	$(function() { // 获取凭证号
				var url = basePath
						+ "ea/cashierbills/sajax_ea_getBillID.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
					url : url,
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = "<%=basePath%>page/ea/not_login.jsp";
						}
						$("#journalNum").val(member.BillID);
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
			});
});

function re_load() {
	var url = "";
	url = basePath
			+ "/ea/personalarchive/ea_getListPersonalInfo.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&search=" + search + "&sdate="
			+ sdate + "&edate=" + edate + "&type=" + type;
	document.location.href = url;
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
			
			
  $("input.JQueryreturn1").click(function() {// 取消
						var formID = $($("#formID").attr("value"));
						$("#newccode").jqmHide();
						$(".jqmWindow", formID).jqmShow();
					});
}


/** **********************************选择物品**************************************** */
$(document).ready(function() {

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
				$(".jqmWindow", $("#goodsForm")).jqmShow();
				$("#loadcab")
						.attr(
								"src",
								basePath
										+ "page/ea/main/human/cstaff/loadActiveX.html?code="
										+ Math.random());
				$("#goodsForm #recordCode").val("");
				$("#tbody").html("");
				$(".scan").hide();
				$(".manual").show();
				$("#searchGood").hide();	
			});

	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN, type);
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
					cx(typeCN, type);
				} else {
					alert("已是尾页！");
				}
			});

	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa");
			});

	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					// 选中一行克隆一行
					select++;
					docNull += 1;
					// 克隆一行并修改文本框的name
					$("#kelong").before(

							$("#kelong").clone(true).attr("id",
									"kelong" + select).addClass("checkgoods"));
					// 修改input标签Id为goodsNomber的值
					// $("input#numbers", $("#kelong" + select)).attr("value",
					// select - 1);
					$("span#numbers", $("#kelong" + select)).text(select - 1);
					// // 修改当前行的所有input的name
					// $("#kelong" + select).find(':input').each(function() {
					// $(this).attr("name",
					// "goodsmap[" + select + "]." + this.name);
					//
					// });
					// $op = $("<option value='' selected>请选择</option>");

					// 遍历Id为$(this).val()的tr里说以的td
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "staffID") {
							$("input#staffID", $("#kelong" + select)).attr(
									"value", $(this).text());
						} else {
							$("span#" + this.id, $("#kelong" + select))
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
		
		loadcab.window.closePort();// 关闭读数据端口
		chipids = new Array();
        i = 0;
	});

	
	
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
					$se.attr("name", "financialBill.accountNum");
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
			contactcID = "";
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
			var contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
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
					$se.attr("name", "financialBill.userAccountNum");
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
					$("span#" + this.id, $("table#table5"))
							.text($(this).text());
					$("input#" + this.id, $("table#table5"))
							.val($(this).text());
				}
			});
			cuID = "";
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
			var relation = $("select#relation", $("table#searchuser")).val();
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
			var relation = $("select#relation", $("table#searchuser")).val();
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
							+ pageForm.list[i].referenceOrganization + "</td>";
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

// 查看详情
function viewDetail(staffID) {
	// alert(staffID);
	// var url = basePath +
	// "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
	// + staffID;
	// window.open(url);
	var url = basePath
			+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
			+ staffID;
	window.open(url, '', 'scrollbars=yes,resizable=yes,channelmode');

}


// ajax查询物品通过芯片

function QueryArchiveInfo(typeCN) {
	typeCN='"'+typeCN+'"';
	setTimeout("getArchive("+typeCN+")",1000);
}

function getArchive(typeCN){
	for (var i = 0; i < chipids.length; i++) {
	if (chipids[i].indexOf(typeCN)> -1) {
         return false;
	  }
	}
    chipids[i]=typeCN;
	typeCN = "parameter=" + typeCN;
	notoken = 1;
	var searchurl = basePath
			+ "ea/personalarchive/sajax_ea_QueryArchiveInfo.jspa?type=" + type
			+ "&";
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var staffinfo = member.staffinfo;
			if(staffinfo==null){
				return;
			}
			var tabletr = "";
			var staffIDs = '"' + staffinfo.staffID + '"';
			tabletr += "<tr style='cursor: hand;' id = "
					+ staffinfo.staffID + ">";
			tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
					+ staffinfo.staffID + " name='check'/></td>";
			tabletr += "<td id='staffCode' align='center'>"
					+ staffinfo.staffCode + "</td>";
			tabletr += "<td id='recordCode'  align='center'>"
					+ staffinfo.recordCode + "</td>";
			tabletr += "<td id='staffName'  align='center'>"
					+ staffinfo.staffName + "</td>";
			tabletr += "<td id='usedNmae' align='center'>"
					+ staffinfo.usedNmae + "</td>";
			tabletr += "<td id='sex' align='center'>" + staffinfo.sex
					+ "</td>";
			tabletr += "<td id='staffID' style='display:none' align='center'>"
					+staffinfo.staffID + "</td>";
			tabletr += "<td id='birthday'  align='center'>"
					+ staffinfo.birthday + "</td>";
			tabletr += "<td id='nationality'  align='center'>"
					+ staffinfo.nationality + "</td>";
			tabletr += "<td id='nativePlace'  align='center'>"
					+ staffinfo.nativePlace + "</td>";
			tabletr += "<td id='nation'  align='center'>"
					+ staffinfo.nation + "</td>";
			tabletr += "<td id='staffIdentityCard'  align='center'>"
					+ staffinfo.staffIdentityCard + "</td>";
			tabletr += "<td id='price'  align='center'><a  onclick='viewDetail("
					+ staffIDs + ")'>查看</a></td>";
			tabletr += " </tr>";
			$("#tbody").append(tabletr);
			$("#body_02").show();
			notoken = 0;
		},
		error : function cbf(data) {
			notoken = 0;
			alert("获取物品出错！");
		}
	
	});
	i++;
}
