<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>集团调查管理菜单页</title>
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
							<span id="frametitle">集团市场调查管理</span>

						</div>
						<!--左边的树 -->											
						<ul id="navigation" style="width: 180px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">集团市场调查管理</span>
								<ul  id="zzgldd">
									<li>
										<span
											class="folder"><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=99" target="admin1">集团社会人力管理</a></span>
										<ul>
											<li>
												<span class="folder" id="dydc">地域调查</span>
												<ul>
													<li>
														<span class="folder" id="dbr">东北地区</span>
														<ul>
														<s:iterator value="northeast" id="neast">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${neast.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${neast.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hbr">华北地区</span>
														<ul>
														<s:iterator value="northChina" id="north">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${north.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${north.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hdr">华东地区</span>
														<ul>
														<s:iterator value="eastChina" id="east">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${east.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${east.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hnr">华南地区</span>
														<ul>
														<s:iterator value="southernChina" id="southern">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${southern.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${southern.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xnr">西南地区</span>
														<ul>
														<s:iterator value="southwest" id="swest">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${swest.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${swest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xbr">西北地区</span>
														<ul>
														<s:iterator value="northwest" id="nwest">
															<li><a href="<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa?search=${nwest.districtName}&showType=address&cstaff.status=99" target="admin1"><span class="file">${nwest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
												</ul>
											</li>
										</ul>	
									</li>
									<li>
										<span
											class="folder"><a href="<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa" target="admin1">集团社会往来单位</a></span>
										<ul>
											<li>
												<span class="folder" id="dydc">地域调查</span>
												<ul>
													<li>
														<span class="folder" id="db">东北地区</span>
														<ul>
														<s:iterator value="northeast" id="neast">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?search=${neast.districtName}&showType=address" target="admin1"><span class="file">${neast.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hb">华北地区</span>
														<ul>
														<s:iterator value="northChina" id="north">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?search=${north.districtName}&showType=address" target="admin1"><span class="file">${north.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hd">华东地区</span>
														<ul>
														<s:iterator value="eastChina" id="east">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?search=${east.districtName}&showType=address" target="admin1"><span class="file">${east.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="hn">华南地区</span>
														<ul>
														<s:iterator value="southernChina" id="southern">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?search=${southern.districtName}&showType=address" target="admin1"><span class="file">${southern.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xn">西南地区</span>
														<ul>
														<s:iterator value="southwest" id="swest">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?search=${swest.districtName}&showType=address" target="admin1"><span class="file">${swest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
													<li>
														<span class="folder" id="xb">西北地区</span>
														<ul>
														<s:iterator value="northwest" id="nwest">
															<li><a href="<%=basePath%>ea/contactcompany/ea_toSearch.jspa?search=${nwest.districtName}&showType=address" target="admin1"><span class="file">${nwest.districtName}</span></a></li>
														</s:iterator>
														</ul>
													</li>
												</ul>
											</li>
											
										</ul>
									</li>
									<li>
										<a href="<%=basePath%>/ea/marketsurvey/ea_getListMarketSurvey.jspa" target="admin1"><span
											class="file">集团市场调查</span> </a>
									</li>
									<li>
										<span
											class="folder">集团价格调查</span> 
									</li>
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
     });
</script>
	</body>

</html>
