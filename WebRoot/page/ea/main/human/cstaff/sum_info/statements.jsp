<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事在职员工信息统计报表</title>
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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/sum_info/statements.js"></script>
<script type="text/javascript">
 var  basePath='<%=basePath%>';           
 var  pNumber =${pageNumber};  
 var  search='${search}';
 var  token = 0 ;
 var actionName='${actionName}';
 var actionNameExcel='${actionNameExcel}';
 var basicInfo='${basicInfo}';
 var conditions='${conditions}';
</script>
	</head>
	<body>
		<div>
		<form id="myform" name="myform" action="" method="post">
		<input type="submit" name="submit" style="display: none" />
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith" >
						<th width="50" align="center" >
							分类信息
						</th>
					<c:forEach items="${lists[0]}" var="headers" varStatus="status" >
						<th width="65" align="center">
							${headers[0]}
						</th>
					</c:forEach>
					<th width="50" align="center" >
							统计
						</th>
					</tr>
				</thead>
				<tbody>
				<% int number=0; %>
					<c:forEach var="objs" items="${lists}"  varStatus="i">
						<tr id="<%=number %>">
								<td >
									<span>${informa[i.index]}</span>
								</td>
							<c:forEach items="${objs}" var="obj" >
							<td >
								<span>${obj[2]}</span>
							</td>
							</c:forEach>
							<td >
									<span>${sumInforma[i.index]}</span>
								</td>
						</tr>
						<% number++;%>
					</c:forEach>
					<tr>
						<td>
						<span>统计</span>
						</td>
						<c:forEach var="Informa2" items="${sumInforma2}">
							<td>
							<span>${Informa2}</span>
							</td>
						</c:forEach>
						<td>
						<span>${sumAll}</span>
						</td>
					</tr>
				</tbody>
			</table>
			</form>
		</div>
	</body>
</html>