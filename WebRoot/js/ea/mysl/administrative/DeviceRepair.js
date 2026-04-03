$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	if(type=="org"){
		$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '部门设备维修申请',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
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
				}]
			});
	}else if(type=="my"){
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '个人设备维修申请',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '提交审核',
					bclass : 'examine',
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
				}, {
					name : '修改',
					bclass : 'edit',
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
				}]
			});
	}
	
	function action(com, grid) {
		switch (com) {
			case '添加' :
			    derid = "";
			    $("input.JQuerypersonvalue").attr("checked", false);
			    document.devicerepairForm.reset();
			    getSerialNumber();
				$("#jqModel").jqmShow();
				break;
			case '提交审核' :
			    if (derid == "") {
					alert('请选择!');
					return;
				}
				var statu = $("tr#"+derid).find("span#status").text();
				if (statu != "草稿") {
					alert('草稿状态单据才能提交审核!');
					return;
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				colsetype=1;
				break;
			case '删除' :
				if (derid == "") {
					alert('请选择！');
					return;
				}
				var statu = $("tr#"+derid).find("span#status").text();
				if (statu != "草稿") {
					alert('只能删除草稿状态单据!');
					return;
				}
				$f = $('#devicerepairForm');
				$f.find(':input#id').val(derid);
				if (confirm("确定继续？")) {
					$f.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/derepair/ea_delderepairApply.jspa?pageNumber="
											+ pageNumber + "&search=" + search);
					document.devicerepairForm.submit.click();
                    token = 2;
					$("tr#" + derid).remove();
					derid = "";
				}
				break;
			case '修改' :
				if (derid == "") {
					alert('请选择!');
					return;
				}
				document.devicerepairForm.reset();
				$t = $("table#devicerepairtable");
				$p = $("tr#" + derid);
				var status=$p.find("span#status").text();
				if (status != "草稿") {
					alert('只能修改草稿状态的单据!');
					return;
				}
				var dname = $("tr#"+derid).find("span#dvdealname").text();
				var serialNumber = $("tr#"+derid).find("span#serialNumber").text();
				$t.find("span#dvdealname").text(dname);
				$t.find("span#serialNumber").text(serialNumber);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});
				//$("input#save2").css("display","none");
				$("#jqModel").jqmShow();
				break;
		   case '查看' :
			    if (derid == "") {
					alert('请选择!');
					return;
				}
			    window.open(basePath + "ea/derepair/ea_toDetailDeviceRepair.jspa?derepair.id="+derid);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#postSearchForm").find("input#dvname").focus();
				break;
			case '导出' :
			    var url = basePath+ "ea/derepair/ea_showExcel.jspa?type="+type+"&search="+search;
			    open(url);
				break;
			case '打印预览' :
			    if (derid == "") {
					alert('请选择!');
					return;
				}
			    window.open(basePath + "ea/derepair/ea_toPrintDeviceRepair.jspa?derepair.id="+derid);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/derepair/ea_getDtMydevicerepairList.jspa?type="+type+"&1=1";
				numback(url);
				break;
		}
	}
	$(".close").click(function() {// 取消查询搜索
		        submittype="";
		        $("table#cataffSearchTable").find("input#dvname").val("");
		        $("table#cataffSearchTable").find("input#dvdealname").val("");
				$("#jqModelSearch").jqmHide();
			    $("input#save2").css("display","block");
			});
	//选择审核人关闭
	$(".checkclose").click(function() {
				$("#jqModelSend").jqmHide();
                if(colsetype==2)
		        {
		        $("#jqModel").jqmShow();
		        }
			});	
	//添加，点击取消
	$(".JQueryreturn").click(function() {
		        submittype="";
				$("#jqModel").jqmHide();
				$("input#save2").css("display","block");
				re_load();
			});
	//条件查询确定
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/derepair/ea_toSearch.jspa?type="+type+"&1=1");
		document.postSearchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击查看按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				derid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	//提交审核
    $("#submitResult").click(function() {
			if ($("#SendForm #receiverID").val() == "") {
				alert("请选择审核人");
				return;
			}
		    if(notoken){
				return;
			}
			if (confirm("确认要提交审核？")) {
				notoken=1;
				var auditorcompanyid=$("select#receiverCompanyID").find("option:selected").val();
				var auditorcompanyname=$("select#receiverCompanyID").find("option:selected").text();
				var auditororgid=$("select#receiverDeptID").find("option:selected").val();
				var auditororgname=$("select#receiverDeptID").find("option:selected").text();
				var auditorid=$("select#receiverID").find("option:selected").val();
				var auditorname=$("select#receiverID").find("option:selected").text();

                if(submittype=="savecheck"){
                  $("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMycheck.auditorcompanyid"}).appendTo($("form#devicerepairForm"));
				  $("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMycheck.auditorcompanyname"}).appendTo($("form#devicerepairForm"));	
				  $("<input>",{type:"hidden",value:auditororgid,name:"dtMycheck.auditororgid"}).appendTo($("form#devicerepairForm"));
				  $("<input>",{type:"hidden",value:auditororgname,name:"dtMycheck.auditororgname"}).appendTo($("form#devicerepairForm"));
				  $("<input>",{type:"hidden",value:auditorid,name:"dtMycheck.auditorid"}).appendTo($("form#devicerepairForm"));
				  $("<input>",{type:"hidden",value:auditorname,name:"dtMycheck.auditorname"}).appendTo($("form#devicerepairForm"));
				   $("#devicerepairForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/derepair/ea_saveDeviceRepair.jspa?submittype="+submittype+"&1=1"
									);
			       document.devicerepairForm.submit.click();
			       document.devicerepairForm.reset();
			       token = 2;
			    }
				else{
				  $("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMycheck.auditorcompanyid"}).appendTo($("form#submitcheckForm"));
				  $("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMycheck.auditorcompanyname"}).appendTo($("form#submitcheckForm"));	
				  $("<input>",{type:"hidden",value:auditororgid,name:"dtMycheck.auditororgid"}).appendTo($("form#submitcheckForm"));
				  $("<input>",{type:"hidden",value:auditororgname,name:"dtMycheck.auditororgname"}).appendTo($("form#submitcheckForm"));
				  $("<input>",{type:"hidden",value:auditorid,name:"dtMycheck.auditorid"}).appendTo($("form#submitcheckForm"));
				  $("<input>",{type:"hidden",value:auditorname,name:"dtMycheck.auditorname"}).appendTo($("form#submitcheckForm"));
				  $f = $('form#submitcheckForm');
				  $f.find(':input#id').val(derid);
				  var url = basePath
						+ "ea/derepair/ea_tocheck.jspa?pageNumber="
						+ pageNumber;
				  $f.attr("target","hidden").attr(
						"action",url);
				  document.submitcheckForm.submit.click();
			      document.submitcheckForm.reset();
			      token = 2;
		          derid = "";
		          $("input.JQuerypersonvalue").attr("checked", false);
				}
		        
			}
	});
});
//添加、修改单据
function  tosave(stype) {
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if(stype=="savecheck"){
		  submittype="savecheck";
		}
		else if(stype=="save"){
		  submittype="save";
		}
		//判断添加数据
		if (derid == "") {
			if(submittype=="savecheck"){
				$("#jqModel").jqmHide();
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				colsetype=2;
		    }else if(submittype="save"){
		        $("#devicerepairForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/derepair/ea_saveDeviceRepair.jspa?submittype="+submittype+"&1=1"
									);
			    document.devicerepairForm.submit.click();
			    document.devicerepairForm.reset();
			    token = 2;
		    }
			return;
		}
		//修改数据
		if(submittype=="savecheck"){
			$("#jqModel").jqmHide();
		    $("#jqModelSend").jqmShow();
		    document.SendForm.reset();
		    getAllCompanyOfGroup();
		    colsetype=2;
	    }else if(submittype="save"){
	        $("#devicerepairForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/derepair/ea_saveDeviceRepair.jspa?submittype="+submittype+"&1=1"
								);
		    document.devicerepairForm.submit.click();
		    document.devicerepairForm.reset();
		    token = 2;
	    }
	}
