<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>电子秤称重</title>
	<meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=1,initial-scale=1,user-scalable=no" />
	<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>
	<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/keyboard2.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/scaleMpc.css">
	<script src="<%=basePath%>js/ea/marketing/supermarket/num.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/marketing/supermarket/num2.js" type="text/javascript" charset="utf-8"></script>
	<script src="<%=basePath%>js/ea/marketing/supermarket/scaleMpc.js" type="text/javascript" charset="utf-8"></script>
	<script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
	<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
		<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
	</object>
	<script type="text/javascript">
        var basePath="<%=basePath%>";
        var companyID = "${companyID}";
        var ccompanyID = "";
        var pageNumber = 0;
        var pageSize = 35;
        var pageCount = 0;
        var  number = 0;

	</script>

</head>
<body>
<header>
	<div class="box_content clearfix">
		<div>
			<img src="<%=basePath%>images/supermarket/scale/img_return.png"/>
			<p><a href="javascript:backLogin()">退出</a></p>
		</div>
		<div>
			<input type="text" placeholder="请输入或扫描条形编码" name="" class="t-keybord barcode" id="inp_cz" value=""  autofocus/>
			<img src="<%=basePath%>images/supermarket/scale/jianp.png"/>
		</div>
		<ul>
			<li class="hd">
				<a href="<%=basePath%>ea/sm/ea_openCard.jspa">
				<img src="<%=basePath%>images/supermarket/scale/kt.png"/>
				<p>开通购物卡</p>
				<a/>
			</li>
			<li>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_03.png"/>--%>
				<%--<p>打印明细</p>--%>
			</li>
			<li class="gwc">
				<img src="<%=basePath%>images/supermarket/scale/img_shop.png"/>
				<p>购物车</p>
				<span>
							0
						</span>
			</li>
		</ul>
	</div>
</header>
<div class="chengzhong">
	<div class="box">
		<div>
			无码称重
			<img src="<%=basePath%>images/supermarket/scale/close.png"/>
		</div>
		<ul class="chengzhong_js">
			<li>
				<p>商品名称：</p><span class="goodsname">苹果</span>
			</li>
			<li>
				<p>商品plu：</p> <span class="plu">0315</span>
			</li>
			<li>
				<p>商品单价：</p>
				<span class="pr"></span>元
				<span class="prcc" style="display: none;"></span>
				<span class="hhh" style="display: none;"></span>
				<span class="pd" style="display: none;"></span>
				<span class="ppprit" style="display: none;"></span>
				<span class="cccostm" style="display: none;"></span>
				<span class="aaactivityID" style="display: none;"></span>
			</li>
			<li  class="weight">
				<p>商品重量：</p> <span class="wvalue">10</span>KG
			</li>
			<li class="inputnum" style="display: none;">
				<p>商品个数：</p> <input type="text" id="inputnum">PCS</span>
			</li>
			<li>
				<p>合计：¥</p><span class="totalMoney">0.00</span>

			</li>
		</ul>
		<div class="clearfix">
			<p id="printLabel">
				打印价签
			</p>
			<p id="jrgwc">
				加入购物车
			</p>
		</div>
	</div>
</div>
<div class="cz2">
	<div id="t-keybord2">
		<input id="t-value2" disabled type="text" ></input>
		<ul id="t-keyvalue2" class="clearfix">
			<li>1</li>
			<li>2</li>
			<li>3</li>
			<li>4</li>
			<li>5</li>
			<li>6</li>
			<li>7</li>
			<li>8</li>
			<li>9</li>
			<div id="t-del2">删除</div>
			<li>0</li>
			<div id="t-close2" class="t-button">清除</div>
		</ul>
		<div id="t-qx2" class="t-button">取消</div>
		<div id="t-submit2" class="t-button">确定</div>
	</div>
</div>
<div class="cz">
	<div id="t-keybord">
		<input id="t-value" disabled type="text" ></input>
		<ul id="t-keyvalue" class="clearfix">
			<li>1</li>
			<li>2</li>
			<li>3</li>
			<li>4</li>
			<li>5</li>
			<li>6</li>
			<li>7</li>
			<li>8</li>
			<li>9</li>
			<div id="t-del">删除</div>
			<li>0</li>
			<div id="t-close" class="t-button">清除</div>
		</ul>
		<div id="t-qx" class="t-button">取消</div>
		<div id="t-submit" class="t-button">确定</div>
	</div>
