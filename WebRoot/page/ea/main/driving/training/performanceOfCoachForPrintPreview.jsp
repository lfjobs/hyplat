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
<title>(个人)教练业绩明细表-打印预览</title>
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
				<td height="25" align="center"><span style="font-size: x-large;">${sdate }至${edate }(个人)教练业绩统计明细表</span></td>
			</tr>
		</table>
			<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data"><input type="submit" name="submit" style="display:none"/>
				<table id="stafftable" width="99%" align="center" cellpadding="0" border="1"
					cellspacing="0" class="table" >
					<thead>
						 <tr>
						 	<td colspan="13" style="font-size: large;text-align: left;">
						 		公司:<%=c.getCompanyName()%>&nbsp;&nbsp;科目:${docstatus=='01'?'科一':docstatus=='02'?'科二':docstatus=='03'?'科三':'科四'}
						 	</td>
						 </tr>	
					     <tr>
					     	 <td> 序号</td>
					    	 <td> 参考时间</td>
						     <td>姓名</td>
						     <td>车型</td>
						     <td>身份证号码</td>
						     <td>教练</td>
						     <td>车牌号</td>
						     <td>得分</td>
						     <td>合格</td>
						     <td>不合格</td>
						     <td>缺考</td>
						     <td>误报</td>
						     <td>缴费情况</td>
					     </tr> 
					</thead>
					<c:set var="total" value="0" ></c:set>
					<c:set var="qualified" value="0" ></c:set>
					<c:set var="unqualified" value="0"></c:set>
					<c:set var="unqualified1" value="0"></c:set>
					<c:set var="unqualified2" value="0"></c:set>
					<c:set var="coach" value=""></c:set>
					<tbody>
						 <s:iterator value="#request.listOfStudent" status="i" var="bean">
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
						 		<td><s:property value="#bean[8]"/></td>
						 		<td><s:property value="#bean[9]"/></td>
						 		<td><s:property value="#bean[10]"/></td>
						 		<td><s:property value="#bean[11]"/></td>
						 		<s:if test="#bean[7]=='合格'">
						 			<c:set var="qualified" value="${qualified+1}"></c:set>
						 		</s:if>
						 		<s:elseif test="#bean[8]=='不合格'">
						 			<c:set var="unqualified" value="${unqualified+1}"></c:set>
						 		</s:elseif>
						 		<s:elseif test="#bean[9]=='缺考'">
						 			   <c:set var="unqualified1" value="${unqualified1+1}"></c:set>
						 		</s:elseif>
						 		<s:elseif test="#bean[10]=='误报'">
						 			    <c:set var="unqualified2" value="${unqualified2+1}"></c:set>
						 		</s:elseif>
						 		<c:set var="total" value="${total+1}"></c:set>
						 		<c:set var="coach" value="${bean[4]}"></c:set>
						 	</tr>
						 </s:iterator>	
					</tbody>
					<tfoot>
						<tr>
							<td colspan="13" style="font-size: large;text-align: left;">
							共参考: <c:out value="${total}"></c:out>
							合格: <c:out value="${qualified}"></c:out>
							不合格:  <c:out value="${unqualified}"></c:out>
							缺考:  <c:out value="${unqualified1}"></c:out>
							误报:  <c:out value="${unqualified1}"></c:out>
							合格率:<fmt:formatNumber value="${ qualified/total}" type="percent" pattern="#0.00%"/>
							</td>
						</tr>
						<tr>
							<td colspan="13" style="font-size: large;text-align: right;">
							教练：<c:out value="${coach}"></c:out>
							</td>
						</tr>
						<tr>
							<td colspan="1" style="font-size: large;text-align: left;">
							制表人：
							</td>
							<td colspan="3" style="font-size: large;text-align: left;">
							
							</td>
							<td colspan="1" style="font-size: large;text-align: left;">
							查询人：
							</td>
							<td colspan="3" style="font-size: large;text-align: left;">
							
							</td>
							<td colspan="1" style="font-size: large;text-align: left;">
							审核：
							</td>
							<td colspan="3" style="font-size: large;text-align: left;">
							
							</td>
						</tr>
						<tr>
							<td colspan="13" style="font-size: large;text-align: left;">
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