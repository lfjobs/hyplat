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
		<title>折扣设定</title>
		<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css" type="text/css"/>	
		<style type="text/css">
		
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

</style>
<style type="text/css">
<!--

.table03 {
	font-size: 14px;
	line-height:17px;
	font-weight:bold;
	margin:10px 0 0 10px；
}

.table03 th {
	font-size: 14px;
	line-height:17px;
	font-weight:bold;
}
.table03 td {
	font-size: 14px;
	line-height:17px;
	font-weight:bold;
}


body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
	</head>
	<body>
		<div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
								<td>
									<table height="90" border="0" align="left" cellpadding="0"
										cellspacing="0" style="margin-top: 5px">
										<tr>
											<td rowspan="2" align="center" >
												<div class="na_back_img_ks"></div><div class="center_a"><strong>

												折扣设定</strong></div>
											</td>
											<td align="center">
												<div class="na_back_img_jt_hx"></div>
											</td>
											<td rowspan="2" align="center">
												<table border="0" cellspacing="0" cellpadding="0"
													height="132px">
													<tr>
														<td><div class="na_back_img"
															onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/salary_manage_b.jsp?'">
															</div>
							<div class="center_a">
								<span>其他折扣</span></div>
													  </td>
														<td><div class="na_back_img"
															onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/salary_manage_c.jsp?'">
															</div>
							<div class="center_a">
								<span>考勤折扣设定</span></div>
														
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
			</table>
		</div>
	</body>
</html>
