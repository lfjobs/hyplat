<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>盖章申请单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ page import="com.mysl.bo.administrative.DtMysealregistration" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/taxsprint.js"></script>
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
					<td height="25" align="center" style="font-weight: bold;font-size:16px">
						&nbsp;绵阳市水利规划设计研究院盖章申请单
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
			  <tr>
			  </tr>
			  <tr>
			    <td width="5%" height="25" align="right">公司：</td>
			    <td width="25%" align="left">${dtMysealregistration.companyname}</td>
			    <td width="13.5%" align="right">部门：</td>
			    <td width="10%" align="left">${dtMysealregistration.organizationname}</td>
			    <td width="10%" align="center">责任人：${dtMysealregistration.staffname }</td>
			  </tr>
			  <tr>
			    <td width="12%" height="25" align="right">制单人：</td>
			    <td colspan="2" align="left">${dtMysealregistration.staffname}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制单日期：${fn:substring(dtMysealregistration.addtime, 0, 10)}</td>
			    <td width="11%" align="right" height="10">凭证号：</td>
			    <td width="10%" align="center" height="10"><% 
			    								DtMysealregistration data =(DtMysealregistration)request.getAttribute("dtMysealregistration");
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
												%><br/>
												${dtMysealregistration.serialnumber}</td>
			  </tr>
			</table>
			<table width="600" border="0" cellpadding="0" cellspacing="0"
				style="background: #FFFFFF;" class="table">
				<tr>
					<td   align="center" height="30">
						工程名称：
					</td>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.projectname}
					</td>
					<td  align="center">
						经办人：
					</td>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.agentpersonname}
					</td>
					<td align="center">
						接收人：
					</td>
					<td  align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.recipientpersonname}
					</td>
				</tr>
				<tr>
					<td align="center" height="30">
						盖章类型：
					</td>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.sealtype}
					</td>
					<td align="center">
						资料类型：
					</td>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.datatype}
					</td>
					<td  align="center">
						数量/份：
					</td>
					<td  align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.quantity}
					</td>
				</tr>
				<tr>
					<td  align="center" height="30">
						办理情况：
					</td>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.dealsituation}
					</td>
					<td  align="center">
						接收数量：
					</td>
					<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.recipientquantity}
					</td>
					<td  align="center">
						接收时间：
					</td>
					<td  align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.recipienttime}
					</td>
				</tr>
				<tr>
					<td  align="center" height="30">
						领导人：
					</td>
					<td align="left" colspan="3">
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.leadersperson}
					</td>
					<td  align="center">
						联系电话：
					</td>
					<td align="left" >
						&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.phone}
					</td>
				</tr>
				<tr>
					<td height="60" align="center" >
						备注：
					</td>
					<td align="left" colspan="5">
						<p>&nbsp;&nbsp;&nbsp;&nbsp;${dtMysealregistration.note}</p> 
					</td>
				</tr>
			</table>
			<table width="620" border="0" cellpadding="0" cellspacing="0">
			  <tr>
			    <td width="92" height="25" align="right" style="font-size:12px">负责人签名：</td>
			    <td width="42">&nbsp;</td>
			    <td width="90" align="right" style="font-size:12px">部门主管签名：</td>
			    <td width="42">&nbsp;</td>
			    <td width="82" align="right" style="font-size:12px">责任人签名：</td>
			    <td width="42">&nbsp;</td>
			  </tr>
			</table>
		</div>
		<br />
		<br />
		<br />
		<br />
	</body>
</html>
