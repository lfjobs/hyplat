<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<title>微信安全支付</title>
<meta charset="UTF-8">
<meta name="viewport" content=" initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>





<script>
	$(function(){
		$(".loading").attr("style","position: absolute;top:"+window.innerHeight*0.38+"px;")
		$(".loading").find("img").css("width",window.innerWidth*0.6);
		$(".loading").find("img").css("height",window.innerHeight*0.14);
		$(".loading").find("img").css("position","relative");
		$(".loading").find("img").css("left",window.innerWidth*0.17)
	})
var code = "<%=request.getParameter("code")%>";
var params = "<%=request.getParameter("params")%>";
var staffID = "";
var journalNum = "";
var total = "";

var  goodsname = "";



	$(document).ready(function() {
		var url ="<%=basePath%>/ea/wfjshop/sajax_ea_weChatpay.jspa";
		$.ajax({
			url:url,
			aysnc:false,
			type:"get",
			dataType:"json",
			data:{
				code:code,
				params:params
			},
			success:function(data){
				$(".loading").hide();
				var me = eval("("+data+")");
				var finalPackage = me.finalPackage;
                journalNum = me.journalNum;
				staffID = me.staffID;
                goodsname = me.goodsname;
				total = me.total;
                attach = me.attach;
				appId = finalPackage.appId;
				timeStamp =finalPackage.timeStamp;
				nonceStr = finalPackage.nonceStr;
				packages = finalPackage.packages;
				signType = finalPackage.signType;
				paySign = finalPackage.paySign;

				if (typeof WeixinJSBridge ==

				"undefined") {
					if (document.addEventListener)

					{

						document.addEventListener(

						'WeixinJSBridgeReady',

						onBridgeReady, false);
					} else if

					(document.attachEvent) {
						document.attachEvent(

						'WeixinJSBridgeReady',

						onBridgeReady);
						document.attachEvent(

						'onWeixinJSBridgeReady',

						onBridgeReady);
					}
				} else {
					onBridgeReady();
				}
			},
			erorr:function(){
				alert("支付失败")
			}
			
			
		});
			

			});


	function onBridgeReady() {

		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : appId, //公众号名称，由商户传入
			"timeStamp" : timeStamp, //时间戳
			"nonceStr" : nonceStr, //随机串
			"package" :packages,//扩展包
			"signType" : signType, //微信签名方式:1.sha1
			"paySign" : paySign
		//微信签名
		}, function(res) {
			if (res.err_msg == 'get_brand_wcpay_request:ok') {
				 window.location.href = "<%=basePath%>ea/wfjshop/ea_jumpResult.jspa?ddid="+journalNum+"&morre="+total+"&goodsname="+goodsname+"&staffid="+staffID+"&attach="+attach;
			          
			} else if(res.err_msg == 'get_brand_wcpay_request:cancel'){
                 //  alert("支付过程中用户取消支付");

            }else {
            	   alert(res.err_msg);
                  // alert("支付失败");


							}

						});
	}

</script>
</head>
<body>
<center>
	<div class="loading"><img src="<%=basePath%>/images/WFJClient/loading14.gif"></div>
</center>
</body>

</html>
