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
				title : "待主管审核",
				minheight : 80,
				buttons : [{
					name : '转经理审核',
					bclass : 'add',
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
					name : '驳回',
					bclass : 'delete',
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
			case '驳回' :
				if (taxstatusDu == "taxstatusDu") {
					alert("历史数据不可更改");
					return;
				}
				$form = $("#Competentform");
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				if (confirm("确定驳回？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/competent/ea_updateResponsible.jspa?search="
											+ search + "&pageNumber=" + pNumber);
					$form.find("input#cashierBillsID").val(cashierBillsID);
					$form.find("input#taxstatus").val("10");
					document.Competentform.submit.click();
					$("tr#" + cashierBillsID).remove();
					cashierBillsID = "";
					token = 12;
				}
				break;
			case '转经理审核' :
				if (taxstatusDu == "taxstatusDu") {
					alert("历史数据不可更改");
					return;
				}
				$form = $("#Competentform");
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				if (confirm("确定转经理审核？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/competent/ea_updateResponsible.jspa?search="
											+ search + "&pageNumber=" + pNumber);
					$form.find("input#cashierBillsID").val(cashierBillsID);
					$form.find("input#taxstatus").val("02");
					document.Competentform.submit.click();
					$("tr#" + cashierBillsID).remove();
					cashierBillsID = "";
					token = 12;
				}
				break;
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/competent/ea_toCashier.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&sdate=" + sdate + "&edate="
						+ edate + "&search=" + search + "&tsdate=" + tsdate
						+ "&tedate=" + tedate + "&taxstatusDu=" + taxstatusDu;
				break;
			case '导出' :
				url = basePath + "/ea/competent/ea_showExcel.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate
						+ "&tsdate=" + tsdate + "&tedate=" + tedate
						+ "&taxstatusDu=" + taxstatusDu;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/competent/ea_getDutiableList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate
						+ "&tsdate=" + tsdate + "&tedate=" + tedate;
				numback(url);
				break;
			case '查询' :

				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
		}
	}
	// 一行单击事件
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				status = $("tr#" + cashierBillsID).find("span#status").text();
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
		$("#SearchForm").attr(
				"action",
				basePath + "/ea/competent/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
	// 一行双击事件
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
			+ "/ea/competent/ea_getDutiableList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search + "&sdate=" + sdate + "&edate=" + edate
			+ "&tsdate=" + tsdate + "&tedate=" + tedate + "&taxstatusDu="
			+ taxstatusDu;
}
function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#taxstatus").text();
	if (statusfj != '01') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}