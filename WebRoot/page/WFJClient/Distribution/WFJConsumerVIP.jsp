<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>消费者VIP会员</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
 
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

	<link href="<%=basePath%>css/WFJClient/wfjhtmlStyle.css" rel="stylesheet"/>
	<script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
  </head>
   <script type="text/javascript">
     var basePath ="<%=basePath%>";
     var yeshu=${pagenull};
     var cpid="";
  </script>
  <body>
   <div class="wfjall">
        <div class="wfjtop">
            <ul>
                <li>消费者VIP会员</li>
            </ul>
        </div>
		 <div class="wfjlogo">
            <img src="<%=basePath%>/images/WFJClient/Distribution/banner.png" />
        </div>
        <div class="all_width">
            <div class="wfjpro left">
                <div class="wfjp">
                    <ul>
                        <li>产品分类</li>
                    </ul>
                </div>
                <div class="wfjproduct">
                    <ul>
                        <li><a name="product" onclick="change_product(this)" href="#">服装</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">鞋</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#" style="color:#0094ff;">手机</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">电脑</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">品牌男装</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">内衣配饰</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">家具建材</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">居家生活</a></li>
                        <li><a name="product" onclick="change_product(this)" href="#">家具家纺</a></li>
                    </ul>
                </div>
            </div>
            <div class="wfjpro right">
                <div class="wfjproinfo">
                    <ul>
                        <li>
                            <img src="<%=basePath%>/images/WFJClient/Distribution/shouji1.jpg" /></li>
                        <li><a href="#">华为 P8</a></li>
                    </ul>
                    <ul>
                        <li>
                            <img src="<%=basePath%>/images/WFJClient/Distribution/shouji2.jpg" /></li>
                        <li><a href="#">华为 4C</a></li>
                    </ul>
                    <ul>
                        <li>
                            <img src="<%=basePath%>/images/WFJClient/Distribution/shouji3.jpg" /></li>
                        <li><a href="#">vivo C5</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="wfjcontent left">
            <div class="wfj_content">
                <div class="wfj_con">
                    <ul class="wfj_title title_height3">
                        <li>消费者VIP会员</li>
                    </ul>
                    <ul class="wfj_cons">
                        <li><a href="#">订单<span>></span></a></li>
                        <li><a href="#">收货单<span>></span></a></li>
                        <li><a href="#">积分<span>></span></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <jsp:include page="/page/WFJClient/Distribution/footer.jsp" />
    </div>
</body>
</html>