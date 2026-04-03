
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
	<link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/my/vip/perVip1.css" type="text/css"></link>
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
	<link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/my/vip/perVip1.css?version=2026020612" type="text/css"></link>
	<link rel="stylesheet" href="<%=basePath%>css/WFJClient/news_list.css" type="text/css"></link>
	<link rel="stylesheet" href="<%=basePath%>css/WFJClient/swiper-3.3.1.min.css">
	<script type="text/javascript" src="<%=basePath%>js/WFJClient/swiper-3.3.1.min.js"></script>
	<script src="<%=basePath%>js/WFJClient/pc/my/vip/perVip1.js" type="text/javascript" charset="utf-8"></script>

	<script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = 0;
        var pageCount  = 0;

    $(function(){
        var mySwiper = new Swiper('.swiper-container', {
            direction: 'horizontal',
            autoplay: 3000,
            loop: true,
            pagination: '.swiper-pagination', // 分页器
        });

	})


	</script>
</head>
  <body>
  <header>
	  <ul class="clearfix">
		  <li>
			  <a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>/images/WFJClient/Platform/left_jt.png" >
			  </a>
		   </li>
		  <li>
			  个人会员
		  </li>
		  <li>
		  </li>
	  </ul>
  </header>
  <section><div class="sousuo"><select class="searchtype"><option value="1" selected>公司</option><option value="2">商品</option></select><input type="text" /><span class="search">搜索</span></div></section>
  <div class="wrap_page" id="listnews">

		  <div class="banner_wrap swiper-container ">
			  <div class="swiper-wrapper">
				  <c:forEach items="${advlist}" var="li" varStatus="l">
					  <a href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${li[5] }&ccompanyId=${li[8] }&type=time&miniSystemJudge=03" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
						  <%--<span class="banner_tit">${li[0] }</span>--%>
					  </a>
				  </c:forEach>
			  </div>
			  <div class="swiper-pagination"></div>
		  </div>
  </div>
  <div class="content">
	  <section>
		  <div class="hy1">
			  <ul class="hy-ul">
				  <li class="active tj" id="">推荐</li>
				  <li id="scode20190415raqvqk3uvs0000000762">超市</li>
				  <li id="scode20170721cnjcrn5jm20000001237">餐饮</li>
				  <li id="bType202602097W9ZPN52BW0000087282">驾校</li>
				  <li id="scode20180829hr497b9ynb0000000242">零售</li>
				  <li id="scode20170715cnjcrn5jm20000000505">软件</li>
				  <li id="scode20170721cnjcrn5jm20000001466">美容</li>
				  <li id="scode20170722cnjcrn5jm20000002739">金融</li>
			  </ul>
			  <img src="<%=basePath%>images/WFJClient/pc/my/fl.png"  class="industryType"/>
			  <input type="hidden" name="contactCompany.industryId" class="industryId" value="">
			  <input type="hidden" class="industryName" value="">
		  </div>
	  </section>
	  <div class="sresult" style="display: none;"><img src="<%=basePath%>images/WFJClient/pc/my/sresult.png"></br>无搜索结果</div>
	  <div class="main" style="display: none;"></div>
	 <%--<div class="mm">--%>
	  <%--<div class="left">--%>
		  <%--<div class="div1">--%>
		  <%--<img src = "<%=basePath%>upload_files/company20250331WEBHY485MY0000000084/scmanage/2026-01-27/88f64854f0104fa08362cab356802aef.jpg">--%>
		  <%--</div>--%>
		  <%--<div  class="div2"> <div class="div3"></div><div class="div4">购物送业务会员</div><img src="<%=basePath%>images/WFJClient/pc/my/gw.png"></div>--%>


	  <%--</div>--%>
	  <%--<div class="right">--%>
		  <%--<div class="com">北京天太世统科技有限公司</div>--%>
		  <%--<p class="p-address">北京市东城区东直门外大街</p>--%>
		  <%--<p class="p-hy">IT软件</p>--%>
		  <%--<p class="p-totalSales">已售6000</p>--%>
		  <%--<div class="p-pro">--%>
			  <%--<ul class="p-main">--%>
				<%--<li>--%>
			    <%--<ul class="p-fpro">--%>
			       <%--<li>--%>
					   <%--<div class="pro-img"><img src="https://www.impf2010.com/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2025-03-11/f470638eb5a04efbb79c0659349abff0.jpg" /></div></li>--%>
			       <%--<li>经理商城业主会员</li>--%>
					<%--<li><span>赠送</span>眼贴盐酸额度眼贴盐酸额度眼贴盐酸</li>--%>
			      <%--<li>￥10000</li>--%>
		        <%--</ul>--%>
			  <%--</li>--%>
				  <%--<li>--%>
					  <%--<ul class="p-fpro">--%>
						  <%--<li><div class="pro-img"><img src="https://www.impf2010.com/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2025-03-11/f470638eb5a04efbb79c0659349abff0.jpg" /></div></li>--%>
						  <%--<li>经理商城业主</li>--%>
						  <%--<li><span>赠送</span>眼贴盐酸额度眼贴盐酸额度眼贴盐酸</li>--%>

						  <%--<li>￥10000</li>--%>
					  <%--</ul>--%>
				  <%--</li>--%>
				  <%--<li>--%>
					  <%--<ul class="p-fpro">--%>
						  <%--<li><div class="pro-img"><img src="https://www.impf2010.com/upload_files/company20171016JB3UJDBINY0000046150//upload_files/gooddesign/2025-03-11/f470638eb5a04efbb79c0659349abff0.jpg" /></div></li>--%>
						  <%--<li>经理商城业主会员</li>--%>
						  <%--<li><span>赠送</span>眼贴盐酸额度眼贴盐酸额度眼贴盐酸</li>--%>

						  <%--<li>￥10000</li>--%>
					  <%--</ul>--%>
				  <%--</li>--%>
			  <%--</ul>--%>
		  <%--</div>--%>
     <%--</div>--%>
	  <%--</div>--%>
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

  </div>
  </div>

  <%--行业--%>
  <div class="hyfl">
	  <div class="div-header">
		  <ul class="clearfix">
			  <li>
				  <img src="<%=basePath%>/images/scMobile/qyrz/arrow_left.gif" class="hyback"/>
			  </li>
			  <li>
				  行业分类
			  </li>
			  <li>

			  </li>
		  </ul>
	  </div>
	  <div class="hyfl-content">
		  <input type="text" style="display: none" id="selid"/>
		  <p class="p-top" id="sel">
			  请选择行业
		  </p>
		  <ul class="hy">
		  </ul>
	  </div>
  </div>
    

</body>
</html>
