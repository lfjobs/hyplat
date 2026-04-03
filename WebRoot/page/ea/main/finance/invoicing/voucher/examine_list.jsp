<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    <title>凭证审核</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<script src="<%=basePath%>js/ea/formatMoney.js"></script>
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" 
			type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" 
			type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />		
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/ea/finance/invoicing/voucher/examine_list.js" 
			type="text/javascript" ></script>
		<style type="text/css">
			.button{
				line-height:10px;
				height:24px;
				width:40px;
				margin:3px;
				border:1px solid #336699;
				background : repeat-x 0px -2px;
			}
			.spans{
				display:-moz-inline-box;
				display:inline-block;
				width:100%;
				text-align: left;}
			.tbody{
				display: block;
				overflow: auto;}
			#tb2{
				display: block;
				height:350px;
				}
			.date{
				width: 120px;}
			.number{
				width: 120px;}
		</style>
		<script type="text/javascript">
			var basePath = "<%=basePath%>";
			var Id="";
			var status="";
		</script>
  </head>
  
  <body>
    <div id="main_main" class="main_main">
    	<div id="fex1">
    	<form action="" method="post" id="form2" name="form2">
    		<table class="fexlist">
    		 <thead>
    			<tr>
    				<th width="20" align="center"></th>
    				<th width="80" align="center">凭证日期</th>
    				<th width="80" align="center">凭证号码</th>
    				<th width="80" align="center">凭证类别</th>
    				<th width="80" align="center">凭证来源</th>
    				<th width="80" align="center">借方金额</th>
    				<th width="80" align="center">贷方金额</th>
    				<th width="103" align="center">制单人</th>
    				<th width="77" align="center">部门</th>
    				<th width="80" align="center">审核人员</th>
    				<th width="94" align="center">审核时间</th>
    				<th width="43" align="center">状态</th>
    			</tr>
    		 </thead>
			 <tbody class="tbody">
			 	<c:forEach items="${pageForm.list}" var="l" varStatus="a">
			 	 	<tr id="${l.voucherid}" class="main"> 
			 	 		<td><input type="checkbox" name="voucherIds" class="check" value="${l.voucherid}">
			 	 			<input type="hidden" class="hid" value="${l.vouchercategory}"></td>
			 			<td><span class="spans">${l.voucherdate}</span></td>
			 			<td><span class="spans">${l.journalnum}</span></td>	
			 			<td><span class="spans">${l.VTName}</span></td>	
			 			<td><span class="spans">${l.voucherorigin}</span></td>
			 			<td><span class="spans" style="text-align:right;display: block;"></span></td>
			 			<td><span class="spans" style="text-align:right;display: block;"></span></td>		 			
			 			<td><span class="spans">${l.makepeople}</span></td>
			 			<td><span class="spans">${l.orgName}</span></td>
			 			<td><span class="spans">${l.auditingpeo}</span></td>
			 			<td><span class="spans">${l.auditingdate}</span></td>
						<c:if test="${l.vouchercategory==1}">
			 			<td><span class="spans">未审核</span></td>
			 			</c:if>
						<c:if test="${l.vouchercategory==2}">
			 			<td><span class="spans">退审核</span></td>
			 			</c:if>
			 			<c:if test="${l.vouchercategory==3}">
			 			<td><span class="spans">已审核</span></td>
			 			</c:if>
			 			<c:if test="${l.vouchercategory==4}">
			 			<td><span class="spans">退记账</span></td>
			 			</c:if> 
			 		</tr>
			 	</c:forEach>
			 </tbody> 
    		</table>
    		<input type="submit" id="submit2" name="submit2" style="display: none">
    		</form>
    <c:import url="../../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/voucher/ea_getVoucherExamineList.jspa?pageNumber=${pageNumber}&search=${search}&otype=pzsh">
		</c:param>
	</c:import>
    	</div>
    	
    	<div style="overflow: auto;" id="fex2">
    		<table class="fexlist2">
    		 <thead>
    			<tr>
    				<th width="20" align="center">序号</th>
    				<th width="104" align="center">会计科目</th>
    				<th width="69" align="center">借贷方</th>
    				<th width="69" align="center">部门</th>
    				<th width="89" align="center">本位币金额</th>
    				<th width="89" align="center">记账金额</th>
    				<th width="69" align="center">币别</th>
    				<th width="69" align="center">汇率</th>
    				<th width="69" align="center">数量</th>
    				<th width="89" align="center">摘要</th>
    				<th width="170" align="center">供应商客户</th>
    			</tr>
    		 </thead>
    		 <tbody id="tb2">
    		 	<tr class="model1 trs">
    		 		<td><span class="spans"></span></td><td><span class="spans"></span></td><td><span class="spans"></span></td>
    		 		<td><span class="spans"></span></td><td><span style="text-align:right;display: block;"></span></td>
    		 		<td><span style="text-align:right;display:block;"></span></td><td><span class="spans"></span></td>
    		 		<td><span class="spans"></span></td><td><span class="spans"></span></td>
    		 		<td><span class="spans"></span></td><td><span class="spans"></span></td>
    		 	</tr>
    		 </tbody>
    		</table>
    	</div>
    	
    </div>
    <iframe name="hidden"border="0"  height="0" frameborder="0"></iframe>
 </body>
</html>
