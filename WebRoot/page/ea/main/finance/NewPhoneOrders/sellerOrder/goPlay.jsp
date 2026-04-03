<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>发货成功</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js""></script>
</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">我的订单</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content">
        <div class="head_img">
            <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/gou.png" alt="">
            <h3>恭喜您订单发货成功！</h3>
        </div>
        
            <div class="btn_thd" onclick="dianji()">
                <p><a ><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/return.png" alt=""></a>返回订单</p>
            </div>
       
    </div>
</div>

<script>
var basePath = "<%=basePath%>";
function dianji(){
	var companyid = "${param.companyid}";
	var staffid = "${param.staffId}";
	var url = basePath+"ea/seller/ea_getcomporder.jspa?companyid="+companyid
							+"&staffid="+staffid;
	
	document.location.href = url;
}
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");

    })
</script>

<script>
    window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>
