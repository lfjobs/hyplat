
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
	<link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/my/vip/prolist.css?version=2026020612" type="text/css"></link>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

	<script src="<%=basePath%>js/WFJClient/pc/my/vip/prolist.js?version=2026020612" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = 0;
        var pageCount  = 0;
        var companyId = "${param.companyId}";




	</script>
</head>
  <body>
  <header>
	  <ul class="clearfix">
		  <li>
			  <a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>/images/WFJClient/Platform/left_jt.png" >
			  </a>
		  </li>
		  <li class="p-com">

		  </li>
		  <li>
		  </li>
	  </ul>
  </header>
  <section class="hd-section">
	  <div class="bg">
	  <div class="sousuo"><img src="<%=basePath%>images/WFJClient/pc/my/sousuo.png"><input type="text" /></div>
		  <div class="sm-div">
			  <ul>
				   <li>
			  <div class="img-div">
				  <img id="imglogo"  onerror="this.src='<%=basePath%>/images/ea/production/forum/reportAnError.png'"/>
			  </div>
				  </li>
				  <li>
			             <p class="p-com"></p><p class="p-hy"></p>
				  </li>
				  <li>
					  <div class="gz-div" id="id-gz"><img src="<%=basePath%>images/WFJClient/pc/my/gzt.png" id="gz"/><span class="span-gz">关注</span></div>
					  <p class="p-fs"><span class="span-fs"></span>人关注</p>

				  <li>
			  </ul>
		  </div>
	  </div>


  </section>

  <div class="content">
	  <ul class="ul-m">
		  <%--<li class="sresult" style="display: none;"><img src="<%=basePath%>images/WFJClient/pc/my/sresult.png"></br>无搜索结果</li>--%>
		   <%--<li>--%>
             <%--<div class="ul-l">--%>
			 <%--<div class="left-div">--%>
				 <%--<div class="img-ul">--%>
					 <%--<img src="https://www.impf2010.com/upload_files/company201009046vxdyzy4wg0000000025/gooddesign/2017-01-26/941f7b965c564029a6aaa08ffeb6ba56.png"/>--%>
				 <%--</div>--%>
			 <%--</div>--%>
			 <%--<div class="right-div">--%>
				 <%--<p class="p-1">经理商城业主会员</p>--%>
				 <%--<p class="p-2">“赠系统会员送商品”</p>--%>
				 <%--<p class="p-3"><span>赠送</span>眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴眼贴</p>--%>
				 <%--<p class="p-4">￥10000</p>--%>
				 <%--<p class="p-5">已售：40万</p>--%>

			 <%--</div>--%>
		    <%--</div>--%>
		  <%--</li>--%>


	  </ul>

  </div>

    
    

</body>
</html>
