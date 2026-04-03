<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>售前、售中、售后服务管理</title>
<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css"
	type="text/css"></link>
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>	
<style>

body table tr td {
    height: 50px;
    text-align: center;
    width: 220px;
}

</style>
</head>
<body>
	<div align="left" style="margin-left: 70px;margin-top: 220px;">
		<table>
			<tr>
				<td><div class="na_back_img"
						onclick="document.location.href='<%=basePath%>/ea/cstaff/ea_getFileMarketSurvey.jspa?cstaff.status=99'"></div>
					<div class="center_a"><strong>市场调查管理</strong></div></td>
				<td><div class="na_back_img"
						onclick="document.location.href='<%=basePath%>/ea/productdesign/ea_getFilePackageProduct.jspa?'"></div>
					<div class="center_a"><strong>产品发布设计</strong></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath%>/ea/companytrack/ea_getFileCustomerAdvisory.jspa?'"></div>
					<div class="center_a"><strong>客户咨询管理</strong></div></td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath%>/ea/transactionservice/ea_getFileTurnOverProducts.jspa?'"></div>
					<div class="center_a"><strong>成交产品服务</strong></div></td>
				<td>
					<div class="na_back_img" 
						onclick="document.location.href='<%=basePath%>ea/clientTracking/ea_getFileTrackService.jspa?'"></div>
					<div class="center_a"><strong>跟踪产品客户服务</strong></div></td>
			</tr>

		</table>
	</div>
</body>
</html>