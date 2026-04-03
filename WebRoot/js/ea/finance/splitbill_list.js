$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
		$(".jqmreturn").click(function() {
				notoken = 0;
				$("#documentsjqModel").jqmHide();
				$("#previewjqModel").jqmHide();
				$("#journalNumAjax").attr("value", "");
				$("#taxDateAjax").attr("value", "");
				showDocument = false;
			});
		
		var query ="<form method='post' name='SearchForm' id='SearchForm'><input type='submit' style='display:none;' name='submit' /><input type='hidden' name='search' value='search'/>" 
			+ (zz=='11'?'固定资产':zz=='12'?'资产增加':zz=='13'?'资产减少':zz=='14'?'预收款单':zz=='15'?'现收款单':zz=='16'?'销售订货':zz=='17'?'销售发货':zz=='18'?'销售退货':zz=='19'?'销售调拨':zz=='20'?'资产报损':zz=='09'?'应付工资':zz=='10'?'已付工资':zz=='00'?'现金收入':zz=='01'?'现金支出':zz=='02'?'银行收入':zz=='03'?'银行支出':zz=='04'?'应收账款':zz=='06'?'现金日记账':zz=='07'?'资金收入':zz=='08'?'资金支出':zz=='21'?'资金收支':'应付账款')+'列表'
			+ "<table  id='SearchTable'><tr>"
			+ "<td align='right'>&nbsp;&nbsp;项目名称：<input type='text' style=\"width: 100px\" id='xmname' name='cashierBillsVO.projectName'/>" 
			+ "&nbsp;&nbsp;黏贴单编号：<input id='journalNum' style=\"width: 100px\" name='cashierBillsVO.journalNum' />" 
			/*+ "&nbsp;&nbsp;部门：<select name='cashierBillsVO.departmentID' style=\"width: 110px\" id='departmentID'><option value=''>请选择</option></select>" */
			+ "&nbsp;&nbsp;单据类别：<select name='cashierBillsVO.billsType' style=\"width: 85px;hight: 50px\"><option value='' selected='selected'>全部</option>" 
		    + "<option value='收款单'>收款单</option><option value='支款单'>支款单</option><option value='销售明细单'>销售明细单</option>" 
			+ "<option value='项目收入预算单'>项目收入预算单</option><option value='欠款单'>欠款单</option></select>"
			+ "&nbsp;&nbsp;责任人：<input id='staffname' style=\"width: 50px\" name='cashierBillsVO.staffname' />" +
			/*"&nbsp;&nbsp;制单人：<input type='text' style=\"width: 50px\" id='inputs' name='cashierBillsVO.input'/>" +*/
			"&nbsp;&nbsp;制单日期：<input id='sdate' name='sdate' onfocus='\date(this);\' style='width: 85px' />至<input id='edate' name='edate' onfocus='\date(this);\' style='width: 85px' />" 
			
			+ "&nbsp;&nbsp;单据状态:<select name='cashierBillsVO.status' style=\"width: 85px;hight: 50px\"><option value='' selected='selected'>全部</option>" 
		    + "<option value='04'>审核中</option><option value='07'>已审核</option>" 
			+ "<option value='11'>驳回待修改</option><option value='09'>未确认收款</option><option value='45'>已收款</option><option value='46'>系统生成</option>" 
			+ "<option value='30'>未审核作废</option></select>"
			+ "&nbsp;&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></td>"
			+ "<tr><td></table>"
			/*+ "&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;margin-top:11px;\"  id='refress' value=' 刷新 '/></td></tr>";*/
			+ "</form>";
	$('.flexme11').flexigrid({
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '审核记录',
					bclass : 'mysearch',
					onpress : action
					// 当点击调用方法
				},{
					separator : true
				},{
					name : '确认收款',
					bclass : 'edit',
					onpress : action
					// 当点击调用方法
				},{
					separator : true
				},{
					name : '确定预算',
					bclass : 'edit',
					onpress : action
					// 当点击调用方法
				},{
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '打印预览',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '作废',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '费用报销准则',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				}]
			});
	$("div.bDiv",$("div.flexigrid")).css("height","500px");
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				window.open(basePath
						+ "/ea/splitbill/ea_toSaveCashierBills.jspa?cashierBills.cashierBillsID="+ cashierBillsID);
				break;
			case '审核记录':
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				var searchurl = basePath
				+ "ea/cashier/sajax_ea_AjxaGetCheckbill.jspa?cashierBillsID="+cashierBillsID+"&date="
							+ new Date().toLocaleString();
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
						var checkList = member.checkList;
						if(checkList!=null){
							$("#CheckTable").empty();
							var tabletr="<tr id='checkTitle'>" +
									"<th width='200' align='center'>审核阶段</th>" +
									"<th width='200' align='center'>审核时间</th>" +
									"<th width='70' align='center'>审核人</th>" +
									"<th width='123' align='center'>审核状态</th>" +
									"<th width='123' align='center'>审核意见</th>" +
									"</tr><tr class='shxx'>";
							for (var i = 0; i < checkList.length; i++) {
								var status=checkList[i].auditorstatus=='01'?'未审核':checkList[i].auditorstatus=='02'?'审核通过':'驳回';
								tabletr += "<td id='billtatus' align='center'>"
									+ checkList[i].billtatus + "</td>";
								tabletr += "<td id='audittime' align='center'>"
										+ checkList[i].audittime+"</td>";
								tabletr += "<td id='auditorname' align='center'>"
										+ checkList[i].auditorname + "</td>";
								tabletr += "<td id='auditorstatus' align='center'>"
										+ status + "</td>";
								tabletr += "<td id='comments' align='center'>"
										+ checkList[i].comments + "</td>";
								tabletr += " </tr>";
							}
							$("#CheckTable").append(tabletr);
							//alert("数据加载成功");
							notoken = 0;
							window.status = "数据加载成功";
							$("#jqModelCheck").jqmShow();
						}else{
							alert("该单据没有审核记录！");
						}					
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
				break;
			case '确认收款':
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				$form = $("#CashierBillsform");
				if($("tr#"+cashierBillsID).find("input#status").attr("value")=="09"){
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/splitbill/ea_confirmReceivables.jspa?search="
											+ search + "&search=" + search
											+ "&pagepageNumber=" + $("#pageNumber").attr("value")
											+ "&differenceStyle=" + differenceStyle+ "&zz=" + zz);
					$form.find("input#cashierBillsID").val(cashierBillsID);
					document.CashierBillsform.submit.click();
					cashierBillsID = "";
					token = 12;
				}else{
					alert("必须是待确认收款单据！");
				}
				break;
			case '确定预算':
				action("查看");
				break;
			case '打印预览' :
				var billsType="";
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				 billsType = $("tr#"+cashierBillsID).find("span#billsType").text();
				 if(billsType=="预入款单"){
				     $("#jqModelbill").jqmShow();
				 }else{
					 $("#jqModelpj").jqmShow();
				 }
				break;
			case '导出' :
				if(zz=='08'){
					$("#jqModelExcel").jqmShow();
				}else{
					url = basePath + "/ea/splitbill/ea_showExcel.jspa?search="
					+ search + "&sdate=" + sdate + "&edate=" + edate+ "&zz=" + zz;
					open(url);
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/splitbill/ea_getCashierBillsList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate + "&zz=" + zz;
				numback(url);
				break;
			case '费用报销准则' :
				var docPath = "upload_files/finance/fybxzz.doc";
			
				
				window.open(basePath
			  			+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
			  			+docPath+"&fileType=W&isRead=1&stage=准则");
				break;
			case '作废' :
			   if (cashierBillsID == "") {
					alert("请选择！");
					return;
			   }
			   if(confirm("是否继续操作？"))
			   if($.trim(status)=="草稿"||$.trim(status)=="待主管审核"){
					var url = basePath + "/ea/splitbill/sajax_ea_updateCashierBillsInvalid.jspa?date="
							+ new Date().toLocaleString()+"&cashierBills.cashierBillsID="+ cashierBillsID+ "&zz=" + zz;				
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
							$("tr#" + cashierBillsID).find("span#status").text("未审核作废");
							alert("操作成功！");
						},
						error : function cbf(data) {
							 alert("数据获取失败！");
						}
					});				
				}else{
					alert("此单据已“审核”或者“作废”！不可操作");
					return;
				}			
				break;	
		}
	}
	
	$("#printvsk").click(function(){
		var radioType=$("input[name='pj']:checked").val();		
		window.open(basePath
				+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
				+ cashierBillsID+"&radioType="+radioType);
	});
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				status = $("tr#" + cashierBillsID).find("span#status").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "/ea/splitbill/ea_toSearch.jspa?pageNumber="
						+ pNumber+ "&zz=" + zz);
		document.SearchForm.submit.click();
	});
	$("#tosearchExcel").click(function() {
		url = basePath + "/ea/splitbill/ea_showExcelzc.jspa?search="
			+ search + "&sdate=" + $("#sdates").val() + "&edate=" + $("#edates").val()+ "&zz=" + zz;
		open(url);
		$("#jqModelExcel").jqmHide();
	});
	
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
	});
	
	$("#printprev").click(function(){
		var billTypese = $("#selectbilltype").val();
		
		window.open(basePath
				+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
				+ cashierBillsID+"&billTypese="+billTypese);
	}
	);
	
	
