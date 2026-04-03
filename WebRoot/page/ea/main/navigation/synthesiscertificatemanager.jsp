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
<title>综合办证管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
<script type="text/javascript">
			var token = 0;
			var basePath = '<%=basePath%>';
			var notoken = 0;
		</script>
</head>

<body>
	<table>
		<tr>
			<td align="center">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>基本信息</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div>
			</td>
			<td><table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>ea/productregister/ea_getAllListProductregister.jspa'"></div>
							<div class="center_a">记录表信息</div></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td align="center">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>表单打印</strong>
				</div></td>
			<td><div class="na_back_img_jt_hx"></div>
			</td>
			<td><table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/ea/dispitch/ea_getListorderwork.jspa?'"></div>
							<div class="center_a">派工单打印</div></td>
							<td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>ea/productregister/ea_getAllListProductregister.jspa?showtype=recordlsit'"></div>
							<div class="center_a">记录表打印</div></td>
							<td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/ea/productregister/ea_getlistregistrationlist.jspa?showtype=registration'"></div>
							<div class="center_a">使用登记证打印</div></td>
							<td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/ea/productregister/ea_getlistregistrationlist.jspa?showtype=panfeitongzhi'"></div>
							<div class="center_a">判废通知打印</div></td>
							<td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/ea/productregister/ea_getlistregistrationlist.jspa?showtype=metereport'"></div>
							<div class="center_a">评定报告打印</div></td>
							
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
