$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$(".JQueryreturns").click(function() {
				quzhi="";
				notoken = 0;
				$(".jqmWindow").jqmHide();
			});
	
	var query ="<form method='post' name='SearchForm' id='SearchForm'><input type='submit' style='display:none;' name='submit' /><input type='hidden' name='search' value='search'/>"	
		+(cancel=="cancel"?"已作废现金申请":weibokuan!='weibokuan'?"现金申转对账单":"现金申请明细审批单")
		+ "<table id='SearchTable'><tr>"
		+ "<td align='right'>&nbsp;&nbsp;项目名称：<input type='text' style=\"width: 100px\" id='xmname' name='cashierBills.projectName'/>" 
		+ "&nbsp;&nbsp;申请单编号：<input id='journalNum' style=\"width: 100px\" name='cashierBills.journalNum' />" 
		+ "&nbsp;&nbsp;使用责任人：<input id='person' style=\"width: 50px\" name='cashierBills.staffName' />" +
		/*"&nbsp;&nbsp;制单人：<input type='text' style=\"width: 50px\" id='inputs' name='cashierBills.inputName'/>" +*/
		(other=="other"?"":"&nbsp;&nbsp;拨款状态：<select name='cashierBills.status' style=\"width: 85px;hight: 50px\">" +
		"<option value='' selected='selected'>全部</option>" 
		+ "<option value='41'>未拨款</option><option value='42'>审核中</option><option value='44'>暂不拨款</option></select>") +
		"&nbsp;&nbsp;申请日期：<input id='sdate' name='sdate' onfocus='\date(this);\' style='width: 85px' />至<input id='edate' name='edate' onfocus='\date(this);\' style='width: 85px' />" 
	    +"&nbsp;&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='searchStaff'/></td>"
		+ "<tr><td></table>"
		+ "</form>";
	$('.flexme11').flexigrid({
				height : 280,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons :[/*{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
					// 当点击调用方法
				},{
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},*/{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					// 当点击调用方法
				},{
					separator : true
				},other=="other"?{
					name : '明细',
					bclass : 'edit',
					onpress : action
					// 当点击调用方法
				}:{
					name : '拨款',
					bclass : 'add',
					onpress : action
				},other=="other"?{
					name : ''
				}:{
					separator : true
				},other=="other"?{
					name : ''
				}:{
					name : '审核',
					bclass : 'edit',
					onpress : action
					// 当点击调用方法
				},other=="other"?{
					name : ''
				}:{
					separator : true
				},other=="other"?{
					name : ''
				}:{
					name : '作废',
					bclass : 'delete',
					onpress : action
					// 当点击调用方法
				}/*,other=="other"?{
					name : ''
				}:{
					separator : true
				},other=="other"?{
					name : ''
				}:{
					name : '已作废现金申请',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
				},other!="other"&&level=='allCompany'?{
					   separator : true
				}:{
					name : ''
				},other!="other"&&level=='allCompany'?{
					name : '调拨',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
				}:{
					name : ''
				}*/]
			});
	$("div.bDiv",$("div.flexigrid")).css("height","500px");
	function action(com, grid) {
		switch (com) {
			case '查询' :
			$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/cashapplybills/ea_toCash.jspa?search="+search+"&sdate="+sdate+"&edate="+edate+"&weibokuan="+weibokuan+"&other="+other+"&level="+level+"&cancel="+cancel+"&cother="+cother;
				numback(url);
				break;
			case '拨款' :
			case '明细' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
			    }
				if(other!="other"&&(status=="42"||status=="43")){
					alert("单据已拨款，不能继续拨款");
					return;
				}
				window.open("ea/cashapplybills/ea_toCash.jspa?&cother=cother&weibokuan="+weibokuan+"&other="+other+"&level="+level+"&str="+cashierBillsID);
				break;
			case '审核' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
			    }
				if(status=="41"){
					alert("单据未拨款不能审核");
					return;
				}
				if(status=="43"||status=="44"){
					alert("单据已审核通过，不能继续审核");
					return;
				}
				window.open("ea/cashapplybills/ea_toCash.jspa?&cother=cother&weibokuan="+weibokuan+"&other="+other+"&level="+level+"&str="+cashierBillsID);
				break;
			case '作废' :
				/*var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
								
							}
						});*/
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
			   }
				
				if(confirm("确定作废")){
			        var url = basePath+"ea/cashapplybills/sajax_ea_CancelCashApplyBills.jspa";
			        $.ajax({
			         url:url,
			         type:"get",
			         async:false,
			         dataType:"json",
			         data:{
			         	str:cashierBillsID
			         },
			         success:function(data){
						$("tr#"+cashierBillsID).remove();
			         },
			         error:function(data){
			         	alert("作废操作失败！");
			         }
			        
			        });
				}
				break; 
			case '已作废现金申请' :
			    document.location.href=basePath+"ea/cashapplybills/ea_toCash.jspa?weibokuan=weibokuan&level=allCompany&cancel=cancel";
				break; 
			case '调拨' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
			  	$("#jqModelChange").jqmShow();
				break; 
			case '打印预览' :
				if(cother=="cother"){
					open(basePath
							+ "page/ea/main/human/office/production/printWagesFrame.jsp?sdate=" + sdate + "&edate=" + edate
							+ "&search=" + search+"&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one1&cother=cother&weibokuan="+weibokuan+"&level="+level);
					}
				else{open(basePath
						+ "page/ea/main/human/office/production/printWagesFrame.jsp?sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search+"&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one1&weibokuan="+weibokuan+"&level="+level);}
				break;	
		}
	}
	
	$(".close").live("click", function(event) {
		parent.$("#bankJqm").jqmHide();
	   bokuan="";
	   parent.re_load();
	});
	// 复选框选中