//	var treeName =treeNames;
//	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
//			+ new Date().toLocaleString();
//	$.ajax({
//				url : encodeURI(url),
//				type : "get",
//				async : true,
//				dataType : "json",
//				success : function cbf(data) {
//					var member = eval("(" + data + ")");
//					var nologin = member.nologin;
//					if (nologin) {
//						document.location.href = basePath
//								+ "page/ea/not_login.jsp";
//					}
//					var oList = member.organizationlist;
//					var data = new Array();
//					data[0] = {
//						id : treeID,
//						pid : '-1',
//						text : treeName
//					};
//					for (var i = 0; i < oList.length; i++) {
//						data[i + 1] = {
//							id : oList[i].organizationID,
//							pid : oList[i].organizationPID,
//							text : oList[i].organizationName
//						};
//					}
//					$t = $("table#SearchTable");
//					var ts = new TreeSelector(
//							$t.find("select#departmentID")[0], data, -1);
//					ts.createTree();
//				},
//				error : function cbf(data) {
//					// alert("数据获取失败！")
//				}
//			});
	
	// 添加所选中的物品到物品单
	$("#qduser").click(function() {
		if ($("[name='gncheck']").is(':checked')) {
			window.open(basePath
					+ "/ea/splitbill/ea_toPageNum.jspa?gncheck="
					+ $('#gncheck:checked').val());
		} else {
			alert("请选择物品！");
		}
	});
	
	$(document).ready(function() {
		
		// 双击选中物品
		$("table#gouserstable tr[id]").live("click", function(event) {
			var b = $("input.ragood", $(this)).attr("checked");
			$("input.ragood", $(this)).attr("checked", !b);
		});
		
		// 单选框选中物品
		$(".ragood").live("click", function(event) {
			var b = $(this).attr("checked");
			$(this).attr("checked", !b);
		});
		
		$(".JQueryreturns").click(function() { 
			notoken = 0;
	 		$(".jqmWindow").jqmHide();
		});
		
	});

});

