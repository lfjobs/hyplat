<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>资金申请树</title>
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
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script src="<%=basePath%>/js/ea/finance/Navigation/finace_na.js"></script>

		<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript"
			src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
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
		<%-- <div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
					<td id="qh_sw" style="width: 15%" valign="top">
						<div class="qh_gg_nav">
							&nbsp;
							<span id="frametitle">资金申请</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 200px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">资金申请管理</span>
								<ul>
									<li>
										<a href="#" onclick="tonclick('zjszdz')">
										<span class="file" id="tit">资金审转对账管理</span></a>
									</li>
									<li>
										<a href="<%=basePath%>ea/cashapplybills/ea_toCashList.jspa?weibokuan=weibokuan&level=organization" target="mainframe" >
										<span class="file" id="tit">未拨现金申请</span></a>
									</li>
									<li>
										<a href="<%=basePath%>ea/cashapplybills/ea_toCashList.jspa?other=other&level=organization&cother=all" target="mainframe" onclick="tonclick('ybxjsq')">
										<span class="file" id="tit">已拨现金申请</span></a>
									</li>
									<li>
										<a href="#" onclick="tonclick('fycgmx')">
										<span class="file" id="tit">费用采购明细账</span></a>
									</li>
								</ul>
							</li>
						</ul>
					</td>
					<td style="width: 85%;" valign="top">
						<iframe id="mainframe"
							frameborder="0" style="width: 100%;"></iframe>
					</td>
				</tr>
			</table>
		</div> --%>
		<br />
	<table width="90%" cellspacing="0" cellpadding="5" align="center">
		<tr>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/cashapplybills/ea_toCashList.jspa?other=other&level=organization&cother=all'"></div>
				<div class="center_a">资金审转对账管理</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/cashapplybills/ea_toCashList.jspa?weibokuan=weibokuan&level=organization'"></div>
				<div class="center_a">未拨现金申请</div></td>
			<td><div class="na_back_img"
					onclick="document.location.href='<%=basePath%>ea/cashapplybills/ea_toCashList.jspa?other=other&level=organization&cother=all'"></div>
				<div class="center_a">已拨现金申请</div></td>
		</tr>
	</table>
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
     });
</script>
	</body>


</html>
