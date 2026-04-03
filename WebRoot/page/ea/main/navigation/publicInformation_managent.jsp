<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>公共信息管理</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>

<script type="text/javascript">
   
             var basePath='<%=basePath%>';  
        </script>
		<style type="text/css">
.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
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
							<span id="frametitle">公共信息管理</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 180px;"
							class="filetree">
										<li>
								<span class="folder" id="tit">公共信息管理</span>
										<ul>
											<li>
												<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=AnnNoti&d=0.9556356496029352" target="admin1"><span
													class="file">公告通知管理</span> </a>
											</li>
											<li>
												<a  href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=news&d=0.8990270135857279" target="admin1"><span
													class="file">新闻管理</span> </a>
											</li>
											<li>
												<a   target="admin1"><span
													class="file">投票管理</span> </a>
											</li>
											
											<li>
												<a href="<%=basePath%>ea/telephone/ea_getaTelephoneList.jspa?" target="admin1"><span
													class="file" id="unex">公共电话薄</span></a>
													
											</li>
											<li>
												<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=bulletin&d=0.7985786618717468" target="admin1"><span
													class="file">简报管理</span> </a>
											</li>
											
											<li>
												<a   target="admin1"><span
													class="file" id="unex">数据提醒管理</span></a>
											</li>
										<li>
												<a href="<%=basePath%>ea/schedule/ea_getScheduleList.jspa?" target="admin1"><span
													class="file" id="unex">公共日程管理</span></a>
											</li><li>
												<a   target="admin1"><span
													class="file" id="unex">公共日志管理</span></a>
											</li>
							
						</ul>
							</li>
						</ul>
									
					</td>
					<td style="width: 85%;" valign="top">
						<iframe  src="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=AnnNoti&d=0.9556356496029352" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
     $(document).ready(function() {  
   		$("#navigation").treeview({
   		persist: "location",
   		collapsed: true,
   		unique:true
   		});   
   		var frametitle = "";
   		setInterval(function() {
   					var FormObj = document.getElementById("mainframe").contentWindow;
   					var total = FormObj.document.getElementById("totals");
   					if (total != null) {
   						total.innerText = Number(result1) + Number(result2)
   								+ Number(result3) + Number(result4)
   								+ Number(result5) + Number(result6);

   					}

   				}, 100);
   	   
    	});
    	
</script>
	

	</body>
</html>