/*	$(".chx").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	*/
	
	$(".JQueryflexme tr[id]").click(function() {
		cashierBillsID = this.id;
		status = $("tr#" + cashierBillsID).find("input#notesStatus").val();
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	$("#DaoRuFan").click(function(){// 返回
       parent.$("#bankJqm").jqmHide();
	   bokuan="";
	   parent.re_load();
	   
	});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModelSearch").jqmHide();
	});
	$("input.JQueryreturnc").click(function() {// 取消
		$("#jqModelChange").jqmHide();
    });
	// 查询相关操作
	$("#searchStaff").click(function() {
		if(cother=="cother"||cother=="all"){
			$("#SearchForm")
			.attr(
					"action",
					basePath
							+ "ea/cashapplybills/ea_toSearch.jspa?pageNumber="
							+ pNumber+"&weibokuan="+weibokuan+"&cother="+cother+"&level="+level);
			
		}else{
			$("#SearchForm")
					.attr(
							"action",
							basePath
									+ "ea/cashapplybills/ea_toSearch.jspa?pageNumber="
									+ pNumber+"&weibokuan="+weibokuan+"&other="+other+"&level="+level);
		}
		document.SearchForm.submit.click();
	});
	// 调拨相关操作
	$("#ChangeStaff").click(function() {
		$("#cstaffChangeForm")
				.attr(
						"action",
						basePath
								+ "ea/cashapplybills/ea_ChangeCashApplyBills.jspa?pageNumber="
								+ pNumber+"&search="+search+"&weibokuan="+weibokuan+"&level="+level+"&strc="+strc);
		alert("调拨成功！");
		document.getElementById("cstaffChangeForm").submit();
	});
	
	$("#DaoRuFan2").click(function(){// 返回
       $("#yhbankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd2").click(function(){// 选择确定
		var childopertionID = window.frames["yhdaoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择");
			return;
		}
		var childopertionName =window.frames["yhdaoRu"].$('tr#'+childopertionID).find("span#childbankName").text();
		$("tr#"+goodsBillsID).find("#appropriationNum").attr("value",childopertionName);
		$("#yhdaoRu").attr("src","");
        $("#yhbankJqm").jqmHide();
    });
	
	
//--------------------拨款保存操作-----------------------------------
	$("#DaoRuFanqd").click(function() {
		//var frameReturn=$("#daoRu")[0].contentWindow;
		//	$(".ckTextLength").trigger("blur");
		
		$(".posnum").trigger("blur");
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}		
		notoken = 1;
		if ($("#CashApplyBillsform .error").length) {
			$("input.posnum").each(function(){
				if($.trim($(this).val())==''){
					$(this).css("background-color","red");
				}
			});
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		var bj=0;
		$("tr[id]",$("#CashApplyBillsform")).each(function(){
			var sqje=parseFloat($.trim($(this).find("#money").text()));
			var pzje=parseFloat($.trim($(this).find("#appropriationMoney").attr("value")));
			if(pzje>sqje){
				$(this).find("#appropriationMoney").css("color","red");
				$(this).find("#appropriationMoney").css("background-color","blue");
				bj++;
			}
		});
		if(bj){
			alert("批准金额不得大于申请金额！！");
			notoken = 0;
			return;
		}
		title="";
		$("#CashApplyBillsform").attr("target","hidden")
			.attr(
					"action",
					basePath
							+ "ea/cashapplybills/ea_SaveCashApplyBills.jspa?pageNumber="
							+ pNumber);
		document.getElementById("CashApplyBillsform").submit();
		parent.$("#bankJqm").jqmHide();
	    bokuan="";
		token = 2;
	    //parent.re_load();
	});
	
	
	// 查询总公司下的所有子公司 
	$(function() {
				var url = basePath
						+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
						+ new Date().toLocaleString();
						if(level=='company'||level=='organization'){
							url+="&series=one";
						}
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var companylist = member.companylist;
								var data1 = new Array();
								for (var i = 0; i < companylist.length; i++) {
									data1[i] = {
										id : companylist[i].companyID,
										pid : companylist[i].companyPID,
										text : companylist[i].companyName
									};
								}
								var ts3 = new TreeSelector($("#deptID")[0],
										data1, data1[0].pid);
								ts3.createTree();
								$("#deptID").trigger("change");
							},
							error : function cbf(data) {
								alert("1数据获取失败！");
							}
						});
				// 公司名称change事件
				$("#deptID").change(function() {
							if ($(this).val() != '') {
								bmDept(this.value);
							} else {
								$("option", $("#orgID")).remove();
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
});
// 调拨查询总公司下的所有子公司 
	$(function() {
				var url = basePath
						+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
						+ new Date().toLocaleString();
						if(level=='company'||level=='organization'){
							url+="&series=one";
						}
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var companylist = member.companylist;
								var data1 = new Array();
								for (var i = 0; i < companylist.length; i++) {
									data1[i] = {
										id : companylist[i].companyID,
										pid : companylist[i].companyPID,
										text : companylist[i].companyName
									};
								}
								var ts3 = new TreeSelector($("#cdeptID")[0],
										data1, data1[0].pid);
								
								ts3.createTree();
								$("#cdeptID").trigger("change");
							},
							error : function cbf(data) {
								alert("2数据获取失败！");
							}
						});
				// 公司名称change事件
				$("#cdeptID").change(function() {
							if ($(this).val() != '') {
								var name=$("#cdeptID").find("option:selected").text();
								$("#othercompanyname").val(name.replace("　├",""));
								bmDeptc(this.value);
							} else {
								$("option", $("#corgID")).remove();
							}
						});
				// 部门名称change事件
				$("#corgID").change(function() {
					
					var name=$("#corgID").find("option:selected").text();
					if(name=="全部")
					    {$("#otherdepartmentname").val(name.replace("全部",""));}
					else{$("#otherdepartmentname").val(name.replace("　├",""));}
					var temp = $("#corgID").val();
					if (temp.substring(0, 7) != 'company') {
						getPersonc($("#cdeptID").val(), this.value);
					} else {
						$("#otherstaffname").val("");
						$("option", $("select#cperson")).remove();
						$("#cperson").html("<option value=''>请选择部门</option>");
					}
				});
				// 责任人名称change事件
				$("#cperson").change(function() {
					var name=$("#cperson").find("option:selected").text();
					if(name=="全部")
					{$("#otherstaffname").val(name.replace("全部",""));}
				    else{$("#otherstaffname").val(name.replace("　├",""));}
				});
});
});
function yhzh(ids){
	goodsBillsID=ids;
	$("#yhdaoRu").attr("src",basePath+"ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa");
	$("#yhbankJqm").jqmShow();
}
//----------------------------查询开始------ 根据公司ID和部门ID查询责任人
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

