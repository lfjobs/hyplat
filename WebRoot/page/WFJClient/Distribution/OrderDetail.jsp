<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
	<title>订单详情</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript"></script>
	<link href="<%=basePath%>/css/WFJClient/wfjhtmlStyle.css" rel="stylesheet" />
	<link href="<%=basePath%>css/WFJClient/wfjstyle.css" rel="stylesheet" />
	
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
	<script src="<%=basePath%>js/ea/wechat/wechartMenugrcp.js"></script>
	
	<script type="text/javascript">
		var basePath='<%=basePath%>';
		var token=0;
		var cpid="";
		var notoken=0;
	</script>
	</head>
		<body>
			 <div class="Orderdetail">
        <div class="pro_top">
            <ul>
                <li class="left"><a style="color:#ffffff;padding-left:15px;font-weight:bold;" href="#"><</a></li>
                <li class="pro_info">订单详情</li>
            </ul>
        </div>
        <div class="careless">
            <table style="width:100%">
                <tr>
                    <td class="table_left"><img src="<%=basePath%>/images/WFJClient/Distribution/order_product.png" /></td>
                    <td>健安喜葡萄糖汁胶囊</td>
                </tr>
                <tr>
                    <td class="table_left">收货人姓名</td>
                    <td class="table_right">小灰灰</td>
                </tr>
                <tr>
                    <td class="table_left">收货人手机号</td>
                    <td class="table_right">13333333333</td>
                </tr>
                <tr>
                    <td class="table_left">收货人地址</td>
                    <td class="table_right">北京市东直门外大街42号宇飞大厦801室</td>
                </tr>
                <tr>
                    <td class="table_left">下单日期</td>
                    <td class="table_right">2015年6月9日 11:09:10</td>
                </tr>
            </table>
        </div>
        <div class="careless">
            <table style="width:100%">
                <tr>
                    <td class="table_left"><img src="<%=basePath%>/images/WFJClient/Distribution/order_product.png" /></td>
                    <td>健安喜葡萄糖汁胶囊</td>
                </tr>
                <tr>
                    <td class="table_left">收货人姓名</td>
                    <td class="table_right">小灰灰</td>
                </tr>
                <tr>
                    <td class="table_left">收货人手机号</td>
                    <td class="table_right">13333333333</td>
                </tr>
                <tr>
                    <td class="table_left">收货人地址</td>
                    <td class="table_right">北京市东直门外大街42号宇飞大厦801室</td>
                </tr>
                <tr>
                    <td class="table_left">下单日期</td>
                    <td class="table_right">2015年6月9日 11:09:10</td>
                </tr>
            </table>
        </div>
        <div class="careless">
            <table style="width:100%">
                <tr>
                    <td class="table_left"><img src="<%=basePath%>/images/WFJClient/Distribution/order_product.png" /></td>
                    <td>健安喜葡萄糖汁胶囊</td>
                </tr>
                <tr>
                    <td class="table_left">收货人姓名</td>
                    <td class="table_right">小灰灰</td>
                </tr>
                <tr>
                    <td class="table_left">收货人手机号</td>
                    <td class="table_right">13333333333</td>
                </tr>
                <tr>
                    <td class="table_left">收货人地址</td>
                    <td class="table_right">北京市东直门外大街42号宇飞大厦801室</td>
                </tr>
                <tr>
                    <td class="table_left">下单日期</td>
                    <td class="table_right">2015年6月9日 11:09:10</td>
                </tr>
            </table>
        </div>
    </div>
		</body>
	</html>