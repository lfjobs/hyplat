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
		<title>人员配备管理</title>
		<!-- 人事生产调用改动  可删 -->
		<!-- 部门调用修改 可删 -->
		<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
		<table>
			<tr>
				<td>
					<table>
						<tr>
							<td><div class="na_back_img_ks"></div><div class="center_a"><strong>
												人员配备管理</strong></div></td>
											   <td><div class="na_back_img_jt_hx"></div></td>						
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<div class="na_back_img"
												onclick="document.location.href='<%=basePath%>/ea/corganization/ea_getCompanyMessage.jspa?corString=orgtree'">
												</div><div class="center_a">微型企业人员配备
												</div>
										</td>
										<td>
											<div class="na_back_img"></div><div class="center_a">小型企业人员配备
															</div></td>
										<td>
											<div class="na_back_img"></div><div class="center_a">中型企业人员配备
															</div></td>
										<td>
											<div class="na_back_img"></div><div class="center_a">大型企业人员配备
															</div></td>
										<td>
											<div class="na_back_img"></div><div class="center_a">集团人员配备
															</div></td>
										
										</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			
			
		</table>
	</body>
</html>
