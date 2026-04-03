<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>批发商家</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_comlist.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_comlist.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">

        var basePath="<%=basePath%>";
        var companyMID = "${param.companyMID}";
        var companyName = "";
        var pagenumber = 0;
        var  disCrit = "";
        var  saleCrit = "";
        var  placeCrit = "";
        var  cateCrit ="";
        var t = "";

	</script>
</head>
<body>
<header>
	<section>
		<input type="search" name="" id="search" value="" placeholder="搜索商家" />
	</section>
</header>
<div class="content">
	<p class="clearfix">
		<a href="#" class="txt" >
					<span>
						全国
					</span>
			<img src="<%=basePath%>images/ea/collage/phl/sanjiao.png"/>
		</a>
		<a href="#">
					<span>
						销售品类
					</span>
			<img src="<%=basePath%>images/ea/collage/phl/sanjiao.png"/>
		</a>
		<a href="#" id="disCrit">
			距离最近
		</a>
		<a href="#" id="saleCrit">
			销量最高
		</a>
	</p>
	<section>
		<ul class="busiul">
			<%--<li class="clearfix">--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/img_08.png"/>--%>
				<%--<p>霍福兰</p>--%>
				<%--<p>天太世统科技有限公司</p>--%>
				<%--<p>北京市海淀区苏州街北一街48号</p>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/img_09.png"/>--%>
				<%--<p>霍福兰</p>--%>
				<%--<p>天太世统科技有限公司</p>--%>
				<%--<p>北京市海淀区苏州街北一街48号</p>--%>
			<%--</li>--%>
		</ul>
	</section>
	<section id="position">
		<p>
			自动定位:
			<span>
						<img src="<%=basePath%>images/ea/collage/phl/location.png"/>
						<span id="city">未知</span>
					</span>
		</p>
		<menu class="provicem">
			<%--<li class="active">--%>
				<%--河北省--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--山西省--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--湖北市--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--江苏省--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--浙江省--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--澳门特别行政区--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--香港特别行政区--%>
			<%--</li>--%>
		</menu>
		<menu class="citym">
			<%--<li>武汉市</li>--%>
			<%--<li>黄石市</li>--%>
			<%--<li>十堰市</li>--%>
			<%--<li>宜昌市</li>--%>
			<%--<li>孝感市</li>--%>
			<%--<li>荆门市</li>--%>
			<%--<li>咸宁市</li>--%>
			<%--<li>天门市</li>--%>
			<%--<li>荆州市</li>--%>
		</menu>
	</section>
	<section id="category">
		<menu class="leftmenus">
			<%--<li class="active">--%>
				<%--水果--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--蔬菜--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--畜牧水产--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--粮油米面--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--农副加工--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--苗木花草--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--中药材--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--包装--%>
			<%--</li>--%>
		</menu>
		<!--以下分类内容最多五个字-->
		<menu class="rightmenus">
			<%--<li>--%>
				<%--<p>全部</p>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<h3 class="li_bor">--%>
					<%--油类--%>
				<%--</h3>--%>
				<%--<ul class="clearfix">--%>
					<%--<li>花生油</li>--%>
					<%--<li>黄秋葵油</li>--%>
					<%--<li>火麻油</li>--%>
					<%--<li>红花籽油</li>--%>
					<%--<li>核桃油</li>--%>
					<%--<li>芝麻油</li>--%>
					<%--<li>蚝油</li>--%>
					<%--<li>芝麻油</li>--%>
					<%--<li>葵花籽油</li>--%>
				<%--</ul>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<h3 class="li_bor">--%>
					<%--种植类--%>
				<%--</h3>--%>
				<%--<ul class="clearfix">--%>
					<%--<li>豆豉</li>--%>
					<%--<li>大豆</li>--%>
					<%--<li>大米</li>--%>
					<%--<li>大麦</li>--%>
					<%--<li>稻壳</li>--%>
					<%--<li>冻米</li>--%>
					<%--<li>玉米</li>--%>
					<%--<li>稻谷</li>--%>
					<%--<li>黄豆</li>--%>
				<%--</ul>--%>
			<%--</li>--%>
		</menu>
	</section>
</div>
</body>
<script type="text/javascript">
    $(function(){
        //计算选择地区部分高度
        var menuHei=$(window).height()-$("header").outerHeight()-$(".content>p:first-of-type").outerHeight()-$("#position>p:first-of-type").outerHeight();
        $("#position menu").height(menuHei);
        //计算选择品货部分高度
        var menuHei2=$(window).height()-$("header").outerHeight()-$(".content>p:first-of-type").outerHeight();
        $("#category menu").height(menuHei2);
        //选择排序
//        $(".content>p:first-of-type a").not(":nth-of-type(2)").click(function(){
//            //$(this).siblings().removeClass("active");
//            $(this).toggleClass("active");
////            if(!$(".content>p:first-of-type a:last-of-type").is(".active")&&!$(".content>p:first-of-type a:nth-of-type(3)").is(".active")){
////                $(".content>p:first-of-type a:first-of-type").addClass("active");
////            }
//        })

    })
</script>
</html>



