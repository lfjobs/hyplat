$(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector

	
	
	
	var query = "<form method='post' name='searchf' id='searchf'>" +
	"<input type='submit' style='display:none;' name='submit' />" +
	"<input type='hidden' name='search' value='search'/>" +
	"<input type='hidden' name='zctype' value='"+zctype+"'/>" +
	"<input type='hidden' name='yg' value='"+yg+"'/>" +
	"<input type='hidden' name='billsType' value='"+billsType+"'/>" +
	"<table border='0'>" +
	"<tr>" +
	"<td><strong>"+(zctype=="y"?"项目收入预算管理":zctype=="t"?"项目收入预算调整管理":zctype=="p"?"项目跟踪进度":zctype=="dc"?"项目授权分配":zctype=="cg"?"常规项目支出预算":zctype=="fcg"?"非常规项目支出预算":"项目支出预算")+"</strong>&nbsp;&nbsp;&nbsp;&nbsp;项目名称</td><td><input type='text' name='cashierBills.projectName' size='6'/></td>" +
	"<td>项目分类</td><td><input type='hidden' class='xmtype' name='cashierBills.xmtype'/><input type='text' class='xmtypename'  size='6'/>" +
	"</td><td>凭证号</td><td><input type='text'  name='cashierBills.journalNum' size='6'/></td><td>责任人</td><td><input type='text' name='cashierBills.staffName' size='6'/></td>" +
// "<td>制单人</td><td><input type='text' name='cashierBills.inputName'
// size='6'/></td>" +
	"<td>制单时间</td><td><input type='text' onfocus='daytime(this);' size='6' name='startTime'/>至<input type='text' onfocus='daytime(this);' size='6' name='endTime'/></td>" +
// "<td>单据状态</td><td><select name='cashierBills.status'
// style='width:80px;'>"+status+"</select></td>" +
	"<td><input type='button' value='  查询  ' class='input-button' style='margin:2px;'id='tosearch'/>" +
	"<input type='button' value='  刷新  ' class='input-button' style='margin:2px;'  onclick='refress();'/></td>" +
	"</tr></table></form>";
	var bts=new Array();
	if(fgtype!=null&&fgtype!=""){
		if(fgtype == "02"){
			bts.push({name:"提交项目质量考评",bclass:"edit",onpress:action});
			bts.push({separator:true});
		}else if(fgtype == "01"){
			bts.push({name:"项目提交跟踪进度",bclass:"edit",onpress:action});
			bts.push({separator:true});
		}else{//fgtype == "00"
			bts.push({name:"提交项目分配",bclass:"edit",onpress:action});
			bts.push({separator:true});
			bts.push({name:"添加",bclass:"add",onpress:action});
			bts.push({separator:true});
			bts.push({name:"修改",bclass:"edit",onpress:action});
			bts.push({separator:true});
			bts.push({name:"删除",bclass:"delete",onpress:action});
			bts.push({separator:true});
			bts.push({name:"打印",bclass:"printer",onpress:action});
			bts.push({separator:true});
		}
	}else{
		if(yg==1){
			bts.push({name:"查看",bclass:"see",onpress:action});
			bts.push({separator:true});
			bts.push({name:"打印",bclass:"printer",onpress:action});
			bts.push({separator:true});
			bts.push({name:"导出",bclass:"excel",onpress:action});
			bts.push({separator:true});
		}else{
			if(zctype=="t"){
				bts.push({name:"预算调整",bclass:"add",onpress:action});
				bts.push({separator:true});
				bts.push({name:"项目",bclass:"edit",onpress:action});
				bts.push({separator:true});
				bts.push({name:"查看",bclass:"see",onpress:action});
				bts.push({separator:true});
				bts.push({name:"打印",bclass:"printer",onpress:action});
				bts.push({separator:true});
				bts.push({name:"导出",bclass:"excel",onpress:action});
				bts.push({separator:true});
			}else{
				bts.push({name:"添加",bclass:"add",onpress:action});
				bts.push({separator:true});
				bts.push({name:"修改",bclass:"edit",onpress:action});
				bts.push({separator:true});
				bts.push({name:"删除",bclass:"delete",onpress:action});
				bts.push({separator:true});
				bts.push({name:"打印",bclass:"printer",onpress:action});
				bts.push({separator:true});
				if(zctype=="cg"||zctype=="fcg"){
					bts.push({name:"比价审核",bclass:"compare",onpress:action});
					bts.push({separator:true});
				}else{
					bts.push({name:"确定预算",bclass:"edit",onpress:action});
					bts.push({separator:true});
					bts.push({name:"导出",bclass:"excel",onpress:action});
					bts.push({separator:true});
				}
			}
		}
	}
	bts.push({name:"设置每页显示条数",bclass:"mysearch",onpress:action});
	$('.flexme11').flexigrid({
		allDouble : false,
		height : '280',
		width : 'auto',
		minwidth : 30,
		title : query,
		minheight : 80,
		buttons : bts
	});
	
	function action(com, grid) {
		switch (com) {
		case '添加':
			var ul=basePath+"/ea/costsheet/ea_toSaveCostSheet.jspa?jumptype="+jumptype
					+"&billsType="+billsType+"&zctype="+zctype+"&zhuangtai="+zhuangtai
					+"&fgtype="+fgtype;
			window.open(ul);
		
			break;
		case '查看':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			
			getCurrentStatus();
			if(statuscurr=="noexist"){


				alert("该单据不存在");
				token = 2;
        		re_load();
        		return;
			}
				window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + cashierBillsID+"&vuvtype=edit&jumptype="+jumptype+"&billsType="+billsType+"&zctype="+zctype+"&zhuangtai="+zhuangtai);
			break;
        case '预算调整':
			
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + cashierBillsID+"&vuvtype=edit_e&jumptype="+jumptype+"&billsType="+billsType+"&zctype="+zctype);
			break;
		case '修改':
			
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			getCurrentStatus();
			var status = $("tr#"+cashierBillsID).find("span#status").text();
			if(statuscurr!="noexist"){
            if(statuscurr!="00"){
            	if(billsType=="项目收入预算单"){
				    alert("已确定预算不能修改");
            	}
            	if(billsType=="项目支出预算单"){
				    alert("已比价审核不能修改");
            	}
            	if(statuscurr!=status){
            		token = 2;
            		re_load();
            	}
				return;
			}
			}else{
				alert("该单据不存在");
				token = 2;
        		re_load();
        		return;
			}
				window.open(basePath
						+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + cashierBillsID+"&vuvtype=edit_e&jumptype="+jumptype+"&billsType="+billsType+"&zctype="+zctype+"&zhuangtai="+zhuangtai);
			break;
		case '确定预算':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			getCurrentStatus();
			var status = $("tr#"+cashierBillsID).find("span#status").text();
			if(statuscurr!="noexist"){
            if(statuscurr!="00"&&statuscurr!="11"){
           
				  alert("不可重复确认预算");

            	if(statuscurr!=status){
            		token = 2;
            		re_load();
            	}
				return;
			}
			}else{
				alert("该单据不存在");
				token = 2;
        		re_load();
        		return;
			}
			if (confirm("确定收入预算？")) {
				var url = basePath
						+ "ea/costsheet/sajax_ea_confCostSheet.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					data : {
						"cashierBills.cashierBillsID" : cashierBillsID
					},
					success : function cbf(data) {
						alert("操作成功！");
						token = 2;
						re_load();

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
			}
			 break;
		case '比价审核':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			getCurrentStatus();
			var status = $("tr#"+cashierBillsID).find("span#status").text();
			if(statuscurr!="noexist"){
            if(statuscurr!="00"&&statuscurr!="11"){
				  alert("不可重复比价审核");
            	if(statuscurr!=status){
            		token = 2;
            		re_load();
            	}
				return;
			}
			}else{
				alert("该单据不存在");
				token = 2;
        		re_load();
        		return;
			}

			if (confirm("确定比价审核？")) {

				var url = basePath
						+ "ea/costsheet/sajax_ea_confCostSheet.jspa?date="
						+ new Date().toLocaleString();

				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					data : {
						"cashierBills.cashierBillsID" : cashierBillsID
					},
					success : function cbf(data) {
						alert("操作成功！");
						token = 2;
						re_load();

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
			}
			break;
		case '删除':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			getCurrentStatus();
			var status = $("tr#"+cashierBillsID).find("span#status").text();
			
			if(statuscurr!="noexist"){
            if(statuscurr!="00"){
				  alert("不可删除");
            	if(statuscurr!=status){
            		token = 2;
            		re_load();
            	}
				return;
			}
			}else{
				alert("该单据不存在");
				token = 2;
        		re_load();
        		return;
			}
			
			if (confirm("确定删除？")) {
				$("#paystatusID").attr("value", cashierBillsID);
				$("#CashierBillsform").attr("target", "hidden").attr("action",
						basePath + "/ea/costsheet/ea_deteleCostSheet.jspa?");
				document.CashierBillsform.submit.click();
				token = 2;
				
				
			}
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
			getCurrentStatus();
			if(statuscurr=="noexist"){


				alert("该单据不存在");
				token = 2;
        		re_load();
        		return;
			}
			window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID="
					+ cashierBillsID+"&vuvtype=printcsb");
			break;
			
		case '提交项目分配':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
		// if($("#"+cashierBillsID).find("span#paystatus").text() != "01"){
			// alert("！");
				// return;
			// }		
		   if($("#"+cashierBillsID).find("span#paystatus").text()=='01')
		   {
			   alert("项目已完成分配");
			   return;
		   }
		   if($("#"+cashierBillsID).find("span#paystatus").text()=='02')
		   {
			   alert("项目已完成跟踪");
			   return;
		   }
		   if($("#"+cashierBillsID).find("span#paystatus").text()=='03')
		   {
			   alert("项目已完成考评");
			   return;
		   }
		   
			if (confirm("确定提交项目分配？")) {
						$("#paystatusID").attr("value",cashierBillsID);
						$("#CashierBillsform")
							.attr("target", "hidden")
							.attr("action", basePath+"/ea/costsheet/ea_toQpro.jspa?");
						document.CashierBillsform.submit.click();
						}
			token = 2;
			break;
		case '项目提交跟踪进度':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
	// if($("#"+cashierBillsID).find("span#paystatus").text() == "00"){
		// // alert("项目未确立！");
		// return;
		// }else if($("#"+cashierBillsID).find("span#paystatus").text() ==
		// "02"){
		// alert("项目已经完成！");
		// return;
		// }
			if($("#"+cashierBillsID).find("span#paystatus").text() == "01"){
				alert("项目未分配！");
				return;
			}
			if($("#"+cashierBillsID).find("span#paystatus").text()=="02")
				{
				alert("该项目已经提交跟踪进度");	
				return;
				};
				if($("#"+cashierBillsID).find("span#paystatus").text()=="03")
				{
				alert("该项目已经考评");	
				return;
				};
				
			if (confirm("项目确定提交跟踪进度？")) {
			$("#paystatusID").attr("value",cashierBillsID);
			$("#CashierBillsform")
				.attr("target", "hidden")
				.attr("action", basePath+"/ea/costsheet/ea_toWpro.jspa?");
			document.CashierBillsform.submit.click();
			}
			token = 2;
			break;
		case '提交项目质量考评':
			if (cashierBillsID == "") {
				alert("请选择！");
				return;
			}
			if($("#"+cashierBillsID).find("span#paystatus").text() == "01"){
				alert("项目未分配！");
				return;
			}
			if($("#"+cashierBillsID).find("span#paystatus").text() == "02"){
				alert("项目未跟踪！");
				return;
			}else if($("#"+cashierBillsID).find("span#paystatus").text() =="03"){
				alert("项目已经完成考评！");
			 return;
			}
			$("#paystatusID").attr("value",cashierBillsID);
			$("#CashierBillsform")
				.attr("target", "hidden")
				.attr("action", basePath+"/ea/costsheet/ea_toEpro.jspa?");
			document.CashierBillsform.submit.click();
			token = 2;
			break;
			
		case '设置每页显示条数':
			var url =basePath
					+ "/ea/costsheet/ea_getCostSheetList.jspa?"
					+"&search="+ search +"&jumptype=" + jumptype
					+"&billsType="+billsType+"&zctype="+zctype
					+"&yg="+yg+"&fgtype="+fgtype+"&zhuangtai=01";
			numback(url);
			break;
	
	}
	}
	// 这一行的单击事件
	$(".flexme11 tr[id]").dblclick(function() {

		
		cashierBillsID = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		action("查看");


	});
	

	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {

		
		cashierBillsID = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");


	});



	$("#refress").click(function() {
		refress();

	});
	
	
	
	   // 查询按钮单击事件
    $("#tosearch").click(function(){
       $("#searchf").attr("action", basePath+"/ea/costsheet/ea_toSearch.jspa?pageNumber="+pNumber+"&jumptype="+jumptype+"&fgtype="+fgtype);
       document.searchf.submit.click();
   });
    
    
	/**
	 * 
	 * 键入时查询项目分类
	 */
	$(".xmtypename").focus(function(){
		getxmtype($(this));
	}).keyup(function(){
	
		getxmtype($(this));

		
	});
});