function bmDept(val) {

	$("option", $("#orgID")).remove();
	var url = basePath
			+ "ea/responsibilities/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&date=" + new Date().toLocaleString()+"&series=one"+"&level="+level;
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
						id : $("#deptID").val(),
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
					ts = new TreeSelector($("#orgID")[0], data2, '-1');
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#orgID")).val("");
}
//-----------------------------查询调用js结束------------------
//-----------------------------调拨调用js开始---调拨根据公司ID和部门ID查询责任人
function getPersonc(company, org) {
	$("option", $("select#cperson")).remove();
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
			$("#cperson").html(str);
		}
	});
}

function bmDeptc(val) {

	$("option", $("#corgID")).remove();
	var url = basePath
			+ "ea/responsibilities/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&date=" + new Date().toLocaleString()+"&series=one"+"&level="+level;
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
						id : $("#cdeptID").val(),
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
					ts = new TreeSelector($("#corgID")[0], data2, '-1');
					ts.createTree();
					var name=$("#corgID").find("option:selected").text();
					if(name=="全部")
						{$("#otherdepartmentname").val(name.replace("全部",""));}
					else{$("#otherdepartmentname").val(name.replace("　├",""));}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#corgID")).val("");
}
//-----------------------------调拨调用js结束------------------


window.onbeforeunload=function checkLeave(e){
         	var evt = e ? e : (window.event ? window.event : null); 
         	if(title=='title'&&bokuan=='bokuan')
				evt.returnValue="确定离开当前页面吗？";
};


