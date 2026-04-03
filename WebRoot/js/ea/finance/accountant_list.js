$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	var query ="<form method='post' name='SearchForm' id='SearchForm'><input type='submit' style='display:none;' name='submit' /><input type='hidden' name='search' value='search'/>待会计审核列表"	
		+ "<table id='SearchTable'><tr>"
		+ "<td align='right'>&nbsp;&nbsp;项目名称：<input type='text' style=\"width: 100px\" id='xmname' name='cashierBillsVO.projectName'/>" 
		+ "&nbsp;&nbsp;黏贴单编号：<input id='journalNum' style=\"width: 100px\" name='cashierBillsVO.journalNum' />" 
		/*+ "&nbsp;&nbsp;部门：<select name='cashierBillsVO.departmentID' style=\"width: 110px\" id='departmentID'><option value=''>请选择</option></select>" */
		+ "&nbsp;&nbsp;责任人：<input id='staffname' style=\"width: 50px\" name='cashierBillsVO.staffname' />" +
		"&nbsp;&nbsp;单据类别：<select name='cashierBillsVO.billsType' style=\"width: 85px;hight: 50px\">" +
		"<option value='' selected='selected'>全部</option>" 
		+ "<option value='收款单'>收款单</option><option value='支款单'>支款单</option><option value='欠款单'>欠款单</option>" +
			"<option value='销售明细单'>销售明细单</option><option value='项目收入预算单'>项目收入预算单</option>" +
			"<option value='项目支出预算单'>项目支出预算单</option><option value='采购单'>采购单</option>" +
			"<option value='验货单'>验货单</option><option value='采购入库单'>采购入库单</option><option value='销售出库单'>销售出库单</option></select>"+
		/*"&nbsp;&nbsp;制单人：<input type='text' style=\"width: 50px\" id='inputs' name='cashierBillsVO.input'/>" +*/
		"&nbsp;&nbsp;制单日期：<input id='sdate' name='sdate' onfocus='\date(this);\' style='width: 85px' />至<input id='edate' name='edate' onfocus='\date(this);\' style='width: 85px' />" 
		
		/*+ "&nbsp;&nbsp;单据类别:<select name='cashierBillsVO.billsType' id='btype'></select>" */
	   /* + "<option value='04'>待主管审核</option><option value='05'>待会计审核</option><option value='06'>待出纳审核</option>" 
		+ "<option value='07'>已审核</option><option value='11'>驳回待修改</option><option value='09'>未确认收款</option>" 
		+ "<option value='30'>未审核作废</option></select>"*/ +
				"&nbsp;&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></td>"
		+ "<tr><td></table>"
		/*+ "&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;margin-top:11px;\"  id='refress' value=' 刷新 '/></td></tr>";*/
		+ "</form>";
	
	
	$('.flexme11').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
							// 设置分割线
							separator : true
					},{
							name : '驳回',
							bclass : 'delete',
							onpress : action
							// 当点击调用方法
					},{
							separator : true
					},{ 
							name : '查看',
							bclass : 'edit',
							onpress :action // 当点击调用方法 
					},{ separator : true
						
					},{
							name : '转出纳审核',
							bclass : 'edit',
							onpress : action
							// 当点击调用方法
					},{
							separator : true
					},{
							name : '审核记录',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					},{
							separator : true
					},{
							name : '导出',
							bclass : 'excel',
							onpress : action
							// 当点击调用方法
					},{
							separator : true
					},{
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					},{
							separator : true
					},{
							name : '查询历史数据',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					},{
							separator : true
					}]
			});


	function action(com, grid) {
		switch (com) {
			case '驳回' :
				if (hs == "history") {
					alert("历史数据不能修改");
					return;
				}
				var cid="";
				var str="";
				cashierBillsID="";
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
						cid=$(this).val();
						if($("tr#"+cid).find("#statusbill").attr("value")!=null&&$("tr#"+cid).find("#statusbill").attr("value")=="00"){
							str="00";
						}
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0) {
					alert('请选择');
					return
				} 
				if(str=="00"){
					alert("拨款自动生成单据不能驳回！");
					return;
				}
				return;
				$("#CashierBillsform").find("input#status").val("10");
				$("#jqModelSend2").jqmShow();
				document.SendForm2.reset();
				break;
			case '查看' :
				cashierBillsID="";
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0||cashierBillsID.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				/*document.location.href = basePath
						+ "ea/accountant/ea_toSaveAccountant.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID+"&sdate="+sdate+"&edate="+edate+ "&checkOutUrl=%2Fea%2Faccountant%2Fea_getAccountantList.jspa%3FpageNumber%3D" + pNumber
						+ "%26pageForm.pageNumber%3D" + $("#pageNumber").attr("value")+"%26sdate%3D"+sdate+"%26edate%3D"+edate;;*/
				window.open(basePath
						+ "ea/responsible/ea_toSaveResponsible.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&sdate=" + sdate + "&edate="
						+ edate+"&checkOutUrl=%2Fea%2Fresponsible%2Fea_getResponsibleList.jspa%3FpageNumber%3D" + pNumber
						+ "%26pageForm.pageNumber%3D" + $("#pageNumber").attr("value")
						+ "%26sdate%3D" + sdate + "%26edate%3D" + edate);
				break;
			case '转出纳审核' :
				cashierBillsID="";
				if (hs == "history") {
					alert("历史数据不能修改");
					return;
				}
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0) {
					alert('请选择');
					return
				} 

				$("#CashierBillsform").find("input#status").val("06");
				$("#jqModelSend2").jqmShow();
				document.SendForm2.reset();
				break;
			break;
		case '导出' :
			url = basePath + "ea/accountant/ea_showExcel.jspa?search=" + search+"&sdate="+sdate+"&edate="+edate;
			open(url);
			break;
		case '审核记录' :
			cashierBillsID="";
			$("[name='chkMsgId23']").each(function() {
				if ($(this).is(':checked')) {
					cashierBillsID += $(this).val() + ",";
				}
			});
			if (cashierBillsID == "" || cashierBillsID.length == 0||cashierBillsID.split(",").length>2) {
				alert('请选择单个任务');
				return
			}
			var searchurl = basePath
			+ "ea/accountant/sajax_ea_AjxaGetCheckbill.jspa?cashierBillsID="+cashierBillsID+"&date="
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
		
					//var pageForm = member.pageForm;
					var checkList = member.checkList;
					/*if (pageForm == null) {
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
					$("span#grzycount").text(zys);*/
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
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/accountant/ea_getAccountantList.jspa?search="
					+ search +"&sdate="+sdate+"&edate="+edate;
			numback(url);
			break;
		case '查询' :
			$("#jqModelSearch").jqmShow();
			$("input#journalNum").focus();
			break;
		case '查询历史数据' :
			document.location.href = basePath
					+ "ea/accountant/ea_toSearch.jspa?search=search&cashierBillsVO.status=history&pageNumber="
					+ pNumber;
			break;
	}
}
	

	$("#tosearch").click(function() {
	/*var opt=$("#btype").find("option:selected").text();
		if(opt=="请选择"){
			$("#btype").find("option:selected").attr("value","");
		}else{
			var leng=opt.length;
			var num=$("#btype").find("option:selected").text().indexOf("├");
			$("#btype").find("option:selected").attr("value",$("#btype").find("option:selected").text().substr(num+1,leng));
		}*/
		$("#SearchForm").attr(
				"action",
				basePath + "ea/accountant/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});

	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});

	$(".flexme11 tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	$("#submitResult2").click(function(){
		if ($.trim($("#SendForm2 #comments").val()) == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		 }
		$form = $("#CashierBillsform");
		if (confirm("确定执行该操作？")) {
			$form
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/accountant/ea_updateAccountant.jspa?search="
									+ search + "&pageNumber=" + pNumber);
			$form.find("input#cashierBillsID").val(cashierBillsID);
			$form.find("input#comments2").val($("#SendForm2 #comments").val());
			var str=cashierBillsID.split(',');
			for(var s=0;s<str.length;s++){
				if(str[s]!=null&&str[s]!=""){
					$("tr#" + str[s]).remove();
				}
			}
			cashierBillsID = "";
			token = 12;
			document.CashierBillsform.submit.click();
		}
	});


});
function re_load() {
	document.location.href = basePath
			+ "ea/accountant/ea_getAccountantList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&sdate="+sdate+"&edate="+edate;
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

$(function() {
			// /////////////////////////////////////
			var treeName = treenames;
			//var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
		//	var treePName = parent.frames["leftFrame"].tree
					//.getItemText(treePID);

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