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
<title>退款单列表</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script src="<%=basePath%>js/ea/finance/BenDis/refundSheet_list.js"
	type="text/javascript"></script>




<script type="text/javascript">

var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token = 1;
	var rsid = "";
	var stype = "${stype}";

</script>
</head>
<body>

	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" style='height:30px;' name="SearchForm" method="post">
		<input type="hidden" name="stype" value="${stype}"/>
			<input type="submit" name="submit" style="display:none;" />
			
			<table>
			<tr>
			<td><strong>退款单</strong>&nbsp;&nbsp;&nbsp; 订单编号：</td>
			<td>
			<input type="text" style="width:90px;height:18px;"
				name="refundSheet.orderCode" />
			</td>
			<td>	
				&nbsp;用户账号：
			</td>
			<td>
			<input type="text" name="refundSheet.userAccount"
				style="width:90px;height:18px;" />
			</td>
			<td>
			<input type="button" class="input-button" value="  查询  "
				id="tosearch" />
				<input type="hidden" value="search" name="search" />
			<input type="hidden" id="rsid" name="refundSheet.rsid" />
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
					<th width="170" align="center">退款单编号</th>
					<th width="170" align="center">订单编号</th>
					<th width="170" align="center">下单时间</th>
					<th width="100" align="center">用户姓名</th>
					<th width="100" align="center">用户账号</th>
					<th width="100" align="center">会员类型</th>
					<th width="100" align="center">退款类型</th>
					<th width="100" align="center">退货原因</th>
					<th width="100" align="center">退款金额</th>
					<th width="150" align="center">退款说明</th>
					<th width="150" align="center">凭证</th>
					<th width="170" align="center">退货申请时间</th>
					<th width="170" align="center">处理时间</th>
					<th width="100" align="center">状态</th>
					<th width="170" align="center">公司</th>


				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${rsid}">
						<td><input type="radio" name="a" class="JQuerypersonvalue"
							value="${rsid}" />
						</td>

						<td><%=number%></td>
						<td><span id="refundCode">${refundCode}</span>
						</td>
						<td><span id="orderCode">${orderCode}</span>
						</td>
						<td><span id="orderDate">${fn:substring(orderDate,0,19)}</span>
						</td>

						<td><span id="userName">${userName}</span>
						</td>

						<td><span id="userAccount">${userAccount}</span>
						</td>
						<td><span id="vipType">${vipType}</span>
						</td>
						<td><span id="refundType" style="display:none;">${refundType}</span>
							<s:if test='refundType=="00"'>仅退款</s:if> <s:if
								test='refundType=="01"'>退款并退货</s:if></td>

						<td><span id="refundCause" style="display:none;">${refundCause}</span>
						    <s:if test="refundCause=='00'">七天无理由退换货</s:if>
							<s:if test="refundCause=='01'">收到商品破损/漏发</s:if>
							<s:if test="refundCause=='02'">商品与描述不符合</s:if>
							<s:if test="refundCause=='03'">商品质量问题</s:if>
							<s:if test="refundCause=='04'">未按时间发货</s:if>
						</td>
						<td><span id="refundMoney">${refundMoney}</span>
						</td>
						<td><span id="refundRemark">${refundRemark}</span>
						</td>
						<td><span id="voucherfile1" style="display:none;">${voucherfile1}</span>
							<span id="voucherfile2" style="display:none;">${voucherfile2}</span>
							<span id="voucherfile3" style="display:none;">${voucherfile3}</span>
							<s:if test='voucherfile1!=null&&voucherfile1!=""'>
								<a title="点击查看" href="#" onclick="lookImage('${voucherfile1}');">凭证1</a>
							</s:if> <s:if test='voucherfile2!=null&&voucherfile2!=""'>
								<a title="点击查看" href="#" onclick="lookImage('${voucherfile2}');">凭证2</a>
							</s:if> <s:if test='voucherfile3!=null&&voucherfile3!=""'>
								<a title="点击查看" href="#" onclick="lookImage('${voucherfile3}');">凭证3</a>
							</s:if></td>
						<td><span id="refundDate">${fn:substring(refundDate,0,19)}</span>
						</td>
						<td><span id="dealDate">${fn:substring(dealDate,0,19)}</span>
						</td>
						<td><span id="refundstate" style="display:none;">${refundstate}</span>
							<s:if test='refundstate=="00"'>买家提交退款申请</s:if> <s:if
								test='refundstate=="01"'>卖家同意退款</s:if> <s:if
								test='refundstate=="02"'>卖家拒绝退款</s:if> <s:if
								test='refundstate=="03"'>买家退货中</s:if><s:if
								test='refundstate=="04"'>卖家确认收货</s:if> <s:if
								test='refundstate=="05"'>卖家已银行打款</s:if></td>
						<td><span id="companyName">${companyName}</span>
						</td>






					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/refund/ea_getRefundSheetList.jspa?pageNumber=${pageNumber}&search=${search}&stype=${stype}">
			</c:param>
		</c:import>
	</div>




	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>