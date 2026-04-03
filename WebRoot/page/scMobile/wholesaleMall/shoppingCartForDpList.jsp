<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<title>商品清单</title>
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/keyboard2.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>js/scMobile/wholesaleMall/shoppingCart/selfService_qdlist.js" type="text/javascript"></script>
<!-- 兼容Electron -->
<script>if(typeof module === 'object') {window.module = module; module = undefined;}</script>
<!-- 兼容Electron -->
<script>if(window.module) module = window.module;</script>
<script>
	function onFocus () {
		var target = event.target
		setTimeout(function () {
			target.readOnly = false
		},0)
	}
	function onBlur() {
		event.target.readOnly = true
	}
	var basePath = "<%=basePath%>";
	var barCode = "${barCode}";
	var journalNum = "${param.journalNum}";
	var  number = 0;
	var totalnum = 0;
	var totalmoney = 0;
	var posNum = "${param.posNum}";
	var ccompanyID = "${param.ccompanyID}";
	var companyName = "${companyName}";
	var companyID = "${companyID}";
	var fh = "${fh}";
	var directUrl = "${param.directUrl}";
	var back = "${param.back}";
	$(function(){
		$(".barcode").focus();
		$(".barcode").blur(function(){
				$(".barcode").focus();
		});

		if(fh=="1"){
			 $(".head1").show();
			$(".head2").hide();
			$(".comm").removeClass("comm_s")
			$("figure").addClass("figure-ones");
		}else{
			$(".head1").hide();
			$(".head2").show();
			$(".comm").addClass("comm_s")
		}

	});
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
			VirtualKeyboard.toggle('ipt', 'softkey');
			$("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
		  //  $("#softkey").attr("style","position:absolute;z-index:99;top:"+top+"px;left:"+left+"px;");

		}
	}
