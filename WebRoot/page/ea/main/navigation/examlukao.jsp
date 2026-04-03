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
<title>考试管理（科四）</title>
<link rel="stylesheet" href="<%=basePath%>/css/navigation_a.css"
	type="text/css"></link>
	
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<table width="200" border="0">
		<tr>
			<td rowspan="3">
				<div class="na_back_img_ks" ></div> 
      			<div class="center_a"><strong>考试管理(科四)</strong></div>
			</td>
			<td>
			<div class="na_back_img_jt_xs"></div>
			</td>
			<td>
				<div class="na_back_img_ks" ></div> 
      			<div class="center_a"><strong>科四学校考试管理</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四约考管理&theModule=05&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四约考管理</span></div>
			</td>
			<td>
				<div class="na_back_img" ></div>
      			<div class="center_a"><span>科四考试内容管理</span></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四考试成绩&theModule=06&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四考试成绩管理</span></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四合格管理&theModule=07&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四合格管理</span></div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=04&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科四合格率管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/driving/ea_getStatisticsList.jspa?docstatus=04&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科四考试信息统计</span>
				</div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四档案管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四档案管理</span></div>
			</td>
		</tr>
		<tr>
			<td>
			<div class="na_back_img_jt_hx"></div>
			</td>
			<td>
				<div class="na_back_img_ks" ></div> 
      			<div class="center_a"><strong>科四车管管理</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四约考管理&theModule=05&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四约考管理</span></div>
			</td>

			<td>
				<div class="na_back_img" ></div>
      			<div class="center_a"><span>科四考试内容管理</span></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四考试成绩&theModule=06&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四考试成绩管理</span></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四合格管理&theModule=07&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四合格管理</span></div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=04&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科四合格率管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/driving/ea_getStatisticsList.jspa?docstatus=04&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科四考试信息统计</span>
				</div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四档案管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四档案管理</span></div>
			</td>
		</tr>
		<tr>
			<td>
			<div class="na_back_img_jt_xx"></div>
			</td>
			<td>
				<div class="na_back_img_ks" ></div> 
      			<div class="center_a"><strong>科四运管管理</strong></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四约考管理&theModule=05&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四约考管理</span></div>
			</td>
			<td>
				<div class="na_back_img" ></div>
      			<div class="center_a"><span>科四考试内容管理</span></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四考试成绩&theModule=06&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四考试成绩管理</span></div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四合格管理&theModule=07&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四合格管理</span></div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/ea/main/driving/driving_list_fenxi_search.jsp?docstatus=04&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科四合格率管理</span>
				</div>
			</td>
			<td>
				<div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/driving/ea_getStatisticsList.jspa?docstatus=04&other=other&companyGroupLogo=${param.companyGroupLogo}'"></div>
				<div class="center_a">
					<span>科四考试信息统计</span>
				</div>
			</td>
			<td>
				<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四档案管理&companyGroupLogo=${param.companyGroupLogo}'"></div>
      			<div class="center_a"><span>科四档案管理</span></div>
			</td>
		</tr>
	</table>
</body>
</html>
