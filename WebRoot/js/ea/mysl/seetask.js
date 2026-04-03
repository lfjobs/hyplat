// 新建任务页面的js

$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	// 获取历史记录
	// ajaxHistoryRecord(taskid);
	// 初始化数据
	updatefz();
	

	
		//上传附件按钮
	$("#uploadattach").click(function() {

				$("#jqModelUpload").jqmShow();

			});
	
	if(auditid!=null&&auditid!=""){
		
		$("#anniu").show();
	}
	
	$("#bh").click(function (){
		$("#jqModelSend2").jqmShow();
		document.SendForm2.reset();
		getAllCompanyOfGroup();
		$("#SendForm2 #auditorstatu").val("03");
	});
	
	$("#jx").click(function (){
		$("#jqModelSend").jqmShow();
		document.SendForm.reset();
		getAllCompanyOfGroup();
	});
	
	$("#js").click(function (){
		$("#jqModelSend2").jqmShow();
		document.SendForm2.reset();
		getAllCompanyOfGroup();
		$("#auditorstatu",$("#jqModelSend2")).val("02");
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
			var index=0;
			
			var auditorcompanyid=$("select#auditorcompanyid").find("option:selected").val();
			var auditorcompanyname=$("select#auditorcompanyid").find("option:selected").text();
			var auditororgID=$("select#auditororgID").find("option:selected").val();
			var auditororgName=$("select#auditororgID").find("option:selected").text();
			var auditorid=$("select#auditorid").find("option:selected").val();
			var auditorname=$("select#auditorid").find("option:selected").text();
			var comments=$("#SendForm #comments").val();
			$("<input>",{type:"hidden",value:auditid,name:"dtMyaudit2Map[" + index + "].auditid"}).appendTo($("form#SendForm"));
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
			var index=0;
			var comments=$("#SendForm2 #comments").val();
			var auditorstatus=$("#SendForm2 #auditorstatu").val();
			
			$("<input>",{type:"hidden",value:comments,name:"dtMyauditMap[" + index + "].comments"}).appendTo($("form#SendForm2"));
			$("<input>",{type:"hidden",value:auditorstatus,name:"dtMyauditMap[" + index + "].auditorstatus"}).appendTo($("form#SendForm2"));
			$("<input>",{type:"hidden",value:auditid,name:"dtMyaudit2Map[" + index + "].auditid"}).appendTo($("form#SendForm2"));
			$("#SendForm2")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/checkmanage/ea_audit.jspa?date="
										+ new Date());
			document.SendForm2.submit2.click();
			token = 2;
		}
	});
});

// 修改时赋值
function updatefz() {
	
	//归档查看附件隐藏
	if(jieduan=="04"){
		$("#fj").hide();
	}
	
	//为select赋值
	$("#newForm").find("select").each(function() {

				var id = $(this).attr("id");
				if ($("." + id).val() != "") {

					$("#" + id).val($("." + id).val());
				}

			});

	//给附件名称变色
	$("#attachtbl").find("tr").each(function() {
				if ($(this).find(".statuss").text() == "00") {
					$(this).find(".filenames").css("color", "blue");
				} else {
					$(this).find(".filenames").css("color", "#9D9D9D");
				}

			});
	//隐藏显示历史表
		if(!$("#spls").has('tr').length)	
		{
			$("#audithistory").hide();
		}else{
			$("#audithistory").show();
			
		}
		
			if(!$("#attachtbl").has('tr').length)	
		{
			//$("#attachhistory").hide();
		}else{
			$("#attachhistory").show();
			
		}
		
		
}



// 获取具体模板停用了
function getTemplate(filetype) {
	var url = basePath + "ea/taskmanage/sajax_ea_getOfficeTemplate.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"mytask.filetype" : filetype

				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var templist = member.templist;
					var docPath = member.docPath;
					var filetype = member.filetype;
					var str = "";
					for (var i = 0; i < templist.length; i++) {
						var obj = templist[i];

						str += "<option  title ='" + obj.templateId
								+ "' value='" + obj.templateId + "'>"
								+ obj.fileShowName + "</option>";
					}
					$("#templateid").html(str);
					if ($(".attachpath").val() != ""
							&& filetype == $(".filetype").val()) {
						docPath = $(".attachpath").val();

						$("#templateid").val($(".templateid").val());

					}
					$("#attachpath").val(docPath);

					// // 赋初始页面路径
					// $("#office").attr(
					// "src",
					// basePath + "page/mysl/office.jsp?fileType="
					// + filetype + "&docPath=" + docPath);

				}
			});
}

function tosave() {

	$("#uploadForm").attr("target", "hidden").attr("action",
			basePath + "ea/taskmanage/ea_saveUploadAttach.jspa");
	document.uploadForm.submit.click();
	token = 2;
}

function re_load() {
	if(auditurl!=null&&auditurl!=""){
		document.location.href = basePath+ auditurl;
	}else{
		if (token) {

			document.location.href = basePath
					+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid=" + taskid+"&editOrAdd="+editOrAdd;

		}
	}
	
}

// 下载

function download(docPath) {

	window.open(basePath + "/servlets/render?filename=" + encodeURI(docPath));

}

// 操作附件，作废恢复
function operateAttach(attachid) {
	$("#attachid").val(attachid);
	$("#uploadForm").attr("target", "hidden").attr("action",
			basePath + "ea/taskmanage/ea_operateAttach.jspa");
	document.uploadForm.submit.click();
	token = 2;
}

function attachRecord(){
	
	$("#jqModel").jqmShow();
}

function circulated(){
	
	$("#jqModel1").jqmShow();
}

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
