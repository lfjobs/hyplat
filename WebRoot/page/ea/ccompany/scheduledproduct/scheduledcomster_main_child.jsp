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
<title>成交客户建档</title>
<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css"
	type="text/css"></link>
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>	
<style>
div {
	width: 110px;
}
</style>
</head>
<body>
	<div align="left">
		<table>
			<tr>
				<td><div>
						<div class="na_back_img_ks"></div>
						<div class="center_a">
							<strong>成交客户建档</strong>
						</div>
					</div></td>
				<td><div class="na_back_img_jt_hx"></div></td>
				<%-- 2013-09-04 注释刘总要求 <td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Reservation'"></div>
					<div class="center_a">预定客户建档</div></td>
				<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal'"></div>
					<div class="center_a">成交客户建档</div></td>
			    --%><td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train'"></div>
					<div class="center_a">学员档案管理</div></td>
					
				 <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getSArchiveSheetList.jspa'"></div>
					<div class="center_a">学员档案出库单据</div></td>
			</tr>
			
		</table>
	</div>
</body>
</html>