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
<title>采购申请单</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/purBudSheet_check.js"></script>




<script type="text/javascript">

    var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token = 1;
	var pcId = "";
	var fiveClear="${fiveClear}";
</script>

</head>
<body>

	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post">
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>采购申请审核</strong>&nbsp;&nbsp;&nbsp;凭证号：</td>
					<td><input type="text" name="purchaseCheck.journalNum" style="width:90px;height:18px;" /></td>
					<td>&nbsp;申请人：</td>
					<td><input type="text" name="purchaseCheck.applyor" style="width:90px;height:18px;" /></td>
					<td>&nbsp;单据状态：</td>
					<td><select name="purchaseCheck.status">
					            <option value='' selected="selected">全部</option>
					            <option value='00'>待审核</option>
					            <option value='01'>通过</option>
					            <option value='02'>驳回</option>
					
					</select></td>
					<td><input type="button" class="input-button" value="  查询  " style="margin:0px;margin-left:5px;"
						id="tosearch" /> <input type="hidden" value="search" name="search" />
						<input type="hidden" id="pcId" name="purchaseCheck.pcId" />
						<input type="hidden" name="fiveClear" 
								value="${fiveClear}" />
						</td>
				</tr>
			</table>
		</form>

	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="30" align="center">
						选择
					</th>
					<th width="30" align="center">
						序号
					</th>
					<th width="200" align="center">
						凭证号
					</th>
					<th width="70" align="center">
						单据类型
					</th>
					<th width="200" align="center">
						申请公司
					</th>
					<th width="100" align="center">
						申请部门
					</th>
					<th width="90" align="center">
						申请人
					</th>
					<th width="150" align="center">
						申请日期
					</th>
					<th width="200" align="center">
						审核公司
					</th>
					<th width="100" align="center">
						审核部门
					</th>
					<th width="70" align="center">
						审核人
					</th>
					<th width="150" align="center">
						审核日期
					</th>
					<th width="90" align="center">
						审核状态
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${pcId}">
					     <td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${pcId}" />
						</td>
						<td>
							<span><%=number%></span>
						</td>
						<td>
							<span id="journalNum">${journalNum}</span>
							<span id="cashierBillsID" style="display:none;">${cashierBillsID}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="comapplyName">${comapplyName}</span>
						</td>
						
						<td>
							<span id="orgapplyName">${orgapplyName}</span>
						</td>
						<td>
							<span id="applyor">${applyor}</span>
						</td>
						<td>
							<span id="applyTime">${fn:substring(applyTime,0,19)}</span>
						</td>
						<td>
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="organizationName">${organizationName}</span>
						</td>
						<td>
							<span id="auditor">${auditor}</span>
						</td>
						
						<td>
							<span id="dcheckTime">${fn:substring(dcheckTime,0,19)}</span>
						</td>
						
						<td>
						   <span id="status" style="display:none;">${status}</span>

						   <s:if test="status=='00'">待审核</s:if>
						   <s:if test="status=='01'">通过</s:if>
						   <s:if test="status=='02'">驳回</s:if>

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
				value="ea/purchasebids/ea_findAuditPurSheetList.jspa?pageNumber=${pageNumber}&search=${search}&fiveClear=${fiveClear}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>