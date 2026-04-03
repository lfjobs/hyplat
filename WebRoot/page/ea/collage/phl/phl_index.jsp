<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>

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
	<title>${companyName}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/phl_index.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/collage/phl/slick/slick.css"/>
	<script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>

	<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_index.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/collage/phl/phl_indexpro.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>css/ea/collage/phl/slick/slick.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>
    <%--<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=f24dd8c87226673baeba0239b2863a6d&plugin=AMap.Geocoder"></script>--%>
	<script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>

	<!-- UI组件库 1.0 -->
   <%--<script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>--%>

	<script type="text/javascript">

        var basePath="<%=basePath%>";

        var pagenumber = 0;
        var ccomIDPlatform = "${ccompanyID}";
        var  codePID = "";
        var  companyID = "${companyID}";
        var  staffid='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';


	</script>


</head>
<body>
<div id="allmap"></div>
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
<section class="frame">
	<div>
		<a class="close" href="javascript:;"><img src="<%=basePath%>images/ea/collage/phl/close.png"/></a>
		<ul class="clearfix">
			<li>
				<p><img src="<%=basePath%>images/ea/collage/phl/purchase.png"/></p>
				<p>发采购</p>
			</li>
			<li>
				<p><img src="<%=basePath%>images/ea/collage/phl/supply.png"/></p>
				<p>发供应</p>
			</li>
		</ul>
	</div>
</section>
<div class="clearfix divdz" style="display: none">
	<img class="img01" src="<%=basePath%>images/WFJClient/Newjspim/wfj11_address_01.png"/>
	<p class="adr">定位中...</p>
	<img class="img02" src="<%=basePath%>images/ea/collage/phl/arrowx.png"/>
</div>
<header>
	<section>
		<input type="search" name="" value="" placeholder="搜索商品" id="search"/>
	</section>
</header>
<div class="banner">
	<div class="slider single-item">
	     <s:iterator value="advertmap1" var="ml">
           <div>
               <a class="banner_img" href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${ml.key}&ccompanyId=${ccompanyID}&type=time&miniSystemJudge=03&pricetype=1">
			<img src="<%=basePath%>${ml.value}"/></a>
		   </div>
        	
        </s:iterator>
      <%--   <div>
			<img src="<%=basePath%>images/ea/collage/phl/banner_01.png"/>
		</div>  --%>
		
		<%--
		
		<div>
			<img src="<%=basePath%>images/ea/collage/phl/banner_01.png"/>
		</div>
		<div>
			<img src="<%=basePath%>images/ea/collage/phl/banner_01.png"/>
		</div> --%>
	</div>
</div>
<script>
    $('.single-item').slick({
        dots: true,
        autoplay: true,
    });
