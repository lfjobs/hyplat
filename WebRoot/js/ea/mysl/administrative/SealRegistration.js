$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 355,
				width : 'auto',
				minwidth : 30,
				title : '盖章申请',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				id = '';
				$("input.JQuerypersonvalue",$("tr[id]")).attr("checked",false);
				document.SealRegistration.reset();
				getSerialNumber();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (id == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + id);
				var status=$.trim($p.find("span#status").text());//status=="01"||status=="02"||status=="03"||status=="04"||status=="05"
				if(status!="00"){
					alert("只能修改草稿!");
					return;
				}
				document.SealRegistration.reset();
				$t = $("div#jqModel");
				$p.find("span[id]").each(function() {
							$t.find("#" + this.id).val($(this).text());
				});
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (id == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + id);
				var status=$.trim($p.find("span#status").text());
				if(status!="00"){
					alert("只能删除草稿状态!");
					return;
				}
				if(confirm("确认要删除吗")){
					var value=$.trim($p.find("span#key").text());
					$("<input>",{type:"hidden",value:value,name:"dtMysealregistration.key"}).appendTo($("form#postSearchForm"));
					$("#postSearchForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/sealregistration/ea_deleteBySealRegistration.jspa?");
					document.postSearchForm.submit.click();
					token = 2;
				}
				break;		
			case '查看' :
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath + "ea/sealregistration/ea_getDetailsBySealRegistration.jspa?dtMysealregistration.id="
				+ id;
				open(url);
				break;	
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/sealregistration/ea_exportBySealRegistration.jspa?search="
						+ search;
				open(url);
				break;
			case '打印预览' :
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath + "ea/sealregistration/ea_toPrintPreviewBySealRegistration.jspa?dtMysealregistration.id="
						+ id;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/sealregistration/ea_getListBySealRegistration.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	/**
	 * 保存草稿
	 */
	$("input.JQuerySave",$("form#SealRegistration")).click(function() {// 提交审核或保存草稿
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if(notoken){
					return;
				}
				notoken=1;
				$("form#SealRegistration")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/sealregistration/ea_saveOrEditBySealRegistration.jspa?");
				document.SealRegistration.submit.click();
				document.SealRegistration.reset();
				token = 2;
			});
	/**
	 * 提交审核
	 */
	$("input.JQuerySubmit",$("form#SealRegistration")).click(function() {// 提交审核或保存草稿
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		$("#jqModelSend").jqmShow();
		$("#jqModel").jqmHide();
		getAllCompanyOfGroup("jqModelSend");
	});
	// 新加内容结束
	$(".JQueryflexme tr[id]").dblclick(function() { // 双击查看
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	
	$(".JQueryflexme tr[id]").click(function() {
				id = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/sealregistration/ea_toSearchBySealRegistration.jspa?");
		document.postSearchForm.submit.click();
	});
	/**
	 * 选择经办人 与 选择接收人
	 */
	var id_a;
	var name_a;
	$("a.agent,a.recipient").click(function(){
		
		if($(this).hasClass("agent")){
			id_a="agentpersonid";
			name_a="agentpersonname";
		}else if($(this).hasClass("recipient")){
			id_a="recipientpersonid";
			name_a="recipientpersonname";
		}
		$("#jqModelSelect").jqmShow();
		getAllCompanyOfGroup("jqModelSelect"); 
	});
	/**
	 * 选择经办人 与 选择接收人 取消与确定
	 */
	$("#SelectResult").click(function() {// 确定
		var auditorid=$("select#receiverID",$("#jqModelSelect")).find("option:selected").val();
		var auditorname=$("select#receiverID",$("#jqModelSelect")).find("option:selected").text();
		$("input#"+id_a,$("#SealRegistration")).val(auditorid);
		$("input#"+name_a,$("#SealRegistration")).val(auditorname);
		$("#jqModelSelect").jqmHide();
	});
	$("#SelectColsed").click(function() {// 返回
		$("#jqModelSelect").jqmHide();
	});
	/**
	 * 返回
	 */
	$(".JQueryreturn").click(function() {// 返回
		$("#jqModel").jqmHide();
	});
	
	/**
	 * 关闭审核
	 * @returns
	 */
	$("input#submitColsed").click(function(){
		$("#jqModelSend").jqmHide();
		$("#jqModel").jqmShow();
	});
	/**
	 * 提交审核
	 * @returns
	 */
	$("#submitResult").click(function() {
			if ($("#receiverID").val() == "") {
				alert("请选择收件人");
				return;
			}
			if(notoken){
				return;
			}
			if (confirm("确认要发送至领导审批？")) {
				notoken=1;
				var auditorcompanyid=$("select#receiverCompanyID",$("#jqModelSend")).find("option:selected").val();
				var auditorcompanyname=$("select#receiverCompanyID",$("#jqModelSend")).find("option:selected").text();
				var auditororgID=$("select#receiverDeptID",$("#jqModelSend")).find("option:selected").val();
				var auditororgName=$("select#receiverDeptID",$("#jqModelSend")).find("option:selected").text();
				var auditorid=$("select#receiverID",$("#jqModelSend")).find("option:selected").val();
				var auditorname=$("select#receiverID",$("#jqModelSend")).find("option:selected").text();
				
				$("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMycheck.auditorcompanyid"}).appendTo($("form#SealRegistration"));
				$("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMycheck.auditorcompanyname"}).appendTo($("form#SealRegistration"));	
				$("<input>",{type:"hidden",value:auditororgID,name:"dtMycheck.auditororgid"}).appendTo($("form#SealRegistration"));
				$("<input>",{type:"hidden",value:auditororgName,name:"dtMycheck.auditororgname"}).appendTo($("form#SealRegistration"));
				$("<input>",{type:"hidden",value:auditorid,name:"dtMycheck.auditorid"}).appendTo($("form#SealRegistration"));
				$("<input>",{type:"hidden",value:auditorname,name:"dtMycheck.auditorname"}).appendTo($("form#SealRegistration"));
				
	
				$("input#buttonType",$("form#SealRegistration")).attr("value","1");
				
				$("form#SealRegistration")
					.attr("target", "hidden")
					.attr(
						"action",
						basePath
								+ "ea/sealregistration/ea_saveOrEditBySealRegistration.jspa?");
				document.SealRegistration.submit.click();
				document.SealRegistration.reset();
				token = 2;
			}
	});
	
});

