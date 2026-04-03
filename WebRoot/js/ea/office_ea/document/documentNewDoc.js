	//甲方乙方处理
	var contype = "";
	var contype2= "";
$(document).ready(function() {
	
	if(journalNum!=""){
		$("#journalNum").val(journalNum);
		$("#projectName").val(projectName);
	}
	
	
	    var arrayText = new Array("董事会会议决定文件","董事长办公室文件","总裁办公室文件","总部人事处文件","总部办公室文件","总部财务处文件","总部教务(生产)处文件","总部营销处文件","总部服务(创收)平台","总部教务部文件");
	    var arrayValue = new Array("aa","bb","cc","dd","ee","ff","gg","hh","ii","jj");
	    for(var i = 0;i<arrayValue.length;i++){
	       if(arrayValue[i]==docType){
	        $("#docTypeText").val(arrayText[i]);
	       break;
	       }
	    }
	
	
	getAllCompanyOfGroups();
	// 文件类型
	$("#newForm #fileType").change(function() {
				if ($(this).val() != '') {
					getSpecialTemplate(this.value);
				} else {
					$("option", $("#specificTemplate")).remove();
					$("#specificTemplate")
							.html("<option value=''>请选择具体模板</option>");
				}
			});

	// //////////////////////////////////////初始化设置开始///////////////////////////////////////////////
	// 判断是否是收件箱的修改
	if (module == "contract") {
		$(".trtip").show();
		if (type == "send") {
			$("#storedoc").hide();
		} else {
			$("#storedoc").show();
		}
		if (type == "receiver") {
			$("#toplate").hide();
		} else {
			$("#toplate").show();
		}
	} else {
		$(".trtip").hide();
		$("#toplate").hide();
	}
	// 控制驳回时审批人意见的显示；
	if (status == "R") {
		$(".subcoment").show();
	} else {
		$(".subcoment").hide();
	}
	// commonComment的显示
	if (commonComment == "") {
		$(".ccment").hide();
	} else {
		$(".ccment").show();
	}
	

	


	// //////////////////////////////////////初始化设置结束///////////////////////////////////////////////

	// 点击文件名查看附件
	$("#filename").click(function() {
				var result = $("input[type=radio].template").val();
				
				var docPath = $("#filepath").val();
				var filename = $("#filename").text();
				var fileShowNameField = filename.substr(0, filename
								.lastIndexOf("."));
				var fileType = $("#fileType").val();
				ViewOffice(docPath, fileType);

			});
	// 移除附件
	$("input#move").click(function() {
			//	if (confirm("确定要移除该附件?")) {
					$("#newForm #filename").text("");
					$("#filepath").val("");
					$("#newForm #fileId").val("");
					$("#newForm #title").val("");
					$("#newForm #theme").val("");
					$("#newForm #emergencyType").val("");
					$("tr#view").hide();
					$(".temptr").show();
					// $("#specificTemplate").attr("disabled", false);
					// $("#fileType").attr("disabled", false);
					$("#newForm .filetype").show();
					return;
			//	}

			});

	// 至领导审批公文（启动工作流）
	$("#senddoc").click(function() {
				if (!validatepage())
					return;
				$("div#titlem").text("至领导审批");
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();

			});

	// 至领导审批提交和传阅
	$("#submitResult").click(function() {

        var radio = $(".source:checked").val();

        if(radio=="in") {
            if ($("#SendForm #receiverID").val() == "") {
                alert("请选择收件人");
                return;
            }else{
                $("#newForm #receiverCompanyID2").val($("#SendForm #receiverCompanyID")
                    .val());
                $("#newForm #receiverDeptID2").val($("#SendForm #receiverDeptID").val());
                $("#newForm #receiverID2").val($("#SendForm #receiverID").val());
                $("#SearchTable").remove();
            }
        }else{
            if($("#receiverid").val()==""){
                alert("请填写收件人");
                return;
            }else{
                $("#newForm #receiverCompanyID2").val($("#SendForm #comid")
                    .val());
                $("#newForm #receiverDeptID2").val($("#SendForm #deptid").val());
                $("#newForm #receiverID2").val($("#SendForm #receiverid").val());
                $("#SearchTable2").remove();
            }

        }

		if ($("div#titlem").text() == "至领导审批") {
		//	if (confirm("确定发送至领导审批")) {
				$("#newForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentflow/ea_createDocument.jspa?date="
										+ new Date());
				document.newForm.submit.click();
				token = 2;

			//}

		} else {
		//	if (confirm("确定传阅草稿")) {
				$("#newForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentinfo/ea_passDraftDocuments.jspa?date="
										+ new Date());
				document.newForm.submit.click();
				token = 2;
		//	}

		}

	});
	

	// 传阅草稿
	$("#topass").click(function() {
				if (!validatepage())
					return;
				$("div#titlem").text("传阅草稿");
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();

			});
	// 点击传至信息平台显示弹出框
	$("#toplate").click(function() {
				if (!validatepage())
					return;
				$("#jqModelInfo").jqmShow();
			});

	// 点击马上添加显示添加表单
	$("#rightadd").click(function() {
				if ($(this).text() == "取消添加") {
					$(this).text("马上添加");
					$("#addform").hide();
					$("#addsubmit").show();
				} else {
					$(this).text("取消添加");
					$("#addform").show();
					$("#addsubmit").hide();
				}

			});

	// 选择方法一的提交
	$("#addInfoForm #addsubmit").click(function() {
		var stelphone = $("#addInfoForm #historyinfo").find("option:selected")
				.val();
		var smname = $("#addInfoForm #historyinfo").find("option:selected")
				.text();
		if (stelphone == "") {
			alert("请选择社会人员");
			return;
		}
		$("#socialName").val(smname);
		$("#InfoForm #socials").val(stelphone);
		$("#jqModeladdInfo").jqmHide();
		$("#jqModelInfo").jqmShow();

	});

	setSelectEditValue(docTypeSel, emergencyTypeSel, fileType, template);
	hidetemp(fileId);

	// ****************************************************************************************************************

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	// 临时添加表单验证
	$("#addInfoForm :input").bind("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".put3")) {
			if ($.trim(inputValue) == "") {
				$parent
						.append("<span class=\"error\"><a  class=\"tex\">不能为空</a></span>");
				return;
			} else {

				$parent
						.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");

			}
		}
		if ($input.is(".tel")) {
			if ($.trim(inputValue) == "") {
				$parent
						.append("<span class=\"error\"><a  class=\"tex\">不能为空</a></span>");
				return;
			} else {
				reg = /^(13[0-9]|15[0|2|3|6|7|8|9]|18[7|8|9])\d{8}/;
				if (!reg.test(inputValue)) {
					$parent
							.append("<span class=\"error\"><a  class=\"tex\">号码不合法</a></span>");
					return;
				} else if (checkTel($.trim(inputValue)) == 1) {
					$parent
							.append("<span class=\"error\"><a  class=\"tex\">号码已存在</a></span>");
				} else {
					$parent
							.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
				}
			}
		}

	});

});


