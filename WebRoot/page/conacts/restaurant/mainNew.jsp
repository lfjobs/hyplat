<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>

	<meta charset='utf-8'>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
	<title>${contactCompany.companyName}</title>

	<link rel="stylesheet" href="<%=basePath%>css/swiper.min.css">
	<link href="<%=basePath%>css/contacts/Restaurant/mainNew.css" rel="stylesheet"
		type="text/css" />

	<script type="text/javascript">
        var basePath="<%=basePath%>";
        var companyId="${companyId}";
        var ccompanyId='${ccompanyId}';
        var posNum = "${param.posNum}";
        var postype = "${param.postype}";
        var pageNumber = ${pageForm==null?0:pageForm.pageNumber};
        var  pagecount = ${ppageForm==null?0:ageForm.pageCount};
        var recordCount = ${pageForm==null?0:pageForm.recordCount};
        var mySwiper = "";
        var t = "";
        var maxIndex = ${pageForm==null?0:pageForm.pageSize};
        var categoryId = "${categoryId}";
  function back(){
     history.back();
  }
	</script>
</head>
<body>
<ul class="header">
	<li onclick="back();"><img src="<%=basePath%>images/contacts/restaurant/mainNew/ico-left.png" alt=""></li>
	<li>${contactCompany.companyName}</li>
	<input type="search" name="" value="" placeholder=""  id="search">
</ul>
<div class="ord-con">
<!--banner-->
<div class="swiper-container" id="swiper-ord">
	<div class="swiper-wrapper">
              <c:forEach items="${list }" var="li" varStatus="l">
            		<c:choose>
						<c:when test="${li[6] == '会员分享' }">
							<a href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${li[5] }&ccompanyId=${li[8] }&type=time&miniSystemJudge=03" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
								<span class="banner_tit">${li[0] }</span>
							</a>							
						</c:when>
						<c:otherwise>
						 	<a href="<%=basePath%>ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=${li[8] }&search=${search }&goodsid=${li[3] }" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
						 		<span class="banner_tit">${li[0] }</span>
						 	</a>			
						</c:otherwise>			
					</c:choose>	
            	</c:forEach>
	  </div>
	<div class="swiper-pagination"></div>
</div>
<ul class="ord-nav">
	<li class="active">预定点菜</li>
	<li>门店点餐</li>
	<li>配送点餐</li>
	<li>物流席桌</li>
	<li>快递点餐</li>
	<li>节时带购</li>
</ul>
<div class="ord-nav2">
	<div class="left">
		<img src="<%=basePath%>images/contacts/restaurant/mainNew/ico-l.png" alt="" id="l">
		<div class="con" id="con">
			<ul class="menu">
          <c:forEach items="${catelist}" var="item" varStatus="l">

			  <li id="${item.categoryId}" class="<c:if test="${l.index eq 0}">active</c:if>">${item.categoryName}</li>

				<%--<li class="active">推荐套餐</li>--%>
				<%--<li>牛肉</li>--%>
				<%--<li>潮汕锅底</li>--%>
				<%--<li>六月新品锅底</li>--%>
				<%--<li>潮汕锅底</li>--%>
				<%--<li>六月新品锅底</li>--%>
				<%--<li>潮汕锅底</li>--%>
              </c:forEach>
				<li>未分类</li>
			</ul>
		</div>
		<img src="<%=basePath%>images/contacts/restaurant/mainNew/ico-r.png" alt="" id="r">
	</div>
	<div class="right">
		<a href = "<%=basePath%>ea/restmn/ea_cateMenu.jspa?companyId=${companyId}&ccompanyId=${ccompanyId}&posNum=${param.posNum}">
		<img src="<%=basePath%>images/contacts/restaurant/mainNew/ico-more.png" alt="">
		</a>
	</div>
</div>
<%--<div class="ord-nav3">--%>
	<%--<ul>--%>
		<%--<li class="active">耗牛肉</li>--%>
		<%--<li>毛肚</li>--%>
		<%--<li>千层肚</li>--%>
		<%--<li>牛排</li>--%>
		<%--<li>牛筋</li>--%>
		<%--<li>牛舌</li>--%>
	<%--</ul>--%>
