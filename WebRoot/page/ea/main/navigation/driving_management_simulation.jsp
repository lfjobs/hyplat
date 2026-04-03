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
<title>模拟科</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>

<body>
	<div>
		<table >
			<tr>
				<td>
					<table>
						<tr>

							<td  rowspan="2" >
								<div class="na_back_img_ks"></div>
								<div class="center_a">
									<strong>模拟科</strong>
								</div>
							</td>
							<td ><div class="na_back_img_jt_hx"></div>
							</td>
							<td >
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="110"  >
											<div class="na_back_img"></div>
											<div class="center_a">
												<span >教研管理</span>
											</div></td>
										<td width="110"  >
											<div class="na_back_img"></div>
											<div class="center_a">
												<span >模拟管理</span>
											</div></td>
										<td width="110"  >
											<div class="na_back_img"></div>
											<div class="center_a">
												<span >综合办证管理</span>
											</div></td>
										<td width="110"  >
											<div class="na_back_img"></div>
											<div class="center_a">
												<span >培训管理</span>
											</div></td>
										<td width="110"  >
											<div class="na_back_img"></div>
											<div class="center_a">
												<span >考试管理</span>
											</div></td>
									</tr>
								</table></td>
						</tr>
					</table></td>
			</tr>
		</table>
	</div>
</body>
</html>
