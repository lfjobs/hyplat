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
	<title>拼货拉批发商城</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_product.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_product.js?version=20210713" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">

        var basePath="<%=basePath%>";
        var ccompanyID = "${param.ccompanyID}";
        var companyName = "";
        var pagenumber = 0;
        var  disCrit = "";
        var  saleCrit = "";
        var  placeCrit = "";
        var  cateCrit ="${param.cateCrit}";
        var  cateName = "${param.cateName}";
        var goodsName = "${param.goodsName}";
        var  priceCrit = "";
        var t = "";
        var hsize;
        var hcolor;
       var hotID = "${param.hotID}";
       var hotValue = "${param.hotValue}";

	</script>

</head>
<body>
<div class="pecifications" style="display:none;">
	<div class="box_per">
		<div class="clearfix">
			<img id="img_src" src="<%=basePath%>images/ea/collage/phl/img_003.png"/>
			<p id="price"></p>
			<div class="xel">
				<span class="selstate">请选择</span>  <span class="selsize"></span>  <span class="selcolor"></span>
			</div>
			<img class="pecifications_del" src="<%=basePath%>images/ea/collage/phl/pecifications_del.png"/>
		</div>
		<section class="clearfix size">
			<h4>尺码</h4>
			<div class="choice_01 psize">
				<%--<p class="active_cm">35</p>--%>
				<%--<p>36</p>--%>
				<%--<p>37</p>--%>
				<%--<p>38</p>--%>
				<%--<p>39</p>--%>
				<%--<p>40</p>--%>
				<%--<p>41</p>--%>
			</div>
		</section>
		<section class="clearfix color">
			<h4>颜色分类</h4>
			<div class="choice_02 pcolor">
				<%--<p class="active_ys">浅蓝</p>--%>
				<%--<p>深蓝</p>--%>
			</div>
		</section>


		<section class="clearfix">
			<p>购买数量</p>
			<div>
				<img class="Specifications_reduce" src="<%=basePath%>images/ea/collage/phl/Specifications_reduce.png"/>
				<input type="number" class="purchase_quantity" name="" id="" disabled value="1" />
				<img class="Specifications_add" src="<%=basePath%>images/ea/collage/phl/Specifications_add.png"/>
			</div>
		</section>
		<input type="button" name=""  id="sure" value="确定" />
	</div>
</div>
<header>
	<section>
		<input type="search" name="" id="search" value="${param.goodsName}" placeholder="搜索商品" />
		<div>
			<a href="<%=basePath%>/ea/wholesaleMall/ea_shoppingCartList.jspa">
			<img src="<%=basePath%>images/ea/collage/phl/shopping_cart_2.png"/>
			<span id="num_shop">0</span>
			</a>
		</div>
	</section>
</header>
<div class="content">
	<div class="clearfix">
		<a href="#" class="txt" >
					<span>
						全国
					</span>
			<img src="<%=basePath%>images/ea/collage/phl/sanjiao.png"/>
		</a>
		<a href="#" class="pl">
					<span>
						品类
					</span>
			<img src="<%=basePath%>images/ea/collage/phl/sanjiao.png"/>
		</a>
		<a class="input_select" href="#">
			综合排序
		</a>
		<div class="box">
			<div class="dropdown">
				<ul>
				     <li class="zhCrit">
						综合排序
					</li>
					<li class="disCrit">
						距离最近
					</li>
					<li class="saleCrit">
						销量最高
					</li>
					<li class="priceCrit">
						价格最低
					</li>
				</ul>
			</div>
		</div>
		<span>
					<img id="switch" src="<%=basePath%>images/ea/collage/phl/switch_1.png"/>
				</span>
	</div>
	<section>
		<ul class="one clearfix prourl">
			<%--<li class="clearfix">--%>
				<%--<p>--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/img_001.png"/>--%>
					<%--<span class="cx"><i></i></span>--%>
				<%--</p>--%>
				<%--<p>红富士大苹果</p>--%>
				<%--<p>￥<i>288</i>元/斤<span>原价<del>45元</del></span></p>--%>
				<%--<p>湖北十堰</p>--%>
				<%--<p>销量30750笔</p>--%>
				<%--<a href="#" class="shopping_cart" >--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/shopping_cart.png"/>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<p class="cx"><img src="<%=basePath%>images/ea/collage/phl/img_002.png"/><span class="vip"><i></i></span></p>--%>
				<%--<p>猕猴桃</p>--%>
				<%--<p>￥<i>66</i>元/斤<span>原价<del>45元</del></span></p>--%>
				<%--<p>湖北十堰</p>--%>
				<%--<p>销量30750笔</p>--%>
				<%--<a href="#" class="shopping_cart"  >--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/shopping_cart.png"/>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<p><img src="<%=basePath%>images/ea/collage/phl/img_002.png"/><span class="tj"><i></i></span></p>--%>
				<%--<p>猕猴桃</p>--%>
				<%--<p>￥<i>35</i>元/斤<span>原价<del>45元</del></span></p>--%>
				<%--<p>湖北十堰</p>--%>
				<%--<p>销量30750笔</p>--%>
				<%--<a href="#" class="shopping_cart">--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/shopping_cart.png"/>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<p class="vip"><img src="<%=basePath%>images/ea/collage/phl/img_003.png"/></p>--%>
				<%--<p>砂糖柑</p>--%>
				<%--<p>￥<i>35</i>元/斤<span>原价<del>45元</del></span></p>--%>
				<%--<p>湖北十堰</p>--%>
				<%--<p>销量30750笔</p>--%>
				<%--<a href="#" class="shopping_cart">--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/shopping_cart.png"/>--%>
				<%--</a>--%>
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
<section class="shelter_layer">
	<div>
		<p></p>
		<p>恭喜，添加购物车成功！</p>
	</div>
</section>

<section class="warn_layer">
	<div>
		<p class="u-icon-warningO">!</p>
		<p class="cons"></p>
	</div>
</section>
</body>
<script type="text/javascript">
    $(function(){
        //计算选择地区部分高度
        var menuHei=$(window).height()-$("header").outerHeight()-$(".content>div:first-of-type").outerHeight()-$("#position>p:first-of-type").outerHeight();
        $("#position menu").height(menuHei);
        //计算选择品货部分高度
        var menuHei2=$(window).height()-$("header").outerHeight()-$(".content>div:first-of-type").outerHeight();
        $("#category menu").height(menuHei2);
        //商品部分高度
        var menuHei3=$(window).height()-$("header").outerHeight()-$(".content>div:first-of-type").outerHeight()-$("#position>p:first-of-type").outerHeight();
        $(".content>section:first-of-type").height(menuHei2);


    })
</script>
</html>

