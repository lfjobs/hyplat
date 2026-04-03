$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
		height : 300,
		width : 'auto',
		minwidth : 30,
		title : auditorstatus=='01'?'未审核':auditorstatus=='02'?'已审核':'审核管理',
		minheight : 80,
		buttons : [ {
			name : '驳回',
			bclass : 'delete',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '通过审核并结束',
			bclass : 'edit',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '通过审核并继续审核',
			bclass : 'edit',
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
			name : '导出',
			bclass : 'excel',
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
		case '驳回':
			var str = "";
			var stra="";
			$("[name='chbox']").each(function() {
				if ($(this).is(':checked')) {
					str += $(this).val() + ",";
					auditid = $(this).val();
					if($("#astatus",$("tr#"+auditid)).val()=="01"){
					}else{
						stra=$("#astatus",$("tr#"+auditid)).val();
					}
				}
			});
			if (str == "" || str.length == 0) {
				alert('请选择');
				return
			}
			if(stra!=""){
				alert('选中的任务已经执行过审核操作了不能再进行审核操作了！');
				return;
			}
			$("#jqModelSend2").jqmShow();
			document.SendForm2.reset();
			getAllCompanyOfGroup();
			$("#SendForm2 #auditorstatu").val("03");
			$("#auditid",$("#jqModelSend2")).val(auditid);
			break;
		case '查看':
			var str = "";
			$("[name='chbox']").each(function() {
				if ($(this).is(':checked')) {
					str += $(this).val() + ",";
					auditid = $(this).val();
				}
			});
			if (str == "" || str.length == 0||str.split(",").length>2) {
				alert('请选择单个任务');
				return
			}
			var audit="";
			if($("#astatus",$("tr#"+auditid)).val()=="01"){
				audit="&auditid="+auditid+"&auditurl=%2Fea%2Fcheckmanage%2Fea_getDtMyauditList.jspa%3FpageNumber%3D"
						+ ppageNumber + "%26pageForm.pageNumber%3D"
						+ $("#pageNumber").attr("value")+"%26search%3D"+search+"%26sdate%3D"+sdate+"%26edate%3D"+edate+"%26auditorstatus%3D"+auditorstatus;
			}
			var taskid=$("#"+str).find("#taskid").val();
			document.location.href = basePath
				+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid+audit;
			break;
		case '通过审核并结束':
			var str = "";
			var stra="";
			$("[name='chbox']").each(function() {
				if ($(this).is(':checked')) {
					str += $(this).val() + ",";
					auditid = $(this).val();
					if($("#astatus",$("tr#"+auditid)).val()=="01"){
					}else{
						stra=$("#astatus",$("tr#"+auditid)).val();
					}
				}
			});
			if (str == "" || str.length == 0) {
				alert('请选择');
				return
			}
			if(stra!=""){
				alert('选中的任务已经执行过审核操作了不能再进行审核操作了！');
				return;
			}
			$("#jqModelSend2").jqmShow();
			document.SendForm2.reset();
			getAllCompanyOfGroup();
			$("#auditorstatu",$("#jqModelSend2")).val("02");
			$("#auditid",$("#jqModelSend2")).val(auditid);
			break;
		case '通过审核并继续审核':
			var str = "";
			var stra="";
			$("[name='chbox']").each(function() {
				if ($(this).is(':checked')) {
					str += $(this).val() + ",";
					auditid = $(this).val();
					if($("#astatus",$("tr#"+auditid)).val()=="01"){
					}else{
						stra=$("#astatus",$("tr#"+auditid)).val();
					}
				}
			});
			if (str == "" || str.length == 0) {
				alert('请选择');
				return
			}
			if(stra!=""){
				alert('选中的任务已经执行过审核操作了不能再进行审核操作了！');
				return;
			}
			$("#jqModelSend").jqmShow();
			document.SendForm.reset();
			getAllCompanyOfGroup();
			$("#auditid",$("#SendForm")).val(auditid);
			break;
		case '查询':
			$("#jqModelsubmit").jqmShow();
			break;
		case '导出':
			var url = basePath + "/ea/checkmanage/ea_showExcelDtMyaudit.jspa?search=" + search
						+ "&sdate=" + sdate + "&edate=" + edate+"&auditorstatus="+auditorstatus;
			open(url);
			break;	
		case '设置每页显示条数':
			var url = basePath
					+ "/ea/checkmanage/ea_getDtMyauditList.jspa?search=" + search
						+ "&sdate=" + sdate + "&edate=" + edate+"&auditorstatus="+auditorstatus;
			numback(url);
			break;
		}
	}
	
	$("input.JQueryreturn").click(function() {// 取消
		$("#jqModel").jqmHide();
		re_load();
	});
	$(".close").click(function() {// 取消
		$("#jqModel").jqmHide();
		re_load();

	});
	
	$(".checkAll").click(function(){
		$("input[name='chbox']").attr("checked",this.checked);
	});
	$("input[name='chbox']").click(function(){
		var checkedlen  = $("input[name='chbox']").filter(":checked").length;
		checkedlen>=$("input[name='chbox']").length?$(".checkAll").attr("checked",true):$(".checkAll").attr("checked",false);

		});
	
	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});

	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	
	$("#JQuerySubmit").click(function() {
		$("#cstaffForm").attr(
				"action",
				basePath+"/ea/checkmanage/ea_toSearchByDtMyaudit.jspa?pageNumber="
						+ ppageNumber+"&auditorstatus="+auditorstatus);
		document.cstaffForm.submit.click();
	});
	
	$("#submitResult").click(function(){
		if ($("#SendForm #auditorid").val() == "") {
			alert("请选择审核人");
			return;
		}
		if ($("#SendForm #comments").val() == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		 }
		if (confirm("确认要审核通过并继续审核？")) {
			$("[name='chbox']").each(function(index) {
				if ($(this).is(':checked')) {
					var value=$(this).val();
					$("<input>",{type:"hidden",value:value,name:"dtMyaudit2Map[" + index + "].auditid"}).appendTo($("form#SendForm"));
				}
			});
			var index=0;
			var auditorcompanyid=$("select#auditorcompanyid").find("option:selected").val();
			var auditorcompanyname=$("select#auditorcompanyid").find("option:selected").text();
			var auditororgID=$("select#auditororgID").find("option:selected").val();
			var auditororgName=$("select#auditororgID").find("option:selected").text();
			var auditorid=$("select#auditorid").find("option:selected").val();
			var auditorname=$("select#auditorid").find("option:selected").text();
			var comments=$("#SendForm #comments").val();
			$("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMyauditMap[" + index + "].auditorcompanyid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMyauditMap[" + index + "].auditorcompanyname"}).appendTo($("form#SendForm"));	
			$("<input>",{type:"hidden",value:auditororgID,name:"dtMyauditMap[" + index + "].auditororgID"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditororgName,name:"dtMyauditMap[" + index + "].auditororgName"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorid,name:"dtMyauditMap[" + index + "].auditorid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorname,name:"dtMyauditMap[" + index + "].auditorname"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:comments,name:"dtMyauditMap[" + index + "].comments"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:"01",name:"dtMyauditMap[" + index + "].auditorstatus"}).appendTo($("form#SendForm"));
			$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/checkmanage/ea_audit.jspa?date="
										+ new Date());
			document.SendForm.submit.click();
			token = 2;
		}
	});
	
	$("#submitResult2").click(function(){
		if ($("#SendForm2 #comments").val() == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		 }
		if (confirm("确定执行该操作？")) {
			$("[name='chbox']").each(function(index) {
				if ($(this).is(':checked')) {
					var value=$(this).val();
					$("<input>",{type:"hidden",value:value,name:"dtMyaudit2Map[" + index + "].auditid"}).appendTo($("form#SendForm2"));
				}
			});
			var index=0;
			var comments=$("#SendForm2 #comments").val();
			var auditorstatus=$("#SendForm2 #auditorstatu").val();
			$("<input>",{type:"hidden",value:comments,name:"dtMyauditMap[" + index + "].comments"}).appendTo($("form#SendForm2"));
			$("<input>",{type:"hidden",value:auditorstatus,name:"dtMyauditMap[" + index + "].auditorstatus"}).appendTo($("form#SendForm2"));
			$("#SendForm2").attr("target", "hidden").attr("action",
					basePath + "/ea/checkmanage/ea_audit.jspa?pageNumber="
							+ ppageNumber);
			document.SendForm2.submit2.click();
			token = 2;
		}
	});

	
	
});
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
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName + "'value='"
								+ obj.companyID + "'>" + obj.companyName
								+ "</option>";
					}
					$("#SendForm select#auditorcompanyid").html(str);
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
	
	$("option", $("#auditororgID")).remove();

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
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#SendForm #auditororgID").html(str);

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
	$("option", $("select#auditorid")).remove();

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
					var str = "<option value=''>请选择审核人</option>";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
					}
					$("#SendForm #auditorid").html(str);
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
		$("#auditororgID").html("<option value=''>请选择部门</option>");
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
	var dept = $("#SendForm #auditororgID").val();
	if (dept != "") {
		getPerson($("#SendForm #auditorcompanyid").val(), dept);
	} else {
		$("#SendForm #auditorid").html("<option value=''>请选择人员</option>");
	}
}
function re_load() {
	if (token){
		document.location.href = basePath
				+ "/ea/checkmanage/ea_getDtMyauditList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&auditorstatus="+auditorstatus;
	};
};