</script>
<div class="content">
	<section>
		<ul class="clearfix">
			<c:if test='${scale eq "0"}'>
			<li>
				<a href="<%=basePath%>page/ea/collage/phl/phl_market.jsp">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_01.png"/>
					</p>
					<p>
						批发市场
					</p>
				</a>
			</li>
			</c:if>
			<c:if test='${scale eq "1"}'>
			<li>
				<a href="<%=basePath%>page/ea/collage/phl/phl_comlist.jsp?companyMID=${companyID}">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_00.png"/>
					</p>
					<p>
						商家
					</p>
				</a>
			</li>
			</c:if>
			<li>
				<a href="<%=basePath%>page/ea/collage/phl/phl_product.jsp?ccompanyID=${ccompanyID}">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_02.png"/>
					</p>
					<p>
						批发商城
					</p>
				</a>
			</li>
			<li>
				<a href="#">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_03.png"/>
					</p>
					<p>
						采购报价
					</p>
				</a>
			</li>
			<li>
				<a href="<%=basePath%>page/ea/collage/phl/phl_car.jsp?ccompanyID=${ccompanyID}">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_04.png"/>
					</p>
					<p>
						有货拉物流
					</p>
				</a>
			</li>
			<li>
				<a href="<%=basePath%>ea/sbq/ea_getAllInfo.jspa?nav=nav&ccomIDPlatform=${ccompanyID}">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_05.gif"/>
					</p>
					<p>
						农友圈
					</p>
				</a>
			</li>
			<li>
				<a href="#" id="btn_1">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_06.png"/>
					</p>
					<p>
						我要发布
					</p>
				</a>
			</li>
			<li class="fenshi">
				<a href="javascript:fenshi()">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_10.gif"/>
					</p>
					<p>
						粉视频
					</p>
				</a>
			</li>
		 	<li class="jmsj">
				<a href="<%=basePath%>page/ea/collage/phl/phl_seljoin.jsp?ccompanyID=${ccompanyID}">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_07.png"/>
					</p>
					<p>
						司机加盟
					</p>
				</a>
			</li> 
			
			<%--  <li>
				<a href="<%=basePath%>ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_08.png"/>
					</p>
					<p>
						商家入驻
					</p>
				</a>
			</li> 
			 <li>
				<a href="<%=basePath%>ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany">
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/navigation_08.png"/>
					</p>
					<p>
						签到领积分
					</p>
				</a>
			</li>  --%>
		</ul>
	</section>
	<section>
		<ul class="clearfix">
			<c:forEach items="${catelist}" var="item" varStatus="status">
				<c:if test="${status.index==4}">
					<li id="">
						<a href="<%=basePath%>ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid=${companyID}&ccompanyID=${ccompanyID}&ccomIDPlatform=${ccompanyID}&companyName=${companyName}&phl=phl">
							<p>
								<img src="<%=basePath%>images/ea/collage/phl/classification_05.png"/>
							</p>
							<p>
								分类
							</p>
						</a>
					</li>
					<li id="${item[1]}"  class="hotcate">
						<a href="#">
							<p>
								<img src="<%=basePath%>${item[2]}" onerror="this.src='<%=basePath%>images/ea/collage/phl/classification_01.png'"/>
							</p>
							<p class="fenlei">${item[0]}</p>
						</a>
					</li>

				</c:if>
				<c:if test="${status.index!=4}">
					<li id="${item[1]}" class="hotcate">
						<a href="#">
							<p>
								<img src="<%=basePath%>${item[2]}" onerror="this.src='<%=basePath%>images/ea/collage/phl/classification_01.png'"/>
							</p>
							<p class="fenlei">${item[0]}</p>
						</a>
					</li>

				</c:if>


			</c:forEach>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_01.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--蔬菜--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_02.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--水果--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_03.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--畜牧水产--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_04.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--农副加工--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_05.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--全部分类--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_10.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--鸡蛋--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_07.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--纸袋--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_06.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--粮油米面--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_08.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--中药材--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<p>--%>
						<%--<img src="<%=basePath%>images/ea/collage/phl/classification_09.png"/>--%>
					<%--</p>--%>
					<%--<p>--%>
						<%--包装--%>
					<%--</p>--%>
				<%--</a>--%>
			<%--</li>--%>
		</ul>
		<ul class="clearfix view">
			<%--<li>--%>
				<%--<a href="#">--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/img_05_01.png"/>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">花生</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">大豆</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">薄皮核桃</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">火龙果</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">哈密瓜</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">葡萄</a>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<a href="#">苹果</a>--%>
			<%--</li>--%>
		</ul>
	</section>
	<section>
		<ul class="clearfix" id="ts">
			<%--  <li>
				<a href="#">
					<p>土特产展馆</p>
					<p>鸽鸽食品香辣豆角</p>
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/img_01.png"/>
						<img src="<%=basePath%>images/ea/collage/phl/img_01.png"/>
					</p>
				</a>
			</li> --%>
			<%-- <li>
				<a href="#">
					<p>新品尝鲜</p>
					<p>三只松鼠夏威夷果12.8/袋</p>
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/img_02.png"/>
						<img src="<%=basePath%>images/ea/collage/phl/img_02.png"/>
					</p>
				</a>
			</li>
			<li>
				<a href="#">
					<p>每日推荐</p>
					<p>鸽鸽食品香辣豆角</p>
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/img_02.png"/>
						<img src="<%=basePath%>images/ea/collage/phl/img_02.png"/>
					</p>
				</a>
			</li>
			<li>
				<a href="#">
					<p>爆款来了</p>
					<p>三只松鼠夏威夷果12.8/袋</p>
					<p>
						<img src="<%=basePath%>images/ea/collage/phl/img_03.png"/>
						<img src="<%=basePath%>images/ea/collage/phl/img_03.png"/>
					</p>
				</a>
			</li>  --%>
		</ul>
	</section>
	<section class="banner_02">
		<div class="slider single-item">
		  <s:iterator value="advertmap2" var="ml">
           <div>
            <a class="banner_img"  href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${ml.key}&ccompanyId=${ccompanyID}&type=time&miniSystemJudge=03&pricetype=1">
			<img src="<%=basePath%>${ml.value}"/>
			</a>
		   </div>
        	
        </s:iterator>
			<%-- <div>
				<img src="<%=basePath%>images/ea/collage/phl/banner_02.png"/>
			</div>
			<div>
				<img src="<%=basePath%>images/ea/collage/phl/banner_02.png"/>
			</div>
			<div>
				<img src="<%=basePath%>images/ea/collage/phl/banner_02.png"/>
			</div>
			<div>
				<img src="<%=basePath%>images/ea/collage/phl/banner_02.png"/>
			</div> --%>
		</div>
	</section>
	<script>
        $('.single-item').slick({
            dots: true,
            autoplay: true,
        });
	</script>
	<section>
		<img src="<%=basePath%>images/ea/collage/phl/img_05.png"/>
		<menu id="rotation">
			<%--<li>--%>
				<%--<p class="txt_2">--%>
					<%--1今年鸡蛋价格跌涨如何？目前多少 钱一斤？--%>
				<%--</p>--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/pic_00.png"/>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<p class="txt_2">--%>
					<%--2今年鸡蛋价格跌涨如何？目前多少 钱一斤？--%>
				<%--</p>--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/pic_00.png"/>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<p class="txt_2">--%>
					<%--3今年鸡蛋价格跌涨如何？目前多少 钱一斤？--%>
				<%--</p>--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/pic_00.png"/>--%>
			<%--</li>--%>
		</menu>
	</section>
	<section>
		<h3 class="clearfix">
			有货拉物流
			<span><a href="<%=basePath%>page/ea/collage/phl/phl_car.jsp?ccompanyID=${ccompanyID}">更多</a></span>
		</h3>
			<ul class="yhl">
				<%-- <li class="clearfix"><img
					src="<%=basePath%>images/ea/collage/phl/img_06.png" />
					<menu>
						<li>车主：吴师傅</li>
						<li>车牌号：鄂C58866</li>
						<li>载重：900公斤</li>
						<li>载重体积：2.4方</li>
					</menu>
					<menu>
						<li>车型：小面包车</li>
						<li>长宽高：1.8*1.2*1.1</li>
						<li>市场：湖北宏伟批发市场</li>
					</menu></li>
				<li class="clearfix"><img
					src="<%=basePath%>images/ea/collage/phl/img_07.png" />
					<menu>
						<li>车主：吴师傅</li>
						<li>车牌号：鄂C58866</li>
						<li>载重：900公斤</li>
						<li>载重体积：2.4方</li>
					</menu>
					<menu>
						<li>车型：小面包车</li>
						<li>长宽高：1.8*1.2*1.1</li>
						<li>市场：湖北宏伟批发市场</li>
					</menu></li>
			</ul> --%>
		</section>
	<section>
		<h3 class="clearfix">
			优质商家
			<span><a href="<%=basePath%>page/ea/collage/phl/phl_comlist.jsp?companyMID=${companyID}">更多</a></span>
		</h3>
		<ul class="buul">
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

	<section>
		<h3 class="clearfix">
			${scale=="0"?"推荐市场":"附近其它市场"}
			<span><a href="<%=basePath%>page/ea/collage/phl/phl_market.jsp">更多</a></span>
		</h3>
		<ul class="smul">
			<%--<li class="clearfix">--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/pic_01.png"/>--%>
				<%--<p>火猫货科技有限公司</p>--%>
				<%--<p>--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/location.png"/>--%>
					<%--北京市海淀区苏州街北一街17号--%>
				<%--</p>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<img src="<%=basePath%>images/ea/collage/phl/pic_02.png"/>--%>
				<%--<p>凯尔拉科技有限公司</p>--%>
				<%--<p>--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/location.png"/>--%>
					<%--北京市海淀区苏州街北一街17号--%>
				<%--</p>--%>
			<%--</li>--%>
		</ul>
	</section>
