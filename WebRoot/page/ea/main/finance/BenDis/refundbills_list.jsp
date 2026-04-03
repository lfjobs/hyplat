<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<meta http-equiv="cache-control" content="no-cache" />
<title>退款单据</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script  type="text/javascript" src="<%=basePath%>js/ea/finance/BenDis/refundbills_list.js"></script>



		<script type="text/javascript">

        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
			var pNumber = "${pageNumber}";
			var token = 0;
			var stype = "${stype}";

			document.onkeydown = function(evt) {//捕捉回车 
				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
				var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
				if (key == 13) { //判断是否是回车事件。
					$("form#SearchForm")
							.find("input#journalNum")
							.each(
									function() {
										if ($(this).val() == "") {
											return false;
										} else {
											$("#SearchForm")
													.attr(
															"action",
															basePath
																	+ "/ea/cashierbills/ea_toSearch.jspa?pageNumber="
																	+ pNumber);
											document.SearchForm.submit.click();
										}
									});
				}
			}
		</script>
</head>
<body>
	<form name="CashierBillsform" id="CashierBillsform">

		<input type="submit" name="submit" style="display: none" />
		<input name="cashierBills.cashierBillsID" id="cashierBillsID"
			style="display: none" />
		<s:token />
	</form>
	<table class="flexme11">
		<thead>
			<tr>
				<th width="30" align="center">选择</th>
				<th width="200" align="center">公司名称</th>
				<th width="200" align="center">项目名称</th>
				<th width="150" align="center">黏贴单编号</th>
				<th width="80" align="center">单据类别</th>
				<th width="100" align="center">部门</th>
				<th width="60" align="center">责任人</th>
				<th width="60" align="center">付款方</th>
				<th width="60" align="center">制单人</th>
				<th width="80" align="center">制单日期</th>
				<th width="80" align="center">单据状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="pageForm.list">
				<tr id="${cashierBillsID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue"
						value="${cashierBillsID}" /></td>
					<td><span id="companyname">${companyname}</span></td>
					<td><span id="projectName">${projectName}</span></td>
					<td><span id="journalNum">${journalNum}</span></td>
					<td><span id="billsType">${billsType}</span></td>
					<td><span id="departmentname">${departmentname}</span></td>
					<td><span id="staffname">${staffname}</span></td>
					<td><span id="appropriationcompanyName">${appropriationcompanyName}</span>
					</td>
					<td><span id="inputName">${inputName}</span></td>
					<td><span id="cashierDate">${fn:substring(cashierDate,
							0, 10)}</span></td>
					<td><span id="statuss">${status=='04'?"审核中":
							status=='05'?"待会计审核":status=='06'?"待出纳审核":status=='07'?"已审核":
							status=='20'?"税务单据":status=='30'?"未审核作废":status=='11'?"驳回待修改":status=='09'?"待确认收款":status=='40'?"待确定预算":status=='45'?"已收款":status=='46'?"系统生成":status==null?"":"驳回作废"}</span>
						<input type="hidden" value="${status}" id="status" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="/ea/refund/ea_findRefundCashList.jspa?search=${search}&pageNumber=${pageNumber}&stype=${stype}">
		</c:param>
	</c:import>

	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	</script>
</body>
</html>
