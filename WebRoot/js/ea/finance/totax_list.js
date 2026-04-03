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

	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "单据报税管理",
				minheight : 80,
				buttons : [{
					name : '添加新单据',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '添加已有单据',
					bclass : 'add',
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
					name : '查看',
					bclass : 'edit',
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
					name : '查询',
					bclass : 'mysearch',
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
			case '添加新单据' :
				document.location.href = basePath
						+ "ea/dutiable/ea_toSaveCashierBills.jspa?pageNumber="
						+ pNumber + "&differenceStyle=" + differenceStyle
						+ "&BType=" + bill;
				break;
			case '添加已有单据' :
				$("#documentsjqModel").jqmShow();
				showDocument = true;
				break;
			case '删除' :
				$form = $("#CashierBillsform");
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				if (taxstatus != "草稿") {
					alert("不能删除！");
					return;
				}
				if (confirm("确定删除？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/dutiable/ea_delCashierBills.jspa?search="
											+ search + "&pageNumber=" + pNumber);
					$form.find("input#cashierBillsID").val(cashierBillsID);
					document.CashierBillsform.submit.click();
					$("tr#" + cashierBillsID).remove();
					cashierBillsID = "";
					token = 11;
				}
				break;
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "ea/dutiable/ea_toCashier.jspa?pageNumber=" + pNumber
						+ "&cashierBills.cashierBillsID=" + cashierBillsID
						+ "&sdate=" + sdate + "&edate=" + edate + "&search="
						+ search + "&tsdate=" + tsdate + "&tedate=" + tedate
						+ "&taxstatusDu=" + taxstatusDu;
				break;
			case '导出' :
				url = basePath + "ea/dutiable/ea_showExcel.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate
						+ "&tsdate=" + tsdate + "&tedate=" + tedate
						+ "&taxstatusDu=" + taxstatusDu;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/dutiable/ea_getDutiableList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate
						+ "&tsdate=" + tsdate + "&tedate=" + tedate
						+ "&taxstatusDu=" + taxstatusDu;
				numback(url);
				break;
			case '查询' :

				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
		}
	}
	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				taxstatus = $("tr#" + cashierBillsID).find("span#taxstatus")
						.text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	// 查询按钮单击事件
	$("#tosearch").click(function() {
		var opt=$("#btype").find("option:selected").text();
		if(opt=="请选择"){
			$("#btype").find("option:selected").attr("value","");
		}else{
			var leng=opt.length;
			var num=$("#btype").find("option:selected").text().indexOf("├");
			$("#btype").find("option:selected").attr("value",$("#btype").find("option:selected").text().substr(num+1,leng));
		}
		$("#SearchForm")
				.attr(
						"action",
						basePath + "ea/dutiable/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	// "添加为报税单据"按钮的单击事件
	$("#toTaxbutton").click(function() {
		if (cashierBillsID == "") {
			alert("请选择");
			return;
		}
		if ($("form#myform").find("input#taxDateAjax").val() == "") {
			alert("报税日期不能为空!\t\n");
			$("input#taxDate").focus();
			return;
		}
		if (confirm("确定转为报税单据？")) {

			$("#myform").attr("target", "hidden").attr("action",
					basePath + "/ea/dutiable/ea_taxUpdateZero.jspa");

			document.myform.submit.click();

			token = 12;
		}
	});
	// 这一行的双击事件
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});
	$(function() {
		// /////////////////////////////////////
		//var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
		var treePID = treeID;
		var treePName = treeNames;
		var url = basePath
				+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
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
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var oList = member.organizationlist;

				var data1 = new Array();
				data1[0] = {
					id : treePID,
					pid : '-1',
					text : treePName
				};
				for (var i = 0; i < oList.length; i++) {
					data1[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				$t = $("table#SearchTable");
				var ts3 = new TreeSelector($("#departmentID")[0], data1, -1);
				ts3.createTree();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	
   	var treeid = 'scode20101101dfs3uhdprp0000000029'; //单据类别
	var url = basePath + "ea/ccode/sajax_ea_getAllListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.codeList;
                var data2 = new Array();
		        data2[0] = {
	                id: treeid,
	                pid: '-1',
	                text: '请选择'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].codeID,
                        pid: oList[i].codePID,
                        text: oList[i].codeValue
                    };
                }
               var ts = new TreeSelector($("#btype")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
});
});

function re_load() {
	document.location.href = basePath
			+ "ea/dutiable/ea_getDutiableList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate
			+ "&tsdate=" + tsdate + "&tedate=" + tedate;
}

function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '04') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}