// 点击临时添加
function addSociety() {
	$("#jqModelInfo").jqmHide();
	$("#jqModeladdInfo").jqmShow();
	$("#addform").hide();
	$(".hr2").hide();
	$("#rightadd").text("马上添加");
	getSocialMember();
	$("#addsubmit").show();
}

// 添加社会人员信息
function addInfo() {
	$("#addInfoForm input").trigger("blur");

	if ($("form .error").length) {
		return;
	}
	var smName = $.trim($("#addform #smName").val());
	var scompany = $.trim($("#addform #scompany").val());
	var stelphone = $.trim($("#addform #stelphone").val());
	var url = basePath + "ea/documentinfo/sajax_ea_addSocialMember.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					"socialMemberInfo.smName" : smName,
					"socialMemberInfo.companyName" : scompany,
					"socialMemberInfo.telphone" : stelphone
				},
				success : function(data) {
					$("#InfoForm #socialName").val(smName + "(" + scompany
							+ ")");
					$("#InfoForm #socials").val(stelphone);
					$("#jqModeladdInfo").jqmHide();
					$("#jqModelInfo").jqmShow();
				},
				error : function(data) {
					alert("添加社会人员失败");
				}

			});

}
// 获得所有社会人员信息
function getSocialMember() {
	var url = basePath + "ea/documentinfo/sajax_ea_getSocialMember.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var smlist = member.result;
					var str = "<option value=''>请选择社会人员</option>";
					for (var i = 0; i < smlist.length; i++) {
						var obj = smlist[i];
						str += "<option title=" + obj.smName + "("
								+ obj.companyName + ") value = " + obj.telphone
								+ ">" + obj.smName + "(" + obj.companyName
								+ ")</option>";
					}
					$("#addInfoForm #historyinfo").html(str);
				},
				error : function(data) {
					alert("读取数据失败");
				}
			});
}

