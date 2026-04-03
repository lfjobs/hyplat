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
<title>车辆CNG气瓶检测办公导航</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>

</head>

<body>
	<table>
	<tr>
			<td align="center">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>产品登记</strong>
				</div>
			</td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<tr>
                             <td>
                              <div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/productregister/ea_getListProductregister.jspa?'"></div>
                              <div class="center_a">气瓶登记</div>
                            </td>
                          </tr>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="center">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>生产项目</strong>
				</div>
			</td>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td><table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<tr>
                             <td>
                              <div class="na_back_img" onclick="document.location.href='<%=basePath%>page/ea/main/navigation/synthesiscertificatemanager.jsp'"></div>
                              <div class="center_a">综合办证管理</div>
                            </td>
                            <td>
							<div class="na_back_img"
								onclick="document.location.href='<%=basePath%>/"></div>
							<div class="center_a">归档管理</div>
						</td>
                          </tr>
						
						
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
