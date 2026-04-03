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
<title>股东会生产管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	<style type="text/css">
		.colors {
	border-bottom: 1px dashed #FF0000;
}
	td{
		height: 30px;
		
		}
	</style>
</head>
<body>
	<div>
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td><table>
				
				
				      	<tr>
							<td><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>生产合同管理</strong>
											</div></td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">生产合同流转</div>
													</td>
													<td width="110"><div class="na_back_img"></div>
														<div class="center_a">生产合同查询</div>
													</td>
													
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr><td class="colors"></td></tr>
						<tr>
							<td><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong> 公司股东会管理</strong>
											</div></td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">公司股东会(人事)管理</div>
													</td>
													<td width="110"><div class="na_back_img"></div>
														<div class="center_a">公司股东会(办公室)管理</div>
													</td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">公司股东会(财务)管理</div>
													</td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">公司股东会(生产)管理</div></td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">公司股东会(营销)管理</div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr><td class="colors"></td></tr>
						<tr>
							<td><table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td >
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>集团股东会管理</strong>
											</div></td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table border="0" cellspacing="0" cellpadding="0">
												<tr>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">集团股东会{人事}管理</div>
													</td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">集团股东会(办公室)管理</div>
													</td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">集团股东会(财务)管理</div>
													</td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">集团股东会(生产)管理</div>
													</td>
													<td width="110">
														<div class="na_back_img"></div>
														<div class="center_a">集团股东会(营销)管理</div>
													</td>
												</tr>
											</table>
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