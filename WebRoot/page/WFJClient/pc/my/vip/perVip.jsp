
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>个人会员</title>
	<meta charset="utf-8"/>
	<meta name="viewport"
		  content="width=device-width, initial-scale=1.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
	<link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/my/vip/perVip.css" type="text/css"></link>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

	<script src="<%=basePath%>js/WFJClient/pc/my/vip/perVip.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = 0;
        var pageCount  = 0;




	</script>
</head>
  <body>
  <header>
	  <ul class="clearfix">
		  <li>
			  <a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png" >
			  </a>
		   </li>
		  <li>
			  个人会员
		  </li>
		  <li>
		  </li>
	  </ul>
  </header>
  <div class="content">
	  <%--<div class="mm">
	  <div class="left"><img src = "<%=basePath%>images/home/ico-6.png"></div>
	  <div class="right">
		  <div class="com">北京天太世统科技有限公司</div>
		  <div>
			  <ul class="pro">
				  <li><span class="zpro">业务经理系统</span><span>￥600</span></li>
				  <ul class="zp">
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
				  </ul>
		      </ul>
		  </div>
		  <div>
			  <ul class="pro">
				  <li><span class="zpro">业务经理系统</span><span>￥600</span></li>
				  <ul class="zp">
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
				  </ul>
			  </ul>
		  </div>
     </div>
	  </div>
	  <div class="mm">
	  <div class="left"><img src = "<%=basePath%>images/home/ico-6.png"></div>
	  <div  class="right">
		  <div class="com">北京投资管理有限公司</div>
		  <div>
			  <ul class="pro">
				  <li><span class="zpro">业务经理系统</span><span>￥600</span></li>
				  <ul class="zp">
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
				  </ul>
			  </ul>
		  </div>
		  <div>
			  <ul class="pro">
				  <li><span class="zpro">业务经理系统</span><span>￥600</span></li>
				  <ul class="zp">
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
					  <li><span>赠大眼睛贴</span><span>￥600</span></li>
				  </ul>
			  </ul>
		  </div>
	  </div>


  </div>--%>
  </div>

    
    

</body>
</html>
