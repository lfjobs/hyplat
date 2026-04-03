$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({ 
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : "出纳记账单列表",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
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
						// }, {
						// name: '转主管审核',
						// bclass: 'edit',
						// onpress: action//当点击调用方法
						// }, {
						// separator: true
					}, {
					name : '查询',
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
			case '添加' :
				var url = basePath
						+ "ea/cashierbills/ea_toSaveCashierBills.jspa?pageNumber="
						+ pNumber + "&differenceStyle=" + differenceStyle
						+ "&BType=" + bill;
				document.location.href = encodeURI(url);
				break;
			case '删除' :
				$form = $("#CashierBillsform");
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				if (status != "草稿") {
					alert("不能删除！");
					return;
				}
				if (confirm("确定删除？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/cashierbills/ea_delCashierBills.jspa?search="
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
						+ "ea/cashierbills/ea_toSaveCashierBills.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value")
						+ "&differenceStyle=" + differenceStyle + "&BType="
						+ bill;
				break;
			case '打印预览' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				window
						.open(basePath
								+ "/ea/printcashierbills/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
				break;
			case '导出' :
				url = basePath + "ea/cashierbills/ea_showExcel.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				open(url);
				break;
			case '查询历史数据' :
				//document.location.href = basePath+ "/ea/cashierbills/ea_getCashierBillsList.jspa";
				re_load();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/cashierbills/ea_getCashierBillsList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate ;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#SearchForm").find("input#journalNum").focus();
				break;
			case '作废' :
			   if (cashierBillsID == "") {
					alert("请选择！");
					return;
			   }
			   if(confirm("是否继续操作？"))
			   if($.trim(status)=="草稿"||$.trim(status)=="待主管审核"){
					var url = basePath + "ea/cashierbills/sajax_ea_updateCashierBillsInvalid.jspa?date="
							+ new Date().toLocaleString()+"&cashierBills.cashierBillsID="+ cashierBillsID;				
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
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				status = $("tr#" + cashierBillsID).find("span#status").text();
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
		$("#SearchForm").attr(
				"action",
				basePath + "ea/cashierbills/ea_toSearch.jspa?pageNumber="
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
	 * async: true, dataType: "json", success: function cbf(data){ var member =
	 * eval("(" + data + ")"); var nologin = member.nologin; if(nologin){
	 * document.location.href =basePath+"page/ea/not_login.jsp"; } var codeList =
	 * member.codeList; for(var i = 0;i<codeList.length;i++){ $op = $("<option/>");
	 * $op.val(codeList[i].codeValue); $op.text(codeList[i].codeValue);
	 * $("#bills[name]").append($op); } }, error: function cbf(data){
	 * alert("数据获取失败！") } });
	 */
	// /////////////////////////////////////
	// var treeID = parent.frames["leftFrame"].tree.getSelectedItemId();
	var treeName =treeNames;
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
					$t = $("table#SearchTable");
					var ts = new TreeSelector(
							$t.find("select#departmentID")[0], data, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					// alert("数据获取失败！")
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
	var url = basePath
			+ "ea/cashierbills/ea_getCashierBillsList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&sdate=" + sdate + "&edate="
			+ edate;
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