function re_load() {
	if (token) {
		document.location.href = basePath
				+ "/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&search="
				+ search +"&jumptype=" + jumptype+"&billsType="+billsType
				+"&zctype="+zctype+"&yg="+yg+"&fgtype="+fgtype
				+"&zhuangtai=01";
	}
}

function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '01') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath + "ea/uploadfile/ .jspa?loadFile.parmeterID=" + cID);
}

function getAllCompanyOfGroups() {
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
			$("#SendForm select#receiverCompanyID").html(str);
			if (receiverCompanyID != "") {
				$(
						"#SendForm select#receiverCompanyID option[value="
								+ receiverCompanyID + "]").attr("selected",
						"selected");

				bmdepts(receiverCompanyID);
			}

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}
function test1(val) {
	// $("#companyID").val(val);
	$("#" + val).empty();
	var url = basePath + "ea/costsheet/sajax_ea_seachgroupsync.jspa?date="
			+ new Date().toLocaleString() + "&companyID=" + val;
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var departmentList = member.departmentList;

			var str1 = "";
			for ( var i = 0; i < departmentList.length; i++) {
				var obj = departmentList[i];
				var orgid = obj.organizationID;
				str1 += "<li><span class='file' onclick='searchRenyuan(\""
						+ val + "\",\"" + orgid + "\");'>"
						+ obj.organizationName + "</span></li>";
			}
			/* $("#SendForm #receiverDeptID").html(str); */
			$("#" + val).append(str1);

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
function bmdepts(val) {

	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
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
			$("#SendForm #receiverDeptID").html(str);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
	if (receiverDeptID != "") {
		$(
				"#SendForm select#receiverDeptID option[value="
						+ receiverDeptID + "]").attr("selected", "selected");
		getPersons(receiverCompanyID, receiverDeptID);
	}

}
// 根据公司部门查询当前所选部门的员工

function searchRenyuan(companyid, org) {
	$("#leftfields").empty();
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffListfordep.jspa?date111="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		data:{
			currentCompanyID:companyid,
			currentOrgnizationID:org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var depstaff = member.list;
			var str = "";
			for ( var i = 0; i < depstaff.length; i++) {
				var obj2 = depstaff[i];
				str += "<option id= '" + obj2.staffID 
						+ "'  value='"+companyid+"-"+org+"-"+obj2.staffID+"'>" + obj2.staffName
						+ obj2.staffCode + "</option>";
			}
			
			$("#leftfields").html(str);

		}
	});
}