//ajax查询往来个人列表
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
			+ "/ea/splitbill/sajax_ea_getgoodsbillBygoodsnum.jspa?";
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
				$("#grsy").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#grxy").attr("title", dqy + 1);
			}
			$("span#grzycount").text(zys);
			var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>公司</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>部门</th><th align='center' bgcolor='#E4F1FA'>责任人</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编号</th><th align='center' bgcolor='#E4F1FA'>物品名称</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>类型</th><th align='center' bgcolor='#E4F1FA'>数量</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>单价</th><th align='center' bgcolor='#E4F1FA'>科目名称</th>";
				 for (var i = 0; i < pageForm.list.length; i++) {
					var arr=pageForm.list[i];
					tabletr += "<tr style='cursor: hand;' id = "
							+ arr[0] + ">";
					tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
							+ arr[0] + " name='gncheck' id='gncheck'/></td>";
					tabletr += "<td id='companyname' align='center'>"
							+(arr[1]==null?"":arr[1]) + "</td>";
					tabletr += "<td id='ORGANIZATIONNAME'  align='center'>"
							+ (arr[2]==null?"":arr[2]) + "</td>";
					tabletr += "<td id='STAFFNAME'  align='center'>"
							+ (arr[3]==null?"":arr[3]) + "</td>";
					tabletr += "<td id='goodsCoding' align='center'>"
							+ (arr[11]==null?"":arr[11]) + "</td>";
					tabletr += "<td id='goodsName'  align='center'>"
							+ (arr[12]==null?"":arr[12]) + "</td>";
					tabletr += "<td id='typeID' align='center'>"
							+ (arr[13]==null?"":arr[13]) + "</td>";
					tabletr += "<td id='QUANTITY'  align='center'>"
							+ (arr[4]==null?"":arr[4])+ "</td>";
					tabletr += "<td id='PRICE' align='center'>"
							+(arr[5]==null?"":arr[5]) + "</td>";
					tabletr += "<td id='subjectsName'  align='center'>"
							+ (arr[8]==null?"":arr[7]) + "</td>";
					tabletr += "<td id='defaultStorage'  align='center' style='display:none'>"
							+ arr[23] + "</td>";
					tabletr += "<td id='mnemonicCode' align='center' style='display:none'>"
							+ arr[21] + "</td>";
					tabletr += "<td id='model' align='center' style='display:none'>"
							+ arr[22]+ "</td>";
					tabletr += "<td id='acquiesceStandard' align='center' style='display:none'>"
							+ arr[19] + "</td>";
					tabletr += "<td id='goodsID'  align='center'  style='display:none'>"
							+ arr[13] + "</td>";
					tabletr += "<td id='standard' align='center' style='display:none'>"
							+ arr[20] + "</td>";
					tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>"
							+ arr[18] + "</td>";
					tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
							+ arr[29]+ "</td>";
					tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
							+ arr[31] + "</td>";
					tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
							+ arr[33]+ "</td>";
					tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
							+arr[35] + "</td>";
					tabletr += "<td id='goodsBillsID' title='ava' style='display:none' align='center'>"
						+arr[0] + "</td>";
					tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02cu").html(tabletr);
			$("#body_02cu").show();
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

function re_load() {
	var url = basePath
			+ "/ea/splitbill/ea_getCashierBillsList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&sdate=" + sdate + "&edate="
			+ edate+ "&zz=" + zz;
	document.location.href = encodeURI(url);
}
function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '草稿') {
		alert("只能修改草稿附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}

/** **********************************ajax扫描普通已审核单据**************************************** */
$(document).ready(function() {
	// 增加点击事件
	var cuID = "";
	$("table#gostable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	
	document.onkeydown = function(evt) {// 捕捉回车 根据激光扫描枪查询单据
		evt = (evt) ? evt : ((window.event) ? window.event : ""); // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		if (key == 13 && showDocument == true) { // 判断是否是回车事件与是否打开扫描窗口。
			$("input#searchdocument").trigger("click");
		}
	};
	// 根据输入的单据编号查询
	$("input#searchdocument").click(function() {
				var yy = true;
				var typeName = "";
				if ($.trim($("#journalNumAjax").attr("value")) == "") {
					yy = false;
				} else {
					typeName = $.trim($("#journalNumAjax").attr("value"));
					$("table#gostable").find("td#journalNum").each(function() {
								if ($.trim($(this).text()) == typeName) {
									$.messager.lays(200, 200);
									$.messager.anim('fade', 1000);
									$.messager.show("提示信息", "该编号单据已加载到列表中！",1000);
									yy = false;
								}
							});
				}
				if (yy) {
					cxwldw("parameter=" + typeName);
				}
			});
	$("#toTaxbutton").click(function(){
		var subRuleurl = basePath+ "ea/splitbill/sajax_ea_getbills.jspa?date="+ new Date().toLocaleString()+"&costSheetbill.csbID="+cuID+"&zz=" + zz;
		$.ajax({
				url : encodeURI(subRuleurl),
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath+ "page/ea/not_login.jsp";
					}
					var counts = member.counts;
					if(parseInt(counts)>0){
						alert("该项目已生成出纳单据！");
						notoken = 0;
						re_load();
					}else{
						if(confirm("确定生成？")){
							$("#myform").attr("target", "hidden").attr("action",basePath + "/ea/splitbill/ea_cashier.jspa?costSheetbill.csbID="+cuID+"&zz=" + zz);
							document.myform.submit.click();
							token = 12;
						  }
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
				});
	});
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var searchurl = basePath+ "ea/splitbill/sajax_ea_getAjaxCashList.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var costSheetbill = member.costSheetbill;
				if (costSheetbill == null) {
					alert("没有数据！");
					$("#taxDateAjax").attr("value", "");
					notoken = 0;
					return;
				}
			
				if (costSheetbill.billStatus == '00' || costSheetbill.billStatus == '01' || costSheetbill.billStatus == '10') {
					$("#taxDateAjax").attr("value", "");
					alert( "未审核通过，请添加其他单据！");
					notoken = 0;
					return;
				}
				if ($("#journalNumAjax").val() == "" || $("#journalNumAjax").val() == null) {
					$("#taxDateAjax").attr("value", "");
					alert("单据编号不能为空！");
					notoken = 0;
				}
				var tabletr = "<tr style='cursor: hand;'  id = "+ costSheetbill.csbID + ">";
				tabletr += "<td id='checkcc'align='center'><input type ='radio'class='rauser'value="
						+ costSheetbill.csbID + " name='check'/></td>";
				tabletr += "<td id='companyName' align='center'>"
						+ costSheetbill.companyName + "</td>";
				tabletr += "<td id='departmentName' align='center'>"
						+ costSheetbill.departmentName + "</td>";
				tabletr += "<td id='journalNum' align='center'>"
						+ costSheetbill.journalNum + "</td>";
				tabletr += "<td id='projectName'  align='center'>"
						+ costSheetbill.projectName + "</td>";
				tabletr += "<td id='startTime'  align='center'>"
						+ costSheetbill.startTime + "</td>";
				tabletr += "<td id='endTime'  align='center'>"
						+ costSheetbill.endTime + "</td>";
				tabletr += "<td id='costdescription'  align='center'>"
						+ costSheetbill.costdescription + "</td>";
				tabletr += "<td id='companyBankNum'  align='center'>"
						+ costSheetbill.companyBankNum + "</td>";
				tabletr += "<td id='staffName' align='center'>"
						+ costSheetbill.staffName + "</td>";
				tabletr += "<td id='cstaffName' align='center'>"
						+ costSheetbill.cstaffName + "</td>";
				tabletr += "<td id='cstaffRelationship'  align='center'>"
						+ costSheetbill.cstaffRelationship + "</td>";
				tabletr += "<td id='ccompanyName'  align='center'>"
						+ costSheetbill.ccompanyName + "</td>";
				tabletr += "<td id='ccompanyRelationship'  align='center'>"
						+ costSheetbill.ccompanyRelationship + "</td>";
				tabletr += "<td id='billsDate'  align='center'>"
						+ costSheetbill.billsDate + "</td>";
				tabletr += "<td id='billStaffName'  align='center'>"
						+ costSheetbill.billStaffName + "</td>";
				tabletr += "<td id='csreviewedName'  align='center'>"
						+ costSheetbill.csreviewedName + "</td>";
				tabletr += " </tr>";
				//2013081609120100002
				$("#body_08cu table#gostable").append(tabletr);
				$.messager.lays(200, 200);
				$.messager.anim('fade', 1000);
				$.messager.show("提示信息", "数据加载成功！", 1000);
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


