// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;
var subtype="";//单击的按钮是保存还是提交审核
$(function() {
	//修改加载页面
	if (cashierBillsID != "") {
			var URL = basePath
					+ "ea/purchase1/sajax_ea_getListcashierByID.jspa?cashierBillsVO.cashierBillsID="
					+ cashierBillsID + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(URL),
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
					var goodsList = member.goodsList;
					for (var i = 0; i < goodsList.length; i++) {
					select++;
					var type=goodsList[i].identifyingCode;
					if(type=="00"){
					   // 克隆一行并修改文本框的name
					$("#kelongold").before(
							$("#kelongold").clone(true).attr("id",
									"kelongold" + select).addClass("checkgoods"));
				    // 修改input标签Id为goodsNomber的值（序号列）
					$("input#goodsNomber", $("#kelongold" + select)).attr("value",
							select);
					// 修改当前行的所有input的name
					$("#kelongold" + select).find(':input').each(function() {
						$(this).attr("name",
								"goodsbillmap[" + select + "]." + this.name);
					});
					// 遍历Id为$(this).val()的tr里所有的td
					$("tr#kelongold" + select).show();
					$("input#oldgoodsName", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].goodsName);
					$("input#oldgoodsID", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].goodsID);
					$("input#oldprice", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].price);
					$("input#oldquantity", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].quantity);
					$("input#oldunit", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].goodsVariableID);
					$("input#money", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].money);
					//科目
					$("input#oldsubjectsID", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].subjectsID);
					$("input#oldtosubjects", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].subjectsName);
					//供应商
					$("input#oldccompanyID", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].ccompanyID);
					$("input#oldccompanyName", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].ccompanyname);
					//备注
					$("input#oldremindedContent", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].remindedContent);	
					}else if(type=="01"){
						      // 克隆一行并修改文本框的name
						$("#kelong").before(
								$("#kelong").clone(true).attr("id",
										"kelong" + select).addClass("checkgoods"));
					    // 修改input标签Id为goodsNomber的值（序号列）
						$("input#goodsNomber", $("#kelong" + select)).attr("value",
								select);
						// 修改当前行的所有input的name
						$("#kelong" + select).find(':input').each(function() {
							$(this).attr("name",
									"goodsbillmap[" + select + "]." + this.name);
						});
						// 遍历Id为$(this).val()的tr里所有的td
						$("tr#kelong" + select).show();
						$("input#goodsName", $("tr#kelong" + select)).attr(
										"value",goodsList[i].goodsName);
						$("input#goodsID", $("tr#kelong" + select)).attr(
										"value",goodsList[i].goodsID);
						$("input#price", $("tr#kelong" + select)).attr(
										"value",goodsList[i].price);
						$("input#quantity", $("tr#kelong" + select)).attr(
										"value",goodsList[i].quantity);
						$("input#unit", $("tr#kelong" + select)).attr(
										"value",goodsList[i].goodsVariableID);
						$("input#money", $("tr#kelong" + select)).attr(
										"value",goodsList[i].money);
						//科目
						$("input#subjectsID", $("tr#kelong" + select)).attr(
										"value",goodsList[i].subjectsID);
						$("input#tosubjects", $("tr#kelong" + select)).attr(
										"value",goodsList[i].subjectsName);
						//供应商
					    $("input#ccompanyID", $("tr#kelong" + select)).attr(
									"value",goodsList[i].ccompanyID);
					    $("input#ccompanyname", $("tr#kelong" + select)).attr(
									"value",goodsList[i].ccompanyname);
						//备注
						$("input#remindedContent", $("tr#kelong" + select)).attr(
										"value",goodsList[i].remindedContent);
					}
					
				 }
				//计算金额合计
				var heji=0;
				$("table .checkgoods").each(function(){
					var s=$(this).find("input#money").val();
					var x=parseFloat(s);
					if(!isNaN(x)){
					heji=heji+x
					}
				});
				DX(heji);
				$("input#countmoney").val(heji);
				$("input#goodsmoney").val(heji);
				 //保存和提交审核功能释放
				//$(".addline").attr("disabled",true).addClass("grey");
				 $("input.disabled").attr("disabled",false).removeClass("grey");
				 
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
	}
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				//loadcab.window.closePort();// 关闭读数据端口
				chipids = new Array();
                i = 0;
			});
	$(".JQueryClose").click(function() {
		    if(confirm("确定要关闭添加窗口？")){
		    	window.close();
				//re_load();
		     }
			});
	// 计算金额合计和金额大写
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
		var heji=0;
		$("table .checkgoods").each(function(){
			var s=$(this).find("input#money").val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("input#countmoney").val(heji);
		$("input#goodsmoney").val(heji);
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
		var heji=0;
		$("table .checkgoods").each(function(){
			var s=$(this).find("input#money").val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("input#countmoney").val(heji);
		$("input#goodsmoney").val(heji);
		
	});
	// 克隆的商品删除
	$(".klsc").click(function() {
				$(this).parent().parent().remove();
				docNull -= 1;
				//计算金额合计
				var heji1=0;
				$("table .checkgoods").each(function(){
					var s=$(this).find("input#money").val();
					var x=parseFloat(s);
					if(!isNaN(x)){
					heji1=heji1+x
					}
				});
				$("input#countmoney").val(heji1);
				$("input#goodsmoney").val(heji1);
				DX(heji1);
			});
    //打印预览
	$("input#JQueryprint").click(function() {
		window.open(basePath + "/ea/purchase1/ea_toPrintPurchase.jspa?cashierBills.cashierBillsID="+cashierBillsID);
	});
	// 保存归档
	$("input.JQuerySubmitgd").click(function() {
		var buttonname=$(this).val();
		if(buttonname=="保存"){
		  subtype="save";
		}else if(buttonname=="提交审核"){
		  subtype="savecheck";
		}
		$(".notnull").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		if (docNull == 0) {
			alert("必须添加预算项目");
			notoken = 0;
			return;
		}
        //提交审核如果没有选择物品将提示请选择物品
		var trlength=$("#goodtable").find("tr").length;
		if (trlength == 2) {
			alert("请添加采购物品");
			notoken = 0;
			return;
		}
		var re=0;
		$(".panduan",$(".checkgoods")).each(function(){
			if (this.value==""){
				$(this).css("background-color","red");
				$(this).attr("class","notnull");
				re=1;
			}
		});
		$(".put3", $("tr.checkgoods")).trigger("blur");
		if ($("form .error").length){
         	re=1;
        }
        if(re){
			alert("请填完所有必填项");
			notoken = 1;
			return;
		}
		if ($("form#PurchaseForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		if (cashierBillsID == "") {
			$("#PurchaseForm").attr("target", "hidden").attr(
					"action",
					basePath + "/ea/purchase1/ea_savePurchase.jspa?pageNumber="
							+ pNumber+"&subtype="+subtype);
			document.PurchaseForm.submit.click();
			document.PurchaseForm.reset();
			//$("select#contactConnections", $("table#table4")).hide();//往来单位的选择往来关系
			//$("select#aNum", $("table#table4")).hide();//往来单位的选择银行账号
			//$("select#phone", $("table#table5")).hide();//往来个人电话选择
			//$("select#userNum", $("table#table5")).hide();//往来个人银行账号选择
			$("#cashierstatus").attr("value", "00");//采购物品状态：00 未收货
			$("tr.checkgoods").remove();
			$("input#countmoney").val(0);
			$("span#0").text("");
			$("span#1").text("");
			$("span#2").text("");
			$("span#3").text("");
			$("span#4").text("");
			$("span#5").text("");
			$("span#6").text("");
			$("input#goodsmoney").val("");
			token = 2;
			return;
		}
		$("#PurchaseForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/purchase1/ea_editPurchase.jspa?pageNumber="
						+ pNumber+"&subtype="+subtype);
		document.PurchaseForm.submit.click();
		token = 2;
		
	});
	/** **************************************科目管理******************************************* */
	var subjectsNumber = "";
	var subjectsName = "";
	$("input#tosubjects").click(function() {
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
			$("#tosubjects", $(".receivesubjects")).attr("value",
					subjectsName);
		} else {
			$("#subjectsID", $(".receivesubjects")).attr("value", "");
			$("#tosubjects", $(".receivesubjects")).attr("value", "");
		}
		$("#PurchaseForm").find("td.receivesubjects")
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

	/** ************************************************************** */
});

function re_load() {
	if(subtype=="save"){
	   window.location.reload();//刷新当前页面
	}
	if(subtype=="savecheck"){
	   var url = basePath+ "/ea/purchase1/ea_toPurchase.jspa?pageNumber="
				+ pNumber + "&cashierBills.cashierBillsID="
			    + cashierBillsID;
	   document.location.href = url;//跳转到查看页面
	}
	/*var url = basePath + "/ea/purchase1/ea_getPurchaseList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&type=00" + "&search=" + search
			+ "&sdate=" + sdate + "&edate=" + edate;
	document.location.href = url;*/
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



/************************************选择物品*****************************************/
$(document).ready(function() {
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
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
				$(":input#parmss").val("typeID=" + treename);
				cxs("typeID=" + treename);
				return;
			}
		}
	});
	
	
	
	
	// 双击选中物品
	$("table#gotables tr[id]").live("click", function(event) {
		var b = $("input.ragoods", $(this)).attr("checked");
		$("input.ragoods", $(this)).attr("checked", !b);
	});
	
	// 复选框选中物品
	$(".ragoods").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	// 导入数据（选择物品）
	var kelongid="";
	$("#goodsName").click(function() {
		kelongid=$(this).parent().parent().attr("id");
		$("#body_03").html("");
		$("#selectType").val("goods");
		$("#goodsForms").Show();
	});
	$("a#goodsName").click(function() {
		$("#body_03").html("");
		$("#selectType").val("goods");
		$(".jqmWindow", $("#goodsForms")).jqmShow();
	});
	
	// 上一页
	$("#wpsy_1").click(function() {
		var sy = $("#wpsy_1").attr("title");
		if (sy != 0) {
			var typeName = $(":input#parmss").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + sy;
			cxs(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	
	// 下一页
	$("#wpxy_1").click(function() {
		var xy = $("#wpxy_1").attr("title");
		if (xy != 0) {
			var typeName = $(":input#parmss").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + xy;
			cxs(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	
	// 新增物品
	$(".xzwp").click(function() {
		window.open(basePath
				+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
	});
	// 增加物品行
	var kelongtr="";
	$("#addgoodtr").click(function() {
					// 选中一行克隆一行
					select++;
					docNull += 1;//克隆行序号
					// 克隆一行并修改文本框的name
					$("#kelong").before(
							$("#kelong").clone(true).attr("id",
									"kelong" + select).addClass("checkgoods"));
					kelongtr="kelong" + select;
					// 修改input标签Id为goodsNomber的值（序号列）
					$("input#goodsNomber", $("#kelong" + select)).attr("value",
							select);
					// 修改当前行的所有input的name
					$("#kelong" + select).find(':input').each(function() {
						$(this).attr("name",
								"goodsbillmap[" + select + "]." + this.name);
					});
					// 当前行Id为goodsVariableID的select标签后追加$op变量（单位增加请选择）
					$op = $("<option value='' selected>请选择</option>");
					$("select#unit", $("#kelong" + select)).append($op);
					
					// 遍历Id为$(this).val()的tr里所有的td
					$("tr#kelong" + select).show();
					$(this).attr("checked", false);
	});
	//删除克隆的行
	$("#delgoodtr").click(function() {
		var trhtml=$("tr#" + kelongtr).html();
		//判断增加行的数据值有几个
		var a=0;
		$("tr#" + kelongtr).find("input[type='text']").each(function(){
      		var value=$(this).val();
      		if(value!=""){
      		  a++;
      		}
        });
		if(trhtml!=null){
			if(a>1){
			   if(window.confirm('删除行存在数据，删除后数据不可恢复，是否进行删除操作？')){
                 //alert("确定");
		     	 $("tr#" + kelongtr).remove();
                 return true;
              }else{
                 //alert("取消");
                 return false;
              }
			}
			else{
			   $("tr#" + kelongtr).remove();
			}
		     
		}else{
		   alert("新增行已经删除！");
		}
		
	});
	// 添加所选中的物品到物品单
	$("#selectGoods").click(function() {
		//判断选择物品
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "goodsID") {
							$("input#goodsID", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						else if (this.id == "goodsName") {
							$("input#goodsName", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						/*else if (this.id == "subjectsName") {
							$("input#tosubjects", $("#" + kelongid))
									.attr("value", $(this).text());
						}*/ else if (this.id == "subjectsID") {
							$("input#subjectsID", $("#" + kelongid)).attr(
									"value", $(this).text());
						} else if (this.id == "quantity") {
							$("input#quantity", $("#" + kelongid)).attr(
									"value", $(this).text());
						} else if (this.id == "price") {
							$("input#price", $("#" + kelongid)).attr(
									"value", $(this).text());
						} else if (this.id == "money") {
							$("input#money", $("#" + kelongid)).attr(
									"value", $(this).text());
						} else if (this.id == "appropriationmoney") {
							$("input#appropriationmoney", $("#" + kelongid)).attr(
									"value", $(this).text());
						} else if (this.id == "cashApplyBillsID") {
							$("input#cashApplyBillsID", $("#" + kelongid)).attr(
									"value", $(this).text());
						}else if (this.id == "unit") {
							$("input#unit", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						else {
							$("span#" + this.id, $("#" + kelongid))
									.text($(this).text());
						}
					});
					$("tr#" + kelongid).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#goodsForms")).jqmHide();
		} else {
			alert("请选择物品！");
		}
		
		//loadcab.window.closePort();// 关闭读数据端口
		chipids = new Array();
        i = 0;
	});
	/*//保存/编辑处理
	$("a#baocun").click(function() {
		var a=$(this).parent().parent().parent().attr("id");
		$("tr#" + a).children("td").each(function() {
		
		});
		alert(a);
	});*/
	// 根据输入的物品编号或物品名称查询
	$("input#searchGoods").click(function() {
		var typeName = $("#typeID", $("table#searchgoods")).val();
		$(":input#parmss").val("parameter=" + typeName);
		cxs("parameter=" + typeName);
	});
	
	// ajax查询物品列表
	function cxs(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy_1").attr("title", 0);
		$("#wpxy_1").attr("title", 0);
		$("#wpzy_1").attr("title", 0);
		var searchurl = basePath
				+ "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
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
					$("#wpsy_1").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy_1").attr("title", dqy + 1);
				}
				//‘
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotables' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'><input type ='checkbox' id='checkAll'/>全选</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th>" +
						"<th align='center' bgcolor='#E4F1FA'>统一分类条码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>类型</th>" +
						"<th align='center' bgcolor='#E4F1FA'>品牌</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>库存量</th>" +
						"<th align='center' bgcolor='#E4F1FA'>单位</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th>" +
						"<th align='center' bgcolor='#E4F1FA'>型号</th>" +
						"<th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
			    var types="checkbox";
				if($("#selectType").val()=="projects"){
					
					types = "radio";
				}
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].goodsID + ">";
					tabletr += "<td id='check' align='center'><input type ='"+types+"' class='ragoods' value="
							+ pageForm.list[i].goodsID + " name='check'/></td>";
					tabletr += "<td id='goodsNum' align='center'>"
							+ pageForm.list[i].goodsCoding + "</td>";
					tabletr += "<td id='goodsName'  align='center'>"
							+ pageForm.list[i].goodsName + "</td>";
					tabletr += "<td id='sortCode'  align='center'>"
							+ pageForm.list[i].defaultStorage + "</td>";					
					tabletr += "<td id='type' align='center'>"
							+ pageForm.list[i].typeID + "</td>";
					tabletr += "<td id='brand' align='center'>"
							+ pageForm.list[i].mnemonicCode + "</td>";					
					tabletr += "<td id='variableID'  align='center'>"
							+ "</td>";
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='unit' title='ava' align='center'>"
							+ pageForm.list[i].variableID + "</td>";
					tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable1ID + "</td>";
					tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable2ID + "</td>";
					tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable3ID + "</td>";
					tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable4ID + "</td>";
					tabletr += "<td id='acquiesceStandard' align='center'>"
							+ pageForm.list[i].acquiesceStandard + "</td>";
					tabletr += "<td id='modelNumber' align='center'>"
							+ pageForm.list[i].model + "</td>";
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
						+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='standard' align='center'>"
						+ pageForm.list[i].standard + "</td>";
					tabletr += "<td id='subjectsName' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsName + "</td>";
					tabletr += "<td id='subjectsID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_03").html(tabletr);
				$("#body_03").show();
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
/** **********************************关联票据**************************************** */
$(document).ready(function() {
	var cashierbillID = "";//已经选中的票据id
	$("table#gostableca tr[id]").live("click", function(event) {
				cashierbillID = this.id;
				$("input.raca", $(this)).attr("checked", "checked");
			});

	//关联票据
	$(".JQueryRelation").click(function() {
		        var id=$("input#cashierBillsID").val();
		        if(id!=""){
		            if (!confirm("已经关联票据，不能继续关联，如果关联其它票据，已关联票据数据会丢失，是否关联票据？")) {
                        return;
                    }else{
                       $("input.disabled").attr("disabled",true).addClass("grey");
                       $("input#cashierBillsID").val("");
                       $("tr.checkgoods").remove();
                       DX(0);
				       $("input#countmoney").val(0);
				       $("input#goodsmoney").val(0);
                       $("input#journalNum", $("table#searchcashier")).val("");
				       $(".jqmWindow", $("#selectcashierForm")).jqmShow();
				       cxcndj("cashierBillsVO.journalNum=");
                    }
		        }
		        $("input#journalNum", $("table#searchcashier")).val("");
				$(".jqmWindow", $("#selectcashierForm")).jqmShow();
				cxcndj("cashierBillsVO.journalNum=");
			});
	
	// 关联所选中票据
	$("#qdcashier").click(function() {
		if (cashierbillID != "") {
			$("input#cashierBillsID").val(cashierbillID);
			var URL = basePath
					+ "ea/purchase1/sajax_ea_getListcashierByID.jspa?cashierBillsVO.cashierBillsID="
					+ cashierbillID + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(URL),
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
					var goodsList = member.goodsList;
					for (var i = 0; i < goodsList.length; i++) {
					select++;
				    // 克隆一行并修改文本框的name
					$("#kelongold").before(
							$("#kelongold").clone(true).attr("id",
									"kelongold" + select).addClass("checkgoods"));
				    // 修改input标签Id为goodsNomber的值（序号列）
					$("input#goodsNomber", $("#kelongold" + select)).attr("value",
							select);
					// 修改当前行的所有input的name
					$("#kelongold" + select).find(':input').each(function() {
						$(this).attr("name",
								"goodsbillmap[" + select + "]." + this.name);
					});
					// 遍历Id为$(this).val()的tr里所有的td
					$("tr#kelongold" + select).show();
					$("input#oldgoodsName", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].goodsName);
					$("input#oldgoodsID", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].goodsID);
					$("input#oldprice", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].price);
					$("input#oldquantity", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].quantity);
					$("input#oldunit", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].variableID);
					$("input#money", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].money);
					//科目
					$("input#oldsubjectsID", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].subjectsID);
					$("input#oldtosubjects", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].subjectsName);
					//备注
					$("input#oldremindedContent", $("tr#kelongold" + select)).attr(
									"value",goodsList[i].remindedContent);	
				 }
				//计算金额合计
				var heji=0;
				$("table .checkgoods").each(function(){
					var s=$(this).find("input#money").val();
					var x=parseFloat(s);
					if(!isNaN(x)){
					heji=heji+x
					}
				});
				DX(heji);
				$("input#countmoney").val(heji);
				$("input#goodsmoney").val(heji);
				 //保存和提交审核功能释放
				 $("input.disabled").attr("disabled",false).removeClass("grey");
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
			cashierbillID = "";
			$(".jqmWindow", $("#selectcashierForm")).jqmHide();
		} else {
			alert("请选择关联的票据！");
		}
	});
	// 根据输入的黏贴单编号查询
	$("input#searchca").click(function() {
		var journalNum = $("input#journalNum", $("table#searchcashier")).val();
		cxcndj("cashierBillsVO.journalNum=" + journalNum);
	});
	// 上一页
	$("#dwsyca").click(function() {
		var sy = $("#dwsyca").attr("title");
		if (sy != 0) {
			var journalNum = $("input#journalNum", $("table#searchcashier")).val();
			var typeCN = "cashierBillsVO.journalNum=" + journalNum
					+ "&pageForm.pageNumber=" + sy;
			cxcndj(typeCN);
		} else {
			alert("已是首页！");
		}
	});

	// 下一页
	$("#dwxyca").click(function() {
		var xy = $("#dwxyca").attr("title");
		if (xy != 0) {
			var journalNum = $("input#journalNum", $("table#searchcashier")).val();
			var typeCN = "cashierBillsVO.journalNum=" + journalNum
					+ "&pageForm.pageNumber=" + xy;
			cxcndj(typeCN);
		} else {
			alert("已是尾页！");
		}
	});

	// ajax查询列表
	function cxcndj(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		//notoken = 1;
		$("#dwsyca").attr("title", 0);
		$("#dwxyca").attr("title", 0);
		$("#dwzyca").attr("title", 0);
		var searchurl = basePath
				+ "ea/purchase1/sajax_ea_getListcashierByjournalNum.jspa?";
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
					$("#dwsyca").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#dwxyca").attr("title", dqy + 1);
				}
				$("span#zycountca").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择票据</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostableca' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += "<tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>公司名称</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>黏贴单编号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>单据类别</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>单据状态</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>部门</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>责任人</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>制单日期</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					var status=pageForm.list[i].status;
					if(status=="07")
					status="已三审通过";
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].cashierBillsID + ">";
					tabletr += "<td id='checkcc' align='center'>" +
							"<input type ='radio'  class='raca' value="+ pageForm.list[i].cashierBillsID
							+ " name='checkradioca'/></td>";
					tabletr += "<td id='companyname' align='center'>"
							+ pageForm.list[i].companyname + "</td>";
					tabletr += "<td id='journalNum' align='center'>"
							+ pageForm.list[i].journalNum + "</td>";
					tabletr += "<td id='billsType' align='center'>"
							+ pageForm.list[i].billsType + "</td>";
					tabletr += "<td id='status' style='display:none' align='center'>"
							+ pageForm.list[i].status + "</td>";
					tabletr += "<td id='statusname' align='center'>"
							+ status + "</td>";
					tabletr += "<td id='departmentname' align='center'>"
							+ pageForm.list[i].departmentname + "</td>";
					tabletr += "<td id='staffname' align='center'>"
							+ pageForm.list[i].staffname + "</td>";
					tabletr += "<td id='cashierDate'  align='center'>"
							+ pageForm.list[i].cashierDate + "</td>";
					tabletr += "<td id='cashierBillsID' style='display:none' align='center'>"
							+ pageForm.list[i].cashierBillsID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02ca").html(tabletr);
				$("#body_02ca").show();
				// alert("数据加载成功");
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
/** **********************************往来单位(供货商)**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});

	// 选择往来单位
	$("input#ccompanyname").click(function() {
		        $("input#inputname", $("table#searchcompany")).val($(this).attr("name"));
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
		var inputname=$("input#inputname", $("table#searchcompany")).val();
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
					$se.append("<option selected='selected' value = ''>--请选择--</option>");
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
					$("input[name='"+inputname.split(".")[0]+".ccompanyID']")
							.val($(this).text());
				} else if (this.id == "ccompanyname") {
					$("input[name='"+inputname+"']")
							.val($(this).text());
				}else if (this.id == "companyTel") {
					$("input[name='"+inputname.split(".")[0]+".ccompanyTel']", $("table#table3"))
							.val($(this).text());
				}else if (this.id == "contactConnections") {
					$("select#contactConnections option[value="
									+ $(this).text() + "]", $("table#table4"))
							.remove();
					$("select#contactConnections", $("table#table4"))
							.append("<option selected='selected' value = "
									+ $(this).text() + ">" + $(this).text()
									+ "</option>").show();
					$("span#contactConnections", $("table#table4")).hide();
				} else {
					/*$("span#" + this.id, $("table#table3"))
							.text($(this).text());
					$("input#" + this.id, $("table#table3"))
							.val($(this).text());*/
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
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>单位名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>业务关系</th>";
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
				// alert("数据加载成功");
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
/** **********************************往来个人**************************************** */
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
				// alert("数据加载成功");
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


function QueryArchiveInfo(typeCN) {
	typeCN = '"' + typeCN + '"';
	setTimeout("getArchive(" + typeCN + ")", 1000);
}

function getArchive(typeCN) {
	for ( var i = 0; i < chipids.length; i++) {
		if (chipids[i].indexOf(typeCN) > -1) {
			return false;
		}
	}
	chipids[i] = typeCN;
	typeCN = "parameter=" + typeCN;
	notoken = 1;
	var searchurl = basePath
			+ "ea/cashierbills/sajax_ea_QueryArchiveInfo.jspa?";

	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var goodinfo = member.goodinfo;
			if (goodinfo == null) {
				return;
			}
			var tabletr = "";
			tabletr += "<tr style='cursor: hand;' id = " + goodinfo.goodsID
					+ ">";
			tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
					+ goodinfo.goodsID + " name='check'/></td>";
			tabletr += "<td id='goodsNum' align='center'>"
					+ goodinfo.goodsCoding + "</td>";
			tabletr += "<td id='goodsName'  align='center'>"
					+ goodinfo.goodsName + "</td>";
			tabletr += "<td id='sortCode'  align='center'>"
					+ goodinfo.defaultStorage + "</td>";
			tabletr += "<td id='type' align='center'>" + goodinfo.typeID
					+ "</td>";
			tabletr += "<td id='brand' align='center'>" + goodinfo.mnemonicCode
					+ "</td>";
			tabletr += "<td id='variableID'  align='center'>" + "</td>";
			tabletr += "<td id='goodsID' style='display:none' align='center'>"
					+ goodinfo.goodsID + "</td>";
			tabletr += "<td id='unit' title='ava' align='center'>"
					+ goodinfo.variableID + "</td>";
			tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
					+ goodinfo.variable1ID + "</td>";
			tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
					+ goodinfo.variable2ID + "</td>";
			tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
					+ goodinfo.variable3ID + "</td>";
			tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
					+ goodinfo.variable4ID + "</td>";
			tabletr += "<td id='acquiesceStandard' align='center'>"
					+ goodinfo.acquiesceStandard + "</td>";
			tabletr += "<td id='modelNumber' align='center'>" + goodinfo.model
					+ "</td>";
			tabletr += "<td id='subjectsName' title='ava' style='display:none' align='center'>"
					+ goodinfo.subjectsName + "</td>";
			tabletr += "<td id='subjectsID' title='ava' style='display:none' align='center'>"
					+ goodinfo.subjectsID + "</td>";
			tabletr += " </tr>";
			$("#tbodya").append(tabletr);
			$("#body_02").show();
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("获取物品出错！");
		}

	});
	i++;
}
//选择物品复选框全选
$("input#checkAll").live("click",function(){ //全选
    if($(this).attr("checked")){
    	$("#goodsForms").find("input[type='checkbox']").each(function(){
      		$(this).attr("checked",true);
        });
    }else{
    	$("#goodsForms").find("input[type='checkbox']").each(function(){
        	$(this).attr("checked",false);
        });
    }
});
//金额大写
function DX(n) {
       if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
           return "数据非法";
           var unit = "千百拾亿千百拾万千百拾元角分", str = "";
           n += "00";
       var p = n.indexOf('.');
       if (p >= 0)
           n = n.substring(0, p) + n.substr(p+1, 2);
           unit = unit.substr(unit.length - n.length);
           var j=n.length-1;
       for (var i=0; i < n.length; i++)
       {
             if(j>=6)
             {
               str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
               var mm=  str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").
                replace(/(亿)万|壹(拾)/g, "$1$2");
                j--;
               if(j==5)
               {
                 mm=mm.replace("万",""); 
                 $("span#6").text(mm);
               }
                
             }else
             {  
                $("span#"+(n.length)).text('￥');
                $("span#"+j).text('零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)));
                j=j-1;
             }
                
       }
}