function re_load() {
	if(pageNumber==1){
		pageNumber=0;   //找不到问题 暂时之使用这个方式  原因 不做任何操作 pageNumber 自动 变成  1
	}
	if (token)
		document.location.href = basePath
				+ "ea/sealregistration/ea_getListBySealRegistration.jspa?pageNumber="
				+ pageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function getSerialNumber(){  
					var url=basePath+"ea/sanitaryinspection/sajax_ea_getSerialNumber.jspa?date="+new Date().toLocaleString();
					$.ajax({
				            url: url,
				            type: "get",
				            async: true,
				            dataType: "json",
				            data:{"serialnumber":"005"},
				            success: function cbf(data){
						    var member = eval("(" + data + ")");
						    var nologin = member.nologin;
							if(nologin){
								document.location.href =basePath+"page/ea/not_login.jsp";
							}
							vouch = member.BillID;
							$("input#serialnumber",$("form#SealRegistration")).val(vouch);
						},
						error : function cbf(data) {
							alert("数据获取失败!");
						}
					});
}

/**---------------------------------------------------------选择多个人员---------------------------------------------**/
/**
 * 
 * 获得当前公司集团的所有公司
 */
function getAllCompanyOfGroup(selectValue) {
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
					$("select#receiverCompanyID",$("#"+selectValue)).html(str);
					$("#receiverDeptID",$("#"+selectValue)).html("<option value=''>请选择部门</option>");
					$("#receiverID",$("#"+selectValue)).html("<option value=''>请选择人员</option>");
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
function bmdept(val,selectValue) {
	
	$("select#receiverDeptID option",$("#"+selectValue)).remove();

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
					$("#receiverDeptID",$("#"+selectValue)).html(str);

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
function getPerson(company, org,selectValue) {

	$("select#receiverID option", $("#"+selectValue)).remove();
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
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
					}
					$("#receiverID", $("#"+selectValue)).html(str);
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
function changeCompany(obj,selectValue) {
	if ($(obj).val() != '') {
		bmdept($(obj).val(),selectValue);
	} else {
		$("#receiverDeptID", $("#"+selectValue)).html("<option value=''>请选择部门</option>");
	}
}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj,selectValue) {
	if ($(obj).val() != '') {
		getPerson($("#receiverCompanyID",$("#"+selectValue)).val(), $(obj).val(),selectValue);
	} else {
		$("#receiverID", $("#"+selectValue)).html("<option value=''>请选择人员</option>");
	}
}