<%--</div>--%>
<div class="swiper-shop">
	<div class="swiper-container" id="swiper-ord2">
		<div class="swiper-wrapper" id="showBig">



           <c:forEach items="${pageForm.list}" var="item" varStatus="l">
			   <div class='swiper-slide' index='${l.index}'>

				<img src='<%=basePath%>${item[3]}' onerror='this.src="<%=basePath%>/images/contacts/restaurant/mainNew/shop1.png"'  alt=''>
				<div class="text">
					<h3>${item[1]}</h3>
					<c:if test="${item[6]} ne '0'">
						<div class='yj'><p class='price'>原价 &yen;${item[4]}<span></span></p><p class='p2'>活动价 &yen;${item[6]}<span></span></p></div>
					</c:if>
					<c:if test="${item[5]} ne '0'">
						<div><p class='price'>原价 &yen;${item[4]}<span></span></p><p class='p2'>VIP价 &yen;${item[5]}"<span></span></p></div>
					</c:if>
					<c:if test="${item[6] eq '0' && item[5] eq '0'}">
						<div><p class='price'>原价 &yen;${item[4]}<span></span></p></div>
					</c:if>
					<div class="btn">
						<span class='ppid'>${item[0]}</span>
						<input class="min" name="" type="button" value="-">
						<input class="text_box" name="" type="text" value="${item[7]}" readonly="readonly">
						<input class="add" name="" type="button" value="+">
					</div>
				</div>
			</div>
		   </c:forEach>
			<%--<div class="swiper-slide">--%>
				<%--<img src="<%=basePath%>images/contacts/restaurant/mainNew/shop1.png" alt="">--%>
				<%--<div class="text">--%>
					<%--<h3>雪花肥牛</h3>--%>
					<%--<p class="price">&yen;49/<span>1（份）</span></p>--%>
					<%--<div class="btn">--%>
						<%--<span>单价:</span><span class="price">1.50</span>--%>
						<%--<input class="min" name="" type="button" value="-">--%>
						<%--<input class="text_box" name="" type="text" value="1" readonly="readonly">--%>
						<%--<input class="add" name="" type="button" value="+">--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="swiper-slide">--%>
				<%--<img src="<%=basePath%>images/contacts/restaurant/mainNew/shop1.png" alt="">--%>
				<%--<div class="text">--%>
					<%--<h3>雪花肥牛</h3>--%>
					<%--<p class="price">&yen;49/<span>1（份）</span></p>--%>
					<%--<div class="btn">--%>
						<%--<span>单价:</span><span class="price">1.50</span>--%>
						<%--<input class="min" name="" type="button" value="-">--%>
						<%--<input class="text_box" name="" type="text" value="1" readonly="readonly">--%>
						<%--<input class="add" name="" type="button" value="+">--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
		</div>
	</div>
	<div class="swiper-button-prev"></div>
	<div class="swiper-button-next"></div>

 </div>
</div>
<div class="fooder">
	<ul>
		<li class="goshopping">结账</li>
		<li>呼叫服务员</li>
		<li class="finiMenu">已点菜单<i>0</i></li>
	</ul>
</div>
<div class="alert_gg_">
	<div class="alert_gg">
		<h4>酸辣笋尖面</h4>
		<img src="<%=basePath%>images/contacts/restaurant/mainNew/ico-x.png" alt="" id="x">
		<ul class="gg">
			<p>规格：</p>
			<li class="active">细面</li>
			<li>宽面</li>
			<li>粗面</li>
		</ul>
		<ul class="gg">
			<p>辣度：</p>
			<li class="active">微辣</li>
			<li>中辣</li>
			<li>重辣</li>
		</ul>
		<div class="bottom">
			<p>&yen;36<span>（重辣）</span></p>
			<div class="btn">
				<input class="min" name="" type="button" value="-">
				<input class="text_box" name="" type="text" value="1" readonly="readonly">
				<input class="add" name="" type="button" value="+">
			</div>
		</div>
	</div>
</div>

</body>
<script src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
<script src="<%=basePath%>js/swiper.min.js"></script>
<script type="application/javascript" src="<%=basePath%>js/font-size.js"></script>
<script type="text/javascript"  src="<%=basePath%>js/restaurant/mainNew.js"></script>
<script>
    timer = setTimeout(function(){
        $(".ord-con").scrollTop("99999");
    },1);
</script>
</html>
