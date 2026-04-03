<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>市场调查管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
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
             var module = '<%=session.getAttribute("module")%>'; 
             var journalNum = "${param.journalNum}";
             var projectName = "${param.projectName}";
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
							<span id="frametitle">市场调查管理</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 200px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">市场调查管理</span>
								<ul>
									<li>
										<span class="folder">
											<a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=99" target="admin1">社会人力管理</a></span>
										<ul id="zzgldd">
											<li>
												<span class="folder" id="dydc">地域调查</span>
												<ul >
													<li>
														<span class="folder" id="dbr">东北地区</span>
														<ul>
														<s:iterator value="northeast" var="neast">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${neast.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${neast.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" var="hbr">华北地区</span>
														<ul>
														<s:iterator value="northChina" var="north">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${north.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${north.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" var="hdr">华东地区</span>
														<ul>
														<s:iterator value="eastChina" var="east">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${east.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${east.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" var="hnr">华南地区</span>
														<ul>
														<s:iterator value="southernChina" var="southern">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${southern.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${southern.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" var="xnr">西南地区</span>
														<ul>
														<s:iterator value="southwest" var="swest">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${swest.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${swest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xbr">西北地区</span>
														<ul>
														<s:iterator value="northwest" var="nwest">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${nwest.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${nwest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
												</ul>
											</li>
											<li>
												<span class="folder" id="dydc">往来关系</span>
												<ul >
													<s:iterator value="connectionlist" var="connection" >
														<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?parameter=${connection.codeValue}&showType=connection&cstaff.status=99" target="admin1"><span class="file">${connection.codeValue}</span></a></li>
													</s:iterator>
												</ul>
											</li>
										</ul>
										<ul>
									</li>
									</ul>
									<li>
										<span
											class="folder"><a href="<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa" target="admin1">社会往来单位</a></span>
										<ul id="shwldw">
											<li>
												<span class="folder" id="dydc">地域调查</span>
												<ul>
													<li>
														<span class="folder" id="db">东北地区</span>
														<ul>
														<s:iterator value="northeast" var="neast">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${neast.districtName}&showType=address" target="admin1"><span class="file">${neast.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hb">华北地区</span>
														<ul>
														<s:iterator value="northChina" var="north">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${north.districtName}&showType=address" target="admin1"><span class="file">${north.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hd">华东地区</span>
														<ul>
														<s:iterator value="eastChina" var="east">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${east.districtName}&showType=address" target="admin1"><span class="file">${east.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hn">华南地区</span>
														<ul>
														<s:iterator value="southernChina" var="southern">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${southern.districtName}&showType=address" target="admin1"><span class="file">${southern.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xn">西南地区</span>
														<ul>
														<s:iterator value="southwest" var="swest">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${swest.districtName}&showType=address" target="admin1"><span class="file">${swest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xb">西北地区</span>
														<ul>
														<s:iterator value="northwest" var="nwest">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${nwest.districtName}&showType=address" target="admin1"><span class="file">${nwest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
												</ul>
											</li>
											<li>
												<span
													class="folder" id="hylb">行业类别调查</span>
												<ul>
													<s:iterator value="typelist" var="ss">
														<li>
															<a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${ss.codeValue}&showType=type" target="admin1"><span class="file">${ss.codeValue}</span></a>
														</li>
													</s:iterator>
												</ul> 
											</li>
											
											<li>
												<span
													class="folder" id="hylb">往来关系</span>
												<ul>
													<s:iterator value="comconnectlist" var="comconnect">
														<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?parameter=${comconnect.codeValue}&showType=connection" target="admin1"><span class="file">${comconnect.codeValue}</span></a></li>
													</s:iterator>
												</ul> 
											</li>
										</ul>
									</li>
									<li>
										<a href="<%=basePath%>/ea/marketsurvey/ea_getListMarketSurvey.jspa" target="admin1"><span
											class="file">市场调查</span> </a>
									</li>
									<li>
										<span class="folder">客户调查</span>
										<ul>
											<li>
												<a href="<%=basePath%>ea/marketingCrmCustomer/ea_getCustomerList.jspa?" target="admin1"><span
													class="file" id="grkh">个人客户</span></a>
											</li>
											<li>
												<span
													class="file" id="dwkh">单位客户</span> 
											</li>
										</ul>
									</li>
									<li>
										<span class="folder">供应商调查</span>
										<ul>
											<li>
												<a href="<%=basePath%>/ea/academicadmin/ea_getCompanyListForIncumbent.jspa?" target="admin1"><span
													class="file" id="gr">个人供应商</span></a>
											</li>
											<li>
												<span
													class="file" id="dw">单位供应商</span> 
											</li>
											
										</ul>
									</li>
									<li>
										<span class="folder"><a href="<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa?flag=web" target="admin1">往来单位网站管理</a></span>
									
									</li>
									<!-- 									
									<li>
										<span
											class="folder">竞争对手调查</span> 
									</li>
									<li>
										<span
											class="folder">市场价格调查</span> 
									</li>
									 -->
								</ul>
							</li>
						</ul>
					</td>
					
					<td style="width: 85%;" valign="top">
						<iframe src="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=99" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
					</td>
				</tr>
			</table>
		</div>

	<script type="text/javascript">
	$("#shrl").click(function(){
		
		
		var shrl=$("#shrl").text();
		//alert(shrl);
		
		});
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#navigation").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height()+ "px"}); 
    	$("#navigation").treeview();  
 		$("#zzgldd").treeview({
 			 persist: "location",
 		      collapsed: true,
 		      unique: true
 		}); 
    	$("#shwldw").treeview({
			 persist: "location",
		      collapsed: true,
		      unique: true
		});
     });
    	
</script>
	</body>

</html>
