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
		buttons : auditorstatus=="01"?([{
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
		}]):([{
			name : '查看',
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
			name : '打印预览',
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
		}])
	});
	function action(com, grid) {
		switch (com) {
			case '驳回':
				var str = "";
				var stra="";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						checkid = $(this).val();
						if($("#status",$("tr#"+checkid)).val()=="01"){
						}else{
							stra=$("#status",$("tr#"+checkid)).val();
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
				$("#checkid",$("#jqModelSend2")).val(checkid);
				break;
			case '查看':
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						checkid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				
				var url=$("#"+str).find("#lookOverurl").val();
				if($("#status",$("tr#"+checkid)).val()=="01"){
					url+="&checkid="+checkid+"&checkurl=%2Fea%2Freceiptcheck%2Fea_getDtMycheckList.jspa%3FpageNumber%3D"
						+ ppageNumber + "%26pageForm.pageNumber%3D"
						+ $("#pageNumber").attr("value")+"%26search%3D"+search+"%26sdate%3D"+sdate+"%26edate%3D"+edate+"%26auditorstatus%3D"+auditorstatus;
				}
				document.location.href = basePath+url;
				break;
			case '通过审核并结束':
				var str = "";
				var stra="";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						checkid = $(this).val();
						if($("#status",$("tr#"+checkid)).val()=="01"){
						}else{
							stra=$("#status",$("tr#"+checkid)).val();
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
				$("#checkid",$("#jqModelSend2")).val(checkid);
				break;
			case '通过审核并继续审核':
				var str = "";
				var stra="";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						checkid = $(this).val();
						if($("#status",$("tr#"+checkid)).val()=="01"){
						}else{
							stra=$("#status",$("tr#"+checkid)).val();
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
				$("#checkid",$("#SendForm")).val(checkid);
				break;
			case '查询':
				$("#jqModelsubmit").jqmShow();
				break;
			case '打印':
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						checkid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				var url=$("#"+str).find("#printurl").val();
				open(basePath+url);
				break;
			case '导出':
				var url = basePath + "/ea/receiptcheck/ea_toExportExcelByCheck.jspa?search=" + search
							+ "&sdate=" + sdate + "&edate=" + edate+"&auditorstatus="+auditorstatus+"&receiptType="+receiptType;
				open(url);
				break;
			case '设置每页显示条数':
				var url = basePath
						+ "/ea/receiptcheck/ea_getDtMycheckList.jspa?search=" + search
							+ "&sdate=" + sdate + "&edate=" + edate+"&auditorstatus="+auditorstatus+"&receiptType="+receiptType;
				numback(url);
				break;
			case '查看历史单据':
				document.location.href = basePath
					+ "/ea/receiptcheck/ea_getDtMycheckList.jspa?history=history&auditorstatus="+auditorstatus;
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
				basePath+"/ea/receiptcheck/ea_toSearchByDtMycheck.jspa?pageNumber="
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

		if (confirm("确认要审核通过并继续审核？")) {
			$("[name='chbox']").each(function(index) {
				if ($(this).is(':checked')) {
					var value=$(this).val();
					$("<input>",{type:"hidden",value:value,name:"dtMycheck2Map[" + index + "].checkid"}).appendTo($("form#SendForm"));
				}
			});
			var index=0;
			var auditorcompanyid=$("select#auditorcompanyid").find("option:selected").val();
			var auditorcompanyname=$("select#auditorcompanyid").find("option:selected").text();
			var auditororgid=$("select#auditororgID").find("option:selected").val();
			var auditororgname=$("select#auditororgID").find("option:selected").text();
			var auditorid=$("select#auditorid").find("option:selected").val();
			var auditorname=$("select#auditorid").find("option:selected").text();
			var comments=$("#SendForm #comments").val();
			$("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMycheckMap[" + index + "].auditorcompanyid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMycheckMap[" + index + "].auditorcompanyname"}).appendTo($("form#SendForm"));	
			$("<input>",{type:"hidden",value:auditororgid,name:"dtMycheckMap[" + index + "].auditororgid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditororgname,name:"dtMycheckMap[" + index + "].auditororgname"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorid,name:"dtMycheckMap[" + index + "].auditorid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorname,name:"dtMycheckMap[" + index + "].auditorname"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:comments,name:"dtMycheckMap[" + index + "].comments"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:"01",name:"dtMycheckMap[" + index + "].auditorstatus"}).appendTo($("form#SendForm"));
			$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/receiptcheck/ea_audit.jspa?date="
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
		if (confirm("确定执行该操作？")) {
			$("[name='chbox']").each(function(index) {
				if ($(this).is(':checked')) {
					var value=$(this).val();
					$("<input>",{type:"hidden",value:value,name:"dtMycheck2Map[" + index + "].checkid"}).appendTo($("form#SendForm2"));
				}
			});
			var index=0;
			var comments=$("#SendForm2 #comments").val();
			var auditorstatus=$("#SendForm2 #auditorstatu").val();
			$("<input>",{type:"hidden",value:comments,name:"dtMycheckMap[" + index + "].comments"}).appendTo($("form#SendForm2"));
			$("<input>",{type:"hidden",value:auditorstatus,name:"dtMycheckMap[" + index + "].auditorstatus"}).appendTo($("form#SendForm2"));
			$("#SendForm2").attr("target", "hidden").attr("action",
					basePath + "/ea/receiptcheck/ea_audit.jspa?pageNumber="
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
	
	$("option", $("#auditororgid")).remove();
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
					$("#SendForm #auditororgid").html(str);

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
		$("#auditororgid").html("<option value=''>请选择部门</option>");
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
	var dept = $("#SendForm #auditororgid").val();
	if (dept != "") {
		getPerson($("#SendForm #auditorcompanyid").val(), dept);
	} else {
		$("#SendForm #auditorid").html("<option value=''>请选择人员</option>");
	}
}
function re_load() {
	if (token){
		document.location.href = basePath
				+ "/ea/receiptcheck/ea_getDtMycheckList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&auditorstatus="+auditorstatus;
	};
};