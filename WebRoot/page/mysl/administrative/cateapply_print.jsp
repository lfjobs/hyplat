<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>证书领用单据打印</title>
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
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
</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight: bold;font-size:16px;">&nbsp;
					绵阳市水利规划设计研究院(证书领用单)
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td width="10%" height="25" align="left">
						公司：
					</td>
					<td width="15%" align="left">
						${cateapply.companyname}
					</td>
					<td width="10%" align="left">
						部门：
					</td>
					<td width="15%" align="left">
					    ${cateapply.organizationname}
					</td>
					<td width="10%" align="left">
						领用人：
					</td>
					<td width="15%" align="left">
					   ${cateapply.cateusername}
					</td>
					<td width="25%" colspan="2" align="left">
						制单日期：${fn:substring(cateapply.addtime, 0, 19)}
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
						${cateapply.staffname}
					</td>
					<td width="10%" align="left">
						单据状态：
					</td>
					<td width="15%" align="left">
					<c:if test="${cateapply.status eq 00}">草稿</c:if><c:if test="${cateapply.status eq 01}">未审核</c:if><c:if test="${cateapply.status eq 02}">已审核</c:if><c:if test="${cateapply.status eq 03}">驳回</c:if><c:if test="${cateapply.status eq 04}">办理中</c:if><c:if test="${cateapply.status eq 05}">已办理</c:if>
					</td>
					<td width="50%" align="right">
						单据编号：<%
						com.mysl.bo.administrative.DtMycertificateapply data = (com.mysl.bo.administrative.DtMycertificateapply) request
								.getAttribute("cateapply");
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
						 ${cateapply.serialNumber}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>

			<br/>
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<tr height="30">
					<td width="100" align="center">
						证书名称
					</td>
					<td width="210">
						${cateapply.catename}
					</td>
					<td width="100" align="center">
						数量
					</td>
					<td width="210">
						${cateapply.catenum}
					</td>
				</tr>
				<tr height="30">
					<td align="center">
						领用时间
					</td>
					<td >
					    ${fn:substring(cateapply.cateusedate,0,16)}
					</td>
					<td align="center">
						用途
					</td>
					<td>
						${cateapply.cateuse}
					</td>
				</tr>
				<tr height="30">
					<td align="center">
						备注
					</td>
					<td colspan="3" align="center">
						<input type="text" style="width:100%; height: 100%; background: none; border: 0px; text-align:left" value="${cateapply.remarks}"/>
					</td>
				</tr>
			</table>
			<br/>
			<table width="620" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="100" height="25" align="right">
						科室负责人签名：
					</td>
					<td width="100">
						&nbsp;
					</td>
					<td width="110" align="right">
						批准人签名：
					</td>
					<td width="100">
						&nbsp;
					</td>
					<td width="110" align="right">
						领用人签名：
					</td>
					<td width="100">
						&nbsp;
					</td>
				</tr>
			</table>
			<br/>
		</div>
		<br />
		<br />
		<br />
		<br />
	</body>
</html>
