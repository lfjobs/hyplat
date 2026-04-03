<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目物品清单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript">
   var token = 0;
   var search="${search}";
   var basePath = '<%=basePath%>';
   var pNumber="${pageNumber}";
   var notoken = 0;
   var sdate="${sdate}";
   var edate="${edate}";
   var xiang="${costSheetBill.journalNum}";
   var people="${costSheetBill.staffID}";
   var type="${type}";
   var treeid="${treeid}";
   var treenums="${treeid}";
   var treeID = '<%=session.getAttribute("organizationID")%>';
   var journalNum="";
   var csdID="";
   var goodsID="";
</script>
<script src="<%=basePath%>js/ea/finance/invoicing/budget_fund.js"></script>
</head>
<body>
	<form name="fundForm" id="fundForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="fund">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">产品名称</th>
						<th width="165" align="center">产品编号</th>
						<th width="60" align="center">部门</th>
						<th width="80" align="center">责任人</th>
						<th width="60" align="center">产品类型</th>
						<th width="80" align="center">单据类别</th>
						<th width="80" align="center">产品预算单价</th>
						<th width="50" align="center">产品数量</th>
						<th width="80" align="center">余额</th>
						<th width="140" align="center">单据凭证号</th>
						<th width="120" align="center">制单日期</th>
					    <th width="120" align="center">款源日期</th>
						<th width="80" align="center">项目名称</th>
						<th width="80" align="center">单据状态</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0]}">
							<td>
							<input type="radio" name="a" class="JQuerypersonvalue" value="${arr[0]}" />
								
							</td>
							<td><span id="goodsname">${arr[1]}</span></td>
							<td><span id="goodsnum">${arr[2]}</span></td>
							<td><span id="departmentName">${arr[3]}</span></td>
							<td><span id="StaffName">${arr[4]}</span></td>
							<td><span id="typeID">${arr[5]}</span></td>
							<td><span id="billsType">${arr[6]}</span></td>
							<td><span id="Price">${arr[7]}</span></td>
							<td><span id="quantity">${arr[8]}</span></td>
							<td><span id="money">${arr[9]}</span></td>
							<td><span id="journalNum">${arr[10]}</span></td>
							<td><span id="cashierDate">${arr[11]}</span></td>
							<td><span id="startDate">${arr[12]}</span></td>
							<td><span id="projectName">${arr[13]}</span></td>
							<td><span id="status" style="display:none;">${arr[14]}</span>
							<c:if test='${arr[14]=="00"}'>未审核</c:if>
							<c:if test='${arr[14]=="01"}'>审核中-招标前</c:if>
							<c:if test='${arr[14]=="02"}'>已通过-招标前</c:if>
							<c:if test='${arr[14]=="03"}'>招标比价中</c:if>
							<c:if test='${arr[14]=="04"}'>待主管审核</c:if>
							<c:if test='${arr[14]=="05"}'>待会计审核</c:if>
							<c:if test='${arr[14]=="06"}'>待出纳审核</c:if>
							<c:if test='${arr[14]=="07"}'>已通过三审</c:if>
							<c:if test='${arr[14]=="08"}'>三审已归档</c:if>
							<c:if test='${arr[14]=="09"}'>待确认收款</c:if>
							<c:if test='${arr[14]=="11"}'>驳回待修改</c:if></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath" value="ea/product/ea_getZbPriceedList.jspa?search=${search}&pageNumber=${pageNumber}"></c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
