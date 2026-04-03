$(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector



	var query = "<form method='post' name='searchf' id='searchf'>"
		+ "<input type='submit' style='display:none;' name='submit' />"
		+ "<input type='hidden' name='search' value='search'/>"
		+ "<table border='0'>"
		+ "<tr>"
		+ "<td><strong>"+(jumptype=='zbqsh'?'招标前未审核':'招标前已审核')+"</strong>&nbsp;&nbsp;&nbsp;&nbsp;项目名称</td><td><input type='text' name='cashierBills.projectName' size='13'/></td>"
		+ "<td>主项目</td><td><input type='hidden' class='xmtype' name='cashierBills.xmtype'/><input type='text' class='xmtypename' size='13'/>"
		+ "</td><td>凭证号</td><td><input type='text' name='cashierBills.journalNum'' size='13'/></td><td>责任人</td><td><input type='text' name='cashierBills.staffName' size='10'/></td>"
		+ "<td>制单人</td><td><input type='text' name='cashierBills.inputName' size='10'size='10'/></td>"
		+ "<td>制单时间</td><td><input type='text' onfocus='daytime(this);' size='13' name='startTime'/>至<input type='text' onfocus='daytime(this);' size='13' name='endTime'/></td>"
		+ "<td><input type='button' value=' 查询 ' class='input-button' style='margin:2px;'id='tosearch'/></td>"
		+ "</tr></table></form>";

	$('.flexme11').flexigrid({
		height : 280,
		width : 'auto',
		minwidth : 30,
		title : query,
		minheight : 80,
		buttons : jumptype == "zbqsh" ? [ {
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '审核通过并结束审核',
			bclass : 'examine',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '审核通过并转他人审核',
			bclass : 'transfer',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '驳回',
			bclass : 'reject',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '审核记录',
			bclass : 'examine',
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
			name : '打印',
			bclass : 'printer',
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
		} ] : [ {
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		},{
			separator : true
		}, {
			name : '审核记录',
			bclass : 'examine',
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
			name : '打印',
			bclass : 'printer',
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
		} ]
	});

	function action(com, grid) {
		switch (com) {
		case '查看':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}

			window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?pageNumber=" + pNumber
					+ "&cashierBills.cashierBillsID=" + cashierBillsID+"&vuvtype=edit");
			break;
		case '审核通过并结束审核':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			
			
			$("#jqModelComment").jqmShow();
			$("#commtype").val("01");
			$("#examinecsbID").val(cashierBillsID);
			$("#examineOpra").val("01");
			
			

			break;
		case '审核通过并转他人审核':
			if (cashierBillsID == "") {
				alert("请选择");
				return;
			}

			
			$("#commtype").val("02");
			$("#jqModelComment").jqmShow();
			$("#examineOpra").val("02");
			
			break;
		case '驳回':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			
			$("#commtype").val("03");
			$("#jqModelComment").jqmShow();

			$("#examinecsbID").val(cashierBillsID);
			$("#examineOpra").val("03");
			

			break;
		case '审核记录':
			if (cashierBillsID == "") {
				alert("请选择");
				return;
			}
			var url = basePath
					+ "ea/costsheet/sajax_ea_AjxaGetCheckbill.jspa?de="
					+ new Date();
			$
					.ajax({
						url : url,
						type : "get",
						async : false,
						dataType : "json",
						data : {
							"cashierBills.cashierBillsID" : cashierBillsID
						},
						success : function(data) {

							var mem = eval("(" + data + ")");
							var checkList = mem.checkList;
							if (checkList != null) {
								var tabletr = "<tr>";
								for ( var i = 0; i < checkList.length; i++) {
									var status = checkList[i].auditorstatus == '01' ? '未审核'
											: checkList[i].auditorstatus == '02' ? '审核通过'
													: '驳回';
									tabletr += "<td id='billtatus' align='center'>"
											+ checkList[i].billtatus + "</td>";
									tabletr += "<td id='audittime' align='center'>"
											+ checkList[i].audittime + "</td>";
									tabletr += "<td id='auditorname' align='center'>"
											+ checkList[i].auditorname
											+ "</td>";
									tabletr += "<td id='auditorstatus' align='center'>"
											+ status + "</td>";
									tabletr += "<td id='comments' align='center'>"
											+ checkList[i].comments + "</td>";
									tabletr += " </tr>";
								}
								$("#checkTitle").html(tabletr);
								notoken = 0;
								window.status = "数据加载成功";
								$("#jqModelCheck").jqmShow();
							} else {
								alert("该单据没有审核记录！");
							}

						},
						error : function(data) {
							alert("获取审核记录失败");
						}

					});
			break;
		case '导出':
			var url = basePath + "ea/costsheet/ea_exportExcel.jspa?search=" + search+"&jumptype="+jumptype;
			open(url);
			break;
		case '打印':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID="
					+ cashierBillsID+"&vuvtype=printcsb");
			break;
		case '设置每页显示条数':
			var url = basePath
					+ "/ea/costsheet/ea_getCostSheetList.jspa?search=" + search
					+ "&jumptype=" + jumptype;
			numback(url);
			break;
		
	   }
	}
		
		// 这一行的双击事件
    $(".flexme11 tr[id]").dblclick(function() {
			$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
			cashierBillsID = this.id;
		    action("查看");
		});

	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {
		cashierBillsID = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");


	});

	
	
	$("#rejectSubmit").click(function(){
		
		var commtype= $("#commtype").val();
		var comments = $("#comments2").val();
		$("#comments1").val(comments); 
		if($.trim(comments)==""){
			alert("请填写意见");
			return;
		}

		//审核驳回和通过并结束
		if(commtype=="03"||commtype=="01"){
		$("#sendForm").attr("target", "hidden").attr("action",
				basePath + "/ea/costsheet/ea_zbqExamining.jspa");
		document.sendForm.submit.click();
		token = 2;
	    }
	 
	    //转他人
	   if(commtype=="02"){
		   $("#jqModelComment").jqmHide();
		   $("#jqModelSend").jqmShow();
			getAllCompanyOfGroup();
	    }
		
	});

	/**
	 * 
	 * 键入时查询项目分类
	 */
	$(".xmtypename").focus(function() {
		getxmtype($(this));
	}).keyup(function() {

		getxmtype($(this));

	});

	// 查询按钮单击事件
	$("#tosearch").click(
			function() {

				$("#searchf").attr(
						"action",
						basePath + "/ea/costsheet/ea_toSearch.jspa?pageNumber="
								+ pNumber + "&jumptype=" + jumptype);
				document.searchf.submit.click();
			});




	

});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&jumptype=" + jumptype;
	}
}

