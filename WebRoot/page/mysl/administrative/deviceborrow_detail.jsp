<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>设备借用单查看</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
		
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body {
	margin-left: 15px;
}
body td{
			font-size:11px;
		
		}
#apDiv1 {
	position: absolute;
	left: 507px;
	top: 287px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var checkid="${checkid}";
    var checkurl="${checkurl}";
    $(function() {
    	
    	$(".jqmWindow").jqm({
			modal : true,// 限制输入（鼠标点击，按键）的对话
			overlay : 20
		// 遮罩程度%
		}).jqmAddClose(".close");// 添加触发关闭的selector
		
		if(checkid!=null&&checkid!=""){
		
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
			$("#SendForm2 #auditorstatu").val("02");
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
			$("<input>",{type:"hidden",value:checkid,name:"dtMycheck2Map[" + index + "].checkid"}).appendTo($("form#SendForm"));
			$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/receiptcheck/ea_audit.jspa?");
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
			var index=0;
			var comments=$("#SendForm2 #comments").val();
			var auditorstatus=$("#SendForm2 #auditorstatu").val();
			$("<input>",{type:"hidden",value:checkid,name:"dtMycheck2Map[" + index + "].checkid"}).appendTo($("form#SendForm2"));
			$("<input>",{type:"hidden",value:comments,name:"dtMycheckMap[" + index + "].comments"}).appendTo($("form#SendForm2"));
			$("<input>",{type:"hidden",value:auditorstatus,name:"dtMycheckMap[" + index + "].auditorstatus"}).appendTo($("form#SendForm2"));
			$("#SendForm2").attr("target", "hidden").attr("action",
					basePath + "/ea/receiptcheck/ea_audit.jspa?");
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
		document.location.href = basePath+checkurl;
	};
};
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold;font-size:16px;">&nbsp;
					绵阳市水利规划设计研究院(设备借用单)详情
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="10%" align="left">
						公司：
					</td>
					<td width="15%" align="left">
						${deborrow.companyname}
					</td>
					<td width="10%" align="left">
						部门：
					</td>
					<td width="15%" align="left">
					    ${deborrow.organizationname}
					</td>
					<td width="10%" align="left">
						借用人：
					</td>
					<td width="15%" align="left">
					   ${deborrow.dvusername}
					</td>
					<td width="25%" colspan="2" align="left">
						制单日期：${fn:substring(deborrow.addtime, 0, 19)}
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="10%" align="left">
						制单人：
					</td>
					<td width="15%" align="left">
						${deborrow.staffname}
					</td>
					<td width="10%" align="left">
						单据状态：
					</td>
					<td width="15%" align="left">
					<c:if test="${deborrow.status eq 00}">草稿</c:if><c:if test="${deborrow.status eq 01}">未审核</c:if><c:if test="${deborrow.status eq 02}">已审核</c:if><c:if test="${deborrow.status eq 03}">驳回</c:if><c:if test="${deborrow.status eq 04}">办理中</c:if><c:if test="${deborrow.status eq 05}">已办理</c:if>
					</td>
					<td width="50%" align="right">
						单据编号：<%
						com.mysl.bo.administrative.DtMydeviceborrow data = (com.mysl.bo.administrative.DtMydeviceborrow) request
								.getAttribute("deborrow");
						if (data != null) {
							StringBuffer barCode = new StringBuffer();
							barCode.append("<img src='");
							barCode.append(request.getContextPath());
							barCode.append("/CreateBarCode?data=");
							barCode.append(data.getSerialNumber());
							barCode
									.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
							out.println(barCode.toString());
						} else {
							out.println("no data");
						}
					%><br />
						 ${deborrow.serialNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>

			<br/>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<tr height="30">
					<td width="100" align="center">
						设备名称
					</td>
					<td width="210">
						${deborrow.dvname}
					</td>
					<td width="100" align="center">
						数量
					</td>
					<td width="210">
						${deborrow.dvnum}
					</td>
				</tr>
				<tr height="30">
					<td align="center">
						借用时间
					</td>
					<td >
					    ${fn:substring(deborrow.dvusetime,0,16)}
					</td>
					<td align="center">
						计划归还时间
					</td>
					<td >
					    ${fn:substring(deborrow.dvbacktime,0,16)}
					</td>
				</tr>
				<tr height="30">
					<td align="center">
						用途
					</td>
					<td colspan="3">
						${deborrow.dvuse}
					</td>
				</tr>
				<tr height="30">
					<td align="center">
						备注
					</td>
					<td colspan="3">
						${deborrow.remarks}
					</td>
				</tr>
				<tr>
				<td colspan="4" align="center" style="display: none;" id="anniu">
				<input type="button" value="审核通过并继续审核" id="jx" class="input-button" style="cursor:pointer;width:120px;"/> &nbsp;&nbsp;&nbsp;
				<input type="button" value="审核通过并结束审核" id="js" class="input-button" style="cursor:pointer;width:120px;"/> &nbsp;&nbsp;&nbsp;
				<input type="button" value="驳回" id="bh" class="input-button" style="cursor:pointer;width:80px;"/>
				</td>
				</tr>
			</table>
			<br/>
			<br/>
			<h2>签核记录</h2><br/>
			<table width="620" cellpadding="0" cellspacing="0" class="table">
				<tr>
				    <th align="center">
						序号
					</th>
					<th align="center">
						申请人
					</th>
					<th align="center">
						签核人
					</th>
					<th align="center">
						签核时间
					</th>
					<th align="center">
						签核状态
					</th>
					<th align="center">
						签核意见
					</th>
				</tr>
				<s:iterator value="pageForm.list" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="applyername">${applyername}</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="auditorname">${auditorname}</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="audittime">${fn:substring(audittime,0,19)}</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="auditorstatus">
							<c:if test="${auditorstatus eq 01}">未审核</c:if><c:if test="${auditorstatus eq 02}">已审核</c:if><c:if test="${auditorstatus eq 03}">驳回</c:if><c:if test="${auditorstatus eq 04}">办理中</c:if><c:if test="${auditorstatus eq 05}">已办理</c:if>
							</span>
						</td>
						<td align="center" bgcolor="#FFFFFF">
							<span id="comments">${comments}</span>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		<br />
		<br />
		<br />
		<br />
			<!-- -----------------------------------审核并继续审核-------------------------------- -->
	<form name="SendForm" id="SendForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 290px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核通过并继续
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="10"
				cellpadding="20">
				<tr class="shows">
					<td align="right" width="20%">审核人公司：</td>
					<td align="left"><select id="auditorcompanyid"
						name="dtMycheck.auditorcompanyid" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人部门：</td>
					<td align="left"><select id="auditororgid"
						name="dtMycheck.auditororgID" onchange="changeDept(this);"
						style="width: 200px;">
							<option value="">请选择审核人部门</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人姓名：</td>
					<td align="left"><select name="dtMycheck.auditorid"
						id='auditorid' style="width: 200px;">
							<option value="">请选择审核人</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="dtMycheck.comments" id="comments"  class="ckTextLength" maxlength="1000"></textarea>
					
					</td>
				</tr>
			</table>

			<div align="center">
				<input type="button" class="input-button" id="submitResult" value=" 提交 " />
			</div>
			</center>
		</div>
	</form>
	
	<!-- -----------------------------------审核-------------------------------- -->
	<form name="SendForm2" id="SendForm2" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
			id="jqModelSend2">
			<input type="submit" name="submit2" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right" width="20%">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="dtMycheck.comments" id="comments"  class="ckTextLength" maxlength="1000"></textarea>
					
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="submitResult2" value=" 提交 " />
					<input type="hidden" id="auditorstatu" value=""/>
			</div>
			</center>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	</body>
</html>
