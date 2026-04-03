<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />         
        <title>公司合伙人管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
     
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
     <script src="<%=basePath%>js/ea/human/office/SersonnelSystem/complist.js"></script>
        <script>
             var basePath = "<%=basePath%>";
             var ppageNumber = '${pageNumber}';
             var pageSize = '${pageSize}';
             var weixinCompanyId='${weixinCompanyId}';
             var inforType='${inforType}';
             var staffName='${staffName}';
             var custype='${custype}';
             var status='${status}';
             var token=0;
        </script>
	</head>
<body>
	<div class="main_main">
		<table class="address">
			<thead>
				<tr class="tablewith">

					<th width="40" align="center">请选择</th>
					<th width="50" align="center">编号</th>

					<th width="100" align="center">登陆账号</th>


					<th width="180" align="center">姓名</th>
					<th width="100" align="center">身份证号码</th>

					<th width="100" align="center">电话号码</th>

					<th width="100" align="center">购买注册日期</th>

					<th width="100" align="center">来源</th>
					<th width="180" align="center">会员名片二维码</th>
			</thead>


			<%    int number = 1; %>

			<c:forEach items="${pageForm.list}" var="a">
				<tr id="${a[0]}">
					<td><input type="radio" name="a" class="JQuerypersonvalue"
						value="${a[0]}" /></td>
					<td><span><%=number%></span></td>


					<td><span id="author">${a[1]}</span></td>
					<td><span id="logoNote">${a[2]}</span></td>
					<td><span id="logoNote"> ${a[3]} </span></td>
					<td><span id="logoNote"> ${a[4]} </span></td>

					<td><span id="logoNote"> ${a[5]} </span></td>

					<td><span id="logoNote"> ${a[6]} </span></td>
					<td><span id="jcode"><a onclick="downLoadCode('${a[0]}','',this)"  style="cursor:pointer;">下载</a></td>

				</tr>
				<%  number++; %>
			</c:forEach>

		</table>

		<form style="display: none;" name="addressForm" id="addressForm"
			method="post"
			action="<%=basePath%>ea/activity/ea_findhehuochuangye.jspa">
			<s:token></s:token>
			<input id="formtest" type="test" name="weixinCompanyId"
				style="display:none" />
			<input id="custype" type="hidden" name="custype" value="${custype }" />
			<input id="status" type="hidden" name="status" value="${status }" />
			<input type="submit" name="submit" style="display:none" />
		</form>
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/activity/ea_findhehuochuangye.jspa?pageForm.pageNumber=${pageForm.pageNumber}&pageNumber=${pageNumber}&status=${status }&custype=${custype }">
			</c:param>
		</c:import>
		<s:token></s:token>
	</div>


	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>