<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>权限移交记录打印</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 12px;
}

body {
	margin-left: 15px;
}

		 th, TH {
				font-size: 12px;
				border-color: #000000;
				height:18;
}
    </style>
  <script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sdate="${sdate}";
    var edate="${edate}";
</script>
	</head>
  <body>
  	 <table border="0" cellspacing="0" cellpadding="0" align="center" width="60%">
		  <tr>
			<td colspan="48" align="center"><span style="font-size: 20px"><font color="red">权限移交单</font> </span></td>
		  </tr>
		  </table>
		    <table border="1" cellspacing="0" cellpadding="0" align="center" width="60%" class="table">
		    <thead>
				
				<tr>
					<th >序号</th>
					<th >公司名称</th>
					<th>帐号名称</th>
					<th >原责任人起时间</th>
					<th >移交责任人止时间</th>
					<th >原责任人</th>
					<th >移交责任人</th>
				</tr>
				</thead>
				<% int cou=1; %>
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }"  align="center">
						<td>
							<span ><%=cou%> </span>
						</td>
						<td>
							<span >${arr[2]}</span>
						</td>
						<td>
							<span >${arr[3]}</span>
						</td>
						<td>
							<span >${arr[6]==null?"未知":arr[6]}</span>
						</td>
						<td>
							<span >${arr[7]==null?"未知":arr[7]}</span>
						</td>
						<td>
							<span >${arr[4]==null?"无":arr[4]}</span>
						</td>
						<td>
							<span >${arr[5]==null?"无":arr[5]}</span>
						</td>
					</tr>
					<% cou++; %>
				</c:forEach>
  	</table>
  </body>
</html>
