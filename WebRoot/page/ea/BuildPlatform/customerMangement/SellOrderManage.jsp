<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>客户订单管理</title>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
	<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a href="<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa" class="back"></a>
        <h1>客户订单管理</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
       
       
        <section class="tab_con">
                <div class="order_m_box order_m_box1">
                    <i class="order_man_ico order_man_ico5"></i>
                    <span>订单管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="order_m_box order_m_box8">
                    <i class="order_man_ico order_man_ico5"></i>
                    <span>新版订单管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="order_m_box order_m_box2">
                    <i class="order_man_ico order_man_ico6"></i>
                    <span>收货管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="order_m_box order_m_box3">
                    <i class="order_man_ico order_man_ico7"></i>
                    <span>退货管理</span>
                    <i class="order_arr_R"></i>
                </div>
        </section>
    </div>

    <!--  页面内容 end -->
	<script>
	var basePath='<%=basePath%>';
    var companyid='${ sessionScope.companyid}';
    var staffid='${ sessionScope.staffid}';
    	
  //卖家订单管理
   	$(".order_m_box1").click(function(){
   		document.location.href = basePath+"ea/seller/ea_getcomporder.jspa?companyid="+companyid+"&staffid="+staffid;
    });
	//卖家收货单
	$(".order_m_box2").click(function(){
		document.location.href = basePath+"ea/seller/ea_getReceipt.jspa?sta=00&companyid="+companyid;
	});
   	//卖家退货单
    $(".order_m_box3").click(function(){
    	document.location.href = basePath+"ea/seller/ea_getReturnOrder.jspa?companyid="+companyid;
    });
    //新版订单管理
    $(".order_m_box8").click(function () {
        document.location.href = basePath + "/page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?&companyid="+companyid+ "&staffid=" + staffid+"&sort=1";
    });
    
	</script>
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>
     <script>
        $(function(){
            $(".tab_con").eq(0).show();//初始化tab显示          
        })
    </script>    
</body>
</html>