/** **********************************ajax扫描普通已审核单据**************************************** */
$(document).ready(function() {
	// 增加点击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
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
									$.messager.show("提示信息", "该编号单据已加载到列表中！",
											1000);
									yy = false;
								}
							});
				}
				if (yy) {
					cxwldw("cashierBillsVO.journalNum=" + typeName);
				}
			});
	// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var searchurl = basePath
				+ "ea/cashiersummarycompany/sajax_ea_getAjaxCashierList.jspa?";
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
				var cashierBillsVO = member.cashierBillsVO;
				if (cashierBillsVO == null) {
					$.messager.lays(200, 200);
					$.messager.anim('fade', 1000);
					$.messager.show("提示信息", "没有数据！", 1000);
					$("#taxDateAjax").attr("value", "");
					notoken = 0;
					return;
				}
				if (cashierBillsVO.status == '04'
						|| cashierBillsVO.status == '05'
						|| cashierBillsVO.status == '06'
						|| cashierBillsVO.status == '10') {
					$.messager.lays(200, 200);
					$.messager.anim('fade', 1000);
					$("#taxDateAjax").attr("value", "");
					$.messager.show("提示信息", "未审核通过，请添加其他单据！", 1000);

					notoken = 0;
					return;
				}
				if (cashierBillsVO.status == '20') {
					$.messager.lays(200, 200);
					$.messager.anim('fade', 1000);
					$("#taxDateAjax").attr("value", "");
					$.messager.show("提示信息", "已经存在，请添加其他单据！", 1000);

					notoken = 0;
					return;
				}
				if ($("#journalNumAjax").val() == ""
						|| $("#journalNumAjax").val() == null) {
					$.messager.lays(200, 200);
					$.messager.anim('fade', 1000);
					$("#taxDateAjax").attr("value", "");
					$.messager.show("提示信息", "单据编号不能为空！", 1000);

					notoken = 0;
				}
				var tabletr = "<tr style='cursor: hand;'  id = "
						+ cashierBillsVO.cashierBillsID + ">";
				tabletr += "<td id='checkcc'align='center'><input type ='checkbox'class='ra'value="
						+ cashierBillsVO.journalNum
						+ " name='checkradio'/></td>";
				tabletr += "<td id='companyname' align='center'>"
						+ cashierBillsVO.companyname + "</td>";
				tabletr += "<td id='journalNum' align='center'>"
						+ cashierBillsVO.journalNum + "</td>";
				tabletr += "<td id='billsType' align='center'>"
						+ cashierBillsVO.billsType + "</td>";
				tabletr += "<td id='departmentname'  align='center'>"
						+ cashierBillsVO.departmentname + "</td>";
				tabletr += "<td id='staffname' align='center'>"
						+ cashierBillsVO.staffname + "</td>";
				tabletr += "<td id='cashierDate' align='center'>"
						+ cashierBillsVO.cashierDate + "</td>";
				tabletr += "<td id='afterDiscount'  align='center'>"
						+ cashierBillsVO.afterDiscount + "</td>";
				tabletr += "<td id='bankDepotName'  align='center'>"
						+ cashierBillsVO.bankDepotName + "</td>";
				tabletr += "<td id='dataDepotName'  align='center'>"
						+ cashierBillsVO.dataDepotName + "</td>";
				tabletr += "<td id='ccompanyname'  align='center'>"
						+ cashierBillsVO.ccompanyname + "</td>";
				tabletr += "<td id='accountNum'  align='center'>"
						+ cashierBillsVO.accountNum + "</td>";
				tabletr += "<td id='contactConnections'  align='center'>"
						+ cashierBillsVO.contactConnections + "</td>";
				tabletr += "<td id='contactUserName'  align='center'>"
						+ cashierBillsVO.contactUserName + "</td>";
				tabletr += "<td id='userAccountNum'  align='center'>"
						+ cashierBillsVO.userAccountNum + "</td>";
				tabletr += "<td id='phone'  align='center'>"
						+ cashierBillsVO.phone + "</td>";
				tabletr += "<td id='responsible'  align='center'>"
						+ cashierBillsVO.responsible + "</td>";
				tabletr += "<td id='accountant'  align='center'>"
						+ cashierBillsVO.accountant + "</td>";
				tabletr += "<td id='cashier'  align='center'>"
						+ cashierBillsVO.cashier + "</td>";
				tabletr += "<td id='status'  align='center'> 已审核 </td>";
				tabletr += "<td id='depStatue'  align='center'>"
						+ cashierBillsVO.depStatue + "</td>";
				tabletr += "<td  align='center'><a href='#' onclick='fj("
						+ cashierBillsVO.cashierBillsID
						+ ")' class='fj'>附件</a></td>";
				tabletr += " </tr>";
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
