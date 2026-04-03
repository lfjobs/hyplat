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
<title>(年度)各科目合格率统计报表-打印预览</title>
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
				<td height="25" align="center"><span style="font-size: x-large;">${sdate }至${edate}(年度)各科目合格率统计</span></td>
			</tr>
		</table>
			<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data"><input type="submit" name="submit" style="display:none"/>
				<table id="stafftable" width="99%" align="center" cellpadding="0" border="1"
					cellspacing="0" class="table" >
					<thead>
						 <tr>
						 	<td colspan="12" style="font-size: large;text-align: left;">
						 		公司:<%=c.getCompanyName()%>
						 	</td>
						 </tr>	
					     <tr>
					     	 <td> 序号</td>
					    	 <td> 月份</td>
					    	 <td> 科目</td>
						     <td>参考</td>
						     <td>合格</td>
						     <td>不合格</td>
						     <td>缺考</td>
						     <td>误报</td>
						     <td>合格率</td>
					     </tr> 
					</thead>
					<tbody>
						 <s:iterator value="#request.listQualificationOfSubjects" status="i" var="bean">
						 	<tr>
						 		<td><s:property value="#i.count"/></td>
						 		<td><s:property value="#bean[0]"/></td>
						 		<td><s:property value="#bean[1]"/></td>
						 		<td><s:property value="#bean[2]"/></td>
						 		<td><s:property value="#bean[3]"/></td>
						 		<td><s:property value="#bean[4]"/></td>
						 		<td><s:property value="#bean[5]"/></td>
						 		<td><s:property value="#bean[6]"/></td>
						 		<td><s:property value="#bean[7]"/></td>
						 	</tr>
						 </s:iterator>	
					</tbody>
					<tfoot>
						<tr>
							<td colspan="12" style="font-size: large;text-align: left;">
							<%request.setAttribute("currentTimeStamp",new Date());%>
							制表时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${currentTimeStamp}"/> <br/>
							</td>
						</tr>
					</tfoot>	
				</table>	
			</form>
    </div>	
</body>
</html>