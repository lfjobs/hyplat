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
    <title>查看物流</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
    <script type="text/javascript" src="<%=basePath %>${ctx}/js/jquery-1.9.1.min.js"></script>

	<script type="text/javascript">
		var cashid="";
		/*alert(cashid);*/
		var flag='fhwl';
        var basePath = "<%=basePath%>";
        <%--var exCode="111";--%>
        <%--var waybillno="${requestData.waybillno}";--%>
	</script>
</head>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        <li style="width: 80%;text-align: center;">查看物流</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content_hidden">
    <div class="content">
    <div class="mil">
        <div class="left">
            <%--<img src="<%=basePath %>images/ea/finance/NewPhoneOrders/shop1.jpg" alt="">--%>
                <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/shop1.jpg" alt="">
        </div>
        <div class="right">
            <h3>物流状态<span id="state"></span></h3>
            <h4>承运公司：<span id="expName"></span></h4>
            <h4>运单编号：<span id="number"></span></h4>

        </div>
    </div>
    <div class="tracking">
        <h1>物流跟踪</h1>
    </div>
    <div class="tracking_txt" id="div">
        <ul id="ul">
        </ul>
    </div>
    <hr style="border-top: 10px solid #ddd;margin: 0 0 0.5rem 0;">
</div>
</div>

<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92+"px");
        (function ($) {
            $.getUrlParam = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return decodeURI(r[2]);
                return null;
            }
        })(jQuery);

        cashid = $.getUrlParam("cbid");
//        alert(cashid)
        if(cashid==null||cashid==''){
            cashid='${cashid}';
        }
        wuLiu(cashid);
    })


    function wuLiu(cashid){

    	var url="";
    	if(flag=='fhwl'){
    		url=basePath+"ea/pobuy/sajax_getWuliuDetails.jspa?cbid="+cashid;
    	}else{
    		url= basePath +"ea/refundMoney/sajax_wl.jspa?cashId="+cashid;
    	}
   	    $.ajax({
   	    	url : url,
  		    type : "get",
  		  	async : false,
  		  	dataType : "json",
  		  	success : function(data){
  			 	 var member = eval("("+data+")");

                var Success = member.msg;
//               alert("状态码：" + Success);
                var noSta = member.status;//获取快递单号状态
//              alert("快递单号状态："+noSta)
                if (Success !="ok"||noSta!=0) {
                    if (noSta==201){
                        $("#state").text("运单号为空，请核实后再次查询");
                    }else if (state == 202) {
                        $("#state").text("快递公司为空，请核实后再次查询");
                    }else if (state == 203) {
                        $("#state").text("快递公司不存在，请核实后再次查询");
                    }else if (state == 204) {
                        $("#state").text("快递公司识别失败，请核实后再次查询");
                    }else if (state == 205) {
                        $("#state").text("没有信息；单号错误 (一个单号对应多个快递公司，请求须指定快递公司)");
                    }else if (state == 207) {
                        $("#state").text("错误单号；一个单号对应多个快递公司，请求须指定快递公司");
                    }else{
                        $("#state").text("没有信息");
                    }
                    var str = new Array();
                    str.push('<li class="mil">');
                    str.push('<div class="yuan"></div>');
                    str.push('<div class="yuan"></div>');
                    str.push('<h3>此单无物流信息</h3>');
                    str.push('<span></span>');
                    str.push('<hr style="border-top: 1px solid #f9f9f9;margin: 0.5rem 0;border-left: none;">');
                    str.push('</li>');
                    $("#ul").append(str.join(""));
                } else {
                    var number=member.result.number;
                    var expName =member.result.expName;
                    $("#number").text(number);
                    $("#expName").text(expName);
                    var state = member.result.deliverystatus;//获取快递单号状态
//                   alert("快递状态"+state);
                    if (state == 0) {
                        $("#state").text("揽件");
                    } else if (state == 1) {
                        $("#state").text("在途中");
                    } else if (state == 2) {
                        $("#state").text("正在派件");
                    } else if (state == 3) {
                        $("#state").text("已签收");
                    } else if (state == 4) {
                        $("#state").text("派送失败（无法联系到收件人或客户要求择日派送，地址不详或手机号不清）");
                    } else if (state == 5) {
                        $("#state").text("疑难件（收件人拒绝签收，地址有误或不能送达派送区域，收费等原因无法正常派送）");
                    } else if (state == 6) {
                        $("#state").text("退件签收");
                    } else {
                        $("#state").text("无信息");
                    }
                   var state = member.state;//获取快递状态
//                     alert(state);

                    //物流跟踪
                    var traces = member.result.list;
//                   alert("物流轨迹list"+traces);
                    if (traces == "") {
                        var str = new Array();
                        str.push('<li class="mil">');
                        str.push('<div class="yuan"></div>');
                        str.push('<div class="yuan"></div>');
                        str.push('<h3>此单无物流信息</h3>');
                        str.push('<span></span>');
                        str.push('<hr style="border-top: 1px solid #f9f9f9;margin: 0.5rem 0;border-left: none;">');
                        str.push('</li>');
                        $("#ul").append(str.join(""));
                    } else {

                        var str = new Array();
                        for (var i = 0; i < traces.length - 1; i++) {
                            var wl = traces[i];
                            str.push('<li class="mil">');
                            str.push('<div class="yuan"></div>');
                            str.push('<div class="yuan"></div>');
                            /* str.push( '<h3>商品正在等待揽见</h3>'; */
                            str.push('<h3>' + wl.status + '</h3>');
                            str.push('<span>' + wl.time + '</span>');
                            str.push('<hr style="border-top: 1px solid #f9f9f9;margin: 0.5rem 0;border-left: none;">');
                            str.push('</li>');
                        }
                        str.push('<li class="mil">');
                        str.push('<div class="yuan"></div>');
                        str.push('<div class="yuan"></div>');
                        str.push('<h3>商品正在等待揽收</h3>');
                        str.push('<span>${refundSheet.backDate }</span>');
                        str.push('<hr style="border-top: 1px solid #f9f9f9;margin: 0.5rem 0;border-left: none;">');
                        str.push('</li>');
                        $("#ul").append(str.join(""));

                    }
  				 }

  		  	},
   	    });
    }
</script>

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