/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 选择往来单位
	$(".xzwlaw").click(function() {
		trid=$(this).parent().parent().parent().attr("id");
		$("input#ccompanyID", $("table#searchcompany")).val("");
		$("select#contactConnections", $("table#searchcompany")).val("全部");
		$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
		cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
			});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			$("tr #" + contactcID).children("td").each(function() {
				if (this.id == "ccompanyID") {
					$("input#ccompanyID", $("tr#"+trid)).val($(this).text());
				}else if (this.id == "ccompanyname") {
					$("input#ccompanyName", $("tr#"+trid)).val($(this).text());
					$("span#ccompanyName", $("tr#"+trid)).text($(this).text());
				}
			});
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
		quzhi = contactConnections;
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
			var contactConnections = "";
			if (quzhi != "") {
				contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
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
			var contactConnections = "";
			if (quzhi != "") {
				contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
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

function re_load() {
if(cother=="cother")
{document.location.href =basePath+"ea/cashapplybills/ea_toCash.jspa?pageNumber="+pNumber+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&bokuan="+bokuan+"&weibokuan="+weibokuan+"&cother="+cother+"&level="+level+"&str="+str;
}
else{document.location.href =basePath+"ea/cashapplybills/ea_toCash.jspa?pageNumber="+pNumber+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&bokuan="+bokuan+"&weibokuan="+weibokuan+"&other="+other+"&level="+level+"&str="+str;	
}
}	
	