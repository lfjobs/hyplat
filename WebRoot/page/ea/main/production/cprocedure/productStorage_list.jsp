<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
	<title>产品入库单</title>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script src="<%=basePath%>js/ea/production/cprocedure/productStorage_list.js"  type="text/javascript" ></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript">
        var basePath="<%=basePath%>";
        var id="";
        var dtnumber=0;
        var goodsId="";
        var status="";
        var fiveClear="${fiveClear}";
        var status="00";
	</script>
	<style type="text/css">
		.fieldName{
			text-align:right;
			width: 80px;
		}
		.goodsOutOfTheLibrary tr{
			height:30px;
		}
	</style>
</head>

<body>
<div  id="main_main" class="main_main"> 	<div id="fexdiv">
	<table class="fexlist">
		<thead>
		<tr>
			<th width="30" align="center">选择</th>
			<th width="130" align="center">入库编号</th>
			<th width="70" align="center">产品编号</th>
			<th width="80" align="center">产品名称</th>
			<th width="60" align="center">入库数量</th>
			<th width="60" align="center">仓库编号</th>
			<th width="60" align="center">仓库名称</th>
			<th width="100" align="center">仓库地址</th>
			<th width="60" align="center">单据状态</th>
			<th width="100" align="center">负责人</th>
			<th width="80" align="center">验收人</th>
			<th width="80" align="center">验收时间</th>
			<th width="80" align="center">单据时间</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageForm.list}" var="l">
			<tr name="dps" class="dps" id="${l.cashierbillsid}page">
				<td><input type="radio" name="radio" class="radio" id="${l.cashierbillsid}">
					<input type="hidden" class="ppId" value="${l.ppId}"></td>
				<td><span>${l.journalnum}</span></td>
				<td><span>${l.goodsnum}</span></td>
				<td><span>${l.goodsname}</span></td>
				<td><span>${l.quantity}</span></td>
				<td><span>--</span></td>
				<td><span>${l.drdepotName}</span></td>
				<td><span> -- </span></td>
				<td>
					<c:if test="${l.status==14}"><span>已验货</span></c:if>
					<c:if test="${l.status==15}"><span>已入库</span></c:if></td>
				<td><span>${l.staffName}</span></td>
				<td><span>${l.staffsName}</span></td>
				<td><span>${l.examinegoodsDate}</span></td>
				<td><span>${l.cashierDate}</span>
						<%-- 	<input type="hidden"  class="jNumOrder" value="${l.jNumOrder}"></td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div></div>
<form method="post" name="forms" id="forms">
	<input type="submit" name="submits" id="submits" style="display: none;">
	<input type="hidden" id="idd" name="utboundOrderVo.cashierbillsid">
</form>
<c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
			 value="ea/production/ea_getAccessToProductInformation.jspa?pageNumber=${pageNumber}">
	</c:param>
</c:import>
<iframe name="hidden" border="0"  height="0" frameborder="0"></iframe>
</body>
</html>
