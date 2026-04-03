<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="java.util.Date"%>
<%@page import="hy.ea.bo.human.Staff"%>
<%@page import="hy.ea.bo.Company"%>
<%@ page language="java" pageEncoding="UTF-8" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
	String filepath = request.getSession().getServletContext().getRealPath("/");
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
	Company c = (Company)session.getAttribute("currentcompany"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>(月度)教练培训质量排行榜-打印预览</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<style type="text/css">
	table.table td{
		text-align: center;
	}
</style>
</head>

<body style="overflow: scroll;">
	<div class="content" style="width:100%;margin-top: 10px;">
		<table width="99%" border="0" align="center" cellpadding="0"  style="margin-top: 5px;"
			cellspacing="0"  >
			<tr>
				<td height="25" align="center"><span style="font-size: x-large;">${sdate }至${edate}(月度)教练培训质量排行榜</span></td>
			</tr>
		</table>
			<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data"><input type="submit" name="submit" style="display:none"/>
				<table id="stafftable" width="99%" align="center" cellpadding="0" border="1" style="margin-bottom: 0px;"
					cellspacing="0" class="table" >
					<tr>
					 	<td style="font-size: large;text-align: left;">
					 		公司:<%=c.getCompanyName()%>&nbsp;&nbsp;科目:${docstatus=='01'?'科一':docstatus=='02'?'科二':docstatus=='03'?'科三':'科四'}
					 	</td>
					</tr>
				</table>
				<table id="stafftable" width="99%" align="center" cellpadding="0" border="1" style="margin-top: 0px;margin-bottom: 0px;"
					cellspacing="0" class="table" >
					<thead>
					     <tr>
					     	 <td rowspan="2"> 序号</td>
					    	 <td rowspan="2" width="100"> 教练</td>
					    	 <s:iterator value="#request.testDateList" var="t">
					    	 	<td colspan="4"><s:property value="#t"/></td>
					    	 </s:iterator>
					    	 <td colspan="4">合计</td>
					    	 <td rowspan="2">合格率</td>
					     </tr>
					     <tr>
					     	<s:iterator value="#request.testDateList" var="t">
					    	 	<td >总人</td>
					    	 	<td >合格</td>
					    	 	<td >一次不合格</td>
					    	 	<td >800</td>
					    	 </s:iterator>
					    	 <td >总人</td>
					    	 <td >合格</td>
					    	 <td >一次不合格</td>
					    	 <td >800</td>
					     </tr> 
					</thead>
					<tbody>
						<s:iterator value="#request.listOfListOfCoachsOfqualified" status="i" var="array">
						 	<tr>
						 		<td><s:property value="#i.count"/></td>
						 		<s:iterator	value="#array" var="a" begin="1">
						 			<td><s:property value="#a"/></td>
						 		</s:iterator>
						 	</tr>
						 </s:iterator>
					</tbody>
				</table>
				<table id="stafftable" width="99%" align="center" cellpadding="0" border="1" style="margin-top: 0px;"
					cellspacing="0" class="table" >
					<tr>
							<td  style="font-size: large;text-align: left;">
							<%request.setAttribute("currentTimeStamp",new Date());%>
							制表时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${currentTimeStamp}"/> <br/>
							</td>
						</tr>
				</table>	
			</form>
    </div>	
</body>
</html>