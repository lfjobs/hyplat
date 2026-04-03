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
<title>考试管理（理论）</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>

 <%--
  param:  theModule  describe：区别模块   parameters：培训计时 00,分车管理 01,预约培训管理02,培训管理03,接送管理 04,约考管理05,考试成绩管理06,合格管理07
  --%>
</head>
<body>
	<table>
		<tr>
			<td rowspan="3">
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong> 考试管理(科一)</strong>
				</div></td>
			<td><div>
					<div class="na_back_img_jt_xs"></div>
				</div></td>
			<td><div>
					<div class="na_back_img_ks"></div>
					<strong>科一学校考试管理</strong>
				</div></td>
			<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一基本信息录入</span>
			</div></td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一约考管理&theModule=05&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一约考管理</span>
				</div></td>
			<td>
				<div class="na_back_img"></div>
				<div class="center_a">
					<span>科一考试内容管理</span>
				</div></td>
			<td>
				<div class="na_back_img" 
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一考试成绩&theModule=06&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一考试成绩管理</span>
				</div></td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一合格管理&theModule=07&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一合格管理</span>
				</div></td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=01&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一合格率管理</span>
				</div>
			</td>	
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/driving/ea_getStatisticsList.jspa?docstatus=01&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一考试信息统计</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一档案管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一档案管理</span>
				</div></td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_hx"></div></td>
			<td>
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>科一车管管理</strong>
				</div>
			</td>
			<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一基本信息录入</span>
			</div></td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一约考管理&theModule=05&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一约考管理</span>
				</div></td>
			<td>
				<div class="na_back_img"></div>
				<div class="center_a">
					<span>科一考试内容管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一考试成绩&theModule=06&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一考试成绩管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一合格管理&theModule=07&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一合格管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=01&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一合格率管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/driving/ea_getStatisticsList.jspa?docstatus=01&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一考试信息统计</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一档案管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一档案管理</span>
				</div>
			</td>
		</tr>
		<tr>
			<td><div>
					<div class="na_back_img_jt_xx"></div>
				</div></td>
			<td>
				<div class="na_back_img_ks"></div>
				<div class="center_a">
					<strong>科一运管管理</strong>
				</div>
			</td>
			<td width="110" align="center">
										<div class="na_back_img"
											onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一基本信息录入&companyGroupLogo=${param.companyGroupLogo}'"></div>
										<div class="center_a">
											<span style="font-weight: normal; font-size: 12px;">科一基本信息录入</span>
			</div></td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一约考管理&theModule=05&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一约考管理</span>
				</div></td>
			<td>
				<div class="na_back_img"></div>
				<div class="center_a">
					<span>科一考试内容管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一考试成绩&theModule=06&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一考试成绩管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一合格管理&theModule=07&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一合格管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=01&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一合格率管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/driving/ea_getStatisticsList.jspa?docstatus=01&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一考试信息统计</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一档案管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科一档案管理</span>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>
