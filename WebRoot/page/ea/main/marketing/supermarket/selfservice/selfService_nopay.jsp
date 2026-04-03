<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>未结算处理</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">

	<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/marketing/supermarket/selfService_nopay.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>

	<script>
		 var basePath = "<%=basePath%>";
		 var posNum  = "${param.posNum}";
		 var relateID = "${list[0].relateID}";

         function test(obj){
             var isAndroid = false;
             try {
                 var u = window.navigator.userAgent;
                 isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
             }catch(error){
                 isAndroid = false;
             }
             if(isAndroid==false){
                 var top = $(obj).offset().top+70;
                 var left = $(obj).offset().left;


                 VirtualKeyboard.toggle('other', 'softkey');
                 $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
                 //  $("#softkey").attr("style","position:absolute;z-index:99;top:"+top+"px;left:"+left+"px;");

             }



         }
	</script>


</head>
<body>

<section class="success nopay">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header>
			<img src="<%=basePath%>images/supermarket/warn.png">
			<p>上次购物有删除或未结算的商品，请说明理由</p>
		</header>
		<!--头部 end-->
		<!--内容-->
		<figure class="fig-wjs">
			<h2><span>${fn:substring(list[0].joinDate,0,19)}</span></h2>
			<ul class="listc">
			<c:forEach items="${list}" var="item">
				<li class="tr">
					<h5><span class="goodsName">${item.goodsName}${item.stardard eq "默认规格"||item.stardard eq null ?"":"*".concat(item.stardard)}</span></h5>
					<p style="display: none;" ><span class="barCode">${item.barCode}</span></p>
					<p class="nub"><span class="num">${item.joinNum}</span></p>
					<p><span class="ynum">${item.itemNum}</span></p>
					<p style="display: none;"><span class="tprice">${item.price}</span></p>
					<p class="mony">&yen;<span class="price"></span></p>
				</li>


			</c:forEach>


			</ul>
			<h4>合计<p>共<span class="totalnum">0</span>件 &yen;<span class="totalmoney">0</span></p></h4>

		</figure>
		<section class="container">
			<!--扫码支付内容-->
			<div class="div-wjs">
				<!--内容-->
				<p>
					删除或没有结算，没有收到现金原因?
				</p>
				<div class="sex">
					<div class="female">
						<input id="female-1" type="radio" name="rea"  class="input-male" value="顾客没有购买">
						<span class="female-custom"></span>
						<label for="female-1">顾客没有购买</label>
					</div>
					<div class="female">
						<input id="female-2" type="radio" name="rea" class="input-male" value="测试">
						<span class="female-custom"></span>
						<label for="female-2">测试</label>
					</div>
					<div class="female">
						<input id="female-3" type="radio" name="rea"  class="input-male" value="系统故障">
						<span class="female-custom"></span>
						<label for="female-3">系统故障</label>
					</div>
					<div class="female">
						<input id="female-4" type="radio" name="rea" class="input-male" value="其他因素">
						<span class="female-custom"></span>
						<label for="female-4" class="lab-qt">其他因素：</label>
						<input type="text" class="inp-qt" name="" id="other" value=""  onclick='test(this);'/>
					</div>
				</div>
				<input type="button" name="" id="save" value="确定提交" />
				<!--内容 end-->
			</div>
			<!--扫码支付内容 end-->
		</section>
	</article>
</section>

<form name="form" id="form" target="hidden" method="post"  action="<%=basePath%>ea/sm/ea_addReason.jspa">
	<input type="submit" name="submit" style="display:none;"/>
	<input type="hidden"  name="scanGoods.reason" id="reason"/>
	<input type="hidden"  name="scanGoods.relateID" id="relateID"/>
</form>
<div id="softkey"></div>
<iframe  name="hidden" width="0" height="0" frameborder="0"></iframe>
</body>
<script>
    $(".input-male").click( function () {
        $(this).siblings("span").addClass("active");
        $(this).parents("div").siblings("div").children("span").removeClass("active");
    });
</script>
</html>