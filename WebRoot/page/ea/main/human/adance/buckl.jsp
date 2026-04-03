<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>奖扣记录汇总</title>
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
<script src="<%=basePath%>js/ea/human/adance/buckl.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var bucklid = "";
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="bucklForm" id="bucklForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="buckl">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="120" align="center">姓名</th>
						<th width="120" align="center">部门</th>
						<th width="120" align="center">迟到</th>
						<th width="120" align="center">早退</th>
						<th width="120" align="center">旷工</th>
						<s:iterator value="beans">
							<th width="80" align="center">${confname }</th>
						</s:iterator>
					</tr>
				</thead>
				<tbody id="tbwid">
					<s:iterator var="arr" value="pageForm.list">
						<tr id="${arr[0]}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" /></td>
							<td><span id="">${arr[1] }</span></td>
							<td><span id="">${arr[2] }</span></td>
							<td><span id="">
								<c:if test="${arr3 == null }">0</c:if>
								<c:if test="${arr3 != null }">${arr[3] }</c:if>次
							</span></td>
							<td><span id="">
								<c:if test="${arr4 == null }">0</c:if>
								<c:if test="${arr4 != null }">${arr[3] }</c:if>次
							</span></td>
							<td><span id="">
								<c:if test="${arr5 == null }">0</c:if>
								<c:if test="${arr5 != null }">${arr[5] }</c:if>次
							</span></td>
							<s:iterator value="beans" status="s">
								<td><span id="">
									<c:if test="${arr[s.index+7] == null}">0 小时</c:if>
									<c:if test="${arr[s.index+7] != null}">${arr[s.index+7]} 小时</c:if>
								</span></td>
							</s:iterator>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/buckl/ea_getAwarBuckl.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 300px; right: 45%; top: 30%"
			id="jqModelSerch">
			<form name="jqModelSerchForm" id="jqModelSerchForm" method="post">
			<input type="submit" name="submit" style="display:none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="260px" id="SearchTable">
					<tr>
						<td align="right">
							日期时间：
						</td>
						<td>
							<input name="seachdate" id="seachdate" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" readonly="readonly"/>
						</td>
					</tr>
					
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchBuckl"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
</body>
</html>