/** ---------------------------------------------------------选择多个人员---------------------------------------------* */
/**
 * 
 * 获得当前公司集团的所有公司
 */
function getAllCompanyOfGroup() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var companylist = member.companylist;

			var str = "<option value=''>请选择公司</option>";
			for ( var i = 0; i < companylist.length; i++) {
				var obj = companylist[i];
				str += "<option title='" + obj.companyName + "'value='"
						+ obj.companyID + "'>" + obj.companyName + "</option>";
			}
			$("#jqModelSend select#receiverCompanyID").html(str);
			$("#jqModelSend #receiverDeptID").html(
					"<option value=''>请选择部门</option>");
			$("#jqModelSend #receiverID").html(
					"<option value=''>请选择人员</option>");
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

/**
 * 
 * 根据公司获得部门
 * 
 * @param {}
 *            val
 */
function bmdept(val) {

	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"companyID" : val
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var orgaizationlist = member.orgaizationlist;
			var str = "<option value=''>请选择部门</option>";
			for ( var i = 0; i < orgaizationlist.length; i++) {
				var obj = orgaizationlist[i];
				str += "<option value='" + obj.organizationID + "'>"
						+ obj.organizationName + "</option>";
			}
			$("#jqModelSend #receiverDeptID").html(str);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

/**
 * 
 * 根据部门获得人员
 * 
 * @param {}
 *            company
 * @param {}
 *            org
 */
function getPerson(company, org) {

	$("option", $("select#receiverID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"currentCompanyID" : company,
			"checkOrgID" : org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "<option value=''>请选择人员</option>";
			for ( var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj.staffID + "'>" + obj.staffName
						+ "(" + obj.staffCode + ")</option>";
			}
			$("#jqModelSend #receiverID").html(str);
		}
	});
}
/**
 * 
 * 当公司改变时，获取部门
 * 
 * @param {}
 *            obj
 */
function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#jqModelSend #receiverDeptID").html(
				"<option value=''>请选择部门</option>");
	}
}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj) {
	if ($(obj).val() != '') {
		getPerson($("#jqModelSend #receiverCompanyID").val(), $(obj).val());
	} else {
		$("#jqModelSend #receiverID").html("<option value=''>请选择人员</option>");
	}
}

/**
 * 
 * 提交审核
 */
function submitExamine() {

	if ($("#receiverID").val() == "") {
		alert("请选择审核人");
		return;
	}

	$("#examineComName").val(
			$("#receiverCompanyID").find("option:selected").text());
	$("#examineorgName").val(
			$("#receiverDeptID").find("option:selected").text());

	$("#examineName").val($("#receiverID").find("option:selected").text());
	$("#examinecsbID").val(cashierBillsID);
	$("#sendForm").attr("target", "hidden").attr("action",
			basePath + "ea/costsheet/ea_zbqExamining.jspa");
	document.sendForm.submit.click();
	token = 2;
}

//键入时查询项目分类

function getxmtype(obj){
	
	var left = $(obj).position().left;
	var top = $(obj).position().top;
		var url = basePath
				+ "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				parameter : $.trim($(obj).val())
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var xmList = member.xmlist;
				
				var str="";

				for (var i = 0; i < xmList.length; i++) {
					if(xmList[i].isLeaf =="00"){
						str+="&nbsp;&nbsp;<span><a href='#' onclick='selectz(this);'>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</a></span><br/>";
					}else{
					str+="&nbsp;&nbsp;<span>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</span><br/>";
					}
				}
				if(str==""){
					str="&nbsp;无搜索结果";
				}
				
				$("#treeBoxs").html(str);

					
				    $("#jqModel").css({
						position : "absolute",
						left : left,
						top : top+23
					}).show();
				    

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

}

//选中主项目
function selectz(obj){
	$("#jqModel").hide();
	var codeValues=$(obj).text();
	$(".xmtypename").val(codeValues.substring(codeValues.indexOf(")")+1));
	$(".xmtype").val(codeValues.substring(1,codeValues.indexOf(")")));
	
}