<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文件审批跟踪登录列表</title>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/ea/office_ea/document/fileAuditTrack.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

<script type="text/javascript">
var basePath="<%=basePath%>";
	var docId = "";
	var finishType = "${finishType}";
	var searchType = "${searchType}";
	var visiturl =  "${visiturl}";
	var search = "${search}";
</script>
</head>
<body>
	

	<div id="wsp">
		<table class="wspdoc">
			<thead>
				<tr>
					<th width="30" align="center">选择</th>
					<th width="30" align="center">序号</th>
					<th width="70" align="center">公文编号</th>

					<th width="200" align="center">文件标题</th>

					<th width="70" align="center">拟稿</th>
					<th width="70" align="center">审批</th>
					<th width="70" align="center">盖章</th>
					<th width="70" align="center">分发</th>
					<th width="70" align="center">阅读</th>
					<th width="70" align="center">归档</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list">
					<tr class="docs" id="${docId}${docid}">
						<td class="td_bg01"><input type="radio" name="radioGroup"
							class="JQuerypersonvalue" id="docId" value="${docId}${docid}" /></td>
						<td class="td_bg01"><span><%=number%></span></td>

						<td class="td_bg01"><span id="docNum">${docNum}${docnum}</span></td>

						<td class="td_bg01"><span id="title">${title}</span></td>
						<td class="td_bg01"><s:if test='status=="I"'>拟稿</s:if></td>

						<td class="td_bg01"><s:if test='status=="S"||status=="T"'>审批</s:if>
							<s:if test='status=="R"'>驳回</s:if> <s:if test='status=="U"'>不予通过</s:if>
						</td>
						<td class="td_bg01"><s:if test='status=="A"'>盖章</s:if></td>
						<td class="td_bg01"><s:if test='status=="P"'>分发</s:if></td>
						<td class="td_bg01"><s:if test='status=="O"'>阅读</s:if></td>
						<td class="td_bg01"><s:if test='status=="G"'>归档</s:if></td>

					</tr>
					<%
						number++;
					%>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="${visiturl}?track=00&searchType=${searchType}&pageNumber=${pageNumber}&search=${search}&finishType=${finishType}">
			</c:param>
		</c:import>
	</div>

	<form id="searchForm" name="searchForm" method="post">
		<div class="jqmWindow" id="jqModelSearch"
			style="width:500px;left:30%;top:10%;">
			<input type="submit" name="submit" style="display: none" />
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						<div id="titles">查询打印</div>
						<div class="close"></div>
					</div>
				</div>
				<div>
					<table style="width:100%;">
						<tr>
							<td align="right">文件标题：</td>
							<td align="left">&nbsp;&nbsp;&nbsp;<input
								name="documentSearchInfo.title" type="text" value="" />
							</td>
						</tr>
						<tr>
							<td align="right">时间：</td>
							<td align="left">从<input id="startDate"
								name="documentSearchInfo.sStart" type="text"
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', onpicked:function(){endDate.focus();}})" />&nbsp;到&nbsp;<input
								name="documentSearchInfo.sEnd" type="text" 
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startDate\')}'})"
								id="endDate" />
							</td>
						</tr>

						<tr>
							<td colspan="2" align="center"><input type="button"
								class="input-button" id="submitresult" value="打印预览" /> <input
								type="hidden" name="search" value="search" /> <input
								type="hidden" name="searchType" id="searchType" value="${searchType}"/> 
								<input
								type="hidden" name="finishType" id="finishType" value="${finishType}"/>
								<input
								type="hidden" name="track" value="10" id="track"/> <input
								name="documentSearchInfo.docType" type="hidden" value="" /> <input
								name="documentSearchInfo.docNum" type="hidden" value="" />
								<input
								name="documentSearchInfo.fromMember" type="hidden" value="" />
								<input
								name="documentSearchInfo.status" type="hidden" value="" />
								</td>

						</tr>


					</table>
				</div>

			</div>
	</form>





	<iframe name="hidden" width="0" height="0"></iframe>
</body>
</html>
