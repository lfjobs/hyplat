<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>耗材领用详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ page import="com.mysl.bo.administrative.DtMyconsumerecipt" %>
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
		
		#textDate {
			text-decoration: underline;
		}
		body td{
			font-size:11px;
		
		}
		</style>
		<script type="text/javascript">
		    var basePath = "<%=basePath%>";
		</script>
	</head>
	<body>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;">
				<tr>
					<td height="25" align="center" style="font-weight:bold;font-size:16px">
						&nbsp;耗材领用申请单
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
			  <tr>
			  </tr>
			  <tr>
			    <td width="5%" height="25" align="right">公司：</td>
			    <td width="25%" align="left">${dtMyconsumerecipt.companyname}</td>
			    <td width="13.5%" align="right">部门：</td>
			    <td width="10%" align="left">${dtMyconsumerecipt.organizationname}</td>
			    <td width="10%" align="center">责任人：${DtMyconsumerecipt.staffname}</td>
			  </tr>
			 <tr>
			    <td width="12%" height="25" align="right">制单人：</td>
			    <td colspan="2" align="left">${dtMyconsumerecipt.staffname}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制单日期：${fn:substring(dtMytravel.addtime, 0, 10)}</td>
			    <td width="11%" align="right" height="10">凭证号：</td>
			    <td width="10%" align="center" height="10"><% DtMyconsumerecipt data =(DtMyconsumerecipt)request.getAttribute("dtMyconsumerecipt");
														if (data != null) {
															StringBuffer barCode = new StringBuffer();
															barCode.append("<img src='");
															barCode.append(request.getContextPath());
															barCode.append("/CreateBarCode?data=");
															barCode.append(data.getSerialnumber());
															barCode
																	.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
															out.println(barCode.toString());
														} else {
															out.println("no data");
														}
												%>
											${dtMytravel.serialnumber}</td>
			  </tr>
			</table>
			<table width="600" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<tr>
					<td width="100" height="30" align="center">
						品名：
					</td>
					<td width="500"align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMyconsumerecipt.name}
					</td>
				</tr>
				<tr>
					<td height="40" align="center">
						数量：
					</td>
					<td colspan="2" align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMyconsumerecipt.amount}
					</td>
				</tr>
				<tr>
					<td height="60" align="center" width="100">
						备注：
					</td>
					<td align="left" width="500">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;${dtMyconsumerecipt.remark}</p> 
					</td>
				</tr>
			</table>
			<br/>
			<table width="620" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="92" height="25" align="right" style="font-size:12px">科室所：</td>
			    <td width="42">&nbsp;</td>
			    <td width="90" align="right" style="font-size:12px">分管院长：</td>
			    <td width="42">&nbsp;</td>
			    <td width="82" align="right" style="font-size:12px">人事处：</td>
			    <td width="42">&nbsp;</td>
			    <td width="82" align="right" style="font-size:12px">财务审核：</td>
			    <td width="42">&nbsp;</td>
			    <td width="80" align="right" style="font-size:12px">责任人签字：</td>
			    <td width="32">&nbsp;</td>
			  </tr>
			  <tr>
			    <td height="25" align="right" style="font-size:12px">总部总经理：</td>
			    <td>&nbsp;</td>
			    <td align="right" style="font-size:12px">总部部门主管：</td>
			    <td>&nbsp;</td>
			    <td align="right" style="font-size:12px">总部人事处：</td>
			    <td>&nbsp;</td>
			    <td align="right" style="font-size:12px">总财务审核：</td>
			    <td>&nbsp;</td>
			    <td align="right" style="font-size:12px"></td>
			    <td>&nbsp;</td>
			  </tr>
			</table>
	</body>
</html>
