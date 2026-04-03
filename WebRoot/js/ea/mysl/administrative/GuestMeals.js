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
				title : '客餐申请表',
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
				document.GuestMeals.reset();
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
				document.GuestMeals.reset();
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
					$("<input>",{type:"hidden",value:value,name:"dtMyguestmeals.key"}).appendTo($("form#postSearchForm"));
					$("#postSearchForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/guestmeals/ea_deleteByGuestMeals.jspa?");
					document.postSearchForm.submit.click();
					token = 2;
				}
				break;		
			case '查看' :
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath + "ea/guestmeals/ea_getDetailsByGuestMeals.jspa?dtMyguestmeals.id="
				+ id;
				open(url);
				break;	
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/guestmeals/ea_exportByGuestMeals.jspa?search="
						+ search;
				open(url);
				break;
			case '打印预览' :
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath + "ea/guestmeals/ea_toPrintPreviewByGuestMeals.jspa?dtMyguestmeals.id="
						+ id;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/guestmeals/ea_getListByGuestMeals.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	/**
	 * 保存草稿
	 */
	$("input.JQuerySave",$("form#GuestMeals")).click(function() {// 提交审核或保存草稿
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if(notoken){
					return;
				}
				notoken=1;
				$("form#GuestMeals")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/guestmeals/ea_saveOrEditByGuestMeals.jspa?");
				document.GuestMeals.submit.click();
				document.GuestMeals.reset();
				token = 2;
			});
	/**
	 * 提交审核
	 */
	$("input.JQuerySubmit",$("form#GuestMeals")).click(function() {// 提交审核或保存草稿
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		$("#jqModelSend").jqmShow();
		$("#jqModel").jqmHide();
		getAllCompanyOfGroup();
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
				basePath + "ea/guestmeals/ea_toSearchByGuestMeals.jspa?");
		document.postSearchForm.submit.click();
	});
	
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
	})
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
				var auditorcompanyid=$("select#receiverCompanyID").find("option:selected").val();
				var auditorcompanyname=$("select#receiverCompanyID").find("option:selected").text();
				var auditororgID=$("select#receiverDeptID").find("option:selected").val();
				var auditororgName=$("select#receiverDeptID").find("option:selected").text();
				var auditorid=$("select#receiverID").find("option:selected").val();
				var auditorname=$("select#receiverID").find("option:selected").text();
				
				$("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMycheck.auditorcompanyid"}).appendTo($("form#GuestMeals"));
				$("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMycheck.auditorcompanyname"}).appendTo($("form#GuestMeals"));	
				$("<input>",{type:"hidden",value:auditororgID,name:"dtMycheck.auditororgid"}).appendTo($("form#GuestMeals"));
				$("<input>",{type:"hidden",value:auditororgName,name:"dtMycheck.auditororgname"}).appendTo($("form#GuestMeals"));
				$("<input>",{type:"hidden",value:auditorid,name:"dtMycheck.auditorid"}).appendTo($("form#GuestMeals"));
				$("<input>",{type:"hidden",value:auditorname,name:"dtMycheck.auditorname"}).appendTo($("form#GuestMeals"));
				
	
				$("input#buttonType",$("form#GuestMeals")).attr("value","1");
				
				$("form#GuestMeals")
					.attr("target", "hidden")
					.attr(
						"action",
						basePath
								+ "ea/guestmeals/ea_saveOrEditByGuestMeals.jspa?");
				document.GuestMeals.submit.click();
				document.GuestMeals.reset();
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
				+ "ea/guestmeals/ea_getListByGuestMeals.jspa?pageNumber="
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
				            data:{"serialnumber":"013"},
				            success: function cbf(data){
						    var member = eval("(" + data + ")");
						    var nologin = member.nologin;
							if(nologin){
								document.location.href =basePath+"page/ea/not_login.jsp";
							}
							vouch = member.BillID;
							$("input#serialnumber",$("form#GuestMeals")).val(vouch);
						},
						error : function cbf(data) {
							alert("数据获取失败!")
						}
					});
}

/**---------------------------------------------------------选择多个人员---------------------------------------------**/
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
					$("#jqModelSend select#receiverCompanyID").html(str);
					$("#jqModelSend #receiverDeptID").html("<option value=''>请选择部门</option>");
					$("#jqModelSend #receiverID").html("<option value=''>请选择人员</option>");
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
					$("#jqModelSend #receiverDeptID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
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
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
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
		$("#jqModelSend #receiverDeptID").html("<option value=''>请选择部门</option>");
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