<%-- 	<section>
		<h3 class="clearfix">
			采购报价
			<span><a href="#">更多</a></span>
		</h3>
		<ul>
			<li class="clearfix">
				<p>2018-12-19</p>
				<p>新疆红提</p>
				<p>采购数量500斤</p>
				<p>湖北</p>
				<p>面议</p>
			</li>
			<li class="clearfix">
				<p>2018-11-28</p>
				<p>泰国榴莲</p>
				<p>采购数量100斤</p>
				<p>江苏</p>
				<p>面议</p>
			</li>
			<li class="clearfix">
				<p>2018-10-30</p>
				<p>红心蜜柚</p>
				<p>采购数量600斤</p>
				<p>北京</p>
				<p>面议</p>
			</li>
			<li class="clearfix">
				<p>2018-12-13</p>
				<p>海南椰子</p>
				<p>采购数量900斤</p>
				<p>云南</p>
				<p>面议</p>
			</li>
		</ul>
	</section> --%>
	<section>
		<p><span><a href="<%=basePath%>page/ea/collage/phl/phl_product.jsp?ccompanyID=${ccomIDPlatform}">更多</a></span></p>
		<div>
			<ul class="listWidth_last">
				<li class="active" id=""><a href="javascript:;">好货推荐</a></li>
				<%--<li><a href="javascript:;">苹果</a></li>--%>
				<%--<li><a href="javascript:;">香蕉</a></li>--%>
				<%--<li><a href="javascript:;">葡萄</a></li>--%>
				<%--<li><a href="javascript:;">葡萄</a></li>--%>
				<%--<li><a href="javascript:;">葡萄</a></li>--%>
			</ul>
		</div>
		<menu class="promenu">
			<%--<li class="clearfix">--%>
				<%--<a href="#">--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/pic_03.png"/>--%>
					<%--<p>徐香猕猴桃新鲜水果奇异果中大果</p>--%>
					<%--<p>江苏-南京</p>--%>
					<%--<p>57元/斤</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<a href="#">--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/pic_04.png"/>--%>
					<%--<p>当季新鲜金都红肉火龙果</p>--%>
					<%--<p>浙江-金华</p>--%>
					<%--<p>25元/斤</p>--%>
				<%--</a>--%>
			<%--</li>--%>
			<%--<li class="clearfix">--%>
				<%--<a href="#">--%>
					<%--<img src="<%=basePath%>images/ea/collage/phl/pic_05.png"/>--%>
					<%--<p>红提新疆红提提子新鲜水果</p>--%>
					<%--<p>广东-河源</p>--%>
					<%--<p>38元/斤</p>--%>
				<%--</a>--%>
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
<section class="tshopping_cart">
    <a href="<%=basePath%>ea/wholesaleMall/ea_shoppingCartList.jspa" >
        <div id="end">
            <img src="<%=basePath%>/images/unmannedsupermarket/shopping_cart.png">
            <span id="num_shop">0</span>
        </div>
    </a>
