<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
		%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>模拟测试管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<style type="text/css">
.windowJqm {
	left: 55%;
	width: 850px;
	margin-left: -450px;;
}

.underline {
	text-decoration: underline;
}

.sty {
	padding-left: 5px;
}

#industryClassification {
	width: 150px;
}
</style>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/admin_main.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/overlayer.css" />
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript"
	src="<%=basePath%>js/ea/production/bsimtest/BsimTest.js"></script>
<script type="text/javascript">
var type="${type}";
       		var treeID = "${department}";
	         
	        var treePID = '<%=c.getCompanyID()%>';
	        var treePName = '<%=c.getCompanyName()%>';
       		var token = 0;
            var id = ""; 
            var basePath='<%=basePath%>';
            var ppageNumber='${pageNumber}';
            var search='${search}';
            var acceName = '';  //附件查看赋值
            var times = '0';
            var vouch = '';  //凭证号传值
            var status="${status}"
            var search="${search}"
			var auditTime="${auditTime}";
		</script>

</head>
<body>
	<div>
		<table class="flexigrid">

			<thead>
				<tr>

					<th width="40" align="center">请选择</th>
					<th width="40" align="center">序号</th>
					<th width="70" align="center">产品行业</th>
					<th width="110" align="center">项目产品编号</th>
					<th width="70" align="center">产品条码</th>
					<th width="70" align="center">产品名称</th>
					<th width="70" align="center">产品规格</th>
					<th width="40" align="center">单价</th>
					<th width="60" align="center">
					  <c:if test="${status=='00'}">考核数量</c:if>
					  <c:if test="${status=='02'}">合格数量</c:if>
					  <c:if test="${status=='03'}">不合格数量</c:if>
					</th>
					<th width="30" align="center">金额</th>
					<th width="40" align="center">审核人</th>
					<th width="90" align="center">审核人部门</th>
					<th width="80" align="center">审核时间</th>
					<th width="40" align="center">状态</th>
				</tr>

			</thead>
			<tbody class="JQueryflexme">

				<%
						int number = 1;
					%>
				<s:iterator value="pageForm.list" var="list">
					<tr id="${bsimTestkey}">
						<td><input type="radio" name="a" class="JQuerypersonvalue" />
						</td>
						<td><span id="number"><%=number%></span></td>
						<td><span id="industryClassification">${industryClassification}</span>
						</td>
						<td><span id="itemNumber">${itemNumber}</span></td>
						<td><span id="goodBar">${goodBar}</span></td>

						<td><span id="goodName">${goodName}</span></td>
						<td><span id="goodStandard">${goodStandard}</span></td>
						<td><span id="price">${price}</span></td>

						<td>
						<c:if test="${status=='00'}">
						<span id="btnumber">${btnumber}</span></c:if>
						<c:if test="${status=='02'}">
						<span id="btnumber">${yieldnumber}</span></c:if>
						<c:if test="${status=='03'}">
						<span id="btnumber">${noyieldnumber}</span></c:if>
						</td>
						<td><span id="money">${money}</span></td>
						<td><span id="auditor">${auditor}</span></td>
						<td><span id="organizationName">${list.organizationName}</span></td>
						<td><span id="auditTime">${auditTime}</span></td>
						<td>
						      <span id="status" style="display:none;">${status}</span>
						       <s:if test="status=='00'">待检测</s:if>
						       <s:if test="status=='02'">合格</s:if>
						       <s:if test="status=='03'">不合格</s:if>
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
				value="ea/bsimtest/ea_getBsimtestList.jspa?pageNumber=${pageNumber}&type=${type}&status=${status}&search=${search}">
			</c:param>
		</c:import>
	</div>

	<!-- 合格 与 不合格-->
	<div class="jqmWindow "
		style="display: none; width: 450px; height: 350px; right: 20%; top: 10%;"
		id="jqModelSend">
		<form name="bsimtestForm" id="bsimtestForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />

			<div class="drag">模拟测试单</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">

				<tr>

					<td >审核意见：</td>
					<td><textarea style="width:200px;height: 100px;"
							name="bsimtest.auditoroption" id="auditoroption" class="put3"></textarea><span class="xx" id="auditoroptionSpan" style="color:red;">*</span></td>
				</tr>
				<tr>
					<td align="right">审核人部门：</td>
					<td align="left"><input name="bsimtest.organizationName"
						value="${department}" readonly="readonly" /></td>
				</tr>
				<tr>
					<td align="right">审核人姓名：</td>
					<td align="left"><input name="bsimtest.auditor"
						value="${account.staffName}"  readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right">审核时间：</td>
					<td align="left"><input name="bsimtest.auditTime" id="time"
						value="${auditTime}" readonly="readonly"/></td>
				</tr>
			</table>

			<div align="center" style="margin-top: 25px;">
				<input type="button" class="submitResult" id="pass" value="合格" /> <input
					type="button" class="submitResult" id="nopass" value="不合格" /> <input
					type="button" id="submitColsed" value="返回" onclick="div()" />
			</div>
			</center>
		</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>