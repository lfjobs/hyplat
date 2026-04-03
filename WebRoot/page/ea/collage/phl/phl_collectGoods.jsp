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
	<title>商品收藏</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_collectGoods.css">
	<link rel="stylesheet" href="<%=basePath%>css/ea/collage/phl/font-awesome/4.4.0/css/font-awesome.min.css">
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_collectGoods.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">

        var basePath="<%=basePath%>";


        var pagenumber = 0;
        var pagenumber1 = 0;
        var pagecount= 0;
        var pagecount1 = 0;

        var t = "";
        var tt = "";


	</script>

</head>
<body>

<header>
	<section>
		<input type="search" name="" id="search" value="${param.goodsName}" placeholder="搜索" />
		<div>
			<a>
				<img src="<%=basePath%>images/WFJClient/DigitalMall/ico-search.png" onclick="search()"/>
			</a>
		</div>
	</section>
</header>
<div class="content">
	<div class="clearfix" id="tab-sec">
		<a href="#" class="txt active" >
					<span>
						商品 ( <span id="goodsnum">0</span> )
					</span>
		</a>
		<a href="#" class="pl">
					<span>
					 店铺 ( <span id="shopsnum">0</span> )
					</span>
		</a>
		<a class="input_select" id="a-bianji" href="#">编辑</a>
	</div>



		<section id="sec-shangpin">
			<ul class="one clearfix prourl">

			</ul>
			<div style="display: none" class="zwsp">
			<img src="<%=basePath%>images/ea/collage/phl/zwsp.png"/>
			<p>暂无收藏商品</p>
			</div>
			<div class="div-bot-01 clearfix">
				<div class="checkbox" id="ckx-quanxuan2">
					<input type="checkbox" data-ckx="1" name="">
					<span>全选</span>
				</div>
				<p id="cacel-collect2">取消收藏</p>
			</div>
		</section>
		<section id="sec-dianpu" style="display: none;">
			<ul class="ul-02">

			</ul>
			<div style="display: none" class="zwdp">
				<img src="<%=basePath%>images/ea/collage/phl/zwdp.png"/>
				<p>暂无收藏店铺</p>
			</div>
			<div class="div-bot-02 clearfix">
				<div class="checkbox" id="ckx-quanxuan">
					<input type="checkbox" data-ckx="1" name="">
					<span>全选</span>
				</div>
				<p id="cacel-collect">取消收藏</p>
			</div>
		</section>


</div>
<section class="shelter_layer">
	<div>
		<p></p>
		<p class="tip">取消商品收藏成功</p>
	</div>
</section>

<section class="warn_layer">
	<div>
		<p class="u-icon-warningO">!</p>
		<p class="cons"></p>
	</div>
</section>
</body>

</html>