// 修改时若有附件隐藏模板
function hidetemp(fileId) {
	if (fileId != "") {
		$("tr.temptr").hide();
		$(".filetype").hide();
		$("tr#view").show();
	} else {
		if ($("#newForm #type").val() == "send") {
			$(".filetype").hide();
			$("tr.temptr").hide();
			$("tr#view").show();
		} else {
			$("tr.temptr").show();
			$("tr#view").hide();
			$(".filetype").show();
		}
	}
}

// 起草公文
function DraftDocument() {
	
	var specificTemplate = $("#specificTemplate").val();
	if (specificTemplate == "") {
		alert("请选择具体模板！");
		return;
	}
	var fileType = $("select#fileType").val();
	var url = basePath + "ea/zoffice/sajax_ea_createOfficeByTemp.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"templateId" : specificTemplate,
			"fileType" : fileType
		},
		success : function cbf(data) {
			var jsonresult = eval("(" + data + ")");
			var docPath = jsonresult.result;
			if (docPath != "failed") {
				

		
				window.open(encodeURI(basePath
										+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
										+ docPath+"&fileType=" + fileType+"&isRead=2&stage=拟稿"));
	
			
			} else {
				alert("获取模板失败！");
			}
		},
		error : function cbf(data) {
			alert("数据获取失败gege！");
		}
	});

}


function closeOpen(returnvar){
		for(id in returnvar){
			if(id!=""){
				if($.trim(returnvar[id])!=""){
					  $("#"+$.trim(id)).val($.trim(returnvar[id]));
					 
				}
			 
			}


		}
		var fileShowName = $.trim(returnvar.title);
		var fileType = $("select#fileType").val();
		var ext;
		if (fileType == "W") {
			ext = ".doc";
		} else {
			ext = ".xls";
		}
		$("#filename").text(fileShowName + ext);
		$("#filenamehid").text(fileShowName);
		$("#filepath").val(returnvar.docPath);

		$("#view").show();
		$(".temptr").hide();

		$("#newForm .filetype").hide();





	
	
}

// 传送草稿到公共信息平台
function passDraftToComPlat() {
	var social = $("#InfoForm #socials").val();
	if (social == "") {
		alert("请选择人员");

		return;
	}
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			length += 1;
			values += checkinput[i].value + ",";
		}
	}
	if (length == 0) {
		alert("请选择交互平台");
		return;
	}

	var visitType = $("input[name=inputRadio]:radio:checked").val();

	$("#newForm #social2").val(social);
	$("#newForm #social3").val(social);
	$("#newForm  #web2").val(values);
	$("#newForm #visitType2").val(visitType);
	//if (confirm("确定要传递至信息平台？")) {
		$("#newForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/documentinfo/ea_passDraftToComPlat.jspa?inner=inner&date="
								+ new Date());
		document.newForm.submit.click();
		token = 2;
	//}
}

// 点击查看office
function ViewOffice(docPath, fileType) {

	


	 window
			.open(
					encodeURI(basePath
							+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
							+ docPath+"&fileType=" + fileType+"&isRead=2&stage=拟稿"));

}


function getParam(){
var obj = new Object();
	
	$("#newForm tr.g").find(":input").each(function(){
		
		if(this.id!=""&&$(this).val()!=""){
		   obj[this.id]=$(this).val();
		}
	});
	obj["module"]=module;
	return obj;
}

// 存草稿
function storeDraft() {
	if (!validatepage())
		return;
	$("#newForm").attr("target", "hidden").attr(
			"action",
			basePath + "ea/documentinfo/ea_storeDocument.jspa?date="
					+ new Date());

	document.newForm.submit.click();
	token = 2;
}

// 添加主题词打开界面
//function addTheme() {
//	document.themeForm.reset();
//	$("#jqModeltheme").jqmShow();
//}

// 提交添加主题词
//function submitTheme() {
//	var theme = $("#addtheme").val();
//	var url = basePath + "ea/documentinfo/sajax_ea_AddTheme.jspa?date="
//			+ new Date();
//	$.ajax({
//				url : encodeURI(url),
//				type : "post",
//				async : true,
//				dataType : "json",
//				data : {
//					theme : theme
//				},
//				success : function cbf(data) {
//					var member = eval("(" + data + ")");
//					var result = member.result;
//					var theme = member.theme;
//					if (result == "fail") {
//						alert("已存在该主题");
//						return;
//					}
//					$("#jqModeltheme").jqmHide();
//					$op = $("<option/>");
//					$op.attr("value", theme).text(theme);
//					$("#theme", "#newForm").append($op);
//					alert("操作成功！");
//
//				},
//				error : function cbf(data) {
//					alert("添加主题失败！")
//				}
//			});
//}


