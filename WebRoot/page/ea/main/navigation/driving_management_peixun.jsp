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
<title>教务管理科——培训管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<table
		style="width: 100%; border: 0; text-align: left; padding: 0; margin: 0"
		class="table03">
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>综合信息查询</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train'"></div>
										
										--%><div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total'"></div>	
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">综合信息中心</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total'"></div>
										
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">综合车管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">综合运管管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">综合培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total'"></div>
										
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=01'"></div>	
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">综合考试归档管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科一管理</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一基本信息</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img" 
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一报车管管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一报车管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一报运管管理</span>
										</div></td>		
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=01&educationalCategories=01&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title=科一&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/cultivateManager.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一考试归档管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科二管理</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=02&view_Identifier=educational_train&module_title=科二基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科二基本信息</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=02&view_Identifier=educational_train&module_title=科二报车管管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科二报车管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科二报运管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=02&educationalCategories=02&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title=科二&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科二培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/examzhuangkao.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科二考试归档管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科三管理</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=03&view_Identifier=educational_train&module_title=科三基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科三基本信息</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=03&view_Identifier=educational_train&module_title=科三报车管管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科三报车管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科三报运管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=03&educationalCategories=03&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title=科三&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科三培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/examchangdi.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科三考试归档管理</span>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="120" align="center" rowspan="2">
							<div class="na_back_img_ks"></div>
							<div class="center_a">
								<strong>科四管理</strong>
							</div>
						</td>
						<td width="55" align="center"><div class="na_back_img_jt_hx"></div>
						</td>
						<td>
							<table border="0" cellspacing="0" cellpadding="0">
								<tr>
								    <td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科四基本信息</span>
										</div></td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四报车管管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科四报车管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"></div>
										<%--<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=01&companyGroupLogo=${param.companyGroupLogo}'"></div>
										--%><div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科四报运管管理</span>
										</div></td>	
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/jiaowutheory_list.jsp?docstatusRequest=04&educationalCategories=04&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title=科四&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科四培训管理</span>
										</div>
									</td>
									<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/examlukao.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科四考试归档管理</span>
										</div>
									</td>
									<%--<td width="110" align="center">
										<div class="na_back_img"
											></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">报表管理</span>
										</div>
									</td>
								--%></tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
