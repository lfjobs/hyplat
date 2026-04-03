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
				title : '项目档案管理',
				minheight : 80,
				buttons : [{
					name : '申请修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '添加档案',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '项目完成',
					bclass : 'examine mark',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '查询',
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '申请修改' :
				var str = "";
				var upval="";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
						if(upval=="01"){
							alert("申请修改");
						}else{
							upval=$("#updatestatus",$("tr#"+taskid)).val();
						}
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return;
				}
				if(upval=="01"){
					alert('选中的任务已经在申请修改中');
					return;
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				break;
			case '查看' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				document.location.href = basePath
							+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid;
				break;
			case '添加档案' :
				taskid = '';
				document.location.href = basePath
						+ "ea/taskmanage/ea_addNewArchivesPage.jspa?myproject.proid="+proid;
				break;
			case '项目完成' :
				var url = basePath
					+ "ea/taskmanage/sajax_ea_getMyprojectbyproid.jspa?date="
					+ new Date().toLocaleString()+"&myproject.proid="+proid;
				var str=0;
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var dtproject = member.dtproject;
						if(dtproject.factfinishdate!=null&&dtproject.factfinishdate!=""){
							alert('项目已经完成!');
							str=1;
						}
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				if(str){
					return;
				}
				$("#overForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/projectmanager/ea_finishedDtMyproject.jspa?project.proid="+proid+"&pageNumber="
						+ ppageNumber);
				document.overForm.submit.click();
				token = 2;
				break;
			case '查询' :
				$("#jqModel").jqmShow();
				break;
			case '导出' :
				var url = basePath + "ea/taskmanage/ea_toExportExcelByTask.jspa?search=" + search+"&myproject.proid="+proid+"&mytask.phasestatus=04";
				open(url);
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/taskmanage/ea_getListByTaskManageFile.jspa?myproject.proid="+proid;
				numback(url);
				break;
		}
	}	
	
	$("input.JQueryreturn").click(function() {// 取消
		$("#jqModel").jqmHide();
		location.reload(); 
	});
	
	$(".close").click(function() {// 取消
			$("#jqModel").jqmHide();
	
	});
	
	$(".checkAll").click(function(){
		$("input[name='chbox']").attr("checked",this.checked);
	});
	
	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});

	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	//查询
	$("#search").click(function() {
		$("#searchForm").attr("action",
				basePath + "ea/taskmanage/ea_toSearchByTaskManageFile.jspa");
		document.searchForm.submit.click();	
	});

	$("#submitResult").click(function() {
		if ($("#SendForm #receiverID").val() == "") {
			alert("请选择收件人");
			return;
		}
	
		if (confirm("确认要申请修改？")) {
			$("[name='chbox']").each(function(index) {
				if ($(this).is(':checked')) {
					var value=$(this).val();
					$("<input>",{type:"hidden",value:value,name:"dtMytaskMap[" + index + "].taskid"}).appendTo($("form#SendForm"));
				}
			});
			var index=0;
			var auditorcompanyid=$("select#receiverCompanyID").find("option:selected").val();
			var auditorcompanyname=$("select#receiverCompanyID").find("option:selected").text();
			var auditororgID=$("select#receiverDeptID").find("option:selected").val();
			var auditororgName=$("select#receiverDeptID").find("option:selected").text();
			var auditorid=$("select#receiverID").find("option:selected").val();
			var auditorname=$("select#receiverID").find("option:selected").text();
			$("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMyauditMap[" + index + "].auditorcompanyid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMyauditMap[" + index + "].auditorcompanyname"}).appendTo($("form#SendForm"));	
			$("<input>",{type:"hidden",value:auditororgID,name:"dtMyauditMap[" + index + "].auditororgID"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditororgName,name:"dtMyauditMap[" + index + "].auditororgName"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorid,name:"dtMyauditMap[" + index + "].auditorid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorname,name:"dtMyauditMap[" + index + "].auditorname"}).appendTo($("form#SendForm"));
			$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/taskmanage/ea_toExamineByTaskupdate.jspa?date="
										+ new Date());
			document.SendForm.submit.click();
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
				$("#SendForm select#receiverCompanyID").html(str);
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
				for (var i = 0; i < orgaizationlist.length; i++) {
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
		$("#receiverDeptID").html("<option value=''>请选择部门</option>");
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
		getPerson($("#SendForm #receiverCompanyID").val(), dept);
	} else {
		$("#SendForm #receiverID").html("<option value=''>请选择人员</option>");
	}
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/taskmanage/ea_getListByTaskManageFile.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&myproject.proid="+proid;
}