</section>
</body>
<script type="text/javascript">
    $(function(){
        //兼容性处理
        var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;
        $(window).scroll(function(){
            var ban_hei=$(".banner").outerHeight();
            if($(this).scrollTop()>=ban_hei){
                $("header").addClass("fid_top");
                $(".content").css("padding-top",$("header").outerHeight())
            }else{
                $("header").removeClass("fid_top");
                $(".content").css("padding-top",0)
            }
        })


        //我要发布
        $("#btn_1").click(function(){
            $(".frame").toggle();
            $("body").css({overflow:"hidden"})
        })
        $(".close").click(function(){
            $(".frame").toggle();
            $("body").css({overflow:"auto"})
        })

		//最新资讯轮换
        var i=1;
        var x=2;
        var time=50;//运行速度 越大越慢
        var time2=500;//切换速度 越大越慢
        var int = setInterval(function(){
            star();
            x=1;
        },time)
        function star(){
            clearInterval(int);
            x=2;
            i+=1;
            var li_hei=$("#rotation li:first-of-type").outerHeight();

            if(i%li_hei==0){
                $("#rotation").animate({
                    marginTop:-li_hei,
                },time2,function(){
                    $("#rotation").append($("#rotation li:first-of-type"));
                    $("#rotation").css("margin-top","0");
                })
                i=0;
            }
            int = setInterval(function(){
                star();
                x=1;
            },time)
        }

    })
</script>
</html>

