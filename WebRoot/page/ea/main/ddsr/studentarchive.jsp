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
<title>学员归档管理</title>
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
<script src="<%=basePath%>js/ea/ddsr/studentarchive.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var studentarchiveID = "";
	var search = '${search}';
	
</script>
</head>
<body>
	<form name="adanceForm" id="adanceForm" method="post">
		<s:token></s:token>
		<div id="main_main" class="main_main">
		<input type="submit" name="submit" style="display:none" />
			<table class="JQueryflexme">
				<thead>
					<tr>
						<th width="30" align="center">选择</th>
						<th width="80" align="center">档案编号</th>
						<th width="100" align="center">报名时间</th>
						<th width="80" align="center">姓名</th>
						<th width="100" align="center">身份证</th>
						<th width="100" align="center">电话</th>
						<th width="80" align="center">证照类型</th>
						<th width="80" align="center">身份证复印件</th>
						<th width="50" align="center">照片</th>
						<th width="50" align="center">体检表</th>
						<th width="50" align="center">申请表</th>
						<th width="50" align="center">培训记录</th>
						<th width="50" align="center">学生证</th>
						<th width="100" align="center">暂住证（外地）</th>
						<th width="100" align="center">查询单（增驾）</th>
						<th width="100" align="center">驾驶复印件（增驾）</th>
						<th width="100" align="center">驾照复印件（毕业）</th>
						<th width="150" align="center">其他档案</th>
					</tr>
				</thead>
				<tbody id="tbwid">
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0]}">
							<td><input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" /></td>
							<td><span id="">${arr[1]}</span></td>
							<td><span id="">${arr[2]}</span></td>
							<td><span id="">${arr[3]}</span></td>
							<td><span id="">${arr[4]}</span></td>
							<td><span id="">${arr[5]}</span></td>
							<td><span id="">${arr[6]}</span></td>
							<td><span id=""><c:if test="${arr[7]==null||arr[7]=='' }">--</c:if><c:if test="${arr[7]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[8]==null||arr[8]=='' }">--</c:if><c:if test="${arr[8]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[9]==null||arr[9]=='' }">--</c:if><c:if test="${arr[9]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[10]==null||arr[10]=='' }">--</c:if><c:if test="${arr[10]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[11]==null||arr[11]=='' }">--</c:if><c:if test="${arr[11]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[12]==null||arr[12]=='' }">--</c:if><c:if test="${arr[12]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[13]==null||arr[13]=='' }">--</c:if><c:if test="${arr[13]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[14]==null||arr[14]=='' }">--</c:if><c:if test="${arr[14]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[15]==null||arr[15]=='' }">--</c:if><c:if test="${arr[15]!= null }">有</c:if></span></td>
							<td><span id=""><c:if test="${arr[16]==null||arr[16]=='' }">--</c:if><c:if test="${arr[16]!= null }">有</c:if></span></td>
							<td><span id="">${arr[17]}</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/studentarchive/ea_getListSTU.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 300px; right: 45%; top: 30%"
			id="jqModelSerch">
			<form name="SearchForm" id="SearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="260px" id="adaSearchTable">
					<tr>
						<td align="right">
							姓名：
						</td>
						<td>
							<input name="dpp.studentname" id="studentname"  class="xxx"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证：
						</td>
						<td>
							<input name="dpp.studentcard" id="studentcard" />
						</td>
					</tr>
					<tr>
						<td align="right">
							证照类型：
						</td>
						<td>
							<select name="dpp.registrationcarname" id="registrationcarname">
								<option value="">--选择--</option>
								<option value="A1">A1</option>
								<option value="A2">A2</option>
								<option value="A3">A3</option>
								<option value="B1">B1</option>
								<option value="B2">B2</option>
								<option value="C1">C1</option>
								<option value="C2">C2</option>
								<option value="C5">C5</option>
								<option value="D">D</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
</body>
</html>
