<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收货单列表</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script src="<%=basePath%>js/ea/finance/BenDis/consigneeSheet_list.js"
	type="text/javascript"></script>




<script type="text/javascript">

var basePath="<%=basePath%>";
var search = "${search}";
var  ppageNumber = "${pageNumber}";
var token = 1;
var csid = "";
var stype = "${stype}";
var cusType = "${cusType}";
</script>
</head>
<body>
    
	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">
		
		<form id="SearchForm" name="SearchForm" method="post" >
		<input type="hidden" name="stype" value="${stype}"/>
		<input type="hidden" name="cusType" value="${cusType}"/>
		<input type="submit" name="submit" style="display:none;"/>
		<table>
		<tr>
		<td>
		<strong>收货单&nbsp;&nbsp;&nbsp;</strong>订单编号：
		</td>
		<td>
		<input type="text"
			style="width:90px;height:18px;" name="consigneeSheet.orderCode"/>
		</td>
		<td>
			&nbsp;用户账号：
		</td>
		<td>
		<input type="text" name="consigneeSheet.userAccount" style="width:90px;height:18px;" />
		</td>
		<td>
		<input
			type="button" class="input-button" value="  查询  "  id="tosearch"/>
			<input
			type="hidden" value="search" name="search"/>
			<input
			type="hidden" id="csid" name="consigneeSheet.csid"/>
		</td>
			</tr>
			</table>
		</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="170" align="center">收货单编号</th>
					<th width="170" align="center">订单编号</th>
					<th width="150" align="center">下单时间</th>
					<th width="100" align="center">用户姓名</th>
					<th width="100" align="center">用户账号</th>
					<th width="100" align="center">会员类型</th>
					<th width="100" align="center">收货人名称</th>
					<th width="100" align="center">收货人电话</th>
					<th width="100" align="center">邮编</th>
					<th width="180" align="center">收货地址</th>
					<th width="150" align="center">收货时间</th>
					<th width="100" align="center">发货人名称</th>
					<th width="100" align="center">发货人电话</th>
					<th width="180" align="center">发货地址</th>
					<th width="150" align="center">发货时间</th>
					<th width="100" align="center">公司</th>
					

				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${csid}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${csid}" /></td>

						<td><%=number%></td>
						<td><span id="consigneCode">${consigneCode}</span></td>
						<td><span id="orderCode">${orderCode}</span></td>
						<td><span id="orderDate">${orderDate}</span></td>

						<td><span id="userName">${userName}</span></td>

						<td><span id="userAccount">${userAccount}</span></td>
						<td><span id="vipType">${vipType}</span></td>
						<td><span id="consigneeName">${consigneeName}</span></td>

						<td><span id="consigneeTel">${consigneeTel}</span></td>
						<td><span id="postcode">${postcode}</span></td>
						<td><span id="consigneeAddress">${consigneeAddress}</span></td>
						<td><span id="consignneDate">${fn:substring(consignneDate,0,19)}</span></td>
						<td><span id="senderName">${senderName}</span></td>
						<td><span id="senderTel">${senderTel}</span></td>
						<td><span id="sendAddress">${sendAddress}</span></td>
						<td><span id="sendDate">${fn:substring(sendDate,0,19)}</span></td>
						<td><span id="companyName">${companyName}</span></td>
						
						
						
			
						

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/consignee/ea_getConsigneeSheetList.jspa?pageNumber=${pageNumber}&search=${search}&stype=${stype}&cusType=${cusType}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>