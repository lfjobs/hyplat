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
<title>二维码付款</title>
<link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<%--<script src="<%=basePath%>/js/qrcode.js"></script>--%>
<script src="<%=basePath%>js/ea/marketing/supermarket/selfService_pay.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/qrcode2.js"></script>
<script  type="text/javascript" src="<%=basePath%>js/utf.js"></script>
<script  type="text/javascript" src="<%=basePath%>js/jquery.qrcode.js"></script>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
        var  journalNum = "${journalNum}";
        var totalMoney = "${totalMoney}";
        var remainMoney = "${param.remainMoney}";
        var sccid = "${sccid}";
        var timer = "";
        var cardNum = "${param.cardNum}";
        var posNum = "${param.posNum}";
        var ccompanyID  = "${param.ccompanyID}";
        var  cresccid = "";
        var directUrl = "${param.directUrl}";
        var comID = "${comID}";
        var fh = "${param.fh}";
        var remainMoney = "${param.remainMoney}";
        var fhform = "${param.fhform}";
        $(function(){

            if(fhform=="3"){
                if(remainMoney!=""&&remainMoney!=null){
                    if(remainMoney!=totalMoney){
                         $(".tmy").text(remainMoney);

					}
				}
            }

            if(posNum!=null&&posNum!=""){
                if(cardNum!=null&&cardNum!=""){
                    $(".warn1").hide();
                    $(".warn2").show();
                    $(".barcode").focus();
				}else{
                    if(fh=="1"){
                        $(".warn1").hide();
                        $(".warn2").show();
                        $(".barcode").focus();
					}else  if(fh=="2"){
                        $(".warn1").show();
                        $(".warn2").hide();
					}

                }


			}else{
                $(".warn1").hide();
                $(".warn2").show();
                $(".barcode").focus();
			}


            $(".barcode").blur(function(){

                $(".barcode").focus();
            });

        });

        function onFocus () {
            var target = event.target
            setTimeout(function () {
                target.readOnly = false
            },0)
        }
        function onBlur() {
            event.target.readOnly = true
        }

	</script>

</head>



<%--应付总金额：￥<span>${totalMoney}</span>(共<span>${totalNum}</span>件)--%>
<%--<div id="qrcode" style="width:151px;height: 151px ;margin: 250px auto 0px auto" ></div>--%>
<%--<input type="button" value="取消订单" id="corder"/><input type="button" value="修改商品" id="uorder" />--%>

<section class="code-pay">
	<!--扫码支付内容-->
	<article>
		<!--头部-->
		<header>
			<img src="<%=basePath%>images/supermarket/code-pay-header_r.png" alt="">


				<p class="warn1">1.打开(微分金APP）在快捷应用中“扫一扫”支付<br/>
					2.微信“扫一扫”支付</p>
				<p class="warn2">方式一： 打开微分金APP或微信“扫一扫”支付<br/>
					方式二：打开微信/支付宝将付款码置于扫描口下</p>


			<a href="javascript: window.history.go(-1);"><input type="button" value="返回"></a>

		</header>
		<!--头部 end-->
		<!--内容-->
		<figure>
			<p class="amount">应付总额：<span class="tmy">${totalMoney}</span>(共<span>${totalNum}</span>件)</p>
			<div id="qrcode"></div>
		<%--<img src="<%=basePath%>images/supermarket/code.png" alt="" id="code">--%>
			<div class="btn">
				<input type="button" value="取消订单" id="cancel" readonly="readonly">
				<input type="button" value="修改商品" id="amend"  readonly="readonly">
				<input type="text" class="barcode" onfocus="onFocus()" onblur="onBlur()" style="opacity:0;"/>
			</div>
			<p class="p1">点击下载(微分金APP)一次扫码全搞定呦</p>
		</figure>
		<!--内容 end-->
	</article>
	<!--扫码支付内容 end-->
</section>
<div class="alert_cancel_"></div>
<div class="alert_cancel">
	<h2>取消订单</h2>
	<p>确定取消订单吗</p>
	<figure>
		<input type="button" value="去意已决">
		<input type="button" value="我再想想" id="xx">
	</figure>
</div>
<div class="alert_download">
	<div id="qrcode1"></div>
	<%--<img src="<%=basePath%>images/supermarket/code.png" class="code">--%>
	<p>打开手机扫一扫，下载微分金APP</p>
</div>
<div class="alert_weigh_">
	<div class="alert_weigh">
		<p><span>3</span>秒后自动关闭</p>
		<h2 class="tipcon"></h2>
		<input type="button" value="确定" id="confirm">
	</div>
</div>
</body>
</html>