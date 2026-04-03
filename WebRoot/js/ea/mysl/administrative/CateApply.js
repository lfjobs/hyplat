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
				title : '部门证书领用申请',
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
				title : '个人证书领用申请',
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
			    cateid = "";
			    $("input.JQuerypersonvalue").attr("checked", false);
			    document.cateapplyForm.reset();
			    getSerialNumber();
				$("#jqModel").jqmShow();
				break;
			case '提交审核' :
			    if (cateid == "") {
					alert('请选择!');
					return;
				}
				var statu = $("tr#"+cateid).find("span#status").text();
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
				if (cateid == "") {
					alert('请选择！');
					return;
				}
				var statu = $("tr#"+cateid).find("span#status").text();
				if (statu != "草稿") {
					alert('只能删除草稿状态单据!');
					return;
				}
				$f = $('#cateapplyForm');
				$f.find(':input#id').val(cateid);
				if (confirm("确定继续？")) {
					$f.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/cateapply/ea_delCateApply.jspa?pageNumber="
											+ pageNumber + "&search=" + search);
					document.cateapplyForm.submit.click();
                    token = 2;
					$("tr#" + cateid).remove();
					cateid = "";
				}
				break;
			case '修改' :
				if (cateid == "") {
					alert('请选择!');
					return;
				}
				document.cateapplyForm.reset();
				$t = $("table#cateapplytable");
				$p = $("tr#" + cateid);
				var status=$p.find("span#status").text();
				if (status != "草稿") {
					alert('只能修改草稿状态的单据!');
					return;
				}
				var cid = $("tr#"+cateid).find("span#cateusername").text();
				var serialNumber = $("tr#"+cateid).find("span#serialNumber").text();
				$t.find("span#cateusername").text(cid);
				$t.find("span#serialNumber").text(serialNumber);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});
				//$("input#save2").css("display","none");
				$("#jqModel").jqmShow();
				break;
		   case '查看' :
			    if (cateid == "") {
					alert('请选择!');
					return;
				}
			    window.open(basePath + "ea/cateapply/ea_toDetailcate.jspa?cateapply.id="+cateid);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#postSearchForm").find("input#catename").focus();
				break;
			case '导出' :
			    var url = basePath+ "ea/cateapply/ea_showExcel.jspa?type="+type+"&search="+search;
			    open(url);
				break;
			case '打印预览' :
			    if (cateid == "") {
					alert('请选择!');
					return;
				}
			    window.open(basePath + "ea/cateapply/ea_toPrintCateApply.jspa?cateapply.id="+cateid);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/cateapply/ea_getDtMycertificateapplyList.jspa?type="+type+"&1=1";
				numback(url);
				break;
		}
	}
	$(".close").click(function() {// 取消查询搜索
		        submittype="";
		        $("table#cataffSearchTable").find("input#catename").val("");
		        $("table#cataffSearchTable").find("input#cateusername").val("");
		        $("table#cataffSearchTable").find("input#Cateusedate").val("");
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
				basePath + "ea/cateapply/ea_toSearch.jspa?type="+type+"&1=1");
		document.postSearchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击查看按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				cateid = this.id;
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
                  $("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMycheck.auditorcompanyid"}).appendTo($("form#cateapplyForm"));
				  $("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMycheck.auditorcompanyname"}).appendTo($("form#cateapplyForm"));	
				  $("<input>",{type:"hidden",value:auditororgid,name:"dtMycheck.auditororgid"}).appendTo($("form#cateapplyForm"));
				  $("<input>",{type:"hidden",value:auditororgname,name:"dtMycheck.auditororgname"}).appendTo($("form#cateapplyForm"));
				  $("<input>",{type:"hidden",value:auditorid,name:"dtMycheck.auditorid"}).appendTo($("form#cateapplyForm"));
				  $("<input>",{type:"hidden",value:auditorname,name:"dtMycheck.auditorname"}).appendTo($("form#cateapplyForm"));
				  $("#cateapplyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/cateapply/ea_saveCateApply.jspa?submittype="+submittype+"&1=1"
									);
			     document.cateapplyForm.submit.click();
			     document.cateapplyForm.reset();
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
				  $f.find(':input#id').val(cateid);
				  var url = basePath
						+ "ea/cateapply/ea_tocheck.jspa?pageNumber="
						+ pageNumber;
				  $f.attr("target","hidden").attr(
						"action",url);
				  document.submitcheckForm.submit.click();
			      document.submitcheckForm.reset();
			      token = 2;
		          cateid = "";
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
		if (cateid == "") {
			if(submittype=="savecheck"){
				$("#jqModel").jqmHide();
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				colsetype=2;
		    }else if(submittype="save"){
		       $("#cateapplyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/cateapply/ea_saveCateApply.jspa?submittype="+submittype+"&1=1"
									);
			     document.cateapplyForm.submit.click();
			     document.cateapplyForm.reset();
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
		    $("#cateapplyForm")
				.attr("target", "hidden")
				.attr(
					"action",
					basePath
						+ "ea/cateapply/ea_saveCateApply.jspa?submittype="+submittype+"&1=1"
							);
			document.cateapplyForm.submit.click();
			document.cateapplyForm.reset();
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
				            data:{"serialnumber":"004"},
				            success: function cbf(data){
						    var member = eval("(" + data + ")");
						    var nologin = member.nologin;
							if(nologin){
								document.location.href =basePath+"page/ea/not_login.jsp";
							}
							vouch = member.BillID;
							$("input#serialNumber",$("form#cateapplyForm")).val(vouch);
							$("span#serialNumber",$("form#cateapplyForm")).text(vouch);
						},
						error : function cbf(data) {
							alert("数据获取失败!")
						}
					});
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cateapply/ea_getDtMycertificateapplyList.jspa?type="+type+"&1=1";
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