<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>

<%@ page language="java" pageEncoding="UTF-8"%>
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
<title>手机支付</title>
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/font-size.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-zf.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/marketing/supermarket/selfService_ppay.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>


	<script type="text/javascript">
		var basePath = "<%=basePath%>";
		var ddid = "${journalNum}";
		var totalMoney = "${param.totalMoney}";
        var remainMoney = "${param.remainMoney}";
		var companyName="${companyName}";
        var zffs = 1;//选择的支付方式   默认为支付宝
		var jifen = "${jifen}";
		var jb = "${jb}";
		var t = 0;
        var posNum = "${param.posNum}";
        var  addressID = "${staffaddress.addressID}";
        var  cardNum = "${param.cardNum}";
        var vipmoney = "${vipmoney}";
        var fh  = "${param.fh}";
        var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
       $(function () {
		   if(remainMoney!=""&&remainMoney!=null&&Number(remainMoney)<Number(totalMoney)){
               $(".tm").text(remainMoney);
               $("#sub").val("确认支付￥"+remainMoney);
               $(".sy").show();
               $("#jfdh-li").hide();
               $("#jbdh-li").hide();
		   }else{
               $(".sy").hide();
		   }
       })

	</script>

</head>

<section class="Payment Payment2">
	<article>
		<div class="head">
			<div class="money">
				<h2><span class="sy">积分或者金币已抵扣部分金额，剩余</span>支付金额: &yen;<span class="tm">${param.totalMoney}</span></h2>
				<h2 class="vipjg" style="display: none;">会员专享价: &yen;<span>${vipmoney}</span></h2>
			</div>
			<div class="address" style="display:none;">
				<ul>
					<li class="">收货人：${staffaddress.consignee}</li>
					<li class="right">${staffaddress.phone}</li>
				</ul>
				<div class="map" id="addr">
					<img src="<%=basePath%>images/supermarket/gps.png" alt="">
					<p><c:if test="${staffaddress.area eq null||staffaddress.area eq ''}">请选择收货地址</c:if>${staffaddress.area}${staffaddress.addressDetailed}</p>
					<img src="<%=basePath%>images/supermarket/ico-right.png" alt="">
				</div>
			</div>

		</div>
		<div class="mode">
			<h4>选择支付方式</h4>
			<ul>
				<li class="1" >
					<img src="<%=basePath%>images/supermarket/p-1.png" alt="" class="logo">
					<h5 class="name">支付宝支付</h5>
					<p class="gou">
						<img src="<%=basePath%>images/supermarket/p-g.png" alt="" style="display: inline">
					</p>
				</li>
				<li class="3" >
					<img src="<%=basePath%>images/supermarket/p-2.png" alt="" class="logo">
					<h5 class="name">微信支付</h5>
					<p class="gou">
						<img src="<%=basePath%>images/supermarket/p-g.png" alt="">
					</p>
				</li>
				<%--<li class="4">--%>
					<%--<img src="<%=basePath%>images/supermarket/p-3.png" alt="" class="logo">--%>
					<%--<h5 class="name">银联支付</h5>--%>
					<%--<p class="gou">--%>
						<%--<img src="<%=basePath%>images/supermarket/p-g.png" alt="">--%>
					<%--</p>--%>
				<%--</li>--%>
				<c:if test="${param.cardNum ==''|| param.cardNum == null}">
				<c:if test="${jb ne 'dj'}">
					<li class="4" id="jbdh-li">
						<img src="<%=basePath%>images/supermarket/jb.png" alt="" class="logo">
						<h5 class="name">金币兑换(金币为${jb} <span class="jbtip"></span>)</h5>
						<p class="gou">
							<img src="<%=basePath%>images/supermarket/p-g.png" alt="">
						</p>
					</li>
				</c:if>
				</c:if>
				<c:if test="${param.cardNum ==''|| param.cardNum == null}">
				<li class="2" id="jfdh-li">
					<img src="<%=basePath%>images/supermarket/p-4.png" alt="" class="logo">
					<h5 class="name">积分兑换( 积分为${jifen} <span class="jftip"></span>)</h5>
					<p class="gou">
						<img src="<%=basePath%>images/supermarket/p-g.png" alt="">
					</p>
				</li>
				</c:if>
			</ul>
			<input type="button" value="确认支付￥${vipmoney eq ""?param.totalMoney:vipmoney}" id="sub" onclick="zf()">
		</div>
	</article>
	<div class="alert_dh">
		<div class="zfz">

			<img src="<%=basePath%>images/supermarket/zfz.png" alt="">
			<p>正在支付中...</p>
		</div>
	</div>
</section>

<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>
</body>
</html>