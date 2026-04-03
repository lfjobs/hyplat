<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>退货单</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/ea/finance/NewPhoneOrders/refund_list.js"></script>
	<script type="text/javascript">	
	    var basePath='<%=basePath%>';
		var staffid="${staffid}";		
		var type = "01"
		var state;//退货 
		var t;//计时器
		var pagenumber=0;
		var pagecount=0;
		var pl="00";
	</script>

</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        <li style="width: 80%;text-align: center;">退货单</li>
    </ul>
</div>
<div class="header">
     <ul class="rec_head">
         <li class="active appraise" >退货中</li>
         <li class="successful">退货结束</li>
      </ul>
</div>
<div class="content_hidden">
    <div class="content rec_content">
        <div class="rec_con">
            <!--退货中-->
            <div class="rec_eva" id="appraise"></div>
            <!--退货结束-->
            <div class="rec_eva" id="successful"></div>
        </div>
    </div>
</div>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>

