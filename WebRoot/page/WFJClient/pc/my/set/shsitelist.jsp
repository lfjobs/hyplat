<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/selectCompany.css"/>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

	<title>数字</title>

	<style type="text/css">


		@media (min-width: 960px) {
			html{
				margin: 0 20% 0 20%;
			}




		}

	</style>
</head>
<body>
<header>
	<ul class="clearfix">
		<li>
			<a onclick="javascript: window.history.go(-1);return false;" target="_self" >
				<img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
			</a>
		</li>
		<li>
			收货地址
		</li>
	</ul>
</header>



<iframe id="iframe" src="<%=basePath%>ea/perinfor/ea_getReceiptAddressList.jspa?staffId=<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>" width="100%" frameborder="0"></iframe>
</body>
<script type="text/javascript">
	var  basePath = "<%=basePath%>";
	$(function(){
		$("#iframe").attr("height",$(window).height()-50);


	})

</script>
</html>
