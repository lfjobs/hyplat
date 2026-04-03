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
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/purBudSheet_list.js"></script>




<script type="text/javascript">

    var basePath="<%=basePath%>";
	var search = "${search}";
	var ppageNumber = "${pageNumber}";
	var token = 1;
	var cashierBillsID = "";
	var fiveClear="${fiveClear}";
	var type="${type}";
</script>

</head>
<body>

	<div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post">
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>采购申请单</strong>&nbsp;&nbsp;&nbsp;产品编号：</td>
					<td><input type="text" style="width:90px;height:18px;"
						name="cashierBills.projectCode" /></td>
					<td>&nbsp;产品名称：</td>
					<td><input type="text" name="cashierBills.projectName"
						style="width:90px;height:18px;" /></td>
					<td>&nbsp;凭证号：</td>
					<td><input type="text" name="cashierBills.journalNum"
						style="width:90px;height:18px;" /></td>
					<td>&nbsp;单据状态：</td>
					<td><select name="cashierBills.status">
					            <option value='' selected="selected">全部</option>
					            <option value='00'>草稿</option>
					            <option value='01'>审核中</option>
					
					</select></td>
					<td><input type="button" class="input-button" value="  查询  " style="margin:0px;margin-left:5px;"
						id="tosearch" /> <input type="hidden" value="search" name="search" />
						<input type="hidden" id="cashierBillsID" name="cashierBills.cashierBillsID" />
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
						公司名称
					</th>
					<th width="150" align="center">
						产品编号
					</th>
					<th width="150" align="center">
						产品名称
					</th>
					<th width="150" align="center">
						单据凭证号
					</th>
					<th width="150" align="center">
						单据类别
					</th>
					<th width="100" align="center">
						责任人部门
					</th>
					<th width="70" align="center">
						责任人
					</th>
					<th width="70" align="center">
						制单人
					</th>
					<th width="150" align="center">
						制单日期
					</th>
					<th width="90" align="center">
						单据状态
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr id="${cashierBillsID}">
					     <td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${cashierBillsID}" />
						</td>
						<td>
							<span><%=number%></span>
						</td>
						<td>
							<span id="companyName">${companyName}</span>
						</td>
						<td>
							<span id="projectCode">${projectCode}</span>
						</td>
						<td>
							<span id="projectName">${projectName}</span>
						</td>
						
						<td>
							<span id="journalNum">${journalNum}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="departmentName">${departmentName}</span>
						</td>
						<td>
							<span id="staffName">${staffName}</span>
						</td>
						<td>
							<span id="inputName">${inputName}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(cashierDate,0,19)}</span>
						</td>
						
						<td>
						   <span id="status" style="display:none;">${status}</span>

						
						   <s:if test="status=='00'">草稿</s:if>
						   <s:if test="status=='01'">审核中</s:if>
							
						
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
				value="ea/purchasebids/ea_getPurcBugetSheetList.jspa?pageNumber=${pageNumber}&search=${search}&fiveClear=${fiveClear}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>