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
				height : 100,
				width : 'auto',
				minwidth : 30,
				title : (zz=="11"?"固定资产":zz=="12"?"资产增加":zz=="13"?"资产减少":zz=="14"?"预收款单":zz=="15"?"现收款单":zz=="16"?"销售订货":zz=="17"?"销售发货":zz=="18"?"销售退货":zz=="19"?"销售调拨":zz=="20"?"资产报损":zz=="09"?"应付工资":zz=="10"?"已付工资":zz=="00"?"现金收入":zz=="01"?"现金支出":zz=="02"?"银行收入":zz=="03"?"银行支出":zz=="04"?"应收账款":zz=="06"?"现金日记账":zz=="07"?"资金收入":zz=="08"?"资金支出":"应付账款")+"列表",
				minheight : 80,
				buttons : [ {
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
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
				},{
					name : '审核记录',
					bclass : 'mysearch',
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
				},{
					name : '费用报销准则',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				},{
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
						+ "/ea/splitbillgroup/ea_toSaveCashierBills.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value")
						+ "&differenceStyle=" + differenceStyle+ "&zz=" + zz+ "&checkOutUrl=%2Fea%2Fsplitbillcompany%2Fea_getCashierBillsList.jspa%3FpageNumber%3D"
						+ pNumber + "%26pageForm.pageNumber%3D"
						+ $("#pageNumber").attr("value") + "%26sdate%3D" + sdate + "%26edate%3D"
						+ edate+ "%26zz%3D" + zz;
				break;
			case '审核记录' :
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
							var tabletr="<tr>";
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
							$("#checkTitle").after(tabletr);
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
			case '打印预览' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				window
						.open(basePath
								+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
				break;
			case '导出' :
				if(zz=='08'){
					$("#jqModelExcel").jqmShow();
				}else{
					url = basePath + "/ea/splitbillgroup/ea_showExcel.jspa?search="
					+ search + "&sdate=" + sdate + "&edate=" + edate+ "&zz=" + zz;
					open(url);
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/splitbillgroup/ea_getCashierBillsList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate + "&zz=" + zz;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#SearchForm").find("input#journalNum").focus();
				break;
				
			case '费用报销准则' :
				ViewOffice("upload_files/finance/fybxzz.doc", ".doc");
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
				basePath + "/ea/splitbillgroup/ea_toSearch.jspa?pageNumber="
						+ pNumber+ "&zz=" + zz);
		document.SearchForm.submit.click();
	});
	$("#tosearchExcel").click(function() {
		url = basePath + "/ea/splitbillcompany/ea_showExcelzc.jspa?search="
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
	
	  // 查询总公司下的所有子公司
        var pid=companyid;
        var pname=companyname;
        var url = basePath
                + "ea/company/sajax_n_ea_getCompanyList.jspa?date="
                + new Date().toLocaleString();
        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : true,
            dataType : "json",
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                var companylist = member.companylist;
                var data1 = new Array();
                data1[0] = {
                    id : pid,
                    pid : '-1',
                    text :pname
                };
                for (var i = 0; i < companylist.length; i++) {
                    data1[i + 1] = {
                        id : companylist[i].companyID,
                        pid : companylist[i].companyPID,
                        text : companylist[i].companyName
                    };
                }
                var ts3 = new TreeSelector($("#deptID")[0],
                        data1, -1);
                ts3.createTree();
            },
            error : function cbf(data) {
                alert("数据获取失败！");
            }
        });
    // 公司名称change事件
    $("#deptID").change(function() {
        if ($(this).val() != '') {
            bmDept(this.value);
        } else {
            $("option", $("#orgID")).remove();
            $("#orgID")
                    .html("<option value=''>请选择公司</option>");
        }
    });
    // 部门名称change事件
    $("#orgID").change(function() {
        var temp = $("#orgID").val();
        if (temp.substring(0, 7) != 'company') {
                getPerson($("#deptID").val(), this.value);
        } else {
            $("option", $("select#person")).remove();
            $("#person")
                    .html("<option value=''>请选择部门</option>");
        }
    });
	
	// 添加所选中的物品到物品单
	$("#qduser").click(function() {
		if ($("[name='gncheck']").is(':checked')) {
			window.open(basePath
					+ "/ea/splitbillcompany/ea_toPageNum.jspa?gncheck="
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

//根据公司ID和部门ID查询责任人
function getPerson(company, org) {
    $("option", $("select#person")).remove();
    var url = basePath
            + "ea/cashiersummary/sajax_ea_getStaffList.jspa?currentCompanyID="
            + company + "&currentOrgnizationID=" + org + "&date111="
            + new Date();
    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var persons = member.stafflist;
            var str = "<option value=''>全部</option>";
            for (var i = 0; i < persons.length; i++) {
                var obj = persons[i];
                str += "<option value='" + obj[0] + "'>" + obj[1] + "</option>";
            }
            $("#person").html(str);
        }
    });
}

// 根据公司ID查询对应的部门列表
function bmDept(val) {
    $("option", $("#orgID")).remove();
    var url = basePath
            + "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
            + val + "&date=" + new Date().toLocaleString();
    $.ajax({
                url : encodeURI(url),
                type : "get",
                async : true,
                dataType : "json",
                success : function cbf(data) {
                    /** **添加部门列表** */
                    var member = eval("(" + data + ")");
                    var oList = member.organizationlist;
                    var data2 = new Array();
                    data2[0] = {
                        id : val,
                        pid : '-1',
                        text : '全部'
                    };
                    for (var i = 0; i < oList.length; i++) {
                        data2[i + 1] = {
                            id : oList[i].organizationID,
                            pid : oList[i].organizationPID,
                            text : oList[i].organizationName
                        };
                    }                    
                    ts = new TreeSelector($("#orgID")[0], data2, -1);
                    ts.createTree();
                },
                error : function cbf(data) {
                    alert("数据获取失败！");
                }
            });
    $("option[value=" + this.value + "]", $("#orgID")).val("");
}

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
			+ "/ea/splitbillcompany/sajax_ea_getgoodsbillBygoodsnum.jspa?";
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
			+ "/ea/splitbillcompany/ea_getCashierBillsList.jspa?pageNumber="
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

 function ViewOffice(docPath, fileType) {
	  window.open(
					basePath
							+ "page/ea/main/office_ea/corporationcode/Fileonlyreadprint.jsp?docPath="
							+ docPath + "&fileType=" + fileType);
}
