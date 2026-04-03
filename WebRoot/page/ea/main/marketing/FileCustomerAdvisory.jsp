<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客户咨询管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/flexigrid_blue.css"/>
		<script type="text/javascript" src="<%=basePath %>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
		<script type="text/javascript">  
             var basePath='<%=basePath%>';  
        </script>
		<style type="text/css">
#doctree {
	position: absoute;
	padding-top: 0cm;
	margin-top: 0px;
	width: 100%;
	background-color: #FFFFFF;
	float: left;
}

#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}

.numcss {
	color: red;
}
</style>
	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
					<td id="qh_sw" style="width: 15%" valign="top">
						<div class="qh_gg_nav">
							&nbsp;
							<span id="frametitle">客户咨询管理</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 180px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">客户咨询管理</span>
								<ul>
									<li>
										<a href="<%=basePath%>/ea/companytrack/ea_getCompanyList.jspa" target="admin1"><span
											class="file">单位客户咨询管理</span> </a>
									</li>
									
									<li>
										<span class="folder">个人客户咨询管理</span>
										<ul>
											<li>
												<a href="<%=basePath%>/ea/clinch/ea_getListContactUserConsult.jspa?module_Identifier=customer_Consultation" target="admin1"><span
													class="file" id="unex">个人客户基本信息</span></a>
											</li>
											<li>
												<a href="<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000030&type=101" target="admin1"><span
													class="file">潜在客户需求报表</span> </a>
											</li>
											<li>
												<a href="<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000030&type=102" target="admin1"><span
													class="file">客户来源管理报表</span> </a>
											</li>
											<li>
												<a href="<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000030&type=103" target="admin1"><span
													class="file">客户产品兴趣报表</span> </a>
											</li>
											
										</ul>
									</li>
									<li>
										<a href="<%=basePath%>/ea/custdata/ea_getCustomerDataList.jspa" target="admin1"><span
											class="folder">客户注册单位</span> </a>
										<ul>
											<li>

												<a href="<%=basePath%>/ea/custdata/ea_getCustomerDataList.jspa" target="admin1"><span
														class="folder">个人商户</span> </a>

												<ul>
													<li>
														<a href="<%=basePath%>/ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type=2401&applyResult.applyment_state=AUDITING" target="admin1"><span
																class="file" id="unex">待审核商户</span></a>
													</li>
													<li>
														<a href="<%=basePath%>/ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type=2401&applyResult.applyment_state=FINISH" target="admin1"><span
																class="file">已审核商户</span> </a>
													</li>
													<li>
														<a href="<%=basePath%>/ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type=2401&applyResult.applyment_state=" target="admin1"><span
																class="file">个人商户汇总</span> </a>
													</li>

												</ul>
											</li>

											<li>

												<a href="<%=basePath%>/ea/custdata/ea_getCustomerDataList.jspa" target="admin1"><span
														class="folder">企业商户</span> </a>

												<ul>
													<li>
														<a href="<%=basePath%>/ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type=2&applyResult.applyment_state=AUDITING" target="admin1"><span
																class="file" id="unex">待审核商户</span></a>
													</li>
													<li>
														<a href="<%=basePath%>/ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type=2&applyResult.applyment_state=FINISH" target="admin1"><span
																class="file">已审核商户</span> </a>
													</li>
													<li>
														<a href="<%=basePath%>/ea/merch/ea_getMerchanetsRegistList.jspa?applyParam.organization_type=2&applyResult.applyment_state" target="admin1"><span
																class="file">企业商户汇总</span> </a>
													</li>


												</ul>
											</li>
											<li>
												<a href="<%=basePath%>/ea/customermanage/ea_getPotentialListSummary.jspa?status1=scode201306069vd8t6qypi0000000030&type=102" target="admin1"><span
														class="file">全部商户</span> </a>
											</li>
										</ul>
									</li>
									<li>
										<a href="<%=basePath%>/ea/comregist/ea_getCompanyRegistList.jspa" target="admin1"><span
											class="file">内部企业注册单位</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>/ea/companytrack/ea_getCustomerAskList.jspa" target="admin1"><span
											class="file">客户咨询内容</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>/ea/consult/ea_getConsultList.jspa" target="admin1"><span
												class="file">项目咨询</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>/ea/companytrack/ea_getCustomerAskList.jspa" target="admin1"><span
												class="file">项目咨询汇总</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>/ea/giftcard/ea_getGidtCardPage.jspa" target="admin1"><span
												class="file">购物卡管理</span> </a>
									</li>
							</li>
							<li><span class="folder">生产管理</span>
							            <ul>
							            <li>
							              <a href="<%=basePath%>page/ea/main/navigation/product_procedure.jsp?fiveClear=5"><span class="file">营销生产</span></a>
							            </li>
						</ul>
					</td>
					<td style="width: 85%;" valign="top">
						<iframe src="<%=basePath%>/ea/companytrack/ea_getCompanyList.jspa" id="mainframe1" style="margin:0px;width:100%;height:auto;" name="admin1" scrolling="no" frameBorder="0"></iframe>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                 $("#mainframe1").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
		$("#navigation").height($(window).height()- 30);
		$("#mainframe1").css({"height" : $(window).height() - 5 + "px"}); 
		$("#navigation").treeview();   
     });
    function e(h){
    	$("body").last().append(h);
    }	
</script>
	</body>

</html>