// 修改时设置select的所选项
function setSelectEditValue(docTypeSel, emergencyTypeSel, fileType, template) {
	if (docTypeSel != "") {
		$("select#docType option[value=" + docTypeSel + "]").attr("selected",
				"selected");
		// $("#specificTemplate").attr("disabled", true);
		// $("#fileType").attr("disabled", true);
		// $("#newForm .filetype").hide();
	} else {
		$("#newForm .filetype").show();
	}

	if (emergencyTypeSel != "") {
		$("select#emergencyType option[value=" + emergencyTypeSel + "]").attr(
				"selected", "selected");
	}
	if (fileType != "") {
		// 初始化设置文件类型
		$("#newForm select#fileType option[value=" + fileType + "]").attr(
				"selected", "selected");
	}
	if (template != "") {
		// 初始化设置文件类型
		getSpecialTemplate(fileType);
		$("#newForm select#specificTemplate option[value=" + template + "]")
				.attr("selected", "selected");
	}



}

// 验证
function validatepage() {
	var title = document.getElementById("title").value;
	var docType = document.getElementById("docType").value;
	var numWord = document.getElementById("numWord").value;
	var theme = document.getElementById("theme").value;
	var emergencyType = document.getElementById("emergencyType").value;
	var fileType = document.getElementById("fileType").value;
	var tempalte = document.getElementById("specificTemplate").value;
	var appendComment = $("#appendComment").val();
	
	if (title == "自动与附件同步生成" || title == "") {
		alert("请起草公文");
		return false;

	}
	if (theme == "") {
		alert("请选择主题");
		return false;

	} else if ((theme.length) * 2 >= 50) {
		alert("文件主题不能大于50");
		return false;
	}
	if (emergencyType == "") {
		alert("请选择文件缓急");
		return false;

	}
	if (numWord == "") {
		alert("请输入正式编号");
		return false;

	} else if ((numWord.length) * 2 >= 36) {
		alert("文件编号字不能大于36");
		return false;
	}
	
    if(contype=="1"){
    	if($("#partyAName").val()==""){
    		alert("请选择甲方");
    		return false;
    	}
    }else if(contype=="2"){
    	if($("#partyAstaffnames").val()==""){
    		alert("请选择甲方");
    		return false;
    	}
    }
    
     if(contype2=="1"){
    	if($("#partyB").val()==""){
    		alert("请选择乙方");
    		return false;
    	}
    }else if(contype2=="2"){
    	if($("#partyBstaffnames").val()==""){
    		alert("请选择乙方");
    		return false;
    	}
    }
	if (docType == "") {
		alert("请选择公文类型");
		return;
	}
	if (fileType == "") {
		alert("请选择文件格式");
		return;
	}
	// if (tempalte == "") {
	// 	alert("请选择具体模板");
	// 	return;
	// }

	if ((appendComment.length) * 2 >= 500) {

		alert("意见长度不能大于500");
		return;
	}

	return true;
}

// 验证
function validateReceiver() {
	var receiverID = document.getElementById("receiverID").value;

	if (receiverID == "") {
		alert("请选择收件人");
		return false;

	}

	return true;
}

// 验证电话号码不可重复
function checkTel(telphone) {
	var num = 0;
	var url = basePath + "ea/documentinfo/sajax_ea_checktel.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				datatype : "json",
				data : {
					"socialMemberInfo.telphone" : telphone
				},
				success : function(data) {
					var memeber = eval("(" + data + ")");
					var result = memeber.result;
					if (result == "fail") {
						num = 1;
					}
				},
				error : function(data) {
					alert("验证电话失败");
				}

			});

	return num;
}

// 获取草稿或者收件箱列表
// type:draft;receiver;send
function re_load() {

	var type = $("#type").val();
	if (token) {
		if (type == "draft" || type == "draftupdate") {
			document.location.href = basePath
					+ "ea/documentinfo/ea_getDocDraftList.jspa";
		} else if (type == "receiver") {
			document.location.href = basePath
					+ "ea/documentinfo/ea_getReceiveBoxList.jspa";
		} else {
			document.location.href = basePath
					+ "ea/documentinfo/ea_getSendedDocList.jspa";
		}
	}
}