</div>
<div class="content">
	<ul class="left fcate">
		<li class="active">
			全部
		</li>
		<%--<li>--%>
			<%--蔬菜--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--水果--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--干货--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--膨化食品--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--冷冻食品--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--糖果甜食--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--饮品饮料--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--水果--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--干货--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--膨化食品--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--冷冻食品--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--糖果甜食--%>
		<%--</li>--%>
		<%--<li>--%>
			<%--饮品饮料--%>
		<%--</li>--%>
	</ul>

	<section class="right">
		<form  id  = "form" name = "form" class="form1" method="post"  target="hidden" action="<%=basePath%>ea/sm/ea_payErCode.jspa?posNum=${param.posNum}&ccompanyID=${param.ccompanyID}">
			<input type="submit" name="submit" style="display:none;"/>
			<input type="hidden" name="message" value="0"/>
			<input type="hidden" name="companyID" value="${companyID}"/>
		<div class="jiesuan">
			<p></p>
			<section>
				<ul class="clearfix">
					<li class="li_xh">
						序号
					</li>
					<li class="li_name">
						商品名称
					</li>
					<li class="li_l">
						数量/重量
					</li>
					<li class="tprice">
						金额(元)
					</li>
				</ul>
				<div class="jiesuan_box shop">
					<%--<ul class="muban1">--%>
						<%--<span></span>--%>
						<%--<li>--%>
							<%--184克豆豉海鲜鱼罐头--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<img class="jian" src="<%=basePath%>images/supermarket/scale/jian.png"/>--%>
							<%--<input disabled type="text" value="1"></input>--%>
							<%--<img class="jia" src="<%=basePath%>images/supermarket/scale/jia.png"/>--%>
						<%--</li>--%>
						<%--<li>--%>

						<%--</li>--%>
						<%--<li>--%>
							<%--10.80--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<img src="<%=basePath%>images/supermarket/scale/img_close.png"/>--%>
						<%--</li>--%>
					<%--</ul>--%>
					<%--<ul class="muban2">--%>
						<%--<span></span>--%>
						<%--<li>--%>
							<%--184克豆豉海鲜鱼罐头--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<span>5.8</span>KG--%>
						<%--</li>--%>
						<%--<li>--%>

						<%--</li>--%>
						<%--<li>--%>
							<%--10.80--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<img src="<%=basePath%>images/supermarket/scale/img_close.png"/>--%>
						<%--</li>--%>
					<%--</ul>--%>
					<%--<ul class="clearfix shuliang jh">--%>
						<%--<li>--%>
							<%--184克豆豉海鲜鱼罐头--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<img class="jian" src="<%=basePath%>images/supermarket/scale/jian.png"/>--%>
							<%--<input disabled type="text" value="1"></input>--%>
							<%--<img class="jia" src="<%=basePath%>images/supermarket/scale/jia.png"/>--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--6.80--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--6.80--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<img src="<%=basePath%>images/supermarket/scale/img_close.png"/>--%>
						<%--</li>--%>
					<%--</ul>--%>
					<%--<ul class="jh">--%>
						<%--<li>--%>
							<%--184克豆豉海鲜鱼罐头--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<span>5.8</span>KG--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--10.80--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--58.80--%>
						<%--</li>--%>
						<%--<li>--%>
							<%--<img src="<%=basePath%>images/supermarket/scale/img_close.png"/>--%>
						<%--</li>--%>
					<%--</ul>--%>

				</div>
				<div style="display: none">
					<div class="bigBag">
						<span class="goodsName"></span>
						<span class="price"></span>
                        <input type="hidden" class="barCode" />
						<input type="hidden" class="companyid" />
						<input type="hidden" class="pid" />
						<input type="hidden" class="pritype" />
						<input type="hidden" class="costmoney" />
						<input type="hidden" class="activityID" />
					</div>
					<div class="smallBag">
						<span class="goodsName"></span>
						<span class="price"></span>
						<input type="hidden" class="barCode" />
						<input type="hidden" class="companyid" />
						<input type="hidden" class="pid" />
						<input type="hidden" class="pritype" />
						<input type="hidden" class="costmoney" />
						<input type="hidden" class="activityID" />

					</div>
				</div>
			</section>
			<section class="clearfix">
				<p class="shop_num_2">
					总数：<span class="totalnum">0</span>件
				</p>
				<div class="shop_num_3">
					<p>合计：   ¥<span class="totalmoney">0</span></p>
					<p>
						<%--优惠：   -¥<span>25.00</span>--%>
					</p>
					<p>支付： ¥<span class="totalmoney">0</span></p>
				</div>
			</section>
			<section class="clearfix">
				<div class="clearfix">
					<p id="gig_shopping_bags" class="bag">大购物袋</p>
					<p id="small_shopping_bags" class="bag">小购物袋</p>
				</div>
				<p id="js_end">
					去结算  ¥<span class="totalmoney" id="paymoney">0</span>
				</p>
			</section>

		</div>
		</form>

		<div>
			<ul class="clearfix ul_list scate">
				<%--<li class="active">--%>
					<%--苹果--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
				<%--<li>--%>
					<%--香蕉--%>
				<%--</li>--%>
			</ul>
		</div>
		<ul class="clearfix ul_list_sp">
			<%--<li data-name = "001">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富--%>
					<%--红富士苹果红富--%>
					<%--红富士苹果红富--%>
					<%--红富士苹果红富--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li class="li_cz" data-name = "002">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果--%>
				<%--</p>--%>
				<%--<span>10.00</span>--%>
			<%--</li>--%>
			<%--<li class="li_cz" data-name = "003">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士大苹果--%>
				<%--</p>--%>
				<%--<span>12.00</span>--%>
			<%--</li>--%>
			<%--<li data-name="004">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="005">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="006">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="007">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="008">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="009">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="010">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="011">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="012">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="013">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="014">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="015">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li data-name="016">--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
			<%--<li>--%>
				<%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
				<%--<p class="txt_2">--%>
					<%--红富士苹果红富 最多两行...--%>
				<%--</p>--%>
				<%--<span>10.80</span>--%>
			<%--</li>--%>
		</ul>
	</section>
