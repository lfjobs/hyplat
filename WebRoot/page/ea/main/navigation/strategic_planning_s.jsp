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
<title>办公室-企业战略管理处</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/navigation_a.css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<br />
	<table>
		<tr>
			<td>
				<table>
					<tr align="center">
						<td>
						<div><img src="<%=basePath%>images/sytemicon/r10_c1.gif"></img></div>
						<div><strong>企业战略规划管理</strong></div>
						</td>
						<td><div class="na_back_img_jt_hx"></div></td>
						<td><table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=cg&type=company&date=Math.random()'">
										<img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img>
										</div>
									    <div class="center_a">公司规划管理</div>
									</td>
									<td>
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=dg&type=company&date=Math.random()'">
										<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
										</div>
									    <div class="na_back_dh" class="center_a">部门规划管理</div>
									</td>
									<td>
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=pg&type=company&date=Math.random()'">
										<img src="<%=basePath%>images/sytemicon/r11_c7.gif"></img>
										</div>
									    <div class="center_a">个人规划管理</div>
									</td>
									<td>
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>ea/documentsummary/ea_getSummaryDocList.jspa?module=jg&type=company&date=Math.random()'">
										<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
										</div>
									    <div class="center_a">职业规划管理</div>
									</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</td>

		</tr>
		<tr>
			<td>
				<!--项目预算招标管理-->
				<table>
					<tr align="center">
						<td rowspan="2">
							<div><img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img></div>
							<div><strong>项目预算招标管理</strong></div>
						</td>
						<td ><div class="na_back_img_jt_xs"></div></td>		
							<td><table class="d">
									<tr>
										<td >
											<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=00&type=00'">
											<img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img>
											</div>
											<div class="center_a">预算前调查资金管理</div></td>
										<td >
											<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=01&type=00'">
											<img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img>
											</div>
											<div class="center_a">预算资金</div></td>
										<td >
											<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=02&type=00'">
											<img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img>
											</div>
											<div class="center_a">预算比价管理</div></td>
										<td >
											<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=03&type=00'">
											<img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img>
											</div>
											<div class="center_a">预算使用对账</div></td>
										<%--<td >
											<div class="na_back_img"
												onclick="document.location.href='<%=basePath%>/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type=00'"></div>
											<div class="center_a">确定预算</div></td>
										 <td >
											<div class="na_back_img"
												onclick="document.location.href='<%=basePath%>/ea/purchase/ea_getMarketResearchList.jspa?type=04'"></div>
											<div class="center_a">预算市场资金管理</div></td>--%>
										<td >
											<div><img src="<%=basePath%>images/sytemicon/r16_c1.gif"></img></div>
											<div class="center_a">常规标准预算</div></td> 
									</tr>
								</table></td>
					</tr>
					<tr>
						<td >
							<div class="na_back_img_jt_xx"></div></td>
						<td><table  class="d">
								<tr>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=00&type=08'"></div>
										<div class="center_a">招标已申请管理</div></td>
									<%-- <td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=04&type=08'"></div>
										<div class="center_a">招标物品费用录入</div></td> --%>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=02&type=08'"></div>
										<div class="center_a">招标比价管理</div></td>
									<td >
										<div class="na_back_img"></div>
										<div class="center_a">项目物品费用未申请</div></td>
									<td >
										<div class="na_back_img"></div>
										<div class="center_a">项目物品费用已审批</div></td>
									<td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=03&type=08'"></div>
										<div class="center_a">项目已审批未审批</div></td>
									<%-- <td >
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=08'"></div>
										<div class="center_a">产品清单表</div></td> --%>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
