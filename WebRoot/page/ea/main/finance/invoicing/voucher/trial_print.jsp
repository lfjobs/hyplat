<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>试算表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<style type="text/css">
		.dps{
		height: 20px;
		font-size: 11px;}	
		#but{
		height:30px;
		float: right;}
		.tr{
			font-weight:bold;
			height: 30px;
			font-size:13px;}
		table {
			border-collapse: collapse;
			border: 1px solid #000000;
		}
		table td {
			border: 1px solid #C0D9D9;
			height:20px;
		}
	</style>
	<style media="print">
		#but{
		display:none;}
		table td {
			border: 1px solid #000000;
			height:20px;
		}
	</style>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/finance/invoicing/voucher/trial_print.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var trial="${trial}";
		$(function(){
		if(trial=="null"){
			alert("该日期内没有数据");
		}
		})
	</script>
  </head>
  
  <body>
  <center>
    <div style="width: 790px;">	<br>  	
     <span><font size="5" style="font-weight:bold;">试算表</font></span><br>
   	 <input type="button" value="导出Excel表格" id="but" /><br><br>
    	<table align="center">     
    		<tr class="tr">
    			<td></td>
    			<!-- <td></td> -->
    			<td></td>
    			<td></td>
    			<td colSpan="2" align="center">期初</td>
    			<td colSpan="2" align="center">本期</td>
    			<td colSpan="2" align="center">期末</td>
    			<td></td>
    			<td colSpan="2" align="center">期末本位币</td>
    		</tr>
    		<tr id="tr" class="tr">
    			<td width="4%" align="center">序号</td>
    			<td width="16%" align="center">会计科目</td>
    			<!-- <td width="96px" align="center">科目说明</td> -->
    			<td width="9%" align="center">部门</td>
    			<td width="7%" align="center">借方金额</td>
    			<td width="7%" align="center">贷方金额</td>
    			<td width="7%" align="center">借方金额</td>
    			<td width="7%" align="center">贷方金额</td>
    			<td width="7%" align="center">借方金额</td>
    			<td width="7%" align="center">贷方金额</td>
    			<td width="4%" align="center">币别</td>
    			<td width="12%" align="center">借方本位币金额</td>
    			<td width="12%" align="center">贷方本位币金额</td>
    		</tr>
    		<c:forEach items="${list}" var="l" varStatus="a">
    			<tr class="dps">
    				<td align="left"><span>${a.index+1}</span></td>
    				<td align="left">
    					<span>${l.subjectsname}</span>
    					<input type="hidden" value="${l.subjectsname}"></td>
    				<%-- <td align="left">
    					<span>${l.subjectsname}</span>
    					<input type="hidden" value="${l.subjectsname}"></td> --%>
    				<td align="left">
    					<input type="hidden" name="${l.orgId}" id="${l.comId}" class="orgId">
    					<span calss="orgName"></span></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.dbAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.dbAmt}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.cbAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.cbAmt}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.DAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.DAmt}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.CAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.CAmt}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.deAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.deAmt}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.ceAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.ceAmt}"></td>
    				<td align="left">
    					<span>${l.curName}</span>
    					<input type="hidden" value="${l.curName}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.dedAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.dedAmt}"></td>
    				<td align="right">
    					<span><fmt:formatNumber value="${l.cedAmt}" pattern="#,##0.00"/></span>
    					<input type="hidden" value="${l.cedAmt}"></td>
    			</tr>
    		</c:forEach>
    	</table>
    </div>
  </body>
</html>
