<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文件责任人跟踪表</title>

<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 18px;
}

.td {
	border: #cccccc 1px solid;
}
</style>
</head>
<body>
	<table width="800" border="0" align="center" style="margin-top:20px;">
		<tr>
			<td align="center" colspan="6">
				<h2>文件审批责任人跟踪表</h2>
			</td>
		</tr>
	</table>
	<table width="800" align="center" class="table" style="font-size:16px;"
		cellpadding="10">

		<tr align="center">
			<td width="70" height="26">文件标题</td>
			<td height="26" align="left" colspan="5">${document.title}</td>

		</tr>
		<tr align="center">
			<td width="70">主题词</td>
			<td height="26" align="left" colspan="5">${document.theme}</td>
		</tr>
		<tr>
			<td height="26">文件编号</td>
			<td width="160">${document.docNum}</td>
			<td width="70">文件缓急</td>
			<td width="80" align="left"><c:choose>
					<c:when test='${document.emergencyType=="p"}'>普通</c:when>
					<c:when test='${document.emergencyType=="j"}'>急件</c:when>
					<c:when test='${document.emergencyType=="t"}'>特急</c:when>
					<c:otherwise>${document.emergencyType}</c:otherwise>
				</c:choose>
			</td>
			<td width="70">公文类型</td>
			<td align="left"><c:choose>
					<c:when test='${document.docType=="aa"}'>董事会会议决定文件</c:when>
					<c:when test='${document.docType=="bb"}'>董事长办公室文件</c:when>
					<c:when test='${document.docType=="cc"}'>总裁办公室文件</c:when>
					<c:when test='${document.docType=="dd"}'>总部人事处文件</c:when>
					<c:when test='${document.docType=="ee"}'>总部办公室文件</c:when>
					<c:when test='${document.docType=="ff"}'>总部财务处文件</c:when>
					<c:when test='${document.docType=="gg"}'>总部教务(生产)处文件</c:when>
					<c:when test='${document.docType=="hh"}'>总部营销处文件</c:when>
					<c:when test='${document.docType=="jj"}'>总部教务部文件</c:when>
					<c:otherwise>总部服务(创收)平台</c:otherwise>
				</c:choose></td>

		</tr>
		<tr align="center" height="22">
			<td height="26">申报公司</td>
			<td colspan="5" align="left">${document.companyName}</td>
		</tr>
		<tr align="center" height="22">
			<td height="26">申报部门</td>
			<td colspan="5" align="left">${document.deptNameOfDraft}</td>
		</tr>
		<tr align="center" height="22">
			<td height="26">申报人</td>
			<td colspan="5" align="left">${document.drafterName}</td>
		</tr>
		<tr align="center" height="22">
			<td height="26">甲方</td>
			<td colspan="5" align="left"><span id="partyAName">${document.partyAName}</span>
				<span id="partyAstaffnames">${document.partyAstaffnames}</span> <span
				id="staffIdentityCardA">${document.staffIdentityCardA}</span></td>
		</tr>
		<tr align="center" height="22">
			<td height="26">乙方</td>
			<td colspan="5" align="left"><span id="partyBName">${document.partyBName}</span>
				<span id="partyBstaffnames">${document.partyBstaffnames}</span> <span
				id="staffIdentityCardB">${document.staffIdentityCardB}</span></td>
		</tr>
		<tr align="center" height="22">
			<td height="26">有效期</td>
			<td colspan="5" align="left">${fn:substring(document.startValidity,
				0,10)}至${fn:substring(docuement.endValidity, 0, 10)}</td>
		</tr>


	</table>
	<table width="800" align="center" class="table" cellpadding="10">
		<tr align="center" height="22">
			<td height="26" colspan="9">审批记录</td>
		</tr>
		<tr align="center">
			<td style="width:15%;">状态</td>
			<td>意见</td>
			<td style="width:20%;">签字</td>

		</tr>
		<tr align="center">
			<td height="80">拟稿</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>
		<tr align="center">
			<td height="80">审批</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>
		<tr align="center">
			<td height="80">盖章</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>
		<tr align="center">
			<td height="80">分发</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>
		<tr align="center">
			<td height="80">阅读</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>
		<tr align="center">
			<td height="80">归档</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>

		</tr>

	</table>
</body>
</html>
