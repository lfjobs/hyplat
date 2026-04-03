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
		<title>营销生产管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<style type="text/css">
		body table tr td {
  	 	height: 150px;
   	 	text-align: center;
   	 	width: 50px;
		}
		</style>
	</head>

	<body>
		<div style="margin-left: 60px;margin-top: 80px;">
		<table width="1000" border="0" >
			<tr>
				<td>
					<div class="na_back_img_ks"></div><div class="center_a">
						<strong>公司营销管理 </strong>
					</div>
				</td>
				<td>
					<div class="na_back_img_jt_hx"></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cstaff/ea_getFileMarketSurvey.jspa?cstaff.status=99&produce=Company'"></div>
					<div class="center_a"><strong>市场调查管理</strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/productdesign/ea_getFilePackageProduct.jspa?produce=Company'"></div>
					<div class="center_a"><strong>产品设计推广管理</strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/companytrack/ea_getFileCustomerAdvisory.jspa?produce=Company'"></div>
					<div class="center_a"><strong>客户咨询管理 </strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/transactionservice/ea_getFileTurnOverProducts.jspa?produce=Company'"></div>
					<div class="center_a"><strong>成交产品服务</strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/clientTracking/ea_getFileTrackService.jspa?produce=Company'"></div><div class="center_a">
						<strong>跟踪产品客户服务</strong>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						<strong>集团营销管理</strong>
					</div>
				</td>
				<td>
					<div class="na_back_img_jt_hx"></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/cstaff/ea_getFileMarketSurvey.jspa?cstaff.status=99&produce=group'"></div>
					<div class="center_a"><strong>集团市场调查管理 </strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/productdesign/ea_getFilePackageProduct.jspa?produce=group'"></div>
					<div class="center_a"><strong>集团产品设计推广管理 </strong></div>
				</td>
				<td>
					<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/companytrack/ea_getFileCustomerAdvisory.jspa?produce=group'"></div>
					<div class="center_a"><strong>集团客户咨询管理</strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/transactionservice/ea_getFileTurnOverProducts.jspa?produce=group'"></div>
					<div class="center_a"><strong>集团成交产品服务</strong></div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/clientTracking/ea_getFileTrackService.jspa?produce=group'"></div>
					<div class="center_a"><strong>集团跟踪产品客户服务</strong></div>
				</td>
			</tr>
		</table>
		</div>
	</body>
</html>