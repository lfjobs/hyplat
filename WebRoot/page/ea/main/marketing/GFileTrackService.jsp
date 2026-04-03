<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>集团跟踪产品客户服务</title>
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
							<span id="frametitle">集团跟踪产品客户服务</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 180px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">集团跟踪产品客户服务</span>
								<ul>
									<li>
										<a href="<%=basePath%>ea/clientTracking/ea_getClientTrackingList.jspa" target="admin1"><span
											class="file">集团跟踪服务</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>ea/clientPblm/ea_getClientPblmList.jspa" target="admin1"><span
											class="file">集团问题解决</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>ea/clientIncrement/ea_getClientIncrementList.jspa" target="admin1"><span
											class="file">集团增值服务</span> </a>
									</li>
									<li>
										<span
											class="file">集团成交增值</span>
									</li>
									<li>
										<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=complaint" target="admin1"><span
											class="folder">集团投诉处理</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=InterDis" target="admin1"><span
											class="folder">集团内部纠纷</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=ExterDis" target="admin1"><span
											class="folder">集团外部纠纷</span> </a>
									</li>
									<li>
										<a href="<%=basePath%>ea/extralflow/ea_showExtralDocModule.jspa" target="admin1"><span
											class="folder">集团网站投诉</span> </a>
									</li>
								</ul>
							</li>
						</ul>
					</td>
					<td style="width: 85%;" valign="top">
						<iframe src="" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
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
     });
</script>
	</body>

</html>
