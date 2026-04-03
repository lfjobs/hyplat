  <%@ page import="java.net.URLEncoder" %>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司信息管理</title>
<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size:12px;
}
a:link,a:visited,a:active{
	text-decoration:none;
}
a:hover{
	text-decoration:underline;
}
a {
	color: #333;
	text-decoration:none;
}
a:active{color: #002f76;
font-weight:bold;}
-->
</style>
</head>
<body >

<div class="header">
			<div class="padder">
				<div class="nav">
					<div class="navLaftBg">
						<div class="navRightBg">
							<div class="mainNav">
								<a href="#" class="actived"><span>公司信息管理</span> </a>
							</div>
						</div>
					</div>
				</div>
				<div class="secondNav" id="secondNav">
					<div id="subNav2">
						<p class="secondNav">
							<a href="#"
                            	onclick="parent.left_tree.document.location.href='<%=basePath%>/ea/ccompany/ea_toCompanyBonding.jspa'">绑定上级</a>
						</p>
						<p class="secondNav">
							<a href="#"
								onclick="parent.left_tree.document.location.href='<%=basePath%>/ea/ccompany/ea_toEditCompanyDetail.jspa'">级别信息</a>
						</p>
					</div>
				</div>
			</div>


    </body>
</html>
