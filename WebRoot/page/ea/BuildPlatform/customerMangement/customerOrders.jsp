<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>北京天太世统科技有限公司</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/swiper-3.3.1.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/BuildPlatform/customerOrders.js"></script>

		<script type="text/javascript">	
		    var basePath='<%=basePath%>';					
			var t;//计时器
			var pagenumber=0;
			var pagecount=0;
			var companyid = "${sessionScope.companyid }";
			var cusName;
		</script>
</head>
<body>
<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1)"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">客户列表</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="search_frd_">
            <div class="search_frd">
                <input type="text" id="cusName" />
                <div class="search_">
                    <img src="<%=basePath %>images/BuildPlatform/ico-search.png" alt="">
                    <p>搜索 </p>
                </div>
            </div>
        </div>
        <div class="con" id="cus"></div>

    </div>
</div>

</body>
</html>