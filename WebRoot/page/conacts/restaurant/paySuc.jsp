<%@ taglib prefix="c" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>css/contacts/Restaurant/base.css">
<link rel="stylesheet"
	href="<%=basePath%>css/contacts/Restaurant/QR_scan.css">

<title>支付成功</title>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sccid='<%=((TEshopCusCom) session.getAttribute("key_shop_cus_com")) != null
					? ((TEshopCusCom) session.getAttribute("key_shop_cus_com"))
							.getSccId() : ""%>';
    var sccId = "${sccId}";
    if(sccId==null){
        sccId =sccid;
	}
    var cashierBillsID = "${cashierBillsID}";
    var jump = "${param.jump}";
    if(jump==""||jump==null){
        jump = "${jump}";
    }
    var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    $(document).ready(function(){
           if(cashierBillsID!=null&&cashierBillsID!="") {
               document.location.href = basePath + "ea/pobuy/ea_getCashBill.jspa?cbid=" + cashierBillsID + "&sccId=" + sccId + "&lastPay=close";
           }

        if(jump=="smsk"){
            // 3秒后执行函数
            setTimeout(function() {
                document.location.href = basePath
                    + "ea/earth/ea_earthIndex.jspa";
                return false;
            }, 1000); // 单位：毫秒

        }
    	try {
			if (isAndroid == true) {
				Android.speechOutputForAndroid("支付成功");
			} else if (isiOS == true) {
				console.log("声音提醒开发中");
			}

		} catch (err) {
			console.log("报错了");
		}
    });

        function compelte() {
            if(jump=="book"){

			document.location.href = basePath
					+ "ea/mappointment/ea_theTestTime.jspa?sccId=" + sccid;
			return false;
		}
		
		 if(jump=="phl"){
              
			document.location.href = basePath
					+ "ea/wfjshop/ea_getWFJshops.jspa";
			return false;
		}
            if(jump=="smsk"){

                document.location.href = basePath
                    + "ea/earth/ea_earthIndex.jspa";
                return false;
            }
		try {
			if (isAndroid == true) {
				Android.close();
			} else if (isiOS == true) {
				var url = "func=" + 'doneClose';
				window.webkit.messageHandlers.Native.postMessage(url);
			}

		} catch (err) {
			var ua = navigator.userAgent.toLowerCase();
			var isWeixin = ua.indexOf('micromessenger') != -1;
			if (isWeixin) {
				WeixinJSBridge.call('closeWindow');
			} else {
				self.opener = null;
				self.open('', '_self');
				self.close();
			}
		}
	}
</script>
</head>

<body>

	<div class="wrap_page">
		<div class="completed_wrap">
			<img
				src="<%=basePath%>images/contacts/restaurant/scanCodePay/success_ico.png"
				alt=""> <span>${param.flag=='isCard'?"开通":"支付"}成功</span>
		</div>
		<button onclick="compelte()" class="QRpay_btn" style="width:100px;">完成</button>
	</div>
</body>
</html>