// 根据公司ID和部门ID查询收件人
function getPerson(company, org) {
	$("option", $("select#subscriberID")).remove();
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffListforsub.jspa?currentCompanyID="
			+ company + "&currentOrgnizationID=" + org + "&date111="
			+ new Date();
	$
			.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "<option value=''>请选择收件人</option>";
					for ( var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj[0] + "'>" + obj[1]
								+ "</option>";
					}
					$("#subscriberID").html(str);
					if (subscriberIDs != "")
						$(
								"#newForm select#subscriberID option[value="
										+ subscriberIDs + "]").attr("selected",
								"selected");
				}
			});
}

function bmdept(val) {
	$("option", $("#receiverDeptID")).remove();
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
				text : '请选择部门'
			};
			for ( var i = 0; i < oList.length; i++) {
				data2[i + 1] = {
					id : oList[i].organizationID,
					pid : oList[i].organizationPID,
					text : oList[i].organizationName
				};
			}

			ts = new TreeSelector($("#receiverDeptID")[0], data2, -1);
			ts.createTree();
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

	$("option[value=" + this.value + "]", $("#receiverDeptID")).val("");
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
function getPersons(company, org) {

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
			var str = "<option value=''>请选择收件人</option>";
			for ( var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj.staffID + "'>" + obj.staffName
						+ "(" + obj.staffCode + ")</option>";
			}
			$("#SendForm #receiverID").html(str);
		}
	});
	if (receiverID != "") {
		$("#SendForm select#receiverID option[value=" + receiverID + "]").attr(
				"selected", "selected");
	}
}

