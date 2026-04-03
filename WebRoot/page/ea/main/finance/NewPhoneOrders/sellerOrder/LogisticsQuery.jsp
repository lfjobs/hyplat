<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>我的订单</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    

</head>
<script>
var basePath = "<%=basePath%>";
var companyid = "${param.companyid}";
var staffid ="${param.staffid}";

</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">查看物流</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content_hidden">
    <div class="content">
    <div class="mil">
        <div class="left">
            <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/shop1.jpg" alt="">
        </div>
        <div class="right">
      
            <h3>物流状态<span id="ant"></span></h3>
            <h4>承运公司：<span>${param.exCode}</span></h4>
            <h4>运单编号：<span>${param.waybillNumber}</span></h4>
        </div>
    </div>
    <div class="tracking">
        <h1>物流跟踪</h1>
    </div>
    <div class="tracking_txt">
        <ul id="ulp">
    </div>
    <hr style="border-top: 10px solid #ddd;margin: 0 0 0.5rem 0;">
</div>
</div>

<script>
var basePath="<%=basePath%>";
var exCode="${param.exCode}";
var waybillNumber="${param.waybillNumber}";
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");
       
        var url= basePath+"ea/seller/sajax_ea_logisticsQuery.jspa?exCode="+exCode+"&waybillNumber="+waybillNumber+"&type=00";
    	$.ajax({
    		url : encodeURI(url),
    		type : "get",
    		async : false,
    		dataType : "json",
    		success : function(data) {
    			var member = eval("(" + data + ")");
    			var stp =member.Traces;
				var cceptStation =stp.AcceptStation; 
				var str='';
				var state = member.State;
				console.log(state);
				
				for(var j = stp.length-1;j>0;j--){
					var pp=stp[j];
				
				str +='<li class="mil">';
				str +='<div class="yuan"></div>';
				str +='<h3>'+pp.AcceptStation+'</h3>';
				str +='<p>'+pp.AcceptTime+'</p>';
				str +='<hr style="border-top: 1px solid #f9f9f9;margin: 0.5rem 0;border-left: none;"></li>';
				
				}
				str +='<input type="hidden" id="state" value="'+state+'">';
				$("#ulp").append(str);
    //3910700079092
    		},
    		error : function(data) {
    		}
    	});

    	
    	 //物流状态: 0-无轨迹，1-已揽收，2-在途中 201-到达派件城市，3-签收,4-问题件
    	 if($("#state").val()=="0"){
    		 $("#ant").text("无轨迹");
        }else if($("#state").val()=="1"){
        	 $("#ant").text("已揽收");
        }else if($("#state").val()=="2"){
        	 $("#ant").text("在途中");
        }else if($("#state").val()=="3"){
        	 $("#ant").text("签收");
        }else if($("#state").val()=="4"){
        	 $("#ant").text("问题件");
        }else if($("#state").val()=="201"){
        	 $("#ant").text("到达派件城市");
        }

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

