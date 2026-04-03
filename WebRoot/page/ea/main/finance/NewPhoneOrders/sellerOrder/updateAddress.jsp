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
    <title>我的订单-更改地址</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    

</head>
<script>
var basePath = "<%=basePath%>";
var companyid = "${param.companyid}";
var staffid ="${param.staffid}";
var journalNum ="${param.oaBillId}";
var casid ="${param.casid}";
var sort ="${param.sort}";
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">更换地址</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content sel_con">
        <div class="add_div">
            <ul>
                <li>
                	<input  type="hidden" id="oaKey" value="${orderBill.oaKey}">
                    <h4>收货人姓名</h4>
                    <input type="text" id="receivename" value="${orderBill.receivename}">
                    
                </li>
                <li>
                    <h4>收货人电话</h4>
                    <input type="text" id="receivetel" value="${orderBill.receivetel}" onblur="phone()">
                </li>
                <li class="add">
                    <h4>收货人地址</h4>
                    <textarea rows="3" id="receiveaddress" >${orderBill.receiveaddress}</textarea>
                </li>
            </ul>
        </div>
        <a onclick="update()"><input type="button" value="更换地址" class="add_ipt"></a>
    </div>
</div>

<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");

    })
    function phone(){
		var phone = $("#receivetel").val(); 

		 var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[7-9])[0-9]{8}$/ ; //手机正则
		if(phone == ''){
			alert('手机还没填呢...'); 
			$("#receivetel").val(""); 
			return
		}else if(!PhoneReg.test(phone)){
			alert('手机格式错咯...'); 
			$("#receivetel").val(""); 
			return
		} 

		}
    function update(){
    	var phone = $("#receivetel").val(); 
    	if(phone == ''){
			alert('手机还没填呢...'); 
			
			return
		}
	    var oaKey = $("#oaKey").val();
	    var receivename = $("#receivename").val();
	    var receivetel = $("#receivetel").val();
	    var receiveaddress = $("#receiveaddress").val();
	  	var url = basePath+"/ea/seller/ea_updateAddress.jspa?oaKey="+oaKey
	  								+"&receivename="+receivename
	  								+"&receiveaddress="+receiveaddress
	  								+"&receivetel="+receivetel
	  								+"&oaBillId="+journalNum
	  								+"&cashierBillsID="+casid
	  								+"&type=05&companyid="+companyid+"&staffid="+staffid+"&sort="+sort;
    document.location.href = url;
    	
    
    }
</script>

<script>
    window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>

