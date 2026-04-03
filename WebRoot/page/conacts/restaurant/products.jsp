<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<head>
   <title>餐饮</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" /> 
	<link href="<%=basePath%>css/contacts/Restaurant/style12.css" rel="stylesheet"
		type="text/css" />
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/contacts/Restaurant/style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/restaurant/products.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/restaurant/font-size.js"></script>
<script type="text/javascript">
	var companyId="${companyId}";
    var ccompanyId='${ccompanyId}';
    var user="${user}";
    var scandc = "${scandc}";
    $(function(){
        if(scandc=="scandc") {
            $(".bhj").html("&nbsp;");
        }

    })
</script>
</head>

<body >
	<div class="wfj12_002">
		<div class="wfj12_002_top">
			<ul id="tops">
				<li class="bhj">
					<a
					href="javascript:history.go(-1)"><img
						src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png" />
				</a>
				</li>
				<li>${contactCompany.companyName}</li>
				<li class="wechat"><a href="<%=basePath%>ea/consignee/ea_toVipCenter.jspa?backu=2"><img src="<%=basePath%>images/ea/office/scanPay/sfshop/vipcenter.png"/></a></li>
			</ul>
		</div>

		<div class="wfj12_002_content">
			<div class="wfj12_002_con_hidden">

				<!--产品中心-->
				<div class="chanp">
					<!--菜单分类-->
					<div class="left" id="ant">
						<div id="product_hide">
							<ul id="product">
							</ul>
						</div>
					</div>
					<!--菜-->
					<div class="right">
						<div id="pp"></div>
					</div>
				</div>
			</div>

		</div>
		<div class="shop_footer">
			<%--<div style="float:left;">--%>
				<h5 class="tit"><a class="left2">购物车</a><a class="delete"><img src="<%=basePath%>images/contacts/restaurant/ico-det.png">清空购物车</a></h5>
				<ul class="shop_car">

					<%--<img src="<%=basePath%>images/contacts/restaurant/shopcar_17.jpg"
						style="vertical-align:middle;" alt="" />--%>
				</ul>
				<ul class="grid">
					<li class="cart">
						<div class="cart_">
							<div class="num0 num2" style="display: none;"><span id="span">1</span></div>
							<img src="<%=basePath%>images/contacts/restaurant/ico-cart.png">
						</div>
					</li>
					<li class="mon" id="total">
						<p>&yen;<span class="money">0</span></p>
					</li>
					<li class="goshopping">
						<p>&yen;<span id="qu"></span></p>
					</li>
					<li class="nomoney"></li>
				</ul>
			<%--</div>--%>

			<%--<div class="mon">
				<span>合计:</span><span>&yen;</span><span class="money">0</span>
			</div>
			<div class="goshopping">
				<span id="qu">去结算</span>
			</div>
			<div class="nomoney"></div>--%>
		</div>
	</div>
	<div class="alert_new_"></div>
	<div class="food_alert">

	</div>
	<div class="alert_"></div>
	<div class="alert_food"></div>

	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var count = 0;
		var pageSize = 0;
		var name = "";
		var mon;
		var pn;
		var pagecount;
		var pagenumber = 1;
		var posNum = "${param.posNum}";

        $(function(){
            var finiMenu = "${param.finiMenu}";
            if(finiMenu=="1"){
                $(".shop_footer .shop_car").toggleClass("display2");
                $(".alert_").toggleClass("display2");
                $(".shop_footer .tit").toggleClass("display2");
            }
		})

	</script>
	
	<script type="text/javascript">
	$(document).ready(function(e) {
    
    $("body").css("height", $(window).height());
	 /*顶部*/
    $("#tops").attr("style","overflow:hidden;background:#fff;");
	$("#tops").find("li").attr("style", "float:left;");
	$("#tops").find("li").eq(0).attr("style","width:15%;");
	$("#tops").find("li").eq(0).find("img").attr("style","height:" + $(window).height() * 0.04+ "px;padding-left:"+ $(window).height() * 0.02+ "px; vertical-align:middle;");

	$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+ $(window).height() * 0.025+ "px;margin-bottom:0px;");
	$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
	$("#tops").find("li").eq(2).find("img").attr("style","height:"+ $(window).height()* 0.056+ "px; width:auto; vertical-align:middle;");
    $(".wfj12_002_top").css("height",$(window).height() * 0.08 + "px");
    $(".wfj12_002_top").css({"lineHeight" : $(window).height()* 0.08 + "px","border-bottom" : "1px solid #e3e3e3","box-sizing" : "content-box"});
	$(".wfj12_002_top li").css({"font-size" : "18px"});
	$(".wfj12_002_top_img").attr("style","margin-top:" + $(window).height() * 0.01+ "px;");
    $(".wfj12_002_hidden").attr("style","height:"+ $(".wfj12_002_sort").height()+ "px;width:"+ parseInt($(".wfj12_002_sort").width() + 17) + "px;");

	//左侧高度
	$("#product_hide").css("height",$(window).height() * 0.8 + "px");

    $(".chanp").attr("style", "height:100%");
    $("#pp").attr("style","height:" + (window.innerHeight) * 0.75+ "px;width:100%;overflow:auto;position:absolute;top:0;left:0;z-index: 9999;");
	$("#pp").css("height", $(window).height()- $(".wfj12_002_top").height()- $(".shopcar").height() + "px");

        $(".right").attr("style","width:100%;overflow:auto;position:absolute;top:"+$(window).height() * 0.08+"px;left:"+$(window).width()*0.22+"px;z-index: 9;");
        $(".right").css("height",$(window).height()- $(".wfj12_002_top").height()- $(".grid").height() + "px");

        $(".content").css("height",$(window).height()*0.92-$(".shop_footer").height()-1+"px");
        $(".right dl").css("width",$(window).width()-$(window).width()*0.22+"px");

  });
	</script>
	<script type="text/javascript">
        $(document).ready(function(){
            $(".wfj12_002 .wfj12_002_content").css("height",$(window).height()*0.92-$(".shop_footer").height()-1+"px");
            $(".wfj12_002 .wfj12_002_content .wfj12_002_con_hidden").css("height",$(window).height()*0.92-$(".shop_footer").height()-1+"px");
            $("#pp").css("height",$(window).height()*0.92-$(".shop_footer").height()-1+"px");
			/*如果购物车有东西*/
            nohas();
            function has(){
                if($(".shop_footer .shop_car").find("li").length > 0){
                    $(".shop_footer .shop_car").show();
                    $(".alert_").show();
                    $(".shop_footer .grid .cart .cart_").attr("style","background-color: #ffc958;");
                    $(".shop_footer .grid .goshopping p").attr("style","background-color: #ffc958;");
                    $(".shop_footer .grid .goshopping p").text("去结算");
                    $(".shop_footer .grid .goshopping p").attr("style","color:#000;");
                    $(".shop_footer .grid .cart .cart_ img").attr("src","<%=basePath%>images/contacts/restaurant/ico-cart.png");
                    $(".shop_footer .grid .goshopping p").show();
                }
            }
			/*如果购物车没有有东西*/
            function nohas(){
                if($(".shop_footer .shop_car").find("li").length == 0){
                    $(".shop_footer .shop_car").hide();
                    $(".alert_").hide();
                    $(".shop_footer .grid .cart .cart_").attr("style","background-color: #454545;");
                    $(".shop_footer .grid .goshopping").attr("style","background-color: #454545;");
                    $(".shop_footer .grid .goshopping p").text("20起送");
                    $(".shop_footer .grid .goshopping p").attr("style","color: #9a9a9a;");
                    $(".shop_footer .grid .goshopping p").hide();
                }
            }

			/*购物车头部定位高度*/
			height();
            function height(){
                var len=$(".shop_footer .shop_car").find("li").length;
                if(len < 7){
                    $(".shop_footer .tit").css("bottom",$(".shop_car li").height()*len+$(".shop_footer .tit").height()-35+"px")
//                    $(".shop_footer .tit").css("bottom",len*2.5+5.5+"rem")
                }else{
                    $(".shop_footer .tit").css("bottom",$(".shop_car li").height()*7+$(".shop_footer .tit").height()-35+"px")
                   // $(".shop_footer .tit").css("bottom",18+5.5+"rem")
                }

            }

			/*购物车加减*/
           /* $(function(){*/
                $(document).on("click",".min",function(){
                    var t=$(this).parent().find('.nub_txt'); //当前产品数量
                    var d=$(this).parents("li").find('.cash span');
                    var dj=$(this).parents("li").find('.cash a');//当前产品单价
                    var ppid=$(this).parents("li").find('#ppid').val();
					var parentId=$(this).parents("li").find('#parentId').val();
                    $old = $("."+ppid).siblings(".span").find(".jian").siblings('h5').html();
                    if ($old == '0') {
                        $new = $old;
                        $("."+ppid).siblings(".span").find(".jian").attr('style', '');
                    } else {
                        $new = parseInt($old) - 1;
                        if (parseInt($new) == 0) {
                            $("."+ppid).siblings(".span").find(".jian").attr('style', '');
                        }
                    }
                    $("."+ppid).siblings(".span").find(".jian").siblings('h5').html($new);
                    if ($new == '0') {
                        $("."+ppid).siblings(".span").find(".jian").css("display", "none");
                        $("."+ppid).siblings(".span").find(".jian").siblings('h5').css("display", "none");
                    }
                    var  number1 = $("."+parentId).siblings().children().text();

                    number1 = parseInt(number1) - 1;

                    if (number1 > 0) {

                        $("."+parentId).siblings().children().text(number1);
                        $("."+parentId).siblings().css("display", "block");
                    }
                    if (number1 == 0) {
                        $("."+parentId).siblings().children().text(number1);
                        $("."+parentId).siblings().css("display", "none");
                    }
                    var nub=$(this).parent().find('.nub_txt').val();
                    var shu=t.val(parseInt(nub)-1);
                    var Num=$(this).parent().find('.nub_txt').val();
                    t.html(Num);
                    t.val(Num);
                    if(parseFloat(t.val())==0){
                        $(this).parents("li").remove();

                        height();
                    }
                    if($(".shop_footer .shop_car").find("li").length == 0){
                        nohas();
                        $(".shop_footer .shop_car").removeClass("display2");
                        $(".alert_").removeClass("display2");
                        $(".shop_footer .tit").removeClass("display2");
                        $(".shop_footer .grid .cart .cart_ .num0").attr("style","none");
                    }
                    var guig=$(this).parents("li").find('.guig').text()==""?"默认规格":$(this).parents("li").find('.guig').text();
                    var url = basePath
                        + "ea/restaurant/sajax_ea_gateringOrders2.jspa?cart.itemNum="
                        + Num+ "&cart.pid=" + ppid;
                    $.ajax({
                        url : encodeURI(url),
                        type : "post",
                        async : true,
                        data : {
                            ccompanyId : ccompanyId,
                            companyId : companyId,
                            "stardard" : guig,
							posNum:posNum
                        },
                        dataType : "json",
                    });
                    setTotal();
                    zongshu();
                });
                $(document).on("click",".add",function(){
                    var t=$(this).parent().find('.nub_txt');
                    var d=$(this).parents("li").find('.cash span');
                    var dj=$(this).parents("li").find('.cash a');//当前产品单价
                    var ppid=$(this).parents("li").find('#ppid').val();
                    var parentId=$(this).parents("li").find('#parentId').val();
                    var caa=$("."+ppid).siblings(".span").children().eq(0).attr("class");
                    if(caa=="jia"){
                    $old = $("."+ppid).siblings(".span").find(".jia").siblings('h5').html();
                    if ($old == '99') {
                        $new = $old;
                    } else {
                        $new = parseInt($old) + 1;
                        if ($new == '99') {
                            $("."+ppid).siblings(".span").find(".jia").css({
                                "border" : "1px solid rgba(0,0,0,0.3)",
                                "color" : "rgba(0,0,0,0.3)"
                            });
                        }

                    }
                    if ($new > '0') {
                        $("."+ppid).siblings(".span").find(".jia").siblings('h5').css("display", "block");
                        $("."+ppid).siblings(".span").find(".jia").siblings(".jian").css("display", "block");
                    }
                    $("."+ppid).siblings(".span").find(".jia").siblings('h5').html($new);
                    $("."+ppid).siblings(".span").find(".jia").siblings(".jian").css({
                        "border" : "1px solid #ff4800",
                        "color" : "#ff4800"
                    });
                    }else {
                        $old = $("."+ppid).siblings(".span").find(".gg").siblings('h5').html();
                        if ($old == '99') {
                            $new = $old;
                        } else {
                            $new = parseInt($old) + 1;
                            if ($new == '99') {
                                $("."+ppid).siblings(".span").find(".gg").css({
                                    "border" : "1px solid rgba(0,0,0,0.3)",
                                    "color" : "rgba(0,0,0,0.3)"
                                });
                            }

                        }
                        if ($new > '0') {
                            $("."+ppid).siblings(".span").find(".gg").siblings('h5').css("display", "block");
                            $("."+ppid).siblings(".span").find(".gg").siblings(".jian").css("display", "block");
                        }
                        $("."+ppid).siblings(".span").find(".gg").siblings('h5').html($new);
                        $("."+ppid).siblings(".span").find(".gg").siblings(".jian").css({
                            "border" : "1px solid #ff4800",
                            "color" : "#ff4800"
                        });
					}
                    var  number1 = $("."+parentId).siblings().children().text();
                    number1 = 1 + parseInt(number1);
                    if (number1 >= 0) {

                        $("."+parentId).siblings().css("display", "block");
                        $("."+parentId).siblings().children().text(number1);

                    } else if (number1 == 0) {
                        $("."+parentId).siblings().css("display", "none");
                    }
                    var guig=$(this).parents("li").find('.guig').text()==""?"默认规格":$(this).parents("li").find('.guig').text();
                    var nub=$(this).parent().find('.nub_txt').val();
                    var shu=t.val(parseInt(nub)+1);
                    var Num=$(this).parent().find('.nub_txt').val();
                    t.html(Num);
                    t.val(Num);
                    var url = basePath
                        + "ea/restaurant/sajax_ea_gateringOrders.jspa?cart.itemNum="
                        + Num+ "&cart.pid=" + ppid;
                    $.ajax({
                        url : encodeURI(url),
                        type : "post",
                        async : true,
                        data : {
                            ccompanyId : ccompanyId,
                            companyId : companyId,
                            "stardard" : guig,
                            posNum:posNum
                        },
                        dataType : "json",
                    });
                    setTotal();zongshu();
                });
               /* setTotal();*/
                function setTotal(){
                    var s=0;
                    $(".shop_footer .shop_car li").each(function(){
                        s+=parseFloat($(this).find('.cash span').text()*($(this).find('.nub .nub_txt').val()=="1"?"1":$(this).find('.nub .nub_txt').val()))*100;
                    });
                    $("#total p span").html((s/100).toFixed(2));
                }

				/*清空购物车*/
                $(document).on("click",".shop_footer .tit .delete",function(){
                   var  ppidint=$(".shop_footer .shop_car").children("li").find("#ppid");
                    var tId = $(".shop_footer .shop_car").children("li").find("#parentId");
                    var t=$(".shop_footer .shop_car").children("li").find('.nub_txt');
                   var ppid="";
                   var num="";
                   var partId="";
                   for(var i=0; i<ppidint.length;i++){
                       ppid+=ppidint[i].value+",";
                       num+=t[i].value+",";
                       partId+=tId[i].value+",";
				   }
                       $.ajax({
                           url :  basePath
                           + "ea/restaurant/sajax_ea_clearCart.jspa?pid=" + ppid,
                           type : "post",
                           async : true,
                           data : {
                               ccompanyId : ccompanyId,
                               companyId : companyId,
                               "cart.stardard" : "默认规格",
							   posNum:posNum
                           },
                           dataType : "json",
                       });
					var id =ppid.split(",");
					for(var i=0;i<id.length;i++){
					    var pp=id[i];
					    if(pp!=""){
                            $old = $("."+pp).siblings(".span").find(".jian").siblings('h5').html();
                            $new = parseInt($old) - parseInt($old);
                            $("."+pp).siblings(".span").find(".jian").css("display", "none");
                            $("."+pp).siblings(".span").find(".jian").siblings(".hh").css("display", "none");
                            $("."+pp).siblings(".span").find(".jian").siblings('h5').html($new);
                        }
					}
					var parentId=partId.split(",");
                    for (var i=0;i<parentId.length;i++){
                        var pare=parentId[i];
                        if(pare!=""){
                        var  number1 = $("."+pare).siblings().children().text();
                        number1 = parseInt(number1) - parseInt(number1);
                            $("."+pare).siblings().children().text(number1);
                            $("."+pare).siblings().css("display", "none");
                        }
					}

                    $(".shop_footer .shop_car").find("li").remove();
                    if($(".shop_footer .shop_car").find("li").length == 0){
                        $(".shop_footer .shop_car").hide();
                        $(".alert_").hide();
                        $(".shop_footer .shop_car").removeClass("display2");
                        $(".alert_").removeClass("display2");
                        $(".shop_footer .tit").removeClass("display2");
                        $("#total p span").html("0");
                        $(".shop_footer .grid .cart .cart_ .num0").attr("style","none");
                    }
                    $(".shop_footer .grid .cart .cart_").attr("style","background-color: none;");
                    $(".shop_footer .grid .goshopping").css("background-color","#454545");
                    $(".shop_footer .grid .goshopping p").text("");
                    $(".shop_footer .grid .goshopping p").attr("style","color: #9a9a9a;");
                    $(".shop_footer .grid .goshopping ").hide();
                });
				/*点击购物车显示隐藏购物列表*/
                $(document).on("click",".shop_footer .grid .cart .cart_",function(){
                    if($(".shop_footer .shop_car").find("li").length == 0){
                        $(".shop_footer .shop_car").hide();
                    }else{
                        $(".shop_footer .shop_car").toggleClass("display2");
                        $(".alert_").toggleClass("display2");
                        $(".shop_footer .tit").toggleClass("display2");
                    }
                });
            $(document).on("click",".shop_footer .grid .mon p",function(){
                if($(".shop_footer .shop_car").find("li").length == 0){
                    $(".shop_footer .shop_car").hide();
                }else{
                    $(".shop_footer .shop_car").toggleClass("display2");
                    $(".alert_").toggleClass("display2");
                    $(" .shop_footer .tit").toggleClass("display2");

                }
            });
                $(document).on("click",".alert_",function(){
                    $(".shop_footer .shop_car").toggleClass("display2");
                    $(".alert_").toggleClass("display2");
                    $(" .shop_footer .tit").toggleClass("display2");

                });

				/*加入购物车列表*/
                $(document).on("click",".jia",function(){
                    var name=$(this).parent().siblings().eq(3).text();
                    var money=parseFloat($(this).parent().siblings().eq(5).children().eq(0).text());
                    var nub=$(this).siblings(".hh").text();
                    var ppid=$(this).parent().siblings("#ppid").val();
                    var goodsId=$(this).parent().siblings("#goodsID").val();
                    var parentId=$(this).parent().siblings("#parentId").val();
                    var ppidj=$(".shop_footer .shop_car").children("li").find("."+ppid).val();
                   if(ppidj==ppid){
                        var t=$(".shop_footer .shop_car").children("li").find("."+ppid).siblings().find('.nub_txt');
                        t.val(nub);
                        t.html(nub);
                       zongshu();
					}else {
                    var list="<li><input type='hidden' value='"
                        + ppid
                        + "' class='"
                        + ppid
                        + "'id='ppid'/><input type='hidden' value='"
                        + goodsId
                        + "'  id='goodsID'/><input type='hidden' value='"+parentId+"'  id='parentId'/><p class='name' style='line-height: 3.5rem; '>"+name+"</p><p class='cash'>&yen;<span>"+money+"</span><a>"+money+"</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='"+nub+"' readonly='readonly'><a style='background-color: #ffc958;' class='add'>+</a></div>";
                    $(".shop_footer .shop_car").append(list);

                    $(".shop_footer .grid .cart .cart_").attr("style","background-color: #ffc958;");
                    $(".shop_footer .grid .goshopping").attr("style","background-color: #ffc958;");
                    $(".shop_footer .grid .goshopping p").text("去结算");
                    $(".shop_footer .grid .goshopping p").attr("style","color:#000;");
                    $(".shop_footer .grid .cart .cart_ img").attr("src","<%=basePath%>images/contacts/restaurant/ico-cart.png");
                    setTotal();
                    height();
                       zongshu();
                    }
                });
            $(document).on("click",".Shopping",function(){
                var name=$(this).parent().siblings().eq(3).text();
                var ppid=$(this).parent().siblings("#ppid").val();
                var money=parseFloat($(this).siblings(".money1").find("span").text());
                var goodsId=$(this).parent().siblings("#goodsID").val();
                var parentId=$(this).parent().siblings("#parentId").val();
                var nub=$(".shop_footer .shop_car").children("li").find("."+ppid).siblings().find('.nub_txt');//获取购物车的购买个数
                var gg=$("."+ppid).siblings(".name").find(".guig");//获取购车里的规格
                var g="";//获取购车里的规格
                var nu="";//获取购物车的购买个数
                for(var j=0;j<gg.length;j++){
                    g+=gg[j].getAttribute("value")+",";
                    nu+=nub[j].value+",";
				}
                var gu=$(".active");
                var guig="";//添加的规格
                for(var i=0;i<gu.length;i++){
                    guig+=gu[i].getAttribute("value")+"+";
                }
                if(guig!="") {
                    var glist=g.split(",");//获取购车里的规格
                    var nulist=nu.split(",");//获取购物车的购买个数
                    if(glist!=""){
						var gig=guig.substr(0, guig.length - 1);
                        var a = gig.split("+").join("");
						if(glist.indexOf(gig)!=-1){
                            var t=$("#"+a).parent().siblings().find(".nub_txt").val();
                            var num=parseInt(t)+1;
                            $("#"+a).parent().siblings().find(".nub_txt").val(num);
                            $("#"+a).parent().siblings().find(".nub_txt").html(num);
                            zongshu();
						}else {
                            var list = "<li><input type='hidden' value='"
                                + ppid
                                + "' class='"
                                + ppid
                                + "'id='ppid'/><input type='hidden' value='"
                                + goodsId
                                + "'  id='goodsID'/><input type='hidden' value='" + parentId + "'  id='parentId'/><p class='name'>" + name + "<br/><span class='guig' style='font-size:10px' value='"+gig+"'id='"+a+"'>"+gig +"</span></p><p class='cash'>&yen;<span>" + money + "</span><a>" + money + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='1' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div></li>";
                            $(".shop_footer .shop_car").append(list);

                            $(".shop_footer .grid .cart .cart_").attr("style", "background-color: #ffc958;");
                            $(".shop_footer .grid .goshopping").attr("style", "background-color: #ffc958;");
                            $(".shop_footer .grid .goshopping p").text("去结算");
                            $(".shop_footer .grid .goshopping p").attr("style", "color:#000;");
                            $(".shop_footer .grid .cart .cart_ img").attr("src", "<%=basePath%>images/contacts/restaurant/ico-cart.png");
                            setTotal();
                            height();
                            zongshu();
						}
                    }else {
                        var gig=guig.substr(0, guig.length - 1);
                        var a = gig.split("+").join("");
                        var list = "<li><input type='hidden' value='"
                            + ppid
                            + "' class='"
                            + ppid
                            + "'id='ppid'/><input type='hidden' value='"
                            + goodsId
                            + "'  id='goodsID'/><input type='hidden' value='" + parentId + "'  id='parentId'/><p class='name'>" + name + "<br/><span class='guig' style='font-size:10px' value='"+gig+"' id='"+a+"'>"+gig+"</span></p><p class='cash'>&yen;<span>" + money + "</span><a>" + money + "</a></p><div class='nub'><a class='min'>-</a><input class='nub_txt' type='text' value='1' readonly='readonly' onfocus='this.blur()'><a style='background-color: #ffc958;' class='add'>+</a></div></li>";
                        $(".shop_footer .shop_car").append(list);

                        $(".shop_footer .grid .cart .cart_").attr("style", "background-color: #ffc958;");
                        $(".shop_footer .grid .goshopping").attr("style", "background-color: #ffc958;");
                        $(".shop_footer .grid .goshopping p").text("去结算");
                        $(".shop_footer .grid .goshopping p").attr("style", "color:#000;");
                        $(".shop_footer .grid .cart .cart_ img").attr("src", "<%=basePath%>images/contacts/restaurant/ico-cart.png");
                        setTotal();
                        height();
                        zongshu();
					}
                }
            });
			/*从购物车列表减去*/
            $(document).on("click",".jian",function(){
                var ppid=$(this).parent().siblings().eq(0).val();
                var t=$(".shop_footer .shop_car").children("li").find("."+ppid).siblings().find('.nub_txt');
                t.val(parseFloat(t.val())-1);
                if(parseFloat(t.val())==0||parseFloat(t.val())<0){
                    $("."+ppid).parents("li").remove();

                    height();
                }
                if($(".shop_footer .shop_car").find("li").length == 0){
                    nohas();
                    $(".shop_footer .shop_car").removeClass("display2");
                    $(".alert_").removeClass("display2");
                    $(".shop_footer .grid .cart .cart_ .num0").attr("style","none");
                }

                zongshu();
            });

        });
function zongshu() {
  var  tId = $(".shop_footer .shop_car").children("li").find("#parentId");
    var partId="";
    for(var i=0; i<tId.length;i++){
        partId+=tId[i].value+",";
    }
    var parentId=partId.split(",");
    var new_arr=[];
    for(var i=0;i<parentId.length;i++) {
        　　var items=parentId[i];
        　　//判断元素是否存在于new_arr中，如果不存在则插入到new_arr的最后
        　　if($.inArray(items,new_arr)==-1) {
            　　　　new_arr.push(items);
		  　　}
        }
	var sum=0;
    for (var i=0;i<new_arr.length;i++){
        var pare=new_arr[i];
        if(pare!=""){
            var  number1 = $("."+pare).siblings().children().text();
            sum = parseInt(sum) + parseInt(number1);
            $(".cart_ .num0").children().html(sum);
            $(".cart_ .num0").css("display", "block");
        }
    }
}

	</script>
	<script type="text/javascript">
        $(function(){
            var ua = navigator.userAgent.toLowerCase();
            var isWeixin = ua.indexOf('micromessenger') != -1;
            if (!isWeixin) {

                $(".wechat").hide();
            }else{
                $(".wechat").show();

            }



        });

	</script>
</body>
</html>