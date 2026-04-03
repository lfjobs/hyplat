
<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>公司信息管理</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<td>
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>公司信息管理</strong>
							</div>
						</td>
						<td><div class="na_back_img_jt_hx"></div></td>

						<td>
							<table>
								<tr>
									<td width="110">
										<div class="na_back_img"
											onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/ea/ccompany/ea_toCompanyBonding.jspa'"></div>
										<div class="center_a">
											<span>绑定上级</span>
										</div>
									</td>
									<td width="110">
										<div class="na_back_img"
											onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/ea/ccompany/ea_toEditCompanyDetail.jspa'"></div>
										<div class="center_a">
											<span>级别信息</span>
										</div>
									</td>
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
