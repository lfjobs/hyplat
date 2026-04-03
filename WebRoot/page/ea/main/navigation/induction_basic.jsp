<%@ page language="java" pageEncoding="UTF-8"%><%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>入职管理</title>
			<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
			<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
	</head>
	<body >
<div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
								<td>
									<table height="90" border="0" align="left" cellpadding="0"
										cellspacing="0" class="table03" style="margin-top: 5px">
										<tr>
											<td width="114" rowspan="2" align="center" class="colors">
												<div class="na_back_img_ks"></div>
                      							<div class="center_a"><strong>人事管理</strong></div>
											</td>
											<td width="55"  align="center">
												<div class="na_back_img_jt_hx"></div>
											</td>
											<td rowspan="2" align="center">
												<table border="0" cellspacing="0" cellpadding="0"
													height="132px">
													<tr>
														<td width="120" align="center" class="colors">
															<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/departmentpost/ea_getDepartmentPostList.jspa'"></div>
                      										<div class="center_a"><span>职责汇总</span></div>
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
