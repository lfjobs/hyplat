$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$(".JQueryreturnsCcompany").click(function() {
				notoken = 0;
				$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
			});
	$(".JQueryreturnsUser").click(function() {
				notoken = 0;
				$(".jqmWindow", $("#selectuserForm")).jqmHide();
			});
	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#documentsjqModel").jqmHide();
				$("#previewjqModel").jqmHide();
				showDocument = false;
			});
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "出纳明细单据汇总",
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
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
					name : '查询历史数据',
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
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "ea/cashiersummarycompany/ea_toCashier.jspa?pageNumber="
						+ pNumber + "&cashierBillsVO.cashierBillsID="
						+ cashierBillsID;
				break;
			case '导出' :
				url = basePath
						+ "ea/cashiersummarycompany/ea_showExcel.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				open(url);
				break;
			case '查询历史数据' :
				re_load();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/cashiersummarycompany/ea_getCashierList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
		}
	}
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
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
						basePath
								+ "ea/cashiersummarycompany/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});
		// 为弹出框准备下拉表内容
		/*
		 * var url =
		 * basePath+"ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101101dfs3uhdprp0000000029&date="+new
		 * Date().toLocaleString(); $.ajax({ url: encodeURI(url), type: "get",
		 * async: true, dataType: "json", success: function cbf(data){ var
		 * member = eval("(" + data + ")"); var nologin = member.nologin;
		 * if(nologin){ document.location.href
		 * =basePath+"page/ea/not_login.jsp"; } var codeList = member.codeList;
		 * for(var i = 0;i<codeList.length;i++){ $op = $("<option/>");
		 * $op.val(codeList[i].codeValue); $op.text(codeList[i].codeValue);
		 * $("#billsType[name]").append($op); } }, error: function cbf(data){
		 * alert("数据获取失败！") } });
		 */
});
$(function() {
	// /////////////////////////////////////
	//var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
	var treePID = treeID;
	var treePName = treeNames;
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
function re_load() {
	document.location.href = basePath
			+ "ea/cashiersummarycompany/ea_getCashierList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&sdate=" + sdate + "&edate="
			+ edate;
}

function fj(cID) {
	if (showDocument == true) {
		$.messager.lays(200, 200);
		$.messager.anim('fade', 1000);
		$.messager.show("提示信息", "不能查看或修改附件！！", 3000);
	}
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '04') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}

/** **********************************ajax扫描出纳单据**************************************** */
$(document).ready(function() {
	var cashierBillsID = "";
	// 增加点击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 显示单据预览
	$("input#previewbutton").click(function() {
		if (cashierBillsID == "") {
			alert("请选择！");
			return;
		}
		$("iframe[name='previewcontent']")
				.attr(
						"src",
						basePath
								+ "ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
		$("#previewjqModel").jqmShow();
	});
	// 隐藏单据预览
	$("span#previewclose").click(function() {
				$("#previewjqModel").jqmHide();
			});
	// 点击查看
	$("input#ckdocument").click(function() {
		if (cashierBillsID == "") {
			alert("请选择！");
			return;
		}
		window
				.open(basePath
						+ "ea/cashiersummarycompany/ea_toCashier.jspa?cashierBillsVO.cashierBillsID="
						+ cashierBillsID + "&tohide=tohide");

	});
	document.onkeydown = function(evt) {// 捕捉回车 根据激光扫描枪查询单据
		evt = (evt) ? evt : ((window.event) ? window.event : ""); // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		if (key == 13 && showDocument == true) { // 判断是否是回车事件与是否打开扫描窗口。
			$("input#searchdocument1").trigger("click");
		}
	};
	// 导出列表信息
	$("input#daochudocument").click(function() {
		var journalNums = "";
		var arr = $("table#gostable").find("td#journalNum");
		if (arr.length > 0) {
			arr.each(function() {
						journalNums += $.trim($(this).text()) + ",";
					});
			journalNums = journalNums
					.substring(0, journalNums.lastIndexOf(","));
		}
		url = basePath
				+ "ea/cashiersummarycompany/ea_showExcel.jspa?journalNums="
				+ journalNums;
		open(url);

	});
	// 根据输入的单据编号查询
	$("input#searchdocument1").click(function() {
				var yy = true;
				var typeName = "";
				if ($.trim($("#journalNumAjax").attr("value")) == "") {
					$.messager.lays(200, 200);
									$.messager.anim('fade', 1000);
									$.messager.show("提示信息", "单据编号为空！",
											1000);
											
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
					notoken = 0;
					return;
				}
				var tabletr = "<tr style='cursor: hand;'  id = "
						+ cashierBillsVO.cashierBillsID + ">";
				tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
						+ cashierBillsVO.cashierBillsID
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
				tabletr += "<td id='status'  align='center'>"
						+ cashierBillsVO.status + "</td>";
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

/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	//var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 选择往来单位
	$("#wldw").click(function() {
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
			$s = $("#SearchForm");
			var wldwName = $("tr#" + contactcID, $("#selectcompanyForm"))
					.find("td#ccompanyname").html();
			$s.find("input#ccID").attr("value", wldwName);
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
	//var userstaffID = "";
	$("table#gouserstable tr[id]").live("click", function(event) {
				cuID = this.id;
				userstaffID = this.title;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 选择往来个人
	$("#wlgr").click(function() {
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
			$s = $("#SearchForm");
			var wlgrName = $("tr#" + cuID, $("#selectuserForm"))
					.find("td#contactUserName").html();
			$s.find("input#cuID").attr("value", wlgrName);
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