</div>

<!--交接班弹框 2019.1.25-->
<div class="alert_hand_">
	<div class="alert_address alert_hand">
		<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" id="del">
		<h1>交接班</h1>
		<ul class="text jjb">
			<li>店名：<span class="companyName">溯源互帮超市</span></li>
			<li>收银员：<span class="staffcode">1001</span></li>
			<li>POS终端：<span class="pos">0001</span></li>
			<li>交接班时间：<span class="changeDate"><fmt:formatDate value="<%=new Date()%>"  pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
			<li>交班地址：<span class="companyAddress">四川省成都市锦江区龙启小区1709</span></li>
		</ul>
		<div class="btn">
			<input type="button" value="交接班并登出">
		</div>
	</div>
</div>
<!--交接班弹框 2019.1.25-->
<div class="alert_hand-pd_">

	<div class="alert_hand-pd alert_hand-pd-1">
		<form action="" onsubmit="return check(this)" method="post" autocomplete=off>
			<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" class="del">
			<div class="text">
				<p>输入收银员密码</p>
				<input type="password" value="" placeholder="请输入密码" onclick='test(this);' id="psd" autocomplete=off>
			</div>
			<input type="submit" value="确定" id="cfmpsw">
		</form>
	</div>

	<div class="alert_hand-pd alert_hand-pd-2">
		<%--<img src="<%=basePath%>images/supermarket/ico-del-2.png" alt="" class="del">--%>
		<div class="text">
			<p>交接班完成</p>
			<input type="text" value="" placeholder="输入打印份数默认为1" id="printNum" onclick='test(this);'>
		</div>
		<div class="btn">
			<input type="button" value="再打印" class="printAgain">
			<input type="button" value="直接退出" class="right exit">
		</div>

	</div>

</div>

<div class="alert_weigh_">
	<div class="alert_weigh">
		<p><span>3</span>秒后自动关闭</p>
		<h2 class="tipcon"></h2>
		<input type="button" value="确定" id="confirm">
	</div>
</div>
<div id="softkey"></div>
<iframe name="hidden" width="0" height="0" />
</body>
</html>