// 根据文件格式获取相应模板
function getSpecialTemplate(fileType) {
	var url = basePath
			+ "ea/documenttemplate/sajax_n_ea_getTemplateListByType.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			fileType : fileType
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var templatelist = member.templatelist;
			if (null != templatelist) {
				var str = "<option value=''>选择具体模板</option>";
				for (var i = 0; i < templatelist.length; i++) {
					str += "<option value='" + templatelist[i].templateId
							+ "'>" + templatelist[i].fileShowName + "</option>";

				}

				$("#newForm #specificTemplate").html(str);
			}
		},
		error : function cbf(data) {
			alert("数据获取失败f！");
		}
	});
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
			for (var i = 0; i < companylist.length; i++) {
				var obj = companylist[i];
				str += "<option title='" + obj.companyName + "'value='"
						+ obj.companyID + "'>" + obj.companyName + "</option>";
			}
			$("#SendForm select#receiverCompanyID").html(str);
			if (receiverCompanyID != "") {
				$("#SendForm select#receiverCompanyID option[value="
						+ receiverCompanyID + "]").attr("selected", "selected");

				bmdepts(receiverCompanyID);
			}

		},
		error : function cbf(data) {
			alert("数据获取失败！")
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
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#SendForm #receiverDeptID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
	if (receiverDeptID != "") {
		$("#SendForm select#receiverDeptID option[value=" + receiverDeptID
				+ "]").attr("selected", "selected");
		getPersons(receiverCompanyID, receiverDeptID);
	}

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
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
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
		bmdepts($(obj).val());
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
	var dept = $("#SendForm #receiverDeptID").val();
	if (dept != "") {
		getPersons($("#SendForm #receiverCompanyID").val(), dept);
	} else {
		$("#SendForm #receiverID").html("<option value=''>请选择人员</option>");
	}
}

// 获得人力资源或者在职员工等的弹出框的操作
function importGY2(url,type) { // 打开页面
	$("#daoRu").attr("src", basePath + url + "?date=" + new Date());
	$("#socialJqm2").jqmShow();
	$("#socialJqm2 #type2").val(type);
}

function DaoruConfirm2() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择")
		return;
	}
	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	var type = $("#socialJqm2 #type2").val();
	var companyName = "";	
	var staffIdentityCard;
	if(type=="partyAD"||type=="partyBD"){ 
        companyName= window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#companyName").text();
			
      }
     
    if(type=="partyA"||type=="partyB"){ 
        staffIdentityCard= window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffIdentityCard").text();
			
      }
	
	if(type=="partyA"){
		$("#main #partyAstaffnames").val(staffName);
		$("#main #partyAstaff").val(childopertionID);
		$("#main #staffIdentityCardA").val(staffIdentityCard);
		
	}else if(type=="partyB"){
		$("#main #partyBstaffnames").val(staffName);
		$("#main #partyBstaff").val(childopertionID);
		$("#main #staffIdentityCardB").val(staffIdentityCard);
		
	}else if(type=="partyAD"){
		$("#main #partyAName").val(companyName);
		$("#main #partyA").val(childopertionID);	
	    
	}else  if(type=="partyBD"){
		$("#main #partyBName").val(companyName);
		$("#main #partyB").val(childopertionID);
		
	}else{
	$("#searchForm #socialName").val(staffName);
	$("#InfoForm #socialName").val(staffName);
	$("#searchForm #socials").val(childopertionID);
	$("#InfoForm #socials").val(childopertionID);
	}
	$("#daoRu").attr("src", "");
	$("#socialJqm2").jqmHide();
}
function cancelJqm2() {
	$("#socialJqm2").jqmHide();
}



function clears(f){
	
	if(f=="partyAD"){
		$("#main #partyAName").val("");
		$("#main #partyA").val("");	
	}
	if(f=="partyA"){
		$("#main #partyAstaffnames").val("");
		$("#main #partyAstaff").val("");
		$("#main #staffIdentityCardA").val("");
	}
	if(f=="partyBD"){
		$("#main #partyBName").val("");
		$("#main #partyB").val("");
	}
	if(f=="partyB"){
		$("#main #partyBstaffnames").val("");
		$("#main #partyBstaff").val("");
		$("#main #staffIdentityCardB").val("");
	}
	
	
}