function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#SendForm #receiverDeptID").html("<option value=''>请选择部门</option>");
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
	var dept = $(obj).val();
	if (dept != "") {
		getPersons($("#SendForm #receiverCompanyID").val(), dept);
	} else {
		$("#SendForm #receiverID").html("<option value=''>请选择人员</option>");
	}
}

function childMenu(companyID) {// 2级
	if ($("ul#" + companyID + ">li").length > 0) {

		return;
	}
	var url2 = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url2),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			companyID : companyID
		},
		success : function cbf(data) {

			/** **添加部门列表** */

			var member = eval("(" + data + ")");
			var orglist = member.orgaizationlist;
			var data = new Array();
			var result = "";
			for ( var i = 0; i < orglist.length; i++) {
				data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				};
				result += "<li class='curor closed'  title='" + data[i].text
						+ "'><span onclick='javascript:getSubDept(\""
						+ companyID + "\",\"" + data[i].id
						+ "\")' class='folder'>" + data[i].text + "</span>";
				result += "<ul id='" + data[i].id + "'></ul></li>";
			}

			$(result).appendTo("#" + companyID);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}

// 根据父部门查询子部门，循环查询
function getSubDept(companyID, deptID) {
	$("#goodsForm #companyIDs").val(companyID);
	$("#goodsForm #deptIDs").val(deptID);

	if ($("ul#" + deptID + ">li").length > 0) {
		getPersonsTerm(companyID, deptID);
		return;
	}
	var url2 = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getSubDeptList.jspa?companyID="
			+ companyID + "&date=" + new Date().toLocaleString();
	$
			.ajax({
				url : encodeURI(url2),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					companyID : companyID,
					checkDeptID : deptID
				},
				success : function cbf(data) {

					/** **添加部门列表** */

					var member = eval("(" + data + ")");
					var orglist = member.organizationlist;

					var data = new Array();
					var result = "";
					for ( var i = 0; i < orglist.length; i++) {
						data[i] = {
							id : orglist[i].organizationID,
							text : orglist[i].organizationName
						};
						result += "<li  class='closed'><span onclick='javascript:getSubDept(\""
								+ companyID
								+ "\",\""
								+ data[i].id
								+ "\")' title='"
								+ data[i].text
								+ "' class='folder'>"
								+ data[i].text
								+ "</span>";
						result += "<ul id='" + data[i].id + "'></ul></li>";
					}
					$(result).appendTo("#" + deptID);
					getPersonsTerm(companyID, deptID);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}

// 查询人使用中；
function getPersonsTerm(companyID, org, pagenumber, search, querytxt) {
	// if (notoken) {
	// alert("正在获取数据！请稍等");
	// return;
	// }
	notoken = 1;
	$("#wpsy1").attr("title", 0);
	$("#wpxy1").attr("title", 0);
	$("#wpzy1").attr("title", 0);
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept2.jspa?date="
			+ new Date() + pagenumber;
	$
			.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				data : {
					"currentCompanyID" : companyID,
					"checkOrgID" : org,
					"search" : search,
					"querytxt" : querytxt
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var pageForm = member.pageForm;
					var companyName = member.companyName;
					var orgName = member.orgName;
					if (pageForm == null) {
						alert("没有数据");
						notoken = 0;
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#wpsy1").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#wpxy1").attr("title", dqy + 1);
					}
					$("span#wpzycount1").text(zys);
					var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择人员</td></tr></table>";
					tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
					tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'>人员编号</th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'> 姓名 </th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'> 性别 </th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'> 身份证 </th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'> 电话 </th>";
					tabletr += "<th align='center' bgcolor='#E4F1FA'> 部门 </th>"
							+ "<th align='center' bgcolor='#E4F1FA'> 所在公司</th>";
					for ( var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' id = "
								+ pageForm.list[i][0] + ">";
						tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
								+ pageForm.list[i][0]
								+ " name='check'/><input type='hidden' id='deptIDss' value='"
								+ pageForm.list[i][8]
								+ "'/>"
								+ "<input type='hidden' id='companyIDss' value='"
								+ pageForm.list[i][9] + "'/></td>";
						tabletr += "<td id='staffCode' align='center'>"
								+ pageForm.list[i][1] + "</td>";
						tabletr += "<td id='staffName'  align='center'>"
								+ pageForm.list[i][2] + "</td>";
						tabletr += "<td id='sex'  align='center'>"
								+ pageForm.list[i][3] + "</td>";
						tabletr += "<td id='staffIdentityCard' align='center'>"
								+ pageForm.list[i][4] + "</td>";
						tabletr += "<td id='reference' align='center'>"
								+ pageForm.list[i][5] + "</td>";
						if (orgName == undefined) {
							tabletr += "<td id='orgName'  align='center'>"
									+ pageForm.list[i][6] + "</td>";
						} else {
							tabletr += "<td id='orgName'  align='center'>"
									+ orgName + "</td>";
						}
						if (companyName == undefined) {
							tabletr += "<td id='companyName' align='center'>"
									+ pageForm.list[i][7] + "</td>";
						} else {
							tabletr += "<td id='companyName' align='center'>"
									+ companyName + "</td>";
						}

						tabletr += " </tr>";
					}
					tabletr += " </table>";
					$("#body_02").html(tabletr);
					$("#body_02").show();
					// alert("数据加载成功");
					notoken = 0;
					window.status = "数据加载成功";
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
}

// 刷新功能
function refress() {
	token = 1;
	re_load();

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
			basePath + "ea/costsheet/ea_zbqSubmitExamine.jspa");
	document.sendForm.submit.click();
	token = 2;
}



// 键入时查询项目分类

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
					// if(xmList[i].isLeaf =="00"){
						str+="&nbsp;&nbsp;<span><a href='#' onclick='selectz(this);'>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</a></span><br/>";
					// }else{
					// str+="&nbsp;&nbsp;<span>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</span><br/>";
					// }
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

// 选中主项目
function selectz(obj){
	$("#jqModel").hide();
	var codeValues=$(obj).text();
	$(".xmtypename").val(codeValues.substring(codeValues.indexOf(")")+1));
	$(".xmtype").val(codeValues.substring(1,codeValues.indexOf(")")));
	
}

// 获取单据真正状态status
function getCurrentStatus(){
	var url = basePath
	+ "ea/costsheet/sajax_ea_getCurrentStatus.jspa?date="
	+ new Date().toLocaleString();
   $.ajax({
    url : encodeURI(url),
    type : "post",
    async : false,
     dataType : "json",
     data : {
	  "cashierBills.cashierBillsID" : cashierBillsID
    },
    success : function cbf(data) {
	var member = eval("(" + data + ")");
	var status = member.result;
	statuscurr = status;

    },error : function cbf(data) {
	      alert("获取状态失败！");
    }
});
	
	
}