</script>
</head>
<body>
<section class="comm wet ">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header>
			<img src="<%=basePath%>images/supermarket/143.png" alt="">
			<a><input type="button" value="返回首页" id="backhome" onclick="toBack();"></a>
		</header>
		<!--头部 end-->
		<!--内容-->
		<form  id  = "form" name = "form" class="form1" method="post"  target="hidden" action="<%=basePath%>ea/sm/ea_payErCode.jspa?posNum=${param.posNum}&ccompanyID=${param.ccompanyID}&companyName=${companyName}">
			<input type="submit" name="submit" style="display:none;"/>
			<input type="hidden" name="message" value="0"/>
			<input type="hidden" name="companyID" value="${companyID}"/>
			<figure>
				<div class="head head1">
					<h2 class="serial">序号</h2>
					<h2 class="name">商品名称</h2>
					<h2 class="number">数量</h2>
					<h2 class="money">金额</h2>
				</div>
				<div class="head head2" style="display: none;">
					<h2 class="serial">序号</h2>
					<h2 class="name">商品名称</h2>
					<h2 class="number">已取数量</h2>
					<h2 class="number">配送数量</h2>
					<h2 class="money">金额</h2>
				</div>
				<ul class="shop">
				</ul>
				<div class="total">
					<p class="mony">合计&yen;<span class="totalmoney">0</span></p>
					<p class="piece">共<span class="totalnum">0</span>件</p>
				</div>
				<div class="btn2">
					<input type="button" value="去付款" id="pay">
					<input type="button" value="购物袋" id="shop_car">
					<input type="button" value="无码称重" id="wm">
				</div>
				<div class="btn">

					<input type="text" value="" placeholder="扫描或手动输入条码" onfocus="onFocus()" onblur="onBlur()" id="ipt" class="barcode" onclick='test(this);'>
					<input type="button" value="确定" id="btn">
				</div>

			</figure>
		</form>
		<!--内容 end-->
		<!--键盘弹框-->
		<table class="jp-se">
			<tbody><tr>
				<td> <input id="Button1" type="button" value="1" onclick="return btnNum_onclick(1)"></td>
				<td> <input id="Button2" type="button" value="2" onclick="return btnNum_onclick(2)"></td>
				<td> <input id="Button3" type="button" value="3" onclick="return btnNum_onclick(3)"></td>
			</tr>
			<tr>
				<td> <input id="Button4" type="button" value="4" onclick="return btnNum_onclick(4)"></td>
				<td> <input id="Button5" type="button" value="5" onclick="return btnNum_onclick(5)"></td>
				<td> <input id="Button6" type="button" value="6" onclick="return btnNum_onclick(6)"></td>
			</tr>
			<tr>
				<td><input id="Button7" type="button" value="7" onclick="return btnNum_onclick(7)"></td>
				<td><input id="Button8" type="button" value="8" onclick="return btnNum_onclick(8)"></td>
				<td><input id="Button9" type="button" value="9" onclick="return btnNum_onclick(9)"></td>
			</tr>
			<tr>
				<td><input id="ButtonDel" type="button" value="删除" onclick="return delText()"></td>
				<td><input id="Button0" type="button" value="0" onclick="return btnNum_onclick(0)"></td>
				<td><input id="Buttond" type="button" value="." onclick="return dian()"></td>
			</tr>
			<tr>
				<td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"></td>
				<%--<td colspan="2"><input id="btnEnter" type="button" value="确定" ></td>--%>
			</tr>
			</tbody>
		</table>
		<!--称重商品弹框-->
		<div class="alert_weigh_">
			<div class="alert_weigh">
				<p class="mb"><span>3</span>秒后自动关闭</p>
				<h2 class="tipcon">称重商品须依次扫描</h2>
				<input type="button" value="确定" id="confirm">
			</div>
		</div>
		<!--购物袋弹框-->
		<div class="alert_shopping_"></div>
		<div class="alert_shopping">
			<h1>来个购物袋</h1>
			<p>付款后别忘记领取哦</p>
			<ul class="bagtbl">
			<%--	<li>
					<div class="xz">
						<img src="<%=basePath%>images/supermarket/gou2.png" alt="" class="gou">
						<img src="<%=basePath%>images/supermarket/131.png" alt="">
					</div>
					<h4>小购物袋</h4>
					<p class="mony" style="display: none;">0.20</p>
					<p class="money">&yen;<span>0</span></p>
					<div class="jj">
						<input type="button" value="-" class="min">
						<input type="text" value="0" class="text" readonly="readonly">
						<input type="button" value="+" class="add">
					</div>
				</li>
				<li>
					<div class="xz big">
						<img src="<%=basePath%>images/supermarket/gou2.png" alt="" class="gou">
						<img src="<%=basePath%>images/supermarket/131.png" alt="">
					</div>
					<h4>大购物袋</h4>
					<p class="mony" style="display: none;">0.40</p>
					<p class="money">&yen;<span>0</span></p>
					<div class="jj">
						<input type="button" value="-" class="min">
						<input type="text" value="0" class="text" readonly="readonly">
						<input type="button" value="+" class="add">
					</div>
				</li>--%>
			</ul>
			<div class="btn">
				<input type="button" value="不需要" id="noneed">
				<input type="button" value="确定" id="sure">
			</div>
		</div>
		<!--称重提示 2019.3.12-->
		<div class="alert_wed_">
			<div class="alert_wed">
				<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" id="del">
				<div class="wed_btn">
					<input type="text" value=""  class="parameter jp-s" placeholder="输入条码/货号/名称/拼音码" id="wed_ipt">
					<input type="button" value="确定" id="wed_btn" class="cfm">
					<%--<img src="<%=basePath%>images/supermarket/ico-jp.png" alt="">--%>
				</div>
				<ul class="content">
					<li class="left">
						<h3>热销产品</h3>
						<ul class="con hotsale">

						</ul>
					</li>
					<li class="right">
						<h3 class="scale1">请选择需要称重的商品</h3>
						<h3 class="scale2" style="display:none;">请输入商品数量</h3>
						<ul class="con scalemain">
							<li>
								<h4>名称：<span class="goodsname"></span><span class="plu"></span></h4>
							</li>
							<li>
								<h4>单价：<span class="price"></span>元</h4>
								<span class="hhh" style="display: none;"></span>
								<span class="pd" style="display: none;"></span>
								<span class="ppprit" style="display: none;"></span>
								<span class="cccostm" style="display: none;"></span>
								<span class="aaactivityID" style="display: none;"></span>
							</li>
							<li class="weight">
								<h4>重量：<span><input class= "wvalue" type="text" value="0" readonly="readonly">KG</span></h4>
							</li>
							<li class="num" style="display: none;">
								<h4>个数：<span><input type="text" id="inputnum">pcs</span></h4>
							</li>
							<li>
								<h4>合计：<span class="totalMoney"></span>元</h4>
							</li>
						</ul>
						<input type="button" value="确定" class="btn"  id="toCon">
					</li>
				</ul>
			</div>
		</div>
		<!--温馨提示 2019.3.14-->
		<div class="alert_wxts_">
			<div class="alert_wxts">
				<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" class="del-2">
				<img src="<%=basePath%>images/supermarket/wxts.png" alt="" class="wxts">
				<div class="btn">
					<input type="button" value="继续称重" class="jx">
					<input type="button" value="立即结账" class="lj">
				</div>
			</div>
		</div>
		<!--搜索商品 2019.3.12-->
		<div class="alert_sssp_ ql">
			<div class="alert_sssp">
				<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" id="del-3">
				<div class="wed_btn">
					<input type="text" class="parameter" value="" placeholder="输入plu/名称/拼音码" id="wed_ipt-3">
					<input type="button" value="确定" id="wed_btn-3" class="cfm">
					<img src="<%=basePath%>images/supermarket/ico-jp.png" alt="">
				</div>
				<div class="tab">
					<table width="870" class="tab-tit">
						<thead>
						<tr>
							<th style="width: 50%;" class="name">商品名称</th>
							<th style="width: 12%;">plu</th>
							<th style="width: 19%;">单价</th>
							<th style="width: 19%;">计价单位</th>
						</tr>
						</thead>
					</table>
					<div class="tab-box">
						<table width="870">
							<tbody class="salegoods">
							<%--<tr>--%>
							<%--<td class="name">红富士大苹果脆甜可脆甜可</td>--%>
							<%--<td class="hh">1</td>--%>
							<%--<td class="tm">600元</td>--%>
							<%--<td class="dj">KGM</td>--%>
							<%--</tr>--%>

							</tbody>
						</table>
					</div>
					<input type="button" value="确定" id="sure2">
				</div>
			</div>
		</div>
		<div id="softkey"></div>
		<iframe name="hidden" width="0" height="0" />
	</article>
	<!--扫码支付内容 end-->
</section>
<script type="text/javascript">
    $(function(){
        $("#city").click(function (e) {
            SelCity(this,e);
        });
		/*删除列表*/
        $(document).on("click",".wet figure .shop .del",function () {
            $(this).parents("li").remove();
        });
    })
</script>
<script>
    //后退
    function toBack() {
        window.location.href = basePath + "ea/wholesaleMall/ea_toWholesaleMall.jspa";
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });
</script>
</body>
</html>