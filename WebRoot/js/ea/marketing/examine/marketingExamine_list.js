$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "待营销审核管理",
				minheight : 80,
				buttons : [{
							// 设置分割线
							separator : true
						}, {
							name : '驳回',
							bclass : 'delete',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '查看',
							bclass : 'edit',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '转人事审核',
							bclass : 'edit',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '导出',
							bclass : 'excel',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '查询',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '查询历史数据',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
				}]
	});

	function action(com, grid){
		switch (com) {
			case '驳回':
                if(hostStatus=="hostStatus"){
                 	alert("历史数据不可更改");
                 	return ;
                }
				$form =$("#CashierBillsform"); 
				if(cashierBillsID==""){
                    alert("请选择！");
                    return;
                }
				if (confirm("确定驳回？")){
                    $form.attr("target","hidden").attr("action", basePath+"ea/marketingExamine/ea_updateMarketing.jspa?search="+search+"&pageNumber="+pNumber);
                    $form.find("input#cashierBillsID").val(cashierBillsID);
                    $form.find("input#status").val("10");
                    document.CashierBillsform.submit.click();
                    $("tr#"+cashierBillsID).remove();
                    cashierBillsID = "";
                    token = 12;
                }
                break;
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath+"ea/marketingExamine/ea_toMarketingPage.jspa?pageNumber="+pNumber+"&cashierBills.cashierBillsID="
									+cashierBillsID+"&search="+search+"&pagepageNumber="+$("#pageNumber").attr("value");
				break;
			case '转人事审核':
                if(hostStatus=="hostStatus"){
                 	alert("历史数据不可更改");
                 	return ;
                }
                $form =$("#CashierBillsform"); 
 				if(cashierBillsID==""){
                    alert("请选择！");
                    return;
                }
				if (confirm("确定转人事审核？")){
                    $form.attr("target","hidden").attr("action", basePath+"ea/marketingExamine/ea_updateMarketing.jspa?search="+search+"&pageNumber="+pNumber);
                    $form.find("input#cashierBillsID").val(cashierBillsID);
                    $form.find("input#status").val("02");
                    document.CashierBillsform.submit.click();
                    $("tr#"+cashierBillsID).remove();
                    cashierBillsID = "";
                    token = 12;
                }
                break;
		    case '导出':
				url = basePath + "ea/marketingExamine/ea_showExcel.jspa?search="+search+"&sdate="+sdate+"&edate="+edate;
				open(url);
				break;
		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/marketingExamine/ea_getMarketingList.jspa?search="
						+ search+"&sdate="+sdate+"&edate="+edate;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
			case '查询历史数据' :
				document.location.href = basePath
						+ "ea/marketingExamine/ea_toSearch.jspa?search=search&cashierBillsVO.consultStatus=hostStatus&cashierBills.cashierBillsID="
						+ cashierBillsID;
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
		cashierBillsID = this.id;
		status = $("tr#" + cashierBillsID).find("span#status").text();
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/marketingExamine/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
	
	$(".flexme11 tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		cashierBillsID = this.id;
		action("查看");
	});
});

function re_load() {
	document.location.href = basePath
			+ "ea/marketingExamine/ea_getMarketingList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&sdate="+sdate+"&edate="+edate;
}

function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '01') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}

$(function(){
	//var treeName = treenames;
	//var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	//var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);

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
					document.location.href = basePath
							+ "page/ea/not_login.jsp";
				}
				var oList = member.organizationlist;

				var data1 = new Array();
				data1[0] = {
					id : treeID,
					pid : '-1',
					text : treeName
				};
				for (var i = 0; i < oList.length; i++) {
					data1[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				$t = $("table#SearchTable");
				var ts3 = new TreeSelector($t
								.find("select#departmentID")[0],
						data1, -1);
				ts3.createTree();

				//var s = "2010-9-9";
				//var d = new Date(Date.parse(s.replace(/-/g, "/")));
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
	});
});