//单据编号
function getSerialNumber(){
					var url=basePath+"ea/sanitaryinspection/sajax_ea_getSerialNumber.jspa?date="+new Date().toLocaleString();
					$.ajax({
				            url: url,
				            type: "get",
				            async: true,
				            dataType: "json",
				            data:{"serialnumber":"003"},
				            success: function cbf(data){
						    var member = eval("(" + data + ")");
						    var nologin = member.nologin;
							if(nologin){
								document.location.href =basePath+"page/ea/not_login.jsp";
							}
							vouch = member.BillID;
							$("input#serialNumber",$("form#devicerepairForm")).val(vouch);
							$("span#serialNumber",$("form#devicerepairForm")).text(vouch);
						},
						error : function cbf(data) {
							alert("数据获取失败!")
						}
					});
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/derepair/ea_getDtMydevicerepairList.jspa?type="+type+"&1=1";
}
/*****************提交审核选择审核人***********************/
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
					
					var str = "<option value=''>请选择审核人公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName + "'value='"
								+ obj.companyID + "'>" + obj.companyName
								+ "</option>";
					}
					$("#SendForm select#receiverCompanyID").html(str);
				},
				error : function cbf(data) {
					alert("数据获取失败！")
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
		$("#receiverDeptID").html("<option value=''>请选择审核人部门</option>");
	}

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
					var str = "<option value=''>请选择审核人部门</option>";
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
		$("#SendForm #receiverID").html("<option value=''>请选择审核人</option>");
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
					var str = "<option value=''>请选择审核人</option>";
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
