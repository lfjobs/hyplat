<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="hy.ea.bo.Company"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色添加/修改</title>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/common/organizationTree.js"></script>
</head>
<body>
	<form name="croleForm" method="post" id="croleForm" action="">
		<input type="submit" name="submit" style="display:none" />
		<div class="main">
			<table width="98%" border="0" align="center" cellpadding="2"
				cellspacing="1" bgcolor="#d8e6f4" style="margin-top: 8px">
				<tr bgcolor="#E7E7E7">
					<td height="24" width="20%" align="left" bgcolor="#d8e6f4">
						&nbsp; <span class="txt">角色添加/修改</span>&nbsp;</td>
					<td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
					<td width="20%" height="24" align="right" bgcolor="#d8e6f4"><img
						src="<%=basePath%>images/ea/main/list_add.gif" width="8"
						height="8" /> <a
						href="<%=basePath%>ea/ccrole/ea_getListCRole.jspa?search=${param.search}" class="link02">返回角色列表</a>
					</td>
				</tr>
			</table>
			<table width="98%" height="166" align="center"
				style="border: #d8e6f4 1px solid;">
				<tr height="22">
					<td width="35%" height="33" align="right" class="txt02">公司名称：
					</td>
					<td>
					<select id="companyID" name="crole.companyID"></select>
						<input name="crole.companyName" type="hidden"  id="textfield" value="${crole.companyName}"/> 
						<input name="crole.companyID" type="hidden"  id="textfieldCompanyID" value="${crole.companyID}" disabled="disabled"/>
						<span  style="color: #FF0000;">*${fn:substring(caccount.companyID,0,7)=="company"?"(不可编辑)":""}</span>
					</td>
				</tr>
				<tr height="22">
					<td width="35%" height="33" align="right" class="txt02">部门名称：
					</td>
					<td><select name="crole.organizationName"
						style="width: 250px;" id="orgID">
					</select> <input name="crole.organizationNameDesc" id="organizationNameDesc"
						type="hidden" value="${crole.organizationNameDesc}" class="put3"/> <span
						style="color: #FF0000;">*</span>
					</td>
				</tr>
				<tr height="22">
					<td width="35%" height="33" align="right" class="txt02">岗位名称：
					</td>
					<td><input name="crole.opostName" type="text" maxlength="50"
						class="kuang put3 ckTextLength" id="opostName"
						style="width: 250px;" value="${crole.opostName}" /> <span
						style="color: #FF0000;">*</span>
					</td>
				</tr>
				<tr height="22">
					<td width="35%" height="33" align="right" class="txt02">职务名称：
					</td>
					<td><input name="crole.roleName" type="text" maxlength="50"
						class="kuang put3 ckTextLength" id="roleName"
						style="width: 250px;" value="${crole.roleName}" /> <span
						style="color: #FF0000;">*</span>
					</td>
				</tr>

				<tr height="22">
					<td height="90" align="right"><span class="txt02">角色描述：</span>
					</td>
					<td><textarea name="crole.roleDesc" id="textarea" cols="40"
							rows="5">${crole.roleDesc}</textarea>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr align="center" bgcolor="#FFFFFF" height="22">
					<td height="33" align="right">&nbsp;</td>
					<td height="33" align="center">
						<div class="submit"
							onclick="edit('${crole.roleKey}','${crole.roleID}')">保存</div> <s:token />
					</td>
					<td align="left">&nbsp;</td>
				</tr>
			</table>
			<s:token />
		</div>
	</form>
	<script type="text/javascript">
var companyID='${crole.companyID}';
var companyName='${crole.companyName}';
var oragtionID='${crole.organizationName}'; 
var basePath='<%=basePath%>';
function edit(roleKey,roleID){
        $(".put3").trigger("blur");
          if($("form .error").length)
          { 
            return;
          }  
      $("#croleForm").attr("action","<%=basePath%>ea/ccrole/t_ea_savaCRole.jspa?pageNumber=<%=request.getParameter("pageNumber")%>&crole.roleKey=" + roleKey + "&crole.roleID="+roleID+"&search=${param.search}");
       document.croleForm.submit.click();
       alert("保存成功！");         
}

$(document).ready(function(){
	
	var url = basePath
	+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
	+ new Date().toLocaleString();
$.ajax({
		url : encodeURI(url),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var companylist = member.companylist;
			var data1 = new Array();
			data1[0] = {
					id : "<%=c.getCompanyID()%>",
					pid : '-1',
					text : "<%=c.getCompanyName()%>"
				};
			for (var i = 0; i < companylist.length; i++) {
				data1[i + 1] = {
					id : companylist[i].companyID,
					pid : companylist[i].companyPID,
					text : companylist[i].companyName
				};
			}
			var ts3 = new TreeSelector($("#companyID")[0],
					data1, -1);
			ts3.createTree();
			if(companyID!=''){
				$("#companyID").find("option[value='"+companyID+"']").attr("selected","selected");
			}
			changeValue();
		},
		error : function cbf(data) {
			alert("机构数据获取失败！");
		}
});
$("#companyID").change(function(){
	changeValue();
});
	//选择部门获得岗位与职务
	$("select#orgID").change(function(){
			var organizationID=$("select#orgID").val();
			var valName=$.trim($("select#orgID option:selected").text());
			$("input#organizationNameDesc").attr("value",valName.substring(valName.indexOf('├')+1));
			var url1 =basePath + "ea/corganization/sajax_ea_getAjaxOrganization.jspa?organizationID=" + organizationID + "&date=" + new Date().toLocaleString();
			$.ajax({
						url : encodeURI(url1),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							/** **添加部门列表** */
							var member = eval("(" + data + ")");
							var organization = member.organization;
							if(organization!=null&&organization.opostName!=null&&$.trim(organization.opostName)!=""){
								$("input#opostName").attr("value",organization.opostName);
							}else{
								alert(" @1 岗位获取为空!需手动填写");
								$("input#opostName").attr("value","");
							}
							if(organization!=null&&organization.odutiesName!=null&&$.trim(organization.odutiesName)!=""){
								$("input#roleName").attr("value",organization.odutiesName);
							}else{
								alert("@2 职务获取为空!需手动填写");
								$("input#roleName").attr("value","");
							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
			});
	});
});

function changeValue(){
	var companyName=$("#companyID").find("option:selected").text();
	$("#textfield").attr("value",companyName.substring(companyName.indexOf("├")+1));
	//-------------------
	var companyID1=$("#textfieldCompanyID").attr("value");
	if(companyID1.substr(0,7)=="company"){
		$("#companyID").attr("disabled","disabled");
		$("#textfieldCompanyID").attr("disabled","");
	}
	var companyID=$("#companyID").find("option:selected").attr("value");
	$("#orgID").html("");
	var url =basePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+companyID+"&date=" + new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			/** **添加部门列表** */
			var member = eval("(" + data + ")");
			var oList = member.organizationlist;
			var data2 = new Array();
			data2[0] = {
				id : companyID,
				pid : '-1',
				text : ""
			};
			for (var i = 0; i < oList.length; i++) {
				data2[i + 1] = {
					id : oList[i].organizationID,
					pid : oList[i].organizationPID,
					text : oList[i].organizationName
				};
			}
			ts = new TreeSelector($("#orgID")[0], data2, -1);
			ts.createTree();
			if(oragtionID!=''){
			$("select#orgID").find("option[value='"+oragtionID+"']").attr("selected","selected");
			}
		},
		error : function cbf(data) {	
			alert("数据获取失败！");
		}
	});
	//----------------------
}
</script>
</body>
</html>