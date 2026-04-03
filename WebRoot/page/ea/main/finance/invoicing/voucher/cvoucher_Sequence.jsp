<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.Company"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String filepath = request.getSession().getServletContext().getRealPath("/");
			Company c = (Company)session.getAttribute("currentcompany");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>集团--序时账报表</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/finance/invoicing/voucher/cvoucher_sequence.js"></script>
		 <script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript">
var basePath="<%=basePath%>";
var pNumber=${pageNumber};
var voucherID='';
var search="${search}";

var companyid="<%=c.getCompanyID()%>";
var companyname="<%=c.getCompanyName()%>";
var pageNumber=<%=request.getParameter("pagepageNumber")%>;
var notoken = 0;
var token = 0;
var tokens = 0;
var personurl = "";
var sdate="${sdate}";
var edate="${edate}";
var kemu="${kemu}";
var keid="${keid}";
var peo="${peo}";
var vnum="${vnum}";
</script>
</head>
<body>
	<div id="main_main" class="main_main">
		<form name="voucherForm" id="voucherForm" method="post">
			<input type="submit" name="submit" style="display: none" />
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="200" align="center">公司名称</th>
							<th width="100" align="center">记账日期</th>
							<th width="200" align="center">凭证编号</th>
							<th width="150" align="center">科目名称</th>
							<th width="80" align="center">科目编号</th>
							<th width="150" align="center">摘要</th>
							<th width="100" align="center">借方金额</th>
							<th width="100" align="center">贷方金额</th>
							<th width="100" align="center">制单人</th>
						</tr>
					</thead>
					<tbody>
					 <c:forEach items="${pageForm.list}" var="arr">
						<tr>
							<td align="center">&nbsp;${arr[0]}</td>
							<td align="center">&nbsp;${arr[1]}</td>
							<td align="center">&nbsp;${arr[2]}</td>
							<td align="center">&nbsp;${arr[3]}</td>
							<td align="center">&nbsp;${arr[4]}</td>
							<td align="center">&nbsp;${arr[5]}</td>
							<td align="center">&nbsp;${arr[6]}</td>
							<td align="center">&nbsp;${arr[7]}</td>
							<td align="center">&nbsp;${arr[8]}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp"> <c:param name="actionPath" value="ea/vsequence/ea_getVsequenceList.jspa?pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&peo=${peo}&vnum=${vnum}"></c:param></c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			</form>
			</div>
			 <!-- 查询按钮 -->
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%" id="jqModelSearch">
				<form name="SearchForm" id="SearchForm" method="post" >
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="SearchTable">
					<tr>
						<td>查询条件 </td>
					</tr>
					<tr>
						<td>科目名称：</td>
						<td>
						<s:select list="%{#request.sublist}" style="width:200px" headerKey="" headerValue="请选择" listKey="subjectsNumbers" name="vouchers.subjectsID" listValue="subjectsName" theme="simple" id="ming">
						</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID">
							</select>
						</td>
					</tr>
					<tr>
						<td >记账期间：</td>
						<td>
						<input id="sdate"  onfocus="date(this);" style="width: 85px" />至<input id="edate"  onfocus="date(this);" style="width: 85px" />
						</td>
					</tr>
					<tr>
						<td >凭证编号：</td>
						<td>
						<input type="text" id="nums"/>
						</td>
					</tr>
				</table>
				<div style="text-align:center ;margin:0 auto">
					<input type="button" class="input-button" id="search" value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
				</form>
			</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>
